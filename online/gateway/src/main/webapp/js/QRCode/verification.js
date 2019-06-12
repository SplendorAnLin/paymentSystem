(function() {
  // 验证元素选择器
  var FLAG_SELECTOR = 'vd-selector';
  // 验证函数标志
  var FLAG_CHECK = 'vd-check';
  // 验证消息标志
  var FLAG_MSG = 'vd-msg';
  // 验证方法自定义消息
  var FLAG_MSG_custom = 'vd-msg-custom';
  // 异步标志
  var FLAG_WAIT = 'wait';
  // 验证状态
  var FLAG_STATUS = 'vd-status';
  // 消息方法标志
  var FLAG_PROMPT = 'vd-prompt';
  // 消息元素
  var FLAG_MSG_ELEMENT = 'vd-msg-element';
  // 自定义消息提示函数
  var FLAG_PROMPT_CUSTOM = 'vd-prompt-custom';

  /**
   * 验证结果状态枚举
   */
  var Status = {
    success: true,
    fail: false,
    asyn: 'wait',
  };

  /**
   * 触发事件枚举
   */
  var Trigger = {
    both: 'both',
    keyup: 'keyup',
    blur: 'blur',
  };




  /**
   * 绑定事件
   */
  function handler_keyup(event) {
    var datas = event.data;
    var self = datas['Validator'];
    var ele = datas['ele'];
    self.element(ele, null, Trigger.keyup);
  }
  function handler_blur(event) {
    var datas = event.data;
    var self = datas['Validator'];
    var ele = datas['ele'];
    if (!ele.attr('disabled') && ele.attr('justWhenSubmit') == undefined) { 
      self.element(ele, null, Trigger.blur);
    }
  }





  /**
   * 辅助工具
   */
  var utils = {
    /**
     * @description 获取input长度 (select)多选情况下选中的个数, (radio|checkbox)同组选中个数
     * @param value {string}
     * @param element {jQuery}
     * @returns {number}
     */
    getLength: function(value, element) {
      switch (element[0].nodeName.toLowerCase()) {
      case 'select':
        return $('option:selected', element ).length;
      case 'input':
        if (utils.checkable(element)) {
          return utils.findByName( element[0].name, element.parents('form') ).filter( ':checked').length;
        }
      }
      return value.length;
    },
    /**
     * @description 是否为radio|checkbox
     * @param element {jQuery}
     * @returns {boolean}
     */
    checkable: function(element) {
      if (!element[0] || !element[0].type) {
        return false;
      }
      return (/radio|checkbox/i ).test( element[0].type);
    },
    /**
     * @description 根据name寻找元素
     * @param name {string}
     * @param currentForm {jQuery}
     * @returns {number}
     */
    findByName: function(name, currentForm) {
      return $(currentForm).find('[name="' + utils.escapeCssMeta( name ) + '"]');
    },
    /**
     * @description 转义特殊字符
     * @param string {string}
     * @returns {string}
     */
    escapeCssMeta: function(string) {
      return string.replace(/([\\!"#$%&'()*+,./:;<=>?@\[\]^`{|}~])/g, '\\$1');
    },
    /**
     * @description 格式化参数输出
     * @param msg {string}
     * @param args {array|string}
     * @returns {string}
     */
    format: function(msg, args) {
      if (!msg) {
        return msg;
      }
      if (typeof (args) === 'string' && $(args).length === 1) {
        return msg.replace(/\{0\}/g, $(args).val());
      }
      if (!$.isArray(args)) {
        msg = msg.replace(/\{0\}/g, args);
        return msg;
      } else {
        $.each(args, function(i, param) {
          msg = msg.replace(new RegExp('\\{' + i + '\\}', 'g'), param);
        });
        return msg;
      }
    },
    /**
     * @description 获取对象参数
     * @param paramStr {string} 对象字符串
     * @returns {object}
     */
    toJson: function(paramStr) {
      if (paramStr === 'required') {
        return true;
      }
      // json模板字符串
      var json = '{"data": ' + paramStr + '}';
      var data = null;
      try {
        data = (JSON.parse(json)).data;
      } catch (err) {
        // 将参数转为字符串, 再次尝试
        json = '{"data": "' + paramStr + '"}';
        data = (JSON.parse(json)).data;
      }
      return data;
    },
    /**
     * @description 验证必填
     * @param required {string|function}
     * @param val {string}
     * @returns {boolean}
     */
    checkRequire: function(required, val) {
      if (required === false) {
        return false;
      }
      if ($.isFunction(required) && !required(val)) {
        return false;
      }
      if (typeof required === 'string' && $(required).length === 0) {
        return false;
      }
      return true;
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
  };
  if (window.utils) {
    window.utils = $.extend(window.utils, utils);
  } else {
    window.utils = utils;
  }
  /**
   * 验证核心
   */
  var Core = {
    /**
     * 验证函数
     */
    methods: {
      required: function(value, element) {
        if ( element[0].nodeName.toLowerCase() === 'select' ) {
          // Could be an array for select-multiple or a string, both are fine this way
          var val = $( element ).val();
          return val && val.length > 0;
        }
        if ( utils.checkable( element ) ) {
          return utils.getLength( value, element ) > 0;
        }
        return Boolean(value && value.replace(/\'|\s/g, '') !== '');
      },
      email: function(value, param) {
        if (param === 'empty') {
          return true;
        }
        return /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/.test(value);
      },
      url: function(value, param) {
        if (param === 'empty') {
          return true;
        }
        return /^(?:(?:(?:https?|ftp):)?\/\/)(?:\S+(?::\S*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})).?)(?::\d{2,5})?(?:[/?#]\S*)?$/i.test( value );
      },
      date: function(value, param) {
        if (param === 'empty') {
          return true;
        }
        return !/Invalid|NaN/.test(new Date(value).toString());
      },
      dateISO: function(value, param) {
        if (param === 'empty') {
          return true;
        }
        return /^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$/.test( value );
      },
      number: function(value, param) {
        if (param === 'empty') {
          return true;
        }
        return /^(?:-?\d+|-?\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/.test( value );
      },
      digits: function(value, param) {
        if (param === 'empty') {
          return true;
        }
        return /^\d+$/.test( value );
      },
      minlength: function(value, element, param) {
        var length = $.isArray( value ) ? value.length : utils.getLength( value, element );
        return length >= ($.isFunction(param) ? param(value) : param);
      },
      maxlength: function(value, element, param) {
        var length = $.isArray( value ) ? value.length : utils.getLength( value, element );
        return length <= ($.isFunction(param) ? param(value) : param);
      },
      rangelength: function( value, element, params ) {
        var length = $.isArray( value ) ? value.length : utils.getLength( value, element );
        var local_params = ($.isFunction(params) ? params(value) : params);
        return ( length >= local_params[0] && length <= local_params[1] );
      },
      max: function( value, element, param ) {
        // 参数支持选择器
        if (!$.isFunction(param) && $(param).length === 1 && typeof param == 'string') {
          param = $(param).val() || 0;
        }
        return parseFloat(value) <= parseFloat(($.isFunction(param) ? param(value) : param));
      },
      min: function( value, element, param ) {
        // 参数支持选择器
        if (!$.isFunction(param) && $(param).length === 1 && typeof param == 'string') {
          param = $(param).val() || 0;
        }
        return parseFloat(value) >= parseFloat(($.isFunction(param) ? param(value) : param));
      },
      range: function( value, element, params) {
        var local_params = ($.isFunction(params) ? params(value) : params);
        return ( parseFloat(value) >= parseFloat(local_params[0]) && parseFloat(value) <= parseFloat(local_params[1]));
      },
      step: function( value, element, param ) {
        var type = $( element ).attr( 'type' ),
          errorMessage = 'Step attribute on input type ' + type + ' is not supported.',
          supportedTypes = ['text', 'number', 'range'],
          re = new RegExp( '\\b' + type + '\\b' ),
          notSupported = type && !re.test( supportedTypes.join() );

        if ( notSupported ) {
          throw new Error( errorMessage );
        }
        return ( value % ($.isFunction(param) ? param(value) : param) === 0 );
      },
      equalTo: function( value, element, param ) {
        var target = $( ($.isFunction(param) ? param(value) : param) );
        return value === target.val();
      },
      notEqual: function(value, element, param) {
        var local_param = ($.isFunction(param) ? param(value) : param);
        var ele = $(local_param);
        if (ele.length > 0) {
          return value !== ele.val();
        } else {
          return value !== local_param;
        }
      },
      notZero: function(value, element, param) {
        return parseFloat(value) !== 0;
      },
      /**
       * @description 合法后缀名
       * @param params {array} ['txt', 'gif']
       * @returns {boolean}
       */
      accept: function(value, element, params) {
        var result = false;
        $.each(($.isFunction(params) ? params(value) : params), function(index, val) {
          var patten = new RegExp('\\.' + val + '$');
          if (patten.test(value)) {
            result = true;
          }
        });
        return result;
      },
      /**
       * @description 图片后缀名
       * @returns {boolean}
       */
      acceptIamge: function(value, element) {
        var params = ["png", "jpg"];
        var result = false;
        $.each(params, function(index, val) {
          var patten = new RegExp('\\.' + val + '$', 'i');
          if (patten.test(value)) {
            result = true;
          }
        });
        return result;
      },
      pattern: function(value, element, patten) {
        var reg;
        var local_patten = ($.isFunction(patten) ? patten(value) : patten);
        if (typeof local_patten === 'string') {
          reg = new RegExp(local_patten);
        } else {
          reg = local_patten;
        }
        return reg.test(value);
      },
      idCard: function(value) {
        return utils.isIdCardNo(value);
      },
      zipCode: function(value) {
        var tel = /^[0-9]{6}$/;
        return tel.test(value);
      },
      phone: function(value) {
        var mobile = /^(13[0-9]|15[0123456789]|17[03678]|18[0-9]|14[57])[0-9]{8}$/;
        return (value.length === 11 && mobile.test(value));
      },
      tel: function(value) {
        var tel = /^\d{3,4}-?\d{7,9}$/;
        return tel.test(value);
      },
      telPhone: function(value) {
        var length = value.length;
        var mobile = /^(13[0-9]|15[0123456789]|17[03678]|18[0-9]|14[57])[0-9]{8}$/;
        var tel = /^\d{3,4}-?\d{7,9}$/;
        return (tel.test(value) || (length === 11 && mobile.test(value)));
      },
      chinese: function(value) {
        var tel = /^([\u4e00-\u9fa5]|\u00b7)+$/;
        return tel.test(value);
      },
      notChinese: function(value) {
        var result = true;
        for (var i = 0; i < value.length; ++i) {
          var patten = /^([\u4e00-\u9fa5]|\u00b7)+$/;
          if (patten.test(value[i])) {
            result = false;
          }
        }
        return result;
      },
      bankCard: function(value) {
        // value为NAN(非法值)或者长度小于12, 则false
        if (isNaN(value))
          return false;
        if (value.length < 12) {
          return false;
        }
        // 将 123456字符串卡号,分割成数组 ['1', '2', '3', ...]
        var nums = value.split('');
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
          return false;
        }
        return true;
      },
      amount: function(value, element, bits) {
        // 默认金额为小数点2位
        var bit = bits || 2;
        var patten = new RegExp('^([1-9][\\d]{0,7}|0)(\\.[\\d]{1,' + bit + '})?$');
        return patten.test(value);
      },
    },
    /**
     * 提示消息
     */
    messages: {
      required: '必填',
      email: 'email格式无效',
      url: '网址无效',
      date: '无效的日期格式',
      dateISO: '无效的的日期格式 (YYYY-MM-DD)',
      number: '无效数字',
      digits: '只能为整数',
      equalTo: '输入不相同',
      notEqual: '值不能相同',
      maxlength: '不能超过 {0} 个字符',
      minlength: '不能少于 {0} 个字符',
      rangelength: '请输入长度 {0} 到 {1} 之间的字符',
      range: '请输入范围在 {0} 到 {1} 之间的数值',
      max: '不能大于 {0} 的数值',
      min: '不能小于 {0} 的数值',
      step: '必须是 {0} 的倍数',
      accept: '请输入有效的后缀',
      acceptIamge: '图片无效',
      pattern: '输入不匹配',
      idCard: '无效的身份证',
      zipCode: '无效的邮政编码',
      phone: '无效的手机号',
      tel: '无效的电话号码',
      telPhone: '无效的电话/手机号码',
      chinese: '只能为中文',
      notChinese: '不能为中文',
      bankCard: '无效的银行卡号',
      amount: '无效的金额',
      notZero: '不能为零'
    },
    /**
     * 提示函数
     */
    prompt: {
      // 不处理
      'none': function() {},
      /**
       * @description 显示错误提示
       * @param element {jQuery}
       * @param status {string|boolean} 验证状态
       * @param msg {string} 验证信息
       * @param where {string} 自定义插入位置
       * @returns
       */
      'message': function(element, status, msg, where) {
        if (!element.data('vd-lastcolor')) {
          element.data('vd-lastcolor', element.css('border'));
        }
        // 消息dom选择器
        var selector = element.attr(FLAG_MSG);
        // 如果类型为checkbox/radio, 则一组公用一个消息元素
        var msgElement;
        if (selector) {
          msgElement = $(selector).text(msg);
        } else {
          var bindElement = element.data(FLAG_MSG_ELEMENT);
          if (bindElement) {
            msgElement = bindElement.text(msg);
          } else {
            msgElement = $('<span class="vd-error"></span>');
            if (utils.checkable(element)) {
              var checkables = utils.findByName(element[0].name, element.parents('form'));
              checkables.data(FLAG_MSG_ELEMENT, msgElement);
            } else {
              element.data(FLAG_MSG_ELEMENT, msgElement);
            }

            if ($.isFunction(where)) {
              // 自定义插入位置
              where(element, msgElement);
            } else {
              element.parent().append(msgElement.text(msg));
            }
          }
        }
        switch (status) {
        case Status.success:
          if (!element.is('select')) {
            element.css('border', element.data('vd-lastcolor'));
          }
          // 验证通过, 隐藏消息
          msgElement.hide();
          break;
        case Status.fail:
          if (!element.is('select')) {
            element.css('border', '1px solid red');
          }
          // msgElement[0].scrollIntoView(false);
          msgElement.show();
          break;
        case Status.asyn:
          msgElement.text(msg).show();
          break;
        }
      },
      /**
       * 边框变红
       */
      'borderRed': function(element, status, msg) {
        if (!element.data('vd-lastcolor')) {
          element.data('vd-lastcolor', element.css('border'));
        }
        switch (status) {
        case Status.success:
          element.css('border', element.data('vd-lastcolor'));
          break;
        case Status.fail:
          element.css('border', '1px solid red');
          break;
        case Status.asyn:
          break;
        }
      },
      'block': function(element, status, msg) {
        Core.prompt.message(element, status, msg, function(element, msgElement) {
          msgElement.css('color', 'red');
          element.parents('.block').append(msgElement.text(msg));
        });
      },
      'inputWrap': function(element, status, msg) {
        Core.prompt.message(element, status, msg, function(element, msgElement) {
          msgElement.css('color', 'red');
          element.parents('.input-wrap').append(msgElement.text(msg));
        });
      },
      'nextLine': function(element, status, msg) {
        Core.prompt.message(element, status, msg, function(element, msgElement) {
          // 获取input左边距离
          var left = element.parents('.input-wrap').position().left;
          msgElement.css({
            'color': 'red',
            'position': 'relative',
            'left': left
          });
          element.parents('.item').append(msgElement.text(msg));
        });
        // 自动更新对话框高度
        if (window.modal) {
          window.modal.resize();
        }
      }
    },
    /**
     * @description 添加自定义验证
     * @param name {string}
     * @param method {function}
     * @param message {string}
     * @returns
     */
    addMethod: function(name, method, message, waitMessage) {
      if (!$.isFunction(method)) {
        console.warn('addMethod: ', method, '不是一个验证函数!');
        return;
      }
      Core.methods[name] = method;
      // todo 这里不从message获取值, 而是写一个函数, 根据状态参数, 判断返回message还是waitMessage
      Core.messages[name] = function(element, status) {
        if (status === Status.asyn) {
          return waitMessage || '';
        } else if (status === Status.fail) {
          return message || '';
        } else {
          return '';
        }
      };
    },
    /**
     * @description 添加自定义提示
     * @param name {string}
     * @param promptFn {function}
     * @returns
     */
    addPrompt: function(name, promptFn) {
      // 禁止覆盖内置提示方法
      if (!Core.prompt[name]) {
        Core.prompt[name] = promptFn;
      }
    },
  };

  /**
   * 表单验证
   */
  function Validator(form, options) {
	  
    // 默认参数
    var option = {
      prompt: Validator.Prompt,
      debug: false,
      rules: {},
      messages: {},
      onchange: {},
      trigger: {},
      dynamic: false,
      submitHandler: null,
      stopSubmit: false,
      // 只有提交表单时验证
      justWhenSubmit: false
    };
    // 设置参数
    var self = this;
    self.currentForm = form;
    self.options = $.extend(option, options);
    
    self.elements = [];
    // 初始化
    self.init();
    // 捕获submit事件
    self.handler_submit();
  }

  /**
   * 初始化
   */
  Validator.prototype.init = function() {
    var self = this;
    var form = self.currentForm;
    // 获取验证元素
    self.elements = self.findElement(form, self.options.rules);
    // 生成验证函数
    $.each(self.elements, function() {
      var element = $(this);
      // 生成验证函数
      self.compose(element);
      // 是否不绑定事件
      if (self.options.justWhenSubmit) {
        return;
      }
      // 绑定事件
      self.bind(element);
    });
  };
  /**
   * @description 获取验证元素
   * @returns {array}
   */
  Validator.prototype.findElement = function(form, rules) {
    var self = this;
    var elements = [];
    // 获取属性规则元素
    elements = $.makeArray($('[required]', form));
    // 获取选项规则
    for (var name in rules) {
      // 当作name属性或者选择器获取元素
      var cache = utils.findByName(name, form);
      var inputs = cache.length > 0 ? cache : $(name);
      inputs.data(FLAG_SELECTOR, name);
      self.push(elements, inputs);
    }
    return elements;
  };
  /**
   * @description 获取属性规则
   * @returns {object}
   */
  Validator.prototype.attrRules = function(element) {
    var rules = {};
    for (var method in Core.methods) {
      var attr = element.attr(method);
      if (attr !== undefined) {
        // 获取参数
        var param = utils.toJson(element.attr(method));
        rules[method] = param;
      }
    }
    return rules;
  };
  /**
   * @description 验证单个方法
   * @returns {boole|string} 返回验证状态
   */
  Validator.prototype.singleVerify = function(element, method, param, val, asynFn) {
    var self = this;
    var fn = Core.methods[method];
    if (!$.isFunction(fn)) {
      console.warn(method, '不是一个验证方法!');
      return true;
    }
    element.data(FLAG_MSG, '');
    var status = fn(val, element, param, function(asynStatus) {
      // 处理异步验证消息
      self.setMessage(element, asynStatus, method, param);
      // 异步返回后触发消息提示
      asynFn(asynStatus);
    });
    
    // todo 这里变成 wati 了
    
    status = status === undefined ? Status.asyn : status;
    // 处理验证消息 (自定义验证方法未返回状态, 则默认为等待状态)
    self.setMessage(element, status, method, param);
    return status;
  };
  /**
   * @description 设置验证消息
   * @returns
   */
  Validator.prototype.setMessage = function(element, status, method, param) {
    var self = this;
    var thenfor = element.data(FLAG_SELECTOR);
    // 获取选项指定的自定义消息
    var custom_msg = self.options.messages[thenfor];
    // 默认消息
    var default_msg = Core.messages[method];
    // 消息
    var msg;
    // 处理undefined, 处理函数, 处理内置优先级
    var priority = (custom_msg && custom_msg[method]) || default_msg;
    // 元素属性上自定义错误消息
    var dataErrorMsg = element.attr('vd-ErrorMsg') || element.attr('msg-' + method);
    if (dataErrorMsg) {
      priority = dataErrorMsg;
    }
    if (status === Status.fail && element.data(FLAG_MSG_custom)) {
      priority = element.data(FLAG_MSG_custom);
    }
    if (!priority) {
      priority = '';
      console.warn('验证方法', method, '既没有内置消息也没有自定义消息');
    }
    msg = $.isFunction(priority) ? priority(element, status) : priority;
    
    
    // 根据状态设置参数
    if (status === true) {
      // 验证成功状态
      element.data(FLAG_MSG, '');
    } else {
      element.data(FLAG_MSG, utils.format(msg, param));
    }
  };

  /**
   * @description 生成验证
   * @param element {jQuery}
   * @returns
   */
  Validator.prototype.compose = function(element) {
    var self = this;
    var rules = self.options.rules;
    var trigger = self.options.trigger[element.data(FLAG_SELECTOR) || element.attr('name')];
    // 生成验证函数
    element.data(FLAG_CHECK, function(triger, asynFn) {
      var debug = self.options.debug;
      // 元素值
      var val = element.val();
      // 元素选项规则
      var opt_rule = rules[element.data(FLAG_SELECTOR)];
      // 元素属性规则
      var attr_rule = self.attrRules(element);
      // 合并规则
      var rule = $.extend({}, opt_rule, attr_rule);
      // 非必填 或者 表单被禁用且不是异步等待时
      if (!utils.checkRequire(rule.required, val) || (element.attr('disabled') && element.data(FLAG_STATUS) !== Status.asyn) ) {
        return Status.success;
      }

      for (var method in rule) {
        var fn = Core.methods[method];
        // 判断当前验证方法是否在规定的触发事件中
        if (triger !== Trigger.both) {
          if (trigger && trigger[method] !== Trigger.both && trigger[method] !== undefined) {
            if (trigger[method] !== triger) {
              continue;
            }
          }
        }
        if (!$.isFunction(fn)) {
          console.warn(method, '不是一个验证方法!');
          return Status.success;
        }
        var status = self.singleVerify(element, method, rule[method], val, asynFn);
        if (status !== Status.success) {
          if (debug && status === Status.fail) {
            console.log(method, '验证失败: ', element[0], element.val());
          }
          // 单个验证失败就不继续验证其他方法
          return status;
        }
      }
      return Status.success;
    });
  };
  /**
   * @description 加入元素, 存在则不加入
   * @returns
   */
  Validator.prototype.push = function(elements, items) {
    $.each(items, function() {
      var item = this;
      var exist = false;
      $.each(elements, function() {
        if (this === item) {
          exist = true;
        }
      });
      if (!exist) {
        elements.push(item);
      } else {
        // console.log('重复', this);
      }
    });
    return elements;
  };
  /**
   * @description 绑定事件
   * @returns
   */
  Validator.prototype.bind = function(ele) {
    var self = this;
    ele.unbind(Trigger.keyup, handler_keyup);
    ele.unbind(Trigger.blur, handler_blur);
    if (ele.attr('noKeyup') === undefined) {
      ele.bind('keyup', {
      'Validator': self,
      'ele': ele
      }, handler_keyup);
    }
    ele.bind('blur', {
    'Validator': self,
    'ele': ele
    }, handler_blur);
  };
  /**
   * 提交表单
   */
  Validator.prototype.submit = function() {
    var self = this;
    self.currentForm.trigger('submit', true);
  };
  /**
   * @description 挂接提交表单事件
   * @returns
   */
  Validator.prototype.handler_submit = function() {
    var self = this;
    var form = self.currentForm;
    var submitHandler = self.options.submitHandler;
    // 禁用浏览器默认验证
    form.attr('novalidate', 'novalidate');
    // 捕获表单提交事件
    form.unbind('submit');
    var aysn = false;
    form.submit(function(event, submit) {
      if (self.options.stopSubmit === true) {
        return;
      }
      if (aysn) {
        aysn = false;
        return;
      }
      self.form(function(total, pass) {
        if ($.isFunction(Validator.SubmitHandler)) {
          Validator.SubmitHandler(form, pass);
        }
        // submitHandler返回true, 则不让提交
        if ($.isFunction(self.options.submitHandler) && !submit) {
          var isHandler = self.options.submitHandler(form, pass, function() {
            // 兼容旧代码
            aysn = true;
            form.submit();
          });
        }
        if (pass && isHandler !== true) {
          // 提交表单
          aysn = true;
          form.submit();
        }
      });
      if (!aysn) {
        event.preventDefault();
        return false;
      }
    });
  };
  /**
   * @description 验证单个元素
   * @param element {jQuery}
   * @returns
   */
  Validator.prototype.element = function(element, aysnFn, trigerEvent) {
    var self = this;
    var fn = element.data(FLAG_CHECK);
    if (!self.has(element) && !fn) {
      console.warn('严重元素:', element, '不存在验证数组中.');
      return false;
    }
    
    // 是否不绑定事件
    if (self.options.justWhenSubmit) {
    	if (trigerEvent == Trigger.keyup || trigerEvent == Trigger.blur) {
    		return Status.success;
    	}
    }
    
    // 如果元素已经从DOM中删除,则不进行验证
    if (element.parent().length === 0 || fn === undefined) {
      return Status.success;
    }
    // 去除元素值中的空格 (加上后会不停设置光标)
    // element.val(element.val().replace(/\'|\s/g, ''));
    var triger = trigerEvent || Trigger.both;
    var prompt = self.options.prompt;
    // select,textarea 优先级大于这些, 优先使用红色边框验证提示
    if (element.is('textarea')) {
      prompt = 'borderRed';
    } else {
      prompt = element.attr(FLAG_PROMPT) || element.data(FLAG_PROMPT) || self.options.prompt;
    }
    var promptFn = element.data(FLAG_PROMPT_CUSTOM) || Core.prompt[prompt];
    var onchange = self.options.onchange;
    // 对应回调函数
    var calbc = onchange[element.data(FLAG_SELECTOR)];
    // 验证后依状态处理
    var verifyEnd = function(status) {
      // 设置状态
      element.data(FLAG_STATUS, status);
      // 回调函数, 返回true则捕获消息提示,可用于自定义提示样式
      if ( $.isFunction(calbc)) {
        if (calbc(element, status) === true) {
          return;
        }
      }
      // 显示验证提示
      if ($.isFunction(promptFn)) {
        promptFn(element, status, element.data(FLAG_MSG));
      }
    };

    var endStatus = fn(triger, function(asynStatus) {
      element.removeAttr('disabled');
      verifyEnd(asynStatus);
      if ($.isFunction(aysnFn)) {
        aysnFn(asynStatus);
      }
    });
    if (endStatus === Status.asyn) {
      // 异步验证时禁用元素以免输入
      element.attr('disabled', 'disabled');
    }
    verifyEnd(endStatus);

    return endStatus;
  };
  /**
   * @description 验证整个表单
   * @returns
   */
  Validator.prototype.form = function(asynFn) {
    var self = this;

    if (self.options.dynamic === true) {
      self.init();
    }
    var elements = self.elements;
    var pass = true;
    var total = 0;
    var asynCount = 0;
    var aysnSucces = 0;
    $.each(elements, function() {
      var element = $(this);
      var status = self.element(element, function(asynStatus) {
        ++asynCount;
        if (asynStatus === Status.success) {
          ++aysnSucces;
        }
        if (asynCount === total && $.isFunction(asynFn)) {
          // 异步都验证成功 true && 异步成功 === 异步总数
          asynFn(total, pass && aysnSucces === total);
        }
      }, Trigger.both);
      if (status === Status.asyn) {
        ++total;
        return;
      }
      if (status !== Status.success) {
        pass = false;
        return;
      }
    });
    if (total === 0 && $.isFunction(asynFn)) {
      asynFn(total, pass);
    }
    return pass;
  };
  /**
   * @description 根据name获取对应规则
   * @param name {string}
   * @returns {object}
   */
  Validator.prototype.rules = function(name) {
    var self = this;
    if (self.options.rules[name] === undefined) {
      console.warn('Validator: options.rules 选项中没有此name对应配置', name);
    }
    return self.options.rules[name];
  };

  /**
   * @description 销毁
   * @returns
   */
  Validator.prototype.destroy = function() {
    var self = this;
    var form = self.currentForm;
    var elements = self.elements;
    if (!form) {
      return;
    }
    // 取绑表单事件
    form.unbind('submit');
    $.each(elements, function() {
      var element = $(this);
      element.unbind(Trigger.keyup, handler_keyup);
      element.unbind(Trigger.blur, handler_blur);
      element.removeAttr(FLAG_SELECTOR);
      element.data(FLAG_CHECK, null);
      element.data(FLAG_MSG, null);
      element.data(FLAG_STATUS, null);
      element.data(FLAG_PROMPT, null);
      element.data(FLAG_PROMPT_CUSTOM, null);
      var msgElement = element.data(FLAG_MSG_ELEMENT);
      if (msgElement) {
        msgElement.remove();
      }
      element.data(FLAG_MSG_ELEMENT, null);
    });
  };
  /**
   * @description 判断元素是否存在
   * @param element {jQuery}
   * @returns
   */
  Validator.prototype.has = function(element) {
    var self = this;
    var elements = self.elements;
    var exist = false;
    $.each(elements, function() {
      if (this === element[0]) {
        exist = true;
      }
    });
    return exist;
  };

  // 全局提示类型
  Validator.FLAG_SELECTOR = FLAG_SELECTOR;
  Validator.FLAG_CHECK = FLAG_CHECK;
  Validator.FLAG_MSG = FLAG_MSG;
  Validator.FLAG_WAIT = FLAG_WAIT;
  Validator.FLAG_STATUS = FLAG_STATUS;
  Validator.FLAG_PROMPT = FLAG_PROMPT;
  Validator.FLAG_MSG_ELEMENT = FLAG_MSG_ELEMENT;
  Validator.FLAG_PROMPT_CUSTOM = FLAG_PROMPT_CUSTOM;
  Validator.FLAG_MSG_custom = FLAG_MSG_custom;
  Validator.Prompt = 'message';
  Validator.SubmitHandler = null;
  Validator.Status = Status;
  Validator.Trigger = Trigger;
  window.Validator = Validator;
  window.toJson = utils.toJson;

  /**
   * 添加到jquery实例
   */
  $.fn.Validator = function(options) {
    var last = null;
    this.each(function() {
      var element = $(this);
      var lastValidator = this.Validator;
      if (lastValidator) {
        lastValidator.destroy();
      }
      lastValidator = new Validator(element, options);
      last = lastValidator;
      this.Validator = lastValidator;
      element.data('Validator', lastValidator);
    });
    // 兼容老旧代码
    return last;
  };

  /**
   * 添加到jquery静态
   */
  $.extend({
    addMethod: Core.addMethod,
    addPrompt: Core.addPrompt,
  });
})();