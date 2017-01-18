define(function(require, exports, module) {
    var popupTip = require('component/popupTip');
    var FileUploadPlugin = require('component/fileUpload');
    var validateUtil = require('scaffold/util').validateUtil;
    var OwnerInfomationsModel = require('entity/property/propertyModel').OwnerInfomationsModel;
    var baseFormView = require('baseView/baseFormView');
    var FieldsetView = baseFormView.FieldsetView;
    var SketchItemView = baseFormView.SketchItemView;
    var FormDialogView = baseFormView.FormDialogView;

    var root = global_const.root;

    var EditHostView = FormDialogView.extend({
        template: _.template($('#editHostInfoViewTmpl').html(), {
            variable: 'obj'
        }),
        className: 'modal fade form-modal',
        events: {
            'click .ownerType': 'switchOwnerPage'
        },
        initialize: function() {
            EditHostView.__super__.initialize.apply(this, arguments);

            this.switchOwnerPage();
            this.initValidator();
            this.initFileUploader();
        },
        initValidator: function() {
            var self = this;

            jQuery.validator.addMethod('uniqueCertificateNo', function(value, element) {
                var ownerType = self.getOwnerType();
                var certificateType = self.$('.personal [name="certificateType"]').val();
                var certificateNoEl = self.$('.personal [name="certificateNo"]');
                var certificateNo = certificateNoEl.val();

                var isIdExist = ownerType == '0' // 个人
                    && certificateType == '0' // 身份证
                    && certificateNo // 非空
                    && self.model.isIdExist(certificateNo)

                return this.optional(element) || !isIdExist;
            }, '身份证号不能重复！');

            jQuery.validator.addMethod("validateIDCard", function(value, element) {
                var ownerType = self.getOwnerType();
                if (ownerType == '1') {
                    return true;
                }
                var certificateType = self.$('.personal [data-params="certificateType"]:visible').val();
                if (certificateType == '0') {
                    return this.optional(element) || validateUtil.isIDCardValid(value);
                }
                return true;
            }, '您的证件号无法被地球人所识别');

            jQuery.validator.addMethod("validateRegistrationLicenseNo", function(value, element) {
                var ownerType = self.getOwnerType();
                if (ownerType == '0') {
                    return true;
                }
                return this.optional(element) || validateUtil.regExps.number.test(value);
            }, "您的工商注册执照号无法被地球人所识别");

            jQuery.validator.addMethod('validateOwnerName', function(value, element) {
                var ownerType = self.getOwnerType();
                var optional = this.optional(element);
                if (ownerType == '0') {
                    return optional || validateUtil.regExps.chineseCharactor.test(value) || validateUtil.regExps.englishName.test(value);
                } else {
                    return optional || !validateUtil.regExps.includeNumber.test(value);
                }
            }, '业主名称不能是这样的吧');

            jQuery.validator.addMethod('validateCertificateType', function(value, element) {
                var ownerType = self.getOwnerType();
                var certificateType = self.$('[name=certificateType]').val();
                if(ownerType == '0' && certificateType != -1) {
                    return value.trim() !== '';
                }else {
                    return true;
                }
            }, '证件号不能为空');

            jQuery.validator.addMethod('validateCertificateNo', function(value, element) {
                var ownerType = self.getOwnerType();
                var certificateNo = self.$('.personal [name="certificateNo"]').val();
                if(ownerType == '0' && certificateNo.trim() != '') {
                    return value != -1;
                }else {
                    return true;
                }
            }, '请选择证件类型');

            this.validator = this.$('.form').validate({
                ignore: '[type=file], :hidden',
                onsubmit: false,
                rules: {
                    ownerName: {
                        required: true,
                        validateOwnerName: true
                    },
                    certificateNo: {
                        validateRegistrationLicenseNo: true,
                        validateIDCard: true,
                        uniqueCertificateNo: true,
                        validateCertificateType: true
                    },
                    certificateType: {
                        validateCertificateNo: true
                    }
                },
                errorPlacement: function(error, element) {
                    if(element.is('[name=certificateType]')) {

                    }else {
                        error.insertAfter(element);
                    }
                }
            });
        },
        initFileUploader: function() {
            var self = this;

            FileUploadPlugin.globalUploadImage(this.$('.file-input'), {
                onPlacement: function(img, el){
                    self.$(el.data('target')).html(img);
                }
            });
        },
        getOwnerType: function() {
            return this.$('.ownerType:checked').val();
        },
        switchOwnerPage: function() {
            var ownerType = this.$('.ownerType:checked').val();
            if (ownerType == '0') {
                this.$('.personal').show();
                this.$('.enterprise').hide();
            } else {
                this.$('.personal').hide();
                this.$('.enterprise').show();
            }
        },
        validate: function() {
            var g = this.validator.form();
            return g;
        },
        extractDomData: function() {
            var buildImageObject = function (el) {
                var data = el.data();
                if(!data) return {};
                else return {
                    imgKey: data.original,
                    thumbNailsImgKey: data.thumbnail,
                    imageName: ''
                };
            };
            var attr = {},
                tmp;
            attr.ownerType = this.$('.ownerType:checked').val();

            if(attr.ownerType == '0') {
                tmp = this._extractDomData(this.$('.personal [data-params]'), true);

                var frontImage = this.$('.front-pic-images .item');
                if(frontImage.length > 0) {
                    tmp.frontCertificateImg = buildImageObject(frontImage);
                }else {
                    tmp.frontCertificateImg = null;
                }

                var backImage = this.$('.back-pic-images .item');
                if(backImage.length > 0) {
                    tmp.backCertificateImg = buildImageObject(backImage);
                }else {
                    tmp.backCertificateImg = null;
                }
            }else {
                tmp = this._extractDomData(this.$('.enterprise [data-params]'));

                var frontImage = this.$('.enterprise-pic-images .item');
                if(frontImage.length > 0) {
                    tmp.frontCertificateImg = buildImageObject(frontImage);
                }else {
                    tmp.frontCertificateImg = null;
                }

                tmp.backCertificateImg = null;
            }

            $.extend(attr, tmp);

            return attr;
        },
        submitHandler: function(e) {
            if (!this.validate()) {
                return;
            }

            this.save();
            this.hide();
        }
    });

    var HostSketchInfoView = SketchItemView.extend({
        template: _.template($('#hostSketchInfoViewTmpl').html()),
        toEdit: function() {
            var editView = new EditHostView({
                model: this.model
            });
            editView.show();
        }
    });

    var HostSketchListView = FieldsetView.extend({
        el: $('.host-info'),
        events: {
            'click .add': 'onClickAdd'
        },
        initialize: function(options) {
            this.sketchListEl = this.$('.sketch-list');
            this.collection = new Backbone.Collection(this.model.get('ownerInfomations'), {
                model: OwnerInfomationsModel,
                completeModel: this.model
            });

            this.listenTo(this.collection, 'add', this.addOne);
            this.listenTo(this.collection, 'all', this.save);

            this.addAll();
        },
        onClickAdd: function(e) {
            e.preventDefault();

            var self = this;
            var model = new OwnerInfomationsModel({}, {
                completeModel: this.model
            });
            var editView = new EditHostView({ model: model });

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
        addOne: function(model) {
            var itemView = new HostSketchInfoView({
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
            var hosters = this.collection.toJSON();
            this.model.set('ownerInfomations', hosters);
        }
    });

    module.exports = HostSketchListView;

});