define(function(require, exports, module) {
    var FormDialogView = require('baseView/baseFormView').FormDialogView;
    var popupTip = require('component/popupTip');

    var root = global_config.root;
    var $ = jQuery;

    var ModifyPenaltyView = FormDialogView.extend({
        template: _.template($('#modifyPenaltyTmpl').html(), {
            variable: 'obj'
        }),
        actions: {
            submit: function(orderId) {
                return root + '/payment-manage/order/' + orderId + '/edit';
            }
        },
        initialize: function() {
            ModifyPenaltyView.__super__.initialize.call(this, arguments);
            this.defineValidator();
        },
        defineValidator: function() {
            this.validator = this.$('.form').validate({
                rules: {
                    comment: {
                        required: true,
                        maxlength: 50
                    }
                }
            });
        },
        validate: function() {
            return this.validator.form();
        },
        save: function() {
            var self = this;
            var attr = this.extractDomData();
            attr.orderId = this.model.get('orderId');
            var opt = {
                url: this.actions.submit(this.model.get('orderId')),
                type: 'post',
                dataType: 'json',
                data: attr
            };

            opt.success = function(resp) {
                if (resp.code == 0) {
                    // self.model.set(attr);
                }
                self.model.trigger('modify:penaltyAmount', attr, resp);
            };

            $.ajax(opt);
        },
        submitHandler: function() {
            if (!this.validate()) return;
            this.save();
            this.hide();
        }
    });

    var OrderDetailView = Backbone.View.extend({
        el: '.content',
        events: {
            'click #modifyPenalty': 'onClickModifyPenalty'
        },
        initialize: function(opts) {
            this.initModel(opts.orderId);
        },
        initModel: function(orderId) {
            var attr = {};
            attr.penalty = +this.$('.penalty-amount').data('value');
            attr.comment = this.$('.comment').text().trim();
            attr.orderId = orderId;
            attr.assetRecycleDate = this.$('.asset-recycle-date').text().trim();
            attr.customerName = this.$('.customer-name').text().trim();
            attr.numbersOfOverdueDays = this.$('.numbers-of-overdue-days').text().trim();
            this.model = new Backbone.Model(attr);
        },
        onClickModifyPenalty: function() {
            var view = new ModifyPenaltyView({
                model: this.model
            });

            this.model.once('modify:penaltyAmount', function(attr, resp) {
                if (resp.code == 0) {
                    popupTip.show('操作成功');

                    setTimeout(function() {
                        location.reload();
                    }, 1000);
                } else {
                    popupTip.show(resp.message || '操作失败，请重试');
                }
            });

            view.show();
        }
    });


    module.exports = OrderDetailView;

});