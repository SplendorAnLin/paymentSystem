/**
 * 事件枚举
 */
(function() {


  var SysEvent = {
    // 窗口管理器事件
    WindowManage: {
      // 切换 [index, noFlush]
      Switch: 'switch',
      // 创建加载完毕事件 [index, title]
      onLoad: 'onLoad',
      // 创建窗口 [title, notload]
      Create: 'Create',

    },
    // 选项卡管理器事件
    TbasManage: {
      // 创建 
      Create:'created',
      // 选择 [index, noFlush]
      Selected:'selected',
      // 删除
      Deleted: 'deleted'
    },
    // 菜单栏事件
    MenuBar: {
      // 菜单选中, [url, title]
      MenuSelected: 'menu-selected'
    }
  };

  window.SysEvent = SysEvent;
})();


/**
 * 菜单栏
 * 选中菜单后, 会发送事件到window对象
 */
(function() {

  // 导出
  window.MenuBar = {
    MenuSelected: 'menu-selected'
  };

  // 菜单功能
  var initNavigation = function(navigation) {
    // 文件夹激活样式
    var className_foler = 'open';
    // 菜单项激活样式
    var className_item = 'selected';
    // 导航栏元素 navigation
    // 当前选中菜单项
    var currentItem = null;
    // 当前展开文件夹
    var currentFloder = null;

    // 寻找菜单
    var findMenuLi = function(element) {
      return element.parents('li');
    };

    // 文件夹展开折叠事件
    var handler_floderChange = function() {
      var foldElement = $(this);
      // 点击自身切换
      if (currentFloder && currentFloder.get(0) === this) {
        findMenuLi(currentFloder).toggleClass(className_foler);
        currentFloder = null;
        return;
      } else if (currentFloder) {
        // 关闭上一个文件夹
        findMenuLi(currentFloder).removeClass(className_foler);
      }
      currentFloder = foldElement;
      findMenuLi(currentFloder).addClass(className_foler);
    };

    // 菜单项选中事件
    var handler_selectItem = function(event) {
      var itemElement = $(this);
      var foldElement = $('.folder', findMenuLi(itemElement));
      if (currentItem) {
        currentItem.removeClass(className_item);
        currentItem = null;
        // 关闭上一个文件夹
        if (currentFloder && currentFloder.get(0) !== foldElement.get(0)) {
          findMenuLi(currentFloder).removeClass(className_foler);
          currentFloder = false;
        }
        // 设置为当前文件夹
        if (foldElement.length !== 0) {
          currentFloder = foldElement;
        }
      }
      currentItem = itemElement;
      currentItem.addClass(className_item);
      findMenuLi(currentItem).addClass(className_foler);
      $(window).trigger(SysEvent.MenuBar.MenuSelected, [currentItem.attr('href'), currentItem.text()]);
      // if (window.windowManage) {
      //   window.windowManage.create(currentItem.attr('href'), currentItem.text());
      // }
      event.preventDefault();
    };

    // 绑定事件
    $('.folder', navigation).click(handler_floderChange);
    $('a', navigation).click(handler_selectItem);
  };


  // 导航栏
  var navigation = $('#navigation');
  // 抽屉导航
  var drawerNav = $('#drawer-nav');


  // 初始化普通菜单
  initNavigation(navigation);
  
  if (!drawerNav.length)
    return;
  // 初始化抽屉菜单
  initNavigation(drawerNav);
  // 创建抽屉实例
  var drawer = drawerNav.Drawer();
  $('.toggle-drawerNav').click(function() {
    drawer.toggle();
  });


})();


/**
 * 选项卡管理器
 * 选中, 创建等操作会发送事件到window对象
 */
(function() {

  // 选项卡外层
  var tabOut = $('.tabs-out');
  // tab选项卡总可用
  var available = $('.tabs-in');
  // tab选项卡容器
  var container = $('#tabs-container');
  // 左箭头
  var arrow_left = $('.tabs-arrow.left');
  // 右箭头
  var arrow_right = $('.tabs-arrow.right');

  // tab管理器
  var TbasManage = function(tabsElement) {
    // 当前选中索引
    this.index = -1;
    // 选项卡集合
    this.tabs = [];
    // 选项卡容器
    this.tabsElement = tabsElement;
    // 绑定事件
    this.bind();
  };
  // 创建
  TbasManage.prototype.create = function(title, notload) {
    var template = '<li>\
        <a class="inner noselect" href="javascript:void(0);">\
          <span title="{{title}}" class="title">{{title}}</span>\
          <span class="close"><i class="fa fa-times"></i></span>\
        </a>\
      </li>';

    var html = $(utils.tpl(template, {
        'title': title,
    }));
    var tab = {
      'liElement': html,
      'titleElement': $('.title', html)
    };
    html.data('index', this.tabs.length);
    if (this.tabs.length == 0) {
      html.addClass('first');
    }

    $(window).trigger(SysEvent.TbasManage.Create);
    this.tabsElement.append(html);
    this.tabs.push(tab);
    this.checkOverflow();
    this.selecte(this.tabs.length - 1, true, notload);
  };
  // 删除
  TbasManage.prototype.deleted = function(index) {
    var tabs = this.tabs;
    if (index < 1 || index >= tabs.length) {
      return;
    }
    var nextIndex = this.index;
    if (index < this.index) {
      nextIndex = this.index - 1;
    }
    if (index === this.index && index === tabs.length - 1) {
      nextIndex = index - 1;
    }

    // 窗口管理器对应删除
    // todo window.windowManage.deleted(index);
    $(window).trigger(SysEvent.TbasManage.Deleted, index);
    tabs[index].liElement.remove();
    this.index = -1;
    tabs.splice(index, 1);
    // 重建索引
    this.buildIndex();
    this.checkOverflow();
    this.selecte(nextIndex, true);
  };
  // 选中
  TbasManage.prototype.selecte = function(index, noFlush, notload, noMove) {
    var tabs = this.tabs;
    if (index < 0 || index >= tabs.length) {
      return;
    }
    // 移除上一个选中
    if (this.index !== -1 && this.index !== undefined) {
      tabs[this.index].liElement.removeClass('selected');
    }

    if (!notload) {
      $(window).trigger(SysEvent.TbasManage.Selected, [index, noFlush]);
      // todo window.windowManage.active(index, noFlush);
    }
    tabs[index].liElement.addClass('selected');
    this.index = index;
    this.arrowSwitch(this.index, false, noMove);
  };
  // 设置标题
  TbasManage.prototype.title = function(index, title) {
    var tabs = this.tabs;
    if (index < 0 || index >= tabs.length) {
      return;
    }
    tabs[index].titleElement.text(title);
  };
  // 绑定事件
  TbasManage.prototype.bind = function() {
    var self = this;

    $(this.tabsElement).on('click', 'li', function() {
      var li = $(this);
      var index = li.data('index');
      self.selecte(index, false, false, true);
    });

    $(this.tabsElement).on('click', '.close', function() {
      var li = $(this).closest('li');
      var index = li.data('index');
      self.deleted(index);
      event.stopPropagation();
      return false;
    });


    $(window).resize(function() {
      self.checkOverflow();
    });
    arrow_left.click(function() {
      self.arrowSwitch(self.index - 1, true);
    });
    arrow_right.click(function() {
      self.arrowSwitch(self.index + 1, true);
    });
  };
  // 重建索引
  TbasManage.prototype.buildIndex = function() {
    var tabs = this.tabs;
    $.each(tabs, function(i) {
      this.liElement.data('index', i);
      this.liElement.attr('data-index', i);
    });
  };
  // 检测溢出
  TbasManage.prototype.checkOverflow = function() {
    // 总宽度
    var maxWidth = available.width();
    // 当前宽度
    var width = container.width();
    if (width > maxWidth) {
      // 显示切换箭头
      tabOut.addClass('show-arrow');
    } else {
      // 隐藏切换箭头
      tabOut.removeClass('show-arrow');
    }

  };
  // 左右箭头切换
  TbasManage.prototype.arrowSwitch = function(i, isSwitch, noMove) {
    var self = this;
    if (i >= self.tabs.length || i < 0) {
      return;
    }

    if (!noMove) {
      var maxWidth = available.width();
      var width = container.width();
      var left = self.tabs[i].liElement.position().left;
      if ((width - left) < maxWidth) {
        if (maxWidth > width) {
          container.css('left', 0);
          return;
        }
        // 不够切换
        container.css('left', -(width - maxWidth));
      } else {
        // 正常切换
        container.css('left', -left);
      }
    }

    if (isSwitch) {
      self.selecte(i);
    }
  };

  // 创建并导出
  window.tabs = new TbasManage(container);


  // 挂接事件
  $(window).bind(SysEvent.WindowManage.Switch, function(event, index, noFlush) { window.tabs.selecte(index, noFlush); })
  $(window).bind(SysEvent.WindowManage.onLoad, function(event, index, title) {  window.tabs.title(index, title); })
  $(window).bind(SysEvent.WindowManage.Create, function(event, title, notload) { window.tabs.create(title, notload); })



})();


/**
 * 窗口管理器
 * 创建, 选中, 删除等操作会发送事件到window对象
 */
(function() {

  // 窗口管理器
  var WindowManage = function(container, loadElement) {
    // 当前显示窗口索引
    this.index = -1;
    // 窗口集合
    this.windows = [];
    // 框架容器
    this.container = container;
    // 加载元素
    this.loadElement = loadElement;
  };
  // 创建
  WindowManage.prototype.create = function(url, title, notload) {
    if (!url) {
      console.warn('WindowManage: 创建窗口失败, 没有url地址!');
      return;
    }

    var self = this;
    var windows = this.windows;
    var container = this.container;
    var win;

    if (this.checkUrl(url)) {
      var i = this.getIndexForUrl(url);
      // 切换过去
      self.active(i, true);
      // todo window.tabs.selecte(i, true);
      $(window).trigger(SysEvent.WindowManage.Switch, [i, true]);
      return this;
    } else {
      if (!notload) {
        // 等待加载
        self.loading();
      }
      // 创建窗口实例
      win = new Windows(url, title, this);
      win.create(function(_title) {
        $(window).trigger(SysEvent.WindowManage.onLoad, [self.getIndex(win), _title]);
        if (self.index === self.getIndex(win))
          win.show();
      }, notload);
      container.append(win.box);
      windows.push(win);
      $(window).trigger(SysEvent.WindowManage.Create, [win.title, notload]);
    }
  };
  // 删除
  WindowManage.prototype.deleted = function(index) {
    var windows = this.windows;
    if (index < 1 || index >= windows.length) {
      return;
    }
    var nextIndex = this.index;
    if (index < this.index) {
      nextIndex = this.index - 1;
    }
    if (index === this.index && index === windows.length - 1) {
      nextIndex = index - 1;
    }
    windows[index].deleted();
    this.index = -1;
    windows.splice(index, 1);
    this.active(nextIndex);
  };
  // 激活
  WindowManage.prototype.active = function(index, noFlush) {
    var windows = this.windows;
    var self = this;
    if (index < 0 || index >= windows.length) {
      return;
    }
    if ( (index === this.index) && !noFlush) {
      // 刷新当前窗口
      windows[index].flush();
      return;
    }

    // 移除上一个选中
    if (this.index !== -1 && this.index !== undefined) {
      windows[this.index].hide();
    }


    this.index = index;
    // 如果当前窗口没有被加载, 则加载, 否则仅显示
    // 如果当前窗口正在加载, 则等待其加载
    if (windows[index].isloading) {
      return;
    }

    if (windows[index].status === 'error') {
      $(window).trigger(SysEvent.WindowManage.onLoad, [index, '加载失败']);
      windows[index].show();
    } else if (windows[index].status) {
      windows[index].show();
    } else {
      windows[index].show();
      windows[index].load(windows[index].url, function(title) {
        if (self.index !== -1 && self.index !== undefined) {
          windows[self.index].hide();
        }
        self.index = index;
        $(window).trigger(SysEvent.WindowManage.onLoad, [self.getIndex(windows[index]), title]);
        // todo window.tabs.title(self.getIndex(windows[index]), windows[index].title);
        windows[index].show();
      });
    }

    
    this.index = index;
  };
  // 等待加载/结束等待
  WindowManage.prototype.loading = function(stop) {
    if (!this.loadElement) {
      return;
    }
    if (stop) {
      this.loadElement.fadeOut(0);
    } else {
      this.loadElement.fadeIn(0);
    }
  };
  // 获取索引
  WindowManage.prototype.getIndex = function(win) {
    var self = this;
    var index = -1;
    $.each(self.windows, function(i) {
      if (this === win) {
        index = i;
      }
    });
    return index;
  };
  // 获取索引
  WindowManage.prototype.getIndexForUrl = function(url) {
    var self = this;
    var index = -1;
    $.each(self.windows, function(i) {
      if (this.iframe.attr('src') == url) {
        index = i;
      }
    });
    return index;
  };
  // 检测是否存在url true=存在
  WindowManage.prototype.checkUrl = function(url) {
    var windows = this.windows;
    var exist = false;
    $.each(windows, function() {
      if (this.url === url) {
        exist = true;
      }
    });
    return exist;
  };
  // 获取当前窗口实例
  WindowManage.prototype.current = function() {
    return this.windows[this.index];
  };
  // 导出窗口
  WindowManage.prototype.exprot = function() {
    var result = [];
    $(this.windows).each(function(i) {
      var win = this;
      // 忽略首页
      if (i == 0)
        return;

      result.push({
        'title': win.title,
        'url': win.url
      });
    });

    return JSON.stringify({
      'tabs': result,
      'currentIndex': this.index
    });
  };
  // 导入窗口
  WindowManage.prototype.improt = function(windata) {
    if (!windata || windata == '')
      return;
      windata = JSON.parse(windata);

    $(windata.tabs).each(function() {
      windowManage.create(this.url, this.title, true);
    });
    
    windowManage.active(windata.currentIndex);
  };

  // 窗口
  var Windows = function(url, title, windowManage) {
    // 窗口管理实例
    this.windowManage = windowManage;
    // 标题
    this.title = title;
    // 容器元素
    this.box = null;
    // 框架元素
    this.iframe = null;
    // 地址
    this.url = url;
    // 是否加载完毕
    this.status = false;
    // 是否正在加载
    this.isloading = false;
  };
  // 创建窗口
  Windows.prototype.create = function(fuc, notload) {
    if (!this.url) {
      console.warn('Window: 创建窗口失败, 没有url地址!');
      return;
    }

    var self = this;
    var windowManage = self.windowManage;
    self.box = $('<div class="iframe-box"></div>');
    self.iframe = $('<iframe frameborder="0"></iframe>');
    self.box.append(self.iframe);

    if (!notload) {
      self.load(self.url, fuc);
    }

  };
  // 加载iframe
  Windows.prototype.load = function(src, fuc) {
    var self = this;
    var iframe = self.iframe;
    if (!iframe)
      return;

    self.status = false;
    self.isloading = true;
    utils.loadIframe(iframe, src, function(status) {
      // 增加加载错误状态
      self.status = status ? status : 'error';
      self.isloading = false;
      // 结束转圈
      windowManage.loading(true);
      if (!status) {
        fuc('加载失败');
        return;
      }
      // 如果显示提供标题，则设置标题
      var title = self.iframe[0].contentWindow.document.title;
      utils.iframeFind(iframe, 'body').addClass('sys-window');
      if (title === '' && self.title !== '') {
        self.iframe[0].contentWindow.document.title = self.title;
      } else {
        self.title = title;
      }
      // 通知tab选项卡更新标题
      fuc(self.title);
    });


  };
  // 删除窗口
  Windows.prototype.deleted = function() {
    if (!this.box) {
      return;
    }
    this.box.remove();
  };
  // 显示窗口
  Windows.prototype.show = function() {
    if (!this.box) {
      return;
    }
    // 如果正在加载, 显示加载图片
    windowManage.loading(!this.isloading && this.status);
    this.box.addClass('active');
  };
  // 隐藏窗口
  Windows.prototype.hide = function() {
    if (!this.box) {
      return;
    }
    this.box.removeClass('active');
  };
  // 刷新窗口
  Windows.prototype.flush = function() {
    if (!this.iframe) {
      return;
    }
    this.status = false;
    this.isloading = true;
    windowManage.loading();
    this.iframe.attr('src', this.url);
  };
  // 跳转地址
  Windows.prototype.go = function(url, title) {
    if (!this.iframe || !url || url === '') {
      return;
    }
    windowManage.loading(false);
    this.title = title;
    this.iframe.attr('src', url);
  };

  // 创建窗口管理器并导出
  window.windowManage =  new WindowManage($('#iframes'), $('#loading-img'));
  window.WindowManage = WindowManage;
  // 窗口管理器事件===================
  // 窗口管理事件-切换窗口
  WindowManage.Switch = 'switch';

  // 处理事件
  $(window).bind(SysEvent.TbasManage.Deleted, function(event, index) { window.windowManage.deleted(index); });
  $(window).bind(SysEvent.TbasManage.Selected, function(event, index, noFlush) { window.windowManage.active(index, noFlush); });
  $(window).bind(SysEvent.MenuBar.MenuSelected, function(event, url, text) { window.windowManage.create(url, text); });

})();


/**
 * 页眉下拉菜单
 */
(function() {

  var panel = $('.control-panel');

  $('.user-name').click(function(event) {
    panel.removeClass('active');
    panel.toggleClass('active');
  });

  $('a', $('.menus')).click(function() {
    panel.removeClass('active');
  });


  panel.mouseenter(function(event) {
    panel.addClass('active');
    event.stopPropagation();
    return false;
  });

  panel.mouseleave(function(event) {
    panel.removeClass('active');
  });

})();