define(function (require, exports, module) {
    var TableContentView = require('baseView/tableContent');
    var MyMock = require('scaffold/mymock');
    var cashManipulateLog = require('./cashManipulateLog');
    var RespJSON = MyMock.RespJSON;

    
    RespJSON.route({
        
        '/property-publish/search': {
            methodName: '',
            request: {
                type: 'get'
            },
            response: {
                code: 0,
                data: {
                    list:[{
		            	roomNo: '10010',
			            propertName: '杭州',
			            releaseStatus: '1',
			            propertyStatus:'2',
			            rentalContractMaturityDate:'2016-02-02',
			            releaseDays: '90'
		            }, 5]
                }
            }
        }
       });

    MyMock.RespJSON.start({root: global_config.root});
    MyMock.Mock().proxy(jQuery);


	var PublishListView = TableContentView.extend({
		events: {
			'click #exportExcel': 'onClickExport',
			'click #submitproject': 'onClickSubmit',
			'click .detail-bill': 'detailBill'
		},
		onClickExport:function(e) {

		},
		onClickSubmit:function(e) {

		},
		detailBill:function(e) {
				
		}
	});

    exports.PublishListView = PublishListView;

});