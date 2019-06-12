<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>设备管理</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">

    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="deviceQuery.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">商户编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="customer_no">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">设备编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="customer_pay_no">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">采购流水号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="purchase_serial_number">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">所属批次:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="batch_number">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">所属服务商:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="agent_no">
              </div>
            </div>
          </div>

          <div class="item">
            <div class="input-area">
              <span class="label w-90">设备状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="status" dictTypeId="PURCHASING_STATUS"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">出库时间:</span>
              <div class="input-wrap">
                <input type="text" name="out_warehouse_time_start" class="input-text default-date double-input date-start ignoreEmpy">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="out_warehouse_time_end" class="input-text default-date-end double-input date-end ignoreEmpy">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">激活时间:</span>
              <div class="input-wrap">
                <input type="text" name="activate_time_start" class="input-text default-date double-input date-start ignoreEmpy">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="activate_time_end" class="input-text default-date-end double-input date-end ignoreEmpy">
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <a class="btn" id="show_viewCount" data-action="deviceSum.action" href="javascript:void(0);">查看合计</a>
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
        <p class="label">初始化:</p>
        <p class="value"><span class="order_wait"></span> 个</p>
      </div>
      <div class="row fix">
        <p class="label">制作中:</p>
        <p class="value"><span class="making"></span> 个</p>
      </div>
      <div class="row fix">
        <p class="label">已绑定:</p>
        <p class="value"><span class="binded"></span> 个</p>
      </div>
    </div>
    <div class="col fl">
      <div class="row fix">
        <p class="label">已入库:</p>
        <p class="value"><span class="ok"></span> 个</p>
      </div>
      <div class="row fix">
        <p class="label">已分配:</p>
        <p class="value"><span class="allot"></span> 个</p>
      </div>
    </div>
  </div>
  
  
  
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    // 查看合计
    $('#show_viewCount').data('sumFn', function(data, viewCount) {
      var result = JSON.parse(data);
      $('.order_wait', viewCount).text(utils.toFixed(result.order_wait) || 0);
      $('.making', viewCount).text(utils.toFixed(result.making) || 0);
      $('.binded', viewCount).text(utils.toFixed(result.binded) || 0);
      $('.ok', viewCount).text(utils.toFixed(result.ok) || 0);
      $('.allot', viewCount).text(utils.toFixed(result.allot) || 0);
    });
  </script>
</body>
</html>
