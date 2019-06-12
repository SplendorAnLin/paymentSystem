
var alertModal = null;

// 增加一种提示方式, 消息框提醒
$.addPrompt('alert', function(element, status, msg) {
  if (alertModal !== null || status === true || status === 'wait') {
    return;
  }
  
  

  
  
  // 寻找label
  var label = element.parents('.input-row').find('label').text();
  var showmsg = label + ' ' + msg;
  
  
  if (element.attr('id') == 'agree') {
	  showmsg = "请先同意用户协议!";
  }
  
  alertModal = new Alert(showmsg, '验证失败', function() {
    alertModal = null;
  });

});



/**
 * 全局组件配置
 */
window.config = function() {
  // 表单验证配置
  if (window.Validator) {
    // 默认提示方式
    Validator.Prompt = 'alert';
  }
};
window.config();

// 默认表单验证
$('.validator').each(function() {
  var form = $(this);
  // 生成trigger树
  var triggers = {};
  $('[trigger]', form).each(function() {
    var element = $(this);
    var name = element.attr('name');
    var jsonString = element.attr('trigger');
    if (!name || !jsonString) {
      return;
    }
    var trigger = window.toJson(jsonString);
    triggers[name] = trigger;
  });
  form.Validator({
	justWhenSubmit: true,
    trigger: triggers
  });
});




/**
 * 手机验证码
 */
(function() {
  var times = {};
  var codes = {};

  var VerificaCode = {
    send: function(url, process, end, time) {
      if (VerificaCode.exist(url) || !url)
        return false;

      time = time || 60;
      var timeId = timing(time, process, function() {
        VerificaCode.remove(url);
        end();
      });
      VerificaCode.add(url, timeId, 0);

      $.ajax({
        "url": url,
        "async": true,
        "type": "post",
        "success": function(code) {
/*          if (!code) {
            VerificaCode.remove(url);
            console.warn('Ajax返回结果为空,', url);
            return;
          }*/
          VerificaCode.add(url, timeId, code); 
        },
        "error": function() {
          VerificaCode.remove(url);
          console.warn('Ajax请求失败: %s', url);
        }
      });
    },
    get: function(url) {
      return codes[url];
    },
    exist: function(url) {
      return times.hasOwnProperty(url);
    },
    add: function(url, timeId, code) {
      times[url] = timeId;
      codes[url] = code;
    },
    remove: function(url) {
      clearInterval(times[url]);
      delete times[url];
    }
  };

  /**
   * 倒计时
   * @param {Number} time 倒计时秒数
   * @param {Function} process  每秒回调
   * @param {Function} end      完毕回调
   */
  function timing(time, process, end) {
    var interval = 1000;
    var count = 0;
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
  }

  window.VerificaCode = VerificaCode;

})();






