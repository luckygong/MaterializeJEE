(function($) {
	'use strict';

	var initSelect = {
		
		categoryMap :{},
		
		/** 定义的默认参数 */
		options : {
			'method' : 'POST',
			'category' : '',
			'addEmptyOption' : true, //添加空值选项
			'emptyOptionTxt' : '', //空值选项显示内容
			'defaultSelected' : '', //默认选择的option
			'exceptValueArray':null,//需要过滤的值
			'onlyShowArray':null,//只需要显示的值
			'load' : false,
			'timeout' : 5000
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
			$(element).data("options", $.extend({}, initSelect.options, option));
		},

		/**
		 * 发送ajax请求
		 *
		 * @param options
		 */
		ajaxSend : function(element) {
			var options = $(element).data("options");
			$.ajax({
				type : options.method,
				url : getProjectName()+'/initSelectOption',
				dataType : 'json',
				data : {"category":options.category,"exceptValueArray":options.exceptValueArray,"onlyShowArray":options.onlyShowArray},
				timeout : options.timeout,
				cache : false,
				async: false,
				beforeSend : function(XMLHttpRequest) {
					if(options.load){
						blockUI.block();
                    } 
				},
				error : function(XMLHttpRequest, status, thrownError) {
					if(status == "timeout"){
						Message.danger({message:"初始化下拉框请求超时！"});
					}else{
						Message.danger({message:"初始化下拉框请求失败！"});
					}
					if(options.load){
						blockUI.unblock();
	                }	 
				},
				success : function(msg) {
					initSelect.callBackFunction(element,options, msg);
				}
			});
		},

		/**
		 * ajax成功默认执行方法
		 *
		 */
		callBackFunction : function(element,options, msg) {
			if(options.load){
				blockUI.unblock();
	        }
			
			if (isEmpty(msg)){
				$(element).html('<option value="" selected></option>');
				return false;			
			}
			
			var h='';
			if(options.addEmptyOption){
				h+='<option value="">'+options.emptyOptionTxt+'</option>';
			}
			var datas = msg.data;
			initSelect.categoryMap = {};
			for(var i=0;i<datas.length;i++){
				var dic = datas[i];
				if(dic.code==options.defaultSelected){
					h+='<option value="'+dic.code+'" selected >'+dic.translation+'</option>';
				}else{
					h+='<option value="'+dic.code+'" >'+dic.translation+'</option>';
				}
				initSelect.categoryMap[dic.code] = dic.translation;
			}
			$(element).html(h);
			return true;
		}

	};
	
	$.fn.initSelect = function(option) {
		initSelect.setOptions(this, option);
		initSelect.ajaxSend(this);
		return initSelect.categoryMap;
	};
})(jQuery);

(function($) {
	var initDynDropDown = {
		
		options:{
      		constrain_width: false, 
      		hover: false, 
      		alignment: 'left', 
      		gutter: 0, 
      		belowOrigin: true 
        },
        
        setOptions : function(element, option) {
			$(element).data("options", $.extend({}, initDynDropDown.options, option));
		},
			
		init : function(element) {
			var options = $(element).data("options");
			$(element).dropdown({
			    inDuration: 300,
			    outDuration: 125,
			    constrain_width: options.constrain_width, 
			    hover: options.hover, 
			    alignment: options.alignment, 
			    gutter: options.gutter, 
			    belowOrigin: options.belowOrigin 
			});
		},
	};
	$.fn.initDynDropDown = function(option) {
		if(isEmpty(option)){
			option = {};
		}
		initDynDropDown.setOptions(this, option);
		initDynDropDown.init(this);
	};
})(jQuery);

function initCategoryMap(option){
	var result = {};
	var options = {
		'method' : 'POST',
		'category' : '',
		'exceptValueArray':null,//需要过滤的值
		'timeout' : 5000
	};
	
	options = $.extend({}, options, option)
	
	$.ajax({
		type : options.method,
		url : getProjectName()+'/initSelectOption',
		dataType : 'json',
		data : {"category":options.category},
		timeout : options.timeout,
		cache : false,
		async: false,
		success : function(msg) {
			var datas = msg.data;
			for(var i=0;i<datas.length;i++){
				var dic = datas[i];
				result[dic.code] = dic.translation;
			}
		}
	});
	return result;
}
