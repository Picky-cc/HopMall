define(function(require, exports, module) {
	var TableContentView = require('baseView/tableContent');
	var DialogView = require('component/dialogView');
	var popupTip = require('component/popupTip');

	var BillDetailView = Backbone.View.extend({
		el: $('.bill-detail'),
		events: {
			'click .discard-bill': 'onDiscard'
		},
		initialize: function() {
			this.dialogView = new DialogView();
			var parent = this;
			this.dialogView.on('goahead', function(billingPlanUuid) {
				parent.dialogView.hide();
				parent.doDiscard(billingPlanUuid);
			});
		},
		onDiscard: function(e) {
			e.preventDefault();
			var settlingstatus = $('.discard-bill').attr('settlingstatus');
			var bilingplanuuid = $('.discard-bill').attr('billingplanuuid');
			var isPayOff = settlingstatus == '2' || settlingstatus == '3';
			if (!isPayOff) {
				this.dialogView.show('该账单未付清，一旦关闭将不能恢复，是否继续？', bilingplanuuid);
			} else {
				this.dialogView.show('账单一旦关闭将不能恢复，是否继续？', bilingplanuuid);
			}
		},
		doDiscard: function(billingPlanUuid) {

			var options = {
				url: '../leasing-bill-list//batch-close-bills',
				dataType: 'json',
				type: 'post',
				data: {
					billingPlanUuidList: JSON.stringify([billingPlanUuid])
				},
				success: function(resp) {
					if(resp.code == 0){
						var message = resp.data.result ? '关闭订单成功！':'关闭订单失败！';
						popupTip.show(message);
						$('.discard-bill').addClass('disabled');
					}else{//exception 
						popupTip.show('系统异常，请稍候再试！');
					}
				
				}
			};

			$.ajax(options);
		}
	});

	exports.BillOverviewView=TableContentView;
    exports.BillDetailView=BillDetailView;
    exports.LeasingBillListView=TableContentView;
	
});