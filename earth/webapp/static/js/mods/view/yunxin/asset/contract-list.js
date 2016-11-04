define(function(require, exports, module) {
    var TableContentView = require('baseView/tableContent');

    var ContractListView = TableContentView.extend({
        initialize: function() {
            ContractListView.__super__.initialize.apply(this, arguments);

            var self = this;
            var $num = self.$('.instruction .num');

            this.cache = {};

            this.on('checkall.tablelist', function(ischecked) {
                if (ischecked) {
                    var num = this.getPageControlOptions().totalNum;
                    $num.html(num);
                } else {
                    $num.html(0);
                }
            });

            this.on('checkitem.tablelist', function(ischecked) {
                var original = +$num.text().trim();
                var now = ischecked ? original + 1 : original - 1;
                $num.html(now);
            });

            this.trigger('refreshdata.tablelist', function(data, opepration, response, query) {
                $num.html(response.data.size);
            });
        },
        _queryByParams: function() {
            this.shouldChangePageIndex = false;
            var params = this.collectParams();
            this.pageControl.redirect(1, params);

            this.cache = {};
        },
        match: function(pageIndex, id) {
            var page = this.cache[pageIndex];
            var item = null;
            if (page) {
                item = _.findWhere(page, {id: id});
            }
            return item;
        },
        checkAll: function(flag, target) {
            this.allChecked = flag;
            this.trigger('checkall.tablelist', flag, target);
        },
        checkItem: function(flag, target) {
            this.itemChecked = flag;
            this.trigger('checkitem.tablelist', flag, target);
        },
        polish: function(list) {
            var self = this;
            var allChecked = this.allChecked;
            var pageIndex = this.getPageControlOptions().pageIndex;
            var page = this.cache[pageIndex];

            if (!page) {
                page = this.cache[pageIndex] = list;
            }

            list.forEach(function(item) {
                var lastItem = self.match(pageIndex, item.id);
                item.isChecked = lastItem ? lastItem.isChecked : allChecked;
            });

            return list;
        }
    });

    module.exports = TableContentView;

});