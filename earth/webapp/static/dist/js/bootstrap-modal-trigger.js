
$(document).on('click', "[data-toggle=modal]", function(event) {
    event.preventDefault();
    var url = $(this).attr("href");
    var width = $(this).attr("data-width");
    var id = $(this).attr("data-target").substring(1);
    if (url.indexOf("#") == 0) {
        $(url).modal('open');
    } else {
    	var params = $(this).attr("data-form-params");
    	
    	var data = params ? $('#' + params).serialize() : {};
    	
        $.get(url, data, function(data) {
        	if(width) {
        		$('<div class="modal fade" id="' + id + '" role="dialog" aria-hidden="true" style="width:' + width + 'px">' + data + '</div>').modal().css({
        	        'margin-left': function () {
        	            return -($(this).width() / 2);
        	        }
        	    }).on('hidden.bs.modal', function () { $(this).remove(); });
        	} else {
        		$('<div class="modal fade" id="' + id + '" role="dialog" aria-hidden="true">' + data + '</div>').modal().on('hidden.bs.modal', function () { $(this).remove(); });
        	}
        });
    }
});