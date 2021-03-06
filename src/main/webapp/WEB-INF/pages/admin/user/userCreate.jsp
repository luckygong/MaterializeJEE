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
				<h5 class="breadcrumbs-title">用户管理</h5>
				<ol class="breadcrumbs">
					<li class="active">首页</li>
					<li class="active">系统管理</li>
					<li class="active">用户管理</li>
					<li class="active">新增用户</li>
				</ol>
			</div>
		</div>
	</div>
</div>
				
<!--start container-->
<div class="container">
	<div class="card-panel">
    	<div class="breadcrumbs-title"><span>用户基本信息</span></div>
     	<div class="row">
        	<form id="form" class="col s12 right-alert" method="post" enctype="multipart/form-data">
            	<div class="row">
                	<div class="input-field col s12 m6 l6">
	                   	<i class="mdi-maps-local-laundry-service prefix"></i>
						<select id="userType" name="userType" class="browser-default validate"></select>
						<label for="userType"></label>
					</div>
					<div id="refIdDiv" class="hide">
                    	<div class="input-field col s8 m4 s4">
                    		<i class="mdi-social-share prefix"></i>
                      		<input id="refName" name="refName" type="text" disabled>
                      		<input id="refId" name="refId" class="hide" type="text" class="validate">
                      		<label for="refId">关联员工</label>
                    	</div>
						<div class="input-field col s4 m2 s2">
                      		<div id="userSelect" class="btn btn-suit modal-trigger" href="#modalUserSelect"><span>选择</span></div>
                   		</div>
                	</div>
               	</div>
              	<div class="row">
                	<div class="input-field col s12 m6 s6">
                		<i class="mdi-action-account-circle prefix"></i>
                  		<input id="username" name="username" type="text" class="validate">
                  		<label for="username">用户名</label>
               		</div>
                	<div class="input-field col s12 m6 s6">
                		<i class="mdi-action-lock-outline prefix"></i>
                  		<input id="password" name="password" type="text" class="validate">
                  		<label for="password">密码</label>
               		</div>
              	</div>
          		<div class="row">
                 	<div class="input-field col s12 m6 s6">
                   		<i class="mdi-action-visibility-off prefix"></i>
                     	<input id="nikeName" name="nikeName" type="text" class="validate">
                     	<label for="nikeName">昵称</label>
                  	</div>
             		<div class="input-field col s12 m6 s6">
                   		<i class="mdi-action-visibility prefix"></i>
                     	<input id="realName" name="realName" type="text" class="validate">
                     	<label for="realName">真实姓名</label>
                 	</div>
             	</div>
             	<div class="row">
                  	<div class="input-field col s12 m6 s6">
                   		<i class="mdi-hardware-phone-iphone prefix"></i>
                     	<input id="phone" name="phone" type="text" class="validate">
                     	<label for="phone">手机</label>
                	</div>
                  	<div class="input-field col s12 m6 s6">
                   		<i class="mdi-communication-email prefix"></i>
                     	<input id="email" name="email" type="text" class="validate">
                     	<label for="email">邮箱</label>
                  	</div>
            	</div>
               	<div class="row">
                   	<div class="input-field col s12 m6 l6">
                 		<i class="mdi-image-nature-people prefix"></i>
						<select id="sex" name="sex" class="browser-default validate"></select>
						<label for="sex"></label>
					</div>
             	</div>
                <div class="row">
	              	<div class="col s12">
	                  	<p><i class="mdi-editor-insert-photo prefix" style="font-size:2rem;width: 3rem"></i> <span>头像（文件2MB以内）</span></p>
	                  	<input type="file" id="file" name="file" class="dropify" data-max-file-size="2M" />
	              	</div>
              	</div>
              	<div class="row">
                 	<div class="input-field col s12">
                     	<button id="submitButton" class="btn waves-effect waves-light" type="submit">保存<i class="mdi-content-send"></i></button>
                      	<button class="btn waves-effect waves-light grey goBack" type="button">返回<i class="mdi-action-history"></i></button>
                 	</div>
              	</div>
          	</form>
       	</div>
  	</div>
    	
	<div id="modalUserSelect" class="modal" style="height:450px">
		<nav class="task-modal-nav">
			<div class="nav-wrapper">
           		<div class="col s12">
                 	<ul>
                    	<li><a>选择员工</a></li>
                    	<li class="right"><a href="#!"><i class="modal-action modal-close  mdi-navigation-close"></i></a></li>
                	</ul>
           		</div>
     		</div>
  		</nav>
  		<div class="modal-content">
       		<div class="row">
				<form id="userPageQueryForm" class="col s12">
					<div class="row">
						<div class="input-field col s12 m4 l4">
							<label for="searchName">姓名</label>
							<input id="searchName" name="name" type="text">
						</div>
						<div class="input-field col s12 m4 l4">
							<label for="searchPhone">手机</label>
							<input id="searchPhone" name="phone" type="text">
						</div>
						<div class="input-field col s12 m4 l4">
							<button id="searchSubmit" class="btn cyan waves-effect waves-light" type="button">
								查询
							</button>
							<button id="chooseEm" class="btn waves-effect waves-light right" type="button">
								选择
							</button>
						</div>
					</div>
				</form>
			</div>
			<div class="divider"></div>
           	<div id="page">
             	<div id="list"></div>
            	<div id="pagination"></div>
         	</div>
     	</div>                
	</div>
</div>
<!--end container-->

<script type="text/javascript">
	$(document).ready(function() {
		$("#userType").initSelect({"category":"SYS_USER_TYPE","emptyOptionTxt":"-请选择用户类型-"});
		$("#sex").initSelect({"category":"SEX","emptyOptionTxt":"-请选择性别-"});
		
		$("#userType").change(function(){
			if($(this).val()=="3"){
				$("#refIdDiv").removeClass("hide");
			}else{
				$("#refIdDiv").addClass("hide");
				$("#refId").val('');
				$("#refName").val('');
			}
		});
		
		$("#searchSubmit").click(function(){
			$(".collapsible-header").click();
			initPageData('admin/employee/employeeSearch', $("#userPageQueryForm").serialize(), "page", 1, 5);
		});
		
		$("#form").validate({
			submitHandler:function(form){
				if($("#userType").val()=="3" && isEmpty($("#refId").val())){
					Message.warn({"message":"请选择关联员工","time":3});
					return;
				}
				blockUI.block();
				$("#form").ajaxSubmit({
					type: "post",
					url: getProjectName()+"/admin/user/save",
					dataType: "json",
					success: function(data){
				  		blockUI.unblock();
						if(data.status==1){
							Message.info({"message":"保存成功","time":3});
							$("#submitButton").attr("disabled","disabled")
						}else{
							var info = data.info;
							if(isEmpty(info)){
								info = "保存失败";
							}
							Message.danger({"message":info});
						} 
				     }
				 });
	        },    
	        rules: {
	        	username: {
	                required: true,
	                remote: {
                        url: getProjectName()+"/admin/user/checkOnly",
                        type: "post",
                        data: {
                            fieldName: "username",
                            fieldValue: function () { return $("#username").val(); }
                        }
                    }
	            },
	            realName: {
	                required: true
	            },
	            password: {
					required: true,
					minlength:6
				},
	            phone: {
					required: true,
					number:true,
					maxlength:11,
					remote: {
                        url: getProjectName()+"/admin/user/checkOnly",
                        type: "post",
                        data: {
                            fieldName: "phone",
                            fieldValue: function () { return $("#phone").val(); }
                        }
                    }
				},
	            email: {
	            	email: true
				},
				sex: {
					required: true
				},
				userType: {
					required: true
				},
				refId: {
					required: true
				}
	        },
	        //For custom messages
	        messages: {
	            password:{
	                required: "请输入密码",
	                minlength: "密码至少6位"
	            },
	            phone:{
					required: "请输入手机号",
	            	number:"请输入有效手机号",
					maxlength:"手机号最长11位",
					remote: "手机号已存在"
	            },
	            username : {
	                required: "请输入用户名",
	                remote: "用户名已存在"
	            },
	            realName : {
	                required: "请输入真实姓名"
	            },
	            email : {
	            	email: "请输入有效邮箱"
	            },
	            sex : {
	                required: "请输入性别"
	            },
	            userType : {
	                required: "请输入用户类型"
	            },
	            refId : {
	                required: "请选择关联用户"
	            }
	        }
	     });
		
	});
	
	function initPageTable(queryResult) {
		var tbody = $('#'+queryResult.pageContentId).find("#list");
		var h = '<ul class="collection">';
		var data = queryResult.data.pageData;
		for (var i = 0; i < data.length; i++) {
			var em = data[i];
			
			h+='<li class="collection-item avatar">'
			+'<img data-original="'+getFtpWebPath()+'/'+em.avatar+'" alt="" class="circle lazy">'
			+'<span class="title">'+em.name+'</span>'
			+'<p>'+em.phone
			+'</p>'
			+'<input name="selectEm" type="radio" id="'+em.id+'" /><label class="secondary-content" for="'+em.id+'"></label>'
			+'</li>';
		}
		h+='</ul>';
		tbody.html(h);
		var pagination = queryResult.pagination;
		$('#'+queryResult.pageContentId).find("#pagination").html(pagination);
		
		$("#chooseEm").on('click',function(){
			var select = $("input[name=selectEm]:checked");
			if(select.length>0){
				var id = $(select).attr("id");
				var name = $(select).closest("li").children("span").text();
				$("#refId").val(id);
				$("#refName").val(name);
				$("#refName").closest(".input-field").children("label").addClass("active");
				$(".modal-close").click();
			}
		});
		setTimeout("$('img.lazy').lazyload();",400); 
	}
</script>
