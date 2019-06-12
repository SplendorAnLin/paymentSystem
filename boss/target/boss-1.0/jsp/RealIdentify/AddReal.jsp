<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<title>认证新增</title>
<%@ include file="../include/header.jsp"%>
<style>
.w-110 {
	width: 110px;
}
</style>
</head>
<body style="width: 410px; padding: 0.2em;">

	<div class="query-box" style="padding: 0.8em 3em;">
		<!--查询表单-->
		<div class="query-form">
			<form class="validator notification"
				action="${pageContext.request.contextPath}/addAuthConfig.action"
				method="post" id="form" prompt="DropdownMessage" data-success="新增成功"
				data-fail="新增失败">
				<div class="fix">
					<div class="item">
						<div class="input-area">
							<span class="label w-90">商户编号:</span>
							<div class="input-wrap">
								<input type="text" class="input-text"
									name="authConfigBean.customerNo" required checkCustomerNo trigger='{"checkCustomerNo": 2}' />
							</div>
						</div>
					</div>

					<div class="item">
						<div class="input-area">
							<span class="label w-90">商户名称:</span>
							<div class="input-wrap">
								<input type="text" class="input-text" readonly="readonly"
									name="authConfigBean.customerName" required />
							</div>
						</div>
					</div>

					<div class="item">
						<div class="input-area">
							<span class="label w-90">业务类型:</span>
							<div class="input-wrap">
								<dict:select name="authConfigBean.busiType"
									styleClass="input-select" dictTypeId="BUSI_TYPE"></dict:select>
							</div>
						</div>
					</div>

					<div class="item">
						<div class="input-area">
							<span class="label w-90">是否实时:</span>
							<div class="input-wrap">
								<dict:select name="authConfigBean.isActual"
									styleClass="input-select" dictTypeId="IS_ACTUAL"></dict:select>
							</div>
						</div>
					</div>

					<div class="item">
						<div class="input-area">
							<span class="label w-90">路由模板:</span>
							<div class="input-wrap">
								<select class="input-select" name="authConfigBean.routingTemplateCode">
									<c:forEach items="${info }" var="template">
										<option value="${template.CODE}">${template.NAME}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>

					<div class="item">
						<div class="input-area">
							<span class="label w-90">备注:</span>
							<div class="input-wrap">
								<textarea class="input-text" name="authConfigBean.remark"></textarea>
							</div>
						</div>
					</div>
				</div>
		<div class="text-center mt-10 hidden">
			<button class="btn btn-submit">新 增</button>
		</div>
		</form>
		</div>
	</div>

	<%@ include file="../include/bodyLink.jsp"%>
  <script>
    var productType = $('[name="authConfigBean.busiType"]');
    var customerName = $('[name="authConfigBean.customerName"]');
    
    // 获取商户编号全称
    Compared.add('checkCustomerNo', function(val, params, ele, ansyFn) {
      Api.boss.authQueryCustomerNoBool(val, productType.val(), function(result) {
    	if (result == 'FALSE' || result == 'false') {
    		customerName.val('');
    		ansyFn(Compared.toStatus(false, '此商户不存在!'));
    	} else {
    		result = JSON.parse(result);
    		customerName.val(result.fullName);
    		console.log(result.status);
    		ansyFn(Compared.toStatus(result.status == '', '商户已存在此业务类型!'));
    	}
        
      });
    });
    
    productType.change(function() {
    	$('[name="authConfigBean.customerNo"]').trigger('change');
    });
    
    
  </script>
</body>
</html>
