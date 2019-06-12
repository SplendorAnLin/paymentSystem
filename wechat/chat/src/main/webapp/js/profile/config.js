/* 组件配置 */
/**
 * WdatePicker 日期组件
 */
(function() {

  // todo 通过组件设置了输入框日期后,要主动触发输入框的change事件 

  /**
   * 日期选择框 [官网](http://www.my97.net/dp/index.asp)
   * 需要显示时分秒则加上 `date-hms` 样式类
   * 自定义格式则设置属性 `date-fmt="HH:mm"`
   */
  window.SetInputDate = function(dateInput) {
    if (!dateInput || dateInput.length === 0) {
      return;
    }
    var dateFmt = dateInput.attr('date-fmt');
    var fmt = dateInput.hasClass('date-hms') ? 'yyyy-MM-dd HH:mm:ss' : 'yyyy-MM-dd';
    if (dateFmt) {
      fmt = dateFmt;
    }
    dateInput.attr('onClick', "WdatePicker({ 'errDealMode': 1, 'skin': 'twoer', 'dateFmt': '" + fmt + "' })");
  };

  // 注册组件到框架渲染
  window.compoment.register(function(parent) {
 
    var timeInputs;

    // 基本日期输入框
    timeInputs =  $('.input-date,.date-hms', parent).each(function() {
      window.SetInputDate($(this));
    });

    // 范围如期输入框
    timeInputs = timeInputs.add($('.date-start').each(function() {
      var dateFmt = $(this).attr('date-fmt');
      var parentItem = $(this).attr('date-parent');
      var fmt = dateFmt || 'yyyy-MM-dd HH:mm:ss';
      var inputStart = $(this);

      // 寻找结束日期
      var inputEnd = $('.date-end', parentItem ? $(parentItem) : inputStart.closest('.item'));

      inputStart.click(function() {
        // 结束日期
        var dateEnd = inputEnd.val();
        this.initcfg = {
          el: this,
          errDealMode: 1,
          skin: 'twoer',
          startDate: dateFmt ? '00:00' : '%y-%M-%d 00:00:00',
          maxDate: dateEnd !== '' ? dateEnd : '',
          dateFmt: fmt
        }
        window.WdatePicker(this.initcfg);
      });
    }));

    timeInputs = timeInputs.add($('.date-end').each(function() {
      var dateFmt = $(this).attr('date-fmt');
      var parentItem = $(this).attr('date-parent');
      var fmt = dateFmt || 'yyyy-MM-dd HH:mm:ss';
      var inputEnd = $(this);

      // 寻找结束日期
      var inputStart = $('.date-start', parentItem ? $(parentItem) : inputEnd.closest('.item'));

      inputEnd.click(function() {
        // 结束日期
        var dateStart = inputStart.val();
        this.initcfg = {
          el: this,
          errDealMode: 1,
          skin: 'twoer',
          startDate: dateFmt ? '23:59' : '%y-%M-%d 23:59:59',
          minDate: dateStart !== '' ? dateStart : '%y-%M-%d 23:59:59',
          dateFmt: fmt
        };
        WdatePicker(this.initcfg);
      });
    }));

    // 初始化默认时间
    // 默认时间-开始
    $('.default-time').val(utils.formatTime(new Date(), 'yyyy-MM-dd 00:00:00'));
    // 默认时间-结束
    $('.default-time-end').val(utils.formatTime(new Date(), 'yyyy-MM-dd 23:59:59'));

    // change事件冒泡到表单, 来触发表单验证
    timeInputs.each(function() {
      this.onchange = function() {
        $(this).parents('form').trigger('change');
      }
    });

  });

})();

/**
 * Message 消息组件
 */
(function() {

  window.Messages = {};
  window.Messages.info = function(msg) {
    return window.top.Messagesed.info(msg);
  };
  window.Messages.success = function(msg) {
    return window.top.Messagesed.success(msg);
  };
  window.Messages.warn = function(msg) {
    return window.top.Messagesed.warn(msg);
  };
  window.Messages.error = function(msg) {
    return window.top.Messagesed.error(msg);
  };
  window.Messages.wait = function(msg) {
    return window.top.Messagesed.wait(msg);
  };

})();


/**
 * MyDialog 对话框组件
 */
(function() {

  // 配置顶层框架加载渲染对话框内组件
  if (window.top === window) {
    // 挂接对话框加载钩子
    MyDialog.open = function(dialog, content) {
      // 如果对话框类型为普通对话框, 则重新渲染组件
      if (typeof(dialog.options.target) != 'string') {
        // 重新渲染组件
        window.compoment.render(dialog.content[0]);
      }
    };
  }

  // 封装
  $.fn.Alert = function(title, onCancel, noPadding) {
    return new window.top.Alert(this, title, onCancel, noPadding);
  };
  $.fn.Confirm = function(title, onConfirm, onCancel) {
    return new window.top.Confirm(this, title, onConfirm, onCancel);
  };
  // ajax询问框, 点击确定时自动发送ajax
  window.AjaxConfirm = function(msg, title, ajaxConfig, reload) {
    var errorFn = ajaxConfig.error;
    var successFn = ajaxConfig.success;

    ajaxConfig.error = function() {
      errorFn();
      if (reload)
        window.RefresQueryResult();
    };
    ajaxConfig.success = function(msg) {
      successFn(msg);
      if (reload)
        window.RefresQueryResult();
    };

    var dialog = new window.top.Confirm(msg, title, function() {
      utils.ajax(ajaxConfig);
    }, null);
    return dialog;
  };

  // 查看密匙
  window.showKey = function(element, title) {
    var keyTemplate = $('<div style="max-width: 7.4rem;word-break: break-all;"><p><span class="key"></span><a href="javascript:void(0);" class="btn-copy ml-10">[复制]</a></p></div>');
    var key = $(element).attr('data-key');
    $('.key', keyTemplate).text(key);
    $('.btn-copy', keyTemplate).attr('data-clipboard-text', key);
    keyTemplate.Alert(title || '查看密匙');
  }


  // 图片对话框
  window.lookImg = function(path, title, element) {
    // 测试图片
    path = '/images/idCard.jpg';

    // path 可以是img图片路径 或者 一个已存在的img图片标签
    if ($(element).hasClass('look-loading-img'))
      return;
    if (typeof(path) === 'string') {
      $(element).addClass('look-loading-img');
      $(element).loading();
      utils.loadImage(path, function(img, status) {
        $(element).ending();
        $(element).removeClass('look-loading-img');
        if (!status) {
          Messages.error('图片加载失败, 请稍后重试...');
          return;
        }
        $(img).Alert(title || '查看图片');
      });
    } else {
      $($(path).clone()).Alert(title || '查看图片');
    }
    


  };

  // 根据对话框内元素, 寻找当前对话框
  window.queryDialog = function(ele) {
    // 对话框内容元素
    var flag = ele.parents('.dialog-content-flag');
    // 获取对话框实例
    var dialog = window.top.MyDialog.get(flag.attr('dialog-id'));
    return dialog;
  };



  // 注册组件到框架渲染
  window.compoment.register(function(parent) {

    var createMyIframe = function() {
      var btn = $(this);
      try {
        var option = JSON.parse(btn.attr('data-myiframe'));
      } catch (error) {
        console.error('创建对话框出错, data-myiframe 配置不是合法JSON格式!');
        return;
      }

      option.onClose = function() { btn.ending(); };
      option.onOpen = function() { btn.ending(); };
      new window.top.MyIframe(option);
      btn.loading(0, btn.attr('data-hideIco'));
    };

    $('[data-myiframe]', parent).unbind('click', createMyIframe).click(createMyIframe);
  });

})();



/**
 * SelectBox 下拉列表组件
 */
(function() {
  // 注册组件到框架渲染
  window.compoment.register(function(parent) {
    $('.input-select').SelectBox();
    // data-value 来设置默认值
    $('select[data-value]').each(function() {
      var val = $(this).attr('data-value');
      $(this).selectForValue(val);
    });
  });
})();


/**
 * Toggle 切换组件
 */
(function() {
  // 注册组件到框架渲染
  window.compoment.register(function(parent) {
    $('.checkbox', parent).CheckBox();
    $('.switch', parent).Switch();
    $('.switch-text', parent).SwitchText();
    $('.radio', parent).Radio();
  });
})();


/**
 * FormValidator 表单验证
 */
(function() {

  // 自定义验证规则===================================
  // 最大金额验证
  Compared.add('maxIgnoreEmpy', function(val, params, ele, ansyFn) {
      var max = '';
      var msg = '';
      if ($.isFunction(params)) {
        var result = param(val, ele);
        max = result.value;
        msg = result.message;
      } else {
        max = $(params).val();
        msg = '超过最大值 ' + max;
      }
      max = parseFloat(max);
      if (isNaN(max) || max == 0) {
        return Compared.toStatus(true);
      }
      return  Compared.toStatus(parseFloat(val) < max, msg);
  });
  // 最小金额验证
  Compared.add('minIgnoreEmpy', function(val, params, ele, ansyFn) {
      var min = '';
      var msg = '';
      if ($.isFunction(params)) {
        var result = param(val, ele);
        min = result.value;
        msg = result.message;
      } else {
        min = $(params).val();
        msg = '低于最小值 ' + min;
      }
      min = parseFloat(min);
      if (isNaN(min) || min == 0) {
        return Compared.toStatus(true);
      }
      return  Compared.toStatus(parseFloat(val) > min, msg);
  });

  // 挂接事件===================================
  // 挂接验证钩子
  FormValidator.SubmitHandler = function(status, form) {
    var dialog = window.queryDialog(form);
    if (dialog)
      dialog.loading(status);
  };
  // 获取表单验证实例
  window.getValidator = function(form) {
    if (!form || form.length <= 0)
      return undefined;
    return form.get(0).FormValidator;
  };

  // 注册组件到框架渲染
  window.compoment.register(function() {
    $('.validator').FormValidator();
  });

  // 添加提示方法===================================
  // 便条 -  在对话框顶部漂浮一个便条, 显示验证错误信息, 同时高亮验证错误元素
  var NoteMessage = function() {
    this.top = $(document.body).css('padding-top');
  };
  NoteMessage.prototype.show = function(ele, msg) {
    var note = $(ele).data('note-ele');
    // 未绑定便条则创建
    if (!note) {
      note = this.createNote(ele, msg);
    } else {
      $('.message', note).text(msg);
    }
    $(document.body).css('padding-top', note.outerHeight() + 10);
  };
  NoteMessage.prototype.wait = function(ele, msg) {
    this.show(ele, msg);
  };
  NoteMessage.prototype.hide = function(ele) {
    this.destroy(ele);
  };
  NoteMessage.prototype.createNote = function(ele, msg) {
    var self = this;
    var note = $('<div class="dialog-note"></div>');
    var message = $('<p class="message">' + msg + '</p>');
    var close = $('<span class="close"><i class="fa fa-times"></i></span>');
    note.append(message).append(close);
    // 点击关闭按钮销毁
    close.click(function() {
      self.destroy(ele);
    });
    $(document.body).data('top', $(document.body).css('padding-top'));
    ele.data('note-ele', note);
    // 插入dom
    $(document.body).append(note);
    return note;
  };
  NoteMessage.prototype.destroy = function(ele) {
    var note = $(ele).data('note-ele');
    if (note) {
      $(document.body).css('padding-top', this.top);
      note.remove();
      $(ele).data('note-ele', '');
    }
  };

  
  // 换行提示
  var NextLineMessage = function() {};
  // 显示通知消息
  NextLineMessage.prototype.show = function(ele, msg) {
    ele.inputEnding();
    var msgEle = $(ele).data('msg-element');
    if (!msgEle) {
      msgEle = $('<span class="next-error"></span>');
      $(ele).data('msg-element', msgEle);
    }
    var wrap = ele.parents('.input-wrap');
    var item = ele.parents('.item');
    msgEle.text(msg).show().css({
      display: 'inline-block',
      width: ele.width(),
      left: wrap.position().left,
      position: 'relative'
    });
    item.append(msgEle);
    this.findMaxHeight(item);
    ele.addClass('borderRed');
  };
  // 显示通知等待消息
  NextLineMessage.prototype.wait = function(ele, msg) {
    //this.show(ele, msg);
    ele.inputLoading();
  };
  // 隐藏通知消息
  NextLineMessage.prototype.hide = function(ele) {
    var msgEle = ele.data('msg-element');
    ele.inputEnding();
    if (msgEle) {
      var item = ele.parents('.item');
      msgEle.remove();
      ele.data('msg-element', '');
      this.findMaxHeight(item);
    }
    ele.removeClass('borderRed');
  };
  // 设置最高元素的定位方式
  NextLineMessage.prototype.findMaxHeight = function(item) {
    var maxHeight = 0;
    var maxEle = $();
    var eles = $('.next-error', item).css('position', 'relative');
    eles.each(function() {
      var errEle = $(this);
      var height = errEle.height();
      if (height > maxHeight) {
        maxHeight = height;
        maxEle = errEle;
      }
    });
    if (eles.length > 1)
      maxEle.css('position', 'absolute');
  };

  // Dropdown提示 - 默认只把编辑框标红, 编辑框有焦点时才显示Dropdown进行提示
  // todo 注意不是主动通过验证状态改变而显示隐藏, 而是绑定 元素的焦点事件和失去焦点事件, 来显示和隐藏
  var DropdownMessage = function() {
    this.create();
  };
  // 显示通知消息
  DropdownMessage.prototype.show = function(ele, msg) {
    var self = this;
    // 是否绑定事件
    var isBind = ele.data('isbind');
    if (isBind === undefined) {
      ele.focus(function() {
        self.text(ele, msg);
      });
      ele.blur(function() {
        self.hidePop(ele, msg);
      });
      ele.data('isbind', true);
    }

    // 记录元素最后一次验证文本
    ele.data('last-msg', msg);
    ele.attr('autocomplete', 'off');
    if (ele.is(':focus')) {
      this.text(ele, msg);
    }
  };
  // 显示通知等待消息
  DropdownMessage.prototype.wait = function(ele, msg) {
    if (ele.is(':focus')) {
      // this.text(ele, msg);
    }
  };
  // 隐藏通知消息
  DropdownMessage.prototype.hide = function(ele) {
    ele.data('last-msg', null);

    if (ele.is(':focus')) {
      this.hidePop(ele);
    }
  };
  // 创建提示容器
  DropdownMessage.prototype.create = function() {
    this.pop = $('<div class="pop-message"></div>').css('left', '-100%');
    this.content = $('<div class="content"></div>');
    this.pop.append(this.content);
    $(document.body).append(this.pop);
  };
  // 设置提示文本和重设方向
  DropdownMessage.prototype.text = function(ele, msg) {
    var text = ele.data('last-msg');
    if (!text)
      return;
    this.content.text(ele.data('last-msg'));
    var offset = ele.offset();
    
    // 偏移
    var interval = 10;
    var height = this.pop.height();
    var availab = utils.getAvailabHeight(ele);
    var top = 0;

    if (availab > height) {
      // 下方显示
      this.pop.addClass('down').removeClass('up');
      top = offset.top + ele.outerHeight() + interval;
    } else {
      // 上方显示
      this.pop.addClass('up').removeClass('down');
      top = offset.top - height - interval;
    }

    this.pop.css({
      'min-width': ele.width(),
      left: offset.left,
      top:  top,
      bottom: 'auto',
      opacity: 1
    });
  };
  // 隐藏消息框
  DropdownMessage.prototype.hidePop = function(ele, msg) {
    this.pop.css({
      top: '-100%',
      opacity: 0
    });
  };


  // 注入提示方式
  if (window.Prompt) {
    Prompt.add('NoteMessage', new NoteMessage());
    Prompt.add('NextLineMessage', new NextLineMessage());
    Prompt.add('DropdownMessage', new DropdownMessage());
  }


})();


/* 插件配置 */
/**
 * flexible.js
 */
(function() {
  window.RefreshRem = function() {
    $('.input-select').SelectBox();
    // window.compoment.render();
  };
})();

/**
 * Clipboard 拷贝功能
 */
(function() {
  if (!window.Clipboard)
    return;
  // 注册组件到框架渲染
  window.compoment.register(function(parent) {
    $('.btn-copy', parent).unbind('click');
    var clipboard = new Clipboard('.btn-copy');
    clipboard.on('success', function(e) {
      e.clearSelection();
      window.Messages.success('拷贝成功!');
    });
    clipboard.on('error', function(e) {
      console.error('clipboard-Action:', e.action);
      console.error('clipboard-Trigger:', e.trigger);
    });
  });
})();



/* 组合功能配置 */
(function() {

  // 表单结果提示
  // 依赖 对话框和Message组件配置
  $.fn.formNotification = function(form) {
    form = form || $(this);
    if (!form || form.length !== 1) {
      return;
    }

    // 提示文本
    var success = form.attr('data-success') || '操作成功';
    var fail = form.attr('data-fail') || '操作失败';
    // 创建iframe接收事件
    var iframe = $('<iframe name="receiver"></iframe>').hide();
    form.parent().append(iframe);
    // 设置接受结果框架
    form.attr('target', 'receiver');


    // 提示消息
    var shoeMessage = function(status) {
      if (status) {
        Messages.success(success);
      } else {
        Messages.error(fail);
      }
      // 刷新查询表格
      window.RefresQueryResult();
    };

    // 关闭对话框
    var closeDialog = function(status) {
      var dialog = window.queryDialog(form);
      if (dialog) {
        dialog.loading(false);
        dialog.close(function() {
          shoeMessage(status);
        });
      } else {
        shoeMessage(status);
      }
    };

    // 监听事件
    utils.listenIframe(iframe, function(status) {
      // 获取表单钩子
      var hookFn = form.data('receiver-hook');
      if ($.isFunction(hookFn)) {
        hookFn(status, closeDialog);
      } else {
        closeDialog(status);
      }
    });

  };

  // 表单结果提示 - ajax提交表单
  // 依赖 对话框和Message组件配置
  $.fn.ajaxFormNotification = function(form) {
    form = form || $(this);
    if (!form || form.length !== 1) {
      return;
    }
    // 提示文本
    var success = form.attr('data-success') || '操作成功';
    var fail = form.attr('data-fail') || '操作失败';

    // 提示消息
    var shoeMessage = function(status) {
      if (status) {
        Messages.success(success);
      } else {
        Messages.error(fail);
      }
      // 刷新查询表格
      window.RefresQueryResult();
    };
    // 关闭对话框
    var closeDialog = function(status) {
      var dialog = window.queryDialog(form);
      if (dialog) {
        dialog.loading(false);
        dialog.close(function() {
          shoeMessage(status);
        });
      } else {
        shoeMessage(status);
      }
    };

    form.submit(function(event, ignore) {
      // 验证失败则不继续
      if (!ignore)
        return;
      utils.ajax({
        url: form.attr('action'),
        type: form.attr('method') || 'get',
        data: form.serialize(),
        error: function() {
          closeDialog(false);
        },
        success: function(msg) {
          // 获取表单钩子
          var hookFn = form.data('receiver-hook');
          if ($.isFunction(hookFn)) {
            hookFn(msg, closeDialog);
          } else {
            closeDialog(msg == 'true' || msg == 'TRUE');
          }
        }
      });
      return false;
    });









  };



  // 注册组件到框架渲染
  window.compoment.register(function(parent) {
    $('.notification').formNotification();
    $('.ajaxFormNotification').ajaxFormNotification();
  });


})();