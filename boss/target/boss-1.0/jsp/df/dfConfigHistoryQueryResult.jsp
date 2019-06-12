<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>付款配置-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>

  <s:if test="#attr['dpayConfigHistory'].list.size()>0">
    <vlh:root value="dpayConfigHistory" url="?" includeParameters="*" configName="defaultlook">
    
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>出款方式</th>
                <th>重复付款</th>
                <th>付款起止时间</th>
                <th>审核金额</th>
                <th>支持最大金额</th>
                <th>支持最小金额</th>
                <th>假日付</th>
                <th>出款状态</th>
                <th>配置时间</th>
                <th>操作人</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="dpayConfigHistory">
                <s:set name="dpayConfig" value="#attr['dpayConfigHistory']" />
                <vlh:column><dict:write dictId="${dpayConfig.remit_type }" dictTypeId="PAYMENT"></dict:write></vlh:column>
                <vlh:column><dict:write dictId="${dpayConfig.reremit_flag }" dictTypeId="STATUS_COLOR"></dict:write></vlh:column>
                <vlh:column>${dpayConfig.start_time}-${dpayConfig.end_time}</vlh:column>
                <vlh:column property="audit_amount"></vlh:column>
                <vlh:column property="max_amount"></vlh:column>
                <vlh:column property="min_amount"></vlh:column>
                <vlh:column><dict:write dictId="${dpayConfig.holiday_status }" dictTypeId="STATUS_COLOR"></dict:write></vlh:column>
                <vlh:column><dict:write dictId="${dpayConfig.status }" dictTypeId="STATUS_COLOR"></dict:write></vlh:column>
                <vlh:column><s:date name="#dpayConfig.create_date" format="yyyy-MM-dd HH:mm:ss" /></vlh:column>
                <vlh:column property="oper"></vlh:column>
              </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
  
      
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:if test="#attr['dpayConfigHistory'].list.size()==0">
    无符合条件的查询结果
  </s:if>
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
