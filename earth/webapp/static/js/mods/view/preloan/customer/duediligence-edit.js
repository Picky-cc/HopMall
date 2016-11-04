define(function(require, exports, module) {
    var path = require('scaffold/path');
    var popupTip = require('component/popupTip');
    var FileUploadPlugin = require('component/fileUpload');
    var baseFormView = require('baseView/baseFormView');
    var FieldsetView = baseFormView.FieldsetView;
    var FormDialogView = baseFormView.FormDialogView;


    var $ = jQuery;
    var root = global_config.root;
    var upload_path = image_oss_host;

    var placeFileDom = function(file, el) {
        var tar = el.parents('.field-row').find('.upload-files');
        tar.html(file);
    };

    var createFileDom = function(data) {
        var file = '<li class="item" data-filename="' + data.fileName + '" data-currentstoredkey="' + (data.currentStoredKey || '') + '">' +
                        '<a target="_blank" class="filename" href="' + upload_path.original + '/' + data.currentStoredKey + '">' + data.fileName + '</a><a href="#" class="delete">删除</a>' +
                    '</li>';
        return file;
    };

    var FILE_FILTER_LIMIT = {
        duediligence_item_license_original: ['.png', '.jpg'],
        duediligence_item_license_copy: ['.png', '.jpg'],
        duediligence_item_national_tax_certificate: ['.png', '.jpg'],
        duediligence_item_land_tax_certificate: ['.png', '.jpg'],
        duediligence_item_org_code_certificate_original: ['.png', '.jpg'],
        duediligence_item_org_code_certificate_copy: ['.png', '.jpg'],
        duediligence_item_shareholding_structure: ['.pdf', '.doc', '.docx', '.png', '.jpg', '.rar', '.zip'],
        duediligence_item_by_laws: ['.pdf', '.doc', '.docx', '.png', '.jpg', '.rar', '.zip'],
        duediligence_item_credit_query_authorization: ['.png', '.jpg'],
        duediligence_item_corporate_representative_id_card: ['.png', '.jpg'],
        duediligence_item_company_history: ['.pdf', '.doc', '.docx', '.png', '.jpg', '.rar', '.zip'],

        duediligence_item_financial_report: ['.pdf', '.doc', '.docx', '.rar', '.zip'],
        duediligence_item_audit_report: ['.pdf', '.doc', '.docx', '.rar', '.zip'],
        duediligence_item_other_taxes_and_fees_description: ['.pdf', '.doc', '.docx', '.png', '.jpg', '.rar', '.zip'],

        duediligence_item_contract: ['.pdf', '.doc', '.docx', '.png', '.jpg', '.rar', '.zip'],
        duediligence_item_asset: ['.pdf', '.doc', '.docx', '.png', '.jpg', '.rar', '.zip'],
        duediligence_item_other: ['.pdf', '.doc', '.docx', '.png', '.jpg', '.rar', '.zip']
    };

    var DEFAULT_FILE_OPTION = {
        action: root + '/preloan/file',
        onPlacement: placeFileDom,
        createItemDom: createFileDom
    };

    var EditFileView = FormDialogView.extend({
        template: _.template($('#editFileTmpl').html() || ''),
        initialize: function(opt) {
            EditFileView.__super__.initialize.apply(this, arguments);

            var options = $.extend({}, DEFAULT_FILE_OPTION, {fileFilter: opt.fileFilter});

            FileUploadPlugin.globalUploadFile(this.$('.file-input'), options);
        },
        extractDomData: function() {
            var attr = this._extractDomData(this.$('.real-value'));

            attr.fileInfos = [];
            var files = this.$('.upload-files .item');
            for(var i=0; i<files.length; i++) {
                var d = files.eq(i).data();
                var ext = path.extname(d.filename);
                if(d) {
                    attr.fileInfos.push({
                        fileName: attr.fileName ? (attr.fileName + ext) : d.filename,
                        currentStoredKey: d.currentstoredkey
                    });
                }
            }

            return attr;
        },
        submitHandler: function() {
            this.save();
            this.hide();
        }
    });


    var DuediligenceEditView = Backbone.View.extend({
        el: $('.form-wrapper'),
        events: {
            'click .offline-done': 'onClickOfflineDone',
            'click .submit': 'onClickSubmit'
        },
        initialize: function(opts) {
            this.options = opts || {};
            var getItemKey = this.getItemKey;

            this.$('.file-input').each(function() {
                var key = getItemKey(this);

                var options = $.extend({}, DEFAULT_FILE_OPTION, {fileFilter: FILE_FILTER_LIMIT[key]});

                FileUploadPlugin.globalUploadFile($(this), options);
            });                        
        },
        getItemKey: function(tar) {
            return $(tar).parents('[data-item-key]').data('item-key');  
        },
        onClickOfflineDone: function(e) {
            // var uploadbtn = $(e.target).parents('.field-row').find('.file-input');
            // if(e.target.checked) {
            //     uploadbtn.attr('disabled', true);
            // }else {
            //     uploadbtn.attr('disabled', false);
            // }
        },
        validate: function() {
            return true;
        },
        onClickSubmit: function(e) {
            e.preventDefault();
            if(!this.validate()) return;
            var attr = this.extractFromDom();
            var url = path.format({
                path: root + '/preloan/customer',
                query: {
                    customerUuid: this.options.customerUuid,
                    key: this.options.key,
                    k: 'duediligence'
                }
            });
            this.submit(attr, function() {
                popupTip.show('编辑成功，正在跳转...');
                setTimeout(function() {
                    location.assign(url);
                }, 1500);
            });
        },
        extractFromSection: function(section) {
            var sec = {};
            var fields = section.find('[data-item-key]');
            var subfields = section.find('[data-subitem-key]');

            for(var i=0; i<fields.length; i++) {
                var el = fields.eq(i);
                var key = el.data('item-key');
                var subels = subfields.filter('[data-paritem-key='+key+']');
                var field = this.extractFromField(el, subels);
                sec[key]=field;
            }
            return sec;
        },
        extractFromField: function(field, subfields) {
            var fie = {};
            fie.hasOfflineArchives = field.find('.offline-done').get(0).checked;
            
            fie.fileInfos = [];
            var files = field.find('.upload-files .item');
            for(var i=0; i<files.length; i++) {
                var d = files.eq(i).data();
                if(d) {
                    fie.fileInfos.push({
                        fileName: d.filename,
                        currentStoredKey: d.currentstoredkey
                    });
                }
            }


            if(subfields && subfields.length>0) {
                var subs = fie.subTextItemsMap={};
                for(var j=0; j<subfields.length; j++) {
                    var el = subfields.eq(j);
                    var key = el.data('subitem-key');
                    var value = el.find('.real-value').val();
                    subs[key] = value;
                }
            }

            return fie;
        },
        extractFromDom: function() {
            var attr = {};
            var sections = this.$('[data-section-key]');

            for(var i=0; i<sections.length; i++) {
                var el =sections.eq(i);
                var key = el.data('section-key');
                var sec = this.extractFromSection(el);
                attr[key]=sec;
            }

            return attr;
        },
        submit: function(attr, success) {
            var action = root + '/preloan/customer/edit?k=duediligence';
            var params = {};
            params.customerUuid = this.options.customerUuid;
            params.key = this.options.key;
            params.sectionsMap = JSON.stringify(attr);
            $.post(action, params, function(resp) {
                try {
                    resp = JSON.parse(resp);
                    if(resp.code == 0) {
                        success(resp.data);
                    }else {
                        popupTip.show(resp.message);
                    }
                }catch(err) {
                    popupTip.show('无法解析的json格式');
                }
            });
        }
    });

    DuediligenceEditView.extend = function(protoProps) {
        var view = Backbone.View.extend.apply(this, arguments); // this: 父类
        view.prototype.events = _.extend({}, this.prototype.events, protoProps.events);
        return view;
    };


    var BasicView = DuediligenceEditView;

    var FinanceView = DuediligenceEditView.extend({
        events: {
            'click .add': 'onClickAdd'
        },
        onClickAdd: function(e) {
            e.preventDefault();

            var par = $(e.target).parents('.field-row');
            var key = this.getItemKey(e.target);
            var model = new Backbone.Model();
            var container = par.find('.upload-files');

            model.once('change', function(model) {
                if(container) {
                    var file = createFileDom(model.get('fileInfos')[0]);
                    container.append(file);
                }    
            });

            var dialog = new EditFileView({
                model: model, 
                fileFilter: FILE_FILTER_LIMIT[key]
            });

            if(key === 'duediligence_item_financial_report') {
                dialog.updateTitle('添加财务报告');
                dialog.show();
            }else if(key === 'duediligence_item_audit_report') {
                dialog.updateTitle('添加审计报告');
                dialog.show();
            }else if(key === 'duediligence_item_other_taxes_and_fees_description') {
                dialog.updateTitle('添加其他税费事项说明');
                dialog.show();
            }
        }
    });

    
    var OtherView = DuediligenceEditView.extend({
        events: {
            'click .add': 'onClickAdd'
        },
         onClickAdd: function(e) {
            e.preventDefault();

            var par = $(e.target).parents('.field-row');
            var key = this.getItemKey(e.target);
            var model = new Backbone.Model();
            var container = par.find('.upload-files');

            model.once('change', function(model) {
                if(container) {
                    var file = createFileDom(model.get('fileInfos')[0]);
                    container.append(file);
                }    
            });

            var dialog = new EditFileView({
                model: model, 
                fileFilter: FILE_FILTER_LIMIT[key]
            });

            if(key === 'duediligence_item_contract') {
                dialog.updateTitle('协议材料');
                dialog.show();
            }else if(key === 'duediligence_item_asset') {
                dialog.updateTitle('资产材料');
                dialog.show();
            }else if(key === 'duediligence_item_other') {
                dialog.updateTitle('其他材料');
                dialog.show();
            }
        }
    });
     
    exports.basic = BasicView;
    exports.finance = FinanceView;
    exports.other = OtherView;

});