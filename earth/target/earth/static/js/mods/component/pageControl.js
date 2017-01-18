define(function(require, exports, module) {
    var defOptions = {
        pageIndex: 1,
        pageRecordNum: 12,
        curPageRecordNum: null,
        totalNum: false
    };

    var $ = jQuery;
    var $doc = $(document);
    var calculatePageNum = function(size, pageRecordNum) {
        var res = Math.max(1, Math.ceil(size / pageRecordNum));
        return res;
    };

    var PageControl = Backbone.View.extend({
        el: '.page-control',
        events: {
            'click .popup-redirect-form': 'openRedirectForm'
        },
        initialize: function(options) {
            this.indicateEl = this.$('.tip');
            this.nextEl = this.$('.next');
            this.prevEl = this.$('.prev');
            this.lastEl = this.$('.last-page');
            this.firstEl = this.$('.first-page');

            options = this.processOptions(options);
            this.setOptions(options);
        },
        render: function() {
            var totalPageNum = this.getTotalPageNum();

            if (this.pageIndex <= 1) {
                this.prevDisabled = true;
            } else {
                this.prevDisabled = false;
            }

            if (totalPageNum === 1
                || this.isGteTotalPageNum(this.pageIndex, true)
                || (this.curPageRecordNum != null && this.curPageRecordNum < this.pageRecordNum)) {
                this.nextDisabled = true;
            } else {
                this.nextDisabled = false;
            }

            if (this.prevDisabled) {
                this.prevEl.addClass('disabled');
                this.firstEl.addClass('disabled');
            } else {
                this.prevEl.removeClass('disabled');
                this.firstEl.removeClass('disabled');
            }

            if (this.nextDisabled) {
                this.lastEl.addClass('disabled');
                this.nextEl.addClass('disabled');
            } else {
                this.nextEl.removeClass('disabled');
                this.lastEl.removeClass('disabled');
            }

            if (totalPageNum !== 0 && !totalPageNum) {
                this.indicateEl.html('第' + this.pageIndex + '页');
            } else {
                this.indicateEl.html(this.pageIndex + '/' + totalPageNum + '页');
                this.$('.total').text(this.totalNum);
            }
        },
        reset: function() {
            this.setOptions({pageIndex: 1});
        },
        setOptions: function(opt) {
            $.extend(this, opt);
            this.render();
        },
        getOptions: function() {
            var opt = _.pick(this, 'pageIndex', 'pageRecordNum', 'curPageRecordNum', 'totalNum');
            return opt;
        },
        getTotalPageNum: function() {
            // 该属性是计算而来
            if (this.totalNum !== false) {
                return calculatePageNum(this.totalNum, this.pageRecordNum);
            } else {
                return false;
            }
        },
        processOptions: function(options) {
            var htmlOptions = this.processHtmlOptions(this.$el);

            var _options = $.extend({}, defOptions, options, htmlOptions);

            var val = this.$('.total').text().trim();
            _options.totalNum = val === '' ? false : +val;

            return _options;
        },
        processHtmlOptions: function(el) {
            var htmlOptions = el.data();
            var _htmlOptions = {};
            for (var prop in htmlOptions) {
                if (!htmlOptions[prop]) {
                    continue;
                }

                var _prop = prop.split('_');
                _prop = _prop.map(function(item, index) {
                    if (index === 0) return item;
                    return item[0].toUpperCase() + item.slice(1);
                });

                _htmlOptions[_prop.join('')] = htmlOptions[prop];
            }
            return _htmlOptions;
        },
        openRedirectForm: function() {
            var self = this;
            var redirectForm = this.$('.redirect-form');

            if (redirectForm.hasClass('hide')) {
                redirectForm.find('.page-index').val('').removeClass('form-control-error');
                redirectForm.removeClass('hide');
                $doc.on('click', function handler(e) {
                    if ($.contains(self.el, e.target)) return;
                    redirectForm.addClass('hide');
                    $doc.off('click', handler);
                });
            }
        },
        getCurrentPageIndex: function() {
            return this.pageIndex;
        },
        combineParams: function(params) {
            var search = {
                pageIndex: this.pageIndex,
                page: this.pageIndex, // 旧版后台需要page参数
                pageNumber: this.pageRecordNum
            };
            $.extend(search, params);

            return search;
        },
        // 当前索引是否大于等于总页数
        isGteTotalPageNum: function(index, isEqualDisabled) {
            var totalPageNum = this.getTotalPageNum();
            if (typeof totalPageNum !== 'number') {
                return false;
            } else {
                return isEqualDisabled === true ? index >= totalPageNum : index > totalPageNum;
            }
        },
        importPageIndexRedirect: function(params) {
            var pageIndexInput = this.$('.redirect-form .page-index');
            var index = +pageIndexInput.val();

            if (isNaN(index) || this.isGteTotalPageNum(index)) {
                pageIndexInput.addClass('form-control-error');
            } else {
                pageIndexInput.removeClass('form-control-error');
                this.redirect(index, params);
            }
        },
        redirect: function(pageIndex, params) {
            if (this.isGteTotalPageNum(pageIndex)) return;

            this.pageIndex = pageIndex || 1;

            var search = this.combineParams(params);

            this.getData(search, 'redirect');
        },
        first: function(params) {
            if (this.prevDisabled) return;
            this.redirect(1, params);
        },
        last: function(params) {
            if (this.nextDisabled) return;
            this.redirect(this.getTotalPageNum(), params);
        },
        next: function(params) {
            if (this.nextDisabled) return;

            this.pageIndex++;
            var search = this.combineParams(params);
            this.getData(search, 'next');
        },
        prev: function(params) {
            if (this.prevDisabled) return;

            this.pageIndex--;
            var search = this.combineParams(params);
            this.getData(search, 'prev');
        },
        getData: function(search, type, callback) {
            if (this.curXhrObj && !this.curXhrObj.isDone) {
                this.curXhrObj.abort();
            }

            var that = this;
            var opt = {
                url: this.url,
                data: search,
                dataType: 'json',
                type: 'get'
            };

            this.trigger('request', type, search);

            opt.success = function(resp, textStatus, xhr) {
                if (resp.code == 0) {

                    var arr;
                    var opt = {};

                    arr = !$.isArray(resp.data.list) ? JSON.parse(resp.data.list) : resp.data.list;
                    that.trigger(type + ':pagecontrol', arr, type, resp, search); // 传递原始的数据和查询参数

                    opt.curPageRecordNum = arr.length;

                    $.isFunction(callback) && callback(arr);

                    if (resp.data.size == null) {
                        opt.totalNum = false;
                    } else {
                        opt.totalNum = resp.data.size;
                        if (opt.totalNum == 0) {
                            opt.pageIndex = 1;
                        } else {
                            var pageNum = calculatePageNum(opt.totalNum, that.pageRecordNum);
                            if (pageNum < that.pageIndex) {
                                opt.pageIndex = pageNum;
                            }
                        }
                    }

                    that.setOptions(opt);
                }
            };

            opt.complete = function(xhr, textStatus) {
                that.curXhrObj.isDone = true;
            };

            this.curXhrObj = $.ajax(opt);
        },
        refresh: function(params) {
            var search = this.combineParams(params);
            this.getData(search, 'redirect');
        }
    });

    module.exports = PageControl;
});
