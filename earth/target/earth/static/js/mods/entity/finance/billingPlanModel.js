define(function(require, exports, module) {   
    var $ = jQuery;

    var BillingPlanModel = Backbone.Model.extend({
        actions: {
            account: global_config.root+'/billing-plan/query-by-leasing-contract',
            lease: './query-simplified-contract-info'
        },
        defaults: {
            appId: '',
            cashFlowTime: '',
            subjectMatterSourceNo: '',
            keyWord: ''
        },
        getAccountList: function (options) {
            var self = this;
            var params = this.toJSON();
           
            this.getList(this.actions.account, params, function(list){
                self.trigger('accountlist', list, options);
            });
        },
        getLeaseList:function (options){
            var self = this;
            var params = this.toJSON();

            if(options && options.isBack) {
                this.trigger('leaselist', this.cacheLeaseList);
            }else {
                this.getList(this.actions.lease, params, function(list){
                    self.trigger('leaselist', list, options);
                    self.cacheLeaseList = list;
                });
            }
        },
        getList: function (url, params, callback) {
            var self = this;

            this.trigger('request');

            $.getJSON(url, params || {}, function (resp) {
                if(resp.code != 0) {
                    self.trigger('error', resp.message, resp);
                }else {
                    callback(resp.data.list);
                }
            });
        }
    });

    module.exports = BillingPlanModel;

});