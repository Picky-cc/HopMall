define(function(require, exports, module) {
    var TableContentView = require('baseView/tableContent');
    var popupTip = require('component/popupTip');

    var B = Backbone;

    var CashBillModel = B.Model.extend({
        submit: function(attr) {
            var opt = {
                url: '',
                type: 'post',
                dataType: 'json',
                data: attr
            };

            opt.success = function(resp) {
                if (resp.code == 0) {
                    this.set(attr); // change事件
                }
            }.bind(this);

            this.trigger('request');

            $.ajax(opt);
        },
        disable: function() {
            var opt = {
                url: '',
                type: 'post',
                dataType: 'json',
                data: attr
            };

            opt.success = function(resp) {
                if (resp.code == 0) {
                    this.destroy(); // destroy事件
                }
            }.bind(this);

            this.trigger('request');

            $.ajax(opt);
        }
    });

    var CashBillCollection = B.Collection.extend({
        model: CashBillModel,
        fetch: function() {
            var opt = {
                type: 'post',
                dataType: 'json',
            };

            opt.success = function(resp) {
                if (resp.code == 0) {
                    this.reset(resp.data); // reset事件
                }
            }.bind(this);

            setTimeout(function() {
                var resp = {
                    code:0,
                    data:[{a:1},{a:2}]
                }
                opt.success(resp);
            }, 1000);
 
            this.trigger('request');

            //$.ajax(opt);
        },
        create: function(attr) {
            var opt = {
                url: '',
                type: 'post',
                dataType: 'json',
                data: attr
            };

            opt.success = function(resp) {
                if (resp.code == 0) {
                    this.bills.add(resp.data.bill); // add事件
                }
            }.bind(this);

            this.trigger('request');

            $.ajax(opt);
        }
    });

    var CashFlowAuditModel = B.Model.extend({
        initialize: function() {
            this.expanded = false;
            this.bills = new CashBillCollection();
        },
        fetchBill: function() {
            this.bills.fetch();
        },
        createBill: function(attr) {
            this.bills.create(attr);
        },
        toggle: function() {
            if (this.expanded) {
                this.collapse();
            } else {
                this.expand();
            }
        },
        expand: function() {
            this.expanded = true;
            this.trigger('expand');
            this.fetchBill();
        },
        collapse: function() {
            this.expanded = false;
            this.trigger('collapse');
        }
    });

    var CashFlowAuditCollection = B.Collection.extend({
        model: CashFlowAuditModel
    });

    exports.CashBillModel = CashBillModel;
    exports.CashBillCollection = CashBillCollection;
    exports.CashFlowAuditModel = CashFlowAuditModel;
    exports.CashFlowAuditCollection = CashFlowAuditCollection;

});