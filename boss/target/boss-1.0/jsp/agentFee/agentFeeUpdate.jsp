<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>修改服务商费率</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 4em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="updateAgentFee.action" method="post" prompt="DropdownMessage"  data-success="修改成功" data-fail="修改失败">
        <input type="hidden" name="agentFee.id" value="${agentFee.id}">
        <input type="hidden" id="productType" value="${agentFee.productType }">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">产品类型:</span>
              <div class="input-wrap">
                <dict:select value="${agentFee.productType }" styleClass="input-select" disabled="true" name="agentFee.productType" dictTypeId="BF_SHARE_PAYTYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">费率类型:</span>
              <div class="input-wrap">
                <dict:select value="${agentFee.feeType }" styleClass="input-select fee-type" name="agentFee.feeType" dictTypeId="FEE_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">服务商编号:</span>
              <div class="input-wrap">
                    <input type="text" class="input-text" name="agentFee.agentNo" id="agentNo" value="${agentFee.agentNo}" disabled>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">服务商全称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" id="agentFee_fullName" name="agentFee.fullName" value="${agent.fullName}" disabled>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">费率:</span>
              <div class="input-wrap">
                <input type="text" class="input-text fee" name="agentFee.fee" value="${agentFee.fee}" required amount fee>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">最小费率:</span>
              <div class="input-wrap">
                <input type="text" class="input-text minfee" name="agentFee.minFee" value="${agentFee.minFee}" required amount minfee maxIgnoreEmpy="[name='agentFee.maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">最大费率:</span>
              <div class="input-wrap">
                <input type="text" class="input-text maxfee" name="agentFee.maxFee" value="${agentFee.maxFee}" required amount maxfee minIgnoreEmpy="[name='agentFee.minFee']" message='{"minIgnoreEmpy": "低于最小费率"}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">分润类型:</span>
              <div class="input-wrap">
                <dict:select value="${agentFee.profitType }" styleClass="input-select profit-type" diy="readonly" name="agentFee.profitType" dictTypeId="PROFIT_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">分润比例:</span>
              <div class="input-wrap">
                <input type="text" class="input-text profit-ratio" name="agentFee.profitRatio" value="${agentFee.profitRatio}" required amount profitratio>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">状态:</span>
              <div class="input-wrap switch-wrap">
                <dict:radio value="${agentFee.status }" name="agentFee.status" dictTypeId="ALL_STATUS"></dict:radio>
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
    // 增加代理费率/分润等验证
    general.addAgentVerify();
    // 更改费率类型
    $('[name="agentFee.feeType"]').change(function() {
      general.changeFeeType(this.value, $('form'));
    }).trigger('change');
    // 获取全称
    var agentFullName = $('#agentFee_fullName');
    Api.boss.checkAgentNo('${agentFee.agentNo}', function(fullName) {
      if (fullName !== 'false') {
        agentFullName.val(fullName);
       // 获取此服务商的上级服务商, 并根据上级来切换费率验证
        Api.boss.findParentNoByAgentNo('${agentFee.agentNo}', function(parentNo) {
          if (parentNo == '' || parentNo == 'false') {
            general.verifyForDefault();
            return;
          }
          general.feesForParent(parentNo, function() {
          });
        });
      } else {
        agentFullName.val('');
        general.verifyForDefault();
      }
    });
  </script>
</body>
</html>
