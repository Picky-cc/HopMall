!function(e,t){function r(e){return function(t){return{}.toString.call(t)=="[object "+e+"]"}}function n(){return A++}function a(e){return e.match(O)[0]}function s(e){for(e=e.replace(T,"/"),e=e.replace(D,"$1/");e.match(q);)e=e.replace(q,"/");return e}function i(e){var t=e.length-1,r=e.charAt(t);return"#"===r?e.substring(0,t):".js"===e.substring(t-2)||e.indexOf("?")>0||"/"===r?e:e+".js"}function o(e){var t=E.alias;return t&&j(t[e])?t[e]:e}function u(e){var t,r=E.paths;return r&&(t=e.match(C))&&j(r[t[1]])&&(e=r[t[1]]+t[2]),e}function c(e){var t=E.vars;return t&&e.indexOf("{")>-1&&(e=e.replace(I,function(e,r){return j(t[r])?t[r]:e})),e}function l(e){var t=E.map,r=e;if(t)for(var n=0,a=t.length;a>n;n++){var s=t[n];if(r=_(s)?s(e)||e:e.replace(s[0],s[1]),r!==e)break}return r}function f(e,t){var r,n=e.charAt(0);if(M.test(e))r=e;else if("."===n)r=s((t?a(t):E.cwd)+e);else if("/"===n){var i=E.cwd.match(U);r=i?i[0]+e.substring(1):e}else r=E.base+e;return 0===r.indexOf("//")&&(r=location.protocol+r),r}function d(e,t){if(!e)return"";e=o(e),e=u(e),e=c(e),e=i(e);var r=f(e,t);return r=l(r)}function v(e){return e.hasAttribute?e.src:e.getAttribute("src",4)}function h(e,t,r){var n=$.createElement("script");if(r){var a=_(r)?r(e):r;a&&(n.charset=a)}p(n,t,e),n.async=!0,n.src=e,B=n,F?X.insertBefore(n,F):X.appendChild(n),B=null}function p(e,t,r){function n(){e.onload=e.onerror=e.onreadystatechange=null,E.debug||X.removeChild(e),e=null,t()}var a="onload"in e;a?(e.onload=n,e.onerror=function(){N("error",{uri:r,node:e}),n()}):e.onreadystatechange=function(){/loaded|complete/.test(e.readyState)&&n()}}function g(){if(B)return B;if(L&&"interactive"===L.readyState)return L;for(var e=X.getElementsByTagName("script"),t=e.length-1;t>=0;t--){var r=e[t];if("interactive"===r.readyState)return L=r}}function m(e){var t=[];return e.replace(K,"").replace(V,function(e,r,n){n&&t.push(n)}),t}function b(e,t){this.uri=e,this.dependencies=t||[],this.exports=null,this.status=0,this._waitings={},this._remain=0}if(!e.seajs){var y=e.seajs={version:"2.3.0"},E=y.data={},x=r("Object"),j=r("String"),S=Array.isArray||r("Array"),_=r("Function"),A=0,w=E.events={};y.on=function(e,t){var r=w[e]||(w[e]=[]);return r.push(t),y},y.off=function(e,t){if(!e&&!t)return w=E.events={},y;var r=w[e];if(r)if(t)for(var n=r.length-1;n>=0;n--)r[n]===t&&r.splice(n,1);else delete w[e];return y};var N=y.emit=function(e,t){var r=w[e];if(r){r=r.slice();for(var n=0,a=r.length;a>n;n++)r[n](t)}return y},O=/[^?#]*\//,T=/\/\.\//g,q=/\/[^\/]+\/\.\.\//,D=/([^:\/])\/+\//g,C=/^([^\/:]+)(\/.+)$/,I=/{([^{]+)}/g,M=/^\/\/.|:\//,U=/^.*?\/\/.*?\//,$=document,H=location.href&&0!==location.href.indexOf("about:")?a(location.href):"",k=$.scripts,R=$.getElementById("seajsnode")||k[k.length-1],G=a(v(R)||H);y.resolve=d;var B,L,X=$.head||$.getElementsByTagName("head")[0]||$.documentElement,F=X.getElementsByTagName("base")[0];y.request=h;var P,V=/"(?:\\"|[^"])*"|'(?:\\'|[^'])*'|\/\*[\S\s]*?\*\/|\/(?:\\\/|[^\/\r\n])+\/(?=[^\/])|\/\/.*|\.\s*require|(?:^|[^$])\brequire\s*\(\s*(["'])(.+?)\1\s*\)/g,K=/\\\\/g,W=y.cache={},Y={},z={},J={},Q=b.STATUS={FETCHING:1,SAVED:2,LOADING:3,LOADED:4,EXECUTING:5,EXECUTED:6};b.prototype.resolve=function(){for(var e=this,t=e.dependencies,r=[],n=0,a=t.length;a>n;n++)r[n]=b.resolve(t[n],e.uri);return r},b.prototype.load=function(){var e=this;if(!(e.status>=Q.LOADING)){e.status=Q.LOADING;var r=e.resolve();N("load",r);for(var n,a=e._remain=r.length,s=0;a>s;s++)n=b.get(r[s]),n.status<Q.LOADED?n._waitings[e.uri]=(n._waitings[e.uri]||0)+1:e._remain--;if(0===e._remain)return e.onload(),t;var i={};for(s=0;a>s;s++)n=W[r[s]],n.status<Q.FETCHING?n.fetch(i):n.status===Q.SAVED&&n.load();for(var o in i)i.hasOwnProperty(o)&&i[o]()}},b.prototype.onload=function(){var e=this;e.status=Q.LOADED,e.callback&&e.callback();var t,r,n=e._waitings;for(t in n)n.hasOwnProperty(t)&&(r=W[t],r._remain-=n[t],0===r._remain&&r.onload());delete e._waitings,delete e._remain},b.prototype.fetch=function(e){function r(){y.request(i.requestUri,i.onRequest,i.charset)}function n(){delete Y[o],z[o]=!0,P&&(b.save(s,P),P=null);var e,t=J[o];for(delete J[o];e=t.shift();)e.load()}var a=this,s=a.uri;a.status=Q.FETCHING;var i={uri:s};N("fetch",i);var o=i.requestUri||s;return!o||z[o]?(a.load(),t):Y[o]?(J[o].push(a),t):(Y[o]=!0,J[o]=[a],N("request",i={uri:s,requestUri:o,onRequest:n,charset:E.charset}),i.requested||(e?e[i.requestUri]=r:r()),t)},b.prototype.exec=function(){function e(t){return b.get(e.resolve(t)).exec()}var r=this;if(r.status>=Q.EXECUTING)return r.exports;r.status=Q.EXECUTING;var a=r.uri;e.resolve=function(e){return b.resolve(e,a)},e.async=function(t,r){return b.use(t,r,a+"_async_"+n()),e};var s=r.factory,i=_(s)?s(e,r.exports={},r):s;return i===t&&(i=r.exports),delete r.factory,r.exports=i,r.status=Q.EXECUTED,N("exec",r),i},b.resolve=function(e,t){var r={id:e,refUri:t};return N("resolve",r),r.uri||y.resolve(r.id,t)},b.define=function(e,r,n){var a=arguments.length;1===a?(n=e,e=t):2===a&&(n=r,S(e)?(r=e,e=t):r=t),!S(r)&&_(n)&&(r=m(""+n));var s={id:e,uri:b.resolve(e),deps:r,factory:n};if(!s.uri&&$.attachEvent){var i=g();i&&(s.uri=i.src)}N("define",s),s.uri?b.save(s.uri,s):P=s},b.save=function(e,t){var r=b.get(e);r.status<Q.SAVED&&(r.id=t.id||e,r.dependencies=t.deps||[],r.factory=t.factory,r.status=Q.SAVED,N("save",r))},b.get=function(e,t){return W[e]||(W[e]=new b(e,t))},b.use=function(t,r,n){var a=b.get(n,S(t)?t:[t]);a.callback=function(){for(var t=[],n=a.resolve(),s=0,i=n.length;i>s;s++)t[s]=W[n[s]].exec();r&&r.apply(e,t),delete a.callback},a.load()},y.use=function(e,t){return b.use(e,t,E.cwd+"_use_"+n()),y},b.define.cmd={},e.define=b.define,y.Module=b,E.fetchedList=z,E.cid=n,y.require=function(e){var t=b.get(b.resolve(e));return t.status<Q.EXECUTING&&(t.onload(),t.exec()),t.exports},E.base=G,E.dir=G,E.cwd=H,E.charset="utf-8",y.config=function(e){for(var t in e){var r=e[t],n=E[t];if(n&&x(n))for(var a in r)n[a]=r[a];else S(n)?r=n.concat(r):"base"===t&&("/"!==r.slice(-1)&&(r+="/"),r=f(r)),E[t]=r}return N("config",e),y}}}(this),!function(){function e(e){o[e.name]=e}function t(e){return e&&o.hasOwnProperty(e)}function r(e){for(var r in o)if(t(r)){var n=","+o[r].ext.join(",")+",";if(n.indexOf(","+e+",")>-1)return r}}function n(e,t){var r=i.XMLHttpRequest?new i.XMLHttpRequest:new i.ActiveXObject("Microsoft.XMLHTTP");return r.open("GET",e,!0),r.onreadystatechange=function(){if(4===r.readyState){if(r.status>399&&r.status<600)throw new Error("Could not load: "+e+", status = "+r.status);t(r.responseText)}},r.send(null)}function a(e){e&&/\S/.test(e)&&(i.execScript||function(e){(i.eval||eval).call(i,e)})(e)}function s(e){return e.replace(/(["\\])/g,"\\$1").replace(/[\f]/g,"\\f").replace(/[\b]/g,"\\b").replace(/[\n]/g,"\\n").replace(/[\t]/g,"\\t").replace(/[\r]/g,"\\r").replace(/[\u2028]/g,"\\u2028").replace(/[\u2029]/g,"\\u2029")}var i=window,o={},u={};e({name:"text",ext:[".tpl",".html"],exec:function(e,t){a('define("'+e+'#", [], "'+s(t)+'")')}}),e({name:"json",ext:[".json"],exec:function(e,t){a('define("'+e+'#", [], '+t+")")}}),e({name:"handlebars",ext:[".handlebars"],exec:function(e,t){var r=['define("'+e+'#", ["handlebars"], function(require, exports, module) {','  var source = "'+s(t)+'"','  var Handlebars = require("handlebars")["default"]',"  module.exports = function(data, options) {","    options || (options = {})","    options.helpers || (options.helpers = {})","    for (var key in Handlebars.helpers) {","      options.helpers[key] = options.helpers[key] || Handlebars.helpers[key]","    }","    return Handlebars.compile(source)(data, options)","  }","})"].join("\n");a(r)}}),seajs.on("resolve",function(e){var n=e.id;if(!n)return"";var a,s;(s=n.match(/^(\w+)!(.+)$/))&&t(s[1])?(a=s[1],n=s[2]):(s=n.match(/[^?]+(\.\w+)(?:\?|#|$)/))&&(a=r(s[1])),a&&-1===n.indexOf("#")&&(n+="#");var i=seajs.resolve(n,e.refUri);a&&(u[i]=a),e.uri=i}),seajs.on("request",function(e){var t=u[e.uri];t&&(n(e.requestUri,function(r){o[t].exec(e.uri,r),e.onRequest()}),e.requested=!0)}),define("seajs/seajs-text/1.1.1/seajs-text-debug",[],{})}(),!function(){function e(e){return function(t){return{}.toString.call(t)=="[object "+e+"]"}}function t(e){return"[object Function]"=={}.toString.call(e)}function r(e,r,a,s){var i=E.test(e),o=m.createElement(i?"link":"script");if(a){var u=t(a)?a(e):a;u&&(o.charset=u)}void 0!==s&&o.setAttribute("crossorigin",s),n(o,r,i,e),i?(o.rel="stylesheet",o.href=e):(o.async=!0,o.src=e),p=o,y?b.insertBefore(o,y):b.appendChild(o),p=null}function n(e,t,r,n){function s(){e.onload=e.onerror=e.onreadystatechange=null,r||seajs.data.debug||b.removeChild(e),e=null,t()}var i="onload"in e;return!r||!x&&i?void(i?(e.onload=s,e.onerror=function(){seajs.emit("error",{uri:n,node:e}),s()}):e.onreadystatechange=function(){/loaded|complete/.test(e.readyState)&&s()}):void setTimeout(function(){a(e,t)},1)}function a(e,t){var r,n=e.sheet;if(x)n&&(r=!0);else if(n)try{n.cssRules&&(r=!0)}catch(s){"NS_ERROR_DOM_SECURITY_ERR"===s.name&&(r=!0)}setTimeout(function(){r?t():a(e,t)},20)}function s(e){return e.match(S)[0]}function i(e){for(e=e.replace(_,"/"),e=e.replace(w,"$1/");e.match(A);)e=e.replace(A,"/");return e}function o(e){var t=e.length-1,r=e.charAt(t);return"#"===r?e.substring(0,t):".js"===e.substring(t-2)||e.indexOf("?")>0||".css"===e.substring(t-3)||"/"===r?e:e+".js"}function u(e){var t=j.alias;return t&&g(t[e])?t[e]:e}function c(e){var t,r=j.paths;return r&&(t=e.match(N))&&g(r[t[1]])&&(e=r[t[1]]+t[2]),e}function l(e){var t=j.vars;return t&&e.indexOf("{")>-1&&(e=e.replace(O,function(e,r){return g(t[r])?t[r]:e})),e}function f(e){var r=j.map,n=e;if(r)for(var a=0,s=r.length;s>a;a++){var i=r[a];if(n=t(i)?i(e)||e:e.replace(i[0],i[1]),n!==e)break}return n}function d(e,t){var r,n=e.charAt(0);if(T.test(e))r=e;else if("."===n)r=i((t?s(t):j.cwd)+e);else if("/"===n){var a=j.cwd.match(q);r=a?a[0]+e.substring(1):e}else r=j.base+e;return 0===r.indexOf("//")&&(r=location.protocol+r),r}function v(e,t){if(!e)return"";e=u(e),e=c(e),e=l(e),e=o(e);var r=d(e,t);return r=f(r)}function h(e){return e.hasAttribute?e.src:e.getAttribute("src",4)}var p,g=e("String"),m=document,b=m.head||m.getElementsByTagName("head")[0]||m.documentElement,y=b.getElementsByTagName("base")[0],E=/\.css(?:\?|$)/i,x=+navigator.userAgent.replace(/.*(?:AppleWebKit|AndroidWebKit)\/?(\d+).*/i,"$1")<536;seajs.request=r;var j=seajs.data,S=/[^?#]*\//,_=/\/\.\//g,A=/\/[^\/]+\/\.\.\//,w=/([^:\/])\/+\//g,N=/^([^\/:]+)(\/.+)$/,O=/{([^{]+)}/g,T=/^\/\/.|:\//,q=/^.*?\/\/.*?\//,m=document,D=location.href&&0!==location.href.indexOf("about:")?s(location.href):"",C=m.scripts,I=m.getElementById("seajsnode")||C[C.length-1];s(h(I)||D),seajs.resolve=v,define("seajs/seajs-css/1.0.5/seajs-css",[],{})}(),seajs.config({paths:{component:"component",baseView:"view",scaffold:"scaffold",entity:"entity"}}),_.templateSettings={evaluate:/\{%([\s\S]+?)\%\}/g,interpolate:/\{%=([\s\S]+?)\%\}/g,escape:/\{%-([\s\S]+?)%\}/g};var global_const={loadingImg:$('<img class="loading" src="'+global_config.root+'/static/images/loading.gif">')};if(Number.prototype.formatMoney=function(e,t,r,n){e=isNaN(e=Math.abs(e))?2:e,t=void 0!==t?t:"$",r=r||",",n=n||".";var a=this,s=0>a?"-":"",i=parseInt(a=Math.abs(+a||0).toFixed(e),10)+"",o=(o=i.length)>3?o%3:0;return t+s+(o?i.substr(0,o)+r:"")+i.substr(o).replace(/(\d{3})(?=\d)/g,"$1"+r)+(e?n+Math.abs(a-i).toFixed(e).slice(2):"")},Number.prototype.formatPercent=function(e){e=isNaN(e=Math.abs(e))?2:e;var t=this;return t.toFixed(e)+"%"},Date.prototype.format=function(e){var t=Number,r=function(e){return 1===t(e).toString().length?"0"+e:e},n=this.getFullYear(),a=this.getMonth()+1,s=this.getDate(),i=this.getHours(),o=this.getMinutes(),u=this.getSeconds(),c=e.replace("yyyy",n).replace("MM",r(a)).replace("dd",r(s)).replace("HH",r(i)).replace("mm",r(o)).replace("ss",r(u));return/NaN/i.test(c)?"":c},"function"!=typeof"".trim){var s_reg=/^\s*/,e_reg=/\s*$/;String.prototype.trim=function(){var e=this.replace(s_reg,"").replace(e_reg,"");return e}}seajs.use("router",function(){Backbone.history.start({root:global_config.root,hashChange:!1,pushState:!1})});