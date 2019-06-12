<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>接口信息详细</title>
  <%@ include file="../../include/header.jsp"%>
  <style>
    .detail .label {
      width: 180px;
    }
    .detail .value {
      width: calc(100% - 200px);
    }
  </style>
</head>
<body style="width: 870px; padding:0.2em;">
  
  <div style="padding: 0.4em;">
    <!-- 基本信息 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">基本信息</span>
      </div>
      <div class="content detail">

        <div class="col w-p-50">
          <div class="row">
            <span class="label">接口编号:</span>
            <span class="value">${interfaceInfoBean.code}</span>
          </div>
          <div class="row">
            <span class="label">接口类型:</span>
            <span class="value color-orange"><dict:write dictId="${interfaceInfoBean.type }" dictTypeId="BF_INTERFACE_TYPE_NAME"></dict:write></span>
          </div>
          <div class="row">
            <span class="label">接口提供方:</span>
            <span class="value">${interfaceInfoBean.provider}</span>
          </div>
          <div class="row">
            <span class="label">交易时间:</span>
            <span class="value">${interfaceInfoBean.startTime } ~ ${interfaceInfoBean.endTime }</span>
          </div>
          <div class="row">
            <span class="label">单笔最大限额:</span>
            <span class="value"><fmt:formatNumber value="${interfaceInfoBean.singleAmountLimit }" pattern="#.##" />元</span>
          </div>
        </div>
        <div class="col w-p-50">
          <div class="row">
            <span class="label">接口名称:</span>
            <span class="value">${interfaceInfoBean.name}</span>
          </div>
          <div class="row">
            <span class="label">接口状态:</span>
            <span class="value color-green"><dict:write dictId="${interfaceInfoBean.status }" dictTypeId="ACCOUNT_STATUS_COLOR"></dict:write></span>
          </div>
          <div class="row">
            <span class="label">费率类型:</span>
            <span class="value"><dict:write dictId="${interfaceInfoBean.feeType }" dictTypeId="FEE_TYPE"></dict:write></span>
          </div>
          <div class="row">
            <span class="label">费率:</span>
            <span class="value"><fmt:formatNumber value="${interfaceInfoBean.fee }" pattern="#.#####"/></span>
          </div>
          <div class="row">
            <span class="label">单笔最小限额:</span>
            <span class="value"><fmt:formatNumber value="${interfaceInfoBean.singleAmountLimitSmall }" pattern="#.##" />元</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 交易配置详细信息 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">交易配置详细信息</span>
      </div>
      <div class="content detail">
        <div class="fix">
          <c:if test="${tradeConfigs != null && tradeConfigs.size() > 0}">
            <c:forEach var="property" items="${tradeConfigs}">
              <div class="row">
                <span class="label">${property.key}:</span>
                <span class="value">${property.value}</span>
              </div>
            </c:forEach>
          </c:if>
          <c:if test="${tradeConfigs ==null || tradeConfigs.size() <= 0}">
            暂无交易配置!
          </c:if>
          
        </div>
      </div>
    </div>

    <!-- 描述 -->
    <c:if test="${fn:length(interfaceInfoBean.description)>0}">
    	<div class="row">
	      <div class="title-h1 fix tabSwitch2">
	        <span class="primary fl">描述</span>
	      </div>
	      <div class="content">
	        <div class="textarea-box">
	          <textarea name="infoBean.description" readonly class="w-p-100" style="height: 1.8rem;">${interfaceInfoBean.description }</textarea>
	        </div>
	      </div>
       </div>
     </c:if>
  </div>

  
  <%@ include file="../../include/bodyLink.jsp"%>
</body>
</html>
