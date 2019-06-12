<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>设备生产</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 4em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator ajaxFormNotification" action="devicePurchAdd.action" method="post" prompt="DropdownMessage"  data-success="设备生产成功" data-fail="设备生产失败">
        <select class="hidden" id="select-cache">
          <c:forEach items="${ deviceTypeList}" var="device">
            <c:if test="${device.parentId != '0'}">
              <option value="${device.id }" data-type="${device.parentId }"  data-price="${device.unitPrice }" >${device.deviceName }</option>
            </c:if>
          </c:forEach>
        </select>
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">设备数量:</span>
              <div class="input-wrap">
                <input class="input-text" type="text" name="devicePurch.quantity" required digits>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">设备类型:</span>
              <div class="input-wrap">
                <select class="input-select" name="deviceTypeInfo.parentId" onchange="modifyType_primary(this.value);" required>
                  <c:forEach items="${ deviceTypeList}" var="device">
                    <c:if test="${device.parentId == null}">
                      <option value="${device.id }" data-type="${device.parentId }" >${device.deviceName }</option>
                    </c:if>
                  </c:forEach>
                </select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">设备型号:</span>
              <div class="input-wrap">
                <select class="input-select" name="devicePurch.deviceTypeId" required></select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">采购单价:</span>
              <div class="input-wrap">
                <input class="input-text readonly_verif"  type="text" name="devicePurch.unitPrice" id="price" required amount>
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10 hidden">
          <button class="btn btn-submit">新 增</button>
        </div>
      </form>
    </div>
  </div>

  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>

  /**
   * 列表联动
   * 设备类型 value 对应 设备型号 data-type
   */

   // 设备型号
   var deviceTypeId = $('[name="devicePurch.deviceTypeId"]');
   // 设备价格
   var price = $('#price');
   // 数据缓存
   var cache = $('#select-cache').find('option');

   // 更改设备类型
   function modifyType_primary(val) {
     deviceTypeId.empty();
    // 匹配对应option, 写入设备型号
    var options = cache.filter(function() {
      return $(this).attr('data-type') == val;
    }).clone();
    deviceTypeId.html(options).SelectBox();
   }

   $('[name="deviceTypeInfo.parentId"]').trigger('change');
   

  </script>
</body>
</html>
