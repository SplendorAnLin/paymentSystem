<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html>
<head>
</head>
<body class="over-y-h">
	<div class="in-pop" style="width: 400px;padding: 20px 0;">
		<form action="updateInterfaceProvider.action" method="post" id="updateform" target="Result" style="margin-left: 70px;">
			<input type="hidden" value="${interfaceProviderBean.code}" name="interfaceProvider.code"/>
	        <div class="block">
	          <span class="lable w-75">级别编号:</span> 
	          <span class="static-lable c_gray v-m">JPDL</span>
	        </div>
		  <div class="block">
	        <span class="lable w-75">级别名称:</span>
	        <div class="b-input w-170">
	          <div class="input-wrap w-p-100">
	            <input type="text" name="interfaceProvider.shortName" id="shortName" required value="${interfaceProviderBean.shortName}" class="input-text" >
	          </div>
	        </div>
	      </div>
		  <div class="block">
	        <span class="lable w-75">级别描述:</span>
	        <div class="b-input w-170">
	          <div class="input-wrap w-p-100">
	            <input type="text" name="interfaceProvider.fullName" id="fullname" required value="${interfaceProviderBean.fullName}" class="input-text" >
	          </div>
	        </div>
	      </div>
			
			<input style="display:none" type="submit" class="btn btn-submit" value="确认修改" />
			<input style="display:none" type="reset" class="btn rest_btn" value="重 置"  />
		</form>
		<iframe data-content-success="<p>修改成功</p>" data-content-fail="<p>修改失败</p>" name="Result" id="Result" src="" class="hidden"></iframe>
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