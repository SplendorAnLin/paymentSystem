<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>修改服务商信息</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 900px; padding:10px">
  
  <form class="validator  notification" id="agentAdd" method="post" enctype="multipart/form-data" action="updateAgent.action" prompt="DropdownMessage"data-success="修改成功" data-fail="修改失败"  style="padding: 0.4em;">
    <input type="hidden" id="bankCode" readonly="readonly" name="agentSettle.bankCode" value="${agentSettle.bankCode }">
    <input type="hidden" class="organization" name="agent.organization" />

    <!-- 基本信息 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">基本信息</span>
      </div>
      <div class="content">
        <div class="fix">
          <div class="col fl w-p-50">
            <div class="fix">
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">服务商编号:</span>
                  <div class="input-wrap">
                    <span class="text-secondary">${agent.agentNo}</span>
                    <input type="hidden" class="input-text" name="agent.agentNo" value="${agent.agentNo}" readonly>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">服务商状态:</span>
                  <div class="input-wrap">
                    <span class="text-secondary"><dict:write dictId="${agent.status }" dictTypeId="SERVICE_PROVIDER_STATUS_COLOR"></dict:write></span>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">服务商全称:</span>
                  <div class="input-wrap">
                    <span class="text-secondary">${agent.fullName}</span>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">所属服务商:</span>
                  <div class="input-wrap">
                    <span class="text-secondary">${agent.parenId}</span>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">服务商等级:</span>
                  <div class="input-wrap">
                    <span class="text-secondary"><dict:write dictId="${agent.agentLevel }" dictTypeId="SERVICE_PROVIDER_LEVEL"></dict:write></span>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">服务商联系电话:</span>
                  <div class="input-wrap">
                    <span class="text-secondary">${agent.phoneNo}</span>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">服务商类型:</span>
                  <div class="input-wrap">
                    <dict:select value="${agent.agentType }" styleClass="input-select accountType" shield="OEM"  name="agent.agentType" dictTypeId="AGENT_TYPE"></dict:select>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90 cardText">身份证号:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="agent.idCard" value="${agent.idCard }" required idCard>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col fl  w-p-50">
            <div class="fix">
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">服务商简称:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="agent.shortName" value="${agent.shortName}" required rangelength="[2,16]" checkAgentSortName trigger='{"checkAgentSortName": 2}'>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">服务商联系人:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="agent.linkMan" value="${agent.linkMan}" required rangelength="[2,16]">
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">所在省:</span>
                  <div class="input-wrap">
                    <select class="input-select prov" name="agent.province" required>
                    </select>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">所在市:</span>
                  <div class="input-wrap">
                    <select class="input-select city" name="agent.city"  required>
                    </select>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">服务商地址:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="agent.addr" value="${agent.addr }" required rangelength="[1,40]">
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">邮箱:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="agent.eMail" value="${agent.EMail}" requiredNot email>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>

  <!-- 企业信息 -->
    <div class="row companyInfo" style="display:none;">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">企业信息</span>
      </div>
    <div class="content">
      <div class="fix">
        <div class="col fl">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">法人姓名:</span>
              <div class="input-wrap">
                <input type="text" class="input-text open-cate" name="agentCert.legalPerson" value="${agentCert.legalPerson}" disabled required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">企业网址:</span>
              <div class="input-wrap">
                <input type="text" class="input-text open-cate" name="agentCert.enterpriseUrl" value="${agentCert.enterpriseUrl}" disabled required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">客服电话:</span>
              <div class="input-wrap">
                <input type="text" class="input-text open-cate" name="agentCert.consumerPhone" value="${agentCert.consumerPhone}" disabled required telPhone>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">ICP备案号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text open-cate" name="agentCert.icp" value="${agentCert.icp}" disabled required>
              </div>
            </div>
          </div>
        </div>
        <div class="col fl">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">经营范围:</span>
              <div class="input-wrap">
                <input type="text" class="input-text open-cate" name="agentCert.businessScope" value="${agentCert.businessScope}" disabled required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">企业信用码:</span>
              <div class="input-wrap">
                <input type="text" class="input-text open-cate" name="agentCert.enterpriseCode" value="${agentCert.enterpriseCode}" disabled required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">经营地址:</span>
              <div class="input-wrap">
                <input type="text" class="input-text open-cate" name="agentCert.businessAddress" value="${agentCert.businessAddress}" disabled required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">营业期限:</span>
              <div class="input-wrap">
                <input type="text" name="agentCert.validStartTime" class="input-text double-input date-start" value="<fmt:formatDate value="${agentCert.validStartTime}" pattern="yyyy-MM-dd" />" disabled required>
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="agentCert.validEndTime" class="input-text double-input date-end" value="<fmt:formatDate value="${agentCert.validEndTime}" pattern="yyyy-MM-dd" />" disabled required>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="fix">
        <!-- noWidth="true" -->
          <div class="item">
            <div class="input-area">
              <span class="label w-90">补充材料:</span>
              <div class="input-wrap">
                <a class="btn" href="javascript:void(0);" noWidth="true" onclick="spliceImgPath('FILE_ATT','补充材料', this);" >查看原始图片</a>
                <a class="btn btn-tr-file" noWidth="true" href="javascript:void(0);"> <i style="color: #ff9900;" class="fa hidden fa-exclamation-circle"></i> 修改</a>
                <input name="attachment" class="btn-file-input hidden" type="file" prompt="message" requiredNot checkImageSize accept="image/jpeg,image/jpg,image/png">
              </div>
            </div>
          </div>
      </div>
    </div>
    </div>

    <!-- 费率信息 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">费率信息</span>
      </div>
      <div class="content">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">选择待开通业务:</span>
              <div class="input-wrap">
                <dict:select multiple="true" id="productTypeSelect" name="multiple" styleClass="input-select" dictTypeId="PRODUCT_TYPE"></dict:select>
              </div>
            </div>
          </div>
        </div>
        <div id="productWrap">
          <!-- 个人网银 -->
          <div class="productItem" data-productType="B2C">
            <input type="hidden" class="ignoreEmpy" name="agentFees[0].productType" value="B2C">
            <input type="hidden" class="ignoreEmpy agentNo" name="agentFees[0].agentNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="agentFees[0].status">
            <input type="hidden" class="ignoreEmpy fee-id" name="agentFees[0].id" />
            <div class="title-h2 fix">
              <span class="primary fl">个人网银</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select fee-type" dictTypeId="FEE_TYPE" name="agentFees[0].feeType"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="agentFees[0].minFee" required amount minfee maxIgnoreEmpy="[name='agentFees[0].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select profit-type" name="agentFees[0].profitType" dictTypeId="PROFIT_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text fee" name="agentFees[0].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="agentFees[0].maxFee" required amount maxfee minIgnoreEmpy="[name='agentFees[0].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润比例:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text profit-ratio" name="agentFees[0].profitRatio" required amount profitratio>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 企业网银 -->
          <div class="productItem" data-productType="B2B">
            <input type="hidden" class="ignoreEmpy" name="agentFees[1].productType" value="B2B">
            <input type="hidden" class="ignoreEmpy agentNo" name="agentFees[1].agentNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="agentFees[1].status">
            <input type="hidden" class="ignoreEmpy fee-id" name="agentFees[1].id" />
            
            <div class="title-h2 fix">
              <span class="primary fl">企业网银</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select fee-type" dictTypeId="FEE_TYPE" name="agentFees[1].feeType"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="agentFees[1].minFee" required amount minfee maxIgnoreEmpy="[name='agentFees[1].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select profit-type" name="agentFees[1].profitType" dictTypeId="PROFIT_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text fee" name="agentFees[1].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="agentFees[1].maxFee" required amount maxfee minIgnoreEmpy="[name='agentFees[1].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润比例:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text profit-ratio" name="agentFees[1].profitRatio" required amount profitratio>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 认证支付 -->
          <div class="productItem" data-productType="AUTHPAY">
            <input type="hidden" class="ignoreEmpy" name="agentFees[2].productType" value="AUTHPAY">
            <input type="hidden" class="ignoreEmpy agentNo" name="agentFees[2].agentNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="agentFees[2].status">
            <input type="hidden" class="ignoreEmpy fee-id" name="agentFees[2].id" />
            <div class="title-h2 fix">
              <span class="primary fl">认证支付</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select fee-type" dictTypeId="FEE_TYPE" name="agentFees[2].feeType"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="agentFees[2].minFee" required amount minfee maxIgnoreEmpy="[name='agentFees[2].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select profit-type" name="agentFees[2].profitType" dictTypeId="PROFIT_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text fee" name="agentFees[2].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="agentFees[2].maxFee" required amount maxfee minIgnoreEmpy="[name='agentFees[2].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润比例:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text profit-ratio" name="agentFees[2].profitRatio" required amount profitratio>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 微信公众号支付 -->
          <div class="productItem" data-productType="WXJSAPI">
            <input type="hidden" class="ignoreEmpy" name="agentFees[3].productType" value="WXJSAPI">
            <input type="hidden" class="ignoreEmpy agentNo" name="agentFees[3].agentNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="agentFees[3].status">
            <input type="hidden" class="ignoreEmpy fee-id" name="agentFees[3].id" />
            <div class="title-h2 fix">
              <span class="primary fl">微信公众号支付</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select fee-type" dictTypeId="FEE_TYPE" name="agentFees[3].feeType"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="agentFees[3].minFee" required amount minfee maxIgnoreEmpy="[name='agentFees[3].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select profit-type" name="agentFees[3].profitType" dictTypeId="PROFIT_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text fee" name="agentFees[3].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="agentFees[3].maxFee" required amount maxfee minIgnoreEmpy="[name='agentFees[3].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润比例:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text profit-ratio" name="agentFees[3].profitRatio" required amount profitratio>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 微信二维码支付 -->
          <div class="productItem" data-productType="WXNATIVE">
            <input type="hidden" class="ignoreEmpy" name="agentFees[4].productType" value="WXNATIVE">
            <input type="hidden" class="ignoreEmpy agentNo" name="agentFees[4].agentNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="agentFees[4].status">
            <input type="hidden" class="ignoreEmpy fee-id" name="agentFees[4].id" />
            <div class="title-h2 fix">
              <span class="primary fl">微信二维码支付</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select fee-type" dictTypeId="FEE_TYPE" name="agentFees[4].feeType"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="agentFees[4].minFee" required amount minfee maxIgnoreEmpy="[name='agentFees[4].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select profit-type" name="agentFees[4].profitType" dictTypeId="PROFIT_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text fee" name="agentFees[4].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="agentFees[4].maxFee" required amount maxfee minIgnoreEmpy="[name='agentFees[4].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润比例:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text profit-ratio" name="agentFees[4].profitRatio" required amount profitratio>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 支付宝 -->
          <div class="productItem" data-productType="ALIPAY">
            <input type="hidden" class="ignoreEmpy" name="agentFees[5].productType" value="ALIPAY">
            <input type="hidden" class="ignoreEmpy agentNo" name="agentFees[5].agentNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="agentFees[5].status">
            <input type="hidden" class="ignoreEmpy fee-id" name="agentFees[5].id" />
            <div class="title-h2 fix">
              <span class="primary fl">支付宝</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select fee-type" dictTypeId="FEE_TYPE" name="agentFees[5].feeType"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="agentFees[5].minFee" required amount minfee maxIgnoreEmpy="[name='agentFees[5].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select profit-type" name="agentFees[5].profitType" dictTypeId="PROFIT_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text fee" name="agentFees[5].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="agentFees[5].maxFee" required amount maxfee minIgnoreEmpy="[name='agentFees[5].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润比例:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text profit-ratio" name="agentFees[5].profitRatio" required amount profitratio>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 代付 -->
          <div class="productItem" data-productType="REMIT">
            <input type="hidden" class="ignoreEmpy" name="agentFees[6].productType" value="REMIT">
            <input type="hidden" class="ignoreEmpy" name="serviceConfigBean.ownerId">
            <input type="hidden" class="ignoreEmpy agentNo" name="agentFees[6].agentNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="agentFees[6].status">
            <input type="hidden" class="ignoreEmpy fee-id" name="agentFees[6].id" />
            <div class="title-h2 fix">
              <span class="primary fl">代付</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select fee-type" dictTypeId="FEE_TYPE" name="agentFees[6].feeType"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="agentFees[6].minFee" required amount maxIgnoreEmpy="[name='agentFees[6].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">审核:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select" name="serviceConfigBean.manualAudit" dictTypeId="YN"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">IP:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text" name="serviceConfigBean.custIp">
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">单笔最大金额:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text" name="serviceConfigBean.maxAmount" required amount  minIgnoreEmpy="[name='serviceConfigBean.minAmount']" message='{"minIgnoreEmpy": "低于单笔最小金额"}' >
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">单日最大金额:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text" name="serviceConfigBean.dayMaxAmount" required amount minIgnoreEmpy="[name='serviceConfigBean.maxAmount']" message='{"minIgnoreEmpy": "低于单笔最大金额"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">自动结算:</span>
                      <div class="input-wrap">
                        <select class="input-select" name="serviceConfigBean.fireType" required>
                          <option value="AUTO">自动</option>
                          <option value="MAN">人工</option>
                        </select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select profit-type" name="agentFees[6].profitType" dictTypeId="PROFIT_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">是否开通假日付:</span>
                      <div class="input-wrap">
                        <select class="input-select holidayStatus" name="serviceConfigBean.holidayStatus" required>
                          <option value="TRUE">是</option>
                          <option value="FALSE">否</option>
                        </select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text fee" name="agentFees[6].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="agentFees[6].maxFee" required amount minIgnoreEmpy="[name='agentFees[6].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">短信验证:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select" name="serviceConfigBean.usePhoneCheck" dictTypeId="YN"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">域名:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text" name="serviceConfigBean.domain">
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">单笔最小金额:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text" name="serviceConfigBean.minAmount" required amount  maxIgnoreEmpy="[name='serviceConfigBean.maxAmount']" message='{"maxIgnoreEmpy": "超过单笔最大金额"}' >
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">付款起止时间:</span>
                      <div class="input-wrap">
                        <input type="text" name="serviceConfigBean.startTime" class="date-start w-78" date-fmt="H:mm" required>
                      </div>
                      <span class="cut"> - </span>
                      <div class="input-wrap">
                        <input type="text" name="serviceConfigBean.endTime" class="date-end w-78" date-fmt="H:mm" required>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">运营审核:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select" name="serviceConfigBean.bossAudit" dictTypeId="DF_AUDIT"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润比例:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text profit-ratio" name="agentFees[6].profitRatio" required amount profitratio>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 假日付 -->
          <div class="productItem" data-productType="HOLIDAY_REMIT">
            <input type="hidden" class="ignoreEmpy" name="agentFees[7].productType" value="HOLIDAY_REMIT">
            <input type="hidden" class="ignoreEmpy fee-id" name="agentFees[7].id" />
            <div class="title-h2 fix">
              <span class="primary fl">假日付</span>
            </div>
            <div class="fix">
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select fee-type" dictTypeId="FEE_TYPE" name="agentFees[7].feeType"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="agentFees[7].minFee" required amount minfee maxIgnoreEmpy="[name='agentFees[7].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select profit-type" name="agentFees[7].profitType" dictTypeId="PROFIT_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text fee" name="agentFees[7].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="agentFees[7].maxFee" required amount maxfee minIgnoreEmpy="[name='agentFees[7].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润比例:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text profit-ratio" name="agentFees[7].profitRatio" required amount profitratio>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 代收 -->
          <div class="productItem" data-productType="RECEIVE">
            <input type="hidden" class="ignoreEmpy" name="agentFees[8].productType" value="RECEIVE">
            <input type="hidden" class="ignoreEmpy" name="receiveConfigInfoBean.ownerId" >
            <input type="hidden" class="ignoreEmpy agentNo" name="agentFees[8].agentNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="agentFees[8].status">
            <input type="hidden" class="ignoreEmpy fee-id" name="agentFees[8].id" />
            <div class="title-h2 fix">
              <span class="primary fl">代收</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select fee-type" dictTypeId="FEE_TYPE" name="agentFees[8].feeType"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="agentFees[8].minFee" required amount maxIgnoreEmpy="[name='agentFees[8].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">IP:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text" name="receiveConfigInfoBean.custIp">
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">单笔最大金额:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text" name="receiveConfigInfoBean.singleMaxAmount" required amount >
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">单批次最大笔数:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text" name="receiveConfigInfoBean.singleBatchMaxNum" required digits min="1">
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select profit-type" name="agentFees[8].profitType" dictTypeId="PROFIT_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text fee" name="agentFees[8].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="agentFees[8].maxFee" required amount minIgnoreEmpy="[name='agentFees[8].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">域名:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text" name="receiveConfigInfoBean.domain">
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">日限额:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text" name="receiveConfigInfoBean.dailyMaxAmount" required amount  minIgnoreEmpy="[name='receiveConfigInfoBean.singleMaxAmount']" message='{"minIgnoreEmpy": "小于单笔最大金额"}' >
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">状态:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select rate-select" name="receiveConfigInfoBean.status" dictTypeId="STATUS"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润比例:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text profit-ratio" name="agentFees[8].profitRatio" required amount profitratio>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 银行卡收单 -->
          <div class="productItem" data-productType="POS">
            <input type="hidden" class="ignoreEmpy" name="agentFees[9].productType" value="POS">
            <input type="hidden" class="ignoreEmpy agentNo" name="agentFees[9].agentNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="agentFees[9].status">
            <input type="hidden" class="ignoreEmpy fee-id" name="agentFees[9].id" />
            <div class="title-h2 fix">
              <span class="primary fl">银行卡收单</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select fee-type" dictTypeId="FEE_TYPE" name="agentFees[9].feeType"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="agentFees[9].minFee" required amount minfee maxIgnoreEmpy="[name='agentFees[9].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select profit-type" name="agentFees[9].profitType" dictTypeId="PROFIT_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text fee" name="agentFees[9].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="agentFees[9].maxFee" required amount maxfee minIgnoreEmpy="[name='agentFees[9].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润比例:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text profit-ratio" name="agentFees[9].profitRatio" required amount profitratio>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>


          <!-- 快捷支付 -->
          <div class="productItem" data-productType="QUICKPAY">
            <input type="hidden" class="ignoreEmpy" name="agentFees[10].productType" value="QUICKPAY">
            <input type="hidden" class="ignoreEmpy agentNo" name="agentFees[10].agentNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="agentFees[10].status">
            <input type="hidden" class="ignoreEmpy fee-id" name="agentFees[10].id" />
            <div class="title-h2 fix">
              <span class="primary fl">快捷支付</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select fee-type" dictTypeId="FEE_TYPE" name="agentFees[10].feeType"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="agentFees[10].minFee" required amount minfee maxIgnoreEmpy="[name='agentFees[10].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select profit-type" name="agentFees[10].profitType" dictTypeId="PROFIT_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text fee" name="agentFees[10].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="agentFees[10].maxFee" required amount maxfee minIgnoreEmpy="[name='agentFees[10].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润比例:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text profit-ratio" name="agentFees[10].profitRatio" required amount profitratio>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 支付宝条码 -->
          <div class="productItem" data-productType="ALIPAYMICROPAY">
            <input type="hidden" class="ignoreEmpy" name="agentFees[11].productType" value="ALIPAYMICROPAY">
            <input type="hidden" class="ignoreEmpy agentNo" name="agentFees[11].agentNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="agentFees[11].status">
            <input type="hidden" class="ignoreEmpy fee-id" name="agentFees[11].id" />
            <div class="title-h2 fix">
              <span class="primary fl">支付宝条码</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select fee-type" dictTypeId="FEE_TYPE" name="agentFees[11].feeType"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="agentFees[11].minFee" required amount minfee maxIgnoreEmpy="[name='agentFees[11].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select profit-type" name="agentFees[11].profitType" dictTypeId="PROFIT_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text fee" name="agentFees[11].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="agentFees[11].maxFee" required amount maxfee minIgnoreEmpy="[name='agentFees[11].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润比例:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text profit-ratio" name="agentFees[11].profitRatio" required amount profitratio>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          
          <!-- 微信条码 -->
          <div class="productItem" data-productType="WXMICROPAY">
            <input type="hidden" class="ignoreEmpy" name="agentFees[12].productType" value="WXMICROPAY">
            <input type="hidden" class="ignoreEmpy agentNo" name="agentFees[12].agentNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="agentFees[12].status">
            <input type="hidden" class="ignoreEmpy fee-id" name="agentFees[12].id" />
            <div class="title-h2 fix">
              <span class="primary fl">微信条码</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select fee-type" dictTypeId="FEE_TYPE" name="agentFees[12].feeType"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="agentFees[12].minFee" required amount minfee maxIgnoreEmpy="[name='agentFees[12].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select profit-type" name="agentFees[12].profitType" dictTypeId="PROFIT_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text fee" name="agentFees[12].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="agentFees[12].maxFee" required amount maxfee minIgnoreEmpy="[name='agentFees[12].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润比例:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text profit-ratio" name="agentFees[12].profitRatio" required amount profitratio>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 绑卡认证 -->
          <div class="productItem" data-productType="BINDCARD_AUTH">
            <input type="hidden" class="ignoreEmpy" name="agentFees[13].productType" value="BINDCARD_AUTH">
            <input type="hidden" class="ignoreEmpy agentNo" name="agentFees[13].agentNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="agentFees[13].status">
            <input type="hidden" class="ignoreEmpy fee-id" name="agentFees[13].id" />
            <div class="title-h2 fix">
              <span class="primary fl">绑卡认证</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select fee-type" dictTypeId="FEE_TYPE" name="agentFees[13].feeType"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="agentFees[13].minFee" required amount minfee maxIgnoreEmpy="[name='agentFees[13].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select profit-type" name="agentFees[13].profitType" dictTypeId="PROFIT_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text fee" name="agentFees[13].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="agentFees[13].maxFee" required amount maxfee minIgnoreEmpy="[name='agentFees[13].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润比例:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text profit-ratio" name="agentFees[13].profitRatio" required amount profitratio>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 支付宝服务窗 -->
          <div class="productItem" data-productType="ALIPAYJSAPI">
            <input type="hidden" class="ignoreEmpy" name="agentFees[14].productType" value="ALIPAYJSAPI">
            <input type="hidden" class="ignoreEmpy agentNo" name="agentFees[14].agentNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="agentFees[14].status">
            <input type="hidden" class="ignoreEmpy fee-id" name="agentFees[14].id" />
            <div class="title-h2 fix">
              <span class="primary fl">支付宝服务窗</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select fee-type" dictTypeId="FEE_TYPE" name="agentFees[14].feeType"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="agentFees[14].minFee" required amount minfee maxIgnoreEmpy="[name='agentFees[14].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润类型:</span>
                      <div class="input-wrap">
                        <dict:select styleClass="input-select profit-type" name="agentFees[14].profitType" dictTypeId="PROFIT_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col fl  w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text fee" name="agentFees[14].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="agentFees[14].maxFee" required amount maxfee minIgnoreEmpy="[name='agentFees[14].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">分润比例:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text profit-ratio" name="agentFees[14].profitRatio" required amount profitratio>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>


        </div>
      </div>
    </div>
	


    <!-- 服务商结算卡信息 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">服务商结算卡信息</span>
      </div>
      <div class="content">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账户类型:</span>
              <div class="input-wrap">
                <dict:select value="${agentSettle.agentType }" name="agentSettle.agentType" id="accType" styleClass="input-select" dictTypeId="ACCOUNT_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">入账周期:</span>
              <div class="input-wrap">
                <dict:select value="${cycle }" styleClass="input-select" name="cycle" id="customerCycle" dictTypeId="ENTRY_CYCLE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账户名称:</span>
              <div class="input-wrap">
                <input type="text" value="${agentSettle.accountName }" name="agentSettle.accountName" id="cardName" class="input-text"  required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">银行卡号:</span>
              <div class="input-wrap">
                <input type="text" value="${agentSettle.accountNo }" name="agentSettle.accountNo" id="accountNo" class="input-text"  required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">开户行名称:</span>
              <div class="input-wrap mr-10">
                <input type="text" class="input-text" value="${agentSettle.openBankName }" style="width: 280px;" name="agentSettle.openBankName" disabled id="openBankName" placeholder="请输入地区、分行支行名称等关键词进行匹配" required>
              </div>
              <input class="checkbox" type="checkbox" id="sabkBankFlag" value="0">
              <label>总行</label>
            </div>
          </div>
        </div>
      </div>
    </div>


    <!-- 证件信息  -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">证件信息</span>
      </div>
      <div class="content">
        <ul class="id-phone ml-10">
          <li>
            <span class="ico"><i class="fa fa-exclamation-circle"></i></span>
            <span class="ib w-200">企业营业执照 | 个人身份证正面</span>
            <span class="operating">
              <a class="btn" href="javascript:void(0);"  onclick="spliceImgPath('FILE_BUSILICECERT', '企业营业执照 | 个人身份证正面', this)" >查看原始图片</a>
              <a class="btn btn-tr-file" href="javascript:void(0);">修改</a>
              <input name="busiLiceCert" class="btn-file-input hidden" type="file" prompt="message" requiredNot checkImageSize accept="image/jpeg,image/jpg,image/png">
            </span>
          </li>
          <li>
            <span class="ico"><i class="fa fa-exclamation-circle"></i></span>
            <span class="ib w-200">企业税务登记证 | 个人身份证反面</span>
            <span class="operating">
              <a class="btn" href="javascript:void(0);" onclick="spliceImgPath('FILE_TAXREGCERT', '企业税务登记证 | 个人身份证反面', this)" 
               >查看原始图片</a>
              <a class="btn btn-tr-file" href="javascript:void(0);">修改</a>
              <input name="taxRegCert" class="btn-file-input hidden" type="file" prompt="message" requiredNot checkImageSize accept="image/jpeg,image/jpg,image/png">
            </span>
          </li>
          <li>
            <span class="ico"><i class="fa fa-exclamation-circle"></i></span>
            <span class="ib w-200">组织机构证 | 个人银行卡正面</span>
            <span class="operating">
              <a class="btn" href="javascript:void(0);"  onclick="spliceImgPath('FILE_ORGANIZATIONCERT', '组织机构证 | 个人银行卡正面', this)" 
               >查看原始图片</a>
              <a class="btn btn-tr-file" href="javascript:void(0);">修改</a>
              <input name="organizationCert" class="btn-file-input hidden" type="file" prompt="message" requiredNot checkImageSize accept="image/jpeg,image/jpg,image/png">
            </span>
          </li>
          <li>
            <span class="ico"><i class="fa fa-exclamation-circle"></i></span>
            <span class="ib w-200">银行开户许可证 | 个人银行卡反面</span>
            <span class="operating">
              <a class="btn" href="javascript:void(0);" onclick="spliceImgPath('FILE_OPENBANKACCCERT', '银行开户许可证 | 个人银行卡反面', this)" 
               >查看原始图片</a>
              <a class="btn btn-tr-file" href="javascript:void(0);">修改</a>
              <input name="openBankAccCert" class="btn-file-input hidden" type="file" prompt="message" requiredNot checkImageSize accept="image/jpeg,image/jpg,image/png">
            </span>
          </li>
          <li>
            <span class="ico"><i class="fa fa-exclamation-circle"></i></span>
            <span class="ib w-200">企业法人身份证 | 个人手持身份证</span>
            <span class="operating">
              <a class="btn" href="javascript:void(0);"  onclick="spliceImgPath('FILE_IDCARD', '企业法人身份证 | 个人手持身份证', this)" 
               >查看原始图片</a>
              <a class="btn btn-tr-file" href="javascript:void(0);">修改</a>
              <input name="idCard" class="btn-file-input hidden" type="file" prompt="message" requiredNot checkImageSize accept="image/jpeg,image/jpg,image/png">
            </span>
          </li>
        </ul>
      </div>
    </div>


    <!-- 手机号重要提示 -->
    <div id="tip-phone-content" style="width: 300px;display:none;" class="pd-10 tips">
      <h1>请谨慎填写此项!</h1>
      <p class="text-secondary">手机号是极为重要的凭证, 不可重复注册!</p>
    </div>


    <div class="text-center hidden mt-10">
      <button class="btn btn-submit">提 交</button>
      <input type="reset" class="btn clear-form" value="重 置" style="padding: 0px; width: 77px;">
    </div>

  </form>

  
  
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script src="${pageContext.request.contextPath}/js/lib/citySelect.js"></script>
  <script>

    // 增加代理费率/分润等验证
    general.addAgentVerify();

    // 初始化城市下拉列表
    $('form').dictCitySelect({
      prov: '${agent.province}',
      city: '${agent.city}'
    });
    // 初始化产品管理器
    product.init();
    // 根据上级费率进行验证
    general.feesForParent(function() {
      // console.log('切换费率验证');
    });
    
  // 服务商类型切换
    $('.accountType').change(function() {
      // 更改入网账户类型
      general.changeAccountType(this.value);
    }).trigger('change');
    
    function spliceImgPath(fileName, title, btn) {
      var path = '${pageContext.request.contextPath}/findDocumentImg.action?fileName=' + fileName + '&agentCert.agentNo=${agent.agentNo}&msg=' + Math.random();
      lookImg(path, title, btn);
    }
    

    $('.btn-tr-file').click(function() {
      $(this).next().trigger('click');
    });

    $('input[type="file"]').change(function() {
      if ($(this).val() == '') {
        $(this).closest('li').removeClass('modify');
        $(this).closest('.input-wrap').find('.fa-exclamation-circle').addClass('hidden');
      } else {
        $(this).closest('li').addClass('modify');
        $(this).closest('.input-wrap').find('.fa-exclamation-circle').removeClass('hidden');
      }
    });
    
    
    
    // 初始化结算卡验证
    $(document).ready(function() {
      var agentForm = $('#agentAdd');
      var submitBtn = $('.btn-submit');
      // 获取表单验证对象实例 
      var agentValidator = window.getValidator(agentForm);
      // 初始化卡识别验证
      general.SettleCardValidator(agentForm, agentValidator, true);
      // 启用验证调试
      agentValidator.options.debug = false;
      // 验证钩子
      agentValidator.options.onSubmit = function(status) {
        if (status !== Compared.EnumStatus.fail) {
          submitBtn.loading();
        } else {
          submitBtn.ending();
        }
        
      };
      agentValidator.options.onCustomize = function() {
        
        // 必须开通一个业务产品
        if (product.products.length === 0) {
          new window.top.Alert('必须开通一个业务!', '警告');
          submitBtn.ending();
          return false;
        }

        // 没有开通代付则弹询问框确认
        if (utils.indexOf(product.products, 'REMIT') == -1) {
          new window.top.Confirm('未开通代付许多功能将无法使用<br/>确定不开通代付吗?', '警告', function() {
            agentValidator.submit(true);
          }, function() {
            submitBtn.ending();
          }, null, true);
          return false;
        }
        return true;
      };

      // 更改费率类型
      $('.fee-type').change(function(event, ignore) {
        general.changeFeeType(this.value, $(this).closest('.productItem'), function(rote, minfee, maxfee, feeType) {
          // 获取表单验证对象实例 
          if (ignore === true)
            return;
          // 更改费率类型后主动验证
          if (rote.get(0).enumstatus === Compared.EnumStatus.fail)
            agentValidator.element(rote);
          if (minfee.get(0).enumstatus === Compared.EnumStatus.fail)
            agentValidator.element(minfee);
          if (maxfee.get(0).enumstatus === Compared.EnumStatus.fail)
            agentValidator.element(maxfee);
        });
      }).trigger('change', true);

    });

  </script>
  
  
  <c:if test="${agentFees != null && agentFees.size() > 0}">
    <c:forEach items="${agentFees }" var="agentFee" varStatus="tempIdx">
      <!-- 循环普通产品费率 -->
      <c:if test="${agentFee.productType.name() != 'REMIT' && agentFee.productType.name() != 'RECEIVE' }">
        <script>
          product.fillBaseValue(product.open('${agentFee.productType.name()}', null, true), {
            // 费率类型 RATIO=百分比, FIXED=固定
            '.fee-type': '${agentFee.feeType }',
            // 费率id
            '.fee-id': '${agentFee.id}',
            // 不知道干什么的
            '.fee-agentNo': '${agentFee.agentNo}',
            // 费率
            '.fee': '${agentFee.fee }',
            // 最低费率
            '.minfee': '${agentFee.minFee }',
            // 最高费率
            '.maxfee': '${agentFee.maxFee }',
            // 分润类型
            '.profit-type': '${agentFee.profitType}',
            // 分润比率
            '.profit-ratio': '${agentFee.profitRatio }',
            // 服务商编码
            '.agentNo': '${agentFee.agentNo}',
            // 费率状态
            '.fee-status': '${agentFee.status}',
            // id
            '.fee-id': '${agentFee.id}'
          });
        </script>
      </c:if>
      <!-- 初始化代付费率 -->
      <c:if test="${agentFee.productType.name() == 'REMIT'}">
        <script>
          product.fillRemit(product.open('REMIT', null, true), {
            // 费率类型 RATIO=百分比, FIXED=固定
            '.fee-type': '${agentFee.feeType }',
            // 费率id
            '.fee-id': '${agentFee.id}',
            // 不知道干什么的
            '.fee-agentNo': '${agentFee.agentNo}',
            // 费率
            '.fee': '${agentFee.fee }',
            // 最低费率
            '.minfee': '${agentFee.minFee }',
            // 最高费率
            '.maxfee': '${agentFee.maxFee }',
            // 分润类型
            '.profit-type': '${agentFee.profitType}',
            // 分润比率
            '.profit-ratio': '${agentFee.profitRatio }',
            // 审核 TRUE=是, FALSE=否
            '[name="serviceConfigBean.manualAudit"]': '${serviceConfigBean.manualAudit }',
            // 短信验证 TRUE=是, FALSE=否
            '[name="serviceConfigBean.usePhoneCheck"]': '${serviceConfigBean.usePhoneCheck }',
            // IP
            '[name="serviceConfigBean.custIp"]': '${serviceConfigBean.custIp }',
            // 域名
            '[name="serviceConfigBean.domain"]': '${serviceConfigBean.domain }',
            // 单笔最大金额
            '[name="serviceConfigBean.maxAmount"]': '${serviceConfigBean.maxAmount }',
            // 单笔最小金额
            '[name="serviceConfigBean.minAmount"]': '${serviceConfigBean.minAmount }',
            // 单日最大金额
            '[name="serviceConfigBean.dayMaxAmount"]': '${serviceConfigBean.dayMaxAmount }',
            // 付款起始时间
            '[name="serviceConfigBean.startTime"]': '${serviceConfigBean.startTime}',
            // 付款截止时间
            '[name="serviceConfigBean.endTime"]': '${serviceConfigBean.endTime}',
            // 自动结算 AUTO=自动, MAN=人工
            '[name="serviceConfigBean.fireType"]': '${serviceConfigBean.fireType}',
            // 运营审核 TRUE=需要运营审核, FALSE=不需要
            '[name="serviceConfigBean.bossAudit"]': '${serviceConfigBean.bossAudit }',
            // 是否开通假日付
            '[name="serviceConfigBean.holidayStatus"]': '${serviceConfigBean.holidayStatus}',
            // ownerId
            '[name="serviceConfigBean.ownerId"]': '${serviceConfigBean.ownerId }',
            // 服务商编码
            '.agentNo': '${agentFee.agentNo}',
            // 费率状态
            '.fee-status': '${agentFee.status}',
            // id
            '.fee-id': '${agentFee.id}'
          });
        </script>
      </c:if>
      <!-- 初始化代收费率 -->
      <c:if test="${agentFee.productType.name() == 'RECEIVE'}">
        <script>
          product.fillRemit(product.open('RECEIVE', null, true), {
            // 费率类型 RATIO=百分比, FIXED=固定
            '.fee-type': '${agentFee.feeType }',
            // 费率id
            '.fee-id': '${agentFee.id}',
            // 不知道干什么的
            '.fee-agentNo': '${agentFee.agentNo}',
            // 费率
            '.fee': '${agentFee.fee }',
            // 最低费率
            '.minfee': '${agentFee.minFee }',
            // 最高费率
            '.maxfee': '${agentFee.maxFee }',
            // 分润类型
            '.profit-type': '${agentFee.profitType}',
            // 分润比率
            '.profit-ratio': '${agentFee.profitRatio }',
            // IP
            '[name="receiveConfigInfoBean.custIp"]': '${receiveConfigInfoBean.custIp }',
            // 域名
            '[name="receiveConfigInfoBean.domain"]': '${receiveConfigInfoBean.domain }',
            // 单笔最大金额
            '[name="receiveConfigInfoBean.singleMaxAmount"]': '${receiveConfigInfoBean.singleMaxAmount}',
            // 单批最大笔数
            '[name="receiveConfigInfoBean.singleBatchMaxNum"]': '${receiveConfigInfoBean.singleBatchMaxNum}',
            // 日限额
            '[name="receiveConfigInfoBean.dailyMaxAmount"]': '${receiveConfigInfoBean.dailyMaxAmount}',
            // 状态
            '[name="receiveConfigInfoBean.status"]': '${receiveConfigInfoBean.status}',
            // ownerId
            '[name="receiveConfigInfoBean.ownerId"]': '${receiveConfigInfoBean.ownerId }',
            // 服务商编码
            '.agentNo': '${agentFee.agentNo}',
            // 费率状态
            '.fee-status': '${agentFee.status}',
            // id
            '.fee-id': '${agentFee.id}'
          });
        </script>
      </c:if>
      
    </c:forEach>
  </c:if>
  <c:if test="${ agentFees.size() == 0}">
    <!-- 显示暂无费率信息 -->
    <script>
      product.showNothing();
    <script>
  </c:if>
  
</body>
</html>
