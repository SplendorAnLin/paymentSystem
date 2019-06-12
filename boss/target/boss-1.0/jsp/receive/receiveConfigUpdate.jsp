<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>修改代收配置</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 4em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator ajaxFormNotification" action="receiveConfigUpdate.action" method="post" id="form" prompt="DropdownMessage"  data-success="修改成功" data-fail="修改失败">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">用户编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="receiveConfigInfoBean.ownerId" value="${receiveConfigInfoBean.ownerId  }" readonly>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">域名:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="receiveConfigInfoBean.domain" value="${receiveConfigInfoBean.domain }">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">IP:</span>
              <div class="input-wrap">
                <input type="text" class="input-text fee" name="receiveConfigInfoBean.custIp" value="${receiveConfigInfoBean.custIp }">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">单日次最大笔数:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="receiveConfigInfoBean.singleBatchMaxNum" required digits value="${receiveConfigInfoBean.singleBatchMaxNum }">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">单笔最大金额:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="receiveConfigInfoBean.singleMaxAmount" required amount value="<fmt:formatNumber groupingUsed="false" currencySymbol="" type="currency" value="${receiveConfigInfoBean.singleMaxAmount }" />">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">日限额:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="receiveConfigInfoBean.dailyMaxAmount" value="<fmt:formatNumber groupingUsed="false" currencySymbol="" type="currency" value="${receiveConfigInfoBean.dailyMaxAmount }" />" required amount minIgnoreEmpy="[name='receiveConfigInfoBean.singleMaxAmount']" message='{"minIgnoreEmpy": "低于单笔最大金额"}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">是否效验授权:</span>
              <div class="input-wrap switch-wrap">
              	<dict:radio value="${receiveConfigInfoBean.isCheckContract }"  name="receiveConfigInfoBean.isCheckContract" dictTypeId="YN"></dict:radio>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">状态:</span>
              <div class="input-wrap switch-wrap">
                <dict:radio value="${receiveConfigInfoBean.status }" name="receiveConfigInfoBean.status" dictTypeId="ALL_STATUS"></dict:radio>
              </div>
            </div>
          </div>


        </div>
        <div class="text-center mt-10 hidden">
          <button class="btn btn-submit">新 增</button>
        </div>
      </form>
    </div>
  </div>
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
