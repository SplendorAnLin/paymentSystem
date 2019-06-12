<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>文档详情</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 1020px;height: 700px; padding: 1px;">

  <div class="document-preview">
    <div class="document-title floatFixed">
      <p class="title">${protocolManagement.title }</p>
      <div class="date-effective">
        <span class="effectTime mr-10">创建日期: <span class="date-label"><fmt:formatDate value="${protocolManagement.createTime}"
                pattern="yyyy-M-d hh:mm:ss" /></span></span>
      </div>
    </div>
    <div class="document-content topMargin">
      ${protocolManagement.content }
    </div>
  </div>
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
