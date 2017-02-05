//获取项目名称
function getProjectName(){
	//获取主机地址之后的目录，如： report/login.html
	var pathName=window.document.location.pathname;
	//获取带"/"的项目名，如：/report
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	return projectName;
}

//加载ftp资源的网络地址，根据实际进行配置
function getFtpWebPath(){
	return "http://192.168.1.109:8088";
} 