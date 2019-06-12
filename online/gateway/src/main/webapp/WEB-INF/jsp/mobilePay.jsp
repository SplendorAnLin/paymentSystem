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
    <title>支付页面</title>
    <link type="text/css" rel="stylesheet" href="${applicationScope.staticFilesHost}${requestScope.contextPath}/css/mobileBase.css" />
	<link type="text/css" rel="stylesheet" href="${applicationScope.staticFilesHost}${requestScope.contextPath}/css/mobileStyle.css" />

	<script type="text/javascript">
		//获取短信验证码
        function page_cg(i) {
        	document.getElementById("verifyButton").value = "";
        	document.getElementById("verifyButton").value = "获取短信验证码("+i+")";
            if (i > 0) {
                i--;
            }else {
                document.getElementById("verifyButton").disabled = 0;  //可用
                document.getElementById("verifyButton").value = "获取短信验证码";
                return;
            }
            window.setTimeout(function () { page_cg(i) }, 600);
        }

		function sendVerifyCode(){
			var payerMobNo = $("#payerMobNo").val();
			var merOrderId = $("#hidMerOrderId").val();
			if(payerMobNo == ''){
				$("#hidLi").attr("style","display: '';");
				$("#hidError").text("支付手机号不能为空!");
				$("#hidError").fadeOut(1500);
				return;
			}else{
				$("#hidLi").attr("style","display: '';");
				if(!(/^1[3|4|5|8]\d{9}$/.test(payerMobNo))){
					$("#hidError").text("支付手机号格式错误!");
					$("#payerMobNo").val("");
					return;
		    	}
			}

			var url="${pageContext.request.contextPath}/sendVerifyCode.htm?payerMobNo="+payerMobNo+"&merOrderId="+merOrderId;
			$.ajax({
				type : "post",
				url : url,
				success : function(msg) {
					if (msg == 'success') {
						$("#verifyButton").attr("disabled","disabled");
						$("#hidSpan").text("获取验证码成功,请在十分钟内支付!");
						var i = 150;
						page_cg(i);
					}else{
						document.getElementById("verifyButton").disabled = 0;  //可用
						$("#hidSpan").text("获取验证码失败,重新发送!");
					}
				}
			});
		}

		//表单提交
		function mySubmit(){
			var CVN2 = $("#CVN2").val();
			var certNo = $("#certNo").val();
			var ValDate = $("#ValDate").val();
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
			if(ValDate == ""){
				msg = "有效期不能为空!";
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
			if(verifycode == ""){
				msg = "短信验证码不能为空!";
			}

			if(msg == ""){
				$("#mobileForm").submit();
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
		    <form:form action="mobilePay.htm" method="post" modelAttribute="mobileInfoBean" id="mobileForm">
		        <div class="orderinfo_wrap">
		            <ul class="orderinfo_box">
		                <li class="title">订单信息</li>
		                <li>订单编号<span class="fr">${merOrderId }</span></li>
		                <li>订单金额<span class="fr">${orderInfo.amount }元</span></li>
		                <li>下单时间<span class="fr"><fmt:formatDate value="${orderInfo.orderTime }" pattern="yyyy-MM-dd"/></span></li>
		            </ul>
		            <input type="hidden" name="merOrderId" value="${merOrderId }" id="hidMerOrderId"/>
		            <input type="hidden" name="amount" value="${orderInfo.amount }" />
		            <input type="hidden" name="forwardFlag" value="mobile">
		        </div>
		        <div class="payselect_wrap">
		            <ul class="payselect_box">
		                <li class="title">使用信用卡支付</li>
	                	<li style="display: none;" id="hidLi"><span id="hidError" style="color: red;" ></span></li>
		                <li><p>银行卡号：</p>
		                    <input type="num" class="pay_input" name="bankCardNo" id="bankCardNo">
		                </li>
		                <li><p>卡有效期：</p>
		                    <input type="num" class="pay_input_middle" name="ValDate" id="ValDate">
		                    <p class="tips_font">银行卡正面有效期，如09月12年则填1209</p>
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
		                <li><p>短信验证码：</p>
		                	<input type="text" class="pay_input_small" name="verifycode" id="verifycode">
		                	<input type="button" value="获取短信验证码" onclick="sendVerifyCode();" id="verifyButton" /><br>
		                	<span id="hidSpan"></span>
		                </li>
		            </ul>
		        </div>
		        <button class="red_btn" type="button" onclick="mySubmit();">下一步</button>
			</form:form>
	    </div>
	  </div>
    <footer></footer>
</body>
</html>