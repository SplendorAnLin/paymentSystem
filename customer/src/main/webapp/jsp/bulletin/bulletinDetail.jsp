<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>公告详情</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 1020px; height: 700px; padding: 1px;">
  <s:set name="entity" value="#attr['bulletin'].list.get(0)" />
  <div class="document-preview">
    <div class="document-title floatFixed">
      <p class="title">${bulletin.title }</p>
      <div class="date-effective">
        <span class="effectTime mr-10">生效日期: <span class="date-label"><fmt:formatDate value="${bulletin.effTime}" pattern="yyyy-M-d hh:mm:ss" /></span></span>
        <span class="expireTime">失效日期: <span class="date-label"><fmt:formatDate value="${bulletin.extTime}" pattern="yyyy-M-d hh:mm:ss" /></span></span>
      </div>
    </div>
    <div class="document-content topMargin">
      ${bulletin.content }
    </div>
  </div>
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
