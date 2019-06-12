<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="format-detection" content="telephone=no" />
		<link type="text/css" rel="stylesheet" href="${applicationScope.staticFilesHost}${pageContext.request.contextPath}/css/bootstrap.min.css">
		<link type="text/css" rel="stylesheet" href="${applicationScope.staticFilesHost}${pageContext.request.contextPath}/css/bootstrap-theme.min.css">
		<link type="text/css" rel="stylesheet" href="${applicationScope.staticFilesHost}${pageContext.request.contextPath}/css/mobileGateway.css">
		<script type="text/javascript" src="${applicationScope.staticFilesHost}/js/jquery/jquery-1.10.2.min.js"></script>
		<script type="text/javascript">
			// 全局变量contextPath，存放着当前应用名
	    	var contextPath = "${pageContext.request.contextPath }";
		</script>
		<script src="${applicationScope.staticFilesHost}${pageContext.request.contextPath}/js/mobileGateway.js"></script>
</head>
<body>
		<div id="mainContent" style="height:156px">
			<div class="wrapper">
				<div class="custInfo">
					<div class="custLogo"></div>
					<div class="custName">
						${tradeBean.customerName }
						<input type="hidden" id="hidCustomerName" value="${tradeBean.customerName }"/>
					</div>
				</div>
			</div>
			<div class="wrapper">
				<div class="content" style="height:15rem" >
					<div class="shuru2 paddingleft2 margin_top20">
						付款金额
					</div>
					<div class="shuru2 paddingleft1 margin_top10" style="line-height:6rem; font-size:4.5rem;">
						￥<input type="text" class="" id="amont" placeholder="0.00" readOnly />
					</div>
				</div>
			</div>
		</div>
		<div class="navbar-fixed-bottom">
			<table class="keyboardTable "  border="40px" ><!--navbar-fixed-bottom -->
				<tr>
					<td class="keyboardTd" ontouchstart="enterValue(1, this)" ontouchend="continuousEnterValue()">
						1
					</td>
					<td class="keyboardTd" ontouchstart="enterValue(2, this)" ontouchend="continuousEnterValue()">
						2
					</td>
					<td class="keyboardTd" ontouchstart="enterValue(3, this)" ontouchend="continuousEnterValue()">
						3
					</td>
					<td class="keyboardTd delButton" rowspan="2" ontouchstart="enterValue('del', this)" ontouchend="continuousEnterValue()">

					</td>
				</tr>
				<tr>
					<td class="keyboardTd" ontouchstart="enterValue(4, this)" ontouchend="continuousEnterValue()">
						4
					</td>
					<td class="keyboardTd" ontouchstart="enterValue(5, this)" ontouchend="continuousEnterValue()">
						5
					</td>
					<td class="keyboardTd" ontouchstart="enterValue(6, this)" ontouchend="continuousEnterValue()">
						6
					</td>

				</tr>
				<tr>
					<td class="keyboardTd" ontouchstart="enterValue(7, this)" ontouchend="continuousEnterValue()">
						7
					</td>
					<td class="keyboardTd" ontouchstart="enterValue(8, this)" ontouchend="continuousEnterValue()">
						8
					</td>
					<td class="keyboardTd" ontouchstart="enterValue(9, this)" ontouchend="continuousEnterValue()">
						9
					</td>
					<td class="keyboardTd nextTd" rowspan="2" id="nextBtn" ontouchend="continuousEnterValue()" ontouchstart="nextBtnTouch()">
						支付
					</td>
				</tr>
				<tr>
					<td class="keyboardTd" ontouchstart="enterValue('.', this)" ontouchend="continuousEnterValue()">
						.
					</td>
					<td class="keyboardTd" colspan="2" ontouchstart="enterValue(0, this)" ontouchend="continuousEnterValue()">
						0
					</td>

				</tr>
			</table>
		</div>
		<!-- Modal -->
		<div id="amontValidate" class="myModal-dialog-hide">
			<div id="validateDialog" class="myModal-content">
		    	<div class="modal-body align-center">
		        	输入金额有误
				</div>
		      <div class="modal-footer align-center myModal-footer">
		        <button type="button" id="closeBtn" class="myHideBtn"onclick="reEnter()">重新输入</button>
		      </div>
		    </div>
		</div>

		<div id="confirm" class="myModal-dialog-hide" style="width:80%;display: none;">
			<div id="validateDialog" class="myModal-content">
		    	<div class="modal-body">
		        	<div class="text_center" style="color:red;font-size:18px">
		        		支付确认
		        	</div>
		        	<div class="" style="margin-top:1rem">请在微信内完成支付，如果已支付成功，请点击"支付完成"。</div>
				</div>
		      	<div class="modal-footer myModal-footer">
		      		<div class="text_center margin_bottom10 margin_top10">
		      			<input type="button" class="btn failBtn" onclick="cancel();" value="取消"/>

						<input type="button" class="rePayBtn btn" value="支付完成" onclick="complete();"/>
		      		</div>
		      	</div>
		    </div>
		</div>
		<div id="payFail" class="myModal-dialog-hide" style="display: none;">
			<div id="validateDialog" class="myModal-content">
		    	<div class="modal-body pay_fail " style="margin-top:1rem">
		    		<div class="pay_fail_logo"></div>
		        	<div class="pay_fail_info">
		        		您本次支付尚未完成，请重新支付；如确认在微信内完成支付，请联系商户处理。
		        	</div>
				</div>
		      	<div class="modal-footer myModal-footer">
		      		<div class="text_center margin_bottom10 margin_top10">
		      			<input type="button" class="btn rePayBtn" onclick="closeDialogId('payFail')" value="确定"/>
		      		</div>
		      	</div>
		    </div>
		</div>

		<div style="display: none;">
			<form id="tradeForm" action="${pageContext.request.contextPath }/mobilePay/trade.htm">
				<input type="hidden" name="openId" value="${tradeBean.openId }"/>
				<input type="hidden" name="offlineCustomerName" value="${tradeBean.customerName }"/>
				<input type="hidden" name="businessFlag" value="${tradeBean.businessFlag }"/>
				<input type="hidden" name="notifyURL" value="${tradeBean.notifyURL }"/>
				<input type="text" name="apiCode" value="directPay" /> <br/>
				<input type="text" name="versionCode" value="1.0" /> <br/>
				<input type="text" name="inputCharset" id="inputCharset" value="UTF-8" /> <br/><br/>
				<input type="text" id="partner" name="partner" value="${tradeBean.customerNo }"/> <br/>
				<input type="text" name="buyer" value="000000000" /> <br/>
				<input type="text" name="buyerContactType" value="email" /> <br/>
				<input type="text" name="buyerContact" value="rui.wang@lefu8.com" /> <br/><br/>
				<input id="outOrderId" type="text" name="outOrderId"/> <br/>
				<input type="text" name="amount" id="amount"/> <br/>
				<input type="text" name="currency" value="CNY" /> <br/>
				<input type="text" name="paymentType" value="ALL"/> <br/>
				<input type="text" name="interfaceCode" value="${tradeBean.routeCode }"/> <br/>
				<input type="text" name="retryFalg" value="TRUE" /> <br/>
				<input type="text" id="submitTime" name="submitTime"/> <br/>
				<input type="text" id="timeout" name="timeout" value="3H"/> <br/><br/>
				<input type="text" name="signType" value="MD5" /> <br/>
				<input type="text" id="sign" value="" name="sign"/><br/>
			</form>
		</div>
	</body>
</html>