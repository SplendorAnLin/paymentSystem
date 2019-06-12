<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport" content="width=device-width">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>支付-收银台</title>
<link rel="stylesheet" type="text/css" href="mobleGateway/public2.css">
</head>

<body>


	<form>
		<section style="display: none;" >
			<header class="header">
				<img src="mobleGateway/logo789.png">
				<p>
					<span>会</span><span>支</span><span>付</span><span>会</span><span>生</span><span>活</span>
				</p>
			</header>
			<div class="content">

				<div class="pay-panel">
					<div class="order-line">
						<label>商户名称</label>
						<div class="value" id="productName">${customerName}</div>
					</div>
					<div class="order-line">
						<label>金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;额</label>
						<div class="value" id="amount">
							<span>￥</span>${amount}</div>
					</div>
				</div>
				<button type="button" class="btn" id="paynow" onclick="pay();">立即支付</button>

			</div>

		</section>
	</form>
	<div class="modelback"></div>
	
	<input type="hidden" id="pay_info" value='${result.params.pay_info}'/>
	<input type="hidden" id="code_url" value='${result.params.code_url}'/>
	<input type="hidden" id="payAmount" value='${amount}'/>
	<input type="hidden" id="customerName" value='${customerName}'/>
	<input type="hidden" id="typeFlag" value='${typeFlag}'/>
	<script type="text/javascript" src="mobleGateway/utils.js"></script>
	<script type="text/javascript" src="mobleGateway/mobpex-js-sdk.js"></script>
	<script type="text/javascript" src="mobleGateway/ap.js"></script>
	<script>
		function pay() {
			var typeFlag = document.getElementById("typeFlag").value;
			if (typeFlag == "ALIPAY") {
				alipayJsapi();
			} else {
				onBridgeReady();
			}
		}
	
		function alipayJsapi() {
			var pay_info = document.getElementById("code_url").value;
			window.location.href= pay_info;
		}
		
		function onBridgeReady() {
			var pay_info = eval('(' + document.getElementById("pay_info").value + ')');
			if (typeof WeixinJSBridge == "undefined") {
				if (document.addEventListener) {
					document.addEventListener('WeixinJSBridgeReady', onBridgeReady,
							false);
				} else if (document.attachEvent) {
					document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
					document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
				}
			}
			
			WeixinJSBridge.invoke('getBrandWCPayRequest', {
				"appId":pay_info.appId, //公众号名称，由商户传入     
				"timeStamp":pay_info.timeStamp, //时间戳，自1970年以来的秒数     
				"nonceStr":pay_info.nonceStr, //随机串     
				"package":pay_info.package,
				"signType":pay_info.signType, //微信签名方式：     
				"paySign":pay_info.paySign //微信签名
			}, function(res) {
				if (res.err_msg == "get_brand_wcpay_request:ok") {
					var amountStr = document.getElementById("payAmount").value;
					var customerName = encodeURI(encodeURI(document.getElementById("customerName").value));
					var paystatus = encodeURI(encodeURI("支付成功！"));
					//交易成功
					window.location.href = "wxJsapiSuccess.htm?payStatus="+paystatus+"&payAmount="+amountStr+"&customerName="+customerName;
				}
			});
		}
		window.onload=function(){
			pay();
		}
	</script>
</body>
</html>