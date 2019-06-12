<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>商户路由详细信息</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body style="width: 700px; padding:10px;">
  
  
  <form method="post" style="padding: 0 0.4em 0.4em;">
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
                <span class="label w-100">合作方编码:</span>
                <div class="input-wrap">
                  <input type="text" class="input-text" value="${partnerRouterBean.partnerCode }" disabled>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-100">合作方角色:</span>
                <div class="input-wrap">
                  <dict:select styleClass="input-select" disabled="true" value="${partnerRouterBean.partnerRole }" dictTypeId="PARTNER_ROLE"></dict:select>
                </div>
              </div>
            </div>
          </div>
          <div class="col fl w-p-50">
            <div class="item">
              <div class="input-area">
                <span class="label w-100">创建时间:</span>
                <div class="input-wrap">
                  <input type="text" class="input-text" value="<fmt:formatDate value="${partnerRouterBean.createTime }" pattern="yyyy-MM-dd" />" disabled>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-100">路由状态:</span>
                <div class="input-wrap">
                  <dict:select value="${partnerRouterBean.status }" styleClass="input-select"  disabled="true" name="partnerRouterBean.status"  dictTypeId="ALL_STATUS"></dict:select>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>



    <!-- 合作方路由配置 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">合作方路由配置</span>
      </div>
      
      <c:forEach items="${partnerRouterBean.profiles }" var="profiles" varStatus="pfIdx">
        <input type="hidden" id="profiles[${pfIdx.index }].interfaceType_ipt" name="profiles[${pfIdx.index }].interfaceType" value="${profiles.interfaceType }" />
        <input type="hidden" id="profiles[${pfIdx.index }].policySelectType_ipt" name="profiles[${pfIdx.index }].policySelectType" value="${profiles.policySelectType }" />
        <div class="content">
          <div class="title-h2 fix tabSwitch2">
            <span class="primary fl">商户规则</span>
          </div>
          <div class="fix">
            <div class="col fl w-p-50">
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">接口类型:</span>
                  <div class="input-wrap">
                    <dict:select value="${profiles.interfaceType }" styleClass="input-select" dictTypeId="BF_INTERFACE_TYPE_NAME" disabled="true"></dict:select>
                  </div>
                </div>
              </div>
            </div>
            <div class="col fl w-p-50">
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">策略选择方式:</span>
                  <div class="input-wrap">
                    <dict:select value="${profiles.policySelectType }" styleClass="input-select" dictTypeId="ROUTING_TEMPLATE" disabled="true" ></dict:select>
                  </div>
                </div>
              </div>
            </div>
          </div>
  

          <!-- 路由模板 -->
          <c:forEach items="${profiles.templateInterfacePolicy }" var="templatePolicys" varStatus="tempIdx">
            <div class="title-h2 fix tabSwitch2">
              <span class="primary fl">路由模板</span>
            </div>
            <input type="hidden" id="partnerProfiles[${pfIdx.index }].templateInterfacePolicy" name="profiles[${pfIdx.index }].templateInterfacePolicy" value="${templatePolicys }" />
            <div class="fix detail">
              <div class="col fl w-p-50">
                <div class="item">
                  <div class="input-area">
                    <span class="label w-100">路由模板名称:</span>
                    <span class="value">
                      <c:if test="${MapInterfacePolicyBean !=null }">
                        <c:forEach items="${MapInterfacePolicyBean }"
                          var="MapInterfacePolicyBean">
                          <c:if test="${profiles.templateInterfacePolicy eq MapInterfacePolicyBean.key}">
                            ${MapInterfacePolicyBean.value }
                          </c:if>
                        </c:forEach>
                      </c:if>
                    </span>
                  </div>
                </div>
              </div>
              <div class="col fl w-p-50">
                <div class="item">
                  <div class="input-area">
                    <span class="label w-100">路由模板编码:</span>
                    <span class="value">${templatePolicys }</span>
                  </div>
                </div>
              </div>
            </div>
          </c:forEach>
        </div>
        
       </c:forEach>
    </div>
        
      
    
      
  </form>

  
  
  <%@ include file="../../include/bodyLink.jsp"%>
</body>
</html>
