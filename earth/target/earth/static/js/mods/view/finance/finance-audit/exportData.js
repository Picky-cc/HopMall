define(function(require, exports, module) {
    var popupTip = require('component/popupTip');
    var FormDialogView = require('baseView/baseFormView').FormDialogView;

    var ExportKingDeeDataView = FormDialogView.extend({
        template: _.template($('#exportKingDeeDataTmpl').html() || ''),
        actions: {
            export_kingdee_voucher: './export-kingdee-voucher'
        },
        initialize: function (options) {
            ExportKingDeeDataView.__super__.initialize.apply(this, arguments);
            this.query = options.query;
            this.defineValidator();
        },
        validate: function () {
            return this.validator.form();
        },
        defineValidator: function () {
            this.validator = this.$('.form').validate({
                rules: {
                  fSerialNum:{
                    positiveInteger:true
                  },
                   fMonth:{
                    positiveInteger:true
                  },
                  fYear:{
                    positiveInteger:true
                  },
                  fNumber:{
                    positiveInteger:true
                  }
                }
            });
        },
        save: function () {
            var attr= this.extractDomData();

            $.extend(attr, this.query);

            var paramStr = '?';
            for(var param in attr){
                paramStr+=param+"="+attr[param]+"&";
            }

            location = this.actions.export_kingdee_voucher + paramStr;
        },
        submitHandler: function (e) {
            if(!this.validate()) return;
            this.save();
            this.hide();
        }
    });

    var ExportYonYouDataView = FormDialogView.extend({
        template: _.template($('#exportYonYouDataTmpl').html() || ''),
        actions: {
            export_yonyou_voucher: './export-yongyou-voucher'
        },
        initialize: function (options) {
            ExportYonYouDataView.__super__.initialize.apply(this, arguments);
            this.query = options.query;
            this.defineValidator();
        },
        validate: function () {
            return this.validator.form();
        },
        defineValidator: function () {
            this.validator = this.$('.form').validate({
                rules: {
                    fFinicialYear:{
                        positiveInteger:true
                    },
                    fFinicialPeriod:{
                        positiveInteger:true
                    }
                }
            });
        },
        save: function () {
            var attr= this.extractDomData();

            $.extend(attr, this.query);

            var paramStr = '?';
            for(var param in attr){
                paramStr+=param+"="+attr[param]+"&";
            }

            location = this.actions.export_yonyou_voucher + paramStr;
        },
        submitHandler: function (e) {
            if(!this.validate()) return;
            this.save();
            this.hide();
        }
    });

    exports.ExportKingDeeDataView = ExportKingDeeDataView;
    exports.ExportYonYouDataView = ExportYonYouDataView;

});