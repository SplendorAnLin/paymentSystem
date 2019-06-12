<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>修改商户信息</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 900px; padding:10px">
  
  <form class="validator notification" id="customerAdd" method="post" enctype="multipart/form-data" action="updateCustomerAction.action" prompt="DropdownMessage"  data-success="修改成功" data-fail="修改失败" style="padding: 0.4em;">
    <input type="hidden" id="bankCode" readonly="readonly" name="customerSettle.bankCode" value="${customerSettle.bankCode }">
    <input type="hidden" class="organization" name="customer.organization" />

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
                  <span class="label w-90">商户编号:</span>
                  <div class="input-wrap">
                    <span class="text-secondary">${customer.customerNo}</span>
                    <input type="hidden" class="input-text" name="customer.customerNo" value="${customer.customerNo}" readonly>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">商户状态:</span>
                  <div class="input-wrap">
                    <span class="text-secondary"><dict:write dictId="${customer.status }" dictTypeId="CUSTOMER_STATUS_COLOR"></dict:write></span>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">商户全称:</span>
                  <div class="input-wrap">
                    <span class="text-secondary">${customer.fullName}</span>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">所属服务商:</span>
                  <div class="input-wrap">
                    <span class="text-secondary">${customer.agentNo}</span>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">商户联系电话:</span>
                  <div class="input-wrap">
                    <span class="text-secondary">${customer.phoneNo}</span>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">商户类型:</span>
                  <div class="input-wrap">
                    <span class="text-secondary"><dict:select name="customer.customerType" styleClass="input-select accountType" shield="OEM" value="${customer.customerType }" dictTypeId="AGENT_TYPE"></dict:select></span>
                  </div>
                </div>
              </div>
      <div class="item">
                <div class="input-area">
                  <span class="label w-90 cardText">身份证号:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="customer.idCard" value="${customer.idCard }" required idCard>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col fl w-p-50">
            <div class="fix">
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">商户简称:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="customer.shortName" value="${customer.shortName}" required checkCustomerSortName trigger='{"checkCustomerSortName": 2}' rangelength="[2,16]">
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">商户联系人:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="customer.linkMan" value="${customer.linkMan}" required rangelength="[2,16]">
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">所在省:</span>
                  <div class="input-wrap">
                    <select class="input-select prov" name="customer.province" required>
                    </select>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">所在市:</span>
                  <div class="input-wrap">
                    <select class="input-select city" name="customer.city" required>
                    </select>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">商户地址:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="customer.addr" value="${customer.addr }" required rangelength="[1,40]">
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">邮箱:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="customer.eMail" value="${customer.EMail}" requiredNot email>
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
                <input type="text" class="input-text open-cate" name="customerCert.legalPerson" value="${customerCert.legalPerson}" disabled required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">企业网址:</span>
              <div class="input-wrap">
                <input type="text" class="input-text open-cate" name="customerCert.enterpriseUrl" value="${customerCert.enterpriseUrl}" disabled required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">客服电话:</span>
              <div class="input-wrap">
                <input type="text" class="input-text open-cate" name="customerCert.consumerPhone" value="${customerCert.consumerPhone}" disabled required telPhone>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">ICP备案号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text open-cate" name="customerCert.icp" value="${customerCert.icp}" disabled required>
              </div>
            </div>
          </div>
        </div>
        <div class="col fl">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">经营范围:</span>
              <div class="input-wrap">
                <input type="text" class="input-text open-cate" name="customerCert.businessScope" value="${customerCert.businessScope}" disabled required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">企业信用码:</span>
              <div class="input-wrap">
                <input type="text" class="input-text open-cate" name="customerCert.enterpriseCode" value="${customerCert.enterpriseCode}" disabled required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">经营地址:</span>
              <div class="input-wrap">
                <input type="text" class="input-text open-cate" name="customerCert.businessAddress" value="${customerCert.businessAddress}" disabled required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">营业期限:</span>
              <div class="input-wrap">
                <input type="text" name="customerCert.validStartTime" class="input-text double-input date-start"  date-fmt="yyyy-MM-dd" value="<fmt:formatDate value="${customerCert.validStartTime}" pattern="yyyy-MM-dd" />" disabled required>
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="customerCert.validEndTime" class="input-text double-input date-end"  date-fmt="yyyy-MM-dd" value="<fmt:formatDate value="${customerCert.validEndTime}" pattern="yyyy-MM-dd" />" disabled required>
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
                <a class="btn" href="javascript:void(0);" noWidth="true" onclick="lookImg('${pageContext.request.contextPath}/findCustomerDocumentImg.action?fileName=FILE_ATT&customerCert.customerNo=${customer.customerNo}','补充材料', this);" >查看原始图片</a>
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
            <input type="hidden" class="ignoreEmpy" name="customerFees[0].productType" value="B2C">
            <input type="hidden" class="ignoreEmpy fee-id" name="customerFees[0].id"  />
            <input type="hidden" class="ignoreEmpy customerNo" name="customerFees[0].customerNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="customerFees[0].status">
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
              <div class="col fl  w-p-50">
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
            <input type="hidden" class="ignoreEmpy fee-id" name="customerFees[1].id"  />
            <input type="hidden" class="ignoreEmpy customerNo" name="customerFees[1].customerNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="customerFees[1].status">
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
              <div class="col fl  w-p-50">
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
            <input type="hidden" class="ignoreEmpy fee-id" name="customerFees[2].id"  />
            <input type="hidden" class="ignoreEmpy customerNo" name="customerFees[2].customerNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="customerFees[2].status">
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
              <div class="col fl  w-p-50">
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
            <input type="hidden" class="ignoreEmpy fee-id" name="customerFees[3].id"  />
            <input type="hidden" class="ignoreEmpy customerNo" name="customerFees[3].customerNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="customerFees[3].status">
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
              <div class="col fl  w-p-50">
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
            <input type="hidden" class="ignoreEmpy customerNo" name="customerFees[4].customerNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="customerFees[4].status">
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
              <div class="col fl  w-p-50">
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
            <input type="hidden" class="ignoreEmpy customerNo" name="customerFees[5].customerNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="customerFees[5].status">
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
              <div class="col fl  w-p-50">
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
            <input type="hidden" class="ignoreEmpy customerNo" name="customerFees[6].customerNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="customerFees[6].status">
            <input type="hidden" class="ignoreEmpy" name="serviceConfigBean.ownerId">
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
                        <dict:select name="customerFees[6].feeType" styleClass="input-select fee-type" dictTypeId="FEE_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="customerFees[6].minFee" minfee required amount maxIgnoreEmpy="[name='customerFees[6].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
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
                        <input type="text" class="input-text fee" name="customerFees[6].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="customerFees[6].maxFee" maxfee  required amount minIgnoreEmpy="[name='customerFees[6].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
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
              <div class="col fl  w-p-50">
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
                        <input type="text" class="input-text minfee" name="customerFees[7].minFee" minfee required amount minfee maxIgnoreEmpy="[name='customerFees[7].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
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
                        <input type="text" class="input-text fee" name="customerFees[7].fee" required amount fee>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最高费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text maxfee" name="customerFees[7].maxFee" maxfee required amount maxfee minIgnoreEmpy="[name='customerFees[7].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
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
            <input type="hidden" class="ignoreEmpy fee-id" name="customerFees[8].id"  />
            <input type="hidden" class="ignoreEmpy customerNo" name="customerFees[8].customerNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="customerFees[8].status">
            <input type="hidden" class="ignoreEmpy" name="receiveConfigInfoBean.ownerId" >
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
                        <dict:select name="customerFees[8].feeType" styleClass="input-select fee-type" dictTypeId="FEE_TYPE"></dict:select>
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="input-area">
                      <span class="label w-90">最低费率:</span>
                      <div class="input-wrap">
                        <input type="text" class="input-text minfee" name="customerFees[8].minFee" minfee required amount maxIgnoreEmpy="[name='customerFees[8].maxFee']" message='{"maxIgnoreEmpy": "超过最大费率"}'>
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
              <div class="col fl  w-p-50">
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
                        <input type="text" class="input-text maxfee" name="customerFees[8].maxFee" maxfee required amount minIgnoreEmpy="[name='customerFees[8].minFee']" message='{"minIgnoreEmpy": "低于最低费率"}'>
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
            <input type="hidden" class="ignoreEmpy customerNo" name="customerFees[9].customerNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="customerFees[9].status">
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
              <div class="col fl  w-p-50">
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
            <input type="hidden" class="ignoreEmpy customerNo" name="customerFees[10].customerNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="customerFees[10].status">
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
              <div class="col fl  w-p-50">
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
            <input type="hidden" class="ignoreEmpy customerNo" name="customerFees[11].customerNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="customerFees[11].status">
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
              <div class="col fl  w-p-50">
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
            <input type="hidden" class="ignoreEmpy customerNo" name="customerFees[12].customerNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="customerFees[12].status">
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
              <div class="col fl  w-p-50">
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
            <input type="hidden" class="ignoreEmpy customerNo" name="customerFees[13].customerNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="customerFees[13].status">
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
              <div class="col fl  w-p-50">
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
            <input type="hidden" class="ignoreEmpy customerNo" name="customerFees[14].customerNo">
            <input type="hidden" class="ignoreEmpy fee-status" name="customerFees[14].status">
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
              <div class="col fl  w-p-50">
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

    <!-- 商户结算卡信息 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">商户结算卡信息</span>
      </div>
      <div class="content">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账户类型:</span>
              <div class="input-wrap">
                <dict:select value="${customerSettle.customerType }" styleClass="input-select" dictTypeId="ACCOUNT_TYPE" id="accType" name="customerSettle.customerType"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">入账周期:</span>
              <div class="input-wrap">
                <dict:select value="${cycle }" name="cycle" id="customerCycle" styleClass="input-select" dictTypeId="ENTRY_CYCLE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账户名称:</span>
              <div class="input-wrap">
                <input type="text" name="customerSettle.accountName" value="${customerSettle.accountName }" id="cardName" class="input-text"  required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">银行卡号:</span>
              <div class="input-wrap">
                <input type="text" name="customerSettle.accountNo" value="${customerSettle.accountNo }" id="accountNo" class="input-text"  required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">开户行名称:</span>
              <div class="input-wrap mr-10">
                <input type="text" class="input-text" style="width: 280px;" name="customerSettle.openBankName" value="${customerSettle.openBankName }"  id="openBankName" placeholder="请输入地区、分行支行名称等关键词进行匹配" required>
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
              <input name="taxRegCert" class="btn-file-input hidden" type="file" prompt="message" requiredNot checkImageSize  accept="image/jpeg,image/jpg,image/png">
            </span>
          </li>
          <li>
            <span class="ico"><i class="fa fa-exclamation-circle"></i></span>
            <span class="ib w-200">组织机构证 | 个人银行卡正面</span>
            <span class="operating">
              <a class="btn" href="javascript:void(0);"  onclick="spliceImgPath('FILE_ORGANIZATIONCERT', '组织机构证 | 个人银行卡正面', this)" 
               >查看原始图片</a>
              <a class="btn btn-tr-file" href="javascript:void(0);">修改</a>
              <input name="organizationCert" class="btn-file-input hidden" type="file" prompt="message" requiredNot  accept="image/jpeg,image/jpg,image/png">
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


    <div class="text-center mt-10 hidden">
      <button class="btn btn-submit">提 交</button>
      <input type="reset" class="btn clear-form" value="重 置" style="padding: 0px; width: 77px;">
    </div>

  </form>

  
  
  
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script src="${pageContext.request.contextPath}/js/lib/citySelect.js"></script>
  <script>
    // 初始化城市下拉列表
    $('form').dictCitySelect({
      prov: '${customer.province}',
      city: '${customer.city}'
    });
    
  //商户类型切换
    $('.accountType').change(function() {
      // 更改入网账户类型
      general.changeAccountType(this.value);
    }).trigger('change');
    
    function spliceImgPath(fileName, title, btn) {
      var path = '${pageContext.request.contextPath}/findCustomerDocumentImg.action?fileName=' + fileName + '&customerCert.customerNo=${customer.customerNo}&msg=' + Math.random();
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
    $(window).load(function() {
      var customerForm = $('#customerAdd');
      var submitBtn = $('.btn-submit');
      // 获取表单验证对象实例 
      var customValidator = window.getValidator(customerForm);
      // 初始化卡识别验证
      general.SettleCardValidator(customerForm, customValidator, true);
      // 启用验证调试
      customValidator.options.debug = false;
      // 验证钩子
      customValidator.options.onSubmit = function(status) {
        if (status !== Compared.EnumStatus.fail) {
          submitBtn.loading();
        } else {
          submitBtn.ending();
        }
        
      };
      customValidator.options.onCustomize = function() {
        
        // 必须开通一个业务产品
        if (product.products.length === 0) {
          new window.top.Alert('必须开通一个业务!', '警告');
          submitBtn.ending();
          return false;
        }

        // 没有开通代付则弹询问框确认
        if (utils.indexOf(product.products, 'REMIT') == -1) {
          new window.top.Confirm('未开通代付许多功能将无法使用<br/>确定不开通代付吗?', '警告', function() {
            customValidator.submit(true);
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
            customValidator.element(rote);
          if (minfee.get(0).enumstatus === Compared.EnumStatus.fail)
            customValidator.element(minfee);
          if (maxfee.get(0).enumstatus === Compared.EnumStatus.fail)
            customValidator.element(maxfee);
        });
      }).trigger('change', true);

    });

    // 增加商户费率/分润等验证
    general.addCustomerVerify();
    // 初始化网点管理器
    shopMannage.init();
    // 初始化产品管理器
    product.init();
    // 根据上级费率进行验证
    general.feesForParent(function() {
      // console.log('切换费率验证');
    });
  </script>
  
  
 <!-- 网点管理器初始化 -->
 <c:if test="${shopList != null && shopList.size() > 0}">
  <c:forEach items="${shopList }" var="shop" varStatus="i">
    <script>
      shopMannage.add({
        shopName: '${shop.shopName }',
        printName: '${shop.printName }',
        bindPhoneNo: '${shop.bindPhoneNo }'
      }, '${shop.shopNo}');
    </script>
  </c:forEach>
 </c:if>
 <c:if test="${shopList == null || shopList.size() <= 0}">
  <script>
    shopMannage.destroy();
  </script>
 </c:if>

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
