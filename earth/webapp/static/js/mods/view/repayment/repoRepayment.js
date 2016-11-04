define(function(require, exports, module) {
    var TableContentView = require('baseView/tableContent');
    var DialogView = require('component/dialogView');
    var popupTip = require('component/popupTip');
    var util = require('scaffold/util');
    //var SidebarBillSelectView = require('./sidebarBillSelect');
    
    
//    var templateStorage = function () {
//        var obj = {};
//
////        var billCardItemTmplEl = $('#billCardItemTmpl');
////        var _fillBillTrTmpl = billCardItemTmplEl.html();
////        obj.fillBillTrTmplFunc = _.template(_fillBillTrTmpl);
//
//        //var tdNum = $(_fillBillTrTmpl).find('td').length;
//        var _blankBillTrTmpl = [
//            "<td></td>",
//            "<td><input type='text' placeholder='请输入租约编号...' class='bill-number'></td>",
//            "<td><input type='text' placeholder='' class='bill-name'></td>",
//            "<td><input type='text' placeholder='' class='house-number'></td>",
//            "<td><a href='javascript:void(0)' class=''>删除</a></td>"
//            //util.copyStringWithNum(tdNum-3, '<td></td>')
//        ].join("");
//        obj.blankBillTrTmpl = _.template(_blankBillTrTmpl)();
//
//        //obj.billCardTmpl = $('#billCardTmpl').html() || '';
//
//        return obj;
//    }($);

    var RepoRepaymentView = TableContentView.extend({
        events: {
        	'click .add-bill': 'addBill',
        	'click .repo-delete': 'deleteRepo',
            'click .submit': 'submitBill',
            //'click .check-bill': 'checkBill',
            //'blur .reconcile-money-input': 'reconcileMoneyInputBlurHandler',
            //'focus .reconcile-money-input': 'reconcileIptFocus',

            'keyup .contract-number': 'getBillHandler',
            'blur .contract-number': 'getBillHandler',
            'keyup .repo-amount': 'validateAmount',
            'blur .repo-amount': 'validateAmount',
            'change select': '_queryByParams',
        },
        //template: _.template(templateStorage.billCardTmpl),
        initialize: function() {
            //$.extend(this, _.pick(options, ['appId', 'cashFlowUuid', 'amount', 'billItemEl', 'auditStatus', 'cashFlowTime']));
            //this.amount = toNumber(this.amount.replace(/,/g, ''));
        	this.recordList = this.$('.record-list');
            //this.initModel();
            this.initDialogView();
            //this.switchView(options.mode);

            // btnOnceClickProxy.proxy(this.submitBill, this._submitBill, this);
        },
//        initModel: function () {
//            //this.model = new BillCardModel({ cashFlowUuid: options.cashFlowUuid, appId: options.appId }, options);
//            //this.listenTo(this.model, 'sync', this.render);
//            //this.listenTo(this.model, 'add:billingplan', this.addBillingPlanHandler);
//        },
        addBill: function(e) {
            e.preventDefault();
            var nextNo = 1;
            if (this.recordList.children().last().is('.blank-item')) {
            	nextNo = Number(this.recordList.children().last().children().first().html()) + 1;
            }
            var blankBillTrTmpl = [
                                     "<td>"+nextNo+"</td>",
                                     "<td><input type='text' placeholder='请输入租约编号...' class='contract-number'></td>",
                                     "<td><input type='text' placeholder='' class='app-name' disabled='disabled'></td>",
                                     "<td><input type='text' placeholder='' class='house-number' disabled='disabled'></td>",
                                     "<td><input type='text' placeholder='' class='repo-amount'></td>",
                                     "<td><a href='javascript:void(0)' class='repo-delete'>删除</a></td>"
                                 ].join("");
            
            this.recordList.append($('<tr class="blank-item">').html(blankBillTrTmpl));
        },
        deleteRepo: function(e) {
        	var tar = $(e.target);
        	tar.parents('.blank-item').remove();
        	this.updateTotalAmout();
        },
        getBillHandler: function (e) {
            if(~['blur', 'focusout'].indexOf(e.type) || (e.type === 'keyup' && e.keyCode === 13)) {
                var value = e.target.value.trim();
                if(!value) return;
                this._searchBill({contractNo: value, tar:e.target});
            }
        },
        _searchBill: function (params) {
            var that = this;
            var tar = params.tar;
            params = {contractNo: params.contractNo};
            $.getJSON('./query-contract', params, function (resp, textStatus, xhr) {
                if(resp.code == 0) {
                	var contract = resp.data.contract;
                    //var first = resp.data.billMatchResultList[0];
                    if(contract) {
                    	that.addContractHandler({contract: contract, tar: tar});
                    }else {
                        popupTip.show('合同不存在');
                        $(tar).val('');
                    }
                }else {
                    popupTip.show(resp.message);
                    $(tar).val('');
                }
            });
        },
        addContractHandler: function (parms) {
        	var tar = $(parms.tar);
        	tar.attr('contractid',parms.contract.id);
        	tar.parents('.blank-item').find('.app-name').val(parms.contract.app.name);
        	tar.parents('.blank-item').find('.house-number').val(parms.contract.house.community);
        },
        validateAmount: function (e) {
        	//var that = this;
        	if(~['blur', 'focusout'].indexOf(e.type) || (e.type === 'keyup' && e.keyCode === 13)) {
        		var value = e.target.value.trim();
            	var regular = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
            	if(!value) {
            		return;
            	}
            	if(!regular.test(value)){
            		popupTip.show('请正确填写回购金额！');
            		$(e.target).val('');
            		this.updateTotalAmout();
            		return;
    			}
            	this.updateTotalAmout();
        	}
        },
        addBillingPlanHandler: function (attr, model) {
            this._processOneBillItemData(attr);
            this.recordList
                .find('.blank-item')
                .replaceWith(templateStorage.fillBillTrTmplFunc({
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
                //that.switchView('match');
                succDialogV.hide();
            });
        },
//        render: function() {
//            var str = this.template(this.compactData(this.model.toJSON()));
//            this.$el.html(str);
//            this.recordList = this.$('.record-list');
//            return this;
//        },
        _processOneBillItemData: function (item) {
            var processSubtractAmout = function (item) {
                var result;
                result = item.receivableAmount - (this.viewMode === 'match' ? item.settlementAmount : item.currentSpecificAmount);
                isNumber(result) || (result = item.receivableAmount);
                item.subtractAmount = Math.min(result, this.amount); // 取两者的最小值
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
        compactData: function(source) {
            source.mode = this.viewMode;
            var records = source.records;
            var item;
            var _totalDebitAmount = 0;
            for (var i = 0, len1 = records.length; i < len1; i++) {
                item = records[i];
                this._processOneBillItemData(item);
                // 合计对账金额
                if (item.journalVoucherStatus == 'VOUCHER_ISSUED') {
                    _totalDebitAmount += toNumber(typeof item.bookingAmount != 'undefined' ? item.bookingAmount : item.subtractAmount);
                }
            }
            source.totalDebitAmount = _totalDebitAmount.toFixed(2);
            return source;
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
                this.$('.blank-item').each(function (index, el) {
                    var $el = $(el);
                    //if (!$el.find('.check-bill').get(0).checked) return;
                    var val = Number($el.find('.repo-amount').val());
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
            //var subtractValue = +tar.data('subtractvalue');
        	var no = tar.children().first().html();
        	var contractNo = tar.find('.contract-number').val().trim();
        	var appName = tar.find('.app-name').val().trim();
        	var houseNo = tar.find('.house-number').val().trim();
            var inputMoney = tar.find('.repo-amount').val().trim();
            // if(!inputMoney) {
            //     tar.val(subtractValue.toFixed(2))
            //         .addClass('default');
            //     return true;
            // }
            if(!contractNo || !appName || !houseNo) {
            	popupTip.show('请正确输入'+ no +'号租约');
            	//this.toastErrorTip(tar, '请正确输入'+ no +'号租约');
                return false;
            }
            inputMoney = +inputMoney;
            if (isNaN(inputMoney)) {
            	popupTip.show('请正确输入'+ no +'号租约的回购金额');
                //this.toastErrorTip(tar, '请正确输入'+ no +'号租约的回购金额');
                return false;
            }
            //tar.val(inputMoney.toFixed(2));
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
        	var repoMode = this.$('#repoMode').val();
        	var totalAmount = Number(this.$('.summary .money').html());
        	var repoTime = this.$('.datetimepicker-form-control').val();
        	
        	var list = this.$('.blank-item');
        	var contractIds = '';
        	var amounts = '';
            list.each(function(index, el) {
                var tar = $(el);
                
                contractIds += tar.find('.contract-number').attr('contractid') + ',';
                amounts += tar.find('.repo-amount').val().trim() + ',';
            });

            return {
            	repoMode: repoMode,
            	totalAmount: totalAmount,
            	repoTime: repoTime,
            	contractIds: contractIds,
            	amounts: amounts
            };
        },
        submitBill: util.execOnceBeforeDone(function(e, recover) {
            if (this.validate()) {

            	var totalAmount = Number(this.$('.summary .money').html());
            	var repoTime = this.$('.datetimepicker-form-control').val();
            	
                this.wrongDialogView.show('回购总金额' + totalAmount + '元，回购时间' + repoTime + '，请确认');
                recover();
                
            } else {
                recover();
            }
        }),
        validate: function() {
            var isWrong = false;
            var that = this;
            var list = this.$('.blank-item');
            if(list.length<1) return 0;
            list.each(function(index, el) {
                var $el = $(el);
                if (!that.validateReconcileIpt($el)) {
                	return false;
                    //isWrong = true;
                }
            });
            var totalAmount = Number(this.$('.summary .money').html());
        	if(totalAmount <= 0) {
        		popupTip.show('请完整添加一条回购记录');
        		return false;
        	}
        	var repoMode = this.$('#repoMode').val();
        	if(!repoMode) {
        		popupTip.show('请选择回购模式');
        		return false;
        	}
        	var repoTime = this.$('.datetimepicker-form-control').val();
        	if(!repoTime) {
        		popupTip.show('请填写回购时间');
        		return false;
        	}
            return !isWrong;
        },
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
                relationship: sum - this.amount, 
                sum: sum, 
                amount: this.amount
            };
        },
        _submitBill: function(data, resolve) {
            var that = this;
            //var billMatchResultList = data.billMatchResultList;
            //data.billMatchResultList = JSON.stringify(data.billMatchResultList);
            $.ajax({
                url: './repo-repay-apply',
                type: 'post',
                data: data,
                dataType: 'json',
                success: function(resp) {
                    if (resp.code == 0) {
                        //that.updateBillItemEl(resp.data.auditStatus);
                        //that.auditStatus = resp.data.auditStatus;
                        that.succDialogView.show('回购回款提交成功!');
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
            if(!this.billItemEl) return;
            var htm = '';
            htm += '<span class="reconcile-status reconcile-'+status.toLowerCase()+'">'+DebitCashFlowConst.AuditStatus[status]+'</span>'
            this.billItemEl.find('.audit-cols').html(htm);
            htm = '';
            if(status == 'CREATE') {
                htm = '<a class="cancel-bill">取消</a>';
            }else {
                htm = '<a class="regulate-bill">调账</a>';
            }
            htm += '<a class="detail-bill">详情</a>';
            this.billItemEl.find('.operation-cols').html(htm);
        },
//        switchView: function(mode) {
//            this.viewMode = mode;
//            if(this.viewMode === 'match' && this.auditStatus === 'CLOSE') {
//                this.$el.html('<td colspan="1000" style="text-align: center;">没有数据</td>');
//                return;
//            }
//            this.model.fetch();
//        },
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
        _queryByParams: function () {
            return;
        },
    });

    exports.RepoRepaymentView = RepoRepaymentView;

});