<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>接口提供方查询-结果</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body class="pb-2">

  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <c:if test="${interfaceProviderBeanList!= null && fn:length(interfaceProviderBeanList) > 0}">
    <div class="table-warp">
      <div class="table-sroll">
        <table class="data-table shadow--2dp  w-p-100 tow-color">
          <thead>
            <tr>
              <th>提供方编号</th>
              <th>提供方全称</th>
              <th>提供方简称</th>
              <th>创建时间</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${interfaceProviderBeanList}" var="list">
              <tr>
                <td>${list.code}</td>
                <td>${list.fullName}</td>
                <td>${list.shortName}</td>
                <td><fmt:formatDate value="${list.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                <td>
                  <button data-myiframe='{
                    "target": "${pageContext.request.contextPath }/toUpdateInterfaceProvider.action?code=${list.code}",
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "修改", "trigger": ".btn-submit"}
                    ]
                  }' class="btn-small">修改</button>
                  <a class="btn-small" data-dialog="${pageContext.request.contextPath }/toHistory.action?code=${list.code}">历史</a>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    <div class="jump-rarp mt-20 text-center" id="PageWrap" total_count="${page.totalResult}" total="${page.totalPage}"  current="${page.currentPage}"></div>
  </c:if>
  <c:if test="${fn:length(interfaceProviderBeanList)==0}">
    无符合条件的记录
  </c:if>
  
  <%@ include file="../../include/bodyLink.jsp"%>
</body>
</html>
