define(function(require, exports, module) {   
    var AnimationView = require('baseView/animationView');
    var BillingPlanModel = require('entity/finance/billingPlanModel');
    var Backdrop = require('component/backdrop');
    var util = require('scaffold/util');

    var $ = jQuery;
    var $body = $('body');
    var loadingImg = global_const.loadingImg;
    var helpTip = window.helpTip;

    var SidebarBillSelectView = AnimationView.extend({
        events: {
            'click .back': 'onClickBack',
            'click .remove': 'onClickRemove',
            'click .account': 'onClickAccount',
            'click .lease': 'onClickLease',
            'keyup [name=keyWord]': 'onKeyupKeyWord',
            'click .create': 'onClickCreate'
        },
        tagName: 'div',
        id: 'sidebarBillSelect',
        className: 'sidebar-bill-select',
        template: _.template($('#sidebarBillSelectTmpl').html() || ''),
        initialize: function(options) {
            SidebarBillSelectView.__super__.initialize.apply(this, arguments);

            this.maskEl = $('<div class="mask">').append(loadingImg.clone());

            this.initBackdrop();
            this.initModel(options);     

            this.on('slideOut:end', function() {
                this.trigger('sidebar:remove');
                this.remove();
            });

            $(document).on('keydown.sidebar', _.bind(function(e) {
                if(e.keyCode == 27) {
                    this.close();    
                }
            }, this));
        },
        remove: function() {
            SidebarBillSelectView.__super__.remove.apply(this, arguments);
            $(document).off('keydown.sidebar');
        },
        show: function () {
            this.backdrop.open();
            return this;
        },
        close: function() {
            helpTip.hide();
            this.backdrop.hide();
            return this;
        },
        initBackdrop: function() {
            this.backdrop = new Backdrop();
            this.listenTo(this.backdrop, 'close:backdrop', function() {
                this.slideOut('right', 200);
            });
            this.listenTo(this.backdrop, 'open:backdrop', function() {
                $body.append(this.$el);
            });
        },
        initModel: function (options) {
            this.model = new BillingPlanModel(options);

            this.listenTo(this.model, 'accountlist', _.bind(this.renderAccountList, this));
            this.listenTo(this.model, 'leaselist', _.bind(this.renderLeaseList, this));
            this.listenTo(this.model, 'request', _.bind(this.waiting, this));
            this.listenTo(this.model, 'error', function(msg){ console.log(msg); });            

            this.model.getLeaseList();
        },
        render: function (data) {
            var finallyData = this.model.toJSON();
            $.extend(finallyData, data);

            var htm = this.template(finallyData);
            this.$el.html(htm);

            var el = this.$('[name=keyWord]').get(0);
            el && util.selectText(el, 0);

            return this;
        },
        renderAccountList: function (list) {
            var josnData = {
                list: list,
                type: 'account-list'
            };
            this.viewType = 'account-list';
            this.render(josnData);
        },
        renderLeaseList: function (list, options) {
            if(options && options.isSearch) {
                var htm;

                if(list.length > 0) {
                    var temp = list.map(function(item) {
                        return '<li class="item">' + 
                                    '<a class="lease" data-title="'+item.sourcePropertyNo+ '-' + item.address + '" data-sourcepropertyno="'+item.sourcePropertyNo+'" data-businesscontractuuid="'+item.businessContractUuid+'">'+ _.compact([item.sourceContractNo, item.contractName]).join('-') +'</a>' +
                                '</li>';
                    });

                    temp.unshift('<ul class="list">');
                    temp.push('</ul>');

                    htm = temp.join('');
                }else {
                    htm = '<p style="text-align: center">没有数据</p>';
                }
                
                this.maskEl.remove();
                this.$('#results').html(htm);
            }else {
                var josnData = {
                    list: list,
                    type: 'lease-list'
                };
                this.viewType = 'lease-list';
                this.render(josnData);
            }
        },
        waiting: function () {
            helpTip.hide();
            this.$el.append(this.maskEl);
        },

        onClickLease:function (e) {
            var target = $(e.target);
            var businesscontractuuid = target.data('businesscontractuuid');
            var sourcepropertyno = target.data('sourcepropertyno');
            this.model.set({
                businessContractUuid: businesscontractuuid,
                sourcePropertyNo: sourcepropertyno
            });
            this.model.getAccountList();
        },
        onClickAccount: function (e) {
            var id = $(e.target).data('billingplanuuid');
            this.trigger('sidebar:selecedbill', id);
            this.close();
        },
        onClickBack: function (e) {
            if(this.viewType === 'account-list') {
                this.model.getLeaseList({isBack: true});
            }
        },
        onClickRemove: function (e) {
            e.preventDefault();
            this.close();
        },
        onKeyupKeyWord: function(e) {
            var self = this;
            var model = this.model;
            var value = e.target.value.trim();

            if(!value) return; 

            if(this.timer) {
                clearTimeout(this.timer);
                this.timer = null;
            }

            this.timer = setTimeout(function() {
                model.set('keyWord', value);
                model.getLeaseList({isSearch: true});
            }, 200);
        },
        onClickCreate: function(e) {
            e.preventDefault();
            var attr = {
                subjectMatterSourceNo: this.model.get('sourcePropertyNo')
            };
            this.trigger('sidebar:createbill', attr);
            this.close();
        }
    });

    module.exports = SidebarBillSelectView;

});