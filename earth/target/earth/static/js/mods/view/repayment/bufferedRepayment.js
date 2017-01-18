define(function(require, exports, module) {
    var TableContentView = require('baseView/tableContent');
    var DialogView = require('component/dialogView');
    var popupTip = require('component/popupTip');
    //var CashFlowCardView = require('./cashFlowCardView');

    var RepaymentStatusZN = _.invert(BillConst.RepaymentStatus);

    var BufferedRepaymentView = TableContentView.extend({
        events: {
            'click .expand-bill': 'operateAuditView',
            'click .regulate-bill': 'operateAuditView',
            'click .buffered-repay': 'bufferedRepay',
            'click #allExpand': 'allExpandHandler'
        },
        initialize: function() {
        	BufferedRepaymentView.__super__.initialize.apply(this, arguments);
            //this.cashFlowCardViewList = {};

            var that = this;
            this.cancelDialogView = new DialogView({
                bodyInnerTxt: '确认提交缓冲回款申请？'
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
            $.post('./buffered-repay-apply', { repaymentBillUuid: options.repaymentBillUuid }, function (resp) {
                resp = JSON.parse(resp);
                that.cancelDialogView.hide();
                that.refresh();
                resp.code == 0 ? that.refresh() : popupTip.show(resp.message);
            });
        },
        bufferedRepay: function(e) {
            e.preventDefault();
            var tar = $(e.currentTarget);
            var basicOptions = $.extend({}, this._getOrderOptions(tar.parents('.bill-item')));
            this.cancelDialogView.show(basicOptions);
        },
        _getOrderOptions: function (parEl) {
            var repaymentBillUuid = parEl.data('repaymentbilluuid');
            var appId = parEl.data('appid');
            //var cashFlowTime = parEl.find('.happen-time').html().trim();
            var totalRent = parEl.find('.totalRent').html().trim();
            var repaymentStatus = RepaymentStatusZN[parEl.find('.reconcile-status').html().trim()];
            if (!repaymentBillUuid) throw 'no repaymentBillUuid';
            return {
            	repaymentBillUuid: repaymentBillUuid,
                appId: appId,
            	totalRent: totalRent,
                cashFlowItemEl: parEl,
                //cashFlowTime: cashFlowTime,
                repaymentStatus: repaymentStatus
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

    exports.BufferedRepaymentView = BufferedRepaymentView;

});