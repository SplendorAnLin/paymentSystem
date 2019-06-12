<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<html>
<head>
	<style type="text/css">
		.ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }  
		.ui-timepicker-div dl { text-align: left; }  
		.ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; }  
		.ui-timepicker-div dl dd { margin: 0 10px 10px 65px; }  
		.ui-timepicker-div td { font-size: 90%; }  
		.ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; } 
	</style>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/datePicker.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui-timepicker-addon.js"></script>
	<script type="text/javascript">
		function getCustomerImg(){
			if(getInfo("#ownerId","customerNo","getCustomerImg.action") == true){
				$("#img").show();
			}else{
				$("#img").hide();
				alert('未获取到商户代付资质');
			}
		}
		function showImg(){
			var url = "${pageContext.request.contextPath}/showImg.action?customerNo=" + $("#ownerId").val() ;
			$("#customerImg").attr("src",url);
			$("#customerDiv").show();
		}
		function isopen(){
			var isopen = document.getElementById("isOpen").checked;
			if(isopen == true){
				$(".phoneTr").show();
			}else{
				$(".phoneTr").hide();
			}
			$("#customerDiv").draggable();
		}
		
		function colseImg(){
			$("#customerDiv").hide();
		}
		
		function radioChange(dom){
			$("#div1").attr("style","display:none;");
			$("#div2").attr("style","display:none;");
			$("#div3").attr("style","display:none;");
			var v=$(dom).val();
			if(v=="2"){
				$("#div2").removeAttr("style");
			}else if(v=="3"){
				$("#div3").removeAttr("style");
			}else{
				$("#div1").removeAttr("style");
			}
		}
		
		function doSubmit(){
			if(!doValidate()){
				return;
			}
			var customerNo = $("#ownerId").val();
			var role = $("#ownerRole option:selected").val();
			var param = {customerNo:customerNo,role:role};
			$.ajax({
				url:"findRateByCustomerNo.action",
				type:"POST",
				dataType:"text",
				async:false,
				data:param,
				success:function(data){
					if(data!=null && data!=''){
						var params=$("#dfSet").serialize();
						$.ajax({
							url:"dfSetUpdate.action",
							async:false,	//异步请求
							data:params,
							dataType:"text",
							type:"POST",
							success:function(msg){
								var fn=function(){
									$("#dfSetQuery",window.parent.document).submit();
								};
								if(msg == 'SUCCESS'){
									dialogMsg2("操作成功!",fn);
								}
								if(msg == 'FAIL'){
									dialogMsg2("操作失败，系统异常!",fn);
								}
							}
						});
					}else{
						var fn=function(){
							$("#dfSetQuery",window.parent.document).submit();
						};
						dialogMsg2("该商户未设置费率信息!",fn);
					}
				}
			});
		}
		
		/**表单验证**/
		function doValidate(){
			var flag=true;
			
			var ownerId=$("#ownerId").val();
			var limitAmountSingle=$("#limitAmountSingle").val();
			var limitAmountDay=$("#limitAmountDay").val();
			var limitCountDay=$("#limitCountDay").val();
			
			if(ownerId == ''){
				alert('持有者编号不能为空');
				return false;
			}
			
			if(limitAmountSingle == ''){
				alert('单笔限额不能为空');
				return false;
			}
			
			if(!isNaN(limitAmountSingle)){
				alert('单笔限额必须为数字');
				return false;
			}
			
			if(Number(limitAmountSingle)< 0){
				alert('单笔限额必须为大于0的数字');
				return false;
			}
			
			if(limitAmountDay == ''){
				alert('日累计限额不能为空');
				return false;
			}

			if(!isNaN(limitAmountDay)){
				alert('日累计限额必须为数字');
				return false;
			}
			
			if(Number(limitAmountDay)< 0){
				alert('日累计限额必须为大于0的数字');
				return false;
			}
			
			if(limitCountDay == ''){
				alert('日交易笔数不能为空');
				return false;
			}

			if(!isNaN(limitCountDay)){
				alert('日交易笔数必须为数字');
				return false;
			}
			
			if(Number(limitCountDay)< 0){
				alert('日交易笔数必须为大于0的数字');
				return false;
			}
			
			
			var isopen = document.getElementById("isOpen").checked;
			if(isopen == true){
				if($("#phone").val() == ''){
					alert("手机号码不能为空!");
					return false;
				}
				var regPhone = /^(13|15|17|18|14)[0-9]{9}$/;
				if(!regPhone.test($("#phone").val())){
					alert("手机号码输入有误!");
					return false;
				}
				if($("#complexName").val() == ''){
					alert("复核人输入有误!");
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
		
		function stepFeeChange(dom){
			
		}
		
		function bagFeeChange(dom){
			
		}
		
		function stepAdd(){
			var htmStr="<tr>"+
				"<td><center>0&lt;交易量&lt;=<input class='inp' type='text' style='width: 80px;''/></center></td>"+
				"<td><center>"+
				"<input class='inp' type='text' style='width: 80px;' name='rate'/>"+
				"<select style='width: 60px;' onchange='stepFeeChange(this);'>"+
				"<option value='rate'>%/笔</option><option vate='fee'>元/笔</option>"+
				"</select>"+
				"</center></td>"+
				"<td><center>"+
				"<a href='javascript:void 0;' onclick='stepAdd();'>添加</a>&nbsp;&nbsp;<a id='stepDelete' href='javascript:void 0;' onclick='stepDelete(this);'>删除</a>"+
				"</center></td>"+
				"</tr>";
			$("#stepTable").append(htmStr);
		}
		
		function stepDelete(dom){
			$(dom).parent().parent().parent().remove();
		}
	</script>	
</head>
<body onload="isopen()">
	<div class="search_tit"><h2>代付设置新增</h2></div>
	<form id="dfSet">
		<div id="customerDiv" style="display: none; z-index: 100;text-align: center; position:absolute;left: 20%;right: 20%;top:10%;">
			<img id="customerImg" src="" style="max-height:350px;max-width: 600px;">
			<br/><br/>
			<a href="javascript:rotateImg('customerImg','r')"><img src="./image/left.jpg"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:WinOpen('customerImg')">打开原图</a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:colseImg('customerImg')">隐藏图片</a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:rotateImg('customerImg','l')"><img src="./image/right.jpg"></a>
		</div>
		<table class="global_table" cellpadding="0" cellspacing="0">
			<tr>
				<th>持有者ID</th>
				<td>
					<input type="text" name="ownerId" id="ownerId"/>
					<span><a href="javascript:showImg()" style="display: none;" id="img">授权资质</a></span>
				</td>
				<th>持有者类型</th>
				<td colspan="3">
					<dict:select dictTypeCode="DPAYROLE" name="ownerRole" id="ownerRole" nullOption="true" style="width:150px;"/>
			</tr>
			<%-- <tr>
				<th width="10%">开始计费时间：</th>
				<td width="20%">
					<input type="text" value="" id="startChargeTime" name="startChargeTime"
						class="Wdate width120" />
				</td>	
				<th width="10%">服务期限：</th>
				<td width="20%">
					<input class="inp" type="text" style="width: 50px;" name="serviceDeadLine"/>月
				</td>
				<th width="10%"></th>
				<td width="20%"></td>	 -->
				<!-- <th width="10%">统计周期：</th>
				<td width="20%">
					<input class="inp" type="text" style="width: 50px;" name="statisticalCycle"/>月
				</td> -->
			</tr>	
			<!-- <tr><th colspan="6"></th></tr>
			<tr>		
				<th>手续费设置：</th>
				<td colspan="5">
					<table class="global_table" cellpadding="0" cellspacing="0">
						<tr>
							<th style="width: 15%;">费率类型：</th>
							<td style="width: 85%;">
								<input type="radio" name="feeType" checked="checked" value="1" onchange="radioChange(this);"/>固定费率
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="feeType" value="2" onchange="radioChange(this);"/>阶梯费率
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="feeType" value="3" onchange="radioChange(this);"/>包量
							</td>
						</tr>
						<tr id="div1">
							<th>固定费率设置：</th>
							<td>
								<table class="global_table" cellpadding="0" cellspacing="0">
									<th style="width: 10%;">下限：</th>
									<td style="width: 20%;">
										<input class="inp" type="text" style="width: 80px;" name="lowerLimit"/>元
									</td>
									<th style="width: 10%;">费率：</th>
									<td style="width: 20%;">
										<input class="inp" type="text" style="width: 80px;" id="fixedFee" name="rate"/>
										<input type="hidden" id="type" name="type" value="RATIO"/>
										<select onchange="fixedFeeChange(this);">
											<option value="rate">%/笔</option>
											<option value="fee">元/笔</option>
										</select>
									</td>
									<th style="width: 10%;">上限：</th>
									<td style="width: 20%;">
										<input class="inp" type="text" style="width: 80px;" name="upperLimit"/>元
									</td>
								</table>
							</td>
						</tr>
						<tr id="div2" style="display: none;">
							<th>阶梯费率设置：</th>
							<td colspan="6">
								<table class="global_table" cellpadding="0" cellspacing="0" id="stepTable">
									<tr>
										<th style="width: 30%;"><center>交易量（万元）</center></th>
										<th style="width: 40%;"><center>费率</center></th>
										<th style="width: 30%;"><center>操作</center></th>
									</tr>
									<tr>
										<td>
											<center>0&lt;交易量&lt;=<input class="inp" type="text" style="width: 80px;"/></center>
										</td>
										<td>
											<center>
												<input class="inp" type="text" style="width: 80px;" id="stepRate"/>
												<select style="width: 60px;" onchange="stepFeeChange(this);">
													<option value="rate">%/笔</option>
													<option vate="fee">元/笔</option>
												</select>
											</center>
										</td>
										<td>
											<center>
												<a href="javascript:void 0;" onclick="stepAdd();">添加</a>&nbsp;&nbsp;
											</center>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr id="div3" style="display: none;">
							<th>包量设置：</th>
							<td colspan="6">
								<table class="global_table" cellpadding="0" cellspacing="0">
									<tr>
										<th style="width: 25%;"><center>费用（元）</center></th>
										<th style="width: 25%;"><center>流量</center></th>
										<th style="width: 25%;"><center>折合费率</center></th>
										<th style="width: 25%;"><center>超量</center></th>
									</tr>
									<tr>
										<td>
											<center><input class="inp" type="text" style="width: 80px;"/>元</center>
										</td>
										<td>
											<center><input class="inp" type="text" style="width: 80px;"/>万元</center>
										</td>
										<td>
											<center><input class="inp" type="text" style="width: 80px;" disabled="disabled"/>%</center>
										</td>
										<td>
											<center>
												<input class="inp" type="text" style="width: 80px;" id="bagFee"/>
												<select style="width: 60px;" onchange="bagFeeChange(this);">
													<option option="rate">%/笔</option>
													<option option="fee">元/笔</option>
												</select>
											</center>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>						
			</tr>
			<tr><th colspan="6"></th></tr> --%>
			<tr>
				<th><center>开关设置</center></th>
				<td colspan="5">
					<table class="global_table" cellpadding="0" cellspacing="0">
						<tr>
							<th style="width: 20%;">打款支持账户类型：</th>
							<td style="width: 80%;">
								<input type="checkbox" name="accountTypes" value="INDIVIDUAL"/>对私账户
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="checkbox" name="accountTypes" value="OPEN"/>对公账户
							</td>
						</tr>
						<tr>
							<th>是否开通代付复核：</th>
							<td>
								<input type="radio" value="true" onchange="isopen();" name="manualAudit"/>不开通
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" id="isOpen" onchange="isopen();" value="false" name="manualAudit"/>开通
							</td>
						</tr>
						<tr class="phoneTr">
							<th>接收代付复核密码手机号：</th>
							<td>
								<input type="text" id="phone" name="phone"/>
							</td>
						</tr>
						<tr class="phoneTr">
							<th>姓名：</th>
							<td>
								<input type="text" id="complexName" name="complexName"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr><th colspan="6"></th></tr>
			<tr>
				<th><center>限制设置</center></th>
				<td colspan="5">
					<table class="global_table" cellpadding="0" cellspacing="0" >
						<tr>
							<th style="width: 10%;">单笔交易限额：</th>
							<td style="width: 15%;">
								<input class="inp" type="text" style="width: 80px;" value="50000" name="limitAmountSingle"/>元
							</td>
							<th style="width: 10%;">日交易限额：</th>
							<td style="width: 15%;">
								<input class="inp" type="text" style="width: 80px;" value="1000000" name="limitAmountDay"/>元
							</td>
							<th style="width: 10%;">日交易笔数：</th>
							<td style="width: 15%;">
								<input class="inp" type="text" style="width: 80px;" value="1000" name="limitCountDay"/>笔
							</td>
							<th style="width: 10%;">风险预存期：</th>
							<td style="width: 15%;">
								<select style="width: 60px;" name="freezeDayForRisk" id="freezeDayForRisk">
									<option value="0">0</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
								</select>天
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
</div>
</body>
</html>