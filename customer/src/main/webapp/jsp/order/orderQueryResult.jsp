<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
<head>
	<script type="text/javascript">
		
		function detail(id){
			var url = "${pageContext.request.contextPath}/orderDetail.action?id="+id+"";
			window.parent.parent.refresh("popframe-1", url);
			window.parent.parent.showDialog("popdiv-1");		
		}	
		
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
		
		function order(id){
			var url = "${pageContext.request.contextPath}/signBill.action?posExternalId="+id+"&date="+new Date+"";
	
			window.parent.parent.refresh("popframe-1", url);
			window.parent.parent.showDialog("popdiv-1");	
		}
	</script>
	<style type="text/css">
		li{font-weight: bold;}
		li .checkSpan{color: red;}
	</style>
</head>
<body>	

	<s:if test="#attr['order'].list.size()>0">
		<vlh:root value="order" url="?" includeParameters="*" configName="defaultlook">
			
			<table cellpadding="0" cellspacing="1" class="global_tableresult">
				<vlh:row bean="order">					
					<s:set name="order" value="#attr['order']" />
																						
					<vlh:column title="交易类型" property="trans_type"	 attributes="width='8%'" >
						<dict:write dictTypeId="TransType" dictId="${order.trans_type}"></dict:write>
					</vlh:column>					
					<vlh:column title="终端号" property="pos_cati"	 attributes="width='10%'" />
					<vlh:column title="卡号" attributes="width='12%'" >
						<boss:pan pan="${order.pan}" />						 										
					</vlh:column>	
					<vlh:column title="卡类型" attributes="width='5%'" >
						<dict:write dictTypeId="CardType" dictId="${order.card_type}"></dict:write>
					</vlh:column>
					<vlh:column title="参考号" property="external_id" attributes="width='10%'" />
					<vlh:column title="金额" property="amount" attributes="width='6%'" />
					<vlh:column title="手续费" property="customer_fee" attributes="width='6%'">
						<s:if test="${order.status == 'INIT'}"><font color="red">0.00</font></s:if>
					</vlh:column>
					<vlh:column title="交易状态" property="status" attributes="width='8%'">
						<s:if test="${order.status == 'SETTLED'}">成功</s:if>
						<s:if test="${order.status == 'SUCCESS'}">成功</s:if>
						<s:if test="${order.status == 'INIT'}"><font color="red">未付</font></s:if>
						<s:if test="${order.status == 'REPEAL'}"><font color="green">撤销</font></s:if>
						<s:if test="${order.status == 'REVERSALED'}"><font color="red">冲正</font></s:if>
						<s:if test="${order.status == 'AUTHORIZED'}"><font color="coral">已授权</font></s:if>
					</vlh:column>				
					<vlh:column title="交易时间" attributes="width='10%'">
						<s:date name="#order.create_time" format="yy-MM-dd HH:mm" />
					</vlh:column>					
					<vlh:column title="操作" attributes="width='10%'" >
						<a href="javascript:detail(${order.id});">详细</a>
						<s:if test="${order.check_bill_record_status == 'NEED_UPLOAD' && (order.status == 'SUCCESS' || order.status == 'SETTLED')}">
							<a href="javascript:showCheckBill('${order.audit_remark}','${order.external_id}','${order.pos_cati}','${order.pos_batch}','${order.pos_request_id}','<boss:pan pan="${order.pan}" />','${order.amount}','${order.agent_remark }');"><s:if test="${order.audit_remark != null || order.agent_remark!=null}">重传签购单</s:if><s:else>上传签购单</s:else></a>
						</s:if>
						<s:elseif test="${order.status == 'SUCCESS' || order.status == 'SETTLED' }">
							<a href="javascript:order(${order.external_id});">签购单</a>
						</s:elseif>
					</vlh:column>
					
				</vlh:row>
			</table>
			
			<div class="gpage">
				<vlh:paging showSummary="true" pages="5" attributes="width='400'" />
			</div>				
		</vlh:root>
	</s:if>	

	<s:if test="#attr['order'].list.size()==0">
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