<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <%@ include file="../include/header.jsp"%>
  <title>代付查询</title>
</head>
<body>

  <div class="query-box">
    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="dfQuery.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">订单号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="flowId">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">收款账号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="accountNo">
              </div>
            </div>
          </div>
           <div class="item">
            <div class="input-area">
              <span class="label w-90">商户订单号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="requestNo">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">审核状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" name="auditStatus" styleClass="input-select" dictTypeId="CUSTOMER_AUDIT_NAME"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">代付状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="requestStatus" dictTypeId="DF_STATUS"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账户类型:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" name="accountType" styleClass="input-select" dictTypeId="ACCOUNT_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">收款人姓名:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="accountName">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">创建时间:</span>
              <div class="input-wrap">
                <input type="text" name="applyDate1" class="input-text double-input date-start default-time ignoreEmpy">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="applyDate2" class="input-text double-input date-end default-time-end ignoreEmpy">
              </div>
            </div>
          </div>
         <div class="item">
            <div class="input-area">
              <span class="label w-90">完成时间:</span>
              <div class="input-wrap">
                <input type="text" name="finish_date1" class="input-text double-input date-start">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="finish_date2" class="input-text double-input date-end">
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <a class="btn" id="show_viewCount" data-action="getDPayCount.action" href="javascript:void(0);">查看合计</a>
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
        <p class="label">总金额:</p>
        <p class="value"><span class="sum-amount"></span> 元</p>
      </div>
      <div class="row fix">
        <p class="label">总笔数:</p>
        <p class="value"><span class="sum-count"></span> 笔</p>
      </div>
      <div class="row fix">
        <p class="label">总交易成本:</p>
        <p class="value"><span class="sum-cost"></span> 元</p>
      </div>
    </div>
  </div>

 <%@ include file="../include/bodyLink.jsp"%>
  <script>
    // 查看合计
    $('#show_viewCount').data('sumFn', function(data, viewCount) {
      var result = JSON.parse(data);
      $('.sum-amount', viewCount).text(utils.toFixed(result.amount, 2) || 0);
      $('.sum-count', viewCount).text(utils.toFixed(result.no, 0) || 0);
      $('.sum-cost', viewCount).text(utils.toFixed(result.fee, 2) || 0);
    });
  </script>
</body>
</html>