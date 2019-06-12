'use strict';

// / <reference path="E:\DefinitelyTyped\jquery\jquery.d.ts" />

/**
 * 首页功能
 */

// 弹出窗口
var Init_PoP_Window = function Init_PoP_Window() {
  // 内容弹出框模板
  var wd_template = '\n    <div class="global-wd">\n      <div class="mask"></div>\n      <div class="container">\n        <span class="middle"></span>\n        <div class="box-out ib">\n          <div class="box-in"">\n            <div class="head">\n              <p class="t1 title"></p>\n              <div class="close-2 close-btn"></div>\n            </div>\n            <div class="body">\n              <div class="if-box">\n                <iframe src="" frameborder="0"></iframe>\n              </div>\n            </div>\n            <div class="foot">\n              <a class="mr-10 btn reverse close-btn ">\u53D6\u6D88</a>\n              _BTN_\n            </div>\n          </div>\n        </div>\n      </div>\n    </div>\n  ';

  // z-index基数
  var zindex = 90;
  // 弹出框计数
  var count = 0;

  var WindowPop = function WindowPop(title, url, mask, rename, link) {
    var self = this;
    var template = wd_template;
    // 如果存在 data-link, 则默认的确定按钮点击会跳转到那个链接
    template = wd_template.replace('_BTN_', link ? '<a href="' + link + '" target="_blank" class="mr-10 btn close-btn">\u786E\u5B9A</a>' : '<a href="javascript:void(0);" class="mr-10 btn">确定</a>');
    if (rename) {
      template = template.replace('确定', rename);
    }
    // 通过模板创建dom
    this.element = $(template).css({
      'z-index': zindex + count
    }).hide();
    // 关闭按钮
    this.DOM_close = $('.close-btn', this.element);
    // head
    this.DOM_head = $('.head', this.element);
    // 框架
    this.DOM_iframe = $('iframe', this.element);
    var DOM_iframe = this.DOM_iframe;
    // 遮罩
    this.DOM_mask = $('.mask', this.element);
    // 标题
    this.DOM_title = $('.title', this.element).text(title);
    // 页脚
    this.DOM_foot = $('.foot', this.element);
    // 处理不显示遮罩
    if (!mask) {
      this.DOM_mask.remove();
    }
    // 加入dom
    $(document.body).append(this.element);
    this.DOM_iframe.attr('src', url);
    this.element.fadeIn();

    DOM_iframe.bind('load', function () {
      var InPop = DOM_iframe.contents().find('.in-pop');
      //console.log(InPop.outerHeight());
      // 获取框架内宽度
      DOM_iframe.css({
        height: InPop.outerHeight(),
        width: InPop.outerWidth(),
        'max-height': $(document.body).outerHeight() * 0.80 + 'px', // 最大高度应该计算屏幕高度的百分比
        'max-width': '100%'
      });
      InPop.css({
        'max-width': '100%',
        'overflow': 'hidden'
      });
      // 监听框架内的 close.btn 来关闭窗口
      DOM_iframe.contents().find('.close-btn').click(function () {
        self.destroy();
      });

      setTimeout(function () {
        // 获取框架内宽度
        DOM_iframe.css({
          height: InPop.outerHeight(),
          width: InPop.outerWidth()
        });
      }, 30);
    });
    // 增加计数
    ++count;
    // 绑定事件
    this.bind();
  };

  WindowPop.prototype.setFoot = function (data) {
    /**
     * data = [
     *  {title: '标题', reverse: false, close: false, event: fuc}
     * ];
     */

    var DOM_foot = this.DOM_foot;

    var self = this;
    var result = $('<div></div>');

    for (var i = 0; i < data.length; ++i) {
      var value = data[i];
      var btn = $('<button class="mr-10 btn \n      ' + (value.close ? 'close-btn' : '') + '  \n      ' + (value.reverse ? 'reverse' : '') + '">\n      ' + value.title + '</button>');
      if (value.event) {
        btn.bind('click', self, value.event);
      }
      if (value.close) {
        btn.bind('click', self, function () {
          return self.destroy();
        });
      }
      result.append(btn);
    }

    DOM_foot.html(result);
  };

  WindowPop.prototype.bind = function () {
    var DOM_close = this.DOM_close,
        element = this.element,
        DOM_head = this.DOM_head;

    var self = this;
    DOM_close.click(function () {
      self.destroy();
    });

    var box = $('.box-out', element);
    var pos = box.position();
    var mouseX = 0;
    var mouseY = 0;

    var moveWindow = function moveWindow(_event) {
      var offsetX = _event.pageX - mouseX;
      var offsetY = _event.pageY - mouseY;

      box.css({
        'position': 'absolute',
        'left': offsetX + pos.left,
        'top': offsetY + pos.top
      });
    };

    // 拖拽
    DOM_head.mousedown(function (event) {
      pos = box.position();
      mouseX = event.pageX;
      mouseY = event.pageY;
      DOM_head.mousemove(moveWindow);
    });

    element.mouseup(function () {
      DOM_head.unbind('mousemove', moveWindow);
    });
  };

  WindowPop.prototype.destroy = function () {
    var element = this.element;

    element.fadeOut('normal', function () {
      element.remove();
      --count;
    });
  };

  WindowPop.prototype.bindTrigger = function (related) {
    var DOM_iframe = this.DOM_iframe,
        DOM_foot = this.DOM_foot;

    // 获取页尾的按钮

    $('.btn', DOM_foot).each(function (index, element) {
      $(element).click(function () {
        // 寻找对应元素点击
        DOM_iframe.contents().find(related[index]).trigger('click');
      });
    });
  };

  window.WindowPop = WindowPop;
};

Init_PoP_Window();

// 导航栏管理
var NavManage = {
  foldActive: 'active',
  itemAvtive: 'on',
  navigation: $('#Navigation-Bar'),
  fold: null,
  item: null,
  Init_Event: function Init_Event() {
    $('.menu-title', NavManage.navigation).click(NavManage.Selected_menuTitle);
    $('.sub-title', NavManage.navigation).click(NavManage.FoldMenus);
    $('.menus a', NavManage.navigation).click(NavManage.Selected);
  },
  FoldMenus: function FoldMenus() {
    if (NavManage.fold) {
      NavManage.fold.parent().removeClass(NavManage.foldActive);
      if (NavManage.fold[0] === this) {
        NavManage.fold = null;
        return;
      }
    }
    NavManage.fold = $(this);
    NavManage.fold.parent().addClass(NavManage.foldActive);
  },
  Selected_menuTitle: function Selected_menuTitle() {
    if (NavManage.item) {
      NavManage.item.removeClass(NavManage.itemAvtive);
      NavManage.item.parent().removeClass(NavManage.foldActive);
    }
    NavManage.item = $(this);
    NavManage.item.parent().addClass(NavManage.foldActive);
    if (window.tabsManage) {
      window.tabsManage.Create(NavManage.item.attr('data-url'), NavManage.item.text());
    }
  },
  Selected: function Selected() {
    if (NavManage.item) {
      NavManage.item.parent().removeClass(NavManage.foldActive);
      NavManage.item.removeClass(NavManage.itemAvtive);
    }
    NavManage.item = $(this);
    NavManage.item.addClass(NavManage.itemAvtive);
    if (window.tabsManage) {
      window.tabsManage.Create(NavManage.item.attr('data-url'), NavManage.item.text());
    }
  }
};
// 导航栏初始化
NavManage.Init_Event();

var toScrollFrame = function toScrollFrame(iFrame, mask) {
  if (!navigator.userAgent.match(/iPad|iPhone/i)) return false;
  // do nothing if not iOS devie
  var mouseY = 0;
  var mouseX = 0;
  jQuery(iFrame).ready(function () {
    // wait for iFrame to load
    // remeber initial drag motition
    jQuery(iFrame).contents()[0].body.addEventListener('touchstart', function (event) {
      mouseY = event.targetTouches[0].pageY;
      mouseX = event.targetTouches[0].pageX;
    });

    // update scroll position based on initial drag position
    jQuery(iFrame).contents()[0].body.addEventListener('touchmove', function (event) {
      event.preventDefault();
      // prevent whole page dragging
      var box = jQuery(mask);
      box.scrollLeft(box.scrollLeft() + mouseX - event.targetTouches[0].pageX);
      box.scrollTop(box.scrollTop() + mouseY - event.targetTouches[0].pageY);
      // mouseX and mouseY don't need periodic updating, because the current position
      // of the mouse relative to th iFrame changes as the mask scrolls it.
    });
  });

  return true;
};

/**
 * 构造函数
 * @param tabSize tab容器
 * @param iframeBox 框架容器
 * @param loadImg 等待图片
 */
function tabsManage(tabSize, iframeBox, loadImg) {
  this.DOM_tabSize = tabSize;
  this.DOM_iframeBox = iframeBox;
  this.DOM_loadImg = loadImg.fadeOut();
  // tab项模版
  this.Tab_Template = '<li><a class="tabs-inner" href="javascript:void(0)">\n  <span class="title">_Title_</span><span class="close"></span></a></li>';
  // iframe模版
  this.Iframe_Template = '<div class="div-iframe"><iframe src="_Url_" frameborder="0"></iframe></div>';
  // tab选择类
  this.select_class = 'selected';
  // iframe选择类
  this.iframe_class = 'active';
  // data索引标志
  this.IndexFlag = 'data-Index';
  // tab数组
  this.tabs = [];
  // 当前tab索引
  this.tab_index = undefined;
  // 监听事件
  tabSize.bind('click', this, this.Event_Handler);
}
/**
 * 创建一个选项卡
 * @param url  框架地址
 * @param title 选项卡标题
 */
tabsManage.prototype.Create = function (url, title) {
  // 变量申明
  var tabs = this.tabs,
      Tab_Template = this.Tab_Template,
      Iframe_Template = this.Iframe_Template,
      IndexFlag = this.IndexFlag,
      DOM_tabSize = this.DOM_tabSize,
      DOM_iframeBox = this.DOM_iframeBox,
      DOM_loadImg = this.DOM_loadImg;

  var self = this;

  // 参数检测
  if (!url || !title || url === '' || title === '') {
    return;
  }
  // 如果存在则切换过去
  for (var i = 0; i < tabs.length; ++i) {
    if (tabs[i].id === title) {
      this.Select(i);
      return;
    }
  }

  // 创建tab标签元素
  var tab = $(Tab_Template.replace('_Title_', title));
  tab.data(IndexFlag, tabs.length);

  var iframe = $(Iframe_Template.replace('_Url_', url));
  // 加入dom
  DOM_tabSize.append(tab);
  DOM_iframeBox.append(iframe);

  // 加入数组
  tabs.push({
    'tab': tab,
    'id': title,
    'url': url,
    'title': title,
    'iframe': iframe,
    'isload': false
  });

  // 缓存当前索引
  var cache_index = tabs.length - 1;
  // 防止快速创建标签, 导致过度动画跟不上
  DOM_loadImg.stop(true, true);

  // 处理第一个标签的特殊样式
  if (tabs.length === 1) {
    tab.addClass('first');
  }

  toScrollFrame($('iframe', iframe), iframe);

  // 激活当前创建标签
  this.Select(tabs.length - 1);
  // 获取div-iframe内的框架
  // 监听载入事件
  $('iframe', iframe).bind('load', function () {
    tabs[cache_index].isload = true;
    DOM_loadImg.fadeOut();
    if (navigator.userAgent.match(/iPad|iPhone/i)) {
      self.Select(cache_index);
    }
  });

  if (window.flush_tabs) {
    window.flush_tabs(true);
  }
};
/**
 * 切换选项卡
 * @param index 选项卡索引
 */
tabsManage.prototype.Select = function (index) {
  // 变量声明
  var tabs = this.tabs,
      iframe_class = this.iframe_class,
      select_class = this.select_class,
      DOM_loadImg = this.DOM_loadImg,
      tab_index = this.tab_index;
  // 参数验证

  index = parseInt(index);
  if (isNaN(index) || index === null) {
    return;
  }
  if (index < 0 || index >= tabs.length) {
    return;
  }
  // 移除上一个激活样式
  if (tab_index >= 0 && tab_index < tabs.length) {
    tabs[tab_index].tab.removeClass(select_class);
    tabs[tab_index].iframe.removeClass(iframe_class).fadeOut();
  }
  // 显示加载等待图片
  if (!tabs[index].isload) {
    DOM_loadImg.fadeIn();
  }
  // 添加激活样式
  tabs[index].tab.addClass(select_class);
  tabs[index].iframe.addClass(iframe_class).fadeIn();
  // 设置当前索引
  this.tab_index = index;
};
/**
 * 销毁选项卡
 * @param index 选项卡索引
 */
tabsManage.prototype.Destroy = function (index) {
  // 变量声明
  var tabs = this.tabs,
      tab_index = this.tab_index;
  // 参数验证

  index = parseInt(index);
  if (isNaN(index) || index === null) {
    return;
  }
  if (index <= 0 || index >= tabs.length) {
    return;
  }

  var tab = tabs[index].tab;
  var iframe = tabs[index].iframe;
  // 计算删除后当前索引
  var tab_index_result = tab_index;
  // 要删除标签页在激活标签页前面
  if (index < tab_index) {
    tab_index_result = tab_index - 1;
  }
  // 要删除标签页等于激活标签页
  if (index === tab_index && index === tabs.length - 1) {
    tab_index_result = index - 1;
  }

  // 从数组中删除
  tabs.splice(index, 1);
  // 从dom中删除
  tab.remove();
  iframe.remove();
  // 刷新tab列表的索引
  this.SetIndex();
  this.tab_index = undefined;
  // 重新激活选项卡
  this.Select(tab_index_result);
  if (window.flush_tabs) {
    window.flush_tabs();
  }
};
/**
 * 设置选项卡标题
 */
tabsManage.prototype.SetTitle = function (index, title) {
  var tabs = this.tabs;

  index = parseInt(index);
  if (isNaN(index) || index === null || !title) {
    return;
  }
  if (index < 0 || index >= tabs.length) {
    return;
  }
  var tab = tabs[index].tab;
  tabs[index].title = title;
  $('.title', tab).text(title);
};
/**
 * 刷新选项卡
 * @param index 选项卡索引
 * @param url 重定向的标题
 * @param title 重新设置标题
 */
tabsManage.prototype.Flush = function (index, url, title) {
  // 变量声明
  var tabs = this.tabs;
  // 参数验证

  index = parseInt(index);
  if (isNaN(index) || index === null) {
    return;
  }
  if (index < 0 || index >= tabs.length) {
    return;
  }
  this.SetTitle(index, title);
  tabs[index].url = url;
  $('iframe', tabs[index].iframe).attr('src', url || tabs[index].url);
  // tabs[index].iframe[0].contentWindow.location.reload(true);
};
/**
 * 销毁其他选项卡
 * @param index 选项卡索引
 */
tabsManage.prototype.Destroy_Other = function (index) {
  // 变量声明
  var tabs = this.tabs;
  // 参数验证

  index = parseInt(index);
  if (isNaN(index) || index === null) {
    return;
  }
  if (index < 0 || index >= tabs.length) {
    return;
  }
  // 从dom中删除
  for (var i = 1; i < tabs.length; ++i) {
    if (i === index) {
      continue;
    }
    tabs[i].tab.remove();
    tabs[i].iframe.remove();
  }
  // 从数组中删除
  if (index === 0) {
    tabs = [tabs[0]];
  } else {
    tabs = [tabs[0], tabs[index]];
  }
  // 刷新tab列表的索引
  this.SetIndex();
  // 重新激活选项卡
  this.Select(tabs.length === 1 ? 0 : 1);
  if (window.flush_tabs) {
    window.flush_tabs();
  }
};
/**
 * 重新设置选项卡索引
 * private
 */
tabsManage.prototype.SetIndex = function () {
  var tabs = this.tabs,
      IndexFlag = this.IndexFlag;

  for (var i = 0; i < tabs.length; ++i) {
    tabs[i].tab.data(IndexFlag, i);
  }
};
/**
 * 事件处理函数
 * private
 */
tabsManage.prototype.Event_Handler = function (event) {
  var IndexFlag = event.data.IndexFlag;

  var select = event.target.nodeName === 'LI' ? $(event.target) : $(event.target).parents('li');
  var index = select.data(IndexFlag);

  if ($(event.target).hasClass('close')) {
    event.data.Destroy(index);
  } else {
    event.data.Select(index);
  }
};

window.tabsManage = new tabsManage($('#tabs-size'), $('#iframeBox'), $('#loadIMG'));

/**
 * 选项卡溢出控制
 */
var overflow_control = function overflow_control() {
  // 选项卡总容器
  var box = $('#tabs-in');
  // 真实移动容器
  var tabsSize = $('#tabs-size');
  // 控制箭头
  var arrow = $('#arrow-lr');
  // 左箭头
  var leftArrow = $('.arrow-left', arrow);
  // 左箭头
  var rightArrow = $('.arrow-right', arrow);
  // 步长
  var distance = 140;
  // 是否显示
  var isShow = false;
  // 刷新选项卡布局
  var flush_tabs = function flush_tabs(toEnd) {
    // 容器宽度
    var box_width = box.outerWidth();
    // 选项卡宽度
    var tabs_width = tabsSize.outerWidth();
    // 溢出宽度
    var over_width = box_width - tabs_width;

    if (over_width > 0) {
      if (isShow) {
        // 没有溢出
        arrow.fadeOut();
        // 还原边距
        tabsSize.css({
          'margin': '0',
          'left': '0'
        });
        isShow = false;
      }
    } else {
      if (!isShow) {
        // 溢出了, 显示控制箭头
        arrow.fadeIn();
        // 增加边距
        tabsSize.css({
          'margin': '0 ' + rightArrow.outerWidth() + 'px 0 ' + leftArrow.outerWidth() + 'px'
        });
        isShow = true;
      }
      // 新建选项卡时, 自动移动到底部
      if (toEnd === true) {
        tabsSize.animate({
          'left': '-' + (Math.abs(over_width) + rightArrow.outerWidth() * 2 + 5)
        });
      }
    }
  };
  // 箭头事件
  leftArrow.click(function () {
    // 选项卡集合向右移动, left 为正数
    var next_left = tabsSize.position().left + distance;

    // 如果溢出宽度小于一次移动步长, 则直接移动完剩余步长, 否则移动一个步长
    if (next_left > 0) {
      tabsSize.animate({
        'left': '0'
      });
    } else {
      tabsSize.animate({
        'left': next_left
      });
    }
  });
  rightArrow.click(function () {
    // 选项卡集合向左移动, left 为负数
    // 容器宽度
    var box_width = box.outerWidth();
    // 选项卡宽度
    var tabs_width = tabsSize.outerWidth();
    // 溢出宽度
    var over_width = Math.abs(box_width - tabs_width);
    // 剩余距离
    var lave = over_width + rightArrow.outerWidth() * 2 + 5;
    // 下次移动后距离
    var next_left = tabsSize.position().left - distance;
    if (next_left < -lave) {
      tabsSize.animate({
        'left': '-' + lave
      });
    } else {
      tabsSize.animate({
        'left': next_left
      });
    }
  });

  return flush_tabs;
};

window.flush_tabs = overflow_control();
$(window).resize(window.flush_tabs);

// 创建默认首页
window.tabsManage.Create('html/account/index.html', '首页');
// 修复移动设备设置最大宽度问题
if (window.screen.availWidth < 1020) {
  $('.header').css({
    'min-width': 'initial'
  });
}