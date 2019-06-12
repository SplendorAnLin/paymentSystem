<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>新增代收配置</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 4em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator ajaxFormNotification" action="receiveServiceConfigAdd.action" method="post" id="form" prompt="DropdownMessage"  data-success="新增成功" data-fail="新增失败">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">用户编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="receiveConfigInfoBean.ownerId" required checkReceiveConfig trigger='{"checkReceiveConfig": 2}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">用户名称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="userName" id="userName"  required readonly placeholder="请填写用户编号">
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
              <span class="label w-90">IP:</span>
              <div class="input-wrap">
                <input type="text" class="input-text fee" name="receiveConfigInfoBean.custIp">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">单日次最大笔数:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="receiveConfigInfoBean.singleBatchMaxNum" required digits>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">单笔最大金额:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="receiveConfigInfoBean.singleMaxAmount" required amount>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">日限额:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="receiveConfigInfoBean.dailyMaxAmount" required amount minIgnoreEmpy="[name='receiveConfigInfoBean.singleMaxAmount']" message='{"minIgnoreEmpy": "低于单笔最大金额"}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">是否效验授权:</span>
              <div class="input-wrap switch-wrap">
              	<dict:radio name="receiveConfigInfoBean.isCheckContract" dictTypeId="YN"></dict:radio>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">状态:</span>
              <div class="input-wrap switch-wrap">
                <dict:radio name="receiveConfigInfoBean.status" dictTypeId="ALL_STATUS"></dict:radio>
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
  <script>

    var userName = $('#userName');
    // 检测用于代收配置是否已存在
    Compared.add('checkReceiveConfig', function(value, params, element, callback) {
      Api.boss.checkReceiveConfig(value, function(exists) {
      	if (exists == 'false') {
      		Api.boss.accInfo(value, function(accInfo) {
      			if (accInfo && accInfo.fullName) {
      				userName.val(accInfo.fullName);
      			} else {
      				userName.val('');
      			}
      		});
      	} else {
      		userName.val('');
      	}
        callback(Compared.toStatus(exists == 'false', '用户已存在代收配置!'));
      });
    });

  </script>
</body>
</html>
