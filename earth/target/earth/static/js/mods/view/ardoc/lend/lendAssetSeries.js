define(function (require, exports, module) {
    var util = require('scaffold/util');
    var path = require('scaffold/path');
    var FileUploadView = require('component/fileUploadView');
    var popupTip = require('component/popupTip');

    var loadingImgEl = global_const.loadingImg.clone();

    var supportFormData = typeof FormData !== 'undefined';
    var LendAssetAddView=Backbone.View.extend({
        el: '.content',
        events: {
            'change #fileInput': 'fileUploadChange',
            'click .file-del': 'fileDelete',
            'submit #mForm': 'submitHandler'
        },
        initialize: function (search) {
            this.fileUploadView = new FileUploadView({
                single: true,
                fileFilter: ['.xlsx', '.xls']
            });
            this.listenTo(this.fileUploadView, 'add:fileupload', this.fileAdd);
            this.formEl = this.$('#mForm');

            if(supportFormData) {
                this.ajaxSubmit = true;
            }
        },
        submitHandler: function (e) {
            // form提交和ajax提交分为两个URL好了
            if(!this.validate()) {
                e.preventDefault();
                return;
            }
            if(this.ajaxSubmit===true) {
                e.preventDefault();
                this.submit();
            }
            this.$('#submit').attr('disabled', true);
        }, 
        validate: function () {
            if(!this.hadUploadFile()) {
                popupTip.show('请先上传文件');
                return false;
            }

            var selProjectEl = this.$('#selProject');
            if(!selProjectEl.val()) {
                selProjectEl.addClass('form-control-error');
                return false;
            }else {
                selProjectEl.removeClass('form-control-error');
            }
            
            return true;
        },
        hadUploadFile: function () {
            var fileUploadView = this.fileUploadView;
            var singleMode = fileUploadView.single;
            var file = fileUploadView.getHadUploadFiles();

            if(file.name != null || !singleMode && !_.isEmpty(file)) return true;
            else return false;
        },
        submit: function () {
            var action = this.formEl.attr('action');
            if(!action) return;

            var that = this;
            var formData = this.organizeFormData();

            this.$('.operation').append(loadingImgEl);

            FileUploadView.post({
                url: action,
                data: formData,
                success: function (resp) {
                    if(resp.code=='0') {
                        var timelineUuid = resp.data.timelineUuid;
                        var projectId = resp.data.projectId;
                        location.href='./submit?projectId='+projectId+'&timelineUuid='+timelineUuid;
                    }else {
                        popupTip.show(resp.message);
                    }
                },
                complete: function (xhr) {
                    loadingImgEl.remove();
                    that.$('#submit').attr('disabled', false);
                }
            });
        },
        organizeFormData: function () {
            var fields = {};
            var formData = new FormData();
            var items=this.$('.block-data .form-control');

            $.each(items, function () {
                $.extend(fields, util.extractValueFromDifferentDom($(this)));
            });

            for(var prop in fields) {
                formData.append(prop, fields[prop]);
            }
            formData.append('ardocFile', this.fileUploadView.getHadUploadFiles());
            return formData;
        },
        fileDelete: function (e) {
            var tar = $(e.target);
            var fileId = tar.data('fileid');
            this.fileUploadView.deleteFile(fileId, function () {
                tar.parent().remove();
            });
        },
        fileAdd: function (file) {
            this.$('.had-upload')
                .html('')
                .append(this.createFileItemDom(file));
        },
        createFileItemDom: function (file) {
            var div = $('<div class="item">');
            var str = '<span class="file-name">'+file.name+'</span>';
            str += '<a href="javascript: void 0;" data-fileid='+ file.name +' class="file-del">删除</a>';
            div.html(str);
            return div;
        }
    });

    var LendAssetSubmitView = Backbone.View.extend({
        el: '.content',
        events: {
            'click #submit': 'popopErrorDialog'
        },
        initialize: function (search) {
            var submitSuccessDialogEl = this.submitSuccessDialogEl = $('#submitSuccessDialog');
            submitSuccessDialogEl.on('click', '#sure', function () {
                location.href = './projects/'+ path.parseQueryString(search).projectId+'/';
            });
            var errorDialogEl = this.errorDialogEl = $('#errorDialog');
            errorDialogEl.on('click', '#continueSubmit', _.bind(this.submit, this));
        },
        popopErrorDialog: function () {
            var num = this.$('#preCheckNum').html();
            num = parseInt(num);
            if(num>0) {
                this.errorDialogEl.modal();
            }else {
                this.submit();
            }
        },
        submit:function () {
            this.errorDialogEl.modal('hide');
            var url = './projects/submit'+location.search;
            var that = this;
            $.ajax({
                url: url,
                type: 'post',
                dataType: 'json',
                success: function (resp) {
                    if(resp.code == '0'){
                        that.submitSuccessDialogEl.modal();
                    }else {
                        popupTip.show(resp.message);
                    }
                }
            });
        }
    });

    exports.LendAssetAddView=LendAssetAddView;
    exports.LendAssetSubmitView=LendAssetSubmitView;

});