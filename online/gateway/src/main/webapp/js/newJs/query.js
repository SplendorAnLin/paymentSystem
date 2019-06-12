'use strict';

/// <reference path="E:\DefinitelyTyped\jquery\jquery.d.ts" />
{
  (function () {
    var _window$top = window.top,
        ResultPop = _window$top.ResultPop,
        WindowPop = _window$top.WindowPop;

    /**
     * 通用弹出框
     */

    $('.pop-window').click(function (event) {
      var e = $(this);
      new WindowPop(e.attr('data-title'), e.attr('data-url'), true);
    });

    /**
     * 通用弹出结果框
     */
    $('.pop-result').click(function () {
      new ResultPop($(this).attr('data-url'), true);
    });

    // 切换按钮
    var switch_box = $('.swich');
    // 开启按钮
    $('.on', switch_box).click(function (event) {
      $(this).removeClass('active').siblings().addClass('active');
    });
    // 开启按钮
    $('.off', switch_box).click(function (event) {
      $(this).removeClass('active').siblings().addClass('active');
    });

    // 上传文件
    $('.file-btn').click(function (event) {

      // 获取同胞file
      var _file = $('.file-input', $(this).parent());
      // 获取同胞标签
      var lable = $('.file-text', $(this).parent());

      _file.bind('change', function () {
        lable.attr('value', $(this).attr('value'));
      });
      // 模拟点击
      _file.trigger('click');
    });

    // 表格checkbox
    $('tr', $('.choice-more tbody')).click(function (event) {

      var e = $(this);
      var table = $(this).parents('table');

      if ($(event.target).attr('type') === 'checkbox') {
        return;
      }

      var activeClass = 'active';
      e.toggleClass(activeClass);

      var check = $('input[type="checkbox"]', this);
      check.attr('checked', !check.attr('checked'));
    });

    // 表格全选
    $('.checkAll').click(function () {
      var table = $(this).parents('table');
      $('input[type="checkbox"]', table).attr('checked', $(this).attr('checked'));
    });
  })();
}