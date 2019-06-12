<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>修改费率</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 4em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="updateCustomerFeeAction.action" method="post" id="form" prompt="DropdownMessage"  data-success="修改成功" data-fail="修改失败">
        <input type="hidden" name="customerFee.id" value="${customerFee.id}">
        <input type="hidden" id="productType" value="${customerFee.productType }">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">商户编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="customerFee.customerNo"  value="${customerFee.customerNo}" id="customerNo" readonly>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">商户简称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" value="${customer.shortName }" disabled>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">产品类型:</span>
              <div class="input-wrap">
                <input type="text"  class="input-text" disabled required value="<dict:write dictId="${customerFee.productType }" dictTypeId="BF_SHARE_PAYTYPE"></dict:write>">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">费率类型:</span>
              <div class="input-wrap">
                <dict:select value="${customerFee.feeType }" name="customerFee.feeType" styleClass="input-select fee-type" dictTypeId="FEE_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">费率:</span>
              <div class="input-wrap">
                <input type="text" class="input-text fee" name="customerFee.fee" value="${customerFee.fee}" required amount fee>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">最小费率:</span>
              <div class="input-wrap">
                <input type="text" class="input-text minfee" name="customerFee.minFee" value="${customerFee.minFee}" required amount minFee maxIgnoreEmpy="[name='customerFee.maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">最大费率:</span>
              <div class="input-wrap">
                <input type="text" class="input-text maxfee" name="customerFee.maxFee" value="${customerFee.maxFee}" required amount maxFee minIgnoreEmpy="[name='customerFee.minFee']" message='{"minIgnoreEmpy": "低于最小费率"}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">Mcc:</span>
              <div class="input-wrap">
                <input type="text" class="input-text mcc" name="mcc" value="${customer.mcc }" required mcc trigger='{"mcc": 2}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">状态:</span>
              <div class="input-wrap switch-wrap">
                <dict:radio name="customerFee.status" value="${customerFee.status }" dictTypeId="ALL_STATUS"></dict:radio>
              </div>
            </div>
          </div>


        </div>
        <div class="text-center mt-10 hidden">
          <button class="btn btn-submit">修 改</button>
        </div>
      </form>
    </div>
  </div>
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    // 增加商户费率/分润等验证
    general.addCustomerVerify()
    // 获取此商户的上级服务商, 并根据上级来切换费率验证
    Api.boss.findParentNo('${customerFee.customerNo}', function(parentNo) {
      if (parentNo == '' || parentNo == 'false') {
    	console.log('使用默认验证...');
      	// 使用默认验证
        general.verifyForDefault();
        return;
      }
      // 通过上级编号来验证
      general.feesForParent(parentNo, function(status) {
      	// console.log(status == false, ''上级是否存在);
      });
    });
    // 更改费率类型
    $('[name="customerFee.feeType"]').change(function() {
      general.changeFeeType(this.value, $(this).closest('form'));
    }).trigger('change');
    
    // mcc
    (function() {
    	var productType = $('#productType').val();
      var mccItem = $('.mcc').closest('.item');
      if (productType == 'POS' || productType == 'MPOS') {
        mccItem.show().enablelInput();
      } else {
        mccItem.hide().disableInput();
      }
    })();
    
  </script>
</body>
</html>
