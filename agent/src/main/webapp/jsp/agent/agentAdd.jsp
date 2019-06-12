<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>服务商入网</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="min-width: 810px; padding:0.2em;">
  
  <form class="validator" id="agentAdd" method="post" enctype="multipart/form-data" action="openAgent.action" prompt="DropdownMessage"  style="padding: 0.4em;">
    <input type="hidden" id="bankCode" readonly="readonly" name="agentSettle.bankCode">
    <input type="hidden" class="organization" name="agent.organization" />

    <!-- 基本信息 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">基本信息</span>
      </div>
      <div class="content">
        <div class="fix">
          <div class="col fl">
            <div class="fix">
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">服务商全称:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="agent.fullName" required rangelength="[2,16]" checkAgentFullName trigger='{"checkAgentFullName": 2}'>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">服务商简称:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="agent.shortName" required rangelength="[2,16]" checkAgentSortName trigger='{"checkAgentSortName": 2}'>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">服务商联系人:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="agent.linkMan" required rangelength="[2,16]">
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">保证金:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="agent.cautionMoney" required amountAny>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">手机号(登陆名):</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="agent.phoneNo" required phone checkagentPhone trigger='{"checkagentPhone": 2}'>
                    <i id="tips-phone" style="cursor: pointer;" class="color-orange fa fa-exclamation-circle"></i>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90 cardText">身份证号:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="agent.idCard" required idCard>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col fl">
            <div class="fix">
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">服务商类型:</span>
                  <div class="input-wrap">
                    <dict:select styleClass="input-select accountType" shield="OEM"  name="agent.agentType" dictTypeId="AGENT_TYPE"></dict:select>
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
                    <input type="text" class="input-text" name="agent.addr" required rangelength="[1,40]">
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">邮箱:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="agent.eMail" email requiredNot>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>


  <!-- 企业信息 -->
    <div class="row companyInfo">
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
                  <input type="text" class="input-text open-cate" name="agentCert.legalPerson" disabled required>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">企业网址:</span>
                <div class="input-wrap">
                  <input type="text" class="input-text open-cate" name="agentCert.enterpriseUrl" disabled>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">客服电话:</span>
                <div class="input-wrap">
                  <input type="text" class="input-text open-cate" name="agentCert.consumerPhone" disabled required telPhone>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">ICP备案号:</span>
                <div class="input-wrap">
                  <input type="text" class="input-text open-cate" name="agentCert.icp" disabled>
                </div>
              </div>
            </div>
          </div>
          <div class="col fl">
            <div class="item">
              <div class="input-area">
                <span class="label w-90">经营范围:</span>
                <div class="input-wrap">
                  <input type="text" class="input-text open-cate" name="agentCert.businessScope" disabled required>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">企业信用码:</span>
                <div class="input-wrap">
                  <input type="text" class="input-text open-cate" name="agentCert.enterpriseCode" disabled required>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">经营地址:</span>
                <div class="input-wrap">
                  <input type="text" class="input-text open-cate" name="agentCert.businessAddress" disabled required>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">营业期限:</span>
                <div class="input-wrap">
                  <input type="text" name="agentCert.validStartTime" class="input-text double-input date-start" disabled required>
                </div>
                <span class="cut"> - </span>
                <div class="input-wrap">
                  <input type="text" name="agentCert.validEndTime" class="input-text double-input date-end" disabled required>
                </div>
              </div>
            </div>
          </div>
        </div>
        
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">补充材料:</span>
              <div class="input-wrap">
                <div class="file-wrap">
                  <input type="text" class="file-text open-cate w-375" placeholder="上传照片" readonly>
                  <a class="btn" noWidth="true" href="javascript:void(0);">浏览</a>
                  <input type="file" class="open-cate" name="attachment" required accept="image/jpeg,image/jpg,image/png" checkImageSize prompt="message" disabled>
                </div>
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
<!--                 <select multiple class="input-select" name="productTypeSelect" id="productTypeSelect">
                  <option value="B2C">个人网银</option>
                  <option value="B2B">企业网银</option>
                  <option value="AUTHPAY">认证支付</option>
                  <option value="REMIT">代付</option>
                  <option value="WXJSAPI">微信公众号</option>
                  <option value="WXNATIVE">微信扫码</option>
                  <option value="ALIPAY">支付宝</option>
                  <option value="RECEIVE">代收</option>
                  <option disabled value="WXMICROPAY">微信条码(暂无)</option>
                  <option value="POS">银行卡收单(开发中...)</option>
                </select> -->
              </div>
            </div>
          </div>
        </div>
        <div id="productWrap">
          <!-- 个人网银 -->
          <div class="productItem" data-productType="B2C">
            <input type="hidden" class="ignoreEmpy" name="agentFees[0].productType" value="B2C">
            <div class="title-h2 fix">
              <span class="primary fl">个人网银</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl">
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
              <div class="col fl">
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
            <div class="title-h2 fix">
              <span class="primary fl">企业网银</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl">
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
              <div class="col fl">
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
            <div class="title-h2 fix">
              <span class="primary fl">认证支付</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl">
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
              <div class="col fl">
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
            <div class="title-h2 fix">
              <span class="primary fl">微信公众号支付</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl">
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
              <div class="col fl">
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
            <div class="title-h2 fix">
              <span class="primary fl">微信二维码支付</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl">
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
              <div class="col fl">
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
            <div class="title-h2 fix">
              <span class="primary fl">支付宝</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl">
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
              <div class="col fl">
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
            <div class="title-h2 fix">
              <span class="primary fl">代付</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl">
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
                        <select class="input-select holidayStatus" name="serviceConfigBean.holidayStatus" data-value="FALSE" required>
                          <option value="TRUE">是</option>
                          <option value="FALSE">否</option>
                        </select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col fl">
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
                        <input type="text" name="serviceConfigBean.startTime" class="input-date w-78" date-fmt="H:mm" required>
                      </div>
                      <span class="cut"> - </span>
                      <div class="input-wrap">
                        <input type="text" name="serviceConfigBean.endTime" class="input-date w-78" date-fmt="H:mm" required>
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
            <div class="title-h2 fix">
              <span class="primary fl">假日付</span>
            </div>
            <div class="fix">
              <div class="col fl">
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
              <div class="col fl">
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
            <div class="title-h2 fix">
              <span class="primary fl">代收</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl">
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
              <div class="col fl">
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
            <div class="title-h2 fix">
              <span class="primary fl">银行卡收单</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl">
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
              <div class="col fl">
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
            <div class="title-h2 fix">
              <span class="primary fl">快捷支付</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl">
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
              <div class="col fl">
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
            <div class="title-h2 fix">
              <span class="primary fl">支付宝条码</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl">
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
              <div class="col fl">
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
            <div class="title-h2 fix">
              <span class="primary fl">微信条码</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl">
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
              <div class="col fl">
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
            <div class="title-h2 fix">
              <span class="primary fl">绑卡认证</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl">
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
              <div class="col fl">
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
            <div class="title-h2 fix">
              <span class="primary fl">支付宝服务窗</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl">
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
              <div class="col fl">
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
                <dict:select name="agentSettle.agentType" id="accType" styleClass="input-select" dictTypeId="ACCOUNT_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">入账周期:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select" name="cycle" id="customerCycle" dictTypeId="ENTRY_CYCLE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账户名称:</span>
              <div class="input-wrap">
                <input type="text" name="agentSettle.accountName" id="cardName" class="input-text"  required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">银行卡号:</span>
              <div class="input-wrap">
                <input type="text" name="agentSettle.accountNo" id="accountNo" class="input-text"  required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">开户行名称:</span>
              <div class="input-wrap mr-10">
                <input type="text" class="input-text" style="width: 280px;" name="agentSettle.openBankName" disabled id="openBankName" placeholder="请输入地区、分行支行名称等关键词进行匹配" required>
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
        <ul class="ml-10">
          <li class="mb-10">
            <span class="ib w-220">企业营业执照|个人身份证正面:</span>
            <div class="file-wrap">
              <input type="text" class="file-text w-200" placeholder="上传照片" readonly>
              <a class="btn" href="javascript:void(0);">浏览</a>
              <input type="file" name="busiLiceCert" required accept="image/jpeg,image/jpg,image/png" checkImageSize prompt="message">
            </div>
          </li>
          <li class="mb-10">
            <div class="file-wrap">
              <span class="ib w-220">企业税务登记证|个人身份证反面:</span>
              <input type="text" class="file-text w-200" placeholder="上传照片" readonly>
              <a class="btn" href="javascript:void(0);">浏览</a>
              <input type="file" name="taxRegCert" required accept="image/jpeg,image/jpg,image/png" checkImageSize prompt="message">
            </div>
          </li>
          <li class="mb-10">
            <div class="file-wrap">
              <span class="ib w-220">组织机构证|个人银行卡正面:</span>
              <input type="text" class="file-text w-200" placeholder="上传照片" readonly>
              <a class="btn" href="javascript:void(0);">浏览</a>
              <input type="file" name="organizationCert" required accept="image/jpeg,image/jpg,image/png" checkImageSize prompt="message">
            </div>
          </li>
          <li class="mb-10">
            <div class="file-wrap">
              <span class="ib w-220">银行开户许可证|个人银行卡反面:</span>
              <input type="text" class="file-text w-200" placeholder="上传照片" readonly>
              <a class="btn" href="javascript:void(0);">浏览</a>
              <input type="file" name="openBankAccCert" required accept="image/jpeg,image/jpg,image/png" checkImageSize prompt="message">
            </div>
          </li>
          <li class="mb-10">
            <div class="file-wrap">
              <span class="ib w-220">企业法人身份证|个人手持身份证:</span>
              <input type="text" class="file-text w-200" placeholder="上传照片" readonly>
              <a class="btn" href="javascript:void(0);">浏览</a>
              <input type="file" name="idCard" required accept="image/jpeg,image/jpg,image/png" checkImageSize prompt="message">
            </div>
          </li>
        </ul>
      </div>
    </div>


    <!-- 手机号重要提示 -->
    <div id="tip-phone-content" style="width: 300px;display:none;" class="pd-10 tips">
      <h1>请谨慎填写此项!</h1>
      <p class="text-secondary">手机号是极为重要的凭证, 不可重复注册!</p>
    </div>


    <div class="text-center mt-10">
      <button class="btn btn-submit">提 交</button>
      <input type="reset" class="btn clear-form" value="重 置" style="padding: 0px; width: 77px;">
    </div>

  </form>

  
  
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script src="${pageContext.request.contextPath}/js/lib/citySelect.js"></script>
  <script>
    // 入网结果提示
    (function() {
      var dialog = '';
      if ( <%=request.getParameter("status") %> == true ) {
        dialog = new window.top.Confirm('入网成功, 是否立即去查询!', '通知',  function() {
          var windowManage = window.top.windowManage;
          var win = windowManage.current();
          win.go('${pageContext.request.contextPath}/toAgentQuery.action', '服务商信息');
        }, function() {
          dialog.close(null, true);
        }, null, true);
      } else if ( <%=request.getParameter("status") %> == false ) {
        new window.top.Alert('入网失败, 请联系管理员或重试!', '通知');
      }
    })();

    // 增加代理费率/分润等验证
    general.addAgentVerify();
    // 根据上级费率进行验证
    general.feesForParent(function() {
      // console.log('切换费率验证');
    });
    // 服务商类型切换
    $('.accountType').change(function() {
      // 更改入网账户类型
      general.changeAccountType(this.value);
    }).trigger('change');
    
    // 初始化城市下拉列表
    $('form').dictCitySelect({
      prov: null,
      city: null
    });
    
    // 手机号下拉提示
    $('#tips-phone').Dropdown({
      target: '#tip-phone-content',
      direction: 'down',
      trigger: 'mouseenter',
      autoClose: true,
      style: ['Dropdown-on', 'Dropdown-off']
    });
    // 初始化产品管理器
    product.init();

    // 初始化结算卡验证
    $(document).ready(function() {
    	product.openAll();
      var agentForm = $('#agentAdd');
      var submitBtn = $('.btn-submit');
      // 获取表单验证对象实例 
      var agentValidator = window.getValidator(agentForm);
      // 初始化卡识别验证
      general.SettleCardValidator(agentForm, agentValidator);
      // 启用验证调试
      agentValidator.options.debug = false;
      // 验证钩子
      agentValidator.options.onSubmit = function(status) {
        if (status !== Compared.EnumStatus.fail) {
          submitBtn.loading();
          agentForm.showLoadImage(null, true);
        } else {
          submitBtn.ending();
          agentForm.hideLoadImage();
        }
        
      };
      agentValidator.options.onCustomize = function() {
        
        // 必须开通一个业务产品
        if (product.products.length === 0) {
          new window.top.Alert('必须开通一个业务!', '警告');
          submitBtn.ending();
          agentForm.hideLoadImage();
          return false;
        }

        // 没有开通代付则弹询问框确认
        if (utils.indexOf(product.products, 'REMIT') == -1) {
          new window.top.Confirm('未开通代付许多功能将无法使用<br/>确定不开通代付吗?', '警告', function() {
            agentValidator.submit(true);
          }, function() {
            submitBtn.ending();
            agentForm.hideLoadImage();
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
</body>
</html>
