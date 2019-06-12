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
    dateInput.attr('onfocus', "WdatePicker({ 'errDealMode': 0, 'skin': 'twoer', 'dateFmt': '" + fmt + "' })");
  };

  // 注册组件到框架渲染
  window.compoment.register(function(parent) {
 
    var timeInputs;

    // 基本日期输入框
    timeInputs =  $('.input-date,.date-hms', parent).each(function() {
      window.SetInputDate($(this));
    });

    // 范围如期输入框
    timeInputs = timeInputs.add($('.date-start', parent).each(function() {
      var dateFmt = $(this).attr('date-fmt');
      var parentItem = $(this).attr('date-parent');
      var fmt = dateFmt || 'yyyy-MM-dd HH:mm:ss';
      var inputStart = $(this);

      // 寻找结束日期
      var inputEnd = $('.date-end', parentItem ? $(parentItem) : inputStart.closest('.item'));

      inputStart.focus(function() {
        // 结束日期
        var dateEnd = inputEnd.val();
        this.initcfg = {
          el: this,
          errDealMode: 0,
          skin: 'twoer',
          startDate: dateEnd ? (dateFmt ? '00:00' : '%y-%M-%d 00:00:00') : '',
          maxDate: dateEnd !== '' ? dateEnd : '',
          dateFmt: fmt
        }
        window.WdatePicker(this.initcfg);
      });
    }));

    timeInputs = timeInputs.add($('.date-end', parent).each(function() {
      var dateFmt = $(this).attr('date-fmt');
      var parentItem = $(this).attr('date-parent');
      var fmt = dateFmt || 'yyyy-MM-dd HH:mm:ss';
      var inputEnd = $(this);

      // 寻找结束日期
      var inputStart = $('.date-start', parentItem ? $(parentItem) : inputEnd.closest('.item'));

      inputEnd.focus(function() {
        // 结束日期
        var dateStart = inputStart.val();
        this.initcfg = {
          el: this,
          errDealMode: 0,
          skin: 'twoer',
          startDate: dateStart ? (dateFmt ? '23:59' : '%y-%M-%d 23:59:59') : '',
          minDate: dateStart !== '' ? dateStart : '%y-%M-%d 23:59:59',
          dateFmt: fmt
        };
        WdatePicker(this.initcfg);
      });
    }));

    // 初始化默认时间
    // 默认时间-开始
    $('.default-time', parent).val(utils.formatTime(new Date(), 'yyyy-MM-dd 00:00:00'));
    // 默认时间-结束
    $('.default-time-end', parent).val(utils.formatTime(new Date(), 'yyyy-MM-dd 23:59:59'));

    // 默认时间-开始
    $('.default-date', parent).val(utils.formatTime(new Date(), 'yyyy-MM-dd'));
    // 默认时间-结束
    $('.default-date-end', parent).val(utils.formatTime(new Date(), 'yyyy-MM-dd'));




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

	// test: 测试加载图片使用默认路径, 发布时解开下面代码
	MyDialog.loadImg = contextPath + '/images/loading.gif';

  // 配置顶层框架加载渲染对话框内组件
  if (window.top === window) {
    // 挂接对话框加载钩子
    MyDialog.open = function(dialog, content) {
      // 如果对话框类型为普通对话框, 则重新渲染组件
      if (typeof(dialog.options.target) != 'string') {
        // 重新渲染组件
        window.compoment.render(content[0]);
      }
    };
    // 挂接对话框关闭钩子
    MyDialog.close = function(dialog, errInfo) {
      if (errInfo) {
        console.warn('errInfo-url: ', errInfo.url);
        console.warn('errInfo-exception: ', errInfo.exception);
        Messages.error('加载失败, 请联系管理员...! #2');
      }
    };
  }

  // 封装
  $.fn.Alert = function(title, onCancel, noPadding) {
    return new window.top.Alert(this, title, onCancel, noPadding);
  };
  $.fn.Confirm = function(title, onConfirm, onCancel, autoClose) {
    return new window.top.Confirm(this, title, onConfirm, onCancel, null, autoClose);
  };
  // ajax询问框, 点击确定时自动发送ajax
  window.AjaxConfirm = function(msg, title, ajaxConfig, reload) {
    var errorFn = ajaxConfig.error;
    var successFn = ajaxConfig.success;

    ajaxConfig.error = function() {
      dialog.close(null, true);
      errorFn();
      if (reload)
        window.RefresQueryResult();
    };
    ajaxConfig.success = function(msg) {
      dialog.close(null, true);
      successFn(msg);
      if (reload)
        window.RefresQueryResult();
    };

    var dialog = new window.top.Confirm(msg, title, function() {
      dialog.loading(true);
      utils.ajax(ajaxConfig);
    }, null);
    return dialog;
  };

  // 查看密钥
  window.showKey = function(element, title) {
    var keyTemplate = $('<div style="max-width: 400px;word-break: break-all;"><p><span class="key"></span><a href="javascript:void(0);" class="btn-copy ml-10">[复制]</a></p></div>');
    var key = $(element).attr('data-key');
    $('.key', keyTemplate).text(key);
    $('.btn-copy', keyTemplate).attr('data-clipboard-text', key);
    keyTemplate.Alert(title || '查看密钥');
  }


  // 图片对话框
  window.lookImg = function(path, title, element) {
    // test: 测试图片路径, 发布注释
    // path = '/images/idCard.jpg';

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
        $(img).css({
          'max-width': '900px'
        });
        $(img).Alert(title || '查看图片');
      });
    } else {
      $($(path).clone()).Alert(title || '查看图片');
    }
    


  };

  // 根据对话框内元素, 寻找当前对话框
  window.queryDialog = function(ele) {
    // 对话框内容元素
    var flag = ele.attr('dialog-id') ? ele : ele.closest('.dialog-content-flag');
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

    var createDialog = function() {
      var btn = $(this);
      var url = btn.attr('data-dialog');
      if (!url || url === '')
        return;
      var option = {
        target: url,
        isModal: false,
        onOpen: function() { btn.ending(); },
        onClose: function() { btn.ending(); },
      };
      new window.top.MyIframe(option);
      btn.loading(0, btn.attr('data-hideIco'));
    };

    $('[data-myiframe]', parent).unbind('click', createMyIframe).click(createMyIframe);

    // 封装历史查询对话框
    $('[data-dialog]', parent).unbind('click', createDialog).click(createDialog);

  });

  // 组件渲染完毕重新resize当前对话框
  $(document).bind('compoment-render-over', function() {
    // ...
  });



})();



/**
 * SelectBox 下拉列表组件
 */
(function() {

  // 获取下拉列表实例
  window.getSelectBox = function(select) {
    if (!select || select.length <= 0)
      return null;
    return select.get(0).selectBox;
  };


  // 注册组件到框架渲染
  window.compoment.register(function(parent) {
    $('.input-select', parent).SelectBox();
    // 排除非初次渲染
    $('.input-select', $('.not-ready-render', parent)).each(function() {
      if (this.selectBox)
        this.selectBox.destroy();
    });

    // data-value 来设置默认值
    $('select[data-value]', parent).each(function() {
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
    $('.switch-wrap', parent).each(function() {
      $(this).SwitchTextRadio();
    })
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
      var ignoreZero = ele.attr('data-ignorezero');
      if ($.isFunction(params)) {
        var result = param(val, ele);
        max = result.value;
        msg = result.message;
      } else {
        max = $(params).val();
        setTimeout(function() {
          if ($(params).get(0).enumstatus === Compared.EnumStatus.fail) {
          	$(params).change();
          }
        }, 50);
        msg = '超过最大值 ' + max;
      }
      max = parseFloat(max);
      if (isNaN(max) || (max == 0 && ignoreZero === undefined) ) {
        return Compared.toStatus(true);
      }
      return  Compared.toStatus(parseFloat(val) < max, msg);
  });
  // 最小金额验证
  Compared.add('minIgnoreEmpy', function(val, params, ele, ansyFn) {
      var min = '';
      var msg = '';
      var ignoreZero = ele.attr('data-ignorezero');
      if ($.isFunction(params)) {
        var result = param(val, ele);
        min = result.value;
        msg = result.message;
      } else {
        min = $(params).val();
        setTimeout(function() {
          if ($(params).get(0).enumstatus === Compared.EnumStatus.fail) {
          	$(params).change();
          }
        }, 50);
        msg = '低于最小值 ' + min;
      }
      min = parseFloat(min);
      if (isNaN(min) || (min == 0 && ignoreZero === undefined)) {
        return Compared.toStatus(true);
      }
      return  Compared.toStatus(parseFloat(val) > min, msg);
  });

    // 图片尺寸不能超过最大限制
    Compared.add('checkImageSize', function(val, params, ele, ansyFn) {
      if (ele[0].files.length == 0)
          return Compared.toStatus(true);
      var maxSize = $(ele).attr('data-max') || 4080;
      var size = ele[0].files[0].size  / 1024;
      return Compared.toStatus(size < maxSize, '图片尺寸超过:' + maxSize + 'kb');
    });


  // 挂接事件===================================
  // 挂接验证钩子
  FormValidator.SubmitHandler = function(status, form) {
    var dialog = window.queryDialog(form);
    if (dialog)
      dialog.loading(status);
  };
  
  // 挂接验证失败钩子

  FormValidator.submitError = function(info) {
	  var name = $(info.errInfo.element).closest('.input-area').find('.label').text();
	  utils.showValidateError(name + ' ' + info.errInfo.message);
	  window.scrollTo(0, $(info.errInfo.element).offset().top - 10);
  };
  
  
  
  
  // 获取表单验证实例
  window.getValidator = function(form) {
    if (!form || form.length <= 0)
      return undefined;
    return form.get(0).FormValidator;
  };

  // 注册组件到框架渲染
  window.compoment.register(function(parent) {
    
    $('.validator', parent).FormValidator();
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
  $.fn.formNotification = function(count) {
    form = $(this);
    if (!form || form.length !== 1) {
      return;
    }

    var selfForm = form;
    var name = 'receiver_' + Math.floor(Math.random() * 100);
    $('iframe[name="' + this.name + '"]').remove();
    // 提示文本
    var success = form.attr('data-success') || '操作成功';
    var fail = form.attr('data-fail') || '操作失败';
    // 创建iframe接收事件
    var iframe = $('<iframe class="receive-iframe" name="' + name + '"></iframe>').hide();
    selfForm[0].receiver_iframe = iframe;
    form.parent().append(iframe);
    // 设置接受结果框架
    form.attr('target', name);


    // 提示消息
    var shoeMessage = function(status, msg) {
      if (status) {
        Messages.success(msg || success);
      } else {
        Messages.error(msg || fail);
      }
      // 刷新查询表格
      window.RefresQueryResult();
    };

    // 关闭对话框
    var closeDialog = function(status, msg, notShowMsg) {
      var dialog = window.queryDialog(selfForm);
      if (dialog) {
        dialog.loading(false);
        dialog.close(function() {
          if (!notShowMsg)
            shoeMessage(status, msg);
        });
      } else {
        if (!notShowMsg)
          shoeMessage(status, msg);
      }
    };


    // 监听事件
    utils.listenIframe(iframe, function(status) {
      // 获取表单钩子
      var hookFn = selfForm.data('receiver-hook');
      if ($.isFunction(hookFn)) {
        hookFn(status, closeDialog, iframe);
      } else {
        closeDialog(status);
      }
    });

  };

  // 表单结果提示 - ajax提交表单
  // 依赖 对话框和Message组件配置
  $.fn.ajaxFormNotification = function(count) {
    form = $(this);
    if (!form || form.length !== 1) {
      return;
    }

    // 提示文本
    var success = form.attr('data-success') || '操作成功';
    var fail = form.attr('data-fail') || '操作失败';

    // 提示消息
    var shoeMessage = function(status, errMsg) {
      switch (status) {
        case true:
          Messages.success(errMsg || success);
          break;
        case false:
          Messages.error(errMsg || fail);
          break;
        case 'error':
          Messages.error('未知错误, 请联系管理员: ' + errMsg);
          break;
      }
      // 刷新查询表格
      window.RefresQueryResult();
    };
    // 关闭对话框
    var closeDialog = function(status, errMsg, notShowMessage) {
      var dialog = window.queryDialog(form);
      if (dialog) {
        dialog.loading(false);
        dialog.close(function() {
          if (!notShowMessage)
            shoeMessage(status, errMsg);
        });
      } else {
        if (!notShowMessage)
          shoeMessage(status, errMsg);
      }
    };

    form.submit(function(event, ignore) {
      // 验证失败则不继续
      if (!ignore)
        return;
      utils.ajax({
        url: form.attr('action'),
        type: form.attr('method'),
        data: form.serialize(),
        error: function(errMsg) {
          closeDialog('error', errMsg || '#001');
        },
        success: function(msg) {
          // 获取表单钩子
          var hookFn = form.data('receiver-hook');
          if ($.isFunction(hookFn)) {
            hookFn(msg, closeDialog);
          } else {
            closeDialog(msg == 'true' || msg == 'TRUE' || msg == '"true"'  || msg == '"TRUE"');
          }
        }
      });
      return false;
    });


  };

  // 注册组件到框架渲染
  window.compoment.register(function(parent) {
    $('.notification', parent).each(function(i) {
      $(this).formNotification(i);
    });
    $('.ajaxFormNotification', parent).each(function(i) {
      $(this).ajaxFormNotification(i);
    });
    // 排除非初次渲染
    $('.notification', $('.not-ready-render', parent)).each(function() {
      if (this.receiver_iframe) {
        $(this).removeAttr('target');
        $(this.receiver_iframe).unbind('load');
        $(this.receiver_iframe).unbind('error');
      	$(this.receiver_iframe).remove();
      }
    });
  });


})();

/* 分页 */
(function() {
  // valuelist分页只有一页时, 插入1的跳转按钮
  // 注册组件到框架渲染
  window.compoment.register(function(parent) {
    // 总页数
    var countPage = $('#countPage');
    var JUMP_INPUT = $('#JUMP_INPUT');
    var total = parseInt((countPage.text()).replace(/,/g, ''));
    JUMP_INPUT.attr('max', total).attr('min', '1');
    // vhl分页跳转
    $('#JUMP_BTN').click(function() {
      // 获取最大页数
      total = parseInt((countPage.text()).replace(/,/g, ''));
      // 即将跳转页数
      var index = parseInt(JUMP_INPUT.val());
      if (isNaN(total) || isNaN(index) || index > total || index < 1) {
        return;
      }
      // 获取link地址
      var link = $('a', $('#JUMP_LINK')).attr('href');
      link = link.replace('pagingPage=1', 'pagingPage=' + index);
      window.location = link;
    });
    $('.gpage', parent).each(function(i) {
      if ($('#countPage', this) !== 1)
        return;
      var countPage = $('#countPage', this).text();
      var count = parseInt(countPage.replace(/,/g, ''));
      if (count == 0 || count == 1 || isNaN(count)) {
      	var td = $('td#JUMP_LINK', this).removeClass('hidden');
      	$('a', td).css({
          'color': '#fff',
          'background-color': '#39f',
          'border-color': '#39f'
        });
      }
    });
  });
})();

/* 字典城市下拉联动 */
(function() {

  $.fn.dictCitySelect = function(opt) {
    var defaultOption = {
      // 默认省
      prov: null,
      // 省份下拉列表是有请选择
      provEmpy: true,
      // 省份下拉列表选择器
      provSelect: '.prov',
      // 默认城
      city: null,
      // 城市下拉列表是有请选择
      cityEmpy: true,
      // 城市下拉列表选择器
      citySelect: '.city',
      // 地区编码输入框选择器
      organization: '.organization'
    };
    var config = $.extend({}, defaultOption, opt);

    // 获取下拉列表
    var provSelect = $(config.provSelect);
    var citySelect = $(config.citySelect);
    var organization = $(config.organization);

    if (provSelect.length == 0 || citySelect.length == 0) {
      console.warn('初始化城市联动组件失败, 未找到省份/城市下拉列表!');
      return;
    } else {
      provSelect.html('<option value="">请选择</option>');
      citySelect.html('<option value="">请选择</option>');
      provSelect.change(changeProv);
      citySelect.change(changeCity);
    }

    // 初始化获取所有省
    Api.agent.queryProvList(function(provList) {
      if (provList == null) {
        Messages.error('获取省份数据失败!, 请检查网络或稍后重试...');
        return;
      }
      // 过滤状态false的元素
      provList = utils.filterDisableItem(provList);
      var options = $();
      if (config.provEmpy) {
        options = options.add($('<option value="">请选择</option>'));
      }
      utils.each(provList, function(provInfo) {
        var provCode =  provInfo.key.split(".")[1];
        var option = $('<option value="' + provInfo.value + '" data-provcode="' + provCode + '">' + provInfo.value + '</option>');
        if (provInfo.value == config.prov) {
          option[0].selected = true;
          provSelect.val(provInfo.value);
          changeProv(provCode);
        }
        options = options.add(option);
      });
      
      provSelect.html(options).renderSelectBox();
    });

    // 更改省份
    function changeProv(provcode) {
      var provCode =  provSelect.currentOption().attr('data-provcode') || provcode;
      Api.agent.queryCityList(provCode, function(cityList) {
        if (cityList == null) {
          Messages.error('获取城市数据失败!, 请检查网络或稍后重试...');
          return;
        }
        // 过滤状态false的元素
        cityList = utils.filterDisableItem(cityList);
        var options = $();
        if (config.cityEmpy) {
          options = options.add($('<option value="">请选择</option>'));
        }
        utils.each(cityList, function(cityInfo) {
          var cityCode = cityInfo.key.split(".")[1];
          var option = $('<option value="' + cityInfo.value + '" data-citycode="' + cityCode + '">' + cityInfo.value + '</option>');
          if (cityInfo.value == config.city) {
            option[0].selected = true;
            citySelect.val(cityInfo.value);
            changeCity(cityCode);
          }
          options = options.add(option);
        });
        citySelect.html(options).renderSelectBox();
      });

    }

    // 更改城市
    function changeCity(citycode) {
      var cityCode =  citySelect.currentOption().attr('data-citycode') || citycode;
      organization.val(cityCode.replace('CITY_', ''));
    }

  };

})();