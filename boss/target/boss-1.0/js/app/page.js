(function() {
	
/**
 * js 分页跳转
 * 使用以下dom来初始化分页
 * <div class="jump-rarp mt-20 text-center" id="PageWrap" total_count="71" total="8"  current="1"></div>
 */
window.jsPage = function() {
  // 最大显示按钮数
  var MAX_BTNS = 7;
  // 总条数
  var total_count = 0;
  // 总页数
  var total = 0;
  // 当前页数
  var current = 0;
  // 当前按钮
  var currentBtn = null;
  // 分页容器
  var page = null;
  // 渲染dom
  function render() {
    
    var wrap = $('#PageWrap');
    total_count = parseInt(wrap.attr('total_count'));
    total = parseInt(wrap.attr('total'));
    current = parseInt(wrap.attr('current'));
    if (wrap.length !== 1 || 
    !total_count || 
    isNaN(total_count) ||
    !total || 
    isNaN(total) ||
    !current ||
    isNaN(current) || 
    current > total ) {
      return;
    }

    page = $('<div class="jump-bar"></div>');
    page.append(render_record());
    page.append(render_btns());
    page.append(render_jumpInput());
    wrap.append(page);


    

    $('a[data-index]', page).click(function(event) {
      var toIndex = $(this).data('index');
      if (toIndex) {
        jump(toIndex);
      }
    });



  }

  // 重新渲染
  function flush() {
    page.html('');
    page.append(render_record());
    page.append(render_btns());
    page.append(render_jumpInput());
  }


  // 渲染-总条数
  function render_record() {
    var template = '<span class="record mr-20">共<span class="count">' + total_count + '</span>条记录</span>';
    return $(template);
  }

  // 渲染-分页按钮
  function render_btns() {
    var wrap = $('<span></span>');
    // 首页按钮
    var indexBtn = $('<span class="page-btn-1"><i class="fa-step-backward fa"></i></span>').click(toHome).hide();
    // 上一页按钮
    var prevBtn = $('<span class="page-btn-1"><i class="fa-chevron-left fa"></i></span>').click(toPrev);
    // 尾页按钮
    var tailBtn = $('<span class="page-btn-1"><i class="fa-step-forward fa"></i></span>').click(toTail).hide();
    // 下一页按钮
    var nextBtn = $('<span class="page-btn-1"><i class="fa-chevron-right fa"></i></span>').click(toNext);
    // 数字按钮列表
    var numbers = $('<span class="jump-number mlr-5"></span>');

    // wrap.append(indexBtn);
    wrap.append(prevBtn);
    wrap.append(numbers);
    wrap.append(nextBtn);
    // wrap.append(tailBtn);
    numbers.append(create_numbers());
    return wrap;
  }

  // 生成数字按钮
  function create_numbers() {
    var hafl = Math.floor(MAX_BTNS / 2);
    var result = $('');

    var start;
    var end;

    if (current <= hafl) {
      start = 1;
      end = Math.min(MAX_BTNS, total);
    } else if (current > (total - hafl)) {
      start = total < MAX_BTNS ? 1 : (total - MAX_BTNS + 1);
      end = total;
    } else {
      start = current - hafl;
      end = current + hafl;
    }

    for (var i = start; i <= end; ++i) {
      var btn = $('<a href="javascript:void(0);">' + i + '</a>');
      btn.data('index', i);
      btn.attr('data-index', i);
      if (i === current) {
        btn.addClass('current');
        currentBtn = btn;
      }
      result = result.add(btn);
    }

    return result;
  }


  // 渲染-跳转编辑框
  function render_jumpInput() {
    var template = '\
      <span class="ml-20 jump-wrap">\
        <span class="plr-5">转至</span>\
        <span class="input-wrap">\
          <input id="page-input" class="input-text w-p-100" type="text" value="' + current + '" max="' + total + '">\
          <span id="page-jump" class="jump-btn page-btn-1"><i class="fa-share fa"></i></span>\
        </span>\
        <span class="plr-5">页</span>\
      </span>\
    ';
    var dom = $(template);
    var input = $('#page-input', dom);
    $('#page-jump', dom).click(function() {
      var toIndex = parseInt(input.val());
      jump(toIndex);
    });
    return dom;
  }


  // 跳转页面
  function jump(toIndex) {
	  
	console.log(toIndex);
	  
    if (toIndex < 1 || toIndex > total || isNaN(toIndex)) {
      console.warn('jsPage::jump 跳转失败, 输入跳转索引越界!');
      return;
    }
    if (current === toIndex) {
      return;
    }
    current = toIndex;
    flush();


    
    
    var form = $('form', parent.document);
    var backup = form.attr('action');
    var url = backup + '?pagingPage='+ current;
    form.attr('action', url);
    $('.query-btn', parent.document).click();
    
    form.attr('action', backup);

  }

  // 首页
  function toHome() {
    jump(1);
  }

  // 尾页
  function toTail() {
    jump(total);
  };

  // 上一页
  function toPrev() {
    jump(current - 1);
  };

  // 下一页
  function toNext() {
    jump(current + 1);
  };

  // 开始渲染
  render();

};
window.jsPage();

	
})();