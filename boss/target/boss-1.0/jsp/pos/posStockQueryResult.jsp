<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>POS申请-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <s:if test="#attr['posStockQuery'].list.size()>0">
    <vlh:root value="posStockQuery" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp w-p-100 tow-color">
            <thead>
              <tr>
                <th>商户号</th>
				<th>POS类型</th>
				<th>商户电话</th>
				<th>商户地址</th>
				<th>备注</th>
				<th>请求类型</th>
				<th>请求时间</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="posStockQuery">
              	<s:set name="pos" value="#attr['posStockQuery']"/>
              	<vlh:column property="customer_no"/>
              	<vlh:column property="pos_type"><dict:write dictId="${pos.pos_type }" dictTypeId="POS_TYPE_ATTR"></dict:write></vlh:column>
              	<vlh:column property="phone"/>
              	<vlh:column property="addr"/>
              	<vlh:column property="remark"/>
              	<vlh:column property="request_record"><dict:write dictId="${pos.request_record }" dictTypeId="REQUEST_RECORD"></dict:write></vlh:column>
              	<vlh:column property="create_time" format="yyyy-MM-dd HH:mm:ss"/>
              </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:if test="#attr['posStockQuery'].list.size()==0">
    	<p class="pd-10">无符合条件的查询结果</p>
  </s:if>
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>