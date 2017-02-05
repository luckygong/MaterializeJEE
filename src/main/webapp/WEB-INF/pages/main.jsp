<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="msapplication-tap-highlight" content="no"/>
	<meta name="msapplication-TileColor" content="#00bcd4"/>
	<meta name="msapplication-TileImage" content="${pageContext.request.contextPath}/assets/images/favicon/mstile-144x144.png"/>
    <link rel="icon" href="${pageContext.request.contextPath}/assets/images/favicon/favicon-32x32.png" sizes="32x32"/>
	<link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/assets/images/favicon/apple-touch-icon-152x152.png"/>
    <title>主页</title>
	
	<link href="${pageContext.request.contextPath}/assets/css/materialize.css" rel="stylesheet" type="text/css" media="screen,projection"/>
	<link href="${pageContext.request.contextPath}/assets/css/style.min.css" rel="stylesheet" type="text/css" media="screen,projection"/>
	<link href="${pageContext.request.contextPath}/assets/css/custom/custom.min.css" rel="stylesheet" type="text/css" media="screen,projection"/>
	<link href="${pageContext.request.contextPath}/assets/js/plugins/perfect-scrollbar/perfect-scrollbar.css" rel="stylesheet" type="text/css" media="screen,projection"/>
	<link href="${pageContext.request.contextPath}/assets/js/plugins/jquery.nestable/nestable.css" rel="stylesheet" type="text/css" media="screen,projection"/>
	<link href="${pageContext.request.contextPath}/assets/js/plugins/sweetalert/sweetalert.css" rel="stylesheet" type="text/css" media="screen,projection"/>
	<link href="${pageContext.request.contextPath}/assets/js/plugins/dropify/css/dropify.min.css" rel="stylesheet" type="text/css" media="screen,projection"/>
	<link href="${pageContext.request.contextPath}/assets/js/plugins/material-datetime-picker/css/date-time-picker.css" rel="stylesheet" type="text/css" media="screen,projection"/>
	<link href="${pageContext.request.contextPath}/assets/css/my.css" rel="stylesheet" type="text/css" media="screen,projection"/>

	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/plugins/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/plugins/jquery-cookies/jquery.cookies.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/plugins/jquery.localize.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/plugins/jquery-validation/jquery.validate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/plugins/jquery-validation/additional-methods.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/plugins/jquery.nestable/jquery.nestable.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/plugins/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/plugins/jquery.lazyload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/plugins/jquery.blockUI.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/plugins/jquery.form.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/materialize.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/plugins/perfect-scrollbar/perfect-scrollbar.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/plugins/floatThead/jquery.floatThead.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/plugins/dropify/js/dropify.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/plugins/material-datetime-picker/js/moment.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/plugins/material-datetime-picker/js/lang/zh-cn.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/plugins/material-datetime-picker/js/date-time-picker.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/plugins.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/scripts/path.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/custom-script.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/language-cookis.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/scripts/block-ui.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/scripts/bind-data.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/scripts/prompt-ui.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/scripts/left-sidebar.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/scripts/page-plugin.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/assets/scripts/select-dropdown.js"></script>
</head>

<body>
    <!-- Start Page Loading -->
    <div id="loader-wrapper">
        <div id="loader"></div>        
        <div class="loader-section section-left"></div>
        <div class="loader-section section-right"></div>
    </div>

    <!-- START HEADER -->
    <header id="header" class="page-topbar">
    	<div class="navbar-fixed">
			<nav id="header-nav" class="navbar-color">
				<div class="nav-wrapper">
					<ul class="left">                    
						<li><h1 class="logo-wrapper"><a href="${pageContext.request.contextPath}/main" class="brand-logo darken-1"><img src="${pageContext.request.contextPath}/assets/images/materialize-logo.png" alt="materialize logo"></a></h1></li>
					</ul>
					<ul class="right">
						<li class="hide-on-med-and-down"><a href="javascript:void(0);" style="height: 100%" class="waves-effect waves-block waves-light translation-button"  data-activates="translation-dropdown"><img id="languageImg" src="${pageContext.request.contextPath}/assets/images/flag-icons/China.png" alt="中文" /></a></li>
						<li><a href="javascript:void(0);" class="waves-effect waves-block waves-light notification-button" data-activates="notifications-dropdown"><i class="mdi-social-notifications"><small class="notification-badge">5</small></i></a></li>                    
						<li class="hide-on-med-and-down"><a href="javascript:void(0);" data-activates="chat-out" class="waves-effect waves-block waves-light chat-collapse"><i class="mdi-communication-chat"></i></a></li>
					</ul>
					<!-- 语言选择下拉 -->
					<ul id="translation-dropdown" class="dropdown-content">
						<li><a href="javascript:;" onclick="javascript:chgLang('zh');"><img src="${pageContext.request.contextPath}/assets/images/flag-icons/China.png" alt="Chinese" />  <span class="language-select">中文</span></a></li>
						<li><a href="javascript:;" onclick="javascript:chgLang('en');"><img src="${pageContext.request.contextPath}/assets/images/flag-icons/United-States.png" alt="English" />  <span class="language-select">English</span></a></li>
					</ul>
					<!-- 通知下拉 -->
					<ul id="notifications-dropdown" class="dropdown-content">
						<li>
							<h5><b><span class="data-localize" data-localize="sys.notifications"></span></b> <span class="new badge">5</span></h5>
						</li>
						<li class="divider"></li>
						<li>
							<a href="#!"><i class="mdi-action-add-shopping-cart"></i><span  class="data-localize" data-localize="sys.receiveNotification"></span></a>
							<time class="media-meta" datetime="2015-06-12T20:50:48+08:00">2 hours ago</time>
						</li>
					</ul>
				</div>
			</nav>
		</div>
    </header>

    <!-- START MAIN -->
    <div id="main">
        <!-- START WRAPPER -->
        <div class="wrapper">

            <aside id="left-sidebar-nav"></aside>
            <aside id="right-sidebar-nav">
            	<ul id="chat-out" class="side-nav rightside-navigation hide-on-med-and-down">
					<li class="li-hover">
						<a href="#" data-activates="chat-out" class="chat-close-collapse right"><i class="mdi-navigation-close"></i></a>
						<div class="row"></div>
					</li>
					<li class="li-hover">
						<ul class="chat-collapsible" data-collapsible="expandable">
							<li>
								<div class="collapsible-header teal white-text active"><i class="mdi-social-whatshot"></i>Recent Activity</div>
								<div class="collapsible-body recent-activity">
									<div class="recent-activity-list chat-out-list row">
										<div class="col s3 recent-activity-list-icon"><i class="mdi-action-add-shopping-cart"></i></div>
										<div class="col s9 recent-activity-list-text">
											<a href="#">just now</a>
											<p>Jim Doe Purchased new equipments for zonal office.</p>
										</div>
									</div>
								</div>
							</li>
							<li>
								<div class="collapsible-header red white-text"><i class="mdi-action-stars"></i>Favorite Associates</div>
								<div class="collapsible-body favorite-associates">
									<div class="favorite-associate-list chat-out-list row">
										<div class="col s4"><img src="${pageContext.request.contextPath}/assets/images/avatar.jpg" alt="" class="circle responsive-img online-user valign profile-image"></div>
										<div class="col s8">
											<p>Eileen Sideways</p>
											<p class="place">Los Angeles, CA</p>
										</div>
									</div>
								</div>
							</li>
						</ul>
					</li>
				</ul>
            </aside>

            <!-- //////////////////////////////////////////////////////////////////////////// -->

            <section id="content"></section>
			
			<!-- Floating Action Button -->
            <div id="floatButton" class="fixed-action-btn click-to-toggle hide-on-med-and-down" style="bottom: 50px; right: 19px;">
            	<a class="btn-floating btn-large"><i class="mdi-action-stars"></i></a>
				<ul>
					<li><a href="javascript:;" class="btn-floating red"><i class="large mdi-communication-live-help"></i></a></li>
					<li><a href="javascript:;" class="btn-floating yellow darken-1"><i class="large mdi-device-now-widgets"></i></a></li>
					<li><a href="javascript:;" class="btn-floating green"><i class="large mdi-editor-insert-invitation"></i></a></li>
					<li><a href="javascript:;" class="btn-floating blue"><i class="large mdi-communication-email"></i></a></li>
				</ul>
            </div>
            <!-- Floating Action Button -->
        </div>
        <!-- END WRAPPER -->

    </div>
    <!-- END MAIN -->
    
    <!-- START FOOTER -->
    <footer id="page-footer" class="page-footer">
   		<div class="footer-copyright">
			<div class="container center">
			Copyright © 2016 By 
			<a class="grey-text text-lighten-4" href="http://www.cnblogs.com/zhaosk/" target="_blank">zhaosk</a> All rights reserved.
			</div>
		</div>
    </footer>

    <!-- //////////////////////////////////////////////////////////////////////////// -->
    <script type="text/javascript">
    $(window).load(function() {
    	loadLanguageIcon("");
    	loadHTMLToContent('content', 'home');
    });
    </script>
</body>
</html>