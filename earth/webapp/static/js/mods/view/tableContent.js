define(function(require, exports, module) {
    /**
     * 视图事件：
     * refreshdata.tablelist: 刷新列表后触发事件
     * checkall.tablelist: 全选后触发事件
     * checkitem.tablelist: 单选后触发事件
     * checkbox.tablelist: 全选，单选后均会触发事件，为了兼容以前的代码
     */

    var util = require('scaffold/util');
    var PageControl = require('component/pageControl');
    var path = require('scaffold/path');

    var $ = jQuery;
    var loadingImg = global_const.loadingImg.clone();
    var extractParamFromDifferentDom = util.extractValueFromDifferentDom;
    var isIE = util.isIE();

    // backbone不能合并父级的events和initialize，默认只会覆盖，所以需要自己调用
    var TableContentView = Backbone.View.extend({
        template: _.template($('#tableFieldTmpl').html() || ''),
        el: '.content',
        events: {
            'click #lookup': '_queryByParams',
            'change .lookup-params': function(e) {
                // 如果是时间选择框的话会触发两次，事件冒泡
                if ($(e.target).parents('.operations').length) return;
                if ($(e.target).is('[autoquery=false]')) return;
                this._queryByParams();
            },
            'keyup .lookup-params': function(e) {
                if ($(e.target).parents('.operations').length) return;
                if (isIE && e.which == 13) {
                    $(e.target).change();
                }
            },

            'click .redirect,.prev,.next,.first-page,.last-page': 'onClickPageNavigateBtn',
            'keyup .redirect-form': 'onKeyupRedirectForm',
            'click .check-box': 'onClickCheckbox',
            'click .clear-input': 'onClickClearInput',
            'click .sort': 'onClickSortBtn',
            'change .real-value': 'onChangeFormValue',
            'click #exportExcel,.export-excel,.export[data-action]': 'onClickExport'
        },
        initialize: function() {
            this.lookupParamsEl = this.$('.lookup-params').first();
            this.tableListEl = this.$('.data-list');

            this.queryAction = this.tableListEl.data('action');
            this.autoload = this.tableListEl.data('autoload');
            this.initialQueryParams = this.parseInitialQueryParams();

            this.initLookupParams();
            this.initSortParams();
            this.initPageControl();
            this.initCheckOperate();

            $(document).on('click', 'a[href]', $.proxy(function(e) {
                if (history.replaceState) {
                    var $target = $(e.target);
                    if ($target.attr('target')) return;

                    var href = $target.attr('href');
                    if (!href || href === '#' || /^javascript:/.test(href)) return;

                    var want = e.target.href.replace(/#.*/, '');
                    var curr = location.href.replace(/#.*/, '');
                    if (want == curr) return;

                    var params = this.collectParams();
                    params.pageIndex = this.pageControl.pageIndex;
                    var search = encodeURI(path.stringifyQueryObject(params));
                    history.replaceState({}, '', '#' + search);
                }
            }, this));

            if (this.autoload) {
                this.getFirstPageData();
            }
        },
        initCheckOperate: function() {
            var self = this;
            var execCheck = function(els, isChecked) {
                for (var i = 0; i < els.length; i++) {
                    var one = els.eq(i);
                    if (one.is(':disabled') || one.is('.disabled')) continue;
                    one[0].checked = isChecked;
                }
            };

            this.on('checkall.tablelist', function(isChecked, target) {
                var checkboxs = self.$('.check-box');
                execCheck(checkboxs, isChecked);

                self.trigger('checkbox.tablelist', isChecked, target);
            });

            this.on('checkitem.tablelist', function(isChecked, target) {
                if (isChecked === false) {
                    var checkboxalls = self.$('.check-box-all');
                    execCheck(checkboxalls, isChecked);

                    self.trigger('checkbox.tablelist', isChecked, target);
                }
            });

            // 不能写死呀
            // this.on('refreshdata.tablelist', function() {
            //     self.checkAll(false);
            // });
        },
        initSortParams: function() {
            var query = this.initialQueryParams;
            var sorts = this.tableListEl.find('.sort[data-paramname]');

            for (var i = 0; i < sorts.length; i++) {
                var el = sorts.eq(i);
                var name = el.data('paramname');
                if (!name) continue;

                var value = query[name];
                if (value) {
                    el.removeClass('none desc asc');
                    el.addClass(value.toLowerCase());
                }
            }
        },
        initLookupParams: function() {
            var query = this.initialQueryParams;
            var params = this.lookupParamsEl.find('.real-value');

            for (var i = 0; i < params.length; i++) {
                var el = params.eq(i);
                var name = el.data('paramname') || el.data('params') || el.attr('name');
                if (!name) continue;

                var value = query[name];

                if (el.is('.selectpicker')) {
                    if (value != null) {
                        el.val(value);
                    } else {
                        el.find('option').prop('selected', true);
                    }
                } else {
                    if (value != null) {
                        el.val(value);
                        var dateEl = el.prev('.datetimepicker-form-control');
                        if (dateEl.length > 0) {
                            dateEl.val(value);
                        }
                    }
                }
            }
        },
        initPageControl: function() {
            var tableListEl = this.tableListEl;
            var pageIndex = this.initialQueryParams.pageIndex != null ? this.initialQueryParams.pageIndex : 1;
            var options = {
                el: this.$('.page-control').get(0),
                url: this.queryAction,
                pageIndex: pageIndex
            };

            this.pageControl = new PageControl(options);

            this.pageControl.on('next:pagecontrol prev:pagecontrol redirect:pagecontrol', $.proxy(this.refreshTableDataList, this));
            this.pageControl.once('request', function() {
                var htm = '<tr><td style="text-align: center; padding: 25px 0;" colspan="100"></td></tr>';
                var el = $(htm).find('td').html(loadingImg);
                tableListEl.find('tbody').html(el);
            });
        },
        parseInitialQueryParams: function() {
            var search = decodeURI(location.hash.slice(1));
            var query = path.parseQueryString(search);
            return query;
        },
        getFirstPageData: function() {
            var params = this.collectParams();
            var index = this.pageControl.pageIndex;
            this.pageControl.redirect(index, params);
        },
        polish: function(list) {
            return list;
        },
        setPageControlOptions: function(options) {
            this.pageControl.setOptions(options);
        },
        getPageControlOptions: function() {
            return this.pageControl.getOptions();
        },
        refreshTableDataList: function(data, opepration, response, query) {
            // 可由子视图继承
            if (!this.template) return;
            var htm;
            if (data.length < 1) {
                htm = '<tr class="nomore"><td style="text-align: center;" colspan="100">没有更多数据</td></tr>';
            } else {
                htm = this.template({list: this.polish(data)});
            }
            this.tableListEl.find('tbody').html(htm);

            this.trigger('refreshdata.tablelist', data, opepration, response, query);
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
        _queryByParams: function() {
            this.shouldChangePageIndex = false;
            var params = this.collectParams();
            this.pageControl.redirect(1, params);
        },
        /*
         * 查询且回到首页
         * 主要是给_queryByParams定一个更好的名字，因为这名字当初起的不太好，而_queryByParams又被外面广泛使用了。。。
         */
        query: function() {
            this._queryByParams();
        },
        /*
         * 查询但留在当页
         */
        refresh: function() {
            var params = this.collectParams();
            this.pageControl.refresh(params);
        },
        checkAll: function(flag, target) {
            this.allChecked = flag;
            this.trigger('checkall.tablelist', flag, target);
        },
        checkItem: function(flag, target) {
            this.itemChecked = flag;
            this.trigger('checkitem.tablelist', flag, target);
        },

        onClickCheckbox: function(e) {
            var flag = e.target.checked;
            var tar = $(e.target);

            tar.is('.check-box-all') ? this.checkAll(flag, tar) : this.checkItem(flag, tar);
        },
        onChangeFormValue: function() {
            /**
             * 这个参数用来维护输入了查询参数，直接点了上/下/跳转页按钮
             */
            this.shouldChangePageIndex = true;
        },
        onClickClearInput: function(e) {
            $(e.target).prev().val('');
        },
        onKeyupRedirectForm: function(e) {
            if (e.keyCode === 13) {
                if (this.shouldChangePageIndex === true) {
                    this._queryByParams();
                    return;
                }

                var params = this.collectParams();
                this.pageControl.importPageIndexRedirect(params);
            }
        },
        onClickPageNavigateBtn: function(e) {
            e.preventDefault();

            if (this.shouldChangePageIndex === true) {
                this._queryByParams();
                return;
            }

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
        },
        onClickSortBtn: function(e) {
            var tar = $(e.currentTarget);
            var order = this.tableListEl.find('thead .sort');
            order.not(tar.get(0)).removeClass('desc asc').addClass('none');
            if (tar.hasClass('desc')) {
                tar.removeClass('desc').addClass('asc');
            } else if (tar.hasClass('asc')) {
                tar.removeClass('asc').addClass('desc');
            } else {
                tar.removeClass('none').addClass('desc');
            }

            this._queryByParams();
        },
        onClickExport: function(e) {
            e.preventDefault();

            var action = $(e.target).data('action');
            if (!action) return;

            var els = this.tableListEl.find('tbody').children();
            var nomore = els.length == 0 || els.first().is('.nomore');
            if (nomore) return;

            var url = path.format({
                path: action,
                query: this.collectParams()
            });

            // 没下完不能关闭
            window.open(url, '_download');
        }
    });

    // http://danhough.com/blog/backbone-view-inheritance/
    // 默认的extend后覆盖父亲的events，这里进行合并
    // 事件可以一级一级继承下来
    TableContentView.extend = function(protoProps) {
        var view = Backbone.View.extend.apply(this, arguments);
        view.prototype.events = _.extend({}, this.prototype.events, protoProps.events);
        return view;
    };

    module.exports = TableContentView;
});