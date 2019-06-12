<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<head>
</head>

<body class="w-p-100 min-width" onload="query();">
	<div class="ctx-iframe">
		<div class="form-box mb-40 mt-20 ml-20">
			<input type="hidden" id="path"
				value="${pageContext.request.contextPath}" />
			<form action="toregCodeQueryQueryResult.action" method="post"
				class="rest-from" target="queryResult">

				<div class="ib">
					<div class="coll-1 ib">
						<div class="block">
							<span class="lable w-100">推荐人手机号:</span>
							<div class="b-input w-170">
								<div class="input-wrap w-p-100">
									<input type="text" class="input-text rest-text" placeholder=""
										name="customer_no" id="customer_no" />
								</div>
							</div>
						</div>
						<div class="block">
							<span class="lable w-100">使用者手机号:</span>
							<div class="b-input w-170">
								<div class="input-wrap w-p-100">
									<input type="text" class="input-text rest-text" placeholder=""
										name="account_no" id="account_no" />
								</div>
							</div>
						</div>
					
					</div>
						<div class="coll-2 ib">
							<div class="block">
								<span class="lable w-100">状态:</span>
								<div class="b-input w-170">
									<select name="customer_type" class="input-select">
										<option value="">全部</option>
										<option value="OPEN">已使用</option>
										<option value="INDIVIDUAL">未使用</option>
									</select>
								</div>
							</div>

							<div class="block">
								<span class="lable w-100">使用日期:</span>
								<div class="ib v-m">
									<div class="input-wrap w-170">
										<input type="text" class="input-text input-date rest-text"
											placeholder="" name="create_time_start" id="createTimeStart">
									</div>
									<span class="cut"> - </span>
									<div class="input-wrap w-170">
										<input type="text" class="input-text input-date rest-text"
											placeholder="" name="create_time_end" id="createTimeEnd">
									</div>
								</div>
							</div>
						</div>
						<div class="rowr-4 text-right mr-10">
							<button class="btn  btn-loading query-btn"
								href="javascript:void(0);" type="submit" id="sub">查询</button>
							<input type="reset" class="btn" value="重 置" />
							<button class="btn pop-modal btn-loading" data-title="添加注册码" data-url="toregCodeAdd.action" >添加注册码</button>
						</div>
				</div>
			</form>
		</div>
		<iframe name="queryResult" class="redirect table-result" src=""
			frameborder="0"></iframe>
	</div>
	<%@ include file="../include/bodyLink.jsp"%>
</body>