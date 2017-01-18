define(function(require, exports, module) {
    var path = require('scaffold/path');
    var popupTip = require('component/popupTip');
    var DialogView = require('component/dialogView');
    var FileUploadPlugin = require('component/fileUpload');
    var baseFormView = require('baseView/baseFormView');
    var FieldsetView = baseFormView.FieldsetView;
    var FormDialogView = baseFormView.FormDialogView;
    var SketchItemView = baseFormView.SketchItemView;

    var $ = jQuery,
        root = global_config.root;

    var ExhibitModel = Backbone.Model.extend({
        url: root + '/subject-matter/exhibit/promotionInfoSave',
        actions: {
            'publish': root + '/subject-matter/exhibit/publish',
        },
        defaults: {
            subjectMatterUuid: '',
            subjectMatterPromotionInfo: {
                houseImg: [], // 房源照片
                mobileHouseImg: [], // 手机端显示照片
                roomType: '', // int, 房间类型
                bedRoomType: '', // int, 卧室类型
                decorateType: '', // int, 装修类型
                independentBathroom: '', // int, 是否有独卫
                rentPrice: '', // dobuble, 租金
                tradeInterval: '', // int, 付款间隔
                depositPrice: '', // dobuble, 押金
                movingTime: null, // date, 可入住时间
                telphone: '', // string, 联系电话
                contactName: '', // string, 联系人
                inventoryStatus: '', // int, 发布状态
                activeExhibitUuid: '', // string, 已发布的展品UUID
                promotionTitle: '', // string, 推广标题
                promotionContent: '', // string, 推广内容
                balcony:'', //int，是否有阳台
                isPublishInfoComplete: '' 
            }
        },
        parse: function(resp) {
            return resp.data;
        },
        save: function(opts) {
            ExhibitModel.__super__.save.call(this, {}, opts);  
        },
        exhibit: function(cb) {
            var opts = {
                url: this.actions.publish,
                type: 'post',
                data: {
                    leasingSubjectMatterUuid: this.get('subjectMatterUuid')
                }
            };

            opts.success = function(resp) {
                resp = JSON.parse(resp);
                cb && cb(resp);
            };

            $.ajax(opts);
        },
        setIsCompleted: function(flag){
            var attr = this.get('subjectMatterPromotionInfo');
            attr.isPublishInfoComplete = flag ? 1:0;
            this.set('subjectMatterPromotionInfo', attr);
        }
    });


    var PublishEditView = FieldsetView.extend({
    	el: '.form-wrapper',
    	events: {
    		'click #save': 'onClickSave',
    		'click #preview': 'onClickPreview',
            'click #exhibit': 'onClickExhibit',
            'change [name=roomType]': 'onChangeRoomType',
            'click .cover-set': 'onClickSetCover',
            'click .cover-delete': 'onClickDelCover'
    	},
        initialize: function(subjectMatterUuid) {
            this.model = new ExhibitModel({
                subjectMatterUuid: subjectMatterUuid
            });
            this.defineValidator();
            this.initFileUplad();

            this.roomTypeSelectEl = this.$('.bed-room-type').remove();
            this.roomTypeSelectEl.removeClass('hide');

            var self = this;
            var dialog = this.previewDialog = new DialogView({
                bodyInnerTxt: '是否先保存',
                cancelBtnTxt: '否',
                goaheadBtnTxt: '是'
            });
            dialog.on('goahead', function() {
                var flag = self.isValid();
                self.save(flag, function() {
                    dialog.hide();
                    self.preview();
                });
            });
            dialog.on('cancel', function() {
                dialog.hide();
                self.preview();
            });
        },
        initFileUplad: function() {
            var upload_path = image_oss_host;
            var warpper = this.$('.photo-covers');
            FileUploadPlugin.globalUploadImage(this.$('.file-input'), {
                limitNum: 8,
                createItemDom: function (data, sourceFile) {
                    var file = '<li class="item" data-original="'+ data.original +'" data-thumbnail="'+ data.thumbnail +'">' + 
                                    '<img src="'+ upload_path.thumbnail + '/' + data.thumbnail + '">' + 
                                    '<a class="operation cover-set">封</a>' + 
                                    '<a class="operation cover-delete"></a>' +
                                '</li>';
                    return file;
                },
                onPlacement: function (img, el) {
                    warpper.append(img);
                },
                onSuccess: function (data, el) {
                    el.removeClass('error');
                }
            });
        },
        defineValidator: function() {
            this.formEl = this.$('.form');
            var settings = {
                ignore: false,
                onsubmit: false,
                rules: {
                    roomType: 'required',
                    bedRoomType: 'required',
                    rentPrice: {
                        money: true
                    },
                    depositPrice: {
                        money: true
                    },
                    telphone: {
                        mobile: true
                    },
                    movingTime: 'required'
                },
                messages: {
                    roomType: {
                        required: '请先勾选'
                    },
                    bedRoomType: {
                        required: '请先勾选'
                    }
                },
                success: function (error, element) {
                    var parent = $(element).parent();
                    if(parent.is('.imitate-datetimepicker-input')) {
                        if(!element.value) {
                            parent.addClass('error');
                        }else {
                            parent.removeClass('error');
                        }
                    }
                },
                errorPlacement: function(error, element) {
                    var parent = element.parent();
                    if(parent.is('.parcel-input')) {
                        error.insertAfter(parent);
                    }else if(parent.find('.right-extra-text').length > 0) {
                        // error.insertBefore(parent.find('.right-extra-text'));
                        parent.append(error);
                    }else if(parent.is('.imitate-datetimepicker-input')) {

                    }else if(parent.is('.radio-box')) {
                        parent.parent().append(error);
                    }else {
                        error.insertAfter(element);
                    }
                }
            };
            this.validator = this.formEl.validate(settings);
        },
        validate: function() {
            var basic = this.validator.form();
            var flag = this.$('.photo-covers .item').length > 0;
            flag ? this.$('.file-input').removeClass('error') : this.$('.file-input').addClass('error');
            return basic && flag;
        },
        isValid: function() {
            var validator = this.validator;
            var original = validator.settings.showErrors;
            validator.settings.showErrors = function() { };
            var flag = this.validator.form();
            validator.settings.showErrors = original;
            return flag;
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

            var res = this._extractDomData(this.$('.real-value'), true);

            var houseImg = [];
            var mobileHouseImg = [];
            this.$('.photo-covers .item').each(function() {
                var tar = $(this);
                var o = buildImageObject(tar);
                if(tar.find('.triangle').length > 0) {
                    mobileHouseImg.push(o);
                    houseImg.unshift(o);
                }else {
                    houseImg.push(o);
                }
            });
            res.houseImg = houseImg;
            res.mobileHouseImg = mobileHouseImg.length>0 ? mobileHouseImg : houseImg.slice(0, 1);

            res.roomType = $('[name=roomType]:checked').val();
            res.bedRoomType = $('[name=bedRoomType]:checked').val();
            res.independentBathroom = $('[name=independentBathroom]:checked').val();
            res.balcony = $('[name=balcony]:checked').val();
            
            return res;
        },
        save: function(flag, callback) {
            var self = this;
            var attr = this.extractDomData();

            this.model.set('subjectMatterPromotionInfo', attr);

            this.model.setIsCompleted(flag);

            this.model.save({
                success: function(model, resp) {
                    callback.call(self, resp);
                }
            });
        },
        preview: function() {
            var url = root+'/mobile/exhibit/property-list/index#!/preview/'+this.model.get('subjectMatterUuid');
            var ref = window.open(url);
        },
        exhibit: function(flag) {
            var attr = this.extractDomData();

            this.model.set('subjectMatterPromotionInfo', attr);

            this.model.setIsCompleted(flag);

            var success = function(model, resp) {
                if (resp.code == 0) {
                    popupTip.show('正在发布...');
                    model.exhibit(function(resp) {
                        if (resp.code == 0) {
                            popupTip.show('成功，正在跳转...');
                            setTimeout(function() {
                                location.assign(root+'/subject-matter/exhibit/property-list/index');
                            }, 1500);
                        } else {
                            popupTip.show(resp.message);
                        }
                    });
                } else {
                    popupTip.show(resp.message);
                }
            };

            this.model.save({
                success: success
            });
        },
        onClickSetCover: function(e) {
            e.preventDefault();
            var el = $('<span class="triangle">');
            var main = this.$('.photo-covers .triangle').remove();
            $(e.target).parent().append(el);
        },
        onClickDelCover: function(e) {
            e.preventDefault();
            var tar = $(e.currentTarget);
            tar.parent().remove();
            $(document).trigger('delete.fileitem', tar);
        },
        onChangeRoomType: function(e) {
            if(e.target.value == 1) {
                this.$('.room-type').after(this.roomTypeSelectEl);
            }else {
                this.roomTypeSelectEl.remove();
            }
        },
    	onClickSave:function(){
            var flag = this.isValid();
            this.save(flag, function(resp) {
                if (resp.code == 0) {
                    popupTip.show('成功，正在跳转...');
                    setTimeout(function() {
                        location.assign(root+'/subject-matter/exhibit/property-list/index');
                    }, 1500);
                } else {
                    popupTip.show(resp.message);
                }
            });
    	},
    	onClickPreview:function () {
            this.previewDialog.show();
    	},
    	onClickExhibit:function () {
            var flag = this.validate();

    		if(!flag) return;

            this.exhibit(flag);
    	}
    });

    exports.PublishEditView = PublishEditView;

});