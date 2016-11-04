define(function(require, exports, module) {
        var TableContentView = require('baseView/tableContent');
        var popupTip = require('component/popupTip');

        var $ = jQuery;
        var location = window.location;
        var root = global_config.root;

        var IndexRentalView = TableContentView;

        var DetailRentalView = Backbone.View.extend({
            el: '.static-info-wrapper',
            surrenderAction: '../leasing-contract/end',
            checkoutAction:'../leasing-contract/checkout',
            events: {
                'click .surrender': 'onSurrender',
                'click .checkout': 'onCheckout'
            },
            initialize: function (options) {
                this.businessContractUuid = options.businessContractUuid;
            },
            onSurrender: function (e) {
                var businessContractUuid = this.businessContractUuid;

                var onSuccess = function (resp) {
                    if(resp.code == 0) {
                        popupTip.show('退租成功');
                        popupTip.once('closedialog', function () {
                            location.reload();
                        });
                    }else {
                        popupTip.show(resp.message);
                        popupTip.once('closedialog', function () {
                            location.href = root + '/billing-plan/leasing-bill-list/index?businessContractUuid='+businessContractUuid;
                        });
                    }
                };

                var options = {
                    url: this.surrenderAction,
                    data: {
                        businessContractUuid: businessContractUuid
                    },
                    type: 'post',
                    dataType: 'json',
                    success: onSuccess
                };

                $.ajax(options);
            },
            onCheckout:function(e){

                var businessContractUuid = this.businessContractUuid;

                var onSuccess = function(resp) {

                    popupTip.show(resp.code == 0 ? '退房成功' : '退房失败');
                    popupTip.once('closedialog', function() {
                        location.reload();
                    });
                };
                var options = {
                    url: this.checkoutAction,
                    data: {
                        businessContractUuid: businessContractUuid
                    },
                    type: 'post',
                    dataType: 'json',
                    success: onSuccess
                };
                $.ajax(options);
            }
        });

        exports.IndexRentalView = IndexRentalView;
        exports.DetailRentalView = DetailRentalView;
});