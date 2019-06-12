<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
	<head>
		 <%@ include file="../include/header.jsp"%>
		<title>实名认证开通</title>
	</head>

	<body>
		<div class="query-box">
			<!--查询表单-->
			<div class="query-form mt-10">
				<form action="findAuthConfig.action" method="post" target="query-result">
					<div class="adaptive fix">

						<div class="item">
							<div class="input-area">
								<span class="label w-90">商户编号:</span>
								<div class="input-wrap">
									<input type="text" class="input-text" name="customerNo">
									</select>
								</div>
							</div>
						</div>
                         
                         <div class="item">
							<div class="input-area">
								<span class="label w-90">商户名称:</span>
								<div class="input-wrap">
									<input type="text" class="input-text" name="customerName">
								</div>
							</div>
						</div>
						
                         <div class="item">
							<div class="input-area">
								<span class="label w-90">状态:</span>
								<div class="input-wrap">
									<dict:select name="status" nullOption="true" styleClass="input-select" dictTypeId="STATUS"></dict:select>
								</div>
							</div>
						</div>
						
						<div class="item">
							<div class="input-area">
								<span class="label w-90">业务类型:</span>
								<div class="input-wrap">
									<dict:select name="busiType" nullOption="true" styleClass="input-select" dictTypeId="BUSI_TYPE"></dict:select>
								</div>
							</div>
						</div>

						<div class="item">
							<div class="input-area">
								<span class="label w-90">是否实时:</span>
								<div class="input-wrap">
									<dict:select name="isActual" nullOption="true" styleClass="input-select" dictTypeId="IS_ACTUAL"></dict:select>
								</div>
							</div>
						</div>
						
						<div class="item">
							<div class="input-area">
								<span class="label w-90">创建时间:</span>
								<div class="input-wrap">
									<input type="text" name="startCreateTime" class="input-text double-input date-start default-time">
								</div>
								<span class="cut"> - </span>
								<div class="input-wrap">
									<input type="text" name="endCreateTime" class="input-text double-input date-end default-time-end">
								</div>
							</div>
						</div>

					</div>

					<div class="text-center mt-10">
						<button class="btn query-btn">查 询</button>
						<a data-myiframe='{
                "target": "${pageContext.request.contextPath}/toAddReal.action",
                "btnType": 2,
                "btns": [
                  {"lable": "取消"},
                  {"lable": "新增", "trigger": ".btn-submit"}
                ]
              }' class="btn" href="javascript:void(0);">新增</a>
						<input type="reset" class="btn clear-form" value="重 置">
					</div>
				</form>
			</div>

			<!--查询结果框架-->
			<div class="query-result mt-20">
				<iframe name="query-result" frameborder="0"></iframe>
			</div>
		</div>
		 <%@ include file="../include/bodyLink.jsp"%>
	</body>

</html>