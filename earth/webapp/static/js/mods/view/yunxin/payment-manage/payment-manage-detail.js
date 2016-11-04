define(function(require, exports, module) {
    var FormDialogView = require('baseView/baseFormView').FormDialogView;
    var DialogView = require('component/dialogView');
    var popupTip = require('component/popupTip');

    var popupTipForOverdueStatus = new DialogView({
        cancelBtnTxt: '关闭'
    });
    var root = global_config.root;
    var $ = jQuery;

    var ModifyOverdueStatusView = FormDialogView.extend({
        template: _.template($('#modifyOverdueStatusTmpl').html(), {
            variable: 'obj'
        }),
        actions: {
            modify: './confirm-overdue'
        },
        events: {
            'change [name=status]': 'onChangeStatus',
            'change [name=selectReason]': 'onChangeSelectReason'
        },
        initialize: function(options) {
            this.listenTo(this.model, 'preChange:status', function(status) {
                if (status == 2) { //  已逾期
                    this.$('.field-overdue-date').removeClass('hide');
                    this.$('[name=selectReason]').removeClass('hide');
                    this.$('[name=textareaReason]').addClass('hide');
                } else {
                    this.$('.field-overdue-date').addClass('hide');
                    this.$('[name=selectReason]').addClass('hide');
                    this.$('[name=textareaReason]').removeClass('hide');
                }
            });

            ModifyOverdueStatusView.__super__.initialize.apply(this, arguments);
            this.defineValidator();
        },
        defineValidator: function() {
            var self = this;

            this.validator = this.$('.form').validate({
                ignore: '.hide [name], .hide',
                rules: {
                    status: 'required',
                    textareaReason: {
                        required: true,
                        maxlength: 30
                    },
                    overdueDate: {
                        required: true
                    }
                },
                showErrors: function(errorMap, errorList) {
                    this.defaultShowErrors();

                    var exist = false;

                    errorList.forEach(function(item) {
                        var $el = $(item.element);
                        if ($el.is('[name=overdueDate]')) {
                            exist = true;
                        }
                    });


                    if (exist) {
                        self.$('[name=overdueDate]').parent().addClass('error');
                    } else {
                        self.$('[name=overdueDate]').parent().removeClass('error');
                    }
                },
                errorPlacement: function(error, element) {
                    var $el = $(element);
                    if ($el.is('[name=overdueDate]')) {

                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        },
        reasonIsCandidate: function(val) {
            return val === '余额不足';
        },
        render: function() {
            var data = this.model.toJSON();
            var status = data.status;
            var reason = data.reason;

            this.$el.html(this.template(data));

            // 手动触发
            this.model.trigger('preChange:status', status);
            this.$('[name=status]').val(status);
        },
        validate: function() {
            return this.validator.form();
        },
        onChangeSelectReason: function(e) {
            var val = $(e.target).val().trim();
            if (this.reasonIsCandidate(val)) {
                this.$('[name=textareaReason]').addClass('hide');
            } else {
                this.$('[name=textareaReason]').removeClass('hide');
            }
        },
        onChangeStatus: function(e) {
            var val = $(e.target).val();
            this.model.trigger('preChange:status', val);
        },
        save: function() {
            var self = this;
            var attr = {};

            attr.status = this.$('[name=status]').val();

            var tReasonEl = this.$('[name=textareaReason]');
            var sReasonEl = this.$('[name=selectReason]');
            if (tReasonEl.is(':visible')) {
                attr.reason = tReasonEl.val();
            } else {
                attr.reason = sReasonEl.val();
            }

            var overdueDateEl = this.$('.field-overdue-date');
            if (overdueDateEl.is(':visible')) {
                attr.overdueDate = overdueDateEl.find('[name=overdueDate]').val();
            }

            var opt = {
                url: this.actions.modify,
                type: 'post',
                dataType: 'json',
                data: attr
            };

            opt.success = function(resp) {
                if (resp.code == 0) {
                    // self.model.set(attr);
                }
                self.model.trigger('modify:auditOverdue', attr, resp);
            };

            $.ajax(opt);
        },
        submitHandler: function(e) {
            if (!this.validate()) return;
            this.save();
            this.hide();
        }
    });

    var ModifyRemarkView = FormDialogView.extend({
        template: _.template($('#modifyRemarkTmpl').html(), {
            variable: 'obj'
        }),
        actions: {
            modify: function(assetSetId) {
                return root + '/assets/' + assetSetId + '/update-comment';
            }
        },
        initialize: function() {
            ModifyRemarkView.__super__.initialize.call(this, arguments);
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
            var opt = {
                url: this.actions.modify(this.model.get('assetId')),
                type: 'post',
                dataType: 'json',
                data: attr
            };

            opt.success = function(resp) {
                if (resp.code == 0) {
                    // self.model.set(attr);
                }
                self.model.trigger('modify:comment', attr, resp);
            };

            $.ajax(opt);
        },
        submitHandler: function() {
            if (!this.validate()) return;
            this.save();
            this.hide();
        }
    });

    var RefundView = FormDialogView.extend({
        template: _.template($('#refundTmpl').html(), {
            variable: 'obj'
        }),
        actions: {
            refund: function(assetSetId) {
                return root + '/assets/' + assetSetId + '/update-refund';
            }
        },
        initialize: function() {
            RefundView.__super__.initialize.call(this, arguments);
            this.defineValidator();
        },
        defineValidator: function() {
            this.validator = this.$('.form').validate({
                rules: {
                    comment: {
                        required: true,
                        maxlength: 30
                    },
                    refundAmount: {
                        required: true,
                        nonNegativeNumber: true,
                        max: this.model.get('amount') ? +this.model.get('amount') : false
                    }
                },
                messages: {
                    refundAmount: {
                        max: $.validator.format('退款金额不超过还款金额 {0} 元')
                    }
                },
                errorPlacement: function(error, element) {
                    if ($(element).is('[name=refundAmount]')) {
                        $(element).parent().append(error);
                    } else {
                        error.insertAfter(element);
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
            var opt = {
                url: this.actions.refund(this.model.get('assetId')),
                type: 'post',
                dataType: 'json',
                data: attr
            };

            opt.success = function(resp) {
                if (resp.code == 0) {
                    // self.model.set(attr);
                }
                self.model.trigger('modify:refundAmount', attr, resp);
            };

            $.ajax(opt);
        },
        submitHandler: function() {
            if (!this.validate()) return;
            this.save();
            this.hide();
        }
    });

    var PaymentManageDetailView = Backbone.View.extend({
        el: '.content',
        events: {
            'click #preAuditOverdue': 'onClickPreAuditOverdue',
            'click #modifyRemark': 'onClickModifyRemark',
            'click #btnRefund': 'onClickBtnRefund'
        },
        initialize: function(opts) {
            this.initModel(opts.assetId);
        },
        initModel: function(assetId) {
            var attr = {};

            attr.assetId = assetId;
            attr.status = this.$('[name=overdueStatus]').val().trim();
            attr.refundAmount = +this.$('.refund-amount').data('value');
            attr.amount = +this.$('.amount').data('value');
            attr.comment = this.$('.comment').text().trim();
            attr.assetRecycleDate = this.$('.asset-recycle-date').text().trim();
            attr.actualRecycleDate = this.$('.actual-recycle-date').text().trim();

            this.model = new Backbone.Model(attr);
        },
        onClickPreAuditOverdue: function(e) {
            e.preventDefault();

            var view = new ModifyOverdueStatusView({
                model: this.model
            });

            this.model.once('modify:auditOverdue', function(attr, resp) {
                if (resp.code == 0) {
                    popupTipForOverdueStatus
                        .controlOperateBtns({
                            excludeGoahed: true,
                            excludeCancel: true
                        })
                        .show('操作成功');

                    setTimeout(function() {
                        location.reload();
                    }, 1000);
                } else {
                    popupTipForOverdueStatus
                        .controlOperateBtns({
                            excludeGoahed: true
                        })
                        .show(resp.message || '操作失败，请重试');
                }
            });

            view.show();
        },
        onClickModifyRemark: function(e) {
            e.preventDefault();

            var view = new ModifyRemarkView({
                model: this.model
            });

            this.model.once('modify:comment', function(attr, resp) {
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
        },
        onClickBtnRefund: function(e) {
            e.preventDefault();

            var view = new RefundView({
                model: this.model
            });

            this.model.once('modify:refundAmount', function(attr, resp) {
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


    module.exports = PaymentManageDetailView;

});