<%@ page language="java" pageEncoding="UTF-8" %>
<html>
<head>
  <title>内部对账查询</title>
  <%@ include file="../include/header.jsp" %>
</head>
<body>

<div class="query-box">
  <!--查询表单-->
  <div class="query-form mt-10">
    <form id="findReconByDateForm" action="${pageContext.request.contextPath }/findReconByDate.action" method="post" target="query-result">
      <div class="adaptive fix">
        <div class="item">
          <div class="input-area">
            <span class="label w-90">对账类型:</span>
            <div class="input-wrap">
              <dict:select nullOption="true" styleClass="input-select" name="reconOrder.reconType"
                           dictTypeId="RECON_TYPE"></dict:select>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-90">对账状态:</span>
            <div class="input-wrap">
              <dict:select nullOption="true" styleClass="input-select" name="reconOrder.reconStatus"
                           dictTypeId="RECON_STATUS"></dict:select>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-90">对账日期:</span>
            <div class="input-wrap">
              <input type="text" name="reconOrder.reconDate" class="input-text double-input date-start ignoreEmpy" date-fmt="yyyy-MM-dd">
             </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-90">创建日期:</span>
            <div class="input-wrap">
              <input type="text" name="startTime" class="input-text double-input date-start default-time ignoreEmpy">
            </div>
            <span class="cut"> - </span>
            <div class="input-wrap">
              <input type="text" name="endTime" class="input-text double-input date-end default-time-end ignoreEmpy">
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

<%@ include file="../include/bodyLink.jsp" %>
</body>
</html>
