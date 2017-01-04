$(window).load(function() {
	
	var h='<!-- 通知下拉 -->'
	+'<li>'
	+'<h5><b><span class="data-localize" data-localize="sys.notifications"></span></b> <span class="new badge">5</span></h5>'
	+'</li>'
	+'<li class="divider"></li>'
	+'<li>'
	+'<a href="#!"><i class="mdi-action-add-shopping-cart"></i><span  class="data-localize" data-localize="sys.receiveNotification"></span></a>'
	+'<time class="media-meta" datetime="2015-06-12T20:50:48+08:00">2 hours ago</time>'
	+'</li>';
	$("#notifications-dropdown").html(h);
	
	var h2='<ul id="chat-out" class="side-nav rightside-navigation hide-on-med-and-down" style="height:'+window.innerHeight+'">'
		+'<li class="li-hover">'
		+'<a href="#" data-activates="chat-out" class="chat-close-collapse right"><i class="mdi-navigation-close"></i></a>'
		+'<div class="row"></div>'
		+'</li>'
		+'<li class="li-hover">'
		+'<ul class="chat-collapsible" data-collapsible="expandable">'
		+'<li>'
		+'<div class="collapsible-header teal white-text active"><i class="mdi-social-whatshot"></i>Recent Activity</div>'
		+'<div class="collapsible-body recent-activity">'
		+'<div class="recent-activity-list chat-out-list row">'
		+'<div class="col s3 recent-activity-list-icon"><i class="mdi-action-add-shopping-cart"></i></div>'
		+'<div class="col s9 recent-activity-list-text">'
		+'<a href="#">just now</a>'
		+'<p>Jim Doe Purchased new equipments for zonal office.</p>'
		+'</div>'
		+'</div>'
		+'</div>'
		+'</li>'
		+'<li>'
		+'<div class="collapsible-header red white-text"><i class="mdi-action-stars"></i>Favorite Associates</div>'
		+'<div class="collapsible-body favorite-associates">'
		+'<div class="favorite-associate-list chat-out-list row">'
		+'<div class="col s4"><img src="'+getRootPath()+'/assets/images/avatar.jpg" alt="" class="circle responsive-img online-user valign profile-image"></div>'
		+'<div class="col s8">'
		+'<p>Eileen Sideways</p>'
		+'<p class="place">Los Angeles, CA</p>'
		+'</div>'
		+'</div>'
		+'</div>'
		+'</li>'
		+'</ul>'
		+'</li>'
		+'</ul>';
	$("#right-sidebar-nav").html(h2);
	
	getLocalize($(".data-localize"));
	
	$('.notification-button').dropdown({
		inDuration: 300,
		outDuration: 225,
		constrain_width: false, // Does not change width of dropdown to that of the activator
	 	hover: true, // Activate on hover
	 	gutter: 0, // Spacing from edge
	 	belowOrigin: true, // Displays dropdown below the button
		alignment: 'left' // Displays dropdown with edge aligned to the left of button
    });
	
	 $('.chat-collapse').sideNav({
	    menuWidth: 300,
	    edge: 'right',
	});
	$('.chat-close-collapse').click(function() {
	    $('.chat-collapse').sideNav('hide');
	});
	$('.chat-collapsible').collapsible({
	    accordion: false
	});
	var righttnav = $("#chat-out").height();
	$('.rightside-navigation').height(righttnav).perfectScrollbar({
		suppressScrollX: true
	}); 
});