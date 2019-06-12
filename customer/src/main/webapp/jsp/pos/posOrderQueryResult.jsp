<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>POS订单-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
    <a class="fr" href="javascript:void(0);" data-exprot="posOrderQueryExport.action">导出</a>
  </div>
  <s:if test="#attr['orderQuery'].list.size()>0">
    <vlh:root value="orderQuery" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>交易流水号</th>
                <th>商户订单号</th>
                <th>收款方</th>
                <th>收款方简称</th>
                <th>POS机具序列号</th>
                <th>支付方式</th>
                <th>订单金额</th>
                <th>手续费</th>
                <th>订单状态</th>
                <th>入账状态</th>
                <th>下单时间</th>
                <th>完成时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="orderQuery">
                <s:set name="order" value="#attr['orderQuery']" />
                <input type="hidden"
                    value="${order.customer_no }"
                    class="hiddenReceiver" />
                <vlh:column property="external_id" />
                <vlh:column property="customer_order_code" />
                <vlh:column property="customer_no" />
                <vlh:column property="short_name" />
                <vlh:column property="pos_sn" />
                <vlh:column>POS收单</vlh:column>
                <vlh:column property="amount" format="#0.00#"/>
                <vlh:column property="customer_fee" format="#0.00#"/>
                <vlh:column property="status">
                  <dict:write dictId="${order.status }" dictTypeId="POS_ORDER_STATUS"></dict:write>
                </vlh:column>
                <vlh:column property="credit_status" >
                  <dict:write dictId="${order.credit_status }" dictTypeId="CREDIT_STATUS_COLOR"></dict:write>
                </vlh:column>
                <vlh:column property="create_time"  format="yyyy-MM-dd HH:mm:ss"/>
                <vlh:column property="complete_time"  format="yyyy-MM-dd HH:mm:ss"/>
                <vlh:column>
                  <button data-dialog="${pageContext.request.contextPath}/findPosOrderByCode.action?id=${order.id }" class="btn-small">详细</button>
                </vlh:column>
              </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:else>
    <p class="pd-10">无符合条件的查询结果</p>
  </s:else>
  
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
