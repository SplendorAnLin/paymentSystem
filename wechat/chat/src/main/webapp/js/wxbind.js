(function() {

  var verification = {
    // 手机号码
    phone: function(val) {
      var mobile = /^(13[0-9]|15[0123456789]|17[03678]|18[0-9]|14[57])[0-9]{8}$/;
      return val.length === 11 && mobile.test(val);
    }
  };

window.verification = verification;


})();

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
        "type": "post",
        "dataType": "json",
        "success": function(code) {
        		if (!code) {
                    VerificaCode.remove(url);
                    console.warn('Ajax返回结果为空,', url);
                    return;
                  }
                  VerificaCode.add(url, timeId, code); 
        },
        "error": function() {
          VerificaCode.remove(url);
          console.warn('Ajax请求失败: %s', url);
        }
      });
    },
    get: function(url) {
    	console.log(url);
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
