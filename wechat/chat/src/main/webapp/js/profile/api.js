(function() {
  var Api = window.Api || {};
  // 测试模式, ajax-error 函数会返回模拟数据以供测试 
  var debug = true;
  Api.boss = {
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
       * @description 获取短信验证码- 付款审核
       * @param type {string} 验证码类型 0=付款审核验证码
       * @param fn {function} 回调函数
       * @returns true=发送成功, false=发送失败
       */
      sendVerifyCode: function (type, fn) {
        utils.ajax({
          url: 'sendVerifyCode.action',
          type: 'post',
          dataType: "json",
          data: {'type':  type},
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
          data: {'verifyCode':  verifyCode},
          success: function (flag) {
            fn(flag == 1);
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
       * @description 检测账户是否开启手机验证 - 付款审核
       * @param auditPassword {string} 复核密码
       * @param fn {function} 回调函数
       * @returns 1=手机短信验证, 'error'=ajax错误, 其他等于密码验证
       */
      usePhoneCheck: function (auditPassword, fn) {
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
       * @description 付款审核- 付款审核
       * @param cbs {string} 订单列表, 由逗号隔开
       * @param flag {string} 标志用于判断通过审核还是审核拒绝 FALSE=审核拒绝
       * @param fn {function} 回调函数
       * @returns 'error'=ajax错误
       */
      remitAudit: function (cbs, flag, fn) {
        utils.ajax({
          url: 'remitAudit.action',
          type: 'post',
          dataType: "json",
          data: {
            'code': cbs,
            'flag': flag
          },
          success: function (data) {
            var response = JSON.parse(data);
            fn(response);
          },
          error: function () {
            if (debug) {
              fn({
                // 结果状态, 1=成功, 2=成功一部分和失败一部分
                result: 1,
                // 成功笔数
                success: 20,
                // 成功金额
                successFlows: 560,
                // 失败笔数
                fail: 1,
                // 失败金额
                failFlows: 10
              });
            } else {
              fn('error');
            }
          }
        });
      },
      /**
       * @description 根据商户编号获取商户全称- 新增商户交易配置
       * @param customerNo {string} 商户编号
       * @param fn {function} 回调函数
       * @returns 'FALSE'=商户全称不存在, 其他=商户全称
       */
      findCustomerFullName: function (customerNo, fn) {
        if (!customerNo) {
          fn(false);
          return;
        }
        utils.ajax({
          url: 'checkCustomerNoOfFullName.action?customerNo=' +customerNo,
          type: 'post',
          success: function (customerFullName) {
            fn(customerFullName);
          },
          error: function () {
            if (debug) {
              fn('模拟商户名称');
            } else {
              fn('FALSE');
            }
          }
        });
      },
      /**
       * @description 检测商户号是否存在 - 新增费率
       * @param customerNo {string} 商户号
       * @param fn {function} 回调函数
       * @returns true=商户存在, false=商户不存在
       */
      checkCustomeNo: function (customerNo, fn) {
        if (!customerNo) {
          fn(false);
          return;
        }
        utils.ajax({
          url: 'getAccInfo.action?owner_id=' + customerNo,
          success: function (msg) {
            fn(msg != 'false');
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
       * @description 根据用户编号检测代付配置是否存在 - 新增用户代付配置
       * @param ownerId {string} 用户编号
       * @param fn {function} 回调函数
       * @returns false=代付配置不存在, true=代付配置存在
       */
      checkDfConfig: function (ownerId, fn) {
        if (!ownerId) {
          fn(false);
          return;
        }
        utils.ajax({
          url: 'checkDfConfig.action?owner_id=' + ownerId,
          success: function (msg) {
            fn(msg == 'false');
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
       * @description 检测手机号是否重复 - 新增用户代付配置
       * @param phone {string} 手机号
       * @param fn {function} 回调函数
       * @returns true=手机号重复, false=手机号不重复
       */
      checkDfPhone: function (phone, fn) {
        if (!phone || phone.length !== 11) {
          fn(true);
          return;
        }
        utils.ajax({
          url: 'phoneVerifymsg.action?phone=' + phone,
          success: function (msg) {
            fn(msg != 'true');
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
       * @description 获取操作员类型下拉列表 - 操作员新增/修改
       * @param fn {function} 回调函数
       * @returns [{'name': '', 'id': ''},...]格式数组
       */
      operatorType: function (fn) {
        utils.ajax({
          url: 'getRole.action',
          success: function (msg) {
            var data = JSON.parse(msg);
            fn(data);
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
       * @description 获取路由模板下拉列表 - 商户审核/商户入网
       * @param productType {string} 产品类型
       * @param fn {function} 回调函数
       * @returns [{'name': '', 'code': ''},...]格式数组
       */
      routerTemplate: function (productType, fn) {
        utils.ajax({
          url: 'findRouterTemplates.action?interfaceType=' + productType,
          type: 'post',
          success: function (interfacePolicyBeans) {
            fn(interfacePolicyBeans);
          },
          error: function () {
            if (debug) {
              fn([
                {
                  name: productType + '模板1',
                  code: 'productType1'
                }, 
                  {
                  name: productType + '模板2',
                  code: 'productType2'
                },
              ]);
            } else {
              fn([]);
            }
          }
        });
      },
      /**
       * @description 获取接口提供方信息下拉列表 - 新增/修改接口信息
       * @param fn {function} 回调函数
       * @returns [{'shortName': '', 'code': ''},...]格式数组
       */
      findAllProvider: function (fn) {
        utils.ajax({
          url: 'findAllProviderAction.action',
          type: 'post',
          success: function (providerList) {
            fn(providerList);
          },
          error: function () {
            fn([]);
          }
        });
      },
      /**
       * @description 获取支付接口下拉列表 - 接口请求查询
       * @param fn {function} 回调函数
       * @returns
       */
      findAllInterface: function (fn) {
        utils.ajax({
          url: 'findAllInterface.action',
          type: 'post',
          dataType: 'json',
          success: function (interfaceInfoBeanList) {
            fn(interfaceInfoBeanList);
          },
          error: function () {
            if (debug) {
              fn([
                {
                  shortName: '银行1',
                  code: 'bank1'
                }, 
                  {
                  shortName: '银行2',
                  code: 'bank2'
                },
              ]);
            } else {
              fn([]);
            }
          }
        });
      },
      /**
       * @description 获取账户信息 - 新增代付配置
       * @param ownerId {String} 用户编号
       * @param fn {function} 回调函数
       * @returns false=该账户不存在, 其他赠与账户信息
       */
      accInfo: function (ownerId, fn) {
        utils.ajax({
          url: 'getAccInfo.action?owner_id=' + ownerId,
          type: 'post',
          dataType: 'json',
          success: function (info) {
            fn(info);
          },
          error: function () {
            if (debug) {
              fn({
                // 账户全称
                fullName: '账户全名',
                // 代理商编号 (如果账户是代理商类型才有)
                agentNo: 'C0001',
                // 商户编号 (如果账户是商户类型才有)
                customerNo: 'C0001'
              });
            } else {
              fn(false);
            }
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

        utils.ajax({
          url: 'toCachecenterCnaps.action',
          type: 'post',
          data: 'word=' 
        	  + encodeURI(encodeURI(bankName.replace(/\s*/g, ''))) 
        	  + '&providerCode=' 
        	  + bankCode || ''
        	  + '&clearBankLevel='
        	  + sabkBankFlag,
          dataType: 'json',
          success: function (data) {

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
          url: 'toCachecenterCnaps.action',
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
  };
  window.Api = Api;
})();