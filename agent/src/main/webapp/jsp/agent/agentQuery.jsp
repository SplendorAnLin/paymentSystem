<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>服务商查询</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">

    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="agentQuery.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">服务商编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="agent.agentNo">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">服务商全称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="agent.fullName">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">服务商等级:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select" name="agentLevel" nullOption="true" dictTypeId="SERVICE_PROVIDER_LEVEL"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">服务商状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="agent.status" dictTypeId="SERVICE_PROVIDER_STATUS"></dict:select>
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
