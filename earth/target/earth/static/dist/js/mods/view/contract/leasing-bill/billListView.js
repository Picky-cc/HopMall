define(function(require,exports,module){var t=require("baseView/animationView"),e=require("baseView/baseFormView").FormDialogView,i=require("component/dialogView"),n=require("component/areaSelect"),a=require("component/popupTip"),o=require("entity/contract/leasingBillModel").BillingPlanCollection,r=jQuery,l=global_const.loadingImg.clone(),s=function(t){var e={};return e.transitionend=t.end,e.support=!!e.transitionend,e.core=e.support&&e.transitionend.match(/(\w*)transitionend/i)[1],e.core?(e.transform=e.core+"Transform",e.transtion=e.core+"Transform"):(e.transform="transform",e.transtion=e.core+"Transform"),e.support=!1,e}(r.support.transition),c=t.extend({events:{"click .prev":"onPrev","click .next":"onNext","click .tab-menu-item":"onItem","click .all":"onEntire"},initialize:function(t){t=this.options=r.extend({},c.DEFAULT_OPTIONS,t),c.__super__.initialize.call(this,t);var e=this.tabs=this.$(".tab-menu"),i=this.items=e.find(".tab-menu-item");if(!(i.length<1)){this.itemWidth=i[0].clientWidth+1,this.totalWidth=this.itemWidth*i.length,this.wrapperWidth=this.$(".inner").width(),e.width(this.totalWidth),this.currentItem=i.filter(t.whichActive).addClass("z-active"),this.offsetX=0,this.isAnimating=!1;var n=this;this.on("slide:end",function(){n.onAnimationEnd()}),this.autoFocus()}},autoFocus:function(){var t=this.currentItem.position().left,e=this.wrapperWidth,i=Math.floor(t/e);this.offsetX=-i*e;var n=this.getDrawElement(),a={};s.support?a[s.transform]="translateX("+this.offsetX+"px)":a.left=this.offsetX,r(n).css(a)},getDrawElement:function(){return this.tabs.get(0)},slidePerGroup:function(t,e){this.setDuration(300);var i={},n={};s.support?(i[s.transform]="translateX("+t+"px)",n[s.transform]="translateX("+e+"px)"):(i.left=t,n.left=e),this.runAnimation(i,n,"slide")},onAnimationEnd:function(){this.isAnimating=!1},onPrev:function(t){if(!this.isAnimating){var e=this.offsetX,i=this.wrapperWidth;if(!(e>=0)){var n=e+i;this.offsetX=n,this.isAnimating=!0,this.slidePerGroup(e,n)}}},onNext:function(t){if(!this.isAnimating){var e=this.totalWidth,i=this.offsetX,n=this.wrapperWidth;if(!(e<=Math.abs(i)+n)){var a=i-n;this.offsetX=a,this.isAnimating=!0,this.slidePerGroup(i,a)}}},onItem:function(t){r(t.currentTarget).hasClass("z-active")||(this.currentItem.removeClass("z-active"),this.currentItem=r(t.currentTarget).addClass("z-active"),this.trigger("item",t.currentTarget))},onEntire:function(t){this.currentItem.removeClass("z-active"),this.currentItem=r(t.currentTarget).addClass("z-active"),this.trigger("entire",t.currentTarget)},getCurrentItem:function(){return this.currentItem}},{DEFAULT_OPTIONS:{whichActive:":first"}}),h=e.extend({template:_.template(r("#editDiscountTmpl").html()),initialize:function(){h.__super__.initialize.apply(this,arguments),this.defineValidator()},remove:function(){delete r.validator.methods.lowCarringAmount,h.__super__.remove.apply(this,arguments)},defineValidator:function(){var t=this.model;r.validator.addMethod("lowCarringAmount",function(e,i){return this.optional(i)||t.get("carringAmount")>=+e},"折扣金额不能大于账单应收金额"),r.validator.addMethod("rightformatAmount",function(t,e){return this.optional(e)||/^([-]*([1-9]\d{0,5})|0)(\.\d{1,2})?$/.test(t)},"请输入正确的金额格式"),this.validator=this.$(".form").validate({rules:{amount:{rightformatAmount:!0,lowCarringAmount:!0}},errorPlacement:function(t,e){var i=e.parent();i.is(".parcel-input")?t.insertAfter(i):t.insertAfter(e)}})},validate:function(){return this.validator.form()},save:function(){var t=this.extractDomData();this.model.modifyDiscount(t)},submitHandler:function(t){this.validate()&&(this.save(),this.hide())}}),d=e.extend({template:_.template(r("#addinvoiceTmpl").html(),{variable:"obj"}),initialize:function(){h.__super__.initialize.apply(this,arguments),this.areaSelect=new n({el:this.$(".area-select")}),this.defineValidator()},defineValidator:function(){this.validator=this.$(".form").validate({rules:{mobile:"mobile"}})},validate:function(){return this.validator.form()},extractDomData:function(){var t=this._extractDomData(this.$(".real-value")),e={};return e.taxNo=this.$("input[name$='taxNo']").val(),e.taxAmount=this.$("input[name$='taxAmount']").val(),t.taxType=this.$(".invoice-type:checked").val(),t.appendix=e,t},save:function(){var t=this.extractDomData(),e=JSON.stringify(t),i=this.model.get("billUuid"),n={url:"./taxinfo-update",type:"POST",dataType:"json",data:{taxInvoice:e,billUuid:i}};n.success=r.proxy(function(e){this.hide(),0==e.code?this.model.set("taxInvoice",t):a.show(e.message)},this),r.ajax(n)},submitHandler:function(){this.validate()&&this.save()}}),u=Backbone.View.extend({tagName:"tr",template:_.template(r("#billingPlanItemTmpl").html()),events:{"click .modify":"onModifyDiscount","click a.discard":"onDiscard","click a.delete":"onDelete","click .check-box":"onClickCheckbox","click .add-invoice":"onClickAddinvoice"},initialize:function(){var t=this;this.model.on("togglecheck:all",function(e){var i=t.$("input[type=checkbox]").get(0);i.checked=e}).on("remove",function(){t.remove()}).on("change",function(){t.render()}).on("display",function(e){e?t.$el.show():t.$el.hide()}),this.dialogView=new i,this.dialogView.on("goahead",function(e){if(t.dialogView.hide(),"delete"===e){var i=t.model.collection;t.model.remove(function(e){0==e.code&&i.length<1&&t.reLoadMonthStage()})}else"discard"===e&&t.model.discard()})},reLoadMonthStage:function(){location.reload()},onClickCheckbox:function(t){if(!r(t.target).is(".txt")){var e=r(t.currentTarget).children("input").get(0).checked;this.model.execCheckByItem(e)}},onModifyDiscount:function(t){t.preventDefault();var e=new h({model:this.model});e.show()},onClickAddinvoice:function(t){t.preventDefault();var e=new d({model:this.model});e.show()},onDiscard:function(t){t.preventDefault();var e=this.model.isPayOff();e?this.dialogView.show("账单一旦关闭将不能恢复，是否继续？","discard"):this.dialogView.show("该账单未付清，一旦关闭将不能恢复，是否继续？","discard")},onDelete:function(t){t.preventDefault();var e=this.model.isPayOff();e?this.dialogView.show("账单一旦删除将不能恢复，是否继续？","delete"):this.dialogView.show("该账单未付清，一旦删除将不能恢复，是否继续？","delete")},render:function(){var t=this.model,e=t.toJSON();e.index=t.get("_index");var i=this.template(e);return this.$el.html(i),this}});r(document).on("mouseenter",".filter-box",function(t){var e=r(t.currentTarget);e.addClass("on")}).on("mouseleave",".filter-box, .dropmenu",function(t){var e=r(t.currentTarget);if(e.is(".dropmenu")){if(r(t.relatedTarget).is(".filter-box"))return;e=e.parent()}e.removeClass("on")});var f=Backbone.View.extend({el:".leasing-bill-wrapper",events:{"click .check-all":"onToggleCheckAll","click #discardAll":"onDiscardAll","click #deleteAll":"onDeleteAll","click .filter-item":"onClickBillStatusItem"},initialize:function(t){this.options=t,this.creditListEl=this.$("#creditBill tbody"),this.debitListEl=this.$("#debitBill tbody"),this.initTabNav(),this.initCollection();var e=this.billCollection,n=this;this.dialogView=new i,this.dialogView.on("goahead",function(t,i){n.dialogView.hide(),"delete"===i?e.deletes(t):"discard"===i&&e.discards(t)})},initCollection:function(){this.billCollection=new o;var t=this,e=t.$(".check-all input").get(0);this.billCollection.on("sync",function(e,i){t.render()}).on("request",function(e){e instanceof o&&t.wating()}).on("togglecheck:item",function(t){t||(e.checked=!1)});var i=this.extractParams(this.tabNav.getCurrentItem());this.billCollection.fetch({data:i})},initTabNav:function(){var t=this;this.tabNav=new c({el:this.$(".tab-nav"),whichActive:".z-active"}),this.tabNav.on("entire",function(e){t.refresh(t.extractParams(e))}).on("item",function(e){t.refresh(t.extractParams(e))})},onClickBillStatusItem:function(t){t.preventDefault();var e,i=r(t.target),n=i.parent().children();i.hasClass("z-active")?i.removeClass("z-active"):(n.removeClass("z-active"),i.addClass("z-active"),e=r(t.target).data("value"));var a=this.getOperatingBillType(t.target);this.billCollection.each(function(t){if(t.get("_uiType")===a){if("undefined"==typeof e)return void t.execDisplay(!0);"~"===e[0]?t.get("billStatus")!=e.slice(1)?t.execDisplay(!0):t.execDisplay(!1):t.get("billStatus")===e?t.execDisplay(!0):t.execDisplay(!1)}})},onDiscardAll:function(t){var e=this.billCollection,i=this.getOperatingBillType(t.target),n=e.filterCheck(i);if(n.length<1)return void a.show("请先选中将要关闭的账单");var o=e.isAllPayOff(n);o?this.dialogView.show("账单一旦关闭将不能恢复，是否继续？",n,"discard"):this.dialogView.show("有账单未付清，一旦关闭将不能恢复，是否继续？",n,"discard")},onDeleteAll:function(t){var e=this.billCollection,i=this.getOperatingBillType(t.target),n=e.filterCheck(i);if(n.length<1)return void a.show("请先选中将要删除的账单");var o=e.isAllPayOff(n);o?this.dialogView.show("一旦删除将不能恢复，是否继续？",n,"delete"):this.dialogView.show("有账单未付清<br>一旦删除将不能恢复，是否继续？",n,"delete")},onToggleCheckAll:function(t){if(!r(t.target).is(".txt")){var e=r(t.currentTarget).children("input").get(0).checked,i=this.getOperatingBillType(t.target);this.billCollection.toggleCheck(e,i)}},getOperatingBillType:function(t){var e=1===r(t).parents("#creditBill").length;return e?"credit":"debit"},extractParams:function(t){var e={businessContractUuid:this.options.search.businessContractUuid},i=r(t);return i.hasClass("all")||(e.stage=i.find(".sup").text()),e},refresh:function(t){for(var e=this.billCollection;e.length;)e.last().destroy();this.$(".check-all input").get(0).checked=!1,this.billCollection.fetch({data:t,reset:!0})},wating:function(){var t=r('<td colspan="11" style="text-align: center;">');t.append(l),this.creditListEl.html(t),this.debitListEl.html(t.clone())},render:function(){for(var t=this.billCollection,e=this.creditListEl.html(""),i=this.debitListEl.html(""),n=0,a=t.length;a>n;n++){var o=t.at(n),r=new u({model:o});"credit"===o.get("_uiType")?e.append(r.render().$el):"debit"===o.get("_uiType")&&i.append(r.render().$el)}}});exports.BillListView=f});