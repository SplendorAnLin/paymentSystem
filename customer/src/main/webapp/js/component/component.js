(function() {


  //todo: #1 选中数据作为一个方法, 里面包含发射事件


  //判断:当前元素是否是被筛选元素的子元素或者本身 
  jQuery.fn.isChildAndSelfOf = function(b){
    return (this.closest(b).length > 0); 
  };


  // 内置过滤器
  var filter = {
    // 模糊匹配
    somevalue: function(value, data, ignoreCase) {
      var patten = new RegExp(value, ignoreCase ? 'i' : '');
      var result = data.filter(function(item, i) {
        return patten.test(item.label);
      });
      return result;
    },
    // 全字匹配
    fullword: function(value, data, ignoreCase) {
      var patten = new RegExp('^' + value + '$', ignoreCase ? 'i' : '');
      var result = data.filter(function(item, i) {
        return patten.test(item.label);
      });
      return result;
    }
  };

  // 工具函数
  var utils = {
    // 字符串模板替换
    tpl: function(tpls, data) {
      return tpls.replace(/{{(.*?)}}/g, function($1, $2) {
        return data[$2] === undefined ? $1 : data[$2];
      });
    },
    // 字符串模板替换, 属性替换, 比如disabled属性, true就添加,false就移除
    tplForAttr: function(tpls, data) {
      return tpls.replace(/{{(.*?)}}/g, function($1, $2) {
        return data[$2] ? $1.replace(/({{)|(}})/g, '') : '';
      });
    },
    // 是否为数组
    isArray: function(obj) {
      return Object.prototype.toString.call(obj) === '[object Array]';
    },
    // 是否为函数
    isFunction: function(fn) {
      return Object.prototype.toString.call(fn) === '[object Function]';
    },
    // 去除首尾空格
    trim: function(str) {
      return str.replace(/(^\s*)|(\s*$)/g, '') || '';
    },
    // 获取元素文档位置
    offset: function(ele) {
      var p = ele.offset();
      return {
        top: p.top + ele.outerHeight(),
        left: p.left
      };
    },
    // 数据规范化, 将dataSource数据源返回的数据规范化
    normalized: function(data) {
      // 参数检查
      if (!utils.isArray(data) || data.length == 0)
        return [];

      var result = [];

      for (var i = 0; i < data.length; ++i) {
        var item = data[i];
        // 数据项是对象还是单个值
        var single = item.label === undefined;
        var label = single ? item : (item.label || '');
        var norm = {
          label: label,
          value: item.value || label
        };
        result.push($.extend({}, item, norm));
      }

      return result;
    }
  };


  var AutoComplete = function(input, opt) {
    var options = {
      // 数据源
      dataSource: [],
      // 插入位置
      appendTo: null,
      // 敲击延迟, 对于本地数据推荐0延迟
      delay: 0,
      // 是否禁用
      disabled: false,
      // 最小长度, 比如匹配银行卡号, 输入11位数时才进行请求
      minLength: 0,
      // 列表最大显示行
      maxListCount: 6,
      // 是否不计算剩余高度
      notAvailable: false,
      // 过滤函数, 内置无过滤, 模糊匹配, 全字匹配, 默认的过滤器都支持是否大小写参数
      filter: filter.somevalue,
      // 是否清除前后空格
      istrim: true,
      // 是否忽略大小写
      ignoreCase: true,
      // 是否空白匹配所有
      allowEmpy: false,
      // item选项模板
      tmp: '<li index="{{index}}" title="{{label}}" value="{{value}}">{{label}}</li>'
    };
    if (!input)
      return;
    // 扩展参数
    this.options = $.extend({}, options, opt);
    this.ref = input;
    this.element = null;
    // 是否打开下拉列表
    this.ishow = false;
    // 当前搜寻到的数据
    this.data = null;
    // 初始化
    this.init();
  };

  AutoComplete.prototype.init = function() {
    var ref = this.ref;
    if (!ref || !ref[0])
  		return;
    if (ref[0].autoComplete)
      ref[0].autoComplete.destroy();
    // 禁用原生自动填充
    ref.attr('autocomplete', 'off');
    ref.attr('data-visibility', 'hide');
    ref.attr('data-select', '');
    // 绑定事件
    this.bind();
    ref[0].autoComplete = this;
    AutoComplete.add(this);
  };
  AutoComplete.prototype.destroy = function() {
    this.hide();
    this.unbind();
    AutoComplete.remove(this);
  };
  AutoComplete.prototype.show = function() {
    var element = this.element;
    if (this.ishow || !element)
      return;

    this.ref.attr('data-visibility', 'show');
    element.show(0);
    this.ishow = true;
    this.ref.trigger(AutoComplete.Event.show, this);
  };
  AutoComplete.prototype.hide = function(trigger) {
    var element = this.element;
    if (!this.ishow )
      return;
    if (element)
      element.remove();

    this.ref.attr('data-visibility', 'hide');
    this.element = null;
    this.ishow = false;
    if (!trigger)
      this.ref.trigger(AutoComplete.Event.hide, this);
  };
  AutoComplete.prototype.bind = function() {
    var ref = this.ref;

    this.debounce_hander_input = _.debounce(this.hander_input, this.options.delay);
    ref.bind('input', this, this.debounce_hander_input);
    ref.bind('keydown', this, this.hander_keyup);
  };
  AutoComplete.prototype.unbind = function() {
    var ref = this.ref;
    ref.unbind('input', this.debounce_hander_input);
    ref.unbind('keyup', this.hander_keyup);
  };
  // 搜寻值,显示匹配下拉列表
  AutoComplete.prototype.search = function(value, _data) {
    var ref = this.ref;
    var options = this.options;
    var filter = options.filter;
    var options = this.options;
    // 过滤值
    var val = this.filter(value || ref.val());
    if (options.disabled || val.length < options.minLength || (!options.allowEmpy && val === '')) {
      // 销毁上一个存在的下拉列表
      this.hide();
      return;
    }

    // 获取数据
    var data = _data || this.get();
    // 过滤数据, 得到最终要显示的数据
    var d = filter(val, data, options.ignoreCase);

    // 创建下拉列表
    this.createList(d);
    // 显示下拉列表
    this.show();
  };
  // 值过滤器
  AutoComplete.prototype.filter = function(value) {
    var options = this.options;
    var val = value;

    // 去除首尾空格
    if (options.istrim) {
       val = utils.trim(val);
    }
    return val;
  };
  // 根据数据源获取数据
  AutoComplete.prototype.get = function() {
    var self = this;
    var dataSource = this.options.dataSource;
    // dataSource=数组
    if (utils.isArray(dataSource)) {
      return utils.normalized(dataSource);
    }
    // dataSource=函数
    if (utils.isFunction(dataSource)) {
      dataSource(self.ref.val(), this, function(asyndata, raw) {
        // asyndata=通过数据源返回的数据, raw当初查询的依据值
        // 如果 当初查询值:异步返回结果 不匹配 当前输入框的值, 则抛弃这个数据,
        if (self.filter(self.ref.val()) === raw) {
          self.search(raw, utils.normalized(asyndata));
        }
      });
      return [];
    }
    console.warn('AutoComplete.options.dataSource 不是一个合法数据源类型!');
    return [];
  };
  // 选中一个下拉列表
  AutoComplete.prototype.select = function(index) {
    var data = this.data;
    var ref = this.ref;
    if (!utils.isArray(data) || data.length === 0)
      return;

    if (index < 0 || index >= data.length) {
      console.warn('AutoComplete.select 选择失败, 索引越界:', index, data);
      return;
    }

    // 当前选中数据
    var current = data[index];
    // 将值填入
    ref.val(current.label);
    ref.attr('data-select', current.label);
    // 发射事件
    ref.trigger(AutoComplete.Event.select, current, self);
    this.hide(true);
    this.data = null;

  };
  // 创建下拉列表
  AutoComplete.prototype.createList = function(data) {
    if (data.length <= 0)
      return;
    var self = this;
    var options = this.options;
    var ref = this.ref;
    var element = $('<div class="AutoComplete-wrap"></div>').hide().data('index', -1).data('data', data);
    var ul = $('<ul></ul>');
    var lis = $();
    self.data = data;
    self.ref.trigger(AutoComplete.Event.beginShow, self);

    for (var i = 0; i < data.length; ++i) {
      var item = data[i];
      var li = $(utils.tpl(options.tmp, $.extend({}, {index: i}, item)));
      // 绑定附加数据
      li.data('attached', item);
      li.data('index', i);
      ul.append(li);
      lis = lis.add(li);
    }
    element.append(ul);

    // 插入或替换下拉列表
    if (options.appendTo === null) {
      self.append(element, ref, self.ishow, data);
    } else {
      if (self.ishow) {
        options.appendTo.replaceWith(element);
      } else {
        options.appendTo.append(element);
      }
    }

    // 绑定选择事件
    lis.click(function() { self.select($(this).data('index'));});
    this.element = element;
  };
  // 默认插入下拉列表
  AutoComplete.prototype.append = function(element, ref, isReplace, data) {
    // 计算剩余高度

    // notAvailable
    var availableHeight = ($(window).height()  + $(window).scrollTop()) - (ref.offset().top + ref.outerHeight());
    var pos = utils.offset(ref);
    var maxListCount = Math.min(this.options.maxListCount, data.length);
    // 先插入到文档中, 以便获取单条项的高度
    $(document.body).append(element.show());
    var itemHeight = $('li', element).eq(0).outerHeight();
    element.css({
      display: 'block',
      position: 'absolute',
      top: pos.top,
      left: pos.left,
      height: maxListCount * itemHeight,
      'max-height': this.options.notAvailable ? maxListCount * itemHeight : availableHeight,
      'width': ref.outerWidth(),
      'overflow': 'auto',
      'overflow-y': 'auto',
      'overflow-x': 'hidden'
    });
    if (isReplace) {
      this.element.replaceWith(element);
    } else {
      $(document.body).append(element);
    }
    setTimeout(function() {
      element.css({
        'overflow': 'auto',
        'overflow-y': 'auto',
        'overflow-x': 'hidden'
      });
    }, 10);
  };
  // 事件控制-input
  AutoComplete.prototype.hander_input = function(event) {
    var autocomplete = event.data;
    var val = $(this).val();
    autocomplete.ref.attr('data-select', '');
    autocomplete.search(val);
    
  }
  // 事件控制-keyup
  AutoComplete.prototype.hander_keyup = function(event) {
    var autocomplete = event.data;
    if (!autocomplete.ishow)
      return;
    var index = autocomplete.element.data('index');
    var data = autocomplete.element.data('data');
    var li = $('ul', autocomplete.element).children();
    
    function jump(i) {
      // 检测越界
      if (i < 0 || i >= data.length)
        return;

      // todo: 被激活元素需要定位, 移动父元素滚动条
      li.eq(i).addClass('active').siblings().removeClass('active');
      li.eq(i)[0].scrollIntoView(false);
      autocomplete.element.data('index', i);
      event.stopPropagation();
    }

    var key = event.keyCode;
    switch (key) {
      case 38:
        jump(index - 1);
      break;
      case 40:
        jump(index + 1);
      break;
      case 13:
        autocomplete.select(index);
        event.stopPropagation();
        return false;
      default:
        break;
    }
  }

  // 静态
  AutoComplete.autocompletes = [];
  // 选择事件
  AutoComplete.Event = {
    'select': 'SELECT',
    'show': 'SHOW',
    'hide': 'HIDE',
    'beginShow': 'BeginShow'
  };
  AutoComplete.add = function(autocomplete) {
    AutoComplete.autocompletes.push(autocomplete);
  };
  AutoComplete.remove = function(autocomplete) {
    var index = -1;
    $.each(AutoComplete.autocompletes, function(i) {
      if (this === autocomplete)
        index = i;
    });
    if (index !== -1)
      AutoComplete.autocompletes.splice(index, 1);
  };
  AutoComplete.hander_click = function(event) {
    var target = $(event.target);
    $.each(AutoComplete.autocompletes, function() {
      if (!target.isChildAndSelfOf(this.ref) && !target.isChildAndSelfOf(this.element)) {
        this.hide();
      }
    });
  };

  // 空白出点击
  $(document.body).click(AutoComplete.hander_click);
  window.AutoComplete = AutoComplete;
})();
/// <reference path="D:\资料\XueYou\DefinitelyTyped\jquery\jquery.d.ts" />
(function() {
  var template = '\
  <div class="drawer">\
    <div class="obfuscator"></div>\
    <div class="content">\
    </div>\
  </div>\
  ';
  var Drawer = function(element) {
    if (!element || element.length !== 1) {
      console.warn('Drawer: 初始化失败, element不是一个jQuery对象!', element);
      return;
    }
    this.element = element;
    this.init();
    this.bind();
  };
  Drawer.prototype.init = function() {
    var element = this.element;
    // 创建抽屉元素
    this.drawer = $(template);
    this.drawer.css('left', '-100%');
    // 背景遮罩
    this.obfuscator = $('.obfuscator', this.drawer);
    // 内容盒子
    this.contentBox = $('.content', this.drawer);
    this.contentBox.append(element);
    element.css({
      'box-sizing': 'border-box',
      'height': '100%',
    });
    // 插入页面
    $(document.body).append(this.drawer);
  };
  Drawer.prototype.bind = function() {
    var self = this;
    var obfuscator = this.obfuscator;
    obfuscator.click(function() {
      self.hide();
    });
  };
  Drawer.prototype.show = function() {
    var drawer = this.drawer;
    drawer.css('left', '0');
    drawer.addClass('is-visible');
  };
  Drawer.prototype.hide = function() {
    var drawer = this.drawer;
    drawer.removeClass('is-visible');
    setTimeout(function() {
      drawer.css('left', '-100%');
    }, 300);
    
  };
  Drawer.prototype.toggle = function() {
    var drawer = this.drawer;
    if (drawer.hasClass('is-visible')) {
      this.hide();
    } else {
      this.show();
    }
  };

  $.fn.Drawer = function() {
    return new Drawer(this);
  };
})();

// Dropdown 下拉列表
(function() {
  var Dropdown = function(element, options) {
    var defaultOption = {
      // 下拉内容元素 todo 支持选择器
      target: null,
      // 下拉方向
      direction: 'down',
      // 水平方向
      levelDirection: 'left',
      // 边距偏移
      offset: 0,
      // 是否自动关闭
      autoClose: true,
      // 是否关闭默认样式
      noSkin: false || element.attr('noSkin'),
      // 事件类型, 点击或者鼠标悬浮(mouseenter)
      trigger: 'click',
      // 切换样式, 显示和隐藏时element样式不同
      style: [
        'Dropdown-on',
        'Dropdown-off'
      ]
    };
    this.options = $.extend({}, defaultOption, options);
    this.element = element;
    if (typeof this.options.target === 'string') {
      this.options.target = $(this.options.target);
    }
    if (!element || element.length !== 1 || !this.options.target || this.options.target.length !== 1 ) {
      console.warn('元素不存在或不是jQuery对象!');
      return;
    }
    this.bind();
  };
  Dropdown.prototype.open = function() {
    var self = this;
    var options = self.options;
    var element = self.element;
    var target = options.target;
    var style = options.style;
    if (self.wrap || self.closeing) {
      return;
    }
    self.origin = target.parent();
    self.orginStyle = target.attr('style');
    // 方向
    var direction = options.direction;
    // 获取尺寸
    var width = target.outerWidth();
    var height = target.outerHeight();
    // 获取偏移距离
    var offset = element.offset();
    // 生成包裹
    var wrap = $('<div class="dropdown-content ' + (direction === 'down' ? 'dropdown-down' : 'dropdown-up') + '"></div>');
    if (options.noSkin) {
      wrap.addClass('noSkin');
    }
    self.wrap = wrap;
    var left = offset.left;
    if (options.levelDirection == 'right') {
      left = offset.left + element.outerWidth();
    }
    // 如果溢出到视窗外, 则减去差值
    var maxWidth = $(window).width();
    if ((left + width) > maxWidth) {
      left = left - ((left + width) - maxWidth);
    }

    if (options.levelDirection == 'left') {
      left += options.offset;
    }
    if (options.levelDirection == 'right') {
      left -= options.offset;
    }
    window.hotDropdown = self;
    wrap.css({
      // 减去10px的箭头偏移, 让下拉框的箭头与触发元素对齐
      left: left - 10,
      top: direction === 'down' ? offset.top + element.outerHeight() + 10 : offset.top - 10 - height,
      bottom: 'auto'
    });
    wrap.append(target.css('display', 'block'));
    $(document.body).append(wrap);
    element.removeClass(style[1]).addClass(style[0]);
    setTimeout(function() {
      wrap.addClass('in');
    }, 10);
  };
  Dropdown.prototype.close = function() {
    var self = this;
    var element = self.element;
    var options = self.options;
    var target = options.target;
    var wrap = self.wrap;
    var style = options.style;
    if (!wrap || self.closeing) {
      return;
    }
    self.closeing = true;
    element.removeClass(style[0]).addClass(style[1]);
    wrap.removeClass('in').addClass('out');
    setTimeout(function() {
      self.closeing = false;
      target.attr('style', self.orginStyle);
      self.origin.append(target);
      wrap.remove();
      self.wrap = null;
    }, 300);
    // console.log('隐藏', options.target);
  };
  Dropdown.prototype.toggle = function() {
    var self = this;
    var options = self.options;
    var target = options.target;
    if (self.closeing) {
      return;
    }
    if (self.wrap) {
      self.close();
    } else {
      self.open();
    }
  };
  Dropdown.prototype.bind = function() {
    var self = this;
    var element = self.element;
    var options = self.options;
    var leave = true;
    element.bind(options.trigger, function(event) {
      self.leave = false;

      if (options.trigger === 'mouseenter' && self.wrap) {
        // 鼠标移到target上在移入回来, 则不切换显示/关闭
      } else {
        self.toggle();
      }
      event.preventDefault();
      event.stopPropagation();
    });

    if (!options.autoClose) {
      return;
    }

    if (options.trigger === 'click') {
      // 出了点击触发内容, 点击其他空白处都关闭
      options.target.click(function(event) {
        event.preventDefault();
        event.stopPropagation();
        //console.log('阻止冒泡');
        return false;
      });
      $(document.body).click(function(event) {
        if (!event.isDefaultPrevented()) {
          //console.log('点击空白处, 关闭');
          self.close();
        }
      });
    } else {
      // 鼠标离开时关闭
      element.bind('mouseleave', function() {
        self.leave = true;
        setTimeout(function() {
          if (self.leave === true) {
            self.close();
          }
        }, 350);
      });
      // 显示目标进入
      options.target.bind('mouseenter', function() {
        self.leave = false;
      });
      // 显示目标离开
      options.target.bind('mouseleave', function() {
        self.leave = true;
        setTimeout(function() {
          if (self.leave === true) {
            self.close();
          }
        }, 350);
      });
    }
  };

  $('[data-dropdown]').each(function() {
    var target = $($(this).attr('data-dropdown'));
    new Dropdown($(this), {
      // 下拉内容元素 todo 支持选择器
      target: target,
      // 下拉方向
      direction: 'down',
      // 是否自动关闭
      autoClose: true,
      // 事件类型, 点击或者鼠标悬浮(mouseenter)
      trigger: 'click',
      // 切换样式, 显示和隐藏时element样式不同
      style: [
        'Dropdown-on',
        'Dropdown-off'
      ]
    });
  });


  $.fn.Tooltip = function() {
    this.each(function() {
      var id = $(this).attr('id');
      var target = $('[for=' + id + ']').addClass('tooltip').hide();
      new Dropdown($(this), {
        // 下拉内容元素 todo 支持选择器
        target: target,
        // 下拉方向
        direction: 'down',
        // 是否自动关闭
        autoClose: true,
        // 是否关闭默认样式
        noSkin: true,
        // 事件类型, 点击或者鼠标悬浮(mouseenter)
        trigger: 'mouseenter',
        // 切换样式, 显示和隐藏时element样式不同
        style: [
          'Dropdown-on',
          'Dropdown-off'
        ]
      });
    });
  };

  window.Dropdown = Dropdown;
  $.fn.Dropdown = function(option) {
    return new Dropdown(this, option);
  };
})();

// 根据 Dropdown封装的菜单
(function() {
  $('[data-Menus]').each(function() {
    var target = $($(this).attr('data-Menus')).hide();
    var trigger = $(this).attr('trigger');
    var keep = $(this).attr('keep');
    var levelDirection = $(this).attr('levelDirection') || 'left';
    var offset = parseFloat($(this).attr('offset')) || 0;
    // 自动将菜单的 data-for 设置为菜单项 li
    $('[type=checkbox]', target).each(function() {
      var element = $(this);
      element.data('data-for', element.parents('li'));
    });

    var dropdown = new window.Dropdown($(this), {
      // 下拉内容元素 todo 支持选择器
      target: target,
      // 下拉方向
      direction: 'down',
      // 是否自动关闭
      autoClose: true,
      // 事件类型, 点击或者鼠标悬浮(mouseenter)
      trigger: trigger || 'click',
      // 水平方向
      levelDirection: levelDirection,
      // 偏移
      offset: offset,
      // 切换样式, 显示和隐藏时element样式不同
      style: [
        'Dropdown-on',
        'Dropdown-off'
      ]
    });

    if (keep) {
      $('li', target).click(function() {
        dropdown.close();
      });
    }
  });
})();
(function() {
  // html模板
  var template = '\
    <div class="message-wrap">\
      <div class="message-content">\
        <div class="message-html">\
          <div class="ib h-p-100">\
            <span class="middle"></span>\
            <i class="fa icon fa-fw"></i>\
          </div>\
          <div class="conten-wrap ib h-p-100"><span class="middle"></span><div class="txt-middle ib"></div>\
          </div>\
        </div>\
        <i class="fa fa-times close"></i>\
      </div>\
    </div>\
  ';

  // 消息类型数组
  var message_type = [
    'fa-info-circle',
    'fa-check-circle',
    'fa-exclamation-circle',
    'fa-times-circle',
    'fa-spinner'
  ];
  // 图标类型对应颜色
  var color = [
    '#0099e5',
    '#1fa67a',
    '#FF9900',
    'red',
    '#84b6e8'
  ];
  var Message = function(config) {
    var self = this;
    var defaultConfig = {
      // 消息顶部距离
      top: 7,
      // 自动关闭延时, 0=不关闭
      duration: Message.duration,
      // 提示内容
      content: '<p></p>',
      // 消息类型
      type: 0,
      // 关闭回调
      onClose: null
    };

    this.config = $.extend({}, defaultConfig, config);
    // 根据模板创建dom
    this.message = $(template);
    // 获取内容区域
    this.content = $('.txt-middle', this.message);
    // 获取图标区域
    this.icon = $('.icon', this.message);
    // 当前消息类型
    var currentType = (this.config.type && this.config.type < message_type.length) ? this.config.type : 0;
    // 设置图标类型
    this.icon.addClass(message_type[currentType]);
    // 根据图标设置不同颜色
    this.icon.css({
      'color': color[currentType],
    });
    // 如果类型为等待则加上旋转属性
    if (currentType === 4) {
      this.icon.addClass('fa-spin');
    }
    // 设置内容
    if (typeof(this.config.content) === 'string')
      this.config.content = '<p>' + this.config.content + '</p>';
    this.content.append($(this.config.content).clone());
    // 监听关闭
    $('.close', this.message).click(function() {
      self.destroy();
    });
    // 插入DOM
    $(document.body).append(this.message);
    // 开始显示消息
    this.message.css({
      'top': '0',
      'opacity': '0'
    });
    this.message.animate({
      'top': this.config.top || '7px',
      'opacity': '1'
    });
    if (this.config.duration !== 0) {
      self.timeHandler = setTimeout(function() {
        self.destroy();
      }, self.config.duration);
    }
  };
  Message.duration = 3000;
  window.Message = Message;

  Message.prototype.destroy = function() {
    var self = this;
    clearTimeout(self.timeHandler);
    self.message.animate({
      'top': '0',
      'opacity': '0'
    }, 'fast', function() {
      self.message.remove();
      if ($.isFunction(self.config.onClose)) {
        self.config.onClose();
      }
    });
  };


  window.Messagesed = {};
  window.Messagesed.info = function(msg) {
    var m = new Message({
      // 消息顶部距离
      top: 7,
      // 自动关闭延时, 0=不关闭
      duration: Message.duration,
      // 提示内容
      content: '<p>' + msg + '</p>',
      // 消息类型
      type: 0,
      // 关闭回调
      onClose: null
    });
  };

  window.Messagesed.success = function(msg) {
    var m = new Message({
      // 消息顶部距离
      top: 7,
      // 自动关闭延时, 0=不关闭
      duration: Message.duration,
      // 提示内容
      content: '<p>' + msg + '</p>',
      // 消息类型
      type: 1,
      // 关闭回调
      onClose: null
    });
    return m;
  };

  window.Messagesed.warn = function(msg) {
    var m = new Message({
      // 消息顶部距离
      top: 7,
      // 自动关闭延时, 0=不关闭
      duration: Message.duration,
      // 提示内容
      content: '<p>' + msg + '</p>',
      // 消息类型
      type: 2,
      // 关闭回调
      onClose: null
    });
    return m;
  };

  window.Messagesed.error = function(msg) {
    var m = new Message({
      // 消息顶部距离
      top: 7,
      // 自动关闭延时, 0=不关闭
      duration: Message.duration,
      // 提示内容
      content: '<p>' + msg + '</p>',
      // 消息类型
      type: 3,
      // 关闭回调
      onClose: null
    });
    return m;
  };

  window.Messagesed.wait = function(msg) {
    var m = new Message({
      // 消息顶部距离
      top: 7,
      // 自动关闭延时, 0=不关闭
      duration: Message.duration,
      // 提示内容
      content: '<p>' + msg + '</p>',
      // 消息类型
      type: 4,
      // 关闭回调
      onClose: null
    });
    return m;
  };

  
})();

(function() {

  //判断:当前元素是否是被筛选元素的子元素或者本身 
  jQuery.fn.isChildAndSelfOf = function(b){
    return (this.closest(b).length > 0); 
  };

  // 是否为数组
  function isArray(array) {
    return Object.prototype.toString.call(array) === '[object Array]';
  }

  // 监听元素尺寸变化
  var listenResize = function(element, fuc) {
    if (!$.isFunction(fuc)) {
      return;
    }
    // 上一次高度宽度
    var width = element.outerWidth();
    var height = element.outerHeight();
    var timehandle = setInterval(function() {
    	// 修复iframe对话框中href跳转导致一瞬间尺寸为0的问题
    	if (element.outerWidth() == 0 || element.outerHeight() == 0)
    		return;
      if (element.outerWidth() !== width || element.outerHeight() !== height) {
        fuc(timehandle, element);
        width = element.outerWidth();
        height = element.outerHeight();
      }
    }, 10);
    return timehandle;
  };



  var utils = {
    // 加载iframe
    loadIframe: function(iframe, url, callback) {
      // 参数检查
      if (!iframe || iframe.length < 1 || !url)
        return;
      iframe.attr('src', url);
      utils.listenIframe(iframe, callback);
    },
    // 监听iframe
    listenIframe: function(iframe, callback) {
      // 参数检查
      if (!iframe || iframe.length < 1)
        return;
      var url = iframe.attr('url');
      // 标志, 是否加载
      var status = null;
      // 普通load事件
      setTimeout(function() {
        
        // 处理error事件
        iframe.error(function() {
          status = false;
          console.warn('utils.loadIframe: 加载iframe失败,', url);
          callback(status);
        });

        // 处理load事件
        iframe.load(function() {
          try {
            // 判断框架内是否为空/或者有异常模板
            var count = iframe.contents().find('head').children().length;
            var errorExpression = iframe.contents().find('h2.demoHeaders').text() == ('Exception / '+'Error');
            status = (count !== 0 && !errorExpression);
          } catch(err) {
            status = false;
            console.warn('utils.loadIframe: 加载iframe事件, 网络异常或跨域!', url);
          }
          callback(status);
        });
      }, 10);

      // 处理跨域或网络异常事件
      setTimeout(function() {
        if (status !== null)
          return;
        try {
          // IE下接异常来判断是否加载完毕
          iframe[0].contentWindow.document;
        } catch (error) {
          status = false;
          callback(status);
          console.warn('utils.loadIframe: 加载iframe事件, 网络异常或跨域!', url);
        }
      }, 100);
    },
    // 反转数组
    reverse: function(array) {
      var result = [];
      for (var i = array.length - 1; i >= 0; --i) {
        result.push(array[i]);
      }
      return result;
    }
  };



  // 对话框HTML
  var html = '\
    <div class="MyDialog-wrap full-size">\
      <div class="dialog full-size">\
        <div class="dialog-box">\
        </div>\
      </div>\
    </div>\
  ';

  // 页脚按钮对象
  var FooterBtn = function(btn, type) {
    var btnStrcut = {
      // 按钮文本 (必须)
      lable: null,
      // 触发内部元素选择器
      trigger: '.submit',
      // click事件附加数据
      data: {},
      // 按钮单击事件
      click: null
    };
    // 按钮类型
    this.type = type || 2;
    // 合并参数
    this.strcut = $.extend({}, btnStrcut, btn);
    // 检测参数
    if (!this.strcut.lable) {
      console.warn('创建按钮失败, 按钮文本参数必填', btn);
    }
  };
  FooterBtn.prototype.create = function(average) {
    average = average || 100;
    var btn = this.strcut;
    var button;
    if (this.type == 1) {
      button = $('<a class="mr-10 btn-1 dialog-btn" href="javascript:void(0);">' + btn.lable + '</a>');
    } else {
      button = $('<a href="javascript:void(0);" style="width: ' + average + '%;" class="text btn-2 dialog-btn">' + btn.lable + '</a>');
    }
    return button;
  };

  // 普通对话框
  var MyDialog = function(options) {
    // 默认参数
    var option = {
      // 显示目标, jQuery对象 | iframe地址 [必填]
      target: null,
      // 页脚按钮, jQuery对象 | 按钮数组
      btns: null,
      // 页脚按钮类型
      btnType: 1,
      // 页眉标题, jQuery对象 | 标题字符串
      title: null,
      // 对话框宽度
      width: null,
      // 对话框高度
      height: null,
      // 是否显示背景遮罩
      mask: true,
      // 是否可拖拽
      drag: false,
      // 是否模态对话框
      isModal: true,
      // 事件-打开对话框
      onOpen: null,
      // 事件-关闭对话框
      onClose: null
    };
    // 合并参数
    this.options = $.extend(option, options);
    // 禁用状态
    this.disabled = false;
    // 对话框ID
    this.id = null;
    // 计时器句柄
    this.timeHandler = null;
    this.create();
    this.bind();
  };
  // 创建对话框元素
  MyDialog.prototype.create = function() {
    var self = this;
    var options = self.options;
    var target = options.target;
    // 参数检查
    if (!self.testParame())
      return;
    // 分配对话框ID
    self.id = MyDialog.add(self);
    // 创建对话框元素
    self.modal = $(html).css('z-index', self.id + 99).hide();
    self.dialogFull = $('.dialog', self.modal);
    self.dialog = $('.dialog-box', self.modal);

    // 创建遮罩
    if (options.mask) {
      self.mask = $('<div class="mask full-size"></div>');
      if (!options.isModal) {
        self.dialogFull.click(function(event) {
          if (event.target === self.dialogFull.get(0))
            self.close();
        });
      }
      self.modal.append(self.mask);
    }

    // 创建页眉
    if (options.title) {
      self.header = $('<div class="dialog-header floatWidth"></div>');
      if (typeof(options.title) === 'string') {
        var title = $('<div class="p-title"><p class="text">' + options.title + '</p><a href="javascript:void(0);" class="dialog-close close">×</a></div>');
        self.header.append(title);
      } else {
        self.header.append(options.title);
      }
      self.dialog.append(self.header);
    }

    // 创建内容
    self.body = $('<div class="body"></div>');
    self.body.append(self.createContent());
    self.dialog.append(self.body);

    // 创建页脚按钮
    if (options.btns) {
      self.footer = $('<div class="footer floatWidth"></div>');
      if (isArray(options.btns)) {
        self.footer.append(self.createBtns(options.btns));
      } else {
        self.footer.append(options.btns);
      }

      self.dialog.append(self.footer);
    }
    // 加入dom
    $(document.body).append(self.modal);
    // 加载内容
    self.loadContent();
  };

  // 创建内容 [可重载]
  MyDialog.prototype.createContent = function() {
    var self = this;
    var options = self.options;
    var target = options.target;

    // 获取原始样式
    self.originStyle = target.attr('style');
    // 获取target原来所在对象, 销毁对话框后还原给他
    self.origin = target.parent();
    // 内容元素
    self.content = options.target.css('display', 'block');
    return self.content;
  };

  // 加载内容 [可重载]
  MyDialog.prototype.loadContent = function() {
    // 给内容增加标志, 内容中的代码通过寻找这个标志来获取对话框实例
    this.content.attr('dialog-id', this.id).addClass('dialog-content-flag');
    this.show();
  };

  // 参数检查 [可重载]
  MyDialog.prototype.testParame = function() {
    var target = this.options.target;
    return (target && target.length === 1);
  };

  // 设置尺寸 [可重载]
  MyDialog.prototype.setSize = function() {
    this.refSize();
  };

  // 设置宽度/高度并排除页眉/页脚边距
  MyDialog.prototype.refSize = function() {
    var self = this;
    var options = self.options;

    // 设置宽度
    if (options.width) {
      self.dialog.css('width', options.width);
      self.content.css('width', '100%');
    }
    if (self.header) {
      self.dialog.css('padding-top', $(self.header).outerHeight());
    }
    if (self.footer) {
      self.dialog.css('padding-bottom', $(self.footer).outerHeight());
    }

    if (options.height) {
      if (options.height.indexOf('%') == -1) {
        self.content.css('height', options.height);
      } else {
        self.dialog.css('height', options.height);
      }
    }
  };

  // 检测是否高度溢出, 需要滚动条
  MyDialog.prototype.available = function() {
    var self = this;
    var content = self.content;
    // 内容高度
    var height = self.content.outerHeight();
    // 窗口宽度
    var maxHeight = $(window).outerHeight();
    // 页眉高度
    var headerHeight = $(self.header).outerHeight();
    // 页脚高度
    var footerHeight = $(self.footer).outerHeight();


    // 处理iphone下无论iframe还是普通对话框内容宽度超过屏幕看宽度, 则对话框被挤不挤见了
    // self.content.css('max-width', $(window).outerWidth() - 10);

    if ( (height) >= (maxHeight - headerHeight - footerHeight)) {
      self.dialog.css('height', '100%');
    } else {
      self.dialog.css('height', '');
    }

  };

  // 设置位置 [可重载]
  MyDialog.prototype.resize = function() {
    this.available();
  };

  // 关闭对话框
  MyDialog.prototype.close = function(callback, forced, errInfo) {
    var self = this;
    var options = self.options;
    // 禁用状态下不允许关闭
    if (self.disabled && forced === undefined) {
      return;
    }
    if (forced) {
      self.loading(false);
    }

    self.hide(function() {
      // 移除内容尺寸计时器
      self.listenSize(false);
      // 移除拖拽
      if (self.dialog && window.Drag) {
        Drag.remove(self.dialog.get(0));
      }
      // 钩子事件
      if ($.isFunction(options.onClose)) {
        options.onClose(self, self.content);
      }
      if ($.isFunction(MyDialog.close))
        MyDialog.close(self, errInfo);
      if ($.isFunction(callback))
        callback(self);
      // 还原target
      if (typeof(options.target) !== 'string')
        options.target.attr('style', self.originStyle);
      if (self.origin)
        self.origin.append(self.content);
      // 删除iframe对话框
      delete MyDialog.iframe[options.target];
      // 从对话框集合移除
      MyDialog.remove(self);
      self.unbind();
      self.modal.remove();
    });
  };

  // 显示对话框
  MyDialog.prototype.show = function() {  
    var self = this;
    var content = self.content;
    var options = self.options;
    // 隐藏body滚动条
    self.overflow = $(document.body).css('overflow');
    $(document.body).addClass('dialog-active').css('overflow', 'hidden');
    // 显示对话框
    self.modal.show(0);
    if (self.mask)
    	self.mask.show(0);
    self.dialogFull.show(0);

    // 设置尺寸
    self.setSize();
    self.resize();

    MyDialog.current = self;
    self.listenSize(true);
    // 一定要对话框显示完毕后在调用此事件, 才能获取到正确高度
    setTimeout(function() {
      if (self.mask)
      	self.mask.addClass('active');
      self.dialogFull.addClass('active');

      var win = self.iframe ? self.iframe[0].contentWindow : window;
     if ($.isFunction(win.DialogLoad))
        win.DialogLoad(self, content);
      $(win).trigger(MyDialog.Event.open, 'a');
      if ($.isFunction(options.onOpen)) {
        options.onOpen(self, self.content);
      }
    }, 10);
    self.setDrag();
    if ($.isFunction(MyDialog.open))
      MyDialog.open(self, self.content);
  }

  // 隐藏对话框
  MyDialog.prototype.hide = function(backcall) {
    var self = this;

    self.dialogFull.removeClass('active');
    setTimeout(function() {
      if (self.mask)
        self.mask.removeClass('active');
      self.modal.hide();
      // 还原body样式
      $(document.body).removeClass('dialog-active').css('overflow', self.overflow === 'visible' ? 'inherit' : self.overflow);
      if ($.isFunction(backcall))
        backcall();
    }, 150);
  };

  // 设置对话框拖拽
  MyDialog.prototype.setDrag = function() {
    var self = this;
    var content = self.content;
    var options = self.options;

    if (options.drag && window.Drag && self.header) {
      // 设置拖拽鼠标手型
      $('.p-title', self.header).css('cursor', 'move');
      // 设置背景层鼠标穿透
      if (!options.mask)
        self.dialogFull.css('pointer-events', 'none');
      // 新打开对话框zindex没有上一个拖拽9999那么高的层级
      // self.modal.css('z-index', '9999');
      Drag.add(self.dialog.get(0), function(ele, target) {
        // 获取 上一个对话框的z-index 并还原 z-index
        if (MyDialog.befor && MyDialog.befor !== self) {
          var zindex = MyDialog.befor.modal.data('zindex');
          MyDialog.befor.modal.css('z-index', zindex);
        }
        // 备份z-index
        var targetZindex = self.modal.data('zindex');
        if (!targetZindex)
          self.modal.data('zindex', self.modal.css('z-index'));
        // 当前对话框最顶层 z-index
        self.modal.css('z-index', '99');
        var exist = $(target).isChildAndSelfOf(self.header);
        // 设置当前激活对话框
        if (exist)
          MyDialog.befor = self;
        return exist;
      });
    };


  };

  // 创建页脚按钮
  MyDialog.prototype.createBtns = function(btns) {
    var self = this;
    var type = self.options.btnType;
    var btnWrap = $('<div class="Alert-footer"></div>');
    if (type === 1) {
      // 由于使用float:right; 所以按钮数组逆序排序
      btns = utils.reverse(btns);
      btnWrap.removeClass('Alert-footer').addClass('btn-footer').addClass('fix');
    }
    var average = 100 / btns.length;
    $.each(btns, function() {
      var btn = this;
      var footerBtn = new FooterBtn(btn, type);
      var button = footerBtn.create(average);
      // 取消按钮的样式反转颜色
      if (btn.lable == '取消') {
        button.addClass('reverse');
      }
      // 监听按钮事件
      button.click(function() {
        if (self.disabled || button.attr('disabled') === 'disabled')
          return;

        if ($.isFunction(btn.click)) {
          btn.click(btn.data, self.content);
        } else if (btn.trigger){
          $(btn.trigger, self.content).trigger('click', self);
        } else {
          self.close();
        }
      });
      btnWrap.append(button);
    });
    return btnWrap;
  };

  // 禁用对话框
  MyDialog.prototype.disable = function(disabled) {
    var self = this;
    self.disabled = disabled === undefined ? true : disabled;
    if (self.disabled) {
      // 找到页脚按钮并禁用
      $('.dialog-btn', self.footer).attr('disabled', 'disabled').addClass('disabled');
    } else {
      // 找到页脚按钮解除禁用
      $('.dialog-btn', self.footer).removeAttr('disabled').removeClass('disabled');
    }
  };

  // 对话框加载动画
  MyDialog.prototype.loading = function(loading, excludeBtn) {
    // 创建等待元素
    var loadElement = $('<div class="dialog-loding dialog-full-size"><div class="loadImg"></div></div>');
    var self = this;
    var body = self.body;
    if (loading) {
      if (self.disabled)
        return;
      var img = $('.loadImg', loadElement);
      img.css('background-image', 'url(' + MyDialog.loadImg + ')');
      if (self.header) {
        img.css('top', $(self.header).outerHeight());
        img.css('height', 'calc(100% - ' + img.css('top') + ')');
      }
      if (self.footer) {
        img.css('bottom', $(self.footer).outerHeight());
        img.css('height', 'auto');
      }



      if (self.content)
        loadElement.css('height', self.content.outerHeight());
      body.append(loadElement);
      img.insertAfter(body);
      if (!excludeBtn)
        self.disable(true);
    } else {
      $('.dialog-loding', body).remove();
      $('.loadImg', self.dialog).remove();
      if (!excludeBtn)
        self.disable(false);
    }
  };

  // 监听尺寸变化
  MyDialog.prototype.listenSize = function(listen) {
    var self = this;
    if (listen) {
      clearInterval(self.timeHandler);
      
      self.timeHandler = listenResize(self.content, function() {
        self.setSize();
        self.resize();
      });
    } else {
      clearInterval(self.timeHandler);
    }
  };

  // 绑定事件
  MyDialog.prototype.bind = function() {
    var self = this;
    // 绑定对话框中的关闭事件
    $('.dialog-close', self.modal).bind('click', {dialog: self}, self.handler_close);
  };

  // 解绑事件
  MyDialog.prototype.unbind = function() {
    $('.dialog-close', this.modal).unbind('click', this.handler_close);
  };

  // 关闭事件控制
  MyDialog.prototype.handler_close = function(event) {
    var self = event.data.dialog;
    self.close();
  };



  // iframe 对话框
  var MyIframe = function(options) {
    // iframe对话框选项
    var option = {
      // 是否自动使用iframe标题
      autoTitle: true,
      // 加载失败是否自动关闭对话框
      fialClose: true,
      // 是否采用fixed模式
      fixed: false,
      // 加载失败回调函数
      onFial: null
    };
    this.options = $.extend({}, option, options);
    if (this.options.autoTitle)
      this.options.title = '加载中...';
    // 继承基类
    MyDialog.call(this, this.options);
    // 是否加载成功
    this.status = false;
  };
  // 设置原型继承
  MyIframe.prototype = new MyDialog();
  MyIframe.prototype.constructor = MyDialog;
  // 创建内容 - 覆盖方法
  MyIframe.prototype.createContent = function() {
    var self = this;
    var options = self.options;
    var target = options.target;
    self.iframeWrap = $('<div class="iframe-wrap"></div>');
    self.iframe = $('<iframe frameborder="0" scrolling="auto"></iframe>').css('display', 'block');
    self.iframeWrap.append(self.iframe);
    self.iframe.css('visibility', 'hidden');
    return self.iframeWrap;
  };
  // 加载内容 - 覆盖方法
  MyIframe.prototype.loadContent = function() {
    var self = this;
    var options = self.options;
    var iframe = self.iframe;

    utils.loadIframe(iframe, options.target, function(status) {
      self.status = status;
      if (!status) {
        if (options.fialClose) {
          self.loading(false);
          self.close(null, true, {
            // 异常地址
            url: iframe.attr('src'),
            // 异常内容
            exception: iframe.contents().find('body').html()
          });
        } else {
          // 仅仅以默认宽度和高度显示错误页面
          self.loading(false);
          $('.text', self.header).text('加载失败');
          self.iframe.css({
            'visibility': 'visible',
            'width': '100%',
            'height': '100%'
          });
          self.refSize();
          self.overflow = $(document.body).css('overflow');
          $(document.body).addClass('dialog-active').css('overflow', 'hidden');
          self.modal.show(0);
          if (self.mask)
            self.mask.show(0);
          self.dialogFull.show(0);
          if (self.mask)
            self.mask.addClass('active');
          self.dialogFull.addClass('active');
        }
        return;
      }

      self.content = iframe.contents().find('body');
      self.content.css('display', 'inline-block');
      if (window.lib && lib.flexible.dpr) {
        $(iframe[0].contentWindow.document.documentElement).css('font-size',  (54 * lib.flexible.dpr) + 'px');
      }
      // 给内容增加标志, 内容中的代码通过寻找这个标志来获取对话框实例
      self.content.attr('dialog-id', self.id).addClass('dialog-content-flag');
      var win = iframe[0].contentWindow;
      // 使用框架内部标题
      var title = win.document.title;
      if (self.header && self.header.length == 1)
        $('.text', self.header).text(title);
      
      win.dialogIframe = self;
      // 设置标志, 代表页面在iframe中
      win.inIframe = true;
      //self.setSize();
      self.resize();
      self.show();
      self.loading(false);
      self.modal.removeClass('status-loading');
      self.iframe.css('visibility', 'visible');
    });
    MyDialog.iframe[options.target] = self;
    self.showLoad();
  };
  // 显示加载中对话框
  MyIframe.prototype.showLoad = function() {
    var self = this;
    var content = self.content;
    var options = self.options;

    // 隐藏body滚动条
    // todo 可以这里要先尝试获取 self.overflow, 存在就不设置self.overflow值
    self.overflow = $(document.body).css('overflow');
    $(document.body).addClass('dialog-active').css('overflow', 'hidden');
    // 显示对话框
    self.modal.show(0);
    self.refSize();
    self.modal.addClass('status-loading');
    if (self.mask) {
      self.mask.show(0);
      self.mask.addClass('active');
    }
    self.dialogFull.show(0);
    self.dialogFull.addClass('active');

    self.loading(true, true);
    MyDialog.current = self;
  };
  // 显示对话框 [重载]
  MyIframe.prototype.show = function() {
    var self = this;
    var content = self.content;
    var options = self.options;
    MyDialog.current = self;
    self.listenSize(true);
    var win = self.iframe ? self.iframe[0].contentWindow : window;
    if ($.isFunction(win.DialogLoad))
      win.DialogLoad(self, content);
    $(win).trigger(MyDialog.Event.open, 'a');
    if ($.isFunction(options.onOpen)) {
      options.onOpen(self, self.content);
    }

    self.setDrag();
    if ($.isFunction(MyDialog.open))
      MyDialog.open(self, self.content);

    // setTimeout(function() {
      
    // }, 10);
  };
  // 参数检查 [重载]
  MyIframe.prototype.testParame = function() {
    var target = this.options.target;
    // 相同url的iframe正在加载中, 不能再次实例化
    if (MyDialog.iframe[this.options.target]) {
      console.warn(this.options.target, '已存在对话框不能再次实例化');
      return false;
    }
    return (target && typeof(target) === 'string');
  };
  // 设置尺寸 [重载]
  MyIframe.prototype.setSize = function() {
    var self = this;
    var options = self.options;
    var content = self.content;
    var iframe = self.iframe;
    var iframeWrap = self.iframeWrap;
    if (!self.status)
      return;

    // 获取原始尺寸来设置iframe尺寸
    var style = self.content.get(0).style;
    var minWidth = content.css('min-width');
    var width = options.width ? options.width : content.outerWidth() + 1;

    if (minWidth && minWidth !== 'auto' && minWidth !== '0px') {
      width = 'auto';
    }

    var height = options.height || (self.content.outerHeight() + 1) + 'px';

    // 这里将对话框内的根字体尺寸设置到iframe, 让 rem使用em, 这样保持对话框内部与iframe尺寸一致
    if (!options.fixed) {
      content.css('overflow', 'hidden');
      content.css('overflow-y', 'hidden');
    } else {
      if (typeof(width) === 'string') {
        var tmpWidth = parseFloat(width.replace('px', ''));
      }
      // +滚动条宽度避免出现滚动条
      width += 20;
    }

    iframeWrap.css({
      'width':  width,
      'min-width': minWidth
    });
    iframeWrap.css('height', self.content.outerHeight());

    $('.dialog-loding', self.body).css('height', height);

    if (!options.fixed) {
      content.css('overflow', 'auto');
      content.css('overflow-y', 'hidden');
    }

    iframeWrap.css('height', self.content.outerHeight());
    
  };
  // 设置位置 [重载]
  MyIframe.prototype.resize = function() {
    if (!this.status)
      return;
    this.setSize();
    $('.dialog-loding', this.body).css('height', this.iframeWrap.outerHeight());
    // 检测高度是否溢出,需要滚动条
    this.available();
  };
  // 检测是否高度溢出, 需要滚动条
  MyIframe.prototype.available = function() {
    var self = this;
    var content = self.content;
    var iframeWrap = self.iframeWrap;
    var options = self.options;
    // 内容高度
    var height = self.content.outerHeight();
    // 窗口宽度
    var maxHeight = $(window).outerHeight();
    // 页眉高度
    var headerHeight = $(self.header).outerHeight();
    // 页脚高度
    var footerHeight = $(self.footer).outerHeight();


    // 处理iphone下无论iframe还是普通对话框内容宽度超过屏幕看宽度, 则对话框被挤不挤见了
    // self.content.css('max-width', $(window).outerWidth() - 10);

    if ( (height) >= (maxHeight - headerHeight - footerHeight)) {
      self.dialog.css('height', '100%');
      if (options.fixed) {
        self.body.css('height', '100%');
        iframeWrap.css('height', '100%');
      } 
    } else {
      self.dialog.css('height', '');
    }

  };


  // 静态============================================
  // 存放所有对话框   对话框ID : 对话框实例  
  MyDialog.dislogs = {};
  // 对话框ID计数器
  MyDialog.count = 0;
  // 当前对话框
  MyDialog.current = null;
  // iframe加载中集合 url : 实例, 如果存在则说明此url正在加载
  MyDialog.iframe = {};
  // 上一个对话框
  MyDialog.befor = null;
  // 钩子函数-对话框打开
  MyDialog.open = null;
  // 钩子函数-对话框关闭
  MyDialog.close = null;
  // 对话框事件-打开对话框
  MyDialog.Event = {
    open: 'opendialog'
  };
  // 配置loading图片路径
  MyDialog.loadImg = '../images/loading.gif';
  // 添加对话框到集合
  MyDialog.add = function(dialog) {
    MyDialog.dislogs[++MyDialog.count] = dialog;
    return MyDialog.count;
  };
  // 移除对话框从集合
  MyDialog.remove = function(dialog) {
    delete  MyDialog.dislogs[dialog.id];
  };
  // 从对话框ID获取实例
  MyDialog.get = function(id) {
    // if (!MyDialog.dislogs[id])
    //   console.warn('获取对话框实例失败! 无效的对话框ID', id);
    return MyDialog.dislogs[id];
  };
  // 绑定事件
  MyDialog.handler_resize = function() {
    for (var id in MyDialog.dislogs) {
      MyDialog.dislogs[id].resize();
    }
  };
  $(window).resize(MyDialog.handler_resize);





  // 封装 Alert
  var Alert = function(element, title, onCancel, noPadding) {
    if (!element)
      return;
    // 默认参数
    var option = {
      // 显示目标, 一个jQuery对象
      target: (typeof(element) !== 'string') ? element : $('<div><p>' + element + '</p></div>'),
      // 按钮样式2
      btnType: 2,
      // 按钮集合或者jQuery对象
      btns: [{
        lable: '确定',
      }],
      // 标题
      title: title,
      // 是否显示遮罩
      mask: true,
      // 是否可拖拽
      drag: false,
      // 是否模态对话框
      isModal: true,
      // 事件-打开对话框
      onOpen: null,
      // 事件-关闭对话框
      onClose: onCancel || null,
    };

    var dialog = new MyDialog(option);
    if (!noPadding) {
      dialog.content.css('padding', '2.5em 4.16em');
    }

    return dialog;
  };

  // 封装 Confirm
  var Confirm = function(element, title, onConfirm, onCancel, noPadding, autoClose) {
    // 默认参数
    var option = {
      // 显示目标, 一个jQuery对象
      target: (typeof(element) !== 'string') ? element : $('<div><p>' + element + '</p></div>'),
      // 按钮样式2
      btnType: 2,
      // 按钮集合或者jQuery对象
      btns: [
        {
          lable: '取消',
          click: function() {
             if ($.isFunction(onCancel)) {
              onCancel(dialog);
            }
            dialog.close();
          },
        },
        {
          lable: '确定',
          click: function() {
            if ($.isFunction(onConfirm)) {
              onConfirm(dialog);
            }
            if (autoClose)
              dialog.close();
          },
        }
      ],
      // 标题
      title: title,
      // 是否显示遮罩
      mask: true,
      // 是否模态对话框
      isModal: true,
      // 事件-打开对话框
      onOpen: null,
      // 事件-关闭对话框
      onClose: null,
    };
    var dialog = new MyDialog(option);
    if (!noPadding) {
      dialog.content.css('padding', '2.5em 4.16em');
    }
    return dialog;
  };


  // 封装 html 属性
  window.InitDialog = function(doc) {
    $('[data-dialog]', doc).click(function() {
      var element = $(this);
      try {
        var option = JSON.parse(btn.attr('data-dialog'));
      } catch (error) {
        console.error('创建对话框出错, data-dialog 属性值不是合法JSON格式!');
        return;
      }

      option.target = $(option.target);
      var dialog = new MyDialog(option);
    });
  };



  $.fn.Alert = function(title, onCancel, noPadding) {
    if (!this.length) {
      return;
    }
    return new Alert(this, title, onCancel, noPadding);
  };
  $.fn.Confirm = function(title, onConfirm, onCancel) {
    if (!this.length) {
      return;
    }
    return new Confirm(this, title, onConfirm, onCancel);
  };





  // 导出
  window.MyDialog = MyDialog;
  window.MyIframe = MyIframe;
  window.Alert = Alert;
  window.Confirm = Confirm;
})();
(function() {

  // html模板
  var SELECTBOX = '\
    <div class="SelectBox" {{disabled}} {{readonly}} {{multiple}} style="{{style}}" >\
      {{TMP}}\
      <i class="icon"></i>\
    </div>\
  ';

  // 工具函数
  var utils = {
    // 字符串模板替换
    tpl: function(tpls, data) {
      return tpls.replace(/{{(.*?)}}/g, function($1, $2) {
        return data[$2] === undefined ? $1 : data[$2];
      });
    },
    // 字符串模板替换, 属性替换, 比如disabled属性, true就添加,false就移除
    tplForAttr: function(tpls, data) {
      return tpls.replace(/{{(.*?)}}/g, function($1, $2) {
        return data[$2] ? $1.replace(/({{)|(}})/g, '') : '';
      });
    },
    // 是否为数组
    isArray: function(obj) {
      return Object.prototype.toString.call(obj) === '[object Array]';
    },
    // 添加/移除 属性
    attr: function(elements, name, isAdd) {
      if (isAdd) {
        elements.attr(name, '');
      } else {
        elements.removeAttr(name);
      }
    },
    // 获取窗口剩余高度
    getAvailabHeight: function(element) {
      return ($(window).height() + $(window).scrollTop()) - (element.offset().top + element.outerHeight());
    },
    // 获取窗口剩余顶部
    getAvailabTop: function(element) {
      return element.offset().top;
    },
    // 提取数组特定字段生成数组字符串
    fieldToArrayStr: function(array, field, notBrackets) {
      if (!utils.isArray(array)) {
        return array ? array[field] : '';
      }
      if (array.length === 0) {
        return '请选择';
      }
      var arr = [];
      for (var i = 0; i < array.length; ++i) {
        arr.push(array[i][field]);
      }
      return notBrackets ? arr.toString() : '[' + arr.toString() + ']';
    },
    // 获取option元素数据
    optionToData: function(option) {
      var data = {
        type: 'option',
        label: option.text(),
        value: option.val(),
        selected: option.get(0).selected,
        disabled: option.attr('disabled') !== undefined,
        inGroup: option.parent().is('optgroup'),
        option: option
      };
      return data;
    },
    // 获取元素文档位置
    offset: function(ele) {
      var p = ele.offset();
      return {
        top: p.top + ele.outerHeight(),
        left: p.left
      };
    },
    /**
     * 根据索引获取option
     * @param  {Object} select select元素
     * @param  {Number} index  索引
     * @return {Object}        索引元素
     */
     getOptionForIndex: function(select, index) {
      index = index === undefined ? select[0].selectedIndex : parseInt(index);
      return select.find('option').eq(index);
     },
    /**
     * 根据value获取option
     * @param  {Object} select select元素
     * @param  {String} value  值
     * @return {Object}        索引元素
     */
     getOptionForValue: function(select, value) {
      var option = select.find('option[value="' + value + '"]');
      return option.length == 0 ? select.find('option').eq(select[0].selectedIndex) : option;
     },
    /**
     * 根据label获取option
     * @param  {Object} select select元素
     * @param  {String} label  值
     * @return {Object}        索引元素
     */
    getOptionForLabel: function(select, label) {
      var option = select.find('option').eq(select[0].selectedIndex);
      select.find('option').each(function() {
        if ($(this).text() == label)
          option = $(this);
      });
      return option;
     },
  };

  //判断:当前元素是否是被筛选元素的子元素或者本身 
  jQuery.fn.isChildAndSelfOf = function(b){
    return (this.closest(b).length > 0); 
  };

  var SelectBox = function(select, opt) {
    if (select[0].selectBox) {
      select[0].selectBox.destroy();
    }
    var options = {
      // select模板, 由2个值参数 {{value}},{{label}}
      selectTmp: '<div class="inner" value="{{value}}" title="{{label}}" >{{label}}</div>',
      // option模板, 由2个值参数 {{value}},{{label}}, 3个属性参数{{inGroup}},{{selected}},{{disabled}}
      optionTmp: '<dd class="option {{inGroup}}" {{selected}} {{disabled}} value="{{value}}" >{{label}}</dd>'
    };

    this.select = select;
    this.status = {
      readonly: false,
      disabled: false,
      multiple: false,
      style: ''
    };
    // 参数
    this.opt = $.extend({}, options, opt);
    // 当前选中项
    this.current = null;
    // 虚拟selextbox
    this.selectBox = null;
    // 虚拟dropdown
    this.dropdown = null;
    // dropdown是否显示
    this.isShow = false;
    // 加入管理器
    SelectBox.add(this);
    // 创建selextbox
    this.renderSelectBox();
    select[0].selectBox = this;
  };

  // 原型
  SelectBox.prototype = {
    get readonly() {
      return this.status.readonly;
    },
    set readonly(readonly) {
      var select = $(this.select).add(this.selectBox);
      this.status.readonly = readonly;
      utils.attr(select, 'readonly', readonly);
    },
    get disabled() {
      return this.status.disabled;
    },
    set disabled(disabled) {
      var select = $(this.select).add(this.selectBox);
      this.select[0].disabled = disabled;
      this.status.disabled = disabled;
      utils.attr(select, 'disabled', disabled);
    },
    get multiple() {
      return this.status.multiple;
    },
    set multiple(multiple) {
      var select = $(this.select).add(this.selectBox);
      this.select[0].multiple = multiple;
      this.status.multiple = multiple;
      utils.attr(select, 'multiple', multiple);
      this.renderSelectBox();
    },
    set options(data) {
      if (!utils.isArray(data) || data.length <= 0)
        return;

      var defaultOpt = {
        disabled: false,
        inGroup: false,
        selected: false,
        type: 'option',
        label: '',
        value: ''
      };

      // 确保数据结构完整
      $.each(data, function(i) {
        var option = this;
        if (option.hasOwnProperty('value')) {
          // 一个option
          data[i] = $.extend({}, defaultOpt, option);
        } else {
          // 一个group
          data[i] = $.extend({}, {label: ''}, option);
        }
      });

      // 注入数据
      this.inject(data);
    },
    // 销毁
    destroy: function() {
      this.hide();
      if (this.selectBox) {
        this.selectBox.remove();
        this.selectBox = null;
      }
      this.select.show();
      SelectBox.remove(this);
    },
    // 提取options数据
    extract: function() {
      return SelectBox.extract(this.select);
    },
    // 注入数据到select
    inject: function(data) {
      var options = $();
      var lastGroup = null;
      // 创建option
      $.each(data, function() {
        var item = this;
        if (item.type === 'option') {
          var option = $(utils.tpl('<option value="{{value}}">{{label}}</option>', item));
          if (item.selected)
            option[0].selected = true;
          if (item.disabled)
            option[0].disabled = true;
          if (item.inGroup && lastGroup) {
            lastGroup.append(option);
          } else {
            // 出了group组
            lastGroup = null;
            options = options.add(option);
          }
        } else {
          var group = $(utils.tpl('<optgroup label="{{label}}"></optgroup>', item));
          options = options.add(group);
          lastGroup = group;
        }

      })

      // 设置原始select
      this.select.html(options);
      this.renderSelectBox();
    },
    // 映射select状态/属性
    mapping: function() {
      var select = this.select;
      var status = this.status;
      status.disabled = select[0].disabled;
      status.multiple = select[0].multiple;
      status.readonly = select.attr('readonly') !== undefined;
      status.style = select.attr('style');
    },
    // 获取选中项
    getCurrent: function() {
      var self = this;
      var current = this.multiple ? [] : null;
      var selectOption = this.select.find('option').filter(function(i) {
        return this.selected;
      });
      selectOption.each(function() {
        var data = utils.optionToData($(this));
        if (self.multiple) {
          current.push(data);
        } else {
          current = data;
        }
      });
      return current;
    },
    // 创建selectBox
    create_selectBox: function() {
      // 获取当前选中项
      this.current = this.getCurrent();
      var select = this.select;
      var current = {
        value: utils.fieldToArrayStr(this.current, 'value'),
        label: utils.fieldToArrayStr(this.current, 'label', true),
        style: this.status.style
      };

      var html = utils.tpl(SELECTBOX, {
        'TMP': this.opt.selectTmp
      });

      // 值替换
      html = utils.tpl(html, current);
      // 属性替换
      html = utils.tplForAttr(html, this.status);
      // 创建元素
      var selectBox = $(html);
      if (select.attr('data-hide')) {
    	  select.hide();
    	  return selectBox.hide();
      } else {
    	  select.show(0);
          selectBox.css({
              'width': select.outerWidth(),
          });
          selectBox.get(0).tabIndex = select.get(0).tabIndex;
          return selectBox.show(0);
      }
    },
    // 创建dropdown
    create_dropdown: function() {
      var self = this;
      // 获取options数据
      var data = this.extract();
      // 根据option数据创建虚拟dom
      var dropdown = $('<div class="SelectBox-dropdown"></div>');
      var options = $('<dl class="SelectBox-options"></dl>');

      // 根据options数组数据创建虚拟option
      $.each(data, function() {
        var item = this;
        var option = null;

        if (item.type === 'option') {
          var optionHTML = self.opt.optionTmp;
          // 替换值
          optionHTML = utils.tpl(optionHTML, {value: item.value, label: item.label});
          // 替换属性
          optionHTML = utils.tplForAttr(optionHTML, item);
          // 创建元素
          option = $(optionHTML);
        } else {
          option = $(utils.tpl('<dt class="optgroup">{{label}}</dt>', item));
        }
        option.attr('title', item.label);
        options.append(option);
      });

      dropdown.append(options);
      return dropdown.css('min-width', this.selectBox.css('width'));
    },
    // 显示下拉列表
    show: function(event) {
      if (this.readonly || this.disabled)
        return;
      // 销毁之前dropdown
      if (this.dropdown !== null)
        this.dropdown.remove();

      var self = this;
      // 创建虚拟dropdown
      var dropdown = this.create_dropdown();
      var selectBox = this.selectBox.addClass('open');

      // 绑定事件
      $('dd', dropdown).bind('click', function(event) {
        self.selectForValue($(this).attr('value'));
      })


      // selectBox坐标
      var pos = utils.offset(selectBox);
      // 上剩余高度
      var above = utils.getAvailabTop(selectBox);
      // 下剩余高度
      var below = utils.getAvailabHeight(selectBox);


      // 插入dom
      $(document.body).append(dropdown);
      dropdown.show(0);

      // 实际高度
      var realHeight = dropdown.outerHeight();
      // 获取单条高度
      var itemHeight = $('.option', dropdown).outerHeight();
      // 最多显示条数
      var maxCount = 6;
      // 计算最大高度
      var maxHeight = 6 * itemHeight;

      // 使用更多剩余的一方来显示
      if (above > below && below <= dropdown.height()) {
        var maxHeight = above > maxHeight ? maxHeight : above - 10;
        // maxHeight 高度可能不是下拉列表的高度, 实际高度可能比maxHeight高度小, 这个时候 top: above - 实际高度
        // console.log('上方显示: ', '剩余高度: ', above, '下拉列表高度:', maxHeight, 'top:', above - maxHeight);
        dropdown.css({
          position: 'absolute',
          top: above - Math.min(maxHeight, realHeight),
          left: pos.left,
          'max-height': maxHeight,
          'overflow-y': 'auto'
        });
      } else {
        dropdown.css({
          position: 'absolute',
          top: pos.top,
          left: pos.left,
          'max-height': below > maxHeight ? maxHeight : below - 10,
          'overflow-y': 'auto'
        });
      }

      dropdown.addClass('open');
      this.dropdown = dropdown;
      this.isShow = true;
      if (event)
        event.stopPropagation();
    },
    // 隐藏下拉列表
    hide: function() {
      var dropdown = this.dropdown;
      if (this.selectBox)
        this.selectBox.removeClass('open');
      this.isShow = false;
      if (dropdown === null)
        return;

      dropdown.removeClass('open').addClass('close');
      setTimeout(function() { dropdown.remove(); }, 200);
    },
    // 通过option选中
    selectForOption: function(option, noHide) {
      // || (!this.multiple && option.get(0).selected)
      if (option.attr('disabled') !== undefined || option.length === 0)
        return;

      if (this.multiple) {
        option.get(0).selected = !option.get(0).selected;
      } else {
        this.select.value = option.get(0).value;
        option.get(0).selected = true;
      }
      this.renderSelectBox(true);
      this.select.trigger('change', this);
      if (!noHide)
        this.hide();
    },
    // 通过索引选中
    selectForIndex: function(index, noHide) {
      this.selectForOption(utils.getOptionForIndex(this.select, index), noHide);
    },
    // 通过value选中
    selectForValue: function(value) {
      this.selectForOption(utils.getOptionForValue(this.select, value));
    },
    // 通过label选中
    selectForLabel: function(label) {
      this.selectForOption(utils.getOptionForLabel(this.select, label));
    },
    // 渲染selectBox
    renderSelectBox: function() {
      var self = this;
      var select = this.select;
      var selectBox = this.selectBox;
      
      // 映射select状态/属性
      this.mapping();
      // 根据状态创建虚拟select
      var virtualSelect = this.create_selectBox();
      var display = select.css('disaplay');
      virtualSelect.css('disaplay', display);


      // 渲染到虚拟select
      select.hide();
      if (selectBox == null) {
        // 初次创建
        select.after(virtualSelect);
      } else {
        // 重新替换渲染
        selectBox.replaceWith(virtualSelect);
        if (selectBox.is(':focus'))
          virtualSelect.addClass('focus').focus();
      }

      // 绑定虚拟select事件
      virtualSelect.click(function() {
        if (self.isShow) { self.hide(); } else { self.show(); }
      });
      // 支持focus事件
      virtualSelect.keyup(function(event) {
        var key = event.keyCode;
        // 当前选中项作为基准索引
        var index = select.get(0).selectedIndex;
        // 所有option
        var options = $('.option', self.dropdown);
        // 跳转激活项
        var jump = function(i) {
          // 越界检测
          if (i < 0 || i >= options.length)
            return;
          options.eq(i).addClass('active').siblings().removeClass('active');
          options.eq(i)[0].scrollIntoView(false);
          //self.selectForIndex(i, true);
          select.get(0).selectedIndex = i;
          event.stopPropagation();
        };


        if (self.isShow) {
          if (key === 38) {
            jump(index - 1);
            return;
          }
          if (key === 40) {
            jump(index + 1);
            return;
          }
        }

        if (key === 13) {
          if (self.isShow) { 
            self.selectForIndex(index);
           } else { self.show(); }
          event.stopPropagation();
          return false;
        }
      });

      this.selectBox = virtualSelect;
    }
  };

  // 静态
  SelectBox.selects = [];
  SelectBox.add = function(select) {
    SelectBox.selects.push(select);
  };
  SelectBox.remove = function(select) {
    var index = -1;
    $.each(SelectBox.selects, function(i) {
      if (this === select)
        index = i;
    });
    if (index !== -1)
      SelectBox.selects.splice(index, 1);

  };
  SelectBox.hander_click = function(event) {
    var target = $(event.target);
    $.each(SelectBox.selects, function() {
      if (!target.isChildAndSelfOf(this.selectBox) && !target.isChildAndSelfOf(this.dropdown)) {
        this.hide();
      }
    });
  };
  SelectBox.extract = function(select) {
    var data = [];
    $('option,optgroup', select).each(function() {
      var option = $(this);
      if (option.is('optgroup')) {
        data.push({label: this.label});
      } else {
        data.push(utils.optionToData(option));
      }
    });
    return data;
  };

  $(document.body).click(SelectBox.hander_click);
  window.SelectBox = SelectBox;

  $.fn.SelectBox = function(opt) {
    return this.each(function(i, select) {
      new SelectBox($(select), opt);
    });
  };

  $.fn.selectForIndex = function(index) {
    return this.each(function(i, select) {
      if (!select.selectBox)
        return;
      select.selectBox.selectForIndex(index);
    });
  };

  // select[0].selectedIndex
  // 获取当前选中option
  $.fn.currentOption = function() {
    var select = this;
    var index = select[0].selectedIndex;
    return select.find('option').eq(index);
  };


  $.fn.findOptionForVal = function(val) {
    var select = this;
    return utils.getOptionForValue($(select), val);
  };


  $.fn.renderSelectBox = function() {
    this.each(function() {
      var selectBox = this.selectBox;
      if (!selectBox)
        return;
      selectBox.renderSelectBox();
    });
    return this;
  };

  $.fn.clearSelect = function() {
    this.each(function() {
      var select = this;
      $(this).html('<option>暂无</option>');
      var selectBox = this.selectBox;
      if (selectBox)
        selectBox.renderSelectBox();
    });
  };

  $.fn.selectForValue = function(value) {
    return this.each(function(i, select) {
      if (select.selectBox) {
        select.selectBox.selectForValue(value);
      } else {
        var option = utils.getOptionForValue($(select), value).get(0);
        if (option)
          option.selected = true;
        this.value = value;
      }
    });
  };

  $.fn.selectForLabel = function(label) {
    return this.each(function(i, select) {
      if (select.selectBox) {
        select.selectBox.selectForLabel(label);
      } else {
        var option = utils.getOptionForLabel($(select), label);
        if (option.length == 1)
          option[0].selected = true;
        this.value = option[0].value;
      }
    });
  };


})();
/// <reference path="D:\资料\XueYou\DefinitelyTyped\jquery\jquery.d.ts" />
(function() {
  var Selectable = function(table) {
    this.table = table;
    this.theadCheck = $('thead input[type=checkbox]', table);
    this.tbodyCheck = $('tbody input[type=checkbox]', table);
    this.bind();
  };
  Selectable.prototype.bind = function() {
    var self = this;
    var theadCheck = self.theadCheck;
    var tbodyCheck = self.tbodyCheck;

    theadCheck.change(function() {
      if (this.checked) {
        self.selectAll();
      } else {
        self.unselectAll();
      }
    });

    tbodyCheck.change(function() {
      self.select(this);
    });
  };
  Selectable.prototype.selectAll = function() {
    var self = this;
    var tbodyCheck = self.tbodyCheck;
    tbodyCheck.check();
    tbodyCheck.each(function() {
      self.select(this);
    });
  };

  Selectable.prototype.unselectAll = function() {
    var self = this;
    var tbodyCheck = self.tbodyCheck;

    tbodyCheck.unCheck();
    tbodyCheck.each(function() {
      self.select(this);
    });
  };
  Selectable.prototype.select = function(checkbox) {
    var tr = $(checkbox).parents('tr');
    if (checkbox.checked) {
      tr.addClass('is-selected');
    } else {
      tr.removeClass('is-selected');
    }
  };

  $.fn.Selectable = function() {
    this.each(function() {
      this.selectable = new Selectable($(this));
    });
  };
})();

(function() {

  /**
   * 选中/切换 复选框/单选框
   * @param {element} input  复选框
   * @param {boolean} checked true=选中, false=不选中, undefined=切换
   * @param {boolean} ignore 只读和禁用状态是否也设置
   * @param {object} args 触发事件所带参数
   */
  function input_check(input, checked, ignore, args) {
    if (!input || (input.type !== 'checkbox' && input.type !== 'radio'))
      return;

    // 禁用或只读则忽略
    if ((input.disabled || $(input).attr('readonly') !== undefined) && !ignore)
      return;
    // 选中复选框
    input.checked = checked === undefined ? !input.checked : checked;
    // 触发事件
    $(input).trigger('change', args);
  }

  // 选中复选框
  $.fn.check = function(args) {
    this.each(function() {
      input_check(this, true, false, args);
    });
    return this;
  };
  // 取消复选框
  $.fn.uncheck = function(args) {
    this.each(function() {
      input_check(this, false, false, args);
    });
    return this;
  };
  // 切换复选框
  $.fn.toggle_check = function(args) {
    this.each(function() {
      input_check(this, undefined, false, args);
    });
    return this;
  };

  // 复选框集合是否全选中
  $.fn.isCheckAll = function() {
    var status = true;
    this.each(function() {
      if (this.checked === false)
        status = false;
    });
    return status;
  };
  // 复复选框是否全未选中
  $.fn.notCheckAll = function() {
    var status = true;
    this.each(function() {
      if (this.checked === true)
        status = false;
    });
    return status;
  };



  /**
   * 禁用/启用 输入框
   * @param {element} input   输入框 
   * @param {boolean} disabled 是否禁用
   */
  function input_disabled(input, disabled) {
    if (!input || (input.type !== 'checkbox' && input.type !== 'radio'))
      return;
    // 禁用/启用
    input.disabled =  disabled === undefined ? !input.disabled : disabled;
    // 发送美化事件
    $(input).trigger('toggle-render');
  }

  // 禁用输入框
  $.fn.disable = function() {
    this.each(function() {
      input_disabled(this, true);
    });
    return this;
  };

  // 禁用输入框
  $.fn.enabled = function() {
    this.each(function() {
      input_disabled(this, false);
    });
    return this;
  };

  // 切换禁用/启用输入框
  $.fn.toggle_disable = function() {
    this.each(function() {
      input_disabled(this);
    });
    return this;
  };

  // 封装 radio 方法用于选择原生 radio
  // 选中radio
  $.fn.radio = function() {
    // 确保单选框一次只能选中一个
    if (this.length !== 1)
      return this;
    // 只读和禁用
    if (this.get(0).disabled || this.attr('readonly') !== undefined)
      return this;
    // 根据name分组
    var name = $(this).attr('name');
    // 寻找当前选中的单选框
    var current = $('input[type="radio"]').filter(function() {
      return $(this).attr('name') === name && this.checked;
    });

    // 取消当前复选框的选中状态
    if (current.length)
      input_check(current.get(0), false, true);
    // 选中当前选择的
    input_check(this.get(0), true);
  };

})();

(function() {

  // 工具函数
  var utils = {
    // 字符串模板替换
    tpl: function(tpls, data) {
      return tpls.replace(/{{(.*?)}}/g, function($1, $2) {
        return data[$2] === undefined ? $1 : data[$2];
      });
    },
    // 字符串模板替换, 属性替换, 比如disabled属性, true就添加,false就移除
    tplForAttr: function(tpls, data) {
      return tpls.replace(/{{(.*?)}}/g, function($1, $2) {
        return data[$2] ? $1.replace(/({{)|(}})/g, '') : '';
      });
    }
  };

  // 渲染事件
  var EventRender = 'toggle-render';



  /**
   * 封装checkbox
   */
  var checkbox_html = '\
    <div class="toggle-checkbox" {{checked}}  {{disabled}} >\
      <span class="box_outline">\
        <span class="tick_outline"></span>\
      </span>\
      <span class="toggle-label">{{label}}</span>\
    </div>\
  ';
  var CheckBox = function(input) {
    if (!input || input.length !== 1)
      return;
    this.input = input;
    // 创建
    this.create();
    // 绑定事件
    this.bind();
  };
  CheckBox.prototype.create = function() {
    var input = this.input;

    if (input.get(0).checkBox)
      input.get(0).checkBox.destroy();
    input.get(0).checkBox = this;
    // 获取input前后紧挨的可选label
    var label = $();
    var next = input.next();
    var prev = input.prev();
    if (next.length === 1 && next.get(0).nodeName === 'LABEL') {
      label = next;
    } else if (prev.length === 1 && prev.get(0).nodeName === 'LABEL') {
      label = prev;
    }
    this.label = label;

    // 获取checkbox状态
    var checked = input.get(0).checked;
    var disabled = input.get(0).disabled;

    // 生成虚拟checkbox
    var html = utils.tpl(checkbox_html, {label: label.text() || ''});
    this.checkbox = $(utils.tplForAttr(html, {
      checked: checked,
      disabled: disabled
    }));

    // 插入dom
    label.hide();
    input.hide().after(this.checkbox);
  };
  CheckBox.prototype.bind = function() {
    var self = this;
    var input = this.input;
    var checkbox = this.checkbox;

    input.bind('change', self, this.handler);
    input.bind(EventRender, function() {
      self.render();
    });
    checkbox.click(function() {
      input.toggle_check();
    });

  };
  CheckBox.prototype.handler = function(event) {
    var checkbox = event.data;
    checkbox.render();
  };
  CheckBox.prototype.render = function() {
    var input = this.input;
    var checkbox = this.checkbox;
    var checked = input.get(0).checked;
    var disabled = input.get(0).disabled;

    if (checked) {
      checkbox.attr('checked', 'checked');
    } else {
      checkbox.removeAttr('checked');
    }

    if (disabled) {
      checkbox.attr('disabled', 'disabled');
    } else {
      checkbox.removeAttr('disabled');
    }
  };
  CheckBox.prototype.destroy = function() {
    var input = this.input;
    if (this.label)
      this.label.show();
    input.show().unbind('change', this.handler);
    this.checkbox.remove();
  };

  $.fn.CheckBox = function() {
    this.each(function() {
      var input = $(this);
      new CheckBox(input);
    });
    return this;
  };


  /**
   * 封装Switch
   */
  var switch_html = '\
    <div class="toggle-switch" {{checked}}  {{disabled}} >\
      <span class="track"></span>\
      <div class="thumb"></div>\
    </div>\
  ';
  var Switch = function(input) {
    if (!input || input.length !== 1)
      return;
    this.input = input;
    // 创建
    this.create();
    // 绑定事件
    this.bind();
  };
  Switch.prototype.create = function() {
    var input = this.input;

    if (input.get(0).Switch)
      input.get(0).Switch.destroy();
    input.get(0).Switch = this;

    // 获取checkbox状态
    var checked = input.get(0).checked;
    var disabled = input.get(0).disabled;

    // 生成虚拟checkbox
    this.Switch = $(utils.tplForAttr(switch_html, {
      checked: checked,
      disabled: disabled
    }));

    // 插入dom
    input.hide().after(this.Switch);
  };
  Switch.prototype.bind = function() {
    var self = this;
    var input = this.input;
    var Switch = this.Switch;

    input.bind('change', self, this.handler);
    input.bind(EventRender, function() {
      self.render();
    });
    Switch.click(function() {
      input.toggle_check();
    });

  };
  Switch.prototype.handler = function(event) {
    var Switch = event.data;
    Switch.render();
  };
  Switch.prototype.render = function() {
    var input = this.input;
    var Switch = this.Switch;
    var checked = input.get(0).checked;
    var disabled = input.get(0).disabled;

    if (checked) {
      Switch.attr('checked', 'checked');
    } else {
      Switch.removeAttr('checked');
    }

    if (disabled) {
      Switch.attr('disabled', 'disabled');
    } else {
      Switch.removeAttr('disabled');
    }
  };
  Switch.prototype.destroy = function() {
    var input = this.input;
    input.show().unbind('change', this.handler);
    this.Switch.remove();
  };

  $.fn.Switch = function() {
    this.each(function() {
      var input = $(this);
      new Switch(input);
    });
    return this;
  };

  /**
   * 封装SwitchText
   */
  var switchText_html = '\
    <span class="i-switch" {{checked}}  {{disabled}}>\
      <span class="switch-inner">\
        <span class="Text">{{label}}</span>\
      </span>\
      <span class="switch-dot"></span>\
    </span>\
  ';
  var SwitchText = function(input) {
    if (!input || input.length !== 1)
      return;
    this.input = input;
    // 创建
    this.create();
    // 绑定事件
    this.bind();
  };
  SwitchText.prototype.create = function() {
    var input = this.input;

    if (input.get(0).switchText)
      input.get(0).switchText.destroy();
    input.get(0).switchText = this;

    // 获取checkbox状态
    var checked = input.get(0).checked;
    var disabled = input.get(0).disabled;
    var offLabel = input.attr('data-off-label');
    var onLabel = input.attr('data-on-label');

    // 生成虚拟checkbox
    var html = utils.tpl(switchText_html, {label: checked ? onLabel: offLabel});
    this.switchText = $(utils.tplForAttr(html, {
      checked: checked,
      disabled: disabled
    }));

    // 插入dom
    input.hide().after(this.switchText);
  };
  SwitchText.prototype.bind = function() {
    var self = this;
    var input = this.input;
    var switchText = this.switchText;

    input.bind('change', self, this.handler);
    input.bind(EventRender, function() {
      self.render();
    });
    switchText.click(function() {
      input.toggle_check();
    });

  };
  SwitchText.prototype.handler = function(event) {
    var switchText = event.data;
    switchText.render();
  };
  SwitchText.prototype.render = function() {
    var input = this.input;
    var switchText = this.switchText;
    var checked = input.get(0).checked;
    var disabled = input.get(0).disabled;
    var offLabel = input.attr('data-off-label');
    var onLabel = input.attr('data-on-label');

    if (checked) {
      switchText.attr('checked', 'checked');
      $('.track', switchText).text(onLabel);
    } else {
      $('.track', switchText).text(offLabel);
      switchText.removeAttr('checked');
    }

    if (disabled) {
      switchText.attr('disabled', 'disabled');
    } else {
      switchText.removeAttr('disabled');
    }
  };
  SwitchText.prototype.destroy = function() {
    var input = this.input;
    input.show().unbind('change', this.handler);
    this.switchText.remove();
  };

  $.fn.SwitchText = function() {
    this.each(function() {
      var input = $(this);
      new SwitchText(input);
    });
    return this;
  };

  /**
   * 封装SwitchText - 从老的radio生成
   */
  var SwitchTextRadio = function(radioWrap) {
    if (!radioWrap || radioWrap.length !== 1)
      return;
    this.radioWrap = radioWrap;
    // 创建
    this.create();
    // 绑定事件
    this.bind();
  };
  SwitchTextRadio.prototype.create = function() {
    var radioWrap = this.radioWrap;
    if (radioWrap.get(0).switchTextRadio)
      radioWrap.get(0).switchTextRadio.destroy();
    radioWrap.get(0).switchTextRadio = this;

    var onRadio = $('#u-on', radioWrap);
    var onLabel = $('[for="u-on"]', radioWrap);
    var offRadio = $('#u-off', radioWrap);
    var offLabel = $('[for="u-off"]', radioWrap);
    this.onRadio = onRadio;
    this.offRadio = offRadio;
    this.onLabel = onLabel;
    this.offLabel = offLabel;


   

    var checked = onRadio.get(0).checked ? true : false;
    // 都没选中则默认选中开启状态的
    if (!onRadio.get(0).checked && ! offRadio.get(0).checked) {
      onRadio.get(0).checked = true;
      checked = true;
    }
      

    var disabled = radioWrap.hasClass('disabled');

    // 生成虚拟checkbox
    var html = utils.tpl(switchText_html, {label: checked ? onLabel.text(): offLabel.text()});
    this.switchText = $(utils.tplForAttr(html, {
      checked: checked,
      disabled: disabled
    }));

    onRadio.hide();
    onLabel.hide();
    offRadio.hide();
    offLabel.hide();
    radioWrap.append(this.switchText);
  };
  SwitchTextRadio.prototype.bind = function() {
    var self = this;
    var onRadio = this.onRadio;
    var offRadio = this.offRadio;
    var radioWrap = this.radioWrap;
    var switchText = this.switchText;

    onRadio.bind('change', self, this.handler);
    offRadio.bind('change', self, this.handler);

    radioWrap.bind(EventRender, function() {
      self.render();
    });

    switchText.click(function() {
      var disabled = radioWrap.hasClass('disabled');
      if (disabled)
        return;

      var checked = onRadio.get(0).checked;
      if (checked) {
        offRadio.radio();
      } else {
        onRadio.radio();
      }
    });

  };
  SwitchTextRadio.prototype.handler = function(event) {
    var switchText = event.data;
    switchText.render();
  };
  SwitchTextRadio.prototype.render = function() {
    var radioWrap = this.radioWrap;
    var switchText = this.switchText;
    var onRadio = this.onRadio;
    var offRadio = this.offRadio;
    var onLabel = this.onLabel;
    var offLabel = this.offLabel;

    var checked = onRadio.get(0).checked ? true : false;
    var label = checked ? onLabel.text(): offLabel.text();
    if (checked) {
      $('.switch-dot', switchText).animate({
        left: '37px',
      }, 200, function() {
        switchText.attr('checked', 'checked');
      });
      $('.Text', switchText).text(onLabel.text());
    } else {
      $('.Text', switchText).text(offLabel.text());
      $('.switch-dot', switchText).animate({
        left: '1px'
      }, 200, function() {
        switchText.removeAttr('checked');
      });
    }

  };
  SwitchTextRadio.prototype.destroy = function() {
    var radioWrap = this.radioWrap;
    var switchText = this.switchText;
    var onRadio = this.onRadio;
    var offRadio = this.offRadio;
    var onLabel = this.onLabel;
    var offLabel = this.offLabel;

    onRadio.show();
    onLabel.show();
    offRadio.show();
    offLabel.show();

    onRadio.unbind('change', this.handler);
    offRadio.unbind('change', this.handler);
    this.switchText.remove();
  };

  $.fn.SwitchTextRadio = function() {
    new SwitchTextRadio(this);
    return this;
  };


  /**
   * 封装Radio
   * todo: 这个组件与其他有些不同, input change事件还需要同时改变 name组中的其他样式渲染
   */
  var radio_html = '\
    <div class="toggle-radio" {{checked}}  {{disabled}} >\
      <span class="box_outline">\
        <span class="tick_outline"></span>\
      </span>\
      <span class="toggle-label">{{label}}</span>\
    </div>\
  ';
  var Radio = function(input) {
    if (!input || input.length !== 1)
      return;
    this.input = input;
    // 创建
    this.create();
    // 绑定事件
    this.bind();
  };
  Radio.prototype.create = function() {
    var input = this.input;

    if (input.get(0).radio)
      input.get(0).radio.destroy();
    input.get(0).radio = this;
    // 获取input前后紧挨的可选label
    var label = $();
    var next = input.next();
    var prev = input.prev();
    if (next.length === 1 && next.get(0).nodeName === 'LABEL') {
      label = next;
    } else if (prev.length === 1 && prev.get(0).nodeName === 'LABEL') {
      label = prev;
    }
    this.label = label;

    // 获取checkbox状态
    var checked = input.get(0).checked;
    var disabled = input.get(0).disabled;

    // 生成虚拟checkbox
    var html = utils.tpl(radio_html, {label: label.text() || ''});
    this.radio = $(utils.tplForAttr(html, {
      checked: checked,
      disabled: disabled
    }));

    // 插入dom
    label.hide();
    input.hide().after(this.radio);
  };
  Radio.prototype.bind = function() {
    var self = this;
    var input = this.input;
    var radio = this.radio;

    input.bind('change', self, this.handler);
    input.bind(EventRender, function() {
      self.render();
    });
    radio.click(function() {
      input.toggle_check();
    });

  };
  Radio.prototype.handler = function(event) {
    var radio = event.data;
    var name = radio.input.attr('name');

    // 获取同name的radio, 一起渲染
    var radios = $('input[type="radio"]').filter(function () {
      return $(this).attr('name') === name;
    });

    radios.each(function() {
      if (this.radio)
        this.radio.render();
    });
  };
  Radio.prototype.render = function() {
    var input = this.input;
    var radio = this.radio;
    var checked = input.get(0).checked;
    var disabled = input.get(0).disabled;

    if (checked) {
      radio.attr('checked', 'checked');
    } else {
      radio.removeAttr('checked');
    }

    if (disabled) {
      radio.attr('disabled', 'disabled');
    } else {
      radio.removeAttr('disabled');
    }
  };
  Radio.prototype.destroy = function() {
    var input = this.input;
    if (this.label)
      this.label.show();
    input.show().unbind('change', this.handler);
    this.radio.remove();
  };

  $.fn.Radio = function() {
    this.each(function() {
      var input = $(this);
      new Radio(input);
    });
    return this;
  };










})();

/**
 * 公用对象
 */
(function() {

  // 验证事件
  var EnumTrigger = {
    // 失去焦点时, 基于元素change事件
    change: 2,
    // 内容改变时, 基于元素input事件, 替代keyup事件
    input: 4
  };

  // 工具函数
  var utils = {
    /**
     * 获取长度
     * @description 获取input长度 (select)多选情况下选中的个数, (radio|checkbox)同组选中个数
     * @param value {string}
     * @param element {jQuery}
     * @returns {number}
     */
    getLength: function(value, element) {
      if (!element) {
        return value.length;
      }
      switch (element.get(0).nodeName.toLowerCase()) {
      case 'select':
        return $('option:selected', element).length;
      case 'input':
        if (utils.checkable(element)) {
          return utils.findByName( element.get(0).name, element.parents('form') ).filter( ':checked').length;
        }
      }
      return value.length;
    },
    /**
     * @description 是否为radio|checkbox
     * @param element {jQuery}
     * @returns {boolean}
     */
    checkable: function(element) {
      if (!element || !element.get(0) || !element.get(0).type) {
        return false;
      }
      return (/radio|checkbox/i ).test( element.get(0).type);
    },
    /**
     * @description 根据name寻找元素
     * @param name {string}
     * @param currentForm {jQuery}
     * @returns {number}
     */
    findByName: function(name, currentForm) {
      return $(currentForm).find('[name="' + utils.escapeCssMeta( name ) + '"]');
    },
    /**
     * @description 转义特殊字符
     * @param string {string}
     * @returns {string}
     */
    escapeCssMeta: function(string) {
      return string.replace(/([\\!"#$%&'()*+,./:;<=>?@\[\]^`{|}~])/g, '\\$1');
    },
    /**
     * @description 格式化参数输出
     * @param msg {string}
     * @param args {array|string}
     * @returns {string}
     */
    format: function(msg, args) {
      if ($.isArray(args)) {
        $.each(args, function(i, param) {
          msg = msg.replace(new RegExp('\\{' + i + '\\}', 'g'), param);
        });
        return msg;
      } else {
        return msg.replace(/\{0\}/g, args);
      }
    },
    /**
     * @description 获取对象参数
     * @param paramStr {string} 对象字符串
     * @returns {object}
     */
    toJson: function(paramStr) {
      if (!paramStr || paramStr === undefined || paramStr === null)
        return null;
      if (paramStr === 'required') {
        return true;
      }
      // json模板字符串
      var json = '{"data": ' + paramStr + '}';
      var data = null;
      try {
        data = (JSON.parse(json)).data;
      } catch (err) {
        // 将参数转为字符串, 再次尝试
        json = '{"data": "' + paramStr + '"}';
        data = (JSON.parse(json)).data;
      }
      return data;
    },
    /**
     * @description 数组map
     * @param array {Array} 数组
     * @param map   {Function} 过滤函数
     * @returns {Array}
     */
    map: function(array, map) {
      var result = [];
      $.each(array, function(i) {
        if (map(this, i)) {
          result.push(this);
        }
      });
      return result;
    },
    /**
     * @description 是否为身份证
     * @param num {string}  身份证字符串
     * @returns {boolean}
     */
    isIdCardNo: function(num) {
      var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1);
      var parityBit = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
      var varArray = new Array();
      var lngProduct = 0;
      var intCheckDigit;
      var intStrLen = num.length;
      var idNumber = num;

      // initialize
      if ((intStrLen !== 15) && (intStrLen !== 18)) {
        return false;
      }
      // check and set value
      for (var i = 0; i < intStrLen; i++) {
        varArray[i] = idNumber.charAt(i);
        if ((varArray[i] < '0' || varArray[i] > '9') && (i !== 17)) {
          return false;
        } else if (i < 17) {
          varArray[i] = varArray[i] * factorArr[i];
        }
      }

      if (intStrLen === 18) {
        // check date
        var date8 = idNumber.substring(6, 14);
        if (utils.isDate8(date8) === false) {
          return false;
        }
        // calculate the sum of the products
        for (i = 0; i < 17; i++) {
          lngProduct = lngProduct + varArray[i];
        }
        // calculate the check digit
        intCheckDigit = parityBit[lngProduct % 11];
        // check last digit
        if (varArray[17] !== intCheckDigit) {
          return false;
        }
      } else {
        var date6 = idNumber.substring(6, 12);
        if (utils.isDate6(date6) === false) {
          return false;
        }
      }
      return true;
    },
    isDate6: function(sDate) {
      if (!/^[0-9]{6}$/.test(sDate)) {
        return false;
      }
      var year, month;
      year = sDate.substring(0, 4);
      month = sDate.substring(4, 6);
      if (year < 1700 || year > 2500) return false;
      if (month < 1 || month > 12) return false;
      return true;
    },
    isDate8: function(sDate) {
      if (!/^[0-9]{8}$/.test(sDate)) {
        return false;
      }
      var year, month, day;
      year = sDate.substring(0, 4);
      month = sDate.substring(4, 6);
      day = sDate.substring(6, 8);
      var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
      if (year < 1700 || year > 2500) return false;
      if (((year % 4 === 0) && (year % 100 !== 0)) || (year % 400 === 0)) {
        iaMonthDays[1] = 29;
      }
      if (month < 1 || month > 12) return false;
      if (day < 1 || day > iaMonthDays[month - 1]) return false;
      return true;
    },
    /**
     * @description 数组中的每个元素都满足测试函数，通过则返回 true
     * @param array {Array}  数组
     * @param fn {Function}  测试函数
     */
    every: function(array, fn) {
      var status = true;
      for (var i = 0; i < array.length; ++i) {
        var item = array[i];
        if (fn(item, i) === false) {
          status = false;
          break;
        }
      }
      return status;
    },
    /**
     * @description 数组中元素重复测试函数
     * @param array {Array}  数组
     * @param fn {Function}  测试函数
     */
    repeat: function(array, fn) {
      var status = true;
      for (var i = 0; i < array.length; ++i) {
        var current = array[i];
        for (var j = i + 1; j < array.length; ++j) {
          if (fn(current, array[j]) === false) {
            status = false;
            break;
          }
        }
      }
      return status;
    }
  };


  // improt 导入
  var Enums = window.Enums || {};
  var validators = window.validators || {};
  Enums.EnumTrigger = EnumTrigger;

  // export 导出
  validators.utils = utils;
  window.validators = validators;
  window.Enums = Enums;
})();


/**
 * 验证对象
 * 注意: Compared.test([...]) 时候数组中不能出现重复的规则,否则会被最后一个规则覆盖
 */
(function() {

  // improt 导入
  var utils = window.validators.utils;

  // 验证状态
  var EnumStatus = {
    // 验证通过
    ok: true,
    // 验证失败
    fail: false,
    // 异步验证中
    asyn: 'asyn'
  };

  // 封装转化结果
  var toStatus = function(status, message, params) {
    if (status) {
      message = '';
    }
    return {status: status, message: utils.format(message, params) || ''};
  };

  // 封装参数检查
  var checkParam = function(ruleName, params) {
      if (!params || params.length <= 0) {
        throw new Error('rules.' + ruleName + '::必须指定参数');
      }
  };

  // 根据ruleName字段查数组元素
  var findByRuleName = function(array, ruleName) {
    var item = null;
    for (var i = 0; i < array.length; ++i) {
      if (array[i].ruleName === ruleName) {
        item = array[i];
      }
    }
    return item;
  };

  // 判断是否所有异步都已返回
  var judgComplete = function(asynInfo) {
    var complete = true;
    for (var i = 0; i < asynInfo.length; ++i) {
      if (asynInfo[i].status === EnumStatus.asyn) {
        complete = false;
      }
    }
    return complete;
  };

  // 规则函数集合
  var rules = {
    // 必填
    required: function(val, params, ele, asynFn) {
      var status = false;
      var nodeType = ele ? ele.get(0).nodeName.toLowerCase() : '';
      if (nodeType === 'select') {
        status = ele.val() ? ele.val().length > 0 : false;
      } else if (utils.checkable(ele)) {
        status = utils.getLength(val, ele) > 0;
      } else {
        status = Boolean(val && val.replace(/\'|\s/g, '') !== '');
      }
      return toStatus(status, '必填');
    },
    // 邮件
    email: function(val) {
      var status = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/.test(val);
      return toStatus(status, '无效的电子邮件');
    },
    // url
    url: function(val) {
      var status = /^(?:(?:(?:https?|ftp):)?\/\/)(?:\S+(?::\S*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})).?)(?::\d{2,5})?(?:[/?#]\S*)?$/i.test(val);
      return toStatus(status, '无效的http地址');
    },
    // 日期 2017-5-13 格式
    date: function(val) {
      var status = true;
      var regexp = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/;
      var matches = regexp.exec(val);
      if (!matches) status = false;
      if (status && matches[4] > 31) status = false;
      if (status && matches[3] == 2 && matches[4] > 28) status = false;
      if (status && (matches[3] == 1 || matches[3] == 3 || matches[3] == 5 || matches[3] == 7 || matches[3] == 8 || matches[3] == 10 || matches[3] == 12) && matches[4] > 30) status = false;
      return toStatus(status, '无效的日期');
    },
    // 标准日期
    dateISO: function(val) {
      var status = true;
      var regexp = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) ((\d{1,2}):(\d{1,2}):(\d{1,2}))$/;
      var matches = regexp.exec(val);
      if (!matches) status = false;
      if (status && matches[4] > 31) status = false;
      if (status && matches[3] == 2 && matches[4] > 28) status = false;
      if (status && (matches[3] == 1 || matches[3] == 3 || matches[3] == 5 || matches[3] == 7 || matches[3] == 8 || matches[3] == 10 || matches[3] == 12) && matches[4] > 30) status = false;
      if (status && !rules.time(matches[5]).status) status = false;
      return toStatus(status, '无效的日期');
    },
    // 时间 17:22:33
    time: function(val) {
      return toStatus(/^((20|21|22|23|[0-1]\d)\:[0-5][0-9])(\:[0-5][0-9])?$/.test(val), '无效的时间');
    },
    // 数值
    number: function(val) {
      return toStatus(/^(?:-?\d+|-?\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/.test(val), '无效的数值');
    },
    // 整数
    digits: function(val) {
      return toStatus(/^\d+$/.test(val), '无效的整数');
    },
    // 最小长度
    minlength: function(val, params, ele) {
      checkParam('minlength', params);
      var length = $.isArray(val) ? val.length : utils.getLength(val, ele);
      return toStatus(length >= ($.isFunction(params) ? param(val) : params), '少于{0}个字符', params);
    },
    // 最大长度
    maxlength: function(val, params, ele) {
      checkParam('maxlength', params);
      var length = $.isArray(val) ? val.length : utils.getLength(val, ele);
      return toStatus(length <= ($.isFunction(params) ? param(val) : params), '多于{0}个字符', params);
    },
    // 字符长度范围
    rangelength: function(val, params, ele) {
      if (!params || params.length !== 2) {
        throw new Error('rules.' + rangelength + '::必须指定参数, 且参数为长度2的数组 [min, max]');
      }
      var length = $.isArray(val) ? val.length : utils.getLength(val, ele);
      return toStatus(length >= params[0] && length <= params[1], '字符范围{0} - {1}', params);
    },
    // 最大数值
    max: function(val, params) {
      checkParam('max', params);
      var value = parseFloat(val);
      return toStatus(value <= parseFloat(params), '数值超过{0}', params);
    },
    // 最小数值
    min: function(val, params) {
      checkParam('min', params);
      var value = parseFloat(val);
      return toStatus(value >= parseFloat(params), '数值小于{0}', params);
    },
    // 数值范围
    range: function(val, params) {
      checkParam('range', params);
      var value = parseFloat(val);
      return toStatus(value >= parseFloat(params[0]) && value <= parseFloat(params[1]), '数值范围{0} - {1}', params);
    },
    // 数值倍数
    step: function(val, params) {
      checkParam('step', params);
      var value = parseFloat(val);
      return toStatus((value % params) === 0, '数值不是{0}的倍数', params);
    },
    // 等同于另一个元素的值
    equalTo: function(val, params) {
      checkParam('equalTo', params);
      var target = $( ($.isFunction(params) ? params(val) : params) );
      return toStatus(val === target.val(), '值不相同');
    },
    // 不能等同于另一个元素的值
    notEqual: function(val, params) {
      checkParam('notEqual', params);
      var target = $( ($.isFunction(params) ? params(val) : params) );
      return toStatus(val !== target.val(), '值相同');
    },
    // 不为数值0
    notZero: function(val) {
      return toStatus(parseFloat(val) !== 0, '值不能为零');
    },
    // 合法后缀名
    acceptSuffix: function(val, params) {
      checkParam('accept', params);
      var status = false;
      $.each(($.isFunction(params) ? params(val) : params), function(index, value) {
        var patten = new RegExp('\\.' + value + '$');
        if (patten.test(val, 'i')) {
          status = true;
        }
      });
      return toStatus(status, '后缀不支持');
    },
    // 合法图片后缀
    acceptIamge: function(val) {
      var status = false;
      var params = ["png", "jpg"];
      $.each(($.isFunction(params) ? params(val) : params), function(index, value) {
        var patten = new RegExp('\\.' + value + '$');
        if (patten.test(val, 'i')) {
          status = true;
        }
      });
      return toStatus(status, '不是合法图片后缀');
    },
    // 正则匹配
    pattern: function(val, params) {
      checkParam('pattern', params);
      var reg;
      var local_patten = ($.isFunction(params) ? params(val) : params);
      if (typeof local_patten === 'string') {
        reg = new RegExp(local_patten);
      } else {
        reg = local_patten;
      }
      return toStatus(reg.test(val), '正则匹配失败');
    },
    // 身份证
    idCard: function(val) {
      return toStatus(utils.isIdCardNo(val), '无效身份证');
    },
    // 邮政编码
    zipCode: function(val) {
      return toStatus(/^[0-9]{6}$/.test(val), '无效邮政编码');
    },
    // 手机号码
    phone: function(val) {
      var mobile = /^(13[0-9]|15[0123456789]|17[03678]|18[0-9]|14[57])[0-9]{8}$/;
      return toStatus(val.length === 11 && mobile.test(val), '无效手机号码');
    },
    // 座机号码
    tel: function(val) {
      return toStatus(/^\d{3,4}-?\d{7,9}$/.test(val), '无效电话号码');
    },
    // 手机或座机
    telPhone: function(val) {
      var length = val.length;
      var mobile = /^(13[0-9]|15[0123456789]|17[03678]|18[0-9]|14[57])[0-9]{8}$/;
      var tel = /^\d{3,4}-?\d{7,9}$/;
      return toStatus(tel.test(val) || (length === 11 && mobile.test(val)), '无效手机或电话号码');
    },
    // 中文字符
    chinese: function(val) {
      return toStatus(/^([\u4e00-\u9fa5]|\u00b7)+$/.test(val), '无效中文字符');
    },
    // 非中文字符
    notChinese: function(val) {
      var status = true;
      for (var i = 0; i < val.length; ++i) {
        var patten = /^([\u4e00-\u9fa5]|\u00b7)+$/;
        if (patten.test(val[i])) {
          status = false;
        }
      }
      return toStatus(status, '不能有中文字符');
    },
    // 银行卡号
    bankCard: function(val) {
      var status = true;
      // value为NAN(非法值)或者长度小于12, 则false
      if (isNaN(val))
        status = false;
      if (val.length < 12) {
        status = false;
      }
      // 将 123456字符串卡号,分割成数组 ['1', '2', '3', ...]
      var nums = val.split('');
      // 合计
      var sum = 0;
      // 索引
      var index = 1;
      for (var i = 0; i < nums.length; i++) {
        // 当前索引是否为偶数
        if ((i + 1) % 2 === 0) {
          // 当前数组倒index的数字 * 2, 转数值
          var tmp = Number(nums[nums.length - index]) * 2;
          if (tmp >= 10) {
            // 将大于等于10的值转字符串
            var t = tmp + ''.split('');
            // tmp 值等于 字符串[0] + 字符串[1],  '16' 就是 1+6=7
            tmp = Number(t[0]) + Number(t[1]);
          }
          // 累加值
          sum += tmp;
        } else {
          // sum 累加当前数组倒index的数字
          sum += Number(nums[nums.length - index]);
        }
        // 累加索引
        index++;
      }
      // 如果值不是10的倍数, 则不是一个合法的银行卡号
      if (sum % 10 !== 0) {
        status = false;
      }

      return toStatus(status, '无效银行卡号');
    },
    // 金额
    amount: function(val, bits) {
      if (val == 0)
        return toStatus(false, '金额不能为空');
      // 默认金额为小数点2位
      var bit = bits || 6;
      var patten = new RegExp('^([1-9][\\d]{0,7}|0)(\\.[\\d]{1,' + bit + '})?$');
      return toStatus(patten.test(val), '无效的金额');
    },
    // 金额-可以为0
    amountAny: function(val, bits) {
      // 默认金额为小数点2位
      var bit = bits || 6;
      var patten = new RegExp('^([1-9][\\d]{0,7}|0)(\\.[\\d]{1,' + bit + '})?$');
      return toStatus(patten.test(val), '无效的金额');
    },
  };

  // 检测验证规则参数, 防止空规则以及规则重复以及不存在的验证规则
  var checkRules = function(rules) {
    // 处理空规则
    if (!rules || rules.length < 1)
      return false;
    // 处理不存在的验证规则
    var existRule = utils.every(rules, function(rule) {
      return $.isFunction(Compared.rules[rule.name])
    });
    if (!existRule)
      return false;
    // 处理重复规则
    var repeat = utils.repeat(rules, function(a, b) {
      return a.name !== b.name;
    });
    return repeat;
  };

  // 验证规则结构
  var ruleInfo = {
    name: '',
    value: '',
    params: null,
    element: null
  };


  var Compared = {
    // 规则集合
    rules: rules,
    // 验证状态枚举
    EnumStatus: EnumStatus,
    // 封装转换结果
    toStatus: toStatus,
    // 测试验证规则集合
    checkRules: checkRules,
    // 与验证, 所有规则验证通过则通过, rules数组顺序验证, 一旦有失败, 则忽略后续验证, 直接返回
    test: function (rules, callback) {
      if (!$.isFunction(callback)) {
        throw new Error('参数缺少, callback参数必须且是一个函数');
      }
      if (checkRules(rules) === false) {
        callback({status: EnumStatus.fail, 'message': '验证规则集参数未通过检测'});
        return;
      }

      // 是否存在验证失败
      var fail = false;
      // 是否存在异步验证
      var asyn = false;
      // 异步验证对象
      var asynInfo = [];

      // 包裹函数
      var ansyCallBack = function(ruleFuc, rule, status) {
        // 开始验证
        var sync = ruleFuc(rule.value, rule.params, rule.element, function(asynStatus) {
          if (fail) {
            return;
          }
          var syncItem = findByRuleName(asynInfo, rule.name);
          if (asynStatus.status === EnumStatus.fail) {
            if (syncItem)
              syncItem.status = EnumStatus.fail;
            fail = true;
          } else {
            if (syncItem)
              syncItem.status = EnumStatus.ok;
          }
          callback($.extend({}, status, asynStatus), asynInfo, judgComplete(asynInfo));
        });
        return sync;
      };

      for (var i = 0; i < rules.length; ++i) {
        if (fail)
          break;
        // 验证规则
        var rule = $.extend({}, ruleInfo, rules[i]);
        // 获取规则函数
        var ruleFuc = Compared.rules[rule.name];
        // 验证状态
        var status = {name: rule.name, params: rule.params, status: EnumStatus.fail, message: ''};
        // 开始验证
        var sync = ansyCallBack(ruleFuc, rule, status);
        // 异步验证状态
        if (sync === undefined) {
          asyn = true;
          asynInfo.push({
            ruleName: rule.name,
            status: EnumStatus.asyn
          });
          status.message = '验证中...';
          status.status = EnumStatus.asyn;
          callback(status, asynInfo);
        } else if (sync.status === EnumStatus.fail) {
          fail = true;
          callback($.extend({}, status, sync), asynInfo);
        } else if (sync.status === true && !asyn && i === rules.length - 1) {
          callback($.extend({}, status, sync), asynInfo);
        }
      }
    },
    // 或验证, 有一个规则验证通过则通过
    testByOne: function (rules, callback) {
      if (!$.isFunction(callback)) {
        throw new Error('Compared::testByOne callback参数是必须的');
      }

      // 同步状态是否存在成功
      var extisOk = false;
      // 是否存在异步验证
      var extisAsyn = false;
      // 异步验证总数量
      var total = 0;
      // 异步验证完毕数量
      var count = 0;

      // 规则结构
      var ruleInfo = {
        name: null,
        value: '',
        params: null,
        element: null
      };

      // 参数检测
      if (rules.length <= 0) {
        console.warn('Compared::test 验证规则数组为空!');
        callback({name: '', params: '', status: EnumStatus.fail, message: ''});
        return;
      }

      // 循环验证规则
      for (var i = 0; i < rules.length; ++i) {
        // 规则
        var rule = $.extend({}, ruleInfo, rules[i]);
        // 获取规则函数
        var ruleFuc = Compared.rules[rule.name];
        // 验证状态
        var status = {name: rule.name, params: rule.params, status: EnumStatus.fail, message: ''};

        // 处理没有验证规则
        if (!$.isFunction(ruleFuc)) {
          status.message = '没有此验证规则';
          callback(status);
          break;
        }

        // todo 需要判断  异步规则 如果验证失败, 则要判断自己是否为最后一个异步返回, 如果是最后一个, 就返回结果
        // 开始验证
        var sync = ruleFuc(rule.value, rule.params, rule.element, function(asynStatus) {
          if (extisOk)
            return;
          if (asynStatus.status === EnumStatus.ok)
            extisOk = true;
          if (count + 1 >= total) {
            callback($.extend({}, status, asynStatus));
          }
          ++count;
        });

        // 如果同步状态为空, 则判定这是一个异步规则
        if (sync === undefined) {
          status.message = '验证中...';
          status.status = EnumStatus.asyn;
          ++total;
          extisAsyn = true;
          callback(status);
        } else if (sync.status === EnumStatus.ok){
          extisOk = true;
          // 同步验证成功
          callback($.extend({}, status, sync));
          break;
        } else if (sync.status === EnumStatus.fail && i == (rules.length-1) && extisAsyn === false) {
          callback($.extend({}, status, sync));
        }

      }


    },
    // 添加规则
    add: function(name, method) {
      if (!$.isFunction(method)) {
        console.error('添加规则失败, 实现方法method参数必须为函数');
        return;
      }
      Compared.rules[name] = method;
    }
  };

  // export 导出
  window.Compared = Compared;

})();


/**
 * 提示对象
 */
(function() {

  // improt 导入
  var EnumStatus = window.Compared.EnumStatus;

  // 提示对象接口
  var PromptInterface = function() {};
  // 显示通知消息
  PromptInterface.prototype.show = function(ele, msg) {};
  // 显示通知等待消息
  PromptInterface.prototype.wait = function(ele, msg) {};
  // 隐藏通知消息
  PromptInterface.prototype.hide = function(ele) {};


  // 内置提示方式一: 不提示
  var PromptNone = function() {};
  // 显示通知消息
  PromptNone.prototype.show = function(ele, msg) {};
  // 显示通知等待消息
  PromptNone.prototype.wait = function(ele, msg) {};
  // 隐藏通知消息
  PromptNone.prototype.hide = function(ele) {};


  // 内置提示方式二: 追加提示消息
  var PromptMessage = function() {};
  // 显示通知消息
  PromptMessage.prototype.show = function(ele, msg) {
    var msgEle = $(ele).data('msg-element') || $($(ele).attr('prompt-ele'));
    // 是否存在验证消息元素
    var exist = (!msgEle || msgEle.length <= 0);
    if (exist) {
      msgEle = $('<span class="vd-error"></span>');
      $(ele).data('msg-element', msgEle);
    }

    msgEle.text(msg).show();
    if (exist) {
      ele.parent().append(msgEle);
    }
    ele.addClass('borderRed');
  };
  // 显示通知等待消息
  PromptMessage.prototype.wait = function(ele, msg) {
    this.show(ele, msg);
  };
  // 隐藏通知消息
  PromptMessage.prototype.hide = function(ele) {
    var msgEle = $(ele).data('msg-element') || $($(ele).attr('prompt-ele'));
    if (msgEle) {
      msgEle.hide();
    }
    ele.removeClass('borderRed');
  };


  // 提示接管对象
  var Prompt = {
    // 提示实例集合
    list: {
      'none': new PromptNone(),
      'message': new PromptMessage()
    },
    // 获取提示实例
    get: function(name) {
      if (!Prompt.list[name])
        throw new Error('Prompt.get: 不存在提示实例 ' + name);
      return Prompt.list[name];
    },
    // 封装提示, 根据状态自动调用不同方法
    autoShow: function(name, status, ele, msg) {
      var prompt = Prompt.get(name);
      switch(status) {
        case EnumStatus.ok:
          prompt.hide(ele, msg);
          break;
        case EnumStatus.fail:
          prompt.show(ele, msg);
          break;
        case EnumStatus.asyn:
          prompt.wait(ele, msg);
          break;
        default:
          console.error('无效的验证状态 %s , 验证状态应为EnumStatus枚举类型', status);
      }
    },
    // 注入提示实例
    add: function(name, prompt) {
      if (!name || !prompt)
        return false;
      Prompt.list[name] = prompt;
      return true;
    }
  };


  // export 导出
  window.Prompt = Prompt;

})();

/**
 * 表单验证对象
 */
(function() {

  // improt 导入
  var EnumStatus = window.Compared.EnumStatus;
  var EnumTrigger = window.Enums.EnumTrigger;
  var utils = window.validators.utils;
  var Prompt = window.Prompt;
  var Compared = window.Compared;

  var FormValidator = function(form, options) {
    var opt = {
      // 提示方式
      prompt: FormValidator.prompt,
      // 触发事件
      trigger: EnumTrigger.input | EnumTrigger.change,
      // 规则树
      tree: {},
      // 是否调试
      debug: false,
      // 是否禁用表单验证
      desabled: false,
      // 钩子回调-表单提交开始验证
      onSubmit: null,
      // 钩子回调-自定义验证控制
      onCustomize: null
    };

    this.currentForm = form;
    this.options = $.extend({}, opt, options);
    this.init();
  };

  /**
   * @description 给元素添加验证规则
   * @param elements {Element} HTML元素
   * @param rules {Array|Object} 规则集合, 或者单个验证规则
   * @param message {String|Object} 验证提示消息
   * @param trigger {EnumTrigger} 触发事件枚举
   * @param onchange {Function}   验证后回调
   * @returns {Boolean}
   */
  FormValidator.prototype.add = function(elements, rules, message, trigger, onchange) {
    var self = this;
    elements.each(function() {
      var ele = $(this);
      var tree = self.options.tree[self.flag(ele)];
      if (!tree)
        tree = {};
      var ruleForAttr = [];
      var _rule = {
        name: '',
        params: null,
        value: ele.val(),
        element: ele
      };
      // 添加自定义消息
      var original_message = utils.toJson(ele.attr('message'));
      if (original_message) {
        ele.attr('message', JSON.stringify($.extend({}, original_message, message)));
      } else {
        tree['message'] = $.extend({}, tree['message'], message);
      }
      // 添加触发事件
      var original_trigger = utils.toJson(ele.attr('trigger'));
      if (original_trigger) {
        ele.attr('trigger', JSON.stringify($.extend({}, original_trigger, trigger)));
      } else {
        tree['trigger'] = $.extend({}, tree['trigger'], trigger);
      }

      // 添加回调函数
      if (tree && onchange) {
        tree['onchange'] = onchange;
      }

      for (var i = 0; i < rules.length; ++i) {
        var rule = $.extend({}, _rule, rules[i]);
        var ruleAttr = ele.attr(rule.name);
        if (ruleAttr !== undefined) {
          // 在属性上修改
          ele.attr(rule.name, JSON.stringify(rule.params));
        } else {
          // 在选项上增加或者修改
          tree['rules'] = self.addRules(tree['rules'], rule);
        }
      }
      self.options.tree[self.flag(ele)] = tree;
    });
  };

  /**
   * @description 修改元素验证规则
   * @param ele {Element} HTML元素
   * @param rules {Array|Object} 规则集合, 或者单个验证规则
   * @param message {String|Object} 验证提示消息
   * @param trigger {EnumTrigger} 触发事件枚举
   * @param onchange {Function}   验证后回调
   * @returns {Boolean}
   */
  FormValidator.prototype.modify = function(ele, rules, message, trigger, onchange) {
    this.add(ele, rules, message, trigger, onchange);
  };

  /**
   * @description 删除元素验证规则
   * @param ele {Element} HTML元素
   * @param rules {Array|Object} 规则集合, 或者单个验证规则
   * @returns {Boolean}
   */
  FormValidator.prototype.remove = function(elements, ruleNames) {
    var self = this;

    elements.each(function() {
      var ele = $(this);
      var tree = self.options.tree[self.flag(ele)];
      $.each(ruleNames, function() {
        var ruleName = this;
        ele.removeAttr(ruleName);
        if (tree && tree['rules']) {
          tree['rules'] = utils.map(tree['rules'], function(rule) {
            return rule.name != ruleName;
          });
        }
      });

      self.options.tree[self.flag(ele)] = tree;

    });

  };

  /**
   * @description 获取元素规则树
   * @param ele {Element} HTML元素
   * @returns {Object}
   */
  FormValidator.prototype.get = function(ele) {
    var struct = {
      rules: [],
      message: {},
      trigger: {},
      debug: false,
      prompt: this.options.prompt,
      onchange: null,
      testMethod: 'test'
    };
    var _rule = {
      name: '',
      params: null,
      value: ele.val(),
      element: ele,
      testOr: false
    };

    // 选项合并
    var structForOpt = this.options.tree[this.flag(ele)];
    struct = $.extend({}, struct, structForOpt);
    // 获取自定义消息
    var message = utils.toJson(ele.attr('message'));
    if (message)
      struct.message = message;
    // 获取触发事件
    var trigger = utils.toJson(ele.attr('trigger'));
    if (trigger)
      struct.trigger = trigger;
    // 获取是否调试
    var debug = ele.attr('debug');
    if (debug !== undefined && debug !== null)
      struct.debug = debug;
    // 获取提示方法
    var prompt = ele.attr('prompt');
    if (prompt !== undefined && prompt !== null)
      struct.prompt = prompt;
    // 获取验证方法, and验证(默认) 还是 or验证
    var testMethod = ele.attr('testMethod');
    if (testMethod !== undefined && testMethod !== null)
      struct.testMethod = testMethod;
    // 从元素属性上获取规则
    for (var ruleName in Compared.rules) {
      var ruleAttr = ele.attr(ruleName);
      if (ruleAttr === undefined)
        continue;
      var params = utils.toJson(ruleAttr);
      // 覆盖选项同名规则
      var rule = $.extend({}, _rule, {
        name: ruleName,
        params: params
      });
      this.addRules(struct.rules, rule);
    }
    // 给选项规则自动添加 value, element 参数
    $.each(struct.rules, function() {
      var rule = this;
      rule.value = ele.val();
      rule.element = ele;
    });
    return struct;
  };
  

  /**
   * @description 加入/覆盖同名规则
   * @param rules {Array} 规则集合
   * @param rule {Object} 规则对象
   * @returns {Array}
   */
  FormValidator.prototype.addRules = function(rules, rule) {
    if (!rules)
      rules = [];

    // 如果规则集合里存在同名规则，则覆盖, 否则加入数组
    var extis = false;
    var index = -1;

    for (var i = 0; i < rules.length; ++i) {
      var currule = rules[i];
      if (currule.name === rule.name) {
        index = i;
        extis = true;
      }
    }

    if (extis) {
      rules.splice(index, 1, rule);
    } else {
      rules.push(rule);
    }
    return rules;
  };


  /**
   * @description 元素是否可进行验证
   * @param ele {Element} HTML元素
   * @returns {Boolean}
   */
  FormValidator.prototype.available = function(ele) {
    // 是否处于验证锁
    var lock = ele.attr('lock');
    if (lock !== undefined) {
      // console.log('元素正在异步验证中, 不进行验证');
      return 'lock';
    }

    // 是否禁用
    var disabledForAttr = ele.attr('disabled');
    var disabledForOpt = this.options.desabled;
    if (disabledForAttr || disabledForOpt) {
      // console.log('元素禁用或验证选项禁用开启, 不进行验证');
      return false;
    }

    // 是否只读
    var readonly = ele.attr('readonly');
    // 是否input只读但是需要验证
    var readonly_verif = ele.hasClass('readonly_verif');
    if (readonly && !readonly_verif) {
      return false;
    }

    // 是否可选
    var requiredNot = ele.attr('requiredNot');
    if (requiredNot !== undefined) {
      if (ele.val() !== '') {
        return true;
      } else {
        var prompt = this.options.prompt;
        // 获取提示方法
        var promptForAttr = ele.attr('prompt');
        if (promptForAttr !== undefined && promptForAttr !== null)
          prompt = promptForAttr;
        this.status(prompt, ele, true);
        // 发送 requiredNot事件, 用于接收可选元素输入空值的时候
        ele.trigger('requiredNot');
        return false;
      }
    }

    // 是否在选项上定义了规则
    var rule = this.options.tree[this.flag(ele)];
    // 是否必填
    var required = ele.attr('required');

    // 是否验证钩子
    var validHook = ele.data('valid-hook');
    if ($.isFunction(validHook) && validHook(ele)) {
      return true;
    }

    if (required || rule) {
      return true;
    }
    return false;
  };


  /**
   * @description 设置元素验证状态
   * @param prompt {String} 提示方法
   * @param ele {Element} HTML元素
   * @param status {Boolean} 验证状态
   * @param message {String} 提示文本
   * @returns {Boolean}
   */
  FormValidator.prototype.status = function(prompt, ele, status, message, onchange) {
    // 设置验证锁
    if (status === EnumStatus.asyn) {
      ele.attr('lock', true);
      ele.attr('disabled', 'disabled');
    } else {
      ele.removeAttr('lock');
      ele.removeAttr('disabled');
    }
    // 记录上一次验证状态
    var prevStatus = ele.attr('enumstatus');
    // 根据验证状态设置对应样式
    ele.attr('enumstatus', status);
    ele.data('status', {status: status, message: message, element: ele});
    ele.get(0).enumstatus = status;
    // 标记从异步状态转换到其他状态的标志
    if (prevStatus == EnumStatus.asyn && status.status !== EnumStatus.asyn) {
      ele.get(0).ValidtorComplete = true;
    }

    // 显示提示消息
    Prompt.autoShow(prompt, status, ele, message);
    // 触发回调
    if ($.isFunction(onchange) && prevStatus !== String(status))
      onchange(ele, status, message);
  };



  /**
   * @description 验证单个元素
   * @param ele {Element} HTML元素
   * @param callback {Function} 结果回调
   * @param trigger {EnumTrigger} 触发事件
   * @returns
   */
  FormValidator.prototype.element = function(ele, callback, trigger) {
    var self = this;
    // 获取元素规则树
    var struct = this.get(ele);
    var _status = {name: '', status: EnumStatus.fail, message: ''};

    // 判断元素是否可以验证
    var available = this.available(ele);
    if (available !== true) {
      _status.status = available === 'lock' ? EnumStatus.asyn : EnumStatus.ok;
      if ($.isFunction(callback) && available === 'lock')
        callback(_status);
      //self.status(struct.prompt, ele, _status.status);
      return;
    }

    // 剔除不匹配当前事件的规则
    struct = this.delNoTrigger(struct, trigger);
    if (struct.rules.length === 0) {
      _status.status = EnumStatus.ok;
      if ($.isFunction(callback))
        callback(_status);
      return;
    }

    ele.get(0).ValidtorComplete = false;
    Compared[struct.testMethod](struct.rules, function(status, asynInfo, complete) {
      var message = struct.message[status.name];
      self.status(struct.prompt, ele, status.status, utils.format(message || status.message, status.params), struct.onchange);
      // 将事件触发element验证的异步结果, 给form方法接收
      if (status.status !== EnumStatus.asyn) {
        if (ele.get(0).ValidtorComplete)
          ele.trigger('FormValidator_Status', status, complete);
      }

      if ($.isFunction(callback))
        callback(status, complete);
    });

  };

  /**
   * @description 验证整个表单
   * @param callback {Function} 结果回调
   * @returns {Object}
   */
  FormValidator.prototype.form = function(callback) {
    var self = this;
    var currentForm = self.currentForm;
    // 验证对象
    var inputs = $('input, select, textarea', currentForm);
    // 验证对象列表
    var validatorElements = $();

     // 判断状态
     var judg = function() {
      // 如果没有验证失败, 且存在异步验证, 则callbacl(EnumStatus.ansy)转圈

      // 是否存在验证失败
      var fail = false;
      // 是否存在异步验证
      var asyn = false;
      var errInfo = {};

      validatorElements.each(function() {
        var ele = $(this);
        var status = ele.attr('enumstatus');
        
        if (status == 'false') {
          fail = true;
          if (!errInfo.element)
            errInfo = ele.data('status');
          return;
        } else if (status == 'asyn') {
          asyn = true;
        }
      });


      if (fail) {
        callback({ status: EnumStatus.fail, errInfo: errInfo});
      } else if (asyn) {
        callback({ status: EnumStatus.asyn, errInfo: errInfo});
      } else {
        callback({ status: EnumStatus.ok, errInfo: errInfo});
      }

     };


    inputs.each(function(i) {
      // 处理锁定状态
      var ele = $(this);
      // 元素是否可以验证
      var available = self.available(ele);
      if (available === false)
        return;


      if (available === 'lock') {
        // 元素正在异步验证
        ele.bind('FormValidator_Status', function(event, status) {
          judg();
          ele.unbind('FormValidator_Status');
        });
      } else if (available === true) {
        self.element(ele, function (status) {
          if (self.options.debug && status.status !== EnumStatus.ok)
            console.warn(ele[0], '元素验证结果', status);
          if (ele.get(0).ValidtorComplete) {
            judg();
          }
        });
      }
      validatorElements = validatorElements.add(ele);
    });
    // 同步验证完毕后主动调用一次
    judg();

  };

  /**
   * @description 移除不符合当前触发事件的验证规则
   * @param struct {Object} 验证对象
   * @param trigger {EnumTrigger} 触发事件
   * @returns {Object}
   */
  FormValidator.prototype.delNoTrigger = function(struct, trigger) {
    if (!trigger)
      return struct;
    // 待删除索引数组
    var delIndexs = [];
    $(struct.rules).each(function(i) {
      var ruleName = this.name;
      if ( (struct.trigger[ruleName] & EnumTrigger[trigger]) === 0 && struct.trigger[ruleName] !== undefined) {
        delIndexs.push(i);
      }
    });

    var result = utils.map(struct.rules, function(rule, i) {
      return delIndexs.indexOf(i) == -1;
    });
    struct.rules = result;
    return struct;
  };

  /**
   * @description 提交表单
   * @param ignore {Boolean} 是否忽略验证
   * @returns
   */
  FormValidator.prototype.submit = function(ignore) {
    this.currentForm.trigger('submit', Boolean(ignore));
  };


  /**
   * 获取元素选项标识
   * @param ele {Element} 元素
   */
  FormValidator.prototype.flag = function(ele) {
    // 使用指定属性作为标识, 否则使用name
    var flag = ele.attr('data-flag');
    var name = ele.attr('name');
    return flag || name;
  };

  /**
   * @description 初始化验证实例
   * @returns
   */
  FormValidator.prototype.init = function() {
    var self = this;
    var form = self.currentForm;
    // 附加实例到元素
    form.get(0).FormValidator = this;
    // 禁用浏览器默认验证
    form.attr('novalidate', 'novalidate');

    // 使用form属性提示方式
    var prompt = form.attr('prompt');
    if (prompt)
      self.options.prompt = prompt;
    // 监听支持的触发事件
    for (var trigger in EnumTrigger) {
      form.unbind(trigger, FormValidator.hander);
      form.bind(trigger, {
        trigger: trigger,
        validator: self
      }, FormValidator.hander);
    }
    // 监听表单提交事件
    form.unbind('submit', self.handlerSubmit);
    form.bind('submit', {validator: self}, self.handlerSubmit);
  };


  /**
   * @description 集中处理触发事件
   * @param ele {Element} 元素
   * @param trigger {EnumTrigger} 触发事件
   * @returns
   */
  FormValidator.prototype.handler = function(ele, trigger) {
    // 获取元素选项标识
    var flag = this.flag(ele);
    if (!flag)
      return;
    // 获取表单选项触发事件
    var triggerForForm = parseInt(this.options.trigger);
    // 存在则验证
    if (EnumTrigger[trigger] & triggerForForm) {
      this.element(ele, null, trigger);
    }
  };

  /**
   * @description 处理表单提交事件
   * @param ele {Element} 元素
   * @param trigger {EnumTrigger} 触发事件
   * @returns
   */
  FormValidator.prototype.handlerSubmit = function(event, ignore) {
    var self = event.data.validator;
    var form = self.currentForm;
    // 是否存在自定义钩子函数
    var extis = $.isFunction(self.options.onCustomize);
    var customize = false;
    // 发送静态事件
    var emiss = function(status) {
      if ($.isFunction(FormValidator.SubmitHandler))
        FormValidator.SubmitHandler(status, form);
    }
    // 忽略验证, 直接提交表单
    if (ignore || self.options.desabled) {
      form.trigger(FormValidator.validatorSubmit);
      emiss(true);
      return;
    }

    self.form(function(status) {
      if (status.status === EnumStatus.fail && $.isFunction(FormValidator.submitError)) {
        FormValidator.submitError(status);
      }
      // 提交事件
      if ($.isFunction(self.options.onSubmit))
        self.options.onSubmit(status.status);
      if (status.status === EnumStatus.asyn) {
        emiss(true);
      } else if (status.status === EnumStatus.ok) {
        if (extis) {
          customize = self.options.onCustomize(status);
          if (customize) {
            self.submit(true);
          } else {
            emiss(false);
          }
        } else {
          self.submit(true);
        }
      } else if (status.status === EnumStatus.fail) {
        emiss(false);
      }
    });

    event.preventDefault();
    return false;

  };

  /**
   * @description 销毁验证实例
   * @returns
   */
  FormValidator.prototype.destroy = function() {
    var self = this;
    var form = self.currentForm;

    // 移除操作
    $('input, select').each(function() {
      var ele = $(this);
      ele.removeAttr('lock');
      ele.removeAttr('EnumStatus');
    });
    // 移除支持的触发事件
    for (var key in EnumTrigger) {
      var trigger = EnumTrigger[key];
      form.unbind(String(trigger), FormValidator.hander);
    }
    // 移除表单提交事件
    form.unbind('submit', self.handlerSubmit);
  };



  // 静态========================
  FormValidator.hander = function(event) {
    var validator = event.data.validator;
    validator.handler($(event.target), event.data.trigger);
  };
  FormValidator.prompt = 'message';
  // 钩子函数-表单提交
  FormValidator.SubmitHandler = null;
  // 钩子函数-表单提交错误时 (用于表单提交错误则显示悬浮提示)
  FormValidator.submitError = null;
  // 事件-表单提交
  FormValidator.validatorSubmit = 'validatorsubmit';
  // export 导出
  window.FormValidator = FormValidator;

  $.fn.FormValidator = function(options) {
    var last = null;
    this.each(function() {
      var element = $(this);
      var lastValidator = this.FormValidator;
      if (lastValidator) {
        lastValidator.destroy();
      }
      lastValidator = new FormValidator(element, options);
      last = lastValidator;
      this.FormValidator = lastValidator;
      element.data('FormValidator', lastValidator);
    });
    return last;
  };




})();