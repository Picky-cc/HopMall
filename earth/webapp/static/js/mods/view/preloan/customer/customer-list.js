define(function (require, exports, module) {
    var TableContentView = require('baseView/tableContent');
    var path = require('scaffold/path');
    var popupTip = require('component/popupTip');
    var baseFormView = require('baseView/baseFormView');
    var FormDialogView = baseFormView.FormDialogView;
    var DialogView = require('component/dialogView');

    var $ = jQuery;
    var root = global_config.root;
    var delete_action = root + '/preloan/customers/delete';
    var submit_action = root + '/preloan/customers/apply-project';

	var CustomerListView = TableContentView.extend({
        events: {
            'click .delete': 'onClickDelete',
            'click #submitproject': 'onClickSubmit'
        },
        initialize: function() {
            CustomerListView.__super__.initialize.apply(this, arguments);
            
            this.confirmDeleteDialog = new DialogView({
                title:'删除信息',
                bodyInnerTxt: '客户信息一旦删除将不再恢复,是否继续?'
            });

            this.listenTo(this.confirmDeleteDialog , 'goahead',function (opts) {
                $.ajax(opts);
                this.confirmDeleteDialog.hide();
            });
            
            this.applyProjectDialog = new DialogView({
                title:'提交立项',
                bodyInnerTxt: '对选中客户进行批量提交立项，是否继续?'
            });

            this.listenTo(this.applyProjectDialog , 'goahead',function (opts) {
                $.ajax(opts);
                this.applyProjectDialog.hide();
            });
        },
        getStatus: function(trEl) {
            return trEl.find('.status').text();
        },
        getCustomerUuid: function(trEl) {
            return trEl.data('customer-uuid');
        },
        getItemElement: function(target) {
            return $(target).parents('tr');
        },
        onClickDelete: function(e) {
            e.preventDefault();

            var self = this;
            var $tr = this.getItemElement(e.target);
            var status = this.getStatus($tr);
            var customerUuid = this.getCustomerUuid($tr);

            if(status == "等待提交"){
                var opts = {
                    url: delete_action,
                    type: 'post',
                    data: {
                        customerUuid: customerUuid
                    },
                    dataType: 'json'
                };

                opts.success = function(resp) {
                    if (resp.code == 0){
                        self.refresh();
                    }else {
                        popupTip.show(resp.message);
                    }
                };

                this.confirmDeleteDialog.show(opts);
            }
            
            
        },
        getCheckedCustomerUuid: function() {
            var res = [];
            var selecteds = this.$('tbody tr .check-box:checked');
            for (var i = 0; i < selecteds.length; i++) {
                var now =  selecteds.eq(i);                
                var $tr = this.getItemElement(now);
                res[i] = this.getCustomerUuid($tr);
            }
            return res;
        },
        onClickSubmit: function (e) {
            var self = this;
            var arr = this.getCheckedCustomerUuid();
            
            var opts = {
                url: submit_action,
                type: 'post',
                data: {
                    'customerUuids': '' + arr
                },
                dataType: 'json'
            };

            opts.success = function(resp) {
                if (resp.code == 0){
                    self.refresh();
                }else {
                    popupTip.show(resp.message);
                }
            };

            if(arr.length == 0){
                popupTip.show("请选择要提交的选项");
            }else{
            	this.applyProjectDialog.show(opts);
            }
                
        },
        polish: function(list) {
            var STATUS_MAP = {
                0: '等待提交',
                1: '等待审核',
                2: '立项成功',
                3: '立项失败'
            };
            var res=list.map(function(value, index) {
                value.statusDesc = STATUS_MAP[value.status] || '未定义';
                return value;
            });
            return res;
        },
        collectSortParams: function(params) {
            var res = {};
            var orders = this.tableListEl.find('thead .sort');
            for(var i=0, len=orders.length; i<len; i++) {
                var tar = orders.eq(i);
                var name = tar.data('paramname');
                var value = tar.hasClass('desc') ? 'DESC' : (tar.hasClass('asc') ? 'ASC' : '');
                if(name && value) {
                    res.sortField = name;
                    res.isAsc = value === 'ASC';
                    break;
                }
            }

            $.extend(params, res);
            return params;
        }
    });


    module.exports = CustomerListView;

});