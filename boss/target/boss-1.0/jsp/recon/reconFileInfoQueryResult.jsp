<%@ page language="java" pageEncoding="UTF-8" %>
<html>
<head>
  <title>对账文件信息 - 结果</title>
  <%@ include file="../include/header.jsp" %>
</head>
<body class="pb-2">

<div class="title-h1 fix">
  <span class="primary fl">查询结果</span>
</div>
<c:if test="${page.object != null && page.object.size() > 0 }">
  <div class="table-warp">
    <div class="table-sroll">
      <table class="data-table shadow--2dp  w-p-100 tow-color">
        <thead>
        <tr>
          <td>接口编号</td>
          <td>接口名称</td>
          <td>文件名</td>
          <td>系统编号</td>
          <td>对账文件类型</td>
          <td>是否开启</td>
          <td>创建时间</td>
          <td>路径</td>
          <td>备注</td>
          <td>操作</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.object }" var="reconFileInfo">
          <tr>
            <td>${reconFileInfo.reconFileInfoExtend.interfaceInfoCode}</td>
            <td>${reconFileInfo.reconFileInfoExtend.bankName}</td>
            <td>${reconFileInfo.fileName}</td>
            <td>${reconFileInfo.systemCode}</td>
            <td>${reconFileInfo.reconFileType.remark}</td>
            <td>
              <c:choose>
                <c:when test="${reconFileInfo.valid=='1'}"><span style="color:blue;">开启</span></c:when>
                <c:otherwise><span style="color: red;">关闭</span></c:otherwise>
              </c:choose>
            </td>
            <td><fmt:formatDate value="${reconFileInfo.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>${reconFileInfo.filePath}</td>
            <td>${reconFileInfo.remark}</td>
            <td>
              <c:choose>
                <c:when test="${reconFileInfo.isBankChannel=='1'}">
                  <button data-myiframe='{
                    "target": "toReconFileInfoModify.action?reconFileInfo.code=${reconFileInfo.code }",
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "修改", "trigger": ".btn-submit"}
                    ]
                  }' class="btn-small">修改
                  </button>
                </c:when>
                <c:otherwise>不允许修改</c:otherwise>
              </c:choose>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
  <div class="jump-rarp mt-20 text-center" id="PageWrap" total_count="${page.totalResult}" total="${page.totalPage}"
       current="${page.currentPage}"></div>
</c:if>
<c:if test="${page.object eq null or page.object.size() == 0 }">
  无符合条件的记录
</c:if>

<%@ include file="../include/bodyLink.jsp" %>
</body>
</html>
