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
				<h5 class="breadcrumbs-title">角色管理</h5>
				<ol class="breadcrumbs">
					<li><a
						href="javascript:window.location.href='${pageContext.request.contextPath}/main';">首页</a></li>
					<li class="active">系统管理</li>
					<li class="active currentPage" data-init-page-url="admin/role/roleList">角色管理</li>
				</ol>
			</div>
		</div>
	</div>
</div>

<!--start container-->
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
								<div class="input-field col s12 m4 l4">
									<label for="roleName">角色名称</label>
									<input id="roleName" name="roleName" type="text" class="validate">
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="roleCode">角色编码</label>
									<input id="roleCode" name="roleCode" type="text" class="validate">
								</div>
								<div class="input-field col s12 m4 l4 btn-group">
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
               	<i class="title-icon mdi-notification-event-note" style=""></i>查询结果
               	<button class="btn-flat waves-effect waves-light  pink accent-2 action-more white-text btn-suit right" type="button"><i class="mdi-image-details"></i></button>
                <button id="create" class="btn-flat waves-effect waves-light  pink accent-2 white-text btn-suit right" type="button">新增 <i class="mdi-content-add right"></i></button>
	          	<div class="action-more-div cyan white-text btn-group hide">
	                <button class="btn-delete btn-flat waves-effect waves-light  pink accent-2 white-text btn-suit right" data-url="admin/user/delete" type="button">删除 <i class="mdi-content-remove right"></i></button>
	                <button class="btn-update btn-flat waves-effect waves-light  pink accent-2 white-text btn-suit right" type="button">修改 <i class="mdi-content-create right"></i></button>
	          	</div>
          	</div>
            <div id="page">
               	<table id="queryTable" class="table-fix col s12 m12 l12 bordered">
               		<thead>
               			<tr>
               				<td width="45px"><a href="javascript:;" class="tableCheckBoxSelectAll" data-action-attr="id">全选</a><a class="hide tableCheckBoxCancelSelect" href="javascript:;"  data-action-attr="id">取消</a></td>
               				<td>角色名称</td>
               				<td>角色编码</td>
               				<td>描述</td>
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
$(document).ready(function() {
	
	$("#create").click(function(){
		loadHTMLToContent('content', 'admin/role/roleCreate', 'GET');
	});
	
	$(".querySubmit").click(function() {
		initPageData('admin/role/listRole', $("#pageQueryForm").serialize(), "page", 1, 5);
	});
	
	$(".querySubmit").click();
});
	
function initPageTable(queryResult) {
	var tbody = $('#'+queryResult.pageContentId).find("table tbody");
	var h = "";
	var data = queryResult.data.pageData;
	for (var i = 0; i < data.length; i++) {
		var role = data[i];
		h +='<tr>'
		   +'<td><input data-action-attr="id" type="checkbox" id="id'+role.id+'" name="id" value="'+role.id+'" /><label for="id'+role.id+'"></label></td>'
		   +'<td>' + role.roleName + '</td>'
		   +'<td>' + role.roleCode + '</td>'
		   +'<td>' + getDataFromJson(role,"description") + '</td>'
		   +'<td>'
		  	  +'<ul id="dropdown'+role.id+'" class="dropdown-content">'
              +'<li><a href="#!" data-id="'+role.id+'" data-url="admin/role/'+role.id+'/roleUpdate" class="-text btn-update-inline">修改<i class="mdi-content-create"></i></a></li>'
              +'<li><a href="#!" data-id="'+role.id+'" data-url="admin/role/'+role.id+'" class="-text btn-view-inline">查看<i class="mdi-content-create"></i></a></li>'
              +'</ul>'
              +'<a class="dropdown-button btn-floating btn-flat waves-effect waves-light  pink accent-2 white-text" href="#!" data-activates="dropdown'+role.id+'"><i class="mdi-navigation-expand-more"></i></a>'
		   +'</td>'
		   +'</tr>';
	}
	tbody.html(h);
	
	$('.dropdown-button').initDynDropDown();
}
	
</script>
