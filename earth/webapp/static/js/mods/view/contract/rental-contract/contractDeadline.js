define(function(require, exports, module) {
    var baseFormView = require('baseView/baseFormView');
    var FieldsetView = baseFormView.FieldsetView;
    var DialogView = require('component/dialogView');

    // const
    var $ =  jQuery;

    var ContractDeadlineView = FieldsetView.extend({
        events: {
            'change .real-value': 'onChange'
        },
        initialize: function (options) {
            var self = this;
            this.sourceModel = options.sourceModel;
            this.sourceModel.on('contractdate:lack', function () {
                self.$('.beginend-datepicker .imitate-datetimepicker-input').addClass('error');
            });

            this.initDialog();
        },
        initDialog: function () {
            var self = this;
            var datetimepickerEl = self.$('.beginend-datepicker');

            // datetimepickerEl.find('.starttime-datepicker').on('clear', function (e) {
            //     self.onChange(e);
            // });
            // datetimepickerEl.find('.endtime-datepicker').on('clear', function (e) {
            //     self.onChange(e);
            // });

            var wrongDialogV = this.wrongDialogView = new DialogView({bodyInnerTxt: '合同期限与缴租阶段日期冲突，是否清除所有缴租阶段重新填写？'});

            var isSure = false;
            this.listenTo(wrongDialogV, 'goahead', function() {
                self.trigger('payperiod:clear');
                wrongDialogV.hide();
                isSure = true;
            }).listenTo(wrongDialogV, 'closedialog', function () {
                if(isSure) {
                    isSure = false;
                    return;
                }else {
                    isSure = false;
                    var formerDate = self.formerDate;
                    if(!formerDate) return;
                    var currentDate = self.model.toJSON();
                    var compare = self.compareDate(formerDate, currentDate);

                    self.model.set(compare);

                    var datetimepicker = datetimepickerEl.data('datepicker');

                    if(compare.effectiveTime) {
                        datetimepicker.updateStartDate(compare.effectiveTime);
                    }

                    if(compare.maturityTime) {
                        datetimepicker.updateEndDate(compare.maturityTime);
                    }
                }                
            });
        },
        compareDate: function (former, current) {
            var result = {};
            for(var prop in current) {
                if(former[prop] !== current[prop]) {
                    result[prop]=former[prop];
                }
            }
            return result;
        },
        onChange: function (e) {
            var name =  e.target.name;
            var value = e.target.value;

            var payperoid = this.sourceModel.paymentInfo.get('rentingIntervalList') || [];
            this.formerDate = this.model.toJSON(); // 保存副本
            this.model.set(name, value);

            if(!value) {
                if(payperoid.length>0) {
                    this.wrongDialogView.show();
                }
            }else {
                if(payperoid.length>0 && name === 'effectiveTime') {
                    var first = payperoid[0];
                    if(new Date(first.startDate) !== new Date(value)) {
                        this.wrongDialogView.show();
                    }
                }else if(payperoid.length>0 && name === 'maturityTime') {
                    var last = payperoid[payperoid.length-1];
                    if(new Date(last.endDate) > new Date(value)) {
                        this.wrongDialogView.show();
                    }
                }

                if(name === 'effectiveTime') {
                    var endDate = new Date(value);
                    endDate.setFullYear(endDate.getFullYear()+1);
                    endDate.setDate(endDate.getDate()-1);
                    var endTimeEl = this.$('.endtime-datepicker');
                    var effectiveTime = endDate.format('yyyy-MM-dd');
                    endTimeEl.find('input').val(effectiveTime);
                    this.model.set('maturityTime', effectiveTime);
                    // if(!endTimeEl.find('.real-value').val()) {
                    // }
                }
            }
        },
        validate: function () {
            var flag = true;

            var maturityTime = this.$('[name=maturityTime]');
            var issueTime = this.$('[name=issueTime]');
            var _issueTime = new Date(issueTime.val());
            var _maturityTime = new Date(maturityTime.val());
            if(_issueTime > _maturityTime) {
                flag = false;
                var label = $('<label id="issueTime_maturityTime_error" class="error" for="issueTime">合同签约日不能大于合同结束时间</label>');
                issueTime.parents('.datetimepick-wrapper').after(label);
                issueTime.parent().addClass('error');
                maturityTime.parent().addClass('error');
            }else if(_issueTime < _maturityTime) {
                issueTime.parent().removeClass('error');
                maturityTime.parent().removeClass('error');
                this.$('#issueTime_maturityTime_error').remove();
            }

            return flag;
        }
    });
    
    
    module.exports = ContractDeadlineView;

});