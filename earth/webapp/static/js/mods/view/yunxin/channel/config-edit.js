define(function(require, exports, module) {
    var DialogView = require('component/dialogView');
    var popupTip = require('component/popupTip');
    var Pagination = require('component/pagination');
    var SegmentControlView = require('component/tab').SegmentControlView;
    var baseView = require('baseView/baseFormView');
    var FileUploadPlugin = require('component/fileUpload');
    var FormDialogView = baseView.FormDialogView;
    var BaseFormView = baseView.BaseFormView;
    var FieldsetView = baseView.FieldsetView;

    var ConfigEditModel = Backbone.Model.extend({
        defaults: {
            creditChannelConfigure: { // 付款
                channelStatus: 'NOTLINK', // 通道状态 停用、开启等
                chargeExcutionMode: '', // 费用模式 向前 向后 等
                chargePerTranscation: '', // 单笔固定时的单笔费用
                chargeType: '', // 交易类型 批量 单笔 都支持
                clearingInterval: '', // 清算周期
                side: '', // 收款方 付款方（应该用不到）
                trasncationLimitPerTransaction: '', // 通道单笔限额
                chargeRateMode: '', // 通道扣率模式 单笔固定 单笔比例
                chargeRatePerTranscation: '', // 比率
                upperChargeLimitPerTransaction: '', // 最高收取
                lowerestChargeLimitPerTransaction: '', // 最低收取
                bankLimitationFileKey: '',
                bankLimitationFileName: ''
            },
            debitChannelConfigure: { // 收款，同付款格式
                channelStatus: 'NOTLINK',
                chargeExcutionMode: '',
                chargePerTranscation: '',
                chargeType: '',
                clearingInterval: '',
                side: '',
                trasncationLimitPerTransaction: '',
                chargeRateMode: '',
                chargeRatePerTranscation: '',
                upperChargeLimitPerTransaction: '',
                lowerestChargeLimitPerTransaction: '',
                bankLimitationFileKey: '',
                bankLimitationFileName: ''
            }
        },
        actions: {
            check: './edit/check',
            save: function() {
                return './' + this.get('paymentChannelUuid') + '/save';
            }
        },
        initialize: function() {
            this.actions.save = this.actions.save.bind(this);
        },
        setBaseInfo: function(attr) {
            this.set(attr);
        },
        setDebit: function(attr) {
            this.set('debitChannelConfigure', attr);
        },
        setCredit: function(attr) {
            this.set('creditChannelConfigure', attr);
        },
        post: function() {
            var self = this;
            var opt = {
                url: this.actions.save(),
                type: 'post',
                dataType: 'json',
                data: {}
            };

            opt.data = {
                paymentChannelUuid: this.get('paymentChannelUuid'),
                paymentChannelName: this.get('paymentChannelName'),
                data: JSON.stringify(this.toJSON())
            };

            opt.success = function(resp) {
                self.trigger('sync', self, resp);
            };

            $.ajax(opt);
        },
        paymentChannelNameIsUnique: function(value) {
            var result = true;

            var opt = {
                url: this.actions.check,
                data: {
                    paymentChannelName: value,
                    paymentChannelUuid: this.get('paymentChannelUuid')
                },
                async: false,
                dataType: 'json'
            };

            opt.success = function(resp) {
                result = resp.code == 0;
            };

            $.ajax(opt);

            return result;
        }
    });

    var BaseinfoView = FieldsetView.extend({
        initialize: function() {
            this.defineValidator();
        },
        defineValidator: function() {
            var self = this;

            $.validator.addMethod('ajaxCheck', function(value, element) {
                return this.optional(element) || self.model.paymentChannelNameIsUnique(value);
            });

            this.validator = this.$('form').validate({
                onkeyup: false,
                rules: {
                    paymentChannelName: {
                        required: true,
                        maxlength: 50,
                        ajaxCheck: true
                    },
                    relatedFinancialContractName: 'required',
                    captitalAccountNameAndNo: 'required'
                },
                messages: {
                    paymentChannelName: {
                        maxlength: '不能超过50个字符',
                        ajaxCheck: '通道名称已存在'
                    }
                }
            });
        },
        validate: function() {
            return this.validator.form();
        },
        save: function() {
            var attr = this.extractDomData();
            this.model.setBaseInfo(attr);
        }
    });

    var PaymentView = FieldsetView.extend({
        events: {
            'change [name=chargeRateMode]': 'onChangeChargeRateMode',
            'change [name=channelStatus]': 'render',
            'click .quota-preview': 'onClickQuotaPreview'
        },
        actions: {
            upload: function() {
                var search = [
                    'paymentInstitutionName=' + this.paymentInstitutionOrdinal,
                    'outlierChannelName=' + this.outlierChannelName,
                    'accountSide=' + this.accountSide
                ];
                return global_config.root + '/paymentchannel/file/upload?' + search.join('&');
            }
        },
        initialize: function() {
            this.paymentInstitutionOrdinal = $('[name=paymentInstitutionOrdinal]').val();
            this.outlierChannelName = $('[name=outlierChannelName]').val();

            this.defineValidator();
            this.initFileUpload();
            this.render();
        },
        initFileUpload: function() {
            var self = this;

            var placeFileDom = function(file, el) {
                var $tar = self.$(el.data('target'));
                $tar.html(file);
            };

            FileUploadPlugin.globalUploadFile(this.$('.file-input'), {
                action: this.actions.upload.call(this),
                fileFilter: ['.xls', '.xlsx'],
                onPlacement: placeFileDom,
                createItemDom: function(data, sourceFile) {
                    var file = '<li class="item" data-filename="' + data.fileName + '" data-filekey="' + data.fileKey + '">' +
                                    '<span class="filename">' + data.fileName + '</span>' +
                                    '<a href="#" class="delete">删除</a>' +
                                '</li>';
                    return file;
                }
            });
        },
        defineValidator: function() {
            this.validator = this.$('form').validate({
                groups: {
                    chargeRateModeGroup: 'chargeRateMode chargePerTranscation chargeRatePerTranscation lowerestChargeLimitPerTransaction upperChargeLimitPerTransaction'
                },
                rules: {
                    channelStatus: 'required',
                    chargeRateMode: 'required',
                    chargePerTranscation: {
                        nonNegativeNumber: true
                    },
                    trasncationLimitPerTransaction: {
                        positiveNumber: true
                    },
                    chargeExcutionMode: 'required',
                    clearingInterval: {
                        required: true,
                        nonNegativeNumber: true
                    },
                    chargeRatePerTranscation: {
                        required: true,
                        positiveNumber: true
                    },
                    lowerestChargeLimitPerTransaction: {
                        positiveNumber: true,
                        minRelatedElement: $.proxy(function() {
                            return this.$('[name=upperChargeLimitPerTransaction]'); // 该元素可能不存在于dom中所以要动态获取
                        }, this)
                    },
                    upperChargeLimitPerTransaction: {
                        positiveNumber: true,
                        maxRelatedElement: $.proxy(function() {
                            return this.$('[name=lowerestChargeLimitPerTransaction]');
                        }, this)
                    }
                },
                messages: {
                    lowerestChargeLimitPerTransaction: {
                        minRelatedElement: '最高收取应大于最低收取'
                    },
                    upperChargeLimitPerTransaction: {
                        maxRelatedElement: '最高收取应大于最低收取'
                    }
                },
                errorPlacement: function(error, element) {
                    var parent = $(element).parent();
                    if (parent.is('.parcel-input')) {
                        parent.parent().append(error);
                    } else {
                        $(element).parent().append(error);
                    }
                }
            });
        },
        validate: function() {
            return this.validator.form();
        },
        extractDomData: function() {
            var attr = this._extractDomData(this.$('.real-value'), true);

            var file = this.$('.upload-files .item');
            attr.bankLimitationFileKey = file.data('filekey');
            attr.bankLimitationFileName = file.data('filename');

            return attr;
        },
        onChangeChargeRateMode: function(e) {
            if (!e.target.value) return;

            var data = this.model.toJSON();
            var t = data[this.type + 'ChannelConfigure'];
            t.chargeRateMode = e.target.value;
            var htm = this.chargeRateModeTemplate(data);
            this.$('.charge-rate-mode').html(htm);
        },
        render: function() {
            var target = this.$('[name=channelStatus]').get(0);
            var val = target.value;
            if (val == 'OFF') {
                this.$('.real-value').not(target).attr('disabled', true);
                this.$('.upload-files .delete').hide();
                var fileUploadPlugin = this.$('.file-input').data('fileUploadPlugin');
                fileUploadPlugin && fileUploadPlugin.setDisable(true);
            } else {
                this.$('.real-value').not(target).attr('disabled', null);
                this.$('.upload-files .delete').show();
                var fileUploadPlugin = this.$('.file-input').data('fileUploadPlugin');
                fileUploadPlugin && fileUploadPlugin.setDisable(false);
            }
        },
        onClickQuotaPreview: function(e) {
            e.preventDefault();
            var model = new Backbone.Model({
                // paymentChannelUuid: this.model.get('paymentChannelUuid'),
                paymentInstitutionOrdinal: this.paymentInstitutionOrdinal,
                outlierChannelName: this.outlierChannelName,
                accountSide: this.accountSide
            });
            var view = new QuotaPreview({model: model});
            view.show();
        }
    });

    var DebitView = PaymentView.extend({
        type: 'debit',
        chargeRateModeTemplate: _.template($('.fieldset-debit .charge-rate-mode').html() || ''),
        accountSide: 1,
        save: function() {
            var attr = this.extractDomData();
            this.model.setDebit(attr);
        }
    });

    var CreditView = PaymentView.extend({
        type: 'credit',
        chargeRateModeTemplate: _.template($('.fieldset-credit .charge-rate-mode').html() || ''),
        accountSide: 0,
        save: function() {
            var attr = this.extractDomData();
            this.model.setCredit(attr);
        }
    });


    var QuotaPreview = FormDialogView.extend({
        el: $('#quotaPreview').get(0),
        // events: {
        //     'click #lookup': 'onClickLookup'
        // },
        initialize: function() {
            QuotaPreview.__super__.initialize.apply(this, arguments);

            this.segmentControl = new SegmentControlView({ el: this.$('.segment-control').get(0) });

            var self = this;
            var QPagination = Pagination.extend({
                collectParams: function() {
                    return self.model.toJSON();
                }
            });

            QPagination.find('.segment-content-item');
        },
        onClickLookup: function() {
            var active = this.segmentControl.getActive();
            var pagination = active.$content.data('pagination');
            pagination.query();
        }
    });

    var ConfigEditView = BaseFormView.extend({
        el: '.form-wrapper',
        template: _.template($('.form-wrapper .form').html() || ''),
        initialize: function(paymentChannelUuid) {
            ConfigEditView.__super__.initialize.apply(this, arguments);

            this.initModel(paymentChannelUuid);
            this.render();
            this.initSubView();
        },
        render: function() {
            var data = this.model.toJSON();
            var htm = this.template(data);
            this.$('.form').html(htm).show();
        },
        initModel: function(paymentChannelUuid) {
            var attr = JSON.parse(this.$('[name=model]').val() || '{}');

            this.model = new ConfigEditModel(attr);

            if (typeof paymentChannelUuid == 'string') {
                this.model.set('paymentChannelUuid', paymentChannelUuid);
            }

            this.listenTo(this.model, 'sync', function(model, resp) {
                if (resp.code != 0) {
                    popupTip.show(resp.messages);
                } else {
                    this.confirm.show('提交成功是否前去切换通道？');
                }
            });
        },
        initSubView: function() {
            this.baseinfoView = new BaseinfoView({
                model: this.model,
                el: this.$('.fieldset-baseinfo').get(0)
            });
            this.deibtView = new DebitView({
                model: this.model,
                el: this.$('.fieldset-debit').get(0)
            });
            this.creditView = new CreditView({
                model: this.model,
                el: this.$('.fieldset-credit').get(0)
            });

            this.confirm = new DialogView({
                cancelBtnTxt: '否',
                goaheadBtnTxt: '是'
            });

            this.listenTo(this.confirm, 'goahead', function() {
                location.href = global_config.root + '/paymentchannel/switch/detail/' + this.model.get('relatedFinancialContractUuid');
            }).listenTo(this.confirm, 'closedialog', function() {
                location.href = global_config.root + '/paymentchannel';
            });
        },
        validate: function() {
            var flagBaseInfo = this.baseinfoView.validate();
            var flagDebit = this.deibtView.validate();
            var flagCredit = this.creditView.validate();

            if (flagBaseInfo && flagDebit && flagCredit) {
                return true;
            }

            this.trigger('invalid');
            return false;
        },
        submitHandler: function() {
            if (!this.validate()) return;
            this.save();
        },
        save: function() {
            this.baseinfoView.save();
            this.deibtView.save();
            this.creditView.save();
            this.model.post();
        }
    });

    module.exports = ConfigEditView;
});