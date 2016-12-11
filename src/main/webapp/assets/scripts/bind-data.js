/**
 * 根据给定的json数据，自动回填数据到对应的dom元素。radio、checkbox根据name属性绑定，其他根据id属性绑定
 * 需要自动回填的元素必须包含data-bind-json-path属性，表示从json根节点到指定节点的路径，用/分开
 * 如：json={"id":"1","name":"zhaosk","child":{"childName":"child1"}}
 * <input id="id" name="id" type="text" data-bind-json-path="/"> //绑定id
 * <input id="name" name="name" type="text" data-bind-json-path="/"> //绑定name
 * <input id="childName" name="childName" type="text" data-bind-json-path="/child"> //绑定childName
 */
(function($) {
	$.fn.bindData = function(json) {
		var elements = this;
		initElements(elements.get(0), json);
	}
	// 对元素的解析回填
	function initElements(element, json) {
		var dom = $(element);
		var jsonPath = dom.attr('data-bind-json-path');
		if (jsonPath) {
			var keys = jsonPath.substr(1).split('/');
			setElementValue(element, json, keys);
			return;
		}
		for (var i = 0; i < element.children.length; i++) {
			initElements(element.children[i], json);
		}
	}
	// 赋值操作
	function setElementValue(element, json, keys) {
		var type = element.type;
		var key;
		if (type == 'radio') {
			key = element.name;
		} else if (type == 'checkbox') {
			key = element.name;
		} else {
			key = element.id;
		}
		var o = json;
		for (var i = 0; i < keys.length; i++) {
			if (keys[i]) {
				o = o[keys[i]];
			}
		}
		if (!(typeof (o[key]) == 'undefined')) {
			switch (element.tagName) {
			case 'INPUT':
				switch (element.type) {
					case 'radio':
						element.removeAttribute("checked");
						if (element.value == o[key]) {
							element.setAttribute("checked","checked");
						}
						break;
					case 'checkbox':
						element.removeAttribute("checked");
						for (var i = 0; i < o[key].length; i++) {
							if (element.value == o[key][i]) {
								element.setAttribute("checked","checked");
								break;
							}
						}
						break;
					default: 
						element.value = o[key]||"";
						$(element).closest(".input-field").children("label").addClass("active");
				}
				$(element).trigger('change');
				break;
			case 'SELECT':
				if ($(element).attr("multiple")) {
					// 多选
					if (toString.apply(o[key]) == '[object Array]') {
						var options = element.children;
						var os = o[key];
						for (var i = 0; i < options.length; i++) {
							// 重置每个选项状态
							options[i].removeAttribute("selected");
							for (var j = 0; j < os.length; j++) {
								if (options[i].value == os[j]) {
									options[i].setAttribute("selected","selected");
									break;
								}
							}
						}
					}
				} else {
					// 单选
					$(element).val(o[key]||'');
				}
				$(element).trigger('change');
				break;
			case "TEXTAREA":
				element.innerHTML = o[key];
				$(element).closest(".input-field").children("label").addClass("active");
				$(element).trigger('change');
				break;
			case "DIV":
			case "SPAN":
			case "TABLE":
			default:
				element.innerHTML = o[key];
				$(element).trigger('change');
			}
		}
	}

})($);