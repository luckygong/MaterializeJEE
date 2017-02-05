<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta charset="utf-8" />
</head>

<!--breadcrumbs start-->
<div id="breadcrumbs-wrapper">
	<div class="container">
		<div class="row">
			<div class="col s12 m12 l12">
				<h5 class="breadcrumbs-title">资源管理</h5>
				<ol class="breadcrumbs">
					<li><a
						href="javascript:window.location.href='${pageContext.request.contextPath}/main';">首页</a></li>
					<li class="active">系统管理</li>
					<li class="active currentPage" data-init-page-url="admin/resource/resourceList">资源管理</li>
				</ol>
			</div>
		</div>
	</div>
</div>
				
<div class="container">
	<ul class="row collapsible collapsible-accordion" data-collapsible="accordion">
   		<li>
       		<div id="query-header" class="collapsible-header cyan white-text">
           		<i class="mdi-action-list"></i>查询条件
       		</div>
         	<div class="collapsible-body light-blue lighten-5">
            	<div class="card-panel">
					<div class="row">
						<form id="pageQueryForm" class="col s12">
							<div class="row">
								<div class="input-field col s12 m4 l3">
									<label for="nameLike">资源名称</label>
									<input id="nameLike" name="nameLike" type="text" class="validate">
								</div>
								<div class="input-field col s12 m4 l3">
									<label for="valueLike">资源值</label>
									<input id="valueLike" name="valueLike" type="text" class="validate">
								</div>
								<div class="input-field col s12 m4 l3">
									<label for="type"></label>
									<select id="type" name="type" type="text" class="browser-default validate"></select>
								</div>
								<div class="input-field col s12 m12 l3 btn-group">
									<button class="btn btn-suit yellow darken-4 waves-effect waves-light right resetForm" type="button">
										重置 <i class="mdi-av-replay right"></i>
									</button>
									<button class="btn btn-suit cyan waves-effect waves-light right querySubmit" type="button">
										查询 <i class="mdi-content-send right"></i>
									</button>
								</div>
							</div>
						</form>
					</div>
				</div>
           	</div>
    	</li>
 	</ul>
				
	<div class="btn-card">
		<div class="card row">
			<div class="card-action cyan white-text btn-group">
               	<i class="title-icon mdi-notification-event-note" style=""></i>资源树
               	<button class="btn-flat waves-effect waves-light  pink accent-2 action-more white-text btn-suit right" type="button"><i class="mdi-image-details"></i></button>
                <button id="create" class="btn-flat waves-effect waves-light  pink accent-2 white-text btn-suit right" type="button">新增 <i class="mdi-content-add right"></i></button>
	          	<div class="action-more-div cyan white-text btn-group hide">
	                <button class="btn-delete btn-flat waves-effect waves-light  pink accent-2 white-text btn-suit right" data-url="admin/resource/delete" type="button">删除 <i class="mdi-content-remove right"></i></button>
	                <button class="btn-update btn-flat waves-effect waves-light  pink accent-2 white-text btn-suit right" type="button">修改 <i class="mdi-content-create right"></i></button>
	          	</div>
          	</div>
        	<div id="page">
             	<div id="page-content" class="card-content">
              		<div id="resourceTree" class="col s12"></div>
            	</div>
         	</div>
     	</div>
	</div>
</div>


<script type="text/javascript">
var resourceTypeMap = {};
var isDirMap = {};
$(document).ready(function() {
	resourceTypeMap = $("#type").initSelect({"category":"RESOURCE_TYPE","emptyOptionTxt":"-请选择资源类型-"});
	isDirMap = initCategoryMap({"category":"RESOURCE_IS_DIR"});
	
	$(".querySubmit").click(function() {
		$.ajax({
			cache: false,
			url:getProjectName()+'/admin/resource/resourceTree',
			dataType:'json',
			data:$("#pageQueryForm").serialize(),
			type: "POST",
			async: true,
			success:function(result){
				if(result.status==1){
					initPageTable(result);
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				Message.danger({"message":"操作失败"});
			}
		});
	});
	$(".querySubmit").click();
	
	$("#create").click(function(){
		loadHTMLToContent('content', 'admin/resource/resourceCreate', 'GET');
	});
});

function initPageTable(queryResult) {
	var contentor = $('#resourceTree');
	var h = "";
	var tree = queryResult.data;
	var h='<div class="dd nestable cascade" style="max-width: 1200px;">'
		+'<ol class="dd-list">'
		for (var i = 0; i < tree.length; i++) {
			var node = tree[i];
			h+=createNestableTree(node);
		}
        h+='</ol></div>';
	contentor.html(h);
	
	$(".createSubMenu").click(function(){
		var id=$(this).attr("data-id");
		var type=$(this).attr("data-type");
		if(!isEmpty(id)){
			loadHTMLToContent('content', 'admin/resource/resourceCreate', 'GET',{'parentId':id,'type':type});
		}
	});
	
	$('.nestable').nestable().nestable('collapseAll');;
	$('.profile-dropdown').initDynDropDown();
}

function createNestableTree(node){
	if(isEmpty(node)){
		return '';
	}
	var h="";
	if(!isEmpty(node.childs) && node.childs.length>0){
              h+='<li class="dd-item" data-id="'+node.id+'">'
                +'<div class="dd3-content" style="height:100%">'+getResourceCardView(node)+'</div>';
                
		var childs = node.childs;
		if(!isEmpty(childs)){
			h+='<ol class="dd-list">';
			for(var j=0;j<childs.length;j++){
				var childNode = childs[j];
				h+=createNestableTree(childNode);
			}
			h+='</ol>';
		}
        h+='</li>';
	}else{
		h+='<li class="dd-item" data-id="'+node.id+'">'
                +'<div class="dd3-content" style="height:100%">'+getResourceCardView(node)+'</div>'
                +'</li>';
	}
	return h;
}

function getResourceCardView(resource){
	var c = '<div class="row valign-wrapper">'
  					+'<input class="valign" type="checkbox" id="id'+resource.id+'" name="id" value="'+resource.id+'" /><label for="id'+resource.id+'"></label>'
  					+'<div class="span-group col s12">'
				   +'<span class="col s6 m3 l3">'+resource.name+'</span>'
				   +'<span class="col s6 m3 l3">'+resourceTypeMap[resource.type]+'</span>';
				   if(!isEmpty(getDataFromJson(resource,"parent.id"))){
			   	   	c+='<span class="col s12 m6 l6">'+getDataFromJson(resource,"value")+'</span>';
				   }
			   c+='</div>'
		   +'</div>'
		 	+'<div class="divider"></div>'
		   +'<div class="row">'
			   +'<div class="span-group col s12">'
			   	   +'<a href="javascript:;" data-url="admin/resource/'+resource.id+'" class="btn-view-inline valign col s3 m3 l1" data-id="'+resource.id+'"><b>详情</b></a>'
			   	   +'<a href="javascript:;" data-url="admin/resource/'+resource.id+'/resourceUpdate" class="btn-update-inline valign col s3 m3 l1" data-id="'+resource.id+'"><b>修改</b></a>';
			   	   if(isEmpty(getDataFromJson(resource,"parent.id"))){
			   	   	c+='<a href="javascript:;" class="createSubMenu valign col s5 m5 l2" data-type="0" data-id="'+resource.id+'"><b>添加子菜单</b></a>';
			   	   }
			   	   if(resource.isDirectory==0){//页面
			   	   	c+='<a href="javascript:;" class="createSubMenu valign col s5 m5 l2" data-type="1" data-id="'+resource.id+'"><b>添加页面方法</b></a>';
			   	   }
	   			c+='</div>'
   			+'</div>';
  		return c;
}
</script>
