define(function(require, exports, module) {
    var popupTip = require('component/popupTip');
    var baseView = require('baseView/baseFormView');
    var FileUploadPlugin = require('component/fileUpload');
    var DialogView = require('component/dialogView');
    var BaseFormView = baseView.BaseFormView;

    var root = global_config.root;

    var ActivePaymentCreateModel = Backbone.Model.extend({
        actions: {
            searchAccountInfoByContractNoUrl: root + '/voucher/active/create/search-contract',
            searchAccountInfoByNameUrl: root + '/voucher/active/create/search-name',
            submitUrl: root + '/voucher/active/create/save'
        },
        searchAccountInfoByContractNo: function(contractNo) {
            var self = this;
            var opt = {
                url: this.actions.searchAccountInfoByContractNoUrl,
                dataType: 'json',
                data: {
                    'contractNo': contractNo
                }
            };
            opt.success = function(resp) {
                self.trigger('model:searchAccountInfoByContractNo', resp, self);
            };
            $.ajax(opt);
        },
        searchAccountInfoByName: function(name) {
            var self = this;
            if (self.loading) return;
            self.loading = true;
            var opt = {
                url: self.actions.searchAccountInfoByNameUrl,
                dataType: 'json',
                data: {
                    'name': name
                }
            };
            opt.success = function(resp) {
                self.trigger('model:searchAccountInfoByName', resp, self);
            };
            opt.complete = function() {
                self.loading = false;
            };
            $.ajax(opt);
        },
        submit: function(data) {
            var self = this;
            var opt = {
                dataType: 'json',
                url: this.actions.submitUrl,
                data: data,
                type: 'post'
            };
            opt.success = function(resp) {
                self.trigger('model:submit', resp, self);
            };
            $.ajax(opt);
        }
    });
    var ActivePaymentCreateView = BaseFormView.extend({
        el: '.form-wrapper',
        template: _.template($('.form-wrapper .form').html() || ' '),
        events: {
            'change [name = contractNo]': 'onChangeContractNoInput',
            'change [name = payerName]': 'onChangePayerNameInput',
            'change [name = voucherType]': 'onChangeVoucherType'
        },
        initialize: function() {
            ActivePaymentCreateView.__super__.initialize.apply(this, arguments);
            this.uuidList = [];
            this.initModel();
            this.defineValidator();
            this.initFileUpload();

            this.confirm = new DialogView({
                goaheadBtnTxt: '确定'
            });

            this.listenTo(this.confirm, 'goahead', function() {
                window.location.href = root + '/voucher/active';
            }).listenTo(this.confirm, 'closedialog', function() {
                window.location.href = root + '/voucher/active';
            });

        },
        initFileUpload: function() {
            var self = this;
            FileUploadPlugin.globalUploadFile(this.$('.file-input'), {
                action: root + '/voucher/active/create/upload-file',
                fileFilter: ['.pdf', '.png'],
                uploadParamName: 'file',
                onPlacement: function(file, el) {
                    el.removeClass('error');
                    el.prev().append(file);
                },
                createItemDom: function(data, sourceFile) {
                    var file = '<li class="item fileItem" data-uuid="' + data.uuid + '" data-filename="' + sourceFile.name + '" data-original="' + data.original + '">' +
                        '<span>' + sourceFile.name + '</span><a href="#" class="delete">删除</a>' +
                        '</li>';
                    return file;
                },
                onSuccess: function(data, el) {
                    self.uuidList.push(data.uuid);
                }
            });
        },
        initModel: function() {
            this.model = new ActivePaymentCreateModel();

            this.listenTo(this.model, 'model:searchAccountInfoByName', function(resp) {
                if (resp.code == 0) {
                    if (_.isEmpty(resp.data.contractAccountList)) return;
                    var contractAccount = resp.data.contractAccountList[0];

                    var bank = contractAccount.bank;
                    var bankAddress = contractAccount.province + ' ' + contractAccount.city;
                    var payAcNo = contractAccount.payAcNo;

                    $('.bank').val(bank);
                    $('.bankAddress').val(bankAddress);
                    $('.payAcNo').val(payAcNo);
                } else {
                    popupTip.show(resp.messages);
                }
            });
            this.listenTo(this.model, 'model:searchAccountInfoByContractNo', function(resp) {
                if (resp.code == 0) {
                    var accountInfo = resp.data.accountInfo;
                    if (_.isEmpty(accountInfo)) return;
                    var contractAccount = accountInfo.contractAccount;
                    var payerName = contractAccount.payerName;
                    var bank = contractAccount.bank;
                    var bankAddress = contractAccount.province + ' ' + contractAccount.city;
                    var payAcNo = contractAccount.payAcNo;
                    var address = accountInfo.contract.house.address;

                    this.planNolist = accountInfo.repaymentPlanList;
                    if (!_.isEmpty(this.planNolist)) {
                        var planNoListHtml = '';
                        for (var i = 0; i < this.planNolist.length; i++) {
                            var listItem = '<option class="option" value=" ' + this.planNolist[i].singleLoanContractNo + ' ">' + this.planNolist[i].singleLoanContractNo + '</option>';
                            planNoListHtml += listItem;
                        }
                        $('.option').remove();
                        $('.repaymentPlanNo').last().prepend(planNoListHtml);
                    }
                    $('.repaymentPlanNo').selectpicker('refresh');

                    $('.payerName').val(payerName);
                    $('.bank').val(bank);
                    $('.bankAddress').val(bankAddress);
                    $('.payAcNo').val(payAcNo);
                    $('.address').val(address);

                } else {
                    popupTip.show(resp.messages);
                }
            });
            this.listenTo(this.model, 'model:submit', function(resp) {
                if (resp.code == 0) {
                    this.confirm.show('主动还款凭证添加成功');
                } else {
                    popupTip.show(resp.message);
                }
            });
        },
        onChangeContractNoInput: function(e) {
            var contractNo = $(e.target).val().replace(/\s+/g, '');
            if (_.isEmpty(contractNo)) return;
            this.model.searchAccountInfoByContractNo(contractNo);
        },
        onChangePayerNameInput: function(e) {
            var name = $(e.target).val().replace(/\s+/g, '');
            if (_.isEmpty(name)) return;
            this.model.searchAccountInfoByName(name);
        },
        onChangeVoucherType: function(e) {
            var chooseType = $(e.target).find('option:selected').val();
            if (chooseType != '5') return;

            $('.paymentName').val($('.payerName').val());
            $('.paymentBank').val($('.bank').val());
            $('.paymentAccountNo').val($('.payAcNo').val());
        },
        defineValidator: function() {
            $.validator.addMethod('rightformatAmount', function(value, element) {
                return this.optional(element) || (/^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/.test(value));
            }, '请输入正确的金额格式');
            this.validator = this.$('form').validate({
                rules: {
                    voucherType: 'required',
                    contractNo: 'required',
                    paymentBank: 'required',
                    bankTransactionNo: 'required',
                    voucherAmount: {
                        required: true,
                        rightformatAmount: true
                    },
                    paymentName: 'required',
                    paymentAccountNo: 'required',
                    address: 'required'
                },
                errorPlacement: function(error, element) {
                    var parent = $(element).parent();
                    if (parent.is('.parcel-input')) {
                        parent.parent().append(error);
                    } else {
                        $(element).parent().append(error);
                    }
                }
            });
        },
        validate: function() {
            var flag = true;
            if (this.$('.filter-option').html() === '请选择还款编号') {
                this.$('.dropdown-toggle').addClass('error');
                flag = false;
            } else {
                flag = true;
                this.$('.dropdown-toggle').removeClass('error');
            }
            return this.validator.form() && flag;
        },
        extractDomData: function() {
            var attr = this._extractDomData(this.$('.real-value'), true);
            attr.repaymentPlanNo = this.getRepaymentPlanNoListJsonStr();
            attr.resourceUuids = JSON.stringify(this.uuidList);
            return attr;
        },
        getRepaymentPlanNoListJsonStr: function() {
            var repaymentPlanNoStr = $('.filter-option').last().text();
            var noList = repaymentPlanNoStr.split(', ');
            return JSON.stringify(noList);
        },
        submitHandler: function() {
            if (!this.validate()) return;
            var attr = this.extractDomData();
            this.model.submit(attr);
        }
    });
    module.exports = ActivePaymentCreateView;
});