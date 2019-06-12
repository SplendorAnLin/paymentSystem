<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>账户信息</title>
  <%@ include file="../include/header.jsp"%>
  <style>
    .detail .key-value {
      max-width: 120px;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      display: inline-block;
      vertical-align: middle;
    }
    .w-p-40 {
      width: 40%;
    }
    .w-p-60 {
      width: 60%
    }
  </style>
</head>
<body>
  
  <div style="padding: 10px;">
    <!-- 商户基本信息 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">商户基本信息</span>
      </div>
      <div class="content detail mt-20" style="width: 900px;line-height: 38px;">
        <div class="col w-p-40">
          <div class="row">
            <span class="label">手机号:</span>
            <span class="value">${customer.phoneNo }</span>
          </div>
          <div class="row">
            <span class="label">商户全称:</span>
            <span class="value">${customer.fullName }</span>
          </div>
          <div class="row">
            <span class="label">商户简称:</span>
            <span class="value">${customer.shortName }</span>
          </div>
          <div class="row">
            <span class="label">商户简称:</span>
            <span class="value">${customer.shortName }</span>
          </div>
          <div class="row">
            <span class="label">商户编号:</span>
            <span class="value">${customer.customerNo }</span>
          </div>
          <c:if test="${customer.agentNo !=null && customer.agentNo!='' }">
            <div class="row">
              <span class="label">上级服务商编号:</span>
              <span class="value">${customer.agentNo }</span>
            </div>
          </c:if>
          <div class="row">
            <span class="label">商户类型:</span>
            <span class="value"><dict:write dictId="${customer.customerType }" dictTypeId="ACCOUNT_TYPE"></dict:write></span>
          </div>
          <c:forEach items="${CustomerKey }" var="key">
            <c:if test="${key.keyType == 'MD5' }">
              <div class="row">
                <span class="label">秘钥类型(交易):</span>
                <span class="value"><span class="key-value">${key.key}</span><a href="javascript:void(0);" onclick="showKey(this, '秘钥类型(交易)');" data-key="${key.key}" ><i class="fa fa-eye"></i></a></span>
              </div>
            </c:if>
            <c:if test="${key.keyType == 'AES' }">
              <div class="row">
                <span class="label">秘钥类型(AES):</span>
                <span class="value"><span class="key-value">${key.key}</span><a href="javascript:void(0);" onclick="showKey(this, '秘钥类型(AES)');" data-key="${key.key}" ><i class="fa fa-eye"></i></a></span>
              </div>
            </c:if>
            <c:if test="${key.keyType == 'DES3' }">
              <div class="row">
                <span class="label">秘钥类型(DES3):</span>
                <span class="value"><span class="key-value">${key.key}</span><a href="javascript:void(0);" onclick="showKey(this, '秘钥类型(DES3)');" data-key="${key.key}" ><i class="fa fa-eye"></i></a></span>
              </div>
            </c:if>
            <c:if test="${key.keyType == 'PASSWORD' }">
              <div class="row">
                <span class="label">秘钥类型(PASSWORD):</span>
                <span class="value"><span class="key-value">${key.key}</span><a href="javascript:void(0);" onclick="showKey(this, '秘钥类型(PASSWORD)');" data-key="${key.key}" ><i class="fa fa-eye"></i></a></span>
              </div>
            </c:if>
          </c:forEach>
          <c:if test="${not empty receiveConfigInfoBean }">
            <div class="row">
              <span class="label">秘钥类型(代收):</span>
              <span class="value"><span class="key-value">${receiveConfigInfoBean.publicCer}</span><a href="javascript:void(0);" onclick="showKey(this, '秘钥类型(代收)');" data-key="${receiveConfigInfoBean.publicCer}" ><i class="fa fa-eye"></i></a></span>
            </div>
          </c:if>
        </div>
        <div class="col w-p-60">
          <div class="row">
            <span class="label">保证金:</span>
            <span class="value">${customer.cautionMoney }</span>
          </div>
          <div class="row">
            <span class="label">入账周期:</span>
            <span class="value">T+${Cycle }</span>
          </div>
          <div class="row">
            <span class="label">地址:</span>
            <span class="value">${customer.addr }</span>
          </div>
          <div class="row">
            <span class="label">开户行:</span>
            <span class="value">${customerSettle.openBankName }</span>
          </div>
          <div class="row">
            <span class="label">开户名:</span>
            <span class="value">${customerSettle.accountName }</span>
          </div>
          <div class="row">
            <span class="label">银行账号:</span>
            <span class="value">${customerSettle.accountNo }</span>
          </div>
          <div class="row">
            <span class="label">账户创建时间:</span>
            <span class="value"><fmt:formatDate value="${customer.createTime }" pattern="yyyy-MM-dd" /></span>
          </div>
          <c:if test="${not empty serviceConfigBean }">
            <div class="row">
              <span class="label">秘钥类型(代付):</span>
              <span class="value"><span class="key-value">${serviceConfigBean.publicKey}</span><a href="javascript:void(0);" onclick="showKey(this, '秘钥类型(代付)');" data-key="${serviceConfigBean.publicKey}" ><i class="fa fa-eye"></i></a></span>
            </div>
          </c:if>
        </div>


      </div>
    </div>

    <!-- 费率信息 -->
    <div class="row mt-20">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">费率信息</span>
      </div>
      <div class="content detail">
      
        <c:if test="${customerFees != null && customerFees.size() > 0 }">
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
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${customerFees}" var="fees" varStatus="i">
                    <tr>
                      <td><dict:write dictId="${fees.status }" dictTypeId="SERVICE_PROVIDER_STATUS_COLOR"></dict:write></td>
                      <td><dict:write dictId="${fees.productType }" dictTypeId="BF_SHARE_PAYTYPE"></dict:write></td>
                      <td><dict:write dictId="${fees.feeType }" dictTypeId="FEE_TYPE"></dict:write></td>
                      <td>${fees.fee}</td>
                      <td>${fees.maxFee }</td>
                      <td>${fees.minFee }</td>
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
