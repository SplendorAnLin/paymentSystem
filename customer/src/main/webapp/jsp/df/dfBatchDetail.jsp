<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
<head>
	<script type="text/javascript">
	function doApply(flowNo) {
		if(flowNo==""){
			dialogMsg2("信息异常，请刷新页面再试!");
		}else{
			dialogSingleMsg(flowNo,"是否同意?");
		}
	}
	
	/**审核操作弹出框**/
	function dialogSingleMsg(flowNo,info) {
		$("#dialog-confirm-info").html(info);
		$("#dialog-confirm").dialog({
			resizable : false,
			height : 140,
			modal : true,
			close:function(){		//关闭后事件
				$(this).dialog("destroy");
			},
			buttons : {
					"同意" : function() {
						$(this).dialog("close");
						$("#updateDiv").show().dialog({
							title : '代付复核',
							resizable : false,
							modal : true,
							width : 400,
							height : 150,
							bgiframe : true,
							show: { effect: "blind", duration: 500 },
						    hide: { effect: "explode", duration: 500 },
							open: function() { 
								$(this).bind("keypress.ui-dialog", function(event) { 
									if (event.keyCode == $.ui.keyCode.ENTER) { 
										$(this).find(".ui-dialog-buttonpane button:first").click(); 
										return false;
									} 
								}); 
							}, 
							beforeClose: function( event, ui ) {
								$("#select-result").empty();
								$(".ui-selected","#updateDiv").each(function(){
									$(this).removeClass("ui-selected");
								});
							},
							buttons: [ { text: "确 认", click: function() {
									var password = $("#password").val();
									if(password == "" || password == null){
										alert("请输入复核密码");
										return;
									}
										$.ajax({
								   			 type: "POST",
								   			 url: "applyVerify.action",
								   			 dataType:"json",
								   			 async:false,
								   			 data: "oldPassword="+password,
								  			 success: function(msg){
							   					 if(msg == true){
							   						doApplySubmit(flowNo,"TRUE","dfBatchAudit.action");	
							   					 	$("#updateDiv").dialog( "close" );
							   					 }else{
							   						 alert("复核密码验证失败！");
							   					 }
								  	         }
									    });					 		
							   		}
								}]
						});
					},
					"拒绝" : function() {
						$(this).dialog("close");
						doApplySubmit(code,"FALSE","dfBatchAudit.action");						
					}
			}
		});
	}

	/**提交审核操作**/
	function doApplySubmit(code,flag,url){
		var params={code:code,flag:flag};
		$.ajax({
			url:url,
			type:"post",
			dataType:"json",
			async:false,	//同步
			data:params,
			success:function(data){
				var re=eval(data); 	
				$("#cbs",window.parent.parent.document).val("");
				var fn=function(){
					$("#dfQuery",window.parent.parent.document).submit();
				};
				var msgs = re.msg;
				if(msgs == "失败"){
					msgs = "账户余额不足或未配置代付功能！";
				}
				dialogMsg3(msgs,fn);
			}
		});
	}
	/**alert提示框**/
	function dialogMsg3(info,fn) {
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
	</script>
</head>
<body>	
	<s:if test="#attr['dpayRequest'].list.size()>0">
		<vlh:root value="dpayRequest" url="?" includeParameters="*" configName="defaultlook">
			<div style="max-height: 360px;max-width: 100%;overflow: auto;">
			<table cellpadding="0" cellspacing="1" class="global_tableresult">
				<vlh:row bean="dpayRequest">					
					<s:set name="dpayRequest" value="#attr['dpayRequest']" />
					<vlh:column title="请求号" property="flow_no"	 attributes="width='7%'" />
					<vlh:column title="申请日期" property="create_date" attributes="width='7%'" format="yyyy-MM-dd"/>
					<vlh:column title="账户名称" property="account_name" attributes="width='7%'" />
					<vlh:column title="收款账号" property="account_no" attributes="width='7%'" />		
					<vlh:column title="开户行" property="cnaps_name" attributes="width='10%'" />				
					<vlh:column title="金额" property="amount" attributes="width='7%'"/>
					<vlh:column title="手续费<br/>类型" attributes="width='7%'">
						<s:if test="${dpayRequest.fee_type=='PAYER' }">
							付款方承担
						</s:if>
						<s:if test="${dpayRequest.fee_type=='REV' }">
							收款方承担
						</s:if>
					</vlh:column>
					<vlh:column title="手续费" attributes="width='7%'">
						<s:if test="${dpayRequest.fee_type=='PAYER' }">
							${dpayRequest.payer_pee }
						</s:if>
						<s:if test="${dpayRequest.fee_type=='REV' }">
							${dpayRequest.rev_pee }
						</s:if>
					</vlh:column>
					<vlh:column title="实付金额" attributes="width='7%'">
						<s:if test="${dpayRequest.fee_type=='PAYER' }">
							${dpayRequest.payer_pee+dpayRequest.amount }
						</s:if>
						<s:if test="${dpayRequest.fee_type=='REV' }">
							${dpayRequest.rev_pee-dpayRequest.amount }
						</s:if>
					</vlh:column>
					<vlh:column title="状态" attributes="width='5%'">
						<s:if test="${dpayRequest.audit_status=='WAIT' }">
							待审核
						</s:if>
						<s:if test="${dpayRequest.audit_status=='REFUSE' }">
							审核拒绝
						</s:if>
						<s:if test="${dpayRequest.audit_status!='WAIT' && dpayRequest.audit_status!='REFUSE' && dpayRequest.handle_status!='SUCCEED' && dpayRequest.handle_status!='FAILED' && dpayRequest.refund_status==null}">
							处理中
						</s:if>
						<s:if test="${dpayRequest.handle_status=='SUCCEED' && dpayRequest.refund_status==null}">
							成功
						</s:if>
						<s:if test="${dpayRequest.handle_status=='FAILED' && dpayRequest.refund_status==null }">
							失败
						</s:if>
						<s:if test="${dpayRequest.refund_status=='WAIT' }">
							待退款
						</s:if>
						<s:if test="${dpayRequest.refund_status=='PASS' || dpayRequest.refund_status=='REFUSE' }">
							已退款
						</s:if>
					</vlh:column>
					<vlh:column title="描述" attributes="width='7%'" property="description"/>
					<%-- <vlh:column title="操作" attributes="width='5%'">
						<s:if test="${dpayRequest.audit_status=='WAIT' }">
						<a href="javascript:void 0;" onclick="doApply('${dpayRequest.request_no }');">审核</a>
						</s:if>
					</vlh:column> --%>			
				</vlh:row>
			</table>
			</div>
			<div class="gpage">
				<vlh:paging showSummary="true" pages="5" attributes="width='400'"/>
			</div>				
		</vlh:root>
	</s:if>	
	<s:else>
		无符合条件的查询结果
	</s:else>
	<center>
		<input type="reset" class="global_btn" value="返 回" onclick="javascript:history.go(-1);"/>
	</center>
</body>
</html>