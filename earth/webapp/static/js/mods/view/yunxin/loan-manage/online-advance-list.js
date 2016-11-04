define(function(require, exports, module) {
    var TableContentView = require('baseView/tableContent');
    var popupTip = require('component/popupTip');
    var $ = jQuery;
    var root = global_config.root;
    var OnlineAdvanceList = TableContentView.extend({
        initialize: function() {
            OnlineAdvanceList.__super__.initialize.call(this, arguments);
            this.initDateTimePicker();
            $(window).on('click', function() {
                //点击屏幕关闭popover
                if ($('body').find('.popover').last().hasClass('clickShow')) {
                    $('body').find('.popover').popover('hide');
                }
            });

        },
        initDateTimePicker: function() {
            $('.datetimepicker-form-control').css('width', '135');
        },
        events: {
            'click .icon-bankcard': 'onClickBankcard',
            'mouseover .showPopover': 'showPopover',
            'mouseout .showPopover': 'showPopover',
            'click #exportData': 'clickExportData'
        },
        actions: {
            export: root + '/capital/plan/execlog/export'
        },
        showPopover: function(e) {
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
                //点击屏幕后移除active属性
                el.removeClass('active');
            });
            el.toggleClass('active');
            return false;
        },
        checkDate: function() {
            var starttimeEl = $('.starttime-datepicker').last();
            var startDateVal = starttimeEl.find('.datetimepicker-form-control').first().val();
            if (_.isEmpty(startDateVal)) {
                popupTip.show('请选择起始日期');
                return false;
            }
            var endtimeEl = $('.endtime-datepicker').last();
            var endDateVal = endtimeEl.find('.datetimepicker-form-control').first().val();

            var startDate = new Date(startDateVal);
            var endDate = new Date(endDateVal);
           
            var diff = endDate.getTime() - startDate.getTime();
            if (diff > 7 * 24 * 1000 * 60 * 60) {
                popupTip.show('不允许时间间隔超过7天');
                return false;
            }
            return true;
        },
        clickExportData: function(e) {
            e.preventDefault();
            if (!this.checkDate()) return;
            var data = $('#myForm').serialize();
            var opt = {
                url: this.actions.export,
                type: 'post',
                dataType: 'json',
                data: data
            };
            opt.success = function(resp) {
                if (resp.code == 0) {
                    window.location.href = root + '/capital/plan/execlog/export?' + data;
                } else {
                    popupTip.show(resp.message);
                }
            };
            $.ajax(opt);

        }
    });

    module.exports = OnlineAdvanceList;
});