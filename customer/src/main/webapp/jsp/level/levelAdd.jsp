<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html>
<head>
</head>
<body class="over-y-h">
<div class="ctx-iframe">
	<div class="in-pop" style="width: 410px;padding: 20px 0;">
		<form action="${pageContext.request.contextPath}/saveInterfaceProviderAction.action" method="post" id="addform" target="Result" style="margin-left: 55px;">
			<div class="coll-1 ib mr-20">
				<div class="block">
	                 <span class="lable w-75">级别编号:</span>
	                 <div class="b-input w-170">
	                     <div class="input-wrap w-p-100">
	                         <input name="interfaceProvider.code" id="code" required notChinese type="text" class="input-text" >
	                     </div>
	                 </div>
	             </div>
	             <div class="block">
	                 <span class="lable w-75">级别名称:</span>
	                 <div class="b-input w-170">
	                     <div class="input-wrap w-p-100">
	                         <input name="interfaceProvider.fullName" id="fullname" required type="text" class="input-text">
	                     </div>
	                 </div>
	             </div>
	             <div class="block">
	                 <span class="lable w-75">描述:</span>
	                 <div class="b-input w-170">
	                     <div class="input-wrap w-p-100">
	                         <input name="interfaceProvider.shortName" id="shortname" required type="text" class="input-text" >
	                     </div>
	                 </div>
	             </div>
			</div>
			<input style="display:none" type="submit" class="btn btn-submit" value="确认修改" />
			<input style="display:none" type="reset" class="btn rest_btn" value="重 置"  />
		</form>
		<iframe data-content-success="<p>修改成功</p>" data-content-fail="<p>修改失败</p>" name="Result" id="Result" src="" class="hidden"></iframe>
	
	</div>
</div>
<%@ include file="../include/bodyLink.jsp"%>
<script>
	var vd = $('#addform').Validator({
		submitHandler: function(form, status) {
			if (status === false) {
				window.hotModal.hotButton[0].End();
			}
		}
	});
	$('.rest_btn').click(function() {
		setTimeout(function() {
			vd.form(function() {});
		}, 100);
	});
</script>
</body>
</html>