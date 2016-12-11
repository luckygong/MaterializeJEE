var blockUI = {
		block:function(options){
			var defaults = { element : undefined };
		   	if(!options) {
		   		options = {};
		  	}
		  	options = $.extend({}, defaults, options);
			
			var h='<div class="row">'
				+'<div class="col s12 center">'
				+'<div class="preloader-wrapper big active">'
				+'<div class="spinner-layer spinner-blue">'
				+'<div class="circle-clipper left">'
				+'<div class="circle"></div>'
				+'</div>'
				+'<div class="gap-patch">'
				+'<div class="circle"></div>'
				+'</div>'
				+'<div class="circle-clipper right">'
				+'<div class="circle"></div>'
				+'</div>'
				+'</div>'
				+'<div class="spinner-layer spinner-red">'
				+'<div class="circle-clipper left">'
				+'<div class="circle"></div>'
				+'</div>'
				+'<div class="gap-patch">'
				+'<div class="circle"></div>'
				+'</div>'
				+'<div class="circle-clipper right">'
				+'<div class="circle"></div>'
				+'</div>'
				+'</div>'
				+'<div class="spinner-layer spinner-yellow">'
				+'<div class="circle-clipper left">'
				+'<div class="circle"></div>'
				+'</div>'
				+'<div class="gap-patch">'
				+'<div class="circle"></div>'
				+'</div>'
				+'<div class="circle-clipper right">'
				+'<div class="circle"></div>'
				+'</div>'
				+'</div>'
				+'<div class="spinner-layer spinner-green">'
				+'<div class="circle-clipper left">'
				+'<div class="circle"></div>'
				+'</div>'
				+'<div class="gap-patch">'
				+'<div class="circle"></div>'
				+'</div>'
				+'<div class="circle-clipper right">'
				+'<div class="circle"></div>'
				+'</div>'
				+'</div>'
				+'</div>'
				+'</div>'
				+'</div>';
			
			if(!isEmpty(options.element)){
				$(element).blockUI({ message: h });
			}else{
				$.blockUI({ message: h });
			}
		},
		unblock:function(options){
			var defaults = { element : undefined };
		   	if(!options) {
		   		options = {};
		  	}
		  	options = $.extend({}, defaults, options);
		  	if(!isEmpty(options.element)){
				$(element).unblockUI();
			}else{
				$.unblockUI();
			}
		}
}