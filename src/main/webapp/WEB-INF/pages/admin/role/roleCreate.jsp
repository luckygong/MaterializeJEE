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
					<li><a
						href="javascript:window.location.href='${pageContext.request.contextPath}/main';">首页</a></li>
					<li class="active">系统管理</li>
					<li class="active">角色管理</li>
					<li class="active">新增角色</li>
				</ol>
			</div>
		</div>
	</div>
</div>

<div class="container">
	<div class="card-panel">
		<form id="form" class="col s12 right-alert" method="post">
			<div class="row">
				<div class="breadcrumbs-title"><span>角色基本信息</span></div>
			</div>
			<div class="row">
				<div class="row">
					<div class="input-field col s12 m6 s6">
						<i class="mdi-action-account-circle prefix"></i> 
						<input id="roleName" name="roleName" type="text" class="validate"> 
						<label for="roleName">角色名称</label>
					</div>
					<div class="input-field col s12 m6 s6">
						<i class="mdi-hardware-keyboard prefix"></i> 
						<input id="roleCode" name="roleCode" type="text" class="validate"> 
						<label for="roleCode">角色编码</label>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12">
						<i class="mdi-action-subject prefix"></i>
						<textarea id="description" name="description" class="materialize-textarea"></textarea>
						<label for="description">描述</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="breadcrumbs-title"><span>权限信息</span></div>
			</div>
			<div class="row">
				<div class="col s12">
					<div id="menuTreeDiv" style="height:200px;overflow: hidden;"></div>
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
<!--end container-->


<script type="text/javascript">
$(document).ready(function() {
	$("#menuTreeDiv").initZTree({'submitName':'resourceIds','ztree':{'url':getProjectName() + "/admin/resource/loadResourseZTree",'checkable':true}});
	
	$("#form").validate({
		submitHandler : function(form) {
			blockUI.block();
			$("#form").ajaxSubmit({
				type : "post",
				url : getProjectName() + "/admin/role/save",
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
						}
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
						}
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
