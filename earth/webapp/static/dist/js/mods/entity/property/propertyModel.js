define(function(require,exports,module){var e=Backbone.Model.extend({initialize:function(e,t){this.completeModel=t.completeModel},getLeaseTypeDesc:function(){return this.completeModel.getLeaseTypeDesc()},isLeasingSubjectMatterNameUnique:function(e){var t=this.get("leasingSubjectMatterName");this.get("index");if(t===e)return!1;var n=this.completeModel.get("leasingUnitAppendixs"),i=_.findWhere(n,{leasingSubjectMatterName:e});return!!i}}),t=Backbone.Model.extend({defaults:{backCertificateImg:{imgKey:"",thumbNailsImgKey:""},certificateNo:"",certificateType:0,frontCertificateImg:{imgKey:"",thumbNailsImgKey:""},ownerName:"",ownerType:"0",tradePartyUUid:""},initialize:function(e,t){this.completeModel=t.completeModel},isIdExist:function(e){var t=this.get("certificateNo");if(t===e)return!1;var n=this.completeModel.get("ownerInfomations"),i=_.findWhere(n,{certificateNo:e});return!!i}}),n=Backbone.Model.extend({getAllFareTypes:function(){return[{name:"水费",value:"COURSEOFDEALING_LEASING_WATER_FEES"},{name:"燃气费",value:"COURSEOFDEALING_LEASING_GAS_FEES"},{name:"电费",value:"COURSEOFDEALING_LEASING_ELECTRICITY_FEES"}]},getSelectedFareType:function(){var e=this.collection.toJSON();return e},getRemainFareType:function(){var e=this.getSelectedFareType()||[],t=this.getAllFareTypes(),n=this.get("type"),i=_.filter(t,function(t){if(t.value===n)return!0;var i=_.find(e,{type:t.value});return!i});return i}}),i=Backbone.Model.extend({urlRoot:"./",idAttribute:"subjectMatterUuid",defaults:{subjectMatterAppendix:{},leasingUnitAppendixs:[],ownerInfomations:[],memo:{}},initialize:function(e,t){},validate:function(e,t){return e.leasingUnitAppendixs.length<1?!1:e.ownerInfomations.length<1?!1:void 0},manualSave:function(e){this.save({},e)},parse:function(e){return e.data},getLeaseTypeDesc:function(){var e=this.rGet("subjectMatterAppendix.leaseType"),t={0:"长租公寓",1:"创业空间"};return t[e]},getAccountInfoMap:function(){var e=this.rGet("subjectMatterAppendix.accountInfoMap");return e},setAccountInfoMap:function(e){var t=this.get("subjectMatterAppendix");t.accountInfoMap=e,this.set("subjectMatterAppendix",t)},rGet:function(e){for(var t=e.split("."),n=this.get(t.shift()),i=n,a=t.length,r=0;a>r;r++){if("object"!=typeof i)return i;i=i[t[r]]}return i},rSet:function(e,t,n){var i,a={};if(null==e)return this;"object"==typeof e?(i=e,n=t):(i={})[e]=t;for(var r in i){for(var o=r.split("."),s={},c=s,u=o.length,l=0;u-1>l;l++)c=c[o[l]]={};c[o[u-1]]=i[r];var f,p;(f={})[o[0]]=this.get(o[0]),p=$.extend(!0,{},f,s),$.extend(a,p)}this.set(a,n)},rUnset:function(e,t){var n=new Function("delete this.attributes."+e);try{n.call(this)}catch(i){console.warn(i.message)}var a=this.toJSON();this.set(a)}});exports.PropertyModel=i,exports.LeasingUnitAppendixsModel=e,exports.OwnerInfomationsModel=t,exports.FareTypeModel=n});