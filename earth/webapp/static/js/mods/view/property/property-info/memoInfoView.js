define(function(require, exports, module) {
    var baseFormView = require('baseView/baseFormView');
    var validateUtil = require('scaffold/util').validateUtil;
    var FareTypeModel = require('entity/property/propertyModel').FareTypeModel;

    var FieldsetView = baseFormView.FieldsetView;
    var SketchItemView = baseFormView.SketchItemView;
    var FormDialogView = baseFormView.FormDialogView;

    var LifeItemView = SketchItemView.extend({
        template: _.template($('#itemAccountInfoViewTmpl').html() || ''),
        toEdit: function() {
            var editView = new EditLifeBillView({
                model: this.model
            });
            editView.show();
        }
    });

    var EditLifeBillView = FormDialogView.extend({
        id: 'payTimeInputDialog',
        className: 'modal fade form-modal',
        template: _.template($('#editAccountInfoViewTmpl').html() || '', { variable: 'obj' }),
        initialize: function() {
            EditLifeBillView.__super__.initialize.apply(this, arguments);
            this.defineValidator();
        },
        defineValidator: function() {
            this.validator = this.$('.form').validate({
                rules: {
                    accountNo: 'required',
                    accountOwnerName: 'required',
                    subbranch: 'required',
                    type: 'required'
                }
            });
        },
        render: function() {
            var data = this.model.toJSON();
            data.fareTypes = this.model.getRemainFareType();
            var str = this.template(data);
            this.$el.html(str);
            return this;
        },
        validate: function() {
            return this.validator.form();
        },
        submitHandler: function(e) {
            if (!this.validate()) return;
            this.save();
            this.hide();
        }
    });

    var MemoInfoView = FieldsetView.extend({
        el: $('.other-info'),
        events: {
            'click .btn-addfare': 'onClickAddfare'
        },
        validate: function() {
            return true;
        },
        initialize: function() {
            MemoInfoView.__super__.initialize.apply(this, arguments);

            this.fareTypesCollection = new Backbone.Collection(this.convertToArray(this.model.getAccountInfoMap()), { model: FareTypeModel });

            this.listenTo(this.fareTypesCollection, 'add',function (model) {
            	this.addOneFare(model);
                if(!this.isRemainFareType()) {
            		this.$(".btn-addfare").hide();
                }
            });

            this.listenTo(this.fareTypesCollection,'remove',function () {
            	this.$(".btn-addfare").show();
            });

            this.addAllFare();
        },
        convertToArray: function(map) {
            var res = [];
            for(var prop in map) {
                var item = map[prop];
                item.type = prop;
                res.push(item);
            }
            return res;
        },
        convertToMap: function(collection) {
            var data = collection.toJSON();
            var res = {};
            for(var i = 0; i<data.length; i++) {
                res[data[i].type] = data[i];
            }
            return res;
        },
        save: function() {
            var accountInfoMap = this.convertToMap(this.fareTypesCollection);
            var attr = this.extractDomData();
            this.model.set('memo', attr);
            this.model.setAccountInfoMap(accountInfoMap);
        },
        isRemainFareType: function() {
            var model = new FareTypeModel({}, {collection: this.fareTypesCollection});
            return model.getRemainFareType().length != 0;
        },
        addAllFare: function() {
            var collection = this.fareTypesCollection;
            for (var i = 0, len = collection.length; i < len; i++) {
                this.addOneFare(collection.at(i));
            }

            if(!this.isRemainFareType()) {
                this.$(".btn-addfare").hide();
            }
        },
        addOneFare: function(model) {
            var itemView = new LifeItemView({ model: model });
            var item = itemView.render().$el;
            this.$('.addfare').append(item);
        },
        onClickAddfare: function(e) {
            var collection = this.fareTypesCollection;
            var addfareModel = new FareTypeModel({}, { collection: collection });
            var editView = new EditLifeBillView({ model: addfareModel });

            addfareModel.once('change', function(model) {
                collection.add(model);
            });

            editView.show();
        }
    });

    module.exports = MemoInfoView;

});