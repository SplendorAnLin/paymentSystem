(function() {
  var Api = window.Api || {};
  // // test: 测试模式, ajax-error 函数会返回模拟数据以供测试 , 发布时候设置为false
  var debug = false;
  Api.boss = {
      /**
       * @description 检测Mcc是否重复 - 新增Mcc
       * @param mcc {string} mcc号
       * @param fn {function} 回调函数
       * @returns false=不重复
       */
      checkMcc: function (mcc, fn) {
        if (!mcc) {
          fn(false);
          return;
        }
        utils.ajax({
          url: 'mccAjaxByMcc.action?mcc=' + mcc,
          dataType: 'json',
          success: function (msg) {
            fn(msg);
          },
          error: function () {
            fn(false);
          }
        });
      },
      /**
       * @description 获取服务商简称 - 安卓OEM修改
       * @param agentNo {string} 服务商编号
       * @param fn {function} 回调函数
       * @returns null=服务商不存在
       */
      findAgentShortName: function (agentNo, fn) {
        utils.ajax({
          url: 'queryAgentShortNameByAgentNo.action?agentNo=' + agentNo,
          success: function (shortName) {
            fn(shortName);
          },
          error: function () {
            fn(null);
          }
        });
      },
      /**
       * @description 检测商户的产品是否已开通 - 新增商户交易配置
       * @param customerNo {string} 商户编号
       * @param productType {string} 产品类型
       * @param fn {function} 回调函数
       * @returns 'false'=不重复 'true'=重复
       */
      checkCustProduct: function (customerNo, productType, fn) {
        if (!customerNo || !productType) {
          fn(false);
          return;
        }
        utils.ajax({
          url: 'queryProductTypeExistsByCustNo.action?customerNo=' + customerNo + '&productType=' + productType,
          dataType: 'json',
          success: function (msg) {
            fn(msg);
          },
          error: function () {
            fn('true');
          }
        });
      },
      /**
       * @description 检测冻结编号是否存在 - 解冻账户
       * @param freezNo {string} 冻结编号
       * @param fn {function} 回调函数
       * @returns 返回 ''则不存在编号, 否则返回解冻编号对应的账户编号
       */
      checkFreezNo: function (freezNo, fn) {
        if (!freezNo) {
          fn('');
          return;
        }
        utils.ajax({
          url: 'checkAccountJCFrozen.action?freezeNo=' + freezNo,
          dataType: 'json',
          success: function (accNo) {
            fn(accNo);
          },
          error: function () {
            fn('');
          }
        });
      },
      /**
       * @description 检测代收配置是否存在 - 代收配置
       * @param ownerId {string} 用户编号
       * @param fn {function} 回调函数
       * @returns 'false'=不存在, 'true'=存在
       */
      checkReceiveConfig: function (ownerId, fn) {
        if (!ownerId) {
          fn('false');
          return;
        }
        utils.ajax({
          url: 'receiveConfigExists.action?ownerId=' + ownerId,
          dataType: 'json',
          success: function (msg) {
            fn(msg);
          },
          error: function () {
            fn('false');
          }
        });
      },
      /**
       * @description 检测商户编号是否存在- POS出库
       * @param customerNo {string} 商户号
       * @param fn {function} 回调函数
       * @returns false=不重复
       */
      checkPosCustomeNo: function (customerNo, fn) {
        if (!customerNo) {
          fn(false);
          return;
        }
        utils.ajax({
          url: 'queryCustomerByCustNo.action?customer.customerNo=' + encodeURI(encodeURI(customerNo)),
          dataType: 'json',
          type: 'POST',
          success: function (msg) {
            fn(msg);
          },
          error: function () {
            fn(false);
          }
        });
      },
      /**
       * @description 检测网点是否存在 - 修改POS
       * @param customerNo {string} 商户编号
       * @param shopNo {string} 网点号
       * @param fn {function} 回调函数
       * @returns false=不重复
       */
      checkShopNo: function (customerNo, shopNo, fn) {
        if (!customerNo || !shopNo) {
          fn(false);
          return;
        }
        utils.ajax({
          url: 'ajaxQueryShopByShopNo.action?customerNo=' + customerNo + '&shopNo=' + shopNo,
          dataType: 'json',
          success: function (msg) {
            fn(msg);
          },
          error: function () {
            fn(false);
          }
        });
      },
      /**
       * @description 查找网点列表 - POS出库
       * @param customerNo {string} 商户编号
       * @param fn {function} 回调函数
       * @returns null=商户编号或网点不存在, []=网点数组
       */
      findShopList: function (customerNo, fn) {
        if (!customerNo) {
          fn(null);
          return;
        }
        utils.ajax({
          url: 'queryShopListByCustomerNo.action?customerNo=' + customerNo,
          dataType: 'json',
          success: function (shopList) {
            fn(shopList);
          },
          error: function () {
            fn(null);
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
          url: 'customerCheckPhone.action?phone=' + phone,
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
       * @description 检测入网手机号是否存在 - 服务商入网
       * @param phone {string} 手机号码
       * @param fn {function} 回调函数
       * @returns true=存在, false=不存在
       */
      checkAgentPhone: function (phone, fn) {
        if (!phone || phone.length !== 11) {
          fn(false);
          return;
        }
        utils.ajax({
          url: '"agentCheckPhone".action?phone=' + phone,
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
       * @description 根据商户编号获取简称- 交易订单-结果
       * @param customerNo {string} 商户编号
       * @param fn {function} 回调函数
       * @returns
       */
      getCustomerInfo: function (customerNo, fn) {
        if (!customerNo) {
          fn(false);
          return;
        }
        utils.ajax({
          url: 'findByCustNoAction.action',
          dataType: 'json',
          data: 'customer.customerNo=' + customerNo,
          type: 'post',
          success: function (accInfo) {
            fn(accInfo);
          },
          error: function () {
            fn(false);
          }
        });
      },
      /**
       * @description 根据商户编号获取简称- 交易订单-结果
       * @param customerNo {string} 商户编号
       * @param fn {function} 回调函数
       * @returns
       */
      getCustShortName: function(customerNo, fn) {
        if (!customerNo) {
          fn('');
          return;
        }
        utils.ajax({
          url: 'findShortNameByCustomerNo.action',
          data: 'customerNo=' + customerNo,
          type: 'post',
          success: function (shrotName) {
            fn(shrotName);
          },
          error: function () {
            fn('');
          }
        });
      },
      /**
       * @description 检测入网全称是否存在 - 服务商入网
       * @param fullName {string} 全称
       * @param fn {function} 回调函数
       * @returns 'error'=网络错误, false=未检测到服务商全称, 其他=服务商全称
       */
      checkAgentFullName: function (fullName, fn) {
        if (!fullName) {
          fn(false);
          return;
        }
        utils.ajax({
          url: 'checkAgentName.action?agent.fullName=' + fullName,
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
              fn({fullName: '全称'});
            } else {
              fn('error');
            }
          }
        });
      },
      /**
       * @description 检测入网简称是否存在 - 服务商入网
       * @param shortName {string} 简称
       * @param fn {function} 回调函数
       * @returns 'error'=网络错误, false=未检测到服务商简称, 其他=服务商简称
       */
      checkAgentSortName: function (shortName, fn) {
        // todo 
        if (!shortName) {
          fn(false);
          return;
        }
        utils.ajax({
          url: 'checkAgentName.action?agent.shortName=' + shortName,
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
              fn({shortName: '简称'});
            } else {
              fn('error');
            }
          }
        });
      },
      /**
       * @description 检测商户编号是否存在 - 商户路由
       * @param customerNo {string} 商户编号
       * @param fn {function} 回调函数
       * @returns 'true'=存在, 'false'=不存在
       */
      checkCustomerExist: function (customerNo, fn) {
        // todo 
        if (!customerNo) {
          fn('false');
          return;
        }
        utils.ajax({
          url: 'customerNoBool.action?customerNo=' + customerNo,
          dataType: 'json',
          success: function (msg) {
            fn(msg);
          },
          error: function () {
            fn('false');
          }
        });
      },
      /**
       * @description 获取商户全称 - 商户路由
       * @param customerNo {string} 商户编号
       * @param fn {function} 回调函数
       * @returns 其他=全称, 'false'=商户不存在
       */
      findCustomerFullName: function (customerNo, fn) {
        // todo 
        if (!customerNo) {
          fn('false');
          return;
        }
        utils.ajax({
          url: 'fullNameByCustNo.action?customerNo=' + customerNo,
          dataType: 'json',
          success: function (fullName) {
            fn(fullName);
          },
          error: function () {
            fn('false');
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
       * @description 查询Mcc是否重复 - Mcc管理
       * @param mccNo {string} Mcc编号
       * @param fn {function} 回调函数
       * @returns 'false'=找到Mcc, null=没找到
       */
      checkMcc: function (mccNo, fn) {
        if (!mccNo) {
          fn(null);
          return;
        }
        utils.ajax({
          url: 'mccAjaxByMcc.action?mcc=' + mccNo,
          type: 'post',
          success: function (msg) {
            fn(msg);
          },
          error: function () {
            fn(null);
          }
        });
      },
      /**
       * @description 检测商户号是否存在 - 新增商户费率
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
       * @description 检测商户号是否存在 - 新增网点
       * @param customerNo {string} 商户号
       * @param fn {function} 回调函数
       * @returns true=商户存在, false=商户不存在
       */
      queryCustomerByCustNo: function(customerNo, fn) {
        if (!customerNo) {
          fn('false');
          return;
        }
        utils.ajax({
          url: 'queryCustomerByCustNo.action?customer.customerNo=' + customerNo,
          success: function (msg) {
            fn(msg);
          },
          error: function () {
            fn('false');
          }
        });
      },
      /**
       * @description 检测接口编号是否重复 - 接口对账配置
       * @param interFaceCode {string} 商户号
       * @param fn {function} 回调函数
       * @returns 'FALSE'=存在, 'TRUE'=不存在
       */
      checkInterfaceCode: function (interFaceCode, fn) {
        if (!interFaceCode) {
          fn('FALSE');
          return;
        }
        utils.ajax({
            url: 'checkInterfaceCode.action?code=' + interFaceCode,
          success: function (msg) {
            fn(msg);
          },
          error: function () {
            fn('FALSE');
          }
        });
      },
      /**
       * @description 根据服务商编号获取服务商全称 - 新增服务商费率
       * @param agentNo {string} 服务商号
       * @param fn {function} 回调函数
       * @returns 
       */
      checkAgentNo: function (agentNo, fn) {
        if (!agentNo) {
          fn(false);
          return;
        }
        var url = 'findAgentFullNameByAgentNo.action?agent.agentNo=' + $.trim(agentNo);
        utils.ajax({
          url: encodeURI(encodeURI(url)),
          type: 'post',
          success: function (fullName) {
            fn(fullName);
          },
          error: function () {
            if (debug) {
              fn('全称');
            } else {
              fn('false');
            }
          }
        });
      },
      /**
       * @description 检测服务商产品费率 - 新增服务商费率
       * @param agentNo {string} 服务商号
       * @param productType {string} 产品类型
       * @param fn {function} 回调函数
       * @returns 'TRUE'=可以添加该产品的费率, 'EXISTS_REMIT'=未开通代付不能添加假日付费率, 其他=该费率已存在,不能添加
       */
      checkAgentFee: function (agentNo, productType, fn) {
        if (!agentNo || !productType) {
          fn(false);
          return;
        }
        var param = utils.tpl('agentFee.agentNo={{agentNo}}&agentFee.productType={{productType}}', {
          agentNo: agentNo,
          productType: productType
        });
        utils.ajax({
          url: 'checkAgentFeeAction.action',
          type: 'post',
          data: param,
          success: function (result) {
            fn(result);
          },
          error: function () {
            if (debug) {
              fn('TRUE');
            } else {
              fn(false);
            }
          }
        });
      },
      /**
       * @description 检测商户产品费率 - 新增商户费率
       * @param customerNo {string} 商户
       * @param productType {string} 产品类型
       * @param fn {function} 回调函数
       * @returns 'TRUE'=可以添加该产品的费率, 'EXISTS_REMIT'=未开通代付不能添加假日付费率, 其他=该费率已存在,不能添加
       */
      checkCustomerFee: function (customerNo, productType, fn) {
        if (!customerNo || !productType) {
          fn(false);
          return;
        }
        var param = utils.tpl('customerFee.customerNo={{customerNo}}&customerFee.productType={{productType}}', {
          customerNo: customerNo,
          productType: productType
        });
        utils.ajax({
          url: 'checkCustomerFeeAction.action',
          type: 'post',
          data: param,
          success: function (result) {
            fn(result);
          },
          error: function () {
            if (debug) {
              fn('TRUE');
            } else {
              fn(false);
            }
          }
        });
      },
      /**
       * @description 获取代理商费率 - 商户入网
       * @param agentNo {string} 代理商编号
       * @returns 'false'=服务商不是没有下级, 'null'=异常, [.]=费率数组
       */
      findAgentFees: function(agentNo, fn) {
        if (!agentNo) {
          fn('null');
          return;
        }
        utils.ajax({
          url: 'queryAgentFeeAllByAgentNo.action?agentNo=' + agentNo,
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
       * @description 根据商户编号获取上级编号 - 商户费率
       * @param customerNo {string} 商户编号
       * @returns 'false'=没有上级
       */
      findParentNo: function(customerNo, fn) {
        if (!customerNo) {
          fn('false');
          return;
        }
        utils.ajax({
          url: 'queryAgentNoByCustomerNo.action?customerNo=' + customerNo,
          type: 'post',
          success: function (parentNo) {
            fn(parentNo);
          },
          error: function () {
            fn('false');
          }
        });
      },
      /**
       * @description 根据服务商编号获取上级编号 - 服务商费率
       * @param agentNo {string} 服务商编号
       * @returns 'false'=没有上级
       */
      findParentNoByAgentNo: function(agentNo, fn) {
        if (!agentNo) {
          fn('false');
          return;
        }
        utils.ajax({
          url: 'queryParenIdByAgentNo.action?agentNo=' + agentNo,
          type: 'post',
          success: function (parentNo) {
            fn(parentNo);
          },
          error: function () {
            fn('false');
          }
        });
      },
      /**
       * @description 检测字典编号是否存在 - 子类类型添加/修改
       * @param dictionaryCode {string} 字典编号
       * @param fn {function} 回调函数
       * @returns true=存在, false=不存在
       */
      checkDictionaryCode: function (dictionaryCode, fn) {
        if (!dictionaryCode) {
          fn(false);
          return;
        }
        utils.ajax({
          url: 'queryDictionaryTypeByCodeAjax.action?dictionaryTypeRanged.code=' + encodeURI(encodeURI(dictionaryCode)),
          type: 'post',
          success: function (result) {
            fn(result);
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
       * @description 获取账户余额信息 - 欢迎页
       * @param fn {function} 回调函数
       */
      accBalance: function(fn) {
        utils.ajax({
          url: 'countAccBalance.action',
          type: 'get',
          dataType:"json",
          success: function(data) {
            var info = JSON.parse(data);
            fn(info);
          },
          error: function() {
            if (debug) {
              fn({
                AGENT: {
                  // 总金额
                  accsum: 500,
                  // 冻结金额
                  freeze: 20,
                  // 在途金额
                  transit: 10
                },
                CUSTOMER: {
                  accsum: 500,
                  freeze: 20,
                  transit: 10
                }
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
       * @description 获取微信菜单数据 - 微信菜单管理
       * @param fn {function} 回调函数
       * @returns false=获取失败
       */
      wxMenuData: function(fn) {
        utils.ajax({
          url: 'wxMenu.action',
          type : "post",
          dataType: 'json',
          success: function(data) {
            fn(JSON.parse(data));
          },
          error: function() {
            if (debug) {
              fn({
                button: [
                  {
                    name: '下载与签到',
                    sub_button: [
                      {
                        name: '我要升级',
                        type: 'view',
                        url: '升级'
                      },
                      {
                        name: '每日签到',
                        type: 'view',
                        url: '签到'
                      },
                      {
                        name: 'ipad版',
                        type: 'view',
                        url: 'ipad'
                      },
                      {
                        name: '手机版',
                        type: 'view',
                        url: 'phone'
                      }
                    ]
                  },
                  {
                    'name': '精品美剧',
                    sub_button: [
                      {
                        name: '越狱',
                        type: 'view',
                        url: 'www.youku.com/越狱'
                      },
                      {
                        name: '绝命毒师',
                        type: 'view',
                        url: 'www.youku.com/绝命毒师'
                      },
                    ]
                  }
                ]
              });
            } else {
              fn(false);
            }
          }
        });
      },
      /**
       * @description 保存微信菜单数据 - 微信菜单管理
       * @param menuData {Object} 微信菜单数据
       * @param fn {function} 回调函数
       * @returns false=保存失败, true=保存成功, 'error'=网络异常
       */
      wxMenuSave: function(menuData) {
        utils.ajax({
          url: 'wxMenuSave.action',
          type : "post",
          dataType: 'json',
          data : {"data":JSON.stringify(menuData)},
          success: function(result) {
            fn(result === true || result === 'TRUE');
          },
          error: function() {
            if (debug) {
              fn(true);
            } else {
              fn('error');
            }
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
          data: {'customerPayNo': payNumber},
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
            fn(msg == 'true');
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
       * @returns {BCM: '交通银行'} 对象格式
       */
      findAllProvider: function (fn) {
        utils.ajax({
          url: 'findAllProviderAction.action',
          type: 'post',
          success: function (providerList) {
            fn(providerList);
          },
          error: function () {
            if (debug) {
              fn(
                {
                  '121212': '中国聚合',
                  'BOB': '北京银行',
                  'CIB': '兴业银行'
                }
              );
            } else {
              fn({});
            }
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
       * @description 根据接口名称获取接口编号 - 新增接口对账单配置
       * @param interfaceName {string} 接口名称
       * @param fn {function} 回调函数
       * @returns null=获取失败, 否则返回接口编号
       */
      checkInterfaceName: function (interfaceName, fn) {
        if (!interfaceName) {
          fn(null);
        }
        utils.ajax({
          url: 'tocheckInterfaceName.action?interfaceNameCode=' + encodeURI(encodeURI(interfaceName)),
          type: 'post',
          dataType: 'json',
          success: function (interfaceCode) {
            fn(interfaceCode);
          },
          error: function () {
            if (debug) {
              fn('A10000');
            } else {
              fn(null);
            }
          }
        });
      },
      /**
       * @description 检测路由是否重复 - 代收路由新增/修改
       * @param code {string} 路由编码
       * @param fn {function} 回调函数
       * @returns null=获取失败, 否则返回接口编号
       */
      checkReceiverRouter: function (code, fn) {
        if (!code) {
          fn(false);
        }
        utils.ajax({
          url: 'receiveConfigFindByIdBool.action?routeConfigBean.code=' + encodeURI(encodeURI(code)),
          type: 'post',
          dataType: 'json',
          success: function (msg) { 
            fn(msg === false || msg === 'false');
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
       * @description 根据接口类型查询接口信息 - 代收路由新增/修改
       * @param interfaceType {string} 接口名称
       * @param fn {function} 回调函数
       * @returns 'error'=获取失败, 否则返回接口信息数组
       */
      interfaceInfoCodeQueryBy: function (interfaceType, fn) {
        if (!interfaceType) {
          fn(null);
        }
        utils.ajax({
          url: 'interfaceInfoCodeQueryBy.action',
          type: 'post',
          dataType: 'json',
          data : 'interfaceTypes=' + interfaceType,
          success: function (interfaceInfo) {
            fn(eval(interfaceInfo));
          },
          error: function () {
            if (debug) {
              fn(['MOCK-RECEIVE:代收测试接口', '测试:11111']);
            } else {
              fn('error');
            }
          }
        });
      },
      /**
       * @description 更改账户状态  - 账户信息
       * @param accountNo {String} 账户编号
       * @param status {String} 账户状态
       * @param remark {String} 更改原因
       * @param userNo {String} 用于ID
       * @param fn {function} 回调函数
       * @returns
       */
      accountModify: function(accountNo, status, remark, userNo, fn) {
        if (!accountNo || !status || !userNo)
          return;

        var url = 'updateAcountStatusAction.action?accountModify.accountStatus='
                  + status + '&accountModify.accountNo='
                  + accountNo + '&accountModify.remark='
                  + remark + '&accountModify.userNo='
                  + userNo;

        utils.ajax({
          // 更改原因有中文所以要编码一下
          url: encodeURI(encodeURI(url)),
          type: 'post',
          success: function(msg) {
            fn(true, msg);
          },
          error: function() {
            if (debug) {
              fn(true, '账户状态更改成功!');
            } else {
              fn(false, 'error');
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
       * @description 检测商户是否存在路由配置 - 新增商户路由
       * @param customerNo {String} 商户编号
       * @param fn {function} 回调函数
       * @returns false=该账户不存在, 其他赠与账户信息
       */
      custRouteByCustomerNo: function (customerNo, fn) {
        utils.ajax({
          url: 'ajaxQueryCustRouteByCustomerNo.action?customerNo=' + customerNo,
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
       * @description 获取可用接口信息 - 路由模板新增/修改
       * @param fn {function} 回调函数
       * @returns 'error'=接口信息获取失败
       */
      interFaceInfo: function (fn) {
        utils.ajax({
          url: 'findAllInterfaceInfoAction.action',
          type: 'post',
          dataType: 'json',
          success: function (interfaceInfos) {
            fn(interfaceInfos);
          },
          error: function () {
            if (debug) {
              fn([
                {
                  // 接口编码
                  code: 'ylzf',
                  // 接口名称
                  name: '快捷支付',
                  // 接口类型 AUTHPAY=认证支付
                  type: 'AUTHPAY'
                },
                {
                  // 接口编码
                  code: 'test-1',
                  // 接口名称
                  name: '测试1',
                  // 接口类型 AUTHPAY=认证支付
                  type: 'AUTHPAY'
                },
                {
                  // 接口编码
                  code: 'test-2',
                  // 接口名称
                  name: '测试2',
                  // 接口类型
                  type: 'B2C'
                },
                {
                  // 接口编码
                  code: 'test-3',
                  // 接口名称
                  name: '个人网银测试',
                  // 接口类型
                  type: 'B2C'
                }
              ]);
            } else {
              fn('error');
            }
          }
        });
      },
      /**
       * @description 根据名称寻找菜单 - 运营功能管理
       * @param menuName {string} 菜单名称
       * @param fn {function} 回调函数
       * @returns
       */
      findMenuByName: function (menuName, fn) {
        if (!menuName) {
          fn([]);
          return;
        }
        utils.ajax({
          url: 'findMenuByName.action',
          type: 'post',
          data:  'menuName=' + encodeURI(encodeURI(menuName)),
          dataType: 'json',
          success: function (data) {
            var menusData = JSON.parse(data);
            menusData = $.map(menusData, function(item) {
              return {
                label: item.name,
                name : item.name,
                value : item.name,
                id : item.id,
                level: item.level
              };
            });
            fn(menusData);
          },
          error: function () {
            if (debug) {
              fn([
                {
                  label: '菜单1',
                  value: 'cd1'
                },
                {
                  label: '菜单2',
                  value: 'cd2'
                },
                {
                  label: '菜单2',
                  value: 'cd2'
                }
              ]);
            } else {
              fn([]);
            }
          }
        });
      },
      /**
       * @description 根据名称寻找菜单 - 商户功能管理
       * @param menuName {string} 菜单名称
       * @param fn {function} 回调函数
       * @returns
       */
      findCustomerMenuByName: function (menuName, fn) {
        if (!menuName) {
          fn([]);
          return;
        }
        utils.ajax({
          url: 'custFindMenuByName.action',
          type: 'post',
          data:  'menuName=' + encodeURI(encodeURI(menuName)),
          dataType: 'json',
          success: function (data) {
            var menusData = JSON.parse(data);
            menusData = $.map(menusData, function(item) {
              return {
                label: item.name,
                name : item.name,
                value : item.name,
                id : item.id,
                level: item.level
              };
            });
            fn(menusData);
          },
          error: function () {
            if (debug) {
              fn([
                {
                  label: '菜单1',
                  value: 'cd1'
                },
                {
                  label: '菜单2',
                  value: 'cd2'
                },
                {
                  label: '菜单2',
                  value: 'cd2'
                }
              ]);
            } else {
              fn([]);
            }
          }
        });
      },
      /**
       * @description 根据名称寻找菜单 - 服务商功能管理
       * @param menuName {string} 菜单名称
       * @param fn {function} 回调函数
       * @returns
       */
      findAgentMenuByName: function (menuName, fn) {
        if (!menuName) {
          fn([]);
          return;
        }
        utils.ajax({
          url: 'agentFindMenuByName.action',
          type: 'post',
          data:  'menuName=' + encodeURI(encodeURI(menuName)),
          dataType: 'json',
          success: function (data) {
            var menusData = JSON.parse(data);
            menusData = $.map(menusData, function(item) {
              return {
                label: item.name,
                name : item.name,
                value : item.name,
                id : item.id,
                level: item.level
              };
            });
            fn(menusData);
          },
          error: function () {
            if (debug) {
              fn([
                {
                  label: '菜单1',
                  value: 'cd1'
                },
                {
                  label: '菜单2',
                  value: 'cd2'
                },
                {
                  label: '菜单2',
                  value: 'cd2'
                }
              ]);
            } else {
              fn([]);
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
       * @description 获取商户编号和产品类型的组合是否存在- 认证新增
       * @param customerNo {string} 商户编号
       * @param busiType {string} 产品类型
       * @param fn {function} 回调函数
       * @returns false商户不存在, 否则 {status: 'false', fullName: 'XueYou'} status代表此组合是否已经存在
       */
      authQueryCustomerNoBool: function (customerNo, busiType, fn) {
        utils.ajax({
          url: 'authQueryCustomerNoBool.action?authConfigBean.customerNo=' + customerNo + '&authConfigBean.busiType=' + busiType,
          type: 'post',
          dataType: 'json',
          success: function (result) {
            fn(result);
          },
          error: function () {
            fn(false);
          }
        });
      },
      /**
       * @description 根据OEM获取服务商信息- 广告新增
       * @param OEMNumber {string} OEM编号
       * @param fn {function} 回调函数
       * @returns false=OEM不存在商户, {agentNo: 服务商编号, agentName: 服务商名称}
       */
      findAgentByOem: function (OEMNumber, fn) {
        utils.ajax({
          url: 'findAgentByOem.action?oem=' + OEMNumber,
          type: 'post',
          success: function (result) {
            fn(result);
          },
          error: function () {
            fn('false');
          }
        });
      },
      /**
       * @description 根据服务商编号获取OEM和服务商名称- 广告新增
       * @param OEMNumber {string} OEM编号
       * @param fn {function} 回调函数
       * @returns false=OEM不存在商户, {agentNo: 服务商编号, agentName: 服务商名称}
       */
      findAgentByAgentNo: function (agentNo, fn) {
        utils.ajax({
          url: 'findAgentByAgentNo.action?agentNo=' + agentNo,
          type: 'post',
          success: function (result) {
            fn(result);
          },
          error: function () {
            fn('false');
          }
        });
      },
      
      
  };
  window.Api = Api;
})();