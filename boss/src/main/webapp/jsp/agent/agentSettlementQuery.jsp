<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>服务商结算信息</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">
    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="agentSettlementQuery.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">服务商编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="agent_no">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账户编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="account_no">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账户名称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="account_name">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">开户行名称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="open_bank_name">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账户类型:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="agent_type" dictTypeId="ACCOUNT_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">银行编号:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" style="width:170px;" name="bank_code" styleClass="input-select" dictTypeId="BANK_CODE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">创建日期:</span>
              <div class="input-wrap">
                <input class="input-text default-time double-input date-start ignoreEmpy" name="create_time_start" type="text" >
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input class="input-text default-time-end double-input date-end ignoreEmpy" name="create_time_end" type="text">
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
