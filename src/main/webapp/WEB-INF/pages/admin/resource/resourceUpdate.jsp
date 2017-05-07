<%@ page language="java" pageEncoding="UTF-8"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
					<li class="active">首页</li>
					<li class="active">系统管理</li>
					<li class="active">资源管理</li>
					<li class="active">修改资源</li>
				</ol>
			</div>
		</div>
	</div>
</div>

<div class="container">
	<div class="card-panel">
		<div class="breadcrumbs-title"><span>资源基本信息</span></div>
		<div class="row">
			<form id="form" class="col s12 right-alert" method="post">
				<div class="row">
					<input id="id" name="id" value="${id}" class="hide" type="text">
					<div class="input-field col s12 m6 s6">
						<i class="mdi-action-account-circle prefix"></i> 
						<input id="name" name="name" type="text" data-bind-json-path="/" data-bind-name="name" class="validate"> 
						<label for="name">资源名称</label>
					</div>
					<div class="input-field col s8 m4 s4">
						<i class="mdi-social-share prefix"></i> 
						<input id="parentName" name="parent.name" type="text" data-bind-json-path="/parent" data-bind-name="name" disabled> 
						<input id="parentId" name="parent.id" class="hide" type="text" class="validate" data-bind-json-path="/parent" data-bind-name="id"> 
						<label for="parentName">父资源</label>
					</div>
					<div class="input-field col s4 m2 s2">
						<div id="resourceSelect" class="btn btn-suit modal-trigger" href="#modalResourceSelect">
							<span>选择</span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12 m6 s6">
						<i class="mdi-hardware-keyboard prefix"></i> 
						<input id="value" name="value" type="text" class="validate" data-bind-json-path="/" data-bind-name="value"> 
						<label for="value">资源值</label>
					</div>
					<div class="input-field col s12 m6 s6">
						<i class="mdi-image-image prefix"></i> 
						<input id="icon" name="icon" type="text" class="validate" data-bind-json-path="/" data-bind-name="icon"> 
						<label for="icon">图标class</label>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12 m6 s6">
						<i class="mdi-action-list prefix"></i> 
						<input id="orders" name="orders" type="text" class="validate" data-bind-json-path="/" data-bind-name="orders"> 
						<label for="orders">排序</label>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12 m6 l6">
						<i class="mdi-action-language prefix"></i> 
						<select id="type" name="type" class="browser-default validate" data-bind-json-path="/" data-bind-name="type"></select> 
						<label for="type"></label>
					</div>
					<div id="isDirDiv" class="hide">
						<div class="input-field col s12 m6 l6">
							<i class="mdi-editor-insert-drive-file prefix"></i> 
							<select id="isDirectory" name="isDirectory" class="browser-default validate" data-bind-json-path="/" data-bind-name="isDirectory"></select> 
							<label for="isDirectory"></label>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12">
						<i class="mdi-action-subject prefix"></i>
						<textarea id="descn" name="descn" class="materialize-textarea" length="120" data-bind-json-path="/" data-bind-name="descn"></textarea>
						<label for="descn">描述</label>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12">
						<button id="submitButton" class="btn waves-effect waves-light" type="submit">保存 <i class="mdi-content-send right"></i></button>
						<button class="btn waves-effect waves-light grey goBack" type="button"> 返回 <i class="mdi-action-history right"></i></button>
					</div>
				</div>
			</form>
		</div>
	</div>
	
	<div id="modalResourceSelect" class="modal" style="height:450px">
		<nav class="task-modal-nav">
			<div class="nav-wrapper">
            	<div class="col s12">
                	<ul>
                    	<li><a style="font-size:1.5rem">选择父资源</a></li>
                     	<li class="right"><a href="#!"><i class="modal-action modal-close  mdi-navigation-close"></i></a></li>
                  	</ul>
             	</div>
          	</div>
		</nav>
  		<div class="modal-content">
        	<div class="row">
				<form id="resourcePageQueryForm" class="col s12">
					<div class="row">
						<div class="input-field col s12 m4 l4">
							<label for="searchName">资源名</label> 
							<input id="searchName" name="name" type="text">
						</div>
						<div class="input-field col s12 m4 l4">
							<label for="searchType"></label>
							<select id="searchType" name="type" type="text" class="browser-default validate"></select>
						</div>
						<div class="input-field col s12 m4 l4 btn-group">
							<button id="chooseRes" class="btn btn-suit waves-effect waves-light right  right" type="button">选择</button>
							<button id="searchSubmit" class="btn btn-suit cyan waves-effect waves-light right" type="button">查询</button>
						</div>
					</div>
				</form>
			</div>
			<div class="row">
				<div id="page">
					<div id="list"></div>
					<div id="pagination"></div>
				</div>
			</div>
		</div>
	</div>

</div>
<!--end container-->


<script type="text/javascript">
$(document).ready(
		function() {
			$("#type,#searchType").initSelect({"category" : "RESOURCE_TYPE","emptyOptionTxt" : "-请选择资源类型-"});
			$("#isDirectory").initSelect({"category" : "RESOURCE_IS_DIR","emptyOptionTxt" : "-请选择是否是目录-"});
			
			$("#type").change(function() {
				if ($(this).val() == "0") {
					$("#isDirDiv").removeClass("hide");
				} else {
					$("#isDirDiv").addClass("hide");
					$("#isDirectory").val('');
				}
			});
			
			var id = $("#id").val();
			$.ajax({
				type : 'POST',
				url : getProjectName()+'/admin/resource/getResourceById',
				dataType : 'json',
				data : {"id":id},
				cache : false,
				error : function(XMLHttpRequest, status, thrownError) {
					Message.danger({message:"初始化页面信息失败！"});
				},
				success : function(data) {
					$("#form").bindData(data.data);
				}
			});

			$("#searchSubmit").click(function(){
				initPageData('admin/resource/listResource', $("#resourcePageQueryForm").serialize(), "page",1, 5);
			});

			$("#form").validate({
				submitHandler : function(form) {
					blockUI.block();
					$("#form").ajaxSubmit({
						type : "post",
						url : getProjectName() + "/admin/resource/update",
						dataType : "json",
						success : function(data) {
							blockUI.unblock();
							if (data.status == 1) {
								Message.info({ "message" : "保存成功", "time" : 3 });
								$("#submitButton").attr( "disabled", "disabled")
							} else {
								var info = data.info;
								if (isEmpty(info)) {
									info = "保存失败";
								}
								Message.danger({ "message" : info });
							}
						}
					});
				},
				rules : {
					name : {
						required : true,
						remote : {
							url : getProjectName() + "/admin/resource/checkOnly",
							type : "post",
							data : {
								fieldName : "name",
								fieldValue : function() {return $("#name").val();},
								excludeId:id
							},
						}
					},
					value : {
						required : true
					},
					type : {
						required : true,
					}
				},
				//For custom messages
				messages : {
					name : {
						required : "请输入资源名",
						remote : "资源名已存在"
					},
					value : {
						required : "请输入资源值"
					},
					type : {
						required : "请选择资源类型"
					}
				}
			});
		});

function initPageTable(queryResult) {
	var tbody = $('#' + queryResult.pageContentId).find("#list");
	var h = '<ul class="collection">';
	var data = queryResult.data.pageData;
	for (var i = 0; i < data.length; i++) {
		var r = data[i];

		h += '<li class="collection-item">'
			+ '<span>资源名称：<font  class="title">' + r.name + '</font></span>'
			+ '<p>资源值：' + getDataFromJson(r,"value") 
			+ '<input name="selectRe" type="radio" id="'+r.id+'" /><label class="secondary-content" for="'+r.id+'"></label>'
			+ '</p>'
			+ '</li>';
	}
	h += '</ul>';
	tbody.html(h);
	var pagination = queryResult.pagination;
	$('#' + queryResult.pageContentId).find("#pagination").html(pagination);

	$("#chooseRes").on('click', function() {
		var select = $("input[name=selectRe]:checked");
		if (select.length > 0) {
			var id = $(select).attr("id");
			var name = $(select).closest("li").find("font.title").text();
			$("#parentId").val(id);
			$("#parentName").val(name);
			$("#parentName").closest(".input-field").children("label").addClass("active");
		}
		$(".modal-close").click();
	});

}
</script>
