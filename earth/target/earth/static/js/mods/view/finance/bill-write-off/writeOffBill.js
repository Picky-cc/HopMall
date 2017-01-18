define(function(require, exports, module) {
    var util = require('scaffold/util');
    var path = require('scaffold/path');
    var TableContentView = require('baseView/tableContent');
    var DialogView = require('component/dialogView');
    var popupTip = require('component/popupTip');
    var FormDialogView = require('baseView/baseFormView').FormDialogView;
    
    var BillWriteOffModel = require('entity/finance/billWriteOffModel').BillWriteOffModel;
    var BillWriteOffCollection = require('entity/finance/billWriteOffModel').BillWriteOffCollection;

    var ManipulateLogView = require('./writeOffBillManipulate').ManipulateLogView;

    var root = global_config.root;
    var $ = jQuery;
    var $body = $(document.body);
    var loadingImg = global_const.loadingImg;

    var BillCardView = Backbone.View.extend({
        tagName: 'tr',
        className: 'bill-card-box',
        template: _.template($('#billCardTmpl').html()),
        events: {
            'click .submit': 'onClickSubmit',
            'click .check-bill': 'onClickCheckBill',
            'blur .reconcile-money-input': function(e) {
                this.auditInputReconcile($(e.target));
            }
        },
        initialize: function() {
            this.on('validate:reconcile', function(result) {
                if(result.flag) {
                    result.el.val(result.reconcileMoney.toFixed(2));
                }else {
                    this.toastErrorTip(result.el, result.message);
                }
                this.updateSummary();
            });

            this.listenTo(this.model, 'request', this.wating);
            this.listenTo(this.model, 'sync', this.render);
            this.listenTo(this.model, 'destroy', this.remove);

            this.listenTo(this.model, 'bill:submit', function(resp) {
                if(resp.code != 0) {
                    popupTip.show(resp.message);
                }
                this.model.refresh();
            });
            
            this.listenTo(this.model, 'totaloffSetAmount:invalid', function(msg, bills) {
                popupTip.show(msg);
            });
        },
        wating: function() {
            var td = $('<td colspan="1000" style="text-align: center;">');
            td.append(loadingImg.clone());
            this.$el.html(td);
        },
        detach: function() {
            this.$el.detach();
        },
        render: function() {
            var data = this.model.toJSON();
            var htm = '';

            data = this.computeData(data);
            htm = this.template(data);

            this.$el.html(htm);
        },
        computeBillItemData: function (item, mode) {
            if(item.amount == null) 
                throw('该条记录错误，没有应付金额');

            item.subtractAmount = item.remainderAmount;
        },
        computeData: function(source) {
            var records = source.records || [];
            var _totalDebitAmount = 0;
            for (var i = 0, len1 = records.length; i < len1; i++) {
                var item = records[i];
                this.computeBillItemData(item, source.mode);
                _totalDebitAmount += +(typeof item.writeOffAmount != 'undefined' ? item.writeOffAmount : item.remainderAmount);
            }
            source.totalDebitAmount = _totalDebitAmount.toFixed(2);

            return source;
        },
        updateSummary: function() {
            var calculateTotalAmount = function() {
                var total = 0;
                this.$('.record-item').each(function (index, el) {
                    var $el = $(el);
                    if (!$el.find('.check-bill').get(0).checked) return;
                    var val = +($el.find('.reconcile-money-input').val());
                    if(!isNaN(val)) {
                        total += val;
                    }
                });
                return total;
            };

            var total = calculateTotalAmount.call(this);
            var balance = this.model.get('canTotalWriteOffAmount') - total;

            this.$('.summary .money').html(total.toFixed(2));
            this.$('.summary .balance-money').html(balance.toFixed(2));
        },
        auditInputReconcile: function(el) {
            var subtractValue = +el.data('subtractvalue');
            var inputMoney = +el.val().trim();
            var result = { 
                el: el,
                reconcileMoney: inputMoney
            };

            if (isNaN(inputMoney)) {
                result.message = '请输入正确的金额';
                result.flag = false;
                this.trigger('validate:reconcile', result);
                return result;
            }

            if (subtractValue < inputMoney) {
                result.message = '冲销金额不能大于默认金额';
                result.flag = false;
                this.trigger('validate:reconcile', result);
                return result;
            }

            if (inputMoney <= 0) {
                result.message = '冲销金额应大于0';
                result.flag = false;
                this.trigger('validate:reconcile', result);
                return result;
            }

            result.flag = true;
            this.trigger('validate:reconcile', result);
            return result;
        },
        toastErrorTip: function(el, message) {
            var errorEl = el.prev('.error');
            var width = message.length * 12 + 30; // 12: 字体大小， 30: icon
            if (errorEl.length < 1) {
                errorEl = $('<span class="error">' + message + '</span>');
                el.before(errorEl);
            } else {
                errorEl.html(message);
            }
            errorEl.width(width).addClass('anim-fadeIn');
            var timer = el.data('timer');
            if (timer) {
                clearTimeout(timer);
            }
            timer = setTimeout(function() {
                errorEl.fadeOut(500, function() {
                    errorEl.remove();
                });
            }, 4000);
            el.data('timer', timer);
        },
        validate: function() {
            var isTrue = true;
            var self = this;
            var list = this.getRecordItemEls();

            list.checkeds.each(function(index, el) {
                var result = self.auditInputReconcile($(el).find('.reconcile-money-input'));
                if (!result.flag) {
                    isTrue = false;
                }
            });

            return isTrue;
        },
        getRecordItemEls: function() {
            var res = {};
            var els = this.$('.record-item .check-bill');

            res.checkeds = els.filter(':checked').parents('.record-item') || [];
            res.uncheckeds = els.not(':checked').parents('.record-item') || [];

            return res;
        },
        onClickSubmit: function(e) {
            if (!this.validate()) return;

            var list = this.getRecordItemEls();
            var checkedBills = $.map(list.checkeds, function(el) {
                var $el = $(el);
                return {
                    billingPlanUuid: $el.data('billing_plan_uuid'),
                    offSetAmount: $el.find('.reconcile-money-input').val()
                };
            });
            var uncheckedBills = $.map(list.uncheckeds, function(el) {
                var $el = $(el);
                return $el.data('billing_plan_uuid');
            });

            if(this.model.validateBill(checkedBills)) {
                this.model.submitBill(checkedBills, uncheckedBills);
            }
        },
        onClickCheckBill: function(e) {
            var reconcileInput = $(e.target).parents('.record-item').find('.reconcile-money-input');

            if (e.target.checked) {
                reconcileInput.removeClass('default');
            } else {
                reconcileInput.addClass('default');
            }

            this.auditInputReconcile(reconcileInput);
        }
    });

    var BillWriteOffItemView = Backbone.View.extend({
        tagName: 'tr',
        className: 'bill-item',
        template: _.template($('#billWriteOffItemTmpl').html()),
        events: {
            'click .expand-bill': 'onClickExpandBill',
            'click .regulate-bill': 'onClickRegulateBill',
            'click .cancel-bill': 'onClickCancelBill',
            'click .detail-bill': 'onClickDetailBill'
        },
        initialize: function(billEntryType) {
            this.billCardView = new BillCardView({model: this.model});

            this.cancelDialog = new DialogView({ bodyInnerTxt: '取消冲销后，将不会为你显示该账单，请确认' });
            this.listenTo(this.cancelDialog, 'goahead', function(options) {
                this.model.cancel(options);
            });

            this.initModel();
        },
        initModel: function() {
            this.listenTo(this.model, 'destroy', this.remove);

            this.listenTo(this.model, 'refresh', function(resp) {
                if(resp.code != 0) {
                    popupTip.show(resp.message);
                }
                this.model.foldIn();
                this.render();
            });

            this.listenTo(this.model.collection, 'reset', function() {
                this.remove();
                this.billCardView.remove();
            });

            this.listenTo(this.model, 'foldin', function() {
                this.$el.removeClass('z-active');
                this.billCardView.detach();
            });

            this.listenTo(this.model, 'foldout', function() {
                this.$el.addClass('z-active');
                this.$el.after(this.billCardView.$el);
            });

            this.listenTo(this.model, 'cancel', function(resp) {
                this.cancelDialog.hide();
                if(resp.code != 0) {
                    popupTip.show(resp.message);
                }
            });
        },
        render: function() {
            var data = this.model.toJSON();
            var htm = this.template({data: data});
            this.$el.html(htm);
        },
        onClickCancelBill: function(e) {
            e.preventDefault();
            this.cancelDialog.show();
        },
        onClickRegulateBill: function(e) {
            e.preventDefault();
            this.model.regulate();
        },
        onClickExpandBill: function(e) {
            e.preventDefault();
            this.model.toggle();
        },
        onClickDetailBill: function(e) {
            e.preventDefault();

            var cb = function(resp, model) {
                if(resp.code == 0) {
                    var dialogView = new ManipulateLogView({ 
                        className: 'modal fade table-modal',
                        title: '冲销操作详情',
                        excludeGoahed: true,
                        model: model 
                    });
                    dialogView.show();
                }else {
                    popupTip.show(resp.message);
                }
            };

            this.model.fetchLog(cb);
        }
    });

    var BillWriteOffView = TableContentView.extend({
        events: {
            'click #allExpand': 'onClickAllExpand'
        },
        initialize: function(billEntryType) {
            this.billEntryType = billEntryType;
            this.allExpandEl = this.$('#allExpand');

            this.initCollection();

            BillWriteOffView.__super__.initialize.apply(this, arguments);
        },
        initCollection: function() {
            this.collection = new BillWriteOffCollection();

            this.listenTo(this.collection, 'reset', function(collection, options) {
                var htm;

                if(collection.length < 1) {
                    htm = '<tr><td style="text-align: center;" colspan="100">没有更多数据</td></tr>';
                }else {
                    htm = [];
                    for(var i = 0, len = collection.length; i<len; i++) {
                        var view = new BillWriteOffItemView({model: collection.at(i)});
                        view.render();
                        htm.push(view.el);
                    }
                }

                // 注意：如果billcardView展开了的话，它的元素里面也是有tbody的，所以不能直接find('body')
                this.tableListEl.find(' > tbody').html(htm);
            });
        },
        polish: function(data) {
            var billEntryType = this.billEntryType;

            data.forEach(function(item) {
                item.billEntryType = billEntryType;
            });

            return data;
        },
        refreshTableDataList: function (data, opepration, response, query) {
            this.allExpandEl.html('全部展开');

            if(data.length < 1) {
                this.collection.reset();
            }else{
                var perfact = this.polish(data);
                this.collection.reset(perfact);
            }

            this.trigger('refreshdata.tablelist');
        },

        onClickAllExpand: function (e) {
            e.preventDefault();
            if(this.collection.isAllShow) {
                this.allExpandEl.html('全部展开');
                this.collection.foldIn();
            }else {
                this.allExpandEl.html('全部收起');
                this.collection.foldOut();
            }
        }
    });

    exports.BillWriteOffView = BillWriteOffView;

});