+(function(){
	document.write('<link rel="icon" href="'+getRootPath()+'/assets/images/favicon/favicon-32x32.png" sizes="32x32">');//Favicons
	document.write('<link rel="apple-touch-icon-precomposed" href="'+getRootPath()+'/assets/images/favicon/apple-touch-icon-152x152.png">');
	document.write('<meta name="msapplication-TileColor" content="#00bcd4">');//For iPhone
	document.write('<meta name="msapplication-TileImage" content="'+getRootPath()+'/assets/images/favicon/mstile-144x144.png">');
})();
+(function(){
	var a=new Array(getRootPath()+"/assets/css/materialize.css",//CORE CSS
					getRootPath()+"/assets/css/style.min.css",
					getRootPath()+"/assets/css/custom/custom.min.css",//Custome CSS
					getRootPath()+"/assets/js/plugins/perfect-scrollbar/perfect-scrollbar.css",
					getRootPath()+"/assets/js/plugins/jquery.nestable/nestable.css",
					getRootPath()+"/assets/js/plugins/sweetalert/sweetalert.css",
					getRootPath()+"/assets/js/plugins/dropify/css/dropify.min.css",
					getRootPath()+"/assets/js/plugins/material-datetime-picker/css/date-time-picker.css",
					getRootPath()+"/assets/css/my.css"
	);
	for(var i=0;i<a.length;i++){
		var f=a[i];
		if(f.match(/.*\.css$/)){
            document.write('<link href="'+f+'" rel="stylesheet" type="text/css" media="screen,projection"/>');
		}else if(f.match(/.*\.js$/)){
			document.write('<script src="' + f + '" type="text/javascript" ></script>'); 
		}
	}
})();
+(function(){
	var a=new Array(
			getRootPath()+"/assets/js/plugins/jquery-1.11.2.min.js",//jQuery Library
			getRootPath()+"/assets/js/plugins/jquery-cookies/jquery.cookies.min.js",
			getRootPath()+"/assets/js/plugins/jquery.localize.min.js",
			getRootPath()+"/assets/js/plugins/jquery-validation/jquery.validate.min.js",
			getRootPath()+"/assets/js/plugins/jquery-validation/additional-methods.min.js",
			getRootPath()+"/assets/js/plugins/jquery.nestable/jquery.nestable.js",
			getRootPath()+"/assets/js/plugins/ajaxfileupload.js",
			getRootPath()+"/assets/js/plugins/jquery.lazyload.js",
			getRootPath()+"/assets/js/plugins/jquery.blockUI.js",
			getRootPath()+"/assets/js/plugins/jquery.form.js",
			getRootPath()+"/assets/js/plugins/angular.min.js",//angularjs
			getRootPath()+"/assets/js/materialize.js",//materialize.js
			getRootPath()+"/assets/js/plugins/angular-materialize.js",//angular-materialize js
			getRootPath()+"/assets/js/plugins/perfect-scrollbar/perfect-scrollbar.min.js",//scrollbar.js
			getRootPath()+"/assets/js/plugins/sweetalert/sweetalert.min.js",//sweetalert.js
			getRootPath()+"/assets/js/plugins/floatThead/jquery.floatThead.min.js",//floatThead
			getRootPath()+"/assets/js/plugins/dropify/js/dropify.min.js",
			getRootPath()+"/assets/js/plugins/material-datetime-picker/js/moment.min.js",
			getRootPath()+"/assets/js/plugins/material-datetime-picker/js/lang/zh-cn.js",
			getRootPath()+"/assets/js/plugins/material-datetime-picker/js/date-time-picker.js",
			getRootPath()+"/assets/js/plugins.js",//custom script
			getRootPath()+"/assets/js/custom-script.js",
			getRootPath()+"/assets/js/language-cookis.js",
			getRootPath()+"/assets/scripts/block-ui.js",
			getRootPath()+"/assets/scripts/bind-data.js",
			getRootPath()+"/assets/scripts/prompt-ui.js",
			getRootPath()+"/assets/scripts/header.js",
			getRootPath()+"/assets/scripts/header-notifications.js",
			getRootPath()+"/assets/scripts/left-sidebar.js",
			getRootPath()+"/assets/scripts/footer.js",
			getRootPath()+"/assets/scripts/float-button.js",
			getRootPath()+"/assets/scripts/page-plugin.js",
			getRootPath()+"/assets/scripts/select-dropdown.js"
	);
	for(var i=0;i<a.length;i++){
		var f=a[i];
		if(f.match(/.*\.css$/)){
            document.write('<link href="'+f+'" rel="stylesheet" type="text/css" />');
		}else if(f.match(/.*\.js$/)){
			document.write('<script src="' + f + '" type="text/javascript" ></script>'); 
		}
	}
})();
	
