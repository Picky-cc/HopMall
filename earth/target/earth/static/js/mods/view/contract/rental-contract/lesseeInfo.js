define(function(require, exports, module) {
    // module
    require('component/autocomplete');
    var FileUploadPlugin = require('component/fileUpload');
    var baseFormView = require('baseView/baseFormView');
    var FieldsetView = baseFormView.FieldsetView;
    var SketchItemView = baseFormView.SketchItemView;
    var FormDialogView = baseFormView.FormDialogView;

    // const
    var $ =  jQuery;
    var upload_path = image_oss_host;

    // renter view
    var ContacterItemView = SketchItemView.extend({
        template: _.template($('#contacterTtemTmpl').html()),
        toEdit: function () {
            var editView = new EditManagerView({
                model: this.model
            });
            editView.show();
        }
    });

    var AccountItemView = SketchItemView.extend({
        template: _.template($('#accountItemTmpl').html()),
        toEdit: function () {
            var editView = new EditAccountView({
                model: this.model
            });
            editView.show();
        }
    });

    var EditManagerView = FormDialogView.extend({
        template: _.template($('#editContacterTmpl').html(), {variable: 'obj'}),
        initialize: function () {
            EditManagerView.__super__.initialize.apply(this, arguments);

            this.defineValidator();
        },
        remove: function () {
            delete $.validator.methods.repeatMobile;
            EditManagerView.__super__.initialize.apply(this, arguments);
        },
        defineValidator: function () {
            var collection = this.collection || this.model.collection;
            var model =  this.model;
            $.validator.addMethod('repeatMobile', function (value, element) {
                if(collection) {
                    var exist = collection.findWhere({mobile: value});
                    if(exist === model) return true;
                    return this.optional(element) || !exist;
                }else {
                    return true;
                }
            }, '手机号不能重复');

            this.validator = this.$('.form').validate({
                rules: {
                    mobile: {
                        mPhoneExt:true,
                        repeatMobile: true
                    }
                }
            });
        },
        validate: function () {
            var basic = this.validator.form();
            return basic;
        },
        submitHandler: function (e) {
            if(!this.validate()) return;

            var result = this._extractDomData(this.$('.real-value'));
//            result.broker = this.$('input[type=checkbox]')[0].checked;

            this.model.set(result);

            this.hide();
        }
    });

    var EditAccountView = FormDialogView.extend({
        template: _.template($('#editAccountTmpl').html(), {variable: 'obj'}),
        events: {
            'change [name=bankCode]': 'onChangeBankSelect'
        },
        initialize: function () {
            EditManagerView.__super__.initialize.apply(this, arguments);

            this.defineValidator();

            this.bankLogoEl = this.$('.prefix-selectbox .bank-logo');
            this.bankLogoUrlPrefix = this.bankLogoEl.data('imgurlprefix');
        },
        defineValidator: function () {
            var collection = this.collection || this.model.collection;
            var model =  this.model;
            $.validator.addMethod('repeatAccountNo', function (value, element) {
                if(collection) {
                    var exist = collection.findWhere({accountNo: value});
                    if(exist === model) return true;
                    return this.optional(element) || !exist;
                }else {
                    return true;
                }
            }, '付款账号不能重复');

            this.validator = this.$('.form').validate({
                rules: {
                    accountNo: {
                        repeatAccountNo: true,
                        number:true
                    }

                }
            });
        },
        validate: function () {
            var basic = this.validator.form();
            var need = true;

            if (this.$("select[name$='bankCode']").val()){
                if(!this.$("input[name$='accountNo']").val()){
                    this.$("input[name$='accountNo']").addClass('error');
                    need = false;
                }else{
                    this.$("input[name$='accountNo']").removeClass('error');
                    need = true;                  
                }
            };
            if (this.$("input[name$='accountNo']").val()) {
                if(!this.$("select[name$='bankCode']").val()){
                    this.$("select[name$='bankCode']").parent().addClass('error');
                    need = false;
                }else{
                    this.$("select[name$='bankCode']").parent().removeClass('error');
                    need = true;
                }
            };
            
            return basic && need;
        },
        remove: function () {
            delete $.validator.methods.repeatAccountNo;
            EditManagerView.__super__.initialize.apply(this, arguments);
        },
        submitHandler: function (e) {
            if(!this.validate()) return;

            var result = this._extractDomData(this.$('.real-value'), true);

            this.model.set(result);

            this.hide();
        },
        onChangeBankSelect: function (e) {
            var value = $(e.target).val();
            if(!value) {
                this.bankLogoEl.addClass('hide');
            }else {
                var src = this.bankLogoUrlPrefix + 'bank_' + value.toLowerCase() + '.png';
                this.bankLogoEl.attr('src', src);
                this.bankLogoEl.removeClass('hide');
            }
        }
    });

    var LesseeView = FieldsetView.extend({
        events: {
            'click .radio-box': 'onClickRadiobox',
            'click .add-account': 'addAccountHandler',
            'click .add-manager': 'addManagerHandler'
        },
        initialize: function (options) {
            this.initFileUpload();
            this.initAutoComplete();

            this.tabs = this.$('.btm');
            this.deleteTab = this.tabs.children('.hide').detach();

            var partyConcernedInfoList = this.model.get('partyConcernedInfoList');
            var accountInfoList = this.model.get('accountInfoList');
            if(this.model.get('renterType') == 0) {
                this.personal_partyConcerneds = new Backbone.Collection(partyConcernedInfoList);
                this.company_partyConcerneds = new Backbone.Collection();

                this.personal_accounts = new Backbone.Collection(accountInfoList);
                this.company_accounts = new Backbone.Collection();

                this.addAllManager(this.personal_partyConcerneds);
                this.addAllAccount(this.personal_accounts);
            }else {
                this.personal_partyConcerneds = new Backbone.Collection();
                this.company_partyConcerneds = new Backbone.Collection(partyConcernedInfoList);
                
                this.personal_accounts = new Backbone.Collection();
                this.company_accounts = new Backbone.Collection(accountInfoList);

                this.addAllManager(this.company_partyConcerneds);
                this.addAllAccount(this.company_accounts);
            }

            this.listenTo(this.personal_partyConcerneds, 'add', this.addOneManager);
            this.listenTo(this.company_partyConcerneds, 'add', this.addOneManager);
            this.listenTo(this.personal_accounts, 'add', this.addOneAccount);
            this.listenTo(this.company_accounts, 'add', this.addOneAccount);
        },
        initAutoComplete: function() {
            var personalName = this.$('.personal-name');
            var companyName = this.$('.company-name');
            var options = {
                responseData: this.model.get('landLordList'),
                parcelItem: function(item) {
                    var str = '<li class="item" data-uuid="' + item.tradePartyUUid + '">'+item.ownerName+'</li>';
                    return str;
                },
                filter: function(resp, inputVal) {
                    var res = [];
                    if(!inputVal) return res;
                    for(var i=0; i<resp.length; i++) {
                        if(resp[i].ownerName.indexOf(inputVal) != -1) {
                            res.push(resp[i]);
                        }
                    }
                    return res;
                },
                onSync: function (list, inputVal, container) {
                    if(list.length<1) {
                        container.hide();
                    }
                },
                onSubmit: $.proxy(function(inputEl, itemEl) {
                    var uuid = itemEl.data('uuid');
                    this.stuffLandlordInfo(uuid);
                }, this)
            };

            personalName.autocomplete($.extend({
                container: personalName.next('.auto-complete-list')
            }, options));

            companyName.autocomplete($.extend({
                container: companyName.next('.auto-complete-list')
            }, options));

        },
        stuffLandlordInfo: function(uuid) {
            var landLordList = this.model.get('landLordList');
            var candidate = _.find(landLordList, {tradePartyUUid: uuid});
            var packetImg = function(data) {
                if($.isEmptyObject(data)) {
                    return '';
                }
                var image = '<li class="item" data-original="'+ data.imgKey +'" data-thumbnail="'+ data.thumbNailsImgKey +'">' + 
                                '<img class="img" src="'+ upload_path.thumbnail + '/' + data.thumbNailsImgKey + '">' + 
                            '</li>';
                return image;
            };

            this.switchTabView(candidate.ownerType);

            this.$('.top input[type=radio]').filter(function() {
                return this.value == candidate.ownerType;
            }).get(0).checked = true;

            this.$('[name=name]').val(candidate.ownerName);
            this.$('[name=certificateNo]').val(candidate.certificateNo);
            this.$('[name=certificateType]').find('option').each(function() {
                if(this.value === candidate.certificateType) {
                    this.selected = true;
                    return false;
                }
            });

            if(candidate.ownerType == 0) {
                var htm = packetImg(candidate.frontCertificateImg);
                this.$('.front-pic-images').html(htm);

                htm = packetImg(candidate.backCertificateImg);
                this.$('.back-pic-images').html(htm);
            }else {
                var htm = packetImg(candidate.frontCertificateImg);
                this.$('.enterprise-pic-images').html(htm);
            }
            this.model.set("tradePartyUuid",candidate.tradePartyUUid,{silent:true});
            this.model.set("tradePartyId", candidate.tradePartyId, {
                silent: !0
            });
        },
        initFileUpload: function () {
            var self = this;

            FileUploadPlugin.globalUploadImage(this.$('.file-input'), {
                onPlacement: function (img, el) {
                    self.$(el.data('target')).html(img);
                },
                onSuccess: function (data, el) {
                    el.removeClass('error');
                }
            });
        },
        addAccountHandler: function (e) {
            e.preventDefault();

            var self = this;
            var model = new Backbone.Model();
            var collection;
            if(self.getRenterTypeFromDom() == 0) {
                collection = this.personal_accounts;
            }else {
                collection = this.company_accounts;
            }
            var editAccountView = new EditAccountView({
                model: model,
                collection: collection
            });
            editAccountView.show();
            model.once('change', function () {
                collection.add(model);
            });
        },
        addOneAccount: function (model) {
            var itemView = new AccountItemView({
                model: model
            });
            this.$('.payers').append(itemView.render().$el);
            this.$('.add-account').removeClass('error');
        },
        addAllAccount: function (arr) {
            for (var i = 0, len = arr.length; i < len; i++) {
                this.addOneAccount(arr.at(i));
            }
        },
        addManagerHandler: function (e) {
            e.preventDefault();

            var self = this;
            var model = this.initManageModel();

            var collection;
            if(self.getRenterTypeFromDom() == 0) {
                collection = this.personal_partyConcerneds;
            }else {
                collection = this.company_partyConcerneds;
            }
            var editManagerView = new EditManagerView({
                model: model,
                collection: collection
            });
            editManagerView.show();
            model.once('change', function () {
                collection.add(model);
            });
        },
        initManageModel:function(){
            var model = new Backbone.Model();
            var name = $('[name="name"]').val();
            model.set("name",name,{silent:true});
            return model;
        },
        addOneManager: function (model) {
            var itemView = new ContacterItemView({
                model: model
            });
            this.$('.contact-persons').append(itemView.render().$el);
            this.$('.add-manager').removeClass('error');
        },
        addAllManager: function (arr) {
            for (var i = 0, len = arr.length; i < len; i++) {
                this.addOneManager(arr.at(i));
            }
        },
        getCurTabView: function () {
            return this.tabs.children(':not(.hide)');
        },
        switchTabView: function(renterType) {
            var curTab = this.getCurTabView();
            var aimSelector;
            if(renterType == 0) {
                aimSelector = '.personal';
            }else {
                aimSelector = '.company';
            }

            // aimSelector === '.personal' ? this.model.set('renterType', 0) : this.model.set('renterType', 1);

            if(!curTab.is(aimSelector)) {
                this.deleteTab.removeClass('hide').appendTo(this.tabs);
                this.deleteTab = curTab.detach();
            }
        },
        onClickRadiobox: function (e) {
            var tar = $(e.currentTarget);
            if(tar.find("input").get(0).disabled) return;
            var renterType = tar.find('input[type=radio]').val();
            this.switchTabView(renterType);
        },
        getRenterTypeFromDom: function () {
            var val = this.$('[name=lessee-type]:checked').val();
            return val;
        },
        extractDomData: function () {
            var result = this._extractDomData(this.$('.real-value'));

            var buildImageObject = function (ul) {
                var data = ul.children().first().data();
                if(!data) return {};
                else return {
                    imgKey: data.original,
                    thumbNailsImgKey: data.thumbnail,
                    imageName: ''
                };
            };

            result.renterType = this.getRenterTypeFromDom();

            if(result.renterType == 0) {
                result.partyConcernedInfoList = this.personal_partyConcerneds.toJSON();
                result.accountInfoList = this.personal_accounts.toJSON();
            }else {
                result.partyConcernedInfoList = this.company_partyConcerneds.toJSON();
                result.accountInfoList = this.company_accounts.toJSON();
            }

            this.$('.upload-imgs').each(function () {
                var $el = $(this);
                if($el.hasClass('front-pic-images')) {
                    result.frontCertificateImg=buildImageObject($el);
                }else if($el.hasClass('back-pic-images')) {
                    result.backCertificateImg=buildImageObject($el);
                }else if($el.hasClass('enterprise-pic-images')) {
                    result.frontCertificateImg=buildImageObject($el);
                }
            });

            return result;
        },
        validate: function () {
            var self = this;
            var flag = true;

            // this.$('.file-input').each(function () {
            //     var el = $(this);
            //     var imgLen = self.$(el.data('target')).children().length;
            //     if(imgLen>0) {
            //         el.removeClass('error');
            //     }else {
            //         el.addClass('error');
            //         flag = false;
            //     }
            // });

            // if(this.$('.contact-persons').children().length > 0) {
            //     this.$('.add-manager').removeClass('error');
            // }else {
            //     this.$('.add-manager').addClass('error');
            //     flag = false;
            // }

            // if(this.$('.payers').children().length > 0) {
            //     this.$('.add-account').removeClass('error');
            // }else {
            //     this.$('.add-account').addClass('error');
            //     flag = false;
            // }

            return flag;
        }
    });

    
    module.exports = LesseeView;

});