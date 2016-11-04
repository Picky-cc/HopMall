define(function(require, exports, module) {
    var root = global_config.root;
    
    var CashflowModel = Backbone.Model.extend({
        idAttribute: 'cashFlowUid',
        urlRoot: './list-billMatchResult',
        actions: {
            cancel: './close-cashflow-audit',
            search: './query-billingPlan',
            submit: './voucher-change',
            mark: root + '/cashflow/journal/',
            log: './show-cash-detail'
        },
        defaults: {
            cashFlowUid: '',
            app: {},
            log: {}, // 存储日志信息
            markInfo: {}, // 存储标记信息，只有fetchMarkInfo之后才有值
            records: [] // 存储账单列表
        },
        initialize: function() {
            this.isShow = false;
        },
        parse: function(resp) {
            if (resp.code != 0) {
                popupTip.show(resp.message);
            } else {
                var res = {
                    records: resp.data.billMatchResultList
                };
                return res;
            }
        },
        getAppId: function() {
            return this.get('app').appId;
        },
        setMode: function(type) {
            this.set('mode', type, {silent: true});
        },
        toggle: function(options) {
            if(this.isShow) {
                this.foldIn(options);
            }else {
                this.setMode(this.get('auditStatus') === 'CREATE' ? 'seem' : 'match');
                this.foldOut(options);
            }
        },
        foldOut: function(options) {
            options || (options = {});
            this.isShow = true;
            if(!options.silent) {
                this.trigger('foldout');
            }
            this.fetch({
                data: {
                    accountSide: this.get('accountSide')
                }
            }, options);
        },
        foldIn: function(options) {
            options || (options = {});
            this.isShow = false;
            if(!options.silent) {
                this.trigger('foldin');
            }
        },
        regulate: function() {
            this.setMode('seem');
            this.foldOut();
        },
        cancel: function() {
            var self = this;
            var opt = {
                url: this.actions.cancel,
                type: 'post',
                dataType: 'json',
                data: {
                    cashFlowUuid: this.get('cashFlowUid'),
                    accountSide: this.get('accountSide')
                }
            };

            opt.success = function(resp) {
                if(resp.code == 0) {
                    self.set('auditStatus', 'CLOSE');
                }
                self.trigger('cancel', resp);
            };

            $.ajax(opt);
        },

        searchBill: function(params) {
            var self = this;
            var opt = {
                url: this.actions.search,
                type: 'get',
                dataType: 'json'
            };

            opt.data = $.extend({}, {
                appId: this.getAppId(),
                accountSide: this.get('accountSide')
            }, params);

            opt.success = function(resp) {
                var bill = null;
                if(resp.code == 0) {
                    bill = resp.data.billMatchResultList[0];
                }
                self.trigger('bill:search', bill, resp);
            };

            $.ajax(opt);
        },
        existBill: function(billingPlanUuid) {
            var existBill = _.findWhere(this.get('records'), {billingPlanUuid: billingPlanUuid});
            return existBill ? true : false;
        },
        addBill: function(attr) {
            var records = this.get('records');
            records.push(attr);
            this.set('records', records);
            this.trigger('bill:add', $.extend({}, attr));
        },
        validateBill: function(bills) {
            var totalBookingAmount = bills.reduce(function(prev, next) {
                var _prev, _next;
                if(typeof prev === 'object') _prev = prev.bookingAmount;
                else _prev = prev;
                if(typeof next === 'object') _next = next.bookingAmount;
                else _next = next;
                return (+_prev) + (+_next);
            }, 0);

            var cashflowAmount = this.get('amount');
            var detla = totalBookingAmount - cashflowAmount;

            if(detla > 0) {
                this.trigger('totalbookingAmount:invalid', '对账金额不能大于流水金额', bills);
                return false;
            }else {
                return true;
            }
        },
        submitBill: function(bills) {
            var self = this;
            var records = this.get('records');
            var res = [];

            for(var i = 0, len = bills.length; i<len; i++) {
                var item = _.findWhere(records, {billingPlanUuid: bills[i].billingPlanUuid});
                item.bookingAmount = bills[i].bookingAmount;
                res.push(item);
            }

            var opt = {
                url: this.actions.submit,
                type: 'post',
                dataType: 'json',
                data: {
                    appId: this.getAppId(),
                    billMatchResultList: JSON.stringify(res),
                    cashFlowUuid: this.get('cashFlowUid'),
                    accountSide: this.get('accountSide')
                }
            };

            opt.success = function(resp) {
                if(resp.code == 0) {
                    self.set('auditStatus', resp.data.auditStatus);
                }
                self.trigger('bill:submit', resp);
            };

            $.ajax(opt);
        },

        getMarkTypes: function() {
            var val = $('#chartOfAccount').val();
            var obj = JSON.parse(val);
            return obj;
        },
        fetchMarkInfo: function(cb) {
            var self = this;
            var opt = {
                url: this.actions.mark,
                type: 'get',
                dataType: 'json',
                data: {
                    cashFlowUuid: this.get('cashFlowUid')
                }
            };

            opt.success = function(resp) {
                if(resp.code == 0) {
                    self.set('markInfo', resp.data.cashFlowJournal, {silent: true});
                }
                cb && cb(resp, self);
            };

            $.ajax(opt);
        },
        saveMarkInfo: function(attr, cb) {
            var self = this;
            var opt = {
                url: this.actions.mark,
                type: 'post',
                dataType: 'json',
                data: {
                    cashFlowUuid: this.get('cashFlowUid'),
                    cashFlowJournal: attr.journal,
                    financialAccountName: attr.financialAccountName,
                    lapse: attr.lapse
                }
            };

            opt.success = function(resp) {
                if(resp.code == 0) {
                    self.set('markInfo', attr);
                }
                cb && cb(resp, self);
            };

            $.ajax(opt);
        },

        getLog: function() {
            var attr = this.get('log');

            var statusMap = {
                CREATE: '待对账',
                ISSUING: '异常',
                ISSUED: '成功',
                CLOSE: '失败'
            };

            if(attr.cashFlowDetail.auditStatus === 'CREATE') {
                attr.cashFlowDetail.billTypeDesc = '疑似';
            }else {
                attr.cashFlowDetail.billTypeDesc = '匹配';
            }

            attr.cashFlowDetail.auditStatusDesc = statusMap[attr.cashFlowDetail.auditStatus];

            return attr;
        },
        fetchLog: function(cb) {
            var self = this;
            var opt = {
                url: this.actions.log,
                type: 'get',
                dataType: 'json',
                data: {
                    cashFlowUuid: this.get('cashFlowUid'),
                    accountSide: this.get('accountSide')
                }
            };

            opt.success = function(resp) {
                if(resp.code == 0) {
                    self.set('log', resp.data, {silent: true});
                }
                cb && cb(resp, self);
            };

            $.ajax(opt);
        }
    });

    var CashflowCollection = Backbone.Collection.extend({
        model: CashflowModel,
        initialize: function() {
            this.isAllShow = false;
        },
        toggle: function(options) {
            if(this.isAllShow) {
                this.foldIn(options);
            }else {
                this.foldOut(options);
            }
        },
        foldOut: function(options) {
            options || (options = {});
            this.isAllShow = true;
            this.forEach(function(model) {
                model.setMode(model.get('auditStatus') === 'CREATE' ? 'seem' : 'match');
                model.foldOut(options);
            });
            if(!options.silent) {
                this.trigger('foldout:all');
            }
        },
        foldIn: function(options) {
            options || (options = {});
            this.isAllShow = false;
            this.forEach(function(model) {
                model.foldIn(options);
            });
            if(!options.silent) {
                this.trigger('foldin:all');
            }
        },
        reset: function() {
            this.isAllShow = false;
            CashflowCollection.__super__.reset.apply(this, arguments);
        },
        getMarkTypes: function() {
            var model = new CashflowModel();
            return model.getMarkTypes();
        }
    });

    exports.CashflowModel = CashflowModel;
    exports.CashflowCollection = CashflowCollection;

});
