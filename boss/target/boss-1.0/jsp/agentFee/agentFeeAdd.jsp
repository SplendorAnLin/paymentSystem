<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>新增服务商费率</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 4em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="createAgentFee.action" method="post" prompt="DropdownMessage"  data-success="新增成功" data-fail="新增失败">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">产品类型:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select" name="agentFee.productType" id="productType" dictTypeId="BF_SHARE_PAYTYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">费率类型:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select fee-type" name="agentFee.feeType" dictTypeId="FEE_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">服务商编号:</span>
              <div class="input-wrap">
                    <input type="text" class="input-text" name="agentFee.agentNo" id="agentNo"  required checkAgentmeNo trigger='{"checkAgentmeNo": 2}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">服务商全称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" id="agentFee_fullName" readonly placeholder="请填写服务商编号" required rangelength="[2, 10]">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">费率:</span>
              <div class="input-wrap">
                <input type="text" class="input-text fee" name="agentFee.fee" required amount fee>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">最小费率:</span>
              <div class="input-wrap">
                <input type="text" class="input-text minfee" name="agentFee.minFee" required amount minfee maxIgnoreEmpy="[name='agentFee.maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">最大费率:</span>
              <div class="input-wrap">
                <input type="text" class="input-text maxfee" name="agentFee.maxFee" required amount maxfee minIgnoreEmpy="[name='agentFee.minFee']" message='{"minIgnoreEmpy": "低于最小费率"}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">分润类型:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select profit-type" diy="readonly" name="agentFee.profitType" dictTypeId="PROFIT_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">分润比例:</span>
              <div class="input-wrap">
                <input type="text" class="input-text profit-ratio" name="agentFee.profitRatio" required amount profitratio>
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
  
    // 检测服务商编号是否存在
    var agentFullName = $('#agentFee_fullName');
    Compared.add('checkAgentmeNo', function(val, params, ele, ansyFn) {
      Api.boss.checkAgentNo(val, function(fullName) {
        if (fullName !== 'false') {
          agentFullName.val(fullName);
         // 获取此服务商的上级服务商, 并根据上级来切换费率验证
          Api.boss.findParentNoByAgentNo(val, function(parentNo) {
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
          agentFullName.val('');
          general.verifyForDefault();
          ansyFn(Compared.toStatus(false, '此服务商不存在!'));
        }
      });
    });
    
    
    $('[name="agentFee.agentNo"]').change(function() {
      if ($(this).val() == '')
        general.verifyForDefault();
    });
    
  
    $(window).load(function() {
      // 获取验证实例
      var formValidator = window.getValidator($('form'));
      // 挂接验证钩子
      formValidator.options.onCustomize = function() {
        // 当前对话框
        var dialog = window.queryDialog($('form'));
        Api.boss.checkAgentFee($('#agentNo').val(), $('#productType').val(), function(result) {
          switch (result) {
            case 'TRUE':
              formValidator.submit(true);
              break;
            case 'EXISTS_REMIT':
              Messages.warn('未开通代付不能添加假日付费率信息!');
              dialog.loading(false);
              break;
            default:
              Messages.warn('该服务商已经存在此费率类型，不能重复添加!');
              dialog.loading(false);
              break;
          }
        });
        return false;
      };
    });
    
    // 更改费率类型
    $('[name="agentFee.feeType"]').change(function() {
      general.changeFeeType(this.value, $(this).closest('form'));
    }).trigger('change');
  </script>
</body>
</html>
