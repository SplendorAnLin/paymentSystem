<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>账户信息</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>
  
  <div style="padding: 10px;">
    <!-- 服务商基本信息 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">服务商基本信息</span>
      </div>
      <div class="content detail mt-20" style="width: 900px;line-height: 38px;">
        <div class="col w-p-30">
          <div class="row">
            <span class="label">手机号:</span>
            <span class="value">${agent.phoneNo }</span>
          </div>
          <div class="row">
            <span class="label">服务商全称:</span>
            <span class="value">${agent.fullName }</span>
          </div>
          <div class="row">
            <span class="label">服务商简称:</span>
            <span class="value">${agent.shortName }</span>
          </div>
          <div class="row">
            <span class="label">服务商编号:</span>
            <span class="value">${agent.agentNo }</span>
          </div>
          <div class="row">
            <span class="label">账户创建时间:</span>
            <span class="value"><fmt:formatDate value="${agent.createTime }" pattern="yyyy-MM-dd" /></span>
          </div>
          <div class="row">
            <span class="label">上级服务商编号:</span>
            <span class="value">${agent.parenId }</span>
          </div>
        </div>
        <div class="col w-p-70">
          <div class="row">
            <span class="label">等级:</span>
            <span class="value"><dict:write dictId="${agent.agentLevel }" dictTypeId="SERVICE_PROVIDER_LEVEL"></dict:write></span>
          </div>
          <div class="row">
            <span class="label">地址:</span>
            <span class="value">${agent.addr }</span>
          </div>
          <div class="row">
            <span class="label">开户行:</span>
            <span class="value">${agentSettle.openBankName }</span>
          </div>
          <div class="row">
            <span class="label">开户名:</span>
            <span class="value">${agentSettle.accountName }</span>
          </div>
          <div class="row">
            <span class="label">银行账号:</span>
            <span class="value">${agentSettle.accountNo }</span>
          </div>
          <div class="row">
            <span class="label">服务商类型:</span>
            <span class="value"><dict:write dictId="${agent.agentType }" dictTypeId="AGENT_TYPE"></dict:write></span>
          </div>
        </div>


      </div>
    </div>

    <!-- 费率信息 -->
    <div class="row mt-20">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">费率信息</span>
      </div>
      <div class="content detail">
      
        <c:if test="${agentFees != null && agentFees.size() > 0 }">
          <div class="table-warp">
            <div class="table-sroll">
              <table class="data-table shadow--2dp  w-p-100 tow-color">
                <thead>
                  <tr>
                    <th>状态</th>
                    <th>产品类型</th>
                    <th>费率类型</th>
                    <th>费率</th>
                    <th>最高费率</th>
                    <th>最低费率</th>
                    <th>分润类型</th>
                    <th>分润比例</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${agentFees}" var="fees" varStatus="i">
                    <tr>
                      <td><dict:write dictId="${fees.status.name() }" dictTypeId="SERVICE_PROVIDER_STATUS_COLOR"></dict:write></td>
                      <td><dict:write dictId="${fees.productType.name() }" dictTypeId="BF_SHARE_PAYTYPE"></dict:write></td>
                      <td><dict:write dictId="${fees.feeType.name() }" dictTypeId="FEE_TYPE"></dict:write></td>
                      <td>${fees.fee}</td>
                      <td>${fees.maxFee }</td>
                      <td>${fees.minFee }</td>
                      <td><dict:write dictId="${fees.profitType.name() }" dictTypeId="PROFIT_TYPE"></dict:write></td>
                      <td>${fees.profitRatio }</td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
        </c:if>
    
      </div>
    </div>




  </div>
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
