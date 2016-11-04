define(function(require, exports, module) {
    var validateUtil = require('scaffold/util').validateUtil;
    var LeasingUnitAppendixsModel = require('entity/property/propertyModel').LeasingUnitAppendixsModel;
    var popupTip = require('component/popupTip');
    var FileUploadPlugin = require('component/fileUpload');
    var baseFormView = require('baseView/baseFormView');
    var FieldsetView = baseFormView.FieldsetView;
    var SketchItemView = baseFormView.SketchItemView;
    var FormDialogView = baseFormView.FormDialogView;

    var root = global_const.root;
    var regExps = validateUtil.regExps;
    var STATION_TYPESIZE_PRICEMAP = JSON.parse($("#stationTypeSizePriceMap").val() || '{}');

    var EditHouseView = FormDialogView.extend({
        template: _.template($('#editHouseInfoViewTmpl').html(), {
            variable: 'obj'
        }),
        className: 'modal fade form-modal',
        events: {
            'click .limit': 'onClickRenterSexLimit',
            'click .checkAllUtilites': 'onToggleCheckAllUtilites'
        },
        initialize: function(options) {
            this.isUpdate = options.isUpdate;

            EditHouseView.__super__.initialize.apply(this, arguments);

            this.initFileUploader();
            this.initValidator();
        },
        initValidator: function() {
            var self = this;

            jQuery.validator.addMethod('uniqueLeasingSubjectMatterName', function(value, element) {
                var exist = self.model.isLeasingSubjectMatterNameUnique(value.trim());
                return this.optional(element) || !exist;
            }, '出租单元名称已存在');

            this.validator = this.$('.form').validate({
                ignore: '.hide [name], [type=file]',
                onsubmit: false,
                groups: {
                    houseType: 'roomNum hallNum bathroomNum kitchenNum'
                },
                rules: {
                    roomNum: 'nonNegativeInteger',
                    hallNum: 'nonNegativeInteger',
                    bathroomNum: 'nonNegativeInteger',
                    kitchenNum: 'nonNegativeInteger',
                    buildArea: 'money',
                    leasingSubjectMatterName: {
                        required: true,
                        uniqueLeasingSubjectMatterName: true
                    }
                },
                messages: {
                    roomNum: {
                        nonNegativeInteger: '请输入正确的配置信息哦~'
                    },
                    hallNum: {
                        nonNegativeInteger: '请输入正确的配置信息哦~'
                    },
                    bathroomNum: {
                        nonNegativeInteger: '请输入正确的配置信息哦~'
                    },
                    kitchenNum: {
                        nonNegativeInteger: '请输入正确的配置信息哦~'
                    },
                    buildArea: {
                        money: '请输入正确的房间面积哦~'
                    },
                    promotionTitle: {
                        minlength: '您的字数未达标'
                    },
                    promotionContent: {
                        minlength: '您的字数未达标'
                    }
                },
                errorPlacement: function(error, element) {
                    var parent = element.parent();
                    var name = element.attr('name');
                    if (~['roomNum', 'hallNum', 'bathroomNum', 'kitchenNum'].indexOf(name)) {
                        parent.parent().append(error);
                    } else if (parent.is('.parcel-input')) {
                        error.insertAfter(parent);
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        },
        initFileUploader: function() {
            var wrapper = this.$('.roomImages');
            var fileInput = this.$('.file-input');

            FileUploadPlugin.globalUploadImage(fileInput, {
                limitNum: 8,
                onPlacement: function(img, el) {
                    wrapper.append(img);
                    if (wrapper.children().length >= 8) {
                        fileInput.hide();
                    }
                }
            });
        },
        render: function() {
            var data = this.model.toJSON();
            data.isUpdate = this.isUpdate;
            this.$el.html(this.template(data));
        },
        validate: function() {
            return this.validator.form();
        },
        submitHandler: function(e) {
            if (!this.validate()) {
                return;
            }

            this.save();
            this.hide();
        },
        extractDomData: function() {
            var buildImageObject = function(el) {
                var data = el.data();
                if (!data) return {};
                else return {
                    imgKey: data.original,
                    thumbNailsImgKey: data.thumbnail,
                    imageName: ''
                };
            };

            var attr = this._extractDomData(this.$('[data-params]'));

            var utilitiesAndAmenityArray = [];
            var utilitiesAndAmenityDescArray = [];
            this.$('input.utilitiesAndAmenity:checked').each(function() {
                var $input = $(this);
                var value = $input.attr('value');
                var alias = $input.attr('alias');
                var utilitiesAndAmenity = {};
                utilitiesAndAmenity.name = value;
                utilitiesAndAmenity.alias = alias;
                utilitiesAndAmenityArray.push(utilitiesAndAmenity);
                utilitiesAndAmenityDescArray.push(alias);
            });
            attr.utilitiesAndAmenityDesc = utilitiesAndAmenityDescArray.join('/');
            attr.utilitiesandAmenities = utilitiesAndAmenityArray;

            var imageList = [];
            this.$('.roomImages .item').each(function() {
                imageList.push(buildImageObject($(this)));
            });
            attr.roomImgList = imageList;

            var $limit = this.$('.limit:checked').val();
            attr.limit = $limit == null ? 'none' : $limit;

            if(typeof attr.leasingSubjectMatterName === 'undefined') {
                attr.leasingSubjectMatterName = '' + this.model.get('index');
            }

            return attr;
        },
        onClickRenterSexLimit: function(e) {
            var targetValue = $(e.target).val();
            var removeCheckedBoxClass = targetValue == 'man' ? 'women' : 'man';
            this.$('.' + removeCheckedBoxClass).removeAttr('checked');
        },
        onToggleCheckAllUtilites: function(e) {
            var checkedValue = e.target.checked;
            this.$('.house-support .utilitiesAndAmenity').each(function() {
                this.checked = checkedValue;
            });
        }
    });

    var EditSpaceView = FormDialogView.extend({
        template: _.template($('#editBusinessSpaceViewTmpl').html(), {
            variable: 'obj'
        }),
        className: 'modal fade form-modal',
        events: {
            'click #delete': 'onDeleteItem',
            'click #add': 'onAddItem',
            'change .type-select': 'changeType'
        },
        initialize: function(options) {
            this.isUpdate = options.isUpdate;

            EditSpaceView.__super__.initialize.apply(this, arguments);

            this.initFileUploader();
            this.initValidator();
        },
        initValidator: function() {
            var self = this;

            jQuery.validator.addMethod('uniqueLeasingSubjectMatterName', function(value, element) {
                var exist = self.model.isLeasingSubjectMatterNameUnique(value.trim());
                return this.optional(element) || !exist;
            }, '出租单元名称已存在');

            this.validator = this.$('.form').validate({
                rules: {
                    stationType: 'required',
                    leasingSubjectMatterName: {
                        required: true,
                        uniqueLeasingSubjectMatterName: true
                    }
                }
            });
        },
        initFileUploader: function() {
            var wrapper = this.$('.upload-imgs');
            var fileInput = this.$('.file-input');
            FileUploadPlugin.globalUploadImage(this.$('.file-input'), {
                limitNum: 8,
                onPlacement: function(img, el) {
                    wrapper.append(img);
                    if (wrapper.children().length >= 8) {
                        fileInput.hide();
                    }
                }
            });
        },
        extractDomData: function() {
            var buildImageObject = function(el) {
                var data = el.data();
                if (!data) return {};
                else return {
                    imgKey: data.original,
                    thumbNailsImgKey: data.thumbnail,
                    imageName: ''
                };
            };

            var self = this;
            var attr = this._extractDomData(this.$('.real-value'));

            var imgs = [];
            this.$('.upload-imgs').children().each(function() {
                imgs.push(buildImageObject($(this)));
            });
            attr.stationImgList = imgs;

            var professions = [];
            this.$('.profession').each(function() {
                var tar = $(this);
                var attr = self._extractDomData(tar.find('[name]'));
                var tem = tar.find('.type-and-money').val();

                if (tem) {
                    var t = tem.split('-');
                    attr.stationSize = t[0];
                    attr.price = t[2];
                    attr.stationSizeOrdianl = t[0];
                }

                professions.push(attr);
            });
            attr.stationList = professions;

            if(typeof attr.leasingSubjectMatterName === 'undefined') {
                attr.leasingSubjectMatterName = '' + this.model.get('index');
            }

            return attr;
        },
        render: function() {
            var data = this.model.toJSON();
            data.stationType = STATION_TYPESIZE_PRICEMAP;
            data.isUpdate = this.isUpdate;
            this.$el.html(this.template(data));
        },
        onDeleteItem: function(e) {
            var target = $(e.target);
            var deleteItem = target.parents(".profession");
            deleteItem.remove();
        },
        onAddItem: function(e) {
            var target = $(e.target);
            var addItem = target.parents(".profession").clone();

            addItem.find("a").attr("id", "delete").text("删除");
            addItem.find("label").hide();
            addItem.find("select").removeClass("error");
            addItem.find("input").val("1");

            this.changeType({
                target: addItem.find('select').get(0)
            });

            addItem.appendTo(".professions");
        },
        validate: function() {
            var flag = true;
            this.$('[name=available]').each(function () {
                var val = $(this).val();
                if(!val || !regExps.positiveInteger.test(val)){
                    $(this).addClass('error');
                    flag = false;
                }else {
                    $(this).removeClass('error');
                }
            });
            return this.validator.form() && flag;
        },
        changeType: function(e) {
            var type = $(e.target);
            if (type.val() == "0") {
                var openType = STATION_TYPESIZE_PRICEMAP.Open;
                var htm = [];
                for (var i = 0; i < openType.length; i++) {
                    var item = '<option value=' + openType[i].stationSizeOrdianl + '-' + openType[i].stationSize + '-' + openType[i].priceDes + '>' + openType[i].desc + '</option>';
                    htm.push(item);
                }
                type.next().html(htm.join(''));
            } else if (type.val() == "1") {
                var closeType = STATION_TYPESIZE_PRICEMAP.Private;
                var htm = [];
                for (var i = 0; i < closeType.length; i++) {
                    var item = '<option value=' + closeType[i].stationSizeOrdianl + '-' + closeType[i].stationSize + '-' + closeType[i].priceDes + '>' + closeType[i].desc + '</option>';
                    htm.push(item);
                }
                type.next().html(htm.join(''));
            }
        },
        submitHandler: function(e) {
            if (!this.validate()) {
                return;
            }

            this.save();
            this.hide();
        }
    });

    var HouseInfoItemView = SketchItemView.extend({
        template: _.template($('#itemHouseViewTmpl').html()),
        render: function() {
            var self = this;
            var data = this.model.toJSON();
            data.type = this.model.getLeaseTypeDesc();

            if(data.type == "创业空间"){
                var datalist = data.stationList[0];
                var arr = [];
                if (datalist.stationType == "0") {
                    var open = STATION_TYPESIZE_PRICEMAP.Open;
                    for (var i = 0; i < open.length; i++) {
                        if (open[i].stationSizeOrdianl == datalist.stationSizeOrdianl) {
                            arr.push("开放空间", open[i].desc, "*", datalist.available, "...");
                        }
                    }

                } else if (datalist.stationType == "1") {
                    var private = STATION_TYPESIZE_PRICEMAP.Private;
                    for (var i = 0; i < private.length; i++) {
                        if (private[i].stationSizeOrdianl == datalist.stationSizeOrdianl) {
                            arr.push("独立空间", private[i].desc, "*", datalist.available, "...");
                        }
                    }

                }
                data.supportDesc = arr.join(' ');
            }

            var str = this.template(data);
            this.$el.html(str);
            return this;
        },
        toEdit: function() {
            var editView;
            var leaseType = this.model.getLeaseTypeDesc();

            if(leaseType == '创业空间') {
                editView = new EditSpaceView({
                    model: this.model,
                    isUpdate: true
                });
            }else {
                editView = new EditHouseView({
                    model: this.model,
                    isUpdate: true
                });
            }

            editView.show();
        }
    });

    var HouseInfoView = FieldsetView.extend({
        el: $('.house-info'),
        events: {
            'click .add': 'onClickAdd'
        },
        initialize: function(options) {
            this.sketchListEl = this.$('.sketch-list');
            this.collection = new Backbone.Collection(this.model.get('leasingUnitAppendixs'), {
                model: LeasingUnitAppendixsModel,
                completeModel: this.model
            });

            this.listenTo(this.collection, 'add', this.addOne);
            // this.listenTo(this.collection, 'remove', this.reSortRoomNo);
            this.listenTo(this.collection, 'all', this.save);

            this.addAll();
        },
        onClickAdd: function(e) {
            e.preventDefault();

            var editView;
            var self = this;
            var attr = { index: this.collection.length + 1 };
            var leaseType = this.model.getLeaseTypeDesc();
            var model = new LeasingUnitAppendixsModel(attr, { completeModel: this.model });

            if(leaseType == '创业空间') {
                editView = new EditSpaceView({model: model});
            }else {
                editView = new EditHouseView({model: model});
            }

            model.once('change', function() {
                self.collection.add(model);
            });

            editView.show();
        },
        validate: function() {
            var flag = this.collection.length > 0;
            if(flag) {
                this.$('.add').removeClass('error');
            }else {
                this.$('.add').addClass('error');
            }
            return flag;
        },
        reSortRoomNo: function() {
            this.collection.each(function(model, index) {
                model.set({
                    index: index + 1
                });
            });
        },
        addOne: function(model) {
            var itemView = new HouseInfoItemView({
                model: model
            });
            this.sketchListEl.append(itemView.render().$el);
            this.$('.add').removeClass('error');
        },
        addAll: function() {
            var collection = this.collection;
            for (var i = 0, len = collection.length; i < len; i++) {
                this.addOne(collection.at(i));
            }
        },
        save: function() {
            var houses = this.collection.toJSON();
            this.model.set('leasingUnitAppendixs', houses);
        }
    });

    module.exports = HouseInfoView;
});