<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>修改信息</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 1.8em 4em;">
    <!--查询表单-->
    <div class="query-form">

      <form class="validator notification" action="operatorUpdate.action" method="post" id="form" prompt="DropdownMessage"  data-success="修改成功" data-fail="修改失败">
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
              <span class="label w-90">操作员类型:</span>
              <div class="input-wrap">
                <s:if test="${operator.roleId==1 }"><input type="text" class="input-text"readonly value="服务商"></s:if>
                <s:else>
                  <select class="input-select" name="operator.roleId" data-value="${operator.roleId}">
                    <c:forEach items="${roleAll }" var="role">
                      <option value="${role.id }" >${role.name }</option>
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
                  <dict:radio value="${operator.status }" name="operator.status" dictTypeId="ALL_STATUS"></dict:radio>
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
