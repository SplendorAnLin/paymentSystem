<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
	function getStream(){
		var id=document.getElementById('id').value;
		$('#codeImage')[0].src="signBillStream.action?posExternalId="+id;
	}
</script>

</script>
</head>
<body style="background-color: #EEEAEA" onload="javascript:getStream()">
<!--  <img alt="" src="<s:property value="src"/>">-->
	<input id="id" name="id" type="hidden" value="${posExternalId} ">
	<table>
		<tr>
			<td>
				<table style="border:1px solid black;width: 300px;margin-left:250px;margin-top:40px;">
					<tr >
						<td align="center">
							<div style="overflow: auto; height: 400px; width: 100%">
								<img id="codeImage"  name="codeImage" />
							</div>
						</td>
					</tr>
				</table>
			</td>
			<td>
				<table style="margin-top:10px">
					<tr>
						<td>
							<a href="signBillStream.action?posExternalId=${posExternalId}">保存到本地</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>