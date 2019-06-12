<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>代付密码</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pd-10">
  
  <div class="row">
    <div class="title-h1 fix">
      <span class="primary fl">代付复核密码修改</span>
    </div>
    <div class="content">
      <input type="hidden" id="msg" value="<%=request.getParameter("msg")%>">
      <form class="notification validator" id="complexPWDForm" action="dPayComplexPWDModifyUpdate.action" method="post" data-success="修改复核密码成功" data-fail="修改复核密码失败">
        <input type="hidden" id="flag" value="${serviceConfigBean.ownerId}" />
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">旧密码:</span>
              <div class="input-wrap">
                <input type="password" class="input-text" name="oldPassword" required rangelength='[6,18]' checkOriginal trigger='{"checkOriginal": 2}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">新密码:</span>
              <div class="input-wrap">
                <input type="password" class="input-text" placeholder="密码长度为6-18个字符" name="serviceConfigBean.complexPassword" required rangelength='[6,18]' notEqual="[name='oldPassword']" message='{"notEqual": "不能与原密码相同"}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">确认新密码:</span>
              <div class="input-wrap">
                <input type="password" class="input-text" placeholder="密码长度为6-18个字符" name="newPassword" required rangelength='[6,18]' equalTo="[name='serviceConfigBean.complexPassword']" message='{"equalTo": "与新密码不相同"}'>
              </div>
            </div>
          </div>
        </div>
        <div class="text-right" style="width: 280px;">
          <button class="btn">提交</button>
        </div>
      </form>
    </div>
  </div>


  <div class="row">
    <div class="title-h1 fix">
      <span class="primary fl">手机验证码复核设置</span>
    </div>
    <div class="content">
      <form class="validator stop-formeNnter" id="phoneCheckForm" action="xx.action" method="post">
        <input type="hidden" id="flag" value="${serviceConfigBean.ownerId}" />
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">复核手机号:</span>
              <div class="input-wrap">
                <span class="text-secondary"><s:property value="serviceConfigBean.phone" /></span>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">开通状态:</span>
              <s:if test="serviceConfigBean.usePhoneCheck!=null && serviceConfigBean.usePhoneCheck eq 'TRUE'">
                <!-- 已开通状态 -->
                <div class="input-wrap">
                  <input type="hidden" class="operation" id="operation" value="FALSE">
                  <i id="tips-phone" style="color: cornflowerblue" class="fa fa-info-circle v-m"></i>
                  <span class="mr-20 v-m text-secondary">已开通</span>
                  <a href="javascript:void(0);" class="btn btn-submit btn-trigger">关闭</a>
                </div>
              </s:if>
              <s:if test="serviceConfigBean.usePhoneCheck==null || serviceConfigBean.usePhoneCheck eq 'FALSE'">
                <!-- 未开通状态 -->
                <div class="input-wrap">
                  <input type="hidden" class="operation" id="operation" value="TRUE">
                  <i id="tips-phone" style="color: cornflowerblue" class="fa fa-info-circle v-m"></i>
                  <span class="mr-20 v-m text-secondary">未开通</span>
                  <a href="javascript:void(0);" class="btn btn-submit btn-trigger">开通</a>
                </div>
              </s:if>
            </div>
          </div>
          <div class="item" style="display:none;">
            <div class="input-area">
              <span class="label w-90">手机验证码:</span>
              <div class="input-wrap">
                <input type="password" class="input-text" placeholder="验证码长度为6个字符"  prompt="NextLineMessage" name="verify-code" required rangelength='[6,6]' dfComplexVerify >
              </div>
              <a id="sendVerifyCode" class="sendVerifyCode" href="javascript:void(0);">重新获取</a>
            </div>
          </div>
        </div>
        <div class="text-right btn-wrap" style="width: 280px;visibility: hidden;">
          <button class="btn disabled">提交</button>
        </div>
      </form>
    </div>
  </div>


  <div class="row">
    <div class="title-h1 fix">
      <span class="primary fl">代付自动审核设置</span>
    </div>
    <div class="content">
      <form class="validator stop-formeNnter" id="manualAuditForm" action="xx.action" method="post">
        <input type="hidden" id="flag" value="${serviceConfigBean.ownerId}" />
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">审核状态:</span>
              <s:if test="serviceConfigBean.manualAudit eq 'TRUE'">
                <!-- 自动状态 -->
                <div class="input-wrap">
                  <input type="hidden" class="operation" id="manualAudit" value="FALSE">
                  <i id="tips-manualAudit" style="color: cornflowerblue" class="fa fa-info-circle v-m"></i>
                  <span class="mr-20 v-m text-secondary">自动</span>
                  <a href="javascript:void(0);" class="btn btn-submit btn-trigger">人工</a>
                </div>
              </s:if>
              <s:if test="serviceConfigBean.manualAudit eq 'FALSE'">
                <!-- 人工状态 -->
                <div class="input-wrap">
                  <input type="hidden" class="operation" id="manualAudit" value="TRUE">
                  <i id="tips-manualAudit" style="color: cornflowerblue" class="fa fa-info-circle v-m"></i>
                  <span class="mr-20 v-m text-secondary">人工</span>
                  <a href="javascript:void(0);" class="btn btn-submit btn-trigger">自动</a>
                </div>
              </s:if>
            </div>
          </div>
          <div class="item" style="display:none;">
            <div class="input-area">
              <span class="label w-90">手机验证码:</span>
              <div class="input-wrap">
                <input type="password" class="input-text" placeholder="验证码长度为6个字符"  prompt="NextLineMessage" name="verify-code" required rangelength='[6,6]' manualAuditVerify >
              </div>
              <a id="sendVerifyCode" class="sendVerifyCode" href="javascript:void(0);">重新获取</a>
            </div>
          </div>
        </div>
        <div class="text-right btn-wrap" style="width: 280px;visibility: hidden;">
          <button class="btn disabled">提交</button>
        </div>
      </form>
    </div>
  </div>




  <!-- 手机复核设置重要提示 -->
  <div id="tip-phone-content" style="width: 300px;display:none;" class="pd-10 tips">
    <h1>此功能可提高企业账户资金安全。</h1>
    <p class="text-secondary">开通功能后，在审核代付订单时，需要输入手机验证码及复核密码。</p>
  </div>
  
  <!-- 代付自动审核重要提示 -->
  <div id="tip-manualAudit-content" style="width: 300px;display:none;" class="pd-10 tips">
    <p class="text-secondary">此功能可提高企业账户资金安全。开通功能后，代付订单无需手动通过。</p>
  </div>
  
  
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    // 检测原密码是否正确
    Compared.add('checkOriginal', function(val, params, ele, ansyFn) {
      Api.customer.checkOldPassword(val, function(status) {
        ansyFn(Compared.toStatus(status == true, '原密码错误'));
      });
    });

    var complexForm = $('#complexPWDForm');
    
    // 挂接表单结果提示钩子
    complexForm.data('receiver-hook', function(msg, closeDialog, iframe) {
      var msg = utils.iframeFind(iframe, '#msg').val();
      closeDialog(msg == 'success');
      window.location.reload();
    });

    $(document).ready(function() {
      // 获取表单验证对象实例 
      var formValidator = window.getValidator(complexForm);
      formValidator.options.onCustomize = function() {
      	if ($('#flag').val() == '') {
      		Messages.error('修改复核密码失败, 未开通代付!');
      		return false;
      	}
      	$(document.body).showLoadImage(null, true);
      	return true;
      };
    });
    
    
    // 开通状态下拉提示
    $('#tips-phone').Dropdown({
      target: '#tip-phone-content',
      direction: 'down',
      trigger: 'mouseenter',
      autoClose: true,
      style: ['Dropdown-on', 'Dropdown-off']
    });
    $('#tips-manualAudit').Dropdown({
      target: '#tip-manualAudit-content',
      direction: 'down',
      trigger: 'mouseenter',
      autoClose: true,
      style: ['Dropdown-on', 'Dropdown-off']
    });
    
    // 初始化手机验证码复核管理器
    window.dfComplex.init($('#phoneCheckForm'));
    
    // 初始化代付自动审核设置管理器
    window.dfManualAudit.init($('#manualAuditForm'));
   
  </script>
</body>
</html>
