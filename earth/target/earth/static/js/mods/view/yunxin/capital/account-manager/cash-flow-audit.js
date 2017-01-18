define(function(require, exports, module) {
    var TableContentView = require('baseView/tableContent');
    var popupTip = require('component/popupTip');
    var DialogView = require('component/dialogView');
    var FormDialogView = require('view/baseFormView').FormDialogView;
    var entity = require('./cash-flow-audit-entity');
    var path = require('scaffold/path');

    var CashFlowAuditModel = entity.CashFlowAuditModel;
    var CashFlowAuditCollection = entity.CashFlowAuditCollection;
    var CashBillModel = entity.CashBillModel;
    var loadingImg = global_const.loadingImg;

    var CashFlowAuditListView = TableContentView.extend({
        initialize: function(accountSide) {
            CashFlowAuditListView.__super__.initialize.apply(this, arguments);
            this.initCollection();
        },
        onClickExport: function(e) {

            e.preventDefault();

            var params = this.collectParams();
            var tradeStartTimeStr = params.tradeStartTime;
            var tradeEndTimeStr = params.tradeEndTime;

            if (tradeStartTimeStr && tradeEndTimeStr) {
                var tradeStartTime = Date.parse(tradeStartTimeStr.replace(/\-/g, '/'));
                var tradeEndTime = Date.parse(tradeEndTimeStr.replace(/\-/g, '/'));
                var diffSecond = Math.floor((tradeEndTime - tradeStartTime) / 1000);
                var maxDiddSecond = 3 * 24 * 60 * 60;
                if (diffSecond > maxDiddSecond) {
                    var dialogView = new DialogView();
                    dialogView.show('时间跨度不允许超过3天！');
                    dialogView.on('goahead', function() {
                        this.hide();
                    });
                    return;
                }
            } else {
                var noTimeDialogView = new DialogView();
                noTimeDialogView.show('请选择入账起止时间!');
                noTimeDialogView.on('goahead', function() {
                    this.hide();
                });
                return;
            }

            var action = $(e.target).data('action');
            if (!action) return;

            var els = this.tableListEl.find('tbody').children();
            var nomore = els.length == 0 || els.first().is('.nomore');
            if (nomore) return;

            var url = path.format({
                path: action,
                query: params
            });

            // 没下完不能关闭
            window.open(url, '_download');
        },
        initCollection: function() {
            this.collection = new CashFlowAuditCollection();

            this.listenTo(this.collection, 'reset', function(collection, options) {
                var htm;

                if (collection.length < 1) {
                    htm = '<tr><td style="text-align: center;" colspan="100">没有更多数据</td></tr>';
                } else {
                    htm = [];
                    for (var i = 0, len = collection.length; i < len; i++) {
                        var view = new CashFlowItemView({
                            model: collection.at(i)
                        });
                        view.render();
                        htm.push(view.el);
                    }
                }

                this.tableListEl.find(' > tbody').html(htm);
            });

        },
        refreshTableDataList: function(data, opepration, response, query) {
            if (data.length < 1) {
                this.collection.reset();
            } else {
                var perfact = this.polish(data);
                this.collection.reset(perfact);
            }

            this.trigger('refreshdata.tablelist');
        }
    });

    var CashFlowItemView = Backbone.View.extend({
        tagName: 'tr',
        className: 'bill-item',
        template: _.template($('#tableFieldTmpl').html(), {
            variable: 'obj'
        }),
        events: {
            'click .btn-recharge': 'onClickExpend',
            'click .expand-bill': 'onClickExpend'
        },
        initialize: function() {
            this.billView = new CashBillView({
                model: this.model
            });

            this.listenTo(this.model, 'expand', function() {
                this.billView.$el.insertAfter(this.$el);
                this.$el.addClass('z-active');
            });

            this.listenTo(this.model, 'collapse', function() {
                this.billView.$el.remove();
                this.$el.removeClass('z-active');
            });

        },
        render: function() {
            var data = this.model.toJSON();
            var htm = this.template(data);
            this.$el.html(htm);
        },
        onClickExpend: function(e) {
            e.preventDefault();
            if ($(e.target).hasClass('btn-recharge')) {
                this.model.set('isRecharge', true);
            } else {
                this.model.set('isRecharge', false);
            }
            this.model.toggle();
        }
    });

    var CashBillView = Backbone.View.extend({
        tagName: 'tr',
        className: 'bill-card-box',
        template: _.template($('#CashBillTmpl').html(), {
            variable: 'obj'
        }),
        events: {
            'click .add-bill': 'onClickAddBill'
        },
        initialize: function() {
            this.listenTo(this.model.bills, 'request', function() {
                var htm = $('<td colspan="1000" style="text-align: center;">');
                htm.append(loadingImg.clone());
                this.$el.html(htm);
            });
            var bills = this.model.bills;
            this.listenTo(bills, 'reset', function() {
                this.addContent();
            });
            this.listenTo(bills, 'add', function() {
                this.addContent();
            });
        },
        addContent: function() {
            var bills = this.model.bills;
            var htm = [];
            if (bills.length < 1) {
                this.model.set('isRecharge', true);
            } else {
                for (var i = 0; i < bills.length; i++) {
                    bills.at(i).set('isRecharge', this.model.get('isRecharge'));
                    var view = new CashBillItemView({
                        model: bills.at(i)
                    });
                    view.render();
                    htm.push(view.el);
                }
            }

            var $content = $(this.template());
            $content.find('.record-list').append(htm);
            if (this.model.get('isRecharge')) {
                $content.find('.clearfix ').removeClass('hide');
            }

            this.$el.html($content);
        },
        render: function() {
            var data = this.model.toJSON();
            var htm = this.template();
            this.$el.html(htm);
        },
        onClickAddBill: function(e) {
            e.preventDefault();
            var model = new CashBillModel();
            var dialogView = new AddBillDialog({
                model: model
            });
            dialogView.show();
            this.listenTo(model, 'addbill', function() {
                this.model.bills.add(model);
            });
        }
    });

    var CashBillItemView = Backbone.View.extend({
        tagName: 'tr',
        template: _.template($('#CashBillItemTmpl').html(), {
            variable: 'obj'
        }),
        events: {
            'click .btn-success': 'onClickSbumit'
        },
        render: function() {
            var data = this.model.toJSON();
            var htm = this.template(data);
            this.$el.html(htm);
        },
        onClickSbumit: function() {
            popupTip.show('充值账单提交成功！');
        },
    });

    var AddBillDialog = FormDialogView.extend({
        template: _.template($('#AddBillDialogTmpl').html()),
        initialize: function() {
            AddBillDialog.__super__.initialize.apply(this, arguments);
        },
        save: function() {
            var data = this.extractDomData();
            this.model.set(data);
            this.model.trigger('addbill');
        },
        submitHandler: function(e) {
            this.save();
            this.hide();
        }
    });

    module.exports = CashFlowAuditListView;

});