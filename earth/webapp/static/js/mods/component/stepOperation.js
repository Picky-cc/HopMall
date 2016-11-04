define(function(require, exports, module) {
    var backboneCascadeExtend = require('scaffold/util').backboneCascadeExtend;

    var StepOperationView = Backbone.View.extend({
        events: {
            'click .next': 'next',
            'click .prev': 'prev'
        },
        initialize: function() {
            this.$tipItems = this.$('.step-tip-item');
            this.$contentItems = this.$('.step-content-item');
            this.$prevBtn = this.$('.prev');
            this.$nextBtn = this.$('.next');
            this.stepCount = this.$tipItems.length;

            this.on('steped', function(index) {
                this.$contentItems.hide();
                this.$tipItems.removeClass('z-active');

                this.$tipItems.eq(index - 1).addClass('z-active');
                this.$contentItems.eq(index - 1).show();

                if (index === 1) {
                    this.$prevBtn.text('取消');
                    this.$nextBtn.text('下一步');
                } else if (index === this.stepCount) {
                    this.$prevBtn.text('上一步');
                    this.$nextBtn.text('确认');
                } else {
                    this.$prevBtn.text('上一步');
                    this.$nextBtn.text('下一步');
                }
            });

            var $tip = this.$tipItems.filter('.z-active');
            $tip.length === 0 && ($tip = this.$tipItems.first());
            this.activate($tip.data('index'));
        },
        beforeStepInto: function(curent, next) {
            return false;
        },
        activate: function(index) {
            var terminal = this.beforeStepInto(this.curStepIndex, index);
            if (terminal) return;
            this.trigger('step', this.curStepIndex, index);
            this.curStepIndex = index;
            this.trigger('steped', this.curStepIndex, this.curStepIndex === this.stepCount);
        },
        next: function() {
            if (this.curStepIndex >= this.stepCount) {
                this.trigger('step:done');
                return;
            }
            this.activate(this.curStepIndex + 1);
        },
        prev: function() {
            if (this.curStepIndex <= 1) {
                this.trigger('step:cancel');
                return;
            }
            this.activate(this.curStepIndex - 1);
        }
    });

    StepOperationView.extend = backboneCascadeExtend();

    module.exports = StepOperationView;
});