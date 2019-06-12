<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>接口请求-结果</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body class="pb-2">

  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
    <a class="f-12 fr" data-exprot="findInterfaceRequestExport.action" href="javascript:void(0);">导出</a>
  </div>
  <c:if test="${InterfaceRequestBeanList != null && InterfaceRequestBeanList.size() > 0}">
    <div class="table-warp">
      <div class="table-sroll">
        <table class="data-table shadow--2dp  w-p-100 tow-color">
          <thead>
            <tr>
              <th>业务请求号</th>
              <th>接口请求号</th>
              <th>提供方编码</th>
              <th>通道订单号</th>
              <th>商户编号</th>
              <th>订单金额</th>
              <th>接口请求状态</th>
              <th>通道返回码</th>
              <th>返回码描述</th>
              <th>支付接口</th>
              <th>接口成本</th>
              <th>创建时间</th>
              <th>完成时间</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${InterfaceRequestBeanList}" var="interfaceRequest">
              <tr>
                <td>${interfaceRequest.bussinessOrderID}</td>
                <td>${interfaceRequest.interfaceRequestID}</td>
                <td>${interfaceRequest.interfaceProviderCode}</td>
                <td>${interfaceRequest.interfaceOrderID}</td>
                <td>${interfaceRequest.ownerID}</td>
                <td><fmt:formatNumber currencySymbol="" type="currency" value="${interfaceRequest.amount }" /></td>
                <td><dict:write dictId="${interfaceRequest.status.name() }" dictTypeId="INTERFACE_STATUS_COLOR"></dict:write></td>
                <td>${interfaceRequest.responseCode}</td>
                <td>${interfaceRequest.responseMessage}</td>
                <td>${interfaceRequest.interfaceInfoCode}</td>
                <td><fmt:formatNumber currencySymbol="" type="currency" value="${interfaceRequest.fee}" /></td>
                <td><fmt:formatDate value="${interfaceRequest.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                <td><fmt:formatDate value="${interfaceRequest.completeTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    <div class="jump-rarp mt-20 text-center" id="PageWrap" total_count="${page.totalResult}" total="${page.totalPage}"  current="${page.currentPage}"></div>
  </c:if>
  <c:if test="${InterfaceRequestBeanList.size() == 0}">
    无符合条件的记录
  </c:if>
  <%@ include file="../../include/bodyLink.jsp"%>
</body>
</html>