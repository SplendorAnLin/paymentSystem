<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html>
<head>
<meta charset="UTF-8">
<title>修改账户信息</title>
</head>
<body style="width: 350px; padding: 10px;">

  <div style="padding: 10px;">
    <div class="query-form">
      <form class="validator notification" action="${pageContext.request.contextPath}/operatorUpdate.action" method="post" prompt="DropdownMessage" data-success="修改成功" data-fail="修改失败">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">手机号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="operator.username" readonly value="${operator.username}">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">操作员类型:</span>
              <div class="input-wrap">
                <s:if test="${operator.roleId==1 }">
                  <select class="input-select" readonly>
                    <option value="1">商户</option>
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
          <div class="item">
            <div class="input-area">
              <span class="label w-90">名字:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="operator.realname" required rangelength="[2, 10]" value="${operator.realname}">
              </div>
            </div>
          </div>
          <s:if test="${phone eq operator.username }"></s:if>
          <s:else>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">状态:</span>
                <div class="input-wrap switch-wrap">
                  <dict:radio value="${operator.status }" dictTypeId="ALL_STATUS" name="operator.status"></dict:radio>
                </div>
              </div>
            </div>
          </s:else>
        </div>
        <div class="text-center mt-10 hidden">
          <button class="btn btn-submit">修 改</button>
        </div>
      </form>
    </div>
  
  </div>

  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
