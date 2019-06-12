<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<title>认证修改</title>
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
				action="${pageContext.request.contextPath}/updateAuthConfig.action"
				method="post" id="form" prompt="DropdownMessage" data-success="修改成功"
				data-fail="修改失败">
				<div class="fix">
					<div class="item">
						<div class="input-area">
							<span class="label w-90">商户编号:</span>
							<div class="input-wrap">
								<input type="text" class="input-text"
									name="authConfigBean.customerNo" value="${authConfigBean.customerNo }" readonly="readonly" />
							</div>
						</div>
					</div>

					<div class="item">
						<div class="input-area">
							<span class="label w-90">商户名称:</span>
							<div class="input-wrap">
								<input type="text" class="input-text" readonly="readonly"
									name="authConfigBean.customerName" value="${authConfigBean.customerName }" />
							</div>
						</div>
					</div>

					<div class="item">
						<div class="input-area">
							<span class="label w-90">业务类型:</span>
							<div class="input-wrap">
								<select name="authConfigBean.busiType" class="input-select" prompt="NextLineMessage" data-value="${authConfigBean.busiType }" required checkCustomerNo >
									<option value="IDENTITY_AUTH">身份认证</option>
									<option value="BINDCARD_AUTH">绑卡认证</option>
								</select>
							</div>
						</div>
					</div>

					<div class="item">
						<div class="input-area">
							<span class="label w-90">是否实时:</span>
							<div class="input-wrap">
								<dict:select name="authConfigBean.isActual" value="${authConfigBean.isActual }"
									styleClass="input-select" dictTypeId="IS_ACTUAL"></dict:select>
							</div>
						</div>
					</div>

					<div class="item">
						<div class="input-area">
							<span class="label w-90">路由模板:</span>
							<div class="input-wrap">
								<select class="input-select" data-value="${authConfigBean.routingTemplateCode }"
									name="authConfigBean.routingTemplateCode">
									<c:forEach items="${info }" var="template">
										<option value="${template.CODE}">${template.NAME}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					
					<div class="item">
						<div class="input-area">
							<span class="label w-90">状态:</span>
							<div class="input-wrap">
								<dict:select name="authConfigBean.status" value="${authConfigBean.status }"
									styleClass="input-select" dictTypeId="STATUS"></dict:select>
							</div>
						</div>
					</div>
					

					<div class="item">
						<div class="input-area">
							<span class="label w-90">备注:</span>
							<div class="input-wrap">
								<textarea class="input-text" name="authConfigBean.remark">${authConfigBean.remark }</textarea>
							</div>
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
    var customerNo = $('[name="authConfigBean.customerNo"]');
    var defaultValue = '${authConfigBean.busiType }';
    // 获取商户编号全称
    Compared.add('checkCustomerNo', function(val, params, ele, ansyFn) {
      if (val == defaultValue && val != '') {
    	  return Compared.toStatus(true);
      }
      Api.boss.authQueryCustomerNoBool(customerNo.val(), val, function(result) {
    	if (result == 'FALSE' || result == 'false') {
    		ansyFn(Compared.toStatus(false, '此商户不存在!'));
    	} else {
    		result = JSON.parse(result);
    		ansyFn(Compared.toStatus(result.status == '', '商户已存在此业务类型!'));
    	}
        
      });
    });
    
  </script>
</body>
</html>
