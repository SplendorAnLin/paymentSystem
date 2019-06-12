<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<head>
<script type="text/javascript">	
	   //跳转到新增服务商费率页面
		function agentFeeAdd(){
			var url = contextPath + "/toAgentFeeAdd.action";
			window.location.href=url;
		}
	</script>
</head>
<body class="w-p-100 min-width" onload="query();">
	<div class="ctx-iframe">
		<div class="form-box mb-40 mt-20 ml-20">
			<form class="rest-from" action="toLevelFeeQueryList.action" method="post"
				target="queryResult">
				<div class="coll-1 ib">
					<div class="block">
						<span class="lable w-75">级别编号:</span>
						<div class="b-input w-170">
							<div class="input-wrap w-p-100">
								<input class="input-text rest-text" placeholder="" type="text"
									name="agentNo" />
							</div>
						</div>
					</div>
				</div>
				<div class="coll-2 ib mr-20">
					<div class="block">
						<span class="lable w-75">级别名称:</span>
						<div class="b-input w-170">
							<div class="input-wrap w-p-100">
								<input class="input-text rest-text" placeholder="" type="text"
									name="fullName" />
							</div>
						</div>
					</div>
				</div>
				<div class="coll-3 ib">
					<button class="btn  btn-loading query-btn">查询</button>
					<a class="btn rest-btn" href="javascript:void(0);">重置</a>
				</div>
				<div class="coll-4 ib">
					<a data-url="toLevelFeeAdd.action" data-title="新增级别费率"
						data-rename="新增" class="btn pop-modal btn-loading"
						href="javascript:void(0);">新增级别费率</a>
				</div>
			</form>
		</div>
		<br>
		<iframe name="queryResult" class="redirect table-result" src=""
			frameborder="0"></iframe>
	</div>
	<%@ include file="../include/bodyLink.jsp"%>
</body>