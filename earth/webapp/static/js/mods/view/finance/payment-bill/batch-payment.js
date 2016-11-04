define(function(require, exports, module) {
        var TableContentView = require('baseView/tableContent');
        var popupTip = require('component/popupTip');
    
        var BatchPaymentView =  TableContentView.extend({
            events: {
                'click .apply-for-envelope': 'onApplyingForEnvelope',
                'click .enveloped-bills': 'onEnvelopedBills',
            },
            initialize: function(billEntryType) {
            	BatchPaymentView.__super__.initialize.apply(this, arguments);
            },
            onEnvelopedBills:function(e){

            	var attr = [];
            	
            	attr.push($(e.target).parent('td').attr('billing-plan-uuid'));
            	
            	var parent = this;
                
            	parent.asyncPost('../enveloped-bills',attr,function(resp) {
                	
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
            }
        });;

        exports.BatchPaymentView = BatchPaymentView;
});