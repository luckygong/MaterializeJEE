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
				<h5 class="breadcrumbs-title">角色管理</h5>
				<ol class="breadcrumbs">
					<li class="active">首页</li>
					<li class="active">系统管理</li>
					<li class="active">角色管理</li>
					<li class="active">修改角色</li>
				</ol>
			</div>
		</div>
	</div>
</div>

<div class="container">
	<div class="card-panel">
		<div class="breadcrumbs-title"><span>角色基本信息</span></div>
		<div class="row">
			<form id="form" class="col s12 right-alert" method="post">
				<div class="row">
					<input id="id" name="id" value="${id}" class="hide" type="text">
					<div class="input-field col s12 m6 s6">
						<i class="mdi-action-account-circle prefix"></i> 
						<input id="roleName" name="roleName" type="text" data-bind-json-path="/" data-bind-name="roleName" class="validate"> 
						<label for="roleName">角色名称</label>
					</div>
					<div class="input-field col s12 m6 s6">
						<i class="mdi-hardware-keyboard prefix"></i> 
						<input id="roleCode" name="roleCode" type="text" data-bind-json-path="/" data-bind-name="roleCode" class="validate"> 
						<label for="roleCode">角色编码</label>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12">
						<i class="mdi-action-subject prefix"></i>
						<textarea id="description" name="description" data-bind-json-path="/" data-bind-name="description" class="materialize-textarea"></textarea>
						<label for="description">描述</label>
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
	
</div>
<!--end container-->


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

	$("#form").validate({
		submitHandler : function(form) {
			blockUI.block();
			$("#form").ajaxSubmit({
				type : "post",
				url : getProjectName() + "/admin/role/update",
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
			roleName : {
				required : true,
				remote : {
					url : getProjectName() + "/admin/role/checkOnly",
					type : "post",
					data : {
						fieldName : "roleName",
						fieldValue : function() {
							return $("#roleName").val();
						},
						excludeId:id
					}
				}
			},
			roleCode : {
				required : true,
				remote : {
					url : getProjectName() + "/admin/role/checkOnly",
					type : "post",
					data : {
						fieldName : "roleCode",
						fieldValue : function() {
							return $("#roleCode").val();
						},
						excludeId:id
					}
				}
			}
		},
		//For custom messages
		messages : {
			roleName : {
				required : "请输入角色名称",
				remote : "角色名称已存在"
			},
			roleCode : {
				required : "请输入角色编码",
				remote : "角色编码已存在"
			}
		}
	});
});

</script>
