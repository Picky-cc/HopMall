define(function (require, exports, module) {
    var ARRecordEntity = require('../../entity/ardoc/ardoc');
    var ARDocTotalStructure = require('../../entity/ardoc/arDocStructure').ARDocTotalStructure;
    var ARRecordModel = ARRecordEntity.ARRecordModel;

    var TableContentView=require('../tableContent');
    var DetailView = require('./recordDetail');

    var $ = jQuery,
        $body = $('body'),
        extractListDataToColumns = function (sourceList) {
            var cols  = _.map(sourceList, function (attr) {
                var model = new ARRecordModel(attr);
                return model.extractDataToColumns();
            });
            return cols;
        };

    var AuditStatus = {
        0: '待审核',
        1: '已通过',
        2: '不通过'
    }, PreCheckStatus = {
        0: '格式正确',
        1: '格式错误'
    };

    var RecordListView = TableContentView.extend({
        events: {
            'click .detail': 'showDetailNoEdit',
            'click .modify': 'showDetailEdit'
        },
        initialize: function (projectId, batchId, documentShcemaObject) {
            RecordListView.__super__.initialize.apply(this, arguments);

            this.recordListArea = this.tableListEl;
            this.initialDocumentSchema(documentShcemaObject);
            this.intialRecord(projectId, batchId);
            this.initRecordBreifListArea();
            this.initialDetailView();

            this.getFirstPageData();
        },
        intialRecord: function(projectId, batchId) {
            this.model = new ARRecordModel({}, {
                projectId: projectId,
                batchId: batchId,
                structure: this.structure
            });
            var that=this;
            this.listenTo(this.model, 'change', function(model) {
                that.recordListArea.bootstrapTable('updateRow', {
                    index: model.tableRowIndex,
                    row: $.extend(model.extractDataToColumns(), {modified: true})
                });
            });
        },
        initialDocumentSchema: function(structure) {
            this.structure = new ARDocTotalStructure(structure, {
                createDetailViewTmpl: $('#blockTmpl')
            });
        },
        initialDetailView: function () {
            this.detailView = new DetailView({
                model: this.model,
                structure: this.structure
            });
            // this.detailView.appendNextModelHandler(_.bind(this.selectItem, this));
            this.detailView.on('nextmodel', _.bind(this.selectItem, this));
            // this.listenTo(this.detailView, 'nextmodel', _.bind(this.selectItem, this));

            this.detailView.render();
        },
        processColumns: function (columnsObj) {
            $.each(columnsObj.audit, function (index, item) { // <=ie8不支持forEach
                if(item.field === 'auditStatus') {
                    item.formatter=function (val, row, index) {
                        return AuditStatus[val];
                    };
                }else if(item.field === 'preCheckStatus') {
                    item.formatter=function (val, row, index) {
                        return PreCheckStatus[val];
                    };
                }
            });
            var normals = columnsObj.normal;
            var widthLemmit = { lessee: '20%' }; // 承租方太长限制宽度
            var len = normals.length;
            for(var prop in widthLemmit) {
                var width = widthLemmit[prop];
                for(var i=0; i<len; i++) {
                    if(normals[i].field === prop) {
                        normals[i].width = width;
                        return;
                    }
                }
            }
            
            columnsObj.normal.unshift({
                title: '序号',
                formatter: function (val, row, index) {
                    return index+1;
                }
            });
        },
        _configBreifListFields: function () {
            var columnsObj = this.structure.extractColumnsForTablePlugins();
            this.processColumns(columnsObj);
            var repetition = this.configBreifListFields(columnsObj);
            return repetition;
        },
        configBreifListFields: function (columnsObj) { // 子类可覆盖该方法
            var repetition = columnsObj.normal.slice(0, 5),
                audit = _.filter(columnsObj.audit, function (item) {
                    if(item.field == 'auditStatus' || item.field == 'preCheckStatus') return true;
                });

            repetition = repetition.concat(audit);
            repetition.push({
                title: '操作',
                formatter: function(val, row, index) {
                    return [
                        '<a class="detail" href="javascript:void(0)" title="详情">',
                        '查看',
                        '</a>'
                    ].join('');
                }
            });
            return repetition;
        },
        initRecordBreifListArea: function() {
            var tableDefOpts = {
                classes: 'table-no-bordered',
                pagination: false,
                pageSize: 12,
                escape: true,
                idField: 'id',
                rowStyle: function() {
                    return {
                        classes: 'record-item'
                    };
                },
                rowAttributes: function (row, index) {
                    return {
                        'data-recordid': row.rowIndex
                    };
                }
            };
            var breifColumnDefinations = this._configBreifListFields();
            this.recordListArea.bootstrapTable(_.extend({}, tableDefOpts, {
                columns: breifColumnDefinations
            }));
        },
        refreshTableDataList: function (list) {
            var changed = extractListDataToColumns(list);
            this.recordListArea.bootstrapTable('load', changed);
        },
        selectItem: function(keyCode) {
            var actTrEl,
                items = this.recordListArea.find('.record-item'),
                curTrEl = items.filter('.active');
            switch (keyCode) {
                case 37:
                    actTrEl = curTrEl.length > 0 ? curTrEl.prev() : items.last();
                    break;
                case 39:
                    actTrEl = curTrEl.length > 0 ? curTrEl.next() : items.first();
                    break;
            }
            actTrEl && actTrEl.find('.detail').click();
        },
        showDetailNoEdit: function (e) {
            this.setDetailViewDisplayMode('noedit');
            this.showDetail(e);
        },
        showDetailEdit: function (e) {
            this.setDetailViewDisplayMode('edit');
            this.showDetail(e);
        },
        setDetailViewDisplayMode: function (mode) {
            this.detailView.setDisplayMode(mode || 'edit');
        },
        showDetail: function(e) {
            this.recordListArea.find('.record-item').removeClass('active');

            var dv = this.detailView;
            var itemEl = $(e.target).parents('.record-item').addClass('active');
            var rowIndex = itemEl.data('index');
            var recordId = itemEl.data('recordid');
            recordId == null && (recordId = $(e.target).data('recordid'));

            if(recordId == null) {
                var pageIndex = this.pageControl.getCurrentPageIndex();
                recordId = (pageIndex -1) * 12 + rowIndex;
            }

            dv.attachToDomTree($body);
            this.model.set({id: recordId}, {silent: true});
            this.model.tableRowIndex = rowIndex;
            this.model.fetch({silent: true});
        }
    });

    module.exports=RecordListView;
});