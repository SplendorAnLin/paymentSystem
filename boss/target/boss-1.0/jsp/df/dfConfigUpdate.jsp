<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>付款配置</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">

    <!--查询表单-->
    <div class="query-form mt-10">
      <form class="hidden" id="dpayConfigHistoryForm" action="dpayConfigHistory.action" method="post" target="query-result"></form>
    </div>

    <form id="updateForm" class="validator ajaxFormNotification" action="dpayConfig.action" method="post"  data-success="配置成功" data-fail="配置失败">
      <input type="hidden" name="dpayConfigBean.id" value="${dpayConfigBean.id}">
      <div class="adaptive fix">
        <div class="item">
          <div class="input-area">
            <span class="label w-90">审核金额:</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="dpayConfigBean.auditAmount" value="${dpayConfigBean.auditAmount}" required amount>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-90">支持最大金额:</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="dpayConfigBean.maxAmount" value="${dpayConfigBean.maxAmount}" required amount minIgnoreEmpy="[name='dpayConfigBean.minAmount']" message='{"minIgnoreEmpy": "低于支持最低金额"}'>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-90">支持最小金额:</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="dpayConfigBean.minAmount" value="${dpayConfigBean.minAmount}" required amount maxIgnoreEmpy="[name='dpayConfigBean.maxAmount']" message='{"maxIgnoreEmpy": "超过支持最大金额"}'>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-90">出款方式:</span>
            <div class="input-wrap">
              <dict:select value="${dpayConfigBean.remitType }" styleClass="input-select" name="dpayConfigBean.remitType" dictTypeId="PAYMENT"></dict:select>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-90">假日付:</span>
            <div class="input-wrap">
              <dict:select value="${dpayConfigBean.holidayStatus }" styleClass="input-select" dictTypeId="STATUS" name="dpayConfigBean.holidayStatus"></dict:select>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-90">出款状态:</span>
            <div class="input-wrap">
              <dict:select value="${dpayConfigBean.status }" styleClass="input-select" dictTypeId="STATUS" name="dpayConfigBean.status"></dict:select>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-90">单日单账户重复金额付款:</span>
            <div class="input-wrap">
              <dict:select value="${dpayConfigBean.reRemitFlag }" name="dpayConfigBean.reRemitFlag" styleClass="input-select" dictTypeId="DUPLICATE_PAYMENT"></dict:select>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-90">付款起止时间:</span>
            <div class="input-wrap">
              <input type="text" name="dpayConfigBean.startTime" value="${dpayConfigBean.startTime}" class="date-start w-78" date-fmt="H:mm" required>
            </div>
            <span class="cut"> - </span>
            <div class="input-wrap">
              <input type="text" name="dpayConfigBean.endTime" value="${dpayConfigBean.endTime}" class="date-end w-78" date-fmt="H:mm" required>
            </div>
          </div>
        </div>
      </div>
      <div class="text-center mt-10">
        <button class="btn">修 改</button>
        <a href="javascript:void(0);" onclick="toHistory();" class="btn query-btn">查 询</a>
        <input type="reset" class="btn clear-form" value="重 置">
      </div>
    </form>


    

    <!--查询结果框架-->
    <div class="query-result mt-20">
      <iframe name="query-result" frameborder="0"></iframe>
    </div>

  </div>
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    // 挂接更改配置表单结果判断
    $('#updateForm').data('receiver-hook', function(msg, closeDialog) {
      closeDialog(msg == 'SUCCESS');
    });

    // 查询更改历史
    function toHistory() {
      $('#dpayConfigHistoryForm').submit();
    }

  </script>
</body>
</html>
