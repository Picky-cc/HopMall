define(function(require,exports,module){function e(){return function(e){var t=Backbone.View.extend.apply(this,arguments);return t.prototype.events=_.extend({},this.prototype.events,e.events),t}}var t=(require("component/popupTip"),require("scaffold/util")),i=jQuery,o=t.extractValueFromDifferentDom,n=Backbone.View.extend({initialize:function(){},_extractDomData:function(e,t){for(var n={},a=0,s=e.length;s>a;a++)i.extend(n,o(e.eq(a),t));return n},extractDomData:function(){return this._extractDomData(this.$(".real-value"),!0)},save:function(){var e=this.extractDomData();this.model.set(e)},validate:function(){}});n.addError=function(e,t,o){e.addClass("error");var n=e.data("error-ref");n||(n=i('<label for="" class="error-abnormal"></label>'),e.data("error-ref",n)),n.text(t),i.isFunction(o)?o(n,e):o.append(e)},n.removeError=function(e){var t=e.data("error-ref");t&&t.remove(),e.removeClass("error")},n.extend=e();var a=Backbone.View.extend({tagName:"li",className:"item",events:{"click .edit":"toEdit","click .delete":"toDelete",mouseenter:"onMouseenter"},initialize:function(){this.listenTo(this.model,"change",this.render),this.model.collection?this.listenTo(this.model,"remove",this.remove):this.listenTo(this.model,"destroy",this.remove)},onMouseenter:function(e){var t=this.$el;this.modelIsLast()?t.addClass("last-item"):t.removeClass("last-item")},modelIsLast:function(){var e=this.model;return e.collection&&e.collection.last()===e},toEdit:function(){},toDelete:function(e){e.preventDefault(),i(e.target).is(".disalbed")||this.model.destroy()},render:function(){var e=this.template(this.model.toJSON());return this.$el.html(e),this}});a.extend=e();var s=n.extend({tagName:"div",className:"modal fade form-modal small-form-modal",attributes:{role:"dialog",tabindex:"-1"},events:{"click .submit":function(e){e.preventDefault(),this.submitHandler(e)},"hidden.bs.modal":"remove"},initialize:function(){s.__super__.initialize.apply(this,arguments),this.model||(this.model=new Backbone.Model),this.render()},render:function(){var e=this.model.toJSON();"function"==typeof this.template&&this.$el.html(this.template(e))},show:function(){this.$el.modal()},hide:function(){this.$el.modal("hide")},updateTitle:function(e){this.$("#dialoglabel").text(e)},popupTip:function(e,t){switch(this.popupTipEl||(this.popupTipEl=i('<p class="popup-tip" />'),this.$(".modal-body").prepend(this.popupTipEl)),el=this.popupTipEl,t){case"warning":el.addClass("warning");break;case"success":el.addClass("success");break;case"error":default:el.addClass("error")}el.text(e).show()},closeTip:function(){this.popupTipEl.hide()},submitHandler:function(e){}}),l=n.extend({events:{"click .submit":function(e){e.preventDefault(),this.submitHandler(e)},"keydown .form":function(e){13===e.keyCode&&e.preventDefault()}},initialize:function(){l.__super__.initialize.apply(this,arguments),this.on("invalid",function(){var e=this.$(".error:not(label)").offset().top,t=i(".scroller").scrollTop(),o=e+t-120;i(".scroller").animate({scrollTop:o},200)})},submitHandler:function(e){}});exports.FieldsetView=n,exports.SketchItemView=a,exports.FormDialogView=s,exports.BaseFormView=l});