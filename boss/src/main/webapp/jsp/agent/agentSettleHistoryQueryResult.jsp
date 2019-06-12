<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>操作历史</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 1200px;padding: 10px;">
  
  <div class="title-h1 fix noBottom">
    <span class="primary fl">服务商结算历史操作信息</span>
  </div>
  <c:if test="${listAgentSettleHistory != null && listAgentSettleHistory.size() > 0}">
    <div class="table-warp">
      <div class="table-sroll">
        <table class="data-table shadow--2dp  w-p-100 tow-color">
          <thead>
            <tr>
              <th>操作人</th>
              <th>服务商编号</th>
              <th>账户名称</th>
              <th>账户编号</th>
              <th>账户类型</th>
              <th>银行编号</th>
              <th>开户行名称</th>
              <th>创建时间</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${listAgentSettleHistory }" var="agentHistory">
              <tr>
                <td>${agentHistory.oper }</td>
                <td>${agentHistory.agentNo }</td>
                <td>${agentHistory.accountName }</td>
                <td>${agentHistory.accountNo }</td>
                <td><dict:write dictId="${agentHistory.agentType }" dictTypeId="ACCOUNT_TYPE"></dict:write></td>
                <td>${agentHistory.bankCode }</td>
                <td>${agentHistory.openBankName }</td>
                <td>${agentHistory.createTime }</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    <div class="jump-rarp mt-20 text-center" id="PageWrapCustomize" total_count="${page.totalResult}" total="${page.totalPage}"  current="${page.currentPage}"></div>
  </c:if>
  <c:if test="${listAgentSettleHistory eq null or listAgentSettleHistory.size() == 0}">
    <p class="pd-10">无符合条件的记录</p>
  </c:if>
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
   // 分页
   $('#PageWrapCustomize').pageCustom('agentSettleHistoryQueryResult.action');
  </script>
</body>
</html>
