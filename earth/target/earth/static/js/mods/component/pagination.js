define(function(require, exports, module) {
    var PageControl = require('./pageControl');
    var util = require('scaffold/util');

    var $ = jQuery;
    var loadingImg = global_const.loadingImg;
    var extractParamFromDifferentDom = util.extractValueFromDifferentDom;

    var Pagination = Backbone.View.extend({
        events: {
            'click .redirect,.prev,.next,.first-page,.last-page': 'onClickPageNavigateBtn'
        },
        initialize: function() {
            var tmplStr = this.$('.template').html() || '';
            if (!tmplStr) return;

            this.template = _.template(tmplStr);
            this.tableListEl = this.$('table[data-action]');
            this.rowNum = this.tableListEl.find('thead th').length;
            this.lookupParamsEl = this.$('.lookup-params');
            this.queryAction = this.tableListEl.data('action');
            this.pageControl = new PageControl({
                el: this.$('.page-control').get(0),
                url: this.queryAction
            });

            this.listenTo(this.pageControl, 'next:pagecontrol prev:pagecontrol redirect:pagecontrol', this.refreshTableDataList);
            this.listenToOnce(this.pageControl, 'request', function() {
                var htm = '<tr><td style="text-align: center;" colspan="' + this.rowNum + '"></td></tr>';
                var el = $(htm).find('td').html(loadingImg.clone());
                this.tableListEl.find('tbody').html(el);
            });

            this.getFirstData();
        },
        refreshTableDataList: function(data, opepration, response, query) {
            if (!this.template) return;
            var htm;
            if (data.length < 1) {
                htm = '<tr class="nomore"><td style="text-align: center;" colspan="' + this.rowNum + '">没有更多数据</td></tr>';
            } else {
                htm = this.template({list: this.polish(data)});
            }
            this.tableListEl.find('tbody').html(htm);
            this.trigger('refresh', data, opepration, response, query);
        },
        collectQueryParams: function(resultParams) {
            var paramsItems = this.lookupParamsEl.find('.real-value');

            for (var i = 0, len = paramsItems.length; i < len; i++) {
                $.extend(resultParams, extractParamFromDifferentDom(paramsItems.eq(i)));
            }

            return resultParams;
        },
        collectSortParams: function(resultParams) {
            var order = this.tableListEl.find('thead .sort');
            var res = {};
            $.each(order, function(index, item) {
                var tar = $(item);
                var name = tar.data('paramname');
                var value = tar.hasClass('desc') ? 'DESC' : (tar.hasClass('asc') ? 'ASC' : '');
                if (name && value) {
                    res[name] = value;
                }
            });

            $.extend(resultParams, res);

            return resultParams;
        },
        collectParams: function() {
            var resultParams = {};

            this.collectQueryParams(resultParams);
            this.collectSortParams(resultParams);

            return resultParams;
        },
        getFirstData: function() {
            var params = this.collectParams();
            this.pageControl.redirect(1, params);
        },
        query: function() {
            var params = this.collectParams();
            this.pageControl.redirect(1, params);
        },
        polish: function(list) {
            return list;
        },
        onClickPageNavigateBtn: function(e) {
            e.preventDefault();

            var tar = $(e.target);
            var params = this.collectParams();

            if (tar.hasClass('prev')) {
                this.pageControl.prev(params);
            } else if (tar.hasClass('next')) {
                this.pageControl.next(params);
            } else if (tar.hasClass('redirect')) {
                this.pageControl.importPageIndexRedirect(params);
            } else if (tar.hasClass('first-page')) {
                this.pageControl.first(params);
            } else if (tar.hasClass('last-page')) {
                this.pageControl.last(params);
            }
        }
    });

    Pagination.find = function(selector, ctx) {
        var Constructor = this;
        var els = $(ctx || document).find(selector);
        for (var i = 0; i < els.length; i++) {
            var $el = els.eq(i);
            var obj = $el.data('pagination');
            if (!obj) {
                $el.data('pagination', new Constructor({el: $el[0]}));
            }
        }
    };

    module.exports = Pagination;


});