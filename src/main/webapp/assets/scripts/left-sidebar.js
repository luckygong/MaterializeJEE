
$(document).ready(function(){
	$.ajax({
		cache: false,
		url:getProjectName()+'/initMenu',
		dataType:'json',
		data:{},
		type: "POST",
		async: true,
		success:function(result){
			if(result.status==1){
				var h='<ul id="slide-out" class="side-nav fixed leftside-navigation">'
					+'<li class="user-details cyan darken-2">'
					+'<div class="row">'
                    +'<div class="col col s4 m4 l4">'
                  	+'<img data-original="'+getFtpWebPath()+'/'+result.data.avatar+'" src="'+getProjectName()+'/assets/images/avatar.jpg" class="circle responsive-img valign profile-image lazy">'
                  	+'</div>'
                  	+'<div class="col col s8 m8 l8">'
                  	+'<ul id="profile-dropdown" class="dropdown-content">'
                  	+'<li><a href="#"><i class="mdi-action-face-unlock"></i><font class="data-localize" data-localize="sys.profile"></font></a></li>'
                  	+'<li><a href="#"><i class="mdi-action-settings"></i><font class="data-localize" data-localize="sys.settings"></font></a></li>'
                  	+'<li class="divider"></li>'
                  	+'<li><a href="'+getProjectName()+'/logout"><i class="mdi-hardware-keyboard-tab"></i><font class="data-localize" data-localize="sys.logout"></font></a></li>'
                  	+'</ul>'
                  	+'<a class="btn-flat dropdown-button waves-effect waves-light white-text profile-btn profile-dropdown" href="#" data-activates="profile-dropdown"><span id="userName">'+result.data.realName+'</span><i class="mdi-navigation-arrow-drop-down right"></i></a>'
                  	+'<p class="user-roal data-localize" data-localize="sys.greetings"></p>'
                  	+'</div>'
                  	+'</div>'
                  	+'</li>'
                  	+'<li class="no-padding">'
                  	+'<ul class="menu collapsible collapsible-accordion">'
				    +'<li class="bold collapsible"><a href="javascript:;" data-url="home" class="collapsible-header waves-effect waves-cyan menu-item active"><i class="mdi-action-dashboard"></i><span class="data-localize" data-localize="sys.menu.home"></span></a>'
				    +'<div class="collapsible-body"></div>'
					+'</li>';
                    if(!isEmpty(result.data.menus)){
    					for(var i=0;i<result.data.menus.length;i++){
    						h+=createMenu(result.data.menus[i]);
    					}
    				}
                    
                    h+='</ul>'
                	+'</li>'
                    +'</ul>'
                    +'<a href="#" data-activates="slide-out" class="sidebar-collapse btn-floating btn-medium waves-effect waves-light hide-on-large-only cyan leftside-nav-menu"><i class="mdi-navigation-menu"></i></a>';
				
                $("#left-sidebar-nav").html(h);
                getLocalize($(".data-localize"));
                
                $('.profile-dropdown').dropdown({
              		inDuration: 300,
              		outDuration: 125,
              		constrain_width: false, 
              		hover: false, 
              		alignment: 'left', 
              		gutter: 0, 
              		belowOrigin: true 
                });
                
                $('.sidebar-collapse').sideNav({
                    edge: 'left',  
                });
                
                $('.collapsible').collapsible();
                
                // Perfect Scrollbar
                $('select').not('.disabled').material_select();
                var leftnav = $(".page-topbar").height();  
                var leftnavHeight = window.innerHeight - leftnav;
                $('.leftside-navigation').height(leftnavHeight).perfectScrollbar({
                    suppressScrollX: true
                });
                
                $(".menu .menu-item").on('click',function(){
                	var menu = $(this);
                	var ref = menu.attr("data-url");
                	loadHTMLToContent('content', ref, 'GET');
                	menu.closest(".menu").find('.menu-item').each(function(){
                		$(this).closest("li").removeClass("active");
                	})
                	menu.closest("li").addClass("active");
                	
                	if($(".leftside-nav-menu").is(":visible")){
                		$(".leftside-nav-menu").click();
                	}
                });
                
                setTimeout("$('img.lazy').lazyload();",400); 
			}else{
				Message.danger({"message":"菜单加载失败，将返回登录页面","callBack":function(){window.location.href=getProjectName()+'/login.jsp';}});
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			Message.danger({"message":"菜单加载失败，将返回登录页面","callBack":function(){window.location.href=getProjectName()+'/login.jsp';}});
		}
	});
});


function createMenu(menu){
	if(isEmpty(menu)){
		return '';
	}
	var h="";
	if(menu.isDirectory){
		h+='<li class="bold collapsible"><a class="collapsible-header waves-effect waves-cyan"><i class="'+menu.icon+'"></i> '+menu.menuName+'</a>';
		var childs = menu.childs;
		if(!isEmpty(childs)){
			h+='<div class="collapsible-body">'
			  +'<ul>';
			for(var j=0;j<childs.length;j++){
				h+='<li><a href="javascript:;" data-url="'+childs[j].menuUrl+'" class="menu-item">'+childs[j].menuName+'</a></li>';
			}
			h+='</ul>'
			  +'</div>';
		}
		h+='</li>';
	}else{
		h+='<li class="bold collapsible"><a href="javascript:;" data-url="'+menu.menuUrl+'" class="class="collapsible-header waves-effect waves-cyan menu-item"><i class="'+menu.icon+'"></i> '+menu.menuName+'</a>'
		+'<div class="collapsible-body"></div>'
		+'</li>';
	}
	return h;
}