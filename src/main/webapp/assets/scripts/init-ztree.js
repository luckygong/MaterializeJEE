(function($) {
	'use strict';

	var initZTree = {
		
		/** 定义的默认参数 */
		options : {
			'submitName' : '',
			'ztree':{
				'url' : '', 
				'params' : {}, 
				'method' : 'POST',
				'checkable' : false, 
				'autoParam' : ["id"]
			},
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
			$(element).data("options", $.extend({}, initZTree.options, option));
		},

		initTree : function(element) {
			$(element).html("");
			
			var options = $(element).data("options");
			var setting = {
					check: {
						enable: options.ztree.checkable
					},
					data: {
						simpleData: {
							enable: true
						}
					},
					async: {
						enable: true,
						contentType: "application/json",
						url:options.ztree.url,
						autoParam:options.ztree.autoParam,
						otherParam:options.ztree.params,
						dataFilter:  function(treeId, parentNode, childNodes) {
					        if (!childNodes) return null;
					        return childNodes;
					    }
					},
					callback: {
						onAsyncSuccess: function(event, treeId, treeNode, msg) {
							initZTree.initCheckedValue(element, event, treeId, treeNode, msg);
						},
						onCheck:  function(event, treeId, treeNode, msg) {
							initZTree.initCheckedValue(element, event, treeId, treeNode, msg);
						}
					}
				};
			
			var tree = '<ul id="tree_'+options.submitName+'" class="ztree"></ul>';
			if(!isEmpty(options.submitName)){
				$(element).append('<input class="hide" id="'+options.submitName+'" name="'+options.submitName+'"></input>');
			}
			$(element).append(tree);
			$.fn.zTree.init($("#tree_"+options.submitName), setting);
			$(element).perfectScrollbar();
		},

		initCheckedValue : function(element, event, treeId, treeNode, msg) {
			var options = $(element).data("options");
			
			if(!isEmpty(options.submitName)){
				var zTree = $.fn.zTree.getZTreeObj(treeId);
				var nodes = zTree.getCheckedNodes(true);
				var ids = '';
				for (var i = 0, l = nodes.length; i < l; i++) {
					var node = nodes[i];
					ids += node.id + (i != l - 1 ? "," : "");
				}
				$("#"+options.submitName).val(ids);
			}
		}

	};
	
	$.fn.initZTree = function(option) {
		initZTree.setOptions(this, option);
		initZTree.initTree(this);
	};
})(jQuery);
