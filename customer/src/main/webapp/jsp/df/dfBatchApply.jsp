<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
<head>
	<script type="text/javascript">
		function doApply(){
			var flag = "";
			var params=$("#dfBatchForm").serialize();
			if(params==null || params==""){
				dialogMsg("没有要提交的内容!");
				return;
			}else if(!doValidate()){
				dialogMsg("信息不合法!");
				return;
			}
			var singleAmount = 0;
			var sumAmount = 0;
			var count = 0;
			var amount = 0;
			var amounts = "";
			
			$("#cbs",window.parent.document).val("");
			var fn=function(){
				$("#queryForm",window.parent.document).submit();
			};
			
			$.ajax({
				url:"getOrderSingleAmount.action",
				type:"post",
				dataType:"json",
				async:false,	//同步
				data:params,
				success:function(data){
					var re=eval(data); 
					singleAmount = re.msg;
				}
			});
			
			/* if(singleAmount == 'null'){
				dialogNoMsg2("获取单笔限额异常，请刷新后再试！",fn);
				return;
			} */
			
			$.ajax({
				url:"getOrderSumAmount.action",
				type:"post",
				dataType:"json",
				async:false,	//同步
				data:params,
				success:function(data){
					var re=eval(data); 
					sumAmount = re.msg;
				}
			});
			
			/* if(sumAmount == 'null'){
				dialogNoMsg2("获取日累计限额异常，请刷新后再试！",fn);
				return;
			} */
			
			$.ajax({
				url:"getOrderCount.action",
				type:"post",
				dataType:"json",
				async:false,	//同步
				data:params,
				success:function(data){
					var re=eval(data); 
					count = re.msg;
				}
			});
			
			/* if(count == 'null'){
				dialogNoMsg2("获取日累计笔数异常，请刷新后再试！",fn);
				return;
			} */
			
			$(".inpAmount").each(function(i){
				
				if(Number($(this).val()) <= 0){
					flag = "amountError";
					return;
				}
				
				if(singleAmount != 'null' && Number(singleAmount) < Number($(this).val())){
					flag = "singleAmount";
					return;
				}
				amount = Number(amount) + Number($(this).val());
				if(sumAmount != 'null' && Number(sumAmount)<Number(amount)){
					flag = "sumAmount";
					return;
				}
				if(count != 'null' && count==0||count<i+1){
					flag = "count";
					return;
				}
				if(i==0){
					amounts = amount;
				}else{
					amounts = amounts +","+ amount;
				}
			});
			
			if(flag == "amountError"){
				dialogNoMsg2("发起代付金额不能小于等于0，请修改后再试！",fn);
				return;
			}
			
			if(flag == "singleAmount"){
				$("#cbs",window.parent.document).val("");
				var fn=function(){
					$("#queryForm",window.parent.document).submit();
				};
				dialogNoMsg2("单笔限额为 "+singleAmount+"元",fn);
				return;
			}
			
			if(flag == "count"){
				$("#cbs",window.parent.document).val("");
				var fn=function(){
					$("#queryForm",window.parent.document).submit();
				};
				dialogNoMsg2("今日可发起代付笔数为 "+count+"笔",fn);
				return;
			}
			
			if(flag == "sumAmount"){
				$("#cbs",window.parent.document).val("");
				var fn=function(){
					$("#queryForm",window.parent.document).submit();
				};
				dialogNoMsg2("今日可发起代付金额为 "+sumAmount+"元",fn);
				return;
			}
			
			var isGetFee = false;
			$.ajax({
				url:"getFee.action",
				type:"post",
				dataType:"json",
				async:false,	//同步
				data:"amount="+amounts,
				success:function(data){
					var re=eval(data); 
					fee = re.msg;
					if(fee == 'null'){
						$("#cbs",window.parent.document).val("");
						var fn=function(){
							$("#queryForm",window.parent.document).submit();
						};
						dialogNoMsg2("计算手续费失败，请刷新后再试",fn);
						isGetFee = true;
						return false;
					}
					amount  = Number(amount) + Number(fee);
				}
			});
			if(isGetFee == true){
				return false;
			}
			
			$.ajax({
				url:"getRechargeAccountBalance.action",
				type:"post",
				dataType:"json",
				async:false,	//同步
				success:function(data){
					var re=eval(data); 
					if(re.msg=='失败'){
						$("#cbs",window.parent.document).val("");
						var fn=function(){
							$("#queryForm",window.parent.document).submit();
						};
						dialogNoMsg2("系统异常，请稍后重试",fn);
						return;
					}
					if(re.msg.split("|")[0]=='LackOfBalance'){
						$("#cbs",window.parent.document).val("");
						var fn=function(){
							$("#queryForm",window.parent.document).submit();
						};
						dialogNoMsg2("充值账户可用余额不足</br>充值账户余额为"+re.msg.split('|')[1]+"元<br/>",fn);
						return;
					}
					if(Number(amount)<Number(re.msg)){
						$.ajax({
							url:"dfBatchApply.action",
							type:"post",
							dataType:"json",
							async:false,	//同步
							data:params,
							success:function(data){
								var re=eval(data); 
								$("#cbs",window.parent.document).val("");
								var fn=function(){
									$("#queryForm",window.parent.document).submit();
								};
								dialogMsg2(re.msg,fn);
							}
						});
					}else{
						$("#cbs",window.parent.document).val("");
						var fn=function(){
							$("#queryForm",window.parent.document).submit();
						};
						dialogNoMsg2("充值账户可用余额不足</br>充值账户可用余额为: "+Number(re.msg)+"元",fn);
					}
				}
			});
		}
		
		/**表单验证**/
		function doValidate(){
			var flag=true;
			
			var reg2_1=/((^[1-9]\d*)|(^0))$/;	//自然数
			var reg2_2=/^[1-9]\d*\.\d+$/;		//正小数>=1
			var reg2_3=/^0\.\d+$/;				//正小数<1
			
			$("input[id='amount']").each(function(){
				var v=$(this).val();
				if(!reg2_1.test(v) && !reg2_2.test(v) && !reg2_3.test(v)){
					flag=false;
					return false;
				}
			});
			return flag;
		}
		
		function dialogMsg(info) {
			$("#df-dialog-msg-info").html(info);
			$("#df-dialog-msg").dialog({
				resizable : false,
				height : 140,
				modal : true,
				close:function(){		//关闭后事件
					$(this).dialog("destroy");
				},
				buttons : {
						"确定" : function() {
							$(this).dialog("close");
						}
				}
			});
		}
		
		/**alert提示框**/
		function dialogMsg2(info,fn) {
			$("#df-dialog-msg-info").html(info);
			$("#df-dialog-msg").dialog({
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
		
		/**alert提示框**/
		function dialogNoMsg2(info,fn) {
			$("#df-dialog-msg-info").html(info);
			$("#df-dialog-msg").dialog({
				resizable : false,
				height : 170,
				modal : true,
				buttons : {
						"确定" : function() {
							$(this).dialog("close");
						}
				}
			});
		}
		
		function deleteTr(dom){
			$(dom).parent().parent().remove(); 
		}
	</script>
</head>
<body>	
	<s:set name="payeeList" value="#attr['dpayTargetAccount'].list"/>
	<div class="total_panel">
		<div class="total">
			<h3>批量代付</h3>
		</div>	
	</div>	
	<form id="dfBatchForm">
		<table cellpadding="0" cellspacing="1" class="global_tableresult">
			<tr>
				<th style="width: 7%;">账户名称</th>
				<th style="width: 12%;">银行账号</th>
				<th style="width: 12%;">开户银行</th>
				<th style="width: 12%;">金额（元）</th>
				<th style="width: 12%;">打款原因</th>
				<th style="width: 12%;">操作</th>
			</tr>
			<s:iterator value="payeeList" id="pl" status="idx">
				<tr>
					<input type="hidden" name="dfBatch[${idx.index }].targetAccountNo" value="${pl.CODE }"/>
					<input type="hidden" name="dfBatch[${idx.index }].feeType" value="PAYER"/>
					<td>${pl.ACCOUNT_NAME }</td>
					<td>${pl.ACCOUNT_NO }</td>
					<td>${pl.CNAPS_NAME}</td>
					<td>
						<input type="text" class="inpAmount" name="dfBatch[${idx.index }].amount" id="amount"/>
					</td>
					<td>
						<input type="text" name="dfBatch[${idx.index }].description"/>
					</td>
					<td>
						<a href="javascript:void 0;" onclick="deleteTr(this);">删除</a>
					</td>
				</tr>	
			</s:iterator>			
		</table>	
		<br/><br/>
		<center>
			<input type="button" class="global_btn" value="发起代付" onclick="doApply();"/>
			<input type="button" class="global_btn" value="返回" onclick="javascript:history.go(-1);"/>
		</center>
	</form>		
</body>
</html>