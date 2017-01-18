define(function(require, exports, module) {
    var TableContentView = require('baseView/tableContent');
    var popupTip = require('component/popupTip');
    var FormDialogView = require('baseView/baseFormView').FormDialogView;
    var baseFormView = require('baseView/baseFormView');
    var SketchItemView = baseFormView.SketchItemView;
    var LeasingBillModel = require('entity/contract/leasingBillModel').BillingPlanModel;

    var root = global_config.root,
        resource = global_config.resource;
    var AccountInfoView = FormDialogView.extend({
        template: _.template($('#accountInfoViewTmpl').html(),{ variable: 'obj' }),
        candicateAccountItemTemplate: _.template($('#candicateAccountItemTmpl').html()),
        initialize: function(options) {
            AccountInfoView.__super__.initialize.apply(this, arguments);
           // this.defineValidator();
            this.otherAccount = $('<div class="accounts clearfix" style="width:325px"/>');
        },
        events: {
            'click .btn-add-account': 'onClickChooseAccount',
            'click .account-item': 'onClickAccountItem',
            'click .add-other-account': 'onClickAddOtherAccount'
        },
        render:function(){
        	var data = this.model.toJSON();
        	$.extend(this,data);
        	this.$el.html(this.template(data));
        },
        validate: function() {
           if(!this.$(".real-value").val()){
              this.$(".btn-add-account").addClass("error");
              return false;
           }else {
             this.$(".btn-add-account").removeClass("error");
              return true;
           }
        },
        onClickChooseAccount: function(e) {
            var billType = 'BILL_ENTRY_TYPE_ACCOUNT_PAYABLE';

            var callback = function(accounts, resp) {
                var htm = this.candicateAccountItemTemplate({
                    billType: billType,
                    accounts: accounts,
                    isWEG: this.isWEG(billType)
                });
                this.otherAccount.html(htm);
                this.$('.add-account').append(this.otherAccount);
                this.$(".accounts ").show();
            };

            this.fetchAccount(billType, $.proxy(callback, this));
        },
        fetchAccount: function(billType, callback) {
            var url = root + '/billing-plan/leasing-bill-account';
            var self = this;
            $.ajax({
                url: url,
                type: 'post',
                dataType: 'json',
                data: {
                    billType: billType,
                    businessContractUuid: this.businessContractUuid
                },
                success: function(resp) {
                    self.cachcAccounts = resp.data.accounts;
                    callback(resp.data.accounts, resp);
                }
            });
        },
        isWEG: function(billType) {
            var isWEG;
            if (billType === 'COURSEOFDEALING_LEASING_WATER_FEES' || billType === 'COURSEOFDEALING_LEASING_GAS_FEES' || billType === 'COURSEOFDEALING_LEASING_ELECTRICITY_FEES') {
                isWEG = true;
            } else {
                isWEG = false;
            }
            return isWEG;
        },
        onClickAccountItem: function(e) {
            var accountOwnerName = $(e.currentTarget).data('accountownername') + '';
            if (accountOwnerName) {
                var res = this.getAccount(accountOwnerName);
                this.model.set('accountInfo', res);
                this.$(".cash-account").val(res.account);
                this.$(".cash-name").val(res.accountOwnerName);
                this.$(".accounts ").hide();
            }
        },
        getAccount: function(accountOwnerName) {
            var res = _.findWhere(this.cachcAccounts, {
                accountOwnerName: accountOwnerName
            });
            return res;
        },
        onClickAddOtherAccount: function(e) {
            e.preventDefault();
            this.otherAccount.remove();

            var leasingtype = $("#judge-leasingtype").val();
            if (leasingtype == "CONTRACT_LEASING_PROPERTY") {
                window.open(root + '/business-contract/leasing-contract/' + this.businessContractUuid);
            } else {
                window.open(root + '/business-contract/rental-contract/' + this.businessContractUuid);
            }
        },
        submitData: function (data) {
            var data = JSON.stringify(this.model.get('accountInfo'));
            var bilingplanUuid = this.bilingplanUuid;
            var opt = {
                    url:root+'/billing-plan/'+bilingplanUuid+'/add-account-info',
                    type: 'POST',
                    dataType: 'json',
                    contentType: 'application/json',
                    data: data
                };
                opt.success = function(resp) {
                    popupTip.show(resp.message);
                };

                $.ajax(opt);
        },
        submitHandler: function(e) {

            if (!this.validate()) return;
            this.submitData();
            this.hide();
            setTimeout(function() {
                location.reload();
            }, 2000);
        }
    });

    var BatchPaymentFormView = FormDialogView.extend({
        template: _.template($('#batchPaymentFormTmpl').html()),
        initialize: function(options) {
            BatchPaymentFormView.__super__.initialize.apply(this, arguments);
            this.envelopeUuid = options.envelopeUuid;
            this.defineValidator();
        },
        validate: function() {
            return this.validator.form();
        },
        defineValidator: function() {
            this.validator = this.$('.form').validate({
                ignore: '.hide [name]',
                rules: {
                    applicationTime: 'required'
                },
                success: function(error, element) {
                    var parent = $(element).parent();
                    if (parent.is('.imitate-datetimepicker-input')) {
                        if (!element.value) {
                            parent.addClass('error');
                        } else {
                            parent.removeClass('error');
                        }
                    }
                },
                errorPlacement: function(error, element) {
                    var parent = element.parent();
                    if (parent.is('.parcel-input')) {
                        error.insertAfter(parent);
                    } else if (parent.is('.imitate-datetimepicker-input')) {
                        // error.insertAfter(parent);
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        },
        save: function() {
            var attr = this.extractDomData();

            attr['envelopeUuid'] = this.envelopeUuid;

            var opt = {
                url: './applicat-payment',
                type: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(attr)
            };
            opt.success = function(resp) {

                popupTip.show(resp.message);
            };

            $.ajax(opt);
        },

        submitHandler: function(e) {
            if (!this.validate()) return;
            this.save();
            this.hide();
            setTimeout(function() {
                location.reload();
            }, 2000);
        }
    });

    var BatchPaymentDetailView = TableContentView.extend({
        events: {
            'click .destroy-envelope': 'onDestoryEnvelope',
            'click .applicat-payment': 'onApplicatPayment',
            'click .ajustPayAccountInfo': 'onAjustPayAccountInfo',
            'click .modify': 'onModifyDiscount',
        },
        initialize: function(billEntryType) {
            BatchPaymentDetailView.__super__.initialize.apply(this, arguments);
        },
        onApplicatPayment: function(e) {

            var envelopeUuid = $(e.target).attr('envelope-uuid');

            var parent = this;

            var batchPaymentFormView = new BatchPaymentFormView({
                envelopeUuid: envelopeUuid
            });

            batchPaymentFormView.show();
        },
        onDestoryEnvelope: function(e) {

            var attr = [];

            attr.push($(e.target).attr('envelope-uuid'));

            var parent = this;

            parent.asyncPost('./destroy-envelope', attr, function(resp) {

                popupTip.show(resp.message);

                parent.redirectPage('./payment-batch/index');
            });
        },
        onAjustPayAccountInfo: function(e) {

            var attr = {};
            
            var $tdTarget = $(e.target).parent('td');
            
            attr['businessContractUuid'] = $tdTarget.attr('business-contract-uuid');
            attr['financialAccountNo']=$tdTarget.attr('financial-account-no');
            attr['financialAccountBankInfoDesc']=$tdTarget.attr('financial-account-bank-info-desc');
            attr['financialAccountName']= $(e.target).prev().text();
            attr['bilingplanUuid']= $tdTarget.attr('bilingplan-uuid');

            var model = new Backbone.Model(attr);
            
            var accountInfoView = new AccountInfoView({model:model});

            accountInfoView.show();
        },
        onModifyDiscount: function (e) {
            e.preventDefault();
            var $tdTarget = $(e.target).parent('td');
            var attr = {};
            attr['billUuid']=$tdTarget.attr('bilingplan-uuid');
            attr['carringAmount']=$tdTarget.attr('carring-amount');
            
            var model = new LeasingBillModel(attr);
            
            var editView = new EditDiscountView({
                model: model
            });
            editView.show();
        },
        asyncPost: function(url, attr, success_callback) {

            var opt = {
                url: url,
                type: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(attr)
            };
            opt.success = success_callback;

            $.ajax(opt);

        },
        refreshTable: function() {
            setTimeout(function() {
                location.reload();
            }, 2000);
        },
        redirectPage: function(url) {
            location.href = url;
        },
    });
    
    var EditDiscountView = FormDialogView.extend({
        template: _.template($('#editDiscountTmpl').html()),
        initialize: function () {
            EditDiscountView.__super__.initialize.apply(this, arguments);

            this.defineValidator();
        },
        remove: function () {
            delete $.validator.methods.lowCarringAmount;
            EditDiscountView.__super__.remove.apply(this, arguments);
        },
        defineValidator: function () {
            var model =  this.model;
            $.validator.addMethod('lowCarringAmount', function (value, element) {
                return this.optional(element) || model.get('carringAmount') >= +value;
            }, '折扣金额不能大于账单应收金额');
            $.validator.addMethod('rightformatAmount', function (value, element) {
                return this.optional(element) || (/^([-]*([1-9]\d{0,5})|0)(\.\d{1,2})?$/.test(value));
            }, '请输入正确的金额格式');
            this.validator = this.$('.form').validate({
                rules: {
                    amount: {
                        rightformatAmount: true,
                        lowCarringAmount: true
                        
                    }
                },
                errorPlacement: function(error, element) {
                    var parent = element.parent();
                    if(parent.is('.parcel-input')) {
                        error.insertAfter(parent);
                    }else {
                        error.insertAfter(element);
                    }
                }
            });
        },
        validate: function () {
            return this.validator.form();
        },
        save: function () {
            var attr= this.extractDomData();
            this.model.modifyDiscount(attr);
        },
        submitHandler: function (e) {
            if(!this.validate()) return;
            this.save();
            this.hide();
        }
    });

    exports.BatchPaymentDetailView = BatchPaymentDetailView;
});