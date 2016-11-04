define(function(require, exports, module) {
    var popupTip = require('component/popupTip');
    var FormDialogView = require('baseView/baseFormView').FormDialogView;

    var AddCashFlowView = FormDialogView.extend({
        template: _.template($('#addCashFlowTmpl').html(), { variable: 'obj' }),
        actions: {
            save: '../finance/add-cash-flow'
        },
        className: 'modal fade form-modal',
        initialize: function(options) {
        	AddCashFlowView.__super__.initialize.apply(this, arguments);
        	this.query = options.query;
            this.defineValidator();
        },
        defineValidator: function() {
            this.validator = this.$('.form').validate({
                ignore: null,
                rules: {
                    payName: 'required',
                    amount: {
                        required: true,
                        positiveNumber: true
                    },
                    remark: {
                        maxlength: 30
                    },
                    transactionDate: 'required'
                },
                success: function(error, element) {
                    var parent = $(element).parent();
                    if(parent.is('.imitate-datetimepicker-input') && element.value) {
                        parent.removeClass('error');
                    }
                },
                errorPlacement: function(error, element) {
                    var parent = element.parent();
                    if(parent.is('.parcel-input')) {
                        error.insertAfter(parent);
                    }else if(parent.is('.imitate-datetimepicker-input')) {
                        parent.addClass('error');
                    }else {
                        error.insertAfter(element);
                    }
                }
            });
        },
        validate: function() {
            return this.validator.form();
        },
        submitHandler: function(e) {
            if (!this.validate()) return;
            this.save();
            this.hide();
        },
        save: function() {
            var self = this;
            var attr = this.extractDomData();
            $.extend(attr, this.query);

            var opt = {
                url: this.actions.save,
                type: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(attr)
            };

            opt.success = function(resp) {
                self.trigger('sync:save', resp);
            };

            $.ajax(opt);
        }
      
    });

    module.exports = AddCashFlowView;

});