<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>修改历史</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body style="width: 1026px;padding: 10px;">
  
  <div class="title-h1 fix noBottom">
    <span class="primary fl">提供方信息修改历史</span>
  </div>
  <c:if test="${interfaceProviderHistoryList!= null && fn:length(interfaceProviderHistoryList) > 0}">
    <div class="table-warp">
      <div class="table-sroll">
        <table class="data-table shadow--2dp  w-p-100 tow-color">
          <thead>
            <tr>
              <th>提供方编号</th>
              <th>提供方全称</th>
              <th>提供方简称</th>
              <th>操作人</th>
              <th>创建时间</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${interfaceProviderHistoryList}" var="interfaceProviderBean">
              <tr>
                <td>${interfaceProviderBean.interfaceProviderCode}</td>
                <td>${interfaceProviderBean.fullName}</td>
                <td>${interfaceProviderBean.shortName}</td>
                <td>${interfaceProviderBean.operator}</td>
                <td><fmt:formatDate value="${interfaceProviderBean.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    <div class="jump-rarp mt-20 text-center" id="PageWrapCustomize" total_count="${page.totalResult}" total="${page.totalPage}"  current="${page.currentPage}"></div>
  </c:if>
  <c:if test="${fn:length(interfaceProviderHistoryList)==0}">
    <p>无符合条件的记录</p>
  </c:if>
  
  <%@ include file="../../include/bodyLink.jsp"%>
  <script>
   // 分页
   $('#PageWrapCustomize').pageCustom('toHistory.action');
  </script>
</body>
</html>
