define(function(require, exports, module) {
    var path = require('scaffold/path');
    var popupTip = require('component/popupTip');
    var FileUploadPlugin = require('component/fileUpload');
    var AreaSelectView = require('component/areaSelect');
    var baseFormView = require('baseView/baseFormView');
    var FieldsetView = baseFormView.FieldsetView;
    var FormDialogView = baseFormView.FormDialogView;
    var SketchItemView = baseFormView.SketchItemView;

    var $ = jQuery;
    var root = global_config.root;
    var upload_path = image_oss_host;
    var createFileDom = function(data) {
        var file = '<li class="item" data-filename="' + data.fileName + '" data-currentstoredkey="' + (data.currentStoredKey || '') + '">' +
            '<a target="_blank" class="filename" href="' + upload_path.original + '/' + data.currentStoredKey + '">' + data.fileName + '</a><a href="#" class="delete">删除</a>' +
            '</li>';
        return file;
    };

    var loadingImgEl = global_const.loadingImg.clone();

    var CustomerModel = Backbone.Model.extend({
        url: function() {
            if (this.isUpdate) {
                return root + '/preloan/customer/edit';
            } else {
                return root + '/preloan/customers/add';
            }
        },
        defaults: {
            "provinceCode": "",
            "cityCode": "",
            "name": "",
            "fName": "",
            "regNo": "",
            "industryUuid": "",
            "address": "",
            "contactPersons": [],
            "confidentialityAgreement": {},
            "creditQueryAuthorization": {},
            "archives": []
        },
        initialize: function(attr) {
            if (attr && attr.customerUuid) {
                this.isUpdate = true;
            } else {
                this.isUpdate = false;
            }
        },
        parse: function(resp) {
            return resp.data;
        },
        manualSave: function(opts) {
            var attr = this.toJSON();
            opts = $.extend({
                data: {
                    customerInfo: JSON.stringify(attr)
                },
                emulateJSON: true // 不支持发送 application/json 编码请求的Web服务器。序列化json,伪造HTML表单请求
            }, opts);
            this.save({}, opts);
        },
        lookupCommerce: function(name, success) {
            var url = root + '/preloan/enterprise-credit';

            var opt = {
                url: url,
                data: {
                    name: name
                },
                dataType: 'JSON'
            };

            opt.success = success;

            $.ajax(opt);
        }
    });

    var EditContacterView = FormDialogView.extend({
        template: _.template($('#contacterEditTmpl').html(), {
            variable: 'obj'
        }),
        initialize: function() {
            EditContacterView.__super__.initialize.apply(this, arguments);
            this.defineValidator();
        },
        defineValidator: function() {
            this.validator = this.$('.form').validate({
                rules: {
                    name: 'required',
                    email: 'email',
                    telephone:{ 
                    	 mPhoneExt:true
                    	}
                    
                }
            });
        },
        validate: function() {
            return this.validator.form();
        },
        submitHandler: function(e) {
            e.preventDefault();

            if (!this.validate()) return;

            this.save();

            this.hide();
        }
    });

    var ContacterItemView = SketchItemView.extend({
        template: _.template($('#contacterTtemTmpl').html()),
        toEdit: function() {
            var editView = new EditContacterView({
                model: this.model
            });
            editView.show();
        }
    });

    var CustomerEditView = FieldsetView.extend({
        el: '.form-wrapper',
        events: {
            'click #lookupCommerce': 'onClickLookupCommerce',
            'click .submit': 'submitHandler',
            'click .btn-contacter': 'onClickBtnContacter',
            'click #check-more': 'onClickMoreCommerce',
            'keypress [name="name"]': 'onChangeCustomerName',
            'keydown .form': function(e) {
                if (e.keyCode === 13) {
                    e.preventDefault();
                }
            }
        },
        initialize: function(customerUuid) {

            if (typeof customerUuid == 'string') {
                this.model = new CustomerModel({
                    customerUuid: customerUuid
                });
                this.model.set(JSON.parse(this.$('#hiddenModelAttr').val()));
                this.correctCusName = this.$("input[name='name']").val().trim();
            } else {
                this.model = new CustomerModel();
                this.correctCusName = '';
            }
            this.defineValidator();
            this.initFileUpload();

            this.arerSelect = new AreaSelectView({
                el: this.$('.area-select')
            });

            this.contacterCollection = new Backbone.Collection(this.model.get('contactPersons'));
            this.listenTo(this.contacterCollection, 'add', this.addOneContacter);
            this.addAllContacter(this.contacterCollection);

            this.addArchives(this.model.get('archives'));
            this.addSecretTreaty(this.model.get('confidentialityAgreement'));
            this.addCreditQueryAuthorization(this.model.get('creditQueryAuthorization'));
        },
        onChangeCustomerName: function(e) {
        	self.$('#check-more').hide();
        },
        onClickLookupCommerce: function(e) {
            e.preventDefault();
            var name = $(e.target).prevAll('[name=name]').val();
            var self = this;
            var more = this.$('#check-more');
            if (!name) {
                popupTip.show('请先输入客户名称');
            } else {
            	e.target.disabled = true;
            	$(e.target).parent().append(loadingImgEl);
                this.model.lookupCommerce(name, function(resp) {
                    if (resp.code != 0) {
                        popupTip.show(resp.message || '未查询到该企业或该企业不存在！请确保客户名称准确无误！');
                    } else {
                        var d = resp.data;
                        self.$('[name=fName]').val(d.fName);
                        self.$('[name=regNo]').val(d.regNo);
                        self.correctCusName = d.entName;
                        more.attr('href', self.getMoreCommerceUrl({
                            k: 'commerce',
                            regNo: d.regNo
                        }));
                        self.$('#check-more').show();
                    }
                    setTimeout(function(){
                    	e.target.disabled = false;
                        loadingImgEl.remove();
                    },500);
                });
            }
        },
        onClickBtnContacter: function(e) {
            e.preventDefault();
            var contacterCollection = this.contacterCollection;
            var model = new Backbone.Model();
            var editContacterView = new EditContacterView({
                model: model,
                sourceModel: this.model
            });
            editContacterView.show();
            model.once('change', function(model) {
                contacterCollection.add(model);
            });
        },
        onClickMoreCommerce:function(e){
            var more = this.$('#check-more');
            var value = $("input[name='name']").val().trim();
            var btn = this.$("#lookupCommerce");
            if (!btn.length) {
                window.open(this.getMoreCommerceUrl({
                    k: 'commerce',
                    customerUuid: this.model.get('customerUuid')
                }), '_blank');
                e.preventDefault();
            } else if (value != this.correctCusName || value == "") {
                e.preventDefault();
            }
        },

        getMoreCommerceUrl: function(query) {
            return path.format({
                path: root + '/preloan/customer',
                query: query
            });
        },
        defineValidator: function() {
            this.validator = this.$('.form').validate({
                rules: {
                    username: 'required',
                    provinceCode: 'required',
                    cityCode: 'required',
                    industryUuid: 'required'
                }
            });
        },
        initFileUpload: function() {
            var self = this;

            var placeFileDom = function(file, el) {
                var $tar = self.$(el.data('target'));
                if ($tar.hasClass('archives')) {
                    $tar.append(file);
                } else {
                    $tar.html(file);
                }
            };

            FileUploadPlugin.globalUploadFile(this.$('.file-input'), {
                action: root + '/preloan/file',
                fileFilter: ['.pdf', '.doc', '.docx', '.png', '.jpg', '.rar', '.zip'],
                onPlacement: placeFileDom,
                createItemDom: createFileDom
            });
        },
        addOneContacter: function(model) {
            var itemView = new ContacterItemView({
                model: model
            });
            this.$('.contacters').append(itemView.render().$el);
        },
        addAllContacter: function(contacters) {
            for (var i = 0, len = contacters.length; i < len; i++) {
                this.addOneContacter(contacters.at(i));
            }
        },
        createFileDoms: function(files) {
            var construct = createFileDom;
            var fragment = $('<div/>');
            for (var i = 0; i < files.length; i++) {
                fragment.append(construct(files[i]));
            }
            return fragment.html();
        },
        addArchives: function(archives) {
            var el = this.$('.archives');
            el.append(this.createFileDoms(archives));
        },
        addSecretTreaty: function(confidentialityAgreement) {
            if (_.isEmpty(confidentialityAgreement) || !confidentialityAgreement.fileName)
                return;
            var el = this.$('.confidentialitys');
            el.append(this.createFileDoms([confidentialityAgreement]));
        },
        addCreditQueryAuthorization: function(creditQueryAuthorization) {
        	if (_.isEmpty(creditQueryAuthorization) || !creditQueryAuthorization.fileName)
                return;
            var el = this.$('.credit-query-authorization');
            el.append(this.createFileDoms([creditQueryAuthorization]));
        },
        extractDomData: function() {
            var buildFileObject = function(el) {
                var data = el.data();
                if (!data)
                    return null;
                else
                    return {
                        fileName: data.filename,
                        currentStoredKey: data.currentstoredkey
                    };
            };

            var attr = this._extractDomData(this.$('.real-value'));

            var res = _.omit(attr, 'agreementNo', 'signedDate', 'sendDate', 'sendOrderNo', 'receiveDate', 'receiveOrderNo', 'cqaSendDate', 'cqaSendOrderNo', 'cqaReceiveDate', 'cqaReceiveOrderNo');

            res.confidentialityAgreement = _.pick(attr, 'agreementNo', 'signedDate', 'sendDate', 'sendOrderNo', 'receiveDate', 'receiveOrderNo');
            var file = buildFileObject(this.$('.confidentialitys').children().first());
            if (file != null) {
                res.confidentialityAgreement.fileName = file.fileName;
                res.confidentialityAgreement.currentStoredKey = file.currentStoredKey;
            }
            
            var cqaPickKeys = ['cqaSendDate', 'cqaSendOrderNo', 'cqaReceiveDate', 'cqaReceiveOrderNo'];
            var cqaKeys = ['sendDate', 'sendOrderNo', 'receiveDate', 'receiveOrderNo'];
            var cqaPickObj = _.pick(attr, cqaPickKeys);
            
            var creditQueryAuthorization = {};
            
            for (var i=0;i<cqaPickKeys.length;i++) {
            	var key = cqaPickKeys[i];
            	if(typeof(cqaPickObj[key]) != "undefined") {
               	 creditQueryAuthorization[cqaKeys[i]] = cqaPickObj[key]
                }
            }
            
            res.creditQueryAuthorization = creditQueryAuthorization;

            var file2 = buildFileObject(this.$('.credit-query-authorization').children().first());
            if (file2 != null) {
                res.creditQueryAuthorization.fileName = file2.fileName;
                res.creditQueryAuthorization.currentStoredKey = file2.currentStoredKey;
            }

            res.archives = [];
            var files = this.$('.archives').children();
            for (var i = 0, len = files.length; i < len; i++) {
                res.archives.push(buildFileObject(files.eq(i)));
            }

            res.contactPersons = this.contacterCollection.toJSON();

            return res;
        },
        save: function() {
            var attr = this.extractDomData();

            this.model.set(attr);

            this.model.manualSave({
                success: function(model, resp) {
                    if (resp.code == 0) {
                        popupTip.show('成功，正在跳转...');
                        setTimeout(function() {
                            AppRouters.navigate('preloan/customers', {
                                absolute: true
                            });
                        }, 1500);
                    } else {
                        popupTip.show(resp.message);
                    }
                }
            });
        },
        validate: function() {
            var basicValid = this.validator.form();
            return basicValid;
        },
        submitHandler: function(e) {
            e.preventDefault();
            var value = this.$("input[name='name']").val().trim();
            if (!this.validate()) return;
            value = value.replace(/（/g, '(').replace(/）/g, ')');
            if (value == this.correctCusName) {
                this.save();
            } else {
                popupTip.show("请输入正确的客户名称");
            }

        }
    });

    module.exports = CustomerEditView;

});