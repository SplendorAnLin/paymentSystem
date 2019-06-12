<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
<head>
	<script type="text/javascript">
		function showCheckBill(auditRemark,externalId,posCati,posBatch,posRequestId,pan,amount,agentRemark){
			$("#externalId").val(externalId);
			$("#auditRemark").html(auditRemark+agentRemark);
			$("#reasonSpan").hide();
// 			if(auditRemark!=null && auditRemark!="" && agentRemark!=null && agentRemark!=""){
// 				$("#reasonSpan").removeAttr("style");
// 			}
			
			if(auditRemark != ""){
				$("#reasonSpan").show();
			}
			$("#externalIdSpan").html(externalId);
			$("#posCatiSpan").html(posCati);
			$("#posBatchSpan").html(posBatch);
			$("#posRequestIdSpan").html(posRequestId);
			$("#panSpan").html(pan);
			$("#amountSpan").html(amount);
			$.blockUI({ 
	            message: $("#checkBillMask"), 
	            css: {
		            top:'0%',
		            left:'0%',
		            textAlign:'left',
		            border:	'0px solid #f00',
		            marginLeft:'0px',
		            marginTop: '0px', 
		            width: '970px',
		            height:'400px'
		        }
			});
			$("#closeMask").click($.unblockUI);
		}
		
		function checkBill(){
			if($("#bill").val()==""){ 
				$("#billTip").attr("style","color: red;");
				return false;
			}
			$("#billTip").attr("style","color: red;display: none;");
// 			$("#reasonSpan").attr("style","display: none;");
			return true;
		}
		
		function cancel(){
			$("#billTip").attr("style","color: red;display: none;");
			$("#bill").val("");
// 			$("#reasonSpan").attr("style","display: none;");
		}
		
	</script>
	<style type="text/css">
		li{font-weight: bold;}
		li .checkSpan{color: red;}
	</style>
</head>
<body>	

	<s:if test="#attr['orderCount'].list.size()>0">
		<vlh:root value="orderCount" url="?" includeParameters="*" configName="defaultlook">
			
			<table cellpadding="0" cellspacing="1" class="global_tableresult">
				<vlh:row bean="orderCount">					
					<s:set name="orderCount" value="#attr['orderCount']" />
					<vlh:column title="POS终端号" property="pos_cati" attributes="width='6%'"/>
					<vlh:column title="网点名称" property="shopname" attributes="width='6%'" />
					<vlh:column title="交易总金额" property="amount" attributes="width='6%'" />
					<vlh:column title="交易总笔数" property="ordercount" attributes="width='6%'" />
					<vlh:column title="手续费" property="customer_fee" attributes="width='6%'"/>
				</vlh:row>
			</table>
			
			<div class="gpage">
				<vlh:paging showSummary="true" pages="5" attributes="width='400'" />
			</div>				
		</vlh:root>
	</s:if>	

	<s:if test="#attr['orderCount'].list.size()==0">
		无符合条件的查询结果
	</s:if>
	
	<div id="checkBillMask" style="display:none;text-align: center;">
		<div style="margin: 50px auto; width: 100%;">
			<div style="color: red; font-size: 1.5em;"><span id="reasonSpan" style="display: none;">最近一次调单失败原因：</span><span id="auditRemark"></span></div>
			<br/>
			<div><span><font color="green">请先核对以下数据与小票上的数据一致</font></span></div>
			<div style="margin: 1px auto; border-bottom: 1px dashed; height: 1px; width: 350px; "></div>
			<div style="margin: 10px auto; width: 200px;">
				<ul style="text-align: left;">
					<li>参考号：<span class="checkSpan" id="externalIdSpan"></span></li>
					<li>终端号：<span class="checkSpan" id="posCatiSpan"></span></li>
					<li>批次号：<span class="checkSpan" id="posBatchSpan"></span></li>
					<li>流水号：<span class="checkSpan" id="posRequestIdSpan"></span></li>
					<li>卡&nbsp;&nbsp;&nbsp;&nbsp;号：<span class="checkSpan" id="panSpan"></span></li>
					<li>金&nbsp;&nbsp;&nbsp;&nbsp;额：<span class="checkSpan" id="amountSpan"></span></li>
				</ul>
			</div>
			<div style="margin: 10px auto; border-top: 1px dashed; height: 3px; width: 350px;"></div>
			<form action="upoladRealTimeCheckBill.action" enctype="multipart/form-data" method="post" onsubmit="return checkBill();">
				<input type="hidden" id="externalId" name="externalId" />
				上传小票：<s:file id="bill" name="bill" accept="image/*" />
				<br/><br/>
				<input type="submit" class="global_btn" value="上传" />
				<input id="closeMask" type="button" class="global_btn" value="取消" onclick="cancel();"/>
			</form>
			<br/>
			<label id="billTip" style="color: red;display: none;">请选择带上传小票</label>
		</div>
	</div>	
</div>
</body>
</html>