define(function(require, exports, module) {
    var popupTip = require('component/popupTip');
    var FormDialogView = require('baseView/baseFormView').FormDialogView;

    var MarkCashTypeView = FormDialogView.extend({
        template: _.template($('#markCashTypeTmpl').html(), {variable: 'obj'}),
        events: {
            // 'change [name=financialAccountName]': 'onChangeMarkType'
        },
        initialize: function (options) {
            MarkCashTypeView.__super__.initialize.apply(this, arguments);
            this.basicOptions = options.basicOptions;
            this.defineValidator();
        },
        defineValidator: function () {
            this.validator = this.$('.form').validate({});
        },
        validate: function () {
            return this.validator.form();
        },
        onChangeMarkType: function(e) {
            var val = e.target.value;
            if(val === '') {
                this.$('[name=journal]').get(0).disabled = true;
            }else {
                this.$('[name=journal]').get(0).disabled = false;
            }
        },
        extractDomData: function() {
            var attr = this._extractDomData(this.$('.real-value'), true);

            attr.financialAccountAlias = this.$('[name=financialAccountName] :selected').text();

            if(attr.financialAccountName === '') {
                attr.lapse = true;
                attr.financialAccountAlias = '无';
                // delete attr.financialAccountName;
            }else {
                attr.lapse = false;
            }

            return attr;
        },
        save: function () {
            var self = this;
            var attr= this.extractDomData();

            this.model.saveMarkInfo(attr, function(resp, model) {
                if(resp.code != 0) {
                    popupTip.show(resp.message);
                }
            });
        },
        render: function() {
            var data = this.model.get('markInfo');
            data.summary = this.model.get('summary');
            data.types = this.model.getMarkTypes();
            this.$el.html(this.template(data));
        },
        submitHandler: function (e) {
            if(!this.validate()) return;
            this.save();
            this.hide();
        }
    });

    module.exports = MarkCashTypeView;

});