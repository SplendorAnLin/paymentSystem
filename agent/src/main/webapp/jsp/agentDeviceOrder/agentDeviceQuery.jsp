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
      <form action="agentDeviceQuery.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">设备编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="device_id">
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
              <span class="label w-90">所属服务商:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="customer_no">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">设备状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="status" dictTypeId="DEVICE_STATUS"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">出库时间:</span>
              <div class="input-wrap">
                <input type="text" name="out_warehouse_time_start" class="input-text double-input date-start">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="out_warehouse_time_end" class="input-text double-input date-end">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">激活时间:</span>
              <div class="input-wrap">
                <input type="text" name="activate_time_start" class="input-text double-input date-start">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="activate_time_end" class="input-text double-input date-end">
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <input type="reset" class="btn clear-form" value="重 置">
        </div>
      </form>
    </div>


    <!--查询结果框架-->
    <div class="query-result mt-20">
      <iframe name="query-result" frameborder="0"></iframe>
    </div>

  </div>
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
