define(function(require, exports, module) {
    var popupTip = require('component/popupTip');
    var TableContentView = require('baseView/tableContent');
    var SegmentControlView = require('component/tab').SegmentControlView;
    var FormDialogView = require('baseView/baseFormView').FormDialogView;
    var $ = jQuery;
    var root = global_config.root;

    var QuotaDetailView = FormDialogView.extend({
        el: $('#quotaDetail').get(0),
        events: {
            'click .modify-quota': 'onClickModifyQuota'
        },
        defineValidator: function() {
            $.validator.addMethod('rightformatAmount', function(value, element) {
                return this.optional(element) || (/^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/.test(value));
            }, '请输入正确的金额格式');

            this.validator = this.$('.form').validate({
                rules: {
                    transactionLimitPerTranscation: {
                        required: false,
                        rightformatAmount: true
                    },
                    transcationLimitPerDay: {
                        required: false,
                        rightformatAmount: true
                    },
                    transactionLimitPerMonth: {
                        required: false,
                        rightformatAmount: true
                    }
                }
            });
        },
        validate: function() {
            return this.validator.form();
        },
        extractDomData: function() {
            var attr = this._extractDomData(this.$('.real-value'), true);
            // attr.paymentInstitutionOrdinal = this.paymentInstitutionOrdinal;
            // attr.accountSideOrdinal = this.accountSideOrdinal;
            // attr.bankCode = this.bankCode;
            attr.bankTransactionLimitSheetUuid = this.bankTransactionLimitSheetUuid;

            return attr;
        },
        submit: function() {
            var data = this.extractDomData();
            var opt = {
                data: data,
                type: 'post',
                dataType: 'json',
                url: root + '/paymentchannel/limitSheet/update'
            };
            opt.success = function(resp) {
                if (resp.code == 0) {
                    popupTip.show('编辑成功，正在跳转...');
                    setTimeout(function() {
                        location.reload();
                    }, 1000);
                } else {
                    popupTip.show(resp.message);
                }
            };
            $.ajax(opt);
        },
        onClickModifyQuota: function(e) {
            if (!this.validate()) return;
            this.submit();
            this.hide();
        },
        initialize: function() {
            QuotaDetailView.__super__.initialize.apply(this, arguments);
            this.segmentControl = new SegmentControlView({
                el: this.$('.segment-control').get(0)
            });
            this.defineValidator();
            this.initView();
        },
        initView: function() {
            // this.paymentInstitutionOrdinal = this.model.get('paymentInstitutionOrdinal');
            this.outlierChannelName = this.model.get('outlierChannelName');
            // this.accountSideOrdinal = this.model.get('accountSideOrdinal');
            this.accountSide = this.model.get('accountSide');
            // this.bankCode = this.model.get('bankCode');
            this.transactionLimitPerTranscation = this.model.get('transactionLimitPerTranscation');
            this.transcationLimitPerDay = this.model.get('transcationLimitPerDay');
            this.transactionLimitPerMonth = this.model.get('transactionLimitPerMonth');
            this.paymentInstitutionName = this.model.get('paymentInstitutionName');
            this.bankName = this.model.get('bankName');
            this.bankTransactionLimitSheetUuid = this.model.get('bankTransactionLimitSheetUuid');

            this.$('.paymentInstitutionName').text(this.paymentInstitutionName);
            this.$('.outlierChannelName').text(this.outlierChannelName);
            this.$('.bankName').text(this.bankName);
            this.$('.accountSide').text(this.accountSide);
            this.$('.transactionLimitPerTranscation').val(this.transactionLimitPerTranscation);
            this.$('.transcationLimitPerDay').val(this.transcationLimitPerDay);
            this.$('.transactionLimitPerMonth').val(this.transactionLimitPerMonth);
        }
    });
    var UploadQuotaFormView = FormDialogView.extend({
        template: _.template($('#createQuotaFormTmpl').html()),
        actions: {
            post: root + '/paymentchannel/file/upload'
        },
        events: {
            'change [name=file]': 'onChangeFile'
        },
        resetFormElement: function(el) {
            el.wrap('<form>').parent('form').get(0).reset();
            el.unwrap();
        },
        onChangeFile: function(e) {
            if (e.target.value && !/\.xlsx?$/.test(e.target.value)) {
                popupTip.show('请上传Excel文件');
                this.resetFormElement($(e.target));
            }
        },
        initialize: function() {
            UploadQuotaFormView.__super__.initialize.apply(this, arguments);
            this.defineValidator();
        },
        defineValidator: function() {
            this.validator = this.$('.form').validate({
                rules: {
                    paymentInstitutionName: 'required',
                    outlierChannelName: 'required',
                    accountSide: 'required'
                }
            });
        },
        validate: function() {
            return this.validator.form();
        },
        submit: function() {
            var opt = {
                url: this.actions.post,
                dataType: 'json',
                type: 'post'
            };
            opt.success = function(resp) {
                if (resp.code == 0) {
                    popupTip.show('上传成功，正在跳转...');
                    setTimeout(function() {
                        location.reload();
                    }, 1000);
                } else {
                    popupTip.show(resp.message);
                }
            };
            this.$('.form').ajaxSubmit(opt);
        },
        submitHandler: function() {
            if (!this.validate()) return;
            this.submit();
            this.hide();
        }
    });

    var TableFieldView = TableContentView.extend({
        events: {
            'click #uploadQuotaForm': 'onClickUploadQuotaForm',
            'click .modify-quota': 'onClickModifyQuota'
        },
        onClickModifyQuota: function(e) {
            e.preventDefault();
            var trData = $(e.target).parents('tr').data();
            // var paymentInstitutionOrdinal = trData.paymentinstitutionordinal;
            var paymentInstitutionName = trData.paymentinstitutionname;
            var outlierChannelName = trData.outlierchannelname;
            // var accountSideOrdinal = trData.accountsideordinal;
            var accountSide = trData.accountside;
            // var bankCode = trData.bankcode;
            var transactionLimitPerTranscation = trData.transactionlimitpertranscation;
            var transcationLimitPerDay = trData.transcationlimitperday;
            var transactionLimitPerMonth = trData.transactionlimitpermonth;
            var bankName = trData.bankname;
            var bankTransactionLimitSheetUuid = trData.banktransactionlimitsheetuuid;

            var model = new Backbone.Model();
            // model.set('paymentInstitutionOrdinal', paymentInstitutionOrdinal);
            model.set('paymentInstitutionName', paymentInstitutionName);
            model.set('outlierChannelName', outlierChannelName);
            // model.set('accountSideOrdinal', accountSideOrdinal);
            model.set('accountSide', accountSide);
            // model.set('bankCode', bankCode);
            model.set('transactionLimitPerTranscation', transactionLimitPerTranscation);
            model.set('transcationLimitPerDay', transcationLimitPerDay);
            model.set('transactionLimitPerMonth', transactionLimitPerMonth);
            model.set('bankName', bankName);
            model.set('bankTransactionLimitSheetUuid',bankTransactionLimitSheetUuid);


            var view = new QuotaDetailView({
                model: model
            });
            view.show();
        },
        onClickUploadQuotaForm: function(e) {
            var model = new Backbone.Model();
            var view = new UploadQuotaFormView({
                model: model
            });
            view.show();
        }
    });

    module.exports = TableFieldView;
});