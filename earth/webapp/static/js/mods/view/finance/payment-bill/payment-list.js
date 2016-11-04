define(function(require, exports, module) {
        var TableContentView = require('baseView/tableContent');
        var popupTip = require('component/popupTip');
        var DialogView = require('component/dialogView');
        
        var PaymentBillView = TableContentView.extend({
            events: {
                'click .apply-for-envelope': 'onApplyingForEnvelope',
                'click .cancel-enveloped': 'onCancelEnvelopeing',
                'click .check-box': 'onClickCheckbox',
                'click .batch-envelope': 'onClickBatchEnvelop'
            },
            initialize: function(billEntryType) {
            	PaymentBillView.__super__.initialize.apply(this, arguments);
            },

            onClickCheckbox: function (e) {
                if($(e.target).is('.txt')) return;
                var checked = $(e.currentTarget).children('input').get(0).checked;
            },

            onApplyingForEnvelope:function(e){

                var attr = [];
            	
            	attr.push($(e.target).parent('td').attr('billing-plan-uuid'));
            	
            	var parent = this;
                
            	parent.asyncPost('../apply-to-enveloping-bills',attr,function(resp) {
//                	popupTip.show(resp.message);
                	parent.refreshTable();
                	
                });
            },
            onCancelEnvelopeing:function(e){

            	var attr = [];
            	
            	attr.push($(e.target).parent('td').attr('billing-plan-uuid'));
            	
            	var parent = this;
                
            	parent.asyncPost('../revoking-enveloping-bills',attr,function(resp) {
                	
//                	popupTip.show(resp.message);
                	parent.refreshTable();
                	
                });
            },
            asyncPost:function(url,attr,success_callback){
            	
            	   var opt = {
                           url: url,
                           type: 'POST',
                           dataType: 'json',
                           contentType: 'application/json',
                           data: JSON.stringify(attr)
                     };
            	   opt.success = success_callback;
            	   
            	   $.ajax(opt);
                      
            },
            refreshTable:function(){
            	this.refresh();
            },

            onClickBatchEnvelop:function(e){
                var attr = [];
                var tr = $("tbody>tr");
                for( var i=0;i<tr.length;i++){
                    if($(tr[i]).find('input').get(0).checked){
                       attr.push($(tr[i]).find('td:last').attr('billing-plan-uuid'));
                    }
                }
                if(attr.length==0){
                    popupTip.show("请选择要申请的账单");
                }else{
                    this.asyncPost('../apply-to-enveloping-bills',attr,function(resp) {
                    popupTip.show(resp.message);
                    if(resp.code=='0'){
                        setTimeout(function() {
                           location.reload();
                       }, 1500);                
                    }
                    });
                }
            }
        });

        exports.PaymentBillView = PaymentBillView;
});