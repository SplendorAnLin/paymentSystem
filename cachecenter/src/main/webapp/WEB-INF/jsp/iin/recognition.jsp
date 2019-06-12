<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/header.jsp"%>
<%@include file="../include/commons-include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3CUTF-8//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
  #word { width: 25em; }
  </style>
<script type="text/javascript">
	function cardIdentification(){
		if(checkCard($("#cardNo").val()) == false){
			alert("无效卡号");
			return;
		}
		$.ajax({
			url : "${pageContext.request.contextPath}/iin/recognition.htm",
			type : "POST",
			dataType : "json",
			data :$("#frm").serialize(),
			success : function(data) {
				$("#data").html(JSON.stringify(data));
			}
		});
	}
</script>
</head>
<body>
	<div class="detail_tit">
		<h2>卡识别</h2>
	</div>
	<br>
	<form id="frm">
		<input type="text" id="cardNo" name="cardNo" value=""/>银行卡号 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="识别" onclick="cardIdentification();">
		</br>
		<input type="checkbox" name="fields" class="attr" value="code"/>卡识别号
		<input type="checkbox" name="fields" class="attr" value="length"/>卡识别长度
		<input type="checkbox" name="fields" class="attr" value="panLength"/>卡号长度
		<input type="checkbox" name="fields" class="attr" value="providerCode"/>接口提供方编号
		<input type="checkbox" name="fields" class="attr" value="cardType"/>卡种
		<input type="checkbox" name="fields" class="attr" value="cardName"/>卡名称
		<input type="checkbox" name="fields" class="attr" value="agencyCode"/>机构代码
		<input type="checkbox" name="fields" class="attr" value="agencyName"/>机构名称
	</form>
	<br>
	<span id="data"></span>
</body>
</html>
