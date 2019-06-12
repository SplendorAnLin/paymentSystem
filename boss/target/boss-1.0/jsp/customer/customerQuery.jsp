<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>商户信息</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>

  <div class="query-box">

    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="customerQuery.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">商户编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="customer_no">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">商户简称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="shortName">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">商户状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="status" id="accountType" dictTypeId="CUSTOMER_STATUS"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">商户类型:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="customer_type" id="accountType" dictTypeId="AGENT_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">所属服务商:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="AGENT_NO">
              </div>
            </div>
          </div>

		<div class="item">
			<div class="input-area">
				<span class="label w-90">创建时间:</span>
				<div class="input-wrap">
					<input type="text" name="create_time_start"
						class="input-text double-input date-start default-time ignoreEmpy">
				</div>
				<span class="cut"> - </span>
				<div class="input-wrap">
					<input type="text" name="create_time_end"
						class="input-text double-input date-end default-time-end ignoreEmpy">
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
