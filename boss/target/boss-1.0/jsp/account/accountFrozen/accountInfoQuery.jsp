<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>冻结/解冻</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">
    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="${pageContext.request.contextPath }/queryAccountAction.action" method="post" target="query-result">
        <input type="hidden" value="2" name="accountAdjustInfo" />
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账号编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="accountQueryBean.accountNo">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">用户编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="accountQueryBean.userNo">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">用户角色:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" name="accountQueryBean.userRole" id="userRole" styleClass="input-select" dictTypeId="USER"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账户状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" name="accountQueryBean.status" id="status" styleClass="input-select" dictTypeId="ACCOUNT_STATUS_INFO"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账户类型:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select" nullOption="true" name="accountQueryBean.accountType" id="accountType" dictTypeId="ACCOUNT_BASE_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">创建日期:</span>
              <div class="input-wrap">
                <input type="text" class="input-text default-time double-input date-start ignoreEmpy" name="accountQueryBean.openStartDate">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" class="input-text default-time-end double-input date-end ignoreEmpy" name="accountQueryBean.openEndDate">
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

  
  <%@ include file="../../include/bodyLink.jsp"%>
</body>
</html>
