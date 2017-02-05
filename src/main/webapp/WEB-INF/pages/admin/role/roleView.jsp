<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta charset="utf-8" />
</head>

<div class="card-panel">
	<div class="row">
		<div class="breadcrumbs-title"><span>角色基本信息</span></div>
	</div>
	<div class="row">
		<form id="form" class="col s12 right-alert" method="post">
			<div class="row">
				<input id="id" name="id" value="${id}" class="hide" type="text">
				<div class="input-field col s12 m6 s6">
					<i class="mdi-action-account-circle prefix"></i> 
					<input id="roleName" name="roleName" type="text" data-bind-json-path="/" data-bind-name="roleName" class="validate" readonly> 
					<label for="roleName">角色名称</label>
				</div>
				<div class="input-field col s12 m6 s6">
					<i class="mdi-hardware-keyboard prefix"></i> 
					<input id="roleCode" name="roleCode" type="text" data-bind-json-path="/" data-bind-name="roleCode" class="validate" readonly> 
					<label for="roleCode">角色编码</label>
				</div>
			</div>
			<div class="row">
				<div class="input-field col s12">
					<i class="mdi-action-subject prefix"></i>
					<textarea id="description" name="description" data-bind-json-path="/" data-bind-name="description" class="materialize-textarea" readonly></textarea>
					<label for="description">描述</label>
				</div>
			</div>
		</form>
	</div>
</div>
    	
<script type="text/javascript">
$(document).ready(function() {
	var id = $("#id").val();
	$.ajax({
		type : 'POST',
		url : getProjectName()+'/admin/role/getRoleById',
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
