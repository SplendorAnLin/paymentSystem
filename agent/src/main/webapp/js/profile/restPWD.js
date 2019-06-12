(function() {

  var utils = {
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
    // 显示提示
    showTips: function(type, text) {
      var typeClass = type == 1 ? 'wran' : 'error';
      $('.tiptext-message').text(text);
      $('.tiptext').attr('data-type', typeClass).show();
    },
    // 隐藏提示
    hideTips: function() {
      $('.tiptext').hide();
    },
    // 显示手机号码提示
    showPhoneTips: function(text) {
      $('#phone-tips').text(text).show();
    },
    // 隐藏手机号码提示
    hidePhoneTips: function() {
      $('#phone-tips').hide();
    },
    // 检测手机号是否输入正确
    checkPhone: function(phone) {
      var mobile = /^(13[0-9]|15[0123456789]|17[03678]|18[0-9]|14[57])[0-9]{8}$/;
      return phone.length === 11 && mobile.test(phone);
    },
    // 检测验证码是否填写
    checkCode: function() {
      var code = $('[name="code"]').val();
      return code.length == 4;
    }
  };

  var phoneStatus = false;
  var codeStatus = false;
  // 初始化绑定事件
  var bind = function() {
    var last = '';
    $('#phone').blur(function() {
      if (this.value == last)
        return;
      if (!utils.checkPhone(this.value)) {
        utils.showPhoneTips('无效的手机号!');
        return;
      } else {
        utils.hideTips();
      }
      phoneStatus = true;
      $.ajax({
        url: 'operVerifymsg.action?phone=' + this.value,
        error: function() {
          utils.showPhoneTips('检测手机号失败!');
        },
        success: function(msg) {
          if (msg == 'false') {
            phoneStatus = true;
            utils.hidePhoneTips();
          } else {
            utils.showPhoneTips('此手机号不存在系统中!');
          }
        }
      });

      last = this.value;
    });

    $('#send-verif').click(function() {
      if (phoneStatus == false) {
        utils.showTips(2, '请先输入合法手机号!');
        return;
      } else {
        utils.hideTips();
      }
      sendSMS($('#phone').val());
    });
  };
  
  window.toSubmit = function() {
	  if (!phoneStatus) {
	      utils.showTips(2, '手机号无效!');
	      return false;
	    }
	    if (!utils.checkCode()) {
	      utils.showTips(2, '验证码必须为4位数!');
	      return false;
	    }
	    if (!codeStatus) {
	      utils.showTips(2, '验证码发送失败!');
	      return false;
	    }
	    // todo 开始jax提交
	    $.ajax({
	      url: 'randomResetpassword.action',
	      data: $('form').serialize(),
	      error: function() {
	        utils.showTips(2, '重置密码失败!');
	      },
	      success: function(msg) {
	    	$('.ui-form').hide();
	    	$('.tiptext').css('margin-top', '50px');
	        if (msg == 'true') {
	          utils.showTips(1, '重置密码成功, 新验证码将发送到您的短信息上.');
	          startLoginTiming($('.timeTips'));
	        } else {
	          utils.showTips(2, '重置密码失败!');
	        }
	      }
	    });
	    return false;
  };
  

  var timeHandler = null;
  // 获取验证码
  var sendSMS = function(phone) {
    // 正在倒计时则不发送请求
    if (timeHandler !== null)
      return;
    $.ajax({
      url: 'forgotPasswordSMSVerificationCode.action?phone=' + phone,
      error: function() {
        codeStatus = false;
        utils.showTips(2, '发送验证码失败!');
        $('[name="code"]').attr('readonly');
        $('#submitBtn').attr('readonly');
      },
      success: function() {
        codeStatus = true;
        utils.hideTips();
        $('[name="code"]').removeAttr('readonly');
        $('#submitBtn').removeAttr('readonly').removeAttr('disabled');
        startTiming($('#send-verif'), $('[name="code"]'));
      }
    });
  };
  // 倒计时
  var startTiming = function(sendBtn, verifyCodeInput) {
      // 正在倒计时则不处理
      if (timeHandler !== null)
        return;
      verifyCodeInput.removeClass('readonly');
      timeHandler = utils.timing(60, function(s) {
        sendBtn.text('剩余 ' + s + ' 秒');
      }, function() {
        sendBtn.text('再次发送');
        timeHandler = null;
      })
  };

  // 跳转登录页倒计时
  var startLoginTiming = function(sendBtn) {
      utils.timing(5, function(s) {
        sendBtn.html('将在  ' + s + ' 秒后自动回到登陆页面! <a href="' + contextPath + '/login.action">立即跳转</a>');
      }, function() {
        window.location.href = contextPath + '/login.action';
      })
  };
  

  window.utils = utils;
  window.onload = bind;
})();