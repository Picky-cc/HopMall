define(function(require,exports,module){var t=require("./pageControl"),e=require("scaffold/util"),a=jQuery,i=global_const.loadingImg,s=e.extractValueFromDifferentDom,r=Backbone.View.extend({events:{"click .redirect,.prev,.next,.first-page,.last-page":"onClickPageNavigateBtn"},initialize:function(){var e=this.$(".template").html()||"";e&&(this.template=_.template(e),this.tableListEl=this.$("table[data-action]"),this.rowNum=this.tableListEl.find("thead th").length,this.lookupParamsEl=this.$(".lookup-params"),this.queryAction=this.tableListEl.data("action"),this.pageControl=new t({el:this.$(".page-control").get(0),url:this.queryAction}),this.listenTo(this.pageControl,"next:pagecontrol prev:pagecontrol redirect:pagecontrol",this.refreshTableDataList),this.listenToOnce(this.pageControl,"request",function(){var t='<tr><td style="text-align: center;" colspan="'+this.rowNum+'"></td></tr>',e=a(t).find("td").html(i.clone());this.tableListEl.find("tbody").html(e)}),this.getFirstData())},refreshTableDataList:function(t,e,a,i){if(this.template){var s;s=t.length<1?'<tr class="nomore"><td style="text-align: center;" colspan="'+this.rowNum+'">没有更多数据</td></tr>':this.template({list:this.polish(t)}),this.tableListEl.find("tbody").html(s),this.trigger("refresh",t,e,a,i)}},collectQueryParams:function(t){for(var e=this.lookupParamsEl.find(".real-value"),i=0,r=e.length;r>i;i++)a.extend(t,s(e.eq(i)));return t},collectSortParams:function(t){var e=this.tableListEl.find("thead .sort"),i={};return a.each(e,function(t,e){var s=a(e),r=s.data("paramname"),l=s.hasClass("desc")?"DESC":s.hasClass("asc")?"ASC":"";r&&l&&(i[r]=l)}),a.extend(t,i),t},collectParams:function(){var t={};return this.collectQueryParams(t),this.collectSortParams(t),t},getFirstData:function(){var t=this.collectParams();this.pageControl.redirect(1,t)},query:function(){var t=this.collectParams();this.pageControl.redirect(1,t)},polish:function(t){return t},onClickPageNavigateBtn:function(t){t.preventDefault();var e=a(t.target),i=this.collectParams();e.hasClass("prev")?this.pageControl.prev(i):e.hasClass("next")?this.pageControl.next(i):e.hasClass("redirect")?this.pageControl.importPageIndexRedirect(i):e.hasClass("first-page")?this.pageControl.first(i):e.hasClass("last-page")&&this.pageControl.last(i)}});r.find=function(t,e){for(var i=this,s=a(e||document).find(t),r=0;r<s.length;r++){var l=s.eq(r),n=l.data("pagination");n||l.data("pagination",new i({el:l[0]}))}},module.exports=r});