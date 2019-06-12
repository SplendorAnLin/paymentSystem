<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>账务明细</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">

    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="${pageContext.request.contextPath }/queryAccountPageAction.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">系统流水号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="accountHistoryQueryBean.transFlow">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">资金标识:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="accountHistoryQueryBean.symbol" id="symbol" dictTypeId="CAPITAL_IDENTIFICATION"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">业务类型:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" dictTypeId="MERCHANT_BUSINESS_TYPE" name="accountHistoryQueryBean.bussinessCode" id="bussinessCode"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">交易日期:</span>
              <div class="input-wrap">
                <input type="text" name="accountHistoryQueryBean.transStartTime" class="input-text double-input date-start default-time">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="accountHistoryQueryBean.transEndTime" class="input-text double-input date-end default-time-end">
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <input type="reset" class="btn clear-form" value="重 置">
        </div>
      </form>
    </div>


    <!--查询结果框架-->
    <div class="query-result mt-20">
      <iframe name="query-result" frameborder="0"></iframe>
    </div>

  </div>
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
