<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>修改商户交易配置信息</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 4em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="customerConfigUpdata.action" method="post" id="form" prompt="DropdownMessage"  data-success="修改成功" data-fail="修改失败">
        <input type="hidden" name="customerConfig.id" value="${customerConfig.id}">
        <input type="hidden" name="customerConfig.productType" value="${customerConfig.productType}">
        <input type="hidden" name="customerConfig.customerNo" value="${customerConfig.customerNo}">
        
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">商户编号:</span>
              <div class="input-wrap">
                    <input type="text" class="input-text" value="${customerConfig.customerNo}" readonly>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">产品类型:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select" value="${customerConfig.productType }" disabled="true" id="productType" dictTypeId="PRODUCT_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">操作日期:</span>
              <div class="input-wrap">
                    <input type="text" class="input-text" readonly value='<fmt:formatDate value="${customerConfig.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />' >
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">交易开始时间:</span>
              <div class="input-wrap">
                <input type="text" class="input-text date-start" date-fmt="H:mm" name="customerConfig.startTime" value="${customerConfig.startTime}" required placeholder="00:00 ~ 23:59" date-parent="form">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">交易结束时间:</span>
              <div class="input-wrap">
                <input type="text" class="input-text date-end" date-fmt="H:mm" name="customerConfig.endTime" value="${customerConfig.endTime}" required placeholder="00:00 ~ 23:59" date-parent="form">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">单笔最低金额:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="customerConfig.minAmount" value="<fmt:formatNumber groupingUsed="false" currencySymbol="" type="currency" value="${customerConfig.minAmount}" />" required amount maxIgnoreEmpy="[name='customerConfig.maxAmount']" message='{"maxIgnoreEmpy": "超过单笔最高金额"}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">单笔最高金额:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="customerConfig.maxAmount" value="<fmt:formatNumber groupingUsed="false" currencySymbol="" type="currency" value="${customerConfig.maxAmount}" />" required amount minIgnoreEmpy="[name='customerConfig.minAmount']" message='{"minIgnoreEmpy": "低于单笔最低金额"}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">日交易上限:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="customerConfig.dayMax" required  value="<fmt:formatNumber groupingUsed="false" currencySymbol="" type="currency" value="${customerConfig.dayMax}" />" amount minIgnoreEmpy="[name='customerConfig.maxAmount']" message='{"minIgnoreEmpy": "低于单笔最高金额"}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">操作原因:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="reason">
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
</body>
</html>
