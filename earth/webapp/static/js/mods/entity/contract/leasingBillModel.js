define(function(require, exports, module) {

    var $ = jQuery;

    var BillingPlanModel = Backbone.Model.extend({
        discardAction: './batch-close-bills',
        deleteAction: './batch-delele-bills',
        modifyDiscountAction: './disconunt-update',
        defaults: {
            discountInfo: {}
        },
        initialize: function(attr, options) {
            this.isCheck = false;
            this.display = true;
        },
        isPayOff: function() {
            var receivedAmount = this.get('receivedAmount');
            var amount = this.get('amount');
            if (receivedAmount == 0 || receivedAmount === amount) return true;
            else return false;
        },
        modifyDiscount: function (attr, options) {
            var onSuccess = $.proxy(function(resp) {
                if (resp.code == 0) {
                    // this.set('discountInfo', attr);
                    this.set({
                        discountInfo: attr,
                        amount: this.get('carringAmount') - attr.amount
                    });
                    // options.success && options.success();
                }
            }, this);

            var options = {
                url: this.modifyDiscountAction,
                dataType: 'json',
                type: 'post',
                data: $.extend({billUuid: this.get('billUuid')}, attr),
                success: onSuccess
            };

            $.ajax(options);
        },
        discard: function(callback) {
            // this.set('billStatus', false);

            var onSuccess = $.proxy(function(resp) {
                if (resp.code == 0) {
                    this.set({
                        billStatusDescription: '关闭',
                        billStatus: 3
                    });
                }
                callback && callback(resp);
            }, this);

            var options = {
                url: this.discardAction,
                dataType: 'json',
                type: 'post',
                data: {
                    billingPlanUuidList: JSON.stringify([this.get('billUuid')])
                },
                success: onSuccess
            };

            $.ajax(options);
        },
        remove: function(callback) {
            var onSuccess = $.proxy(function(resp) {
                if (resp.code == 0) {
                    this.destroy();
                }
                callback && callback(resp);
            }, this);

            var options = {
                url: this.deleteAction,
                dataType: 'json',
                type: 'post',
                data: {
                    billingPlanUuidList: JSON.stringify([this.get('billUuid')])
                },
                success: onSuccess
            };

            $.ajax(options);
        },
        execCheckByItem: function(isCheck) {
            this.isCheck = isCheck;
            this.trigger('togglecheck:item', isCheck);
        },
        execCheckByAll: function(isCheck) {
            this.isCheck = isCheck;
            this.trigger('togglecheck:all', isCheck);
        },
        execDisplay: function(value) {
            this.display = value;
            this.trigger('display', value);
        }
    });

    var BillingPlanCollection = Backbone.Collection.extend({
        url: './query-stage-bill',
        deleteBatchAction: './batch-delele-bills',
        discardBatchAction: './batch-close-bills',
        model: BillingPlanModel,
        initialize: function(options) {},
        toggleCheck: function(isCheck, type) {
            this.each(function(model, index) {
                if(model.get('_uiType') === type) {
                    model.execCheckByAll(isCheck);
                }
            });
        },
        filterCheck: function(type) {
            return this.filter(function(model) {
                if(model.get('_uiType') === type) {
                    return model.isCheck;
                }
            });
        },
        isAllPayOff: function(models) {
            var flag = true;
            for (var i = 0, len = models.length; i < len; i++) {
                flag = models[i].isPayOff();
                if (!flag) break;
            }
            return flag;
        },
        discards: function(models) {
            var arr = [];
            for (var i = 0, len = models.length; i < len; i++) {
                arr.push(models[i].get('billUuid'));
            }

            var onSuccess = $.proxy(function(resp) {
                if (resp.code == 0) {
                    for(var i = 0, len = arr.length; i<len; i++) {
                        models[i].set({
                            billStatusDescription: '关闭',
                            billStatus: 3
                        });
                    }
                }
            }, this);

            var options = {
                url: this.discardBatchAction,
                dataType: 'json',
                type: 'post',
                data: {
                    billingPlanUuidList: JSON.stringify(arr)
                },
                success: onSuccess
            };

            $.ajax(options);
        },
        deletes: function (models) {
            var arr = [];
            for (var i = 0, len = models.length; i < len; i++) {
                arr.push(models[i].get('billUuid'));
            }

            var onSuccess = $.proxy(function(resp) {
                if (resp.code == 0) {
                    for(var i = 0, len = arr.length; i<len; i++) {
                        models[i].destroy();
                    }
                }
            }, this);

            var options = {
                url: this.deleteBatchAction,
                dataType: 'json',
                type: 'post',
                data: {
                    billingPlanUuidList: JSON.stringify(arr)
                },
                success: onSuccess
            };

            $.ajax(options);
        },
        parse: function(resp) {
            if (resp.code != 0) {
                console.error(resp.message);
            } else {
                this.creditStageBills = resp.data.creditStageBills.map(function(item, index) {
                    item._uiType = 'credit';
                    item._index = index;
                    return item;
                });
                this.debitStageBills = resp.data.debitStageBills.map(function(item, index) {
                    item._uiType = 'debit';
                    item._index = index;
                    return item; 
                });
                return [].concat(this.creditStageBills, this.debitStageBills);
            }
        }
    });

    exports.BillingPlanModel = BillingPlanModel;
    exports.BillingPlanCollection = BillingPlanCollection;

});