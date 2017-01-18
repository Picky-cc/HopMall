define(function(require, exports, module) {
    // module
    var popupTip = require('component/popupTip');
    var util = require('scaffold/util');

    // const
    var $ =  jQuery;
    var extractValue = util.extractValueFromDifferentDom;

    function modifyViewExtend () {
        return function (protoProps) {
            var view = Backbone.View.extend.apply(this, arguments);
            view.prototype.events=_.extend({}, this.prototype.events, protoProps.events);
            return view;
        };
    }

    // base form view
    var FieldsetView = Backbone.View.extend({
        initialize: function () {
        },
        _extractDomData: function (doms, reserveBlankString) {
            var result = {};
            for(var i=0, len = doms.length; i<len; i++) {
                $.extend(result, extractValue(doms.eq(i), reserveBlankString));
            }
            return result;
        },
        extractDomData: function () {
            return this._extractDomData(this.$('.real-value'), true); // 注意：这个统一改为true不知道有没有影响
        },
        save: function () {
            var attr= this.extractDomData();
            this.model.set(attr);
        },
        validate: function(){}
    });
    FieldsetView.addError = function(element, msg, wrapper) {
        element.addClass('error');

        var error = element.data('error-ref');
        if(!error) {
            error = $('<label for="" class="error-abnormal"></label>');
            element.data('error-ref', error);
        }
        error.text(msg);

        if($.isFunction(wrapper)) {
            wrapper(error, element);
        }else {
            wrapper.append(element);
        }
    };
    FieldsetView.removeError = function(element) {
        var error = element.data('error-ref');
        error && error.remove();
        element.removeClass('error');
    };
    FieldsetView.extend = modifyViewExtend();

    // base item view
    var SketchItemView = Backbone.View.extend({
        tagName: 'li',
        className: 'item',
        events: {
            'click .edit': 'toEdit',
            'click .delete': 'toDelete',
            'mouseenter': 'onMouseenter'
        },
        initialize: function () {
            this.listenTo(this.model, 'change', this.render);
            if(this.model.collection) {
                this.listenTo(this.model, 'remove', this.remove);  // destroy 还是 remove事件
            }else {
                this.listenTo(this.model, 'destroy', this.remove);  // destroy 还是 remove事件
            }
        },
        onMouseenter: function (e) {
            var el = this.$el;
            if(this.modelIsLast()) {
                el.addClass('last-item');
            }else {
                el.removeClass('last-item');
            }
        },
        modelIsLast: function () {
            var model =  this.model;
            return model.collection && model.collection.last() === model;
        },
        toEdit: function () {
            
        },
        toDelete: function (e) {
            e.preventDefault();
            if($(e.target).is('.disalbed')) return;
            this.model.destroy();
        },
        render: function () {
            var str = this.template(this.model.toJSON());
            this.$el.html(str);
            return this;
        }
    });
    SketchItemView.extend = modifyViewExtend();

    // base form dialog view
    var FormDialogView = FieldsetView.extend({
        tagName: 'div',
        className: 'modal fade form-modal small-form-modal',
        attributes: {
            'role': 'dialog',
            'tabindex': '-1'
        },
        events: {
            'click .submit': function(e) {
                e.preventDefault();
                this.submitHandler(e);
            },
            'hidden.bs.modal': 'remove'
        },
        initialize: function () {
            FormDialogView.__super__.initialize.apply(this, arguments);

            if(!this.model) {
                this.model = new Backbone.Model();
            }

            this.render();
        },
        render: function () {
            var data = this.model.toJSON();
            if (typeof this.template === 'function') {
                this.$el.html(this.template(data));
            }
        },
        show: function () {
            this.$el.modal();
        },
        hide: function () {
            this.$el.modal('hide');
        },
        updateTitle: function(title) {
            this.$('#dialoglabel').text(title);
        },
        popupTip: function(msg, type) {
            if(!this.popupTipEl) {
                this.popupTipEl = $('<p class="popup-tip" />');
                this.$('.modal-body').prepend(this.popupTipEl);
            }
            el = this.popupTipEl;
            switch(type) {
                case 'warning':
                    el.addClass('warning');
                    break;
                case 'success':
                    el.addClass('success');
                    break;
                case 'error':
                default:
                    el.addClass('error');
            }
            el.text(msg).show();
        },
        closeTip: function() {
            this.popupTipEl.hide();
        },
        submitHandler: function (e) {
            
        }
    });

    // base form dialog view
    var BaseFormView = FieldsetView.extend({
        events: {
            'click .submit': function(e) {
                e.preventDefault();
                this.submitHandler(e);
            },
            'keydown .form': function (e) {
                if(e.keyCode === 13) {
                    e.preventDefault();
                }
            }
        },
        initialize: function () {
            BaseFormView.__super__.initialize.apply(this, arguments);

            this.on('invalid', function() {
                var offsetTop = this.$(".error:not(label)").offset().top;
                var scrollTop = $('.scroller').scrollTop();
                var top = offsetTop + scrollTop  - 120;
                $('.scroller').animate({  
                    scrollTop: top  
                }, 200); 
            });
        },
        submitHandler: function(e) {
            
        }
    });
    
    exports.FieldsetView = FieldsetView;
    exports.SketchItemView = SketchItemView;
    exports.FormDialogView = FormDialogView;
    exports.BaseFormView = BaseFormView;

});
