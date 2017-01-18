define(function(require, exports, module) {
    var TableContentView = require('baseView/tableContent');
    var $ = jQuery;

    var PlanOrderListView = TableContentView.extend({
        initialize: function() {
            PlanOrderListView.__super__.initialize.call(this, arguments);
            this.initDateTimePicker();
        },
        initDateTimePicker: function() {
            $('.datetimepicker-form-control').css('width', '135');
        },
        collectSortParams: function(resultParams) {
            var sorts = this.tableListEl.find('thead .sort');
            var res = {};
            $.each(sorts, function(index, item) {
                var tar = $(item);
                var name = tar.data('paramname');
                var value = tar.hasClass('desc') ? 'DESC' : (tar.hasClass('asc') ? 'ASC' : '');
                if (name && value) {
                    res[name] = value === 'ASC';
                }
            });
            $.extend(resultParams, res);
            return resultParams;
        }
    });

    module.exports = PlanOrderListView;
});