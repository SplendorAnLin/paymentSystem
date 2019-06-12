<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp"%>
<%@ include file="include/commons-include.jsp"%>
<!doctype HTML>
<head>
   	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="支付,网关,在线支付">
	<meta http-equiv="description" content="支付网关">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>输入手机号</title>
    <link type="text/css" rel="stylesheet" href="${applicationScope.staticFilesHost}${requestScope.contextPath}/css/mobileBase.css" />
	<link type="text/css" rel="stylesheet" href="${applicationScope.staticFilesHost}${requestScope.contextPath}/css/mobileStyle.css" />
	<script type="text/javascript">
		function loadBankInfo(cardType){
			$.ajax({
				type : "post",
				async : false,
				url : "${pageContext.request.contextPath }/findInterfaceProvider.htm?cardType=" + cardType,
				success : function(providerInfos) {
					var interfaceProviderCodes = providerInfos.split("|");
					if (interfaceProviderCodes.length > 0) {
						for ( var i = 0; i < interfaceProviderCodes.length; i++) {
							var interfaceProviderCode = interfaceProviderCodes[i].split(",");
							$("#bankCode").append("<option value='"+interfaceProviderCode[0]+"'>"+interfaceProviderCode[1]+"</option>");
						}
					}
				}
			});
		}

		function mySubmit(){
			var cardType = $("#cardType").val();
			var selectInput = document.getElementById("bankCode");
			var bankCode = selectInput.value;

			if(cardType == ''){
				alert("卡类型不能为空");
				return;
			}
			if(bankCode == ''){
				alert("支付银行不能为空");
				return;
			}

			//支付接口编号
			var interfaceCode = "MOBILE_" + bankCode + "-" + cardType;
			$("#hidInterfaceCode").val(interfaceCode);

			//表单提交
			$("#mobileGateway").submit();
		}
	</script>
</head>
<body>
	<div>
	    <header>
	        <div class="nav_wrap">
	            <div class="nav_box">
	                <button class="return_btn" type="button" onclick="javascript:history.go(-1);">返回</button>
	                <title>移动支付</title>
	            </div>
	        </div>
	    </header>
	    <div class="context_wrap">
		    <form action="pay.htm" method="post" id="mobileGateway">
		        <div class="orderinfo_wrap">
		            <ul class="orderinfo_box">
		                <li class="title">订单信息</li>
		                <li>订单编号<span class="fr">${result.outOrderId}</span></li>
		                <li>商户名称<span class="fr">${result.partnerName }</span></li>
		                <li>订单金额<span class="fr">${result.amount }元</span></li>
					</ul>
	                <input type="hidden" name="tradeOrderCode" value="${result.tradeOrderCode }" />
					<input type="hidden" name="outOrderID" value="${result.outOrderId }" />
					<input type="hidden" name="amount" value="${result.amount }" />
					<input type="hidden" name="apiCode" value="${result.apiCode }" />
					<input type="hidden" name="versionCode" value="${result.versionCode }" />
					<input type="hidden" name="interfaceCode" id="hidInterfaceCode" />
		        </div>
		        <div class="payselect_wrap">
		            <ul class="payselect_box">
		                <li class="title">使用银行卡支付</li>
		                <li><p>卡类型</p>
		                    <select class="pay_select" onchange="loadBankInfo(this.value);" id="cardType" name="cardType">
		                        <option value="">请选择卡类型</option>
		                        <option value="CREDIT_CARD">信用卡</option>
		                    </select>
		                </li>
		                <li><p>选择银行</p>
		                    <select class="pay_select" id="bankCode" name="bankCode">
		                        <option value="">请选择银行名称</option>
		                    </select>
		                </li>
		            </ul>
		        </div>
		        <button type="button" class="red_btn" onclick="mySubmit();">下一步</button>
		    </form>
	    </div>
    </div>
    <footer></footer>
</body>
</html>