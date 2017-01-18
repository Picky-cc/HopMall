/*
 ajax请求的测试用例：
 前台拦截，返回响应：
     优点：纯粹测试接口及相关逻辑是否正确，这种测试不知道有没有意义？？
     缺点：不测试前后台能否通信成功：

 直接向后台请求：
     优点：能测试前后台能否通信成功
     缺点：一旦改了数据无法恢复

 http://spud.in/2013/05/03/introduce-sinonjs/
 */

define(function(require, exports, module) {
    var Model = require('entity/contract/leasingBillModel');
    var BillingPlanModel = Model.BillingPlanModel;
    var BillingPlanCollection = Model.BillingPlanCollection;

    describe('BillingPlanCollection', function() {
        describe('#fetch', function() {
            after(function() {
                jQuery.ajax.restore();
            });

            before(function() {
                sinon.stub(jQuery, 'ajax').yieldsTo('success', {
                    code: 0,
                    data: {
                        stageBills: [{
                            billUuid:'1001', 
                            tradeStartPoint:'2015-01-01',
                            billName:'账单名称',
                            carringAmount:50, 
                            discountInfo: { 
                                amount:0,
                                discountType: '1',
                                remarks: ''
                            }, 
                            amount:50,
                            receivedAmount:20,
                            billStatus: 1 
                        }, {
                            billUuid:'1002', 
                            tradeStartPoint:'2015-01-01',
                            billName:'账单名称',
                            carringAmount:50,
                            discountInfo: {
                                amount:0,
                                discountType: '1',
                                remarks: ''
                            }, 
                            amount:50,
                            receivedAmount:0,
                            billStatus: 1 
                        }]
                    }
                });
            });

            it('获取远程数据', function() {
                var collection = new BillingPlanCollection();
                collection.fetch();
                assert.equal(collection.length, 2);
                assert.equal(collection.at(0).get('billUuid'), '1001');
                assert.equal(collection.at(1).get('billUuid'), '1002');
            });
        });

        describe('#parse', function() {
            it('解析后台传回的数据，返回data.stageBills', function() {
                var collection = new BillingPlanCollection();
                var result = collection.parse({
                    code: 0,
                    data: {
                        stageBills: [{
                            billUuid: '1001',
                            billStatus: 1
                        }, {
                            billUuid: '1002',
                            billStatus: 3
                        }]
                    }
                });
                assert.deepEqual(result, [{
                    billUuid: '1001',
                    billStatus: 1
                }, {
                    billUuid: '1002',
                    billStatus: 3
                }]);
            });
        });

        describe('#toggleCheck', function() {
            beforeEach(function() {
                this.collection = new BillingPlanCollection([{
                    billUuid: '1001',
                    billStatus: 1
                }, {
                    billUuid: '1002',
                    billStatus: 3
                }]);
            });

            it('全部选择', function() {
                var callback = sinon.spy();
                this.collection.on('togglecheck:all', callback);

                this.collection.toggleCheck(true);

                assert.isTrue(callback.called);
                assert.equal(callback.callCount, 2);

                assert.isTrue(this.collection.at(0).isCheck);
                assert.isTrue(this.collection.at(1).isCheck);
            });

            it('全部取消', function() {
                var callback = sinon.spy();
                this.collection.on('togglecheck:all', callback);

                this.collection.toggleCheck(true);

                assert.isTrue(callback.called);
                assert.equal(callback.callCount, 2);

                assert.isTrue(this.collection.at(0).isCheck);
                assert.isTrue(this.collection.at(1).isCheck);
            });
        });

        describe('#filterCheck', function() {
            it('返回所有已选择的模型', function() {
                var collection = new BillingPlanCollection([{
                    billUuid: '1001',
                    billStatus: 1
                }, {
                    billUuid: '1002',
                    billStatus: 3
                }]);

                assert.equal(collection.filterCheck().length, 0);

                collection.at(0).execCheckByItem(true);
                assert.equal(collection.filterCheck().length, 1);
                assert.isTrue(collection.at(0).isCheck);
                assert.equal(collection.at(0).get('billUuid'), '1001');

                collection.at(1).execCheckByItem(true);
                assert.equal(collection.filterCheck().length, 2);

                collection.at(1).execCheckByItem(false);
                assert.equal(collection.filterCheck().length, 1);
            });
        });

        describe('#isAllPayOff', function() {
            it('是否全部付清', function() {
                var collection = new BillingPlanCollection([]);

                var model1 = new BillingPlanModel({
                    receivedAmount: 10,
                    amount: 10
                });

                var model2 = new BillingPlanModel({
                    receivedAmount: 10,
                    amount: 9
                });

                var model3 = new BillingPlanModel({
                    receivedAmount: 0
                });

                collection.add(model1);
                collection.add(model2);
                collection.add(model3);

                assert.equal(collection.length, 3);
                assert.isFalse(collection.isAllPayOff([model1, model2, model3]));

                model2.set('amount', 10);

                assert.isTrue(collection.isAllPayOff([model1, model2, model3]));
            });
        });

        describe('#discards', function() {
            before(function() {
                sinon.stub(jQuery, 'ajax').yieldsTo('success', {
                    code: 0,
                    data: null
                });
            });

            after(function() {
                jQuery.ajax.restore();
            });

            it('关闭一批数组，被关闭后的模型数组billStatus值应为3', function() {
                var collection = new BillingPlanCollection([]);

                var model1 = new BillingPlanModel({
                    billUuid:'1001', 
                    billStatus: 1 
                });

                var model2 = new BillingPlanModel({
                    billUuid:'1002', 
                    billStatus: 1 
                });

                var model3 = new BillingPlanModel({
                    billUuid:'1003', 
                    billStatus: 1 
                });

                collection.add(model1);
                collection.add(model2);
                collection.add(model3);

                collection.discards([model1, model2]);

                assert.isTrue(jQuery.ajax.calledWithMatch({
                    url: collection.discardBatchAction,
                    data: {
                        billingPlanUuidList: JSON.stringify(['1001', '1002'])
                    }
                }));

                assert.equal(model1.get('billStatus'), 3);
                assert.equal(model2.get('billStatus'), 3);
                assert.equal(model3.get('billStatus'), 1);
            });

            it('已关闭的允许再次被关闭', function() {
                var collection = new BillingPlanCollection([]);

                var model1 = new BillingPlanModel({
                    billUuid:'1001', 
                    billStatus: 1 
                });

                var model2 = new BillingPlanModel({
                    billUuid:'1002', 
                    billStatus: 3
                });

                var model3 = new BillingPlanModel({
                    billUuid:'1003', 
                    billStatus: 1 
                });

                collection.add(model1);
                collection.add(model2);
                collection.add(model3);

                collection.discards([model1, model2, model3]);

                assert.isTrue(jQuery.ajax.calledWithMatch({
                    url: collection.discardBatchAction,
                    data: {
                        billingPlanUuidList: JSON.stringify(['1001', '1002', '1003'])
                    }
                }));

                assert.equal(model1.get('billStatus'), 3);
                assert.equal(model2.get('billStatus'), 3);
                assert.equal(model3.get('billStatus'), 3);
            });
        });

        describe('#deletes', function() {
            before(function() {
                sinon.stub(jQuery, 'ajax').yieldsTo('success', {
                    code: 0,
                    data: null
                });
            });

            after(function() {
                jQuery.ajax.restore();
            });

            it('删除一批模型', function() {
                var collection = new BillingPlanCollection([]);

                var model1 = new BillingPlanModel({
                    billUuid:'1001', 
                    billStatus: 1 
                });

                var model2 = new BillingPlanModel({
                    billUuid:'1002', 
                    billStatus: 1 
                });

                var model3 = new BillingPlanModel({
                    billUuid:'1003', 
                    billStatus: 1 
                });

                var callback = sinon.spy();
                collection.on('remove', callback);

                collection.add(model1);
                collection.add(model2);
                collection.add(model3);

                collection.deletes([model1, model2]);

                assert.isTrue(callback.called);
                assert.equal(callback.callCount, 2);

                assert.isTrue(jQuery.ajax.calledWithMatch({
                    url: collection.discardBatchAction,
                    data: {
                        billingPlanUuidList: JSON.stringify(['1001', '1002'])
                    }
                }));

                assert.equal(collection.length, 1);
                assert.isTrue(collection.at(0) === model3);
            });
        });

    });

    describe('BillingPlanModel', function() {
        describe('initialization', function() {
            beforeEach(function() {
                this.model = new BillingPlanModel();
            });

            it('默认值isCheck(是否勾选)为false', function() {
                assert.isFalse(this.model.isCheck);
            });

            it('默认值display(是否显示)为true', function() {
                assert.isTrue(this.model.display);
            });
        });

        describe('#isPayOff', function() {
            it('应该返回true当receivedAmount === amount', function() {
                var model = new BillingPlanModel();

                model.set({
                    receivedAmount: 10,
                    amount: 10
                });

                assert.isTrue(model.isPayOff());

                model.set({
                    receivedAmount: 10,
                    amount: 9
                });

                assert.isFalse(model.isPayOff());
            });

            it('应该返回true当receivedAmount=0时', function() {
                var model = new BillingPlanModel();

                model.set({
                    receivedAmount: 0,
                    amount: 9
                });

                assert.isTrue(model.isPayOff());

                model.set({
                    receivedAmount: 0
                });

                assert.isTrue(model.isPayOff());
            });
        });

        describe('#modifyDiscount', function() {
            after(function() {
                jQuery.ajax.restore();
            });

            it('修改折扣成功之后amount等于carringAmount－discountInfo.amount', function() {
                sinon.stub(jQuery, 'ajax').yieldsTo('success', {
                    code: 0,
                    data: null
                });

                var model = new BillingPlanModel({
                    billUuid:'1002', 
                    tradeStartPoint:'2015-01-01', // 应收日
                    billName:'账单名称', // 账单名称
                    carringAmount:50, // 账单应收
                    discountInfo: { // 折扣金额
                        amount:0,
                        discountType: '1',
                        remarks: ''
                    }, 
                    amount:50, // 实际应收
                    receivedAmount:20, // 实际已收
                    billStatus: 1 
                });

                var callback = sinon.spy();
                model.on('change', callback);

                model.modifyDiscount({
                    amount:10,
                    discountType: '1',
                    remarks: '修改折扣'
                });

                assert.isTrue(callback.called);

                assert.isTrue(jQuery.ajax.calledWithMatch({
                    url: model.modifyDiscountAction,
                    data: {
                        billUuid: '1002',
                        amount: 10,
                        discountType: '1',
                        remarks: '修改折扣'
                    }
                }), '没有按正确参数请求');

                assert.equal(model.get('amount'), model.get('carringAmount') - model.get('discountInfo').amount);
            });
        });

        describe('#discard', function() {
            before(function() {
                sinon.stub(jQuery, 'ajax').yieldsTo('success', {
                    code: 0,
                    data: null
                });
            });

            after(function() {
                jQuery.ajax.restore();
            });

            it('关闭成功之后账单状态变为3(关闭)', function() {
                var model = new BillingPlanModel({
                    billUuid:'1002', 
                    billStatus: 1 
                });

                var callback = sinon.spy();
                model.on('change', callback);

                model.discard();

                assert.isTrue(callback.called);

                assert.isTrue(jQuery.ajax.calledWithMatch({
                    url: model.discardAction,
                    data: {
                        billingPlanUuidList: JSON.stringify(['1002'])
                    }
                }));

                assert.equal(model.get('billStatus'), 3);
                assert.equal(model.get('billStatusDescription'), '关闭');
            });
        });

        describe('#delete', function() {
            before(function() {
                sinon.stub(jQuery, 'ajax').yieldsTo('success', {
                    code: 0,
                    data: null
                });
            });

            after(function() {
                jQuery.ajax.restore();
            });

            it('删除成功触发destroy事件', function() {
                var model = new BillingPlanModel({
                    billUuid:'1002'
                });

                var callback = sinon.spy();
                model.on('destroy', callback);

                model.remove();

                assert.isTrue(callback.called);

                assert.isTrue(jQuery.ajax.calledWithMatch({
                    url: model.deleteAction,
                    data: {
                        billingPlanUuidList: JSON.stringify(['1002'])
                    }
                }));

            });
        });

        describe('#execCheckByItem', function() {
            it('单选取消勾选触发togglecheck:item事件', function() {
                var model = new BillingPlanModel();

                var callback = sinon.spy();
                model.on('togglecheck:item', callback);

                model.execCheckByItem(false);

                assert.isFalse(model.isCheck);
                assert.isTrue(callback.called);
            });

            it('单选确定勾选触发togglecheck:item事件', function() {
                var model = new BillingPlanModel();

                var callback = sinon.spy();
                model.on('togglecheck:item', callback);

                model.execCheckByItem(true);

                assert.isTrue(model.isCheck);
                assert.isTrue(callback.called);
            });
        });

        describe('#execCheckByAll', function() {
            it('全选确定勾选触发togglecheck:all事件', function() {
                var model = new BillingPlanModel();

                var callback = sinon.spy();
                model.on('togglecheck:all', callback);

                model.execCheckByAll(true);

                assert.isTrue(model.isCheck);
                assert.isTrue(callback.called);
            });

            it('全选取消勾选触发togglecheck:all事件', function() {
                var model = new BillingPlanModel();

                var callback = sinon.spy();
                model.on('togglecheck:all', callback);

                model.execCheckByAll(false);
                
                assert.isFalse(model.isCheck);
                assert.isTrue(callback.called);
            });
        });

        describe('#execDisplay', function() {
            it('显示视图', function() {
                var model = new BillingPlanModel();

                var callback = sinon.spy();
                model.on('display', callback);

                model.execDisplay(true);

                assert.isTrue(model.display);
                assert.isTrue(callback.called);
            });

            it('隐藏视图', function() {
                var model = new BillingPlanModel();

                var callback = sinon.spy();
                model.on('display', callback);

                model.execDisplay(false);

                assert.isFalse(model.display);
                assert.isTrue(callback.called);
            });
        });
    });

});