define(function(require,exports,module){var t=require("component/dialogView"),i=t.extend({events:{"click .fold":"onFoldClick"},manipultateLogTmpl:_.template($("#manipultateLogTmpl").html()),initialize:function(){i.__super__.initialize.apply(this,arguments);var t=this.model.getLog(),e=this.manipultateLogTmpl(t);this.showRichText(e)},onFoldClick:function(t){t.preventDefault();var i=$(t.target);i.hasClass("in")?(i.text("收起"),i.removeClass("in"),this.foldIn()):(i.text("展开"),i.addClass("in"),this.foldOut())},foldIn:function(t){var i=this.$("#logList .log-item");i=i.filter(function(t){return t>0}),i.show()},foldOut:function(t){var i=this.$("#logList .log-item");i=i.filter(function(t){return t>0}),i.hide()}});module.exports=i});