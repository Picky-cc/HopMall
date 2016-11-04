define(function(require, exports, module) {
    var popupTip = require('component/popupTip');
    var FileUploadPlugin = require('component/fileUpload');
    var validateUtil = require('scaffold/util').validateUtil;

    var baseFormView = require('baseView/baseFormView');
    var FieldsetView = baseFormView.FieldsetView;
    var SketchItemView = baseFormView.SketchItemView;
    var FormDialogView = baseFormView.FormDialogView;

    var $ =  jQuery;
    var root = global_const.root;
    var BILL_TYEP = {
        WATER_FARE: 'subjectMatterAppendix.accountInfoMap.COURSEOFDEALING_LEASING_WATER_FEES',
        ELECTRIC_FARE: 'subjectMatterAppendix.accountInfoMap.COURSEOFDEALING_LEASING_ELECTRICITY_FEES',
        HEATER_FARE: 'subjectMatterAppendix.accountInfoMap.COURSEOFDEALING_LEASING_GAS_FEES'
    };

    var EditLifeBillView = FormDialogView.extend({
        id: 'payTimeInputDialog',
        className: 'modal fade form-modal',
        template: _.template($('#editAccountInfoViewTmpl').html() || '', {variable: 'obj'}),
        initialize: function () {
            EditLifeBillView.__super__.initialize.apply(this, arguments);
            this.defineValidator();
        },
        defineValidator: function() {
            this.validator = this.$('.form').validate({
                rules: {
                   accountNo: 'required',
                   accountOwnerName: 'required',
                   subbranch: 'required'
                }
            });
        },
        validate: function () {
        	return this.validator.form();
        },
        submitHandler: function (e) {
            if(!this.validate()) return;
            this.save();
            this.hide();
        }
    });

    var LifeItemView = SketchItemView.extend({
        template: _.template($('#itemAccountInfoViewTmpl').html() || ''),
        toEdit: function () {
            var editView = new EditLifeBillView({
                model: this.model
            });
            editView.show();
        }
    });

    var HostSketchListView = FieldsetView.extend({
        el: $('.account-info'),
        events: {
            'click .btn-addfare': 'onClickAddfare'
        },
        initialize: function (options) {
            HostSketchListView.__super__.initialize.apply(this, arguments);

            var water = this.model.rGet(BILL_TYEP.WATER_FARE);
           

            this.waterfareModel = new Backbone.Model(water);
            this.waterfareModel.set('type', BILL_TYEP.WATER_FARE);
            this.listenTo(this.waterfareModel, 'destroy', function () {
               this.$('.btn-waterfare').show();
               this.waterfareModel.clear();
               this.waterfareModel.set('type', BILL_TYEP.WATER_FARE);
            });
            if(water && water.accountNo){
                this.addWaterFare(this.waterfareModel);
            }
            
            var gas = this.model.rGet(BILL_TYEP.HEATER_FARE);
            this.heaterfareModel = new Backbone.Model(gas);
            this.heaterfareModel.set('type', BILL_TYEP.HEATER_FARE);
            this.listenTo(this.heaterfareModel, 'destroy', function () {
               this.$('.btn-heaterfare').show();
               this.heaterfareModel.clear();
               this.heaterfareModel.set('type', BILL_TYEP.HEATER_FARE);
            });
            if(gas && gas.accountNo){
                this.addHeaterfare(this.heaterfareModel);
            }

            var elect = this.model.rGet(BILL_TYEP.ELECTRIC_FARE);
            this.electricfareModel = new Backbone.Model(elect);
            this.electricfareModel.set('type', BILL_TYEP.ELECTRIC_FARE);
            this.listenTo(this.electricfareModel, 'destroy', function () {
               this.$('.btn-electricfare').show();
               this.electricfareModel.clear();
               this.electricfareModel.set('type', BILL_TYEP.ELECTRIC_FARE);
            });
            if(elect && elect.accountNo){
               this.addElectricfare(this.electricfareModel);
            }
        },
        addfare:function(model){

            var itemView = new LifeItemView({model: model});
            var item = itemView.render().$el;
            if(model.attributes.type == "COURSEOFDEALING_LEASING_WATER_FEES"){
                item.addClass("water");
            }else if(model.attributes.type == "COURSEOFDEALING_LEASING_ELECTRICITY_FEES"){
                item.addClass("electric");
            }else if(model.attributes.type == "COURSEOFDEALING_LEASING_GAS_FEES"){
                item.addClass("gas");
            }
            
            this.$('.addfare').append(item);

        },

        onClickAddfare:function(e) {
            var self = this;
            var addfareModel = new Backbone.Model();
            addfareModel.once('change',function(model){
                var farestyle = $(".fare-style").val();
                if(farestyle =="水费"){
                    addfareModel.set('type',"COURSEOFDEALING_LEASING_WATER_FEES");
                }else if(farestyle == "电费"){
                    addfareModel.set('type',"COURSEOFDEALING_LEASING_ELECTRICITY_FEES");
                }else if(farestyle == "燃气费"){
                    addfareModel.set('type',"COURSEOFDEALING_LEASING_GAS_FEES");
                }
                self.addfare(addfareModel);
            });

            var editView = new EditLifeBillView({model: addfareModel});
            editView.show();
        },

        addWaterFare: function (model) {
            var itemView = new LifeItemView({model: model});
            this.$('.waterfare').append(itemView.render().$el);
            this.$('.btn-waterfare').hide();
        },
        onClickBtnWaterfare: function (e) {
            var self = this;
            var model = this.waterfareModel;
            model.once('change', function (model) {
                self.addWaterFare(model);
            });
            var editView = new EditLifeBillView({model: model});
            editView.show();
        },
        addHeaterfare: function (model) {
            var itemView = new LifeItemView({model: model});
            this.$('.heaterfare').append(itemView.render().$el);
            this.$('.btn-heaterfare').hide();
        },
        onClickBtnHeaterfare: function (e) {
            var self = this;
            var model = this.heaterfareModel;
            model.once('change', function (model) {
                self.addHeaterfare(model);
            });
            var editView = new EditLifeBillView({model: model});
            editView.show();
        },
        addElectricfare: function (model) {
            var itemView = new LifeItemView({model: model});
            this.$('.electricfare').append(itemView.render().$el);
            this.$('.btn-electricfare').hide();
        },
        onClickBtnElectricfare: function (e) {
            var self = this;
            var model = this.electricfareModel;
            model.once('change', function (model) {
                self.addElectricfare(model);
            });
            var editView = new EditLifeBillView({model: model});
            editView.show();
        },
        save: function () {
            var water = this.waterfareModel.toJSON(),
                heater = this.heaterfareModel.toJSON(),
                electric = this.electricfareModel.toJSON();
            water.accountNo ? this.model.rSet(BILL_TYEP.WATER_FARE, water) : this.model.rUnset(BILL_TYEP.WATER_FARE);
            heater.accountNo ? this.model.rSet(BILL_TYEP.HEATER_FARE, heater) : this.model.rUnset(BILL_TYEP.HEATER_FARE);
            electric.accountNo ? this.model.rSet(BILL_TYEP.ELECTRIC_FARE, electric) : this.model.rUnset(BILL_TYEP.ELECTRIC_FARE);
        }
    });

    module.exports = HostSketchListView;

});