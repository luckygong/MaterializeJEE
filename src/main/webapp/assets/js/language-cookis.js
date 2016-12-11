var languageName = "somoveLanguage";
function  chgLang(language) {
	SetCookie(languageName,language);
	location.reload();
}	 

function  SetCookie(languageName,value){ 
	var Days = 30; //此 cookie 将被保存 30 天
	var exp = new Date();    //new Date("December 31, 9998");
	exp.setTime(exp.getTime() + Days*24*60*60*1000);
	document.cookie = languageName + "="+ escape (value) + ";expires=" + exp.toGMTString();
}

function getCookie(languageName){//取cookies函数   
	var arr = document.cookie.match(new RegExp("(^| )"+languageName+"=([^;]*)(;|$)"));
	if(arr != null){
		return unescape(arr[2]); 
	}
	return null
}

function getLocalize(element){
	if (getCookie(languageName) != "" && getCookie(languageName) != null) {  
		if (getCookie(languageName) == "zh") {  
			$(element).localize("static_message", {pathPrefix: getRootPath()+"/assets/language/core", language: "zh"});  
			$(element).localize("validate_message", {pathPrefix: getRootPath()+"/assets/language/validate", language: "zh"});  
        }  
		if (getCookie(languageName) == "en") {  
			$(element).localize("static_message", {pathPrefix: getRootPath()+"/assets/language/core", language: "en"});  
			$(element).localize("validate_message", {pathPrefix: getRootPath()+"/assets/language/validate", language: "en"});  
        }  
    }  else {
    	$(element).localize("static_message", {pathPrefix: getRootPath()+"/assets/language/core", language: "zh"});  
		$(element).localize("validate_message", {pathPrefix: getRootPath()+"/assets/language/validate", language: "zh"}); 
    }
}

function loadLanguageIcon(language){
	if(isEmpty(language)){
		var uulanguage = (navigator.language || navigator.browserLanguage).toLowerCase();  
		if (uulanguage.indexOf("en") > -1) {  
			$("#languageImg").attr('src',getRootPath()+'/assets/images/flag-icons/United-States.png'); 
		}else if (uulanguage.indexOf("zh") > -1) {  
			$("#languageImg").attr('src',getRootPath()+'/assets/images/flag-icons/China.png'); 
		}else{  
			$("#languageImg").attr('src',getRootPath()+'/assets/images/flag-icons/China.png'); 
		}
		
		if (getCookie(languageName) != "" && getCookie(languageName) != null) {  
			if (getCookie(languageName) == "zh") {  
				$("#languageImg").attr('src',getRootPath()+'/assets/images/flag-icons/China.png'); 
	        }  
			if (getCookie(languageName) == "en") {  
				$("#languageImg").attr('src',getRootPath()+'/assets/images/flag-icons/United-States.png'); 
	        }  
	    }  
	}else{
		if(language=="en"){
			$("#languageImg").attr('src',getRootPath()+'/assets/images/flag-icons/United-States.png'); 
		}else{
			$("#languageImg").attr('src',getRootPath()+'/assets/images/flag-icons/China.png');
		}
	}
}

$(function() {  
    var uulanguage = (navigator.language || navigator.browserLanguage).toLowerCase();  
	if (uulanguage.indexOf("en") > -1) {  
		$("[data-localize]").localize("static_message", {pathPrefix: getRootPath()+"/assets/language/core", language: "en"});  
		$("[data-localize]").localize("validate_message", {pathPrefix: getRootPath()+"/assets/language/validate", language: "en"});  
	}else if (uulanguage.indexOf("zh") > -1) {  
		$("[data-localize]").localize("static_message", {pathPrefix: getRootPath()+"/assets/language/core", language: "zh"});  
		$("[data-localize]").localize("validate_message", {pathPrefix: getRootPath()+"/assets/language/validate", language: "zh"});  
	}else{  
		$("[data-localize]").localize("static_message", {pathPrefix: getRootPath()+"/assets/language/core", language: "zh"});  
		$("[data-localize]").localize("validate_message", {pathPrefix: getRootPath()+"/assets/language/validate", language: "zh"}); 
	}
	
	if (getCookie(languageName) != "" && getCookie(languageName) != null) {  
		if (getCookie(languageName) == "zh") {  
			$("[data-localize]").localize("static_message", {pathPrefix: getRootPath()+"/assets/language/core", language: "zh"});  
			$("[data-localize]").localize("validate_message", {pathPrefix: getRootPath()+"/assets/language/validate", language: "zh"});  
        }  
		if (getCookie(languageName) == "en") {  
			$("[data-localize]").localize("static_message", {pathPrefix: getRootPath()+"/assets/language/core", language: "en"});  
			$("[data-localize]").localize("validate_message", {pathPrefix: getRootPath()+"/assets/language/validate", language: "en"});  
        }  
    }  
});  
