<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>服务商信息</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 900px; padding:0.2em;">
  
  
  <div style="padding: 10px;">
    <!-- 基本信息 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">基本信息</span>
      </div>
      <div class="content detail">

        <div class="col w-p-50">
          <div class="row">
            <span class="label">服务商编号:</span>
            <span class="value">${agent.agentNo}</span>
          </div>
          <div class="row">
            <span class="label">服务商类型:</span>
            <span class="value"><dict:write dictId="${agent.agentType }" dictTypeId="AGENT_TYPE"></dict:write></span>
          </div>
          <div class="row">
            <span class="label">服务商联系人:</span>
            <span class="value">${agent.linkMan}</span>
          </div>
          <div class="row">
            <span class="label">服务商全称:</span>
            <span class="value">${agent.fullName}</span>
          </div>
          <div class="row">
            <span class="label">服务商联系电话:</span>
            <span class="value">${agent.phoneNo}</span>
          </div>
          <div class="row">
            <span class="label">可用余额:</span>
            <span class="value"><fmt:formatNumber currencySymbol="" type="currency" value="${balance}" /></span>
          </div>
          <div class="row">
            <span class="label">保证金:</span>
            <span class="value"><fmt:formatNumber currencySymbol="" type="currency" value="${agent.cautionMoney}" /></span>
          </div>
          <div class="row">
            <span class="label">入账周期:</span>
            <span class="value">
            	<c:if test="${cycle == 0}">T+0</c:if>
            	<c:if test="${cycle == 1}">T+1</c:if>
            </span>
          </div>
        </div>
        <div class="col w-p-50">
          <div class="row">
            <span class="label">服务商简称:</span>
            <span class="value">${agent.shortName}</span>
          </div>
          <div class="row">
            <span class="label">服务商等级:</span>
            <span class="value"><dict:write dictId="${agent.agentLevel }" dictTypeId="SERVICE_PROVIDER_LEVEL"></dict:write></span>
          </div>
          <div class="row">
            <span class="label">所属服务商:</span>
            <span class="value">${agent.parenId}</span>
          </div>
          <div class="row">
            <span class="label">所属省:</span>
            <span class="value">${agent.province}</span>
          </div>
          <div class="row">
            <span class="label">所属市:</span>
            <span class="value">${agent.city}市</span>
          </div>
          <div class="row">
            <span class="label">服务商地址:</span>
            <span class="value">${agent.addr}</span>
          </div>
          <div class="row">
            <span class="label">邮箱:</span>
            <span class="value">${agent.EMail}</span>
          </div>
          <div class="row">
            <span class="label">身份证:</span>
            <span class="value">${agent.idCard}</span>
          </div>
        </div>
      </div>
    </div>
	 <c:if test="${agent.agentType != 'INDIVIDUAL'}">
      <!-- 企业信息 -->
      <div class="row companyInfo">
        <div class="title-h1 fix tabSwitch2">
          <span class="primary fl">企业信息</span>
        </div>
        <div class="content detail">
          <div class="fix">
            <div class="col w-p-50">
              <div class="row">
                <span class="label">法人姓名:</span>
                <span class="value">${agentCert.legalPerson}</span>
              </div>
              <div class="row">
                <span class="label">企业网址:</span>
                <span class="value">${agentCert.enterpriseUrl}</span>
              </div>
              <div class="row">
                <span class="label">客服电话:</span>
                <span class="value">${agentCert.consumerPhone}</span>
              </div>
              <div class="row">
                <span class="label">ICP备案号:</span>
                <span class="value">${agentCert.icp}</span>
              </div>
            </div>
            <div class="col w-p-50">
              <div class="row">
                <span class="label">经营范围:</span>
                <span class="value">${agentCert.businessScope}</span>
              </div>
              <div class="row">
                <span class="label">企业信用码:</span>
                <span class="value">${agentCert.enterpriseCode}</span>
              </div>
              <div class="row">
                <span class="label">经营地址:</span>
                <span class="value">${agentCert.businessAddress}</span>
              </div>
              <div class="row">
                <span class="label">营业期限:</span>
                <span class="value"><fmt:formatDate value="${agentCert.validStartTime}" pattern="yyyy-MM-dd" /> ~ <fmt:formatDate value="${agentCert.validEndTime}" pattern="yyyy-MM-dd" /></span>
              </div>
            </div>
          </div>
  
          <div class="fix">
            <div class="item">
              <div class="input-area">
                <span class="label w-90">补充材料:</span>
                <div class="input-wrap">
                  <a class="btn" href="javascript:void(0);" onclick="lookImg('${pageContext.request.contextPath}/findDocumentImg.action?fileName=FILE_ATT&agentCert.agentNo=${agent.agentNo}','补充材料', this);" >查看</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </c:if>

    <!-- 费率信息 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">费率信息</span>
      </div>
      <div class="content">

        <div id="productWrap">
          <c:forEach items="${agentFees }" var="agentFee" varStatus="tempIdx">
            <div class="productItem" data-productType="${agentFee.productType.name() }">
              <div class="title-h2 fix">
                <span class="primary fl"><c:if test="${agentFee.productType.name() == 'HOLIDAY_REMIT'}">假日付</c:if><c:if test="${agentFee.productType.name() != 'HOLIDAY_REMIT'}"><dict:write dictId="${agentFee.productType.name() }" dictTypeId="BF_PRODUCT_TYPE"></dict:write></c:if></span>
                <a class="delProduct fr" href="javascript:void(0);">删除</a>
              </div>
              <div class="fix">
                <div class="col fl w-p-50">
                  <div class="fix">
                    <div class="item">
                      <div class="input-area">
                        <span class="label w-90">费率类型:</span>
                        <div class="input-wrap">
                          <dict:select value="${agentFee.feeType }" name='agentFees[${tempIdx.index }].feeType fee-type' styleClass="input-select rate-select" dictTypeId="FEE_TYPE" disabled="disabled"></dict:select>
                        </div>
                      </div>
                    </div>
                    <div class="item">
                      <div class="input-area">
                        <span class="label w-90">最低费率:</span>
                        <div class="input-wrap">
                          <input type="text" class="input-text minfee" value="${agentFee.minFee }">
                        </div>
                      </div>
                    </div>
                    <div class="item">
                      <div class="input-area">
                        <span class="label w-90">费率类型:</span>
                        <div class="input-wrap">
                          <dict:select disabled="disabled" name='agentFees[${tempIdx.index }].profitType' styleClass="input-select profit-type" value="${agentFee.profitType }" dictTypeId="PROFIT_TYPE"></dict:select>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col fl w-p-50">
                  <div class="fix">
                    <div class="item">
                      <div class="input-area">
                        <span class="label w-90">费率:</span>
                        <div class="input-wrap">
                          <input type="text" class="input-text fee" value="${agentFee.fee}" >
                        </div>
                      </div>
                    </div>
                    <div class="item">
                      <div class="input-area">
                        <span class="label w-90">最高费率:</span>
                        <div class="input-wrap">
                          <input type="text" class="input-text maxfee" maxfee value="${agentFee.maxFee }">
                        </div>
                      </div>
                    </div>
                    <div class="item">
                      <div class="input-area">
                        <span class="label w-90">分润比例:</span>
                        <div class="input-wrap">
                          <input type="text" class="input-text profit-ratio" value="${agentFee.profitRatio }">
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </c:forEach>
        </div>
      </div>
    </div>




    <!-- 结算卡信息 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">服务商结算卡信息</span>
      </div>
      <div class="content">
        <ul class="list">
          <li>
            <span class="info-title">账户名称:</span>
            <span class="info-value">${agentSettle.accountName }</span>
          </li>
          <%-- <li>
            <span class="info-title">银行编号:</span>
            <span class="info-value">${agentSettle.accountNo}</span>
          </li> --%>
          <li>
            <span class="info-title">开户行名称:</span>
            <span class="info-value">${agentSettle.openBankName}</span>
          </li>
          <li>
            <span class="info-title">账户类型:</span>
            <span class="info-value"><dict:write dictId="${agentSettle.agentType }" dictTypeId="AGENT_TYPE"></dict:write></span>
          </li>
          <li>
            <span class="info-title">银行卡号:</span>
            <span class="info-value">${agentSettle.accountNo}</span>
          </li>
        </ul>
      </div>
    </div>

    <!-- 证件信息 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">证件信息</span>
      </div>
      <div class="content">
        <div class="fix">

          <ul class="crt-list">
            <li><a onclick="lookImg('${pageContext.request.contextPath}/findDocumentImg.action?fileName=FILE_BUSILICECERT&agentCert.agentNo=${agent.agentNo}', '企业营业执照 | 个人身份证正面', this);" href="javascript:void(0);"><span class="pre-text">企业营业执照</span> | <span class="pre-text">个人身份证正面</span></a></li>
            <li><a onclick="lookImg('${pageContext.request.contextPath}/findDocumentImg.action?fileName=FILE_TAXREGCERT&agentCert.agentNo=${agent.agentNo}', '企业税务登记证 | 个人身份证反面', this);" href="javascript:void(0);"><span class="pre-text">企业税务登记证</span> | <span class="pre-text">个人身份证反面</span></a></li>
            <li><a onclick="lookImg('${pageContext.request.contextPath}/findDocumentImg.action?fileName=FILE_ORGANIZATIONCERT&agentCert.agentNo=${agent.agentNo}', '组织机构证| 个人银行卡正面', this);" href="javascript:void(0);"><span class="pre-text">组织机构证</span> | <span class="pre-text">个人银行卡正面</span></a></li>
            <li><a onclick="lookImg('${pageContext.request.contextPath}/findDocumentImg.action?fileName=FILE_OPENBANKACCCERT&agentCert.agentNo=${agent.agentNo}', '银行开户许可证 | 个人银行卡反面', this);" href="javascript:void(0);"><span class="pre-text">银行开户许可证</span> | <span class="pre-text">个人银行卡反面</span></a></li>
            <li><a onclick="lookImg('${pageContext.request.contextPath}/findDocumentImg.action?fileName=FILE_IDCARD&agentCert.agentNo=${agent.agentNo}', '企业法人身份证 | 个人手持身份证', this);" href="javascript:void(0);"><span class="pre-text">企业法人身份证</span> | <span class="pre-text">个人手持身份证</span></a></li>
          </ul>

        </div>
      </div>
    </div>






  </div>
  
  
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    $(document).ready(function() {
      // 更改费率类型
      $('.fee-type').change(function(event, ignore) {
        general.changeFeeType(this.value, $(this).closest('.productItem'));
      }).trigger('change', true);
      // 禁用产品
      product.disabledProduct();
    });
  </script>
</body>
</html>
