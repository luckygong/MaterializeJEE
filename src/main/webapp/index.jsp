<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="msapplication-tap-highlight" content="no">
    <title>首页</title>

    <!-- Favicons-->
    <link rel="icon" href="assets/images/favicon/favicon-32x32.png" sizes="32x32">
    <!-- Favicons-->
    <link rel="apple-touch-icon-precomposed" href="assets/images/favicon/apple-touch-icon-152x152.png">
    <!-- For iPhone -->
    <meta name="msapplication-TileColor" content="#00bcd4">
    <meta name="msapplication-TileImage" content="assets/images/favicon/mstile-144x144.png">
    <!-- For Windows Phone -->


    <!-- CORE CSS-->    
    <link href="assets/css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection">
    <link href="assets/css/style.min.css" type="text/css" rel="stylesheet" media="screen,projection">
    <!-- CSS style Horizontal Nav-->    
    <link href="assets/css/layouts/style-horizontal.css" type="text/css" rel="stylesheet" media="screen,projection">
    <!-- Custome CSS-->    
    <link href="assets/css/custom/custom.min.css" type="text/css" rel="stylesheet" media="screen,projection">
    


    <!-- INCLUDED PLUGIN CSS ON THIS PAGE -->
    <link href="assets/js/plugins/perfect-scrollbar/perfect-scrollbar.css" type="text/css" rel="stylesheet" media="screen,projection">
    <link href="assets/js/plugins/jvectormap/jquery-jvectormap.css" type="text/css" rel="stylesheet" media="screen,projection">
    <link href="assets/js/plugins/chartist-js/chartist.min.css" type="text/css" rel="stylesheet" media="screen,projection">
	<link type="text/css" href="assets/js/plugins/vertical-timeline/css/component.css"  rel="stylesheet" media="screen,projection">
	<link href="assets/css/my.css" type="text/css" rel="stylesheet" media="screen,projection">

</head>

<body id="layouts-horizontal">
    <!-- Start Page Loading -->
    <div id="loader-wrapper">
        <div id="loader"></div>        
        <div class="loader-section section-left"></div>
        <div class="loader-section section-right"></div>
    </div>
    <!-- End Page Loading -->

    <!-- START HEADER -->
    <header id="header" class="page-topbar">
        <!-- start header nav-->
        <nav class="navbar-color">
       		<div class="nav-wrapper">
           		<ul class="left" style="padding-top:15px">                      
           			<li><h1 class="logo-wrapper"><a href="index.html" class="brand-logo darken-1" style="padding-top: 0px;"><span>Materialize</span></h1></li>
             	</ul>
             	<ul class="right hide-on-med-and-down">
                 	<li><a href="javascript:void(0);" class="waves-effect waves-block waves-light">首页</a></li>
                 	<li><a href="javascript:void(0);" class="waves-effect waves-block waves-light">简介</a></li>                        
                 	<li><a href="javascript:void(0);" class="waves-effect waves-block waves-light">产品中心</a></li>
                 	<li><a href="javascript:void(0);" class="waves-effect waves-block waves-light">联系我们</a></li>
             	</ul>
         	</div>
     	</nav>
     	<div id="slider" class="slider">
	    	<ul class="slides">
	       		<li>
	         		<img src="assets/images/gallary/1.jpg" alt="img-1">
		         	<div class="caption center-align">
		           		<h3>This is our big Tagline!</h3>
		           		<h5 class="light grey-text text-lighten-3">Here's our small slogan.</h5>
		         	</div>
	       		</li>
	       		<li>
	         		<img src="assets/images/gallary/2.jpg" alt="img-2">
	         		<div class="caption left-align">
	           			<h3>Left Aligned Caption</h3>
	           			<h5 class="light grey-text text-lighten-3">Here's our small slogan.</h5>
	         		</div>
	       		</li>
	       		<li>
	         		<img src="assets/images/gallary/3.jpg" alt="img-3">
	         		<div class="caption right-align">
	           			<h3>Right Aligned Caption</h3>
	           			<h5 class="light grey-text text-lighten-3">Here's our small slogan.</h5>
	         		</div>
	       		</li>
	       		<li>
	         		<img src="assets/images/gallary/4.jpg" alt="img-4">
	         		<div class="caption center-align">
	           			<h3>This is our big Tagline!</h3>
	           			<h5 class="light grey-text text-lighten-3">Here's our small slogan.</h5>
	         		</div>
	       		</li>
	     	</ul>
	   	</div>
    </header>
    <!-- END HEADER -->

    <!-- START MAIN -->
    <div id="main">
        <!-- START WRAPPER -->
        <div class="wrapper">

            <!-- START LEFT SIDEBAR NAV-->
            <aside id="left-sidebar-nav hide-on-large-only">
                <ul id="slide-out" class="side-nav leftside-navigation ">
	                <li><a href="javascript:void(0);" class="waves-effect waves-block waves-light">首页</a></li>
	                <li><a href="javascript:void(0);" class="waves-effect waves-block waves-light">简介</a></li>                        
	                <li><a href="javascript:void(0);" class="waves-effect waves-block waves-light">产品中心</a></li>
	                <li><a href="javascript:void(0);" class="waves-effect waves-block waves-light">联系我们</a></li>
	            </ul>
                <a href="#" data-activates="slide-out" class="sidebar-collapse btn-floating btn-medium waves-effect waves-light hide-on-large-only cyan"><i class="mdi-navigation-menu"></i></a>
            </aside>
            <!-- END LEFT SIDEBAR NAV-->

            <!-- START CONTENT -->
            <section id="content" style="margin-top:10px">
				<div class="container">
            		<div id="breadcrumbs-wrapper">
						<div class="row">
							<div class="col s12 m12 l12">
								<h5 class="breadcrumbs-title">首页</h5>
								<ol class="breadcrumbs"></ol>
							</div>
							<div class="col s4 m4 l3 center">
	                          <div class="btn-floating btn-large waves-effect waves-light modal-trigger" href="#timelineModal"><i class="mdi-content-add"></i></div>
	                          <p>timeline</p>
	                        </div>
							<div class="col s4 m4 l3 center">
	                          <a class="btn-floating btn-large waves-effect waves-light "><i class="mdi-content-add"></i></a>
	                          <p>Raised</p>
	                        </div>
							<div class="col s4 m4 l3 center">
	                          <a class="btn-floating btn-large waves-effect waves-light "><i class="mdi-content-add"></i></a>
	                          <p>Raised</p>
	                        </div>
						</div>
					</div>
					
					<div class="row section">
	                    <div class="col s12 m4 l4">
	                    	<ul id="task-card" class="collection with-header">
			                    <li class="collection-header cyan">
			                      <h4 class="task-card-title">公司简介</h4>
			                    </li>
			                    <li class="collection-item dismissable">
			                      <input type="checkbox" id="task1" />
			                      <label for="task1">Create Mobile App UI. <a href="#" class="secondary-content"><span class="ultra-small">Today</span></a>
			                      </label>
			                      <span class="task-cat teal">Mobile App</span>
			                    </li>
			                    <li class="collection-item dismissable">
			                      <input type="checkbox" id="task2" />
			                      <label for="task2">Check the new API standerds. <a href="#" class="secondary-content"><span class="ultra-small">Monday</span></a>
			                      </label>
			                      <span class="task-cat purple">Web API</span>
			                    </li>
			                    <li class="collection-item dismissable">
			                      <input type="checkbox" id="task3" checked="checked" />
			                      <label for="task3">Check the new Mockup of ABC. <a href="#" class="secondary-content"><span class="ultra-small">Wednesday</span></a>
			                      </label>
			                      <span class="task-cat pink">Mockup</span>
			                    </li>
			                    <li class="collection-item dismissable">
			                      <input type="checkbox" id="task4" checked="checked" disabled="disabled" />
			                      <label for="task4">I did it !</label>
			                      <span class="task-cat cyan">Mobile App</span>
			                    </li>
			                  </ul>
	                    </div>
	                    
	                    <div class="col s12 m4 l4">
	                    	<ul id="task-card" class="collection with-header">
			                    <li class="collection-header cyan">
			                      <h4 class="task-card-title">公司简介</h4>
			                    </li>
			                    <li class="collection-item dismissable">
			                      <input type="checkbox" id="task1" />
			                      <label for="task1">Create Mobile App UI. <a href="#" class="secondary-content"><span class="ultra-small">Today</span></a>
			                      </label>
			                      <span class="task-cat teal">Mobile App</span>
			                    </li>
			                    <li class="collection-item dismissable">
			                      <input type="checkbox" id="task2" />
			                      <label for="task2">Check the new API standerds. <a href="#" class="secondary-content"><span class="ultra-small">Monday</span></a>
			                      </label>
			                      <span class="task-cat purple">Web API</span>
			                    </li>
			                    <li class="collection-item dismissable">
			                      <input type="checkbox" id="task3" checked="checked" />
			                      <label for="task3">Check the new Mockup of ABC. <a href="#" class="secondary-content"><span class="ultra-small">Wednesday</span></a>
			                      </label>
			                      <span class="task-cat pink">Mockup</span>
			                    </li>
			                    <li class="collection-item dismissable">
			                      <input type="checkbox" id="task4" checked="checked" disabled="disabled" />
			                      <label for="task4">I did it !</label>
			                      <span class="task-cat cyan">Mobile App</span>
			                    </li>
			                  </ul>
	                    </div>
                	</div>
                	
                	<div id="floatButton" class="fixed-action-btn hide-on-med-and-down" style="bottom: 50px; right: 19px;">
	                    <a class="btn-floating btn-large">
	                      <i class="mdi-editor-publish"></i>
	                    </a>
                    </div>
				</div>
				
				<div id="timelineModal" class="modal">
					<nav class="task-modal-nav">
						<div class="nav-wrapper">
			            	<div class="col s12">
			                	<ul>
			                    	<li><a style="font-size:1.5rem">时间轴</a></li>
			                     	<li class="right"><a href="#!"><i class="modal-action modal-close  mdi-navigation-close"></i></a></li>
			                  	</ul>
			             	</div>
			          	</div>
					</nav>
			  		<div class="modal-content row">
			        	<div class="col s12">
							<ul class="cbp_tmtimeline">
								<li>
									<time class="cbp_tmtime" datetime="2013-04-10 18:30"><span>13/04/10</span> <span>18:30</span></time>
									<div class="cbp_tmicon cbp_tmicon-phone"></div>
									<div class="cbp_tmlabel">
										<h2>Ricebean</h2>
										<p>Winter purslane courgette pumpkin quandong</p>
									</div>
								</li>
								<li>
									<time class="cbp_tmtime" datetime="2013-04-11T12:04"><span>4/11/13</span> <span>12:04</span></time>
									<div class="cbp_tmicon cbp_tmicon-screen"></div>
									<div class="cbp_tmlabel">
										<h2>Greens</h2>
										<p>Caulie dandelion maize lentil collard greens</p>
									</div>
								</li>
								<li>
									<time class="cbp_tmtime" datetime="2013-04-13 05:36"><span>4/13/13</span> <span>05:36</span></time>
									<div class="cbp_tmicon cbp_tmicon-mail"></div>
									<div class="cbp_tmlabel">
										<h2>Sprout</h2>
										<p>Parsnip lotus root celery yarrow seakale tomato collard.</p>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>
            </section>
            <!-- END CONTENT -->
        </div>
        <!-- END WRAPPER -->
    </div>
    <!-- END MAIN -->

    <!-- START FOOTER -->
    <footer class="page-footer">
        <div class="footer-copyright">
            <div class="container">
                Copyright © 2015 <a class="grey-text text-lighten-4" href="http://themeforest.net/user/geekslabs/portfolio?ref=geekslabs" target="_blank">GeeksLabs</a> All rights reserved.
                <span class="right"> Design and Developed by <a class="grey-text text-lighten-4" href="http://geekslabs.com/">GeeksLabs</a></span>
            </div>
        </div>
    </footer>
    <!-- END FOOTER -->
    
    <!-- ================================================
    Scripts
    ================================================ -->
    
    <!-- jQuery Library -->
    <script type="text/javascript" src="assets/js/plugins/jquery-1.11.2.min.js"></script>
    <!--materialize js-->
    <script type="text/javascript" src="assets/js/materialize.min.js"></script>
    <!--scrollbar-->
    <script type="text/javascript" src="assets/js/plugins/perfect-scrollbar/perfect-scrollbar.min.js"></script>
    
    <script type="text/javascript" src="assets/js/plugins/prism/prism.js"></script>

    <!-- chartist -->
    <script type="text/javascript" src="assets/js/plugins/chartist-js/chartist.min.js"></script>   

    <!-- chartjs -->
    <script type="text/javascript" src="assets/js/plugins/chartjs/chart.min.js"></script>

    <!-- sparkline -->
    <script type="text/javascript" src="assets/js/plugins/sparkline/jquery.sparkline.min.js"></script>
    <script type="text/javascript" src="assets/js/plugins/sparkline/sparkline-script.js"></script>

    <!--jvectormap-->
    <script type="text/javascript" src="assets/js/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
    <script type="text/javascript" src="assets/js/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
    <script type="text/javascript" src="assets/js/plugins/jvectormap/vectormap-script.js"></script>    
    <script type="text/javascript" src="assets/js/plugins/vertical-timeline/js/modernizr.custom.js"></script>
    
    
    <!--plugins.js - Some Specific JS codes for Plugin Settings-->
    <script type="text/javascript" src="assets/js/plugins.js"></script>
    <!--custom-script.js - Add your own theme custom JS-->
    <script type="text/javascript" src="assets/js/custom-script.js"></script>
    <!-- Toast Notification -->
    <script type="text/javascript">
    $(window).load(function() {
    });
    </script>
</body>

</html>