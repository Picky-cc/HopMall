define(function(require, exports, module) {
    require('component/autocomplete');
    var util = require('scaffold/util');
    var path = require('scaffold/path');
    var TableContentView = require('baseView/tableContent');
    var DialogView = require('component/dialogView');
    var popupTip = require('component/popupTip');
    var FormDialogView = require('baseView/baseFormView').FormDialogView;

    var CashflowModel = require('entity/finance/cashflowModel').CashflowModel;
    var CashflowCollection = require('entity/finance/cashflowModel').CashflowCollection;
    
    var SidebarBillSelectView = require('./sidebarBillSelect');
    var MarkCashTypeView = require('./markCashType');
    var ManipulateLogView = require('./cashManipulateLog');
    var CreateBillView = require('./showCreateBill');
    var ExportKingDeeDataView = require('./exportData').ExportKingDeeDataView;
    var ExportYonYouDataView = require('./exportData').ExportYonYouDataView;
    var AddCashFlowView = require('./addCashFlow');

    var root = global_config.root;
    var $ = jQuery;
    var $body = $(document.body);
    var loadingImg = global_const.loadingImg;

    var BillCardView = Backbone.View.extend({
        tagName: 'tr',
        className: 'bill-card-box',
        template: _.template($('#billCardTmpl').html()),
        itemTemplate: _.template($('#billCardItemTmpl').html()),
        events: {
            'click .submit': 'onClickSubmit',
            'click .add-bill': 'onClickAddBill',
            'click .check-bill': 'onClickCheckBill',
            'blur .reconcile-money-input': function(e) {
                this.auditInputReconcile($(e.target));
            },
            'click .show-create-bill': 'onClickCreateBill'
        },
        initialize: function() {
            this.on('validate:reconcile', function(result) {
                if(result.flag) {
                    result.el.val(result.reconcileMoney.toFixed(2));
                    this.updateSummary();
                }else {
                    this.toastErrorTip(result.el, result.message);
                }
            });

            this.listenTo(this.model, 'request', this.wating);
            this.listenTo(this.model, 'sync', this.render);
            this.listenTo(this.model, 'change:auditStatus', function(model, value) {
                if(value == 'CLOSE') {
                    this.model.foldIn();
                }
            });
            this.listenTo(this.model, 'bill:search', function(bill, resp) {
                if(resp.code == 0) {
                    if(this.model.existBill(bill.billingPlanUuid)) {
                        popupTip.show('订单已在列表中');
                    }else {
                        this.model.addBill(bill);
                    }
                }else {
                    popupTip.show(resp.message);
                }
            });
            this.listenTo(this.model, 'bill:add', function(attr) {
                this.computeBillItemData(attr, this.model.get('mode'));

                var recordList = this.$('.record-list');
                var htm = this.itemTemplate({
                    item: attr,
                    app: this.model.get('app')
                });

                recordList.append(htm);

                this.updateSummary();
            });
            this.listenTo(this.model, 'bill:submit', function(resp) {
                if(resp.code == 0) {
                    this.model.setMode('match');
                    this.model.foldOut();
                }else {
                    popupTip.show(resp.message);
                }
            });
            this.listenTo(this.model, 'totalbookingAmount:invalid', function(msg, bills) {
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

            if(data.mode == 'match' && data.auditStatus == 'CLOSE') {
                htm = '<td colspan="1000" style="text-align: center;">没有数据</td>';
            }else {
                data = this.computeData(data);
                htm = this.template(data);
            }

            this.$el.html(htm);
        },
        computeBillItemData: function (item, mode) {
            var processSubtractAmout = function (item) {
                var result;
                result = item.receivableAmount - (mode === 'match' ? item.settlementAmount : item.currentSpecificAmount);
                $.isNumeric(result) || (result = item.receivableAmount);
                item.subtractAmount = Math.min(result, this.model.get('amount')); // 取两者的最小值
            };
            var processPayer = function (item) {
                var _accountNo = [], _accountName = [];
                var accountAndNameMap = item.showData.accountAndNameMap || {};
                $.each(accountAndNameMap, function (key, val) {
                    _accountNo.push(key);
                    _accountName.push(val);
                });
                item.showData.accountName = _accountName.join(', ');
                item.showData.accountNo = _accountNo.join(', ');
            };

            // 应付金额，for test.
            if(item.receivableAmount == null) 
                throw('该条记录错误，没有应付金额');
                
            // 已付金额
            item.settlementAmount == null && (item.settlementAmount = 0);
            // 当前明确金额
            item.currentSpecificAmount == null && (item.currentSpecificAmount = 0);
            // 对账金额默认值
            processSubtractAmout.call(this, item);
            // 付款方名称，付款方账号
            processPayer.call(this, item);
        },
        computeData: function(source) {
            var records = source.records || [];
            var _totalDebitAmount = 0;
            for (var i = 0, len1 = records.length; i < len1; i++) {
                var item = records[i];
                this.computeBillItemData(item, source.mode);
                // 合计对账金额
                if (item.journalVoucherStatus == 'VOUCHER_ISSUED') {
                    _totalDebitAmount += +(typeof item.bookingAmount != 'undefined' ? item.bookingAmount : item.subtractAmount);
                }
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
            var balance = this.model.get('amount') - total;

            this.$('.summary .money').html(total.toFixed(2));
            this.$('.summary .balance-money').html(balance.toFixed(2));
        },
        popupSidebar: function(options) {
            var self = this;
            var sidebarBillSelectView = new SidebarBillSelectView(options);

            sidebarBillSelectView
                .on('sidebar:selecedbill', function (billingPlanUuid) {
                    self.model.searchBill({billingPlanUuid: billingPlanUuid});
                })
                .on('sidebar:createbill', function(attr) {
                    self.popupCreateBillView(attr);
                });

            sidebarBillSelectView.show().slideIn('right', 200);
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
                result.message = '对账金额不能大于默认金额';
                result.flag = false;
                this.trigger('validate:reconcile', result);
                return result;
            }

            if (inputMoney <= 0) {
                result.message = '对账金额应大于0';
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
            var list = this.getCheckedRecordItem();

            if(list.length < 1) return isTrue;

            list.each(function(index, el) {
                var result = self.auditInputReconcile($(el).find('.reconcile-money-input'));
                if (!result.flag) {
                    isTrue = false;
                }
            });

            return isTrue;
        },
        getCheckedRecordItem: function() {
            var res = this.$('.record-item .check-bill:checked').parents('.record-item');
            return res;
        },
        popupCreateBillView: function(attr) {
            var balanceMoney = this.$('.balance-money').text();

            if(+balanceMoney <= 0) {
                popupTip.show('剩余对账金额小于等于0时无法再进行制单操作');
                return;
            }

            attr = $.extend({}, {
                appId: this.model.getAppId(),
                cashFlowTime: this.model.get('time'),
                accountSide: this.model.get('accountSide'),
                balanceMoney: balanceMoney
            }, attr);

            var tmp = new Date(attr.cashFlowTime);
            var year = tmp.getFullYear();
            var month = tmp.getMonth();

            var billEffectiveDate = new Date();
            billEffectiveDate.setFullYear(year, month, 1);
            attr.billEffectiveDate = billEffectiveDate;
            
            var billMaturityDate = new Date();
            billMaturityDate.setFullYear(year, month + 1, 1);
            billMaturityDate.setDate(billMaturityDate.getDate() - 1);
            attr.billMaturityDate = billMaturityDate;

            var model = new Backbone.Model(attr);
            var createBillView = new CreateBillView({ model: model });
            var self = this;

            model.once('change', function(model) {
                self.model.searchBill({billingPlanNumber: model.get('billingPlanNumber')});
            });

            createBillView.show();
        },
        onClickCreateBill: function(e) {
            e.preventDefault();

            var first = this.model.get('records')[0];
            var attr = {
                subjectMatterSourceNo: first ? first.showData.subjectMatterSourceNo : ''
            };
            
            this.popupCreateBillView(attr);
        },
        onClickSubmit: function(e) {
            if (!this.validate()) return;

            var list = this.getCheckedRecordItem();
            var bills = $.map(list, function(el) {
                var $el = $(el);
                return {
                    billingPlanUuid: $el.data('billing_plan_uuid'),
                    bookingAmount: $el.find('.reconcile-money-input').val()
                };
            });

            if(this.model.validateBill(bills)) {
                this.model.submitBill(bills);
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
        },
        onClickAddBill: function(e) {
            e.preventDefault();

            this.popupSidebar({
                appId: this.model.getAppId(),
                cashFlowTime: this.model.get('time'),
                keyWord: this.model.get('payName') ? this.model.get('payName').slice(0, 4) : ''
            });
        }
    });

    var CashflowItemView = Backbone.View.extend({
        tagName: 'tr',
        className: 'bill-item',
        template: _.template($('#cashflowItemTmpl').html()),
        events: {
            'click .cancel-bill': 'onClickCancelBill',
            'click .regulate-bill': 'onClickRegulateBill',
            'click .expand-bill': 'onClickExpandBill',
            'click .mark-bill': 'onClickMarkBill',
            'click .detail-bill': 'onClickDetailBill'
        },
        initialize: function(options) {
            this.billCardView = new BillCardView({model: this.model});

            this.cancelDialog = new DialogView({bodyInnerTxt: '取消对账后，系统将不为当前流水匹配账单，请确认'});
            this.listenTo(this.cancelDialog, 'goahead', function(options) {
                this.model.cancel();
            });

            this.initModel();
        },
        initModel: function() {
            this.listenTo(this.model, 'change:auditStatus', this.render);

            this.listenTo(this.model, 'change:markInfo', function(model, value) {
                this.$('.mark-type').text(value.financialAccountAlias);
            });

            this.listenTo(this.model.collection, 'reset', function() {
                this.remove();
                this.billCardView.remove();
            });

            this.listenTo(this.model, 'foldin', function() {
                this.$el.removeClass('z-active');
                this.billCardView.detach();
            });

            // 因为有多种操作可能会打开foldout(如：单个展开，全部展开)，但都使用同一个事件接口，只要在这个事件接口里更新UI就行了（UI都一致）。否则的话就得在每个操作里面都写一遍更新UI的代码
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
            var htm = this.template({appArriveRecord: data});
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
        onClickMarkBill: function(e) {
            e.preventDefault();

            var cb = function(resp, model) {
                if(resp.code == 0) {
                    var markCashTypeView = new MarkCashTypeView({ model: model });
                    markCashTypeView.show();
                }else {
                    popupTip.show(resp.message);
                }
            };

            this.model.fetchMarkInfo(cb);
        },
        onClickDetailBill: function(e) {
            e.preventDefault();

            var cb = function(resp, model) {
                if(resp.code == 0) {
                    var dialogView = new ManipulateLogView({ 
                        className: 'modal fade table-modal',
                        title: '流水操作详情',
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

    var CashflowDebitView = TableContentView.extend({
        events: {
            'change #appIdSelect': 'onChangeAppId',
            'change .account-info': 'onClickAccountInfo',
            'change [name=accountNo]': 'onChangeAccountNo',
            'click #allExpand': 'onClickAllExpand',
            'click #exportKingDeeVoucher' : 'onClickExportKingDeeVoucher',
            'click #exportYonYouVoucher' : 'onClickExportYonYouVoucher',
            'click #exportAppArriveRecord':'onClickExportAppArriveRecord',
            'click #addCashFlow':'onAddCashFlow'
        },
        actions: {
            trade_parties: './query-trade-parties',
            account: './list-account-accountName?type=appId',
            export_apparrive_record: './export-apparrive-record'
        },
        initialize: function(accountSide) {
            this.accountSide = accountSide;
            this.allExpandEl = this.$('#allExpand');

            this.initCollection();
            this.initFinancialAccount();
            this.initAutoComplete();

            // 因为标记类型是由initFinancialAccount生成的
            CashflowDebitView.__super__.initialize.apply(this, arguments);
        },
        initAutoComplete: function() {
            var self = this;
            var el = this.$('[name="queryKeyWord"]');
            var opt = {
                action: this.actions.trade_parties,
                container: this.$('.auto-complete-list'),
                parse: function(resp) {
                    if (resp.code == 0) {
                        var arr1 = [], // 房东
                            arr2 = []; // 房客
                            
                        _.each(resp.data.tradeParties, function(value, key) {
                            var item = {};
                            item.name = key;
                            item.isHost = value == 0;

                            if(item.isHost) {
                                arr1.push(item);
                            }else {
                                arr2.push(item);
                            }
                        });

                        if(self.accountSide === 'debit') {
                            return arr2.concat(arr1);
                        }else {
                            return arr1.concat(arr2);
                        }
                    } else {
                        return [];
                    }
                },
                search: function(inputVal) {
                    return {
                        appId: self.getAppId(),
                        keyWord: inputVal
                    };
                },
                parcelItem: function(item) {
                    var str;
                    if(item.isHost) {
                        str = "<li class='item'><span>"+item.name+"</span><span style='color: #ff7500'>（房东）</span></li>";
                    }else {
                        str = "<li class='item'><span>"+item.name+"</span></li>";
                    }
                    return str;
                },
                onSync: function (list, inputVal, container) {
                    if(list.length < 1) {
                        container.hide();
                    }
                },
                onSubmit: function(inputEl, itemEl, eventtype) {
                    var title = itemEl.find('span').first().text();

                    if(eventtype == 'enter') {
                        inputEl.val(title);
                    }else if(eventtype == 'click') {
                        inputEl.val(title.slice(0, 4));
                    }

                    self.query();
                }
            };

            el.autocomplete(opt);
        },
        initCollection: function() {
            this.collection = new CashflowCollection();

            this.listenTo(this.collection, 'reset', function(collection, options) {
                var htm;

                if(collection.length < 1) {
                    htm = '<tr><td style="text-align: center;" colspan="100">没有更多数据</td></tr>';
                }else {
                    htm = [];
                    for(var i = 0, len = collection.length; i<len; i++) {
                        var view = new CashflowItemView({model: collection.at(i)});
                        view.render();
                        htm.push(view.el);
                    }
                }

                // 注意：如果billcardView展开了的话，它的元素里面也是有tbody的，所以不能直接find('body')
                this.tableListEl.find(' > tbody').html(htm);
            });
        },
        initFinancialAccount: function() {
            var markTypes = this.collection.getMarkTypes();
            var htm = '';
            $.each(markTypes, function(value, alias) {
                htm += '<option value="'+value+'">'+alias+'</option>';
            });
            this.$('[name=financialAccountName]').append(htm);
        },
        getAppId: function() {
            return this.$('[name="appId"]').val();
        },
        polish: function(data) {
            var accountSide = this.accountSide;

            data.forEach(function(item) {
                item.accountSide = accountSide;
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
        },
        onChangeAccountNo: function(e) {
        	var selectedOption = $(e.target).find(':selected');
            var attr = selectedOption.data('attr') || {};

            if(attr.type === 'cash') {
                this.$('#addCashFlow').show();
            }else {
                this.$('#addCashFlow').hide();
            }
        },
        onClickAccountInfo: function(e) {
            var target = $(e.target);
            var selectedOption = target.find(':selected');
            var id = selectedOption.data('id');
            var select = function(el) {
                var options = el.get(0).options;
                $.each(options, function(index, item) {
                    var _id = $(item).data('id');
                    if (_id === id) {
                        item.selected = true;
                        return;
                    }
                });
            };

            if(target.is('#accountNo')) {
                select(this.$('#accountName'));
            } else if (target.is('#accountName')) {
                select(this.$('#accountNo'));
            }

            // 在TableContent中取消了这两个select控件的change事件
            this._queryByParams();
        },
        onChangeAppId: function(e) {
            var self = this;
            var val = $(e.target).val();
            var nostr = '<option value="">银行账户号</option>';
            var namestr = '<option value="">银行账户名</option>';

            if (!val) {
                this.$('#accountNo').html(nostr);
                this.$('#accountName').html(namestr);
                return;
            }

            var opt = {
                url: this.actions.account,
                type: 'post',
                dataType: 'json',
                data: {
                    appId: val
                }
            };

            opt.success = function(resp) {
                if (resp.code == 0) {
                    resp.data.accountList.forEach(function(item) {
                        nostr += '<option data-id="' + item.id + '" value="' + item.accountNo + '">' + item.accountShortName + '</option>';
                        namestr += '<option data-id="' + item.id + '" value="' + item.accountName + '">' + item.accountName + '</option>';
                    });
                    self.$('#accountNo').html(nostr);
                    self.$('#accountName').html(namestr);
                }
            };

            $.ajax(opt);
        },
        onClickExportKingDeeVoucher:function(e){
            e.preventDefault();

            var view, query;

            query = this.collectParams();
            query.accountSide = this.accountSide;
            view = new ExportKingDeeDataView({ query: query });

            view.show();
        },
        onClickExportYonYouVoucher: function(e) {
            e.preventDefault();

            var view, query;

            query = this.collectParams();
            query.accountSide = this.accountSide;
            view = new ExportYonYouDataView({ query: query });

            view.show();
        },
        onClickExportAppArriveRecord:function(e){
            e.preventDefault();

            var href, query;

            query = this.collectParams();
            query.accountSide = this.accountSide;
            href = path.format({
                path: this.actions.export_apparrive_record,
                query: query
            });

            location.assign(href);
        },
        onAddCashFlow:function(e){
            e.preventDefault();

            var self = this;
            var view, query;

            query = this.collectParams();
            query.accountSide = this.accountSide;
            view = new AddCashFlowView({query: query});

            view.once('sync:save', function(resp) {
                if(resp.code == 0) {
                    popupTip.show('添加成功！');
                    self.refresh();
                }else {
                    popupTip.show(resp.message);
                }
            });

            view.show();
        }
    });

    exports.CashflowDebitView = CashflowDebitView;

});