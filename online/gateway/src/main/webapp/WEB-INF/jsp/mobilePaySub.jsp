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
	<script type="text/javascript">
		$(function(){
			page_cg(150);
			$("#verifyButton").attr("disabled","disabled");
		});
		//获取短信验证码
        function page_cg(i) {
        	document.getElementById("verifyButton").value = "";
        	document.getElementById("verifyButton").value = "验证码已发送("+i+")";
            if (i > 0) {
                i--;
            }else {
                document.getElementById("verifyButton").disabled = 0;  //可用
                document.getElementById("verifyButton").value = "获取短信验证码";
                return;
            }
            window.setTimeout(function () { page_cg(i); }, 600);
        }

		function sendVerifyCode(){
			var payerMobNo = $("#payerMobNo").val();
			var merOrderId = $("#orderId").val();
			var interfaceCode = $("#interfaceCode").val();
			/* If(payerMobNo == ''){
				$("#hidLi").attr("style","display: '';");
				$("#hidError").text("支付手机号不能为空!");
				$("#hidError").fadeOut(1500);
				Return;
			}else{
				$("#hidLi").attr("style","display: '';");
				If(!(/^1[3|4|5|8]\d{9}$/.test(payerMobNo))){
					$("#hidError").text("支付手机号格式错误!");
					$("#payerMobNo").val("");
					Return;
		    	}
			} */

			var url="${pageContext.request.contextPath}/verifyCodeSend.htm?payerMobNo="+payerMobNo+"&merOrderId="+merOrderId+"&interfaceCode="+interfaceCode;
			$.ajax({
				type : "post",
				url : url,
				success : function(msg) {
					if (msg == 'success') {
						$("#verifyButton").attr("disabled","disabled");
						$("#hidSpan").text("获取验证码成功,请在五分钟内支付!");
						var i = 150;
						page_cg(i);
					}else{
						document.getElementById("verifyButton").disabled = 0;  //可用
						$("#hidSpan").text("获取验证码失败,重新发送!");
					}
				}
			});
		}
		function mySubmit(){
			var verifycode = $("#verifycode").val();
			verifycode = verifycode.replace(" ", "");
			if(verifycode == ''){
				alert("验证码不能为空");
				return false;
			}
			$("#mobilePaySub").submit();
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
		    <form action="authPay.htm" method="post" id="mobilePaySub">
		    	<input type="hidden" id="interfaceCode" name="interfaceCode" value="${interfaceCode}">
		    	<input type="hidden" id="orderId" name="orderId" value="${orderId}">
		    	<input type="hidden" id="forwardFlag" name="forwardFlag" value="mobile">
		        <div class="orderinfo_wrap">
		            <ul class="orderinfo_box">
		                <li class="title">订单信息</li>
		                <li>订单号<span class="fr">${orderId }</span></li>
		                <li>订单金额<span class="fr">${orderAmount}元</span></li>
					</ul>
		        </div>
		        <div class="payselect_wrap">
		            <ul class="payselect_box">
		                <li><p>手机号：</p>
		                    <input type="text" class="pay_input_middle" id="payerMobNo" value="${phone}">
		                </li>
		                <li><p>短信验证码：</p>
		                	<input type="text" class="pay_input_small" name="verifycode" id="verifycode">
		                	<input type="button" value="获取短信验证码" onclick="sendVerifyCode();" id="verifyButton" /><br>
		                	<span id="hidSpan"></span>
		                </li>
		            </ul>
		        </div>
		        <button type="button" class="red_btn" onclick="mySubmit();">确认支付</button>
		    </form>
	    </div>
    </div>
    <footer></footer>
</body>
</html>