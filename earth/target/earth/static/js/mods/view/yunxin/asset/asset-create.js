define(function (require, exports, module) {
    var TableContentView = require('baseView/tableContent');
    var FormDialogView = require('baseView/baseFormView').FormDialogView;
    var popupTip = require('component/popupTip');
    var FileUploadPlugin = require('component/fileUpload');

    var $ = jQuery;
    var root = global_config.root;

    var ImportAssertView = FormDialogView.extend({
        template: _.template($('#importAssertTmpl').html(), {variable: 'obj'}),
        actions: {
            create: root + '/assets-package/excel-create-assetData'
        },
        events: {
            'change [name=file]': 'onChangeFile'
        },
        initialize: function (options) {
            ImportAssertView.__super__.initialize.apply(this, arguments);
            this.defineValidator();
            
        },
        resetFormElement: function(el) {
            el.wrap('<form>').parent('form').get(0).reset();
            el.unwrap();
        },
        onChangeFile: function(e) {
            if(!/\.xlsx?$/.test(e.target.value)) {
                popupTip.show('请上传Excel文件');
                this.resetFormElement($(e.target));
            }
        },
        defineValidator: function () {
            this.validator = this.$('.form').validate({
                rules: {
                    financialContractNo: 'required',
                    file: 'required'
                }
            });
        },
        validate: function () {
            return this.validator.form();
        },
        save: function () {
            var self = this;
            
            var opt = {
                url: this.actions.create,
                type: 'post',
                dataType: 'json'
            };

            opt.success = function(resp) {
                popupTip.show(resp.message);
                self.model.trigger('create', resp);
            };

            opt.error = function(resp) {
                popupTip.show('网络错误！稍后再试！');
            };

            this.$('.form').ajaxSubmit(opt);
        },
        submitHandler: function (e) {
            if(!this.validate()) return;
            this.save();
            this.hide();
        }
    });

	var AssertCreateView = TableContentView.extend({
        events: {
            'click .delete': 'onClickDelete',
            'click .activate': 'onClickActivate',
            'click #importAssert': 'onClickImportAssert'
        },
        actions: {
            delete: root + '/loan-batch/delete-loan-batch',
            activate: root + '/loan-batch/activate'
        },
        getDomOptions: function(el) {
            var d = $(el).parents('tr').data();
            return {
                loanBatchId: d.id,
                code: d.code
            };
        },
        delayReload: function(time) {
            setTimeout(function() {
                location.reload();
            }, time || 1000);  
        },
        onClickDelete: function(e) {
            e.preventDefault();

            var self = this;
            var data = this.getDomOptions(e.target);
            var r = confirm("是否确认删除！" + data.loanBatchId);

            if (r == true) {
                var opt = {
                    type: 'POST',
                    url: this.actions.delete,
                    data: data,
                    dataType: 'json'
                };

                opt.success = function(resp) {
                    if (resp.code == 0) {
                        popupTip.show(resp.message);
                        self.delayReload();
                    } else {
                        popupTip.show(resp.message);
                    }
                };

                opt.error = function() {
                    popupTip.show('网络错误！稍后再试！');
                };

                $.ajax(opt);
            }
        },
        onClickActivate: function(e) {
            e.preventDefault();

            var self = this;
            var data = this.getDomOptions(e.target);
            var r = confirm("是否确认激活！");

            if(r == true){
                var opt = {
                    type: 'POST',
                    url: this.actions.activate,
                    data: data,
                    dataType: 'json'
                };

                opt.success = function(resp) {
                    if (resp.code == 0) {
                        popupTip.show(resp.message);
                        self.refresh();
                    } else {
                        popupTip.show(resp.message);
                    }
                };

                opt.error = function() {
                    popupTip.show('网络错误！稍后再试！');
                };

                $.ajax(opt);
            }
        },
        onClickImportAssert: function(e) {
            var self = this;
            var model = new Backbone.Model();
            var view = new ImportAssertView({model: model});

            model.once('create', function(resp) {
                if(resp.code == 0) {
                   self.delayReload();
                }
            });

            view.show();
        }
    });

    module.exports = AssertCreateView;

});