<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>服务商结算信息-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <s:if test="#attr['agentSettlementQuery'].list.size()>0">
    <vlh:root value="agentSettlementQuery" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>服务商编号</th>
                <th>账户名称</th>
                <th>账户编号</th>
                <th>账户类型</th>
                <th>银行编号</th>
                <th>开户行名称</th>
                <th>创建时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="agentSettlementQuery">
                <s:set name="entity" value="#attr['agentSettlementQuery']" />
                <vlh:column property="agent_no" />
                <vlh:column property="account_name" />
                <vlh:column property="account_no" />
                <vlh:column property="agent_type"><dict:write dictId="${entity.agent_type }" dictTypeId="ACCOUNT_TYPE"></dict:write></vlh:column>
                <vlh:column property="bank_code" />
                <dict:write dictId="${bank_code }" dictTypeId="BANK_CODE"></dict:write>
                <vlh:column property="open_bank_name" />
                <vlh:column property="create_time"><s:date name="#entity.create_time" format="yyyy-MM-dd HH:mm:ss" /></vlh:column>
                <vlh:column>
                  <button data-myiframe='{
                    "target": "findAgentSettleByAgentNo.action?agentNo=${entity.agent_no}",
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "修改", "trigger": ".btn-submit"}
                    ]
                  }' class="btn-small">修改</button>
                  <button data-dialog="agentSettleHistoryQueryResult.action?agentNo=${entity.agent_no}" class="btn-small">操作历史</button>
                </vlh:column>
              </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:if test="#attr['reconBillConfigInfo'].list.size()==0">
    <p class="pd-10">暂无服务商结算信息</p>
  </s:if>
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
