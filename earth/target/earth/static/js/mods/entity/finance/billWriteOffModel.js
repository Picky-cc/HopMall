define(function(require, exports, module) {
    var root = global_config.root;
    
    var BillWriteOffModel = Backbone.Model.extend({
        urlRoot: './match/',
        actions: {
            cancel: './close-bill',
            submit: './do-offset',
            log: './detail',
            refresh: root + '/finance/payment-bill-list/query'
        },
        defaults: {
            linkedContractUuid: '',
            log: {}, // 存储日志信息
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
                    records: resp.data.list
                };
                return res;
            }
        },
        setMode: function(type) {
            this.set('mode', type, {silent: true});
        },
        toggle: function(options) {
            if(this.isShow) {
                this.foldIn(options);
            }else {
                this.setMode(this.get('writeOff') ? 'match' : 'seem');
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
                    billEntryType: this.get('billEntryType'),
                    linkedContractUuid: this.get('linkedContractUuid'),
                    writeOffMode: this.get('writeOffMode')
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
                    billingPlanUuid: this.get('billingPlanUuid')
                }
            };

            opt.success = function(resp) {
                self.trigger('cancel', resp);
                if(resp.code == 0) {
                    self.destroy();
                }
            };

            $.ajax(opt);
        },
        refresh: function() {
            var self = this;
            var opt = {
                url: this.actions.refresh,
                dataType: 'json',
                data: {
                    billingPlanUuid: this.get('billingPlanUuid')
                }
            };

            opt.success = function(resp) {
                if(resp.code == 0) {
                    var attr = resp.data.list[0];
                    attr && self.set(attr);
                }
                self.trigger('refresh', resp);
            };

            $.ajax(opt);
        },
        validateBill: function(checkedBills) {
            var totalOffSetAmount = checkedBills.reduce(function(prev, next) {
                var _prev, _next;
                if(typeof prev === 'object') _prev = prev.offSetAmount;
                else _prev = prev;
                if(typeof next === 'object') _next = next.offSetAmount;
                else _next = next;
                return (+_prev) + (+_next);
            }, 0);

            var amount = this.get('amount');
            var canTotalWriteOffAmount = this.get('canTotalWriteOffAmount');
            var detla = totalOffSetAmount - canTotalWriteOffAmount;

            if(detla > 0) {
                this.trigger('totaloffSetAmount:invalid', '冲销金额不能大于流水金额', checkedBills);
                return false;
            }else {
                return true;
            }
        },
        submitBill: function(checkedBills, uncheckedBills) {
            var self = this;
            var billingPlanUuid = this.get('billingPlanUuid');
            var records = this.get('records');
            var res = {
                redBlackBillingUuidPair: {},
                redBlackOffSetAmountMap: {},
                unWriteOff: {}
            };

            res.unWriteOff[billingPlanUuid] = uncheckedBills;

            var sumWriteOffAmount = 0;
            var pair = [];
            var map = {};

            for(var i = 0, len = checkedBills.length; i<len; i++) {
                var item = checkedBills[i];
                pair.push(item.billingPlanUuid);
                map[item.billingPlanUuid] = item.offSetAmount;
                sumWriteOffAmount += +item.offSetAmount;
            }

            if(pair.length > 0) {
                res.redBlackBillingUuidPair[billingPlanUuid] = pair;
            }else {
                res.redBlackBillingUuidPair = null;                
            }

            if(!$.isEmptyObject(map)) {
                map[billingPlanUuid] = sumWriteOffAmount;
                res.redBlackOffSetAmountMap = map;
            }else {
                res.redBlackOffSetAmountMap = null;
            }

            var opt = {
                url: this.actions.submit,
                type: 'post',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(res)
            };

            opt.success = function(resp) {
                self.trigger('bill:submit', resp);
            };

            $.ajax(opt);
        },
        getLog: function() {
            return this.get('log');
        },
        fetchLog: function(cb) {
            var self = this;
            var opt = {
                url: this.actions.log,
                type: 'get',
                dataType: 'json',
                data: {
                    billingPlanUuid: this.get('billingPlanUuid')
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

    var BillWriteOffCollection = Backbone.Collection.extend({
        model: BillWriteOffModel,
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
                model.setMode(model.get('writeOff') ? 'match' : 'seem');
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
            BillWriteOffCollection.__super__.reset.apply(this, arguments);
        }
    });

    exports.BillWriteOffModel = BillWriteOffModel;
    exports.BillWriteOffCollection = BillWriteOffCollection;

});
