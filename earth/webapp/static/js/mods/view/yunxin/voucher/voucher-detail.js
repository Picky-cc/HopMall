define(function(require, exports, module) {
    var Pagination = require('component/pagination');
    var popupTip = require('component/popupTip');
    var VoucherModel = require('./voucher-model').VoucherModel;
    var FormDialogView = require('baseView/baseFormView').FormDialogView;

    var VoucherDetailView = Backbone.View.extend({
        el: '.content',
        events: {
            'click .onekey-writeoff': 'onClickOnekeyWriteoff',
            'click .writeoff': 'onClickSingleWriteoff',
            'click .invalid': 'onClickInvalid',
            'click .cash-flow-select': 'onClickCashFlowSelect'
        },
        initialize: function(detailId) {
            this.initModel(detailId);
            Pagination.find('.voucher-business-list');
        },
        initModel: function(detailId) {
            this.voucherModel = new VoucherModel({
                detailId: detailId
            });

            this.listenTo(this.voucherModel, 'model:writeoff', function(resp) {
                if (resp.code == 0) {
                    location.reload();
                } else {
                    popupTip.show(resp.message);
                }
            });

            this.listenTo(this.voucherModel, 'model:singlewriteoff', function(resp) {
                if (resp.code == 0) {
                    location.reload();
                } else {
                    popupTip.show(resp.message);
                }
            });

            this.listenTo(this.voucherModel, 'model:invalid', function(resp) {
                if (resp.code == 0) {
                    location.reload();
                } else {
                    popupTip.show(resp.message);
                }
            });

            this.listenTo(this.voucherModel, 'model:matchCashFlow', function(resp) {
                if (resp.code == 0) {
                    var model = new VoucherModel(resp.data);
                    model.set('detailId', detailId);
                    var cashFlowSelectView = new CashFlowSelectView({
                        model: model
                    });
                    cashFlowSelectView.show();
                } else {
                    popupTip.show(resp.message);
                }
            });
        },
        onClickSingleWriteoff: function(e) {
            e.preventDefault();
            var sourceDocumentDetailId = $(e.target).parents('tr').data('sourceDocumentDetailid');
            this.voucherModel.singleWriteoff(sourceDocumentDetailId);
        },
        onClickOnekeyWriteoff: function(e) {
            e.preventDefault();
            this.voucherModel.writeoff();
        },
        onClickInvalid: function(e) {
            e.preventDefault();
            this.voucherModel.invalid();
        },
        onClickCashFlowSelect: function(e) {
            e.preventDefault();
            this.voucherModel.matchCashFlow();
        }
    });

    var CashFlowSelectView = FormDialogView.extend({
        className: 'modal fade form-modal',
        template: _.template($('#cashFlowSelectView').html() || '', {
            variable: 'data'
        }),
        events: {
            'click [type=checkbox]': 'onClickCheckbox'
        },
        initialize: function() {
            CashFlowSelectView.__super__.initialize.apply(this, arguments);
            this.active = this.$('.data-list [type=checkbox]:checked').get(0);
            this.listenTo(this.model, 'model:connectionCashFlow', function(resp) {
                if (resp.code == 0) {
                    popupTip.show('流水匹配成功!正在跳转...');
                    setTimeout(function() {
                        location.reload();
                    }, 1000);
                } else {
                    popupTip.show(resp.message);
                }
            });
        },
        onClickCheckbox: function(e) {
            var target = e.target;

            if (!target.checked) {
                this.active = null;
                return;
            }

            if (this.active) {
                this.active.checked = false;
            }

            this.active = target;
        },
        submitHandler: function() {
            var cashFlowUuid = $(this.active).data('cashflowuuid');
            this.model.connectionCashFlow(cashFlowUuid);
            this.hide();
        }
    });

    module.exports = VoucherDetailView;

});