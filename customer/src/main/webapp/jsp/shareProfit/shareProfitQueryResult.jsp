<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html class="w-p-100">
<head>
<script type="text/javascript">
	$(function() {
		initPage('${page.currentPage}', '${page.totalResult}',
				'${page.totalPage}', '${page.entityOrField}', parent.document
						.getElementById("form1"));
	});
</script>
<style>
.block {
	width: 450px;
	margin-bottom: 10px;
}
</style>
</head>
<body class="w-p-100 min-width">
	<div class="ctx-iframe">
		<div class="title-h1 fix">
			<span class="primary fl">查询结果</span> 
			 <a class="export-link f-12 fr"
				href="javascript:window.parent.spExport();">导出</a>
		</div>
				<div class="table-warp pd-r-10">
					<div class="table-scroll">
						<table class="table-two-color">
							<thead>
								<tr>
								<th>订单编号</th>
								<th>会员编号</th>
								<th>产品类型</th>
								<th>订单金额</th>
								<th>订单手续费</th>
								<th>商户费率</th>
								<th>推荐人费率</th>
								<th>会员费率</th>
								<th>利润</th>
								<th>分润类型</th>
								<th>分润比例</th>
								<th>订单创建时间</th>
								<th>订单完成时间</th>
								</tr>
							</thead>
							<tbody>
								
							</tbody>
						</table>
					</div>
				</div>
	</div>
	<%@ include file="../include/bodyLink.jsp"%>
</body>
</html>