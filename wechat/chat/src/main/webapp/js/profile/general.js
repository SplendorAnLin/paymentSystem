// 通用脚本
(function() {
  var general = {};

  // 更改费率类型
  general.changeFeeType = function(feeType, parent, change) {
    // var rote = $('[name="customerFee.fee"]', parent);
    // var minfee = $('[name="customerFee.minFee"]', parent);
    // var maxfee = $('[name="customerFee.maxFee"]', parent);
    var rote = $('.fee', parent);
    var minfee = $('.minfee', parent);
    var maxfee = $('.maxfee', parent);

    if (parent.attr('disabled'))
      return;

    if (feeType === 'RATIO') {
      // 百分比
      rote.attr('placeholder', '格式为:0.02=0.2%');
      maxfee.attr('placeholder', '此处为封顶值').removeAttr('disabled');
      minfee.attr('placeholder', '格式为:0.2=2角').removeAttr('disabled');
      maxfee.closest('.item').show();
      minfee.closest('.item').show();
    } else {
      // 固定
      rote.attr('placeholder', '格式为：（1、2）个位数');
      maxfee.attr('placeholder', '格式为：（1、2）个位数').attr('disabled', 'disabled');
      minfee.attr('placeholder', '格式为：（1、2）个位数').attr('disabled', 'disabled');
      maxfee.closest('.item').hide();
      minfee.closest('.item').hide();
    }
    if ($.isFunction(change))
      change(rote, minfee, maxfee, feeType);
  };

  // 路由模板-根据产品类型设置路由模板
  general.routerTplForType = function(productType, parent) {
    // 路由模板下拉列表
    var select = $('.router-template', parent);
    if (!productType || select.length === 0)
      return;

    Api.boss.routerTemplate(productType, function(interfacePolicyBeans) {
      if (interfacePolicyBeans.length === 0) {
        // 没有路由模板则将2个隐藏输入框禁用
        $('.router-hide', parent).attr('disabled', 'disabled');
        // 移除路由模板下拉列表的必填验证
        select.removeAttr('required');
        return;
      }
      var selectBox = select.get(0).selectBox;
      selectBox.options = optionArray(interfacePolicyBeans, 'name', 'code');
    });

  };

  // 初始化路由模板
  general.routerTemplate = function(parent) {
    $('.productItem').each(function() {
      var productType = $(this).attr('data-productType');
      general.routerTplForType(productType, $(this));
    });
  };

  // 结算卡验证
  general.SettleCardValidator = function(form, customValidator, defaultInit) {
    /**
     * 对公验证
     * 账户类型: OPEN(对公)
     *  - 账户名称: 必填
     *  - 银行卡号: 必填
     *  - 开户行名称: 必填
     */

    /**
     * 对私验证
     * 账户类型: INDIVIDUAL(对私)
     *  - 账户名称: 必填,中文
     *  - 银行卡号: 必填,正常银行卡号, checkBankCard(ajax银行卡验证)
     *  - 开户行名称: 必填, checkBankName(ajax银行名称验证)
     */


    // 银行卡号name
    var cardNo_Name = 'customerSettle.accountNo';
    var bank_Name = 'customerSettle.openBankName';

    // 开户行编号(银行卡号)
    var accountNo = $('#accountNo', form);
    // 账户名称
    var cardName = $('#cardName', form);
    // 账户类型(对公对私下拉列表)
    var customerType = $('#customerType', form);
    // 开户行名称(银行名称)
    var openBankName = $('#openBankName', form);
    // 银行码 [隐藏输入框, 通过获取银行卡号信息设置]
    var bankCode = $('#bankCode', form);
    // 开启总行复选框
    var sabkBankFlag = $('#sabkBankFlag', form);
    // 上一次银行卡号
    var lastAccountNo = accountNo.val();


    // 必须元素验证
    if (accountNo.length !== 1 || cardName.length !== 1 || customerType.length !== 1 || openBankName.length !== 1 || bankCode.length !== 1) {
      console.error('卡识别验证初始化失败: 参数不正确.');
      return;
    }


    // 获取账户类型 INDIVIDUAL=对私 OPEN=对公
    var getCustomerType = function() {
      return customerType.val();
    };
    
    // 获取银行等级 0=分行 1=总行
    var getBankFlag = function() {
      // 页面没有总行复选框则默认0级
      return sabkBankFlag.length !== 1 ? 0 : sabkBankFlag.val();
    };

    // 切黄总行 element=总行复选框
    window.changeFlag = function(element) {
      if (element.checked == true) {
        element.value = 1;
      } else {
        element.value = 0;
      }
    };

    // 银行列表自动完成
    var autocomplete = new AutoComplete(openBankName, {
      // 数据源
      dataSource: function(val, ac, callback) {
        // 这里bankCode为空是对公情况下, 为空默认匹配查找所有银行
        Api.boss.banks(val, bankCode.val(), getBankFlag(), function(banks) {
          callback(banks, val);
        });
      },
      // 敲击延迟, 对于本地数据推荐0延迟
      delay: 300,
      // 是否空白匹配所有
      allowEmpy: false
    });
    // 选中银行 - 暂时还不理解执行这些为了什么, bankCode早就已经设置过了
    // openBankName.on(AutoComplete.Event.select, function(event, data) {
    //   Api.boss.bossBankInfo(bankCode.val(), function(iin) {
    //     if (iin && iin.length > 0) {
    //       bankCode.val(data.bankCode);
    //     }
    //   });
    // });


    // 验证银行卡号
    Compared.add('checkBankCard', function(val, params, ele, ansyFn) {
      // 替换空格
      var card = val.replace(/\s+/g, '');
      if (card === '') {
        return Compared.toStatus(true, '');
      }
      // 对公账户类型则不验证银行卡号
      var type = getCustomerType();
      if (type === 'OPEN') {
        return Compared.toStatus(true, '');
      }

      Api.boss.bankCardInfo(card, function(iin) {
        if (iin !== null) {
          bankCode.val(iin.providerCode);
          // 银行卡号改变, 清空银行名称
          if (lastAccountNo != val) {
            openBankName.val('');
          }
          lastAccountNo = val;
        }
        ansyFn(Compared.toStatus(iin !== null && iin.providerCode !== null, '银行卡号无效'));
      });
    });

    // 验证银行名称
    Compared.add('checkBankName', function(val, params, ele, ansyFn) {
      // 对公账户类型则不验证银行卡号
      var type = getCustomerType();
      if (type === 'OPEN') {
        return Compared.toStatus(true, '');
      }
      // 检测autocomplete组件选中数据, 用户手动输入则无效
      var bankName = $(ele).attr('data-select');
      return Compared.toStatus(bankName != '' && bankName != undefined, '银行名称无效, 请从银行列列表中选择');
      

      // Api.boss.banks(val, bankCode.val(), getBankFlag(), function(banks) {
      //   var exist = false;
      //   $.each(banks, function() {
      //     if (this.name == value)
      //       exist = true;
      //   });
      //   ansyFn(Compared.toStatus(exist, '银行名称无效'));
      // });
    });

    // 添加验证
    // 银行卡号验证
    customValidator.add(accountNo, [
      {
        name: 'bankCard'
      },
      {
        name: 'checkBankCard'
      }
    ], null, {checkBankCard: 2}, function(ele, status, message) {
      var tips = '请先输入银行卡号';
      if (status === Compared.EnumStatus.ok && ele.get(0).ValidtorComplete) {
          if (openBankName.val() === tips) {
            openBankName.val('');
          }
          openBankName.removeAttr('disabled');
          openBankName.focus();
      } else if (status === Compared.EnumStatus.fail) {
          bankCode.val('');
          openBankName.val(tips);
          openBankName.attr('disabled', 'disabled');
          customValidator.element(openBankName);
      }
    });

    // 银行名称验证
    customValidator.add(openBankName, [
      {
        name: 'checkBankName'
      }
    ], null, {checkBankName: 0});

    // 持卡人姓名验证
    customValidator.add(cardName, [
      {
        name: 'chinese'
      }
    ]);


    // 账户类型更改后, 触发验证
    customerType.change(function() {
      if (getCustomerType() === 'OPEN') {
        customValidator.remove(cardName, ['chinese']);
        customValidator.remove(accountNo, ['bankCard']);
        bankCode.val('');
      } else {
        customValidator.add(cardName, [{name: 'chinese'}]);
        customValidator.add(accountNo, [{name: 'bankCard'}]);
      }
      customValidator.element(accountNo);
      customValidator.element(cardName);
    });

    // 带初始值, 则主动验证一次
    if (defaultInit) {
      // 模拟切换账户类型, 切换对应验证
      customerType.trigger('change');
    }


    return autocomplete;
  };


  window.general = general;
})();

// 通用封装
(function() {
  // 查看合计对话框
  var viewCount = $('.viewCount').hide();
  $('#show_viewCount').click(function() {
    if (viewCount.length !== 1)
      return;
    viewCount.Alert('查看合计');
  });

})();

// 代付审核
(function() {
  var dfAudit = {};

  // 增加检测复核密码ajax验证
  window.top.Compared.add('checkAuditPassword', function(val, params, ele, ansyFn) {
    Api.boss.checkAuditPassword(val, function(status) {
      ansyFn(Compared.toStatus(status, '复核密码错误'));
    });
  });

  // 增加短信验证码密码ajax验证
  window.top.Compared.add('checkVerifyCode', function(val, params, ele, ansyFn) {
    Api.boss.checkVerifyCode(val, function(status) {
      ansyFn(Compared.toStatus(status, '验证码错误'));
    });
  });



  // 获取选中订单信息集合
  function orderInfo() {
    // 订单复选框
    var orders = $('.data-table td input[type="checkbox"]');
    // 未选中任何订单则提示
    if (orders.notCheckAll()) {
      Messages.warn('请至少选择一条代付订单!');
      return;
    }

    // 订单信息
    var infos = [];
    // 总订单编号,用于ajax附加数据
    var cbs = '';
    // 总金额
    var totalAmount = 0;
    orders.each(function() {
      // 忽略未选中订单
      if (!this.checked)
        return;
      // 由于valueList输出的表格, 无法带class, 所以只好用索引来获取值
      var tr = $(this).closest('tr');
      var td = $('td', tr);
      // 订单信息
      var info = {
        // 订单编号
        orderId: this.value,
        // 商户编号
        ownerId: td.eq(1).text(),
        // 收款人姓名
        accName: td.eq(4).text(),
        // 账户类型 (对公对私)
        accType: td.eq(9).text() == '对公' ? 'OPEN' : 'INDIVIDUAL',
        // 开户行名称
        bankName: td.eq(8).text(),
        // 付款金额
        amount: parseFloat(td.eq(6).text()),
        // 手续费
        fee: parseFloat(td.eq(7).text()),
        // 总付金额 (付款金额+手续费)
        applyamount: parseFloat($(this).attr('applyamount'))
      };
      infos.push(info);
      cbs = cbs + info.orderId + ',';
      totalAmount = totalAmount + info.applyamount;
    });

    return {infos: infos, cbs: cbs, totalAmount: totalAmount}
  }

  // 显示付款审核验证对话框对话框
  function passwordDialog(orderInfos) {
    var dialogElement = $('#accept-password');
    // 设置对话框内总笔数
    $('.count', dialogElement).text(orderInfos.infos.length);
    // 设置对话框内总金额
    $('.amount', dialogElement).text(orderInfos.totalAmount);
    // 清空上次输入密码
    $('[name="password"]', dialogElement).val();
    var dialog = new window.top.MyDialog({
      target: dialogElement.clone(),
      btns: [
        {
          lable: '取消'
        },
        {
          lable: '确定',
          click: function(data, content) {
            // 获取表单验证实例
            var formValidator = content.find('form').get(0).FormValidator;
            dialog.loading(true);
            formValidator.form(function(status) {
              dialog.loading(false);
              if (status.status === Compared.EnumStatus.ok) {
                // 验证成功, 执行付款审核成功
                doApplySubmit(orderInfos, '', dialog);
              } else if (status.status === Compared.EnumStatus.fail) {
                dialog.loading(false);
              }
            });
          }
        }
      ],
      btnType: 2,
      title: '付款审核验证',
      onOpen: function(dialog, content) {

      }
    });
  }

  // 显示付款审核短信验证对话框对话框
  function smsdDialog(orderInfos) {
    var dialogElement = $('#accept-password-sms');
    // 设置对话框内总笔数
    $('.count', dialogElement).text(orderInfos.infos.length);
    // 设置对话框内总金额
    $('.amount', dialogElement).text(orderInfos.totalAmount);
    // 清空上次输入验证码
    $('[name="verify-code"]', dialogElement).val();

    var dialog = new window.top.MyDialog({
      target: dialogElement.clone(),
      btns: [
        {
          lable: '取消'
        },
        {
          lable: '确定',
          click: function(data, content) {
            // 获取表单验证实例
            var formValidator = content.find('form').get(0).FormValidator;
            dialog.loading(true);
            formValidator.form(function(status) {
              dialog.loading(false);
              if (status.status === Compared.EnumStatus.ok) {
                // 验证成功, 执行付款审核成功
                doApplySubmit(orderInfos, '', dialog);
              } else if (status.status === Compared.EnumStatus.fail) {
                dialog.loading(false);
              }
            });
          }
        }
      ],
      btnType: 2,
      title: '付款审核验证',
      onOpen: function(dialog, content) {
        // 发送验证码按钮
        var sendBtn = content.find('#sendVerifyCode');
        // 验证码输入框
        var verifyCodeInput = content.find('[name="verify-code"]').addClass('readonly');
        sendBtn.click(function() {
          // 正在倒计时则不发送请求
          if (timeHandler !== null)
            return;
          // 发送短信验证码
          Api.boss.sendVerifyCode(0, function(status) {
            if (status == true) {
              Messages.success('发送短信验证码成功!');
              // 开始倒计时
              startTiming(sendBtn, verifyCodeInput);
            } else {
              Messages.error('发送短信验证码失败!');
            }
          });
        });
      },
      onClose: function() {
        clearInterval(timeHandler);
      }
    });

    // 计时器句柄
    var timeHandler = null;
    // 开始倒计时
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

  }



  // 显示审核拒绝对话框
  function rejectDialog(orderInfos) {
    var dialogElement = $('#reject-password');
    // 设置对话框内总笔数
    $('.count', dialogElement).text(orderInfos.infos.length);
    // 设置对话框内总金额
    $('.amount', dialogElement).text(orderInfos.totalAmount);

    dialog = dialogElement.Confirm('审核拒绝', function() {
      doApplySubmit(orderInfos, 'FALSE', dialog);
    });
  }

  // 付款审核
  function doApplySubmit(orderInfos, flag, dialog) {
    Api.boss.remitAudit(orderInfos.cbs, flag, function(data) {
      if (data === 'error') {
        Messages.error('网络异常, 审核失败, 请稍后重试...');
        return;
      }
      var result = data.result;
      var keywords = flag == 'FALSE' ? '拒绝' : '审核';
      var msg = '';
      switch(result) {
        case 1:
          msg = '{{keywords}}成功 {{success}} 笔, 共 {{successFlows}} 元';
        break;
        case 2:
          msg = '{{keywords}}成功 {{success}} 笔, 共 {{successFlows}} 元</br>{{keywords}}失败 {{fail}} 笔, 共 {{failFlows}} 元';
        break;
      }
      // 参数格式化
      msg = utils.tpl(msg, {
        keywords: keywords,
        success: data.success,
        successFlows: data.successFlows,
        fail: data.fail,
        failFlows: data.failFlows
      });

      if (flag != 'FALSE' && result == 2) {
        Messages.warn('为避免重复付款，请勿重复复核。');
        window.location.reload();
      } else {
        // 显示操作结果提示
        new window.top.Alert(msg, '操作结果', function() {
          dialog.close(null, true);
          window.location.reload();
        });
      }
    });
  }


  // 付款审核-审核通过
  dfAudit.accept = function(btn) {
    var orderInfos = orderInfo();
    if (!orderInfos)
      return;

    $(btn).loading();
    // 发送请求判断账户是否开启手机验证
    Api.boss.usePhoneCheck('', function(msg) {
      $(btn).ending();
      if (msg === 'error') {
        Messages.error('网络异常, 请重新登陆后尝试!');
        return;
      }
      if (msg == '1') {
        // 手机短信验证
        // 显示付款审核验证短信验证对话框对话框
        smsdDialog(orderInfos);
      } else {
        // 密码验证
        // 显示付款审核验证对话框对话框
        passwordDialog(orderInfos);
      }
    });


  };


  // 付款审核-审核拒绝
  dfAudit.reject = function(btn) {
    var orderInfos = orderInfo();
    if (!orderInfos)
      return;
      
    rejectDialog(orderInfos);
  };


  window.dfAudit = dfAudit;
})();

// 产品入网
(function() {


  var productWrap = $('#productWrap');
  // 产品下拉列表
  var productSelect = $('#productTypeSelect');


  var product = {
    // 已开通产品数组, 用于产品div排序
    products: [],
    // 初始化入网产品管理器
    init: function() {
      product.close('HOLIDAY_REMIT');
      product.closeAll();
      product.bind();
    },
    // 绑定事件
    bind: function() {
      // 业务选择下拉列表
      productSelect.change(function() {
        var selectBox = productSelect.get(0).selectBox;
        // 已开通产品数组 [{label: '个人网银', value: 'B2C'}, ...]
        var productList = selectBox.getCurrent();
        // 所有业务
        var options = selectBox.extract();
        // 开通已选择产品
        for (var i = 0; i < options.length; ++i) {
          var productType = options[i].value;
          if (options[i].selected) {
            product.open(productType);
          } else {
            product.close(productType);
          }
        }
      });

      // 假日付下拉列表
      var holidaySelect = $('.holidayStatus');
      holidaySelect.change(function() {
        if (this.value === 'TRUE') {
          // 开通假日付
          product.open('HOLIDAY_REMIT');
        } else {
          // 关闭假日付
          product.close('HOLIDAY_REMIT');
        }
      });

      // 关闭产品按钮
      $('.productItem .delProduct').click(function() {
        var productType = $(this).closest('.productItem').attr('data-producttype');
        product.close(productType);
      });

    },
    // 开通一个产品
    open: function(productType, values) {
      var productItem = $('.productItem[data-producttype="' + productType + '"]', productWrap);
      // 已开通则忽略
      if (!productItem.attr('disabled'))
        return;
      // 启用元素
      productItem.enablelInput();
      // 加入产品数组
      product.products.push(productType);
      // 排序div
      product.sort();

      // 处理代付特殊情况, 代付开通且是否开通下拉列表=TRUE则假日付开通
      if (productType === 'REMIT' && $('.holidayStatus', productItem).val() === 'TRUE') {
        product.open('HOLIDAY_REMIT');
      }

      // 显示div
      productItem.slideDown();
      productItem.removeAttr('disabled');

      // 下拉列表也加入
      productSelect.findOptionForVal(productType).get(0).selected = true;
      productSelect.renderSelectBox();
      return productItem;
    },
    // 删除一个产品
    close: function(productType) {
      var productItem = $('.productItem[data-producttype="' + productType + '"]', productWrap);
      // 已关闭则忽略
      if (productItem.attr('disabled'))
        return;
      // 禁用元素
      productItem.disableInput();
      // 清空元素值
      productItem.clearForm();
      // 从产品数组中删除
      var index = utils.indexOf(product.products, productType);
      if (index !== -1) {
        product.products.splice(index, 1);
      }

      // 处理代付特殊情况, 代付关闭则假日付也要关闭
      if (productType === 'REMIT') {
        product.close('HOLIDAY_REMIT');
      }
      // 隐藏div
      productItem.slideUp();
      productItem.attr('disabled', 'disabled');
      if (index !== -1 &&  productType !== 'HOLIDAY_REMIT') {
        // 下拉列表也要刷新
        productSelect.findOptionForVal(productType).get(0).selected = false;
        productSelect.renderSelectBox();
      }
    },
    // 删除所有产品
    closeAll: function() {
      $('.productItem', productWrap).each(function() {
        product.close($(this).attr('data-producttype'));
      });
    },
    // 排序产品div
    sort: function() {

      for(var i = 0; i <  product.products.length; ++i) {
        var productType = product.products[i];
        var productItem = $('.productItem[data-producttype="' + productType + '"]', productWrap);
        productWrap.append(productItem);
      }
      // 处理假日付特殊情况, 永远在代付后面
      $('[data-producttype="HOLIDAY_REMIT"]').insertAfter('[data-producttype="REMIT"]');
    },
    // 基本产品填值
    fillBaseValue: function(productItem, values) {
      var defaultValue= {
        // 费率类型 RATIO=百分比, FIXED=固定
        '.fee-type': 'RATIO',
        // 费率
        '.fee': '',
        // 最低费率
        '.minfee': '',
        // 最高费率
        '.maxfee': '',
        // 路由模板
        '.router-template': '',
      };

      var enters = $.extend({}, defaultValue, values);
      for (var key in enters) {
        var ele = $(key, productItem);
        if (ele.length == 0)
          continue;

        
        if (ele.get(0).nodeName === 'SELECT') {
          ele.selectForValue(enters[key]);
          // ele.renderSelectBox();
        } else {
          ele.val(enters[key]);
        }

      }

    },
    // 代付填值
    fillRemit: function(productItem, values) {
      var defaultValue = {
        // 审核 TRUE=是, FALSE=否
        '[name="serviceConfigBean.manualAudit"]': 'TRUE',
        // 短信验证 TRUE=是, FALSE=否
        '[name="serviceConfigBean.usePhoneCheck"]': 'TRUE',
        // IP
        '[name="serviceConfigBean.custIp"]': '',
        // 域名
        '[name="serviceConfigBean.domain"]': '',
        // 单笔最大金额
        '[name="serviceConfigBean.maxAmount"]': '',
        // 单笔最小金额
        '[name="serviceConfigBean.minAmount"]': '',
        // 单日最大金额
        '[name="serviceConfigBean.dayMaxAmount"]': '',
        // 付款起始时间
        '[name="serviceConfigBean.startTime"]': '',
        // 付款截止时间
        '[name="serviceConfigBean.endTime"]': '',
        // 自动结算 AUTO=自动, MAN=人工
        '[name="serviceConfigBean.fireType"]': 'MAN',
        // 运营审核 TRUE=需要运营审核, FALSE=不需要
        '[name="serviceConfigBean.manualAudit"]': 'TRUE',
        // 是否开通假日付
        '[name="serviceConfigBean.holidayStatus"]': 'TRUE'
      };

      product.fillBaseValue(productItem, $.extend({}, defaultValue, values));

    },
    // 代收填值
    fillReceive: function(productItem, values) {
      var defaultValue = {
        // 状态 TRUE=开启, FALSE=关闭
        '[name="receiveConfigInfoBean.status"]': 'TRUE',
        // IP
        '[name="receiveConfigInfoBean.custIp"]': '',
        // 域名
        '[name="receiveConfigInfoBean.domain"]': '',
        // 单笔最大金额
        '[name="receiveConfigInfoBean.singleMaxAmount"]': '',
        // 日限额
        '[name="receiveConfigInfoBean.dailyMaxAmount"]': '',
        // 单批次最大笔数
        '[name="receiveConfigInfoBean.singleBatchMaxNum"]': ''
      };

      product.fillBaseValue(productItem, $.extend({}, defaultValue, values));
    }
  };

  window.product = product;
})();

// 网点管理
(function() {

  var template = '\
      <div class="item">\
        <div class="input-area">\
          <span class="label w-90">{{label}}:</span>\
          <div class="input-wrap">\
            <input type="text"  name="shopList[{{index}}].{{name}}" data-name="shopList[INDEX].{{name}}" required>\
          </div>\
        </div>\
      </div>\
  ';

  var shopWrap = $('#shopMannage');
  var infos = [
    {
      label: '网点名称',
      name: 'shopName'
    },
    {
      label: '小票打印名称',
      name: 'printName'
    },
    {
      label: '拨号POS绑定码',
      name: 'bindPhoneNo'
    }
  ];


  var shopMannage = {
    shopList: [{}],
    add: function(defaultValue) {
      var option = {
        shopName: '',
        printName: '',
        bindPhoneNo: ''
      };
      var values = $.extend({}, option, defaultValue);
      var shopItem = $('<div class="shopItem fix"></div>');
      var index = shopMannage.shopList.length;
      // 循环创建输入框
      utils.each(infos, function(info, i) {
        var item = $(utils.tpl(template, {
          label: info.label,
          name: info.name,
          index: index
        }));
        $('input', item).val(values[info.name]);
        shopItem.append(item);
      });
      // 插入删除按钮
      var delItem = $('<div class="item"><a class="btn" href="javascript:void(0);">删除</a></div>');
      delItem.click(function() { 
        var i = $(this).closest('.shopItem').data('index');
        console.log(i);
        shopMannage.remove(i);
      });
      shopMannage.shopList.push(shopItem);
      shopItem.data('index', index);
      shopItem.append(delItem);
      shopWrap.append(shopItem);
    },
    remove: function(index) {
      var shopList = shopMannage.shopList;
      if (index <= 0 || index >= shopList.length)
        return;
      shopList[index].remove();
      shopList.splice(index, 1);
      shopMannage.flushIndex();
    },
    flushIndex: function() {
      utils.each(shopMannage.shopList, function(shopItem, i) {
        $(shopItem).data('index', i);
        $('[data-name]', shopItem).each(function() {
          var input = $(this);
          var name = input.attr('data-name').replace('INDEX', i);
          input.attr('name', name);
        });
      });
    },
    init: function() {
      $('.addShop').click(function() {
        shopMannage.add();
      });
    },
  };


  window.shopMannage = shopMannage;

})();