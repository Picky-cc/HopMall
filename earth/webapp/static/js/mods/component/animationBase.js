define(function (require, exports, module) {
    var util = require('scaffold/util');

    var $ = jQuery;
    var raf = util.requestAnimationFrame,
        caf = util.cancelAnimationFrame;

    // 缓动函数
    var easing = {
        linear: function (t) {
            return t;
        }
    };
    
    /**
     * 动画类，来自自百度ife
     * @param {object} options 配置参数
     *     easing
     *     duration
     */
    function Animation (options) {
        this.duration = 500;
        $.extend(this, options);
    }
    Animation.prototype.startWithTarget = function (target) {
        this.target = target;
    };
    Animation.prototype.start = function () {
        var self = this;
        self._startTime = +new Date();

        function mainLoop () {
            var now = +new Date();
            var elapsedTime = now - self._startTime;
            if(elapsedTime >= self.duration) {
                self.tick(1);
                self.stop();
            }else {
                self.tick(elapsedTime/self.duration);
                self._rafId = raf(mainLoop);
            }
        }

        if(!this._rafId) {
            this._rafId = raf(mainLoop);
        }
    };
    Animation.prototype.stop = function () {
        if(this._rafId) {
            caf(this._rafId);
            this._rafId = null;
        }
        this._done && this._done();
    };
    Animation.prototype.tick = function (percent) {
        var easingFn = this.easingName ? easing[this.easingName] : '';
        if(easingFn) {
            percent = easingFn(percent);
        }
        this.draw(percent);
    };
    Animation.prototype.transition = function (from, to, cb) {
        this.from = from;
        this.to = to;
        this.start();
        // 注册回调
        // 更好的方式应该用promise
        this._done = cb;
    };
    Animation.prototype.draw = function (percent) {};

    module.exports = Animation;

});