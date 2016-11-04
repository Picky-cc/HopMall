define(function(require,exports,module){
    var popupTip = require('component/popupTip');
    var AreaSelectView = require('component/areaSelect');
    var baseFormView = require('baseView/baseFormView');
    var FormDialogView = baseFormView.FormDialogView;
    var SketchItemView = baseFormView.SketchItemView;

    var root = global_config.root,
        resource = global_config.resource;


    var financialAccountList = $('#financialAccountList').val() || null;
    var accountInfoMap = $('#accountInfoMap').val() || null;

    var LeasingBillModel = Backbone.Model.extend({
        urlRoot:'./leasing-bill',
        idAttribute:'billingPlanUuid',
        initialize: function(attr, opt) {
            this.businessContractUuid = opt.businessContractUuid;
            this.subjectMatterUuid = opt.subjectMatterUuid;
        },
        getBillTypeZh: function(billType) {
            var title;
            if(billType === 'COURSEOFDEALING_LEASING_WATER_FEES') {
                title = '水费户号';
            }else if(billType === 'COURSEOFDEALING_LEASING_GAS_FEES') {
                title = '燃气费户号';
            }else if(billType === 'COURSEOFDEALING_LEASING_ELECTRICITY_FEES') {
                title = '电费户号';
            }else {
                title = '付款帐号';
            }
            return title;
        },
        isWEG: function(billType) {
            var isWEG;
            if(billType === 'COURSEOFDEALING_LEASING_WATER_FEES'
                || billType === 'COURSEOFDEALING_LEASING_GAS_FEES'
                || billType === 'COURSEOFDEALING_LEASING_ELECTRICITY_FEES') {
                isWEG = true;
            }else {
                isWEG = false;
            }
            return isWEG;
        },
        fetchAccount: function(billType, callback) {
            var url = './leasing-bill-account';
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
        getAccount: function(accountOwnerName) {
            var res = _.findWhere(this.cachcAccounts, {
                accountOwnerName: accountOwnerName
            });
            return res;
        },
        saveAll:function(options){
            this.save({},options);
        }
    });

    var AccountFieldView = Backbone.View.extend({
        template: _.template($('#accountItemTmpl').html(), {variable: 'obj'}),
        candicateAccountItemTemplate: _.template($('#candicateAccountItemTmpl').html()),
        className: 'field-row account-field',
        events: {
            'click .delete':  'onClickDelete',
            'click .btn-add-account': 'onClickChooseAccount',
            'click .add-other-account': 'onClickAddOtherAccount',
            'click .account-item': 'onClickAccountItem'
        },
        initialize: function() {
            this.otherAccount = $('<div class="accounts clearfix" />');

            this.listenTo(this.model, 'change:accountInfo', this.render);
            this.listenTo(this.model, 'change:billType change:entryType', function() {
                this.model.unset('accountInfo', {
                    silent: true
                });
                this.render();
            });
        },
        render: function() {
            var entryType = this.model.get('entryType');
            if(entryType != 'BILL_ENTRY_TYPE_ACCOUNT_PAYABLE') {
                this.$el.detach();
            }else {
                var data = this.model.toJSON();

                data.isWEG = this.model.isWEG(data.billType);
                data.title = this.model.getBillTypeZh(data.billType);

                var htm = this.template(data);
                this.$el.html(htm);

                $('#amountField').after(this.$el);
            }
            return this;
        },
        onClickDelete: function() {
            this.model.unset('accountInfo');
        },
        onClickAccountItem: function(e) {
            var accountOwnerName = $(e.currentTarget).data('accountownername') + '';
            if(accountOwnerName) {
                var res = this.model.getAccount(accountOwnerName);
                this.model.set('accountInfo', res);
            }
        },
        onClickChooseAccount: function(e) {
            var billType = this.model.get('billType');

            var callback = function(accounts, resp) {
                var htm = this.candicateAccountItemTemplate({
                    billType: billType,
                    accounts: accounts,
                    isWEG: this.model.isWEG(billType)
                });
                this.otherAccount.html(htm);
                this.$('.field-value').append(this.otherAccount);
            };

            this.model.fetchAccount(billType, $.proxy(callback, this));
        },
        onClickAddOtherAccount: function(e) {
            e.preventDefault();
            this.otherAccount.remove();
            var billType = this.model.get('billType');
            var leasingtype = $("#judge-leasingtype").val();
            if(this.model.isWEG(billType)) {
                window.open(root + '/subject-matter/property/' + this.model.subjectMatterUuid);
            }else {
                if(leasingtype== "CONTRACT_LEASING_PROPERTY" ){
                    window.open(root + '/business-contract/leasing-contract/' + this.model.businessContractUuid);
                }else{
                    window.open(root + '/business-contract/rental-contract/' + this.model.businessContractUuid); 
                }
            }
        }
    });

    var EditBillView = Backbone.View.extend({
        el:'.bill-form-wrapper',
        events:{
            'click .submit':'onSubmit',
            'click .invoicing': 'onClickInvoicing',
            'change [data-params=entryType]': 'onChangeEntryType',
            'change [data-params=billType]': 'onChangeBillType'
        },   
        initialize: function(businessContractUuid) {
            EditBillView.__super__.initialize.apply(this, arguments);

            this.invoiceForm = this.$('.sub-form');
            this.areaSelect = new AreaSelectView({
                el: this.$('.area-select')
            });

            this.defineValidator();

            this.model = new LeasingBillModel({
                entryType: this.$("[data-params=entryType]").val(),
                billType: this.$('[data-params=billType]').val()
            }, {
                businessContractUuid: this.$('#businessContractUuid').val(),
                subjectMatterUuid: this.$('#subjectMatterUuid').val()
            });

            this.accountFieldView = new AccountFieldView({model: this.model});
        },
        defineValidator: function() {
            this.validator = this.$('.form').validate({
                ignore:'.hide [name]',
                rules:{
                    billEffectiveDate:'required',
                    billMaturityDate:'required',
                    billTradeDate:'required',
                    mobile:'mobile',
                    amount:'nonNegativeInteger'
                },
                success: function (error, element) {
                    var parent = $(element).parent();
                    if(parent.is('.imitate-datetimepicker-input')) {
                        if(!element.value) {
                            parent.addClass('error');
                        }else {
                            parent.removeClass('error');
                        }
                    }
                },
                errorPlacement: function(error, element) {
                    var parent = element.parent();
                    if(parent.is('.parcel-input')) {
                        error.insertAfter(parent);
                    }else if(parent.is('.imitate-datetimepicker-input')) {
                        // error.insertAfter(parent);
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        },
        validate: function() {
            var basic = this.validator.form();
            var flag = true;
            var entryType = this.model.get('entryType');
            var amount = this.$('input[name="amount"]');
            /*if(amount.val()<0){
                flag = false;
                amount.addClass("error");
            }*/

            if(entryType === 'BILL_ENTRY_TYPE_ACCOUNT_PAYABLE') {
                var accountInfo = this.model.get('accountInfo');
                flag = !_.isEmpty(accountInfo);
                flag ? this.$('.btn-add-account').removeClass('error') : this.$('.btn-add-account').addClass('error');
            }

            return basic && flag;
        },
        getBillType: function() {
            var billType = this.$('[data-params="billType"]').val();
            return billType;  
        },

        onSubmit:function(e){
            e.preventDefault();

            if(!this.validate()){
                return;
            }
            var res = {};
            this.$('[data-params]').each(function() {
                var key = $(this).data('params');
                var value = $(this).val();
                res[key]=value;
            });
            res['billEffectiveDate']=this.$('input[name="billEffectiveDate"]').val();
            res['billMaturityDate']=this.$('input[name="billMaturityDate"]').val();
            res['billTradeDate']=this.$('input[name="billTradeDate"]').val();
            res['contractUuid']=this.$('input[name="contractUuid"]').val();

            var taxInvoice = {};
            taxInvoice['taxType'] = this.$('input[name="invoice-type"]').val();
            taxInvoice['invoiceTitle'] = this.$('input[name="invoiceTitle"]').val();
            taxInvoice['addressee'] = this.$('input[name="addressee"]').val();
            taxInvoice['mobile'] = this.$('input[name="mobile"]').val();
            taxInvoice['provinceCode'] = this.$('select[name="provinceCode"]').val();
            taxInvoice['cityCode'] = this.$('select[name="cityCode"]').val();
            taxInvoice['areaCode'] = this.$('select[name="areaCode"]').val();
            taxInvoice['address'] = this.$('input[name="address"]').val();
            taxInvoice['postcode'] = this.$('input[name="postcode"]').val();

            res['taxInfo']=taxInvoice;

            this.model.set(res);
            this.model.saveAll({
                success: function(model, resp) {
                    if (resp.code != 0) {
                        var errorData = resp.data;
                        popupTip.show('参数非法！');
                    } else {

                        var businessContractUuid = resp.data.businessContractUuid;

                        popupTip.once('closedialog', function() {

                            if (typeof businessContractUuid !== 'undefined') {

                                location = './leasing-bill-list/index?businessContractUuid=' + businessContractUuid;
                            }
                        });

                        popupTip.show('成功！');
                    }
                }
            });
        },
        onClickInvoicing: function (e) {
            var checked = e.target.checked;
            checked ? this.invoiceForm.removeClass('hide') : this.invoiceForm.addClass('hide');
        },
        onChangeEntryType:function (e) {
            var title = $(e.target).find("option:selected").text();
            if(title == "付费账单（应付）"){
                this.$(".pay-data").html("应付日期");
                this.$(".pay-money").html("应付金额");
            }else{
                this.$(".pay-data").html("应收日期");
                this.$(".pay-money").html("应收金额");
            }
            this.$(".hd").text(title);

            var entryType = $(e.target).val();

            // 数据驱动开发，事件处理器中只更改模型，监听模型的变化去更改视图
            // 类似于Vue.js，只是它帮我们做了监听模型的变化更改视图这部分
            // DOM操作太麻烦了，容易出错，且可能会在多个地方需要去更改它，且都是一大段的，改为修改数据，事件处理器就变得简单且修改dom就只有一个入口了
            this.model.set('entryType', entryType);
        },
        onChangeBillType: function(e) {
            var billType = this.getBillType();
            this.model.set('billType', billType);
        }
    });

    exports.EditBillView = EditBillView;
});