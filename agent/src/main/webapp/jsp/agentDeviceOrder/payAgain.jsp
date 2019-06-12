<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html>
<head>
<title>再次付款</title>
</head>
<body style="padding: 32px 54px;">

	<div>
		<p class="msg">订单金额：<span class="amount text-secondary">${total }</span> 元</p>
    	<form style="display:none;" action="${url }" method="post" id="signForm"  target="_blank" data-success="订单创建成功" data-success="订单创建失败" >
			<input type="hidden" name="apiCode" value="YL-PAY" />
			<input type="hidden" name="inputCharset" id="inputCharset" value="UTF-8" />
			<input type="hidden" id="partner" name="partner" value="${msg}"/>
			<!-- 订单号 -->
			<input id="outOrderId" class="outOrderId" type="hidden" value="${outOrderId }" name="outOrderId"/>
			<input type="hidden" name="currency" value="CNY" />
			<input type="hidden" name="retryFalg" value="TRUE" />
			<!-- 提交时间 -->
			<input type="hidden" id="submitTime" name="submitTime" value="${customerPayNo }" />
			<input type="hidden" name="redirectUrl" value="http://pay.feiyijj.com/boss/pageCallBack.action?msg=" />
			<input type="hidden" name="notifyUrl" value="http://pay.feiyijj.com/boss/callback.action" />
			<input type="hidden" id="timeout" name="timeout" value="1D"/>
			<input type="hidden" name="productName" value="水牌采购"/>
			<input type="hidden" name="payType" id="payType" value="ALL"/>
			<input type="hidden" name="bankCardNo"/>
			<input type="hidden" name="accMode" value="GATEWAY" /><br/>
			<input type="hidden" name="wxNativeType" value="PAGE" />
			<input type="hidden" name="signType" value="MD5" />
			<!-- 效验码 -->
			<input type="hidden" id="sign" value="${sign }" name="sign" />
			<!-- 订单金额 -->
			<input type="hidden" name="amount" value="${total }" id="transAmount"/>
			<input type="submit" class="btn-submit" value="确认支付" />
		</form>
	</div>
  
  
  
	<%@ include file="../include/bodyLink.jsp"%>
	<script>
	  $('#signForm').submit(function() {
		  var dialog = window.dialogIframe;
		  setTimeout(function() {
			  dialog.close(null, true);
		  }, 1500);
	  });
	</script>
</body>
</html>