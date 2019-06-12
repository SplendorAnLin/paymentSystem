// 工具函数
(function() {
  window.utils = {
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
    // 字符串模板替换
    tpl: function(tpls, data) {
      return tpls.replace(/{{(.*?)}}/g, function($1, $2) {
        return data[$2] === undefined ? $1 : data[$2];
      });
    },
    /**
     * @description 是否为身份证
     * @param num {string}  身份证字符串
     * @returns {boolean}
     */
    isIdCardNo: function(num) {
      var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1);
      var parityBit = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
      var varArray = new Array();
      var lngProduct = 0;
      var intCheckDigit;
      var intStrLen = num.length;
      var idNumber = num;

      // initialize
      if ((intStrLen !== 15) && (intStrLen !== 18)) {
        return false;
      }
      // check and set value
      for (var i = 0; i < intStrLen; i++) {
        varArray[i] = idNumber.charAt(i);
        if ((varArray[i] < '0' || varArray[i] > '9') && (i !== 17)) {
          return false;
        } else if (i < 17) {
          varArray[i] = varArray[i] * factorArr[i];
        }
      }

      if (intStrLen === 18) {
        // check date
        var date8 = idNumber.substring(6, 14);
        if (utils.isDate8(date8) === false) {
          return false;
        }
        // calculate the sum of the products
        for (i = 0; i < 17; i++) {
          lngProduct = lngProduct + varArray[i];
        }
        // calculate the check digit
        intCheckDigit = parityBit[lngProduct % 11];
        // check last digit
        if (varArray[17] !== intCheckDigit) {
          return false;
        }
      } else {
        var date6 = idNumber.substring(6, 12);
        if (utils.isDate6(date6) === false) {
          return false;
        }
      }
      return true;
    },
    isDate6: function(sDate) {
      if (!/^[0-9]{6}$/.test(sDate)) {
        return false;
      }
      var year, month;
      year = sDate.substring(0, 4);
      month = sDate.substring(4, 6);
      if (year < 1700 || year > 2500) return false;
      if (month < 1 || month > 12) return false;
      return true;
    },
    isDate8: function(sDate) {
      if (!/^[0-9]{8}$/.test(sDate)) {
        return false;
      }
      var year, month, day;
      year = sDate.substring(0, 4);
      month = sDate.substring(4, 6);
      day = sDate.substring(6, 8);
      var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
      if (year < 1700 || year > 2500) return false;
      if (((year % 4 === 0) && (year % 100 !== 0)) || (year % 400 === 0)) {
        iaMonthDays[1] = 29;
      }
      if (month < 1 || month > 12) return false;
      if (day < 1 || day > iaMonthDays[month - 1]) return false;
      return true;
    },
    // 银行卡号
    bankCard: function(val) {
      var status = true;
      // value为NAN(非法值)或者长度小于12, 则false
      if (isNaN(val))
        status = false;
      if (val.length < 12) {
        status = false;
      }
      // 将 123456字符串卡号,分割成数组 ['1', '2', '3', ...]
      var nums = val.split('');
      // 合计
      var sum = 0;
      // 索引
      var index = 1;
      for (var i = 0; i < nums.length; i++) {
        // 当前索引是否为偶数
        if ((i + 1) % 2 === 0) {
          // 当前数组倒index的数字 * 2, 转数值
          var tmp = Number(nums[nums.length - index]) * 2;
          if (tmp >= 10) {
            // 将大于等于10的值转字符串
            var t = tmp + ''.split('');
            // tmp 值等于 字符串[0] + 字符串[1],  '16' 就是 1+6=7
            tmp = Number(t[0]) + Number(t[1]);
          }
          // 累加值
          sum += tmp;
        } else {
          // sum 累加当前数组倒index的数字
          sum += Number(nums[nums.length - index]);
        }
        // 累加索引
        index++;
      }
      // 如果值不是10的倍数, 则不是一个合法的银行卡号
      if (sum % 10 !== 0) {
        status = false;
      }

      return status;
    },
    now: function() {
      return new Date().getTime();
    },
    debounce: function(func, wait, immediate) {
      // immediate默认为false
      var timeout, args, context, timestamp, result;
  
      var later = function() {
        // 当wait指定的时间间隔期间多次调用_.debounce返回的函数，则会不断更新timestamp的值，导致last < wait && last >= 0一直为true，从而不断启动新的计时器延时执行func
        var last = utils.now() - timestamp;
  
        if (last < wait && last >= 0) {
          timeout = setTimeout(later, wait - last);
        } else {
          timeout = null;
          if (!immediate) {
            result = func.apply(context, args);
            if (!timeout) context = args = null;
          }
        }
      };
  
      return function() {
        context = this;
        args = arguments;
        timestamp = utils.now();
        // 第一次调用该方法时，且immediate为true，则调用func函数
        var callNow = immediate && !timeout;
        // 在wait指定的时间间隔内首次调用该方法，则启动计时器定时调用func函数
        if (!timeout) timeout = setTimeout(later, wait);
        if (callNow) {
          result = func.apply(context, args);
          context = args = null;
        }
  
        return result;
      };
    },
  };
})();

// 主页验证
(function () {

  window.registerPhoneInit = function() {
    // 验证正则
    var regexp = {
      regexp: {
        PHONE: /^(13[0-9]|15[0123456789]|17[03678]|18[0-9]|14[57])[0-9]{8}$/,
        VCODE: /999999/,
        PASSWORD: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/
      }
    };
    var timeHandler = null;
    // 表单移开焦点验证
    weui.form.checkIfBlur('#form', regexp);
    // 提交表单
    $('#toSubmit').on('click', function () {
      weui.form.validate('#form', function (error) {
        if (!error) {
          var loading = weui.loading('<p class="loading-text">提交中...</p>');
          var param = "phoneNo="+ $("#phone-input").val() +"&password=" + $("#pass-input").val() + 
          "&verifyCode=" + $("#verifyCode-input").val() + "&agentNo=" + $("#agentNo").val();
          $.post("open", param, function(data){
	            loading.hide();
		        weui.toast('<p class="loading-text">提交中...</p>', 3000);
		        if (data == 'VERIFYCODE_ERROR') {
		        	weui.topTips('验证码错误!');
		        } else if (data == 'ERROR') {
		        	weui.topTips('入网失败!');
		        } else if (data == 'SUCCESS') {
		        	window.location.href = 'quickOpensuccess'
		        }
          });
        }
      }, regexp);
  
    });

    // 发送验证码
    $('#send-vcode').on('click', function() {
      if (timeHandler !== null)
        return;
      weui.form.validate('#phone-input-wrap', function(error) {
        if (!error) {
        	var loading = weui.loading('<p class="loading-text">获取验证码</p>');
        	$.ajax({
        		url: "sendVerifyCode?phone=" + $('#phone-input').val(),
        		type: 'post',
        		success: function(result) {
        			loading.hide();
            		if ("TRUE" == result) {
            			
            			timeHandler = utils.timing(120, function (s) {
            				regexp.regexp.VCODE = /\d{4}/g;
            				$('#send-vcode').text('剩余 ' + s + ' 秒');
            			}, function () {
            				$('#send-vcode').text('获取验证码');
            				timeHandler = null;
            			});
            		} else {
            			weui.topTips('该手机号已注册!');
            		}
        		},
        		error: function() {
        			loading.hide();
        			weui.topTips('网络异常, 发送验证码失败!');
        		}
        	});
        	
        }
      }, regexp);
    });

    // 阅读条款
    $('#readTerms').on('click', function() {
      var content = $('#terms-content').html();
      weui.alert(content, {title: '用户服务协议' });
    });


  };


})();



//APP注册验证
(function () {

  window.registerAppInit = function() {
    // 验证正则
    var regexp = {
      regexp: {
        PHONE: /^(13[0-9]|15[0123456789]|17[03678]|18[0-9]|14[57])[0-9]{8}$/,
        VCODE: /999999/,
        PASSWORD: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/,
        AGENT: function(val, input) {
        	var flag = 'notMatch';
            $.ajax({
                url: 'checkAgentNo.action',
                type: 'post',
                async: false,
                data: "agentNo=" + val,
                success: function(msg) {
                	if (msg == 'TRUE') {
                		flag = 'notMatch';
                	} else {
                		flag = null;
                	}
                },
                error: function() {
                	flag = 'notMatch';
                }
              });
        	return flag;
        }
      }
    };
    var timeHandler = null;
    // 表单移开焦点验证
    weui.form.checkIfBlur('#form', regexp);
    // 提交表单
    $('#toSubmit').on('click', function () {
      weui.form.validate('#form', function (error) {
        if (!error) {
          var loading = weui.loading('<p class="loading-text">提交中...</p>');
          var param = $('#form').serialize();
          $.post('appOpen', param, function(data){
	            loading.hide();
		        // weui.toast('<p class="loading-text">提交中...</p>', 3000);
		        if (data == 'VERIFYCODE_ERROR') {
		        	weui.topTips('验证码错误!');
		        } else if (data == 'ERROR') {
		        	weui.topTips('入网失败!');
		        } else if (data == 'SUCCESS') {
		        	window.location.href = 'quickOpensuccess'
		        } else if (data == 'FALSE') {
		        	weui.alert("推荐人编号错误!");
		        }
          });
        }
      }, regexp);
  
    });

    // 发送验证码
    $('#send-vcode').on('click', function() {
      if (timeHandler !== null)
        return;
      weui.form.validate('#phone-input-wrap', function(error) {
        if (!error) {
        	var loading = weui.loading('<p class="loading-text">获取验证码</p>');
        	$.ajax({
        		url: "sendVerifyCode?phone=" + $('#phone-input').val(),
        		type: 'post',
        		success: function(result) {
        			loading.hide();
            		if ("TRUE" == result) {
            			timeHandler = utils.timing(120, function (s) {
            				regexp.regexp.VCODE = /\d{4}/g;
            				$('#send-vcode').text('剩余 ' + s + ' 秒');
            			}, function () {
            				$('#send-vcode').text('获取验证码');
            				timeHandler = null;
            			});
            		} else {
            			weui.topTips('该手机号已注册!');
            		}
        		},
        		error: function() {
        			loading.hide();
        			weui.topTips('网络异常, 发送验证码失败!');
        		}
        	});
        	
        }
      }, regexp);
    });

    // 阅读条款
    $('#readTerms').on('click', function() {
      var content = $('#terms-content').html();
      weui.alert(content, {title: '用户服务协议' });
    });


  };


})();




// 身份认证页验证
(function() {
  window.registerSettleInit = function() {
    // 验证正则
    var regexp = {
      regexp: {
        NAME: /^([\u4e00-\u9fa5]|\u00b7)+$/,
        IDCARD: function(val, input) {
          return utils.isIdCardNo(val) ? null : 'notMatch';
        },
        SETTLECARD: function(val, input) {
          // todo 同步ajax验证结算卡, 并获取银行卡附加信息
          if (!utils.bankCard(val)) {
            return 'notMatch';
          }
          var cardValidateStatus = 'notMatch';
          $.ajax({
            url: 'toCachecenterIin.action',
            type: 'post',
            async: false,
            data: "cardNo=" + val,
            success: function(iin) {
              if (iin) {
                // 设置提供方编码
                $('#bankCode').val(iin.providerCode);
                // 设置卡类型
                $('#cardType').val(iin.cardType);
                cardValidateStatus = null;
              } else {
                cardValidateStatus = 'notMatch';
              }
            },
            error: function() {
              cardValidateStatus = 'notMatch';
            }
          });
          


          return null;
        },
        BANKNAME: function(val, input) {
          return val !== '' ? null : 'notMatch';
        },
      }
    };


    // 表单移开焦点验证
    weui.form.checkIfBlur('#form', regexp);
    // 提交表单
    $('#toSubmit').on('click', function () {
      weui.form.validate('#form', function (error) {
        if (!error) {
          var loading = weui.loading('<p class="loading-text">提交中...</p>');
          setTimeout(function () {
            loading.hide();
            weui.toast('<p class="loading-text">提交成功</p>', 3000);
            $('#form').submit();
          }, 1500);
        } else {
          console.log(error);
          // weui.topTips('请上传身份证图片!');
        }
      }, regexp);
  
    });


  };
})();

