<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
   <%@ include file="../include/header.jsp"%>
  <title>付款订单-结果</title>
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
                  <th>商户订单号</th>
                  <th>收款人姓名</th>
                  <th>收款账号</th>
                  <th>金额</th>
                  <th>手续费</th>
                  <th>开户行</th>
                  <th>账户类型</th>
                  <th>状态</th>
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
                <vlh:column property="flow_no" />
                <vlh:column property="request_no" />
                <vlh:column property="account_name" />
                <vlh:column property="account_no" />
                <vlh:column property="amount" />
                <vlh:column property="fee" />
                <vlh:column property="bank_name" />
                <vlh:column><dict:write dictId="${dpayRequest.account_type }" dictTypeId="ACCOUNT_TYPE"></dict:write></vlh:column>
                <vlh:column>
                  <s:if test="${dpayRequest.audit_status=='WAIT' }">
                    待审核
                  </s:if>
                  <s:if test="${dpayRequest.audit_status=='REFUSE'}">
                    <span style="color: #FF0000">审核拒绝</span>
                  </s:if>
                  <s:if test="${dpayRequest.audit_status=='REMIT_REFUSE' }">
                    <span style="color: #FF0000">付款拒绝</span>
                  </s:if>
                  <s:if
                    test="${(dpayRequest.audit_status=='PASS' || dpayRequest.audit_status=='REMIT_WAIT') && (dpayRequest.status=='WAIT' || dpayRequest.status=='HANDLEDING')}">
                    <span style="color: #FF9800">处理中</span>
                  </s:if>
                  <s:if test="${dpayRequest.status=='SUCCESS'}">
                    <span style="color: blue;">成功</span>
                  </s:if>
                  <s:if test="${dpayRequest.audit_status=='PASS' && dpayRequest.status=='FAILED'}">
                    <span style="color: #FF0000">付款失败</span>
                  </s:if> 
                </vlh:column>
                <vlh:column property="description" />
                <vlh:column property="error_msg" />
                <vlh:column property="create_date" format="yyyy-MM-dd HH:mm:ss" />
                <vlh:column property="audit_date" format="yyyy-MM-dd HH:mm:ss" />
                <vlh:column property="finish_date" format="yyyy-MM-dd HH:mm:ss" />
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