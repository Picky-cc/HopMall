define(function (require, exports, module) {
    var util = require('scaffold/util');
    var Animation = require('component/animationBase');

    var maybeAddPx = util.maybeAddPx;
    var animOptions = ['easing', 'duration'];

    var basicAnims = {
        initialize: function (options) {
            Animation.call(this, _.pick(options, function (val, key) {
                return ~animOptions.indexOf(key);
            }));
        },
        runAnimation: function (from, to, animType) {
            var that = this;
            that.trigger(animType+':start');
            this.transition(from, to, function () {
                that.trigger(animType+':end');
            });
        },
        setDuration: function (duration) {
            if(!typeof duration === 'number') throw 'duration 不是number';
            this.duration = duration;
        },
        slideIn: function (property, duration) {
            this.setDuration(duration);
            var from={}, to={};
            if(!property) property = 'left';
            from[property] = -this.$el.width();
            to[property] = 0;
            this.runAnimation(from, to, 'slideIn');
        },
        slideOut: function (property, duration) {
            this.setDuration(duration);
            var from={}, to={};
            if(!property) property = 'left';
            from[property] = 0;
            to[property] = -this.$el.width();
            this.runAnimation(from, to, 'slideOut');
        },
        fadeIn: function (duration) {
            this.setDuration(duration);
            var from = { opacity: 0 };
            var to = { opacity: 1 };
            this.runAnimation(from, to, 'fadeIn');
        },
        fadeOut: function (duration) {
            this.setDuration(duration);
            var from = { opacity: 1 };
            var to = { opacity: 0 };
            this.runAnimation(from, to, 'fadeOut');
        },
        getDrawElement: function () {
            return this.el;
        },
        draw: function (percent) {
            var el = this.getDrawElement(),
                style = el.style;
            var from = this.from,
                to = this.to;
            for (var prop in from) {
                if(!from.hasOwnProperty(prop)) return;
                // transform的话这里有问题
                var delta = (to[prop] - from[prop]) * percent;
                style[prop] = maybeAddPx(from[prop] + delta, prop);
            }
            this.onPerDraw(style);
        },
        onPerDraw: function () {}
    };

    var AnimationView = Backbone.View.extend($.extend({}, Animation.prototype, basicAnims));

    module.exports = AnimationView;

});