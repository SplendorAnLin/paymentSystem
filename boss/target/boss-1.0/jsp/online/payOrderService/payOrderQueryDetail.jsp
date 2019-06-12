<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>交易订单详情</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body style="width: 1400px;padding: 10px;">
  
  <!--交易基本信息-->
  <div class="row">
    <div class="title-h1 fix tabSwitch2">
      <span class="primary fl">交易基本信息</span>
    </div>
    <div class="content">
      <div class="table-info w-p-100">
        <div class="type-table fl w-p-50">
          <div class="type-tr">
            <div class="type-td w-100 text-primary">交易成本</div>
            <div class="type-td text-secondary">${tradeOrder.cost }</div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">订单状态</div>
            <div class="type-td color-green"><dict:write dictId="${tradeOrder.status }" dictTypeId="TRADE_ORDER_STATUS_COLOR"></dict:write></div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">清分状态</div>
            <div class="type-td color-green"><dict:write dictId="${tradeOrder.clearingStatus }" dictTypeId="LIQUIDATION_STATUS_COLOR"></dict:write></div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">订单金额</div>
            <div class="type-td text-secondary">${tradeOrder.amount }</div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">实付金额</div>
            <div class="type-td text-secondary">${tradeOrder.paidAmount }</div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">商户手续费</div>
            <div class="type-td text-secondary">${tradeOrder.receiverFee }</div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">商户编号</div>
            <div class="type-td text-secondary">${tradeOrder.receiver }</div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">交易流水号</div>
            <div class="type-td text-secondary">${tradeOrder.code }</div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">商户订单号</div>
            <div class="type-td text-secondary">${tradeOrder.requestCode }</div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">通知状态</div>
            <div class="type-td text-secondary"><c:if test="${tradeOrder.status == 'SUCCESS'}"><dict:write dictId="${notifyStatus }" dictTypeId="INTERFACE_REQUEST_STATUS_COLOR"></dict:write></c:if></div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">通知URL</div>
            <div class="type-td text-secondary">${notifyUrl }</div>
          </div>
        </div>
        <div class="type-table fl w-p-50">
          <div class="type-tr">
            <div class="type-td w-100 text-primary">姓名</div>
            <div class="type-td text-secondary">${authSaleBean.payerName }</div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">电话</div>
            <div class="type-td text-secondary">${authSaleBean.payerMobNo }</div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">身份证</div>
            <div class="type-td text-secondary">${authSaleBean.certNo }</div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">银行卡号</div>
            <div class="type-td text-secondary">${authSaleBean.bankCardNo }</div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">支付成功时间</div>
            <div class="type-td text-secondary"><fmt:formatDate value="${tradeOrder.successPayTime}" pattern="yyyy-MM-dd HH:mm:ss" /></div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">订单创建时间</div>
            <div class="type-td text-secondary"><fmt:formatDate value="${tradeOrder.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">超时时间</div>
            <div class="type-td text-secondary"><fmt:formatDate value="${tradeOrder.timeout }" pattern="yyyy-MM-dd HH:mm:ss" /></div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">关闭时间</div>
            <div class="type-td text-secondary"><fmt:formatDate value="${tradeOrder.closeTime }" pattern="yyyy-MM-dd HH:mm:ss" /></div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">通知时间</div>
            <div class="type-td text-secondary">${notifyTime }</div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">通知次数</div>
            <div class="type-td text-secondary">${notifyCount }</div>
          </div>
        </div>
      </div>
    </div>
  </div>
  
  
  <!--交易订单流水-->
  <div class="row">
    <div class="title-h1 fix tabSwitch2">
      <span class="primary fl">交易订单流水</span>
    </div>
    <div class="content">
      <c:if test="${payments.size() > 0 && payments != null}">
        <div class="table-warp">
         <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>支付流水号</th>
                <th>支付方式</th>
                <th>应付金额</th>
                <th>商户手续费</th>
                <th>支付状态</th>
                <th>支付接口编号</th>
                <th>支付接口订单号</th>
                <th>接口成本</th>
                <th>交易开始时间</th>
                <th>交易结束时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${payments }" var="pay">
                <tr>
                  <td>${pay.code }</td>
                  <td><dict:write dictId="${pay.payType }" dictTypeId="BF_SHARE_PAYTYPE"></dict:write></td>
                  <td>${pay.amount }</td>
                  <td>${pay.receiverFee }</td>
                  <td><dict:write dictId="${pay.status }" dictTypeId="PAY_STATUS_COLOR"></dict:write></td>
                  <td>${pay.payinterface }</td>
                  <td>${pay.payinterfaceRequestId }</td>
                  <td><fmt:formatNumber value="${pay.payinterfaceFee }" pattern="#.##" /></td>
                  <td><fmt:formatDate value="${pay.payTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                  <td><fmt:formatDate value="${pay.completeTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                  <td> <c:if test="${tradeOrder.status != 'SUCCESS' }">
                    <button class="btn-small disabled">补发通知</button>
                  </c:if>
                    <c:if test="${tradeOrder.status == 'SUCCESS' }">
                    <button class='btn-small' onclick="notice('${tradeOrder.code }');">补发通知</button>
                  </c:if></td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
          
         
         </div>
        </div>
        <%@ include file="../../include/page.jsp" %>
       </c:if>
       <c:if test="${payments.size() == 0}">
        <p class="pd-10">暂无交易订单</p>
       </c:if>
    </div>
  </div>
  
  <%@ include file="../../include/bodyLink.jsp"%>
   <script>
    // 补发通知
    function notice(ordsCode) {
    	window.AjaxConfirm('是否补发通知?', '请确认', {
    		url: '${pageContext.request.contextPath}/reNotifyMerOrder.action?orderCode=' + ordsCode,
    		type: 'post',
    		dataType: 'json',
    		success: function(msg) {
          if (msg.respCode === 'SUCCESS') {
            Messages.success('补发成功!');
          }else if (msg.respCode === 'OTHER') {
        	  Messages.error(msg.msg);
		  } else {
            Messages.error('补发失败, 请稍后重试!');
          }
    		},
    		error: function() {
    			Messages.error('网络异常, 补发失败, 请稍后尝试...');
    		}
    	}, true)
    }
    $(window).load(function() {
    	// ajax获取简称
    	$('.shortName').each(function() {
    		var span =  $(this);
    		var customerNo = span.attr('data-receiver');
    		Api.boss.getCustShortName(customerNo, function(shrotName) {
    			if (!shrotName)
    				return;
    			span.text(shrotName);
    		});
    	});
    	
    });
  </script>
</body>
</html>
