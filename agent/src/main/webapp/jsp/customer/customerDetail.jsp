<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>商户信息详情</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 900px;">
  
  
  <div style="padding: 20px 10px;">

    <!-- 基本信息 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">基本信息</span>
      </div>
      <div class="content detail">

        <div class="col w-p-50">
          <div class="row">
            <span class="label">商户编号:</span>
            <span class="value">${customer.customerNo}</span>
          </div>
          <div class="row">
            <span class="label">商户类型:</span>
            <span class="value"><dict:write dictId="${customer.customerType }" dictTypeId="AGENT_TYPE"></dict:write></span>
          </div>
          <div class="row">
            <span class="label">商户联系人:</span>
            <span class="value">${customer.linkMan}</span>
          </div>
          <div class="row">
            <span class="label">商户全称:</span>
            <span class="value">${customer.fullName}</span>
          </div>
          <div class="row">
            <span class="label">商户状态:</span>
            <span class="value color-orange"><dict:write dictId="${customer.status }" dictTypeId="CUSTOMER_STATUS"></dict:write></span>
          </div>
          <div class="row">
            <span class="label">商户联系电话:</span>
            <span class="value">${customer.phoneNo}</span>
          </div>
          <div class="row">
            <span class="label">入账周期:</span>
            <span class="value">
              <c:if test="${cycle == 0}">T+0</c:if>
              <c:if test="${cycle == 1}">T+1</c:if>
            </span>
          </div>
          <div class="row">
            <span class="label"><c:if test="${customer.customerType == 'INDIVIDUAL'}">身份证号:</c:if><c:if test="${customer.customerType != 'INDIVIDUAL'}">法人身份证号:</c:if></span>
            <span class="value">${customer.idCard }</span>
          </div>
        </div>
        <div class="col w-p-50">
          <div class="row">
            <span class="label">保证金:</span>
            <span class="value"><fmt:formatNumber currencySymbol="" type="currency" value="${customer.cautionMoney}" /></span>
          </div>
          <div class="row">
            <span class="label">商户简称:</span>
            <span class="value">${customer.shortName}</span>
          </div>
          <div class="row">
            <span class="label">所属服务商:</span>
            <span class="value">${customer.agentNo}</span>
          </div>
          <div class="row">
            <span class="label">所属省:</span>
            <span class="value">${customer.province}</span>
          </div>
          <div class="row">
            <span class="label">所属市:</span>
            <span class="value">${customer.city}市</span>
          </div>
          <div class="row">
            <span class="label">商户地址:</span>
            <span class="value">${customer.addr}</span>
          </div>
          <div class="row">
            <span class="label">邮箱:</span>
            <span class="value">${customer.EMail}</span>
          </div>
        </div>
      </div>
    </div>

  <c:if test="${customer.customerType != 'INDIVIDUAL'}">
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
                <span class="value">${customerCert.legalPerson}</span>
              </div>
              <div class="row">
                <span class="label">企业网址:</span>
                <span class="value">${customerCert.enterpriseUrl}</span>
              </div>
              <div class="row">
                <span class="label">客服电话:</span>
                <span class="value">${customerCert.consumerPhone}</span>
              </div>
              <div class="row">
                <span class="label">ICP备案号:</span>
                <span class="value">${customerCert.icp}</span>
              </div>
            </div>
            <div class="col w-p-50">
              <div class="row">
                <span class="label">经营范围:</span>
                <span class="value">${customerCert.businessScope}</span>
              </div>
              <div class="row">
                <span class="label">企业信用码:</span>
                <span class="value">${customerCert.enterpriseCode}</span>
              </div>
              <div class="row">
                <span class="label">经营地址:</span>
                <span class="value">${customerCert.businessAddress}</span>
              </div>
              <div class="row">
                <span class="label">营业期限:</span>
                <span class="value"><fmt:formatDate value="${customerCert.validStartTime}" pattern="yyyy-MM-dd" /> ~ <fmt:formatDate value="${customerCert.validEndTime}" pattern="yyyy-MM-dd" /></span>
              </div>
            </div>
          </div>
          <div class="fix">
            <div class="item">
              <div class="input-area">
                <span class="label w-90">补充材料:</span>
                <div class="input-wrap">
                  <a class="btn" href="javascript:void(0);" onclick="lookImg('${pageContext.request.contextPath}/findCustomerDocumentImg.action?fileName=FILE_ATT&customerCert.customerNo=${customer.customerNo}','补充材料', this);" >查看</a>
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
          <!-- 个人网银 -->
          <div class="productItem" data-productType="B2C">
            <input type="hidden" class="ignoreEmpy" name="customerFees[0].productType" value="B2C">
            <input type="hidden" class="ignoreEmpy fee-id" name="customerFees[0].id"  />
            <input type="hidden" class="ignoreEmpy" name="customerFees[0].customerNo" >
            <input type="hidden" class="ignoreEmpy" name="customerFees[0].status" >
            <div class="title-h2 fix">
              <span class="primary fl">个人网银</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select name="customerFees[0].feeType" styleClass="input-select fee-type" dictTypeId="FEE_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="customerFees[0].minFee" required amount minfee maxIgnoreEmpy="[name='customerFees[0].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
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
                        <input type="text" class="input-text fee" name="customerFees[0].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="customerFees[0].maxFee" required amount maxfee minIgnoreEmpy="[name='customerFees[0].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 企业网银 -->
          <div class="productItem" data-productType="B2B">
            <input type="hidden" class="ignoreEmpy" name="customerFees[1].productType" value="B2B">
            <input type="hidden" class="ignoreEmpy fee-id" name="customerFees[1].id" />
            <input type="hidden" class="ignoreEmpy" name="customerFees[0].customerNo" >
            <input type="hidden" class="ignoreEmpy" name="customerFees[0].status" >
            <div class="title-h2 fix">
              <span class="primary fl">企业网银</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select name="customerFees[1].feeType" styleClass="input-select fee-type" dictTypeId="FEE_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="customerFees[1].minFee" required amount minfee maxIgnoreEmpy="[name='customerFees[1].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
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
                        <input type="text" class="input-text fee" name="customerFees[1].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="customerFees[1].maxFee" required amount maxfee minIgnoreEmpy="[name='customerFees[1].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 认证支付 -->
          <div class="productItem" data-productType="AUTHPAY">
            <input type="hidden" class="ignoreEmpy" name="customerFees[2].productType" value="AUTHPAY">
            <input type="hidden" class="ignoreEmpy fee-id" name="customerFees[2].id" />
            <input type="hidden" class="ignoreEmpy" name="customerFees[0].customerNo" >
            <input type="hidden" class="ignoreEmpy" name="customerFees[0].status" >
            <div class="title-h2 fix">
              <span class="primary fl">认证支付</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select name="customerFees[2].feeType" styleClass="input-select fee-type" dictTypeId="FEE_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="customerFees[2].minFee" required amount minfee maxIgnoreEmpy="[name='customerFees[2].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
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
                        <input type="text" class="input-text fee" name="customerFees[2].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="customerFees[2].maxFee" required amount maxfee minIgnoreEmpy="[name='customerFees[2].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 微信公众号支付 -->
          <div class="productItem" data-productType="WXJSAPI">
            <input type="hidden" class="ignoreEmpy" name="customerFees[3].productType" value="WXJSAPI">
            <input type="hidden" class="ignoreEmpy fee-id" name="customerFees[3].id" />
            <input type="hidden" class="ignoreEmpy" name="customerFees[0].customerNo" >
            <input type="hidden" class="ignoreEmpy" name="customerFees[0].status" >
            <div class="title-h2 fix">
              <span class="primary fl">微信公众号支付</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select name="customerFees[3].feeType" styleClass="input-select fee-type" dictTypeId="FEE_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="customerFees[3].minFee" required amount minfee maxIgnoreEmpy="[name='customerFees[3].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
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
                        <input type="text" class="input-text fee" name="customerFees[3].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="customerFees[3].maxFee" required amount maxfee minIgnoreEmpy="[name='customerFees[3].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 微信二维码支付 -->
          <div class="productItem" data-productType="WXNATIVE">
            <input type="hidden" class="ignoreEmpy" name="customerFees[4].productType" value="WXNATIVE">
            <input type="hidden" class="ignoreEmpy fee-id" name="customerFees[4].id"  />
            <input type="hidden" class="ignoreEmpy" name="customerFees[0].customerNo" >
            <input type="hidden" class="ignoreEmpy" name="customerFees[0].status" >
            <div class="title-h2 fix">
              <span class="primary fl">微信二维码支付</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select name="customerFees[4].feeType" styleClass="input-select fee-type" dictTypeId="FEE_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="customerFees[4].minFee" required amount minfee maxIgnoreEmpy="[name='customerFees[4].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
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
                        <input type="text" class="input-text fee" name="customerFees[4].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="customerFees[4].maxFee" required amount maxfee minIgnoreEmpy="[name='customerFees[4].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 支付宝 -->
          <div class="productItem" data-productType="ALIPAY">
            <input type="hidden" class="ignoreEmpy" name="customerFees[5].productType" value="ALIPAY">
            <input type="hidden" class="ignoreEmpy fee-id" name="customerFees[5].id"  />
            <input type="hidden" class="ignoreEmpy" name="customerFees[0].customerNo" >
            <input type="hidden" class="ignoreEmpy" name="customerFees[0].status" >
            <div class="title-h2 fix">
              <span class="primary fl">支付宝</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select name="customerFees[5].feeType" styleClass="input-select fee-type" dictTypeId="FEE_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="customerFees[5].minFee" required amount minfee maxIgnoreEmpy="[name='customerFees[5].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
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
                        <input type="text" class="input-text fee" name="customerFees[5].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="customerFees[5].maxFee" required amount maxfee minIgnoreEmpy="[name='customerFees[5].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 代付 -->
          <div class="productItem" data-productType="REMIT">
            <input type="hidden" class="ignoreEmpy" name="customerFees[6].productType" value="REMIT">
            <input type="hidden" class="ignoreEmpy fee-id" name="customerFees[6].id"  />
            <input type="hidden" class="ignoreEmpy" name="customerFees[0].customerNo" >
            <input type="hidden" class="ignoreEmpy" name="customerFees[0].status" >
            <input type="hidden" class="ignoreEmpy" name="serviceConfigBean.ownerId">
            <div class="title-h2 fix">
              <span class="primary fl">代付</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select name="customerFees[6].feeType" styleClass="input-select fee-type" dictTypeId="FEE_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="customerFees[6].minFee" required amount minfee maxIgnoreEmpy="[name='customerFees[6].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">审核:</span>
                      <div class="input-wrap">
                        <dict:select name="serviceConfigBean.manualAudit" styleClass="input-select" dictTypeId="ALL_STATUS"></dict:select>
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
                      <span class="label w-90">是否开通假日付:</span>
                      <div class="input-wrap">
                        <select class="input-select holidayStatus" name="serviceConfigBean.holidayStatus" readonly>
                          <option value="TRUE">是</option>
                          <option value="FALSE">否</option>
                        </select>
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
                        <input type="text" class="input-text fee" name="customerFees[6].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="customerFees[6].maxFee" required amount maxfee minIgnoreEmpy="[name='customerFees[6].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">短信验证:</span>
                      <div class="input-wrap">
                        <dict:select name="serviceConfigBean.usePhoneCheck" styleClass="input-select" dictTypeId="ALL_STATUS"></dict:select>
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
                        <dict:select name="serviceConfigBean.bossAudit" styleClass="input-select" dictTypeId="DF_AUDIT"></dict:select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 假日付 -->
          <div class="productItem" data-productType="HOLIDAY_REMIT">
            <input type="hidden" class="ignoreEmpy" name="customerFees[7].productType" value="HOLIDAY_REMIT">
            <input type="hidden" class="ignoreEmpy fee-id" name="customerFees[7].id"  />
            <div class="title-h2 fix">
              <span class="primary fl">假日付</span>
            </div>
            <div class="fix">
              <div class="col fl w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select name="customerFees[7].feeType" styleClass="input-select fee-type" dictTypeId="FEE_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="customerFees[7].minFee" required amount minfee maxIgnoreEmpy="[name='customerFees[7].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
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
                        <input type="text" class="input-text fee" name="customerFees[7].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="customerFees[7].maxFee" required amount maxfee minIgnoreEmpy="[name='customerFees[7].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 代收 -->
          <div class="productItem" data-productType="RECEIVE">
            <input type="hidden" class="ignoreEmpy" name="customerFees[8].productType" value="RECEIVE">
            <input type="hidden" class="ignoreEmpy fee-id" name="customerFees[8].id" />
            <input type="hidden" class="ignoreEmpy" name="customerFees[0].customerNo" >
            <input type="hidden" class="ignoreEmpy" name="customerFees[0].status" >
            <input type="hidden" class="ignoreEmpy" name="receiveConfigInfoBean.ownerId" >
            <div class="title-h2 fix">
              <span class="primary fl">代收</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select name="customerFees[8].feeType" styleClass="input-select fee-type" dictTypeId="FEE_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="customerFees[8].minFee" required amount minfee maxIgnoreEmpy="[name='customerFees[8].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
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
                </div>
              </div>
              <div class="col fl w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text fee" name="customerFees[8].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="customerFees[8].maxFee" required amount maxfee minIgnoreEmpy="[name='customerFees[8].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
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
                        <dict:select styleClass="input-select" name="receiveConfigInfoBean.status" dictTypeId="STATUS"></dict:select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 银行卡收单 -->
          <div class="productItem" data-productType="POS">
            <input type="hidden" class="ignoreEmpy" name="customerFees[9].productType" value="POS">
            <input type="hidden" class="ignoreEmpy fee-id" name="customerFees[9].id"  />
            <input type="hidden" class="ignoreEmpy" name="customerFees[0].customerNo" >
            <input type="hidden" class="ignoreEmpy" name="customerFees[0].status" >
            <div class="title-h2 fix">
              <span class="primary fl">银行卡收单</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select name="customerFees[9].feeType" styleClass="input-select fee-type" dictTypeId="FEE_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="customerFees[9].minFee" required amount minfee maxIgnoreEmpy="[name='customerFees[9].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">MCC:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text" name="customer.mcc" required>
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
                        <input type="text" class="input-text fee" name="customerFees[9].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="customerFees[9].maxFee" required amount maxfee minIgnoreEmpy="[name='customerFees[9].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
        </div>
        
        
          <!-- 快捷支付 -->
          <div class="productItem" data-productType="QUICKPAY">
            <input type="hidden" class="ignoreEmpy" name="customerFees[10].productType" value="QUICKPAY">
            <input type="hidden" class="ignoreEmpy fee-id" name="customerFees[10].id"  />
            <input type="hidden" class="ignoreEmpy" name="customerFees[10].customerNo" >
            <input type="hidden" class="ignoreEmpy" name="customerFees[10].status" >
            <div class="title-h2 fix">
              <span class="primary fl">快捷支付</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select name="customerFees[10].feeType" styleClass="input-select fee-type" dictTypeId="FEE_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="customerFees[10].minFee" required amount minfee maxIgnoreEmpy="[name='customerFees[10].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
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
                        <input type="text" class="input-text fee" name="customerFees[10].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="customerFees[10].maxFee" required amount maxfee minIgnoreEmpy="[name='customerFees[10].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 支付宝条码 -->
          <div class="productItem" data-productType="ALIPAYMICROPAY">
            <input type="hidden" class="ignoreEmpy" name="customerFees[11].productType" value="ALIPAYMICROPAY">
            <input type="hidden" class="ignoreEmpy fee-id" name="customerFees[11].id"  />
            <input type="hidden" class="ignoreEmpy" name="customerFees[11].customerNo" >
            <input type="hidden" class="ignoreEmpy" name="customerFees[11].status" >
            <div class="title-h2 fix">
              <span class="primary fl">支付宝条码</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select name="customerFees[11].feeType" styleClass="input-select fee-type" dictTypeId="FEE_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="customerFees[11].minFee" required amount minfee maxIgnoreEmpy="[name='customerFees[11].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
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
                        <input type="text" class="input-text fee" name="customerFees[11].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="customerFees[11].maxFee" required amount maxfee minIgnoreEmpy="[name='customerFees[11].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 微信条码 -->
          <div class="productItem" data-productType="WXMICROPAY">
            <input type="hidden" class="ignoreEmpy" name="customerFees[12].productType" value="WXMICROPAY">
            <input type="hidden" class="ignoreEmpy fee-id" name="customerFees[12].id"  />
            <input type="hidden" class="ignoreEmpy" name="customerFees[12].customerNo" >
            <input type="hidden" class="ignoreEmpy" name="customerFees[12].status" >
            <div class="title-h2 fix">
              <span class="primary fl">微信条码</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select name="customerFees[12].feeType" styleClass="input-select fee-type" dictTypeId="FEE_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="customerFees[12].minFee" required amount minfee maxIgnoreEmpy="[name='customerFees[12].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
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
                        <input type="text" class="input-text fee" name="customerFees[12].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="customerFees[12].maxFee" required amount maxfee minIgnoreEmpy="[name='customerFees[12].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          
          
          <!-- 绑卡认证 -->
          <div class="productItem" data-productType="BINDCARD_AUTH">
            <input type="hidden" class="ignoreEmpy" name="customerFees[13].productType" value="BINDCARD_AUTH">
            <input type="hidden" class="ignoreEmpy fee-id" name="customerFees[13].id"  />
            <input type="hidden" class="ignoreEmpy" name="customerFees[13].customerNo" >
            <input type="hidden" class="ignoreEmpy" name="customerFees[13].status" >
            <div class="title-h2 fix">
              <span class="primary fl">绑卡认证</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select name="customerFees[13].feeType" styleClass="input-select fee-type" dictTypeId="FEE_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="customerFees[13].minFee" required amount minfee maxIgnoreEmpy="[name='customerFees[13].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
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
                        <input type="text" class="input-text fee" name="customerFees[13].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="customerFees[13].maxFee" required amount maxfee minIgnoreEmpy="[name='customerFees[13].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          
          <!-- 支付宝服务窗 -->
          <div class="productItem" data-productType="ALIPAYJSAPI">
            <input type="hidden" class="ignoreEmpy" name="customerFees[14].productType" value="ALIPAYJSAPI">
            <input type="hidden" class="ignoreEmpy fee-id" name="customerFees[14].id"  />
            <input type="hidden" class="ignoreEmpy" name="customerFees[14].customerNo" >
            <input type="hidden" class="ignoreEmpy" name="customerFees[14].status" >
            <div class="title-h2 fix">
              <span class="primary fl">支付宝服务窗</span>
              <a class="delProduct fr" href="javascript:void(0);">删除</a>
            </div>
            <div class="fix">
              <div class="col fl w-p-50">
                <div class="fix">
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">费率类型:</span>
                      <div class="input-wrap">
                        <dict:select name="customerFees[14].feeType" styleClass="input-select fee-type" dictTypeId="FEE_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="customerFees[14].minFee" required amount minfee maxIgnoreEmpy="[name='customerFees[14].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
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
                        <input type="text" class="input-text fee" name="customerFees[14].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="customerFees[14].maxFee" required amount maxfee minIgnoreEmpy="[name='customerFees[14].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
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



    <!-- 结算卡信息 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">商户结算卡信息</span>
      </div>
      <div class="content">
        <ul class="list">
          <li>
            <span class="info-title">账户名称:</span>
            <span class="info-value">${customerSettle.accountName }</span>
          </li>
         <%--  <li>
            <span class="info-title">银行编号:</span>
            <span class="info-value">${customerSettle.accountNo}</span>
          </li> --%>
          <li>
            <span class="info-title">开户行名称:</span>
            <span class="info-value">${customerSettle.openBankName}</span>
          </li>
          <li>
            <span class="info-title">账户类型:</span>
            <span class="info-value"><dict:write dictId="${customer.customerType }" dictTypeId="AGENT_TYPE"></dict:write></span>
          </li>
          <li>
            <span class="info-title">银行卡号:</span>
            <span class="info-value">${customerSettle.accountNo}</span>
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
            <li><a onclick="lookImg('${pageContext.request.contextPath}/findCustomerDocumentImg.action?fileName=FILE_BUSILICECERT&customerCert.customerNo=${customer.customerNo}', '企业营业执照 | 个人身份证正面', this);" href="javascript:void(0);"><span class="pre-text">企业营业执照</span> | <span class="pre-text">个人身份证正面</span></a></li>
            <li><a onclick="lookImg('${pageContext.request.contextPath}/findCustomerDocumentImg.action?fileName=FILE_TAXREGCERT&customerCert.customerNo=${customer.customerNo}', '企业营业执照 | 个人身份证正面', this);" href="javascript:void(0);"><span class="pre-text">企业税务登记证</span> | <span class="pre-text">个人身份证反面</span></a></li>
            <li><a onclick="lookImg('${pageContext.request.contextPath}/findCustomerDocumentImg.action?fileName=FILE_ORGANIZATIONCERT&customerCert.customerNo=${customer.customerNo}', '企业营业执照 | 个人身份证正面', this);" href="javascript:void(0);"><span class="pre-text">组织机构证</span> | <span class="pre-text">个人银行卡正面</span></a></li>
            <li><a onclick="lookImg('${pageContext.request.contextPath}/findCustomerDocumentImg.action?fileName=FILE_OPENBANKACCCERT&customerCert.customerNo=${customer.customerNo}', '企业营业执照 | 个人身份证正面', this);" href="javascript:void(0);"><span class="pre-text">银行开户许可证</span> | <span class="pre-text">个人银行卡反面</span></a></li>
            <li><a onclick="lookImg('${pageContext.request.contextPath}/findCustomerDocumentImg.action?fileName=FILE_IDCARD&customerCert.customerNo=${customer.customerNo}', '企业营业执照 | 个人身份证正面', this);" href="javascript:void(0);"><span class="pre-text">企业法人身份证</span> | <span class="pre-text">个人手持身份证</span></a></li>
          </ul>

        </div>
      </div>
    </div>






  </div>
  
  
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    // 初始化产品管理器
    product.init();
    $(document).ready(function() {
      // 更改费率类型
      $('.fee-type').change(function(event, ignore) {
        general.changeFeeType(this.value, $(this).closest('.productItem'));
      }).trigger('change', true);
      // 禁用产品
      product.disabledProduct();
    });

  </script>
  
  <c:if test="${customerFees != null && customerFees.size() > 0}">
    <c:forEach items="${customerFees }" var="fees" varStatus="status">
      <!-- 循环普通产品费率 -->
      <c:if test="${fees.productType.name() != 'REMIT' && fees.productType.name() != 'RECEIVE' }">
        <script>
          product.fillBaseValue(product.open('${fees.productType.name()}', null, true), {
            // 费率类型 RATIO=百分比, FIXED=固定
            '.fee-type': '${fees.feeType }',
            // 费率id
            '.fee-id': '${fees.id}',
            // 费率
            '.fee': '${fees.fee }',
            // 最低费率
            '.minfee': '${fees.minFee }',
            // 最高费率
            '.maxfee': '${fees.maxFee }',
            // mcc
            '[name="customer.mcc"]': '${customer.mcc}',
            // 路由模板
            '.router-template': '',
            // 商户编码
            '.customerNo': '${fees.customerNo}',
            // 费率状态
            '.fee-status': '${fees.status}'
          });
        </script>
      </c:if>
      <!-- 初始化代付费率 -->
      <c:if test="${fees.productType.name() == 'REMIT'}">
        <script>
          product.fillRemit(product.open('REMIT', null, true), {
            // 费率类型 RATIO=百分比, FIXED=固定
            '.fee-type': '${fees.feeType }',
            // 费率id
            '.fee-id': '${fees.id}',
            // 费率
            '.fee': '${fees.fee }',
            // 最低费率
            '.minfee': '${fees.minFee }',
            // 最高费率
            '.maxfee': '${fees.maxFee }',
            // 路由模板
            '.router-template': '',
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
            // 商户编码
            '.customerNo': '${fees.customerNo}',
            // 费率状态
            '.fee-status': '${fees.status}'
          });
        </script>
      </c:if>
      <!-- 初始化代收费率 -->
      <c:if test="${fees.productType.name() == 'RECEIVE'}">
        <script>
          product.fillRemit(product.open('RECEIVE', null, true), {
            // 费率类型 RATIO=百分比, FIXED=固定
            '.fee-type': '${fees.feeType }',
            // 费率id
            '.fee-id': '${fees.id}',
            // 费率
            '.fee': '${fees.fee }',
            // 最低费率
            '.minfee': '${fees.minFee }',
            // 最高费率
            '.maxfee': '${fees.maxFee }',
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
            // 商户编码
            '.customerNo': '${fees.customerNo}',
            // 费率状态
            '.fee-status': '${fees.status}'
          });
        </script>
      </c:if>
      
    </c:forEach>
  </c:if>
</body>
</html>
