//项目前台网络路径，前台跟后台统一部署，则此路径与ajax请求路径一致,否则根据实际进行配置
function getRootPath(){
    return getProjectPath();
}

//项目前台ajax请求路径
function getProjectPath(){
	//获取当前网址，如： http://localhost:8080/report/login.html
	var curWwwPath=window.document.location.href;
	//获取主机地址之后的目录，如： report/login.html
	var pathName=window.document.location.pathname;
	var pos=curWwwPath.indexOf(pathName);
	//获取主机地址，如： http://localhost:8080
	var localhostPaht=curWwwPath.substring(0,pos);
	//获取带"/"的项目名，如：/report
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	return(localhostPaht+projectName);
}

function getRequestPath(){
	//获取当前网址，如： http://localhost:8080/report/login.html
	var curWwwPath=window.document.location.href;
	var project = getProjectPath();
	var path = curWwwPath.substring(project.length)
	if(path.indexOf("?")>=0){
		path = path.substring(0,path.indexOf("?"));
	}
	return path;
}

//加载ftp资源的网络地址，根据实际进行配置
function getFtpWebPath(){
	return "http://192.168.1.109:8088";
} 