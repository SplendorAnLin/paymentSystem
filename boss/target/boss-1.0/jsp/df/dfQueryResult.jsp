<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>付款订单-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
    <a class="fr" data-exprot="dfExport.action" href="#">导出</a>
  </div>
  
  <s:if test="#attr['dpayRequest'].list.size()>0">
    <vlh:root value="dpayRequest" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>商户编号</th>
                <th>订单号</th>
                <th>商户订单号</th>
                <th>收款人姓名</th>
                <th>收款账号</th>
                <th>金额</th>
                <th>手续费</th>
                <th>开户行</th>
                <th>账户类型</th>
                <th>状态</th>
                <th>代付类型</th>
                <td>打款原因</td>
                <td>完成描述</td>
                <td>创建时间</td>
                <td>审核时间</td>
                <td>完成时间</td>
              </tr>
            </thead>
            <tbody>
                <vlh:row bean="dpayRequest">
                  <s:set name="dpayRequest" value="#attr['dpayRequest']" />
                  <vlh:column property="owner_id" attributes="width='80px'" />
                  <vlh:column property="flow_no" attributes="width='220px'" />
                  <vlh:column property="request_no" attributes="width='180px'" />
                  <vlh:column property="account_name" attributes="width='80px'" />
                  <vlh:column property="account_no" attributes="width='180px' class='accoutNoStar'" />
                  <vlh:column property="amount" attributes="width='80px'" format="#.##"/>
                  <vlh:column property="fee" attributes="width='40px'" format="#.##"/>
                  <vlh:column property="bank_name" attributes="width='280px'" />
                  <vlh:column attributes="width='80px'"><dict:write dictId="${dpayRequest.account_type }" dictTypeId="ACCOUNT_TYPE"></dict:write></vlh:column>
                  <vlh:column attributes="width='80px'">
                    <s:if test="${dpayRequest.audit_status=='WAIT' }">
                      商户审核
                    </s:if>
                            <s:if test="${dpayRequest.audit_status=='REMIT_WAIT' }">
                      <span style="color:green;">付款审核</span>
                    </s:if>
                            <s:if test="${dpayRequest.audit_status=='REFUSE'}">
                      <span style="color: red;">审核拒绝</span>
                    </s:if>
                            <s:if test="${dpayRequest.audit_status=='REMIT_REFUSE' }">
                      <span style="color: red;">付款拒绝</span>
                    </s:if>
                            <s:if
                              test="${dpayRequest.audit_status=='PASS' && (dpayRequest.status=='WAIT' || dpayRequest.status=='HANDLEDING')}">
                      <span style="color:#ff9800;">处理中</span>
                    </s:if>
                            <s:if test="${dpayRequest.status=='SUCCESS'}">
                      <span style="color: blue;">成功</span>
                    </s:if> 
                            <s:if test="${dpayRequest.audit_status=='PASS' && dpayRequest.status=='FAILED'}">
                      <span style="color: #FF0000;">付款失败</span>
                    </s:if> 
                 </vlh:column>
                  <vlh:column attributes="width='80px'"><dict:write dictId="${dpayRequest.request_type }" dictTypeId="DF_TYPE"></dict:write></vlh:column>
                  <vlh:column attributes="width='200px'" property="description" />
                  <vlh:column attributes="width='100px'" property="error_msg"  />
                  <vlh:column property="create_date" attributes="width='180px'" format="yyyy-MM-dd HH:mm:ss" />
                  <vlh:column property="audit_date" attributes="width='180px'" format="yyyy-MM-dd HH:mm:ss" />
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
