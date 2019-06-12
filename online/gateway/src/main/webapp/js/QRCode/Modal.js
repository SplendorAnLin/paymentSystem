/// <reference path="D:\资料\XueYou\DefinitelyTyped\jquery\jquery.d.ts" />
(function() {
  // 对话框HTML模板
  var HTML = '\
  <div>\
    <div class="xy-modal">\
      <div class="modal-dialog">\
      </div>\
    </div>\
  </div>\
  ';

  // 全局对话框集合
  window.modals = [];
  // 移出全局对话框集合
  var removeModal = function(modal) {
    var index = -1;
    for (var i = 0; i < window.modals.length; ++i) {
      if (modal === window.modals[i]) {
        index = i;
      }
    }
    if (index !== -1) {
      window.modals.splice(index, 1);
    }
  };
  // 监听尺寸变化
  var listenResize = function(element, fuc) {
    if (!$.isFunction(fuc)) {
      return;
    }
    // 上一次高度宽度
    var width = element.outerWidth();
    var height = element.outerHeight();
    var timehandle = setInterval(function() {
      if (element.outerWidth() !== width || element.outerHeight() !== height) {
        fuc(timehandle);
        width = element.outerWidth();
        height = element.outerHeight();
      }
    }, 30);
    return timehandle;
  };

  var Modal = function(options) {
    // 默认参数
    var option = {
      // 显示目标, 一个jQuery对象或者一个html网址
      target: null,
      // 1=jQuery对象, 2=框架
      type: 1,
      // 按钮集合或者jQuery对象
      btns: null,
      // 标题, 一个jQuery对象或者一个字符串
      title: null,
      // 是否用框架内部标题
      useIfrmaeTitle: true,
      // 是否显示遮罩
      mask: true,
      // 背景遮罩是否可关闭对话框
      maskClose: false,
      // 是否高度自适应(会开启计时器消耗很多性能)
      autoHeight: false,
      // 事件-打开对话框
      onOpen: null,
      // 事件-关闭对话框
      onClose: null
    };

    this.options = $.extend(option, options);
    this.disabled = false;
    this.timeHandler = null;

    var count = window.modals.length - 1;
    if (count >= 0 && option.target === window.modals[count].options.target) {
      // console.log('window.modals', window.modals);
      // console.log('option.target', option.target);
      // console.log('window.modals[count].options.target', window.modals[count].options.target);
      return;
    }
    // 创建页面元素
    this.create();
  };

  // 创建页面元素
  Modal.prototype.create = function() {
    var self = this;
    var options = self.options;
    window.modals.push(self);
    var defaultClose = function() {
      self.close();
    };
    // 创建对话框元素
    self.modal = $(HTML).css('z-index', window.modals.length + 1);;
    self.dialog = $('.modal-dialog', self.modal);
    self.background = $('.xy-modal', self.modal).css('z-index', window.modals.length + 1);

    if (options.mask) {
      this.mask = $('<div class="modal-full modal-mask"></div>').css('z-index', window.modals.length);
      this.modal.append(this.mask);
      self.mask.addClass('modal-active');
    }

    if (options.maskClose) {
      self.background.click(function(event) {
        if (event.target === self.background[0]) {
          self.close();
        }
      });
    }

    if (options.title || (options.useIfrmaeTitle && options.type === 2)) {
      // 创建页眉元素
      this.header = $('<div class="modal-header"></div>');
      if (window.isJquery(options.title)) {
        this.header.append(options.title);
      } else {
        var defaultTitle = $('<span style="display: inline-block;padding: 10px;"><span class="text">' + (options.title || '') + '</span><a class="modal-close" href="javascript:void(0);">×</a></span>');
        var closeBtn = $('.modal-close', defaultTitle);
        closeBtn.click(defaultClose);
        this.header.append(defaultTitle);
      }
      this.dialog.append(this.header);
    }

    // 创建内容元素
    self.body = $('<div class="modal-body"></div>');
    if (options.type === 1) {
      // 获取原始样式
      self.originStyle = options.target.attr('style');
      // 获取target原来所在对象, 销毁对话框后还原给他
      self.origin = options.target.parent();
      self.content = options.target;
      // 内容是jquery对象
      self.body.append(options.target.css('display', 'block'));
    } else if (options.type === 2) {
      // 内容是一个框架地址
      self.iframe = $('<iframe frameborder="0" scrolling="auto"></iframe>');
      self.body.append(this.iframe);
      // 监听框架加载完毕事件
    }
    self.dialog.append(self.body);

    if (options.btns) {
      // 创建页脚
      this.footer = $('<div class="modal-footer"></div>');
      if (window.isJquery(options.btns)) {
        this.footer.append(options.btns);
      } else {
        // 根据按钮集合, 生成页脚按钮
        this.footer.append(this.createBtns(options.btns));
      }
      this.dialog.append(this.footer);
    }

    $(document.body).append(self.modal);
    
    if (options.type === 1) {
      self.show();
    } else if (options.type === 2) {
      self.modal.hide();
      self.loadIframe();
    }
  };
  // 创建页脚按钮
  Modal.prototype.createBtns = function(btns) {
    var self = this;
    var type = self.options.btnType;
    var div = $('<div class="btn-footer"></div>');
    if (type === 2) {
      div.removeClass('btn-footer').addClass('Alert-footer');
    }
    if (!btns.length) {
      return div;
    }
    // 默认按钮事件(关闭对话框)
    var defaultClose = function() {
      self.close();
    };
    var average = 100 / btns.length;
    $.each(btns, function() {
      var btn = this;
      // 检测按钮对象是否合法
      if (!btn.lable) {
        return;
      }
      // 创建按钮
      var button = $('<a class="mr-10 btn-1 modal-btn" href="javascript:void(0);">' + btn.lable + '</a>');
      if (type === 2) {
        button = $('<a href="javascript:void(0);" style="width: ' + average + '%;" class="text btn-2 modal-btn">' + btn.lable + '</a>');
      }
      // 监听事件
      if ($.isFunction(btn.click)) {
        button.click(function() {
          if (self.disabled || button.attr('disabled') === 'disabled') {
            return;
          }
          btn.click(btn.data, self.content);
        });
      } else {
        // 触发按钮 或 默认关闭
        button.click(btn.trigger ? function() {
          if (self.disabled || button.attr('disabled') === 'disabled') {
            return;
          }
          $(btn.trigger, self.content).trigger('click', self);
        } : defaultClose);
      }
      div.append(button);
    });
    return div;
  };
  // 加载框架
  Modal.prototype.loadIframe = function() {
    var self = this;
    var iframe = self.iframe;
    var options = self.options;
    var header = self.header;

    // 监听框架加载完毕
    iframe.load(function() {
      // head 中没有元素则表示加载失败
      var count = iframe.contents().find('head').children().length;
      if (count === 0) {
        console.warn(options.target, '加载失败!');
        self.close();
        return;
      }
      self.content = iframe.contents().find('body');
      // self.html = iframe.contents().find('html');
      // self.html.removeAttr('style');
      // center 让元素居中, 但是框架内部的select就获取不到正确的位置了
      // iframe.contents().find('html').css('text-align', 'left');
      if (options.useIfrmaeTitle && options.type === 2) {
        // 使用框架内部标题
        $('.text', header).text(iframe[0].contentWindow.document.title);
      }
      self.content.css({
        'display': 'inline-block',
        'vertical-align': 'top'
      });
      // self.content.css('overflow-x', 'hidden');
      // self.resize();
      iframe[0].contentWindow.modal = self;
      self.show();
    });
    iframe.error(function() {
      console.warn(options.target, '加载失败!');
      self.close();
    });
    // 设置框架地址
    iframe.attr('src', options.target);
  };
  // 显示对话框(加载完毕)
  Modal.prototype.show = function() {
    var self = this;
    var content = self.content;
    var options = self.options;
    var iframe = self.iframe;
    content.css('position', 'relative');
    self.modal.show(0);
    self.background.addClass('modal-active');
    if ($.isFunction(Modal.open)) {
      Modal.open(self, self.content);
    }
    if ($.isFunction(options.onOpen)) {
      options.onOpen(self, self.content, window);
    } 
    self.resize();
    self.listenResize();
    self.bind();
  };
  // 监听尺寸变化
  Modal.prototype.listenResize = function() {
    var self = this;
    var options = self.options;
    var content = self.content;
    if (!options.autoHeight) {
      return;
    }
    self.timehandle = listenResize(self.content, function() {
      self.resize();
    });
  };
  // 绑定事件
  Modal.prototype.bind = function() {
    var self = this;
    // $(window).bind('resize', self, self.handler_resize);
    $('.close', self.content).click(function() {
      self.close();
    });
  };
  // 事件回调
  Modal.prototype.handler_resize = function(event) {
    var self = event.data;
    self.resize();
  };
  // 关闭对话框
  Modal.prototype.close = function(fn) {
    var self = this;
    if (self.disabled) {
      return;
    }
    var options = self.options;
    if (self.mask) {
      self.mask.removeClass('modal-active');
    }
    self.background.addClass('modal-out').removeClass('modal-active');
    // 销毁监听尺寸变化计时器
    clearInterval(self.timeHandler);
    // 从全局对话框数组中删除
    removeModal(self);
    // 移除事件
    // $(window).unbind('resize', self.handler_resize);
    setTimeout(function() {
      self.loading(false);
      if (options.type === 1) {
        options.target.attr('style', self.originStyle);
        // 还原内容dom
        if (self.origin) {
          self.origin.append(self.content);
        }
      }
      if ($.isFunction(Modal.close)) {
        Modal.close(self);
      }
      if ($.isFunction(self.options.onClose)) {
        self.options.onClose(self, self.content);
      }
      if ($.isFunction(fn)) {
        fn();
      }
      self.modal.remove();
    }, 100);
  };
  // 设置高度
  Modal.prototype.resize = function() {
    var self = this;
    var type = self.options.type;
    var dialog = self.dialog;
    var body = self.body;
    var iframe = self.iframe;
    var content = self.content;
    var headerHeight = self.header ? self.header.outerHeight() : 0;
    var footerHeight = self.footer ? self.footer.outerHeight() : 0;
    var contentHeight = content.outerHeight() + 1;
    var maxHeight = $(window).outerHeight();
    var contentWidth = content.outerWidth();
    // contentWidth == 0 ? 'auto' : contentWidth + 20
    // 页眉加页脚高度
    var otherHeight = 0;
    // 累加页眉高度
    otherHeight += headerHeight;
    // 累加页脚高度
    otherHeight += footerHeight;
    // 页面剩余高度
    var overHeight = maxHeight - otherHeight;
    // 内容最大显示高度
    var maxShowHeight = overHeight - 50;
    // 处理滚动条宽度
    var scrollWidth = 0;
    if (type === 1) {
      if ( (contentHeight) >= maxShowHeight) {
        body.css({
          height: maxShowHeight,
        });
      } else {
        body.css({
          height: contentHeight,
        });
      }
    } else if (type === 2) {
      
      // 最大高度 or 当前高度
      var aheight = contentHeight > maxShowHeight ? maxShowHeight : contentHeight;
      var _width = dialog.width();
      var _height = body.height();

      // 溢出了则加上滚动条高度
      if (_width < contentWidth && (_width  > 10)) {
        aheight = aheight + 20;
      }
      if (_height < contentHeight  && (_height > 10) ) {
        contentWidth = contentWidth + 20;
      }
      iframe.css({
        'width': contentWidth,
        'height': aheight + 10
      });
    }
    var load = $('.modal-loding', dialog);
    if (load) {
      load.css({
        top: headerHeight,
        width: dialog.outerWidth(),
        height: type === 1 ? content.outerHeight() : aheight + 10
      });
    }
  };
  // 禁用对话框按钮
  Modal.prototype.disable = function(yes) {
    var self = this;
    var options = self.options;
    self.disabled = yes === undefined ? true : yes;
    if (self.disabled) {
      // 找到页脚按钮并禁用
      $('.modal-btn', self.footer).attr('disabled', 'disabled').addClass('disabled');
    } else {
      // 找到页脚按钮并禁用
      $('.modal-btn', self.footer).removeAttr('disabled').removeClass('disabled');
    }
  };
  // 对话框显示加载等待动画
  Modal.prototype.loading = function(yes) {
    var self = this;
    var body = self.body;
    var header = self.header;
    var dialog = self.dialog;
    var options = self.options;
    // 创建等待元素
    var loadElement = $('<div class="modal-loding dialog-full-size"><div class="loadImg"></div></div>');
    if (yes) {
      loadElement.css({
        top: header ? header.outerHeight() : 0,
        width: dialog.outerWidth(),
        height: body.outerHeight(),
      });
      dialog.append(loadElement);
      self.disable(true);
    } else {
      $('.modal-loding', dialog).remove();
      self.disable(false);
    }
  };

  // 全局选项
  Modal.open = null;
  Modal.close = null;


  // 封装 Alert
  var Alert = function(element, title, onCancel) {
    // 默认参数
    var option = {
      // 显示目标, 一个jQuery对象
      target: utils.isJquery(element) ?
      element : $('<div><p>' + element + '</p></div>'),
      // 1=jQuery对象
      type: 1,
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
      // 背景遮罩是否可关闭对话框
      maskClose: false,
      // 事件-打开对话框
      onOpen: null,
      // 事件-关闭对话框
      onClose: onCancel || null,
    };

    option.target.css('padding', '30px 50px');
    var modal = new Modal(option);

    return modal;
  };

  // 封装 Confirm
  var Confirm = function(element, onConfirm, onCancel, title) {
    // 默认参数
    var option = {
      // 显示目标, 一个jQuery对象
      target: window.isJquery(element) ?
      element : $('<div><p>' + element + '</p></div>'),
      // 1=jQuery对象
      type: 1,
      // 按钮样式2
      btnType: 2,
      // 按钮集合或者jQuery对象
      btns: [
        {
          lable: '取消',
          click: function() {
            modal.close();
            if ($.isFunction(onCancel)) {
              onCancel(modal);
            }
          },
        },
        {
          lable: '确定',
          click: function() {
            modal.close();
            if ($.isFunction(onConfirm)) {
              onConfirm(modal);
            }
          },
        }
      ],
      // 标题
      title: title,
      // 是否显示遮罩
      mask: true,
      // 背景遮罩是否可关闭对话框
      maskClose: false,
      // 事件-打开对话框
      onOpen: null,
      // 事件-关闭对话框
      onClose: null,
    };
    option.target.css('padding', '30px 50px');
    var modal = new Modal(option);
    return modal;
  };

  // 封装 html 属性
  window.InitModal = function(doc) {
    $('[data-modal]', doc).click(function() {
      var element = $(this);
      var option = JSON.parse(element.attr('data-modal'));
      if (option.type === 1) {
        option.target = $(option.target);
      }
      var modal = new Modal(option);
    });
  };


  $.fn.Modal = function(options) {
    if (!this.length) {
      return;
    }
    return new Modal($.extend({}, options, {target: this, type: 1}));
  };
  $.fn.Alert = function(title, onCancel) {
    if (!this.length) {
      return;
    }
    return new Alert(this, title, onCancel);
  };
  $.fn.Confirm = function(onConfirm, onCancel, title) {
    if (!this.length) {
      return;
    }
    return new Confirm(this, onConfirm, onCancel, title);
  };
  window.Modal = Modal;
  window.Alert = Alert;
  window.Confirm = Confirm;


  // 监听 resize 事件
  $(window).bind('resize', function() {
    $.each(window.modals, function() {
      var modal = this;
      modal.resize();
    });
  });







})();
