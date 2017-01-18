define(function(require, exports, module) {
    var popupTip = require('component/popupTip');
    var TableContentView = require('baseView/tableContent');
    var FormDialogView = require('baseView/baseFormView').FormDialogView;
    var $ = jQuery;
    var root = global_config.root;

    var SwitchDetailDialog = FormDialogView.extend({
        className: 'modal fade form-modal yunxin-modal',
        template: _.template($('#switchDetailTmpl').html() || '', {
            variable: 'data'
        })
    });

    var SwitchListView = TableContentView.extend({
        actions: {
            detail: root + '/paymentchannel/switch/detail'
        },
        events: {
            'click .switchDetail': 'onClickSwitchDetail'
        },
        onClickSwitchDetail: function(e) {
            e.preventDefault();
            var self = this;
            var uuid = $(e.target).parents('tr').data('financialcontractuuid');
            if (self.loading) return;
            self.loading = true;
            var opt = {
                dataType: 'json',
                url: this.actions.detail,
                data: {
                    financialContractUuid: uuid
                }
            };

            opt.success = function(resp) {
                if (resp.code == 0) {
                    var model = new Backbone.Model(resp.data);
                    var view = new SwitchDetailDialog({
                        model: model
                    });
                    view.show();
                } else {
                    popupTip.show(resp.message);
                }
            };

            opt.complete = function() {
                self.loading = false;
            };

            $.ajax(opt);
        }
    });

    module.exports = SwitchListView;
});