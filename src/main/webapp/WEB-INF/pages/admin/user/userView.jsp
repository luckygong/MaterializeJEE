<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta charset="utf-8" />
</head>

<div class="card-panel">
	<div class="row">
		<div class="breadcrumbs-title"><span>用户基本信息</span></div>
	</div>
	<div class="row">
		<form id="form" class="col s12" method="post" enctype="multipart/form-data">
       		<div class="row">
            	<input id="id" name="id" data-bind-json-path="/"  value="${id}" class="hide" type="text">
                <div class="input-field col s12 m6 l6">
                 	<i class="mdi-maps-local-laundry-service prefix"></i>
					<select id="userType" name="userType" data-bind-json-path="/" data-bind-name="userType" class="browser-default validate" disabled></select>
					<label for="userType"></label>
				</div>
				<div id="refIdDiv" class="hide">
              		<div class="input-field col s12 m6 s6">
                		<i class="mdi-social-share prefix"></i>
                   		<input id="name" name="refName" data-bind-json-path="/employee" data-bind-name="name"  type="text" readonly>
                     	<label for="refId">关联员工</label>
               		</div>
            	</div>
      		</div>
       		<div class="row">
            	<div class="input-field col s12 m6 s6">
                 	<i class="mdi-action-account-circle prefix"></i>
                  	<input id="username" name="username"  data-bind-json-path="/" data-bind-name="username" type="text" class="validate" readonly>
                   	<label for="username">用户名</label>
        		</div>
              	<div class="input-field col s12 m6 s6">
                  	<i class="mdi-action-lock-outline prefix"></i>
                 	<input id="password" name="password" type="text" class="validate" readonly>
                   	<label for="password">密码</label>
               	</div>
       		</div>
      		<div class="row">
              	<div class="input-field col s12 m6 s6">
                  	<i class="mdi-action-visibility-off prefix"></i>
                  	<input id="nikeName" name="nikeName"  data-bind-json-path="/" data-bind-name="nikeName" type="text" class="validate" readonly>
               		<label for="nikeName">昵称</label>
              	</div>
             	<div class="input-field col s12 m6 s6">
              		<i class="mdi-action-visibility prefix"></i>
                  	<input id="realName" name="realName"  data-bind-json-path="/" data-bind-name="realName" type="text" class="validate" readonly>
                  	<label for="realName">真实姓名</label>
             	</div>
        	</div>
        	<div class="row">
              	<div class="input-field col s12 m6 s6">
                   	<i class="mdi-hardware-phone-iphone prefix"></i>
                 	<input id="phone" name="phone"  data-bind-json-path="/" data-bind-name="phone" type="text" class="validate" readonly>
                  	<label for="phone">手机</label>
               	</div>
              	<div class="input-field col s12 m6 s6">
                   	<i class="mdi-communication-email prefix"></i>
               		<input id="email" name="email"  data-bind-json-path="/" data-bind-name="email" type="text" class="validate" readonly>
                 	<label for="email">邮箱</label>
               	</div>
           	</div>
			<div class="row">
               	<div class="input-field col s12 m6 l6">
                	<i class="mdi-image-nature-people prefix"></i>
					<select id="sex" name="sex"  data-bind-json-path="/" data-bind-name="sex" class="browser-default validate" disabled></select>
					<label for="sex"></label>
				</div>
            </div>
      		<div class="row">
			  	<div class="col s12">
			      	<p><i class="mdi-editor-insert-photo prefix" style="font-size:2rem;width: 3rem"></i> <span>头像</span></p>
			      	<img id="avatar" alt="" class="lazy">
			 	</div>
          	</div>
    	</form>
 	</div>
</div>
    	
<script type="text/javascript">
	$(document).ready(function() {
		$("#sex").initSelect({"category":"SEX","emptyOptionTxt":"-请选择性别-"});
		$("#userType").initSelect({"category":"SYS_USER_TYPE","emptyOptionTxt":"-请选择用户类型-"});
		
		$("#userType").change(function(){
			if($(this).val()=="3"){
				$("#refIdDiv").removeClass("hide");
			}else{
				$("#refIdDiv").addClass("hide");
				$("#refId").val('');
				$("#refName").val('');
			}
		});
		
		var id = $("#id").val();
		$.ajax({
			type : 'POST',
			url : getProjectName()+'/admin/user/getUserById',
			dataType : 'json',
			data : {"id":id},
			cache : false,
			error : function(XMLHttpRequest, status, thrownError) {
				Message.danger({message:"初始化页面信息失败！"});
			},
			success : function(data) {
				$("#form").bindData(data.data);
				$("#avatar").attr("data-original",getFtpWebPath()+'/'+data.data.avatar);
				setTimeout("$('img.lazy').lazyload();",400); 
			}
		});
		
	});
	
</script>
