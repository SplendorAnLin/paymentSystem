<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>新增费率</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 4em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="createCustomerFeeAction.action" method="post" id="form" prompt="DropdownMessage"  data-success="新增成功" data-fail="新增失败">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">商户编号:</span>
              <div class="input-wrap">
                    <input type="text" class="input-text" name="customerFee.customerNo"  required checkCustomeNo trigger='{"checkCustomeNo": 2}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">商户名称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="userName" readonly placeholder="请填写商户编号" required rangelength="[2, 10]">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">产品类型:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select" name="customerFee.productType" id="productType" dictTypeId="BF_SHARE_PAYTYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">费率类型:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select fee-type" name="customerFee.feeType" dictTypeId="FEE_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">费率:</span>
              <div class="input-wrap">
                <input type="text" class="input-text fee" name="customerFee.fee" required amount fee>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">最小费率:</span>
              <div class="input-wrap">
                <input type="text" class="input-text minfee" name="customerFee.minFee" required amount minfee maxIgnoreEmpy="[name='customerFee.maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">最大费率:</span>
              <div class="input-wrap">
                <input type="text" class="input-text maxfee" name="customerFee.maxFee" required amount maxfee minIgnoreEmpy="[name='customerFee.minFee']" message='{"minIgnoreEmpy": "低于最小费率"}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">Mcc:</span>
              <div class="input-wrap">
                <input type="text" class="input-text mcc" name="mcc" required mcc trigger='{"mcc": 2}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">状态:</span>
              <div class="input-wrap switch-wrap">
                <dict:radio name="customerFee.status" dictTypeId="ALL_STATUS"></dict:radio>
              </div>
            </div>
          </div>


        </div>
        <div class="text-center mt-10 hidden">
          <button class="btn btn-submit">新 增</button>
        </div>
      </form>
    </div>
  </div>
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
  
    // 增加商户费率/分润等验证
    general.addCustomerVerify()
    var userName = $('[name="userName"]');
    // 检测操作员手机号是否重复
    Compared.add('checkCustomeNo', function(val, params, ele, ansyFn) {
      Api.boss.accInfo(val, function(info) {
      	if (info) {
      		userName.val(info.fullName);
      		// 获取此商户的上级服务商, 并根据上级来切换费率验证
      		Api.boss.findParentNo(val, function(parentNo) {
      			if (parentNo == '' || parentNo == 'false') {
      				general.verifyForDefault();
      				ansyFn(Compared.toStatus(true));
      				return;
      			}
      			general.feesForParent(parentNo, function() {
      				ansyFn(Compared.toStatus(true));
      			});
      		});
      	} else {
      		userName.val('');
      		general.verifyForDefault();
      		ansyFn(Compared.toStatus(false, '此商户不存在!'));
      	}
        
      });
    });
    
    $('[name="customerFee.customerNo"]').change(function() {
    	if ($(this).val() == '')
    		general.verifyForDefault();
    });
    
    $(document).ready(function() {
      // 获取验证实例
      var formValidator = window.getValidator($('form'));
      // 挂接验证钩子
      formValidator.options.onCustomize = function() {
        // 当前对话框
        var dialog = window.queryDialog($('form'));
        Api.boss.checkCustomerFee($('[name="customerFee.customerNo"]').val(), $('#productType').val(), function(result) {
          switch (result) {
            case 'TRUE':
              formValidator.submit(true);
              break;
            case 'EXISTS_REMIT':
              Messages.warn('未开通代付不能添加假日付费率信息!');
              dialog.loading(false);
              break;
            default:
              Messages.warn('该商户已经存在此费率类型，不能重复添加!');
              dialog.loading(false);
              break;
          }
        });
        return false;
      };
    });
    
  
    // 更改费率类型
    $('[name="customerFee.feeType"]').change(function() {
      general.changeFeeType(this.value, $(this).closest('form'));
    }).trigger('change');
    
    // 更改产品类型
    $('#productType').change(function() {
    	var mccItem = $('.mcc').closest('.item');
    	if (this.value == 'POS' || this.value == 'MPOS') {
    		mccItem.show().enablelInput();
    	} else {
    		mccItem.hide().disableInput();
    	}
    }).trigger('change');

    
  </script>
</body>
</html>
