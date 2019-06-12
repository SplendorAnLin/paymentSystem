<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>商户路由</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">
    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="${pageContext.request.contextPath}/findAllPartnerRouterAction.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">合作方编码:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="partnerRouterQueryBean.partnerCode">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">接口类型:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select" name="partnerRouterQueryBean.profiles.interfaceType" id="interfaceType" nullOption="true" value="input-select" dictTypeId="BF_INTERFACE_TYPE_NAME"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">合作方角色:</span>
              <div class="input-wrap">
                <dict:select id="partnerRole" nullOption="true" name="partnerRouterQueryBean.partnerRole" styleClass="input-select" dictTypeId="PARTNER_ROLE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">商户路由状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" value="${partnerRouterQueryBean.status }" styleClass="input-select" name="partnerRouterQueryBean.status" id="partnerRole" dictTypeId="ACCOUNT_STATUS" ></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">创建时间:</span>
              <div class="input-wrap">
                <input type="text" name="partnerRouterQueryBean.createStartTime" class="input-text default-time double-input date-start ignoreEmpy">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="partnerRouterQueryBean.createEndTime" class="input-text default-time-end double-input date-end ignoreEmpy">
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <a data-myiframe='{
                "target": "${pageContext.request.contextPath }/partnerRouter/toPartnerRouter.action",
                "btns": [
                  {"lable": "取消"},
                  {"lable": "新增", "trigger": ".btn-submit"}
                ]
              }' class="btn" href="javascript:void(0);">新增商户路由</a>
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
