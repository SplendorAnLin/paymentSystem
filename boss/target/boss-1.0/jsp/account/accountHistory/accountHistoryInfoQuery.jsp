<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>账户历史</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">
    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="${pageContext.request.contextPath }/queryAccountHistoryAction.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">用户号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="accountHistoryQueryBean.userNo">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账户编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="accountHistoryQueryBean.accountNo">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">交易流水号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="accountHistoryQueryBean.transFlow">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">系统编码:</span>
              <div class="input-wrap">
              	 <dict:select name="accountHistoryQueryBean.systemCode" nullOption="true" styleClass="input-select" dictTypeId="SYS_CODE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账户类型:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="accountHistoryQueryBean.userRole" dictTypeId="USER_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">业务类型:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="accountHistoryQueryBean.bussinessCode" id="bussinessCode" dictTypeId="BUSINESS_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">资金标识:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="accountHistoryQueryBean.symbol" id="symbol" dictTypeId="CAPITAL_IDENTIFICATION"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">创建日期:</span>
              <div class="input-wrap">
                <input type="text" class="input-text double-input date-start default-time ignoreEmpy" name="accountHistoryQueryBean.createStartTime">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" class="input-text double-input date-end default-time-end ignoreEmpy" name="accountHistoryQueryBean.createEndTime">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">交易日期:</span>
              <div class="input-wrap">
                <input type="text" class="input-text double-input date-start default-time ignoreEmpy" name="accountHistoryQueryBean.transStartTime">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" class="input-text double-input date-end default-time-end ignoreEmpy" name="accountHistoryQueryBean.transEndTime">
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <a class="btn" id="show_viewCount" data-action="${pageContext.request.contextPath}/queryAccountHistorySum.action" href="javascript:void(0);">查看合计</a>
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
  <div class="viewCount fix" style="min-width: 250px;">
    <div class="col fl">
      <div class="row fix">
        <p class="label">总<span style="color:#FF0000;">入账</span>金额:</p>
        <p class="value"><span class="sum-raiseTotalBalance"></span> 元</p>
      </div>
      <div class="row fix">
        <p class="label">总<span style="color:#FF0000;">入账</span>笔数:</p>
        <p class="value"><span class="sum-raiseTotalCount"></span> 笔</p>
      </div>
    </div>
    <div class="col fl">
      <div class="row fix">
        <p class="label">总<span style="color:#0000FF;">出账</span>金额:</p>
        <p class="value"><span class="sum-subtractTotalBalance"></span> 元</p>
      </div>

      <div class="row fix">
        <p class="label">总<span style="color:#0000FF;">出账</span>笔数:</p>
        <p class="value"><span class="sum-subtractTotalCount"></span> 笔</p>
      </div>
    </div>
  </div>
  
  <%@ include file="../../include/bodyLink.jsp"%>
  
    <script>
    // 查看合计
    $('#show_viewCount').data('sumFn', function(data, viewCount) {
      var result = JSON.parse(data);
      $('.sum-raiseTotalBalance', viewCount).text(utils.toFixed(result.raiseTotalBalance, 2) || 0);
      $('.sum-raiseTotalCount', viewCount).text(utils.toFixed(result.raiseTotalCount, 0) || 0);
      $('.sum-subtractTotalBalance', viewCount).text(utils.toFixed(result.subtractTotalBalance, 2) || 0);
      $('.sum-subtractTotalCount', viewCount).text(utils.toFixed(result.subtractTotalCount, 0) || 0);
    });
  </script>
</body>
</html>
