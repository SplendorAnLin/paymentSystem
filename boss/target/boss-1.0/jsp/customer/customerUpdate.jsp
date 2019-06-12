<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>修改商户信息</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 640px; padding:0.2em;">
  
  
  <form class="validator notification" action="updateCustomerAction.action" method="post" prompt="DropdownMessage"  enctype="multipart/form-data" data-success="修改成功" data-fail="修改失败" style="padding: 0.4em;">

    <input type="hidden" id="customerCert_customerNo" name="customerCert.customerNo" value="${customerCert.customerNo}" />
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
                  <span class="label w-90">商户状态:</span>
                  <div class="input-wrap">
                    <dict:select name="customer.status" value="${customer.status }" styleClass="input-select" dictTypeId="CUSTOMER_STATUS"></dict:select>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">商户全称:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="customer.fullName" value="${customer.fullName}" required rangelength="[2,16]">
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">所属服务商:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="customer.agentNo" value="${customer.agentNo}" readonly>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">商户联系电话:</span>
                  <div class="input-wrap">
                    <input type="text" name="customer.phoneNo" class="input-text" value="${customer.phoneNo}" readonly>
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
                  <span class="label w-90">商户类型:</span>
                  <div class="input-wrap">
                    <dict:select name="customer.customerType" value="${customer.customerType }" styleClass="input-select accountType" dictTypeId="AGENT_TYPE"></dict:select>
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
                  <span class="label w-90">商户编号:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="customer.customerNo" value="${customer.customerNo}" readonly>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">商户简称:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="customer.shortName" value="${customer.shortName}" required rangelength="[2,16]">
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
                    <select class="input-select prov" name="customer.province">
                    </select>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">所在市:</span>
                  <div class="input-wrap">
                    <select class="input-select city" name="customer.city">
                    </select>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">地址:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="customer.addr" value="${customer.addr}" required rangelength="[1,40]">
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">邮箱:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="customer.eMail" value="${customer.EMail}" required email>
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
                <input type="text" name="customerCert.validStartTime" class="input-text double-input date-start"  date-fmt="yyyy-MM-dd" value="${customerCert.validStartTime}" disabled required>
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="customerCert.validEndTime" class="input-text double-input date-end"  date-fmt="yyyy-MM-dd" value="${customerCert.validEndTime}" disabled required>
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


    <div class="text-center mt-10 hidden">
      <button class="btn btn-submit">修 改</button>
    </div>

  </form>
  
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script src="${pageContext.request.contextPath}/js/lib/citySelect.js"></script>
  <script>
  
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
  </script>
</body>
</html>
