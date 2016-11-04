define(function(require, exports, module) {
    var popupTip = require('component/popupTip');
    var BaseFormView = require('baseView/baseFormView').BaseFormView;

    var $ = jQuery;
    var root = global_config.root;
    var BUSINESS_TYPE = {
        MINE: 0,
        AGENT: 1
    };

    var SwitchStrategyModel = Backbone.Model.extend({
        actions: {
            save: root + '/paymentchannel/switch/strategy/submit',
            debitStrategy: root + '/paymentchannel/switch/strategy/debit',
            creditStrategy: root + '/paymentchannel/switch/strategy/credit'
        },
        save: function() {
            var self = this;
            var opt = {
                dataType: 'json',
                type: 'post',
                url: this.actions.save,
                data: this.toJSON()
            };
            opt.success = function(resp) {
                self.trigger('strategy:save', resp);
            };
            $.ajax(opt);
        },
        getDebitStrategy: function(businessType) {
            // 收款策略列表
            var self = this;
            var strategyModeName = businessType === BUSINESS_TYPE.MINE ? 'debitStrategyMode' : 'acdebitStrategyMode';

            if (this.get(strategyModeName) == '-1') {
                self.trigger('strategy:debit', [], {
                    code: 0,
                    data: { lsit: [] }
                }, businessType);
            } else {
                var opt = {
                    url: this.actions.debitStrategy,
                    dataType: 'json',
                    data: {
                        financialContractUuid: this.get('financialContractUuid'),
                        businessType: businessType
                    }
                };

                opt.success = function(resp) {
                    var closed = [];
                    var unclosed = [];
                    var list = resp.data.list || [];
                    list.forEach(function(item) {
                        item.channelStatus == 'OFF' ? closed.push(item) : unclosed.push(item);
                    });
                    self.trigger('strategy:debit', unclosed.concat(closed), resp, businessType);
                };

                $.ajax(opt);
            }
        },
        getCreditStrategy: function(businessType) {
            // 付款策略列表
            var self = this;
            var strategyModeName = businessType === BUSINESS_TYPE.MINE ? 'creditStrategyMode' : 'accreditStrategyMode';

            if (this.get(strategyModeName) == '-1') {
                self.trigger('strategy:credit', [], {
                    code: 0,
                    data: { lsit: [] }
                }, businessType);
            } else {
                var opt = {
                    url: this.actions.creditStrategy,
                    dataType: 'json',
                    data: {
                        financialContractUuid: this.get('financialContractUuid'),
                        businessType: businessType
                    }
                };
                
                opt.success = function(resp) {
                	var closed = [];
                    var unclosed = [];
                    var list = resp.data.list || [];
                    list.forEach(function(item) {
                        item.channelStatus == 'OFF' ? closed.push(item) : unclosed.push(item);
                    });
                    self.trigger('strategy:credit', unclosed.concat(closed), resp, businessType);
                };

                $.ajax(opt);
            }
        }
    });

    var SwitchStrategyView = BaseFormView.extend({
        el: '.form-wrapper',
        template: _.template($('.form').html()),
        creditStrategyItemTeplate: _.template($('#creditStrategyItemTmpl').html()),
        debitStrategyItemTeplate: _.template($('#debitStrategyItemTmpl').html()),
        events: {
            'change [name=creditStrategyMode],[name=accreditStrategyMode]': 'onChangeCreditStrategyMode',
            'change [name=debitStrategyMode],[name=acdebitStrategyMode]': 'onChangeDebitStrategyMode',
            'click .item-debit,.item-credit': 'onClickStrategyItem'
        },
        initialize: function(financialContractUuid) {
            SwitchStrategyView.__super__.initialize.apply(this, arguments);

            this.initModel(financialContractUuid);

            this.model.getDebitStrategy(BUSINESS_TYPE.MINE);
            this.model.getCreditStrategy(BUSINESS_TYPE.MINE);
            this.model.getDebitStrategy(BUSINESS_TYPE.AGENT);
            this.model.getCreditStrategy(BUSINESS_TYPE.AGENT);

            this.render();
        },
        render: function() {
            var data = this.model.toJSON();
            var htm = this.template(data);
            this.$('.form')
                .html(htm)
                .show();
        },
        initModel: function(financialContractUuid) {
            var oridinalAttr = JSON.parse(this.$('[name=oridinalAttr]').val() || '{}');
            var attr = {
                financialContractUuid: financialContractUuid,

                creditChannelUuid: oridinalAttr.creditStrategyData ? oridinalAttr.creditStrategyData.paymentChannelUuid : '',
                debitChannelUuid: oridinalAttr.debitStrategyData ? oridinalAttr.debitStrategyData.paymentChannelUuid : '',
                debitStrategyMode: oridinalAttr.debitPaymentChannelMode,
                creditStrategyMode: oridinalAttr.creditPaymentChannelMode,

                accreditChannelUuid: oridinalAttr.accreditStrategyData ? oridinalAttr.accreditStrategyData.paymentChannelUuid : '',
                acdebitChannelUuid: oridinalAttr.acdebitStrategyData ? oridinalAttr.acdebitStrategyData.paymentChannelUuid : '',
                acdebitStrategyMode: oridinalAttr.acdebitPaymentChannelMode,
                accreditStrategyMode: oridinalAttr.accreditPaymentChannelMode
            };

            this.model = new SwitchStrategyModel(attr);

            this.listenTo(this.model, 'strategy:save', this.onStrategySave);
            this.listenTo(this.model, 'strategy:debit', this.onStratgyDebitGet);
            this.listenTo(this.model, 'strategy:credit', this.onStratgyCreditGet);
        },
        onClickStrategyItem: function(e) {
            e.preventDefault();
            var target = $(e.currentTarget);
            if (target.hasClass('closed')) {
                if ($(e.target).is('a')) {
                    window.open(e.target.href, '_blank');
                }
                e.stopPropagation();
                return;
            }

            var attr = target.data();
            attr.name = target.text();

            if (target.is('.item-credit')) {
                this.handleClickItemCredit(attr);
            } else if (target.is('.item-debit')) {
                this.handleClickItemDebit(attr);
            }
        },
        handleClickItemDebit: function(attr) {
            if (attr.businessType == BUSINESS_TYPE.MINE) {
                this.$('.fieldset-mine .debitChannelName').text(attr.name);
                var btn = $('.fieldset-mine .debit-strategy-list .dropdown-toggle');
                if (btn.hasClass('error')) {
                    btn.removeClass('error');
                }
                this.model.set({ debitChannelUuid: attr.uuid });
            } else {
                this.$('.fieldset-agent .debitChannelName').text(attr.name);
                var btn = $('.fieldset-agent .debit-strategy-list .dropdown-toggle');
                if (btn.hasClass('error')) {
                    btn.removeClass('error');
                }
                this.model.set({ acdebitChannelUuid: attr.uuid });
            }
        },
        handleClickItemCredit: function(attr) {
            if (attr.businessType == BUSINESS_TYPE.MINE) {
                this.$('.fieldset-mine .creditChannelName').text(attr.name);
                var btn = $('.fieldset-mine .credit-strategy-list .dropdown-toggle');
                if (btn.hasClass('error')) {
                    btn.removeClass('error');
                }
                this.model.set({ creditChannelUuid: attr.uuid });
            } else {
                this.$('.fieldset-agent .creditChannelName').text(attr.name);
                var btn = $('.fieldset-agent .credit-strategy-list .dropdown-toggle');
                if (btn.hasClass('error')) {
                    btn.removeClass('error');
                }
                this.model.set({ accreditChannelUuid: attr.uuid });
            }
        },
        onChangeCreditStrategyMode: function(e) {
            var target = $(e.target);
            var creditStrategyMode = target.val();

            if (target.is('[name=accreditStrategyMode]')) {
                this.model.set({ accreditStrategyMode: creditStrategyMode });
                this.model.set({ accreditChannelUuid: '' });
                this.model.getCreditStrategy(BUSINESS_TYPE.AGENT);
            } else {
                this.model.set({ creditStrategyMode: creditStrategyMode });
                this.model.set({ creditChannelUuid: '' });
                this.model.getCreditStrategy(BUSINESS_TYPE.MINE);
            }
        },
        onChangeDebitStrategyMode: function(e) {
            var target = $(e.target);
            var debitStrategyMode = target.val();

            if (target.is('[name=acdebitStrategyMode]')) {
                this.model.set({ acdebitStrategyMode: debitStrategyMode });
                this.model.set({ acdebitChannelUuid: '' });
                this.model.getDebitStrategy(BUSINESS_TYPE.AGENT);
            } else {
                this.model.set({ debitStrategyMode: debitStrategyMode });
                this.model.set({ debitChannelUuid: '' });
                this.model.getDebitStrategy(BUSINESS_TYPE.MINE);
            }
        },
        onStrategySave: function(resp) {
            if (resp.code == 0) {
                location.href = root + '/paymentchannel/switch/list';
            } else {
                popupTip.show(resp.message);
            }
        },
        onStratgyCreditGet: function(list, resp, businessType) {
            if (resp.code != 0) {
                popupTip.show(resp.message);
                return;
            }

            var htm = this.creditStrategyItemTeplate({
                list: list,
                businessType: businessType,
                selectedPaymentChannelUuid: businessType == BUSINESS_TYPE.MINE ? this.model.get('creditChannelUuid') : this.model.get('accreditChannelUuid')
            });

            if (businessType == BUSINESS_TYPE.MINE) {
                var selected = this.$('.fieldset-mine .credit-strategy-list .dropdown-menu')
                    .html(htm)
                    .find('.selected')
                    .first();
                this.$('.fieldset-mine .creditChannelName').text(selected.text() || '请选择交易通道');
            } else {
                var selected = this.$('.fieldset-agent .credit-strategy-list .dropdown-menu')
                    .html(htm)
                    .find('.selected')
                    .first();
                this.$('.fieldset-agent .creditChannelName').text(selected.text() || '请选择交易通道');
            }
        },
        onStratgyDebitGet: function(list, resp, businessType) {
            if (resp.code != 0) {
                popupTip.show(resp.message);
                return;
            }

            var htm = this.debitStrategyItemTeplate({
                list: list,
                businessType: businessType,
                selectedPaymentChannelUuid: businessType == BUSINESS_TYPE.MINE ? this.model.get('debitChannelUuid') : this.model.get('acdebitChannelUuid')
            });

            if (businessType == BUSINESS_TYPE.MINE) {
                var selected = this.$('.fieldset-mine .debit-strategy-list .dropdown-menu')
                    .html(htm)
                    .find('.selected')
                    .first();
                this.$('.fieldset-mine .debitChannelName').text(selected.text() || '请选择交易通道');
            } else {
                var selected = this.$('.fieldset-agent .debit-strategy-list .dropdown-menu')
                    .html(htm)
                    .find('.selected')
                    .first();
                this.$('.fieldset-agent .debitChannelName').text(selected.text() || '请选择交易通道');
            }
        },
        validate: function() {
            var debitChannelValidateResult = true;
            this.$('.fieldset-mine .debitChannelName').toArray().forEach(function(el) {
                var $el = $(el);
                if ($el.text() === '') {
                    $el.parent('.dropdown-toggle').addClass('error');
                    debitChannelValidateResult = false;
                } else {
                    $el.parent('.dropdown-toggle').removeClass('error');
                }
            });

            var creditChannelValidateResult = true;
            this.$('.fieldset-mine .creditChannelName').toArray().forEach(function(el) {
                var $el = $(el);
                if ($el.text() === '') {
                    $el.parent('.dropdown-toggle').addClass('error');
                    creditChannelValidateResult = false;
                } else {
                    $el.parent('.dropdown-toggle').removeClass('error');
                }
            });

            return debitChannelValidateResult && creditChannelValidateResult;
        },
        submitHandler: function() {
            if (!this.validate()) return;
            this.model.save();
        }
    });

    module.exports = SwitchStrategyView;
});