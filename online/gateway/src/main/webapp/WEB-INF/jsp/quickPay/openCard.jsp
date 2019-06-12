<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/commons-include.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="renderer" content="webkit">
		<!--不要缓存-->
		<META HTTP-EQUIV="pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
		<META HTTP-EQUIV="expires" CONTENT="0">
		<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="black" name="apple-mobile-web-app-status-bar-style">
		<meta content="telephone=no" name="format-detection">
		<meta name="format-detection" content="telephone=no">
		<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<!-- uc强制竖屏 -->
		<meta name="screen-orientation" content="portrait">
		<!-- QQ强制竖屏 -->
		<meta name="x5-orientation" content="portrait">
		<!-- UC强制全屏 -->
		<meta name="full-screen" content="yes">
		<!-- QQ强制全屏 -->
		<meta name="x5-fullscreen" content="true">
		<!-- UC应用模式 -->
		<meta name="browsermode" content="application">
		<!-- QQ应用模式 -->
		<meta name="x5-page-mode" content="app">
		<!-- windows phone 点击无高光 -->
		<meta name="msapplication-tap-highlight" content="no">
		<!-- home screen app 全屏 -->
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta http-equiv="x-rim-auto-match" content="none">
		<!--样式初始化-->
		<link rel="stylesheet" href="${applicationScope.staticFilesHost}/gateway/css/quickPay/common.css" />
		<!--通用样式-->
		<link rel="stylesheet" href="${applicationScope.staticFilesHost}/gateway/css/quickPay/basic.css" />
		<!--页面样式-->
		<link rel="stylesheet" href="${applicationScope.staticFilesHost}/gateway/css/quickPay/payment_pay.css?author=zhupan&date=2017110717" />
		<!--页面自适应函数-->
		<script type="text/javascript" src="${applicationScope.staticFilesHost}/gateway/js/quickPay/response.js"></script>
		<!--时间-->
		<link rel="stylesheet" href="${applicationScope.staticFilesHost}/gateway/js/quickPay/DateSelector/DateSelector.css" />
		<script type="text/javascript" src="${applicationScope.staticFilesHost}/gateway/js/quickPay/DateSelector/DateSelector.js"></script>

		<title>开通快捷支付</title>
	</head>

	<body oncontextmenu="return false" onselectstart="return false">
		<header>
			<div class="header">
			<!-- <div class="header_title">
				<img src="image/back.png" />
				<span>开通快捷支付</span>
			</div> -->
            </div>
			<div class="sys-header theme-bg absolute">
				<div class="logo fl">
					<img class="toggle-drawerNav" src="${applicationScope.staticFilesHost}/gateway/image/quickPay/head-logo.png" alt="">
				</div>
				<div class="fr">
					<ul class="links fl">
						<li>
							<a target="_blank" href="javascript:void(0);">聚合运营管理系统</a>
						</li>
						<li>
							<a target="_blank" href="javascript:void(0);">API文档</a>
						</li>
						<li>
							<a target="_blank" href="javascript:void(0);">帮助</a>
						</li>
					</ul>
					<div class="control-panel fl">
						<div class="user-name f-13">
							<span>Hi, </span>
							<span>聚合支付有限公司</span>
						</div>
					</div>
				</div>

			</div>
		</header>
		<div class="se_title">我的订单信息</div>
		<form id="form" action="saveOpenCardInfo">
			<div class="sec loginContent">
				<section>
					<p class="se_title_fo"><img src="${applicationScope.staticFilesHost}/gateway/image/quickPay/warm.png" /> 请认真填写下面信息，用于完成订单支付功能</p>
					<div class="list">
						<div class="item">
							<div class="pull_left">商户名称 </div>
							<div class="pull_right"><input  type="text" readonly="readonly"  value="${customerName }" class="ipt_text" />  </div>
						</div>
						<div class="item">
							<div class="pull_left">订单金额 </div>
							<div class="pull_right"> <input type="text" readonly="readonly"  value="${amount }元" class="ipt_text" /> </div>
						</div>
						<input type="hidden" name="cardNo" id="accountNo" value="${bankCardNo }" />
						<input type="hidden" id="orderCode" name="orderCode" value="${tradeOrderCode }" />
						<input type="hidden" name="interfaceInfoCode" value="${interfaceCode }" />
						<input type="hidden" name="smsCodeType" id="smsCodeType" value="${smsCodeType }" />
						<input type="hidden" name="cardType" id="cardType" value="" />
					</div>

					<div class="list top_2" >
						<div class="item">
							<div class="pull_left">银行卡号</div>
							<div class="pull_right"> <input id="pay_card" name="pay_card" type="text" readonly="readonly" disabled="disabled"  value="${bankCardNo }" class="ipt_text" /></div>
						</div>
						<%--<div class="debat">--%>
						<%--<div class="item">--%>
							<%--<div class="pull_left">CVN2</div>--%>
							<%--<div class="pull_right"><input class="ipt_text hover" name="cvv" id="cvv" type="number" placeholder="卡背面末三位" oninput="if(value.length>3)value=value.slice(0,3)--%>
								<%--" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" pattern="[0-9]" /> </div>--%>
						<%--</div>--%>
						<%--<div class="item">--%>
							<%--<div class="pull_left">有效期</div>--%>
							<%--<div class="pull_right" id="time_validity">--%>
								<%--<i class="time_i">--%>
								<%--<input type="number" name="validityMonth" class="ipt_text time hover" oninput="if(value.length>2)value=value.slice(0,2)" id="date-selector-input" value=""   readonly="readonly" >月</input> &nbsp;&nbsp;/--%>
								<%--<input type="number" name="validityYear" oninput="if(value.length>2)value=value.slice(0,2)" class="ipt_text time hover" id="date-selector-inputs" value=""   readonly="readonly" >年</input>--%>
							    <%--</i>--%>

							<%--</div>--%>
							  <%--<i class="time_warm"><img src="${applicationScope.staticFilesHost}/gateway/image/quickPay/warm.png" />示例：09/15，前面输09，后面输入15</i>--%>
						<%--</div>--%>
						<%--</div>--%>
						<div class="item">
							<div class="pull_left">预留手机号 </div>
							<div class="pull_right"><input name="phone" id="phone" type="number" oninput="if(value.length>11)value=value.slice(0,11)" placeholder="银行预留手机号" value="" class="ipt_text hover" /></div>
						</div>
						

						<div class="item" id="NO_YLZF_CODE">
							<div class="pull_left" >输入验证码 
								<i class="ipt_pattern_i">
									<input class="ipt_pattern hover" id="parttom" type="number" name="verifyCode" placeholder="输入验证码" oninput="if(value.length>6)value=value.slice(0,6)"
								 		onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" pattern="[0-9]" />
								 </i>  
							</div>
							<div class="pull_right">
								<a class="btn_a" id="yanzheng">点击获取</a>
							</div>
						</div>
					</div>
					<div class="foot_btn"> <button class="submit_ok" id="subBtn" type="submit" value="提交">提交</button></div>
					<%-- <div class="wechat">
						<img src="${applicationScope.staticFilesHost}/gateway/image/quickPay/wechat.jpg" />
						<p>扫一扫关注我们官方微信，时刻查询您的交易记录</p> 
					</div> --%>
				</section>
				
				<footer>
					
					<div class="footer">
						<!--<input type="submit" value="提交" />-->
						<button class="submit_ok" id="phoneSubBtn" type="submit" value="提交">提交</button>
						<!--<a class="submit_ok"></a>-->
					</div>
				</footer>
			</div>

		</form>
		<!--装时间的容器-->
		<div id="targetContainer"></div>
		<div id="targetContainers"></div>
		
		<!--加载中-->
		<div id="loading" class="fullpage"><img src="${applicationScope.staticFilesHost}/gateway/image/quickPay/loading.gif" /></div>
		<!--错误弹框-->
		<div class="fullpage" id="errormdg" >
			<div class="errormsg">
				<p id="errormdgs"> 姓名与身份证不匹配</p>
			</div>
		</div>
		
		<!--jquary包-->
		<script type="text/javascript" src="${applicationScope.staticFilesHost}/gateway/js/quickPay/jquery-1.8.3.js"></script>
		<!--验证和表单提交js  只能在这里修改喔！！！-->
		<script type="text/javascript" src="${applicationScope.staticFilesHost}/gateway/js/quickPay/validate.js"></script>
		<!--错误消息js-->
		<script type="text/javascript" src="${applicationScope.staticFilesHost}/gateway/js/quickPay/commons.js"></script>
		<!--页面js-->
		<script type="text/javascript" src="${applicationScope.staticFilesHost}/gateway/js/quickPay/pay_payment.js"></script>
		<script>
			$(function() {
				var dom = $("#pay_card");
				var start = dom.val().substr(0, 4);
                var end = dom.val().substr(dom.val().length-4, dom.val().length);
                dom.val(start + "****" + end);
			});
			/*************************************input聚焦start*************************/
			$("input").css("display", "inline").val();
			$("input").click(function() {
				//moveEnd($(this).get(0));
				var t = $(this).val();
				$(this).val("").focus().val(t);
			})
			function moveEnd(obj) {
				obj.focus();
				var len = obj.value.length;
				if(document.selection) {
					var sel = obj.createTextRange();
					sel.moveStart('character', len); //设置开头的位置
					sel.collapse();
					sel.select();
				} else if(typeof obj.selectionStart == 'number' && typeof obj.selectionEnd == 'number') {
					obj.selectionStart = obj.selectionEnd = len;
				}

			}
			/*************************************input聚焦end******************************************/
			if($("html").width() >= 1024) {
				$(".time").attr("readonly",false);

			} 
			$('#date-selector-input').change(function() {
				var inputdata = $(this).val().replace(/\D/g, '');
				if(inputdata != '' && inputdata < 1) {
					inputdata = '01';
				}
				if(inputdata != '' && inputdata < 10 && inputdata.length<2) {
					inputdata = "0"+inputdata;
				}
				if(inputdata != '' && inputdata > 12) {
					inputdata = 12;
				}
				$(this).val(inputdata);
			});
                $("#date-selector-inputs").change(function(){
				//                    alert(1);
				var inputdata = $("#date-selector-inputs").val().replace(/\D/g, '');
				if(inputdata != '' && inputdata < 1 ) {
					
					inputdata = '00';
				}
				if(inputdata != '' && inputdata < 10 && inputdata.length<2 ) {
					inputdata = "0"+inputdata;
				}
				if(inputdata != '' && inputdata > 30) {
					inputdata = 30;
				}
				$("#date-selector-inputs").val(inputdata);
			});

			new DateSelector({
				input: 'date-selector-inputs', //点击触发插件的input框的id 
				container: 'targetContainers', //插件插入的容器id
				type: 0, //切换按钮
				param: [1, 0, 0, 0, 0], //设置['year','month','day','hour','minute'],1为需要，0为不需要,需要连续的1
				beginTime: [2000], //如空数组默认设置成1970年1月1日0时0分开始，如需要设置开始时间点，数组的值对应param参数的对应值。
				endTime: [2030], //如空数组默认设置成次年12月31日23时59分结束，如需要设置结束时间点，数组的值对应param参数的对应值。
				recentTime: [], //如不需要设置当前时间，被为空数组，如需要设置的开始的时间点，数组的值对应param参数的对应值。
				success: function(arr) {
					var vals=arr[0].toString();
						$("#date-selector-inputs").val(vals.substring(2,4));
					} //回调
			});
			new DateSelector({
				input: 'date-selector-input', //点击触发插件的input框的id 
				container: 'targetContainer', //插件插入的容器id
				type: 0, //切换按钮
				param: [0, 1, 0, 0, 0], //设置['year','month','day','hour','minute'],1为需要，0为不需要,需要连续的1
				beginTime: [01], //如空数组默认设置成1970年1月1日0时0分开始，如需要设置开始时间点，数组的值对应param参数的对应值。
				endTime: [12], //如空数组默认设置成次年12月31日23时59分结束，如需要设置结束时间点，数组的值对应param参数的对应值。
				recentTime: [], //如不需要设置当前时间，被为空数组，如需要设置的开始的时间点，数组的值对应param参数的对应值。
				success: function(arr) {
					var vals=arr[0];
				if(vals<10){
					vals="0"+vals;
				}
						$("#date-selector-input").val(vals);
					} //回调
			});
			
		</script>

	</body>

</html>