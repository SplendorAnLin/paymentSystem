// 通用脚本
(function() {
  var general = {};

  // 更改费率类型
  general.changeFeeType = function(feeType, parent, change) {
    var rote = $('.fee', parent);
    var minfee = $('.minfee', parent);
    var maxfee = $('.maxfee', parent);
    var disabled = parent.attr('disabled');
    // 在服务商没有上级的情况下,分润类型与费率类型一致
    //if (!window.agentHasParnet)
      //$('.profit-type', parent).selectForValue(feeType + '_PROFIT');

    if (feeType === 'RATIO') {
      // 百分比
      rote.attr('placeholder', '格式为:0.002=0.2%');
      maxfee.attr('placeholder', '此处为封顶值');
      minfee.attr('placeholder', '格式为:0.2=2角');
      if (!disabled) {
        maxfee.removeAttr('disabled');
        minfee.removeAttr('disabled');
      }
      maxfee.removeClass('ignoreMinMax').closest('.item').show();
      minfee.removeClass('ignoreMinMax').closest('.item').show();
    } else {
      // 固定
      rote.attr('placeholder', '格式为：（1、2）个位数');
      maxfee.attr('placeholder', '格式为：（1、2）个位数');
      minfee.attr('placeholder', '格式为：（1、2）个位数');
      if (!disabled) {
        maxfee.attr('disabled', 'disabled');
        minfee.attr('disabled', 'disabled');
      }
      maxfee.addClass('ignoreMinMax').closest('.item').hide();
      minfee.addClass('ignoreMinMax').closest('.item').hide();
    }
    general.changeProfitType(feeType, parent);
    if ($.isFunction(change))
      change(rote, minfee, maxfee, feeType);
  };

  // 更改分润类型
  general.changeProfitType = function(profitType, parent) {
    // todo...
  };

  // 更改产品类型
  general.changeProductType = function(productType) {
    // 产品类型更改了, 如果存在上级, 则费率类型, 分润类型根据上级锁定
    if (window.fees && window.fees[productType]) {
      // 根据上级验证
      general.verifyForParent();
    } else {
      // 使用默认验证
      general.verifyForDefault();
    }

  };

  // 更改入网账户类型
  general.changeAccountType = function(accountType, parent) {

    // 获取企业信息块
    var companyInfo = $('.companyInfo', parent);

    switch(accountType) {
      // 个人
      case 'INDIVIDUAL':
        // 禁用内部元素
        companyInfo.disableInput();
        companyInfo.slideUp();
        // 法人身份证 标签改为 身份证号
        $('.cardText', parent).text('身份证号');
        break;
      // 公司
      case 'OPEN':
        // 启用内部元素
        companyInfo.enablelInput();
        companyInfo.slideDown();
        // 身份证号 标签改为 法人身份证
        $('.cardText', parent).text('法人身份证');
        break;
      // 个体工商户
      case 'MERCHANT':
        // 启用内部元素
        companyInfo.enablelInput();
        companyInfo.slideDown();
        // 身份证号 标签改为 法人身份证
        $('.cardText', parent).text('法人身份证');
        break;
      default:
        break;
    }
  };

  // 增加服务商费率/分润等验证
  general.addAgentVerify = function() {
    // 检测服务商手机号是否重复
    Compared.add('checkagentPhone', function(val, params, ele, ansyFn) {
      // 服务商和商户公用这个API
      Api.agent.checkAgentPhone(val,function(exist) {
        ansyFn(Compared.toStatus(exist, '手机号重复'));
      });
    });
    // 费率验证
    Compared.add('fee', function(val, params, ele, ansyFn) {
    	// 用于兼容费率与入网
      var parent = ele.closest('.productItem').length == 0 ? $('form') : ele.closest('.productItem');
      // 获取产品类型
      var productType = parent.attr('data-productType');
      if (!productType) {
        productType = $('#productType').val();
      }
      // 获取费率类型
      var feeType = parent.find('.fee-type').val();
      // 捕获上级没有此费率类型
      if (window.fees && window.fees[productType] == undefined) {
        return Compared.toStatus(false, '上级未开通此业务!');
      }
      // 根据父级验证
      if (window.fees) {
        if (feeType === 'RATIO') {
          // 百分比 限定范围在 [大于上级费率 ~ 0.05] 之间
          return Compared.toStatus(Compared.rules['range'](val, [fees[productType].fee, 0.05]).status, fees[productType].fee +' ~ 0.05');
        } else {
          // 固定
          return Compared.toStatus(Compared.rules['range'](val, [fees[productType].fee, 100000]).status, fees[productType].fee +' ~ 100000');
        }
      } else {
        if (feeType === 'RATIO') {
          // 百分比 限定范围在 [0.001 ~ 0.05] 之间
          return Compared.toStatus(Compared.rules['range'](val, [0.001, 0.05]).status, '0.001 ~ 0.05');
        } else {
          // 固定
          return Compared.toStatus(true);
        }
      }
    });
    // 最低费率验证
    Compared.add('minfee', function(val, params, ele, ansyFn) {
    	// 用于兼容费率与入网
      var parent = ele.closest('.productItem').length == 0 ? $('form') : ele.closest('.productItem');
      // 获取产品类型
      var productType = parent.attr('data-productType');
      if (!productType) {
        productType = $('#productType').val();
      }
      // 获取费率类型
      var feeType = parent.find('.fee-type').val();
      // 捕获上级没有此费率类型
      if (window.fees && window.fees[productType] == undefined) {
        return Compared.toStatus(false, '上级未开通此业务!');
      }
      // 根据父级验证
      if (window.fees) {
        if (feeType === 'RATIO') {
          // 百分比 限定范围在 [大于上级费率 ~ 0.3] 之间
          return Compared.toStatus(Compared.rules['range'](val, [fees[productType].minFee, 100000]).status, fees[productType].minFee +' ~ 100000');
        } else {
          // 固定
          return Compared.toStatus(Compared.rules['range'](val, [fees[productType].minFee, 100000]).status, fees[productType].minFee +' ~ 100000');
        }
      } else {
        if (feeType === 'RATIO') {
          // 百分比 限定范围在 [0.0018 ~ 0.3] 之间
          // return Compared.toStatus(Compared.rules['range'](val, [0.0018, 0.3]).status, '0.0018 ~ 0.3');
          return Compared.toStatus(true);
        } else {
          // 固定
          return Compared.toStatus(true);
        }
      }
    });
    // 最高费率验证
    Compared.add('maxfee', function(val, params, ele, ansyFn) {
    	// 用于兼容费率与入网
      var parent = ele.closest('.productItem').length == 0 ? $('form') : ele.closest('.productItem');
      // 获取产品类型
      var productType = parent.attr('data-productType');
      if (!productType) {
        productType = $('#productType').val();
      }
      // 获取费率类型
      var feeType = parent.find('.fee-type').val();
      // 捕获上级没有此费率类型
      if (window.fees && window.fees[productType] == undefined) {
        return Compared.toStatus(false, '上级未开通此业务!');
      }
      // 根据父级验证
      if (window.fees) {
        if (feeType === 'RATIO') {
          // 百分比 限定范围在 [大于上级费率 ~ 0.3] 之间
          return Compared.toStatus(Compared.rules['range'](val, [fees[productType].maxFee, 100000]).status, fees[productType].maxFee +' ~ 100000');
        } else {
          // 固定
          return Compared.toStatus(Compared.rules['range'](val, [fees[productType].maxFee, 100000]).status, fees[productType].maxFee +' ~ 100000');
        }
      } else {
        if (feeType === 'RATIO') {
          // 百分比 限定范围在 [0.0018 ~ 0.3] 之间
          // return Compared.toStatus(Compared.rules['range'](val, [0.0018, 0.3]).status, '0.0018 ~ 0.3');
          return Compared.toStatus(true);
        } else {
          // 固定
          return Compared.toStatus(true);
        }
      }
    });
    // 分润比例验证
    Compared.add('profitratio', function(val, params, ele, ansyFn) {
    	// 用于兼容费率与入网
      var parent = ele.closest('.productItem').length == 0 ? $('form') : ele.closest('.productItem');
      // 获取产品类型
      var productType = parent.attr('data-productType');
      if (!productType) {
        productType = $('#productType').val();
      }
      // 获取分润类型
      var feeType = parent.find('.profit-type').val();
      // 捕获上级没有此费率类型
      if (window.fees && window.fees[productType] == undefined) {
        return Compared.toStatus(false, '上级未开通此业务!');
      }

      // 新需求, 分润比例不根据上级验证
      if (feeType === 'RATIO_PROFIT') {
        // 百分比 限定范围在 [0.001 ~ 0.05] 之间
        return Compared.toStatus(Compared.rules['range'](val, [0, 1]).status, '0 ~ 1');
      } else {
        // 固定
        return Compared.toStatus(true);
      }

      // 根据父级验证
      // if (window.fees) {
      //   if (feeType === 'RATIO_PROFIT') {
      //     // 百分比 限定范围在 [大于上级费率 ~ 0.05] 之间
      //     return Compared.toStatus(Compared.rules['range'](val, [fees[productType].profitRatio, 1]).status, fees[productType].profitRatio +' ~ 1');
      //   } else {
      //     // 固定
      //     return Compared.toStatus(Compared.rules['range'](val, [fees[productType].profitRatio, 100000]).status, fees[productType].profitRatio +' ~ 100000');
      //   }
      // } else {
      //   if (feeType === 'RATIO_PROFIT') {
      //     // 百分比 限定范围在 [0.001 ~ 0.05] 之间
      //     return Compared.toStatus(Compared.rules['range'](val, [0.01, 1]).status, '0.01 ~ 1');
      //   } else {
      //     // 固定
      //     return Compared.toStatus(true);
      //   }
      // }
    });
    // 检测服务商全称是否重复
    Compared.add('checkAgentFullName', function(val, params, ele, ansyFn) {
      Api.agent.checkAgentFullName(val,function(fullName) {
        ansyFn(Compared.toStatus(fullName === false, '服务商全称重复'));
      });
    });
    // 检测服务商简称是否重复
    var lastSortName = $('[checkAgentSortName]').val();
    Compared.add('checkAgentSortName', function(val, params, ele, ansyFn) {
      if (val == lastSortName && val != '')
        return Compared.toStatus(true);
      Api.agent.checkAgentSortName(val,function(fullName) {
        ansyFn(Compared.toStatus(fullName === false, '服务商简称重复'));
      });
    });

    // 根据上级费率进行验证
    general.feesForParent = function(callback) {
      Api.agent.findParentFees(function(fees) {
        if (!utils.isArray(fees) || fees.length == 0) {
          verifyForDefault();
          callback(false);
          return;
        }
        callback(true);
        // 过滤状态关闭的产品
        window.parentFeesInfo = [];
        window.fees = {};
        utils.each(fees, function(fee) {
          window.fees[fee.productType] = fee;
          window.parentFeesInfo.push(fee);
        });
        // 更改费率验证为以父级为准
        verifyForParent();
      });
    };
    
    // 切换费率验证-根据父级验证
    function verifyForParent() {
      if (!window.parentFeesInfo)
        return;

      // console.log('父级费率信息', parentFeesInfo);

      var productSelect = $('#productTypeSelect');
      if (productSelect.length == 0)
        productSelect = $('#productType');

      if (productSelect.length === 1 && productSelect.get(0).selectBox) {
        var selectBox = productSelect.get(0).selectBox;
        // 所有业务
        var options = selectBox.extract();
        utils.each(options, function(optionInfo) {
          var productType = optionInfo.value;
          var exist = false;
          utils.each(window.parentFeesInfo, function(fee) {
            if (fee.productType === productType)
              exist = true;
          });

          if (!exist) {
            // console.log('应为父级不存在此产品所以移除产品: ', productType);
            // 移除父级不存在的产品
            if (product)
              product.close(productType);
            // 禁用 开通业务下拉列表中父级不存在的产品
            var option = $('option[value="' + productType + '"]', productSelect);
            if (productSelect.val() === productType) {
              // 下拉列表选中父级不存在的产品, 则默认选一个父级存在的产品
              productSelect.selectForValue(window.parentFeesInfo[0].productType);
            }
            if (option.length > 0) {
              option.get(0).disabled = true;
              productSelect.renderSelectBox();
            }

          }

        });
      }

      
      // 将费率类型改为根父级一致
      utils.each(window.parentFeesInfo, function(fee) {
        var productItem = $('.productItem[data-producttype="' + fee.productType + '"]');
        // 兼容费率新增页面
        if (productItem.length == 0 && $('#productWrap').length == 0) {
          productItem = $('form');
        }
        if (productItem.length == 0) {
          return;
        }
        // 已关闭则忽略
        // if (productItem.attr('disabled'))
        //   return;
        if ($('#productType', productItem).length == 1 && $('#productType', productItem).val() !== fee.productType)
          return;
        $('.fee-type', productItem).readonlyInput(true);
        $('.fee-type', productItem).addClass('ignoreDisable').selectForValue(fee.feeType);
        // 模拟更改触发 更改费率类型回调
        $('.fee-type', productItem).change();
        // 分润类型
        $('.profit-type', productItem).readonlyInput(true);
        $('.profit-type', productItem).addClass('ignoreDisable').selectForValue(fee.profitType);
        // 模拟更改触发 更改费率类型回调
        $('.profit-type', productItem).change();
      });

      // 处理假日付特殊情况 上级未开通假日付则禁用是否开通假日付下拉列表
      if (!window.fees['HOLIDAY_REMIT']) {
        if (!product)
          return;
        // 移除假日付产品
        product.close('HOLIDAY_REMIT');
        // 只读下拉列表
        $('.holidayStatus').readonlyInput(true);
        $('.holidayStatus').removeClass('ignoreDisable').selectForValue('FALSE');
      } else {
        // 设置ignoreDisable类表示现在根据上级在验证
        $('.holidayStatus').addClass('ignoreDisable');
      }



    }

    general.verifyForParent = verifyForParent;

    // 切换费率验证-根据默认验证
    function verifyForDefault() {
      window.parentFeesInfo = null;
      window.fees = null;

      var productTypeSelect = $('#productTypeSelect');
      if (productTypeSelect.length == 0)
        productTypeSelect = $('#productType');
      if (productTypeSelect.length == 1) {
        // 解除 开通业务下拉列表中的禁用
        $('option', productTypeSelect).each(function() {
          $(this).enablelInput(true);
        });
        productTypeSelect.renderSelectBox();
      }

      // 解除费率类型的禁用
      $('.fee-type').each(function() {
        $(this).removeClass('ignoreDisable').readInput(true);
      });
      // 解除分润类型的禁用
      $('.profit-type').each(function() {
        $(this).removeClass('ignoreDisable').readInput(true);
      });

      // 解锁假日付下拉列表
      $('.holidayStatus').removeClass('ignoreDisable').readInput(true);

    }

    general.verifyForDefault = verifyForDefault;


    // todo: 此处在服务商系统入网时候需要改成上级服务商编号的输入框name
    $('[name="customer.agentNo"]').bind('requiredNot', function() {
      // 未填写上级服务商则使用默认验证
      if ($(this).val() === '')
        verifyForDefault();
    });

    // 用于费率新增, 监听产品下拉列表更改
    $('#productType').change(function() {
      general.changeProductType(this.value);
    });


  };

  // 增加商户费率/分润等验证
  general.addCustomerVerify = function() {

    // todo 最大/最小费率 暂时是 0.0018 ~ 0.3, 实际上根据产品类型这个区间会不一样

    // 费率验证
    Compared.add('fee', function(val, params, ele, ansyFn) {
    	// 用于兼容费率与入网
      var parent = ele.closest('.productItem').length == 0 ? $('form') : ele.closest('.productItem');
      // 获取产品类型
      var productType = parent.attr('data-productType');
      if (!productType) {
        productType = $('#productType').val();
      }
      // 获取费率类型
      var feeType = parent.find('.fee-type').val();
      // 捕获上级没有此费率类型
      if (window.fees && window.fees[productType] == undefined) {
        return Compared.toStatus(false, '上级未开通此业务!');
      }
      // 根据父级验证
      if (window.fees) {
        if (feeType === 'RATIO') {
          // 百分比 限定范围在 [大于上级费率 ~ 0.05] 之间
          return Compared.toStatus(Compared.rules['range'](val, [fees[productType].fee, 0.05]).status, fees[productType].fee +' ~ 0.05');
        } else {
          // 固定
          return Compared.toStatus(Compared.rules['range'](val, [fees[productType].fee, 100000]).status, fees[productType].fee +' ~ 100000');
        }
      } else {
        if (feeType === 'RATIO') {
          // 百分比 限定范围在 [0.001 ~ 0.05] 之间
          return Compared.toStatus(Compared.rules['range'](val, [0.001, 0.05]).status, '0.001 ~ 0.05');
        } else {
          // 固定
          return Compared.toStatus(true);
        }
      }
    });
    // 最低费率验证
    Compared.add('minfee', function(val, params, ele, ansyFn) {
    	// 用于兼容费率与入网
      var parent = ele.closest('.productItem').length == 0 ? $('form') : ele.closest('.productItem');
      // 获取产品类型
      var productType = parent.attr('data-productType');
      if (!productType) {
        productType = $('#productType').val();
      }
      // 获取费率类型
      var feeType = parent.find('.fee-type').val();
      // 捕获上级没有此费率类型
      if (window.fees && window.fees[productType] == undefined) {
        return Compared.toStatus(false, '上级未开通此业务!');
      }
      // 根据父级验证
      if (window.fees) {
        if (feeType === 'RATIO') {
          // 百分比 限定范围在 [大于上级费率 ~ 0.3] 之间
          return Compared.toStatus(Compared.rules['range'](val, [fees[productType].minFee, 100000]).status, fees[productType].minFee +' ~ 100000');
        } else {
          // 固定
          return Compared.toStatus(Compared.rules['range'](val, [fees[productType].minFee, 100000]).status, fees[productType].minFee +' ~ 100000');
        }
      } else {
        if (feeType === 'RATIO') {
          // 百分比 限定范围在 [0.0018 ~ 0.3] 之间
          // return Compared.toStatus(Compared.rules['range'](val, [0.0018, 0.3]).status, '0.0018 ~ 0.3');
          return Compared.toStatus(true);
        } else {
          // 固定
          return Compared.toStatus(true);
        }
      }
    });
    // 最高费率验证
    Compared.add('maxfee', function(val, params, ele, ansyFn) {
    	// 用于兼容费率与入网
      var parent = ele.closest('.productItem').length == 0 ? $('form') : ele.closest('.productItem');
      // 获取产品类型
      var productType = parent.attr('data-productType');
      if (!productType) {
        productType = $('#productType').val();
      }
      // 获取费率类型
      var feeType = parent.find('.fee-type').val();
      // 捕获上级没有此费率类型
      if (window.fees && window.fees[productType] == undefined) {
        return Compared.toStatus(false, '上级未开通此业务!');
      }
      // 根据父级验证
      if (window.fees) {
        if (feeType === 'RATIO') {
          // 百分比 限定范围在 [大于上级费率 ~ 0.3] 之间
          return Compared.toStatus(Compared.rules['range'](val, [fees[productType].maxFee, 100000]).status, fees[productType].maxFee +' ~ 100000');
        } else {
          // 固定
          return Compared.toStatus(Compared.rules['range'](val, [fees[productType].maxFee, 100000]).status, fees[productType].maxFee +' ~ 100000');
        }
      } else {
        if (feeType === 'RATIO') {
          // 百分比 限定范围在 [0.0018 ~ 0.3] 之间
          // return Compared.toStatus(Compared.rules['range'](val, [0.0018, 0.3]).status, '0.0018 ~ 0.3');
          return Compared.toStatus(true);
        } else {
          // 固定
          return Compared.toStatus(true);
        }
      }
    });
    // 检测商户手机号是否重复
    Compared.add('checkCustomerPhone', function(val, params, ele, ansyFn) {
      Api.agent.checkCustomerPhone(val,function(msg) {
        ansyFn(Compared.toStatus(msg, '手机号重复'));
      });
    });
    // 检测商户全称是否重复
    Compared.add('checkCustomerFullName', function(val, params, ele, ansyFn) {
      Api.agent.checkCustomerFullName(val,function(fullName) {
        ansyFn(Compared.toStatus(fullName === false, '商户全称重复'));
      });
    });
    // 检测商户简称是否重复
    var lastSortName = $('[checkCustomerSortName]').val();
    Compared.add('checkCustomerSortName', function(val, params, ele, ansyFn) {
      if (val == lastSortName && val != '')
        return Compared.toStatus(true);
      Api.agent.checkCustomerSortName(val,function(fullName) {
        ansyFn(Compared.toStatus(fullName === false, '商户简称重复'));
      });
    });

    // 填写服务商编号后根据上级费率改变验证
    Compared.add('checkParentFee', function(val, params, ele, ansyFn) {
      general.feesForParent(val, function(status) {
        ansyFn(Compared.toStatus(status, '服务商编号错误或服务商等级不匹配!'));
      });
    });


    // 根据上级费率进行验证
    general.feesForParent = function(callback) {
      Api.agent.findParentFees(function(fees) {
        if (!utils.isArray(fees) || fees.length == 0) {
          verifyForDefault();
          callback(false);
          return;
        }
        callback(true);
        // 过滤状态关闭的产品
        window.parentFeesInfo = [];
        window.fees = {};
        utils.each(fees, function(fee) {
          window.fees[fee.productType] = fee;
          window.parentFeesInfo.push(fee);
        });
        // 更改费率验证为以父级为准
        verifyForParent();
      });
    };
    


    // 切换费率验证-根据父级验证
    function verifyForParent() {
      if (!window.parentFeesInfo)
        return;

      // console.log('父级费率信息', parentFeesInfo);

      var productSelect = $('#productTypeSelect');
      if (productSelect.length == 0)
        productSelect = $('#productType');

      if (productSelect.length === 1 && productSelect.get(0).selectBox) {
        var selectBox = productSelect.get(0).selectBox;
        // 所有业务
        var options = selectBox.extract();
        utils.each(options, function(optionInfo) {
          var productType = optionInfo.value;
          var exist = false;
          utils.each(window.parentFeesInfo, function(fee) {
            if (fee.productType === productType)
              exist = true;
          });

          if (!exist) {
            // console.log('应为父级不存在此产品所以移除产品: ', productType);
            // 移除父级不存在的产品
            if (product)
              product.close(productType);
            // 禁用 开通业务下拉列表中父级不存在的产品
            var option = $('option[value="' + productType + '"]', productSelect);
            if (productSelect.val() === productType) {
              // 下拉列表选中父级不存在的产品, 则默认选一个父级存在的产品
              productSelect.selectForValue(window.parentFeesInfo[0].productType);
            }
            if (option.length > 0) {
              option.get(0).disabled = true;
              productSelect.renderSelectBox();
            }

          }

        });
      }

      
      // 将费率类型改为根父级一致
      utils.each(window.parentFeesInfo, function(fee) {
        var productItem = $('.productItem[data-producttype="' + fee.productType + '"]');
        // 兼容费率新增页面
        if (productItem.length == 0 && $('#productWrap').length == 0) {
          productItem = $('form');
        }
        if (productItem.length == 0) {
          return;
        }
        // todo 不能通过这种方式判断产品上级是否开通, 已关闭则忽略
        // if (productItem.attr('disabled'))
        //   return;
        if ($('#productType', productItem).length == 1 && $('#productType', productItem).val() !== fee.productType)
          return;
        $('.fee-type', productItem).readonlyInput(true);
        $('.fee-type', productItem).addClass('ignoreDisable').selectForValue(fee.feeType);
        // 模拟更改触发 更改费率类型回调
        $('.fee-type', productItem).change();
      });

      // 处理假日付特殊情况 上级未开通假日付则禁用是否开通假日付下拉列表
      if (!window.fees['HOLIDAY_REMIT']) {
        if (!product)
          return;
        // 移除假日付产品
        product.close('HOLIDAY_REMIT');
        // 只读下拉列表
        $('.holidayStatus').readonlyInput(true);
        $('.holidayStatus').removeClass('ignoreDisable').selectForValue('FALSE');
      } else {
        // 设置ignoreDisable类表示现在根据上级在验证
        $('.holidayStatus').addClass('ignoreDisable');
      }



    }

    general.verifyForParent = verifyForParent;

    // 切换费率验证-根据默认验证
    function verifyForDefault() {
      window.parentFeesInfo = null;
      window.fees = null;

      var productTypeSelect = $('#productTypeSelect');
      if (productTypeSelect.length == 0)
        productTypeSelect = $('#productType');
      if (productTypeSelect.length == 1) {
        // 解除 开通业务下拉列表中的禁用
        $('option', productTypeSelect).each(function() {
          $(this).enablelInput(true);
        });
        productTypeSelect.renderSelectBox();
      }

      // 解除费率类型的禁用
      $('.fee-type').each(function() {
        $(this).removeClass('ignoreDisable').removeClass('.ignoreDisable').readInput(true);
      });
      // 解锁假日付下拉列表
      $('.holidayStatus').removeClass('ignoreDisable').readInput(true);

    }

    general.verifyForDefault = verifyForDefault;




    // 服务商编号事件
    $('[name="customer.agentNo"]').bind('requiredNot', function() {
      // 未填写上级服务商则使用默认验证
      if ($(this).val() === '')
        verifyForDefault();
    });

    // 用于费率新增, 监听产品下拉列表更改
    $('#productType').change(function() {
      general.changeProductType(this.value);
    });


  }

  // 路由模板-根据产品类型设置路由模板
  general.routerTplForType = function(productType, parent) {
    // 路由模板下拉列表
    var select = $('.router-template', parent);
    // 没有路由模板就禁用此input
    if (!productType || select.length === 0) {

      return;
    }

    Api.agent.routerTemplate(productType, function(interfacePolicyBeans) {
      // 根据status状态, 过滤禁用的路由模板
      var interfacePolicys = [];
      utils.each(interfacePolicyBeans, function(bean) {
        if (bean.status === 'TRUE') {
          interfacePolicys.push(bean);
        }
      });
      if (interfacePolicys.length === 0) {
        // 没有路由模板则将2个隐藏输入框禁用
        $('.router-hide', parent).attr('disabled', 'disabled');
        select.attr('disabled', 'disabled');
        // 移除路由模板下拉列表的必填验证
        select.removeAttr('required');
        // 显示默认内容
        select.html('<option value="">无模板</option>').renderSelectBox();
        return;
      }
      var selectBox = select.get(0).selectBox;
      selectBox.options = optionArray(interfacePolicys, 'name', 'code');
    });

  };

  // 初始化路由模板
  general.routerTemplate = function(parent) {
    $('.productItem').each(function() {
      var productType = $(this).attr('data-productType');
      general.routerTplForType(productType, $(this));
    });
  };

  // 设置接口信息下拉列表
  general.infaceInfoSelect = function(infaceType, select) {
    // 查询接口信息, 并填入下拉列表
    Api.agent.interfaceInfoCodeQueryBy(infaceType, function(interfaceInfo) {
      if (!interfaceInfo)
        return;
      
      var infos = [];
      // 解析成对应格式
      utils.each(interfaceInfo, function(infoStr, i) {
        var info = infoStr.split(':');
        infos.push({
          label: info[1],
          value: info[0]
        });
      });
      
      // 导入下拉列表
      // 获取SelectBox组件实例
      var selectBox = window.getSelectBox(select);
      selectBox.options = window.optionArray(infos, 'label', 'value');

      // 初始下拉列表值
      select.selectForValue(select.attr('data-defaultValue'));
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


    // 开户行编号(银行卡号)
    var accountNo = $('#accountNo', form);
    // 账户名称
    var cardName = $('#cardName', form);
    // 账户类型(对公对私下拉列表)
    var accType = $('#accType', form);
    // 开户行名称(银行名称)
    var openBankName = $('#openBankName', form);
    // 银行码 [隐藏输入框, 通过获取银行卡号信息设置]
    var bankCode = $('#bankCode', form);
    // 开启总行复选框
    var sabkBankFlag = $('#sabkBankFlag', form).val(0);
    // 上一次银行卡号
    var lastAccountNo = accountNo.val();
    // 初始化银行名称
    var defaultBankName = null;
    // 卡类型(填写银行卡号后设置)
    var cardType = $('#cardType', form);
    // 联行号(选中一个银行后后设置)
    var sabkCode = $('#sabkCode', form);
    // 分行号
    var cnapsCode = $('#cnapsCode', form);
    // 分行名称
    var cnapsName = $('#cnapsName', form);

    // 必须元素验证
    if (accountNo.length !== 1 || cardName.length !== 1 || accType.length !== 1 || openBankName.length !== 1 || bankCode.length !== 1) {
      console.error('卡识别验证初始化失败: 参数不正确.', accountNo, cardName, accType, openBankName, bankCode);
      return;
    }


    // 获取账户类型 INDIVIDUAL=对私 OPEN=对公
    var getCustomerType = function() {
      return accType.val();
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
    sabkBankFlag.change(function() {
      window.changeFlag(this);
    });

    // 银行列表自动完成
    var autocomplete = new AutoComplete(openBankName, {
      // 数据源
      dataSource: function(val, ac, callback) {
        var type = getCustomerType();
        if (type != 'OPEN')
          openBankName.inputLoading();
        // 这里bankCode为空是对公情况下, 为空默认匹配查找所有银行
        Api.agent.banks(val, bankCode.val(), getBankFlag(), function(banks) {
          if (type != 'OPEN')
            openBankName.inputEnding();
          callback(banks, val);
        });
      },
      // 敲击延迟, 对于本地数据推荐0延迟
      delay: 300,
      // 是否空白匹配所有
      allowEmpy: false
    });
    // 选中银行 - 设置联行号
    openBankName.on(AutoComplete.Event.select, function(event, data) {
      Api.agent.bossBankInfo(bankCode.val(), function(iin) {
        if (iin && iin.length > 0) {
          // bankCode.val(data.bankCode);
          sabkCode.val(iin[0].clearingBankCode);
          cnapsCode.val(iin[0].code);
          cnapsName.val(iin[0].label);
        }
      });
    });


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

      Api.agent.bankCardInfo(card, function(iin) {
        if (iin !== null) {
          if (iin.cardType === 'CREDIT') {
            ansyFn(Compared.toStatus(false, '信用卡无法作为结算卡!'));
            bankCode.val('');
            cardType.val('');
            return;
          } else {
            bankCode.val(iin.providerCode);
            cardType.val(iin.cardType);
            // 银行卡号改变, 清空银行名称
            if (lastAccountNo != val) {
              openBankName.val('');
            }
            lastAccountNo = val;
          }
        } else {
          bankCode.val('');
          cardType.val('');
        }
        ansyFn(Compared.toStatus(iin !== null && iin.providerCode !== null, '银行卡号无效'));
      });
    });

    // 验证银行名称
    Compared.add('checkBankName', function(val, params, ele, ansyFn) {
      // 对公账户类型则不验证银行卡号, 初始化没有改变银行名称也不验证
      var type = getCustomerType();
      if (type === 'OPEN' || defaultBankName) {
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
      var type = getCustomerType();
      if (status === Compared.EnumStatus.ok) {
        if (ele.get(0).ValidtorComplete || type == 'OPEN') {
          if (openBankName.val() === tips) {
            openBankName.val('');
          }
          openBankName.removeAttr('disabled');
          if (type !== 'OPEN')
            openBankName.focus();
        }
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
    accType.change(function() {
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
      // 获取初始化的银行名称
      defaultBankName = openBankName.val();
      // 模拟切换账户类型, 切换对应验证
      accType.trigger('change');
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
    var btn = $(this);
    btn.ending();
    var url = btn.attr('data-action');
    var hookFn = btn.data('sumFn');

    utils.ajax({
      url: url,
      data: $('form').serialize(),
      error: function() {
        Messages.error('网络异常, 统计合计失败, 请稍后尝试...');
        btn.ending();
      },
      success: function(data) {
        btn.ending();
        if (data == null || data == 'null' || data == '') {
          Messages.error('统计合计失败, 请稍后尝试...');
          return;
        }
        if ($.isFunction(hookFn))
          hookFn(data, viewCount);
        viewCount.Alert('查看合计');
      }
    });

  });

})();

// 接口信息
(function() {

  var interfaceInfo = {
    // 绑定接口类型更改
    bindInfoType: function() {
      $('[name="infoBean.type"]').change(function() {
        // 是否实时下拉列表
        var isReal = $('[name="infoBean.isReal"]');
        var type = this.value;
        // 接口类型为付款才能使用是否实时
        if (type == 'REMIT') {
          isReal.enablelInput(true);
        } else {
          isReal.val('').disableInput(true);
        }

      }).trigger('change');
    }
  };

  window.interfaceInfo = interfaceInfo;

})();

// 代付审核
(function() {
  var dfAudit = {};

  // 增加检测复核密码ajax验证
  window.top.Compared.add('checkAuditPassword', function(val, params, ele, ansyFn) {
    Api.agent.checkAuditPassword(val, function(status) {
      ansyFn(Compared.toStatus(status, '复核密码错误'));
    });
  });

  // 增加短信验证码密码ajax验证
  window.top.Compared.add('checkVerifyCode', function(val, params, ele, ansyFn) {
    Api.agent.checkVerifyCode(val, function(status) {
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
    orders.each(function(i) {
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
        amount: utils.toFixed(td.eq(6).text(), 2),
        // 手续费
        fee: utils.toFixed(td.eq(7).text(), 2),
        // 总付金额 (付款金额+手续费)
        applyamount: utils.toFixed($(this).attr('applyamount'), 2)
      };
      infos.push(info);
      cbs = cbs + info.orderId + ',';
      totalAmount = totalAmount + info.applyamount;
    });
    // 去掉末尾逗号
    cbs = cbs.slice(0, cbs.length - 1);
    return {infos: infos, cbs: cbs, totalAmount: totalAmount}
  }

  // 从订单组的总金额
  function sumOrderAmount(orderInfos, orders) {
    var sum = 0;

    for (var i = 0; i < orders.length; ++i) {
      utils.each(orderInfos, function(info) {
        if (info.orderId === orders[i]) {
          sum += info.applyamount
        }
      })
    }

    return utils.toFixed(sum);
  };

  // 显示付款审核验证对话框对话框
  function passwordDialog(orderInfos) {
    var dialogElement = $('#accept-password');
    // 设置对话框内总笔数
    $('.count', dialogElement).text(utils.toFixed(orderInfos.infos.length, 0));
    // 设置对话框内总金额
    $('.amount', dialogElement).text(utils.toFixed(orderInfos.totalAmount, 2));
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
                doApplySubmit(orderInfos, 'TRUE', dialog);
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
    $('.count', dialogElement).text(utils.toFixed(orderInfos.infos.length, 0));
    // 设置对话框内总金额
    $('.amount', dialogElement).text(utils.toFixed(orderInfos.totalAmount, 2));
    // 清空上次输入验证码
    $('[name="verify-code"]', dialogElement).val();
    // 清空上次输入复核密码
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
                doApplySubmit(orderInfos, 'TRUE', dialog);
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
          Api.agent.sendVerifyCode(0, function(status) {
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
    $('.count', dialogElement).text(utils.toFixed(orderInfos.infos.length, 0));
    // 设置对话框内总金额
    $('.amount', dialogElement).text(utils.toFixed(orderInfos.totalAmount, 2));

    dialog = dialogElement.Confirm('审核拒绝', function() {
      doApplySubmit(orderInfos, '', dialog);
    });
  }

  // 付款审核
  function doApplySubmit(orderInfos, flag, dialog) {
    dialog.loading(true);
    Api.agent.remitAudit(orderInfos.cbs, flag, function(data) {
      if (data === 'error') {
        Messages.error('网络异常, 审核失败, 请稍后重试...');
        return;
      }
      var result = data.result;
      var keywords = flag == 'TRUE' ? '审核' : '拒绝';
      var msg = '';
      switch(result) {
        case 1:
          msg = '{{keywords}}成功 {{success}} 笔, 共 {{successFlows}} 元';
        break;
        default:
          msg = '{{keywords}}成功 {{success}} 笔, 共 {{successFlows}} 元</br>{{keywords}}失败 {{fail}} 笔, 共 {{failFlows}} 元';
        break;
      }
      // 参数格式化
      msg = utils.tpl(msg, {
        keywords: keywords,
        success: utils.toFixed(data.success, 0),
        successFlows: sumOrderAmount(orderInfos.infos, data.successFlows),
        fail: utils.toFixed(data.fail, 0),
        failFlows: sumOrderAmount(orderInfos.infos, data.failFlows)
      });

      if (flag != 'FALSE' && result == 2) {
        new window.top.Alert('为避免重复付款，请勿重复复核。', '提示', function() {
          dialog.close(null, true);
          window.RefresQueryResult();
          // window.location.reload();
        });
      } else {
        // 显示操作结果提示
        new window.top.Alert(msg, '操作结果', function() {
          dialog.close(null, true);
          window.RefresQueryResult();
          // window.location.reload();
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
    Api.agent.usePhoneCheck('', function(msg) {
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

  dfAudit.orderInfo = orderInfo;
  window.dfAudit = dfAudit;
})();

// 批量POS出库/绑定
(function() {
  var posBatch = {};

    // 检测代理商编号
    Compared.add('checkPosAgentNo', function(val, params, ele, ansyFn) {
      var agentNameInput = $(ele).closest('form').find('.agentName');
      utils.ajax({
    	  url: 'queryAgentExistsAndLevelOne.action?agentNo=' + val,
    	  dataType: 'json',
    	  success: function(agentInfo) {
          if (agentInfo == 'false') {
            ansyFn(Compared.toStatus(false, '代理商编号不存在!'));
            agentNameInput.val('');
          } else if (agentInfo == 'NotMatch') {
            ansyFn(Compared.toStatus(false, '代理商不符合!'));
            agentNameInput.val('');
          } else {
            agentNameInput.val(JSON.parse(agentInfo).fullName);
            ansyFn(Compared.toStatus(true));
          }
    	  },
    	  error: function() {
    		  agentNameInput.val('');
    		  Messages.error('检测代理商编号失败, 请稍后重试...');
    	  }
      });
    });

    // 检测商户编号
    Compared.add('checkPosCustomerNo', function(val, params, ele, ansyFn) {
      var customerNameInput = $(ele).closest('form').find('.customerName');
      // TODO 之后移入 api.js 中
      utils.ajax({
    	  url: 'queryCustomerExistsAndNotSuperior.action?customerNo=' + val,
    	  dataType: 'json',
    	  success: function(customerInfo) {
    		  if (customerInfo == 'false') {
    			  ansyFn(Compared.toStatus(false, '商户编号不存在!'));
    			  customerNameInput.val('');
    		  } else if (customerInfo == 'notSuperior') {
                  ansyFn(Compared.toStatus(false, '商户不是当前所属!'));
                  customerNameInput.val('');
    		  } else {
    			  customerNameInput.val(JSON.parse(customerInfo).fullName);
    			  ansyFn(Compared.toStatus(true));
    		  }
    	  },
    	  error: function() {
    		  customerNameInput.val('');
    		  Messages.error('检测商户编号失败, 请稍后重试...');
    	  }
      });
    });

    // 获取订单信息
    posBatch.orderInfo = function() {
      // 订单复选框
      var orders = $('.data-table td input[type="checkbox"]');
      // 未选中任何订单则提示
      if (orders.notCheckAll()) {
        Messages.warn('请至少选择一条POS记录!');
        return;
      }

      // 订单信息
      var infos = [];
      // 总POS编号,用于ajax附加数据
      var posNumbers = '';
      orders.each(function(i) {
        // 忽略未选中订单
        if (!this.checked)
          return;
        // 由于valueList输出的表格, 无法带class, 所以只好用索引来获取值
        var tr = $(this).closest('tr');
        var td = $('td', tr);
        // 订单信息
        var info = {
          // POS编号
          orderId: this.value
        };
        infos.push(info);
        posNumbers = posNumbers + info.orderId + ',';
      });
      // 去掉末尾逗号
      posNumbers = posNumbers.slice(0, posNumbers.length - 1);
      return {infos: infos, posNumbers: posNumbers}
    };


    // 批量出库
    posBatch.delivery = function(btn) {
      var orderInfos = posBatch.orderInfo();
      if (!orderInfos)
        return;
      deliveryDialog(orderInfos);
    };

    // 显示批量出库对话框
    function deliveryDialog(orderInfos) {
      var dialogElement = $('#pos-batch-Delivery');
      // 设置对话框内总笔数
      $('.count', dialogElement).text(utils.toFixed(orderInfos.infos.length, 0));
      // 清空上次输入代理商
      $('.agentNo', dialogElement).val('');
      // 清空上次代理商全称
      $('.agentName', dialogElement).val('');
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
                  // 验证成功, 执行批量出库
                  posBatch.doPosDelivery(orderInfos, dialog, content.find('.agentNo').val());
                } else if (status.status === Compared.EnumStatus.fail) {
                  dialog.loading(false);
                }
              });
            }
          }
        ],
        btnType: 2,
        title: '批量POS出库'
      });
    };

    // 执行POS出库
    posBatch.doPosDelivery = function(orderInfos, dialog, agentNo) {
      dialog.loading(true);
      // TODO ajax请求之后放入 api.js 中
      utils.ajax({
        url: 'posBatchDelivery.action',
          type: 'post',
          dataType: "json",
          data: {
            'posCatiArrays': orderInfos.posNumbers,
            'agentNo': agentNo
          },
          success: function (result) {
            var msg = orderInfos.infos.length + ' 个POS出库成功!';
            new window.top.Alert(msg, '批量POS出库', function() {
              dialog.close(null, true);
              window.RefresQueryResult();
            });
          },
          error: function() {
            Messages.error('网络异常, 批量出库失败, 请稍后重试...');
            dialog.close(null, true);
          }
      });
    };


    // 批量绑定
    posBatch.bind = function(btn) {
      var orderInfos = posBatch.orderInfo();
      if (!orderInfos)
        return;
      bindDialog(orderInfos);
    };

    // 显示批量绑定对话框
    function bindDialog(orderInfos) {
      var dialogElement = $('#pos-batch-Bind');
      // 设置对话框内总笔数
      $('.count', dialogElement).text(utils.toFixed(orderInfos.infos.length, 0));
      // 清空上次输入商户
      $('.customerNo', dialogElement).val('');
      // 清空上次商户全称
      $('.customerName', dialogElement).val('');
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
                  // 验证成功, 执行批量出库
                  posBatch.doPosBind(orderInfos, dialog, content.find('.customerNo').val());
                } else if (status.status === Compared.EnumStatus.fail) {
                  dialog.loading(false);
                }
              });
            }
          }
        ],
        btnType: 2,
        title: '批量POS绑定'
      });
    };

    // 执行POS绑定
    posBatch.doPosBind = function(orderInfos, dialog, customerNo) {
      dialog.loading(true);
      // TODO ajax请求之后放入 api.js 中
      utils.ajax({
        url: 'posBind.action',
          type: 'post',
          dataType: "json",
          data: {
            'posCatiArrays': orderInfos.posNumbers,
            'customerNo': customerNo
          },
          success: function (result) {
            var msg = orderInfos.infos.length + ' 个POS绑定成功!';
            new window.top.Alert(msg, '批量POS绑定', function() {
              dialog.close(null, true);
              window.RefresQueryResult();
            });
          },
          error: function() {
            Messages.error('网络异常, 批量绑定失败, 请稍后重试...');
            dialog.close(null, true);
          }
      });
    };


  window.posBatch = posBatch;
})();


// 提现
(function() {

    // 增加检测提现密码ajax验证
    window.top.Compared.add('checkDrawCashPassword', function(val, params, ele, ansyFn) {
      Api.agent.checkDrawCashPassword(val, function(status) {
        ansyFn(Compared.toStatus(status, '提现密码错误'));
      });
    });

    // 增加短信验证码密码ajax验证
    window.top.Compared.add('checkVerifyCode', function(val, params, ele, ansyFn) {
      Api.agent.checkVerifyCode(val, function(status) {
        ansyFn(Compared.toStatus(status, '验证码错误'));
      });
    });

    // 提现点击
    function drawCash(balance) {
      var amount = $('#amount').val();
      if (Compared.rules.amount(amount).status === Compared.EnumStatus.fail) {
        Messages.warn('请填写正确的金额!');
        return;
      }
      if (Compared.rules.max(amount, balance).status === Compared.EnumStatus.fail) {
        Messages.warn('提现金额不能超过可用余额!');
        return;
      }
      if (Compared.rules.range(amount, [amountMin, amountMax]).status === Compared.EnumStatus.fail) {
        Messages.warn('提现金额应在范围: ' + amountMin + ' ~ ' + amountMax + ' 内!');
        return;
      }
      
      Api.agent.usePhoneCheck(function(type) {
        if (type == '1') {
          // 显示对话框-手机短信验证
          smsDialog(amount);
        } else {
          // 显示对话框-提现密码验证
          passwordDialog(amount);
        }
      });
    }

    // 提现
    function doDrawCash(amount, dialog) {
    	dialog.loading(true);
    	function drawCashOver(type, msg) {
        if (type == 1) {
          Messages.success(msg);
        } else {
          Messages.error(msg);
        }
        dialog.close(null, true);
        new window.top.Alert(msg, '提现结果');
    		window.dialogIframe.close(null, true);
    	};
    	
    	Api.agent.drawCash(amount, function(result) {
    	  switch (result) {
          case 'SUCCESS':
    	  		drawCashOver(1, '提现成功!');
    	  		return;
          case 'HANDLEDING':
            drawCashOver(1, '提现处理中!');
            return;
          case 'FAILED':
            drawCashOver(2, '提现发起失败!');
            return;
          case 'UNKNOWN':
            drawCashOver(2, '提现结果未知，请联系管理人员！');
            return;
    	  }
        if (result.indexOf("_") > 0) {
          var msg = result.split("_");
          drawCashOver(2, msg[1]);
        } else {
          drawCashOver(1, result);
        }
    	});
    }
    
    // 显示提现短信验证对话框对话框
    function smsDialog(amount) {
      var dialogElement = $('#accept-password-sms');
      // 设置对话框内提现金额
      $('.cash-amount', dialogElement).text(amount);
      // 清空上次输入验证码
      $('[name="verify-code"]', dialogElement).val();
      // 清空上次输入提现密码
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
                  // 验证成功, 执行提现
                  doDrawCash(amount, dialog);
                } else if (status.status === Compared.EnumStatus.fail) {
                  dialog.loading(false);
                }
              });
            }
          }
        ],
        btnType: 2,
        title: '提现审核',
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
            Api.agent.sendVerifyCode(0, function(status) {
              if (status == true || status == 'true') {
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
    
    // 显示提现审核验证对话框对话框
    function passwordDialog(amount) {
      var dialogElement = $('#accept-password');
      // 设置对话框内提现金额
      $('.cash-amount', dialogElement).text(amount);
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
                  // 验证成功, 执行提现
                	doDrawCash(amount, dialog);
                } else if (status.status === Compared.EnumStatus.fail) {
                  dialog.loading(false);
                }
              });
            }
          }
        ],
        btnType: 2,
        title: '提现审核验证'
      });
    }

    // 获取账号最大最小金额限制
    var amountMax = 0;
    var amountMin = 0;
    window.drawCashInit = function() {
      Api.agent.dfConfig(function(config) {
        if (!config) {
          Messages.error('获取账户代付配置失败, 请联系管理员或稍后重试...');
          return;
        }
        amountMax = config.maxAmount;
        amountMin = config.minAmount;
      });
    };

    window.drawCash = drawCash;

})();

// 手机验证码复核设置
(function() {
  
    // 检测手机验证码
    Compared.add('dfComplexVerify', function(val, params, ele, ansyFn) {
      Api.agent.checkVerifyCode(val, function(status) {
        if (val.length < 6)
          return Compared.toStatus('验证码长度为6位数!');
        ansyFn(Compared.toStatus(status == 1 || status == '1', '验证码错误'));
      });
    });
  
    var dfComplex = {
      form: $(),
      verifyCodeInput: $(),
      // 发送验证码按钮
      sendSMSBtn: $(),
      // 计时器句柄
      timeHandler: null,
      // 初始化
      init: function(form) {
        dfComplex.form = form;
        // 验证码输入框
        dfComplex.verifyCodeInput = form.find('[name="verify-code"]').addClass('readonly');
        dfComplex.sendSMSBtn = form.find('.sendVerifyCode');
        // 开通/关闭按钮事件
        form.find('.btn-trigger').click(function() {
          dfComplex.sendSMS();
        });
        // 重新发送验证码事件
        dfComplex.sendSMSBtn.click(function() {
          dfComplex.sendSMS();
        });
        // 挂接表单通过
        $(window).load(function() {
          var formValidator = window.getValidator(form);
          // 添加验证码验证onchange回调
          formValidator.add(dfComplex.verifyCodeInput, [], null, {rangelength: 2 | 6}, function(ele, status, message) {
            if (status ===  Compared.EnumStatus.ok && dfComplex.verifyCodeInput.get(0).ValidtorComplete) {
              $('.btn-wrap .btn', form).removeClass('disabled');
            } else {
              $('.btn-wrap .btn', form).addClass('disabled');
            }
          });
          dfComplex.hookSubmit(formValidator);
        });
      },
      hookSubmit: function(formValidator) {
        formValidator.options.onCustomize = function() {
          // 获取验证码输入框中的验证码
          var verifyCode = dfComplex.verifyCodeInput.val();
          var btn = $('.btn-wrap .btn', dfComplex.form);
          btn.loading();
          var operation = dfComplex.form.find('.operation').val();
          Api.agent.updateUsePhoneCheck(operation, verifyCode, function(msg) {
            btn.ending();
            if (msg == 'error') {
              new window.top.Alert('网络异常 #2, 请联系管理员或稍后重试...', '手机验证码复核');
              return;
            }
            if (msg == 1 || msg == '1') {
              if (operation == true || operation == 'TRUE') {
                new window.top.Alert('修改成功! 手机验证码复核功能已开启!', '手机验证码复核');
              } else {
                new window.top.Alert('修改成功! 手机验证码复核功能已关闭!', '手机验证码复核');
              }
            } else if (msg == 2 || msg == '2') {
              new window.top.Alert('验证码不正确!', '手机验证码复核');
            } else {
              new window.top.Alert('更新手机验证码复核功能失败!', '手机验证码复核');
            }
            window.location.reload();
          });
          return false;
        };
      },
      // 发送短信
      sendSMS: function() {
        var flag = dfComplex.form.find('.operation').val() == "TRUE" ? 1 : 2;
        // 正在倒计时则不发送请求
        if (dfComplex.timeHandler !== null)
          return;
        // 发送短信验证码
        Api.agent.sendVerifyCode(flag, function(status) {
          if (status == true || status == 'true') {
            // 隐藏点击按钮
            dfComplex.form.find('.btn-trigger').hide();
            // 显示手机验证码输入框
            dfComplex.form.find('.btn-wrap').css('visibility', 'visible');
            dfComplex.sendSMSBtn.closest('.item').show();
            Messages.success('发送短信验证码成功!');
            // 开始倒计时
            dfComplex.startTiming(dfComplex.sendSMSBtn, dfComplex.verifyCodeInput);
          } else {
            Messages.error('发送短信验证码失败!');
          }
        });
      },
      // 计时器包装函数
      startTiming: function(sendBtn, verifyCodeInput) {
        // 正在倒计时则不处理
        if (dfComplex.timeHandler !== null)
          return;
        verifyCodeInput.removeClass('readonly');
        dfComplex.timeHandler = utils.timing(60, function(s) {
          sendBtn.text('剩余 ' + s + ' 秒');
        }, function() {
          sendBtn.text('再次发送');
          dfComplex.timeHandler = null;
        })
      }
    };
  
    window.dfComplex = dfComplex;
  
  })();
  

// 产品入网
(function() {
  
    var uniteFee = {
      // 费率类型 RATIO=百分比, FIXED=固定
      '.fee-type': 'RATIO',
      // 费率
      '.fee': '0.006',
      // 最低费率
      '.minfee': '0.1',
      // 最高费率
      '.maxfee': '9999',
      '[name="customer.mcc"]': '5012'
  };

    // 产品默认值
    var productPlacehold = {
      ALIPAY: uniteFee,
      B2C: uniteFee,
      B2B: uniteFee,
      AUTHPAY: uniteFee,
      WXJSAPI: uniteFee,
      WXNATIVE: uniteFee,
      WXMICROPAY: uniteFee,
      REMIT: {
        // 费率类型 RATIO=百分比, FIXED=固定
        '.fee-type': 'FIXED',
        // 费率
        '.fee': '2',
        // 最低费率
        '.minfee': '',
        // 最高费率
        '.maxfee': '',
        '[name="serviceConfigBean.startTime"]': '08:30',
        '[name="serviceConfigBean.endTime"]': '19:00',
        '[name="serviceConfigBean.maxAmount"]': '50000',
        '[name="serviceConfigBean.minAmount"]': '1',
        '[name="serviceConfigBean.dayMaxAmount"]': '500000',
        '[name="serviceConfigBean.manualAudit"]': 'FALSE',
        '[name="serviceConfigBean.usePhoneCheck"]': 'FALSE',
        '[name="serviceConfigBean.fireType"]': 'MAN',
        '[name="serviceConfigBean.bossAudit"]': 'FALSE'
      },
      HOLIDAY_REMIT: {
        // 费率类型 RATIO=百分比, FIXED=固定
        '.fee-type': 'FIXED',
        // 费率
        '.fee': '2',
        // 最低费率
        '.minfee': '',
        // 最高费率
        '.maxfee': '',
      },
    RECEIVE: {
      // 费率类型 RATIO=百分比, FIXED=固定
      '.fee-type': 'FIXED',
      // 费率
      '.fee': '2',
      // 最低费率
      '.minfee': '',
      // 最高费率
      '.maxfee': '',
      '[name="receiveConfigInfoBean.singleMaxAmount"]': '50000',
      '[name="receiveConfigInfoBean.singleBatchMaxNum"]': '10',
      '[name="receiveConfigInfoBean.dailyMaxAmount"]': '500000'
    },
      POS: uniteFee,
      ALIPAYMICROPAY: uniteFee,
      QUICKPAY: uniteFee,
      BINDCARD_AUTH: {
        // 费率类型 RATIO=百分比, FIXED=固定
        '.fee-type': 'FIXED',
        // 费率
        '.fee': '0.001',
        // 最低费率
        '.minfee': '',
        // 最高费率
        '.maxfee': '',
      }
    };
  
    var productWrap = $('#productWrap');
    // 产品下拉列表
    var productSelect = $('#productTypeSelect');
  
    // 产品下拉列表管理
    var selectMannage = {
      // 下拉列表选择事件
      hander_select: function(event, selectbox) {
        var selectBox = productSelect.get(0).selectBox;
        // 已开通产品数组 [{label: '个人网银', value: 'B2C'}, ...]
        var productList = selectBox.getCurrent();
        // 所有业务
        var options = selectBox.extract();
        // 开通已选择产品
        for (var i = 0; i < options.length; ++i) {
          var productType = options[i].value;
          if (options[i].selected) {
            product.open(productType, null, false, selectbox);
          } else {
            product.close(productType, false, selectbox);
          }
        }
      },
      // 核心
      select: function(productType, selected) {
        if (productSelect.length === 0)
          return;
        productSelect.findOptionForVal(productType).get(0).selected = selected;
        productSelect.renderSelectBox();
      },
      // 开通产品
      open: function(productType, selectbox) {
        if (selectbox)
          return;
        selectMannage.select(productType, true);
      },
      // 关闭产品
      close: function(productType, selectbox) {
        if (selectbox)
          return;
        selectMannage.select(productType, false);
      },
      // 默认开通所有下拉列表中的产品
      openAll: function() {
        var selectBox = productSelect.get(0).selectBox;
        // 已开通产品数组 [{label: '个人网银', value: 'B2C'}, ...]
        var productList = selectBox.getCurrent();
        // 所有业务
        var options = selectBox.extract();
        // 开通已选择产品
        for (var i = 0; i < options.length; ++i) {
          var productType = options[i].value;
          product.open(productType, null, false, selectbox);
        }
      }
    };
    productSelect.change(selectMannage.hander_select);
  
  
    var product = {
      // 已开通产品数组, 用于产品div排序
      products: [],
      // 初始化入网产品管理器
      init: function() {
        $('.productItem', productWrap).hide();
        product.closeAll();
        product.bind();
      },
      // 绑定事件
      bind: function() {
  
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
          $(this).closest('.productItem').hide();
          product.close(productType);
        });
  
      },
      // 开通一个产品
      open: function(productType, values, noAnimate, selectbox) {
        var productItem = $('.productItem[data-producttype="' + productType + '"]', productWrap);
        if (productItem.length == 0) {
          console.warn('暂不支持产品: ', productType);
          return;
        }
        // 已开通则忽略
        if (!productItem.attr('disabled'))
          return;

        // 如果元素没有值, 则使用默认值
        if (productItem.attr('data-value-exist') != 'true') {
          var defaultValues = productPlacehold[productType];
          if (defaultValues) {
        	  product.fillBaseValue(productItem, defaultValues);
          }
        }

        // 启用元素
        // 如果路由模板因为无路由被禁用, 则不要解开禁用
        var options = $('.router-template', productItem).find('option');
        var optionLabel = options.text();
        
        if (optionLabel == '无模板' && options.length == 1) {
          productItem.enablelInput(false, '.router-template,.router-hide,.ignoreDisable');
        } else {
          productItem.enablelInput(false, '.ignoreMinMax');
        }
        
        // 加入产品数组
        product.products.push(productType);
        // 排序div
        product.sort();
  
        // 处理代付特殊情况, 代付开通且是否开通下拉列表=TRUE则假日付开通
        if (productType === 'REMIT' && $('.holidayStatus', productItem).val() === 'TRUE') {
          product.open('HOLIDAY_REMIT');
        }
  
        if (noAnimate) {
          productItem.show();
        } else {
          productItem.slideDown();
        }
        productItem.removeAttr('disabled');
  
        // 下拉列表也加入
        selectMannage.open(productType, selectbox);
        if (values)
          product.fillBaseValue(values);

        return productItem;
      },
      // 开通所有产品(包括假日付)
      openAll: function() {
        $('.productItem', productWrap).each(function() {
          product.open($(this).attr('data-producttype'), null, true);
        });
        $('.holidayStatus').selectForValue('TRUE');
      },
      // 删除一个产品
      close: function(productType, noAnimate, selectbox) {
        var productItem = $('.productItem[data-producttype="' + productType + '"]', productWrap);
        if (productItem.length == 0) {
          return;
        }
        // 已关闭则忽略
        if (productItem.attr('disabled'))
          return;
        // 禁用元素
        productItem.disableInput(false, '.fee-id');
        // 如果代付是根据上级获取的, 那么是否开通假日付下拉列表锁定, 不清空值
        var ignoreClass = '';
        if (window.fees && window.fees['REMIT']) {
          ignoreClass = ',.holidayStatus';
        }
        // 清空元素值
        productItem.clearForm('.ignoreEmpy,.ignoreEmpy,.ignoreDisable' + ignoreClass);
        // 从产品数组中删除
        var index = utils.indexOf(product.products, productType);
        if (index !== -1) {
          product.products.splice(index, 1);
        }
  
        // 处理代付特殊情况, 代付关闭则假日付也要关闭
        if (productType === 'REMIT') {
          product.close('HOLIDAY_REMIT');
        }
        if (noAnimate) {
          productItem.hide();
        } else {
          productItem.slideUp();
        }
        productItem.attr('data-value-exist', 'false');
        productItem.attr('disabled', 'disabled');
        if (index !== -1 &&  productType !== 'HOLIDAY_REMIT') {
          // 下拉列表也要刷新
          selectMannage.close(productType, selectbox);
        }
      },
      // 删除所有产品
      closeAll: function() {
        $('.productItem', productWrap).each(function() {
          product.close($(this).attr('data-producttype'), true);
        });
      },
      // 排序产品div
      sort: function() {
  
        for(var i = product.products.length - 1; i >= 0; --i) {
          var productType = product.products[i];
          var productItem = $('.productItem[data-producttype="' + productType + '"]', productWrap);
          productWrap.append(productItem);
        }
        // 处理假日付特殊情况, 永远在代付后面
        $('[data-producttype="HOLIDAY_REMIT"]').insertAfter('[data-producttype="REMIT"]');
      },
      // 禁用产品-用于详情页显示,但不允许控制
      disabledProduct: function() {
        // 详情页特殊处理
        productWrap.disableInput();
        $('.delProduct, .delShopm, .addShop', productWrap).remove();
      },
      // 禁止删除产品-用于调帐,可以修改但是不能删除
      dedabledDelProduct: function() {
        $('.delProduct', productWrap).remove();
      },
      // 显示暂无费率信息
      showNothing: function() {
        productWrap.html('<p class="mb-10 mt-10">暂无费率信息!<p>');
      },
      // 基本产品填值
      fillBaseValue: function(productItem, values) {
        if (!productItem || productItem.length !== 1) {
          return;
        }
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
        productItem.attr('data-value-exist', 'true');
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
      <div class="item mr-10">\
        <div class="input-area">\
          <span class="label w-90">{{label}}:</span>\
          <div class="input-wrap">\
            <input type="text" class="w-120"  name="shopList[{{index}}].{{name}}" data-name="shopList[INDEX].{{name}}" required>\
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
    shopList: [],
    add: function(defaultValue, defaultId) {
      var option = {
        shopName: '',
        printName: '',
        bindPhoneNo: ''
      };
      var values = $.extend({}, option, defaultValue);
      var index = shopMannage.shopList.length;
      var shopItem = $('<div class="shopItem fix"></div>').attr('data-index', index);
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
      // 初始化时,赋值ID
      if(defaultId !== undefined) {
        shopItem.append('<input type="hidden" name="shopList[' + index + '].shopNo" value="' + defaultId + '" >');
      } else {
        // 插入删除按钮
        var delItem = $('<div class="item"><a class="btn delShop" href="javascript:void(0);">删除</a></div>');
        delItem.click(function() { 
          var i = $(this).closest('.shopItem').data('index');
          shopMannage.remove(i);
        });
      }
      shopMannage.shopList.push(shopItem);
      shopItem.data('index', index);
      if (index !== 0)
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
    init: function(isDefabult, value) {
      $('.addShop').click(function() {
        shopMannage.add();
      });
      // 加入默认
      if (isDefabult)
        shopMannage.add(value);
    },
    // 销毁网点管理, 用于审核初始化没有网点的情况
    destroy: function() {
      $('.shopMannage').remove();
    }
  };


  window.shopMannage = shopMannage;

})();

// 菜单树管理
(function() {
  // 菜单树管理器
  var menuTree = {
    // 菜单树容器
    treeWrap: null,
    // 菜单选中事件
    onClick: null,
    // 当前选中
    current: null,
    // 初始化
    init: function() {
      menuTree.treeWrap = $('#tree-ul');
      menuTree.bind();
    },
    // 绑定事件
    bind: function() {
      // 菜单选中
      menuTree.treeWrap.on('click', 'a', function(event) {
        var li = $(this).closest('li');
        if (menuTree.current) {
          menuTree.current.removeClass('active');
        }
        li.addClass('active');
        menuTree.current = li;
        if ($.isFunction(menuTree.onClick))
          menuTree.onClick(li, li.data('menu-data'));
      });
      // 目录折叠
      menuTree.treeWrap.on('click', '.folder-ico', function(event) {
        var li = $(this).closest('li');
        li.toggleClass('open');
        event.stopPropagation();
        return false;
      });

      $('.menu-openAll').click(function() {
        menuTree.openAll();
      });
      $('.menu-closeAll').click(function() {
        menuTree.closeAll();
      });


    },
    // 增加菜单
    add: function(name, id, parentId) {
      parentId = parentId || '';
      var liHTML = '<li><a href="javascript:;"><i class="fa {{icoClass}}"></i><span>{{name}}</span></a></li>';
      var subMenus = $('<ul class="sub-menus"></div>');
      // 附加数据
      var data = {
        name: name,
        id: id,
        parentId: parentId
      };
      var li = null;
      if (parentId === '') {
        // 一级菜单
        li = $(utils.tpl(liHTML, {
          icoClass: 'folder-ico',
          name: name
        }));
        li.data('menu-data', data);
        li.append(subMenus);
        menuTree.treeWrap.append(li);
      } else {
        // 二级菜单
        li = $(utils.tpl(liHTML, {
          icoClass: 'file-ico',
          name: name
        }));
        li.data('menu-data', data);
        // 获取一级菜单
        var mainMenu = menuTree.findMainMenu(parentId);
        $('.sub-menus', mainMenu).append(li);
      }
    },
    // 根据parentId获取一级菜单
    findMainMenu: function(parentId) {
      var li = $();
      $('li', menuTree.treeWrap).each(function() {
        var data = $(this).data('menu-data');
        if (!data)
          return;
        if (data.id === parentId)
          li = $(this);
      });
      return li;
    },
    // 展开所有
    openAll: function() {
      $('li', menuTree.treeWrap).addClass('open');
    },
    // 折叠所有
    closeAll: function() {
      $('li', menuTree.treeWrap).removeClass('open');
    },
  };


  window.menuTree = menuTree;

})();