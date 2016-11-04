define(function(require, exports, module) {
    var path = require('scaffold/path');

    describe('path - 路径处理模块', function() {
        describe('#basename', function() {
            it('返回路径的最后一部分'/*, function() {
                var url = '/earth/user/index.html';
                assert.isEqual(path.basename(url), 'index.html');

                url = '/earth/user/index';
                assert.isEqual(path.basename(url), 'index');

                url = '/earth/user/';
                assert.isEqual(path.basename(url), '');
            }*/);
        });

        describe('#extname', function() {
            it('返回路径的扩展名', function() {
                var url = '/earth/user/index.html';
                assert.equal(path.extname(url), '.html');

                url = '/earth/user/index';
                assert.equal(path.extname(url), '');
            });
        });

        describe('#join', function() {
            it('连接所有参数, 并且规范化得到的路径', function() {
                assert.equal(path.join('/earth', 'user', 1001), '/earth/user/1001');

                assert.equal(path.join('/earth', '/user', '/index'), '/earth/user/index');

                assert.equal(path.join('earth', 'user', '/index'), 'earth/user/index');
            });

            it('有多个斜杠是替换为一个', function() {
                assert.equal(path.join('/earth/', '/user/', 1001), '/earth/user/1001');
            });

            it('解析相对路径', function() {
                assert.equal(path.join('/earth/user', './teacher', 1001), '/earth/teacher/1001');

                assert.equal(path.join('/earth/user', '.././teacher', 1001), '/teacher/1001');

                assert.equal(path.join('/earth/user', '../teacher', 1001), '/teacher/1001');

                assert.equal(path.join('/earth/user/college', '../../teacher', 1001), '/teacher/1001');
            });
        });

        describe('#format', function() {
            it('输入一个 URL 对象，返回格式化后的 URL 字符串', function() {
                assert.equal(path.format({
                    path: '/earth'
                }), '/earth');

                assert.equal(path.format({
                    path: '/earth',
                    query: {
                        name: 'ych',
                        age: 19
                    }
                }), '/earth?name=ych&age=19');

                assert.equal(path.format({
                    path: '/earth/',
                    query: {
                        name: 'ych'
                    }
                }), '/earth/?name=ych');

                assert.equal(path.format({
                    path: '/earth',
                    hash: 'index/1001'
                }), '/earth#index/1001');

                assert.equal(path.format({
                    path: '/earth',
                    hash: '#index/1001'
                }), '/earth#index/1001');

                assert.equal(path.format({
                    path: '/earth/',
                    query: {
                        name: 'ych'
                    },
                    hash: '#index/1001'
                }), '/earth/?name=ych#index/1001');
            });
        });

        describe('#parseQueryString - 解析查询字符串', function() {
            it('测试无论前缀是否有\'?\'都能正确处理', function() {
                assert.deepEqual(path.parseQueryString('?name=ych&age=19'), {
                    name: 'ych',
                    age: '19'
                });

                assert.deepEqual(path.parseQueryString('name=ych&age=19'), {
                    name: 'ych',
                    age: '19'
                });
            });
        });

        describe('#parse - 输入 URL 字符串，返回一个对象', function() {
            it('测试：/earth/index', function() {
                assert.deepEqual(path.parse('/earth/index'), {
                    hash: null,
                    search: null,
                    query: null,
                    path: '/earth/index',
                    href: '/earth/index'
                });
            });

            it('测试：/earth/index#name=ych', function() {
                assert.deepEqual(path.parse('/earth/index#name=ych'), {
                    hash: '#name=ych',
                    search: null,
                    query: null,
                    path: '/earth/index',
                    href: '/earth/index#name=ych'
                });
            });

            it('测试：/earth/#index/1001?name=ych', function() {
                assert.deepEqual(path.parse('/earth/#index/1001?name=ych'), {
                    hash: '#index/1001?name=ych',
                    search: null,
                    query: null,
                    path: '/earth/',
                    href: '/earth/#index/1001?name=ych'
                });

                assert.deepEqual(path.parse('/earth/#index/1001?name=ych', true), {
                    hash: '#index/1001?name=ych',
                    search: null,
                    query: null,
                    path: '/earth/',
                    href: '/earth/#index/1001?name=ych'
                });
            });

            it('测试：/earth/?name=ych#index/1001', function() {
                assert.deepEqual(path.parse('/earth/?name=ych#index/1001'), {
                    hash: '#index/1001',
                    search: '?name=ych',
                    query: 'name=ych',
                    path: '/earth/',
                    href: '/earth/?name=ych#index/1001'
                });

                assert.deepEqual(path.parse('/earth/?name=ych#index/1001', true), {
                    hash: '#index/1001',
                    search: '?name=ych',
                    query: {
                        name: 'ych'
                    },
                    path: '/earth/',
                    href: '/earth/?name=ych#index/1001'
                });
            });
        });
    });
});