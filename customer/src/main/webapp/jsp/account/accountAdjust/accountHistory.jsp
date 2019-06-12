<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>


 <%@ include file="../../include/header.jsp"%>
  <title>财务明细</title>
</head>
<body>

  <div class="query-box">

    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="${pageContext.request.contextPath}/toAccountHistoryInfo.action" method="post" target="query-result">
        <div class="adaptive fix">
        	<div class="item">
            <div class="input-area">
              <span class="label w-90">资金标识:</span>
              <div class="input-wrap">
                <select class="input-select" name="symbol" >
                	   <option>全部</option>
                  <option value="SUBTRACT">加</option>
                  <option value="PLUS">减</option>
                
                </select>
              </div>
            </div>
          </div>
        	 <div class="item">
            <div class="input-area">
              <span class="label w-90">系统流水号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="accountHistoryQueryBean.transFlow">
              </div>
            </div>
          </div>
            
          
            <div class="item">
            <div class="input-area">
              <span class="label w-90">业务类型:</span>
              <div class="input-wrap">
                <select class="input-select" name="accountHistoryQueryBean.bussinessCode">
                  <option value="">全部</option>
                  <option value="ADJUST">系统调账</option>
                  <option value="DPAY_DEBIT">代付出款</option>
                  <option value="DPAY_DEBIT_FEE">代付手续费出款</option>
                   <option value="DPAY_CREDIT">代付退款</option>
                  <option value="DPAY_CREDIT_FEE">代付手续费退款</option>
                  <option value="ONLINE_CREDIT">支付入账</option>
                   <option value="ONLINE_CREDIT_FEE">支付手续费扣款</option>
                  <option value="RECEIVE_CREDIT">代收入账</option>
                    <option value="RECEIVE_CREDIT_FEE">代付手续费扣款</option>
                  
                </select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">交易日期:</span>
              <div class="input-wrap">
                <input type="text" name="accountHistoryQueryBean.transStartTime" class="input-text double-input date-start default-time">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="accountHistoryQueryBean.transEndTime" class="input-text double-input date-end default-time-end">
              </div>
            </div>
          </div>
        </div>
        
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <a class="btn" id="show_viewCount" href="javascript:void(0);">查看合计</a>
          <input type="reset" class="btn clear-form" value="重 置">
        </div>
      </form>
    </div>


    <!--查询结果框架-->
    <div class="query-result mt-20">
      <iframe name="query-result" frameborder="0"></iframe>
    </div>

  </div>


  <!-- 查看合计弹出框 -->
  <div class="viewCount fix" style="min-width: 400px;">
    <div class="col fl">
      <div class="row fix">
        <p class="label">总金额:</p>
        <p class="value">99.999</p>
      </div>
      <div class="row fix">
        <p class="label">总笔数:</p>
        <p class="value">568</p>
      </div>
    </div>
    <div class="col fl">
      <div class="row fix">
        <p class="label">总手续费:</p>
        <p class="value">53.12</p>
      </div>

      <div class="row fix">
        <p class="label">总交易成本:</p>
        <p class="value">14.33</p>
      </div>
    </div>
  </div>




 <%@ include file="../../include/bodyLink.jsp"%>


  <script>
    // 查看合计
    $('#show_viewCount').data('sumFn', function(data, viewCount) {
      var result = data.split(',');
      $('.sum-amount', viewCount).text(result[0] || 0);
      $('.sum-count', viewCount).text(result[1] || 0);
      $('.sum-cost', viewCount).text(result[2] || 0);
    });
  </script>
</body>
</html>