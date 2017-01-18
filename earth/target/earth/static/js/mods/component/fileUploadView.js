// 旧版fileupload
// 逐渐去掉

define(function (require, exports, module) {
    var path = require('scaffold/path');
    var popupTip = require('./popupTip');

    function clearFileInputContent (el) {
        if(el.outerHTML) {
            el.outerHTML = '<input type="file" name="" id="fileInput">';
        }else {
            $(el).parent().html('选择文件 <input type="file" name="" id="fileInput">');
        }
    }

    var FileUploadView = Backbone.View.extend({
        el: '.file-btn',
        events: {
            'change #fileInput': 'fileUploadChange'
        },
        initialize: function(options) {
            options || (options = {});
            this.single = options.single;
            this.fileFilter = options.fileFilter || [];
            if (options.single) this.file = {};
            else this.files = {};
        },
        deleteFile: function(fileId, callback) {
            var file,
                fileInput = this.$('#fileInput')[0];

            if (this.single) {
                file = this.file;
                this.file = {};
                // 因为可能使用的是iframe
                clearFileInputContent(fileInput);
            } else {
                file = this.files[fileId];
                delete this.files[fileId];
            }

            callback && callback(file);
            if (!file) return;
            this.trigger('delete:fileupload', file);
        },
        addFile: function(file) {
            if (this.single) {
                this.file = file;
            } else {
                this.files[file.name] = file;
            }
            this.trigger('add:fileupload', file);
        },
        getHadUploadFiles: function() {
            return this.single ? this.file : this.files;
        },
        fileUploadChange: function(e) {
            var that = this;
            var file = createFile(e.target);
            if(!file) return;
//            if (this.exist(file.name)) {
//                popupTip.show('文件已存在')
//                return;
//            }
            var extname = path.extname(file.name);
            var fileFilter = this.fileFilter;
            if (fileFilter.length > 0 && fileFilter.indexOf(extname) === -1) {
                popupTip.show('资产文档上传暂只支持.xls与.xlsx文件！')
            } else {
                this.addFile(file);
            }
            
            clearFileInputContent(this.$('#fileInput')[0]);

            function createFile (tar) {
                if(tar.files) {
                    return tar.files[0];
                }else {
                    // <=ie9不支持input控件里的file对象
                    that.supportFilesObject = false;
                    return {
                        name: tar.value
                    };
                }
            }
        },
        exist: function (name) {
            if(this.single)
                return this.file.name === name;
            else
                return this.files[name] ? true : false;
        }
    }, {
        post: function(options) {
            var nop = function() {};
            var defOpt = {
                type: 'post',
                dataType: 'json',
                contentType: false, // 默认为表单提交方式，改为multipart/form-data
                processData: false, // 阻止jquery编码为字符串
                success: nop
            };
            var nopts = $.extend({}, defOpt, options);
            if (!nopts.url) throw '没有文件的上传URL';

            if (FormData) {
                $.ajax(nopts);
            } else {
                console.warn('不支持FormData');
            }
        }
    });

    module.exports = FileUploadView;

});