define(function(require, exports, module) {
    var popupTip = require('component/popupTip');
    var TableContentView = require('baseView/tableContent');
    var FormDialogView = require('baseView/baseFormView').FormDialogView;
    var $ = jQuery;
    var root = global_config.root;

    var BatchLoanInfoView = FormDialogView.extend({
        className: 'modal fade form-modal large-form-modal in',
        template: _.template($('#batchLoanInfoTmpl').html(), {
            variable: 'data'
        }),
        action: {
            confirmLoan: root + ' '
        },
        submitHandler: function() {
            // var opt = {
            //     url: this.action.confirmLoan,
            //     dataType: 'json',
            //     data: ''
            // };
            // opt.success = function(resp) {
            //     if (resp.code === 1) {

            //     } else {
            //         popupTip.show(resp.message);
            //     }
            // };
            // $.ajax(opt);
        }
    });

    var TableView = TableContentView.extend({
        initialize: function() {
            TableView.__super__.initialize.call(this, arguments);
            $(window).on('click', function() {
                if ($('body').find('.popover').last().hasClass('clickShow')) {
                    $('body').find('.popover').popover('hide');
                }
            });
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
        },
        events: {
            'click #batchLoan': 'onClickBatchLoan',
            'click .icon-bankcard': 'onClickBankcard',
            'mouseover .showPopover': 'showPopover',
            'mouseout .showPopover': 'showPopover'
        },
        action: {
            batchLoan: root + ''
        },
        showPopover: function(e) {
            e.preventDefault();
            var el = $(e.target);
            var popoverContent = el.siblings('.account').html();
            el.popover({
                content: popoverContent
            });
            el.popover('toggle');
        },
        onClickBankcard: function(e) {
            e.preventDefault();
            this.showPopover(e);
            var el = $(e.target);
            el.on('shown.bs.popover', function() {
                $('body').find('.popover').last().addClass('clickShow');
            });
            el.on('hidden.bs.popover', function() {
                el.removeClass('active');
            });
            el.toggleClass('active');
            return false;
        },
        onClickBatchLoan: function(e) {
            e.preventDefault();
            var view = new BatchLoanInfoView();
            view.show();

            // if (this.loading) return;
            // this.loading = true;

            // var self = this;
            // var opt = {
            //     url: this.action.batchLoan,
            //     data: '',
            //     dataType: 'json'
            // };
            // opt.success = function(resp) {
            //     if (resp.code == 0) {
            //         var model = new Backbone.Model(resp.data);
            //         var view = new BatchLoanInfoView({
            //             model: model
            //         });
            //         view.show();
            //     } else {
            //         popupTip.show(resp.message);
            //     }
            // };
            // opt.complate = function() {
            //     self.loading = false;
            // };
            // $.ajax(opt);
        }
    });

    module.exports = TableView;
});