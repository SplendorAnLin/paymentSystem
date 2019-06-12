<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
	<head>
		 <%@ include file="../include/header.jsp"%>
		<title>实名认证订单</title>
	</head>

	<body>
		<div class="query-box">
			<!--查询表单-->
			<div class="query-form mt-10">
				<form action="dynamicRealNameAuthOrder.action" method="post" target="query-result">
					<div class="adaptive fix">
                         
                         <div class="item">
							<div class="input-area">
								<span class="label w-90">订单号:</span>
								<div class="input-wrap">
									<input type="text" class="input-text" name="requestCode">
								</div>
							</div>
						</div>
						
						<div class="item">
							<div class="input-area">
								<span class="label w-90">接口订单号:</span>
								<div class="input-wrap">
									<input type="text" class="input-text" name="interfaceRequestId">
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
								<span class="label w-90">交易状态:</span>
								<div class="input-wrap">
									<dict:select name="authOrderStatus" nullOption="true" styleClass="input-select" dictTypeId="AUTH_ORDER_STATUS"></dict:select>
								</div>
							</div>
						</div>


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
								<span class="label w-90">通道编号:</span>
								<div class="input-wrap">
									<input type="text" class="input-text" name="interfaceCode">
								</div>
							</div>
						</div>

						
                         <div class="item">
							<div class="input-area">
								<span class="label w-90">认证状态:</span>
								<div class="input-wrap">
									<dict:select name="authResult" nullOption="true" styleClass="input-select" dictTypeId="AUTH_RESULT"></dict:select>
								</div>
							</div>
						</div>
						
						<div class="item">
							<div class="input-area">
								<span class="label w-90">完成时间:</span>
								<div class="input-wrap">
									<input type="text" name="startCompleteTime" class="input-text double-input date-start default-time">
								</div>
								<span class="cut"> - </span>
								<div class="input-wrap">
									<input type="text" name="endCompleteTime" class="input-text double-input date-end default-time-end">
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
								<span class="label w-90">合作方编号:</span>
								<div class="input-wrap">
									<input type="text" class="input-text" name="customerNo">
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
								<span class="label w-90">清算时间:</span>
								<div class="input-wrap">
									<input type="text" name="startClearTime" class="input-text double-input date-start default-time">
								</div>
								<span class="cut"> - </span>
								<div class="input-wrap">
									<input type="text" name="endClearTime" class="input-text double-input date-end default-time-end">
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