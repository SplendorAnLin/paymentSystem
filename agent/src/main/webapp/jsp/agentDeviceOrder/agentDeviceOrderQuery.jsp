<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>设备采购订单</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">

    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="agentDeviceOrderQuery.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">采购订单流水号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="purchase_serial_number">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">采购联系人账户:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="user">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">采购状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" name="purchase_status" styleClass="input-select" dictTypeId="AGENT_QRSTATUS"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">设备类型:</span>
              <div class="input-wrap">
                <select class="input-select" name="device_type_id" >
                  <option value="">全部</option>
                   <c:forEach items="${deviceType }" var="info">
                    <option value="${info.id }">${info.deviceName }</option>
                  </c:forEach> 
                </select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">订单状态:</span>
              <div class="input-wrap">
                <dict:select name="flow_status" nullOption="true" styleClass="input-select" dictTypeId="DEVICE_ORDER_STATUS"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">数量区间:</span>
              <div class="input-wrap">
                <input type="text" name="quantity_start" class="input-text double-input">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="quantity_end" class="input-text double-input">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">价格区间:</span>
              <div class="input-wrap">
                <input type="text" name="unit_price_start" class="input-text double-input">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="unit_price_end" class="input-text double-input">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">创建时间:</span>
              <div class="input-wrap">
                <input type="text" name="create_time_start" class="input-text double-input date-start">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="create_time_end" class="input-text double-input date-end">
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <button data-myiframe='{
            "target": "toAgentDeviceOrderAdd.action",
            "btns": [
              {"lable": "取消"},
              {"lable": "确定", "trigger": ".btn-submit"}
            ]
          }' class="btn-small">设备申请</button>
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
