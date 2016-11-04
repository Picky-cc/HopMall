define(function(require, exports, module) {
    var TableContentView = require('baseView/tableContent');
    var popupTip = require('component/popupTip');

    var $ = jQuery;

    var BillingPlanSearchView = TableContentView.extend({
        events: {
            'click .detail-order': 'onClickDetailOrder',
            'click #allExpand': 'onClickAllExpand'
        },
        orderDetailTmplFunc: _.template($('#orderDetailTmpl').html()),
        initialize: function() {
            BillingPlanSearchView.__super__.initialize.call(this);
            this.on('refreshdata.tablelist', function() {
                this.$('#allExpand').html('全部展开');
            });
        },
        onClickDetailOrder: function (e) {
            var el = $(e.target).parents('.record-item');
            var opt = this.extractOptFromDom(el);

            if(opt.billingPlanUuid == null) {
                console.error('billingPlanUuid is not exsit');
                return;
            }

            if(el.hasClass('z-active')) {
                this.hideOrderDetail(opt);
            }else {
                this.showOrderDetail(opt);
            }
        },
        onClickAllExpand: function(e) {
            var self = this;
            var target = $(e.target);

            if(target.text() === '全部展开') {
                target.text('全部收起');
                this.tableListEl.find('.record-item').each(function() {
                    var el = $(this);
                    var opt = self.extractOptFromDom(el);

                    if(opt.billingPlanUuid == null) {
                        console.error('billingPlanUuid is not exsit');
                        return;
                    }

                    if(!el.hasClass('z-active')) {
                        self.showOrderDetail(opt);
                    }
                });
            }else {
                target.text('全部展开');
                this.tableListEl.find('.record-item').each(function() {
                    var el = $(this);
                    var opt = self.extractOptFromDom(el);
                    self.hideOrderDetail(opt);
                });
            }
        },
        showOrderDetail:function(opt) {
            var self = this;

            var cb = function(data) {
                var str = self.orderDetailTmplFunc(data);
                opt.el.addClass('z-active').after(str);
                opt.el.find('.detail-order').text('收起');
            };

            this.getOrderList(opt.billingPlanUuid, cb);
        },
        hideOrderDetail: function(opt) {
            opt.el.next('.item-nest').remove();
            opt.el.removeClass('z-active');
            opt.el.find('.detail-order').text('详情');
        },
        extractOptFromDom: function(tr) {
            var res = {
                billingPlanUuid: $(tr).data('billingplanuuid'),
                el: tr
            };
            return res;
        },
        getOrderList: function (billingPlanUuid, cb) {
            var url = './voucher/query/';

            var params = {
                billingPlanUuid: billingPlanUuid
            };

            var success = function(resp) {
                if(resp.code != 0) {
                    popupTip.show(resp.message);
                }else {
                    cb(resp.data);
                }
            };

            $.getJSON(url, params, success);
        }
    });

    exports.BillingPlanSearchView = BillingPlanSearchView;

});