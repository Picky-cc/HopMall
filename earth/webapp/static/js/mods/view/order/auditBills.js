define(function(require, exports, module) {
    var TableContentView = require('baseView/tableContent');
    var DialogView = require('component/dialogView');
    var popupTip = require('component/popupTip');
    var CashFlowCardView = require('./cashFlowCardView');

    var RepaymentAuditStatusZN = _.invert(BillConst.RepaymentAuditStatus);

    var AuditBillsView = TableContentView.extend({
        events: {
            'click .expand-bill': 'operateAuditView',
            'click .regulate-bill': 'operateAuditView',
            'click .cancel-audit': 'cancelAudit',
            'click #allExpand': 'allExpandHandler'
        },
        initialize: function() {
        	AuditBillsView.__super__.initialize.apply(this, arguments);
            this.cashFlowCardViewList = {};

            var that = this;
            this.cancelDialogView = new DialogView({
                bodyInnerTxt: '取消对账后，系统将不为当前审计单对账，请确认'
            });
            this.listenTo(this.cancelDialogView, 'goahead', function(options) {
                that._cancelAudit(options);
            });

            this.on('refreshdata.tablelist', function() {
                that.clearBillCard();
                that.$('#allExpand').html('全部展开').removeClass('expand');
            });

        },
        operateAuditView: function (e) {
            e.preventDefault();
            var target = $(e.currentTarget);
            var billItemEl = target.parents('.bill-item');
            var basicOptions = this._getOrderOptions(billItemEl);

            if(target.hasClass('expand-bill')) {
                this.expandBill(basicOptions, billItemEl);
            }else if(target.hasClass('regulate-bill')) {
                this.regulateBill(basicOptions, billItemEl);
            }
        },
        expandBill: function(basicOptions, billItemEl) {
            basicOptions.repaymentAuditStatus === 'CREATE' ? (basicOptions.mode = 'seem') : (basicOptions.mode = 'match');
        	//basicOptions.mode = 'seem';
            var oldView = this.getCashFlowCardByAuditBillId(basicOptions.auditBillUuid);
            if (oldView) {
                this._foldBill(billItemEl, basicOptions);
            } else {
                this._expandBill(billItemEl, basicOptions);
            }
        },
        _expandBill: function(billItemEl, options) {
            var view = this.getCashFlowCardByAuditBillId(options.auditBillUuid);
            if (!view) {
                view = new CashFlowCardView(options);
                billItemEl.addClass('z-active').after(view.$el);
                this.setCashFlowCardWithAuditBillId(options.auditBillUuid, view);
                return view;
            }
            return view;
        },
        _foldBill: function (billItemEl, options) {
            billItemEl.removeClass('z-active');
            this.deleteCashFlowCardByAuditBillId(options.auditBillUuid);
        },
        _cancelAudit: function(options) {
            var that = this;
//            $.post('./close-debit-audit', { cashFlowUuid: options.cashFlowUuid }, function (resp) {
//                resp = JSON.parse(resp);
                that.cancelDialogView.hide();
                that.refresh();
//                resp.code == 0 ? that.refresh() : popupTip.show(resp.message);
//            });
        },
        cancelAudit: function(e) {
            e.preventDefault();
            var tar = $(e.currentTarget);
            var basicOptions = $.extend({}, this._getOrderOptions(tar.parents('.bill-item')));
            this.cancelDialogView.show(basicOptions);
        },
        regulateBill: function(basicOptions, billItemEl) {
            basicOptions.mode = 'seem';
            var oldView = this.getCashFlowCardByAuditBillId(basicOptions.auditBillUuid);
            if (oldView) {
                oldView.switchView(basicOptions.mode);
            } else {
                this._expandBill(billItemEl, basicOptions);
            }
        },
        _getOrderOptions: function (parEl) {
            var auditBillUuid = parEl.data('auditbilluuid');
            var appId = parEl.data('appid');
            var contractId = parEl.data('contractid');
            //var cashFlowTime = parEl.find('.happen-time').html().trim();
            var totalRent = parEl.find('.totalRent').html().trim();
            var repaymentAuditStatus = RepaymentAuditStatusZN[parEl.find('.reconcile-status').html().trim()];
            if (!auditBillUuid) throw 'no auditBillUuid';
            return {
            	auditBillUuid: auditBillUuid,
                appId: appId,
                contractId: contractId,
            	totalRent: totalRent,
                cashFlowItemEl: parEl,
                //cashFlowTime: cashFlowTime,
            	repaymentAuditStatus: repaymentAuditStatus
            };
        },
        
        getCashFlowCardByAuditBillId: function(auditbilluuid) {
            return this.cashFlowCardViewList[auditbilluuid];
        },
        setCashFlowCardWithAuditBillId: function(auditbilluuid, view) {
            this.cashFlowCardViewList[auditbilluuid] = view;
        },
        deleteCashFlowCardByAuditBillId: function(auditBillId) {
            var list = this.cashFlowCardViewList;
            var view = list[auditBillId];
            if (!view) return;
            view.remove();
            delete list[auditBillId];
            return view;
        },
        clearBillCard: function() {
            var list = this.cashFlowCardViewList;
            this.cashFlowCardViewList = {};
        },
    });

    exports.AuditBillsView = AuditBillsView;

});