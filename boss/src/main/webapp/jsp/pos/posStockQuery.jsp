<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<title>POS申请</title>
<%@ include file="../include/header.jsp"%>
</head>
<body>
	<div class="query-box">
		<!--查询表单-->
		<div class="query-form mt-10">
			<form action="posStockQuery.action" method="post" target="query-result">
				<div class="adaptive fix">
					<div class="item">
						<div class="input-area">
							<span class="label w-90">商户编号:</span>
							<div class="input-wrap">
								<input type="text" class="input-text" name="customer_no">
							</div>
						</div>
					</div>
					<div class="item">
						<div class="input-area">
							<span class="label w-90">POS类型:</span>
							<div class="input-wrap">
								<dict:select nullOption="true" styleClass="input-select" name="pos_type" dictTypeId="POS_TYPE_ATTR"></dict:select>
							</div>
						</div>
					</div>
					<div class="item">
						<div class="input-area">
							<span class="label w-90">手机号:</span>
							<div class="input-wrap">
								<input type="text" class="input-text" name="phone">
							</div>
						</div>
					</div>
					<div class="item">
						<div class="input-area">
							<span class="label w-90">请求类型:</span>
							<div class="input-wrap">
								<dict:select nullOption="true" styleClass="input-select" name="request_record" dictTypeId="REQUEST_RECORD"></dict:select>
							</div>
						</div>
					</div>
					<div class="item">
						<div class="input-area">
							<span class="label w-90">请求时间:</span>
							<div class="input-wrap">
								<input type="text" name="time_start"
									class="input-text double-input date-start default-time">
							</div>
							<span class="cut"> - </span>
							<div class="input-wrap">
								<input type="text" name="time_end"
									class="input-text double-input date-end default-time-end">
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