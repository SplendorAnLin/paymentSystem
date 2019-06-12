<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>代收配置修改历史</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 1200px;padding: 10px;">
  
  <div class="title-h1 fix noBottom">
    <span class="primary fl">代收配置修改历史</span>
  </div>
  <s:if test="#attr['receiveConfigHistoryQuery'].list.size()>0">
    <vlh:root value="receiveConfigHistoryQuery" url="?" includeParameters="*" configName="defaultlook">
    <div class="table-warp">
      <div class="table-sroll">
        <table class="data-table shadow--2dp  w-p-100 tow-color">
          <thead>
            <tr>
              <th>用户编号</th>
              <th>状态</th>
              <th>日限额</th>
              <th>单日最大笔数</th>
              <th>是否校验授权</th>
              <th>IP</th>
              <th>域名</th>
              <th>修改时间</th>
              <th>操作人</th>
            </tr>
          </thead>
          <tbody>
            <vlh:row bean="defConfig">
              <s:set name="entity" value="#attr['defConfig']" />
                <vlh:column property="owner_id" attributes="width='10%'" />
                <vlh:column property="status" attributes="width='8%'"><dict:write dictId="${entity.status }" dictTypeId="HOLIDAY_STATUS_COLOR"></dict:write></vlh:column>
                <vlh:column property="daily_max_amount" attributes="width='13%'" ><fmt:formatNumber value="${entity.daily_max_amount }" pattern="#0.00#" /></vlh:column>
                <vlh:column property="single_batch_max_num" attributes="width='10%'"/>
                <vlh:column property="is_check_contract" attributes="width='15%'" ><dict:write dictId="${entity.is_check_contract }" dictTypeId="YN_COLOR"></dict:write></vlh:column>
                <vlh:column property="cust_ip" attributes="width='15%'" />
                <vlh:column property="domain" attributes="width='15%'" />
                <vlh:column property="create_time" attributes="width='15%'"><s:date name="#entity.create_time" format="yyyy-MM-dd HH:mm:ss" /></vlh:column>
                <vlh:column property="oper" attributes="width='8%'"/>
              </vlh:row>
          </tbody>
        </table>
      </div>
    </div>
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:if test="#attr['receiveConfigHistoryQuery'].list.size()==0">
    <p class="pd-10">此用户还没有代收配置操作历史</p>
  </s:if>
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
