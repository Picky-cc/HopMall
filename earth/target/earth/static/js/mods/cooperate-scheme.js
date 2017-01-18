// 前后台的协定的数据格式
// 按规范编写，后台未完成时可模拟数据

// route定义的都是绝对路径
// methodName: 可选
// MyMock.RespJSON.route可多次调用
// 数据要随机生成需要指定type，如下：['_string', '_uuid', '_date', '_stringZh', '_stringEn', '_number']

define(function(require, exports, module) {
    var MyMock = require('./scaffold/mymock');

    var RespJSON = MyMock.RespJSON;

    // 省市区
    RespJSON.route({
        // 省市区
        '/earth/area/getProvinceList': {
            methodName: 'getCitys',
            request: {
                type: 'get',
                params: {}
            },
            response: {
                code: 0,
                message: '当前数据来自mock',
                data: {
                    provinceList: [{
                        code: '_stringEn',
                        name: '_stringZh'
                    }, '...']
                }
            }
        },
        '/earth/area/getCityList': {
            methodName: 'getCitys',
            request: {
                type: 'get',
                params: {
                    provinceCode: 'require'
                }
            },
            response: {
                code: 0,
                message: '当前数据来自mock',
                data: {
                    cityList: [{
                        code: '_stringEn',
                        name: '_stringZh'
                    }, '...']
                }
            }
        },
        '/earth/area/getDistrictList': {
            methodName: 'getDistricts',
            request: {
                type: 'get',
                params: {
                    cityCode: 'require'
                }
            },
            response: {
                code: 0,
                message: '当前数据来自mock',
                data: {
                    districtList: [{
                        code: '_stringEn',
                        name: '_stringZh'
                    }, '...']
                }
            }
        }
    });

    // 新增物业模型
    RespJSON.route({
        '/property/:id': {
            request: {
                type: 'get',
                params: {}
            },
            response: {
                code: 0,
                message: '',
                data: {
                    "leasingUnitAppendixs": [{
                        "bathroomNum": 1,
                        "buildArea": " buildArea",
                        "hallNum": 1,
                        "index": "_number",
                        "kitchenNum": 1,
                        "leasingUnitSubjectMatterNo": " leasingUnitSubjectMatterNo",
                        "leasingUnitSubjectMatterUuid": " leasingUnitSubjectMatterUuid",
                        "limit": " limit",
                        "occupants": " occupants",
                        "orientation": 1,
                        "promotionContent": " promotion_content",
                        "promotionTitle": " promotion_title",
                        "propertyStatus": 1,
                        "roomImgList": [{
                            "imgKey": "imgKey1",
                            "thumbNailsImgKey": "thumbNailsImgKey1"
                        }, {
                            "imgKey": "imgKey",
                            "thumbNailsImgKey": "thumbNailsImgKey"
                        }],
                        "roomNum": 1,
                        "utilitiesandAmenities": [{
                            "alias": "alias",
                            "check": true,
                            "name": "name"
                        }, {
                            "alias": "alias1",
                            "check": true,
                            "name": "name1"
                        }]
                    }, '...'],

                    "memo": {
                        "manager": "_sringZh",
                        "memo": "_sring"
                    },

                    "ownerInfomations": [{
                        "backCertificateImg": {
                            "imgKey": "imgKey",
                            "thumbNailsImgKey": "thumbNailsImgKey"
                        },
                        "certificateNo": " certificateNo",
                      "certificateType": " certificateType",
                      "certificateTypeAlias":"身份证",
                        "frontCertificateImg": {
                            "imgKey": "imgKey1",
                            "thumbNailsImgKey": "thumbNailsImgKey1"
                        },
                        "ownerName": " ownerName",
                        "ownerType": " ownerType",
                        "tradePartyUUid": "tradePartyUUid"
                    }, "..."],

                    "subjectMatterAppendix": {
                        "subjectMatterName": "_sringZh",
                        "subjectMatterNo": "_number",
                        "subjectMatterUuid": "_uuid",
                        "address": " address",
                        "areaCode": " areaCode",
                        "bathroomNum": 5,
                        "cityCode": " cityCode",
                        "community": " community",
                        "floor": 3,
                        "hallNum": 3,
                        "kitchenNum": 4,
                        "propertyCertificateImg": {
                            "imgKey": "propertyCertificateImgKey",
                            "thumbNailsImgKey": "propertyCertificateImgKey"
                        },
                        "propertyCertificateNo": " propertyCertificateNo",
                        "propertyType": " propertyType",
                        "provinceCode": " provinceCode",
                        "roomNo": " roomNo",
                        "roomNum": 2,
                        "roomType": " roomType",
                        "sourcePropertyNo": "sourcePropertyNo",
                        "totalFloors": 6
                    }

                    
                }
            }
        }
    });

    // 新增租约模型
    RespJSON.route({
        '/business-contract/add': {
            request: {
                type: 'get'
            },
            response: {
                code: 0,
                data: {
                    businessContractUuid: '_uuid',
                    // 物业详情
                    subjectMatterInfo: {
                        _leasingSubjectMatterId: '',
                        leasingSubjectMatterUuid: '',
                        masterSubjectMatterUuid: '',
                        
                        leasingSubjectMatterNo: '',
                        subjectMatterSourceNo:'',
                        masterSubjectMatterNo: '',
                        masterSubjectMatterName: '',
                        masterSubjectMatterAddress: '',
                        deadlineOfRentalContract: '2015-1-1'
                    },
                    // 合同期限
                    contractBasicInfo: {
                        issueTime: '2015-1-1',
                        effectiveTime: '2015-1-1',
                        maturityTime: '2015-1-1'
                    },
                    // 承租人信息
                    renterInfo: {
                        _tradePartyId: '',
                        tradePartyUuid: '',
                        renterType: 0, // 承租人类型,0: 个人，1: 企业
                        name: '',
                        certificateType: '',
                        certificateTypeAlias: '',
                        certificateNo: '',
                        frontCertificateImg: {
                    		imgKey: '',
                    		thumbNailsImgKey: '',
                    		imageName: ''
                        },
                        backCertificateImg: {
                        	imgKey: '',
                    		thumbNailsImgKey: '',
                    		imageName: ''
                        },
                        partyConcernedInfoList: [{
                            _partyConcernedId: '',
                            partyConcernedUuid: '',
                            name: '',
                            mobile: '',
                            broker: true
                        }],
                        accountInfoList: [{
                            financialAccountUuid: '',
                            //修改后
                            bankCode: '',
                            accountNo: '',
                            accountOwnerName: ''
                        }]
                    },
                    // 租金信息
                    paymentInfo: {
                        contractTermUuid: '',
                        settlementModesOfDeposit: 100,
                        depositeAmount: 100000.12,
                        rentingIntervalList: [{
                            paymentClauseUuid: '',
                            
                            startDate: '2015-10-10',
                            endDate: '2016-10-10',
                            amountPerMonth: 123.12,
                            paymentPeriod: 18,
                            payDayInMonth: 18
                        }],
                        taxInfo: {
                        	taxType: 1, // 1: 单位，0:个人
                            invoiceTitle: '',
                            addressee: '',
                            mobile: '',
                            provinceCode: '',
                            cityCode: '',
                            areaCode: '',
                            address: '',
                            postcode: ''
                        },
                        supplementaryTerms: '',
                    },
                    // 附件信息
                    attachmentInfo: {
                        sourceContractNo: '',
                        contractScanning: [{
                        	imgKey: '',
                    		thumbNailsImgKey: '',
                    		imageName: ''
                        }],
                        otherAttachment: [{
                    		fileUrlKey: '',
                    		fileName: ''
                        }]
                    }
                }
            }
        }
    });

    RespJSON.route({
        '/billing-plan/leasing-bill-list/query-stage-bill': {
            request: {
                type:'get',
                params: {
                    businessContractUuid: 'require',
                    stage: 'optional'
                }
            },
            response: {
                code: 0,
                data: {
                    stageBills: [{
                        billUuid:'_uuid', 
                        tradeStartPoint:'_date', // 应收日
                        billName:'账单名称', // 账单名称
                        carringAmount:'_number', // 账单应收
                        discountInfo: { // 折扣金额
                            amount:'_number',
                            discountType: '',
                            remarks: '_stringZh',
                            createTime:'_date'
                        }, 
                        amount:'_number', // 实际应收
                        receivedAmount:'_number', // 实际已收
                        billStatus: 1 // 账单状态 1: 开放 0: 关闭
                    }, '...']
                }
            }
        }
    });

    RespJSON.route({
        '/finance/debit-cash-detail': {
            request: {
                type:'get',
                params: {
                    cashFlowUuid: 'require'
                }
            },
            response: {
                code: 0,
                data: {
                    auditLog: [{
                        time: '2015-01-01 07:14',
                        content: 'nihao',
                        operatorUserName: 'lihua'
                    }],
                    cashFlowDetail: {
                        cashFlowSeriralNo: 'G2581200014873',
                        payerAccount: '3100151510005002018',
                        bankShortName: '招商银行******000',
                        time: '2016-03-02 10:15:35',
                        payerName: '瓦锡兰企业管理（上海）有限公',
                        amount: 90,
                        bankName: '中国银行',
                        remark: '枫叶小区301#',
                        auditStatus: 'CREATE',
                        relatedHouseSourceNo: '0339，0340',
                        relatedBillNo: '0339，0340',
                        relatedContractNo: '0339，0340',
                        paymentWay: '银行转账（pos收款，现金收款）'
                    }
                }
            }
        }
    });

    // RespJSON.route({
    //     '/earth/subject-matter/query-by-address': {
    //         request: {
    //             type: 'get',
    //             params: {}
    //         },
    //         response: {
    //             code: 0,
    //             data: [{
    //                 businessContractUuid:'_stringZh',
    //                 contractName:'_stringZh'
    //             }, '...']
    //         }
    //     }
    // });

    // RespJSON.route({
    //     '/earth/lease/query/by-contract': {
    //         request: {
    //             type: 'get',
    //             params: {}
    //         },
    //         response: {
    //             code: 0,
    //             data: [{
    //                 businessContractUuid:'_stringZh',
    //                 contractName:'_stringZh'
    //             }, '...']
    //         }
    //     }
    // });

    MyMock.RespJSON.start({root: global_config.root});
    MyMock.Mock().proxy(jQuery);
    
});
