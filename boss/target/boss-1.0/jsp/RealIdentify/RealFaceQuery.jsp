<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
	<head>
		 <%@ include file="../include/header.jsp"%>
		<title>认证信息</title>
	</head>

	<body>
		<div class="query-box">
			<!--查询表单-->
			<div class="query-form mt-10">
				<form action="dynamicBindCarInfo.action" method="post" target="query-result">
					<div class="adaptive fix">

						<div class="item">
							<div class="input-area">
								<span class="label w-90">姓名:</span>
								<div class="input-wrap">
									<input type="text" class="input-text" name="payerName">
								</div>
							</div>
						</div>

						<div class="item">
							<div class="input-area">
								<span class="label w-90">手机号:</span>
								<div class="input-wrap">
									<input type="text" class="input-text" name="payerMobNo">
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