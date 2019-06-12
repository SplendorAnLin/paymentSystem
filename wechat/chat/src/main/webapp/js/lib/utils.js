/**
 * 工具类
 */
(function() {
  var debug = true;
  var utils = {
    // 字符串模板替换
    tpl: function(tpls, data) {
      return tpls.replace(/{{(.*?)}}/g, function($1, $2) {
        return data[$2] === undefined ? $1 : data[$2];
      });
    },
    // 字符串模板替换, 属性替换, 比如disabled属性, true就添加,false就移除
    tplForAttr: function(tpls, data) {
      return tpls.replace(/{{(.*?)}}/g, function($1, $2) {
        return data[$2] ? $1.replace(/({{)|(}})/g, '') : '';
      });
    },
    // 是否为数组
    isArray: function(obj) {
      return Object.prototype.toString.call(obj) === '[object Array]';
    },
    // 加载iframe
    loadIframe: function(iframe, url, callback) {
      // 参数检查
      if (!iframe || iframe.length < 1 || !url)
        return;
      iframe.attr('src', url);
      utils.listenIframe(iframe, callback);
    },
    // 监听iframe
    listenIframe: function(iframe, callback) {
      // 参数检查
      if (!iframe || iframe.length < 1)
        return;
      var url = iframe.attr('src');
      // 标志, 是否加载
      var status = null;
      // 普通load事件
      setTimeout(function() {
        
        // 处理error事件
        iframe.error(function() {
          status = false;
          console.warn('utils.loadIframe: 加载iframe失败,', url);
          callback(status);
        });

        // 处理load事件
        iframe.load(function() {
          try {
            // 判断框架内是否为空/或者有异常模板
            var count = iframe.contents().find('head').children().length;
            var errorExpression = iframe.contents().find('h2.demoHeaders').text() == ('Exception / '+'Error');
            status = (count !== 0 && !errorExpression);
          } catch(err) {
            status = false;
            console.warn('utils.loadIframe: 加载iframe事件, 网络异常或跨域!', url);
          }
          callback(status);
        });
      }, 10);

      // 处理跨域或网络异常事件
      setTimeout(function() {
        if (status !== null)
          return;
        try {
          // IE下接异常来判断是否加载完毕
          iframe[0].contentWindow.document;
        } catch (error) {
          status = false;
          callback(status);
          console.warn('utils.loadIframe: 加载iframe事件, 网络异常或跨域!', url);
        }
      }, 100);
    },
    // 获取iframe内元素
    iframeFind: function(iframe, selector) {
      return iframe.contents().find(selector);
    },
    // 是否为iphone设备
    isIphone: function() {
      var UA = navigator.userAgent;
      return (UA.match(/iPad/) || UA.match(/iPhone/) || UA.match(/iPod/));
    },
    // 格式化日期
    // formatTime(new Date(), 'yyyy-MM-dd 23:59:59')
    // formatTime(new Date(), 'yyyy-MM-dd HH:mm:ss')
    formatTime: function(time, format) {
      var t = new Date(time);
      var tf = function(i){return (i < 10 ? '0' : '') + i};
      return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
          switch(a){
              case 'yyyy':
                  return tf(t.getFullYear());
              case 'MM':
                  return tf(t.getMonth() + 1);
              case 'mm':
                  return tf(t.getMinutes());
              case 'dd':
                  return tf(t.getDate());
              case 'HH':
                  return tf(t.getHours());
              case 'ss':
                  return tf(t.getSeconds());
          }
      })
    },
    // 封装ajax, 实现异常检查
    ajax: function(config, printException) {
      // 参数检查
      if (!config ||  !config.url) {
        console.warn('utils.ajax: 参数错误, ajax配置参数不正确!');
        return false;
      }

      $.ajax({
        url: config.url,
        async: config.async,
        cache: config.cache,
        data: config.data,
        type: config.type,
        dataType: config.dataType,
        error: function() {
          if (!debug)
            console.warn('utils.ajax: 请求失败, url:', config.url,' data:', config.data);
          if ($.isFunction(config.error))
            config.error();
        },
        success: function(data) {
          // 返回参数异常检查
          if (!data || data.indexOf('Exception / '+'Error') !== -1) {
            console.warn('utils.ajax: 服务器返回空或异常!, url:', config.url,' data:', config.data);
            // 打印异常信息
            if (printException)
              console.log(data);
            return;
          }
          if ($.isFunction(config.success)) {
            config.success(data);
          }
        }
      });




    },
    // 获取窗口剩余高度
    getAvailabHeight: function(element) {
      return ($(window).height() + $(window).scrollTop()) - (element.offset().top + element.outerHeight());
    },
    // 加载图片
    loadImage: function(url, callback) {
      var img = new Image();
      var count = 0;
      img.src = url;
      if (img.complete) {
        callback(img, true);
        return;
      }
      img.onload = function() {
        if (count !== 0) {
          return;
        }
        callback(img, true);
        ++count;
      };
      img.onerror = function() {
        callback(img, false);
      };
    },
    /**
     * 倒计时
     * @param {Number} time 倒计时秒数 (默认60秒)
     * @param {Function} process  每秒回调
     * @param {Function} end      完毕回调
     */
    timing: function(time, process, end) {
      var interval = 1000;
      var count = 0;
      time = time || 60;
      var timeId = setInterval(function() {
        if (count >= time) {
          clearInterval(timeId);
          if ($.isFunction(end)) {
            end();
          }
        } else {
          ++count;
          if ($.isFunction(process)) {
            process(time - count);
          }
        }
      }, interval);
      if ($.isFunction(process)) {
        process(time);
      }
      return timeId;
    },

    /**
     * 找到元素在数组中的索引, 未找到返回-1
     */
    indexOf: function(array, val) {
      var index = -1;
      for (var i = 0; i < array.length; ++i) {
        if ($.isFunction(val) && val(array[i], i)) {
          index = i;
          break;
        } else if (array[i] === val) {
          index = i;
          break;
        }
      }
      return index;
    },
    /**
     * 数组遍历
     */
    each: function(array, callback) {
      for (var i = 0; i < array.length; ++i) {
        callback(array[i], i);
      }
    },
  };
  // 导出
  window.utils = utils;
})();