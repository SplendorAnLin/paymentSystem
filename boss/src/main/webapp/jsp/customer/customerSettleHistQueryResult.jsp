<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>操作历史</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 1200px;padding: 10px 10px 0px 10px;">
  
  <div class="title-h1 fix noBottom">
    <span class="primary fl">商户结算历史操作信息</span>
  </div>
  
  <s:if test="#attr['customerSettleHist'].list.size()>0">
    <vlh:root value="customerSettleHist" url="?" includeParameters="*" configName="defaultlook">
      
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>商户编号</th>
                <th>账户名称</th>
                <th>账户编号</th>
                <th>商户类型</th>
                <th>银行编号</th>
                <th>开户行名称</th>
                <th>操作人</th>
                <th>创建时间</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="customerSettleHist">         
                <s:set name="entity" value="#attr['customerSettleHist']" />         
                <vlh:column property="customer_no" />
                <vlh:column property="account_name" />
                <vlh:column property="account_no" />
                <vlh:column><dict:write dictId="${entity.customer_type }" dictTypeId="AGENT_TYPE"></dict:write></vlh:column>
                <vlh:column property="bank_code" ><dict:write dictId="${entity.bank_code }" dictTypeId="BANK_CODE"></dict:write></vlh:column>
                <vlh:column property="open_bank_name" />
                <vlh:column property="oper" />
                <vlh:column><s:date name="#entity.create_time" format="yyyy-MM-dd HH:mm:ss" /></vlh:column>
              </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:if test="#attr['customerSettleHist'].list.size()==0">
    无符合条件的查询结果
  </s:if>
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
