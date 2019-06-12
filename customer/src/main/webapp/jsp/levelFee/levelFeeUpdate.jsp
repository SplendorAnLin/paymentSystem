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
	function backQuery() {
		var url = "${pageContext.request.contextPath}/agentFeeQuery.action";
		window.location.href = url;
	}
	function modify() {
		$("#updateForm").submit();
	}
	function checkHasAgent() {
		var urls = "findAgentFullNameByAgentNo.action?agent.agentNo="
				+ $('#agentNo').val();
		$.ajax({
			type : "post",
			async : false,
			url : urls,
			success : function(msg) {
				if (null != msg) {
					$('.agentName').val(msg);
				}
			}
		});
	}
</script>
</head>
<body onload="checkHasAgent();">
	<div class="in-pop" style="width: 400px; padding: 20px;">
		<form class="rest-from verif-form" id="updateForm"
			action="updateAgentFee.action" target="Result" method="post">
			<div class="block">
				<span class="lable w-90">费率类型:</span>
				<div class="b-input w-170">
					<select type="text" name="agentFee.feeType" class="input-select ">
						<option value="FIXED"
							<c:if test="${agentFee.feeType eq 'FIXED'}">selected="selected"</c:if>>固定</option>
						<option value="RATIO"
							<c:if test="${agentFee.feeType eq 'RATIO'}">selected="selected"</c:if>>百分比</option>
					</select>
				</div>
			</div>
			<div class="block">
				<span class="lable w-90">分润类型:</span>
				<div class="b-input w-170">
					<select type="text" name="agentFee.profitType" class="input-select ">
						<option value="FIXED_PROFIT"
							<c:if test="${agentFee.profitType eq 'FIXED_PROFIT'}">selected="selected"</c:if>>固定费率</option>
						<option value="RATIO_PROFIT"
							<c:if test="${agentFee.profitType eq 'RATIO_PROFIT'}">selected="selected"</c:if>>百分比</option>
					</select>
				</div>
			</div>
			<div class="block">
				<span class="lable w-90">产品类型:</span>
				<div class="b-input w-170">
					<select disabled="disabled" class="input-select ">
						<c:if test="${agentFee.productType == 'B2C'}">
							<option value="B2C">个人网银</option>
						</c:if>
						<c:if test="${agentFee.productType == 'B2B'}">
							<option value="B2B">企业网银</option>
						</c:if>
						<c:if test="${agentFee.productType == 'AUTHPAY'}">
							<option value="AUTHPAY">认证支付</option>
						</c:if>
						<c:if test="${agentFee.productType == 'REMIT'}">
							<option value="REMIT">代付</option>
						</c:if>
						<c:if test="${agentFee.productType == 'WXJSAPI'}">
							<option value="WXJSAPI">微信公众号支付</option>
						</c:if>
						<c:if test="${agentFee.productType == 'WXNATIVE'}">
							<option value="WXNATIVE">微信二维码支付</option>
						</c:if>
						<c:if test="${agentFee.productType == 'ALIPAY'}">
							<option value="ALIPAY">支付宝</option>
						</c:if>
						<c:if test="${agentFee.productType == 'HOLIDAY_REMIT'}">
							<option value="HOLIDAY_REMIT">假日付</option>
						</c:if>
						<c:if test="${agentFee.productType == 'RECEIVE'}">
							<option value="RECEIVE">代收</option>
						</c:if>
					</select>
				</div>
			</div>
			<div class="block">
				<span class="lable w-90">级别编号:</span>
				<div class="b-input w-170">
					<div class="input-wrap w-p-100">
						<input type="text" name="agentFee.agentNo"
							value="${agentFee.agentNo}" disabled="disabled" id="agentNo"
							class="input-text verif-behoove  rest-text" placeholder="">
						<input type="hidden" name="agentFee.id" value="${agentFee.id}">
					</div>
				</div>
			</div>
			<div class="block">
				<span class="lable w-90">级别名称:</span>
				<div class="b-input w-170">
					<div class="input-wrap w-p-100">
						<input type="text" name="agent.fullName" value="${agent.fullName}"
							disabled="disabled"
							class="input-text verif-behoove  rest-text agentName"
							placeholder="">
					</div>
				</div>
			</div>
			<div class="block">
				<span class="lable w-90">费率:</span>
				<div class="b-input w-170">
					<div class="input-wrap w-p-100">
						<input class="input-text verif-behoove   rest-text " placeholder=""
							name="agentFee.fee" value="${agentFee.fee}" />
					</div>
				</div>
			</div>
			<div class="block">
				<span class="lable w-90">分润比例:</span>
				<div class="b-input w-170">
					<div class="input-wrap w-p-100">
						<input class="input-text verif-behoove  rest-text " placeholder=""
							name="agentFee.profitRatio" value="${agentFee.profitRatio}" />
					</div>
				</div>
			</div>
			<input type="hidden" class="btn-submit verif-btn" value="修改" />
		</form>

		<iframe data-content-success="<p>修改成功</p>"
			data-content-fail="<p>修改失败</p>" name="Result" id="Result" src=""
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
		/* // 费率类型切换正则验证
		$('.rate-select', $('[data-selectpop]')).change(
				function() {
					var fixed = '^([1-9][\\d]{0,7}|0)(\\.[\\d]{1,2})?$';
					var dom = $('.rote-type', $(this).parents(
							'.select-pop-area'));
					// 获取当前选中值
					var value = $(this).val();

					dom.each(function() {
						var element = $(this);
						if (!element.data('min')) {
							element.data('min', element.attr('data-min'));
						}
						if (!element.data('max')) {
							element.data('max', element.attr('data-max'));
						}
						if (!element.data('placeholder')) {
							element.data('placeholder', element
									.attr('placeholder'));
						}

						if (value == 'RATIO') {
							element.removeAttr('data-verifi').attr({
								'data-min' : element.data('min'),
								'data-max' : element.data('max'),
								'placeholder' : element.data('placeholder'),
							});
						} else {
							element.attr('data-verifi', fixed).removeAttr(
									'data-min').removeAttr('data-max')
									.removeAttr('placeholder');
						}
					});
				});
		$('.sp-select', $('[data-selectpop]')).change(
				function() {
					var fixed = '^([1-9][\\d]{0,7}|0)(\\.[\\d]{1,2})?$';
					var dom = $('.sp-type', $(this).parents(
							'.select-pop-area'));
					// 获取当前选中值
					var value = $(this).val();

					dom.each(function() {
						var element = $(this);
						if (!element.data('min')) {
							element.data('min', element.attr('data-min'));
						}
						if (!element.data('max')) {
							element.data('max', element.attr('data-max'));
						}
						if (!element.data('placeholder')) {
							element.data('placeholder', element
									.attr('placeholder'));
						}

						if (value == 'FIXED_RATIO') {
							element.removeAttr('data-verifi').attr({
								'data-min' : element.data('min'),
								'data-max' : element.data('max'),
								'placeholder' : element.data('placeholder'),
							});
						} else {
							element.attr('data-verifi', fixed).removeAttr(
									'data-min').removeAttr('data-max')
									.removeAttr('placeholder');
						}
					});
				}); */
		//效验end
	</script>
</body>
</html>