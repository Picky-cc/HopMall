define(function(require, exports, module) {
    var backboneCascadeExtend = require('scaffold/util').backboneCascadeExtend;
    var $ = jQuery;

    var mixins = {
        activate: function($menu) {
            var $content = this._getContent($menu);

            if ($content && $content.length) {
                this.$contentItems.hide();
                $content.show();

                this.$menuItems.removeClass('z-active');
                $menu.addClass('z-active');
            }
        },
        onClickSegmentMenuItem: function(e) {
            var $menu = $(e.currentTarget);
            this.activate($menu);
        },
        _getContent: function($menu) {
            var index = $menu.data('index');
            var selector = $menu.data('selector');
            var $content;

            if (selector) {
                $content = this.$contentItems.filter(selector);
            } else if (index != null) {
                $content = this.$contentItems.eq(index);
            }

            return $content;
        },
        getActive: function() {
            var res = {};
            res.$menu = this.$menuItems.filter('.z-active');
            res.$content = this._getContent(res.$menu);
            return res;
        }
    };

    var SegmentControlView = Backbone.View.extend({
        events: {
            'click .segment-menu-item': 'onClickSegmentMenuItem'
        },
        initialize: function() {
            this.$menuItems = this.$('.segment-menu-item');
            this.$contentItems = this.$('.segment-content-item');

            var $menu = this.$menuItems.filter('.z-active');
            $menu.length === 0 && ($menu = this.$menuItems.first());
            this.activate($menu);
        }
    });

    $.extend(SegmentControlView.prototype, mixins);
    SegmentControlView.extend = backboneCascadeExtend();


    var TabPanelView = Backbone.View.extend({
        events: {
            'click .tab-menu-item': 'onClickSegmentMenuItem'
        },
        initialize: function() {
            this.$menuItems = this.$('.tab-menu-item');
            this.$contentItems = this.$('.tab-content-item');

            var $menu = this.$menuItems.filter('.z-active');
            $menu.length === 0 && ($menu = this.$menuItems.first());
            this.activate($menu);
        }
    });

    $.extend(TabPanelView.prototype, mixins);
    SegmentControlView.extend = backboneCascadeExtend();


    exports.SegmentControlView = SegmentControlView;
    exports.TabPanelView = TabPanelView;
});