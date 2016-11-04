define(function(require, exports, module) {
    var popupTip = require('../../component/popupTip');

    var CashFlowCardModel = Backbone.Model.extend({
        urlRoot: './list-cashFlowMatchResult',
        idAttribute: 'auditBillUuid',
        initialize: function(attr, options) {
            // this.switchUrlRoot(options.mode);
        },
        parse: function(resp) {
            if (resp.code != 0) {
                popupTip.show(resp.message);
            } else {
                var res = {
                    records: resp.data.cashFlowMatchResultList
                };
                return res;
            }
        },
        addBillingPlan: function (attr) {
            var records = this.get('records');
            records.push(attr);
            this.set('records', records);
            this.trigger('add:cashflow', $.extend({}, attr), this); // 赋值一份副本
        },
        exitsBillPlan: function (cashFlowUuid) {
            var existBill = _.findWhere(this.get('records'), {cashFlowUuid: cashFlowUuid});
            return existBill ? true : false;
        }
    });

    module.exports = CashFlowCardModel;

});
