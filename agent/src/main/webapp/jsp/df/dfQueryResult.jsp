<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>提现查询-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
    <a class="fr" data-exprot="dfExport.action" href="javascript:void(0);">导出</a>
  </div>
  <s:if test="#attr['dpayRequest'].list.size()>0">
    <vlh:root value="dpayRequest" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>订单号</th>
                <th>收款人姓名</th>
                <th>收款账号</th>
                <th>金额</th>
                <th>手续费</th>
                <th>开户行</th>
                <th>账户类型</th>
                <th>状态</th>
                <th>打款原因</th>
                <th>完成描述</th>
                <th>创建时间</th>
                <th>完成时间</th>
              </tr>
            </thead>
            <tbody>
                <vlh:row bean="dpayRequest">
                  <s:set name="dpayRequest" value="#attr['dpayRequest']" />
                  <vlh:column property="flow_no" attributes="width='220px'" />
                  <vlh:column property="account_name" attributes="width='80px'" />
                  <vlh:column property="account_no" attributes="width='180px' class='accoutNoStar'" />
                  <vlh:column property="amount" attributes="width='80px'" />
                  <vlh:column property="fee" attributes="width='40px'" />
                  <vlh:column property="bank_name" attributes="width='280px'" />
                  <vlh:column attributes="width='80px'"><dict:write dictId="${dpayRequest.account_type }" dictTypeId="ACCOUNT_TYPE"></dict:write></vlh:column>
                  <vlh:column attributes="width='80px'">
                    <s:if test="${dpayRequest.audit_status=='WAIT' }">
                      <span style="color: #ff9800">待审核</span>
                    </s:if>
                    <s:elseif test="${dpayRequest.audit_status=='REFUSE' }">
                      <span style="color: red">审核拒绝</span>
                    </s:elseif>
                    <s:elseif test="${dpayRequest.audit_status=='REMIT_REFUSE' }">
                      <span style="color: red">付款拒绝</span>
                    </s:elseif>
                    <s:elseif test="${(dpayRequest.audit_status=='PASS' || dpayRequest.audit_status=='REMIT_WAIT') && (dpayRequest.status=='WAIT' || dpayRequest.status=='HANDLEDING')}">
                      <span style="color: #ff9800">处理中</span>
                    </s:elseif>
                    <s:elseif test="${dpayRequest.status=='SUCCESS'}">
                      <span style="color: #4CAF50">成功</span>
                    </s:elseif>
                    <s:elseif test="${dpayRequest.status=='FAILED'}">
                      <span style="color: red">失败</span>
                    </s:elseif>
                  </vlh:column>
                  <vlh:column attributes="width='200px'" property="description" />
                  <vlh:column attributes="width='100px'" property="error_msg" />
                  <vlh:column property="create_date" attributes="width='180px'" format="yyyy-MM-dd HH:mm:ss" />
                  <vlh:column property="finish_date" attributes="width='180px'" format="yyyy-MM-dd HH:mm:ss" />
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
