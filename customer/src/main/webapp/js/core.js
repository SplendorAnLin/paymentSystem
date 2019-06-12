// 上传文件
window.InitFileUpload = function(fileWrap) {
  if (!fileWrap || fileWrap.length === 0) {
    return;
  }
  var textInput = $('input[type="text"]', fileWrap);
  var fileInput = $('input[type="file"]', fileWrap);
  var btn = $('.btn', fileWrap);
  var open = false;

  // 移除之前函数, 防止重复初始化
  fileInput.unbind();
  fileInput.click(function() {
    open = true;
    btn.loading(0);
  });
  fileInput.change(function() {
    open = false;
    var fileName = fileInput.val().replace(/c:\\fakepath\\/i, '');
    textInput.val(fileName);
    btn.ending();
    // 主动失去焦点触发验证
    fileInput.blur();
  });
  fileInput.focusin(function() {
    if (open == true) {
      btn.ending();
    }
  });
};

// 数组隐射成下拉列表数组
window.optionArray = function(array, labelField, valueField, hasEmpy) {
  if (!array || array.length < 1)
    return [];

  var options = [];
  if (hasEmpy)
    options = [{label: '全部', value: ''}];
  for (var i = 0; i < array.length; ++i) {
    var item = array[i];
    options.push({
      label: item[labelField],
      value: item[valueField],
    });
  }
  return options;
};

// 对象隐射成下拉列表 key=value, 值=label
window.optionObject = function(optionObj, hasEmpy) {
  if (!optionObj)
    return [];
  
  var options = [];
  if (hasEmpy) {
	  options = [{label: '全部', value: ''}]
  }
  for (var key in optionObj) {
    options.push({
      label: optionObj[key],
      value: key,
    });
  }

  return options;
};







// 封装导出
window.exprotResult = function(action) {
  var form = $('form', parent.document);
  var backup = form.attr('action');
  var backTarget = form.attr('target');
  var backupMethod = form.attr('method');
  form.attr('action', action);
  form.attr('method', 'post');
  form.attr('target', '_blank');
  form.attr('notLoad', true);
  form.submit();
  form.removeAttr('notLoad');
  form.attr('method', backupMethod);
  form.attr('target', backTarget);
  form.attr('action', backup);
};

// 封装ajax导出
window.exprotForAjax = function(action, prefix) {
  var form = $('form', parent.document);
  prefix = prefix || '';
  utils.ajax({
    'url': action,
    'data': form.serializeArray(),
    'success': function(data){
      if(data == 'true'){
        Messages.success(prefix + '导出成功');
      }else {
        Messages.error(prefix + '导出失败');
      }
    }
  }); 
};



// 刷新查询表格
window.RefresQueryResult = function() {
  // 当前窗口
  var windows = window.top.windowManage.current();
  var iframe = windows.iframe;
  // 当前查询结果iframe
  var resultIframe = utils.iframeFind(iframe, '.query-result iframe');
  var form = utils.iframeFind(iframe, '.query-form form');
  if (resultIframe.length === 1) {
    // resultIframe.submit();
    // 转圈
    utils.iframeFind(resultIframe, '.table-warp').showLoadImage();
    form.submit();
    // resultIframe.get(0).contentWindow.location.reload();
  }
};

// 开始加载按钮
$.fn.loading = function(outTime, hideIco, fn) {
  outTime = outTime === undefined ? 0 : outTime;
  var element = this;
  if (element.data('disabled') !== undefined || (!element.hasClass('btn') &&  !element.hasClass('btn-small')) ) {
    return element;
  }

  // 创建加载图标
  var i = $('<i class="fa fa-spinner fa-spin fa-fw"></i>');
  // 插入dom, 添加禁用样式
  if (!hideIco) {
    element.prepend(i);
  }
  element.addClass('loading');
  if (outTime !== 0) {
    // 计时器
    var timeHandler = setTimeout(function() {
      if ($.isFunction(fn)) {
        fn();
      }
      element.ending();
    }, outTime);
  }
  element.data({
    'disabled': true,
    'loadIco': i,
    'timeId': timeHandler
  });
  return element;
};

// 结束加载按钮
$.fn.ending = function() {
  var element = this;
  var i = element.data('loadIco');
  var disabled = element.data('disabled');
  var timeHandler = element.data('timeId');
  if (disabled == undefined) {
    return element;
  }
  i.remove();
  element.removeClass('loading');
  clearTimeout(timeHandler);
  element.removeData('disabled');
  element.removeData('loadIco');
  element.removeData('timeId');
  return element;
};

// 显示框架加载图片
$.fn.showLoadImage = function(noImg, fixed) {
  var element = this;
  if (element.data('loadImage') !== undefined) {
    return element;
  }
  var img = $('<div class="loading-img"><div class="img"></div></div>').show();
  if (fixed)
    img.css('position', 'fixed');
  if (noImg) {
    $('.img', img).hide();
  }
  element.append(img);
  element.data('loadImage', img);
  return element;
};

// 隐藏框架加载图片
$.fn.hideLoadImage = function() {
  var element = this;
  var img = element.data('loadImage');
  $('.loading-img', element).remove();
  element.removeData('loadImage');
  if (img === undefined) {
    return element;
  }
  img.remove();
  element.removeData('loadImage');
  return element;
};

// 开始加载输入框
$.fn.inputLoading = function(outTime, hideIco, fn) {
  outTime = outTime === undefined ? 0 : outTime;
  var input = this;
  // 忽略正在加载
  if (input.data('disabled') !== undefined) {
    return input;
  }
  // 创建加载图标
  var i = $('<i class="fa input-loading-ico fa-spinner fa-spin fa-fw"></i>');
  // 插入dom, 添加禁用样式
  if (!hideIco) {
    input.after(i);
  }
  input.addClass('loading');
  if (outTime !== 0) {
    // 计时器
    var timeHandler = setTimeout(function() {
      if ($.isFunction(fn)) {
        fn();
      }
      input.inputEnding();
    }, outTime);
  }
  input.data({
    'disabled': true,
    'loadIco': i,
    'timeId': timeHandler
  });
  return input;
};

// 结束加载输入框
$.fn.inputEnding = function() {
  var input = this;
  var i = input.data('loadIco');
  var disabled = input.data('disabled');
  var timeHandler = input.data('timeId');
  if (disabled == undefined) {
    return input;
  }
  i.remove();
  input.removeClass('loading');
  clearTimeout(timeHandler);
  input.removeData('disabled');
  input.removeData('loadIco');
  input.removeData('timeId');
  return input;
};

// 清空表单内的值 ignore等于忽略选择器, 比如否个form内有些隐藏值不希望被清空
$.fn.clearForm = function(ignore) {
  var form = $(this);
  // 清空输入框
  $('input', form).not('[type="reset"]').not(ignore).val('');
  // 清空文本域
  $('textarea', form).not(ignore).val('').text('');
  // 清空下拉列表
  $('select', form).not(ignore).val('');
  $('.input-select', form).SelectBox();
};

// 禁用表单内元素
$.fn.disableInput = function(disabledSelf, ignore) {
  if (disabledSelf) {
    this.attr('disabled', 'disabled');
    this.filter('select.input-select').SelectBox();
  } else {
    this.each(function() {
      $('input,textarea,select', this).not('[type="reset"]').not(ignore).attr('disabled', 'disabled');
      // 刷新下拉列表
      $('select', this).not(ignore).SelectBox();
    });
  }
};

// 启用表单内元素
$.fn.enablelInput = function(disabledSelf, ignoreClass) {
  if (disabledSelf) {
    this.removeAttr('disabled');
    this.filter('select.input-select').SelectBox();
  } else {
    this.each(function() {
      $('input,textarea,select', this).not('[type="reset"]').not(ignoreClass).removeAttr('disabled');
      // 刷新下拉列表
      $('select', this).SelectBox();
    });
  }
};


// 只读表单内元素
$.fn.readonlyInput = function(disabledSelf) {
  if (disabledSelf) {
    this.attr('readonly', 'readonly');
    this.filter('select.input-select').SelectBox();
  } else {
    this.each(function() {
      $('input,textarea,select', this).not('[type="reset"]').attr('readonly', 'readonly');
      // 刷新下拉列表
      $('select', this).SelectBox();
    });
  }
};

// 取消只读表单内元素
$.fn.readInput = function(disabledSelf) {
  if (disabledSelf) {
    this.removeAttr('readonly');
    this.filter('select.input-select').SelectBox();
  } else {
    this.each(function() {
      $('input,textarea,select', this).not('[type="reset"]').removeAttr('readonly');
      // 刷新下拉列表
      $('select', this).SelectBox();
    });
  }
};






// 分页
$.fn.page = function(config) {
  var option = {
    // 最大显示按钮数量
    MAX_BTNS: 7,
    // 总条数
    total: 0,
    // 总页数
    totalPage: 0,
    // 当前页数
    current: 0,
    // 每页显示多少条
    pageCount: null,
    // 是否渲染总条数
    isTotal: true,
    // 是否渲染跳转编辑框
    isJumpInput: true,
    // 是否渲染首页尾页
    isFAL: true,
    // 跳转钩子
    jumpHook: null
  };

  // 分页容器
  var page = this;
  // 合并参数
  config = $.extend({}, option, config);

  if (config.pageCount && config.pageCount > 0) {
    config.totalPage = config.total / config.pageCount;
  }


  // 模型
  var model = {
    // 生成数字按钮数组
    numbers: function() {
      var current = config.current;
      var MAX_BTNS = config.MAX_BTNS;
      var totalPage = config.totalPage;
      var hafl = Math.floor(MAX_BTNS / 2);
      var start, end;

      if (current <= hafl) {
        start = 1;
        end = Math.min(MAX_BTNS, totalPage);
      } else if (current > (totalPage - hafl)) {
        start = totalPage < MAX_BTNS ? 1 : (totalPage - MAX_BTNS + 1);
        end = totalPage;
      } else {
        start = current - hafl;
        end = current + hafl;
      }

      var nums = [];
      for (var i = start; i <= end; ++i) {
        nums.push(i);
      }
      return nums
    },
  };



  // 视图
  var view = {
    // 渲染总条数
    record: function() {
      return $('<span class="record mr-20">共<span class="count">' + config.total + '</span>条记录</span>');
    },
    // 渲染跳转按钮
    jumpBtns: function() {
      var wrap = $('<span></span>');
      // 首页按钮
      var indexBtn = $('<span class="page-btn-1"><i class="fa-step-backward fa"></i></span>').click(controller.first);
      // 上一页按钮
      var prevBtn = $('<span class="page-btn-1"><i class="fa-chevron-left fa"></i></span>').click(controller.prev);
      // 尾页按钮
      var tailBtn = $('<span class="page-btn-1"><i class="fa-step-forward fa"></i></span>').click(controller.last);
      // 下一页按钮
      var nextBtn = $('<span class="page-btn-1"><i class="fa-chevron-right fa"></i></span>').click(controller.next);
      // 数字按钮列表
      var numbers = $('<span class="jump-number mlr-5"></span>');

      if (config.current === 1) {
        indexBtn.addClass('off');
        prevBtn.addClass('off');
      }
      if (config.current === config.totalPage) {
        tailBtn.addClass('off');
        nextBtn.addClass('off');
      }

      wrap.append(indexBtn);
      wrap.append(prevBtn);
      wrap.append(numbers.append(view.numbers()));
      wrap.append(nextBtn);
      wrap.append(tailBtn);
      return wrap;
    },
    // 渲染数字按钮
    numbers: function() {
      var result = $('');
      var nums = model.numbers();
      for (var i = 0; i < nums.length; ++i) {
        var toIndex = nums[i];
        var btn = $('<a href="javascript:void(0);">' + toIndex + '</a>');
        btn.data('index', toIndex);
        btn.attr('data-index', toIndex);
        if (toIndex === config.current) {
          btn.addClass('current').addClass('off');
        }
        result = result.add(btn);
      }

      $(result).click(function() {
        var toIndex = $(this).data('index');
        if (toIndex) {
          controller.jump(toIndex);
        }
      });
      return result;
    },
    // 渲染跳转编辑框
    jumpInput: function() {
      var template = '\
        <span class="ml-20 jump-wrap">\
          <span class="plr-5">转至</span>\
          <span class="input-wrap">\
            <input id="page-input" class="input-text w-p-100" type="text" value="' + config.current + '" max="' + config.totalPage + '">\
            <span id="page-jump" class="jump-btn page-btn-1"><i class="fa-share fa"></i></span>\
          </span>\
          <span class="plr-5">页</span>\
        </span>\
      ';
      var dom = $(template);
      var input = $('#page-input', dom);
      $('#page-jump', dom).click(function() {
        var toIndex = parseInt(input.val());
        controller.jump(toIndex);
      });
      return dom;
    },
    // 渲染
    render: function() {
      var jumpBar = $('<div class="jump-bar"></div>');
      if (config.isTotal)
        jumpBar.append(view.record());
      jumpBar.append(view.jumpBtns());
      if (config.isJumpInput)
        jumpBar.append(view.jumpInput());
      page.empty();
      page.append(jumpBar);
    }
  };



  // 控制器
  var controller = {
    // 跳转到某一页
    jump: function(toIndex) {
      if (toIndex < 1 || toIndex > config.totalPage || isNaN(toIndex)) {
        console.warn('page::jump 跳转失败, 输入跳转索引越界!', toIndex);
        return;
      }
      if (config.current === toIndex) {
        return;
      }

      if ($.isFunction(config.jumpHook))
        config.jumpHook(toIndex, config.current);

      config.current = toIndex;
      view.render();

    },
    // 首页
    first: function() { controller.jump(1); },
    // 尾页
    last: function() { controller.jump(config.totalPage); },
    // 上一页
    prev: function() { controller.jump(config.current - 1); },
    // 下一页
    next: function() { controller.jump(config.current + 1); }
  };


  view.render();
  return this;
};

/**
 * 封装js对话框中的分页
 * <div class="jump-rarp mt-20 text-center" id="PageWrapCustomize" total_count="${page.totalResult}" total="${page.totalPage}"  current="${page.currentPage}"></div>
 */
$.fn.pageCustom = function(action, formData) {
  var pageWrap = $('#PageWrapCustomize');
  if (pageWrap.length !== 1) {
    $('.jump-number', pageWrap).html('<a href="javascript:void(0);" class="current off">1</a>');
    return;
  }

  var total = parseInt(pageWrap.attr('total_count').replace(/,/g, ''));
  var totalPage = parseInt(pageWrap.attr('total').replace(/,/g, ''));
  var current = parseInt(pageWrap.attr('current').replace(/,/g, ''));
  var count = parseInt((pageWrap.attr('count') + '').replace(/,/g, ''));
  // 当前页查询当前页标志
  var queryParamer = pageWrap.attr('queryParamer') || 'currentPage';
  // 自动获取url查询参数
  var autoParamer = window.location.search || '?';
  // 去除currentPage参数避免累加
  autoParamer = autoParamer.replace(/(&?)currentPage=(\d+)/, '');
  // 去掉?号
  // autoParamer = autoParamer.replace('?', '');
  pageWrap.page({
    // 总条数
    total: total,
    // 总页数
    totalPage: totalPage,
    // 当前页数
    current: current,
    // 每页显示多少条
    pageCount: count,
    // 是否渲染总条数
    isTotal: true,
    // 是否渲染跳转编辑框
    isJumpInput: true,
    // 是否渲染首页尾页
    isFAL: true,
    // 跳转钩子
    jumpHook: function(to, from) {
      dialogIframe.loading(true);
      window.location.href = action + autoParamer + '&currentPage=' + to;
      // utils.ajax({
      //     url: action,
      //     type: 'post',
      //     data: autoParamer + '&currentPage=' + to,
      //     success: function(html) {
      //       var patten = new RegExp('<' + 'tbody>(.|\\n|\\r)*?<\/tbody>');
      //       var tbody = html.match(patten);
      //       if (tbody && tbody[0]) {
      //         $('tbody').replaceWith(tbody[0]);
      //         dialogIframe.loading(false);
      //       }
      //     },
      //     error: function() {
      //       dialogIframe.loading(false);
      //       Messages.error('网络异常, 跳转失败! 请稍后重试...');
      //     }
      //   });
    }
  });
};