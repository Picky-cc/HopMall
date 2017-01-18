// util.js

define(function(require, exports, module) {
    var $ = jQuery;
    var ls = localStorage;
    var encode = encodeURIComponent;
    var decode = decodeURIComponent;

    // helper
    function isFormDom(el) {
        var filters = ['textarea', 'input', 'select'];
        var flag = el.is(function() {
            for (var i = 0, len = filters.length; i < len; i++) {
                if ($(this).is(filters[i])) {
                    return true;
                }
            }
            return false;
        });
        return flag ? true : false;
    }

    function getItemVal(el, reserveBlankString) {
        var key = el.data('paramname') || el.data('params') || el.attr('name');
        if (!key) return null;

        var val;
        if (isFormDom(el)) {
            if (el.is('[type=checkbox]')) {
                val = el.get(0).checked;
            } else if (el.is('.selectpicker')) {
                var _val = el.selectpicker('val');
                val = _val ? JSON.stringify(_val) : '[]';
            } else {
                val = $.trim(el.val());
            }
        } else {
            val = $.trim(el.data('value'));
        }

        var res = {};

        if (val === '' && !reserveBlankString) {
            return null;
        }

        res[key] = val;

        return res;
    }

    function _reserveNumBit(bitNum) {
        return function(val) {
            var num1 = Math.pow(10, bitNum);
            var num2 = num1 * 10;
            return Math.floor((val * num2 + 5) / 10) / num1;
        };
    }

    // 将字符串格式化成-拼接的形式,一般用在样式属性上，比如border-width
    function dasherize(str) {
        return str.replace(/::/g, '/') // 将：：替换成/
            .replace(/([A-Z]+)([A-Z][a-z])/g, '$1_$2') // 在大小写字符之间插入_,大写在前，比如AAAbb,得到AA_Abb
            .replace(/([a-z\d])([A-Z])/g, '$1_$2') // 在大小写字符之间插入_,小写或数字在前，比如bbbAaa,得到bbb_Aaa
            .replace(/_/g, '-') // 将_替换成-
            .toLowerCase(); // 转成小写
    }

    // 缓存哪些css属性是不需要单位的纯数字
    var cssNumber = {
        'column-count': 1,
        'columns': 1,
        'font-weight': 1,
        'line-height': 1,
        'opacity': 1,
        'z-index': 1,
        'zoom': 1
    };


    // exports.DelayUtil={
    //     delayTimer: null,
    //     delayDo: function (func, interval) {
    //         if(this.delayTimer) {
    //             clearTimeout(this.delayTimer);
    //             this.delayTimer=null;
    //         }
    //         this.delayTimer=setTimeout(function () {
    //             func();
    //         }, interval||0);
    //     }
    // };

    exports.localDbUitl = {
        get: function(key) {
            var res = decode(ls.getItem(encode(key)));
            try {
                return JSON.parse(res);
            } catch (err) {
                console.error(err);
                return res;
            }
        },
        set: function(key, val) {
            var t = encode(JSON.stringify(val));
            ls.setItem(encode(key), t);
            return t;
        },
        remove: function(key) {
            var val = this.get(key);
            ls.removeItem(encode(key));
            return val;
        },
        clear: function() {
            ls.clear();
        },
        push: function(key, val) {
            var old = this.get(key);
            if (_.isArray(old)) {
                old.indexOf(val) == -1 && old.push(val);
                return this.set(key, old);
            } else if (isEmpty(old)) {
                return this.set(key, [val]);
            } else {
                throw new Error('该项不是数组');
            }
        }
    };

    // exports.cookieUtil={
    //     getCookie: function() {

    //     },
    //     setCookie: function() {

    //     },
    //     removeCookie: function() {

    //     }
    // };

    exports.validateUtil = {
        regExps: {
            mobile: /^1[3|4|5|7|8][0-9]\d{4,8}$/,
            number: /^[0-9]+$/,
            phoneExt: /^(0[0-9]{2,3}-)?([2-9][0-9]{6,7})([*#][0-9]{1,4})?$/,
            positiveInteger: /^[1-9][0-9]*$/,
            chineseCharactor: /^[\u4e00-\u9fa5]{1,}$/,
            englishName: /^[A-Za-z,.]+$/,
            numberAndAlphabet: /^[A-Za-z0-9]+$/,
            includeChineseCharactor: /[\u4e00-\u9fa5]{1,}/,
            includeNumber: /[0-9]/,
            IDCard: /(^\d{15}$)|(^\d{17}([0-9]|X)$)/,
            money: /^[0-9]+[\.]?[0-9]{0,3}$/,
            nonNegativeInteger: /^[0-9]+$/
        },

        isIDCardValid: function(value) {
            var isValid = this.regExps.IDCard.test(value);

            if (!isValid) {
                return false;
            }

            if (value.length == 18) {
                // 计算校验位
                var totalCheck = (new Number(value.charAt(0)) + new Number(value.charAt(10))) * 7 + (new Number(value.charAt(1)) + new Number(value.charAt(11))) * 9 + (new Number(value.charAt(2)) + new Number(value.charAt(12))) * 10 + (new Number(value.charAt(3)) + new Number(value.charAt(13))) * 5 + (new Number(value.charAt(4)) + new Number(value.charAt(14))) * 8 + (new Number(value.charAt(5)) + new Number(value.charAt(15))) * 4 + (new Number(value.charAt(6)) + new Number(value.charAt(16))) * 2 + new Number(value.charAt(7)) * 1 + new Number(value.charAt(8)) * 6 + new Number(value.charAt(9)) * 3
                var indexCheck = totalCheck % 11;
                var strCheckCodes = '10X98765432';
                var charCheck = strCheckCodes.charAt(indexCheck);
                if (charCheck === value.charAt(17)) {
                    isValid = true;
                } else if (value.charAt(17) === 'A') {
                    isValid = true;
                } else {
                    isValid = false;
                }
            }
            return isValid;
        }
    };


    (function(golabl, exports) {
        exports.requestAnimationFrame = golabl.requestAnimationFrame || golabl.mozRequestAnimationFrame || golabl.webkitRequestAnimationFrame || golabl.msRequestAnimationFrame || function(callback) {
            setTimeout(function() {
                callback();
            }, 16.7);
        };

        exports.cancelAnimationFrame = golabl.cancelAnimationFrame || golabl.webkitRequestAnimationFrame || golabl.mozCancelAnimationFrame || golabl.msRequestAnimationFrame || function(recordId) {
            clearTimeout(recordId);
        };
    })(window, exports);


    // exports.extractValueFromDifferentDom = function (inputEl) {
    //     var obj={};
    //     if(inputEl.is('select') && inputEl[0].multiple) {
    //         // 多选的select

    //     }else if(inputEl.is('.datetimepicker-form-control, .imitate-datepicker-input')) {
    //         $.each(inputEl, function (index, el) {
    //             $.extend(obj, getItemVal($(el).nextAll('.val')));
    //         });
    //         // obj = getItemVal(inputEl.next('.val'));
    //     }else {
    //         obj=getItemVal(inputEl);
    //     }
    //     return obj;
    // };

    /*
     * 从dom元素中提取值
     * @param  {元素} inputEl            可以是表单元素也可以是其他有data-params,data-paramname的元素
     * @param  {boolean} reserveBlankString 如果值为空字符串是否保留
     * @return {object}                    返回键值对形势
     */
    exports.extractValueFromDifferentDom = function(inputEl, reserveBlankString) {
        var obj = getItemVal(inputEl, reserveBlankString);
        return obj;
    };

    /*
     * 小数点后保留几位
     * @param  {string} val
     * @param  {number} bitNum 需要保留几位
     */
    exports.reserveNumBit = function(val, bitNum) {
        if (bitNum < 1) throw '保留的小数位数至少1位';
        return _reserveNumBit(bitNum)(val);
    };

    /*
     * 对字符串复制n次操作
     * @param  {number} num
     * @param  {string} str
     */
    exports.copyStringWithNum = function(num, str) {
        return new Array(num + 1).join(str);
    };

    /*
     * 给需要单位的css属性值添加单位
     * @param  {number} value
     * @param  {string} name  属性名
     */
    exports.maybeAddPx = function(value, name) {
        return (typeof value == "number" && !cssNumber[dasherize(name)]) ? value + "px" : value
    };

    /*
     * 只执行一次，调用resolve之后恢复
     * @param  {function} _trigger 代理的函数
     * @param  {object} context  指定执行上下文
     * @return {function}          包装后的函数
     */
    exports.execOnceBeforeDone = function(_trigger, context) {
        var runed = false;

        var done = function() {
            runed = false;
        };

        var trigger = function() {
            if (runed) return;
            runed = true;
            var args = Array.prototype.slice.call(arguments, 0);
            args.push(done);
            _trigger.apply(context || this, args);
        };

        return trigger;
    };

    /*
     * 选择文本框中的文本
     * @param  {dom} el
     * @param  {number} start 起始位置
     * @param  {number} end   终止位置
     */
    exports.selectText = function(el, start, end) {
        if (end == null) {
            end = el.value.length;
        }

        if (el.setSelectionRange) {
            el.setSelectionRange(start, end);
        } else if (el.createTextRange) {
            var range = el.createTextRange();
            range.collapse(true);
            range.moveStart('character', start);
            range.moveEnd('character', end - start);
            range.select();
        }
        el.focus();
    };

    /*
     * 是否为IE浏览器
     * @return {Boolean}
     */
    exports.isIE = function(userAgent) {
        userAgent = userAgent || navigator.userAgent;
        return userAgent.indexOf("MSIE ") > -1 || userAgent.indexOf("Trident/") > -1 || userAgent.indexOf("Edge/") > -1;
    };

    /*
     * 默认的extend会覆盖父亲的events，这里进行合并
     * 事件可以一级一级继承下来
     */
    exports.backboneCascadeExtend = function() {
        return function(protoProps) {
            var view = Backbone.View.extend.apply(this, arguments);
            view.prototype.events = _.extend({}, this.prototype.events, protoProps.events);
            return view;
        };
    };

    /*
     * 过几秒重载页面
     */
    exports.delayReload = function(time) {
        return setTimeout(function() {
            location.reload();
        }, time || 1000);
    };

});