define(function(require, exports, module) {
    var path = require('scaffold/path');
    var popupTip = require('./popupTip');

    var $ = jQuery;
    var doc = document;
    var $body = $(doc.body);
    var isSupportFormData = window.FormData ? true : false;
    var defOpts = {
        process: false,
        maxSize: false,
        fileFilter: []
    };

    var EveName = {
        BeforeUpload: 'beforeupload',
        Process: 'process', // 不支持
        Error: 'error',
        Success: 'success',
        Load: 'loadlocalfile',
        Complete: 'complete'
    };

    var FORM_HTML = _.template('<form style="display: inline;" name="{%= formName %}" target="{%= frameName %}" method="post" enctype="multipart/form-data" action="{%= action %}"></form>');
    var IFRAME_HTML = _.template('<iframe style="display: none;" name="{%= frameName %}"></iframe>');

    function FileUpload(options) {
        $.extend(this, defOpts, options);
        this.fileInput = this.el.find('input[type=file]');
        var _name = this.fileInput.attr('name');
        if (_name) {
            this.uploadParamName = _name;
        }
        if (!this.uploadParamName) {
            throw '指定上传文件控件的name属性';
        }
        this.uploadParamName = _name;
        this.init();
    }

    _.extend(FileUpload.prototype, Backbone.Events, {
        init: function() {
            if (!isSupportFormData) {
                var frameName = _.uniqueId('iframe');
                var formName = _.uniqueId('form');
                this.formEl = $(FORM_HTML({
                    frameName: frameName,
                    action: this.action,
                    formName: formName
                }));
                this.iframeEl = $(IFRAME_HTML({
                    frameName: frameName
                }));
            }
            this.el.on('change', 'input[type=file]', _.bind(this.fileUploadChange, this));
        },
        resetFormElement: function(el) {
            el.wrap('<form>').parent('form').get(0).reset();
            el.unwrap();
        },
        fileUploadChange: function(e) {
            var file = compatibleFile(e.target);
            if (!file) return;
            if (!this.isRightType(file.name)) {
                var arr = this.fileFilter.map(function(item) {
                    return '"' + item + '"';
                });
                popupTip.show('只能上传 ' + arr.join('、') + ' 文件！');
                this.resetFormElement(this.fileInput);
                return;
            }
            if (!this.isLowMaxSize(file.size)) {
                popupTip.show('文件太大');
                this.resetFormElement(this.fileInput);
                return;
            }
            // 这个属性只有在支持formdata中的浏览器才可以用，其他情况使用表单提交
            this.uploadFile = file;
            this.trigger(EveName.Load, file);

            function compatibleFile(tar) {
                // <=ie9不支持input控件里的file对象
                if (tar.files)
                    return tar.files[0];
                else
                    return tar.value ? {
                        name: tar.value,
                        size: 0
                    } : void 0;
            }
        },
        exist: function(filename) {
            return false;
        },
        isRightType: function(filename) {
            var extname = path.extname(filename);
            var fileFilter = this.fileFilter;
            if (fileFilter.length > 0 && ~fileFilter.indexOf(extname.toLowerCase()) === 0)
                return false;
            else
                return true;
        },
        isLowMaxSize: function(filesize) {
            return _.isNumber(this.maxSize) ? this.maxSize >= filesize : true;
        },
        setDisable: function(disabled) {
            if (disabled) {
                this.el.addClass('disabled');
                this.fileInput.attr('disabled', true);
            } else {
                this.el.removeClass('disabled');
                this.fileInput.attr('disabled', null);
            }
        },
        post: function() {
            var self = this;
            self.trigger(EveName.BeforeUpload);
            if (isSupportFormData) {
                var options = {
                    type: 'post',
                    url: this.action,
                    dataType: 'json',
                    contentType: false, // 默认为表单提交方式，改为multipart/form-data
                    processData: false, // 阻止jquery编码为字符串
                    success: function(resp) {
                        resp.code == '0' ? self.trigger(EveName.Success, resp.data, self.uploadFile) : popupTip.show(resp.message);
                    },
                    complete: function() {
                        self.trigger(EveName.Complete);
                    }
                };
                var formdata = new FormData();
                formdata.append(self.uploadParamName, this.uploadFile);
                options.data = formdata;
                $.ajax(options);
            } else {
                self.el.after(self.formEl);
                self.formEl.append(self.el);
                $body.append(self.iframeEl);

                self.iframeEl.on('load', function() {
                    // contentDocument有ie8不支持
                    var resp = $(this).contents().find('body').html();
                    try {
                        resp = JSON.parse(resp);
                        resp.code == '0' ? self.trigger(EveName.Success, resp.data, self.uploadFile) : popupTip.show(resp.message);
                    } catch (err) {
                        self.trigger(EveName.Error, resp);
                    }
                    self.trigger(EveName.Complete);

                    self.formEl.remove();
                    self.iframeEl.remove();
                });

                self.formEl.submit();
                self.formEl.after(self.el);
            }

            this.resetFormElement(this.fileInput);
        }
    });


    // 全局上传图片配置
    var root = global_config.root;
    var upload_path = image_oss_host;
    var loadingImg = global_const.loadingImg;
    var globalUploadImageConfig = {
        action: root + '/Image/uploadImage',
        fileFilter: ['.jpg', '.png'],
        uploadParamName: 'image',
        onSuccess: function() {},
        onPlacement: function(img, el) {
            $(el.data('target')).html(img);
        },
        createItemDom: function(data, sourceFile) {
            var image = '<li class="item" data-original="' + data.original + '" data-thumbnail="' + data.thumbnail + '">' +
                '<img class="img" src="' + upload_path.thumbnail + '/' + data.thumbnail + '">' +
                '</li>';
            return image;
        }
    };
    var globalUploadFileConfig = {
        action: root + '/Image/uploadFile',
        limitNum: null,
        uploadParamName: 'file',
        fileFilter: ['.pdf', '.doc', '.jpg', '.png'],
        onSuccess: function(data, el) {},
        onPlacement: function(file, el) {
            $(el.data('target')).html(file);
        },
        createItemDom: function(data, sourceFile) {
            var file = '<li class="item" data-filename="' + sourceFile.name + '" data-original="' + data.original + '">' +
                '<a class="filename" href="' + upload_path.original + '/' + data.original + '">' + sourceFile.name + '</a><a href="#" class="delete">删除</a>' +
                '</li>';
            return file;
        }
    };

    var onHandler = function($el, options) {
        var num = 0;
        var loading = loadingImg.clone().css('margin-left', 15);
        var fileUpload = new FileUpload($.extend({
            el: $el
        }, options));

        fileUpload
            .on('loadlocalfile', function() {
                $el.after(loading);
                fileUpload.post();
            })
            .on('success', function(data, sourceFile) {
                options.onSuccess(data, $el);
                var itemEl = options.createItemDom(data, sourceFile);
                options.onPlacement(itemEl, $el);
                num++;
                if (options.limitNum && num >= options.limitNum) {
                    $el.hide();
                }
            })
            .on('complete', function() {
                loading.remove();
            });

        $(document).on('delete.fileitem', function(e) {
            num--;
            if (options.limitNum && num < options.limitNum) {
                $el.show();
            }
        });

        $el.data('fileUploadPlugin', fileUpload);
    };


    FileUpload.globalUploadImage = function(fileInputs, options) {
        options = $.extend({}, globalUploadImageConfig, options);

        fileInputs.each(function() {
            onHandler($(this), options);
        });
    };

    FileUpload.globalUploadFile = function(fileInputs, options) {
        options = $.extend({}, globalUploadFileConfig, options);

        fileInputs.each(function() {
            onHandler($(this), options);
        });
    };

    module.exports = FileUpload;


});