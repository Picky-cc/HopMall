(function (root, factory) {
    
    if(typeof define === 'function') {
        define(function (require, exports, module) {
            root.MyMock = factory(root, exports);
        });
    }else if(typeof exports !== 'undefined') {
        factory(root, exports);
    }else {
        root.MyMock = factory(root, {});
    }

})(this, function (root, MyMock) {
    var slice = Array.prototype.slice;
    var toString = Object.prototype.toString;

    // helper
    // ==================================================
    var isArray = Array.isArray || function(obj) {
        return toString.call(obj) === '[object Array]';
    };

    var isFunction = function(obj) {
        return typeof obj === 'function';
    };

    var isObject = function(obj) {
        return typeof obj === 'object';
    };

    var random = function(from, to) {
        if (!('1' in arguments)) {
            to = from;
            from = 0;
        }
        return Math.floor(Math.random() * to + from);
    };

    var isPlainObject = function(obj) {
        return isObject(obj) && Object.getPrototypeOf(obj) == Object.prototype;
    };

    var _extend = function(target, source) {
        for (var prop in source) {
            var item = source[prop];
            if (isArray(item)) {
                target[prop] = [];
                _extend(target[prop], item);
            } else if (isPlainObject(item)) {
                target[prop] = {};
                _extend(target[prop], item);
            } else if (item !== undefined)
                target[prop] = source[prop];
        }
    };

    var extend = function() {
        var args = slice.call(arguments);
        var target = args[0];
        for (var i = 0, len = args.length; i < len; i++) {
            _extend(target, args[i]);
        }
        return target;
    };


    // 数据
    // ==================================================
    (function (exports) {
        var DEFAULT_CHARSET = ['abcdefghijklmnopqrstuvwxyz0123456789', '其实无非是想破坏草原生态环境嘛好点子元狩年春正月赵游士进烧荒之策上大悦以国待出入同车言欢尽夜汲黯为不可听二令边民开焚 赐爵一级夏四三北冬十起狂沙越明流南侵郡耕地没人相食谷贼x自号将军攻略县杀太守长吏下千陇西天师围陈仓王败绩罪己妖谢公孙讨于荥阳所爱的题主这就当你穿到汉武帝间并力改变了历史后某些记载'];

        var generator = {};

        generator._string = function(charset) {
            charset || (charset = DEFAULT_CHARSET.join(''));
            var res = charset.split('').sort(function() {
                return (Math.random() - 0.5) > 0 ? true : false;
            });
            return res.slice(random(res.length)).join('');
        };

        generator._boolean = function () {
            var random = Math.random() - 0.5;
            return random > 0;
        };

        generator._stringEn = function() {
            return this._string(DEFAULT_CHARSET[0]);
        };

        generator._stringZh = function() {
            return this._string(DEFAULT_CHARSET[1]);
        };

        generator._date = function() {
            var year = new Date().getFullYear() - random(0, 15);
            var month = random(0, 12);
            var day = random(0, 31);
            return new Date(year, month, day).getTime(); // timestamp
        };

        generator._number = function() {
            return random(0, 9);
        };

        generator._uuid = function () {
            return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
                var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
                return v.toString(16);
            });
        };

        exports.DataCharSet = {
            TYPES : ['_string', '_uuid', '_date', '_stringZh', '_stringEn', '_number', '_boolean'],
            isTrueType: function (type) {
                return ~this.TYPES.indexOf(type);
            },
            get: function(type) {
                return this.isTrueType(type) ? generator[type]() : null;
            },
            // 添加自定义类型
            addDataType: function(){}
        };

    })(MyMock);


    // RespJSON
    // ==================================================
    (function (exports, DataCharSet) {

        var urlHelper;
        var urlReg = /[http(s)?|ftp]:\/\/([\w\.:]+)(\/[\s\S]*)?/;

        var convertToAbsUrl = function (url) {
            if(!urlHelper) {
                urlHelper = document.createElement('a');
            }
            urlHelper.href = url;
            var match = urlHelper.href.match(urlReg);
            if(match) {
                return match[2] ? match[2] :'';
            }else {
                throw '无效的Mck URL：'+url;
            }
        };

        var matchUrl=function(regUrl, url) {
            return regUrl.test(url);
        };

        var matchParams=function(configParams, importParams) {
            for (var configParamName in configParams) {
                if (configParams[configParamName] === 'require' && importParams[configParamName] == null) return false;
            }
            return true;
        };

        var matchHttpType = function (requireType, requestType) {
            return requireType === requestType;
        };

        var generateObject=function(schema) {
            var res = extend({}, schema);

            for (var prop in res) {
                res[prop] = DataCharSet.get(res[prop]);
            }

            return res;
        };

        var generateArray=function(schema) {
            var res = [];
            var num = random(20);
            while (num--) {
                res.push(generateObject(schema));
            }
            return res;
        };


        var RespJSON = {
            routes: {},  // 每一条URL都应该是唯一的所以之后设置的会覆盖前面的
            _routes: [],
            route: function(obj) {
                this._routes.push(obj);
            },
            decode: function(routes) {
                var result = {};
                for (var prop in routes) {
                    var body = routes[prop];
                    var regUrl = this.analysisUrl(prop);
                    result[regUrl.source] = {
                        schema: body.response,
                        httpType: body.request.type || 'get',
                        params: this.analysisParams(body.request.params),
                        regUrl: regUrl
                    };
                }
                return result;
            },
            analysisParams: function (params) {
                var result = {};
                if(!params) return result;
                for(var key in params) {
                    result[key] = ~['require', 'optional'].indexOf(params[key]) ? params[key] : 'require';
                }
                return result;
            },
            analysisUrl: function(sourceUrl) {
                var params = {};
                var url = (this.root + sourceUrl).replace(/\/\//g, '/')
                    .replace(/\(\/\)&/, '/?')
                    .replace(/\/:\w+/g, '/(\\w+)');

                return new RegExp(url+'$');
            },
            start: function(options) {
                options || (options={});
                this.root = (options.root || '/').replace(/.+\/$/, '');
                this.status = 'run';
                for(var i = 0, len = this._routes.length; i<len; i++) {
                    var pack = this.decode(this._routes[i]);
                    extend(this.routes, pack);
                }
                this._routes=[];
            },
            match: function(url, params, httpType) {
                if (this.status !== 'run') throw 'mock没有启动';
                var routes = this.routes;
                var absurl = convertToAbsUrl(url);
                for (var prop in routes) {
                    var item = routes[prop];
                    if(!matchHttpType(item.httpType.toLowerCase(), httpType.toLowerCase()) 
                        || !matchUrl(item.regUrl, absurl) 
                        || !matchParams(item.params, params)) 
                        continue;
                    return this.generate(item.schema);
                }

                return null;
            },
            generate: function(schema) {

                var isSimpleValue = function (val) {
                    return ~['number', 'string', 'boolean'].indexOf(typeof val);
                };

                var recursive = function (schema) {
                    var obj = {};
                    if(isSimpleValue(schema)) {
                        return schema;
                    }
                    for(var prop in schema) {
                        var value  = schema[prop];
                        if(isArray(value)) {
                            obj[prop] = [];
                            var itemScheme = value;
                            var num = itemScheme.length,
                                isOneScheme;
                            if(value.length === 2) {
                                itemScheme = [value[0]];
                                if(value[1] === '...') {
                                    isOneScheme=true;
                                    num = random(20);
                                }else {
                                    num = +value[1];
                                }

                                if(typeof num !== 'number') { // 第二项也是一个值
                                    itemScheme.push(value[1]);
                                    num = itemScheme.length;
                                }else {
                                    isOneScheme=true;
                                }
                            }

                            for(var i = 0; i<num; i++) {
                                var _itemScheme = itemScheme[isOneScheme ? 0 : i];
                                obj[prop].push(recursive(_itemScheme)); // // 如果不是对象有问题
                            }
                        }else if(isPlainObject(value)) {
                            obj[prop]=recursive(value);
                        }else {
                            if(DataCharSet.isTrueType(value)) {
                                obj[prop] = DataCharSet.get(value);
                            }else {
                                obj[prop]=value;
                            }
                        }
                    }
                    return obj;
                };

                return recursive(schema);
                
            }
            
        };

        MyMock.RespJSON = RespJSON;
        
    })(MyMock, MyMock.DataCharSet);


    // Mock
    // ==================================================
    (function (exports, RespJSON) {

        var processOptions = function(methodName, url) {
            var options = {};
            if(methodName === 'post') {
                options.type='post';
            }else if(methodName === 'get' || methodName === 'getJSON') {
                options.type='get';
            }
            if(methodName === 'getJSON') {
                options.dataType = 'json';
            }

            if(arguments.length>2) {
                options.url = url;
                if(isFunction(arguments[3])) {
                    options.success = arguments[3];
                    options.data=arguments[2];
                }else {
                    options.success = arguments[2];
                    options.data={};
                }
            }else {
                options = url;
                options.type || (options.type='get');
            }

            return options;
        };

        var Mock = function() {
            if(!(this instanceof Mock)) {
                return new Mock();
            }
            this.proxyLib = null;
            this.proxyMethods = null;
            this._init();
        };
        Mock.prototype._init = function() {};
        Mock.prototype._generateResponse = function(originArgs, options, errorOld) {
            var xhr = originArgs[0];

            var timerId = setTimeout(function () {
                var resp = RespJSON.match(options.url, options.data, options.type);
                if(resp) {
                    options.success(resp, 200, xhr);
                    options.complete && options.complete(xhr, 200);
                }else {
                    errorOld && errorOld.apply(null, originArgs);
                    options.complete && options.complete(xhr, 404);
                }
            }, random(100, 1000));

            return timerId;
        };
        Mock.prototype.isProxying = function(lib) {
            if (this.proxyLib === lib && this.proxyMethods != null) return true;
            else return false;
        };
        Mock.prototype.packLibMethod=function (methodName) {
            var self = this;
            return function () {
                var args = slice.call(arguments);
                args.unshift(methodName);
                var options = processOptions.apply(null, args);

                var errorOld = options.error;
                options.error = function () {
                    var args = slice.call(arguments, 0),
                        xhr = args[0];

                    if(xhr.status != 404) {
                        errorOld && errorOld.apply(null, args);
                    }else {
                        self._generateResponse(args, options, errorOld);
                    }
                };

                // 全部都委托给原本的ajax，如果成功不会代理，404出错才会代理
                self.proxyMethods.ajax.call(self.proxyLib, options);
            };
        };
        Mock.prototype.saveProxyMethods = function(lib) {
            var self = this;
            var res = {};
            ['ajax', 'get', 'post', 'getJSON'].forEach(function(methodName) {
                if (isFunction(lib[methodName])) {
                    res[methodName] = lib[methodName];
                    lib[methodName] = self.packLibMethod(methodName);
                }
            });
            return res;
        };
        Mock.prototype.proxy = function(lib) {
            var self = this;
            if (self.isProxying(lib)) return this;
            else self.unproxy();
            self.proxyLib = lib;
            self.proxyMethods = this.saveProxyMethods(lib);
            return this;
        };
        Mock.prototype.unproxy = function() {
            var self = this;
            var proxyLib = self.proxyLib;
            var proxyMethods = self.proxyMethods;
            for (var prop in proxyMethods) {
                proxyLib[prop] = proxyMethods[prop];
            }
            self.proxyLib = null;
            self.proxyMethods = null;
            return this;
        };

        exports.Mock = Mock;
    })(MyMock, MyMock.RespJSON);

    return MyMock;

});


