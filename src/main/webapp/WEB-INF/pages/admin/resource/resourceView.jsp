<%@ page language="java" pageEncoding="UTF-8"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta charset="utf-8" />
</head>

	<div class="card-panel">
		<div class="row">
			<div class="breadcrumbs-title"><span>资源基本信息</span></div>
		</div>
		<div class="row">
			<form id="form" class="col s12 right-alert" method="post">
				<div class="row">
					<input id="id" name="id" value="${id}" class="hide" type="text">
					<div class="input-field col s12 m6 s6">
						<i class="mdi-action-account-circle prefix"></i> 
						<input id="name" name="name" type="text" data-bind-json-path="/" data-bind-name="name" class="validate" readonly> 
						<label for="name">资源名称</label>
					</div>
					<div class="input-field col s8 m6 s6">
						<i class="mdi-social-share prefix"></i> 
						<input id="parentName" name="parent.name" type="text" data-bind-json-path="/parent" data-bind-name="name" readonly> 
						<input id="parentId" name="parent.id" class="hide" type="text" class="validate" data-bind-json-path="/parent" data-bind-name="id"> 
						<label for="parentName">父资源</label>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12 m6 s6">
						<i class="mdi-hardware-keyboard prefix"></i> 
						<input id="value" name="value" type="text" class="validate" data-bind-json-path="/" data-bind-name="value" readonly> 
						<label for="value">资源值</label>
					</div>
					<div class="input-field col s12 m6 s6">
						<i class="mdi-image-image prefix"></i> 
						<input id="icon" name="icon" type="text" class="validate" data-bind-json-path="/" data-bind-name="icon" readonly> 
						<label for="icon">图标class</label>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12 m6 s6">
						<i class="mdi-action-list prefix"></i> 
						<input id="orders" name="orders" type="text" class="validate" data-bind-json-path="/" data-bind-name="orders" readonly> 
						<label for="orders">排序</label>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12 m6 l6">
						<i class="mdi-action-language prefix"></i> 
						<select id="type" name="type" class="browser-default validate" data-bind-json-path="/" data-bind-name="type" disabled="disabled"></select> 
						<label for="type"></label>
					</div>
					<div id="isDirDiv" class="hide">
						<div class="input-field col s12 m6 l6">
							<i class="mdi-editor-insert-drive-file prefix"></i> 
							<select id="isDirectory" name="isDirectory" class="browser-default validate" data-bind-json-path="/" data-bind-name="isDirectory" disabled="disabled"></select> 
							<label for="isDirectory"></label>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12">
						<i class="mdi-action-subject prefix"></i>
						<textarea id="descn" name="descn" class="materialize-textarea" length="120" data-bind-json-path="/" data-bind-name="descn" readonly></textarea>
						<label for="descn">描述</label>
					</div>
				</div>
			</form>
		</div>
	</div>
	
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
		});
</script>
