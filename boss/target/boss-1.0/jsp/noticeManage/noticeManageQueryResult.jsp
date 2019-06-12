<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>客户端配置管理-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  
  <c:if test="${page.object != null && page.object.size() > 0}">
    <div class="table-warp">
      <div class="table-sroll">
        <table class="data-table shadow--2dp  w-p-100 tow-color">
          <thead>
            <tr>
              <th>服务商编号</th>
              <th>名称</th>
              <th>类型</th>
              <th>状态</th>
              <th>oem标志</th>
              <th>操作员</th>
              <th>创建时间</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${page.object}" var="noticeConfigBean">
              <tr>
                <td>${noticeConfigBean.ownerId}</td>
                <td>${noticeConfigBean.name}</td>
                <td>${noticeConfigBean.type}</td>
                <td>${noticeConfigBean.stauts }</td>
                <td>${noticeConfigBean.oem }</td>
                <td>${noticeConfigBean.oper }</td>
                <%-- <td><fmt:formatDate value="${noticeConfigBean.createDate.time}" pattern="yyyy-MM-dd HH:mm:ss" /></td> --%>
                <td></td>
                <td>
                  <button data-myiframe='{
                    "target": "findNoticeConfig.action?id=${noticeConfigBean.id}",
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "修改", "trigger": ".btn-submit"}
                    ]
                  }' class="btn-small">修改</button>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    <div class="jump-rarp mt-20 text-center" id="PageWrap" total_count="${page.totalResult}" total="${page.totalPage}"  current="${page.currentPage}"></div>
  </c:if>
  <c:if test="${page.object.size() == 0}">
    无符合条件的记录
  </c:if>
  
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
