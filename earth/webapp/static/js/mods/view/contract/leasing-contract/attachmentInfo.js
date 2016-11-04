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

            this.initFileUploadPlugin();
        },
        initFileUploadPlugin: function () {
            var self = this;

            FileUploadPlugin.globalUploadFile(this.$('.contract-scanfile'), {
                fileFilter: ['.jpg', '.png', '.pdf'],
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
            var result = this._extractDomData(this.$('.real-value'), true);
            var buildImageObject = function (el) {
                var data = el.data();
                if(!data) return {};
                else return {
                    imgKey: data.original,
                    imageName: data.filename
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
            var imgs = this.$('.contract-scanning').children();
            for(var i=0, len = imgs.length; i<len; i++) {
                result.contractScanning.push(buildImageObject(imgs.eq(i)));
            }

            result.otherAttachment=[];
            var files = this.$('.other-attachment').children();
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