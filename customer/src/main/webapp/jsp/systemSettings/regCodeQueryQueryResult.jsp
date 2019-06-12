<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>

<html class="w-p-100">
<head>
<script type="text/javascript">
	function toUpdate(code) {
		var url = "${pageContext.request.contextPath}/toUpdateCustomerSettle.action?customerSettle.customerNo="
				+ code + "";
		window.location.href = url;
	}
	function toHistory(code) {
		var url = "${pageContext.request.contextPath}/customerSettleHistByCustNo.action?customer_no="
				+ code + "";
		window.location.href = url;
	}
</script>
</head>
<body class="w-p-100 min-width">
	<div class="ctx-iframe">
		<div class="title-h1 fix">
			<span class="primary fl">查询结果</span>
		</div>
<%-- 		<s:if test="#attr['customerSettle'].list.size()>0">
			<vlh:root value="customerSettle" url="?" includeParameters="*"
				configName="defaultlook"> --%>
				<div class="table-warp pd-r-10">
					<div class="table-scroll">
						<table class="table-two-color">
							<thead>
								<tr>
									<th>序号</th>
									<th>注册码</th>
									<th>注册链接</th>
									<th>状态</th>
									<th>会员等级</th>
									<th>推荐人手机号</th>
									<th>使用者手机号</th>
									<th>使用时间</th>
									<th>操作</th>
								</tr>
								</thead>
								<%-- <vlh:row bean="customerSettle">
									<s:set name="entity" value="#attr['customerSettle']" />
									<vlh:column property="customer_no" />
									<vlh:column property="account_name" />
									<vlh:column property="account_no" />
									<vlh:column>
										<c:if test="${entity.customer_type eq 'INDIVIDUAL'}">
							对私
						</c:if>
										<c:if test="${entity.customer_type eq 'OPEN'}">
							对公
						</c:if>
									</vlh:column>
									<vlh:column property="bank_code">
										<c:if test="${entity.bank_code eq 'BOC'}">
							中国银行
						</c:if>
										<c:if test="${entity.bank_code eq 'ABC'}">
							农业银行
						</c:if>
										<c:if test="${entity.bank_code eq 'ICBC'}">
							工商银行
						</c:if>
										<c:if test="${entity.bank_code eq 'CCB'}">
							建设银行
						</c:if>
										<c:if test="${entity.bank_code eq 'CMB'}">
							招商银行
						</c:if>
										<c:if test="${entity.bank_code eq 'CMBC'}">
							民生银行
						</c:if>
										<c:if test="${entity.bank_code eq 'SPDB'}">
							浦发银行
						</c:if>
										<c:if test="${entity.bank_code eq 'CEB'}">
							光大银行
						</c:if>
										<c:if test="${entity.bank_code eq 'CGB'}">
							广发银行
						</c:if>
										<c:if test="${entity.bank_code eq 'CNCB'}">
							中信银行
						</c:if>
										<c:if test="${entity.bank_code eq 'PSBC'}">
							邮政储蓄银行
						</c:if>
										<c:if test="${entity.bank_code eq 'BCM'}">
							交通银行
						</c:if>
										<c:if test="${entity.bank_code eq 'PAB'}">
							平安银行
						</c:if>
										<c:if test="${entity.bank_code eq 'BOB'}">
							北京银行
						</c:if>
										<c:if test="${entity.bank_code eq 'BOHB'}">
							河北银行
						</c:if>
										<c:if test="${entity.bank_code eq 'CIB'}">
							兴业银行
						</c:if>
										<c:if test="${entity.bank_code eq 'CZB'}">
							浙商银行
						</c:if>
										<c:if test="${entity.bank_code eq 'BOS'}">
							上海银行
						</c:if>
										<c:if test="${entity.bank_code eq 'HSB'}">
							徽商银行
						</c:if>
										<c:if test="${entity.bank_code eq 'HXB'}">
							华夏银行
						</c:if>
										<c:if test="${entity.bank_code eq 'HCCB'}">
							杭州银行
						</c:if>
										<c:if test="${entity.bank_code eq 'NBCB'}">
							宁波银行
						</c:if>
										<c:if test="${entity.bank_code eq 'BEA'}">
							东亚银行
						</c:if>
										<c:if test="${entity.bank_code eq 'NJCB'}">
							南京银行
						</c:if>
										<c:if test="${entity.bank_code eq 'HRBCB'}">
							哈尔滨银行
						</c:if>
										<c:if test="${entity.bank_code eq 'CQRCB'}">
							重庆农商银行
						</c:if>
									</vlh:column>
									<vlh:column property="open_bank_name" />
									<vlh:column>
										<s:date name="#entity.create_time"
											format="yyyy-MM-dd HH:mm:ss" />
									</vlh:column>
									<vlh:column attributes="width='10%'">
									<button
										data-url="${pageContext.request.contextPath}/toUpdateCustomerSettle.action?customerSettle.customerNo=${entity.customer_no}"
										data-title="修改结算信息" data-rename="修改"
										class="pop-modal btn-loading btn mr-10 mb-10 btn-size-small">修改</button>
									<button
										data-url="${pageContext.request.contextPath}/customerSettleHistByCustNo.action?customer_no=${entity.customer_no}"
										data-title="操作历史" data-nofoot="true"
										class="pop-modal btn-loading btn mb-10 btn-size-small">历史信息</button>
									</vlh:column>
								</vlh:row> --%>
								<tbody>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td><button
										data-url="${pageContext.request.contextPath}/toregCodeEdit.action"
										data-title="修改注册码信息" data-rename="修改"
										class="pop-modal btn-loading btn mr-10 mb-10 btn-size-small">修改</button></td>
								</tr>
								</tbody>
						</table>
					</div>
				<%-- </div>
				<%@ include file="../include/vlhPage.jsp"%>
			</vlh:root>
		</s:if>
		<s:if test="#attr['customerSettle'].list.size()==0">
		无符合条件的查询结果
	</s:if> --%>
	</div>
	<%@ include file="../include/bodyLink.jsp"%>
</body>
</html>