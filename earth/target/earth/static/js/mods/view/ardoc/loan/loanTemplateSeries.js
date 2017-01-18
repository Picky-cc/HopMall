define(function (require, exports, module) {
    var TableContentView=require('../../tableContent');
    var RecordListView=require('../recordList');

    var util = require('scaffold/util');
    var popupTip = require('component/popupTip');
    var ARDocBlockStructure = require('entity/ardoc/arDocStructure').ARDocBlockStructure;


    var LoanTemplateListView = TableContentView.extend({
        initialize: function () {
            LoanTemplateListView.__super__.initialize.apply(this, arguments);
            this.setPageControl({url: '/', totalPageNum: 11});
        }
    });

    var LoanTemplateDetailView = TableContentView.extend({
        initialize: function (projectId, shcemaStr) {
            LoanTemplateDetailView.__super__.initialize.apply(this, arguments);
            this.setPageControl({url: '/', totalPageNum: 1});

            shcemaObj = JSON.parse(shcemaStr);
            this.structure = new ARDocBlockStructure(shcemaObj);
            this.generateListFields();
            this.fillTableData();
        },
        generateListFields: function () {
            var options = {
                pagination: false,
                pageSize: 10,
                idField: 'id'
            };
            options.columns = [{
                title: '序号',
                formatter: function(val, row, index) {
                    return index + 1;
                }
            }, {
                title: '字段名称',
                field: 'displayName'
            }, {
                title: '字段类型',
                field: 'dataType'
            }, {
                title: '可空',
                field: 'isnull'
            }, {
                title: '备注',
                field: 'remark'
            }];
            this.tableListEl.bootstrapTable(options);
        },
        fillTableData: function () {
            var data = this.structure.getColumn();
            this.tableListEl.bootstrapTable('load', data);
        }
    });

    var FieldItemTmplStr = ['<td><input data-paramname="displayName" type="text" class="form-control" placeholder="请输入字段名称"></td>',
        '                          <td>',
        '                            <select data-paramname="dataType" class="form-control">',
        '                              <option value="number">数字</option>',
        '                              <option value="string">字符串</option>',
        '                            </select>',
        '                          </td>',
        '                          <td><input data-paramname="allowNull" class="form-control" type="checkbox">是</td>',
        '                          <td><input data-paramname="remark" type="text" class="form-control"></td>',
        '                          <td><a href="javascript: void 0;" class="del-field-btn">删除</a></td>'
    ].join("");
    var FieldItemTmplDom = $('<tr class="field-item">').html(FieldItemTmplStr);
    var LoanTemplateEditView = Backbone.View.extend({
        el: '.content',
        events: {
            'submit #mForm': 'submitHandler',
            'click .del-field-btn': 'delTemplateFieldItem',
            'click .add-field-btn': 'addTemplateFiledItem'
        },
        initialize: function () {
            this.defineTemplateEl = this.$('.define-template');
            this.formEl = this.$('#mForm');
        },
        submitHandler: function (e) {
            e.preventDefault();
            if(!this.validate()) return;
            this.submit();
        },
        validate: function () {
            var el = this.$('[data-paramname="name"]');
            if(!el.val()) {
                el.addClass('form-control-error')
                return false;
            }else {
                el.removeClass('form-control-error');
            }
            var blockS = this.generateTmplStructure();
            if(blockS.getColumn().length<1) {
                popupTip.show('请先填写一个模版字段');
                return false;
            }
            return true;
        },
        submit: function () {
            var action = this.formEl.attr('action');
            if(!action) return;

            var formData = this.organizeFormData();
            $.ajax({
                type: 'post',
                data: formData,
                dataType: 'json',
                url: action,
                success: function (resp) {
                    
                }
            });
        },
        delTemplateFieldItem: function (e) {
            e.preventDefault();
            $(e.target).parents('.field-item').remove();
        },
        addTemplateFiledItem: function (e) {
            e.preventDefault();
            var num = this.$('.add-field-num').val() || 0;
            while(num-- > 0) {
                this.defineTemplateEl.append(FieldItemTmplDom.clone());
            }
        },
        organizeFormData: function () {
            var fields = {};

            // 基本信息
            this.$('.basic-data .form-control').each(function () {
                $.extend(fields, util.extractValueFromDifferentDom($(this)));
            });

            // 模版结构
            var blockS = this.generateTmplStructure();
            blockS.set({
                uuid: _.uniqueId(),
                name: fields.name
            });

            console.log(JSON.stringify(blockS.toJSON()));
        },
        generateTmplStructure: function () {
            var temp, that = this;
            var blockS = new ARDocBlockStructure();
            this.defineTemplateEl.find('.field-item').each(function () {
                temp = that._extractFieldData($(this));
                temp.displayName && blockS.addColumn(temp);
            });
            return blockS;
        },
        _extractFieldData: function (el) {
            var res = {}, item;
            el.find('.form-control').each(function () {
                item = util.extractValueFromDifferentDom($(this));
                $.extend(res, item);
            });
            res.uuid = _.uniqueId();
            res.interalName = '';
            res.matchPatterns = '';
            return res;
        }
    });
    
    exports.LoanTemplateListView = LoanTemplateListView;
    exports.LoanTemplateDetailView = LoanTemplateDetailView;
    exports.LoanTemplateEditView = LoanTemplateEditView;

});