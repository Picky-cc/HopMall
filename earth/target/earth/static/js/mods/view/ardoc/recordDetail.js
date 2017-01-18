define(function(require, exports, module) {
    var commoncontentObj = global_helperObj;
    var validateHelper = global_validateHelper;
    var $ = jQuery;

    var injectAreaReg = /\<!--\s*#inject_area#\s*--\>/;
    var undefineHtml = [
        '{% if(!_.isEmpty(undefine)) { %}',
        '    <div class="block block-data" data-key="undefine">',
        '      <h3 class="hd">未定义字段</h3>',
        '      <div class="bd clearfix">',
        '        {% _.each(undefine, function(val, key){ %}',
        '          <div class="col-item" data-key="{%- key %}" data-datatype="">',
        '            <span class="col-key" title="{%- key %}">{%- key %}：</span>',
        '            <span class="col-val">{%= global_validateHelper.validateAndPack(val, null, displayMode) %}</span>',
        '          </div>',
        '        {% }); %}',
        '      </div>',
        '    </div>',
        '{% } %}'
    ].join("");
    var historyHtml = [
        '<div class="block block-historycheck">',
        '  <h3 class="hd">历史审核</h3>',
        '  <div class="bd" id="auditHistoryList">',
        '  </div>',
        '</div>'
    ].join("");
    var historyItemHtml = [
        '<div class="col-item">',
        '    <span>【{%= new Date(createTime).format("yyyy-MM-dd HH:mm:ss") %}】</span>&nbsp;&nbsp;',
        '    <span>',
        '    {% if(rentAuditStatus == \'1\'){ %}',
        '租赁合同通过,',
        '{% } %}',
        '    {% if(hostingAuditStatus == \'1\'){ %}',
        '托管合同通过,',
        '{% } %}',
        '    {% if(rentHostingAuditStatus == \'1\'){ %}',
        '租赁托管比对通过,',
        '{% } %}',
        '    {% if(cashFlowAuditStatus == \'1\'){ %}',
        '流水通过,',
        '{% } %}',
        '    </span>',
        '    <span>审核结果：</span>',
        '    {% if(status == \'2\') { %}',
        '        <span class="error">不通过</span>&nbsp;&nbsp;',
        '        <span>备注：</span>',
        '        <span class="error">{%- result %}</span>',
        '    {% }else if(status == \'1\') { %}',
        '        <span>已通过</span>&nbsp;&nbsp;',
        '        <span>备注：</span>',
        '        <span>{%- result ? result : "无" %}</span>',
        '    {% } %}',
        '</div>'
    ].join("");
    var historyItemTempFunc = _.template(historyItemHtml);

    // 时间选择控件改了之后detailview的会有问题还没改
    var RecordDetailView = Backbone.View.extend({
        tagName: 'div',
        id: 'arDetailEl',
        className: 'partition-module',
        template: _.template($('#ardocTmpl').html() || ''),
        events: {
            'blur .form-control': 'validateField',
            'click [data-toggle="datetimepicker"]': 'showDatepicker',
            'click #modified': 'modifiedDocumentRecord',
            'hidden.bs.modal': 'detachFromDomTree',
            'click .check-box-all': 'toggleAllCheckbox',
            'click .check-box': 'verifyIsAllCheck'
        },
        $loading: $('<div class="central">').html(global_const.loadingImg),
        initialize: function(options) {
            this.listenTo(this.model, 'sync', this.fillInteralData);
            this.listenTo(this.model, 'request', this.waitingMask);
            this.listenTo(this.model, 'error', this.errorHandler);

            if (options.structure) {
                this.structure = options.structure;
                this.blockTemplate = _.template(this.compactHtml(this.structure.detailViewTmpl));
            }

            // var that = this;
            // $(document).on('keyup.proxy', function(e) {
            //     e.preventDefault();
            //     if (e.keyCode == 38 || e.keyCode == 40) return;
            //     $('#' + that.id).length > 0 && that.trigger('nextmodel', e.keyCode);
            // });
            
            this.datetimepickerCache = [];
        },
        compactHtml: function(source) {
            source = source.replace(injectAreaReg, function() {
                return undefineHtml + historyHtml;
            });
            return source;
        },
        validateField: function(e) {
            var tar = $(e.target),
                colItemEl = tar.parents('.col-item');
            var dataType = colItemEl.data('datatype');
            if (!dataType) return;
            var validateResult = validateHelper.validate(tar.is('input') ? tar.val() : tar.html(), dataType);
            if (validateResult !== true) {
                tar.addClass('form-control-error');
                tar.after($('<div class="error">').html(validateResult.message));
            } else {
                tar.is('.form-control') ? tar.removeClass('form-control-error') : tar.parents('.form-control').removeClass('form-control-error');
                colItemEl.find('.error').remove();
            }
        },
        waitingMask: function(model, xhr, options) {
            if(options.closeModal) return;
            this.wrapperEl.html('').append(this.$loading);
        },
        detachFromDomTree: function() {
            // 不移除事件监听
            // jquery的$el.remove会移除事件绑定
            this.$el.detach();

            $.each(this.datetimepickerCache, function (index, pickerItem) {
                pickerItem.el.data('my.datetimepicker', null);
                pickerItem.remove();
            });
        },
        render: function() {
            var htm = this.template();
            this.$el.html(htm);
            this.modalEl = this.$('#dialog');
            this.wrapperEl = this.$('.modal-body');

            return this;
        },
        showDatepicker: function(e) {
            var tar = $(e.currentTarget);
            var picker = tar.data('my.datetimepicker');
            if(!picker) {
                var isClear = false;
                if($(e.target).is('.input-group-addon, .glyphicon-remove')) {
                    isClear = true;
                }

                tar.data('my.datetimepicker', picker = commoncontentObj.datepicker(tar));
                this.datetimepickerCache.push(picker);
                isClear ? picker.clear() : picker.show();
            }
        },
        errorHandler: function () {
            this.wrapperEl.html('<div class="central"><p>系统出错或数据有误</p></div>');
        },
        getAuditHistory: function () {
            var that = this;
            var recordId = this.model.get('id');
            if(recordId == null) return;
            var url = './' + recordId + '/log';
            $.getJSON(url, {}, function (resp) {
                if(resp.code == '0') {
                	var list = JSON.parse(resp.data.list);
                    that.$('#auditHistoryList').html(template(list));
                }                
            });

            function template (list) {
                var htm = [],
                    item;
                for(var i=0, len=list.length; i<len; i++) {
                    htm.push(historyItemTempFunc(list[i]));
                }
                return htm.join('') || '没有历史审核记录';
            }
        },
        getDataDiffResult: function() {
        	var that = this;
            var recordId = this.model.get('id');
            if(recordId == null) return;
            var url = './' + recordId + '/diff';
        	$.getJSON(url, {}, function (resp) {
                if(resp.code == '0') {
                	var diffResultMaps = JSON.parse(resp.data.diffResultMaps);
                	for (var key in diffResultMaps) { 
                		var keyPaths = key.split(".");
                		var groupKey = keyPaths[0];
                		var columnKey = keyPaths[1];
                		var targetTag = $("div[data-key='"+groupKey+"'] div[data-key='"+columnKey+"'] span[class='col-val']");
                		
                		var preColumnValue = diffResultMaps[key].preValue;
                		targetTag.append("<i class='icon modified-tip' data-title='修改前记录：<strong>"+preColumnValue+"</strong>'></i>");
                    } 
                }                
            });
        },
        fillInteralData: function(data, resp, options) {
            if(options.closeModal) {
                // close
                this.modalEl.modal('hide');
            }else {
                if (!data || _.isEmpty(data)) {
                    this.errorHandler();
                } else {
                    var displayData = data instanceof Backbone.Model ? data.toJSON() : data;
                    displayData.displayMode = this.displayMode || 'noedit';
                    var htm = this.blockTemplate(displayData);
                    this.wrapperEl.html(htm);
                    this.getAuditHistory();
                    this.getDataDiffResult();
                }
            }
            
            return this;
        },
        attachToDomTree: function(container) {
            this.displayMode === 'edit' ? (this.$('#modified').show(), this.$('.cancel-dialog').html('取消'))
                : (this.$('#modified').hide(), this.$('.cancel-dialog').html('关闭'));

            container.append(this.$el);
            this.modalEl.modal();
        },
        collectFormData: function() {
            var newDocRecord = {};

            this._collectNormalBlockData(newDocRecord);
            this._collectAuditResultData(newDocRecord);

            return newDocRecord;
        },
        _collectNormalBlockData: function(newDocRecord) {
            var dataBlocks = this.$('.block-data');
            dataBlocks.each(function() {
                var blockKey = $(this).data('key');
                if (!blockKey) return true;
                var temp = newDocRecord[blockKey] = {};
                $(this).find('.col-item').each(function() {
                    var fieldVal;
                    var fieldKey = $(this).data('key');
                    var fieldValEl = $(this).find('.real-val');
                    fieldValEl.is('input') ? (fieldVal = fieldValEl.val()) : (fieldVal = fieldValEl.html());
                    temp[fieldKey] = $.trim(fieldVal);
                });
            });
        },
        _collectAuditResultData: function(newDocRecord) {
            var temp = {};
            var el = this.$('.block-checkresult');
            if(el.length<1) return;
            var auditStatus = el.find('#auditStatus').val();
            var checkresult = $.trim(el.find('textarea').val());
            auditStatus !== '' ? (temp.auditStatus = auditStatus) : (temp.auditStatus = null);
            temp.auditResult = checkresult;

            this.$('.checkboxs [name]').each(function () {
                var name = this.name;
                var value = this.checked ? '1' : '0';
                temp[name]=value;
            });

            newDocRecord.audit = temp;
        },
        toggleAllCheckbox: function (e) {
            if($(e.target).is('.txt')) return;
            var target = $(e.currentTarget);
            var isChecked = target.children('input[type=checkbox]')[0].checked;
            if(isChecked) {
                this.$('.checkboxs input[type=checkbox]').each(function () {
                    this.checked = true;
                });
            }else {
                this.$('.checkboxs input[type=checkbox]').each(function () {
                    this.checked = false;
                });
            }
        },
        verifyIsAllCheck: function (e) {
            if($(e.target).is('.txt')) return;
            var checkboxs = this.$('.checkboxs input[type=checkbox]');
            var checkedLength = checkboxs.filter(':checked').length;

            var checkAllBtn = checkboxs.filter(function () {
                return $(this).parent().is('.check-box-all');
            }).get(0);
            checkAllBtn.checked && checkedLength--;

            if(checkedLength >= checkboxs.length-1) {
                checkAllBtn.checked = true;
                this.$('#auditStatus').children().each(function () {
                    if(this.value == '1') {
                        this.selected = true;
                        return false;
                    }
                });
            }else {
                checkAllBtn.checked = false;
                this.$('#auditStatus').children().each(function () {
                    if(this.value == '2') {
                        this.selected = true;
                        return false;
                    }
                });
                //this.$('#auditStatus').children().first().get(0).selected = true;
            }
        },
        modifiedDocumentRecord: function() {
            if (!this.displayMode || this.displayMode === 'noedit') return;

            var newDocRecord = this.collectFormData();
            if(newDocRecord.audit) {
                if(!newDocRecord.audit.auditStatus){
                    this.$('#auditStatus').addClass('form-control-error');
                    this.$('#auditResult').removeClass('form-control-error');
                    return;
                }else if(newDocRecord.audit.auditStatus == '2' && !newDocRecord.audit.auditResult){
                    this.$('#auditStatus').removeClass('form-control-error');
                    this.$('#auditResult').addClass('form-control-error');
                    return;
                }
            }

            this.$('#auditStatus').removeClass('form-control-error');
            this.$('#auditResult').removeClass('form-control-error');
            this.model.save(newDocRecord, {closeModal: true});
        },
        setDisplayMode: function(mode) {
            var options = ['edit', 'noedit'];
            ~options.indexOf(mode) ? (this.displayMode = mode) : (this.displayMode = 'noedit');
        }
    });

    module.exports = RecordDetailView;

});