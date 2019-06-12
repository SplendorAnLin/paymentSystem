(function() {
  var Api = window.Api || {};
  // // test: 测试模式, ajax-error 函数会返回模拟数据以供测试 , 发布时候设置为false
  var debug = false;
  Api.customer = {
      /**
       * @description 检测原密码是否正确 - 提现密码
       * @param oldPassword {string} 原密码
       * @param fn {function} 回调函数
       * @returns false=原密码错误, true=原密码正确
       */
      checkOldPassword: function (oldPassword, fn) {
        if (!oldPassword) {
          fn(false);
          return;
        }
        utils.ajax({
          url: 'applyVerify.action',
          type: 'post',
          data: 'oldPassword=' + oldPassword,
          dataType: 'json',
          success: function(status) {
            fn(status); 
          },
          error: function() {
            fn(false);
          }
        });
      },
      /**
       * @description 检测账户是否开启手机验证 - 提现
       * @param fn {function} 回调函数
       * @returns 1=手机短信验证, 'error'=ajax错误, 其他等于密码验证
       */
      usePhoneCheck: function (fn) {
        utils.ajax({
          url: 'isUsePhoneCheck.action',
          type: 'post',
          data: "oldPassword=",
          success: function (msg) {
            fn(msg);
          },
          error: function () {
            fn('error');
          }
        });
      },
      /**
       * @description 检测账户是否开启手机验证 - 付款审核
       * @param auditPassword {string} 复核密码
       * @param fn {function} 回调函数
       * @returns 1=手机短信验证, 'error'=ajax错误, 其他等于密码验证
       */
      usePhoneCheckDf: function(auditPassword, fn) {
        utils.ajax({
          url: 'isUsePhoneCheck.action',
          type: 'post',
          data: "auditPassword=" + auditPassword,
          success: function (msg) {
            fn(msg);
          },
          error: function () {
            if (debug) {
              fn('1');
            } else {
              fn('error');
            }
          }
        });
      },
      /**
       * @description 检测提现密码是否正确- 提现
       * @param password {string} 提现密码
       * @param fn {function} 回调函数
       * @returns true=提现密码正确, false=提现密码错误
       */
      checkDrawCashPassword: function (password, fn) {
        utils.ajax({
          url: 'applyVerify.action',
          type: 'post',
          dataType: "json",
          data: "oldPassword=" + password,
          success: function (msg) {
            fn(msg == true);
          },
          error: function () {
            if (debug) {
              fn(true);
            } else {
              fn(false);
            }
          }
        });
      },
      /**
       * @description 检测验证码是否正确- 付款审核
       * @param verifyCode {string} 验证码
       * @param fn {function} 回调函数
       * @returns true=验证码正确, false=验证码错误
       */
      checkVerifyCode: function (verifyCode, fn) {
        utils.ajax({
          url: 'checkVerifyCodeEqual.action',
          type: 'post',
          dataType: "json",
          data: 'verifyCode=' + verifyCode,
          success: function (flag) {
            fn(flag == true);
          },
          error: function () {
            if (debug) {
              fn(true);
            } else {
              fn(false);
            }
          }
        });
      },
      /**
       * @description 获取手续费- 收款人管理
       * @param fn {function} 回调函数
       * @returns true=验证码正确, false=验证码错误
       */
      payeeFee: function (fn) {
        utils.ajax({
          url: 'queryCustomerDfFee.action',
          type: 'post',
          success: function (fee) {
            fn(fee);
          },
          error: function () {
            fn(false);
          }
        });
      },
      /**
       * @description 单笔代付- 收款人管理
       * @param args {string} 表单序列化参数
       * @param fn {function} 回调函数
       * @returns true=验证码正确, false=验证码错误
       */
      dfSingleApply: function (args, fn) {
        utils.ajax({
          url: 'dfSingleApply.action',
          dataType: 'json',
          type: 'post',
          data: args,
          success: function (result) {
            fn(result);
          },
          error: function () {
            fn(false);
          }
        });
      },
      /**
       * @description 切换代付自动审核 - 代付自动设置
       * @param operation {string} 开通状态
       * @param verifyCode {string} 验证码
       * @param fn {function} 回调函数
       * @returns true=验证码正确, false=验证码错误
       */
      updateAutoAudit: function (operation, verifyCode, fn) {
        utils.ajax({
          url: 'updateAutoAudit.action?' + Math.random(),
          type: 'post',
          dataType: "json",
          data: {
            "flag" : operation,
            "verifyCode" : verifyCode
          },
          success: function (flag) {
            fn(flag);
          },
          error: function () {
            fn('error');
          }
        });
      },
      /**
       * @description 换一个代付密钥 - 代付密钥
       * @param customerNo {string} 商户编号
       * @param fn {function} 回调函数
       * @returns true=验证码正确, false=验证码错误
       */
      changeDfKeys: function (customerNo, fn) {
        utils.ajax({
          url: 'getDfChangeKeys.action',
          type: 'post',
          data: 'customerNo=' + customerNo,
          success: function (result) {
            fn(result);
          },
          error: function () {
            fn(false);
          }
        });
      },
      /**
       * @description 换一个代付密钥 - 代付密钥
       * @param fn {function} 回调函数
       * @returns true=验证码正确, false=验证码错误
       */
      dfChangeKeys: function (fn) {
        utils.ajax({
          url: 'dfChangeKeys.action',
          type: 'post',
          success: function (result) {
            fn(result);
          },
          error: function () {
            fn(false);
          }
        });
      },
      /**
       * @description 换一个代付密钥 - 代付密钥
       * @param customerNo {string} 商户编号
       * @param fn {function} 回调函数
       * @returns true=验证码正确, false=验证码错误
       */
      changeDsKeys: function (customerNo, fn) {
        utils.ajax({
          url: 'getDsChangeKeys.action',
          type: 'post',
          data: 'customerNo=' + customerNo,
          success: function (result) {
            fn(result);
          },
          error: function () {
            fn(false);
          }
        });
      },
      /**
       * @description 换一个代付密钥 - 代付密钥
       * @param fn {function} 回调函数
       * @returns true=验证码正确, false=验证码错误
       */
      dsChangeKeys: function (fn) {
        utils.ajax({
          url: 'dsChangeKeys.action',
          type: 'post',
          success: function (result) {
            fn(result);
          },
          error: function () {
            fn(false);
          }
        });
      },
      /**
       * @description 检测入网手机号是否存在 - 商户入网
       * @param phone {string} 手机号码
       * @param fn {function} 回调函数
       * @returns true=存在, false=不存在
       */
      checkCustomerPhone: function (phone, fn) {
        if (!phone || phone.length !== 11) {
          fn(false);
          return;
        }
        utils.ajax({
          url: 'checkPhone.action?phone=' + phone,
          success: function (msg) {
            fn(msg === true || msg === 'true' || msg === 'TRUE');
          },
          error: function () {
            if (debug) {
              fn(false);
            } else {
              fn(true);
            }
          }
        });
      },
      /**
       * @description 检测入网全称是否存在 - 商户入网
       * @param fullName {string} 全称
       * @param fn {function} 回调函数
       * @returns 'error'=网络错误, false=未检测到商户全称, 其他=商户全称
       */
      checkCustomerFullName: function (fullName, fn) {
        // todo 
        if (!fullName) {
          fn(false);
          return;
        }
        utils.ajax({
          url: 'checkCustomerName.action?customer.fullName=' + fullName,
          dataType: 'json',
          success: function (msg) {
            var accInfo = JSON.parse(msg);
            if (accInfo.fullName !== 'FALSE') {
              fn(accInfo.fullName);
            } else {
              fn(false);
            }
          },
          error: function () {
            if (debug) {
              fn('FALSE');
            } else {
              fn('error');
            }
          }
        });
      },
      /**
       * @description 检测操作员账号(手机号)是否存在 - 操作员新增
       * @param phone {string} 手机号码
       * @param fn {function} 回调函数
       * @returns false=操作员已存在, true=不存在
       */
      checkOperatorPhone: function (phone, fn) {
        if (!phone || phone.length !== 11) {
          fn(false);
          return;
        }
        utils.ajax({
          url: 'operVerifymsg.action?phone=' + phone,
          success: function (msg) {
            fn(msg === true || msg === 'true' || msg === 'TRUE');
          },
          error: function () {
            if (debug) {
              fn(true);
            } else {
              fn(false);
            }
          }
        });
      },
      /**
       * @description 获取操作员类型下拉列表 - 操作员新增/修改
       * @param fn {function} 回调函数
       * @returns [{'name': '', 'id': ''},...]格式数组
       */
      operatorType: function (fn) {
        utils.ajax({
          url: 'getRole.action',
          success: function (msg) {
            try {
              var data = JSON.parse(msg);
              fn(data);
            } catch (error) {
              fn([]);
            }
          },
          error: function () {
            if (debug) {
              fn([
                {
                  name: '操作员1',
                  id: 'op1'
                },
                  {
                  name: '操作员2',
                  id: 'op2'
                },
              ]);
            } else {
              fn([]);
            }
          }
        });
      },
      /**
       * @description 检测角色名称是否重复 - 角色新增/修改
       * @param name {string} 角色名称
       * @param fn {function} 回调函数
       * @returns true=不重复, false=重复
       */
      checkRoleName: function (name, fn) {
        if (!name) {
          fn(false);
          return;
        }
        utils.ajax({
          url: 'checkRoleName.action?name=' + encodeURI(encodeURI(name)),
          success: function (msg) {
            fn(msg === true || msg === 'true' || msg === 'TRUE');
          },
          error: function () {
            if (debug) {
              fn(true);
            } else {
              fn(false);
            }
          }
        });
      },
      /**
       * @description 检测入网简称是否存在 - 商户入网
       * @param shortName {string} 简称
       * @param fn {function} 回调函数
       * @returns 'error'=网络错误, false=未检测到商户简称, 其他=商户简称
       */
      checkCustomerSortName: function (shortName, fn) {
        // todo 
        if (!shortName) {
          fn(false);
          return;
        }
        utils.ajax({
          url: 'checkCustomerName.action?customer.shortName=' + shortName,
          dataType: 'json',
          success: function (msg) {
            var accInfo = JSON.parse(msg);
            if (accInfo.shortName !== 'FALSE') {
              fn(accInfo.shortName);
            } else {
              fn(false);
            }
          },
          error: function () {
            if (debug) {
              fn('FALSE');
            } else {
              fn('error');
            }
          }
        });
      },
      /**
       * @description 获取账户余额 - 提现
       * @param fn {function} 回调函数
       * @returns 成功返回base64图片, false则失败
       */
      accountBalance: function (fn) {
        utils.ajax({
          url: 'accountBalanceNo.action',
          type: 'post',
          dataType : 'json',
          success: function(balance) {
            fn(balance);
          },
          error: function() {
            fn(false);
          }
        });
      },
      /**
       * @description 获取欢迎页二维码 - 欢迎页-查看二维码
       * @param fn {function} 回调函数
       * @returns 成功返回base64图片, false则失败
       */
      findQrImage: function (fn) {
        utils.ajax({
          url: 'getQrCode.action',
          type: 'post',
          dataType : 'json',
          success: function(data) {
            // 先申请水牌
            if (data == '{"data":"false","type":3}') {
              fn(null, null);
              return;
            }
            var json = JSON.parse(data);
            fn(json['data'], json['No']);
          },
          error: function() {
            fn(false);
          }
        });
      },
      /**
       * @description 根据交易订单获取二维码 - 设备管理-查看二维码
       * @param payNumber {string} 交易订单
       * @param fn {function} 回调函数
       * @returns 成功返回base64图片, false则失败
       */
      findImgForPayNo: function (payNumber, fn) {
        if (!payNumber) {
          fn(false);
          return;
        }
        utils.ajax({
          url: 'getQRcode.action',
          type: 'post',
          data: "customerPayNo=" + payNumber,
          dataType : 'json',
          success: function(data) {
            var json = JSON.parse(data);
            fn(json['data'], json['No']);
          },
          error: function() {
            fn(false);
          }
        });
      },
      /**
       * @description 获取上级费率 - 商户入网/修改
       * @returns 'false'=服务商不是没有下级, 'null'=异常, [.]=费率数组
       */
      findParentFees: function(fn) {
        utils.ajax({
          url: 'queryAgentFeeAction.action',
          type: 'post',
          success: function (fees) {
            fn(eval(fees));
          },
          error: function () {
            if (debug) {
              fn([
                {
                  feeType: 'RATIO',
                  fee: 0.004,
                  maxFee: 0.03,
                  minFee: 0.02,
                  productType: 'B2C',
                  profitType: 'FIXED_PROFIT',
                  profitRatio: 20,
                  status: 'TRUE'
                }
              ]);
            } else {
              fn('null');
            }
          }
        });
      },
      /**
       * @description 获取省份数组 - 商户入网/代理商入网
       * @param fn {function} 回调函数
       * @returns 返回 省份数组, 否则null
       */
      queryProvList: function (fn) {
        utils.ajax({
          url: 'ajaxQueryDictionaryByTypeCode.action?dictionaryTypeRanged.code=PROVINCE_TYPE',
          type: 'post',
          dataType: 'json',
          success: function (provList) {
            try {
              var data = eval(provList);
              fn(data);
            } catch (error) {
              fn(null);
            }
            
          },
          error: function () {
            fn(null);
          }
        });
      },
      /**
       * @description 获取城市数组 - 商户入网/代理商入网
       * @param provCode {string} 省份编码
       * @param fn {function} 回调函数
       * @returns 返回 城市数组, 否则null
       */
      queryCityList: function (provCode, fn) {
        if (!provCode || provCode == '') {
          fn(null);
        }
        utils.ajax({
          url: 'ajaxQueryDictionaryByTypeCode.action?dictionaryTypeRanged.code=' + provCode,
          type: 'post',
          dataType: 'json',
          success: function (cityList) {
            try {
              var data = eval(cityList);
              fn(data);
            } catch (error) {
              fn(null);
            }
            
          },
          error: function () {
            fn(null);
          }
        });
      },
      /**
       * @description 获取银行列表- 商户入网/结算卡修改
       * @param bankName {string} 银行名称(模糊查询)
       * @param bankCode {string} 银行编号(比如: 农业银行=ABC)
       * @param sabkBankFlag {string} 是否获取总行 0=不获取, 1=获取总行
       * @param fn {function} 回调函数
       * @returns
       */
      banks: function (bankName, bankCode, sabkBankFlag, fn) {
        if (!$.isFunction(fn)) {
          console.warn('获取银行列表失败, 参数错误!');
          return;
        }
        if (sabkBankFlag != '0' && sabkBankFlag != '1') {
          console.warn('获取银行列表失败', 'sabkBankFlag(只能为0或者1):', sabkBankFlag);
          return;
        }

        bankCode = bankCode || '';

        utils.ajax({
          url: 'toCachecenterCnaps.action',
          type: 'post',
          data: 'word=' 
        	  + encodeURI(encodeURI(bankName.replace(/\s*/g, ''))) 
        	  + '&providerCode=' 
        	  + bankCode
        	  + '&clearBankLevel='
        	  + sabkBankFlag,
          dataType: 'json',
          success: function (data) {
            if (!utils.isArray(data)) {
              fn([]);
              return;
            }
            // 规范化数据
            data = $.map(data, function(item) {
              return {
                // 银行名称
                label : item.name,
                value : item.name,
                // 银行编码
                code : item.code,
                // 行号, 比如103100000026=北京农业银行xx清算中心的行号
                sabkCode : item.clearingBankCode,
                // 银行提供方编码 比如: 农业银行=ABC
                bankCode : item.providerCode
              };
            })

            fn(data);
          },
          error: function () {
            if (debug) {
              fn([
                {
                  label: '武汉建设银行1',
                  value: '武汉建设银行1',
                  code: 'CCB',
                  sabkCode: '??',
                  bankCode: 'CCB'
                },
                {
                  label: '武汉建设银行2',
                  value: '武汉建设银行2',
                  code: 'CCB',
                  sabkCode: '??',
                  bankCode: 'CCB'
                },
                {
                  label: '武汉建设银行3',
                  value: '武汉建设银行3',
                  code: 'CCB',
                  sabkCode: '??',
                  bankCode: 'CCB'
                },
                {
                  label: '武汉建设银行4',
                  value: '武汉建设银行4',
                  code: 'CCB',
                  sabkCode: '??',
                  bankCode: 'CCB'
                },
                {
                  label: '武汉建设银行5',
                  value: '武汉建设银行5',
                  code: 'CCB',
                  sabkCode: '??',
                  bankCode: 'CCB'
                },
                {
                  label: '武汉建设银行6',
                  value: '武汉建设银行6',
                  code: 'CCB',
                  sabkCode: '??',
                  bankCode: 'CCB'
                },
              ]);
            } else {
              fn([]);
            }
          }
        });
      },
      /**
       * @description 获取银行卡号信息 - 商户入网/结算卡修改
       * @param bankCard {string} 银行卡号
       * @param fn {function} 回调函数
       * @returns null=银行卡号信息获取失败
       */
      bankCardInfo: function (bankCard, fn) {
        if (!bankCard || bankCard == '' || !$.isFunction(fn)) {
          console.warn('获取银行卡号信息 参数错误!:');
          return;
        }
        utils.ajax({
          url: 'toCachecenterIin.action',
          type: 'post',
          data: "cardNo=" + bankCard,
          dataType: 'json',
          success: function (iin) {
            fn(iin);
          },
          error: function () {
            if (debug) {
              setTimeout(function() {
                fn({providerCode: "ABC", cardType: "DEBIT"});
              }, 2000);
            } else {
              fn(null);
            }
          }
        });
      },
      /**
       * @description 获取总行信息 - 商户入网/结算卡修改
       * @param bankCode {string} 银行编号, 比如农业=ABC
       * @param fn {function} 回调函数
       * @returns
       */
      bossBankInfo: function (bankCode, fn) {
        if (!bankCode || bankCode == '' || !$.isFunction(fn)) {
          console.warn('获取获取总行信息 参数错误!:');
          return;
        }
        utils.ajax({
          url: 'toCachecenterCnaps.action',
          type: 'post',
          data: 'word=' 
        	  + ''
        	  + '&providerCode=' 
        	  + bankCode
        	  + '&clearBankLevel='
        	  + 1,
          dataType: 'json',
          success: function (iin) {
            fn(iin);
          },
          error: function () {
            if (debug) {
              fn([{"code":"103100000026","providerCode":"ABC","name":"中国农业银行","clearingBankCode":"103100000026"}]);
            } else {
              fn(null);
            }
          }
        });
      },
      /**
       * @description 获取上次登陆信息 - 欢迎页
       * @param fn {function} 回调函数
       */
      lastloginInfo: function(fn) {
        utils.ajax({
          url: 'lastloginInfo.action',
          type: 'get',
          dataType:"json",
          success: function(loginInfo) {
            fn(loginInfo);
          },
          error: function() {
            if (debug) {
              fn({
                loginIp: '127.0.0.1',
                loginTime: '2017-10-19 13:52:03'
              });
            } else {
              fn(false);
            }
          }
        });
      },
      /**
       * @description 获取账户信息 - 欢迎页
       * @param fn {function} 回调函数
       */
      accountInfo: function(fn) {
        utils.ajax({
          url: 'queryAccountAction.action',
          type: 'get',
          dataType:"json",
          success: function(data) {
            var info = JSON.parse(data);
            fn(info);
          },
          error: function() {
            if (debug) {
              fn({
                // 账户状态
                status: 'NORMAL',
                // 账户编号
                userNo: '',
                // 可用余额
                amount: 100,
                // 账户余额
                balance: 100,
                // 在途余额
                transitBalance: 0,
                // 冻结余额
                freezeBalance: 0,
                // 开户时间
                openDate: '2016/11/8 上午11:13:32'
              });
            } else {
              fn(false);
            }
          }
        });
      },
      /**
       * @description 获取今日数据 - 欢迎页
       * @param fn {function} 回调函数
       */
      dayData: function(fn) {
        utils.ajax({
          url: 'initDate.action',
          type: 'post',
          dataType:"json",
          success: function(info) {
            fn(info);
          },
          error: function() {
            if (debug) {
              fn({
                resMap: {
                  // 总笔数
                  counts: 30,
                  // 总金额
                  sum_amount: 600
                },
                dfMap: {
                  // 总笔数
                  counts: 30,
                  // 总金额
                  sum_amount: 600
                }
              });
            } else {
              fn(false);
            }
          }
        });
      },
      /**
       * @description 获取账号最大最小金额限制 - 提现
       * @param fn {function} 回调函数
       */
      dfConfig: function(fn) {
        utils.ajax({
          url: 'findDfConfigById.action',
          type: 'post',
          success: function(result) {
            fn(JSON.parse(result));
          },
          error: function() {
            if (debug) {
              fn({
                maxAmount: 30000,
                minAmount: 0.01
              });
            } else {
              fn(false);
            }
          }
        });
      },
      /**
       * @description 发送短信验证码 - 提现密码
       * @param flag {string} 复核状态 flag=FALSE(当前已经开通), flag=TRUE(当前未开通)
       * @param fn {function} 回调函数
       * @returns error=发送验证码失败, true=发送成功
       */
      sendVerifyCode: function (flag, fn) {
        // if (flag !== 'FALSE' && flag !== 'TRUE') {
        //   console.warn('发送短信验证码失败, 参数错误!');
        //   fn(false);
        // }
        utils.ajax({
          url: 'sendVerifyCode.action',
          type: 'post',
          data: "type=" + flag,
          success: function (result) {
            fn(result);
          },
          error: function () {
            fn(false);
          }
        });
      },
      /**
       * @description 开始提现 - 提现
       * @param amount {string} 提现金额
       * @param fn {function} 回调函数
       * @returns  SUCCESS=提现成功, HANDLEDING=提现处理中, UNKNOWN=未知结果, FAILED=提现失败
       */
      drawCash: function (amount, fn) {
        utils.ajax({
          url: 'drawCash.action',
          type: 'post',
          data: "amount=" + amount,
          success: function (result) {
            fn(result);
          },
          error: function () {
            if (debug) {
              fn('SUCCESS');
            } else {
              fn('FAILED');
            }
            
          }
        });
      },
      /**
       * @description 充值 - 充值
       * @param serializeData {string} 参数
       * @param fn {function} 回调函数
       * @returns
       */
      recharge: function (serializeData, fn) {
        utils.ajax({
          url: 'rechargeSign.action',
          type: 'post',
          data: serializeData,
          dataType: "json",
          success: function (result) {
            fn(result);
          },
          error: function () {
            fn(false);
          }
        });
      },
      /**
       * @description 更新手机复核状态 - 提现密码
       * @param flag {string} 复核状态 flag=FALSE(当前已经开通), flag=TRUE(当前未开通)
       * @param verifyCode {string} 验证码
       * @param fn {function} 回调函数
       * @returns
       */
      updateUsePhoneCheck: function (flag, verifyCode, fn) {
        // if (flag !== 'FALSE' && flag !== 'TRUE') {
        //   console.warn('发送短信验证码失败, 参数错误!');
        //   fn(false);
        // }
        utils.ajax({
          url: 'updateUsePhoneCheck.action?' + Math.random(),
          type: 'post',
          data : {
            'flag' : flag,
            'verifyCode' : verifyCode
				  },
          success: function (msg) {
            fn(msg);
          },
          error: function () {
            fn('error');
          }
        });
      },
      /**
       * @description 执行批量代付 - 批量代付
       * @param args {string} 表单序列化参数
       * @param fn {function} 回调函数
       * @returns  SUCCESS=提现成功, HANDLEDING=提现处理中, UNKNOWN=未知结果, FAILED=提现失败
       */
      doBatchApply: function (args, fn) {
        utils.ajax({
          url: 'dfUploadBatchApply.action',
          type: 'post',
          data: args,
          success: function (result) {
            fn(result);
          },
          error: function () {
            fn(false);
          }
        });
      },
      /**
       * @description 检测复核密码是否正确- 付款审核
       * @param auditPassword {string} 复核密码
       * @param fn {function} 回调函数
       * @returns true=复核密码正确, false=复核密码错误
       */
      checkAuditPassword: function (auditPassword, fn) {
        utils.ajax({
          url: 'applyVerify.action',
          type: 'post',
          dataType: "json",
          data: "auditPassword=" + auditPassword,
          success: function (msg) {
            fn(msg == true);
          },
          error: function () {
            if (debug) {
              fn(true);
            } else {
              fn(false);
            }
          }
        });
      },
      /**
       * @description 检测复核密码是否正确- 付款审核
       * @param oldPassword {string} 复核密码
       * @param fn {function} 回调函数
       * @returns true=复核密码正确, false=复核密码错误
       */
      checkCustomerAuditPassword: function (oldPassword, fn) {
        utils.ajax({
          url: 'applyVerify.action',
          type: 'post',
          dataType: "json",
          data: "oldPassword=" + oldPassword,
          success: function (msg) {
            fn(msg == true);
          },
          error: function () {
            if (debug) {
              fn(true);
            } else {
              fn(false);
            }
          }
        });
      },
      /**
       * @description 付款审核- 付款审核
       * @param cbs {string} 订单列表, 由逗号隔开
       * @param flag {string} 标志用于判断通过审核还是审核拒绝 FALSE=审核拒绝
       * @param fn {function} 回调函数
       * @returns 'error'=ajax错误
       */
      remitAudit: function (cbs, flag, fn) {
        utils.ajax({
          url: 'dfBatchAudit.action',
          type: 'post',
          dataType: "json",
          data: {
            'code': cbs,
            'flag': flag
          },
          success: function (orderResult) {
            fn(orderResult);
          },
          error: function () {
            if (debug) {
              fn({
                // 结果状态, 1=成功, 0=成功一部分和失败一部分
                result: 1,
                // 成功金额
                success: 20,
                // 成功订单数组
                successFlows: [],
                // 失败金额
                fail: 1,
                // 失败订单数组
                failFlows: []
              });
            } else {
              fn('error');
            }
          }
        });
      },
  };
  window.Api = Api;
})();