define(function(require, exports, module) {
    var util = require('scaffold/util');

    describe('util - 通用模块', function() {

        describe('#execOnceBeforeDone', function() {
            it('不调用done，只执行一次', function() {
                var count = 0;
                var run = function(done) {
                    count++;
                };

                var packet = util.execOnceBeforeDone(run);
                for(var i = 0; i<3; i++) {
                    packet();
                }

                assert.equal(count, 1);
            });

            it('调用done，多次执行', function() {
                var count = 0;
                var run = function(done) {
                    count++;
                    done();
                };

                var packet = util.execOnceBeforeDone(run);
                for(var i = 0; i<3; i++) {
                    packet();
                }

                assert.equal(count, 3);
            });

            it('异步调用及传递参数', function(done) { // 添加一个callback，告诉mocha这个it是一个异步的测试用例
                var count = 0;
                var run = function(name, complete) {
                    count++;
                    assert.equal(name, 'yechehui');

                    setTimeout(function() {
                        complete();
                        assert.equal(count, 1);
                        done();
                    }, 1000);
                };

                var packet = util.execOnceBeforeDone(run);
                for(var i = 0; i<3; i++) {
                    packet('yechehui');
                }
            });
        });

        describe('#maybeAddPx', function() {
            var cssNumber = {
                'column-count': 1,
                'columns': 1,
                'font-weight': 1,
                'line-height': 1,
                'opacity': 1,
                'z-index': 1,
                'zoom': 1
            };

            for(var css in cssNumber) {
                it('属性' + css + '单位没有px', (function(css) {
                    return function() {
                        var res = util.maybeAddPx(cssNumber[css], css);
                        assert.equal(res, cssNumber[css]);
                    };
                })(css));
            }


            var cssString = {
                'margin': 10,
                'padding-top': 10,
                'left': 10
            };

            for(var css in cssString) {
                it('属性' + css + '单位有px', (function(css) {
                    return function() {
                        var res = util.maybeAddPx(cssString[css], css);
                        assert.equal(res, cssString[css] + 'px');
                    };
                }(css)));
            }
        });

        describe('#copyStringWithNum', function() {
            it('复制字符串n次', function() {
                var res = util.copyStringWithNum(3, 'ych');
                assert.equal(res, 'ychychych');

                res = util.copyStringWithNum(1, 'ych');
                assert.equal(res, 'ych');

                res = util.copyStringWithNum(0, 'ych');
                assert.equal(res, '');
            });
        });

        describe('#reserveNumBit', function() {
            it('保留小数点n位', function() {
                var res = util.reserveNumBit(2.332, 2);
                assert.equal(res, 2.33);

                res = util.reserveNumBit(2.335, 2);
                assert.equal(res, 2.34);

                res = util.reserveNumBit(2.33, 1);
                assert.equal(res, 2.3);

                res = util.reserveNumBit(2.3, 1);
                assert.equal(res, 2.3);

                try {
                    res = util.reserveNumBit(2.3, 0);
                }catch(err) {
                    assert.equal(err, '保留的小数位数至少1位');
                }

            });

            it('返回值是数字，所以无法实现：2.30这种情况', function() {
                var res = util.reserveNumBit(2.3, 2);
                assert.notStrictEqual(res, '2.30');
            });
        });

        describe('#extractValueFromDifferentDom', function() {
            it('从dom元素中取存储的value', function() {
                var text = $('<input name="age" value="19">'),
                    select = $('<select name="sex"><option selected value="male">男</option><option value="female">女</option></select>'), // 单选框
                    span = $('<span data-paramname="name" data-value="yechenhui">叶陈辉</span>');

                var res = util.extractValueFromDifferentDom(text);
                assert.deepEqual(res, {age: '19'});

                res = util.extractValueFromDifferentDom(select);
                assert.deepEqual(res, {sex: 'male'});

                res = util.extractValueFromDifferentDom(span);
                assert.deepEqual(res, {name: 'yechenhui'});
            });

            it('空字符串的判断', function() {
                var text = $('<input name="age" value="">'),
                    span = $('<span data-paramname="name" data-value="">叶陈辉</span>');

                var res = util.extractValueFromDifferentDom(text);
                assert.strictEqual(res, null);

                res = util.extractValueFromDifferentDom(text, true);
                assert.deepEqual(res, {age: ''});

                res = util.extractValueFromDifferentDom(span);
                assert.strictEqual(res, null);

                res = util.extractValueFromDifferentDom(span, true);
                assert.deepEqual(res, {name: ''});

            });

            it('null值的判断', function() {

                var text = $('<input name="age">');
                var res = util.extractValueFromDifferentDom(text);
                assert.strictEqual(res, null);

                text = $('<input value="19">');
                res = util.extractValueFromDifferentDom(text);
                assert.strictEqual(res, null);

                var span = $('<span data-paramname="name">叶陈辉</span>');
                res = util.extractValueFromDifferentDom(span);
                assert.strictEqual(res, null);

                span = $('<span data-value="yechenhui">叶陈辉</span>');
                res = util.extractValueFromDifferentDom(span);
                assert.strictEqual(res, null);

            });

            it('多选框的取值未实现');
        });

        describe('#validateUtil', function() {
            var validateUtil = util.validateUtil;
            
            it('身份证号码验证', function() {

                ['332528199403055018', '511702197701193532', '411525197006172529', '44182619770626553X'].forEach(function(item) {
                    assert.isTrue(validateUtil.isIDCardValid(item));
                });

                ['33252819940305501', '51170219770119353a', 'sfsfsdfsfsdfsd', '4-x82619770626553X'].forEach(function(item) {
                    assert.isFalse(validateUtil.isIDCardValid(item));
                });
            });
        });

        describe('#localDbUitl', function() {
            it('localStorage测试用例暂不实现，项目中没有用到');
        });

    });

});