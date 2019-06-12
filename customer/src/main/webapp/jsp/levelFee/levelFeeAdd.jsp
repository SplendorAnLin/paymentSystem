<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>

<html class="over-x-h">
<head>
<style>
.lable {
	margin-right: 10px;
}
</style>
<script type="text/javascript">
	function showMsg(msg) {
		new window.top.Message({
			content : '<p>' + msg + '</p>',
			type : 3
		});
		if (window.hotModal && window.hotModal.hotButton) {
			window.hotModal.hotButton[0].End()
		}
	}
	
	function modify() {
		var param = "agentFee.agentNo=" + $("#agentNo").val()
				+ "&agentFee.productType=" + $("#productType").val();
		$.post("checkAgentFeeAction.action", param, function(result) {
			if (result == "TRUE") {
				$("#updateForm").submit();
				if (window.hotModal && window.hotModal.hotButton) {
					window.hotModal.hotButton[0].End()
				}
			} else if (result == "EXISTS_REMIT") {
				showMsg('<p>未开通代付不能添加假日付费率信息！</p>');
			} else {
				showMsg('<p>该服务商已经存在该费率类型，不能添加！</p>');
			}
		});
	}
	
	function checkHasAgent() {
		var urls = contextPath
				+ "/findAgentFullNameByAgentNo.action?agent.agentNo="
				+ $.trim($('#agentNo').val());
		urls = encodeURI(encodeURI(urls));
		console.log(urls);
		$.ajax({
			type : "post",
			async : false,
			url : urls,
			success : function(msg) {
				if (null != msg) {
					$('#agentFee_fullName').val(msg);
				} else {
					new window.top.Message({
						content : "<p>服务商不存在！</p>",
						type : 3
					//0默认1确定2警告3错误4等待
					});
					$('#agentNo').val('');
					$('#agentFee_fullName').val('');
				}
			}
		});
	}
</script>
</head>
<body style="height: 100%">
	<div class="in-pop" style="width: 400px; padding: 20px;">
		<form class="rest-from verif-form" id="updateForm"
			action="createAgentFee.action" target="Result" method="post">
			<div class="block">
				<span class="lable w-90">费率类型:</span>
				<div class="b-input w-170">
					<select type="text" name="agentFee.feeType" class="input-select ">
						<option value="FIXED"
							<c:if test="${agentFee.feeType eq 'FIXED'}"></c:if>>固定</option>
						<option value="RATIO"
							<c:if test="${agentFee.feeType eq 'RATIO'}"></c:if>>百分比</option>
					</select>
				</div>
			</div>
			<div class="block">
				<span class="lable w-90">分润类型:</span>
				<div class="b-input w-170">
					<select type="text" name="agentFee.profitType" class="input-select">
						<option value="RATIO_PROFIT"
							<c:if test="${agentFee.profitType eq 'RATIO_PROFIT'}"></c:if>>百分比</option>
						<option value="FIXED_PROFIT"
							<c:if test="${agentFee.profitType eq 'FIXED_PROFIT'}"></c:if>>固定费率</option>
					</select>
				</div>
			</div>
			<div class="block">
				<span class="lable w-90">产品类型:</span>
				<div class="b-input w-170">
					<select type="text" name="agentFee.productType" id="productType"
						class="input-select">
						<option value="B2C"
							<c:if test="${agentFee.productType == 'B2C'}">selected="selected"</c:if>>个人网银</option>
						<option value="B2B"
							<c:if test="${agentFee.productType == 'B2B'}">selected="selected"</c:if>>企业网银</option>
						<option value="AUTHPAY"
							<c:if test="${agentFee.productType == 'AUTHPAY'}">selected="selected"</c:if>>认证支付</option>
						<option value="REMIT"
							<c:if test="${agentFee.productType == 'REMIT'}">selected="selected"</c:if>>代付</option>
						<option value="WXJSAPI"
							<c:if test="${agentFee.productType == 'WXJSAPI'}">selected="selected"</c:if>>微信公众号支付</option>
						<option value="WXNATIVE"
							<c:if test="${agentFee.productType == 'WXNATIVE'}">selected="selected"</c:if>>微信二维码支付</option>
						<option value="ALIPAY"
							<c:if test="${agentFee.productType == 'ALIPAY'}">selected="selected"</c:if>>支付宝</option>
						<option value="HOLIDAY_REMIT"
							<c:if test="${agentFee.productType == 'HOLIDAY_REMIT'}">selected="selected"</c:if>>假日付</option>
						<option value="RECEIVE"
							<c:if test="${agentFee.productType == 'RECEIVE'}">selected="selected"</c:if>>代收</option>
					</select>
				</div>
			</div>
			<div class="block">
				<span class="lable w-90">级别编号:</span>
				<div class="b-input w-170">
					<div class="input-wrap w-p-100">
						<input class="input-text verif-behoove rest-text" placeholder=""
							type="text" id="agentNo" name="agentFee.agentNo"
							value="${agentFee.agentNo}" onchange="checkHasAgent();">
						<input type="hidden" name="agentFee.id" value="${agentFee.id}">
					</div>
				</div>
			</div>
			<div class="block">
				<span class="lable w-90">级别名称:</span>
				<div class="b-input w-170">
					<div class="input-wrap w-p-100">
						<input class="input-text verif-behoove rest-text" placeholder=""
							type="text" id="agentFee_fullName" value="" disabled="disabled">
					</div>
				</div>
			</div>
			<div class="block">
				<span class="lable w-90">费率:</span>
				<div class="b-input w-170">
					<div class="input-wrap w-p-100">
						<input type="text" class="input-text verif-behoove  rest-text "
							placeholder="" name="agentFee.fee" value="${agentFee.fee}" />
					</div>
				</div>
			</div>
			<div class="block">
				<span class="lable w-90">分润比例:</span>
				<div class="b-input w-170">
					<div class="input-wrap w-p-100">
						<input type="text" class="input-text verif-behoove  rest-text "
							placeholder="" name="agentFee.profitRatio"
							value="${agentFee.profitRatio}" />
					</div>
				</div>
			</div>
			
			<input type="hidden" class="btn-submit verif-btn" value="新增" />
		</form>
		<iframe data-content-success="<p>新增成功</p>"
			data-content-fail="<p>新增失败</p>" name="Result" id="Result" src=""
			class="hidden"></iframe>
	</div>
	<%@ include file="../include/bodyLink.jsp"%>
	<script type="text/javascript">
		$(".btn-submit").click(function(event) {
			// 如果表单验证失败, 则返回
			if (event.isDefaultPrevented()) {
				return;
			}
			modify();
		})
	</script>
</body>
</html>