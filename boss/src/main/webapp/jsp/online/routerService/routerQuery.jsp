<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>路由模板</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">

    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="${pageContext.request.contextPath}/router/findAllInterfacePolicy.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">路由编码:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="interfacePolicyQueryBean.code">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">模板名称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="interfacePolicyQueryBean.name">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">接口类型:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" name="interfacePolicyQueryBean.interfaceType" styleClass="input-select" dictTypeId="INTERFACE_TYPE" id="interfaceType"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">卡类型:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" id="cardType" name="interfacePolicyQueryBean.profiles.cardType" styleClass="input-select" dictTypeId="CARD_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" name="interfacePolicyQueryBean.status" styleClass="input-select" dictTypeId="ACCOUNT_STATUS"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">创建时间:</span>
              <div class="input-wrap">
                <input type="text" name="interfacePolicyQueryBean.createStartTime" class="input-text default-time double-input date-start ignoreEmpy">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="interfacePolicyQueryBean.createEndTime" class="input-text default-time-end double-input date-end ignoreEmpy">
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <a data-myiframe='{
                "target": "${pageContext.request.contextPath }/router/toAddRouter.action",
                "btns": [
                  {"lable": "取消"},
                  {"lable": "新增", "trigger": ".btn-submit"}
                ]
              }' class="btn" href="javascript:void(0);">新增路由模板</a>
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
