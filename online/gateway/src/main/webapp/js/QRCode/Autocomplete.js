'use strict';

/// <reference path="D:\资料\XueYou\DefinitelyTyped\jquery\jquery.d.ts" />
// todo 同一个页面 多次使用$('#openBankName').Autocomplete 会报错
{
  var Autocomplete = function Autocomplete(element, _ref) {
    var source = _ref.source,
        appendTo = _ref.appendTo,
        _ref$autoFocus = _ref.autoFocus,
        autoFocus = _ref$autoFocus === undefined ? false : _ref$autoFocus,
        _ref$ignoreCase = _ref.ignoreCase,
        ignoreCase = _ref$ignoreCase === undefined ? true : _ref$ignoreCase,
        _ref$customItem = _ref.customItem,
        customItem = _ref$customItem === undefined ? '<li>{TEXT}</li>' : _ref$customItem,
        _ref$delay = _ref.delay,
        delay = _ref$delay === undefined ? 30 : _ref$delay,
        _ref$disabled = _ref.disabled,
        disabled = _ref$disabled === undefined ? false : _ref$disabled,
        _ref$minLength = _ref.minLength,
        minLength = _ref$minLength === undefined ? 0 : _ref$minLength,
        _ref$maxHeight = _ref.maxHeight,
        maxHeight = _ref$maxHeight === undefined ? 222 : _ref$maxHeight,
        onSelect = _ref.onSelect,
        onShow = _ref.onShow,
        onClose = _ref.onClose;

    // 设置参数
    this.element = element;
    this.ignoreCase = ignoreCase;
    this.customItem = customItem;
    this.source = source;
    this.appendTo = appendTo;
    this.autoFocus = autoFocus;
    this.delay = delay;
    this.disabled = disabled;
    this.minLength = minLength;
    this.maxHeight = maxHeight;
    this.onSelect = onSelect;
    this.isShow = false;
    this.onShow = onShow;
    this.onClose = onClose;
    this.lastData = undefined;

    var maxheight = $(window).height() - $(appendTo).offset().top - $(appendTo).height() - 10;
    // 创建DOM
    this.warp = $('<div class="autocomplete-wrap"></div>').hide().css({
      'max-height': maxHeight > maxheight ? maxheight : maxHeight
    });
    this.ul = $('<ul></ul>');
    this.warp.append(this.ul);
    $(appendTo).append(this.warp);
    // 当前列表索引
    this.index = -1;
    this.init();
    // 绑定事件
    this.bind();
  };
  // 初始化
  Autocomplete.prototype.init = function () {
    var self = this;
    var element = self.element;
    // 禁用原生自动填充
    element.attr('autocomplete', 'off');
    // 设置实例
    element.data('autocomplete', self);
  };
  // 设置数据
  Autocomplete.prototype.setSource = function (source) {
    if ($.isFunction(source)) {
      this.source = source();
      return;
    }
    if (!source || !source.length) {
      return;
    }
    this.source = source;
  };
  // 关闭打开的菜单
  Autocomplete.prototype.close = function () {
    var warp = this.warp;

    warp.hide();
    this.isShow = false;
    if ($.isFunction(this.onClose)) {
      this.onClose(this);
    }
  };
  // 显示列表
  Autocomplete.prototype.show = function () {
    var _this = this;

    var warp = this.warp,
        delay = this.delay,
        disabled = this.disabled;

    if (disabled) {
      return;
    }
    setTimeout(function () {
      warp.show();
      _this.isShow = true;
      if ($.isFunction(_this.onShow)) {
        _this.onShow(_this);
      }
    }, delay);
  };
  // 销毁
  Autocomplete.prototype.destroy = function () {
    var warp = this.warp;

    warp.remove();
  };
  // 禁用
  Autocomplete.prototype.disable = function () {
    this.disabled = true;
  };
  // 启用
  Autocomplete.prototype.enable = function () {
    this.disabled = false;
  };
  // 搜索
  Autocomplete.prototype.search = function (value, repeat) {
    var ul = this.ul,
        source = this.source,
        appendTo = this.appendTo,
        warp = this.warp,
        ignoreCase = this.ignoreCase,
        customItem = this.customItem,
        maxHeight = this.maxHeight;

    var self = this;
    
    
    // 如果 value 里全是空格, 则按一个空格算
    if (value) {
      value = value.replace(/^\s+$/, ' ');
    }
    
    if (value == '' || value == ' ') {
    	self.close();
    	return;
    }
    
    // 接着进行六分之一空格的处理 ISO
    if (value) {
      value = value.replace(/\u2006/g, '');
    }

    // 去掉普通空格
    if (value) {
      value = value.replace(/^\s$/g, '');
    }
    
/*    if (!repeat && window.utils.isIphone()) {
        setTimeout(function(){
        	//msg.val(msg.val() + '延迟后检测输入框的值: ' + self.element.val() + '\n');
        	self.search(self.element.val(), true);
        }, 600);
    }*/
    

    
    


    
    
    // 去除前后空格
    value = window.utils.trim(value);


    // 去除内容中所有的空格
    if (value && value !== ' ') {
      value = value.replace(/\s+/g, '');
    }

    function toSearch(value, data) {
      ul.empty();
      self.lastData = data;
      self.index = -1;
      if (!data || !data.length) {
        self.close();
        return;
      }
      // 根据输入字符串, 寻找匹配项, 将匹配的项组成li列表
      for (var i = 0; i < data.length; ++i) {
        // 如果 data[i] 是一个对象, 则把对象的 value 属性当作显示的值
        var item = data[i];
        var nowSource = data[i];
        if (item.value) {
          nowSource = item.value;
        }
        if (!nowSource.search) {
          break;
        }
        if (nowSource.search(new RegExp(value, ignoreCase ? 'i' : '')) !== -1) {
          var template = $.isFunction(customItem) ? customItem(value, item.value) : customItem;
          var li = $(template.replace ? template.replace('{TEXT}', item.value) : template);
          ul.append(li);
        }
      }
      if (ul.children().length === 0) {
        self.close();
      } else {
    	var oneHeight = ul.children().eq(0).outerHeight();
        var maxheight = $(window).height() - ($(appendTo).position().top - $(window).scrollTop()) - $(appendTo).outerHeight();
        warp.css({
          'max-height': maxHeight > maxheight ? maxheight : maxHeight,
          'min-height':  ul.children().length > 5 ? (5 * oneHeight) : oneHeight
        });
        self.show();
      }
    }
    if ($.isFunction(source)) {
        var cache = source(value, function(data, raw) {
    		 var now = (self.element.val()).replace(/\s+/g, '');
   		 if (data.length === 0 || now != raw) {
   			 self.close();
   			 return;
   		 }
   		toSearch(value, data);
         });
         
         
         
         if (cache !== undefined) {
           toSearch(value, cache);
         }
         return;
    } else {
      toSearch(value, self.source);
    }
  };

  // 跳转激活项
  Autocomplete.prototype.jump = function (index) {
    var ul = this.ul,
        warp = this.warp,
        element = this.element;

    var self = this;
    var ac = 'active';
    var childer = ul.children();
    // element.blur();
    $(document.body).focus();
    if (index < 0) {
      this.index = -1;
      childer.removeClass(ac);
      warp.scrollTo(0);
      element.focus();
    }
    // 范围审查
    if (index < 0 || index >= childer.length) {
      return;
    }
    childer.eq(index).addClass(ac).siblings().removeClass(ac);
    warp.scrollTo(childer.eq(index));
    this.index = index;

    var item = self.lastData[index];
    if (item.value) {
      return item.value;
    } else {
      return item;
    }
  };
  // 选中项目, 返回此项目的文本
  Autocomplete.prototype.select = function (index) {
    var ul = this.ul,
        element = this.element;

    var self = this;
    var childer = ul.children();
    if (index === undefined) {
      index = this.index;
    }
    if (index < 0 || index >= childer.length) {
      return;
    }
    this.index = -1;
    this.close();
    var txt = childer.eq(index).text();
    element.val(txt);
    // 获得焦点
    element.focus();
    if (this.onSelect) {
      this.onSelect(txt, self.lastData[index]);
    }
    return txt;
  };
  // 绑定事件
  Autocomplete.prototype.bind = function () {
    var _this2 = this;

    var ul = this.ul,
        element = this.element;

    var self = this;
    ul.click(function (event) {
      // 查找点击的索引
      ul.children().each(function (i, element) {
        // todo 使用了自定义li后, event.target就可能是未知的了
        if (element === event.target || element === $(event.target).parents('li')) {
          _this2.select(i);
          backup = null;
          event.stopPropagation();
          event.preventDefault();
          return;
        }
      });
    });

    var backup = null;
    // todo 处理方向键, element移除焦点, 焦点进入下拉列表
/*    element.keyup(function (event) {
      backup = element.val();
      
      $('#debug-msg').val($('#debug-msg').val() + '触发键盘事件, keyCode:' + event.keyCode + 'e.which:' + event.which + '\n');
      
      var val = '';
      // 处理方向键移动焦点
      if (event.keyCode === 38) {
        val = self.jump(self.index - 1);
        element.val(val);
        event.stopPropagation();
        event.preventDefault();
        return false;
      } else if (event.keyCode === 40) {
        val = self.jump(self.index + 1);
        element.val(val);
        event.stopPropagation();
        event.preventDefault();
        return false;
      } else if (event.keyCode === 13 && self.index !== -1) {
        self.select();
        backup = null;
        event.stopPropagation();
        event.preventDefault();
        return false;
      }
      self.search(element.val());
    });*/

    
    element.bind('input', _.debounce(function(event) {
        backup = element.val();
        
        // $('#debug-msg').val($('#debug-msg').val() + '触发键盘事件, keyCode:' + event.keyCode + 'e.which:' + event.which + '\n');
        
        var val = '';
        // 处理方向键移动焦点
        if (event.keyCode === 38) {
          val = self.jump(self.index - 1);
          element.val(val);
          event.stopPropagation();
          event.preventDefault();
          return false;
        } else if (event.keyCode === 40) {
          val = self.jump(self.index + 1);
          element.val(val);
          event.stopPropagation();
          event.preventDefault();
          return false;
        } else if (event.keyCode === 13 && self.index !== -1) {
          self.select();
          backup = null;
          event.stopPropagation();
          event.preventDefault();
          return false;
        }
        self.search(element.val());
    	
    }, 300));
    
    
    
    $(document).keyup(function (event) {
      if (!self.isShow) {
        return;
      }
      var val = '';
      // 处理方向键移动焦点
      if (event.keyCode === 38) {
        val = self.jump(self.index - 1);
        element.val(val);
        event.stopPropagation();
        event.preventDefault();
        return false;
      } else if (event.keyCode === 40) {
        val = self.jump(self.index + 1);
        element.val(val);
        event.stopPropagation();
        event.preventDefault();
        return false;
      } else if (event.keyCode === 13 && self.index !== -1) {
        self.select();
        backup = null;
        event.stopPropagation();
        event.preventDefault();
        return false;
      }
      self.search(element.val());
    });

    $(document).click(function () {
      if (backup !== null) {
        // element.val(backup);
      }
      self.close();
    });
    
    
    
    
  };

  window.Autocomplete = Autocomplete;
}

$.fn.Autocomplete = function (opt) {
  // todo 之前有实例，则销毁
  if (!this.length) {
    return this;
  }
  return new window.Autocomplete(this, $.extend({}, opt));
};



/*!
 * jQuery.scrollTo
 * Copyright (c) 2007-2015 Ariel Flesler - aflesler ○ gmail • com | http://flesler.blogspot.com
 * Licensed under MIT
 * http://flesler.blogspot.com/2007/10/jqueryscrollto.html
 * @projectDescription Lightweight, cross-browser and highly customizable animated scrolling with jQuery
 * @author Ariel Flesler
 * @version 2.1.2
 */
;(function(factory) {
	'use strict';
	if (typeof define === 'function' && define.amd) {
		// AMD
		define(['jquery'], factory);
	} else if (typeof module !== 'undefined' && module.exports) {
		// CommonJS
		module.exports = factory(require('jquery'));
	} else {
		// Global
		factory(jQuery);
	}
})(function() {
	'use strict';

	var $scrollTo = $.scrollTo = function(target, duration, settings) {
		return $(window).scrollTo(target, duration, settings);
	};

	$scrollTo.defaults = {
		axis:'xy',
		duration: 0,
		limit:true
	};

	function isWin(elem) {
		return !elem.nodeName ||
			$.inArray(elem.nodeName.toLowerCase(), ['iframe','#document','html','body']) !== -1;
	}		

	$.fn.scrollTo = function(target, duration, settings) {
		if (typeof duration === 'object') {
			settings = duration;
			duration = 0;
		}
		if (typeof settings === 'function') {
			settings = { onAfter:settings };
		}
		if (target === 'max') {
			target = 9e9;
		}

		settings = $.extend({}, $scrollTo.defaults, settings);
		// Speed is still recognized for backwards compatibility
		duration = duration || settings.duration;
		// Make sure the settings are given right
		var queue = settings.queue && settings.axis.length > 1;
		if (queue) {
			// Let's keep the overall duration
			duration /= 2;
		}
		settings.offset = both(settings.offset);
		settings.over = both(settings.over);

		return this.each(function() {
			// Null target yields nothing, just like jQuery does
			if (target === null) return;

			var win = isWin(this),
				elem = win ? this.contentWindow || window : this,
				$elem = $(elem),
				targ = target, 
				attr = {},
				toff;

			switch (typeof targ) {
				// A number will pass the regex
				case 'number':
				case 'string':
					if (/^([+-]=?)?\d+(\.\d+)?(px|%)?$/.test(targ)) {
						targ = both(targ);
						// We are done
						break;
					}
					// Relative/Absolute selector
					targ = win ? $(targ) : $(targ, elem);
					/* falls through */
				case 'object':
					if (targ.length === 0) return;
					// DOMElement / jQuery
					if (targ.is || targ.style) {
						// Get the real position of the target
						toff = (targ = $(targ)).offset();
					}
			}

			var offset = $.isFunction(settings.offset) && settings.offset(elem, targ) || settings.offset;

			$.each(settings.axis.split(''), function(i, axis) {
				var Pos	= axis === 'x' ? 'Left' : 'Top',
					pos = Pos.toLowerCase(),
					key = 'scroll' + Pos,
					prev = $elem[key](),
					max = $scrollTo.max(elem, axis);

				if (toff) {// jQuery / DOMElement
					attr[key] = toff[pos] + (win ? 0 : prev - $elem.offset()[pos]);

					// If it's a dom element, reduce the margin
					if (settings.margin) {
						attr[key] -= parseInt(targ.css('margin'+Pos), 10) || 0;
						attr[key] -= parseInt(targ.css('border'+Pos+'Width'), 10) || 0;
					}

					attr[key] += offset[pos] || 0;

					if (settings.over[pos]) {
						// Scroll to a fraction of its width/height
						attr[key] += targ[axis === 'x'?'width':'height']() * settings.over[pos];
					}
				} else {
					var val = targ[pos];
					// Handle percentage values
					attr[key] = val.slice && val.slice(-1) === '%' ?
						parseFloat(val) / 100 * max
						: val;
				}

				// Number or 'number'
				if (settings.limit && /^\d+$/.test(attr[key])) {
					// Check the limits
					attr[key] = attr[key] <= 0 ? 0 : Math.min(attr[key], max);
				}

				// Don't waste time animating, if there's no need.
				if (!i && settings.axis.length > 1) {
					if (prev === attr[key]) {
						// No animation needed
						attr = {};
					} else if (queue) {
						// Intermediate animation
						animate(settings.onAfterFirst);
						// Don't animate this axis again in the next iteration.
						attr = {};
					}
				}
			});

			animate(settings.onAfter);

			function animate(callback) {
				var opts = $.extend({}, settings, {
					// The queue setting conflicts with animate()
					// Force it to always be true
					queue: true,
					duration: duration,
					complete: callback && function() {
						callback.call(elem, targ, settings);
					}
				});
				$elem.animate(attr, opts);
			}
		});
	};

	// Max scrolling position, works on quirks mode
	// It only fails (not too badly) on IE, quirks mode.
	$scrollTo.max = function(elem, axis) {
		var Dim = axis === 'x' ? 'Width' : 'Height',
			scroll = 'scroll'+Dim;

		if (!isWin(elem))
			return elem[scroll] - $(elem)[Dim.toLowerCase()]();

		var size = 'client' + Dim,
			doc = elem.ownerDocument || elem.document,
			html = doc.documentElement,
			body = doc.body;

		return Math.max(html[scroll], body[scroll]) - Math.min(html[size], body[size]);
	};

	function both(val) {
		return $.isFunction(val) || $.isPlainObject(val) ? val : { top:val, left:val };
	}

	// Add special hooks so that window scroll properties can be animated
	$.Tween.propHooks.scrollLeft = 
	$.Tween.propHooks.scrollTop = {
		get: function(t) {
			return $(t.elem)[t.prop]();
		},
		set: function(t) {
			var curr = this.get(t);
			// If interrupt is true and user scrolled, stop animating
			if (t.options.interrupt && t._last && t._last !== curr) {
				return $(t.elem).stop();
			}
			var next = Math.round(t.now);
			// Don't waste CPU
			// Browsers don't render floating point scroll
			if (curr !== next) {
				$(t.elem)[t.prop](next);
				t._last = this.get(t);
			}
		}
	};

	// AMD requirement
	return $scrollTo;
});
