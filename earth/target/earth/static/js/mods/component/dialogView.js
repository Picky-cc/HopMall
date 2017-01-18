define(function(require, exports, module) {
    var $ = jQuery;
    var body = $('body');
    var slice = Array.prototype.slice;
    var DefaultOptions = {
        title: '提示',
        cancelBtnTxt: '关闭',
        goaheadBtnTxt: '确定',
        bodyInnerTxt: '',
        excludeGoahed: false
    };

    var htm = [
        '    <div class="modal-dialog">',
        '        <div class="modal-content">',
        '            <div class="modal-header">',
        '                <button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal">',
        '                    <span aria-hidden="true">&times;</span>',
        '                </button>',
        '                <h4 class="modal-title" id="dialoglabel">{%= title %}</h4>',
        '            </div>',
        '            <div class="modal-body">',
        '                <p class="inner">{%= bodyInnerTxt %}</p>',
        '            </div>',
        '            <div class="modal-footer">',
        '                <button type="button" class="btn btn-default cancel" data-dismiss="modal">{%= cancelBtnTxt %}</button>',
        '                <button type="button" class="btn btn-success goahead">{%= goaheadBtnTxt %}</button>',
        '            </div>',
        '        </div>',
        '    </div>',
    ].join("");

    // 如果没有el传进来就会生成一个新的el
    var DialogView = Backbone.View.extend({
        template: _.template(htm),
        tagName: 'div',
        className: 'modal fade',
        attributes: {
            'role': 'dialog',
            'tabindex': '-1',
            'aria-labelledby': 'dialoglabel',
            'aria-hidden': 'true'
        },
        events: {
            'click .goahead': 'goaheadHandler',
            'click .cancel': 'cancelHandler',
            'hidden.bs.modal': 'closeDialogHandler'
        },
        initialize: function(options) {
            this.currentImportArgs = null;
            this.options = $.extend({}, DefaultOptions, options);
            this.txtEl = this.$('.modal-body .inner');
            if (this.txtEl.length<1) {
                this.render();
                this.txtEl = this.$('.modal-body .inner');
            }
            this.options.bodyInnerTxt && this.txtEl.html(this.options.bodyInnerTxt);

            this.controlOperateBtns(this.options);
        },
        render: function () {
            var htm = this.template(this.options);
            this.$el.html(htm);
            return this;
        },
        cancelHandler: function() {
            this.trigger.apply(this, ['cancel'].concat(this.currentImportArgs));
        },
        goaheadHandler: function() {
            this.trigger.apply(this, ['goahead'].concat(this.currentImportArgs));
        },
        closeDialogHandler: function() {
            this.trigger.apply(this, ['closedialog'].concat(this.currentImportArgs));
            this.currentImportArgs = null;
            this.$el.detach();
        },
        controlOperateBtns: function(opts) {
            var hideElNum = 0;

            if(opts.excludeGoahed) {
                this.$('.goahead').hide();
                hideElNum++;
            }else {
                this.$('.goahead').show();
            }

            if(opts.excludeCancel) {
                this.$('.cancel').hide();
                hideElNum++;
            }else {
                this.$('.cancel').show();
            }

            if(hideElNum == 2) {
                this.$('.modal-footer').css({
                    'padding-top': 0,
                    'padding-bottom': 0
                });
            }else {
                this.$('.modal-footer').css({
                    'padding-top': null,
                    'padding-bottom': null
                });
            }

            return this;
        },
        showRichText: function() {
            this.txtEl.css({
                'margin-top': 0,
                'margin-bottom': 0
            });
            this.show.apply(this, arguments);
        },
        show: function(txt) {
            var arg;
            if(typeof txt === 'string') {
                this.txtEl.html(txt);
                args = slice.call(arguments, 1);
            }else {
                args = slice.call(arguments, 0);
            }
            args.length > 0 ? (this.currentImportArgs = args) : (this.currentImportArgs = null);
            this.$el.modal('show');
        },
        hide: function() {
            this.$el.modal('hide');
        }
    });

    module.exports = DialogView;
});