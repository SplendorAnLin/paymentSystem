<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>代收订单查询-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
    <a class="fr" href="javascript:void(0);" data-exprot="receiveOrderExport.action">导出</a>
  </div>
  <s:if test="#attr['receiveOrderQuery'].list.size()>0">
    <vlh:root value="receiveOrderQuery" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>所有者编号</th>
                <th>收款单号</th>
                <th>银行编码</th>
                <th>账户号</th>
                <th>账户名称</th>
                <th>账户类型</th>
                <th>账号类型</th>
                <th>金额</th>
                <th>成本</th>
                <th>手续费</th>
                <th>证件类型</th>
                <th>证件号码</th>
                <th>订单状态</th>
                <th>清算状态</th>
                <th>创建时间</th>
                <th>完成时间</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="defConfig">
                <s:set name="entity" value="#attr['defConfig']" />
                <vlh:column property="customer_no" attributes="width='10%'" />
                <vlh:column property="receive_id" attributes="width='12%'"/>
                <vlh:column property="payer_bank_no" attributes="width='10%'" />
                <vlh:column property="account_no" attributes="width='7%'"/>
                <vlh:column property="account_name" attributes="width='7%'"/>
                <vlh:column property="acc_type" attributes="width='8%'">
                  <dict:write dictId="${entity.acc_type }" dictTypeId="ACCOUNT_TYPE"></dict:write>
                </vlh:column>
                <vlh:column property="acc_no_type" attributes="width='8%'">
                  <dict:write dictId="${entity.acc_no_type }" dictTypeId="ACCOUNT_CARD"></dict:write>
                </vlh:column>
                <vlh:column property="amount" attributes="width='10%'"/>
                <vlh:column property="cost" attributes="width='10%'"/>
                <vlh:column property="fee" attributes="width='10%'"/>
                <vlh:column property="certificates_type" attributes="width='8%'">
                  <dict:write dictId="${entity.certificates_type }" dictTypeId="TYPE_OF_CERTIFICATE"></dict:write>
                </vlh:column>
                <vlh:column property="certificates_code" attributes="width='10%'"/>
                <vlh:column attributes="width='8%'">
                  <dict:write dictId="${entity.order_status }" dictTypeId="ORDER_STATUS_COLOR"></dict:write>
                </vlh:column>
                <vlh:column attributes="width='8%'">
                  <dict:write dictId="${entity.clear_status }" dictTypeId="RECEIVE_ORDER_STATUS_COLOR"></dict:write>
                </vlh:column>
                <vlh:column property="create_time" attributes="width='150px'" format="yyyy-MM-dd HH:mm:ss" />
                <vlh:column property="last_update_time" attributes="width='150px'" format="yyyy-MM-dd HH:mm:ss" />
              </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
    <s:if test="#attr['receiveOrderQuery'].list.size()==0">
      <p class="pd-10">没有查找到订单信息</p>
    </s:if>
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
