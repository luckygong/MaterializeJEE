$(function() {  
	  $("body").delegate('.task-card input:checkbox','change', function() {
		  checkbox_check(this);
	  });
	  
	 $('table thead').css('background','#ECEFF1');
	 
	 $("body").delegate(".tableCheckBoxSelectAll",'click', function(){
		  var clazz = $(this).attr("data-action-attr");
		  if(clazz){
			  $(this).closest("table").find("tr input[type='checkbox'][data-action-attr='"+clazz+"']").each(function(){
				  $(this).prop("checked",true);
			  });
			  $(this).addClass("hide");
			  $(this).closest("table").find(".tableCheckBoxCancelSelect[data-action-attr='"+clazz+"']").removeClass("hide");
		  }
	 });
	 $("body").delegate(".tableCheckBoxCancelSelect",'click',function(){
		 var clazz = $(this).attr("data-action-attr");
		 if(clazz){
			 $(this).closest("table").find("tr input[type='checkbox'][data-action-attr='"+clazz+"']").each(function(){
				 $(this).prop("checked",false);
			 });
			 $(this).addClass("hide");
			 $(this).closest("table").find(".tableCheckBoxSelectAll[data-action-attr='"+clazz+"']").removeClass("hide");
		 }
	 });
	 
	 $("body").delegate('input[type="checkbox"]','change',function(){
		 var clazz = $(this).attr("data-action-attr");
		 if(clazz){
			 if($(this).closest("table").find("tr input[type='checkbox'][data-action-attr='"+clazz+"']:checked").length==0){
				 $(".tableCheckBoxCancelSelect[data-action-attr='"+clazz+"']").addClass("hide");
				 $(".tableCheckBoxSelectAll[data-action-attr='"+clazz+"']").removeClass("hide");
			 }else{
				 $(".tableCheckBoxCancelSelect[data-action-attr='"+clazz+"']").removeClass("hide");
				 $(".tableCheckBoxSelectAll[data-action-attr='"+clazz+"']").addClass("hide");
			 }
		 }
		 
		 if($(this).closest("tr").find("input[type='checkbox']:checked").length==0){
			 $(this).closest("tr").removeClass("active");
		 }else{
			 $(this).closest("tr").addClass("active");
		 }
	 });
	 
	 $("body").delegate(".action-more",'click', function(){
		 if($(this).closest(".card-action").find(".action-more-div").hasClass("hide")){
			 $(this).closest(".card-action").find(".action-more-div").removeClass("hide");
			 $(this).children("i").addClass("rotate-180");
		 }else{
			 $(this).closest(".card-action").find(".action-more-div").addClass("hide");
			 $(this).children("i").removeClass("rotate-180");
		 }
	 });
	 
	 $("body").delegate(".query-more",'click', function(){
		 if($(this).closest(".card-action").find(".query-div").hasClass("hide")){
			 $(this).closest(".card-action").find(".query-div").removeClass("hide");
		 }else{
			 $(this).closest(".card-action").find(".query-div").addClass("hide");
		 }
	 });
	 
	 $("body").delegate(".querySubmit",'click', function(){
		 $(this).closest(".query-div").addClass("hide");
	 });
	 
	 $("body").delegate(".btn-update-inline", "click" ,function(){
		var id = $(this).attr("data-id");
		var url = $(this).attr("data-url");
		if(!isEmpty(id) && !isEmpty(url)){
			loadHTMLToContent('content', url, 'GET');
		}
	});
	$("body").delegate(".btn-view-inline", "click", function(){
		var id = $(this).attr("data-id");
		var url = $(this).attr("data-url");
		if(!isEmpty(id) && !isEmpty(url)){
			Modal.show({title:"查看", loadUrl:url});
		}
	});
	$("body").delegate(".btn-delete", "click", function(){
		var checkbox = getAllSelectedCheckbox("id");
	    if(!checkbox.length) return;
	    var url = $(this).attr("data-url");
	    if(isEmpty(url)) return;
	    Alert.AjaxConfirm({message:"确认删除选中记录？",type:"warning",ajaxOptions:{
	    	url:url,
	    	data:checkbox.serialize(),
	    	successCallBack:function(){$(".querySubmit").click();return true;}
	    }});
	});
	 
	 $("body").delegate(".resetForm",'click', function(){
		 $(this).closest("form").get(0).reset();
	 });
	 
	 $("div").delegate(".reveal-progress-next","click",function(e){
		 e.stopPropagation();
		 $(this).closest(".reveal").find(".controls .navigate-right").click();
		 $('.reveal').animate({ scrollTop: 0 }, 200);
		 return;
	 });
	 
	 $("div").delegate(".reveal-progress-pre","click",function(e){
		 e.stopPropagation();
		 $(this).closest(".reveal").find(".controls .navigate-left").click();
		 $('.reveal').animate({ scrollTop: 0 }, 200);
		 return;
	 });
});

function checkbox_check(el){
    if (!$(el).is(':checked')) {
        $(el).next().css('text-decoration', 'none');            
    } else {
        $(el).next().css('text-decoration', 'line-through');
    }    
}

function isEmpty(value){
	if(value==null || typeof(value)==undefined || value==undefined || value==''){
		return true;
	}
	return false;
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

function getDataFromJson(json,field,def){
	if(isEmpty(json) || isEmpty(field)){
		return isEmpty(def)?"":def;
	}
	var fs = field.split('.');
	var o = json;
	for (var i = 0; i < fs.length; i++) {
		if (fs[i]) {
			try{
				o = o[fs[i]];
			}catch(e){
			}
		}
	}
	if(isEmpty(o)){
		return isEmpty(def)?"":def;
	}else{
		return o;
	}
}

function perfectScroll($elem, height){
	if(height){
		$elem.height(height).perfectScrollbar({
		    suppressScrollX: true
		}); 
	}else{
		$elem.perfectScrollbar({
			suppressScrollX: true
		}); 
	}
}

function loadHTMLToContent(contentId, URL, type ,params, backURL) {
	var backurl = $("body .breadcrumbs .currentPage").attr("data-init-page-url");
	if(isEmpty(backurl)){
		backurl = "home"
	}
	if(!isEmpty(backURL)){
		backurl = backURL;
	}
	
	if(isEmpty(type)){
		type = "GET";
	}
	
	$.ajax({
		url : getProjectName()+'/'+URL,
		data : params,
		type : type,
		dataType : "html",
		beforeSend : function() {
			$("#"+contentId).html("");
			blockUI.block();
		},
		success : function(htmlData) {
			$("#"+contentId).html(htmlData);
			blockUI.unblock();
			$("#"+contentId+" .goBack").click(function(){
				loadHTMLToContent(contentId, backurl, 'GET');
			});
			initPlugsAfterLoad();
		},
		error : function(XMLHttpRequest, status, thrownError) {
			blockUI.unblock();
			Massage.danger({message:'加载页面失败'});
		}
	});
}

function initPlugsAfterLoad(){
	$('.collapsible').collapsible({
		  accordion : false 
    });
	
	$('.modal-trigger').leanModal({
	      dismissible: true,
	      opacity: .5, 
	      in_duration: 300, 
	      out_duration: 200, 
	      ready: function() {}, // Callback for Modal open
	      complete: function() {} // Callback for Modal close
	});
	
	$('.modal-content').height($(".modal").height()-88).perfectScrollbar({
        suppressScrollX: true
    });
	
	$('.dropify').dropify();
	$(".dropify-message p").css("text-align","center");
}

function getAllSelectedCheckbox(checkboxName) {
	var checkbox = $("body").find(":checkbox[name="+checkboxName+"]:checked");

    if(!checkbox.length) {
    	Message.info({message:"未选择记录",time:3});
    }
    return checkbox;
}

function getFirstSelectedCheckbox(checkboxName) {
	var checkbox = $("body").find(":checkbox[name="+checkboxName+"]:checked:first");
    
    if(!checkbox.length) {
    	Message.info({message:"未选择记录",time:3});
    }
    return checkbox;
}
