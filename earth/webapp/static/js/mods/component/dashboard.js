define(function(require, exports, module) {
    var Backdrop = require('./backdrop');
    var pathHelper = require('scaffold/path');
    var SStorage = sessionStorage;
    var loadingImg = global_const.loadingImg;

    var DashboardView = Backbone.View.extend({
        events: {
            'click [data-key]': 'onClickTabMenuItem',
            'change .selectpicker': 'onChangeSelectpicker',
            'click a[data-path]': 'onClickHref'
        },
        initialize: function() {
            this.tabContentCache = {};
            this.tabContentEl = this.$('.tab-content');
            this.tabMenuEl = this.$('.nav-tabs');
            this.selectpickerEl = this.$('.selectpicker');
            this.backdrop = new Backdrop({ zIndex: 7 });

            this.on('request', function() {
                this.tabContentEl.html(loadingImg.clone());
            });

            this.initActiveTab();

            if (this.$el.data('disable-toggle')) {
                this.fetchTabContent();
                return;
            }

            var self = this;
            var $btnToggleDashboard = $('.web-g-hd .btn-toggle-dashboard');

            this.listenTo(this.backdrop, 'close:backdrop', function() {
                this.$el.addClass('hide');
            });
            this.listenTo(this.backdrop, 'open:backdrop', function() {
                this.$el.removeClass('hide');
                this.fetchTabContent();
            });

            $btnToggleDashboard.on('click', function() {
                self.backdrop.toggle();
            });

            $(window).on('keyup.dashboard', function(e) {
                if (e.keyCode == 27) {
                    self.backdrop.hide();
                }
            });
        },
        initActiveTab: function() {
            if (SStorage) {
                var activeTabKey = SStorage.getItem('active_tab_key');
                var $activeTab = this.$el.find('a[data-key='+ activeTabKey +']').parent('li');
            }

            if (!$activeTab || $activeTab.length == 0) {
                $activeTab = this.$el.find('.nav-tabs li').first();
            }

            $activeTab.addClass('active');
        },
        onClickHref: function(e) {
            e.preventDefault();

            var $target = $(e.currentTarget);
            var attr = this.getSeletedProject();

            var appendHash = pathHelper.stringifyQueryObject(attr);
            var appendSearch = 't=' + Date.now();

            var path = $target.data('path');
            var hash = $target.data('hash');
            var search = $target.data('search');

            search = search ? (search + '&' + appendSearch) : appendSearch;
            hash = hash ? (hash + '&' + appendHash) : appendHash;

            location.assign(encodeURI(path + '?' + search + '#' + hash));
        },
        onChangeSelectpicker: function() {
            this.fetchTabContent();
        },
        onClickTabMenuItem: function(e) {
            e.preventDefault();

            var $target = $(e.currentTarget);
            var $parent = $target.parent();

            if ($parent.is('.active')) return;

            this.tabMenuEl.children().removeClass('active');
            $parent.addClass('active');

            if (SStorage) {
                SStorage.setItem('active_tab_key', $target.attr('data-key'));
            }

            this.fetchTabContent();
        },
        getActiveTabMenu: function() {
            return this.tabMenuEl.find('.active');
        },
        getActiveTabMenuHref: function() {
            var $active = this.getActiveTabMenu();
            var $target = $active.find('a');
            var href = $target.data('href');
            return href;
        },
        getSeletedProject: function() {
            var attr = {};
            var val = this.selectpickerEl.selectpicker('val');
            attr.financialContractIds = val ? JSON.stringify(val) : '[]';
            return attr;
        },
        fetchTabContent: function() {
            var self = this;
            var href = this.getActiveTabMenuHref();

            if (!href) return;

            var attr = this.getSeletedProject();

            self.trigger('request');

            $('<div/>').load(href, attr, function(resp, statusTxt, xhr) {
                if (statusTxt == 'error' || xhr.status != 200) {
                    self.tabContentEl.html('系统异常请稍候重试');
                } else if (!$(resp).data('dashboard-item')) {
                    self.tabContentEl.html('系统异常请稍候重试');
                } else {
                    self.tabContentEl.html(resp);

                    var total = 0;
                    self.tabContentEl.find('[data-total]').each(function() {
                        total += +$(this).data('total');
                    });
                    var $active = self.getActiveTabMenu();
                    $active.find('.total').html(total);
                }
                self.trigger('refresh', resp, statusTxt);
            });
        }
    });

    var dashboardEl = $('.dashboard');
    if (dashboardEl.length > 0) {
        module.exports = new DashboardView({ el: dashboardEl.get(0) });
    }

});
