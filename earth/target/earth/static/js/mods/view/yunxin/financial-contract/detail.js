define(function(require, exports, module) {
    var Pagination = require('component/pagination');

    var FinancialDetailView = Backbone.View.extend({
        el: '.content',
        events: {
            'click .export[data-action]': 'onClickExport'
        },
        initialize: function(opt) {
            this.financialContractId = opt.financialContractId;
            Pagination.find('.contracts', this.el);
        },
        onClickExport: function(e) {
            var action = $(e.target).data('action');
            if (!action) return;

            var els = this.$('.contracts tbody').children();
            var nomore = els.length == 0 || els.first().is('.nomore');
            if (nomore) return;

            var url = action + '?financialContractIds=["' + this.financialContractId + '"]';
            window.open(url, '_download');
        }
    });

    module.exports = FinancialDetailView;

});