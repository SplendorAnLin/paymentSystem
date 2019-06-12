<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<title>交易卡</title>
<%@ include file="../include/header.jsp"%>
</head>
<body>
	<div class="query-box">
		<!--查询表单-->
		<div class="query-form mt-10">
			<form action="transCardQuery.action" method="post" target="query-result">
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
							<span class="label w-90">卡号:</span>
							<div class="input-wrap">
								<input type="text" class="input-text" name="account_no">
							</div>
						</div>
					</div>
					<div class="item">
						<div class="input-area">
							<span class="label w-90">卡状态:</span>
							<div class="input-wrap">
								<dict:select nullOption="true" styleClass="input-select" name="card_status" dictTypeId="TRANS_STATUS"></dict:select>
							</div>
						</div>
					</div>
					<div class="item">
						<div class="input-area">
							<span class="label w-90">卡类型:</span>
							<div class="input-wrap">
								<dict:select nullOption="true" styleClass="input-select" name="card_type" dictTypeId="TRANS_TYPE"></dict:select>
							</div>
						</div>
					</div>
					<div class="item">
						<div class="input-area">
							<span class="label w-90">结算卡编号:</span>
							<div class="input-wrap">
								<input type="text" class="input-text" name="settle_code">
							</div>
						</div>
					</div>
					<div class="item">
						<div class="input-area">
							<span class="label w-90">编号:</span>
							<div class="input-wrap">
								<input type="text" class="input-text" name="code">
							</div>
						</div>
					</div>
					<div class="item">
						<div class="input-area">
							<span class="label w-90">身份证号:</span>
							<div class="input-wrap">
								<input type="text" class="input-text" name="id_number">
							</div>
						</div>
					</div>
					<div class="item">
						<div class="input-area">
							<span class="label w-90">卡属性:</span>
							<div class="input-wrap">
								<dict:select nullOption="true" styleClass="input-select" name="card_attr" dictTypeId="CARD_ATTR"></dict:select>
							</div>
						</div>
					</div>
					<div class="item">
						<div class="input-area">
							<span class="label w-90">绑卡时间:</span>
							<div class="input-wrap">
								<input type="text" name="tied_time_start"
									class="input-text double-input date-start default-time">
							</div>
							<span class="cut"> - </span>
							<div class="input-wrap">
								<input type="text" name="tied_time_end"
									class="input-text double-input date-end default-time-end">
							</div>
						</div>
					</div>
					<div class="item">
						<div class="input-area">
							<span class="label w-90">预留手机号:</span>
							<div class="input-wrap">
								<input type="text" class="input-text" name="phone">
							</div>
						</div>
					</div>
					<div class="item">
						<div class="input-area">
							<span class="label w-90">持卡人姓名:</span>
							<div class="input-wrap">
								<input type="text" class="input-text" name="account_name" />
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