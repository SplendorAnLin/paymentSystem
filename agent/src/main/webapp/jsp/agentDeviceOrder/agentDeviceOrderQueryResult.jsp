<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>设备采购订单</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">

  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <c:if test="${agentDeviceOrderInfo!=null &&agentDeviceOrderInfo.size()>0}">
    <div class="table-warp">
      <div class="table-sroll">
        <table class="data-table shadow--2dp  w-p-100 tow-color">
          <thead>
            <th>订单流水号</th>
            <th>设备类型</th>
            <th>采购渠道</th>
            <th>数量</th>
            <th>单价</th>
            <th>总价</th>
            <th>订单状态</th>
            <th>采购状态</th>
            <th>付款状态</th>
            <th>操作</th>
            <th>付款订单号</th>
            <th>采购人</th>
            <th>创建时间</th>
          </thead>
          <tbody>
            <c:forEach items="${agentDeviceOrderInfo }" var="info">
              <tr>
                <td>${info.purchase_serial_number }</td>
                <td>${info.device_name }</td>
                <td><dict:write dictId="${info.purchase_channel }" dictTypeId="PURCHASE_CHANNEL"></dict:write></td>
                <td>${info.quantity }</td>
                <td><fmt:formatNumber value="${info.unit_price }" pattern="#.##" /></td>
                <td><fmt:formatNumber value="${info.total}" pattern="#.##" /></td>
                <td><dict:write dictId="${info.flow_status }" dictTypeId="DEVICE_ORDER_STATUS_COLOR"></dict:write></td>
                <td><dict:write dictId="${info.purchase_status }" dictTypeId="AGENT_QRSTATUS_COLOR"></dict:write></td>
                <td><dict:write dictId="${info.order_status }" dictTypeId="TRADE_ORDER_STATUS_COLOR"></dict:write></td>
                <td>
                  <button data-myiframe='{
                    "target": "payAgain.action?outOrderId=${info.out_order_id }",
                    "btnType": 2,
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "支付", "trigger": ".btn-submit"}
                    ]
                  }' class="btn-small <c:if test="${info.order_status == 'SUCCESS' }">disabled</c:if> ">再次支付</button>
                </td>
                <td>${info.out_order_id }</td>
                <td>${info.user }</td>
                <td><fmt:formatDate value="${info.create_time }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    <div class="jump-rarp mt-20 text-center" id="PageWrap" total_count="${page.totalResult}" total="${page.totalPage}"  current="${page.currentPage}"></div>
  </c:if>
  <c:if test="${agentDeviceOrderInfo eq null or agentDeviceOrderInfo.size() == 0}">
    <p class="pd-10">无符合条件的记录</p>
  </c:if>
  


  
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
