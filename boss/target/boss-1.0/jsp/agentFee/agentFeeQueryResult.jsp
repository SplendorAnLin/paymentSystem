<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>服务商费率-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <s:if test="#attr['agentFeeInfo'].list.size()>0">
    <vlh:root value="agentFeeInfo" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>服务商编号</th>
                <th>状态</th>
                <td>业务类型</td>
                <th>费率类型</th>
                <th>费率</th>
                <th>最低费率</th>
                <th>最高费率</th>
                <th>分润比例</th>
                <th>分润类型</th>
                <th>创建时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
            <vlh:row bean="agentFeeInfo">
              <s:set name="agentFee" value="#attr['agentFeeInfo']" />
              <vlh:column property="agent_no" />
              <vlh:column><dict:write dictId="${agentFee.status }" dictTypeId="STATUS_COLOR"></dict:write></vlh:column>
              <vlh:column><dict:write dictId="${agentFee.product_type }" dictTypeId="BF_SHARE_PAYTYPE"></dict:write></vlh:column>
              <vlh:column><dict:write dictId="${agentFee.fee_type }" dictTypeId="FEE_TYPE"></dict:write></vlh:column>
              <vlh:column>${agentFee.fee}</vlh:column>
              <vlh:column>${agentFee.min_fee}</vlh:column>
              <vlh:column>${agentFee.max_fee}</vlh:column>
              <vlh:column>${agentFee.profit_ratio}</vlh:column>
              <vlh:column><dict:write dictId="${agentFee.profit_type }" dictTypeId="PROFIT_TYPE"></dict:write></vlh:column>
              <vlh:column>${agentFee.create_time}</vlh:column>
              <vlh:column attributes="width='10%'">
                <button data-myiframe='{
                  "target": "${pageContext.request.contextPath}/toUpdateagentFeeAction.action?agentFee.id=${agentFee.id}",
                  "btns": [
                    {"lable": "取消"},
                    {"lable": "修改", "trigger": ".btn-submit"}
                  ]
                }' class="btn-small">修改</button>
              </vlh:column>
            </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:if test="#attr['agentFeeInfo'].list.size()==0">
    <p class="pd-10">无符合条件的查询结果</p>
  </s:if>
  
  
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
