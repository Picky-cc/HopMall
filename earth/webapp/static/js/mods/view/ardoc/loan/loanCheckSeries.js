define(function(require, exports, module) {
    var TableContentView=require('../../tableContent');
    var RecordListView=require('../recordList');

    var BatchModel = require('entity/ardoc/batch');
    var path = require('scaffold/path');
    var popupTip = require('component/popupTip');
    var FileUploadView = require('component/fileUploadView');

    var $ = jQuery;
    var root = global_config.root;

    var LoanCheckListView=TableContentView.extend({
        events: {
            'click .close-batch': 'closeBatchHandler',
            'click .refute-batch': 'refuteBatchHandler'
        },
        initialize: function () {
            LoanCheckListView.__super__.initialize.apply(this, arguments);
            
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
                var h = root+'/loan/audit';
                location.href = path.join(h,that.curSelectedProjectId, that.curSelectedBatchId+'/');
            });
        },
        setCurSelectedInfo: function (tar) {
            var tr = tar.parents('tr');
            this.curSelectedBatchId = tr.data('batchid');
            this.curSelectedProjectId = tr.data('projectid');
        },
        refuteBatchHandler: function (e) {
            this.setCurSelectedInfo($(e.target));
            this.refuteTipDialog.modal();
        },
        refuteBatch: function () {
            var that = this;
            BatchModel.refute({
                timelineUuid: this.curSelectedBatchId,
                projectId: this.curSelectedProjectId
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
                projectId: this.curSelectedProjectId
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
                projectId: this.curSelectedProjectId
            }, function (resp) {
                if(resp.code == '0'){
                    that.closeTipDialog.modal('hide');
                    popupTip.show('操作成功！');
                    that.refresh();
                }else {
                    popupTip.show(resp.message);
                }
            });
        }
    });

    var supportFormData = typeof FormData !== 'undefined';
    var LoanCheckBatchRecordView = RecordListView.extend({
        events: {
            'click #closeBatch': 'closeBatchHandler',
            'click #refuteBatch': 'refuteBatchHandler'
        },
        initialize: function (projectId, timelineUuid) {
            LoanCheckBatchRecordView.__super__.initialize.apply(this, arguments);
            
            this.projectId = projectId;
            this.timelineUuid = timelineUuid;
            this.initDialog();

            this.fileUploadView = new FileUploadView({
                single: true,
                fileFilter: ['.xlsx', '.xls']
            });
            this.listenTo(this.fileUploadView, 'add:fileupload', this.fileAdd);

            this.fileUpFromEl = this.$('#fileUpFrom');
            if(supportFormData) { // undefind类型的变量职能使用typeof操作附，其他操作都会报错
                global_helperObj.appendFormParams({
                    projectId: this.projectId,
                    timlineId: this.timelineUuid
                }, this.fileUpFromEl[0]);
            }
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
        },
        fileAdd: function (file) {
            var that = this;
            var url = root + '/loan/import-excel';
            if(supportFormData) {
                var formData = new FormData();
                formData.append('projectId', this.projectId);
                formData.append('timlineId', this.timelineUuid);
                formData.append('ardocFile', this.fileUploadView.getHadUploadFiles());
                FileUploadView.post({
                    url: url,
                    data: formData,
                    success: function (resp) {
                        if(resp.code=='0') {
                            popupTip.show('文件上传成功');
                            that.refresh();
                        }else {
                            popupTip.show(resp.message);
                        }
                    }
                });
            }else {
                this.fileUpFromEl.submit();
            }
            
        },
        succCallback: function (type) {
            this[type+'TipDialog'].modal('hide');
            popupTip.show('操作成功！正在跳转...');
            setTimeout(function () {
                location.href = root + '/loan/audit';
            }, 1000);
        },
        refuteBatch: function () {
            var that = this;
            BatchModel.refute({
                timelineUuid: this.timelineUuid,
                projectId: this.projectId
            }, function (resp) {
                if(resp.code == '0'){
                    that.succCallback('refute');
                }else {
                    popupTip.show(resp.message);
                }
            });
        },
        refuteBatchHandler: function () {
            this.refuteTipDialog.modal();
        },
        closeBatchHandler: function () {
            var that = this;
            BatchModel.isFinish({
                timelineUuid: this.timelineUuid,
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
                timelineUuid: this.timelineUuid,
                projectId: this.projectId
            }, function (resp) {
                if(resp.code == '0'){
                    that.succCallback('close');
                }else {
                    popupTip.show(resp.message);
                }
            });
        },
        configBreifListFields: function (columnsObj) {
            var _repetition = columnsObj.normal.slice(0, 7);
            _repetition.splice(3, 1);
            var repetition = _repetition
                audit = _.filter(columnsObj.audit, function (item) {
                    if(item.field == 'auditStatus' || item.field == 'preCheckStatus') return true;
                });

            repetition = repetition.concat(audit);
            repetition.push({
                title: '操作',
                formatter: function(val, row, index) {
                    var res = '<a class="modify" href="javascript:void(0)" title="审核">' + (row.auditStatus === '0' ? '审核' : '重新审核') + '</a>';
                    return res;
                }
            });
            return repetition;
        }
    });

    exports.LoanCheckListView = LoanCheckListView;
    exports.LoanCheckBatchRecordView = LoanCheckBatchRecordView;

});