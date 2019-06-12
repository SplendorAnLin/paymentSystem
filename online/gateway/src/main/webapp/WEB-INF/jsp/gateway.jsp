
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setAttribute("contextPath", request.getContextPath());
%>
<%@ include file="include/commons-include.jsp"%>
<!DOCTYPE html>
<html lang="en" class="full-size oh">
<head>
  <meta charset="UTF-8">
  <title>订单支付 -聚合支付</title>
  <link rel="stylesheet" href="${applicationScope.staticFilesHost}${requestScope.contextPath}/css/rest.css">
  <link rel="stylesheet" href="${applicationScope.staticFilesHost}${requestScope.contextPath}/css/public.css">
  <link rel="stylesheet" href="${applicationScope.staticFilesHost}${requestScope.contextPath}/css/component.css">
  <link rel="stylesheet" href="${applicationScope.staticFilesHost}${requestScope.contextPath}/css/content.css?20171128">
 <style type="text/css">
			.full_Page {
				display:none;
				position: fixed;
				background: rgba(0, 0, 0, 0.2);
				top: 0;
				left: 0;
				width: 100%;
				height: 100%;
				z-index: 10;
				font-size: 20px;
			}
			
			.pageindex {
				position: absolute;
				width: 40%;
				height: 228px;
				margin: auto;
				left: 30%;
				background: white;
				padding: 23px 20px;
				border-radius: 6px;
				/*  text-align: center; */
				top: calc( 50% - 114px);
				z-index: 100;
			}
			
			.input_bank {
				border: 1px solid rgba(220, 220, 220, 1);
				width: 100%;
				height: 40px;
				box-sizing: border-box;
				-moz-box-sizing: border-box;
				/* Firefox */
				-webkit-box-sizing: border-box;
				/* Safari */
				line-height: 40px;
				margin-top: 20px;
				border-radius: 6px;
				outline: #03a9f4;
				padding-left: 15px;
			}
			
			.submit1 {
				background-color: #03a9f4;
				color: white;
				left: 0;
			}
			
			.submit1,
			.submit2 {
				position: absolute;
				width: 48%;
				height: 40px;
				line-height: 40px;
				border-radius: 6px;
				border: 1px solid #03a9f4;
			}
			
			.submit2 {
				right: 0;
				background-color: white;
				color: #03a9f4;
			}
			
			.po_wi {
				margin-top: 20px;
				width: 100%;
				position: relative;
			}
			
			.submit:hover,
			.submit:action {
				background: #03a9f4;
				color: white
			}
		</style>
</head>
<body>

<div class="header">
  <div class="logo-box fl">
    <img src="${applicationScope.staticFilesHost}${requestScope.contextPath}/image/logo-head.png" alt="">
  </div>
  <div class="account fr">
    <ul class="links fl">
      <li><a href="javascript:void(0);">聚合运营管理系统</a></li>
      <li><a href="javascript:void(0);">API文档</a></li>
      <li><a href="javascript:void(0);">帮助</a></li>
    </ul>
    <div class="user-plan fl">
      <span class="title">HI , 聚合支付有限公司</span>
    </div>
  </div>
</div>


<div class="b-out mtb-40 w-950">
  <div class="b-in">
    <div class="uc-process">
      <span class="unit b-babyBlue">确认订单</span>
      <span class="arrow-complete">
        <span class="next"></span>
        <span class="prev"></span>
      </span>
      <span class="unit b-coralBlue">支付</span>
      <span class="arrow-current">
        <span class="next"></span>
        <span class="prev"></span>
      </span>
      <span class="unit b-gray">支付成功</span>
    </div>

    <div class="order-box">
      <div class="order-info">
        <div class="order-title fix">
          <strong class="fl f-18">订单信息</strong>
          <span class="total fr">
            <span class="cny">¥</span>
            <span><fmt:formatNumber pattern="#,##0.00#" value="${result.amount}" /></span>
          </span>
        </div>
        <div class="order-table-box mt-20">
          <div class="order-table-scroll">
            <table class="uc-table w-p-100 f-12" style="font-size: 14px;">
              <tbody>
                <tr>
                  <td class="c_black">商户名称</td>
                  <td>${result.partnerName }</td>
                  <!-- <td class="text-right"><span class="align"><span class="cny">¥</span><span>120</span></span></td> -->
                </tr>
                <tr>
                  <td class="c_black">订单号</td>
                  <td>${result.outOrderId}</td>
                  <!-- <td class="text-right"><span class="align"><span class="cny">¥</span><span>5212.20</span></span></td> -->
                </tr>
                </tr>
                  <td class="c_black">订单金额</td>
                  <td class="money">¥ <fmt:formatNumber pattern="#,##0.00#" value="${result.amount}" /></td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

      </div>


      <div>
      <form id="gatewayForm" action="toPay.htm" method="post">
      	<input type="hidden" name="tradeOrderCode" value="${result.tradeOrderCode }" />
		<input type="hidden" name="outOrderID" value="${result.outOrderId }" />
		<input type="hidden" name="amount" value="${result.amount }" />
		<input type="hidden" name="apiCode" value="${result.apiCode }" />
		<input type="hidden" name="versionCode" value="${result.versionCode }" />
        <div class="pay-way fix" id="payway-tab">
            <span class="p-title ib">支付方式</span>
            <c:if test="${result.interfaceCodes.DEBIT_CARD != null}">
            	<span data-index=".tab1" class="tab ib active">个人网银</span>
            </c:if>
			<c:if test="${result.interfaceCodes.CREDIT_CARD != null}">
				<span data-index=".tab2" class="tab ib <c:if test="${result.interfaceCodes.DEBIT_CARD == null}">active</c:if>">信用卡</span>
			</c:if>
			<c:if test="${result.interfaceCodes.B2B != null}">
				<span data-index=".tab3" class="tab ib <c:if test="${result.interfaceCodes.DEBIT_CARD == null && result.interfaceCodes.CREDIT_CARD == null}">active</c:if>">企业网银</span>
			</c:if>
			<c:if test="${result.interfaceCodes.AUTHPAY != null}">
				<span data-index=".tab4" class="tab ib <c:if test="${result.interfaceCodes.DEBIT_CARD == null && result.interfaceCodes.CREDIT_CARD == null && result.interfaceCodes.B2B == null}">active</c:if>">认证支付</span>
			</c:if>
			<c:if test="${result.interfaceCodes.WXNATIVE != null}">
				<span data-index=".tab5" class="tab ib <c:if test="${result.interfaceCodes.DEBIT_CARD == null && result.interfaceCodes.CREDIT_CARD == null && result.interfaceCodes.B2B == null && result.interfaceCodes.AUTHPAY == null}">active</c:if>">微信支付</span>
			</c:if>
			<c:if test="${result.interfaceCodes.ALIPAY != null}">
				<span data-index=".tab6" class="tab ib <c:if test="${result.interfaceCodes.DEBIT_CARD == null && result.interfaceCodes.CREDIT_CARD == null && result.interfaceCodes.B2B == null && result.interfaceCodes.AUTHPAY == null && result.interfaceCodes.WXNATIVE == null}">active</c:if>">支付宝</span>
			</c:if>
            <span class="fr zf">支付:<span class="money">¥ <fmt:formatNumber pattern="#,##0.00#" value="${result.amount}" /></span></span>
          </div>
        <div class="blank-content" id="bankc">
        	
          <div class="bank-list tab1 on">
         	<c:if test="${result.interfaceCodes.DEBIT_CARD != null}">
         		<ul class="fix">
         			<c:forEach items="${result.interfaceCodes.DEBIT_CARD}" var="bank">
	         			<li>
			            	<input id="${bank }" type="radio" name="interfaceCode" value="${bank }" hidefocus="">
			                <label for="${bank }" class="${bank } banks-ico">
			                </label>
			        	</li>
		        	</c:forEach>
              	</ul>
			</c:if>
          </div>
          <div class="bank-list tab2 <c:if test="${result.interfaceCodes.DEBIT_CARD == null}">on</c:if>">
          	<c:if test="${result.interfaceCodes.CREDIT_CARD != null}">
         		<ul class="fix">
         			<c:forEach items="${result.interfaceCodes.CREDIT_CARD}" var="bank">
	         			<li>
			            	<input id="${bank }" type="radio" name="interfaceCode" value="${bank }" hidefocus="" >
			                <label for="${bank }" class="${bank } banks-ico">
			                </label>
			        	</li>
		        	</c:forEach>
              	</ul>
			</c:if>
          </div>
          <div class="bank-list tab4 <c:if test="${result.interfaceCodes.DEBIT_CARD == null && result.interfaceCodes.CREDIT_CARD == null && result.interfaceCodes.B2B == null}">on</c:if>">
          	<c:if test="${result.interfaceCodes.AUTHPAY != null}">
         		<ul class="fix">
         			<c:forEach items="${result.interfaceCodes.AUTHPAY}" var="bank">
	         			<li>
			            	<input id="${bank }" type="radio" name="interfaceCode" value="${bank }" hidefocus="" >
			                <label for="${bank }" class="${bank } banks-ico">
			                </label>
			        	</li>
		        	</c:forEach>
              	</ul>
			</c:if>
          </div>
          <div class="bank-list tab5 <c:if test="${result.interfaceCodes.DEBIT_CARD == null && result.interfaceCodes.CREDIT_CARD == null && result.interfaceCodes.B2B == null && result.interfaceCodes.AUTHPAY == null}">on</c:if>">
          	<c:if test="${result.interfaceCodes.WXNATIVE != null}">
         		<ul class="fix">
         			<c:forEach items="${result.interfaceCodes.WXNATIVE}" var="bank">
	         			<li>
			            	<input id="${bank }" type="radio" name="interfaceCode" value="${bank }" hidefocus="" >
			                <label for="${bank }" class="wxPay banks-ico">
			                </label>
			        	</li>
		        	</c:forEach>
              	</ul>
			</c:if>
          </div>
          <div class="bank-list tab6 <c:if test="${result.interfaceCodes.DEBIT_CARD == null && result.interfaceCodes.CREDIT_CARD == null && result.interfaceCodes.B2B == null && result.interfaceCodes.AUTHPAY == null && result.interfaceCodes.WXNATIVE == null}">on</c:if>">
          	<c:if test="${result.interfaceCodes.ALIPAY != null}">
         		<ul class="fix">
         			<c:forEach items="${result.interfaceCodes.ALIPAY}" var="bank">
	         			<li>
			            	<input id="${bank }" type="radio" name="interfaceCode" value="${bank }" hidefocus="" >
			                <label for="${bank }" class="alPay banks-ico">
			                </label>
			        	</li>
		        	</c:forEach>
              	</ul>
			</c:if>
          </div>
        </div>
        <!-- 弹框 -->
	   <div class="full_Page">
			<div class="pageindex">
				<form action='' method="">
					<p>请输入银行卡号：</p>
					<input class="input_bank" type="text" name="mobileInfoBean.bankCardNo" value="" placeholder="请输入银行卡号(单笔限额2W)">
					<div class="po_wi">
						<input class="submit1" type="button" onclick="addCardNo()" value="提交">
						<input class="submit2" type="button" onclick="$('.full_Page').hide()" value="取消">
					</div>
				</form>
			</div>
		</div>
      </form>
      </div>


      <div class="submit-box">
        <div class="info-row"></div>
        <div class="button-row">
          <button type="button" onclick="confirm()" class="uc-button ib">确认支付</button>
        </div>
      </div>




		  
</div>





<script src="${applicationScope.staticFilesHost}${requestScope.contextPath}/js/newJs/plugins/jquery-1.8.3.min.js"></script>
<script src="${applicationScope.staticFilesHost}${requestScope.contextPath}/js/newJs/plugins/highcharts.js"></script>
<script src="${applicationScope.staticFilesHost}${requestScope.contextPath}/js/newJs/plugins/select.js"></script>
<script src="${applicationScope.staticFilesHost}${requestScope.contextPath}/js/newJs/plugins/datepicker/WdatePicker.js"></script>
<script src="${applicationScope.staticFilesHost}${requestScope.contextPath}/js/newJs/chart.js"></script>
<script src="${applicationScope.staticFilesHost}${requestScope.contextPath}/js/newJs/UI.js"></script>
<script>
  if (window.tabSwitch) {
    tabSwitch($('#payway-tab'), 'active');
  }
  
  function confirm() {
	  var interfaceCode = $("input[name=interfaceCode]:checked").val();
	  if (interfaceCode == '') {
		  return 
	  }
	  if (interfaceCode.indexOf("B2C") >= 0 || interfaceCode.indexOf("AUTHPAY") >= 0) {
		  // $(".full_Page").show();
          $('#gatewayForm').submit();
	  } else {
		  $('#gatewayForm').submit();
	  }
  }
  
  function addCardNo() {
	  var val = $("input[name='mobileInfoBean.bankCardNo']").val();
	  if (val != '' && !isNaN(val)) {
	  	$('#gatewayForm').submit();
	  } else {
		  $("input[name='mobileInfoBean.bankCardNo']").attr("style","border-color:red");
	  }
  }
</script>
</body>
</html>