<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>代付密钥</title>
  <%@ include file="../include/header.jsp"%>
  <style>
    .key-box {
      width: 600px;
      word-break: break-all;
      margin: 10px 0 0 0;
      padding: 10px;
      border: 1px dotted #785;
      background: #f5f5f5;
    }
  </style>
</head>
<body class="pd-10" onload="changeKey();">
  
  <div class="row">
    <div class="title-h1 fix">
      <span class="primary fl">代付商户密钥查询/更新</span>
    </div>
    <div class="content">
      <form action="dPayKeyUpdate.action" method="post" onsubmit="return checkForm();" style="display: inline-block;">
        <input type="hidden" id="keys" />
        <input type="hidden" id="msg" value="${errorsMsg}">
        <input type="hidden" id="prk" name="serviceConfigBean.privateKey">
        <input type="hidden" id="puk" name="serviceConfigBean.publicKey">
        <div class="fix mt-10">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">商户编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" id="customerNo"name="serviceConfigBean.ownerId" value="${sessionScope.auth.customerno}" readonly>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">原密钥:</span>
              <div class="input-wrap">
                <div class="key-box" id="opukDiv"></div>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">新密钥:</span>
              <div class="input-wrap">
                <div class="key-box" id="npukDiv"></div>
              </div>
            </div>
          </div>
        </div>
        <div class="text-right">
          <a class="btn" href="javascript:void(0);" onclick="changeKey()" >换一个</a>
          <button class="btn">提交</button>
        </div>
      </form>
    </div>
  </div>
  
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    $(document).ready(function() {
      var msg = $("#msg").val();
      if(msg == "success"){
        new window.top.Alert("密钥更新成功！", '代付密钥');
      }
      if(msg == "failed"){
      	new window.top.Alert("密钥更新失败！", '代付密钥');
      }
    });
    
    // 商户编号
    var customerNo = $.trim($("#customerNo").val());
    // 私钥
    var prk = $("#prk");
    // 公钥
    var puk = $("#puk");
    // 当前密钥div
    var oldPublicKey = $("#opukDiv");
    // 新密钥div
    var newPublicKey = $("#npukDiv");
    
    // 换一个密钥
    function changeKey() {
      if(customerNo == ''){
      	new window.top.Alert("商户编号为空，生成新密钥失败！");
        return;
      }
      if ($.trim(prk.val()) == '' || $.trim(puk.val()) == '') {
      	Api.customer.changeDfKeys(customerNo, function(result) {
      		if (result == '') {
      			new window.top.Alert("您暂未开通代付！", '代付密钥');
      			return;
      		}
          var res = result.split('_');
          var oldKey = oldPublicKey.html($.trim(res[0])).html();
          oldPublicKey.html(oldKey.replace(/(\\r|\\n)/g, ''));
          var newkey = newPublicKey.html($.trim(res[1])).html();
          newPublicKey.html(newkey.replace(/(\\r|\\n)/g, ''));
      		
      		puk.val($.trim(res[1]));
      		prk.val($.trim(res[2]));
      	});
      } else {
      	Api.customer.dfChangeKeys(function(result) {
      		var res = result.split('_');
      		var newkey = newPublicKey.html($.trim(res[1])).html();
      		newPublicKey.html(newkey.replace(/(\\r|\\n)/g, ''));
          puk.val($.trim(res[1]));
          prk.val($.trim(res[0]));
      	});
      }
    	
    }
    
    // 表单验证
    function checkForm() {
      if(customerNo ==  ''){
      	new window.top.Alert("商户编号不能为空，请输入正确的商户编号");
        return false;
      }
      
      if($.trim(puk.val()) == '' || $.trim(prk.val()) == '' ){
      	new window.top.Alert("参数信息异常，请刷新后再试");
        return false;
      }
      return true;
    }
    
    
  </script>
</body>
</html>
