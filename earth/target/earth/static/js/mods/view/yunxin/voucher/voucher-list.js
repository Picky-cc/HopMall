define(function(require, exports, module) {
    var TableContentView = require('baseView/tableContent');
    var popupTip = require('component/popupTip');
    var VoucherModel = require('./voucher-model').VoucherModel;

    var VoucherListView = TableContentView.extend({
        events: {
            'click .writeoff': 'onClickWriteoff',
            'click .invalid': 'onClickInvalid'
        },
        onClickInvalid: function(e) {
            e.preventDefault();

            var self = this;
            var detailId = $(e.target).parents('tr').data('detail-id');
            var model = new VoucherModel({detailId: detailId});

            model.once('model:invalid', function(resp) {
                if (resp.code == 0) {
                    self.refresh();
                } else {
                    popupTip.show(resp.message);
                }
            });

            model.invalid();
        },
        onClickWriteoff: function(e) {
            e.preventDefault();

            var self = this;
            var detailId = $(e.target).parents('tr').data('detail-id');
            var model = new VoucherModel({detailId: detailId});

            model.once('model:writeoff', function(resp) {
                if (resp.code == 0) {
                    self.refresh();
                } else {
                    popupTip.show(resp.message);
                }
            });

            model.writeoff();
        }
    });

    module.exports = VoucherListView;

});