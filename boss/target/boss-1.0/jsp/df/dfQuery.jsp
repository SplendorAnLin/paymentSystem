<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>付款订单</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">

    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="dfQuery.action" method="post" target="query-result">
        <div class="adaptive fix">

          <div class="item">
            <div class="input-area">
              <span class="label w-90">商户编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="ownerId">
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
              <span class="label w-90">收款人姓名:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="accountName">
              </div>
            </div>
          </div>
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
              <span class="label w-90">商户订单号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="requestNo">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账户类型:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="accountType" dictTypeId="ACCOUNT_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">审核状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="auditStatus" dictTypeId="DF_AUDIT_STATUS"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">代付状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" name="requestStatus" styleClass="input-select" dictTypeId="DF_STATUS"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">代付类型:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" name="requestType" styleClass="input-select" dictTypeId="DF_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">金额:</span>
              <div class="input-wrap">
                <input type="text" name="amountStart" class="input-text double-input">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="amountEnd" class="input-text double-input">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">创建时间:</span>
              <div class="input-wrap">
                <input type="text" name="applyDate1" class="input-text default-time double-input date-start ignoreEmpy">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="applyDate2" class="input-text default-time-end double-input date-end ignoreEmpy">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">完成时间:</span>
              <div class="input-wrap">
                <input type="text" name="finish_date1" class="input-text default-time double-input date-start ignoreEmpy">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="finish_date2" class="input-text default-time-end double-input date-end ignoreEmpy">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">审核时间:</span>
              <div class="input-wrap">
                <input type="text" name="audit_date1" class="input-text default-time double-input date-start ignoreEmpy">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="audit_date2" class="input-text default-time-end double-input date-end ignoreEmpy">
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
      var result = data.split(',');
      $('.sum-amount', viewCount).text(utils.toFixed(result[1], 2) || 0);
      $('.sum-count', viewCount).text(utils.toFixed(result[0], 0) || 0);
      $('.sum-cost', viewCount).text(utils.toFixed(result[2], 2) || 0);
    });
  </script>
</body>
</html>
