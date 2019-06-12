<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>服务商费率</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">
    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="agentFeeQuery.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">服务商编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="agentNo">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">服务商全称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="fullName">
              </div>
            </div>
          </div>
          <!-- 费率,类型,状态,开通时间 -->
          <div class="item">
            <div class="input-area">
              <span class="label w-90">产品类型:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="PRODUCT_TYPE" id="PRODUCT_TYPE" dictTypeId="BF_SHARE_PAYTYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" dictTypeId="ACCOUNT_STATUS" name="status"></dict:select>
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
		<div class="item">
			<div class="input-area">
				<span class="label w-90">费率:</span>
				<div class="input-wrap">
					<input type="text" name="fee_start"
						class="input-text double-input ignoreEmpy">
				</div>
				<span class="cut"> - </span>
				<div class="input-wrap">
					<input type="text" name="fee_end"
						class="input-text double-input ignoreEmpy">
				</div>
			</div>
		</div>
          
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <a data-myiframe='{
                "target": "toAgentFeeAdd.action",
                "btns": [
                  {"lable": "取消"},
                  {"lable": "新增", "trigger": ".btn-submit"}
                ]
              }' class="btn" href="javascript:void(0);">新增费率</a>
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
