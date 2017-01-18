define(function(require, exports, module) {
    require('component/autocomplete');
    var FileUploadPlugin = require('component/fileUpload');
    var Util = require('scaffold/util');
    var regExps = Util.validateUtil.regExps;
    var AreaSelectView = require('component/areaSelect');
    var baseFormView = require('baseView/baseFormView');
    var FieldsetView = baseFormView.FieldsetView;
    var FormDialogView = baseFormView.FormDialogView;

    var $ = jQuery;
    var root = global_config.root;

    var CommunityMode = Backbone.Model.extend({
        urlRoot: root + '/community/saveCommunity',
        save: function(attrs, options) {
            var self = this;

            if (0 in arguments) {
                this.set(attrs, {
                    validate: true
                });
            }

            this.trigger('request');

            var finalOptions = {
                url: this.urlRoot,
                type: 'post',
                dataType: 'json',
                data: this.toJSON()
            };

            finalOptions.success = function(resp) {
                options.success && options.success(resp);
                if(resp.code == 0) {
                    self.trigger('sync', self, resp, options);
                }
            };

            finalOptions.error = function() {
                options.error && options.error.call(null, arguments);
                self.trigger('error');
            };

            finalOptions.complete = function() {
                options.complete && options.complete.call(null, arguments);
            };

            $.ajax(finalOptions);
        }
    });

    var EditCommunityView = FormDialogView.extend({
        template: _.template($('#editCommunityTmpl').html(), {
            variable: 'obj'
        }),
        className: 'modal fade form-modal',
        initialize: function() {
            EditCommunityView.__super__.initialize.apply(this, arguments);

            this.validator = this.$('.form').validate({
                rules: {
                    provinceCode: 'required',
                    cityCode: 'required',
                    areaCode: 'required'
                }
            });

            this.areaSelet = new AreaSelectView({
                el: this.$('.area-select'),
                defSelectProvince: this.model.get('provinceCode'),
                defSelectCity: this.model.get('cityCode'),
                defSelectDistrict: this.model.get('areaCode')
            });
        },
        submitHandler: function(e) {
            if (!this.validator.form()) return;

            var result = this._extractDomData(this.$('.form-control'));
            var self = this;

            this.model.save(result, {
                success: function(resp) {
                    if(resp.code != 0) {
                        self.popupTip(resp.message);
                    }else {
                        self.hide();
                    }
                }
            });
        }
    });

    var BasicInfoView = FieldsetView.extend({
        el: $('.property-info'),
        events: {
            'blur .room-no': function(e) {
                this.model.rSet('subjectMatterAppendix.roomNo', e.target.value);
            },
            // 'input [name=community]': function(e) {
            //     this.model.rSet('subjectMatterAppendix.community', e.target.value);
            //     this.model.rSet('subjectMatterAppendix.address', '');
            // },
            'click .add-cur-posi': 'onClickAddCurPosi'
        },
        initialize: function(options) {
            var addressCode = this.$('.area').data();
            this.areaSelect = new AreaSelectView({
                el: this.$('.area-select'),
                defSelectProvince: addressCode.provincecode,
                defSelectCity: addressCode.citycode,
                defSelectDistrict: addressCode.areacode
            });

            this.__parent__ = options.__parent__; // 父视图

            this.listenTo(this.model, 'change:subjectMatterAppendix', function() {
                var community = this.model.rGet('subjectMatterAppendix.community'),
                    address = this.model.rGet('subjectMatterAppendix.address');
                var properytName = [community, address, this.model.rGet('subjectMatterAppendix.roomNo')];
                this.$('.address').text(address);
                this.$('[name="community"]').val(community);
                this.$('.property-name').val(properytName.join(''));
            });

            this.initFileUploader();
            this.initCommunity();
        },
        initFileUploader: function() {
            var wrapper = this.$('.property-certificate-images');

            FileUploadPlugin.globalUploadImage(this.$('.property-file-input'), {
                onPlacement: function(img, el){
                    wrapper.html(img);
                }
            });
            var self = this;
            FileUploadPlugin.globalUploadFile(this.$('.assecsory-file-input'), {
	            onSuccess: function (data, el) {
	                el.removeClass('error');
	            },
	            onPlacement: function(file, el) {
	                self.$(el.data('target')).html(file);
	            }
	        });
        },
        initCommunity: function() {
            var self = this;
            var areaSelect = this.areaSelect;
            var btmExtraText = this.$('.btm-extra-text');
            this.communityEl = this.$('.community');
            this.communityEl.autocomplete({
                action: root + '/community/getCommunityList',
                container: this.$('.auto-complete-list'),
                parse: function(resp) {
                    if (resp.code == 0) {
                        return resp.data.communityList;
                    } else {
                        return [];
                    }
                },
                search: function () {
                    var addressCode = areaSelect.getValue().split('-');
                    return {
                        provinceCode: addressCode[0],
                        cityCode: addressCode[1],
                        areaCode: addressCode[2]
                    };
                },
                parcelItem: function(item) {
                    var str = '<li class="item" data-communityuuid="' + item.communityUuid + '"><span class="title">' + item.communityName + '</span><span class="sub-title">' + item.address + '</span></li>';
                    return str;
                },
                onSync: function (list, inputVal, container) {
                    var htm = '';
                    if(list.length<1) {
                        htm = '<p>未找到对应小区/楼宇？</p><p><a href="#" class="add-cur-posi">添加</a><span> ' + inputVal + ' </span>为小区/楼宇</p>';
                        container.html(htm);
                    }else {
                        htm = '<p><a href="#" class="add-cur-posi">添加</a><span> ' + inputVal + ' </span>为小区/楼宇</p>';
                        container.append(htm);
                    }
                },
                onSubmit: function(inputEl, itemEl) {
                    var title = itemEl.find('.title').text(),
                        sutTitle = itemEl.find('.sub-title').text();
                    self.setCommunityIsNotInput(true);
                    self.model.rSet('subjectMatterAppendix.community', title);
                    self.model.rSet('subjectMatterAppendix.address', sutTitle);
                },
                onClose: function(options, inputEl) {
                    if(options && options.triggerType !== 'select') {
                        self.setCommunityIsNotInput(false);
                        self.model.rSet('subjectMatterAppendix.community', inputEl.val());
                        self.model.rSet('subjectMatterAppendix.address', '');
                    }
                }
            });
        },
        onClickAddCurPosi: function(e) {
            e.preventDefault();

            var self = this;
            var addressCode = this.areaSelect.getValue().split('-');
            var model = new CommunityMode({
                communityName: this.communityEl.val(),
                provinceCode: addressCode[0],
                cityCode: addressCode[1],
                areaCode: addressCode[2]
            });
            var editCommunityView = new EditCommunityView({ model: model });

            model.once('sync', function(model) {
                self.setCommunityIsNotInput(true);
                self.model.rSet('subjectMatterAppendix.community', model.get('communityName'));
                self.model.rSet('subjectMatterAppendix.address', model.get('address'));
                self.areaSelect.refresh(model.get('provinceCode'), model.get('cityCode'), model.get('areaCode'));
            });

            editCommunityView.show();
        },
        setCommunityIsNotInput: function(isNotSelected) {
            this.__parent__.communityIsNotInput = isNotSelected;
            this.__parent__.validator.element("[name=community]");
        },
        validate: function() {
            // return this.communityIsNotSelected ? false : true;
            return this.__parent__.communityIsNotInput;
        },
        extractDomData: function () {
            var buildImageObject = function (el) {
                var data = el.data();
                if(!data) return {};
                else return {
                    imgKey: data.original,
                    thumbNailsImgKey: data.thumbnail,
                    imageName: ''
                };
            };
            var buildFileObject = function (el) {
                var data = el.data();
                if(!data) return {};
                else return {
                    fileName: data.filename,
                    fileUrlKey: data.original
                };
            };

            var attr = this._extractDomData(this.$('[data-params]'));

            attr.propertyCertificateImg = buildImageObject(this.$('.property-certificate-images .item'));
            attr.attachmentFile = buildFileObject(this.$('.attachment-files .item'));

            attr.provinceCode = this.$('[name="provinceCode"]').val();
            attr.cityCode = this.$('[name="cityCode"]').val();
            attr.areaCode = this.$('[name="areaCode"]').val();
            attr.address = this.$('.address').text();
            
            return attr;
        },
        save: function() {
            var attr = this.extractDomData();
            this.model.rSet('subjectMatterAppendix', attr);
        }
    });

    module.exports = BasicInfoView;

});