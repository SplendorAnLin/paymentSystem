<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<html>
<head>
	<script type="text/javascript">
		function isopen(){
			var isopen = document.getElementById("isOpen").checked;
			if(isopen == true){
				$(".phoneTr").show();
			}else{
				$(".phoneTr").hide();
			}
		}
		function doSubmit(){
			if(!doValidate()){
				return;
			}
			var limitAmountSingle = $("#limitAmountSingle_s").val().replace(/,/ig, '');
			var limitAmountDay = $("#limitAmountDay_s").val().replace(/,/ig, '');
			$("#limitAmountSingle").val(limitAmountSingle);
			$("#limitAmountDay").val(limitAmountDay);
			var params=$("#dfSet").serialize();
			$.ajax({
				url:"dfSetUpdate.action",
				async:true,	//异步请求
				data:params,
				dataType:"text",
				type:"POST",
				success:function(data){
					var fn=function(){
						$("#dfSetQuery",window.parent.document).submit();
					};
					if(data == 'SUCCESS'){
						dialogMsg2("操作成功!",fn);
					}
					if(data == 'FAIL'){
						dialogMsg2("操作失败，系统异常",fn);
					}
				}
			});
		}

		/**表单验证**/
		function doValidate(){
			var flag=true;
			var v=$("#type").val();

			var reg1=/\S/;	//非空验证reg1.test(str)==false说明是空串或仅包含空白字符的串
			var reg2_1=/((^[1-9]\d*)|(^0))$/;	//自然数
			var reg2_2=/^[1-9]\d*\.\d+$/;		//正小数>=1
			var reg2_3=/^0\.\d+$/;				//正小数<1

			//var dom_serviceDeadLine=$("#serviceDeadLine");		//服务期限
			//var dom_statisticalCycle=$("#statisticalCycle");	//统计周期
			//var dom_lowerLimit=$("#lowerLimit");				//下限
			//var dom_fixedFee=$("#fixedFee");					//费率
			//var dom_upperLimit=$("#upperLimit");				//上限
			//var dom_limitAmountSingle=$("#limitAmountSingle");	//单笔交易限额
			//var dom_limitAmountDay=$("#limitAmountDay");		//日交易限额
			//var dom_limitCountDay=$("#limitCountDay");			//日交易笔数

			//var serviceDeadLine=dom_serviceDeadLine.val();
			//var statisticalCycle=dom_statisticalCycle.val();
			//var lowerLimit=dom_lowerLimit.val();
			//var fixedFee=dom_fixedFee.val();
			//var upperLimit=dom_upperLimit.val();
			//var limitAmountSingle=dom_limitAmountSingle.val();
			//var limitAmountDay=dom_limitAmountDay.val();
			//var limitCountDay=dom_limitCountDay.val();

			/* if(!reg1.test(serviceDeadLine)){
				flag=false;
				dom_serviceDeadLine.next().text("*必填");
				dom_serviceDeadLine.next().attr("style","color:red;");
			}else if(!reg2_1.test(serviceDeadLine)){
				flag=false;
				dom_serviceDeadLine.next().text("*无效");
				dom_serviceDeadLine.next().attr("style","color:red;");
			}else{
				dom_serviceDeadLine.next().attr("style","display:none;");
			} */
			/* if(reg1.test(statisticalCycle)){
				if(!reg2_1.test(statisticalCycle)){
					flag=false;
					dom_statisticalCycle.next().attr("style","color:red;");
				}else{
					dom_statisticalCycle.next().attr("style","display:none;");
				}
			}else{
				dom_statisticalCycle.next().attr("style","display:none;");
			} */
			/* if(reg1.test(lowerLimit)){
				dom_lowerLimit.next().text("*无效");
				if(!reg2_1.test(lowerLimit) && !reg2_2.test(lowerLimit) && !reg2_3.test(lowerLimit)){
					flag=false;
					dom_lowerLimit.next().attr("style","color:red;");
				}else{
					dom_lowerLimit.next().attr("style","display:none;");
				}
			}else{
				if(v=="RATIO"){
					dom_lowerLimit.next().text("*必填");
					if(!reg1.test(lowerLimit)){
						flag=false;
						dom_lowerLimit.next().attr("style","color:red;");
					}else{
						dom_lowerLimit.next().attr("style","display:none;");
					}
				}else{
					dom_lowerLimit.next().attr("style","display:none;");
				}
			}
			if(!reg1.test(fixedFee)){
				flag=false;
				dom_fixedFee.next().next().text("*必填");
				dom_fixedFee.next().next().attr("style","color:red;");
			}else if(!reg2_1.test(fixedFee) && !reg2_2.test(fixedFee) && !reg2_3.test(fixedFee)){
				dom_fixedFee.next().next().text("*无效");
				dom_fixedFee.next().next().attr("style","color:red;");
				flag=false;
			}else{
				dom_fixedFee.next().next().attr("style","display:none;");
			}
			if(reg1.test(upperLimit)){
				dom_upperLimit.next().text("*无效");
				if(!reg2_1.test(upperLimit) && !reg2_2.test(upperLimit) && !reg2_3.test(upperLimit)){
					flag=false;
					dom_upperLimit.next().attr("style","color:red;");
				}else{
					dom_upperLimit.next().attr("style","display:none;");
				}
			}else{
				if(v=="RATIO"){
					dom_upperLimit.next().text("*必填");
					if(!reg1.test(lowerLimit)){
						flag=false;
						dom_upperLimit.next().attr("style","color:red;");
					}else{
						dom_upperLimit.next().attr("style","display:none;");
					}
				}else{
					dom_upperLimit.next().attr("style","display:none;");
				}
			} */
			/* if(!reg1.test(limitAmountSingle)){
				flag=false;
				dom_limitAmountSingle.next().text("*必填");
				dom_limitAmountSingle.next().attr("style","color:red;");
			}else if(!reg2_1.test(limitAmountSingle) && !reg2_2.test(limitAmountSingle) && !reg2_3.test(limitAmountSingle)){
				flag=false;
				dom_limitAmountSingle.next().text("*无效");
				dom_limitAmountSingle.next().attr("style","color:red;");
			}else{
				dom_limitAmountSingle.next().attr("style","display:none;");
			}
			if(!reg1.test(limitAmountDay)){
				flag=false;
				dom_limitAmountDay.next().text("*必填");
				dom_limitAmountDay.next().attr("style","color:red;");
			}else if(!reg2_1.test(limitAmountDay) && !reg2_2.test(limitAmountDay) && !reg2_3.test(limitAmountDay)){
				flag=false;
				dom_limitAmountDay.next().text("*无效");
				dom_limitAmountDay.next().attr("style","color:red;");
			}else{
				dom_limitAmountDay.next().attr("style","display:none;");
			}
			if(!reg1.test(limitCountDay)){
				flag=false;
				dom_limitCountDay.next().text("*必填");
				dom_limitCountDay.next().attr("style","color:red;");
			}else if(!reg2_1.test(limitCountDay)){
				flag=false;
				dom_limitCountDay.next().text("*必填");
				dom_limitCountDay.next().attr("style","color:red;");
			}else{
				dom_limitCountDay.next().attr("style","display:none;");
			} */

			var isopen = document.getElementById("isOpen").checked;
			if(isopen == true){
				if($("#phone").val() == ''){
					alert("手机号码不能为空!");
					return false;
				}
				var regPhone = /^13|15|17|18|14[0-9]{9}$/;
				if(!regPhone.test($("#phone").val())){
					alert("手机号码输入有误!");
					return false;
				}
			}

			return flag;
		}

		/**alert提示框**/
		function dialogMsg2(info,fn) {
			$("#dialog-msg-info").html(info);
			$("#dialog-msg").dialog({
				resizable : false,
				height : 140,
				modal : true,
				close:function(){		//关闭后事件
					$(this).dialog("destroy");
					if(fn!=null){
						fn();
					}
				},
				buttons : {
						"确定" : function() {
							$(this).dialog("close");
						}
				}
			});
		}

		function fixedFeeChange(dom){
			var v=$(dom).val();
			$("#fixedFee").attr("name",v);
			if(v=="rate"){
				$("#type").val("RATIO");
			}else if(v=="fee"){
				$("#type").val("SINGLE");
			}
		}

		$(function(){
			$("#freezeDayForRisk").val("${dpayServiceConfig.freezeDayForRisk }");
			<c:forEach var="sat" items="${dpayServiceConfig.supportAccountTypes }">
				$("input[name=accountTypes]:checkbox").each(function(){
					if("${sat}"==$(this).val()){
						$(this).attr("checked",true);
					}
				});
			</c:forEach>
		});
	</script>
</head>
<body onload="isopen()">
	<div class="search_tit"><h2>代付设置修改</h2></div>
	<form id="dfSet">
		<input type="hidden" name="ownerId" value="${dpayServiceConfig.ownerId }"/>
		<input type="hidden" name="ownerRole" value="${dpayServiceConfig.ownerRole }"/>
		<table class="global_table" cellpadding="0" cellspacing="0">
			<tr>
				<th><center>开关设置</center></th>
				<td colspan="5">
					<table class="global_table" cellpadding="0" cellspacing="0">
						<tr>
							<th>是否开通代付复核：</th>
							<td>
								<input type="radio" value="true" onchange="isopen();" name="manualAudit" ${dpayServiceConfig.manualAudit?'checked="checked"':'' }/>不开通
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" id="isOpen" onchange="isopen();" value="false" name="manualAudit" ${dpayServiceConfig.manualAudit?'':'checked="checked"' }/>开通
							</td>
						</tr>
						<tr class="phoneTr">
							<th>接收代付复核密码手机号：</th>
							<td>
								<input type="text" id="phone" name="phone" value="${dpayServiceConfig.phone}"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

		<br>
		<center>
			<input type="button" class="global_btn"  value="提 交" onclick="doSubmit();"/>
			<input type="button" class="global_btn" value="返 回" onclick="javascript:history.go(-1);"/>
		</center>
		<br/>
	</form>
</body>
</html>