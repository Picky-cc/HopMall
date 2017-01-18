define(function(require, exports, module) {
    var AnimationView = require('baseView/animationView');
    var FormDialogView = require('baseView/baseFormView').FormDialogView;
    var DialogView = require('component/dialogView');
    var AreaSelectView = require('component/areaSelect');
    var popupTip = require('component/popupTip');
    var BillingPlanCollection = require('entity/contract/leasingBillModel').BillingPlanCollection;

    var $ = jQuery;
    var loadingImg = global_const.loadingImg.clone();
    var render = function ($transition) {
        var obj = {};
        obj.transitionend = $transition.end;
        obj.support = !!obj.transitionend;
        obj.core = obj.support && obj.transitionend.match(/(\w*)transitionend/i)[1];
        if(obj.core) {
            obj.transform = obj.core + 'Transform';
            obj.transtion = obj.core + 'Transform';
        }else {
            obj.transform = 'transform';
            obj.transtion = obj.core + 'Transform';
        }
        obj.support = false; // 先禁用
        return obj;
    }($.support.transition);

    var SlideTabNav = AnimationView.extend({
        events: {
            'click .prev': 'onPrev',
            'click .next': 'onNext',
            'click .tab-menu-item': 'onItem',
            'click .all': 'onEntire'
        },
        initialize: function(options) {
            options = this.options = $.extend({}, SlideTabNav.DEFAULT_OPTIONS, options);

            // 上面要处理options
            SlideTabNav.__super__.initialize.call(this, options);

            var tabs = this.tabs = this.$('.tab-menu');
            var items = this.items = tabs.find('.tab-menu-item'); // 不包括all
            
            if(items.length<1) return;

            this.itemWidth = items[0].clientWidth + 1; // border-right;
            this.totalWidth = this.itemWidth * items.length;
            this.wrapperWidth = this.$('.inner').width();

            tabs.width(this.totalWidth);
            this.currentItem = items.filter(options.whichActive).addClass('z-active');

            this.offsetX = 0;
            this.isAnimating = false;

            var self = this;
            this.on('slide:end', function () {
                self.onAnimationEnd();
            });

            this.autoFocus();
        },
        autoFocus: function () {
            var left = this.currentItem.position().left;
            var wrapperWidth = this.wrapperWidth;
            var num = Math.floor(left/wrapperWidth);
            this.offsetX = -num * wrapperWidth;
            
            var el = this.getDrawElement();
            var props = {};
            if(render.support) {
                props[render.transform] = 'translateX('+this.offsetX+'px)';
            }else {
                props.left = this.offsetX;
            }

            $(el).css(props);
        },
        getDrawElement: function () {
            return this.tabs.get(0);
        },
        slidePerGroup: function (fromVal, toVal) {
            this.setDuration(300);
            var from = {};
            var to={};

            if(render.support) {
                from[render.transform] = 'translateX('+fromVal+'px)';
                to[render.transform] = 'translateX('+toVal+'px)';
            }else {
                from.left = fromVal;
                to.left = toVal;
            }

            this.runAnimation(from, to, 'slide');
        },
        onAnimationEnd: function () {
            this.isAnimating = false;
        },
        onPrev: function(e) {
            if(this.isAnimating) return;
            var f_offset = this.offsetX,
                wrapper = this.wrapperWidth;
            if(f_offset>=0) return;
            var t_offset = f_offset + wrapper;
            this.offsetX = t_offset;
            this.isAnimating = true;
            this.slidePerGroup(f_offset, t_offset);
        },
        onNext: function(e) {
            if(this.isAnimating) return;
            var total = this.totalWidth,
                f_offset = this.offsetX,
                wrapper = this.wrapperWidth;
            if(total <= Math.abs(f_offset) + wrapper) return;
            var t_offset = f_offset - wrapper;
            this.offsetX = t_offset;
            this.isAnimating = true;
            this.slidePerGroup(f_offset, t_offset);
        },
        onItem: function(e) {
            if($(e.currentTarget).hasClass('z-active')) return;
            this.currentItem.removeClass('z-active');
            this.currentItem = $(e.currentTarget).addClass('z-active');
            this.trigger('item', e.currentTarget);
        },
        onEntire: function(e) {
            this.currentItem.removeClass('z-active');
            this.currentItem = $(e.currentTarget).addClass('z-active');
            this.trigger('entire', e.currentTarget);
        },
        getCurrentItem: function () {
            return this.currentItem;
        }
    }, {
        DEFAULT_OPTIONS: {
            whichActive: ':first'
        }
    });

    var EditDiscountView = FormDialogView.extend({
        template: _.template($('#editDiscountTmpl').html()),
        initialize: function () {
            EditDiscountView.__super__.initialize.apply(this, arguments);

            this.defineValidator();
        },
        remove: function () {
            delete $.validator.methods.lowCarringAmount;
            EditDiscountView.__super__.remove.apply(this, arguments);
        },
        defineValidator: function () {
            var model =  this.model;
            $.validator.addMethod('lowCarringAmount', function (value, element) {
                return this.optional(element) || model.get('carringAmount') >= +value;
            }, '折扣金额不能大于账单应收金额');
            $.validator.addMethod('rightformatAmount', function (value, element) {
                return this.optional(element) || (/^([-]*([1-9]\d{0,5})|0)(\.\d{1,2})?$/.test(value));
            }, '请输入正确的金额格式');
            this.validator = this.$('.form').validate({
                rules: {
                    amount: {
                        rightformatAmount: true,
                        lowCarringAmount: true
                        
                    }
                },
                errorPlacement: function(error, element) {
                    var parent = element.parent();
                    if(parent.is('.parcel-input')) {
                        error.insertAfter(parent);
                    }else {
                        error.insertAfter(element);
                    }
                }
            });
        },
        validate: function () {
            return this.validator.form();
        },
        save: function () {
            var attr= this.extractDomData();
            this.model.modifyDiscount(attr);
        },
        submitHandler: function (e) {
            if(!this.validate()) return;
            this.save();
            this.hide();
        }
    });

    var AddInvoiceView = FormDialogView.extend({
        template:_.template($('#addinvoiceTmpl').html(),{ variable: 'obj' }),
        initialize: function () {
            EditDiscountView.__super__.initialize.apply(this, arguments);
            this.areaSelect = new AreaSelectView({
                el: this.$('.area-select')
            });
            this.defineValidator();
        },
        defineValidator: function() {
          this.validator = this.$('.form').validate({
            rules:{
                mobile:'mobile'
            }
          });
            
        },
        validate: function() {
            return this.validator.form();
        },
        extractDomData: function() {
            var attr = this._extractDomData(this.$('.real-value'));
            var appendix = {};
            appendix.taxNo = this.$("input[name$='taxNo']").val();
            appendix.taxAmount = this.$("input[name$='taxAmount']").val();
            attr['taxType'] = this.$('.invoice-type:checked').val();
            attr['appendix'] = appendix;
            return attr;
        },
        save: function () {
            var attr = this.extractDomData();

            var data = JSON.stringify(attr);

            var billUuid = this.model.get('billUuid'); 

            var opt = {
                url:"./taxinfo-update",
                type: 'POST',
                dataType: 'json',
                data: {
                    taxInvoice: data,
                    billUuid: billUuid
                }
            };

            opt.success = $.proxy(function(resp) {
                this.hide();
                if(resp.code == 0) {
                    this.model.set('taxInvoice', attr);
                }else {
                    popupTip.show(resp.message);
                }
            }, this);

            $.ajax(opt);
        },
        submitHandler: function (){
            if(!this.validate()) return;
            this.save();
        }
    });

    var BillingPlanItemView = Backbone.View.extend({
        tagName: 'tr',
        template: _.template($('#billingPlanItemTmpl').html()),
        events: {
            'click .modify': 'onModifyDiscount',
            'click a.discard': 'onDiscard',
            'click a.delete': 'onDelete',
            'click .check-box': 'onClickCheckbox',
            'click .add-invoice': 'onClickAddinvoice'
        },
        initialize: function () {
            var self = this;
            this.model.on('togglecheck:all', function (isCheck) {
                var checkbox = self.$('input[type=checkbox]').get(0);
                checkbox.checked = isCheck;
            }).on('remove', function () {
                self.remove();
            }).on('change', function () {
                self.render();
            }).on('display', function (value) {
                value ? self.$el.show() : self.$el.hide();
            });



            this.dialogView = new DialogView();
            this.dialogView.on('goahead', function (operation) {
                self.dialogView.hide();
                if(operation === 'delete') {
                    var collection =  self.model.collection;
                    self.model.remove(function(resp) {
                    	if(resp.code == 0 && collection.length < 1) {
                    		self.reLoadMonthStage();
                    	}
                    });
                }else if(operation === 'discard') {
                    self.model.discard();
                }
            });
        },
        reLoadMonthStage:function(){
             location.reload();
        },
        onClickCheckbox: function (e) {
            if($(e.target).is('.txt')) return;
            var checked = $(e.currentTarget).children('input').get(0).checked;
            this.model.execCheckByItem(checked);
        },
        onModifyDiscount: function (e) {
            e.preventDefault();
            var editView = new EditDiscountView({
                model: this.model
            });
            editView.show();
        },
        onClickAddinvoice:function (e) {
            e.preventDefault();
           
            var addInvoiceView = new AddInvoiceView({
                model: this.model
            });

            addInvoiceView.show();
        },
        onDiscard: function (e) {
            e.preventDefault();
            var isPayOff = this.model.isPayOff();
            if(!isPayOff) {
                this.dialogView.show('该账单未付清，一旦关闭将不能恢复，是否继续？', 'discard');
            }else {
                this.dialogView.show('账单一旦关闭将不能恢复，是否继续？', 'discard');
            }
        },
        onDelete: function (e) {
            e.preventDefault();
            var isPayOff = this.model.isPayOff(); // 判断条件？？
            if(!isPayOff) {
                this.dialogView.show('该账单未付清，一旦删除将不能恢复，是否继续？', 'delete');
            }else {
                this.dialogView.show('账单一旦删除将不能恢复，是否继续？', 'delete');
            }
        },
        render: function () {
            var model = this.model;
            var data = model.toJSON();
            data.index = model.get('_index');
            var htm = this.template(data);
            this.$el.html(htm);
            return this;
        }
    });

    $(document).on('mouseenter', '.filter-box', function (e) {
        var curTar = $(e.currentTarget);
        curTar.addClass('on');
    }).on('mouseleave', '.filter-box, .dropmenu', function (e) {
        var curTar = $(e.currentTarget);
        if(curTar.is('.dropmenu')) {
            if($(e.relatedTarget).is('.filter-box')) return;
            curTar = curTar.parent();
        }
        curTar.removeClass('on');
    });

    var BillListView = Backbone.View.extend({
        el: '.leasing-bill-wrapper',
        events: {
            'click .check-all': 'onToggleCheckAll',
            'click #discardAll': 'onDiscardAll',
            'click #deleteAll': 'onDeleteAll',
            'click .filter-item': 'onClickBillStatusItem'
        },
        initialize: function(options) {
            this.options = options;
            this.creditListEl = this.$('#creditBill tbody');
            this.debitListEl = this.$('#debitBill tbody');
            this.initTabNav();
            this.initCollection();

            var billCollection = this.billCollection;
            var self = this;
            this.dialogView = new DialogView();
            this.dialogView.on('goahead', function (checks, operation) {
                self.dialogView.hide();
                if(operation === 'delete') {
                    billCollection.deletes(checks);
                }else if(operation === 'discard') {
                    billCollection.discards(checks);
                }
            });
        },
        initCollection: function () {
            this.billCollection = new BillingPlanCollection();
            var self = this,
                checkboxAll = self.$('.check-all input').get(0);
            this.billCollection.on('sync', function (collection, resp) {
                self.render();
            }).on('request', function (entity) {
                if(entity instanceof BillingPlanCollection) {
                    self.wating();
                }
            }).on('togglecheck:item', function (isCheck) {
                if(!isCheck) {
                    checkboxAll.checked = false;
                }
            });
            var param = this.extractParams(this.tabNav.getCurrentItem());
            this.billCollection.fetch({ data: param });
        },
        initTabNav: function () {
            var self = this;
            this.tabNav = new SlideTabNav({
                el: this.$('.tab-nav'),
                whichActive: '.z-active'
            });
            this.tabNav.on('entire', function(el) {
                self.refresh(self.extractParams(el));
            }).on('item', function(el) {
                self.refresh(self.extractParams(el));
            });
        },
        onClickBillStatusItem: function (e) {
            e.preventDefault();
            var tar = $(e.target);
            var items = tar.parent().children();
            var result, condition;
            if(tar.hasClass('z-active')) {
                tar.removeClass('z-active');
            }else {
                items.removeClass('z-active');
                tar.addClass('z-active');
                condition = $(e.target).data('value');
            }

            var type = this.getOperatingBillType(e.target);

            this.billCollection.each(function (model) {
                if(model.get('_uiType') === type) {
                    if(typeof condition === 'undefined') {
                        model.execDisplay(true);
                        return;
                    }
                    if(condition[0]==='~') {
                        if(model.get('billStatus') != condition.slice(1)) {
                            model.execDisplay(true);
                        }else {
                            model.execDisplay(false);
                        }
                    }else {
                        if(model.get('billStatus') === condition) {
                            model.execDisplay(true);
                        }else {
                            model.execDisplay(false);
                        }
                    }
                }
            });

        },
        onDiscardAll: function (e) {
            var collection = this.billCollection;
            var type = this.getOperatingBillType(e.target);
            var checks = collection.filterCheck(type);
            if(checks.length<1) {
                popupTip.show('请先选中将要关闭的账单');
                return;
            }
            var isAllPayOff = collection.isAllPayOff(checks);
            if(!isAllPayOff) {
                this.dialogView.show('有账单未付清，一旦关闭将不能恢复，是否继续？', checks, 'discard');
            }else {
                this.dialogView.show('账单一旦关闭将不能恢复，是否继续？', checks, 'discard');
            }
        },
        onDeleteAll: function (e) {
            var collection = this.billCollection;
            var type = this.getOperatingBillType(e.target);
            var checks = collection.filterCheck(type);
            if(checks.length<1) {
                popupTip.show('请先选中将要删除的账单');
                return;
            }
            var isAllPayOff = collection.isAllPayOff(checks);
            if(!isAllPayOff) {
                this.dialogView.show('有账单未付清<br>一旦删除将不能恢复，是否继续？', checks, 'delete');
            }else {
                this.dialogView.show('一旦删除将不能恢复，是否继续？', checks, 'delete');
            }
        },
        onToggleCheckAll: function (e) {
            if($(e.target).is('.txt')) return;
            var checked = $(e.currentTarget).children('input').get(0).checked;

            var type = this.getOperatingBillType(e.target);

            this.billCollection.toggleCheck(checked, type);
        },
        getOperatingBillType: function(target) {
            var isCreditBill = $(target).parents('#creditBill').length === 1;
            return isCreditBill ? 'credit':'debit'
        },
        extractParams: function (el) {
            var obj = {
                businessContractUuid: this.options.search.businessContractUuid
            };
            var $el = $(el);
            if(!$el.hasClass('all')) {
                obj.stage = $el.find('.sup').text();
            }
            return obj;
        },
        refresh: function (params) {
            var collection = this.billCollection;
            while(collection.length) {
                collection.last().destroy();
            }
            this.$('.check-all input').get(0).checked = false;
            // this.$('.filter-box .dropmenu .filter-item').removeClass('z-active');
            this.billCollection.fetch({
                data: params,
                reset: true
            });
        },
        wating: function () {
            var td = $('<td colspan="11" style="text-align: center;">');
            td.append(loadingImg);
            this.creditListEl.html(td);
            this.debitListEl.html(td.clone());
        },
        render: function () {
            var collection = this.billCollection;

            var creditListEl = this.creditListEl.html('');
            var debitListEl = this.debitListEl.html('');

            for(var i = 0, len = collection.length; i<len; i++) {
                var model = collection.at(i);
                var itemView  = new BillingPlanItemView({
                    model: model
                });
                if(model.get('_uiType') === 'credit') {
                    creditListEl.append(itemView.render().$el);
                }else if(model.get('_uiType') === 'debit') {
                    debitListEl.append(itemView.render().$el);
                }
            }
        }
    });


    exports.BillListView = BillListView;

});