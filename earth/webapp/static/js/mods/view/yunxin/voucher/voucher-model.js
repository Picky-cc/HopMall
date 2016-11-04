define(function(require, exports, module) {
    var root = global_config.root;

    var VoucherModel = Backbone.Model.extend({
        actions: {
            writeoff: function(detailId) {
                return root + '/voucher/business/hexiao/' + detailId;
            },
            singleWriteoff: function(sourceDocumentDetailId) {
                return root + '/voucher/business/detail/hexiao/' + sourceDocumentDetailId;
            },
            invalid: function(detailId) {
                return root + '/voucher/business/invalid/' + detailId;
            },
            activePaymentInvalid: function(detailId) {
                return root + '/voucher/active/invalid/' + detailId;
            },
            updateActiveVoucherComment: function(detailId) {
                return root + '/voucher/active/detail/update-comment/' + detailId;
            },
            matchCashFlow: function(detailId) {
                return root + '/voucher/business/detail/match-cash-flow/' + detailId;
            },
            connectionCashFlow: function(detailId) {
                return root + '/voucher/business/detail/connection-cash-flow/' + detailId;
            }
        },
        invalid: function() {
            var self = this;
            var detailId = this.get('detailId');
            var opt = {
                url: this.actions.invalid(detailId),
                dataType: 'json'
            };

            opt.success = function(resp) {
                self.trigger('model:invalid', resp, self);
            };

            $.ajax(opt);
        },
        writeoff: function() {
            var self = this;
            var detailId = this.get('detailId');
            var opt = {
                url: this.actions.writeoff(detailId),
                dataType: 'json'
            };

            opt.success = function(resp) {
                self.trigger('model:writeoff', resp, self);
            };

            $.ajax(opt);
        },
        singleWriteoff: function(sourceDocumentDetailId) {
            var self = this;
            var opt = {
                url: this.actions.singleWriteoff(sourceDocumentDetailId),
                dataType: 'json'
            };

            opt.success = function(resp) {
                self.trigger('model:singlewriteoff', resp, self);
            };

            $.ajax(opt);
        },
        activePaymentInvalid: function() {
            var self = this;
            var detailId = this.get('detailId');
            var opt = {
                url: this.actions.activePaymentInvalid(detailId),
                dataType: 'json'
            };
            opt.success = function(resp) {
                self.trigger('model:activePaymentInvalid', resp, self);
            };

            $.ajax(opt);
        },
        updateActiveVoucherComment: function(comment) {
            var self = this;
            var detailId = this.get('detailId');
            var opt = {
                url: this.actions.updateActiveVoucherComment(detailId),
                dataType: 'json',
                data: {
                    comment: comment
                }
            };
            opt.success = function(resp) {
                self.trigger('model:updateActiveVoucherComment', resp, self);
            };
            $.ajax(opt);
        },
        matchCashFlow: function() {
            var self = this;
            var detailId = this.get('detailId');
            var opt = {
                url: this.actions.matchCashFlow(detailId),
                dataType: 'json'
            };
            opt.success = function(resp) {
                self.trigger('model:matchCashFlow', resp, self);
            };

            $.ajax(opt);
        },
        connectionCashFlow: function(cashFlowUuid) {
            var self = this;
            var detailId = this.get('detailId');
            var opt = {
                url: this.actions.connectionCashFlow(detailId),
                dataType: 'json',
                data: {
                    cashFlowUuid: cashFlowUuid
                }
            };
            opt.success = function(resp) {
                self.trigger('model:connectionCashFlow', resp, self);
            };

            $.ajax(opt);
        }
    });

    exports.VoucherModel = VoucherModel;

});