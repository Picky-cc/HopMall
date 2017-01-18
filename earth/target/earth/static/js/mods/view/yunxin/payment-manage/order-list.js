define(function (require, exports, module) {
    var TableContentView = require('baseView/tableContent');
    var popupTip = require('component/popupTip');

    var $ = jQuery;

    module.exports = TableContentView.extend({
        collectSortParams: function(params) {
            var res = {};
            var orders = this.tableListEl.find('thead .sort');
            for(var i=0, len=orders.length; i<len; i++) {
                var tar = orders.eq(i);
                var name = tar.data('paramname');
                var value = tar.hasClass('desc') ? 'DESC' : (tar.hasClass('asc') ? 'ASC' : '');
                if(name && value) {
                    res.sortField = name;
                    res.isAsc = value === 'ASC';
                    break;
                }
            }

            $.extend(params, res);
            return params;
        }
    });

});