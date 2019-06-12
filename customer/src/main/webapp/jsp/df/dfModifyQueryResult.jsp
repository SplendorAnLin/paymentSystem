<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
	<script type="text/javascript">
	
	/* jQuery.ajaxSetup({cache:false}); */

	/* $(document).ready(function(){

		$("tr").find("td:eq(3)").each(function(){
			var cardNo = $(this).html();
			if(cardNo.length>10){
				var xing = "";
				for(var i = 0;i<cardNo.length-10;i++){
					xing +="*";
				}
				$(this).html(cardNo.substring(0,6)+xing+cardNo.substring(cardNo.length-4,cardNo.length));
			}
		});
	}); */
	</script>
</head>
<body>

	<s:if test="#attr['dpayRequest'].list.size()>0">
		<vlh:root value="dpayRequest" url="?" includeParameters="*" configName="defaultlook">
			<div style="max-height: 270px;max-width:100%;overflow:auto;min-height: 250px;">
			<table cellpadding="0" cellspacing="1" class="global_tableresult">
				<vlh:row bean="dpayRequest">
					<s:set name="dpayRequest" value="#attr['dpayRequest']" />
					<vlh:column title="商户编号" property="owner_id"	 attributes="width='150px'" />
					<vlh:column title="商户订单号" property="request_no"	 attributes="width='150px'" />
					<vlh:column title="订单号" property="flow_no"	 attributes="width='150px'" />
					<vlh:column title="收款人姓名" property="account_name" attributes="width='180px'" />
					<vlh:column title="收款账号" property="account_no" attributes="width='180px' class='accoutNoStar'" />
					<vlh:column title="金额" property="amount" attributes="width='80px'"/>
					<vlh:column title="状态" attributes="width='80px'">
						<s:if test="${dpayRequest.status=='FAILED'}">
							失败
						</s:if>
					</vlh:column>
					<vlh:column title="账户类型" attributes="width='80px'">
						<s:if test="${dpayRequest.account_type=='INDIVIDUAL' }">对私</s:if>
						<s:if test="${dpayRequest.account_type=='OPEN' }">对公</s:if>
					</vlh:column>
					<vlh:column title="卡类型" attributes="width='80px'">
						<s:if test="${dpayRequest.card_type=='DEBIT' }">借记卡</s:if>
						<s:if test="${dpayRequest.card_type=='CREDIT' }">贷记卡</s:if>
					</vlh:column>
					<vlh:column title="认证类型" attributes="width='80px'">
						<s:if test="${dpayRequest.cert_type=='NAME' }">账户名称</s:if>
						<s:if test="${dpayRequest.cert_type=='ID' }">身份证</s:if>
						<s:if test="${dpayRequest.cert_type=='PROTOCOL' }">协议</s:if>
					</vlh:column>
					<vlh:column title="认证号码" property="cert_no" attributes="width='180px'"/>
					<vlh:column title="打款原因" attributes="width='200px'" property="description"/>
					<vlh:column title="完成描述" attributes="width='100px'" property="error_msg"/>
					<vlh:column title="创建时间" property="create_date" attributes="width='180px'" format="yyyy-MM-dd HH:mm:ss"/>
					<vlh:column title="审核时间" property="audit_date" attributes="width='180px'" format="yyyy-MM-dd HH:mm:ss"/>
					<vlh:column title="完成时间" property="finish_date" attributes="width='180px'" format="yyyy-MM-dd HH:mm:ss"/>
					<vlh:column title="操作" attributes="width='180px'">
						<a href="toDfRequestModdify.action?requestBean.flowNo=${dpayRequest.flow_no}">修改</a>|<a href="dfHistoryQuery.action?flowId=${dpayRequest.flow_no}">历史</a>
					</vlh:column>
				</vlh:row>
			</table>
			</div>
			<div class="gpage">
				<vlh:paging showSummary="true" pages="5" attributes="width='500'" />
			</div>
		</vlh:root>
	</s:if>
	<s:else>
		无符合条件的查询结果
	</s:else>
</body>
</html>