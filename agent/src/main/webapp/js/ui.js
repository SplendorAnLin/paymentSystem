/**
 * 组件管理
 */
(function() {

  window.compoment = {
    compoments: [],
    // 注册组件
    register: function(fn) {
      if (!$.isFunction(fn))
        return;
      window.compoment.compoments.push(fn);
    },
    // 渲染组件
    render: function(doc) {
      // 组件所在域
      var parent = doc || document;

      // 文件上传组件
      $('.file-wrap', parent).each(function() {
        window.InitFileUpload($(this));
      });

      // 初始化设置按钮宽度
      $('.btn,.btn-small', parent).each(function() {
        var element = $(this);
        if (element.attr('noWidth') !== undefined || window.modal) {
          return;
        }
          element.css({
            'padding': '0',
            'width': element.outerWidth(),
          });
      });

      // 接管form换行
      $('.stop-formeNnter').each(function() {
        var form = $(this);
        // 取消回车提交
        form.bind('keydown', function(e) {
          var keyNum = e.which || e.keyCode;
          return keyNum == 13 ? false : true;
        });
      });

      // 导出
      $('[data-exprot]').click(function() {
        var action = $(this).attr('data-exprot');
        if (!action || action == '')
          return;
        window.exprotResult(action);
      });

      // 封装ajax获取对象填充到下拉列表
      $('select[data-select-action]').each(function() {
        var select = $(this);
        var value = select.attr('data-value');;
        var url = select.attr('data-select-action');
        utils.ajax({
          url: url,
          success: function(data){
            var bankInfo = $.parseJSON(data);
            var selectBox = window.getSelectBox(select);
            selectBox.options = window.optionObject(bankInfo, true);
            selectBox.selectForValue(value);
            select.renderSelectBox();
          }
        });
      });

      // 封装创建窗口按钮
      $('[data-create]').click(function() {
        var url = $(this).attr('data-create');
        var title = $(this).attr('data-title');
        $(window.top).trigger(window.top.SysEvent.MenuBar.MenuSelected, [url, title]);
      });

      // ajax导出
      $('[data-exprot-ajax]').click(function() {
        var action = $(this).attr('data-exprot-ajax');
        var prefix = $(this).attr('data-prefix');
        if (!action || action == '')
          return;
        window.exprotForAjax(action, prefix);
      });

      // 封装垂直选项卡
      (function() {
        $('.tabSwitch2').unbind('click').click(function() {
          // 选项卡对应内容
          var content = $(this).next();
          if (content.length !== 1 || !content.hasClass('content')) {
            console.warn('tabSwitch2: 切换的选项卡未找到对应内容!');
            return;
          }
          content.toggle();
        });
      })();

      // 新建窗口
      $('.create_window').click(function (event) {
        var url = $(this).attr('href');
        if (window.top.windowManage) {
          window.top.windowManage.create(url, '加载中...');
        }
        event.preventDefault();
      });

      // 封装表格复选框全选
      $('.data-table th input[type="checkbox"]').change(function(event, ignore) {
        if (ignore)
          return;
        // 子复选框
        var checkList = $(this).closest('.data-table').find('td input[type="checkbox"]');
        if (this.checked) {
          checkList.check(true);
        } else {
          checkList.uncheck(true);
        }
      });
      // 如果所有子复选框都取消勾, 则父复选框也取消
      $('.data-table td input[type="checkbox"]').change(function(event, ignore) {
        // tr
        var tr = $(this).closest('tr');
        if (this.checked) {
          tr.addClass('is-selected');
        } else {
          tr.removeClass('is-selected');
        }
        if (ignore)
          return;
        // 父复选框
        var parentCheck = $(this).closest('.data-table').find('th input[type="checkbox"]');
        // 子复选框
        var checkList = $(this).closest('.data-table').find('td input[type="checkbox"]');

        if (checkList.isCheckAll()) {
          parentCheck.check(true);
        } else {
          parentCheck.uncheck(true);
        }
      });

      // 清空按钮
      $('.clear-form').click(function(event) {
        var form = $(this).parents('form');
        // form.clearForm('.ignoreEmpy');
        // event.stopPropagation();
        // return false;
        setTimeout(function() {
          window.compoment.render();
          // $('.input-select', form).SelectBox();
        }, 20);
      });

      // 渲染用户自定义组件
      for (var i = 0; i < window.compoment.compoments.length; ++ i) {
        var renderFn = window.compoment.compoments[i];
        renderFn(parent);
      }

      // 发送组件渲染完毕事件
      $(parent).trigger('compoment-render-over');

    },
  };


  $(document).ready(function() {
    //防止页面后退
    history.pushState(null, null, document.URL);
    window.addEventListener('popstate', function () {
        history.pushState(null, null, document.URL);
    });
  	if (window.stopRender)
  		return;
    // 默认渲染
    window.compoment.render();
  });

})();


/**
 * 查询框架
 */
window.query = function() {
  // 查询接收iframe容器
  var resultIframeWrap = $('.query-result');
  // 结果iframe
  var iframe = $('iframe', resultIframeWrap);
  // 查询按钮 $('.query-form form').find('.query-btn')
  var btn = $('.query-btn'); 

  // 查询事件
  $('.query-form form').submit(function(event) {
    var notLoad = $(this).attr('notLoad');
    if (event.isDefaultPrevented() || notLoad) {
      return;
    }
    btn.loading(10000, false, function() {
      Messages.error(document.title + '查询超时, 请重试!');
      resultIframeWrap.hideLoadImage();
    });
    resultIframeWrap.showLoadImage();
  });

  // 分页也要转圈等待
  $('.gpage td a').click(function() {
    var resultWrap = resultIframeWrap.length == 0 ? $('.query-result', window.parent.document) : resultIframeWrap;
    // 查找对话框中的结果容器
    if (resultWrap.length == 0) {
    	resultWrap = $('body');
    }
    // 跳转编辑框等同于当前页则不跳转
    var current = parseInt(($('.search_resultsSummary b').eq(0).text()).replace(/,/g, ''));
    if (current == parseInt($('#JUMP_INPUT').val()) && $(this).closest('span.input-wrap').length == 1) {
      return;
    }
    // 过滤无法点击的按钮
    if ($(this).closest('.off').length == 0)
      resultWrap.showLoadImage();
  });
  $('.jump-bar span a').click(function() {
    var resultWrap = resultIframeWrap.length == 0 ? $('.query-result', window.parent.document) : resultIframeWrap;
    // 查找对话框中的结果容器
    if (resultWrap.length == 0) {
    	resultWrap = $('body');
    }
    // 过滤无法点击的按钮
    if ($(this).closest('.off').length == 0)
      resultWrap.showLoadImage();
  });



  if(utils.isIphone()){
    // fix iphone BUG
    // resultIframeWrap.css({
    //   'height': '3000px'
    // });
  }

  // 监听iframe
  utils.listenIframe(iframe, function(status) {
    btn.ending();
    resultIframeWrap.hideLoadImage();
    if (!status) {
      return;
    }
    var body = utils.iframeFind(iframe, 'body');
    var warp = utils.iframeFind(iframe, '.table-warp');
    if(utils.isIphone()){
      // fix iphone BUG
      warp.css('width', '100px');
    }
    // new window.top.Alert('高度: ' + utils.toFixed(body.outerHeight(), 0), '提示');
    resultIframeWrap.css({
      'height': utils.toFixed(body.outerHeight(), 0),
      'overflow-y': 'hidden'
    });
    if(utils.isIphone()){
      // fix iphone BUG
      //alert('容器宽度: ' + iframe.width());
      //alert('表格宽度: ' + utils.iframeFind(iframe, 'table').width());
      warp.css('width', iframe.width());
    }
  });

  // 文档加载事件 $(document).ready
  $(window).load(function() {
    // 自动查询
    var btn = $('.query-btn');
    btn.each(function() {
      if (!$(this).hasClass('not-auto-query')) {
        $(this).trigger('click');
      }
    });
  });

};
window.query();


/**
 * js 分页跳转
 * 使用以下dom来初始化分页
 * <div class="jump-rarp mt-20 text-center" id="PageWrap" total_count="71" total="8"  current="1"></div>
 */
(function() {
  var page = $('#PageWrap');
  // 分页只有一页时, 插入1的跳转按钮
  if (page.length !== 1) {
    $('.jump-number', page).html('<a href="javascript:void(0);" class="current off">1</a>');
    return;
  }
  

  var total = parseInt(page.attr('total_count').replace(/,/g, ''));
  var totalPage = parseInt(page.attr('total').replace(/,/g, ''));
  var current = parseInt(page.attr('current').replace(/,/g, ''));
  var count = parseInt((page.attr('count') + '').replace(/,/g, ''));
  // 当前页查询参数
  var queryParamer = page.attr('queryParamer') || 'currentPage';

  page.page({
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
      var form = $('form', parent.document);
      var backup = form.attr('action');
      var url = backup + '?' + queryParamer + '='+ to;
      form.attr('action', url);
      $('.query-btn', parent.document).click();
      form.attr('action', backup);
    }
  });
})();
