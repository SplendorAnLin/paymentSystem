<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>接口信息-结果</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  
  <c:if test="${interfaceInfoBeanList!= null && interfaceInfoBeanList.size() > 0}">
    <div class="table-warp">
      <div class="table-sroll">
        <table class="data-table shadow--2dp  w-p-100 tow-color">
          <thead>
            <tr>
              <th>接口编号</th>
              <th>提供方编号</th>
              <th>接口名称</th>
              <th>接口类型</th>
              <th>接口状态</th>
              <th>单笔最大限额</th>
              <th>单笔最小限额</th>
              <th>创建时间</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${interfaceInfoBeanList}" var="list">
              <tr>
                <td>${list.code}</td>
                <td>${list.provider}</td>
                <td>${list.name}</td>
                <td><dict:write dictId="${list.type }" dictTypeId="BF_INTERFACE_TYPE_NAME"></dict:write></td>
                <td><dict:write dictId="${list.status }" dictTypeId="ACCOUNT_STATUS_COLOR"></dict:write></td>
                <td><fmt:formatNumber value="${list.singleAmountLimit}" pattern="#.##" /></td>
                <td><fmt:formatNumber value="${list.singleAmountLimitSmall}" pattern="#.##" /></td>
                <td><fmt:formatDate value="${list.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                <td>
                  <button data-myiframe='{
                    "target": "toUpdateInterfaceInfoAction.action?code=${list.code}",
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "修改", "trigger": ".btn-submit"}
                    ]
                  }' class="btn-small">修改</button>
                  <button data-dialog="toDetailInterfaceInfoAction.action?code=${list.code}&providerCode=${list.provider}" class="btn-small">详细</button>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    <div class="jump-rarp mt-20 text-center" id="PageWrap" total_count="${page.totalResult}" total="${page.totalPage}"  current="${page.currentPage}"></div>
  </c:if>
  <c:if test="${interfaceInfoBeanList.size() == 0}">
    无符合条件的记录
  </c:if>
  
  
  <%@ include file="../../include/bodyLink.jsp"%>
</body>
</html>
