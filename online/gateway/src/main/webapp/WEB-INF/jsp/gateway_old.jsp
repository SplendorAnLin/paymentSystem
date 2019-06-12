<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp"%>
<%@ include file="include/commons-include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="聚合支付,网关,在线支付">
<meta http-equiv="description" content="聚合支付网关">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="${applicationScope.staticFilesHost}${requestScope.contextPath}/css/global.css" />
<link type="text/css" rel="stylesheet" href="${applicationScope.staticFilesHost}${requestScope.contextPath}/css/index.css" />
</head>

<body>
	<form action="pay.htm" method="post" id="LFFormBk">
		<div class="LFPay" id="LFPayId">
			<div class="clearfix mb15">
				<span class="lc1 fr"></span>
			</div>
			<div class="clear"></div>
			<div class="mes clearfix">
				<i class="t1"></i><i class="t2"></i>
				<div class="mesicon"></div>
				<div class="mestext">
					<p>订单提交成功，请您尽快支付！</p>
					<p>应付金额：<i>${result.amount }元</i></p>
					<p>商户名称：${result.partnerName }</p>
					<p>订单号：${result.outOrderId}</p>
					<input type="hidden" name="tradeOrderCode" value="${result.tradeOrderCode }" />
					<input type="hidden" name="outOrderID" value="${result.outOrderId }" />
					<input type="hidden" name="amount" value="${result.amount }" />
					<input type="hidden" name="apiCode" value="${result.apiCode }" />
					<input type="hidden" name="versionCode" value="${result.versionCode }" />
				</div>
			</div>
			<p class="f14 mt20 mb10 fb Lfplay">请选择支付方式</p>
			<div class="LFPayTab">
				<c:if test="${result.interfaceCodes.DEBIT_CARD != null}"><a class="LFPayTabActive" href="javascript:void(0)">储蓄卡</a></c:if>
				<c:if test="${result.interfaceCodes.CREDIT_CARD != null}"><a 
					<c:if test="${result.interfaceCodes.DEBIT_CARD == null}">class="LFPayTabActive"</c:if> href="javascript:void(0)">信用卡</a></c:if>
				<c:if test="${result.interfaceCodes.B2B != null}"><a 
					<c:if test="${result.interfaceCodes.DEBIT_CARD == null && result.interfaceCodes.CREDIT_CARD == null}">
					class="LFPayTabActive"</c:if> href="javascript:void(0)">企业网银</a></c:if>
				<c:if test="${result.interfaceCodes.OFFLINE != null}"><a 
					<c:if test="${result.interfaceCodes.DEBIT_CARD == null && result.interfaceCodes.CREDIT_CARD == null 
					&& result.interfaceCodes.B2B == null}">class="LFPayTabActive"</c:if> href="javascript:void(0)">乐付通</a></c:if>
				<c:if test="${result.interfaceCodes.AUTHPAY != null}"><a 
					<c:if test="${result.interfaceCodes.DEBIT_CARD == null && result.interfaceCodes.CREDIT_CARD == null 
					&& result.interfaceCodes.B2B == null && result.interfaceCodes.OFFLINE == null}">
					class="LFPayTabActive"</c:if> href="javascript:void(0)">认证</a></c:if>
			</div>
			<!--LFPayTab-->
			<div class="clear"></div>
			<div class="LFPayC">
				<c:if test="${result.interfaceCodes.DEBIT_CARD != null}">
					<div class="LFPayCC" style="display: block">
						<ul>
							<c:forEach items="${result.interfaceCodes.DEBIT_CARD}" var="bank">
								<li>
									<input style="cursor: pointer;" type="radio" id="${bank }" name="interfaceCode" value="${bank }">
          								<label for="${bank }"><span class="${bank }"></span></label>
								</li>
							</c:forEach>
						</ul>
						<p><a href="javascript:;">更多银行</a></p>
						<p class="tr mt15 trs"><input type="button" id="btnGateway" onclick="disDoubleClick();" class="btn1" value=""></p>
					</div>
				</c:if>
				<c:if test="${result.interfaceCodes.CREDIT_CARD != null}">
					<div class="clear"></div>
					<div class="LFPayCC" <c:if test="${result.interfaceCodes.DEBIT_CARD == null}">style="display: block"</c:if>>
						<ul>
							<c:forEach items="${result.interfaceCodes.CREDIT_CARD}" var="bank">
								<li>
									<input style="cursor: pointer;" type="radio" id="${bank }" name="interfaceCode" value="${bank }">
          								<label for="${bank }"><span class="${bank }"></span></label>
								</li>
							</c:forEach>
						</ul>
						<p><a href="javascript:;">更多银行</a></p>
						<p class="tr mt15 trs"><input type="button" id="btnGateway" onclick="disDoubleClick();" class="btn1" value=""></p>
					</div>
				</c:if>
				<c:if test="${result.interfaceCodes.B2B != null}">
					<div class="clear"></div>
					<div class="LFPayCC" <c:if test="${result.interfaceCodes.DEBIT_CARD == null
					&& result.interfaceCodes.CREDIT_CARD == null}">style="display: block"</c:if>>
						<ul>
							<c:forEach items="${result.interfaceCodes.B2B}" var="bank">
								<li>
									<input style="cursor: pointer;" type="radio" id="${bank }" name="interfaceCode" value="${bank }">
         								<label for="${bank }"><span class="${bank }"></span></label>
								</li>
							</c:forEach>
						</ul>
						<p><a href="javascript:;">更多银行</a></p>
						<p class="tr mt15 trs"><input type="button" id="btnGateway" onclick="disDoubleClick();" class="btn1" value=""></p>
					</div>
				</c:if>
				<c:if test="${result.interfaceCodes.OFFLINE != null}">
					<div class="clear"></div>
					<div class="LFPayCC" <c:if test="${result.interfaceCodes.DEBIT_CARD == null
					&& result.interfaceCodes.CREDIT_CARD == null && result.interfaceCodes.B2B == null}">style="display: block"</c:if>>
						<ul>
							<c:forEach items="${result.interfaceCodes.OFFLINE}" var="bank">
								<li>
									<input style="cursor: pointer;" type="radio" id="${bank }" name="interfaceCode" value="${bank }">
         								<label for="${bank }"><span class="${bank }"></span></label>
								</li>
							</c:forEach>
						</ul>
						<p><a href="javascript:;">更多银行</a></p>
						<p class="tr mt15 trs"><input type="button" id="btnGateway" onclick="disDoubleClick();" class="btn1" value=""></p>
					</div>
				</c:if>
				<c:if test="${result.interfaceCodes.AUTHPAY != null}">
					<div class="clear"></div>
					<div class="LFPayCC" <c:if test="${result.interfaceCodes.DEBIT_CARD == null
					&& result.interfaceCodes.CREDIT_CARD == null && result.interfaceCodes.B2B == null
					&& result.interfaceCodes.OFFLINE == null}">style="display: block"</c:if>>
						<ul>
							<c:forEach items="${result.interfaceCodes.AUTHPAY}" var="bank">
								<li>
									<input style="cursor: pointer;" type="radio" id="${bank }" name="interfaceCode" value="${bank }">
         									<label for="${bank }"><span class="${bank }"></span></label>
								</li>
							</c:forEach>
						</ul>
						<p><a href="javascript:;">更多银行</a></p>
						<p class="tr mt15 trs"><input type="button" id="btnGateway" onclick="disDoubleClick();" class="btn1" value=""></p>
					</div>
				</c:if>
				<div class="clear"></div>
			</div>
		<!--LFPayC-->
		</div>
	</form>
</body>
<script type="text/javascript" src="${applicationScope.staticFilesHost}${requestScope.contextPath}/js/index.js" charset="UTF-8"></script>
</html>
