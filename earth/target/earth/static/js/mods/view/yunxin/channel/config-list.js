define(function(require, exports, module) {
    var popupTip = require('component/popupTip');
    var TableContentView = require('baseView/tableContent');
    var FormDialogView = require('baseView/baseFormView').FormDialogView;
    var $ = jQuery;
    var root = global_config.root;

    var ConfigDetailView = FormDialogView.extend({
        className: 'modal fade form-modal yunxin-modal',
        template: _.template($('#configDetailTmpl').html(), {
            variable: 'data'
        })
    });

    var TableFieldView = TableContentView.extend({
        actions: {
            detail: root + '/paymentchannel/config/detail'
        },
        events: {
            'click .detailDialog': 'onClickDetatilDialog'
        },
        onClickDetatilDialog: function(e) {
            e.preventDefault();

            if (this.loading) return;
            this.loading = true;

            var self = this;
            var uuid = $(e.target).parents('tr').data('paymentchanneluuid');
            var opt = {
                url: this.actions.detail,
                data: {
                    paymentChannelUuid: uuid
                },
                dataType: 'json'
            };

            opt.success = function(resp) {
                if (resp.code == 0) {
                    var model = new Backbone.Model(resp.data);
                    var view = new ConfigDetailView({
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
    module.exports = TableFieldView;
});