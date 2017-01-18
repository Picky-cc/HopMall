define(function(require, exports, module) {
    var CashFlowCardModel = require('entity/order/cashFlowCardModel');
    var DialogView = require('component/dialogView');
    var popupTip = require('component/popupTip');
    var util = require('scaffold/util');
    //var SidebarBillSelectView = require('./sidebarBillSelect');
    
    var $ = jQuery;
    var $body = $(document.body);
    var toNumber = Number;
    var isNumber = _.isNumber;
    var loadingImg = global_const.loadingImg;
    var templateStorage = function () {
        var obj = {};

        var cashFlowCardItemTmplEl = $('#cashFlowCardItemTmpl');
        var _fillCashFlowTrTmpl = cashFlowCardItemTmplEl.html();
        obj.fillCashFlowTrTmplFunc = _.template(_fillCashFlowTrTmpl);

        var tdNum = $(_fillCashFlowTrTmpl).find('td').length;
        var _blankBillTrTmpl = [
            "<td><input type='text' placeholder='请输入流水号...' class='serial-number'></td>",
            util.copyStringWithNum(tdNum-1, '<td></td>')
        ].join("");
        obj.blankBillTrTmpl = _.template(_blankBillTrTmpl)();

        obj.cashFlowCardTmpl = $('#cashFlowCardTmpl').html() || '';

        return obj;
    }($);

    var CashFlowCardView = Backbone.View.extend({
        events: {
            'click .submit': 'submitBill',
            'click .add-bill': 'addBill',
            'click .check-bill': 'checkBill',
            'blur .reconcile-money-input': 'reconcileMoneyInputBlurHandler',
            'focus .reconcile-money-input': 'reconcileIptFocus',

            'keyup .serial-number': 'getBillHandler',
            'blur .serial-number': 'getBillHandler',
            'click .unbind': 'unbindContract',
            'click .veto': 'vetoCashFlow'
        },
        tagName: 'tr',
        className: 'bill-card-box',
        template: _.template(templateStorage.cashFlowCardTmpl),
        initialize: function(options) {
            $.extend(this, _.pick(options, ['auditBillUuid', 'appId', 'contractId', 'totalRent', 'cashFlowItemEl', 'repaymentAuditStatus']));
            this.totalRent = toNumber(this.totalRent.replace(/,/g, ''));

            this.initModel(options);
            this.initDialogView();
            this.switchView(options.mode);
            
            var that = this;
            this.unbindDialogView = new DialogView({
                bodyInnerTxt: '确认与改条流水解绑吗？'
            });
            this.listenTo(this.unbindDialogView, 'goahead', function(options) {
                that._unbindContract(options);
            });
            
            this.vetoDialogView = new DialogView({
                bodyInnerTxt: '确认取消该条流水对账吗？'
            });
            this.listenTo(this.vetoDialogView, 'goahead', function(options) {
                that._vetoCashFlow(options);
            });

            // btnOnceClickProxy.proxy(this.submitBill, this._submitBill, this);
        },
        initModel: function (options) {
            this.model = new CashFlowCardModel({ auditBillUuid: options.auditBillUuid }, options);
            this.listenTo(this.model, 'request', this.wating);
            this.listenTo(this.model, 'sync', this.render);
            this.listenTo(this.model, 'add:cashflow', this.addCashFlowHandler);
        },
        addCashFlowHandler: function (attr, model) {
            this._processOneCashFlowItemData(attr);
            this.recordList
                .find('.blank-item')
                .replaceWith(templateStorage.fillCashFlowTrTmplFunc({
                    item: attr, 
                    appId: model.get('appId')
                }));
            this.updateTotalAmout();
        },
        initDialogView: function() {
            var that = this;

            var wrongDialogV = this.wrongDialogView = new DialogView();
            this.listenTo(wrongDialogV, 'goahead', function() {
                wrongDialogV.hide();
                that._submitBill(that.collectBillData());
            });

            var succDialogV = this.succDialogView = new DialogView();
            this.listenTo(succDialogV, 'goahead', function() {
                that.switchView('match');
                succDialogV.hide();
            });
        },
        render: function() {
            var str = this.template(this.compactData(this.model.toJSON()));
            this.$el.html(str);
            this.recordList = this.$('.record-list');
            return this;
        },
        _processOneCashFlowItemData: function (item) {
            var processSubtractAmout = function (item) {
                var result;
                result = item.showData.amount - (this.viewMode === 'match' ? item.settlementAmount : item.currentSpecificAmount);
                isNumber(result) || (result = item.showData.amount);
                item.subtractAmount = Math.min(result, this.totalRent); // 取两者的最小值
            };
//            var processPayer = function (item) {
//                var _accountNo = [], _accountName = [];
//                var accountAndNameMap = item.showData.accountAndNameMap || {};
//                $.each(accountAndNameMap, function (key, val) {
//                    _accountNo.push(key);
//                    _accountName.push(val);
//                });
//                item.showData.accountName = _accountName.join(', ');
//                item.showData.accountNo = _accountNo.join(', ');
//            };

            // 应付金额，for test.
//            if(item.receivableAmount == null) 
//                throw('该条记录错误，没有应付金额');
                
            // 已付金额
            item.settlementAmount == null && (item.settlementAmount = 0);
            // 当前明确金额
            item.currentSpecificAmount == null && (item.currentSpecificAmount = 0);
            // 对账金额默认值
            processSubtractAmout.call(this, item);
            // 付款方名称，付款方账号
            //processPayer.call(this, item);
        },
        compactData: function(source) {
            source.mode = this.viewMode;
            var records = source.records;
            var item;
            var _totalAuditAmount = 0;
            for (var i = 0, len1 = records.length; i < len1; i++) {
                item = records[i];
                this._processOneCashFlowItemData(item);
                // 合计对账金额
                if (item.journalVoucherStatus == 'VOUCHER_ISSUED') {
                    _totalAuditAmount += toNumber(typeof item.bookingAmount != 'undefined' ? item.bookingAmount : item.subtractAmount);
                }
            }
            source.totalDebitAmount = _totalAuditAmount.toFixed(2);
            return source;
        },
        wating: function() {
            var td = $('<td colspan="1000" style="text-align: center;">');
            td.append(loadingImg.clone());
            this.$el.html(td);
            return this;
        },
        checkBill: function(e) {
            var tar = $(e.target);
            var reconcileInput = tar.parents('.record-item').find('.reconcile-money-input');
            if (!this.validateReconcileIpt(reconcileInput)) return;
            if (tar.get(0).checked) {
                this.updateTotalAmout(toNumber(reconcileInput.val()));
                reconcileInput.removeClass('default');
            } else {
                this.updateTotalAmout(-toNumber(reconcileInput.val()));
            }
        },
        updateTotalAmout: function() {
            var totalEl = this.$('.summary .money');
            var total = calculateTotalAmount.call(this);
            totalEl.html(total.toFixed(2));

            function calculateTotalAmount () {
                var total = 0;
                this.$('.record-item').each(function (index, el) {
                    var $el = $(el);
                    if (!$el.find('.check-bill').get(0).checked) return;
                    var val = toNumber($el.find('.reconcile-money-input').val());
                    if(!isNaN(val)) {
                        total += val;
                    }
                });
                return total;
            }
        },
        reconcileMoneyInputBlurHandler: function(e) {
            this.validateReconcileIpt($(e.target));
            this.updateTotalAmout();
        },
        validateReconcileIpt: function(tar) {
            var subtractValue = +tar.data('subtractvalue');
            var inputMoney = tar.val().trim();
            // if(!inputMoney) {
            //     tar.val(subtractValue.toFixed(2))
            //         .addClass('default');
            //     return true;
            // }
            inputMoney = +inputMoney;
            if (isNaN(inputMoney)) {
                this.toastErrorTip(tar, '请输入正确的金额');
                return false;
            }
            if (subtractValue < inputMoney) {
                this.toastErrorTip(tar, '对账金额不能大于默认金额');
                return false;
            }
            if (inputMoney <= 0) {
                //inputMoney = subtractValue;
                // tar.addClass('default');
                this.toastErrorTip(tar, '对账金额应大于0');
                return false;
            }
            tar.val(inputMoney.toFixed(2));
            return true;
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
        reconcileIptFocus: function(e) {
            var tar = $(e.target);
            if (tar.hasClass('default')) {
                var val = tar.val('');
                tar.removeClass('default');
            }
        },
        collectBillData: function() {
            var result = [];
            var source = this.model.toJSON().records;
            var findItem = function(cashFlowUuid) {
                for (var i = 0, len = source.length; i < len; i++) {
                    if (source[i].cashFlowUuid == cashFlowUuid) { // '1001' = 1001
                        return source[i];
                    }
                }
                return null;
            };

            this.$('.record-item').each(function(index, el) {
                var $el = $(el);
                if (!$el.find('.check-bill').get(0).checked) return;
                var recordItem = findItem($el.data('cash_flow_uuid'));
                recordItem.bookingAmount = $el.find('.reconcile-money-input').val();
                result.push(recordItem);
            });

            return {
                appId: this.appId,
                cashFlowMatchResultList: result,
                auditBillUuid: this.auditBillUuid
            };
        },
        submitBill: util.execOnceBeforeDone(function(e, recover) {
            if (this.validate()) {
                var data = this.collectBillData();
                var result = this.amoutRelationship(data.cashFlowMatchResultList);
                if(result.relationship > 0) {
                    popupTip.show('对账金额不能大于账单金额');
                    recover();
                }else if(result.relationship < 0) {
                	this.wrongDialogView.show('账单总金额'+result.amount.toFixed(2)+'元，其中'+result.sum.toFixed(2)+'元本次账单对账成功，剩余'+(result.amount - result.sum).toFixed(2)+'元未完成对账，请确认！');
                    // this.wrongDialogView.show('银行流水中'+result.sum.toFixed(2)+'元金额对账成功，还有'+(result.amount - result.sum).toFixed(2)+'元暂未完成对账，是否提交！');
                    recover();
                }else {
                    this._submitBill(data, recover);
                }
            }else {
                recover();
            }
        }),
        amoutRelationship: function (list) {
            var sum = list.reduce(function (prev, next) {
                var _prev, _next;
                if(typeof prev === 'object') _prev = prev.bookingAmount;
                else _prev = prev;
                if(typeof next === 'object') _next = next.bookingAmount;
                else _next = next;
                return toNumber(_prev) + toNumber(_next);
            }, 0);
            
            return {
                relationship: sum - this.totalRent, 
                sum: sum, 
                amount: this.totalRent
            };
        },
        _submitBill: function(data, resolve) {
            var that = this;
            var cashFlowMatchResultList = data.cashFlowMatchResultList;
            data.cashFlowMatchResultList = JSON.stringify(data.cashFlowMatchResultList);
            $.ajax({
                url: './voucher-change',
                type: 'post',
                data: data,
                dataType: 'json',
                success: function(resp) {
                    if (resp.code == 0) {
                        that.updateBillItemEl(resp.data.auditStatus);
                        that.auditStatus = resp.data.auditStatus;
                        that.succDialogView.show('对帐结果提交成功，请确认!');
                    } else {
                        popupTip.show(resp.message);
                    }
                },
                complete: function () {
                    $.isFunction(resolve) && resolve();
                }
            });
        },
        updateBillItemEl: function (status) {
            if(!this.cashFlowItemEl) return;
            var htm = '';
            htm += '<span class="reconcile-status reconcile-'+status.toLowerCase()+'">'+BillConst.RepaymentAuditStatus[status]+'</span>'
            this.cashFlowItemEl.find('.audit-cols').html(htm);
            htm = '';
            if(status == 'CREATE') {
                htm = '<a class="cancel-bill">取消</a>';
            }else {
                htm = '<a class="regulate-bill">调账</a>';
            }
            htm += '<a class="expand-bill" href=""><i class="icon icon-expand"></i></a>';
            this.cashFlowItemEl.find('.operation-cols').html(htm);
        },
        switchView: function(mode) {
            this.viewMode = mode;
            if(this.viewMode === 'match' && this.auditStatus === 'CLOSE') {
                this.$el.html('<td colspan="1000" style="text-align: center;">没有数据</td>');
                return;
            }
            this.model.fetch();
        },
        validate: function() {
            var isWrong = false;
            var that = this;
            var list = this.$('.record-item');
            if(list.length<1) return 0;
            list.each(function(index, el) {
                var $el = $(el);
                if (!$el.find('.check-bill').get(0).checked) return;
                if (!that.validateReconcileIpt($el.find('.reconcile-money-input'))) {
                    isWrong = true;
                }
            });
            return !isWrong;
        },
        addBill: function(e) {
            e.preventDefault();
            if (this.recordList.children().last().is('.blank-item')) return;
            this.recordList.append($('<tr class="blank-item">').html(templateStorage.blankBillTrTmpl));
        },
        _searchBill: function (params) {
            var that = this;
            params = $.extend({}, {appId: this.appId, auditBillUuid: this.auditBillUuid}, params);
            $.getJSON('./query-cashFlow', params, function (resp, textStatus, xhr) {
                if(resp.code == 0) {
                    var first = resp.data.cashFlowMatchResult;
                    if(first) {
                        var model = that.model;
                        if(model.exitsBillPlan(first.cashFlowUuid)) {
                            popupTip.show('流水已在列表中');
                        }else {
                            model.addBillingPlan(first);
                        }
                    }else {
                        popupTip.show('流水不存在');
                    }
                }else {
                    popupTip.show(resp.message);
                }
            });
        },
        popupSidebarBillSelectView: function (options) {
            var that  = this;
            if(that.openingSidebarView) return;
            var sidebarBillSelectView = new SidebarBillSelectView(options);
            that.openingSidebarView = true;
            sidebarBillSelectView.on('getbillingplanuuid', function (billingPlanUuid) {
                that._searchBill({billingPlanUuid: billingPlanUuid});
            }).on('removefromdomtree', function () {
                that.openingSidebarView = false;
            });
            sidebarBillSelectView.attachToBody($body).slideIn('right', 200);
        },
        getBillHandler: function (e) {
            if(~['blur', 'focusout'].indexOf(e.type) || (e.type === 'keyup' && e.keyCode === 13)) {
                var value = e.target.value.trim();
                if(!value) return;
                this._searchBill({serialNo: value});
            }
        },
        
        unbindContract: function(e) {
            e.preventDefault();
            var tar = $(e.currentTarget);
            var cashFlowUuid = tar.parents('.record-item').data('cash_flow_uuid');
            var options = {
            	auditBillUuid: this.auditBillUuid,
            	contractId: this.contractId,
            	cashFlowUuid: cashFlowUuid,
            	target: tar
            };
            this.unbindDialogView.show(options);
        },
        
        _unbindContract: function(options) {
          var that = this;
          $.post('./unbind-contract', { auditBillUuid: options.auditBillUuid, cashFlowUuid: options.cashFlowUuid, contractId: options.contractId }, function (resp) {
              resp = JSON.parse(resp);
              that.unbindDialogView.hide();
              resp.code == 0 ? that.clearRecordItem(options.target) : popupTip.show(resp.message);
          });
        },
        
        clearRecordItem: function(target) {
        	target.parents('.record-item').remove();
        },
        
        vetoCashFlow: function(e) {
        	e.preventDefault();
            var tar = $(e.currentTarget);
            var cashFlowUuid = tar.parents('.record-item').data('cash_flow_uuid');
            var options = {
            	cashFlowUuid: cashFlowUuid,
            	target: tar
            };
            this.vetoDialogView.show(options);
        },
        
        _vetoCashFlow: function(options) {
        	var that = this;
            $.post('./veto-cashFlow', { cashFlowUuid: options.cashFlowUuid }, function (resp) {
                resp = JSON.parse(resp);
                that.vetoDialogView.hide();
                resp.code == 0 ? that.clearRecordItem(options.target) : popupTip.show(resp.message);
            });
        }
    });

    module.exports = CashFlowCardView;
});