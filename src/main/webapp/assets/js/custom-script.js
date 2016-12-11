
$(function() {  
	 $('table thead').css('background','#ECEFF1');
	 
	 $("#tableCheckBoxSelectAll").click(function(){
		  $(this).closest("table").find("tr input[type='checkbox'].id").each(function(){
			 $(this).prop("checked",true);
		  });;
		  $("#tableCheckBoxSelectAll").addClass("hide");
		  $("#tableCheckBoxCancelSelect").removeClass("hide");
	 });
	 $("#tableCheckBoxCancelSelect").click(function(){
		 $(this).closest("table").find("tr input[type='checkbox'].id").each(function(){
			 $(this).prop("checked",false);
		 });;
		 $("#tableCheckBoxCancelSelect").addClass("hide");
		 $("#tableCheckBoxSelectAll").removeClass("hide");
	 });
	 
	 $("table").delegate('input[type="checkbox"].id','click',function(){
		 if($(this).closest("table").find("tr input[type='checkbox'].id:checked").length==0){
			 $("#tableCheckBoxCancelSelect").addClass("hide");
			 $("#tableCheckBoxSelectAll").removeClass("hide");
		 }
	 });
	 
	 $(".resetForm").click(function(){
		 $(this).closest("form").get(0).reset();
	 });
	 
});

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