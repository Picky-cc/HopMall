define(function (require, exports, module) {
    var TableContentView=require('../../tableContent');
    var RecordListView=require('../recordList');

    var $ = jQuery;
    var path = require('scaffold/path');
    var popupTip = require('component/popupTip');
    var root = global_config.root;
    var submitBatch = function (data, callback) {
        var submitBatchUrl = root + '/lend/projects/submit';
        $.ajax({
            url: submitBatchUrl,
            data: data || {},
            type: 'post',
            dataType: 'json',
            success: function (resp) {
                callback && callback(resp);
            }
        });
    };

    var LendProjectListView=TableContentView;

    var LendProjectBatchView = TableContentView.extend({
        events: {
            'click .has-wrong': 'popupModal',
            'click .submit-asset': 'submitBatchHandler'
        },
        initialize: function (projectId) {
            this.constructor.__super__.initialize.apply(this, arguments);
            this.projectId = projectId;

            var dialogEl = this.dialogEl = $('#dialog');
            dialogEl.on('click', '#goahead', _.bind(this.submit, this))
                .on('click', '#gomodify', _.bind(this.redirect, this));
        },
        popupModal: function (e) {
            this.curSelectedBatchId = $(e.target).parents('tr').data('batchid');
            this.dialogEl.modal();
        },
        submitBatchHandler: function (e) {
            this.curSelectedBatchId = $(e.target).parents('tr').data('batchid');
            this.submit();
        },
        submit: function () {
            var that = this;
            submitBatch({
                projectId: this.projectId,
                timelineUuid: this.curSelectedBatchId
            }, function(resp) {
                if (resp.code == '0') {
                    that.dialogEl.modal('hide');
                    that.refresh();
                } else {
                    popupTip.show(resp.message);
                }
            });
        },
        redirect: function () {
            var curSelectedBatchId = this.curSelectedBatchId;
            if(!curSelectedBatchId) return;
            var href = location.href;
            location.assign(path.join(href, curSelectedBatchId) + '/');
        }
    });

    var LendProjectBatchRecordView = RecordListView.extend({
        events: {
            'click #submitBatch': 'submitHandler'
        },
        initialize: function (projectId, timelineUuid, structor) {
            this.projectId = projectId;
            this.timelineUuid = timelineUuid;
            this.batchStatus = $("#batchStatus").val(); // 要在configBreifListFields之前初始化，即在父类初始化之前
            this.constructor.__super__.initialize.apply(this, arguments);

            this.dialogEl = $('#dialog');
            this.dialogEl.on('click', '#goahead', _.bind(this.submit, this));
        },
        submitHandler: function () {
            this.dialogEl.modal('show');
        },
        submit:function () {
            var that = this;
            submitBatch({
                projectId: this.projectId,
                timelineUuid: this.timelineUuid
            }, function(resp) {
                if (resp.code == '0') {
                    that.dialogEl.modal('hide');
                    that.batchStatus = '1'; // 1待审核状态
                    that.refresh(); // 改为查看
                    that.$('#submitBatch').attr('disabled', true);
                } else {
                    popupTip.show(resp.message);
                }
            });
        },
        canEdit: function () {
            var batchStatus = this.batchStatus;
            var canEdit;
            // 批次：1: 待审核，3:已关闭，只能查看
            if(batchStatus === '3' || batchStatus === '1') canEdit = false;
            else canEdit = true;
            return canEdit;
        },
        configBreifListFields: function (columnsObj) {
            var that = this;
            var _repetition = columnsObj.normal.slice(0, 7);
            _repetition.splice(3, 1);
            var repetition = _repetition
                audit = _.filter(columnsObj.audit, function (item) {
                    if(item.field == 'auditStatus' || item.field == 'preCheckStatus' || item.field == 'lastModifyTime') return true;
                });

            repetition = repetition.concat(audit);
            repetition.push({
                title: '操作',
                formatter: function(val, row, index) {
                    if(that.canEdit() && row.auditStatus !== "1") { // 一条纪录已通过就不能再修改
                        return [
                            '<a class="modify" href="javascript:void(0)">',
                                '修改',
                            '</a>'
                        ].join('');
                    }else {
                        return [
                            '<a class="detail" href="javascript:void(0)">',
                                '查看',
                            '</a>'
                        ].join('');
                    }
                }
            });
            return repetition;
        }
    });

    exports.LendProjectListView=LendProjectListView;
    exports.LendProjectBatchView=LendProjectBatchView;
    exports.LendProjectBatchRecordView = LendProjectBatchRecordView;
});
