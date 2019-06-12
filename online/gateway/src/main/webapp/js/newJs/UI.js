'use strict';

// / <reference path="E:\DefinitelyTyped\jquery\jquery.d.ts" />
/**
 * UI 组件层
 */
// 下拉列表组件初始化
$('select').selectbox();

// 时期选择框组件初始化
$('.input-date').each(function (index, element) {
  var fmt = $(element).hasClass('date-hms') ? 'yyyy-MM-dd HH:mm:ss' : 'yyyy-MM-dd';
  $(element).attr('onClick', 'WdatePicker({ \'dateFmt\': \'' + fmt + '\' })');
});

// 上传文件
$('.file-btn').click(function () {
  // 获取同胞file
  var file = $('.file-input', $(this).parents('.block'));
  // 模拟点击
  file.trigger('click');
});

// 下拉列表选中, 显示某个区域
// 注意由于用到了select插件, 真实的列表被隐藏了, 所以我修改了select插件
// todo 处理删除区域按钮
// todo 另一种下拉列表， 选中了对应下拉列表才显示对应区域，否则隐藏对应区域
/*$('.select-pop-trigger').each(function(i, element) {
  // 获取插件实例
  const exp = element.ExpSelect;
  if (!exp) {
    return;
  }
  // 监听改变事件
  exp.onClose = function() {
    // 选中的option元素
    const option = element.options[element.selectedIndex];
    // 获取需要显示ID
    const index = $(option).attr('data-select-Index');
    console.log();
    // 获取对应区域，将其显示
    const area = $(`[data-selectPop="${index}"]`);
    area.removeClass('hidden');
  };
});*/
// 下拉列表激活唯一区域
$('.select-pop-trigger').each(function (i, element) {
  // 获取插件实例
  var exp = element.ExpSelect;
  if (!exp) {
    return;
  }
  // 是否切换
  var isSwitch = $(element).attr('data-unique') || false;
  // 上一个激活区域
  var prevArea = undefined;
  // 监听改变事件
  exp.onClose = function () {
    if (prevArea && isSwitch) {
      prevArea.addClass('hidden');
    }
    // 选中的option元素
    var option = element.options[element.selectedIndex];
    // 获取需要显示ID
    var index = $(option).attr('data-select-Index');
    console.log();
    // 获取对应区域，将其显示
    var area = $('[data-selectPop="' + index + '"]');
    area.removeClass('hidden');
    prevArea = area;
  };
});

// 删除区域
$('.del-select-area').click(function () {
  $(this).parents('.select-pop-area').addClass('hidden');
});

// 文件控件改变
$('.file-input').bind('change', function () {
  // 获取同胞标签
  var lable = $('.file-text', $(this).parents('.block'));
  // 赋值
  lable.attr('value', this.value.replace(/c:\\fakepath\\/i, ''));
});

// 表单重置
$('.rest-btn').click(function () {
  // 获取要重置的表单
  var form = $(this).parents('.rest-from');
  // 获取表单下的 .rest-text, 重置文本框
  $('.rest-text', form).attr('value', '');
  // 获取表单下的 .rest-date 重置日期组件
  $('.rest-date', form).attr('value', '');
});

// 切换按钮
var switch_box = $('.swich');
// 开启按钮
$('.on', switch_box).click(function () {
  $(this).removeClass('active').siblings().addClass('active');
});
// 开启按钮
$('.off', switch_box).click(function () {
  $(this).removeClass('active').siblings().addClass('active');
});

// 表格checkbox
$('tr', $('.choice-more tbody')).click(function (event) {
  var element = $(this);
  if ($(event.target).attr('type') === 'checkbox') {
    return;
  }

  var activeClass = 'active';
  element.toggleClass(activeClass);

  var check = $('input[type="checkbox"]', this);
  check.attr('checked', !check.attr('checked'));
});

// 表格全选
$('.checkAll').click(function () {
  var table = $(this).parents('table');
  var status = $(this).attr('checked') || false;
  $('input[type="checkbox"]', table).not(this).attr('checked', status);
});

// html属性值转数组, 比如 [null, '.submit']
function ValueToArray(val) {
  var str = val;
  var result = [];
  // 去首尾括号
  str = str.slice(1, -1);
  // 通过 , 分割
  var arr = str.split(',');
  for (var i = 0; i < arr.length; ++i) {
    // 去除首尾空格
    var s = arr[i].replace(/(^\s*)|(\s*$)/g, '');
    // 去除单引号和者双引号
    s = s.replace(/\"/g, '');
    s = s.replace(/\'/g, '');
    result.push(s);
  }
  return result;
}

/**
 * 通用弹出框
 */
$('.pop-window').click(function () {
  var element = $(this);
  var wpop = window.WindowPop || window.top.WindowPop;
  if (wpop === undefined) {
    return;
  }
  var btn = new wpop(element.attr('data-title'), element.attr('data-url'), element.attr('data-mask') || true, element.attr('data-rename'), element.attr('data-link'));
  if (element.attr('data-close')) {
    btn.setFoot([{
      title: '关闭',
      reverse: false,
      close: true,
      event: function event() {
        btn.destroy();
      }
    }]);
  }
  // data-nofoot 属性隐藏页脚
  if (element.attr('data-nofoot')) {
    btn.DOM_foot.css({
      'display': 'none'
    });
  }
  if (element.attr('data-trigger')) {
    // 获取触发数组
    var triggers = ValueToArray(element.attr('data-trigger'));
    btn.bindTrigger(triggers || []);
  }
});

/**
 * 显示表单错误信息
 */
/*window.showInputError = function(box, message, time, callback) {
  const error_template = '<div class="verifi-text-error"><p>_message_</p></div>';
  const errpr = $(error_template.replace('_message_', message));
  // 显示错误提示
  $(box).parent().append(errpr);
  // time秒后隐藏错误
  errpr.animate({
    opacity: 0,
  }, time || 2500, () => {
    errpr.remove();
    if (callback) {
      callback();
    }
  });
};*/

window.showInputError = function (input, message) {
  var template = '<span class="verif-message">' + message + '</span>';
  var block = $(input).parents('.block');
  var verif_message = $('.verif-message', block);
  if (verif_message.length <= 0) {
    // 新建表单错误信息
    verif_message = $(template);
    // 插入DOM
    block.append(verif_message);
  } else {
    // 显示表单错误信息
    verif_message.text(message);
    verif_message.removeClass('hidden');
  }
};

/**
 * 通用正则验证, 验证失败返回 false
 */
window.VerifRegex = function (input) {
  var patten = input.attr('data-verifi');
  if (!patten) {
    return true;
  }
  var text = input.attr('value');
  var reg = new RegExp(patten);
  return reg.test(text);
};

/**
 * 表单总验证函数
 */
window.InputVerif = function (input) {
  var $input = $(input);
  // 验证是否必填
  if ($input.hasClass('verif-behoove') && $input.val() === '') {
    window.showInputError($input, '必填');
    return false;
  }
  // 通用正则验证
  if (!window.VerifRegex($input)) {
    $input.attr('value', '');
    window.showInputError($input, $input.attr('data-message') || '输入错误');
    return false;
  }
  return true;
};

/**
 * 通用正则验证
 */
$('.verif-trigger-onchange').change(function () {
  var block = $(this).parents('.block');
  if (window.InputVerif(this)) {
    // 验证成功, 隐藏验证消息
    $('.verif-message', block).addClass('hidden');
  }
});

/**
 * 提交按钮提交前验证
 */
$('.verif-btn').click(function (event) {
  // 获取容器
  var form = $(this).parents('.verif-form');
  // 错误计数
  var err_count = 0;
  // 获取所有输入表单
  $('.input-text', form).each(function (i, input) {
    if (!window.InputVerif(input)) {
      ++err_count;
    }
  });
  if (err_count !== 0) {
    // 表单验证失败, 阻止提交表单
    event.preventDefault();
  }
});

/**
 * 切换概要和详细区域
 */
$('.switch-area-btn').click(function () {
  var element = $(this);
  // 获取按钮描述数组
  var desc = ValueToArray(element.attr('data-desc'));
  // 当前状态
  var index = parseInt(element.attr('data-switch-index'));
  // 切换状态
  var status = index === 0 ? 1 : 0;
  // 改变当前状态和按钮名称
  element.attr('data-switch-index', status);
  element.text(desc[index]);
  $('.switch-area').addClass('hidden').eq(status).removeClass('hidden');
});

/**
 * 验证
 */
/* const verifi = {
  // 去首尾空格
  trim: function(str) {
    return str.replace(/(^\s*)|(\s*$)/g, '');
  },
};*/

// tab切换
var tabSwitch = function tabSwitch(tabBox, acclass) {
  // 上一个激活
  var prev = $('.' + acclass, tabBox);

  $('.tab', tabBox).click(function () {
    if (prev) {
      prev.removeClass(acclass);
    }
    prev = $(this).addClass(acclass);
    var index = prev.attr('data-index');
    $(index).addClass('on').siblings().removeClass('on');
  });
};

window.tabSwitch = tabSwitch;