define(function (require, exports, module) {
    var TableContentView=require('../../tableContent');
    var RecordListView=require('../recordList');

    var util = require('scaffold/util');
    var path = require('scaffold/path');
    var popupTip = require('component/popupTip');
    var BatchModel = require('entity/ardoc/batch');

    var $ = jQuery;
    var root = global_config.root;
    var gocheckHref = root + '/loan/audit/';

    var LoanProjectListView=TableContentView.extend({
        events: {
            'click #closeAllProject': 'closeAllProject'
        },
        initialize: function () {
            LoanProjectListView.__super__.initialize.apply(this, arguments);
            this.selProjectModalEl = $('#selProDialog');
            this.befCloseTipModelEl = $('#befCloseDialog');
            this.befCloseTipModelEl.on('click', '#goahead', _.bind(this.closeProject, this));
        },
        closeAllProject: function (e) {
            var selected = this.tableListEl.find('input[type=checkbox]:checked');
            if(selected.length<1) {
                this.selProjectModalEl.modal();
            }else {
                this.befCloseTipModelEl.modal();
            }
        },
        collectWannaCloseProjectId: function () {
            var checkboxs = this.tableListEl.find('input[type=checkbox]:checked');
            var arr=[],
                id;
            for(var i= 0, len=checkboxs.length; i<len; i++) {
                id = checkboxs.eq(i).parents('tr').data('projectid');
                id && arr.push(id);
            }
            return arr;
        },
        closeProject: function () {
            var arr = this.collectWannaCloseProjectId();
            if(arr.length<1) return;
            var that = this;
            $.ajax({
                url: './loan/projects/closeproject',
                data: {projectIdList: arr.join(',')},
                dataType: 'json',
                type: 'post',
                success: function (resp) {
                    if(resp.code == '0') {
                        that.refresh();
                        that.befCloseTipModelEl.modal('hide');
                    }
                }
            });
        }
    });

    var LoanProjectBatchView = TableContentView.extend({
        events: {
            'click .close-batch': 'closeBatchHandler',
            'click .refute-batch': 'refuteBatchHandler',
            'click .gocheck': 'gocheck'
        },
        initialize: function (projectId) {
            LoanProjectBatchView.__super__.initialize.apply(this, arguments);
            this.projectId = projectId;
            
            this.initDialog();
        },
        initDialog: function () {
            var that = this;

            this.closeTipDialog = $('#befCloseTipDialog');
            this.closeTipDialog.on('click', '.goahead', function () {
                that.closeBatch();
            });

            this.refuteTipDialog = $('#refuteTipDialog');
            this.refuteTipDialog.on('click', '.goahead', function () {
                that.refuteBatch();
            });

            this.haveUnCheckTipDialog = $('#haveUnCheckTipDialog');
            this.haveUnCheckTipDialog.on('click', '.goahead', function () {
                location.href = path.join(gocheckHref, that.projectId, that.curSelectedBatchId + '/');
            });
        },
        setCurSelectedInfo: function (tar) {
            var tr = tar.parents('tr');
            this.curSelectedBatchId = tr.data('batchid');
        },
        refuteBatchHandler: function (e) {
            this.setCurSelectedInfo($(e.target));
            this.refuteTipDialog.modal();
        },
        refuteBatch: function () {
            var that = this;
            BatchModel.refute({
                timelineUuid: this.curSelectedBatchId,
                projectId: this.projectId
            }, function (resp) {
                if(resp.code == '0'){
                    that.refuteTipDialog.modal('hide');
                    popupTip.show('操作成功！');
                    that.refresh();
                }else {
                    popupTip.show(resp.message);
                }
            });
        },
        closeBatchHandler: function (e) {
            var that = this;
            this.setCurSelectedInfo($(e.target));
            BatchModel.isFinish({
                timelineUuid: this.curSelectedBatchId,
                projectId: this.projectId
            }, function (resp) {
                if(resp.code=='0') {
                    that.closeTipDialog.modal();
                }else {
                    that.haveUnCheckTipDialog.modal();
                }
            });
        },
        closeBatch: function () {
            var that = this;
            BatchModel.close({
                timelineUuid: this.curSelectedBatchId,
                projectId: this.projectId
            }, function (resp) {
                if(resp.code == '0'){
                    that.closeTipDialog.modal('hide');
                    popupTip.show('操作成功！');
                    that.refresh();
                }else {
                    popupTip.show(resp.message);
                }
            });
        },
        gocheck: function (e) {
            e.preventDefault();
            var batchId = $(e.target).parents('tr').data('batchid');
            location.href = path.join(gocheckHref, this.projectId, batchId+'/');
        }
    });

    var LoanProjectBatchRecordView = RecordListView;

    var LoanProjectEditView=Backbone.View.extend({
        el: '.content',
        events: {
            'submit #mForm': 'submitHandler',
            'click [data-teamplateid]': 'moveToSelTmplBox'
        },
        initialize: function (search) {
            this.formEl = this.$('#mForm');
            this.selTmplOptionalEl = this.$('.optional');
            this.selTmplSelectedEl = this.$('.selected');
        },
        submitHandler: function (e) {
            e.preventDefault();
            if(!this.validate()) return;
            this.submit();
        },
        submit: function () {
            var action = this.formEl.attr('action');
            if(!action) return;

            var formData = this.organizeFormData();

            var btnAdd = this.$('#btnAdd');
            btnAdd.attr('disabled', true);
            $.post('./doaddproject', formData, function (resp) {
                btnAdd.attr('disabled', false);
                var data = JSON.parse(resp);
                if(data.code == 0) {
                    location.href = "../../loan";
                }else{
                    popupTip.show(data.message);
                }
            });
        },
        validate: function () {
            var temp, flag = true;
            this.$('.basic-data .form-control').each(function () {
                temp = util.extractValueFromDifferentDom($(this));
                if(!temp) {
                    $(this).addClass('form-control-error');
                    flag = false;
                }else {
                    $(this).removeClass('form-control-error');
                }
            });
            if(!flag) return false;
            return true;
        },
        organizeFormData: function () {
            var fields = {};
            this.$('.basic-data .form-control').each(function () {
                $.extend(fields, util.extractValueFromDifferentDom($(this)));
            });

            // $.extend(fields, util.extractValueFromDifferentDom(this.$('#parentTmpl')));
            // var templates = $.map(this.selTmplSelectedEl.children(), function (el) {
            //     return $(el).data('teamplateid');
            // });
            fields.workingSchema = '';

            return fields;
        },
        moveToSelTmplBox: function (e) {
            var tar = $(e.target);
            var par = tar.parent();
            if(par.hasClass('optional')) {
                this.addToSeledtedBox(tar);
            }else if(par.hasClass('selected')) {
                this.delFromSelectedBox(tar);
            }
        },
        addToSeledtedBox: function (node) {
            if(node.hasClass('active')) return;
            node.addClass('active');
            var c_node = node.clone();
            c_node.find('.icon').removeClass('active').addClass('cancel').html('&times;');
            this.selTmplSelectedEl.append(c_node);
        },
        delFromSelectedBox: function (node) {
            node.remove();
            var str = '[data-teamplateid='+ node.data('teamplateid') +']'
            this.selTmplOptionalEl.find(str).removeClass('active');
        },
    });

    exports.LoanProjectListView=LoanProjectListView;
    exports.LoanProjectBatchView=LoanProjectBatchView;
    exports.LoanProjectBatchRecordView = LoanProjectBatchRecordView;
    exports.LoanProjectEditView = LoanProjectEditView;
});