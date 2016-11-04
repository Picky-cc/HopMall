define(function(require, exports, module) {
    var $ = jQuery;

    var DEFAULT_OPTIONS = {
        action: '', // 像后台请求的URL，会忽视hints
        container: null, // 列表父亲元素
        search: {}, // 或者函数
        parse: function(resp) { return resp; },
        parcelItem: function(item) {},
        onSync: function() {},
        onClose: function(options, input) {},
        onSubmit: function(input, itemEl) {
            input.val(itemEl.text());
        }
    };

    var delay = function (func, interval) {
        var timer = null;
        return function () {
            if (timer) {
                clearTimeout(timer);
                timer = null;
            }

            var args = Array.prototype.slice.call(arguments);

            timer = setTimeout(function () {
                func.apply(null, args);
            }, interval || 500);

            return timer;
        };
    };

    $.fn.autocomplete = function(params) {
        params = $.extend(DEFAULT_OPTIONS, params);

        this.each(function() {
            var input = $(this);
            var prevInputVal = '';

            var proposals = params.container;
            var proposalList = $('<ul class="list">');

            var close = function(options) {
                if(proposals.is(':hidden')) return;
                proposalList.empty();
                proposals.hide();
                params.onClose(options, input);
            };

            var submit = function(el, eventType) {
                params.onSubmit(input, $(el), eventType);
                close({
                    triggerType: 'select'
                });
            };

            function getData(inputVal) {
                var search = params.search;
                if(typeof search === 'function') {
                    search = search(inputVal);
                }

                search.keyword = inputVal;

                var success = function (resp) {
                    proposalList.empty();
                    proposals.show();

                    var data = params.parse(resp);

                    if (data.length === 0) {
                        proposals.addClass('nomatch');
                    } else {
                        for (var i=0, len = data.length; i < len; i++) {
                            var el = params.parcelItem(data[i]);
                            proposalList.append(el);
                        }
                        proposals.html(proposalList);
                    }
                    params.onSync(data, inputVal, proposals);
                };

                if(params.action) {
                    $.getJSON(params.action, search, success);
                }else if(params.responseData) {
                    var filter = params.filter || function(resp, inputVal) {
                        return resp;
                    };

                    var res = filter(params.responseData, inputVal);

                    success(res);                    
                }
            }

            var callback = delay(function (inputVal) {
                getData(inputVal);
            }, 500);
            

            input.on('focus', function() {
                input.val() && getData(input.val());
            });

            proposals.on('click', '.item', function(e) {
                e.stopPropagation();
                submit(e.currentTarget, 'click');
            });

            input.on('keydown', function(e) {
                var items,
                    current,
                    active;
                switch (e.which) {
                    case 38: // Up arrow
                        items = proposals.find('.item');
                        current = items.filter('.selected').removeClass('selected');
                        if (current.length > 0) {
                            active = current.prev();
                        }

                        if (!active || active.length === 0) {
                            active = items.last();
                        }

                        active.addClass('selected');
                        break;
                    case 40: // Down arrow
                        items = proposals.find('.item');
                        current = items.filter('.selected').removeClass('selected');
                        if (current.length > 0) {
                            active = current.next();
                        }

                        if (!active || active.length === 0) {
                            active = items.first();
                        }

                        active.addClass('selected');
                        break;
                    case 13: // Enter
                        items = proposals.find('.item');
                        current = items.filter('.selected');
                        if (current.length > 0) {
                            submit(current, 'enter');
                        }
                        break;
                }
            }).on("keyup", function(e) {
                var inputVal = input.val();

                if (prevInputVal === inputVal) {
                    return;
                } else {
                    prevInputVal = inputVal;
                }

                if (inputVal === '') {
                    close({
                        triggerType: 'clear'
                    });
                    return;
                }

                if (~[13, 37, 38, 39, 40].indexOf(e.which) === 0) {
                    callback(inputVal);
                }
            });

            $(document).on('click', function(e) {
                if (e.target === input.get(0)) return;
                if ($.contains(proposals.get(0), e.target)) return;
                close({
                    triggerType: 'blur'
                });
            });

        });

        return this;
    };
});