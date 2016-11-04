define(function(require, exports, module) {
    var popupTip = require('component/popupTip');
    var DialogView = require('component/dialogView');

    var GuaranteeDetailModel = Backbone.Model.extend({
        actions: {
            activite: './guarantee-active',
            cancel: './guarantee-lapse'
        },
        activite: function() {
            var orderId = this.get('orderId');
            if (!orderId) return;

            var self = this;
            var opt = {
                url: this.actions.activite,
                dataType: 'json',
                type: 'post'
            };

            opt.success = function(resp) {
                self.trigger('activite', resp);
            };

            $.ajax(opt);
        },
        cancel: function() {
            var orderId = this.get('orderId');
            if (!orderId) return;

            var self = this;
            var opt = {
                url: this.actions.cancel,
                dataType: 'json',
                type: 'post'
            };

            opt.success = function(resp) {
                self.trigger('cancel', resp);
            };

            $.ajax(opt);
        }
    });

    var GuaranteeDetailView = Backbone.View.extend({
        el: '.content',
        events: {
            'click #activite': 'onClickActivite',
            'click #cancel': 'onClickCancel'
        },
        initialize: function(orderId) {
            var attr = {orderId: orderId};
            this.model = new GuaranteeDetailModel(attr);
            this.confirm = new DialogView();

            this.listenTo(this.model, 'activite', this.onAjaxCallback);

            this.listenTo(this.model, 'cancel', this.onAjaxCallback);

            this.listenTo(this.confirm, 'goahead', function(fn) {
                fn.call(this.model);
                this.confirm.hide();
            });
        },
        delayReload: function(time) {
            setTimeout(function() {
                location.reload();
            }, time || 1000);
        },
        onAjaxCallback: function(resp) {
            if (resp.code == 0) {
                this.delayReload();
            } else {
                popupTip.show(resp.message);
            }
        },
        onClickActivite: function() {
            this.confirm.show('确认激活？', this.model.activite);
        },
        onClickCancel: function() {
            this.confirm.show('确认作废？', this.model.cancel);
        }
    });

    module.exports = GuaranteeDetailView;

});