define(function (require, exports, module) {
	var popupTip = require('component/popupTip');
    var TableContentView = require('baseView/tableContent');

    var root = global_config.root;
    var $ = jQuery;

    var PROPERTY_STATUS = {
    	Waiting_For_Rent: '待租',
    	On_Lease: '已租'
    };
    var INVERT_PROPERTY_STATUS = _.invert(PROPERTY_STATUS);

    var INVENTORY_STATUS = {
    	'0': '未发布',
    	'1': '已发布'
    };
    var INVERT_INVENTORY_STATUS = _.invert(INVENTORY_STATUS);

    var PublishListView = TableContentView.extend({
    	actions: {
    		'publish': root + '/subject-matter/exhibit/publish',
    		'publish-batch': root+ '/subject-matter/exhibit/publish-batch',
    		'rollback': root+ '/subject-matter/exhibit/rollback',
    		'rollback-batch': root+ '/subject-matter/exhibit/rollback-batch'
    	},
		events: {
			'click #exportExcel': 'onClickExport',
			'click #rollbackBatch': 'onClickRollbackBatch',
			'click #publishBatch': 'onClickPublishBatch',
			'click .publish': 'onClickPublish',
			'click .rollback': 'onClickRollback'
		},

		// 覆盖父类
		initialize: function() {
			PublishListView.__super__.initialize.apply(this, arguments);
			this.on('checkbox.tablelist', this.onChangeCheckbox);
		},
		polish: function(list) {
			var prelongDays = function(date) {
				return Math.floor((Date.now() - date)/(1000*60*60*24));
			};

			list.forEach(function(item) {
				item.propertyStatusDesc = PROPERTY_STATUS[item.propertyStatus];
				item.inventoryStatusDesc = INVENTORY_STATUS[item.inventoryStatus];
				if(item.inventoryStatus == 1 && item.lastPublishedTime) {
					item.lastPublishedDays = prelongDays(item.lastPublishedTime);
				}else {
					item.lastPublishedDays = 0;
				}
			});

			return list;
		},

		// 其他
		canPublishBatch: function(els) {
			var fn1 = this.getInventoryStatus;
			var fn2 = this.getIsPublishInfoComplete;
			var fils = els.filter(function() {
				var inventoryStatus = fn1(this);
				var isPublishInfoComplete = fn2(this);
				return inventoryStatus == 0 && isPublishInfoComplete == 1;
			});
			return fils.length === els.length;
		},
		canRollbackBatch: function(els) {
			var fn1 = this.getInventoryStatus;
			var fils = els.filter(function() {
				var inventoryStatus = fn1(this);
				return inventoryStatus == 1;
			});
			return fils.length === els.length;
		},
		postJSON: function(actionName, data, success) {
			var self = this;
			var action = this.actions[actionName];

			var opts = {
				url: action,
				type: 'post',
				data: data
			};

			opts.success = function(resp) {
				resp = JSON.parse(resp);
				if(resp.code != 0) {
					popupTip.show(resp.message);
				}else {
					success && success(resp);
					self.refresh();
				}
			};

			$.ajax(opts);
		},

		// 事件处理器
		onChangeCheckbox: function(isChecked, el) {
			var trs = this.getCheckedItemDom();
			if(trs.length<1) {
				this.$('#publishBatch').attr('disabled', true).html('全部发布');
				this.$('#rollbackBatch').attr('disabled', true).html('全部撤回');
			}else {
				if(this.canPublishBatch(trs)) {
					this.$('#publishBatch').attr('disabled', false).html('全部发布('+trs.length+')');
				}else {
					this.$('#publishBatch').attr('disabled', true).html('全部发布');
				}

				if(this.canRollbackBatch(trs)) {
					this.$('#rollbackBatch').attr('disabled', false).html('全部撤回('+trs.length+')');
				}else {
					this.$('#rollbackBatch').attr('disabled', true).html('全部撤回');
				}
			}
		},
		onClickRollback: function(e) {
			e.preventDefault();
			var tr = this.getItemDom(e.target);
			var uuid = this.getLeasingSubjectMatterUuid(tr);
			if(uuid) {
				this.postJSON('rollback', {
					leasingSubjectMatterUuid: uuid
				}, function(resp) {
					popupTip.show('成功');
				});
			}
		},
		onClickRollbackBatch:function(e) {
			var trs = this.getCheckedItemDom(e.target);
			var uuids = this.getCheckedLeasingSubjectMatterUuid(trs);
			if(uuids && uuids.length > 0) {
				this.postJSON('rollback-batch', {
					'leasingSubjectMatterUuidListJson': JSON.stringify(uuids)
				}, function(resp) {
					popupTip.show('成功撤回 '+resp.data.success+' 条');
				});
			}
		},
		onClickPublish: function(e) {
			e.preventDefault();
			if($(e.target).is('.disabled')) return;
			var tr = this.getItemDom(e.target);
			var uuid = this.getLeasingSubjectMatterUuid(tr);
			if(uuid) {
				this.postJSON('publish', {
					leasingSubjectMatterUuid: uuid
				}, function(resp) {
					popupTip.show('成功');
				});
			}
		},
		onClickPublishBatch: function(e) {
			var trs = this.getCheckedItemDom(e.target);
			var uuids = this.getCheckedLeasingSubjectMatterUuid(trs);
			if(uuids && uuids.length > 0) {
				this.postJSON('publish-batch', {
					'leasingSubjectMatterUuidListJson': JSON.stringify(uuids)
				}, function(resp) {
					popupTip.show('成功发布 '+resp.data.success+' 条');
				});
			}
		},
		onClickExport: function(e) {
			// 合并代码后删掉
			e.preventDefault();
			var action = $(e.target).data('action');
            if(action) {
            	var query = this.collectParams();
            	var search = [];
            	for(var prop in query) {
            		var str = prop + '=' + query[prop];
            		search.push(str);
            	}
            	var url = action + '?' + search.join('&');

                var ref = window.open(url, '_download');
                if(ref) {
                    setTimeout(function() {
                        if(!ref.closed) {
                            ref.close();
                        }
                    }, 100);
                }
            }
		},

		// 获取dom
		getItemDom: function(tar) {
			return $(tar).parents('tr');
		},
		getCheckedItemDom: function() {
			return this.tableListEl.find('tbody .check-box:checked').parents('tr');
		},

		// 获取dom中的状态
		getCheckedLeasingSubjectMatterUuid: function(els) {
			var res=[];
			var fn = this.getLeasingSubjectMatterUuid;
			for(var i = 0, len = els.length; i<len; i++) {
				res.push(fn(els[i]));
			}
			return res;
		},
		getLeasingSubjectMatterUuid: function(el) {
			return $(el).data('leasingsubjectmatteruuid');
		},
		getInventoryStatus: function(el) {
			var desc = $(el).find('.inventory-status').html();
			return INVERT_INVENTORY_STATUS[desc];
		},
		getIsPublishInfoComplete: function(el) {
			return $(el).data('ispublishinfocomplete');
		}
	});

    exports.PublishListView = PublishListView;

});