/// <reference path="D:\资料\XueYou\DefinitelyTyped\jquery\jquery.d.ts" />
(function() {

  /**
   * 检查Ajax是否返回异常
   */
  function isAjaxError(data, tips) {
    if (!data || (data.indexOf && data.indexOf('异常') != -1)) {
      console.warn(tips, ' -Ajax返回数据异常!');
      console.log(data);
      return true;
    }
    return false;
  };

  window.Api = {
  };

  window.Api['vip'] = {

      /**
       * @description 获取银行卡号信息 - 商户入网
       * @param phone {string} 手机号
       * @param fn {function} 回调函数
       * @returns
       */
      bankCardInfo: function(bankCard, fn) {
        if (!bankCard || bankCard == '' || !$.isFunction(fn)) {
          console.warn('Api.vip.bankCardInfo: 参数错误! bankCard:',bankCard);
          return;
        }
        Ajax.greedy({
          url : context +  '/gateway/toCachecenterIin',
          type : 'post',
          dataType: 'json',
          data : "cardNo=" + bankCard,
          success: function(iin) {
              if (isAjaxError(iin, 'Api.vip.bankCardInfo:')) {
                  fn(null);
                  return;
              }
             fn(iin);
          },
          error: function() {
            console.warn('Api.vip.bankCardInfo: Ajax获取失败...');
            fn(null);
          }
        });
      },
      /**
       * @description 获取银行列表 - 商户入网
       * @param bankName {string} 银行名称(模糊查询)
       * @param bankCode {string} 银行编号(比如: 农业银行=ABC)
       * @param sabkBankFlag {string} 是否获取总行 0=不获取, 1=获取总行
       * @param fn {function} 回调函数
       * @returns
       */
      banks: function(bankName, bankCode, sabkBankFlag, fn) {
        if (!bankCode || bankCode == '' || !$.isFunction(fn)) {
          console.warn('Api.vip.banks: 获取银行列表失败, 参数错误!', 'bankCode:', bankCode);
          return;
        }
        if (sabkBankFlag != '0' && sabkBankFlag != '1') {
          console.warn('Api.vip.banks: 获取银行列表失败, 参数错误!', 'sabkBankFlag(只能为0或者1):', sabkBankFlag);
          return;
        }

        Ajax.greedy({
          url : context + '/gateway/toCachecenterCnaps',
          type : 'post',
          dataType : 'json',
          data : 'word=' 
        	  + encodeURI(encodeURI(bankName.replace(/\s*/g, ''))) 
        	  + '&providerCode=' 
        	  + bankCode
        	  + '&clearBankLevel='
        	  + sabkBankFlag,
          success: function(data) {
            if (isAjaxError(data, 'Api.vip.banks:')) {
              fn([]);
              return;
            }
            
            data = $.map(data, function(item) {
              return {
                // 银行名称
                label : item.name,
                value : item.name,
                // 银行编码
                code : item.code,
                // 总行编码
                sabkCode : item.clearingBankCode,
                // 银行提供方编码 比如: 农业银行=ABC
                bankCode : item.providerCode
              };
            })
            fn(data);
          },
          error: function() {
            console.warn('Api.vip.banks: Ajax获取失败...');
            fn([]);
          }
        });
      },
      /**
       * @description 获取总行信息 - 商户入网
       * @param bankCode {string} 银行编号(比如: 农业银行=ABC)
       * @param fn {function} 回调函数
       * @returns
       */
      bankCoreInfo: function(bankCode, fn) {
        if (!bankCode || bankCode == '' || !$.isFunction(fn)) {
          console.warn('Api.vip.bankCoreInfo: 获取银行列表失败, 参数错误!', 'bankCode:', bankCode);
          return;
        }
        Ajax.greedy({
          url : context + '/gateway/toCachecenterCnaps',
          type : 'post',
          dataType : 'json',
          async : false,
          data : 'word=&providerCode=' 
              + bankCode
              + '&clearBankLevel=1',
          success: function(data) {
            if (isAjaxError(data, 'Api.vip.bankCoreInfo:') || data.length < 1) {
              fn([]);
              return;
            }
            
            fn(data[0]);
          },
          error: function() {
            console.warn('Api.vip.bankCoreInfo: Ajax获取失败...');
            fn([]);
          }
        });
      }

  };


})();
