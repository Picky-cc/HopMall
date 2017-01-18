define(function(require, exports, module) {
    // module
    var AreaSelectView = require('component/areaSelect');
    var popupTip = require('component/popupTip');
    var baseFormView = require('baseView/baseFormView');
    var PayStageItemModel = require('entity/contract/leasingContractModel').PayStageItemModel;
    var ExtraRuleItemModel = require('entity/contract/leasingContractModel').ExtraRuleItemModel;

    var FieldsetView = baseFormView.FieldsetView;
    var SketchItemView = baseFormView.SketchItemView;
    var FormDialogView = baseFormView.FormDialogView;

    // const
    var $ =  jQuery;

    var PayStageItemView = SketchItemView.extend({
        template: _.template($('#payStageItemTmpl').html()),
        initialize: function () {
            PayStageItemView.__super__.initialize.apply(this, arguments);
            
            var self = this;
            var error = this.errorLabel = $('<label class="error-abnormal" for="">'); // 渲染的事情都交给它自己的视图
            this.listenTo(this.model, 'payperiod:validate', function (result, msg) {
                if(!result) {
                    self.$el.addClass('error').after(error.show().html(msg));
                }else {
                    self.$el.removeClass('error');
                    error.remove();
                }
            });
        },
        toDelete: function (e) {
            var tar = $(e.target);
            if(tar.hasClass('disabled') || tar.attr('disabled')) return;
            if(this.modelIsLast()) {
                this.model.destroy();
            }
        },
        toEdit: function () {
            var editView = new EditPayStageView({
                model: this.model
            });
            editView.show();
        },
        remove: function () {
            this.errorLabel.remove();
            PayStageItemView.__super__.remove.apply(this, arguments);
        }
    });
    
    var ExtraRuleItemView = SketchItemView.extend({
        template: _.template($('#extraRuleItemTmpl').html()),
        toEdit: function () {
            var editView = new EditExtraRuleView({
                model: this.model
            });
            editView.show();
        },
        render: function() {
        	var data  = this.model.toJSON();
        	var clauseTypes = this.model.getAllClauseTypes();
        	var selected = _.findWhere(clauseTypes, {value: data.clauseType});
        	data.clauseTypeAlias = selected.name;
        	
        	var htm = this.template(data);
        	this.$el.html(htm);
        	
        	return this;
        }
    });


    var EditView = FormDialogView.extend({
        className: 'modal fade form-modal contract-date-limit',
        initialize: function () {
            EditView.__super__.initialize.apply(this, arguments);

            this.datepicker = this.$('.beginend-datepicker');

            this.initValidator();
        },
        initValidator: function() {
            this.validator = this.$('.form').validate({
                ignore: 'input:disabled',
                rules: {
                    amountPerMonth: {
                        positiveNumber: true
                    }
                },
                onfocusout: false,
                onkeyup: false,
                errorPlacement: function(error, element) {
                    var parent = element.parent();
                    if(parent.is('.parcel-input')) {
                        error.insertAfter(parent);
                    }else if(element.is('[name=paymentPeriod]')) {
                        
                    }else {
                        error.insertAfter(element);
                    }
                }
            });
        },
        remove: function () {
            var datepicker = this.datepicker.data('datepicker');
            datepicker && datepicker.remove();
            EditView.__super__.remove.apply(this, arguments);
        },
        validate: function (result) {
            var basicValide = this.validator.form();
            return basicValide;
        },
        submitHandler: function (e) {
            var result = this._extractDomData(this.$('.real-value'), true);
            if(!this.validate(result)) return;
            this.model.set(result);
            this.hide();
        }
    });

    var EditPayStageView = EditView.extend({
        template: _.template($('#editPayStageTmpl').html(), {variable: 'obj'}),
        render: function () {
            var model = this.model;
            var collection = model.collection;

            var data = model.toJSON();
            data.limitDate = {};
            if(collection) { // 更新
                data.limitDate={
                    startDate: false,
                    endDate: collection.last() === model // true: 能编辑  false: 不能编辑
                };
            }else { // 新增
                data.limitDate={
                    startDate: data.startDate && false, // 是否是从上一个缴租阶段得到的开始时间
                    endDate: true
                };
            }
            this.$el.html(this.template(data));
        },
        validate: function (result) {
            var basicValide = this.validator.form();
            
            var flag = true;
            var starttimeDatepicker = this.$('.starttime-datepicker');
            var endtimeDatepicker = this.$('.endtime-datepicker');

            if(!result.startDate) {
                flag = false;
                starttimeDatepicker.addClass('error');
            }else {
                starttimeDatepicker.removeClass('error');
            }
            if(!result.endDate) {
                flag = false;
                endtimeDatepicker.addClass('error');
            }else {
                endtimeDatepicker.removeClass('error');
            }

            if(!flag) {
                return flag && basicValide;
            }

            var deadLine = this.model.getContractDeadLine();
            var label = this.$('#deadline_error');
            label.length < 1 && (label = $('<label id="deadline_error" class="error-abnormal">'));

            var effectiveTimeValid = deadLine.effectiveTime && new Date(deadLine.effectiveTime) > new Date(result.startDate);
            var maturityTimeValid = deadLine.maturityTime && new Date(deadLine.maturityTime) < new Date(result.endDate);
            if(effectiveTimeValid || maturityTimeValid) {
                flag = false;
                effectiveTimeValid ? starttimeDatepicker.addClass('error') : starttimeDatepicker.removeClass('error');
                maturityTimeValid ? endtimeDatepicker.addClass('error') : endtimeDatepicker.removeClass('error');
                this.$('.datetimepick-wrapper').after(label.html('不能超过合同期限('+deadLine.effectiveTime+'至'+deadLine.maturityTime+')'));
            }else {
                label.remove();
                starttimeDatepicker.removeClass('error');
                endtimeDatepicker.removeClass('error');
            }

            if(!flag) {
                return flag && basicValide;
            }

            // flag = this._validatePeriodMonth(result);

            return flag && basicValide;
        },
        _validatePeriodMonth: function (result) {
            if(!result.paymentPeriod) return false;
            var startDate = new Date(result.startDate);
            var endDate = new Date(result.endDate);
            var year = endDate.getFullYear() - startDate.getFullYear();
            var month = endDate.getMonth() - startDate.getMonth() + 1;
            var periodMonth = year*12 + month;
            var label = this.$('#paymentPeriodLimit_error');
            label.length < 1 && (label = $('<label id="paymentPeriodLimit_error" class="error-abnormal">'));
            // if(+result.paymentPeriod > periodMonth) {
            //     this.$('[name=paymentPeriod]').addClass('error').after(label.html('缴租周期不能大于阶段周期间隔'));
            //     return false;
            // }else {
            //     this.$('[name=paymentPeriod]').removeClass('error');
            //     label.remove();
            //     return true;
            // }
            this.$('[name=paymentPeriod]').removeClass('error');
            label.remove();
            return true;
        },
    });

    var EditExtraRuleView = EditView.extend({
        template: _.template($('#editExtraRuleTmpl').html(), {variable: 'obj'}),
        events: {
            'click [name=fixed]': 'onClickFixed',
            'change [name=paymentPeriod]': 'onChangePaymentPeriod'
        },
        onChangePaymentPeriod: function(e) {
            var payDayInMonthEl = this.$('[name="payDayInMonth"]');
            var optionEl = payDayInMonthEl.find('option').first();
            if($(e.target).val() == -1) {
                optionEl.get(0).selected = true;
                payDayInMonthEl.get(0).disabled = true;
            }else {
                payDayInMonthEl.get(0).disabled = false;
            }
        },
        onClickFixed: function(e) {
            var checked = e.target.checked;
            var el = this.$('[name=amountPerMonth]');
            if(checked) {
                el.val('');
                el.get(0).disabled = true;
            }else {
                el.get(0).disabled = false;
            }
        },
        render: function() {
            var data = this.model.toJSON();
            data.clauseTypes = this.model.getRemainClauseTypes();
            var str = this.template(data);
            this.$el.html(str);
            return this;
        }
    });


    var RentView = FieldsetView.extend({
        events: {
            'click .invoicing': 'invoicingHandler',
            'click .add-pay-stage': 'beforeAddSketchViewHandler',
            'click .add-extra-rule': 'beforeAddSketchViewHandler'
        },
        initialize: function (options) {
            RentView.__super__.initialize.apply(this, arguments);
            this.invoiceForm = this.$('.sub-form');
            var instance = new AreaSelectView({
                el: this.$('.area-select')
            });

            this.sourceModel = options.sourceModel;

            var payStageCollection = this.payStageCollection = new Backbone.Collection(this.model.get('rentingIntervalList'), {
                model: PayStageItemModel,
                sourceModel: options.sourceModel
            });
            this.listenTo(payStageCollection, 'add', this.addOnePayStage);
            this.listenTo(payStageCollection, 'all', function () {
                this.model.set('rentingIntervalList', payStageCollection.toJSON());
            });

            var extraRuleCollection = this.extraRuleCollection = new Backbone.Collection(this.model.get('supplementaryTermInfo').supplementaryTermList, {
                model: ExtraRuleItemModel
            });
            this.listenTo(extraRuleCollection, 'add', function(model) {
                this.addOneExtraRule(model);
                if(!this.isRemainClauseType()) {
                    this.$('.add-extra-rule').parent().hide();
                }
            });
            this.listenTo(extraRuleCollection, 'remove', function(model) {
                this.$('.add-extra-rule').parent().show();
            });
            this.listenTo(extraRuleCollection, 'all', function () {
                var tmp = this.model.get('supplementaryTermInfo');
                tmp.supplementaryTermList = extraRuleCollection.toJSON();
                this.model.set('supplementaryTermInfo', tmp);
            });
            
            this.addAllPayStage();
            this.addAllExtraRule();
        },
        isRemainClauseType: function() {
            var model = new ExtraRuleItemModel({}, {collection: this.extraRuleCollection});
            return model.getRemainClauseTypes().length != 0;
        },
        addOnePayStage: function (model) {
            var itemView = new PayStageItemView({model: model});
            this.$('.paystages').append(itemView.render().$el);
            this.$('.add-pay-stage').removeClass('error');
        },
        addAllPayStage: function () {
            var arr = this.payStageCollection;
            for(var i=0, len = arr.length; i<len; i++) {
                this.addOnePayStage(arr.at(i));
            }

            if(!this.isRemainClauseType()) {
                this.$('.add-extra-rule').parent().hide();
            }
        },
        addOneExtraRule: function(model) {
            var itemView = new ExtraRuleItemView({model: model});
            this.$('.extrarules').append(itemView.render().$el);
            this.$('.add-extra-rule').removeClass('error');
        },
        addAllExtraRule: function() {
            var arr = this.extraRuleCollection;
            for(var i=0, len = arr.length; i<len; i++) {
                this.addOneExtraRule(arr.at(i));
            }
        },
        clearWritedStage: function () {
            var collection = this.payStageCollection;
            while(collection.length) {
                collection.last().destroy();
            }

            // collection = this.extraRuleCollection;
            // while(collection.length) {
            //     collection.last().destroy();
            // }
        },
        beforeAddSketchViewHandler: function(e) {
            e.preventDefault();

            var $target = $(e.target);
            var sourceModel = this.sourceModel;
            var deadLine = sourceModel.contractBasicInfo.toJSON();

            if(!deadLine || !deadLine.effectiveTime || !deadLine.maturityTime) {
                sourceModel.trigger('contractdate:lack');
                location.href='#contractDeadlineAnchor';
                return;
            }

            if($target.is('.add-pay-stage')) {
                this.addPayStageHandler();
            }else if($target.is('.add-extra-rule')) {
                this.addExtraRuleHandler();
            }
        },
        addExtraRuleHandler: function() {
            var collection = this.extraRuleCollection;
            var deadLine = this.sourceModel.contractBasicInfo.toJSON();
            var attr = {};

            attr.startDate = deadLine.effectiveTime;
            attr.endDate = deadLine.maturityTime;

            var model = new ExtraRuleItemModel(attr, { collection: collection });
            var editExtraRuleView = new EditExtraRuleView({model: model});

            model.once('change', function () {
                collection.add(model);
            });

            editExtraRuleView.show();
        },
        addPayStageHandler: function () {
            var deadLine = this.sourceModel.contractBasicInfo.toJSON();
            var collection = this.payStageCollection;
            var lastModel = collection.last();
            var attr = {};
            
            if(lastModel && lastModel.get('endDate') === deadLine.maturityTime) {
                popupTip.show('合同期限已满');
                return;
            }

            if(lastModel) {
                var endDate = new Date(lastModel.get('endDate'));
                endDate.setDate(endDate.getDate()+1);
                attr.startDate = endDate.format('yyyy-MM-dd');
                attr.endDate = deadLine.maturityTime;
                attr.payDayInMonth = lastModel.get('payDayInMonth');
            }else {
                attr.startDate = deadLine.effectiveTime;
                attr.endDate = deadLine.maturityTime;
            }

            var model = new PayStageItemModel(attr, {
                sourceModel: this.sourceModel
            });

            var editPayStageView = new EditPayStageView({model: model});

            model.once('change', function () {
                collection.add(model);
            });

            editPayStageView.show();
        },
        invoicingHandler: function (e) {
            var checked = e.target.checked;
            checked ? this.invoiceForm.removeClass('hide') : this.invoiceForm.addClass('hide');
        },
        extractDomData: function () {
            var invoiceForm = this.invoiceForm.get(0);
            var els = this.$('.real-value').not(function() {
                return $(this).parents('.sub-form').length > 0;
            });
            var result = this._extractDomData(els, true);
            var invoicing = this.getInvoicingData();
            if(invoicing) {
                result.taxInfo = invoicing;
            }
            return result;
        },
        getInvoicingData: function () {
            var isOpenInvoicing = this.$('.invoicing')[0].checked;
            if(isOpenInvoicing) {
                var invoicing = this._extractDomData(this.invoiceForm.find('.form-control'), true);
                invoicing.taxType = this.invoiceForm.find('[name=invoice-type]:checked').val();
                return invoicing;
            }
        },
        validate: function () {
            var flag = true;
            var payStageValid = true;
            var extraRuleValid = true;
            
            if(this.$('.paystages').children('.item').length > 0) {
                this.$('.add-pay-stage').removeClass('error');
            }else {
                this.$('.add-pay-stage').addClass('error');
                flag = false;
            }

            if(!flag) return;

            payStageValid = this.validateStage(this.payStageCollection);

            return flag && payStageValid && extraRuleValid;
        },
        validateStage: function(collection) {
            var flag = true;

            // 判断每个缴租阶段是否在期限内
            // 如果修改了合同日期仍然需要验证
            for(var i = 0, len = collection.length; i<len; i++) {
                if(!collection.at(i).validateStageDate()) {
                    flag = false;
                }
            }

            if(!flag) return flag;

            var firstPeriodValid = collection.first().isEqualContractStartDate();
            var lastPeriodValid = collection.last().isEqualContractEndDate();

            return flag && firstPeriodValid && lastPeriodValid;
        },
        save: function () {
            var attr= this.extractDomData();
            if(!attr.taxInfo) {
                this.model.unset('taxInfo');
            }
            this.model.set(attr);
        },
    });


    module.exports = RentView;

});