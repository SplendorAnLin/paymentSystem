// 图表全局配置
Highcharts.setOptions({
  lang: {
    noData: '暂无数据'
  }
});

// 图表封装实现
(function() {
  // 自定义时间切换
  var custom_chart = function(chartTop, chartConfigFuc) {
    // 图表顶层容器
    var chartContainer = $(chartTop);
    // 图表区域
    var draw = $('.chart-draw', chartContainer);
    // 日期元素
    var dateElement = $('.filter-date', chartContainer);
    // 日期图表
    var dateIco = $('.date-ico', chartContainer);
    // 激活样式
    var ACTIVE = 'active';
    // 上一次激活元素
    var last = $('.' + ACTIVE, chartContainer);

    var loading = {
      labelStyle: {
        fontStyle: 'italic',
        textAlign: 'center',
        'position': 'relative',
        'top': '-10px',
        'font-size': '12px',
        'color': 'black',
      },
      style: {
        'position': 'absolute',
        'opacity': 1,
        'textAlign': 'center',
        'background': 'rgba(255,255,255,0.5) url(' + (window.contextPath || '.') + '/images/loading.gif) center center no-repeat',
      },
    };
 
    // 图表对象
    var chart = new window.Highcharts.Chart(draw[0], {
      loading: loading,
      title: null,
      series: [],
    });

    // 监听切换元素
    var items = $('[data-filter]', chartContainer).click(function() {
      switch_filter($(this));
    });

    draw.data('flush', function(attrConfig) {
    	attrConfig.loading = loading;
      // 获取完毕, 绘制图表
      chart = new window.Highcharts.Chart(draw[0], attrConfig);
    });
    
    dateIco.click(function() {
      window.WdatePicker({
        el: dateElement[0],
        onpicked: function(dp) {
          dateIco.attr('data-filter', dp.cal.getNewDateStr());
          switch_filter(dateIco);
        },
      });
    });

    // 切换图表
    function switch_filter(item) {
      if (last) {
        last.removeClass(ACTIVE);
      }
      last = item.addClass(ACTIVE);
      var data = item.attr('data-filter');
      // 获取图表数据
      chartConfigFuc(data, function(config) {
        config.loading = loading;
        // 获取完毕, 绘制图表
        chart = new window.Highcharts.Chart(draw[0], config);
      }, chart);
    }

    // 默认图表
    switch_filter(last);
  };
  window.custom_chart = custom_chart;
})();


// welcome页查询
(function() {
  function getArrayByKey(array, key) {
    var result = [];
    $.each(array, function() {
      if (this[key] !== undefined) {
        result.push(this[key]);
      }
    });
    return result;
  }
  var colors = {
    "ALIPAY": "#8bc34a",
    "AUTHPAY": "#009688",
    "B2B": "#00bcd4",
    "B2C": "#03a9f4",
    "RECEIVE": "#03a9f4",
    "REMIT": "#4caf50",
    "WXJSAPI": "#3f1b55",
    "WXNATIVE": "#9c27b0",
    "WXMICROPAY": "#9c27b0",
    "POS": "#297cbb",
    "MPOS": "#297ccc",
    "ALIPAYMICROPAY": "#8BC34A",
    "QUICKPAY": "#4caf50"
  };
  var names = {
    "ALIPAY": "支付宝",
    "AUTHPAY": "认证支付",
    "B2B": "企业网银",
    "B2C": "个人网银",
    "RECEIVE": "代收",
    "REMIT": "代付",
    "WXJSAPI": "微信公众号支付",
    "WXNATIVE": "微信二维码支付",
    "WXMICROPAY": "微信条码",
    "POS": "POS",
    "MPOS": "MPOS",
    "ALIPAYMICROPAY": "支付宝条码",
    "QUICKPAY": "快捷支付"
  };
  var indexs = {
    "ALIPAY": 6,
    "AUTHPAY": 2,
    "B2B": 1,
    "B2C": 0,
    "RECEIVE": 7,
    "REMIT": 3,
    "WXJSAPI": 4,
    "WXNATIVE": 5,
    "WXMICROPAY": 8,
    "POS": 9,
    "MPOS": 10,
    "ALIPAYMICROPAY": 12,
    "QUICKPAY": 11
  };


  // 经营分析数据配置
  function analysisConfig(array) {
    $.each(array, function() {
      this['y'] = this.amount;
      this['name'] = this.productName;
      this['color'] = colors[this.paytype];
    });
    return array;
  }

  // 经营分析数据排序
  function analysisSort(array) {
    var result = [1,1,1,1,1,1,1,1];
    $.each(array, function() {	
      var index = indexs[this.payType];
      result[index] = this;
    });
    return result;
  }

  // 获取经营分析X轴
  function analysisCategories(array) {
      var result = [];
      $.each(array, function() {
          result.push(this.productName);
      });
      return result;
  }


  // 检测是否为空
  function customEmpy(array) {
    var empyCount = 0;
    $.each(array, function() {
      if (this.count == 0) {
        ++empyCount;
      }
    });
    return empyCount == array.length;
  }

  // 分析率 [金额] 与 [笔数] 图标切换
	var lastAllConfig = null;
	var draw = $('#analysis-conver-chart-container');
	$('.toggle-span').click(function() {
		 if ($(this).hasClass('amount')) {
			 toogleAmount(lastAllConfig.series[0]);
		 } else {
			 toogleCount(lastAllConfig.series[0]);
		 }
		 draw.data('flush')(lastAllConfig);
	});
  function toogleAmount(config) {
    config['name'] = '分析率(金额)';
    $('.toggle-span.count').removeClass('active');
    $('.toggle-span.amount').addClass('active');
    $.each(config['data'], function() {
      this.y = this.amount;
    });
  }
  function toogleCount(config) {
    config['name'] = '分析率(笔数)';
    $('.toggle-span.count').addClass('active');
    $('.toggle-span.amount').removeClass('active');
    $.each(config['data'], function() {
      this.y = this.count;
    });
  }

  // 收支明细
  var cache = [0,0];
  custom_chart('#pay-and-bay-chart', function(data, fuc, chart) {
      // 实际ajax获取数据
      var config = {
        title: {
          text: null,
        },
        xAxis: {
          categories: null,
        },
        yAxis: {
          title: {
            text: null,
          },
          labels: {
            formatter: function() {
              return this.value + '元';
            },
          },
        },
        tooltip: {
          formatter: function(a, b) {
            var s = '<b>' + this.x + '</b>';
            var valueSuffix = '元';
            s += '<br/>' + this.series.name + ': ' + this.y + valueSuffix + '<br/>笔数：' + cache[this.series.index][this.point.index].count;
            return s;
          },
        },
        legend: {
          enabled: true,
        },
        credits: {
          enabled: false,
        },
        series: [],
        chart: {
          type: 'spline',
        },
      };
      chart.showLoading();
      $.ajax({
      url : "initRD.action",
      type : "post",
      data : "initSumFlag=" + data,
      dataType: 'json',
      success: function(args) {
        cache[0] = args['in'];
        cache[1] = args['out'];
        config.series = [
            {
            "name": "收入",
            "color": "#03a9f4",
            "data": getArrayByKey(args['in'], 'amount')
          },
            {
            "name": "支出",
            "color": "#f1c40f",
            "data": getArrayByKey(args['out'], 'amount')
          }
        ];
        config.xAxis.categories = getArrayByKey(args['in'], 'times');
        chart.hideLoading();
        fuc(config);
      },
      error: function() {
        // 服务器返回格式
        // var args = {
        //   'in': [
        //     {"amount": 0, "count": '0', "times": "20170206"},
        //     {"amount": 0, "count": '0', "times": "20170207"},
        //     {"amount": 0, "count": '0', "times": "20170208"},
        //     {"amount": 400, "count": '40', "times": "20170209"},
        //     {"amount": 0, "count": '0', "times": "20170210"},
        //     {"amount": 200, "count": '20', "times": "20170211"},
        //     {"amount": 0, "count": '支付宝', "times": "20170212"}
        //   ],
        //   'out': [
        //     {"amount": 0, "count": '0', "times": "20170206"},
        //     {"amount": 200, "count": '20', "times": "20170207"},
        //     {"amount": 0, "count": '0', "times": "20170208"},
        //     {"amount": 100, "count": '10', "times": "20170209"},
        //     {"amount": 50, "count": '5', "times": "20170210"},
        //     {"amount": 0, "count": '0', "times": "20170211"},
        //     {"amount": 0, "count": '0', "times": "20170212"}
        //   ]
        // };

        // 测试-临时模拟数据===============================
        var args = {
          'in': [
            {"amount": 0, "count": '0', "times": "20170206"},
            {"amount": 0, "count": '0', "times": "20170207"},
            {"amount": 0, "count": '0', "times": "20170208"},
            {"amount": 400, "count": '40', "times": "20170209"},
            {"amount": 0, "count": '0', "times": "20170210"},
            {"amount": 200, "count": '20', "times": "20170211"},
            {"amount": 0, "count": '支付宝', "times": "20170212"}
          ],
          'out': [
            {"amount": 0, "count": '0', "times": "20170206"},
            {"amount": 200, "count": '20', "times": "20170207"},
            {"amount": 0, "count": '0', "times": "20170208"},
            {"amount": 100, "count": '10', "times": "20170209"},
            {"amount": 50, "count": '5', "times": "20170210"},
            {"amount": 0, "count": '0', "times": "20170211"},
            {"amount": 0, "count": '0', "times": "20170212"}
          ]
        };
        cache[0] = args['in'];
        cache[1] = args['out'];
        config.series = [
            {
            "name": "收入",
            "color": "#03a9f4",
            "data": getArrayByKey(args['in'], 'amount')
          },
            {
            "name": "支出",
            "color": "#f1c40f",
            "data": getArrayByKey(args['out'], 'amount')
          }
        ];
        config.xAxis.categories = getArrayByKey(args['in'], 'times');
        // 测试-临时模拟数据===============================


        chart.hideLoading();
        fuc(config);
        //console.warn('收支明细ajax错误');
      }
      });
      
    });
  


  // 经营分析 
  custom_chart('#analysis-chart', function(data, fuc, chart) {
      // 实际ajax获取数据
    var config = {
      title: {
        text: null,
      },
      xAxis: {
        categories: [],
      },
      plotOptions: {
        series: {
          minPointLength: 3
        }
      },
      yAxis: {
        title: {
          text: null,
        },
        plotLines: [{
          value: 0,
          width: 1,
        }],
        labels: {
          formatter: function() {
            return this.value + '元';
          },
        },
      },
      tooltip: {
        formatter: function() {
          return '<b>' + this.point.name + '</b><br/>交易金额: ' + this.y + '元<br/><p>交易笔数: ' + this.point.count + '</p>';
        },
      },
      legend: {
        enabled: false,
      },
      credits: {
        enabled: false,
      },
      series: [{
        data: [],
      }],
      chart: {
        type: 'column',
      }
    };
    chart.showLoading();
      $.ajax({
      url : "initDayDate.action",
      type : "post",
      data : "initSumFlag=" + data,
      dataType: 'json',
      success: function(args) {
          var productInfo = analysisConfig(args);
          config.xAxis.categories = analysisCategories(productInfo);
        config.series = [{
          "data": productInfo
        }];
        chart.hideLoading();
        fuc(config);
      },
      error: function() {
        // 服务器返回格式
        // [
        //  {amount: 0, count: 0, paytype: 'ALIPAY'},
        //  {amount: 0, count: 0, paytype: 'AUTHPAY'},
        //  {amount: 200, count: 50, paytype: 'B2B'},
        //  {amount: 0, count: 0, paytype: 'B2C'},
        //  {amount: 0, count: 0, paytype: 'RECEIVE'},
        //  {amount: 50, count: 5, paytype: 'REMIT'},
        //  {amount: 0, count: 0, paytype: 'WXJSAPI'},
        //  {amount: 0, count: 0, paytype: 'WXNATIVE'}
        // ]
        // 处理后格式
        // {amount: 0, name: '支付宝', paytype: 'ALIPAY', color: '#8bc34a', count: 0, y: 0}

        // 测试-临时模拟数据===============================
        var args = [
          {amount: 0, count: 0, paytype: 'ALIPAY'},
          {amount: 0, count: 0, paytype: 'AUTHPAY'},
          {amount: 200, count: 50, paytype: 'B2B'},
          {amount: 0, count: 0, paytype: 'B2C'},
          {amount: 0, count: 0, paytype: 'RECEIVE'},
          {amount: 50, count: 5, paytype: 'REMIT'},
          {amount: 0, count: 0, paytype: 'WXJSAPI'},
          {amount: 0, count: 0, paytype: 'WXNATIVE'}
        ];
        config.series = [{
          "data": analysisSort(analysisConfig(args))
        }];
        // 测试-临时模拟数据===============================



        chart.hideLoading();
        fuc(config);
        //console.warn('经营分析 ajax错误');
      }
      });
      
    });


  // 订单转换率
  custom_chart('#conver-chart', function(data, fuc, chart) {
    var zero = true;
      // 实际ajax获取数据
    var config = {
      title: {
        text: null
      },
      tooltip: {
        formatter: function() {
          var value = this.point.percentage + '';
          var index = value.indexOf('.');
          if (index != -1) {
            var v = value[index + 2];
            value = value.slice(0, index + 2);
            if (v >= 5) {
              value = value.replace(/\d$/, function(z) {
                return (Number(z) + 1);
              });
            }
          }
          return this.point.name + ': <b>' + value + '%</b><br/>金额: <b>' + this.point.amount + '</b><br/>笔数: <b>' +  (zero ? 0 : this.point.y)  + '</b>';
        }
      },
      plotOptions: {
        pie: {
          allowPointSelect: false,
          cursor: 'pointer',
          minSize: 80,
          dataLabels: {
            enabled: true,
            format: '<b>{point.name}</b>: {point.percentage:.1f} %',
            crop: false,
            style: {
              color: 'black',
              width: '100px'
            },
          },
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
    chart.showLoading();
      $.ajax({
      url : "initOrder.action",
      type : "post",
      data : "initSumFlag=" + data,
      dataType: 'json',
      success: function(args) {
        chart.hideLoading();
        zero = args['success']['count'] == 0 && args['error']['count'] == 0;
        config.series = [
            {
                  "type": "pie",
                  "name": "订单转换率",
                  "data": [
                          {
                            "name": "成功",
                            "y": zero ? 100 : args['success']['count'],
                            "amount": args['success']['amount'],
                            "color": "rgb(3, 169, 244)",
                            "sliced": true,
                            "selected": true
                          },
                          {
                            "name": "失败",
                            "y": zero ? 0 : args['error']['count'],
                            "amount": args['error']['amount'],
                            "color": "rgb(241, 196, 15)"
                          }
                        ]
          }
        ];
        
        fuc(config);
      },
      error: function() {

        // 测试-临时模拟数据===============================
        var args = {
          'success': {
            'count': 50,
            'amount': 100
          },
          'error': {
            'count': 10,
            'amount': 20
          },
        };
        zero = args['success']['count'] == 0 && args['error']['count'] == 0;
        config.series = [
          {
            "type": "pie",
            "name": "订单转换率",
            "data": [
              {
                "name": "成功",
                "y": zero ? 100 : args['success']['count'],
                "amount": args['success']['amount'],
                "color": "rgb(3, 169, 244)",
                "sliced": true,
                "selected": true
              },
              {
                "name": "失败",
                "y": zero ? 0 : args['error']['count'],
                "amount": args['error']['amount'],
                "color": "rgb(241, 196, 15)"
              }
            ]
          }
        ];
        // 测试-临时模拟数据===============================


        chart.hideLoading();
        fuc(config);
        //console.warn('订单转换率ajax错误');
      }
      });
      
    });


  // 分析率
  custom_chart('#analysis-conver-chart', function(data, fuc, chart) {
    // 实际ajax获取数据
    var config = {
      title: {
        text: null,
      },
      tooltip: {
        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>',
      },
      plotOptions: {
        pie: {
          allowPointSelect: false,
          cursor: 'pointer',
          "minSize": 200,
          dataLabels: {
            enabled: true,
            format: '<b>{point.name}</b>: {point.percentage:.1f} %',
            crop: false,
            style: {
              color: 'black',
              width: '100px'
            },
          },
        },
      },
      legend: {
        enabled: true,
      },
      credits: {
        enabled: false,
      },
      series: [{
        "type": "pie",
        "name": "分析率(金额)",
        "minSize": 200,
        "data": [],
      }],
      chart: {
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
      },
    };
    lastAllConfig = config;
    chart.showLoading();
      $.ajax({
      url : "initDayDate.action",
      type : "post",
      data : "initSumFlag=" + data,
      dataType: 'json',
      success: function(args) {
        var isempy = customEmpy(args);
        if (!isempy) {
          config.series[0]['data'] = analysisConfig(args);
          toogleAmount(config.series[0]);
        }
        lastAllConfig = config;
        chart.hideLoading();
        fuc(config);
      },
      error: function() {

        // 测试-临时模拟数据===============================
        var args = [
          {amount: 100, count: 15, paytype: 'ALIPAY'},
          {amount: 50, count: 22, paytype: 'AUTHPAY'},
          {amount: 200, count: 50, paytype: 'B2B'},
          {amount: 30, count: 55, paytype: 'B2C'},
          {amount: 40, count: 20, paytype: 'RECEIVE'},
          {amount: 77, count: 27, paytype: 'REMIT'},
          {amount: 65, count: 38, paytype: 'WXJSAPI'},
          {amount: 150, count: 52, paytype: 'WXNATIVE'}
        ];
        var isempy = customEmpy(args);
        if (!isempy) {
          config.series[0]['data'] = analysisConfig(args);
          toogleAmount(config.series[0]);
        }
        lastAllConfig = config;
        // 测试-临时模拟数据===============================






        chart.hideLoading();
        fuc(config);
        //console.warn('分析率 ajax错误');
      }
      });
      
    });
})();