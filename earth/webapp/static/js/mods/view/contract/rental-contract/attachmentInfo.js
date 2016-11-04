define(function(require, exports, module) {
    // module
    var FileUploadPlugin = require('component/fileUpload');
    var baseFormView = require('baseView/baseFormView');
    var FieldsetView = baseFormView.FieldsetView;

    // const
    var $ =  jQuery;
    var root = global_config.root;
    var root_image = image_oss_host;

    // attatchment view
    var AttachmentView =FieldsetView.extend({
        initialize: function () {
            AttachmentView.__super__.initialize.apply(this, arguments);

            // this.contractScanningCollection = new Backbone.Collection(this.model.get('contractScanning'));
            // this.listenTo(this.contractScanningCollection, 'all', this.renderContractScanning);
            // this.otherAttachmentCollection = new Backbone.Collection(this.model.get('otherAttachment'));
            // this.listenTo(this.otherAttachmentCollection, 'all', this.renderOtherAttachment);

            this.initFileUploadPlugin();
        },
        renderContractScanning: function () {
            var scanning = this.contractScanningCollection;
            var str = '';
            for(var i=0, len=scanning.length; i<len; i++) {
                var data = scanning.at(i).toJSON();
                str += '<li class="item" data-original="'+ data.imgKey +'">' + 
                            '<img class="img" src="'+ root_image.thumbnail + '/' + data.thumbNailsImgKey + '">' +
                        '</li>';
            }
            this.$('.upload-imgs').html(str);
        },
        renderOtherAttachment: function () {
            var attatchment = this.otherAttachmentCollection;
            var str = '';
            for(var i=0, len=attatchment.length; i<len; i++) {
                str += '<li class="item">' + attatchment.at(i).get('fileName') + '</li>';
            }
            this.$('.upload-files').html(str);
        },
        initFileUploadPlugin: function () {
            var self = this;

            FileUploadPlugin.globalUploadImage(this.$('.contract-scanfile'), {
                onSuccess: function (data, el) {
                    // scanning.add({
                    //     imgKey: data.original,
                    //     thumbNailsImgKey: data.thumbnail,
                    //     imageName: ''
                    // });
                    el.removeClass('error');
                },
                onPlacement: function(img, el) {
                    self.$(el.data('target')).append(img);
                }
            });

            FileUploadPlugin.globalUploadFile(this.$('.other-file'), {
                onSuccess: function (data, el) {
                    el.removeClass('error');
                },
                onPlacement: function(file, el) {
                    self.$(el.data('target')).append(file);
                }
            });
        },
        extractDomData: function () {
            var result = this._extractDomData(this.$('.real-value'));
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
                    fileUrlKey: data.original,
                    fileName: data.filename
                };
            };

            result.contractScanning=[];
            var imgs = this.$('.upload-imgs').children();
            for(var i=0, len = imgs.length; i<len; i++) {
                result.contractScanning.push(buildImageObject(imgs.eq(i)));
            }

            result.otherAttachment=[];
            var files = this.$('.upload-files').children();
            for(var i=0, len = files.length; i<len; i++) {
                result.otherAttachment.push(buildFileObject(files.eq(i)));
            }

            return result;
        },
        validate: function () {
            var self = this;
            var flag = true;

            // if(this.$('.upload-imgs').children().length > 0) {
            //     this.$('.contract-scanfile').removeClass('error');
            // }else {
            //     this.$('.contract-scanfile').addClass('error');
            //     flag = false;
            // }

            return flag;
        }
    });

    
    module.exports = AttachmentView;

});