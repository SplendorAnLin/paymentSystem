'use strict';

// / <reference path="E:\DefinitelyTyped\jquery\jquery.d.ts" />
/**
 * 获取最近7天日期
 */
var get_last_week = function get_last_week() {
  var now = new Date();
  var month = now.getMonth() + 1;
  var day = now.getDate();
  var result = [];

  for (var i = day - 7; i < day; ++i) {
    result.push(month + '-' + i);
  }
  return result;
};

/**
 * 获取最近一个月日期
 */
var get_last_month = function get_last_month() {
  var now = new Date();
  var month = now.getMonth() + 1;
  var day = now.getDate();
  var result = [];

  for (var i = day - 30; i < day; ++i) {
    if (i <= 0) {
      now.setMonth(month - 1);
      now.setDate(0);
      // 上一个月总共多少天
      // let prev_count = now.getDate();
      // 上个月份
      result.push(month - 1 + '-' + (now.getDate() + i));
      continue;
    }
    result.push(month + '-' + i);
  }
  return result;
};

/**
 * 是否为函数
 */
function isFunction(obj) {
  return Object.prototype.toString.call(obj) === '[object Function]';
}

/**
 * 切换图表
 */
var switch_chart = function switch_chart(config, chartTop, chart, defaultFilter, weekFuc, monthFuc, dayFuc, allFuc) {
  // 图表顶层容器
  var chartPrv = $(chartTop);
  // 上一个过滤器
  var last_filter = $(defaultFilter, chartPrv);
  // 过滤器激活样式
  var filter_class = 'active';
  // 过滤器哈希表
  var filter_table = {
    'week': weekFuc,
    'month': monthFuc,
    'day': dayFuc,
    'all': allFuc
  };

  // 事件处理模板
  var hander = function hander() {
    // 移除上一个过滤器样式
    if (last_filter) {
      last_filter.removeClass(filter_class);
    }
    // 设置当前过滤器
    last_filter = $(this);
    last_filter.addClass(filter_class);

    // 过滤器回调
    for (var key in filter_table) {
      if (last_filter.hasClass(key)) {
        if (isFunction(filter_table[key])) {
          config = filter_table[key](config);
        }
      }
    }

    // 绘制图表
    $(chart).highcharts(config);
  };

  // 过滤器事件绑定
  for (var key in filter_table) {
    $('.' + key, chartPrv).click(hander);
  }
  // 激活默认过滤器
  last_filter.trigger('click');
};

// 收支明细图表
var PayAndBay = function PayAndBay() {
  var weekF = function weekF(config) {
    config.xAxis.categories = get_last_week();
    config.series = [{
      name: '收入',
      color: '#03a9f4',
      data: [33, 41, 24, 20, 17, 10, 25]
    }, {
      name: '支出',
      color: '#f1c40f',
      data: [5, 21, 44, 10, 27, 10, 35]
    }];

    return config;
  };

  var monthF = function monthF(config) {
    config.xAxis.categories = get_last_month();
    config.series = [{
      name: '收入',
      color: '#03a9f4',
      data: [62, 117, 120, 118, 110, 117, 29, 8, 17, 18, 19, 62, 19, 87, 80, 90, 100, 31, 32, 32, 26, 17, 50, 33, 41, 24, 20, 17, 10, 25]
    }, {
      name: '支出',
      color: '#f1c40f',
      data: [33, 117, 120, 118, 110, 15, 29, 8, 17, 18, 19, 62, 19, 66, 80, 90, 100, 31, 32, 32, 26, 17, 50, 33, 41, 24, 20, 17, 10, 25]
    }];
    return config;
  };

  var config = {
    title: {
      text: null
    },
    xAxis: {
      categories: []
    },
    yAxis: {
      title: {
        text: null
      },
      labels: {
        formatter: function formatter() {
          return this.value + '\u5143';
        }
      }
    },
    tooltip: {
      formatter: function formatter() {
        var s = '<b>' + this.x + '</b>\'';
        var valueSuffix = '元';
        s += '<br/>' + this.series.name + ': ' + (this.y + valueSuffix);
        return s;
      }
    },
    legend: {
      enabled: true
    },
    credits: {
      enabled: false
    },
    series: [],
    chart: {
      type: 'spline'
    }
  };

  switch_chart(config, '#pay-and-bay-chart', '#pay-and-bay-chart-container', '.week', weekF, monthF);
};

// 经营分析图表
var Analysis = function Analysis() {
  var weekF = function weekF(config) {
    config.xAxis.categories = get_last_week();
    config.series = [{
      name: '收款金额',
      color: '#03a9f4',
      data: [100, 150, 300, 600, 0, 500, 600]
    }, {
      name: '结算金额',
      color: '#f1c40f',
      data: [50, 300, 500, 733, 255, 30, 10]
    }];

    return config;
  };

  var monthF = function monthF(config) {
    config.xAxis.categories = get_last_month();
    config.series = [{
      name: '收入',
      color: '#03a9f4',
      data: [62, 117, 120, 118, 110, 117, 29, 8, 17, 18, 19, 62, 19, 87, 80, 90, 100, 31, 32, 32, 26, 17, 50, 33, 41, 24, 20, 17, 10, 25]
    }, {
      name: '支出',
      color: '#f1c40f',
      data: [33, 117, 120, 118, 110, 15, 29, 8, 17, 18, 19, 62, 19, 66, 80, 90, 100, 31, 32, 32, 26, 17, 50, 33, 41, 24, 20, 17, 10, 25]
    }];
    return config;
  };

  var config = {
    title: {
      text: null
    },
    xAxis: {
      categories: ['10-10']
    },
    yAxis: {
      title: {
        text: null
      },
      plotLines: [{
        value: 0,
        width: 1,
        color: '#808080'
      }],
      labels: {
        formatter: function formatter() {
          return this.value + '\u5143';
        }
      }
    },
    tooltip: {
      formatter: function formatter() {
        return '<b>' + this.x + '</b><br/>' + this.series.name + ': ' + this.y + '\u5143<br/>';
      }
    },
    legend: {
      enabled: true
    },
    credits: {
      enabled: false
    },
    series: [{
      name: '发送量',
      data: [10]
    }],
    chart: {
      type: 'column'
    }
  };

  switch_chart(config, '#analysis-chart', '#analysis-chart-container', '.week', weekF, monthF);
};

// 订单转换率图表
var Conver = function Conver() {
  var weekF = function weekF(config) {
    config.series = [{
      type: 'pie',
      name: '订单转换率',
      data: [{
        name: '成功',
        y: 40,
        sliced: true,
        selected: true
      }, {
        name: '失败',
        y: 60,
        color: '#f1c40f'
      }]
    }];
    return config;
  };

  var monthF = function monthF(config) {
    config.series = [{
      type: 'pie',
      name: '订单转换率',
      data: [{
        name: '成功',
        y: 60,
        sliced: true,
        selected: true
      }, {
        name: '失败',
        y: 40,
        color: '#f1c40f'
      }]
    }];
    return config;
  };

  var dayF = function dayF(config) {
    config.series = [{
      type: 'pie',
      name: '订单转换率',
      data: [{
        name: '成功',
        y: 20,
        sliced: true,
        selected: true
      }, {
        name: '失败',
        y: 80,
        color: '#f1c40f'
      }]
    }];
    return config;
  };

  var allF = function allF(config) {
    config.series = [{
      type: 'pie',
      name: '订单转换率',
      data: [{
        name: '成功',
        y: 33,
        sliced: true,
        selected: true
      }, {
        name: '失败',
        y: 67,
        color: '#f1c40f'
      }]
    }];
    return config;
  };

  var config = {
    title: {
      text: null
    },
    tooltip: {
      pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
    },
    plotOptions: {
      pie: {
        allowPointSelect: true,
        cursor: 'pointer',
        dataLabels: {
          enabled: true,
          format: '<b>{point.name}</b>: {point.percentage:.1f} %',
          style: {
            color: window.Highcharts.theme && window.Highcharts.theme.contrastTextColor || 'black'
          }
        }
      }
    },
    legend: {
      enabled: true
    },
    credits: {
      enabled: false
    },
    series: [],
    chart: {
      plotBackgroundColor: null,
      plotBorderWidth: null,
      plotShadow: false
    }
  };

  switch_chart(config, '#conver-chart', '#conver-chart-container', '.day', weekF, monthF, dayF, allF);
};

PayAndBay();
Analysis();
Conver();