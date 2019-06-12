<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html>
<head>
<meta charset="UTF-8">
<title>修改账户信息</title>
</head>
<body style="width: 400px; padding:0.2em;">

  <div style="padding: 1.8em 4em;">
  
    <s:if test="${fn:substring(operator.customerNo, 0, 1) eq '1'}">
      <!-- 修改账户信息 -->
      <div class="query-form">
        <form class="validator notification" action="${pageContext.request.contextPath}/operatorUpdate.action" method="post" prompt="DropdownMessage" data-success="修改成功" data-fail="修改失败">
          <div class="fix">
            <div class="item">
              <div class="input-area">
                <span class="label w-90">登录名(手机号):</span>
                <div class="input-wrap">
                  <input type="text" class="input-text" name="operator.username" readonly value="${operator.username}">
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">名字:</span>
                <div class="input-wrap">
                  <input type="text" class="input-text" name="operator.realname" required rangelength="[2, 10]" value="${operator.realname}">
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">类型:</span>
                <div class="input-wrap">
                  <s:if test="${operator.roleId==1 }">
                    <select class="input-select" name="operator.roleId" readonly>
                      <option value="1">管理员</option>
                    </select>
                  </s:if>
                  <s:else>
                    <select class="input-select" name="operator.roleId" data-value="${operator.roleId}">
                      <c:forEach items="${roleAll }" var="role">
                        <option value="${role.id }">${role.name }</option>
                      </c:forEach>
                    </select>
                  </s:else>
                </div>
              </div>
            </div>
            <c:if test="${operator.roleId != 1 }">
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">状态:</span>
                  <div class="input-wrap switch-wrap">
                    <dict:radio value="${operator.status }" dictTypeId="ALL_STATUS" name="operator.status"></dict:radio>
                  </div>
                </div>
              </div>
            </c:if>
            
          </div>
          <div class="text-center mt-10 hidden">
            <button class="btn btn-submit">修 改</button>
          </div>
        </form>
      </div>
      
    </s:if>
    <s:else>
       <!-- 查看账户信息 -->
      <div class="fix">
        <s:if test="${fn:substring(operator.customerNo, 0, 1) eq 'C'}">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">商户编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" disabled value="${operator.customerNo}">
              </div>
            </div>
          </div>
        </s:if>
        <s:elseif test="${fn:substring(operator.customerNo, 0, 1) eq 'A'}">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">服务商编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" disabled value="${operator.customerNo}">
              </div>
            </div>
          </div>
        </s:elseif>
        <div class="item">
          <div class="input-area">
            <span class="label w-90">真实姓名:</span>
            <div class="input-wrap">
              <input type="text" class="input-text" disabled value="${operator.realname}">
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-90">手机号码:</span>
            <div class="input-wrap">
              <input type="text" class="input-text" disabled value="${operator.username}">
            </div>
          </div>
        </div>
      </div>
    </s:else>
  
  
  
  </div>

	<%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
