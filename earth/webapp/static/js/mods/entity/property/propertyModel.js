define(function(require, exports, module) {
    // 出租单元集合的一条项目的模型
    var LeasingUnitAppendixsModel = Backbone.Model.extend({
        initialize: function(attr, opts) {
            this.completeModel = opts.completeModel;
        },
        getLeaseTypeDesc: function() {
            return this.completeModel.getLeaseTypeDesc();
        },
        isLeasingSubjectMatterNameUnique: function(testValue) {
            var leasingSubjectMatterName = this.get('leasingSubjectMatterName');
            var index = this.get('index');
            if(leasingSubjectMatterName === testValue) return false;
            var leasingUnitAppendixs = this.completeModel.get('leasingUnitAppendixs');
            var obj = _.findWhere(leasingUnitAppendixs, {leasingSubjectMatterName: testValue});
            return obj ? true : false;
        }
    });

    // 业主集合的一条项目的模型
    var OwnerInfomationsModel = Backbone.Model.extend({
        defaults: {
            "backCertificateImg": {
                "imgKey": "",
                "thumbNailsImgKey": ""
            },
            "certificateNo": "",
            "certificateType": 0,
            "frontCertificateImg": {
                "imgKey": "",
                "thumbNailsImgKey": ""
            },
            "ownerName": "",
            "ownerType": "0",
            "tradePartyUUid": ""
        },
        initialize: function(attr, opts) {
            this.completeModel = opts.completeModel;
        },
        isIdExist: function(testId) {
            var certificateNo = this.get('certificateNo');
            if(certificateNo === testId) return false; // 更新的情况
            var owners = this.completeModel.get('ownerInfomations');
            var obj = _.findWhere(owners, {certificateNo: testId});
            return obj ? true : false;
        }
    });

    // 生活缴费集合的一条项目的模型
    var FareTypeModel = Backbone.Model.extend({
        getAllFareTypes: function() {
            return [{
                name: '水费',
                value: 'COURSEOFDEALING_LEASING_WATER_FEES'
            }, {
                name: '燃气费',
                value: 'COURSEOFDEALING_LEASING_GAS_FEES'
            }, {
                name: '电费',
                value: 'COURSEOFDEALING_LEASING_ELECTRICITY_FEES'
            }];
        },
        getSelectedFareType: function() {
            var data = this.collection.toJSON();
            return data;
        },
        getRemainFareType: function() {
            var selectedTypes = this.getSelectedFareType() || [];
            var allTypes = this.getAllFareTypes();
            var mine = this.get('type');
            var filters = _.filter(allTypes, function(item) {
                if (item.value === mine) {
                    // 要包括自己
                    return true;
                } else {
                    var exist = _.find(selectedTypes, {
                        type: item.value
                    });
                    return !exist;
                }
            });
            return filters;
        }
    });

    var PropertyModel = Backbone.Model.extend({
        urlRoot: './',
        idAttribute: 'subjectMatterUuid',
        defaults: {
            subjectMatterAppendix: {},
            leasingUnitAppendixs: [],
            ownerInfomations: [],
            memo: {}
        },
        initialize: function(attr, options) {
       
        },
        validate: function(attrs, options) {
            if(attrs.leasingUnitAppendixs.length < 1) {
                return false;
            }

            if(attrs.ownerInfomations.length < 1) {
                return false;
            }
        },
        manualSave: function(opts) {
            this.save({}, opts);
        },
        parse: function(resp) {
            return resp.data;
        },
        getLeaseTypeDesc: function() {
            var leaseType = this.rGet('subjectMatterAppendix.leaseType');
            var zh = {
                0: '长租公寓',
                1: '创业空间'
            };
            return zh[leaseType];
        },
        getAccountInfoMap: function() {
            // 如果直接在外面使用subjectMatterAppendix.accountInfoMap获取值的话：
            //     后台传来的数据机构如果发生改变，所有用到这个值的地方都得改
            // 通过方法调用的话只要改方法里面的实现就好，外面不需要改动
            var accountInfoMap = this.rGet('subjectMatterAppendix.accountInfoMap');
            return accountInfoMap;
        },
        setAccountInfoMap: function(accountInfoMap) {
            var source = this.get('subjectMatterAppendix');
            source.accountInfoMap = accountInfoMap;
            this.set('subjectMatterAppendix', source);
        },

        rGet: function(name) {
            var zIndex = name.split('.'),
                source = this.get(zIndex.shift()),
                loop = source;

            var len = zIndex.length;

            for(var i = 0; i < len ; i++) {
                if(typeof loop !== 'object') {
                    return loop;
                }else {
                    loop = loop[zIndex[i]];
                }
            }

            return loop;
        },
        rSet: function(key, val, options) {
            // 合并策略
            // 没法触发里面的事件！
            var attrs,
                res = {};

            if(key == null) return this;

            if(typeof key === 'object') {
                attrs = key;
                options = val;
            }else {
                (attrs = {})[key] = val;
            }

            for(var name in attrs) {
                var zIndex = name.split('.'),
                    source = {},
                    loop = source;

                var len = zIndex.length;

                for(var i = 0; i < len - 1 ; i++) {
                    loop = loop[zIndex[i]] = {};
                }

                loop[zIndex[len-1]] = attrs[name];

                var origin,
                    current;
                (origin = {})[zIndex[0]] = this.get(zIndex[0]);
                current = $.extend(true, {}, origin, source); // 只是合并值，无法替代值
                $.extend(res, current);
            }

            this.set(res, options);
        },
        rUnset: function(name, options) {
            var fn = new Function('delete this.attributes.' + name);
            try {
                fn.call(this);
            }catch(err) {
                console.warn(err.message);
            }

            var data = this.toJSON();
            this.set(data);
        }
    });

    exports.PropertyModel = PropertyModel;
    exports.LeasingUnitAppendixsModel = LeasingUnitAppendixsModel;
    exports.OwnerInfomationsModel = OwnerInfomationsModel;
    exports.FareTypeModel = FareTypeModel;

});