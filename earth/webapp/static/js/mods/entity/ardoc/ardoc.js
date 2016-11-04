
define(function (require, exports, module) {
    var path=require('scaffold/path');

    var validateHelper=global_validateHelper;
    var root = global_config.root;

    // AR文档模型
    var tag=true;
    var ARRecordModel = Backbone.Model.extend({
        urlRoot: './', // /earth/loan/projects|audit/10000001/10001/
        initialize: function (attr, options) {
            options || (options={});
            this.structure = options.structure || null;
            // if(options.projectId && options.batchId) {
            //     var oldurlroot = this.urlRoot;
            //     this.urlRoot = path.join(oldurlroot, options.projectId, options.batchId);
            // }
            // if(options.action) {
            //     this.urlRoot = options.action;
            // }
        },
        validate: function(attr) {
            var structure,
                blockStructure,
                blockData,
                fieldStructure,
                fieldData;
            if(!(structure = this.structure)) return;
            for(var blockProp in attr) {
                blockData = attr[blockProp];
                blockStructure = structure.getBlockStructure(blockProp);
                if(typeof blockData !== 'object' || !blockStructure) {
                    console.log('有未验证的区块：'+blockProp);
                    continue;
                }
                for(var fieldProp in blockData) {
                    fieldData=blockData[fieldProp];
                    fieldStructure = blockStructure.getColumn({interalName: fieldProp}); // 获取列的定义
                    if(!fieldStructure || fieldStructure.length<1) {
                        console.log(blockProp+'块中有未验证的字段：'+fieldProp);
                        continue;
                    }
                    fieldStructure = fieldStructure[0];
                    if(validateHelper.validate(fieldData, fieldStructure.dataType) !== true) 
                        return 'error';
                }
            }
        },
        // sync: function(method, model, options) {
        //     model.trigger('request', model, null, options);
        //     // console.dir(options);
        //     setTimeout(function() {
        //         options.success(getExcelData(tag ? 0 : 1));
        //         tag=!tag;
        //     }, 800);
        // },
        extractDataToColumns: function () {
            var obj=this.toJSON();
            var res={},
                item;
            for(var i in obj) {
                item=obj[i];
                if(_.isString(item))
                    res[i]=item;
                else if(_.isObject(item))
                    _.defaults(res, item);
            }
            return res;
        },
        parse:function (resp) {
            if(resp.data) {
                return resp.data;
            }else {
                return resp;
            }
        }
    });

    // AR文档集合
    // 没用到了
    var ARRecordCollection = Backbone.Collection.extend({
        model: ARRecordModel,
        initialize: function(models, options){
            // 这两个参数不需要了。。fetch是通过分页控件拿到再reset的
            this.taskId=options.taskId;
            this.projectId=options.projectId;

            // this.on('remove', this.removeHandler);
        },
        url: function () {
            return path.join(root, 'ar/data', this.projectId, this.taskId);
        },
        // 把区都拆分成列
        extractDataToColumns: function () {
            var cols  = _.map(this.models, function (model) {
                return model.extractDataToColumns();
            });
            return cols;
        },
        removeHandler: function () {
            
        }
    });

    exports.ARRecordModel=ARRecordModel;
    exports.ARRecordCollection=ARRecordCollection;


    // function getExcelData(index) {
    //     var res = {
    //         "id": "1000001234234",
    //         "houseInfo": {
    //             "contranct": "第一批",
    //             "contranctNo": "",
    //             "roomId": "2",
    //             "roomNo": "汇宁花园3#1204",
    //             "addr": "淮海西路183弄3号1204",
    //             "date": "8/28/15"
    //         },
    //         "baoZuContract": {
    //             "monthRent": "15,000.00",
    //             "startDate": "2014.07.01",
    //             "endDate": "2022.06.30",
    //             "renter": "毛晓军"
    //         },
    //         "zulinContract": {
    //             "rentOwner": "上海源涞实业有限公司",
    //             "renter": "郑江昆（Johannes Kieven）",
    //             "startDate": "2015.08.01",
    //             "endDate": "2017.07.31",
    //             "rentalMoney": "22,500.00",
    //             "payDistance": "押2付1",
    //             "payDate": "1",
    //             "contractDeposit": "45,000.00",
    //             "payer": "郑江昆",
    //             "payAccount": "6225882120912479",        
    //             "leftCount": "11"
    //         }
    //     };
    //     return res;
    // }
});