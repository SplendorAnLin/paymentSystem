<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>代收订单查询</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">

    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="receiveOrderQuery.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账户号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="account_no">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账户名称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="account_name">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">收款单号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="receive_id">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">所有者编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="customer_no">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">银行编码:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="payer_bank_no">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">证件号码:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="certificates_code">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账号类型:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="acc_no_type" dictTypeId="ACCOUNT_CARD"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">证件类型:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="certificates_type" dictTypeId="TYPE_OF_CERTIFICATE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账户类型:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" dictTypeId="ACCOUNT_TYPE" name="acc_type"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">订单状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="order_status" dictTypeId="ORDER_STATUS"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">清算状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" dictTypeId="RECEIVE_ORDER_STATUS" name="clear_status"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">金额:</span>
              <div class="input-wrap">
                <input type="text" name="amount_start" class="input-text double-input">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="amount_end" class="input-text double-input">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">手续费:</span>
              <div class="input-wrap">
                <input type="text" name="fee_start" class="input-text double-input">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="fee_end" class="input-text double-input">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">成本:</span>
              <div class="input-wrap">
                <input type="text" name="cost_start" class="input-text double-input">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="cost_end" class="input-text double-input">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">创建时间:</span>
              <div class="input-wrap">
                <input type="text" name="create_time_start" class="input-text double-input default-date date-start ignoreEmpy" date-fmt="yyyy-MM-dd">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="create_time_end" class="input-text double-input default-date-end date-end ignoreEmpy" date-fmt="yyyy-MM-dd">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">完成时间:</span>
              <div class="input-wrap">
                <input type="text" name="last_update_time_start" class="input-text double-input default-date date-start ignoreEmpy" date-fmt="yyyy-MM-dd">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="last_update_time_end" class="input-text double-input default-date-end date-end ignoreEmpy" date-fmt="yyyy-MM-dd">
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <a class="btn" id="show_viewCount" data-action="receiveOrderSum.action" href="javascript:void(0);">查看合计</a>
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
    </div>
    <div class="col fl">
      <div class="row fix">
        <p class="label">成本:</p>
        <p class="value"><span class="sum-cost"></span> 元</p>
      </div>
      <div class="row fix">
        <p class="label">总手续费:</p>
        <p class="value"><span class="sum-fee"></span> 元</p>
      </div>
    </div>
  </div>

  
  
  
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    // 查看合计
    $('#show_viewCount').data('sumFn', function(data, viewCount) {
      var result = (eval(data))[0];
      $('.sum-amount', viewCount).text(utils.toFixed(result.amount) || 0);
      $('.sum-count', viewCount).text(utils.toFixed(result.ordercount, 0) || 0);
      $('.sum-cost', viewCount).text(utils.toFixed(result.cost) || 0);
      $('.sum-fee', viewCount).text(utils.toFixed(result.fee) || 0);
    });
  </script>

</body>
</html>
