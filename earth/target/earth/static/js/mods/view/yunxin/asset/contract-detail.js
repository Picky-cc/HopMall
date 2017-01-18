define(function(require, exports, module) {
    var FormDialogView = require('baseView/baseFormView').FormDialogView;
    var popupTip = require('component/popupTip');
    var AreaSelectView = require('component/areaSelect');

    var root = global_config.root;
    var loading = global_const.loadingImg;
    var $ = jQuery;

    var EditCustomerInfoVierw = FormDialogView.extend({
        template: _.template($('#editCustomerInfoTmpl').html(), {variable: 'obj'}),
        actions: {
            submit: root + '/modifyContractAccount/repaymentInfo/modify'
        },
        initialize: function() {
            EditCustomerInfoVierw.__super__.initialize.call(this, arguments);
            this.defineValidator();
            this.areaSelect = new AreaSelectView({el: this.$('.area-select')});
            this.$('.selectpicker').selectpicker('render');
        },
        defineValidator: function() {
            this.validator = this.$('.form').validate({
            	rules: {
            		bankAccount: 'required',
            		cityCode: 'required',
            		provinceCode: 'required'
            	}
            });
        },
        validate: function() {
        	var flag = true;
        	if(this.$('.filter-option').html()==='请选择'){
        		this.$('.dropdown-toggle').addClass('error');
        		flag = false;
        	}else{
        		flag = true;
        		this.$('.dropdown-toggle').removeClass('error');
        	}
            return this.validator.form() && flag;
        },
        save: function() {
            var self = this;
            
            var contractId = $('[name=contractId]').val().trim();
            if (!contractId) return;
            
            var attr = this.extractDomData();
            attr.contractId = contractId;
            attr.bankCode = $('[name=bankCode]').val();
            
            var opt = {
                url: this.actions.submit,
                type: 'post',
                dataType: 'json',
                data: attr
            };

            opt.success = function(resp) {
                self.model.trigger('model:edit-customer-info', resp, attr);
            };

            $.ajax(opt);
        },
        submitHandler: function() {
            if (!this.validate()) return;
            this.save();
            this.hide();
        }
    });

    var TerminateContractView = FormDialogView.extend({
        template: _.template($('#terminateContractTmpl').html(), { variable: 'obj' }),
        actions: {
            submit: root + '/contracts/invalidate'
        },
        initialize: function() {
            TerminateContractView.__super__.initialize.call(this, arguments);
            this.defineValidator();
        },
        defineValidator: function() {
            this.validator = this.$('.form').validate({
                rules: {
                    comment: {
                        required: true,
                        maxlength: 50
                    }
                }
            });
        },
        validate: function() {
            return this.validator.form();
        },
        save: function() {
            var self = this;
            var attr = this.extractDomData();
            attr.contractId = this.model.get('contractId');
            var opt = {
                url: this.actions.submit,
                type: 'post',
                dataType: 'json',
                data: attr
            };

            opt.success = function(resp) {
                self.model.trigger('model:terminate-contract', resp, attr);
            };

            $.ajax(opt);
        },
        submitHandler: function() {
            if (!this.validate()) return;
            this.save();
            this.hide();
        }
    });

    var mixins = {
        initialize: function(options) {
            this.$tabMenuItem = options.$tabMenuItem;
            this.mInitialize && this.mInitialize(options);
        },
        hide: function() {
            this.$el.hide();
            this.$tabMenuItem.removeClass('active');
        },
        show: function() {
            this.$el.show();
            this.$tabMenuItem.addClass('active');
        }
    };

    var ReleaseItemView = Backbone.View.extend({
        events: {
            'click .expand': 'onClickExpand'
        },
        orderDetailTemplate: _.template($('#orderDetailTmpl').html() || ''),
        initialize: function() {
            this.$loading = $('<tr><td colspan="8" align="center"></td></tr>').find('td').append(loading.clone());
            this.expanded = false;

            this.on('request:orderdetail', function() {
                this.$el.after(this.$loading);
            });

            this.on('response:orderdetail', function(resp) {
                this.$loading.remove();
                if (resp.code == 0) {
                    var htm = this.orderDetailTemplate(resp.data);
                    this.$el.after(htm);
                } else {
                    popupTip.show(resp.message);
                }
            });
           
        },
        onClickExpand: function(e) {
            e.preventDefault();
            this.toggleOrderDetail();
        },
        toggleOrderDetail: function() {
            if (this.expanded) {
                this.collapseOrderDetail();
            } else {
                this.expandOrderDetail();
            }
        },
        collapseOrderDetail: function() {
            this.expanded = false;
            this.$el.removeClass('active');

            var $next = this.$el.next('.bill-card-box');
            $next.remove();
        },
      
        expandOrderDetail: function() {
            this.expanded = true;
            this.$el.addClass('active');
            var data = {
                remittanceApplicationUuid: this.$el.data('remittance-application-uuid')
            };

            var obj = {
                url: './detail/planlist',
                type: 'get',
                dataType: 'json',
                data: data
            };

            obj.success = function(resp) {
                this.trigger('response:orderdetail', resp);
            }.bind(this);

            this.trigger('request:orderdetail');

            $.ajax(obj);

            // setTimeout(function() {
            //     obj.success({ code: 0 });
            // }, 1000);
        }
    });

    var ReleaseTabContentView = Backbone.View.extend($.extend({}, mixins, {
        mInitialize: function(options) {
            this.$('.item-release').toArray().forEach(function(el, index) {
                var view = new ReleaseItemView({el: el});
                if (index == 0) {
                    view.expandOrderDetail();
                }
            });
        }
    }));

    var RepayTabContentView = Backbone.View.extend($.extend({}, mixins, {

    }));

    var ContractDetailView = Backbone.View.extend({
        el: '.content',
        events: {
            'click #terminateContract': 'onClickTerminateContract',
            'click .edit-customer-info': 'onClickEditCustomerInfo',
            'click .tab-menu-item': 'onClickTabMenuItem',
            'mouseover .showPopover': 'showPopover',
            'click .icon-bankcard': 'onClickBankcard',
            'mouseout .showPopover': 'showPopover'
        },
        initialize: function(attr) {
            this.initModel(attr);
            this.initTabContentView();
            $(window).on('click', function() {
                if ($('body').find('.popover').last().hasClass('clickShow')) {
                    $('body').find('.popover').popover('hide');
                }
            });
        },
        initModel: function(attr) {
            this.model = new Backbone.Model(attr);
            var data  = {};
            data.contractNo = this.$('.contractNo').text().trim();
            data.amount = this.$('.amount').text().trim();
            data.customer = this.$('.customer').text().trim();
            data.idCardNum = this.$('.idcard-num').text().trim();
            data.customerSource = this.$('.customer-source').text().trim();
            data.bank = this.$('.bank').text().trim();
            this.model.set(data);
        },
        showPopover: function(e) {
            e.preventDefault();
            var el = $(e.target);
            var popoverContent = el.siblings('.account').html();
            el.popover({
                content: popoverContent
            });
            el.popover('toggle');
        },
        onClickBankcard: function(e) {
            e.preventDefault();
            this.showPopover(e);
            var el = $(e.target);
            el.on('shown.bs.popover', function() {
                $('body').find('.popover').last().addClass('clickShow');
            });
            el.on('hidden.bs.popover', function() {
                el.removeClass('active');
            });
            el.toggleClass('active');
            return false;
        },
        initTabContentView: function() {
            var obj = this.tabContentViews = {};

            obj.release = new ReleaseTabContentView({
                el: this.$('.tab-content-item-release').get(0),
                $tabMenuItem: this.$('.tab-menu-item-release')
            });

            obj.repay = new RepayTabContentView({
                el: this.$('.tab-content-item-repay').get(0),
                $tabMenuItem: this.$('.tab-menu-item-repay')
            });

            obj.release.hide();
            obj.repay.hide();

            this.activeTabContentView = obj.repay;
            this.activeTabContentView.show();

            this.$('.tabs').show();
        },
        handleAfterPopupHide: function(resp) {
            if (resp.code == 0) {
                popupTip.show('操作成功');

                setTimeout(function() {
                    location.reload();
                }, 1000);
            } else {
                popupTip.show(resp.message || '操作失败，请重试');
            }
        },
        onClickEditCustomerInfo: function(e) {
            e.preventDefault();

            var view = new EditCustomerInfoVierw({model: this.model});

            this.model.once('model:edit-customer-info', this.handleAfterPopupHide);

            view.show();
        },
        onClickTerminateContract: function() {
            var view = new TerminateContractView({ model: this.model });

            this.model.once('model:terminate-contract', this.handleAfterPopupHide);

            view.show();
        },
        onClickTabMenuItem: function(e) {
            e.preventDefault();

            var $target = $(e.currentTarget);
            var tabContentKey = $target.data('target');
            var nextTabContentView = this.tabContentViews[tabContentKey];

            this.activeTabContentView.hide();
            nextTabContentView.show();

            this.activeTabContentView = nextTabContentView;
        }
    });

    module.exports = ContractDetailView;

});