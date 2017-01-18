define(function(require, exports, module) {
    var popupTip = require('component/popupTip');
    var DialogView = require('component/dialogView');
    var Util = require('scaffold/util');
    var regExps = Util.validateUtil.regExps;

    var PropertyModel = require('entity/property/propertyModel').PropertyModel;

    var BaseFormView = require('baseView/baseFormView').BaseFormView;
    var BasicInfoView = require('./basicInfoView');
    var HostSketchListView = require('./hostInfoView');
    var HouseInfoView = require('./houseInfoView');
    var MemoInfoView = require('./memoInfoView');
    

    var AddPropertyView = BaseFormView.extend({
        el: '.property-form-wrapper',
        initialize: function(subjectMatterUuid) {
            AddPropertyView.__super__.initialize.apply(this, arguments);

            if(typeof subjectMatterUuid == 'string') {
                this.communityIsNotInput = true;
            }else {
                this.communityIsNotInput = false;
            }

            this.initModel(subjectMatterUuid);
            this.initSubView();
            this.initValidator();

            this.succDialogView = new DialogView({
                cancelBtnTxt: '取消',
                goaheadBtnTxt: '继续'
            });
        },
        initValidator: function() {
            var self = this;

            jQuery.validator.addMethod('communityIsNotInput', function(value, element) {
                return this.optional(element) || self.communityIsNotInput;
            }, '请添加或选择正确的小区名称');

            jQuery.validator.addMethod("validateLevel", function(value, element) {
              if(regExps.chineseCharactor.test(value)) {
                    return false;
              }
              var totalLevels = self.$('input[name="totalLevels"]').val();
            
              if(regExps.positiveInteger.test(totalLevels) && regExps.positiveInteger.test(value)) {
                return +value <= +totalLevels;
              }else {
                return true;
              }

            }, "楼层信息不正确");

            jQuery.validator.addMethod("validateTotalLevel", function(value, element) {
                if(!regExps.positiveInteger.test(value)) {
                    return false;
                }

                var level = self.$('input[name="level"]').val();

                if(regExps.positiveInteger.test(level)) {
                    return +level <= +value;
                }else {
                    return true;
                }

            }, "楼层信息不正确");

            this.validator = $('.property').validate({
                ignore: '.hide [name], [type=file]',
                onsubmit: false,
                groups: {
                    floor: 'level totalLevels',
                    houseType: 'roomNum hallNum bathroomNum kitchenNum'
                },
                rules: {
                    provinceCode: 'required',
                    cityCode: 'required',
                    totalLevels: 'validateTotalLevel',
                    level: 'validateLevel',
                    roomNum: 'nonNegativeInteger',
                    hallNum: 'nonNegativeInteger',
                    bathroomNum: 'nonNegativeInteger',
                    kitchenNum: 'nonNegativeInteger',
                    sourcePropertyNo: 'numberAndAlphabet',
                    community: {
                        required: true,
                        communityIsNotInput: true
                    }
                },
                messages: {
                    sourcePropertyNo:{
                        'numberAndAlphabet':'请输入正确的自有物业编号，格式仅支持数字和英文哦'
                    }
                },
                errorPlacement: function(error, element) {
                    var parent = element.parent();
                    var name = element.attr('name');
                    if(~['level', 'totalLevels'].indexOf(name)) {
                        parent.parent().append(error);
                    }else if(~['roomNum', 'hallNum', 'bathroomNum', 'kitchenNum'].indexOf(name)) {
                        parent.parent().append(error);
                    }else if (parent.is('.parcel-input')) {
                        error.insertAfter(parent);
                    }else if(element.is('.community')) {
                        parent.append(error);
                    }else {
                        error.insertAfter(element);
                    }
                }
            });
        },
        initModel: function(subjectMatterUuid) {
            var workingData;

            this.model = new PropertyModel();

            if(typeof subjectMatterUuid == 'string') {
                this.model.set('subjectMatterUuid', subjectMatterUuid);
            }

            try {
                workingData = JSON.parse($('#workingDataTmpl').val());
            } catch (err) {
                workingData = {};
            }

            this.model.set(workingData);
            this.model.rSet('subjectMatterAppendix.leaseType', this.$("#leasingType").val());
        },
        initSubView: function() {
            this.houseInfoView = new HouseInfoView({
                model: this.model,
                __parent__: this
            });

            this.hostSketchListView = new HostSketchListView({
                model: this.model,
                __parent__: this
            });

            this.basicPropertyView = new BasicInfoView({
                model: this.model,
                __parent__: this
            });

            this.memoPropertView = new MemoInfoView({
                model: this.model,
                __parent__: this
            });            
        },
        validate: function() {
            var g = this.validator.form(),
                basic = this.basicPropertyView.validate(),
                house = this.houseInfoView.validate(),
                host = this.hostSketchListView.validate(),
                memoInfo = this.memoPropertView.validate();
            return g && basic && memoInfo && host && house;
        },
        submitHandler: function(e) {
            if (!this.validate()) {
                this.trigger('invalid');
                return;
            }

            this.basicPropertyView.save();
            this.memoPropertView.save();
            this.hostSketchListView.save();
            this.houseInfoView.save();

            var self = this;
            var isUpdate = !!this.model.get('subjectMatterUuid');
            var succDialogView = this.succDialogView;

            var redirectRental = function(subjectMatterUuid) {
                AppRouters.navigate('business-contract/rental-contract/?subjectMatterUuid='+subjectMatterUuid, {absolute: true});
            };
            var redirectProperty = function(subjectMatterUuid) {
                if (typeof subjectMatterUuid === 'undefined') {
                    AppRouters.navigate('../property-list/index');
                } else {
                    AppRouters.navigate('../property-view/' + subjectMatterUuid);
                }
            };

            this.model.manualSave({
                success: function(model, resp) {
                    var subjectMatterUuid = resp.data.subjectMatterUuid;
                    if (resp.code == 0) {
                        if(isUpdate) {
                            popupTip.once('closedialog', redirectProperty);
                            popupTip.show('成功！', subjectMatterUuid);
                        }else {
                            succDialogView.once('goahead', redirectRental);
                            succDialogView.once('closedialog', redirectProperty);
                            succDialogView.show('物业房源信息添加成功，继续完成上游合同录入？', subjectMatterUuid);
                        }
                    } else {
                        for(var mes in resp.data){
                            if(resp.data.hasOwnProperty(mes)){
                                popupTip.show(resp.data[mes]);
                            }
                        }
                    }
                }
            });
        }
    });

    exports.AddPropertyView = AddPropertyView;

});