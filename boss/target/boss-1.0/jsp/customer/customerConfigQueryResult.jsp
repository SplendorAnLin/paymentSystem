<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>商户交易配置-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  
  <c:if test="${customerCofig != null && customerCofig.size() > 0 }">
    <div class="table-warp">
      <div class="table-sroll">
        <table class="data-table shadow--2dp  w-p-100 tow-color">
          <thead>
            <tr>
              <th>商户编号</th>
              <th>产品类型</th>
              <th>交易开始时间</th>
              <th>交易结束时间</th>
              <th>单笔最高金额</th>
              <th>单笔最低金额</th>
              <th>日交易上限</th>
              <th>操作日期</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${customerCofig}" var="ords" varStatus="i">
              <tr>
                <td><c:out value="${ords.customerNo }" /></td> 
                <td><dict:write dictId="${ords.productType }" dictTypeId="PRODUCT_TYPE"></dict:write></td>
                <td><c:out value="${ords.startTime }" /></td>
                <td><c:out value="${ords.endTime }" /></td>
                <td><fmt:formatNumber value="${ords.maxAmount }" pattern="#.##" /></td>
                <td><fmt:formatNumber value="${ords.minAmount }" pattern="#.##" /></td>
                <td><fmt:formatNumber value="${ords.dayMax }" pattern="#.##" /></td>
                <td><fmt:formatDate value="${ords.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                <td>
                  <button data-myiframe='{
                    "target": "${pageContext.request.contextPath}/findCustomerConfigById.action?customerConfig.id=${ords.id }",
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "修改", "trigger": ".btn-submit"}
                    ]
                  }' class="btn-small">修改</button>
                  <button data-dialog="${pageContext.request.contextPath}/customerConfigHistory.action?customerConfig.customerNo=${ords.customerNo }&customerConfig.productType=${ords.productType}" class="btn-small">操作历史</button>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    <div class="jump-rarp mt-20 text-center" id="PageWrap" total_count="${page.totalResult}" total="${page.totalPage}"  current="${page.currentPage}"></div>
  </c:if>
  <c:if test="${customerCofig eq null or customerCofig.size() == 0 }">
    无符合条件的查询结果
  </c:if>
  
 
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
