define(function(require, exports, module) {
    require('scaffold/dragula/dragula.css');
    var util = require('scaffold/util');
    var dragula = require('scaffold/dragula/dragula');
    var Pagination = require('component/pagination');
    var popupTip = require('component/popupTip');
    var StepOperationView = require('component/stepOperation');
    var FormDialogView = require('baseView/baseFormView').FormDialogView;

    var $ = jQuery;
    var root = global_config.root;
    var RadioCheckboxControl = global_helperObj.RadioCheckboxControl;

    var StrategyModel = Backbone.Model.extend({
        defaults: {
            financialContractUuid: '',
            businessType: '',
            accountSide: '',
            paymentStrategyMode: '0',
            paymentChannelUuids: [],
            paymentChannelOrderForBanks: {}
        },
        actions: {
            save: root + '/paymentchannel/switch/strategy/saveResult',
            getPaymentChannelList: root + '/paymentchannel/switch/strategy/step/2',
            getAllBank: root + '/paymentchannel/switch/strategy/step/3',
            previewPaymentChannelOrderForBanks: root + '/paymentchannel/switch/paymentChannelOrder'
        },
        save: function() {
            var self = this;
            var attr = this.toJSON();

            var opt = {
                url: this.actions.save,
                dataType: 'json',
                processData: false,
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify(attr)
            };

            opt.success = function(resp) {
                self.trigger('sync', self, resp);
            };

            $.ajax(opt);
        },
        getAllBank: function(callback) {
            if (this.bankList) {
                callback({
                    code: 0,
                    data: {
                        list: this.bankList
                    }
                });
                return;
            }

            var self = this;

            var opt = {
                url: this.actions.getAllBank,
                dataType: 'json',
                data: _.pick(this.toJSON(), 'financialContractUuid', 'businessType', 'accountSide')
            };

            opt.success = function(resp) {
                self.bankList = resp.data.list || [];
                callback && callback(resp);
            };

            $.ajax(opt);
        },
        getPaymentChannelList: function(callback) {
            if (this.paymentChannelList) {
                callback({
                    code: 0,
                    data: {
                        list: this.paymentChannelList
                    }
                });
                return;
            }

            var self = this;

            var opt = {
                url: this.actions.getPaymentChannelList,
                dataType: 'json',
                data: _.pick(this.toJSON(), 'financialContractUuid', 'businessType', 'accountSide')
            };

            opt.success = function(resp) {
                self.paymentChannelList = resp.data.list || [];
                callback && callback(resp);
            };

            $.ajax(opt);
        },
        updatePaymentChannelOrder: function() {
            var uuids = this.get('paymentChannelUuids');
            var source = this.paymentChannelList;
            var dest = [];

            uuids.forEach(function(paymentChannelUuid) {
                for (var i = 0, len = source.length; i < len; i++) {
                    var item = source[i];
                    if (item.paymentChannelUuid === paymentChannelUuid) {
                        source.splice(i, 1);
                        dest.push(item);
                        break;
                    }
                }
            });

            this.paymentChannelList = dest.concat(source);
        }
    });

    var mixins = {
        enableNextStop: function() {
            this.$('.next').attr('disabled', null);
        },
        disableNextStop: function() {
            this.$('.next').attr('disabled', true);
        },
        show: function() {
            this.activate(1);
            this.$('.next').attr('disabled', null);
            this.$el.show();
        },
        hide: function() {
            this.$el.hide();
        }
    };

    var SingleStrategy = StepOperationView.extend({
        templates: $('.single .step-content-item .template').toArray().map(function(item) {
            var tmplStr = $(item).html();
            return _.template(tmplStr);
        }),
        events: {
            'click .panel': 'onClickPanel'
        },
        initialize: function() {
            this.on('steped', function(index) {
                var $content = this.$contentItems.eq(index - 1);
                var tmplFn = this.templates[index - 1];

                switch (index) {
                    case 1: this.enteredStep1($content, tmplFn);
                        break;
                    case 2: this.enteredStep2($content, tmplFn);
                        break;
                }
            });

            this.on('step:done', this.onStepDone);

            this.$('.step-tip-item')
                .removeClass('z-active')
                .first()
                .addClass('z-active');

            SingleStrategy.__super__.initialize.apply(this, arguments);
        },
        onClickPanel: function(e) {
            var $target = $(e.currentTarget);

            if ($target.is('.z-active')) {
                $target.removeClass('z-active');
                this.$('.tip').hide();
            } else {
                this.$('.panel').removeClass('z-active');
                $target.addClass('z-active');
                if ($target.is('.closed')) {
                    this.$('.tip').show();
                } else {
                    this.$('.tip').hide();
                }
            }
        },
        onStepDone: function() {
            var $el = this.$('.panel.z-active');
            var paymentChannelUuid = $el.data('payment-channel-uuid');
            this.model.set('paymentChannelUuids', [paymentChannelUuid]);
        },
        enteredStep1: function($el, tmplFn) {
            var model = this.model;
            var htm = tmplFn(model.toJSON());
            $el.html(htm);
            this.enableNextStop();
        },
        enteredStep2: function($el, tmplFn) {
            var model = this.model;
            var self = this;

            model.getPaymentChannelList(function(resp) {
                if (resp.code != 0) {
                    popupTip.show(resp.message);
                    return;
                }
                var htm = tmplFn({
                    list: model.paymentChannelList,
                    model: model
                });
                $el.html(htm);

                if (!model.paymentChannelList || !model.paymentChannelList.length) {
                    self.disableNextStop();
                }
            });
        }
    });

    $.extend(SingleStrategy.prototype, mixins);

    var IssuerStrategy = StepOperationView.extend({
        templates: $('.issuer .step-content-item .template').toArray().map(function(item) {
            var tmplStr = $(item).html();
            return _.template(tmplStr);
        }),
        initialize: function() {
            this.on('step', function(index) {
                switch (index) {
                    case 2: this.beforeLeaveStep2();
                        break;
                    case 3: this.beforeLeaveStep3();
                        break;
                }
            });

            this.on('steped', function(index) {
                var $content = this.$contentItems.eq(index - 1);
                var tmplFn = this.templates[index - 1];

                switch (index) {
                    case 1: this.enteredStep1($content, tmplFn);
                        break;
                    case 2: this.enteredStep2($content, tmplFn);
                        break;
                    case 3: this.enteredStep3($content, tmplFn);
                        break;
                    case 4: this.enteredStep4($content, tmplFn);
                        break;
                }
            });

            this.on('step:done', this.onStepDone);

            this.$('.step-tip-item')
                .removeClass('z-active')
                .first()
                .addClass('z-active');

            IssuerStrategy.__super__.initialize.apply(this, arguments);
        },
        onStepDone: function() {
            
        },
        beforeLeaveStep2: function() {
            var uuids = this.$('.panel').toArray().map(function(item) {
                return $(item).data('payment-channel-uuid');
            });
            this.model.set('paymentChannelUuids', uuids);
            this.model.updatePaymentChannelOrder();
        },
        beforeLeaveStep3: function() {
            var res = {};

            this.$('tbody tr').each(function() {
                var $el = $(this);
                var bankCode = $el.data('bank-code');
                var paymentChannelUuid = $el.find('input:checked').data('payment-channel-uuid');
                res[bankCode] = paymentChannelUuid;
            });

            this.model.set('paymentChannelOrderForBanks', res);
        },
        enteredStep1: function($el, tmplFn) {
            var model = this.model;
            var htm = tmplFn(model.toJSON());
            $el.html(htm);
            this.enableNextStop();
        },
        enteredStep2: function($el, tmplFn) {
            var model = this.model;
            var self = this;

            model.getPaymentChannelList(function(resp) {
                if (resp.code != 0) {
                    popupTip.show(resp.message);
                    return;
                }
                var htm = tmplFn({
                    list: model.paymentChannelList,
                    model: model.toJSON()
                });
                $el.html(htm);

                self.dragController = dragula([$el.find('.drag-wrapper').get(0)]);

                if (!model.paymentChannelList || !model.paymentChannelList.length) {
                    self.disableNextStop();
                }
            });
        },
        enteredStep3: function($el, tmplFn) {
            var model = this.model;
            var self = this;

            model.getAllBank(function(resp) {
                if (resp.code != 0) {
                    popupTip.show(resp.message);
                    return;
                }
                var htm = tmplFn({
                    paymentChannelList: model.paymentChannelList,
                    bankList: model.bankList,
                    model: model.toJSON()
                });
                $el.html(htm);

                self.radioController && self.radioController.remove();
                self.radioController = new RadioCheckboxControl($el.find('.data-list tbody'), { groupSelector: 'tr' });
            });
        },
        enteredStep4: function($el, tmplFn) {
            var model = this.model;
            var paymentChannelList = model.paymentChannelList.slice(0);
            var bankList = model.bankList.slice(0);
            var paymentChannelOrderForBanks = model.get('paymentChannelOrderForBanks');

            var previewList = bankList.map(function(bank) {
                var a1 = [];
                var a2 = [];

                paymentChannelList.forEach(function(paymentChannel) {
                    if (paymentChannel.paymentChannelUuid === paymentChannelOrderForBanks[bank.bankCode]) {
                        a1.push(paymentChannel);
                    } else {
                        a2.push(paymentChannel);
                    }
                });

                bank.paymentChannelOrder = a1.concat(a2);

                return bank;
            });

            var htm = tmplFn({
                previewList: previewList,
                paymentChannelList: paymentChannelList,
                model: model.toJSON()
            });

            $el.html(htm);
        }
    });

    $.extend(IssuerStrategy.prototype, mixins);

    var SetChannelStrategyView = FormDialogView.extend({
        el: $('#setChannelStrategy').get(0),
        events: {
            'change [name=channel-mode]': 'onChangeChannelMode'
        },
        initialize: function() {
            SetChannelStrategyView.__super__.initialize.apply(this, arguments);

            this.singleStrategy = new SingleStrategy({
                model: this.model,
                el: this.$('.single').get(0)
            });
            this.issuerStrategy = new IssuerStrategy({
                model: this.model,
                el: this.$('.issuer').get(0)
            });


            this.listenTo(this.singleStrategy, 'step:done', function() {
                this.model.save();
                this.hide();
            });
            this.listenTo(this.issuerStrategy, 'step:done', function() {
                this.model.save();
                this.hide();
            });

            this.singleStrategy.show();
            this.issuerStrategy.hide();

            this.listenTo(this.model, 'change:paymentStrategyMode', function(model, mode) {
                if (mode == 1) {
                    this.singleStrategy.hide();
                    this.issuerStrategy.show();
                } else {
                    this.singleStrategy.show();
                    this.issuerStrategy.hide();
                }
            });

            this.listenTo(this.model, 'sync', function(model, resp) {
                if (resp.code == 0) {
                    popupTip.show('提交成功');
                    util.delayReload();
                } else {
                    popupTip.show(resp.message);
                }
            });
        },
        onChangeChannelMode: function(e) {
            var mode = $(e.target).val();
            this.model.set('paymentStrategyMode', mode);
        }
    });


    var PreviewPriorityView = FormDialogView.extend({
        el: $('#priorityPreview').get(0),
        initialize: function() {
            var self = this;
            var QPagination = Pagination.extend({
                refreshTableDataList: function(data, opepration, response, query) {
                    if (!this.template) return;

                    var htm;
                    if (data.length < 1) {
                        htm = '<tr class="nomore"><td style="text-align: center;" colspan="' + this.rowNum + '">没有更多数据</td></tr>';
                    } else {
                        htm = this.template({
                            list: this.polish(data),
                            paymentChannelSize: data[0].paymentChannelList.length
                        });
                    }

                    this.tableListEl.html(htm);
                    this.trigger('refresh', data, opepration, response, query);
                },
                collectParams: function() {
                    return self.model.toJSON();
                }
            });

            this.pagination = QPagination.find('.block');
        }
    });

    var ChannelDetailView = Backbone.View.extend({
        el: $('.content').get(0),
        events: {
            'click .set-channel-strategy': 'onClickSetChannelStrategy',
            'click .btn-priority-preview': 'onClickPreviewPriority'
        },
        onClickSetChannelStrategy: function(e) {
            e.preventDefault();
            var $target = $(e.target);
            var model = new StrategyModel({
                financialContractUuid: this.financialContractUuid,
                accountSide: $target.parents('[data-account-side]').data('account-side'),
                businessType: $target.parents('[data-business-type]').data('business-type')
            });
            var view = new SetChannelStrategyView({model: model});
            view.show();
        },
        onClickPreviewPriority: function(e) {
            e.preventDefault();
            var $target = $(e.target);
            var model = new StrategyModel({
                financialContractUuid: this.financialContractUuid,
                accountSide: $target.parents('[data-account-side]').data('account-side'),
                businessType: $target.parents('[data-business-type]').data('business-type')
            });
            var view = new PreviewPriorityView({model: model});
            view.show();
        },
        initialize: function(financialContractUuid) {
            this.financialContractUuid = financialContractUuid;
        }
    });


    module.exports = ChannelDetailView;
});