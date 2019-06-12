<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
<head>
	<script type="text/javascript">
	
	function showMsg(msg){
		$( "#dialog-messages-info").html(msg);
		$( "#dialog-messages" ).dialog({
			resizable: false,
			height:260,
			modal: true,
			buttons: {"关闭": function() { $( this ).dialog( "close" );}}
		});
	}
	</script>	
</head>
<body style="height: 830px">	
	<div style="overflow: auto;">
		<c:if test="${customerKeys != null && customerKeys.size() > 0 }">
			<table cellpadding="1" cellspacing="1" align="center" class="global_tableresult" style="">
				<tr><td colspan="2"><div class="pop_tit" ><h2>秘钥信息</h2></div></td></tr>
				<tr>
					<td width="160px;">秘钥类型</td>
					<td	width="140px;">操作</td>
				</tr>
				<c:forEach items="${customerKeys}" var="keys" varStatus="i">
					<tr>
						<td>
							${keys.keyType }
						</td>
						<td>
							<a href="javascript:showMsg('${keys.key}');">点击查看</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<c:if test="${customerKeys eq null or customerKeys.size() == 0 }">
			无秘钥
		</c:if>
	</div>
	<div id="dialog-messages" title="秘钥" style="display:none">
		<div id="dialog-messages-info" style="width: 100%;word-break:break-all"></div>
	</div>
</body>
</html>