<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>路由模板详细信息</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body style="width: 700px; padding:10px;">
  
  <form method="post" style="padding: 0 0.4em 0.4em;">
    <input type="hidden" value="${interfacePolicyBean.interfaceType }" name="interfaceType" />
    <!-- 基本信息 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">基本信息</span>
      </div>
      <div class="content">
        <div class="fix">
          <div class="col fl w-p-50">
            <div class="item">
              <div class="input-area">
                <span class="label w-100">路由编码:</span>
                <div class="input-wrap">
                  <input type="text" class="input-text" value="${interfacePolicyBean.code }" readonly>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-100">接口类型:</span>
                <div class="input-wrap">
                  <dict:select id="interfaceType" name="interfacePolicyBean.interfaceType" styleClass="input-select" disabled="true" dictTypeId="INTERFACE_TYPE"></dict:select>
                </div>
              </div>
            </div>
          </div>
          <div class="col fl w-p-50">
            <div class="item">
              <div class="input-area">
                <span class="label w-100">路由名称:</span>
                <div class="input-wrap">
                  <input type="text" class="input-text" value="${interfacePolicyBean.name }" readonly>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-100">路由状态:</span>
                <div class="input-wrap">
                  <dict:select value="${interfacePolicyBean.status }" dictTypeId="ACCOUNT_STATUS" name="interfacePolicyBean.status" id="status" styleClass="input-select" disabled="true"></dict:select>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>



    <!-- 策略规则 -->
    <c:forEach items="${interfacePolicyBean.profiles }" varStatus="pfs" var="pfiles">
      <input type="hidden" id="profiles[${pfs.index }].cardType_ipt" name="interfacePolicyBean.profiles[${pfs.index }].cardType" value="${pfiles.cardType }" />
      <div class="row">
        <div class="title-h1 fix tabSwitch2">
          <span class="primary fl">规则 ${pfs.index }</span>
        </div>
        <div class="content">
          <div class="fix">
            <div class="col fl w-p-50">
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">卡类型:</span>
                  <div class="input-wrap">
                    <dict:select styleClass="input-select" disabled="true" dictTypeId="CARD_TYPE"></dict:select>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">接口策略类型:</span>
                  <div class="input-wrap">
                    <dict:select value="${pfiles.policyType }" disabled="true" styleClass="input-select" dictTypeId="INTERFACE_POLICY"></dict:select>
                  </div>
                </div>
              </div>
            </div>
            <div class="col fl w-p-50">
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">有效期:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text double-input date-start" value="<fmt:formatDate value="${pfiles.effectTime }" pattern="yyyy-MM-dd"/>" readonly >
                  </div>
                  <span class="cut"> - </span>
                  <div class="input-wrap">
                    <input type="text" class="input-text double-input date-end" value="<fmt:formatDate value="${pfiles.expireTime }" pattern="yyyy-MM-dd"/>" readonly >
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">接口提供方编码:</span>
                  <div class="input-wrap">
                    <dict:select value="${pfiles.interfaceProviderCode }" disabled="true" styleClass="input-select ProviderCode" dictTypeId="BANK_CODE"></dict:select>
                  </div>
                </div>
              </div>
            </div>
          </div>
  
          <!-- 接口信息 -->
          <div class="title-h2 fix tabSwitch2">
            <span class="primary fl">接口信息</span>
          </div>
          <div class="fix detail">
            <c:forEach items="${pfiles.interfaceInfos }" varStatus="iifs" var="interInfos">
              <div class="col fl w-p-50">
                <div class="item row">
                  <div class="input-area">
                    <span class="label w-100">接口名称:</span>
                    <c:forEach items="${MaplistInterfaceInfoBean }" var="MaplistInterfaceInfoBean">
                      <c:if test="${interInfos.interfaceCode eq MaplistInterfaceInfoBean.key}">
                         <span class="value">${MaplistInterfaceInfoBean.value }</span>
                      </c:if>
                    </c:forEach>
                  </div>
                </div>
              </div>
              <div class="col fl w-p-50">
                <div class="item row">
                  <div class="input-area">
                    <span class="label w-100">权重\比列:</span>
                    <span class="value">${interInfos.scale }</span>
                  </div>
                </div>
              </div>
            </c:forEach>
         </div>
        </div>
      </div>
      
    </c:forEach>
    
    

  </form>

  
    
  <%@ include file="../../include/bodyLink.jsp"%>
</body>
</html>
