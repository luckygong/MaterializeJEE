(function($) {
	'use strict';

	var pageQuery = {
		
		/** 定义的默认参数 */
		options : {
			'method' : 'post',
			'data' : '',
			'url' : '',
			'load' : true,
			'pageNo' : '1',
			'pageSize' : '10',
			'pagefirstRowKey' : '',
			'timeout' : 30000,
			'pageContent' : '',
			'paginationType' : 'common' //common:普通分页  onlyNext:非关系型数据库分页,只能向下翻页,不能随机跳转
		},

		// 绑定的事件,方便扩展
		event : null,
		/**
		 * 初始化
		 *
		 */
		init : function() { },
		
		/**
		 * 赋值
		 *
		 * @return $this 当前对象
		 */
		setOptions : function(element, option) {
			$(element).data("options", $.extend({}, pageQuery.options, option));
		},

		/**
		 * 发送ajax请求
		 *
		 * @param options
		 */
		ajaxSend : function(options) {
			$.ajax({
				type : options.method,
				url : getProjectName()+'/'+options.url,
				dataType : 'json',
				data : options.data,
				timeout : options.timeout,
				cache : false,
				beforeSend : function(XMLHttpRequest) {
					XMLHttpRequest.setRequestHeader("pageNo", options.pageNo);
					XMLHttpRequest.setRequestHeader("pageSize", options.pageSize);
					XMLHttpRequest.setRequestHeader("pagefirstRowKey", options.pagefirstRowKey);
					if(options.load){
						blockUI.block();
                    } 
				},
				error : function(XMLHttpRequest, status, thrownError) {
					if(status == "timeout"){
						Message.danger({message:"分页查询请求超时！"});
					}else{
						Message.danger({message:"分页查询请求失败！"});
					}
					if(options.load){
						blockUI.unblock();
	                }	 
				},
				success : function(msg) {
					if (!pageQuery.callBackFunction(options, msg))
						return;
				}
			});
		},

		/**
		 * ajax 事件 这里callback特意采用json格式，如果有需要可以在这里修改
		 *
		 */
		ajaxClick : function(element) {
			// 获取当前对象
			var options = $(element).data("options");
			options.method = options.method.toUpperCase();

			// 阻止submit按钮的onSubmit事件
			if (this.event != null) {
				this.event.preventDefault();
				this.event = null;
			}

			/**
			 * 设置参数 
			 */
			var _paramsArr = "";
			if (options.params) {
				_paramsArr = pageQuery.attr2param(options);
				if ("GET" == options.method) {
					options.url += "?" + $.param(_paramsArr);
				} else {
					options.data = $.param(_paramsArr);
				}
			}
			pageQuery.ajaxSend(options);

			// ajax定时任务set interval
			if ((undefined != options.interval) && ('' != options.interval)) {
				window.setInterval(function() {
					pageQuery.ajaxSend(options);
				}, options.interval * 1000);
			}
		},

		/**
		 * ajax成功默认执行方法
		 *
		 */
		callBackFunction : function(options, msg) {
			if(options.load){
				blockUI.unblock();
	        }
			
			if (isEmpty(msg)){
				return false;			
			}
			 
			if (options.complete) {
				var pagination = "";
				if(options.paginationType.toUpperCase()==="COMMON"){
					pagination = getPaginationHtml(options,msg);
				}
				if(options.paginationType.toUpperCase()==="ONLYNEXT"){
					pagination = getNosqlPaginationHtml(options,msg);
				}
				options.complete($.extend({}, msg, {"pagination":pagination,"pageContentId":options.pageContent}));
				return;
			}
			return true;
		},

		/**
		 * 属性转换为参数 v value;t text
		 */
		attr2param : function(options) {
			var _paramsArr = {};
			eval('var _eval_params = (' + options.params + ')');

			$.each(_eval_params, function(param, type) {
				var $temp = $("#" + param);// 临时对象
				var _val = "";// 获取值ֵ
				switch (type) {
				case "t":
					_val = $temp.text();
					break;

				case "v":
					_val = $temp.val();
					break;

				default:
					_val = type;

				}
				_paramsArr[param] = _val;
			});
			return _paramsArr;
		}
	};

	// 初始化控件
	$(document).ready(function(){
		pageQuery.init();
	});
	$.fn.pageQuery = function(option) {
		pageQuery.setOptions(this, option);
		pageQuery.ajaxClick(this);
	};
})(jQuery);

function getPaginationHtml(options,data){
	var currentPage = data.data.currentPage;
	var currentSize = data.data.currentSize;
	var totalPage = data.data.totalPage;
	var totalCount = data.data.totalCount;
	
	//默认展示5页
	var pageCount = 5;
	//开始的页码
	var startPage = currentPage - 2 > 0 ? currentPage - 2 : 1;
	//结束的页码
	var endPage = startPage + pageCount - 1 < totalPage ? startPage + pageCount - 1 : totalPage;
	
	var h = '<div class="col s12 valign-wrapper">';
	if(totalPage==0 || totalPage==1){
		h+='<ul class="pagination" pageContent="'+options.pageContent+'" queryUrl="'+options.url+'" queryParams="'+options.data+'" pageSize="'+currentSize+'" style="float:left;margin-left:0px;">'
		  +'<li class="disabled tooltipped" data-position="bottom" data-delay="50" data-tooltip="首页" style="padding-left: 0px;padding-right: 5px;"><a href="javascript:;"><i class="mdi-av-skip-previous"></i></a></li>'
	      +'<li class="disabled tooltipped" data-position="bottom" data-delay="50" data-tooltip="上一页" style="padding-left: 5px;padding-right: 5px;"><a href="javascript:;"><i class="mdi-image-navigate-before"></i></a></li>'
	      +'<li class="active teal lighten-2"><a href="javascript:;">1</a></li>'
	      +'<li class="disabled tooltipped" data-position="bottom" data-delay="50" data-tooltip="下一页" style="padding-left: 5px;padding-right: 5px;"><a href="javascript:;"><i class="mdi-image-navigate-next"></i></a></li>'
	      +'<li class="disabled tooltipped" data-position="bottom" data-delay="50" data-tooltip="尾页" style="padding-left: 5px;padding-right: 0px;"><a href="javascript:;"><i class="mdi-av-skip-next"></i></a></li>'
	      +'</ul>';
	}else{
		h+='<ul class="pagination" pageContent="'+options.pageContent+'" queryUrl="'+options.url+'" queryParams="'+options.data+'" pageSize="'+currentSize+'" style="float:left;margin-left:0px;">'
		if (currentPage == 1) {//如果为第一页
			h+='<li class="disabled tooltipped" data-position="bottom" data-delay="50" data-tooltip="首页" style="padding-left: 0px;padding-right: 5px;"><a href="javascript:;"><i class="mdi-av-skip-previous"></i></a></li>'
			  +'<li class="disabled tooltipped" data-position="bottom" data-delay="50" data-tooltip="上一页" style="padding-left: 5px;padding-right: 5px;"><a href="javascript:;"><i class="mdi-image-navigate-before"></i></a></li>';
		} else {
			h+='<li class="tooltipped" data-position="bottom" data-delay="50" data-tooltip="首页" style="padding-left: 0px;padding-right: 5px;"><a href="javascript:;" onclick="turnTo(1,this);"><i class="mdi-av-skip-previous"></i></a></li>'
			  +'<li class="tooltipped" data-position="bottom" data-delay="50" data-tooltip="上一页" style="padding-left: 5px;padding-right: 5px;"><a href="javascript:;" onclick="turnToPrevious(this);"><i class="mdi-image-navigate-before"></i></a></li>';
		}
		for (var i = startPage; i <= endPage; i++) {
			if (i == currentPage) {
				h+='<li class="active teal lighten-2"><a href="javascript:;">'+i+'</a></li>';
			} else {
				h+='<li><a href="javascript:;" onclick="turnTo('+i+',this);">'+i+'</a></li>';
			}
		}
		if (currentPage==totalPage) {//如果为最后一页
			h+='<li class="disabled tooltipped" data-position="bottom" data-delay="50" data-tooltip="下一页" style="padding-left: 5px;padding-right: 5px;"><a href="javascript:;"><i class="mdi-image-navigate-next"></i></a></li>'
			  +'<li class="disabled tooltipped" data-position="bottom" data-delay="50" data-tooltip="尾页" style="padding-left: 5px;padding-right: 0px;"><a href="javascript:;"><i class="mdi-av-skip-next"></i></a></li>';
		} else {
			h+='<li class="tooltipped" data-position="bottom" data-delay="50" data-tooltip="下一页" style="padding-left: 5px;padding-right: 5px;"><a href="javascript:;" onclick="turnToNext(this);"><i class="mdi-image-navigate-next"></i></a></li>'
			  +'<li class="tooltipped" data-position="bottom" data-delay="50" data-tooltip="尾页" style="padding-left: 5px;padding-right: 0px;"><a href="javascript:;" onclick="turnTo('+totalPage+',this);"><i class="mdi-av-skip-next"></i></a></li>';
		}
		h+='</ul>';
	}
	h+='<span class="hide-on-med-and-down valign">每页'+currentSize+'条/共'+totalCount+'条，第'+currentPage+'页/共'+totalPage+'页</span></div>';
	return h;
}

function getNosqlPaginationHtml(options,data){
	var currentSize = data.data.currentSize;
	var currentDataSize = data.data.currentDataSize;
	var alreadyQueryCount = data.data.alreadyQueryCount;
	var nextPagefirstRowKey = data.data.nextPagefirstRowKey;
	var hasNextPage = data.data.hasNextPage;
	
	var h = '<div class="row col s12 valign-wrapper" style="margin-left:0px;">'
		+ '<ul><li>'
	
	if(!hasNextPage){
		h+='<a class="disabled waves-effect waves-light btn" >下一页</a>';
	}else{
		h+='<a class="waves-effect waves-light btn" pageContent="'+options.pageContent+'" queryUrl="'+options.url+'" queryParams="'+options.data+'" pageSize="'+currentSize+'" page onclick="nosqlTurnNext(\''+nextPagefirstRowKey+'\');">下一页</a>';
	}
	h+='</li></ul> 每页'+currentSize+'条,当前显示第 '+(alreadyQueryCount+1-currentDataSize)+'—'+alreadyQueryCount+' 条数据';
	return h;
}


function initPageData(url,data,pageContentId,pageNo,pageSize){
	var pageQueryParams = data;
	var pageQueryUrl = url;
	$(this).pageQuery({
		url : pageQueryUrl,
		data :  pageQueryParams,
		pageNo: pageNo,
		pageSize:pageSize,
		pageContent:pageContentId,
		load: true,
		complete : function(result) {
			if (1 == result.status) {
				initPageTable(result);
				var pagination = result.pagination;
				$('#'+result.pageContentId).find("#pagination").html(pagination);
			} else {
				var msg = result.info;
				if(isEmpty(msg)){
					msg = "查询失败";
				}
				Message.danger({"message":msg});
			}
		}
	});
}

function initPhoenixPageData(url,data,pageContentId,firstRowKey,pageSize){
	var pageQueryParams = data;
	var pageQueryUrl = url;
	$(this).pageQuery({
		url : pageQueryUrl,
		data :  pageQueryParams,
		pagefirstRowKey: firstRowKey,
		pageSize:pageSize,
		pageContent:pageContentId,
		load: true,
		timeout:300000,
		paginationType:'onlyNext',
		complete : function(result) {
			if (1 == result.status) {
				initPageTable(result);
			} else {
				var msg = result.info;
				if(isEmpty(msg)){
					msg = "查询失败";
				}
				Message.danger({"message":msg});
			}
		}
	});
}

//上一页
function turnToPrevious(pagination){
	var data=$(pagination).closest(".pagination").find("li.active a").html();
	var page=parseInt(data)-1;
	var url = $(pagination).closest(".pagination").attr("queryUrl");
	var params = $(pagination).closest(".pagination").attr("queryParams");
	var pageContent = $(pagination).closest(".pagination").attr("pageContent");
	var pageSize = $(pagination).closest(".pagination").attr("pageSize");
	initPageData(url , params, pageContent, page, pageSize);
}
//下一页
function turnToNext(pagination){
	var data=$(pagination).closest(".pagination").find("li.active a").html();
	var page=parseInt(data)+1;
	var url = $(pagination).closest(".pagination").attr("queryUrl");
	var params = $(pagination).closest(".pagination").attr("queryParams");
	var pageContent = $(pagination).closest(".pagination").attr("pageContent");
	var pageSize = $(pagination).closest(".pagination").attr("pageSize");
	initPageData(url , params, pageContent, page, pageSize);
}
function turnTo(page,pagination){
	var url = $(pagination).closest(".pagination").attr("queryUrl");
	var params = $(pagination).closest(".pagination").attr("queryParams");
	var pageContent = $(pagination).closest(".pagination").attr("pageContent");
	var pageSize = $(pagination).closest(".pagination").attr("pageSize");
	initPageData(url , params, pageContent, page, pageSize);
}

//下一页
function nosqlTurnNext(nextPagefirstRowKey){
	var url = $(this).attr("queryUrl");
	var data = $(this).attr("queryParams");
	var pageContentId = $(this).attr("pageContent");
	var pageSize = $(this).attr("pageSize");
	initPhoenixPageData(url,data,pageContentId,nextPagefirstRowKey,pageSize)
}
