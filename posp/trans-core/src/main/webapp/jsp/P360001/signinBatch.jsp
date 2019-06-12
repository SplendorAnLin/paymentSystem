<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>

<html>
<head>
<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/signInP360001.js'></script>
	
	<script>
		function signInSubmit(){
			if(confirm("确认进行签到操作吗？")==false){
				return;
			}
			
			var bankCustomerNumber  = $("#bankCustomerNumber").val();
			var bankPosCati = $("#bankPosCati").val();
								
			$("#processing").show();
			$("#button1").hide();
						
			signInP360001.executeSignIn(bankCustomerNumber,bankPosCati,signInBatchBack);
		}
		
	 	function signInBatchBack(retCode){
	 		if(retCode ==0){
	 			alert("批量签到成功！");
	 		}else{
	 			alert("批量签到失败 , " + retCode + " 个终端异常!");
	 		}	
	 		$("#processing").hide();
			$("#button1").show();	
	 	}
	</script>
</head>
<body> 
	<div class="container"> 	
	
		<div class="toptitle" ><h2>深圳建行批量签到</h2></div>
		<form id="form1" action="" method="post" >	
			
			<table class="global_table" cellpadding="0" cellspacing="0">						
				<tr>							
					<th>银行商户号 ：</th>
					<td>
						<input type="text" id="bankCustomerNumber" class="width240" />
					</td>	
				</tr>
				<tr>								
					<th>银行终端号 ：</th>
					<td>	
						<input type="text" id="bankPosCati" class="width240" />							
				    </td>
				</tr>
				<tr id="processing" style="display:none">
						<th>签到中，请等待 ...</th>						
						<td>
							<img src="${pageContext.request.contextPath}/image/loading_3.gif" /> 					    	
						</td>							
					</tr>						
			</table>
				
			<br>
			<div>
				<p class="btn" >
					<input type="button" value="签 到" onclick="signInSubmit()" id="button1" class="global_btn"/> 
				  	<input type="reset"  value="重 置" class="global_btn"/>
				</p>
			</div>
		</form>
	</div>
    
	</body>
</html>
