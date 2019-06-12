<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>POS请求记录-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <s:if test="#attr['requestQuery'].list.size()>0">
    <vlh:root value="requestQuery" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>交易参考号</th>
                <th>商户号</th>
                <th>交易类型</th>
                <th>网点号</th>
                <th>金额</th>
                <th>卡号</th>
                <th>终端号</th>
                <th>POS机具序列号</th>
                <th>POS流水号</th>
                <th>POS批次号</th>
                <th>返回码</th>
                <th>异常码</th>
                <th>创建时间</th>
                <th>完成时间</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="requestQuery">
                <s:set name="posRequest" value="#attr['requestQuery']" />
                <vlh:column property="external_id" attributes="width='220px'" />
                <vlh:column property="customer_no" attributes="width='80px'" />
                <vlh:column property="trans_type" attributes="width='80px'" ><dict:write dictId="${posRequest.trans_type }" dictTypeId="POS_REQUEST_TYPE"></dict:write></vlh:column>
                <vlh:column property="shop_no" attributes="width='80px'" />
                <vlh:column property="amount" attributes="width='80px'" format="#.##"/>
                <vlh:column property="pan" attributes="width='220px'" />
                <vlh:column property="pos_cati" attributes="width='220px'" />
                <vlh:column property="pos_sn" attributes="width='220px'" />
                <vlh:column property="pos_request_id" attributes="width='220px'" />
                <vlh:column property="pos_batch" attributes="width='80px'" />
                <vlh:column property="response_code" attributes="width='80px'" />
                <vlh:column property="exception_code" attributes="width='220px'" />
                <vlh:column property="create_time" attributes="width='220px'" format="yyyy-MM-dd HH:mm:ss" />
                <vlh:column property="complete_time" attributes="width='80px'" format="yyyy-MM-dd HH:mm:ss" />
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
