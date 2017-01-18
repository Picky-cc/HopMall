define(function(require, exports, module) {
    var TableContentView = require('baseView/tableContent');

    var ReportFormList = TableContentView.extend({
    	
    	events:{
    		'click #exportFinanceReport':'onExportFinanceReport'
    	},
    	
        refreshTableDataList: function (data, type, resp) {
            // 可由子视图继承
            if(!this.template) return;

            this.tableListEl.html(this.template(resp.data));
            this.trigger('refreshdata.tablelist');
        },
        onExportFinanceReport:function(){
        	  var reportType = $('[name = "reportType" ] ').val();
	    	  var startTime = $('[name = "startTime"]').val();
	          var endTime = $('[name = "endTime"]').val();
	    	  var paramStr ='?';
	    	  paramStr+='reportType='+reportType;
	          paramStr+='&startTime='+startTime;
	          paramStr+='&endTime='+endTime;
	          location = '../finance-report-list/export' + paramStr;
        }
    });


    module.exports = ReportFormList;

});