define(function(require, exports, module) {
    var popupTip = require('component/popupTip');
    var FormDialogView = require('baseView/baseFormView').FormDialogView;
    var $ = jQuery;
    var root = global_config.root;
    var AgainNotifyResultDialog = FormDialogView.extend({
        template: _.template($('#againNotifyResultTmpl').html()),
        submitHandler: function() {
            this.hide();
            location.reload();
        }
    });
    var OrderDetatilView = Backbone.View.extend({
        el: '.content',
        actions: {
            againNotifyResult: function(remittanceApplicationUuid) {
                return root + '/remittance/application/details/updateplannotifynumber/' + remittanceApplicationUuid;
            }
        },
        events: {
            'mouseover .showPopover': 'showPopover',
            'mouseout .showPopover': 'showPopover',
            'click .sort': 'onClickSortBtn',
            'click .againNotifyResult': 'onClickAgainNotifyResult'
        },
        showPopover: function(e) {
            e.preventDefault();
            var el = $(e.target);
            var popoverContent = el.siblings('.account').html();
            el.popover({
                content: popoverContent
            });
            el.popover('toggle');
        },
        onClickAgainNotifyResult: function(e) {
            var remittanceApplicationUuid = $(e.target).data('remittanceapplicationuuid');
            var opt = {
                url: this.actions.againNotifyResult(remittanceApplicationUuid),
                dataType: 'json'
            };
            opt.success = function(resp) {
                if (resp.code == 0) {
                    var view = new AgainNotifyResultDialog();
                    view.show();
                } else {
                    popupTip.show(resp.message);
                }
            };
            $.ajax(opt);
        }
    });
    module.exports = OrderDetatilView;
});