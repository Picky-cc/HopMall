define(function(require, exports, module) {
    // module
    require('component/autocomplete');
    var FileUploadPlugin = require('component/fileUpload');
    var baseFormView = require('baseView/baseFormView');
    var FieldsetView = baseFormView.FieldsetView;
    var SketchItemView = baseFormView.SketchItemView;
    var FormDialogView = baseFormView.FormDialogView;

    var root = global_config.root;

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

            this.initAutoComplete();
        },
        initAutoComplete: function() {
            var partyConcernedName = this.$('.party-concerned-name');
            var options = {
                action: root + '/party-concerned/search',
                parse: function(resp) {
                    if (resp.code == 0) {
                        return resp.data.list;
                    } else {
                        return [];
                    }
                },
                search: function () {

                    return {};
                },
                parcelItem: function(item) {

                    var mobileInfo = item.mobile !== undefined && "("+item.mobile+")";

                    var str = "<li class='item' data-uuid="+item.partyConcernedUuid+">"+item.name+mobileInfo+"</li>";
                    
                    $.data(document.body,item.partyConcernedUuid,item);

                    return str;
                },
                onSync: function (list, inputVal, container) {
                    if(list.length<1) {
                        container.hide();
                    }
                },
                onSubmit: $.proxy(function(inputEl, itemEl) {

                    var uuid = itemEl.data("uuid");

                    var partyConcerned = $.data(document.body,uuid);

                    this.fillPartyConcernedInfo(partyConcerned);

                }, this)
            };

            partyConcernedName.autocomplete($.extend({
                container: partyConcernedName.next('.auto-complete-list')
            }, options));
        },
        fillPartyConcernedInfo: function(partyConcerned) {

            this.model.set("partyConcernedId",partyConcerned.partyConcernedId,{silent:true});
            this.model.set("partyConcernedUuid",partyConcerned.partyConcernedUuid,{silent:true});
            this.$('[name="name"]').val(partyConcerned.name);
            this.$('[name="mobile"]').val(partyConcerned.mobile);
            this.$('#isBroker')[0].checked = partyConcerned.broker;
            
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
            result.broker = this.$('input[type=checkbox]')[0].checked;

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
            'click .radio-box': 'switchTabView',
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

            this.listenTo(this.personal_partyConcerneds, 'reset', this.addAllManager);
            this.listenTo(this.company_partyConcerneds, 'reset', this.addAllManager);
            this.listenTo(this.personal_accounts, 'reset', this.addAllAccount);
            this.listenTo(this.company_accounts, 'reset', this.addAllAccount);
            
        },
        initAutoComplete: function() {
            var personalName = this.$('.personal-name');
            var companyName = this.$('.company-name');
            var options = {
                action: root + '/trade-party/search',
                parse: function(resp) {
                    if (resp.code == 0) {
                        return resp.data.list;
                    } else {
                        return [];
                    }
                },
                search: function () {

                    var tradePartyInfoParam = {};
                    tradePartyInfoParam.tradePartyLegalType=$('[name="lessee-type"]:checked').val() == '0' ? 'NATURAL_HUMAN' : 'LEGAL_ENTITY';
                    tradePartyInfoParam.partyOrder='Second_Party';

                    return tradePartyInfoParam;
                },
                parcelItem: function(item) {
                    var str = "<li class='item' data-uuid="+item.tradePartyUuid+">"+item.name+"</li>";
                    
                    $.data(document.body,item.tradePartyUuid,item);

                    return str;
                },
                onSync: function (list, inputVal, container) {
                    if(list.length<1) {
                        container.hide();
                    }
                },
                onSubmit: $.proxy(function(inputEl, itemEl) {
                    var uuid = itemEl.data("uuid");

                    var tradeParty = $.data(document.body,uuid);

                    this.stuffTradePartyInfo(tradeParty);
                }, this)
            };

            personalName.autocomplete($.extend({
                container: personalName.next('.auto-complete-list')
            }, options));

            companyName.autocomplete($.extend({
                container: companyName.next('.auto-complete-list')
            }, options));
        },
        stuffTradePartyInfo: function(tradeParty) {

            this.model.set("tradePartyId",tradeParty.tradePartyId);
            this.model.set("tradePartyUuid",tradeParty.tradePartyUuid);
            
            var candidate = tradeParty;
            var packetImg = function(data) {
                if($.isEmptyObject(data)) {
                    return '';
                }
                var image = '<li class="item" data-original="'+ data.imgKey +'" data-thumbnail="'+ data.thumbNailsImgKey +'">' + 
                                '<img class="img" src="'+ upload_path.thumbnail + '/' + data.thumbNailsImgKey + '">' + 
                            '</li>';
                return image;
            };

            this.$('[name=name]').val(candidate.name);
            this.$('[name=certificateNo]').val(candidate.certificateNo);
            this.$('[name=certificateType]').find('option').each(function() {
                if(this.value === candidate.certificateType) {
                    this.selected = true;
                    return false;
                }
            });

            if(candidate.renterType == 0) {
                var htm = packetImg(candidate.frontCertificateImg);
                this.$('.front-pic-images').html(htm);

                htm = packetImg(candidate.backCertificateImg);
                this.$('.back-pic-images').html(htm);
                remove(this.personal_accounts);
                remove(this.personal_partyConcerneds);
                this.personal_accounts.reset(candidate.accountInfoList);
                this.personal_partyConcerneds.reset(candidate.partyConcernedInfoList);
            }else {
                var htm = packetImg(candidate.frontCertificateImg);
                this.$('.enterprise-pic-images').html(htm);
                remove(this.company_accounts);
                remove(this.company_partyConcerneds);
                this.company_accounts.reset(candidate.accountInfoList);
                this.company_partyConcerneds.reset(candidate.partyConcernedInfoList);
            }

            function remove(collection) {
                while(collection.length) {
                    collection.pop().destroy();
                }
            }
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
            var model = new Backbone.Model();
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
        switchTabView: function (e) {
            var tar = $(e.currentTarget);
            if(tar.find("input").get(0).disabled) return;
            var curTab = this.getCurTabView();
            var aimSelector = tar.data('target');

            // aimSelector === '.personal' ? this.model.set('renterType', 0) : this.model.set('renterType', 1);

            if(!curTab.is(aimSelector)) {
                this.deleteTab.removeClass('hide').appendTo(this.tabs);
                this.deleteTab = curTab.detach();
            }
        },
        getRenterTypeFromDom: function () {
            var val = this.$('[name=lessee-type]:checked').val();
            return val;
        },
        extractDomData: function () {
            var result = this._extractDomData(this.$('.real-value'), true);

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
            var flag = true;
            return flag;
        }
    });

    
    module.exports = LesseeView;

});