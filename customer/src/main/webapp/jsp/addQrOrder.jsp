<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>水牌申请</title>
  <%@ include file="include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 4em;">
    <!--查询表单-->
    <div class="query-form">
      <input class="hidden" type="text" id="msg" value="<%=request.getParameter("msg") %>" />
      <form class="validator ajaxFormNotification" action="${pageContext.request.contextPath}/addOrder.action" id="form" prompt="DropdownMessage"  data-success="提交水牌申请成功" data-fail="提交水牌申请失败">
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
              <span class="label w-90">设备类型:</span>
              <div class="input-wrap">
                <select class="input-select" name="deviceInfo" onchange="modifyType_primary(this.value);" required>
                  <c:forEach items="${ deviceTypeList}" var="device">
                    <c:if test="${device.parentId eq '0'}">
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
                <select class="input-select" name="devicePurch.deviceTypeId" onchange="modifyType(this);" required></select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">设备价格:</span>
              <div class="input-wrap">
                <input class="input-text"  type="text" name="devicePurch.unitPrice" id="price" readonly required>
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10 hidden">
          <button class="btn btn-submit">分 配</button>
        </div>
      </form>
    </div>
  </div>

  
  <%@ include file="include/bodyLink.jsp"%>
  <script>
    var status='<%=session.getAttribute("msg")%>';
    // 对话框加载完毕事件回调
    window.DialogLoad = function(dialog) {
      if (status == 'false') {
        new window.top.Alert('已经提交过申请，请耐心等待！', '水牌申请');
        dialog.close(null, true);
      }
    };

    var form = $('form');
    // 挂接表单结果提示钩子
    form.data('receiver-hook', function(msg, closeDialog) {
        if (msg == 'true') {
        	new window.top.Alert('已经提交过申请，请耐心等待！', '水牌申请');
        	closeDialog(msg, null, true);
        } else {
        	new window.top.Alert('水牌申请出错！', '水牌申请');
        	closeDialog(msg, null, true);
        }
    });
  
  
  /**
   * 列表联动
   * 设备类型 value 对应 设备型号 data-type
   * 设备型号 data-price 对应 设备价格
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
    // 主动触发价格
    modifyType(deviceTypeId);
   }
   
   // 更改设备型号
   function modifyType(select) {
    var option = $(select).currentOption();
    price.val(option.attr('data-price'));
   }


   $('[name="deviceInfo"]').trigger('change');


  </script>
</body>
</html>
