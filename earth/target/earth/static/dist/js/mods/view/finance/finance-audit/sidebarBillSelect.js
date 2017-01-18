define(function(require,exports,module){var e=require("baseView/animationView"),t=require("entity/finance/billingPlanModel"),i=require("component/backdrop"),s=require("scaffold/util"),n=jQuery,o=n("body"),c=global_const.loadingImg,l=window.helpTip,a=e.extend({events:{"click .back":"onClickBack","click .remove":"onClickRemove","click .account":"onClickAccount","click .lease":"onClickLease","keyup [name=keyWord]":"onKeyupKeyWord","click .create":"onClickCreate"},tagName:"div",id:"sidebarBillSelect",className:"sidebar-bill-select",template:_.template(n("#sidebarBillSelectTmpl").html()||""),initialize:function(e){a.__super__.initialize.apply(this,arguments),this.maskEl=n('<div class="mask">').append(c.clone()),this.initBackdrop(),this.initModel(e),this.on("slideOut:end",function(){this.trigger("sidebar:remove"),this.remove()}),n(document).on("keydown.sidebar",_.bind(function(e){27==e.keyCode&&this.close()},this))},remove:function(){a.__super__.remove.apply(this,arguments),n(document).off("keydown.sidebar")},show:function(){return this.backdrop.open(),this},close:function(){return l.hide(),this.backdrop.hide(),this},initBackdrop:function(){this.backdrop=new i,this.listenTo(this.backdrop,"close:backdrop",function(){this.slideOut("right",200)}),this.listenTo(this.backdrop,"open:backdrop",function(){o.append(this.$el)})},initModel:function(e){this.model=new t(e),this.listenTo(this.model,"accountlist",_.bind(this.renderAccountList,this)),this.listenTo(this.model,"leaselist",_.bind(this.renderLeaseList,this)),this.listenTo(this.model,"request",_.bind(this.waiting,this)),this.listenTo(this.model,"error",function(e){console.log(e)}),this.model.getLeaseList()},render:function(e){var t=this.model.toJSON();n.extend(t,e);var i=this.template(t);this.$el.html(i);var o=this.$("[name=keyWord]").get(0);return o&&s.selectText(o,0),this},renderAccountList:function(e){var t={list:e,type:"account-list"};this.viewType="account-list",this.render(t)},renderLeaseList:function(e,t){if(t&&t.isSearch){var i;if(e.length>0){var s=e.map(function(e){return'<li class="item"><a class="lease" data-title="'+e.sourcePropertyNo+"-"+e.address+'" data-sourcepropertyno="'+e.sourcePropertyNo+'" data-businesscontractuuid="'+e.businessContractUuid+'">'+_.compact([e.sourceContractNo,e.contractName]).join("-")+"</a></li>"});s.unshift('<ul class="list">'),s.push("</ul>"),i=s.join("")}else i='<p style="text-align: center">没有数据</p>';this.maskEl.remove(),this.$("#results").html(i)}else{var n={list:e,type:"lease-list"};this.viewType="lease-list",this.render(n)}},waiting:function(){l.hide(),this.$el.append(this.maskEl)},onClickLease:function(e){var t=n(e.target),i=t.data("businesscontractuuid"),s=t.data("sourcepropertyno");this.model.set({businessContractUuid:i,sourcePropertyNo:s}),this.model.getAccountList()},onClickAccount:function(e){var t=n(e.target).data("billingplanuuid");this.trigger("sidebar:selecedbill",t),this.close()},onClickBack:function(e){"account-list"===this.viewType&&this.model.getLeaseList({isBack:!0})},onClickRemove:function(e){e.preventDefault(),this.close()},onKeyupKeyWord:function(e){var t=this.model,i=e.target.value.trim();i&&(this.timer&&(clearTimeout(this.timer),this.timer=null),this.timer=setTimeout(function(){t.set("keyWord",i),t.getLeaseList({isSearch:!0})},200))},onClickCreate:function(e){e.preventDefault();var t={subjectMatterSourceNo:this.model.get("sourcePropertyNo")};this.trigger("sidebar:createbill",t),this.close()}});module.exports=a});