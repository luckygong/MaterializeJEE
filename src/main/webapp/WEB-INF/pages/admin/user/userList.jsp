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
					<li class="active currentPage" data-init-page-url="admin/user/userList">用户管理</li>
				</ol>
			</div>
		</div>
	</div>
</div>

<!--start container-->
<div class="container">

	<div class="btn-card">
		<div class="card row">
           	<div class="card-action cyan white-text btn-group">
               	<i class="title-icon mdi-action-list"></i>
               	<button class="btn waves-effect waves-light  pink accent-2 action-more white-text btn-suit right" type="button">更多<i class="mdi-image-details"></i></button>
                <button class="btn waves-effect waves-light  pink accent-2 white-text btn-suit right create" type="button">新增 <i class="mdi-content-add"></i></button>
               	<button class="btn waves-effect waves-light  pink accent-2 white-text btn-suit right query-more" type="button"><i class="mdi-action-search"></i></button>
	          	<div class="action-more-div white-text btn-group hide">
	                <button class="btn-delete btn waves-effect waves-light  pink accent-2 white-text btn-suit right" data-url="admin/user/delete" type="button">删除 <i class="mdi-content-remove"></i></button>
	                <button class="btn-update btn waves-effect waves-light  pink accent-2 white-text btn-suit right" type="button">修改 <i class="mdi-content-create"></i></button>
	          	</div>
	          	<div class="query-div hide">
	               <div class="row">
						<form id="pageQueryForm" class="col s12" style="padding-top: 15px;">
							<div class="row">
								<div class="input-field col s12 m4 l3">
									<label for="username">用户名</label>
									<input id="username" name="username" type="text" class="validate">
								</div>
								<div class="input-field col s12 m4 l3">
									<label for="realname">姓名</label>
									<input id="realname" name="realName" type="text" class="validate">
								</div>
								<div class="input-field col s12 m4 l3">
									<label for="phone">手机</label>
									<input id="phone" name="phone" type="text" class="validate">
								</div>
								<div class="input-field col s12 m12 l3 btn-group">
									<button class="btn btn-suit yellow darken-4 white-text waves-effect waves-light right resetForm" type="button">
										重置 <i class="mdi-av-replay right"></i>
									</button>
									<button class="btn btn-suit pink accent-2 white-text waves-effect waves-light right querySubmit" type="button">
										查询 <i class="mdi-content-send right"></i>
									</button>
								</div>
							</div>
						</form>
					</div>
	          	</div>
          	</div>
            <div id="page">
               	<table id="queryTable" class="table-fix col s12 m12 l12 bordered">
               		<thead>
               			<tr>
               				<td width="45px"><a href="javascript:;" class="tableCheckBoxSelectAll" data-action-attr="id">全选</a><a class="hide tableCheckBoxCancelSelect" href="javascript:;"  data-action-attr="id">取消</a></td>
               				<td>头像</td>
               				<td>用户名</td>
               				<td>姓名</td>
               				<td>性别</td>
               				<td>手机号</td>
               				<td>类型</td>
               				<td width="50px">操作</td>
           				</tr>
           			</thead>
               		<tbody></tbody>
               	</table>
		    	<div id="pagination"></div>
           	</div>
   		</div>
	</div>
</div>
<!--end container-->

<script type="text/javascript">
var sexMap ={};
var userTypeMap ={};
$(document).ready(function() {
	sexMap = initCategoryMap({"category":"SEX"});
	userTypeMap = initCategoryMap({"category":"SYS_USER_TYPE"});
	
	$(".create").click(function(){
		loadHTMLToContent('content', 'admin/user/userCreate', 'GET');
	});
	
	$(".querySubmit").click(function() {
		initPageData('admin/user/listUser', $("#pageQueryForm").serialize(), "page", 1, 5);
	});
	
	$(".querySubmit").click();
});
	
function initPageTable(queryResult) {
	var tbody = $('#'+queryResult.pageContentId).find("table tbody");
	var h = "";
	var data = queryResult.data.pageData;
	for (var i = 0; i < data.length; i++) {
		var user = data[i];
		h +='<tr>'
		   +'<td><input data-action-attr="id" type="checkbox" id="id'+user.id+'" name="id" value="'+user.id+'" /><label for="id'+user.id+'"></label></td>'
		   +'<td><img data-original="'+getFtpWebPath()+'/'+user.avatar+'" alt="" class="circle lazy"></td>'
		   +'<td>' + user.username + '</td>'
		   +'<td>' + user.realName + '</td>'
		   +'<td>' + sexMap[user.sex] + '</td>'
		   +'<td>' + user.phone + '</td>'
		   +'<td>' + userTypeMap[user.userType] + '</td>'
		   +'<td>'
		  	  +'<ul id="dropdown'+user.id+'" class="dropdown-content">'
              +'<li><a href="#!" data-id="'+user.id+'" data-url="admin/user/'+user.id+'/userUpdate" class="-text btn-update-inline">修改<i class="mdi-content-create"></i></a></li>'
              +'<li><a href="#!" data-id="'+user.id+'" data-url="admin/user/'+user.id+'" class="-text btn-view-inline">查看<i class="mdi-content-create"></i></a></li>'
              +'</ul>'
              +'<a class="dropdown-button btn-floating btn-flat waves-effect waves-light  pink accent-2 white-text" href="#!" data-activates="dropdown'+user.id+'"><i class="mdi-navigation-expand-more"></i></a>'
		   +'</td>'
		   +'</tr>';
	}
	tbody.html(h);
	
	$('.dropdown-button').initDynDropDown();
	setTimeout("$('img.lazy').lazyload();",400); 
}
	
</script>
