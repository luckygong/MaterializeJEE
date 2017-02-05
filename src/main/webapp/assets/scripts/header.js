$(window).load(function() {
	
	var h='<div class="navbar-fixed">'
	+'<nav id="header-nav" class="navbar-color">'
	+'<div class="nav-wrapper">'
	+'<ul class="left">'                      
	+'<li><h1 class="logo-wrapper"><a href="'+getProjectName()+'/pages/home.html" class="brand-logo darken-1"><img src="'+getProjectName()+'/assets/images/materialize-logo.png" alt="materialize logo"></a></h1></li>'
	+'</ul>'
	+'<ul class="right">'
	+'<li class="hide-on-med-and-down"><a href="javascript:void(0);" class="waves-effect waves-block waves-light translation-button"  data-activates="translation-dropdown"><img id="languageImg" src="'+getProjectName()+'/assets/images/flag-icons/China.png" alt="中文" /></a></li>'
	+'<li><a href="javascript:void(0);" class="waves-effect waves-block waves-light notification-button" data-activates="notifications-dropdown"><i class="mdi-social-notifications"><small class="notification-badge">5</small></i></a></li> '                       
	+'<li class="hide-on-med-and-down"><a href="javascript:void(0);" data-activates="chat-out" class="waves-effect waves-block waves-light chat-collapse"><i class="mdi-communication-chat"></i></a></li>'
	+'</ul>'
	+'<!-- 语言选择下拉 -->'
	+'<ul id="translation-dropdown" class="dropdown-content">'
	+'<li><a href="javascript:;" onclick="javascript:chgLang(\'zh\');"><img src="'+getProjectName()+'/assets/images/flag-icons/China.png" alt="Chinese" />  <span class="language-select">中文</span></a></li>'
	+'<li><a href="javascript:;" onclick="javascript:chgLang(\'en\');"><img src="'+getProjectName()+'/assets/images/flag-icons/United-States.png" alt="English" />  <span class="language-select">English</span></a></li>'
	+'</ul>'
	+'<!-- 通知下拉 -->'
	+'<ul id="notifications-dropdown" class="dropdown-content">'
	+'</ul>'
	+'</div>'
	+'</nav>'
	+'</div>';

	$("#header").html(h);
	loadLanguageIcon("");
	
	$('.translation-button').dropdown({
		inDuration: 300,
		outDuration: 225,
		constrain_width: false, // Does not change width of dropdown to that of the activator
		hover: true, // Activate on hover
		gutter: 0, // Spacing from edge
		belowOrigin: true, // Displays dropdown below the button
		alignment: 'left' // Displays dropdown with edge aligned to the left of button
	});
	
	$('.toggle-fullscreen').click(function() {
		toggleFullScreen();
	});
			
});

//Fullscreen
function toggleFullScreen() {
	if ((document.fullScreenElement && document.fullScreenElement !== null) || (!document.mozFullScreen && !document.webkitIsFullScreen)) {
		if (document.documentElement.requestFullScreen) {
			document.documentElement.requestFullScreen();
		}else if (document.documentElement.mozRequestFullScreen) {
			document.documentElement.mozRequestFullScreen();
		}else if (document.documentElement.webkitRequestFullScreen) {
			document.documentElement.webkitRequestFullScreen(Element.ALLOW_KEYBOARD_INPUT);
		}
	}else {
		if (document.cancelFullScreen) {
			document.cancelFullScreen();
		}else if (document.mozCancelFullScreen) {
			document.mozCancelFullScreen();
		}else if (document.webkitCancelFullScreen) {
			document.webkitCancelFullScreen();
		}
	}
}

