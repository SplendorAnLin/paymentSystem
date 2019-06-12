<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp"%>
<%@ include file="include/commons-include.jsp"%>
<!doctype HTML>
<head>
   	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="聚合支付,网关,在线支付">
	<meta http-equiv="description" content="聚合支付网关">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>聚合支付网关</title>
    <link type="text/css" rel="stylesheet" href="${applicationScope.staticFilesHost}${requestScope.contextPath}/css/mobileBase.css" />
	<link type="text/css" rel="stylesheet" href="${applicationScope.staticFilesHost}${requestScope.contextPath}/css/mobileStyle.css" />
	<style type="text/css">
	.context_wrap {
		width: 320px;
		min-height: 900px;
		margin: 0 auto;
		margin-top: 60px;
	}
    </style>
	<script type="text/javascript">

		//表单提交
		function mySubmit(){
			var CVN2 = $("#CVN2").val();
			var certNo = $("#certNo").val();
			var valYear = $("#ValYear").val();
			var valMonth = $("#ValMonth").val();
			var payerName = $("#payerName").val();
			var bankCardNo = $("#bankCardNo").val();
			var payerMobNo = $("#payerMobNo").val();
			var verifycode = $("#verifycode").val();

			var msg = "";
			if(CVN2 == ""){
				msg = "CVN2码不能为空!";
			}else if(CVN2.length > 3){
				msg = "CVN2码长度超限!";
			}
			if(certNo == ""){
				msg = "身份证号不能为空!";
			}
			if(valYear == ""){
				msg = "有效期年份不能为空!";
			}
			if(valMonth == ""){
				msg = "有效期月份不能为空!";
			}
			if(payerName == ""){
				msg = "开户姓名不能为空!";
			}
			if(bankCardNo == ""){
				msg = "银行卡号不能为空!";
			}
			if(payerMobNo == ""){
				msg = "支付手机号不能为空!";
			}else{
				if(!(/^1[3|4|5|8]\d{9}$/.test(payerMobNo))){
					msg = "支付手机号格式错误!";
					$("#payerMobNo").val("");
		    	}
			}
// 			if(verifycode == ""){
// 				msg = "短信验证码不能为空!";
// 			}
			var cardType = $("#cardType").val();

			if(cardType == ''){
				msg = "卡类型不能为空";
			}

			//支付接口编号
			//var interfaceCode = "AUTHPAY_" + bankCode + "-" + cardType;
			//$("#hidInterfaceCode").val(interfaceCode);

			if(msg == ""){
				$("#mobileGateway").submit();
			}else{
				$("#hidLi").attr("style","display: '';");
				$("#hidError").text(msg);
				$("#hidError").fadeOut(1500);
			}
		}
	</script>
</head>
<body>
	<div>
	    <header>
	        <div class="nav_wrap">
	            <div class="nav_box">
	                <button class="return_btn" type="button" onclick="javascript:history.go(-1);">返回</button>
	                <title>聚合移动支付</title>
	            </div>
	        </div>
	    </header>
	    <div class="context_wrap">
		    <form:form action="authSale.htm" method="post" id="mobileGateway" modelAttribute="authSaleBean">
		        <div class="orderinfo_wrap">
		            <ul class="orderinfo_box">
		                <li class="title">订单信息</li>
		                <li>订单编号<span class="fr">${merOrderId}</span></li>
		                <li>订单金额<span class="fr">${orderInfo.amount }元</span></li>
					</ul>
					<input type="hidden" name="amount" value="${orderInfo.amount }" />
					<input type="hidden" name="interfaceCode" id="hidInterfaceCode" value="AUTHPAY_EPAY_100001"/>
					<input type="hidden" name="forwardFlag" value="mobile">
					<input type="hidden" name="merOrderId" value="${merOrderId }"/>
		        </div>
		        <div class="payselect_wrap">
		            <ul class="payselect_box">
		                <li class="title">使用银行卡支付</li>
		                 <li><p>银行卡号：</p>
		                    <input type="num" class="pay_input" name="bankCardNo" id="bankCardNo">
		                </li>
		                <li><p>卡有效期：</p>
		                	<span>
			                    <input type="num" class="pay_input_small" name="ExpireMonth" id="ValMonth" size="2">月
			                    <input type="num" class="pay_input_small" name="expireYear" id="ValYear" size="4">年
		                    </span>
		                    <p class="tips_font">银行卡正面有效期，如09月12年则月填09，年填2012</p>
		                </li>
		                <li><p>卡验证码：</p>
		                    <input type="num" class="pay_input_middle" name="CVN2" id="CVN2" >
		                    <p class="tips_font">银行卡背面签名条处末三位数字</p>
		                </li>
		                <li><p>姓名：</p>
		                    <input type="text" class="pay_input" name="payerName" id="payerName">
		                </li>
		                <li><p>身份证号：</p>
		                    <input type="text" class="pay_input" name="certNo" id="certNo">
		                </li>
		                 <li><p>手机号：</p>
		                    <input type="text" class="pay_input_middle" name="payerMobNo" id="payerMobNo" >
		                </li>
		            </ul>
		        </div>
		        <button type="button" class="red_btn" onclick="mySubmit();">下一步</button>
		    </form:form>
	    </div>
    </div>
    <footer></footer>
</body>
</html>