<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>服务商信息</title>
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
              <span class="label w-90">服务商手机号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="agent.phoneNo">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">服务商等级:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="agentLevel" dictTypeId="SERVICE_PROVIDER_LEVEL"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">服务商状态:</span>
              <div class="input-wrap">
                <select class="input-select" name="agent.status">
                  <option value="">全部</option>
                  <option value="AUDIT">审核中</option>
                  <option value="TRUE">开通</option>
                  <option value="FALSE">关闭</option>
                  <option value="AUDIT_REFUSE">审核拒绝</option>
                </select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">服务商类型:</span>
              <div class="input-wrap">
                <select class="input-select" name="agent.agentType">
                  <option value="">全部</option>
                  <option value="INDIVIDUAL">个体户</option>
                  <option value="OPEN">公司</option>
                </select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">创建日期:</span>
              <div class="input-wrap">
                <input class="input-text double-input default-date date-start ignoreEmpy" name="create_time_start" type="text" >
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input class="input-text double-input default-date-end date-end ignoreEmpy" name="create_time_end" type="text">
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
