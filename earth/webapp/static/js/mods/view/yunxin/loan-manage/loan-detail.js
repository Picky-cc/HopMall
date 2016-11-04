define(function(require, exports, module) {
    var popupTip = require('component/popupTip');
    var Pagination = require('component/pagination');
    var FormDialogView = require('baseView/baseFormView').FormDialogView;
    var $ = jQuery;
    var root = global_config.root;
    var ResendRemittancePlanDialog = FormDialogView.extend({
        template: _.template($('#resendRemittancePlanTmpl').html()),
        submitHandler: function() {
            this.hide();
            location.reload();
        }
    });
    var ModifyNoteDialog = FormDialogView.extend({
        template: _.template($('#modifyNoteTmpl').html()),
        action: {
            save: root + ''
        },
        initialize: function() {
            ModifyNoteDialog.__super__.initialize.apply(this, arguments);
            this.defineValidator();
        },
        defineValidator: function() {
            this.validator = this.$('form').validate({
                rules: {
                    note: {
                        required: true,
                        maxlength: 50
                    }
                },
                messages: {
                    note: {
                        maxlength: '备注信息不超过50字'
                    }
                }
            });
        },
        validate: function() {
            this.validator.form();
        },
        save: function() {
            var opt = {
                url: this.action.save,
                dataType: 'json',
                data: ''
            };
            opt.success = function(resp) {
                if (resp.code === 1) {

                } else {
                    popupTip.show(resp.message);
                }
            };
            $.ajax(opt);
        },
        submitHandler: function() {
            if (!this.validate()) return;
            // this.save();
        }
    });
    var ChannelLoanDialog = FormDialogView.extend({
        template: _.template($('#channelLoanTmpl').html()),
        action: {
            save: root + ''
        },
        initialize: function() {
            ChannelLoanDialog.__super__.initialize.apply(this, arguments);
            this.defineValidator();
        },
        defineValidator: function() {
            this.validator = this.$('form').validate({
                rules: {
                    channelSelect: 'required'
                }
            });
        },
        validate: function() {
            this.validator.form();
        },
        save: function() {
            var opt = {
                url: this.action.save,
                dataType: 'json',
                data: ''
            };
            opt.success = function(resp) {
                if (resp.code == 1) {

                } else {
                    popupTip.show(resp.message);
                }
            };
            $.ajax(opt);
        },
        submitHandler: function() {
            if (!this.validate()) return;
            // this.save();
        }
    });
    var LoanDetailView = Backbone.View.extend({
        el: '.content',
        actions: {
        	resendRemittancePlan: function(remittancePlanUuid) {
                return root + '/remittance/plan/resend?remittancePlanUuid=' + remittancePlanUuid;
            }
        },
        events: {
            'click #modifyNote': 'onClickModifyNote',
            'click #channelLoan': 'onClickChannelLoan',
            'click #resendRemittancePlan': 'onClickResendRemittancePlan',
            'mouseover .showPopover': 'showPopover',
            'mouseout .showPopover': 'showPopover'
        },
        onClickResendRemittancePlan: function(e) {
        	var remittancePlanUuid = $(e.target).data('remittanceplanuuid');
            var opt = {
                url: this.actions.resendRemittancePlan(remittancePlanUuid),
                dataType: 'json'
            };
            opt.success = function(resp) {
                if (resp.code == 0) {
                	var view = new ResendRemittancePlanDialog();
                    view.show();
                } else {
                    popupTip.show(resp.message);
                }
            };
            $.ajax(opt);
        },
        onClickModifyNote: function(e) {
        	e.preventDefault();
        	var modifyNoteDialog = new ModifyNoteDialog();
        	modifyNoteDialog.show();
        },
        onClickChannelLoan: function(e) {
            e.preventDefault();
            var channelLoanDialog = new ChannelLoanDialog();
            channelLoanDialog.show();
        },
        showPopover: function(e) {
            var el = $(e.target);
            var popoverContent = el.siblings('.account').html();
            el.popover({
                content: popoverContent
            });
            el.popover('toggle');
        }
    });

    module.exports = LoanDetailView;
});