(function ($) {
 	
    var methods = {
        init: function (options) {

            var url = arguments[0].posturl;
            //alert(url);
            var success = arguments[0].successurl;
            //alert(success);
            var formId = arguments[0].formid;
            this.bind("click",function(event){
            	event.preventDefault();
            	methods.doPost(url,formId,success);
            })

        },
        // validate before we get data if  return false stop submit
        validate: function(formId){
          var isValid = true;
          $("#"+formId).find('[data-toggle="preupload"]').each(function() {
	        if($(this).find('img').length == 0) {
	          $(this).append('<p class="no-botto-margin text-danger">请上传图标！</p>');
	          isValid = false;
	          return false;
	        }
	      });
	      return isValid;
        },

        // get form data
        getData: function(formId){
        	if(methods.validate(formId)){
        		var objChecks=$("#"+formId).find("input,textarea,select,radio");
	        	var data={};
	        	var j = objChecks.length;
	        	for(var i =0 ; i<j; i++){
	        		var name = $(objChecks[i]).attr("name");
	        		var value = $(objChecks[i]).val();
	        		data[name]=value;
	        	}
	        	return data;
	        }
        },

        // do post
        doPost: function (posturl,formId,successurl) {
        	var data = methods.getData(formId);
        	if(data){
        		$.post(posturl, data , function(successData){
        			$("#"+formId).parents(".modal").modal("hide");
        			methods.doSuccess(successData,successurl);
        		});
        	}
        },
        // do somthing when success
        doSuccess: function(successData,successurl){
        	window.location.href=successurl;
        }
    };
 
    $.fn.submitModal = function (options) {
        if (options) {
            return methods.init.apply(this, arguments);
        } else {
            $.error(' Where is the arguments for submitModal ？');
        }
    };
 
})(jQuery);