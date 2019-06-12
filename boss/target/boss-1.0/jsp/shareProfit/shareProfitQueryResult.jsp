<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>经营分析-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">

  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
    <a class="fr" href="javascript:void(0);" data-exprot="spExport.action">导出</a>
  </div>
  
  <s:if test="#attr['shareProfit'].list.size()>0">
    <vlh:root value="shareProfit" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>订单号</th>
                <th>接口编号</th>
                <th>订单来源</th>
                <th>服务商编号</th>
                <th>商户编号</th>
                <th>产品类型</th>
                <th>订单金额</th>
                <th>商户费率</th>
                <th>订单手续费</th>
                <th>通道成本</th>
                <th>服务商利润</th>
                <th>直属服务商利润</th>
                <th>间属服务商利润</th>
                <th>平台利润</th>
                <th>订单完成时间</th>
                <th>分润时间</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="shareProfit">
                <s:set name="entity" value="#attr['shareProfit']" />
                <vlh:column property="order_code" attributes="width='100px'" />
                <vlh:column property="interface_code" attributes="width='150px'" />
                <vlh:column><dict:write dictId="${entity.source }" dictTypeId="ORDER_SOURCE"></dict:write></vlh:column>
                <vlh:column property="agent_no" attributes="width='100px'" />
                <vlh:column property="customer_no" attributes="width='80px'" />
                <vlh:column attributes="width='80px'"><dict:write dictId="${entity.product_type }" dictTypeId="BF_SHARE_PAYTYPE"></dict:write></vlh:column>
                <vlh:column property="amount" attributes="width='150px'" />
                <vlh:column property="customer_fee" attributes="width='100px'" />
                <vlh:column property="fee" attributes="width='100px'" />
                <vlh:column property="channel_cost" attributes="width='80px'" />
                <vlh:column property="agent_profit" attributes="width='80px'" />
                <vlh:column property="direct_agent_profit" attributes="width='80px'" />
                <vlh:column property="indirect_agent_profit" attributes="width='80px'" />
                <vlh:column property="platfrom_profit" attributes="width='100px'" />
                <vlh:column property="order_time" attributes="width='150px'" format="yyyy-MM-dd HH:mm:ss" />
                <vlh:column property="create_time" attributes="width='150px'" format="yyyy-MM-dd HH:mm:ss" />
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
