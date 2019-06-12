<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>设备申请</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 360px; padding: 10px;">
  
    <form class="hidden" action="payGetSing.action" method="post" id="signForm" >
      <input type="hidden" name="apiCode" value="YL-PAY" />
      <input type="hidden" name="inputCharset" id="inputCharset" value="UTF-8" />
      <input type="hidden" id="partner" name="partner" value="${msg}"/>
      <!-- 订单号 -->
      <input id="outOrderId" class="outOrderId" type="hidden" name="outOrderId"/>
      <input type="hidden" name="currency" value="CNY" />
      <input type="hidden" name="retryFalg" value="TRUE" />
      <!-- 提交时间 -->
      <input type="hidden" id="submitTime" name="submitTime" />
      <input type="hidden" name="redirectUrl" value="http://pay.feiyijj.com/boss/pageCallBack.action?msg=" />
      <input type="hidden" name="notifyUrl" value="http://pay.feiyijj.com/boss/callback.action" />
      <input type="hidden" id="timeout" name="timeout" value="1D"/>
      <input type="hidden" name="productName" value="水牌采购"/>
      <input type="hidden" name="payType" id="payType" value="ALL"/>
      <input type="hidden" name="bankCardNo"/>
      <input type="hidden" name="accMode" value="GATEWAY" /><br/>
      <input type="hidden" name="wxNativeType" value="PAGE" />
      <input type="hidden" name="signType" value="MD5" />
      <!-- 效验码 -->
      <input type="hidden" id="sign" name="sign" />
      <!-- 订单金额 -->
      <input type="hidden" name="amount" id="transAmount"/>
    </form>
  
  
  <div class="query-box" style="padding: 1px;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator ajaxFormNotification" action="agentDeviceOrderAdd.action" id="form" prompt="DropdownMessage"  data-success="设备申请成功" data-fail="设备申请失败">
        <input class="outOrderId" name="outOrderId"  type="hidden" />
        <select class="hidden" id="select-cache">
          <c:forEach items="${ deviceType}" var="device">
            <c:if test="${device.parentId != '0'}">
              <option value="${device.id }" data-type="${device.parentId }"  data-price="${device.unitPrice }" >${device.deviceName }</option>
            </c:if>
          </c:forEach>
        </select>
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">定购设备数量:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="agentDeviceOrderBean.quantity" required digits>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">设备类型:</span>
              <div class="input-wrap">
                <select class="input-select" name="deviceInfo" onchange="modifyType_primary(this.value);" required>
                  <c:forEach items="${ deviceType}" var="device">
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
                <select class="input-select" name="agentDeviceOrderBean.deviceTypeId" onchange="modifyType(this);" required></select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">单价:</span>
              <div class="input-wrap">
                <input class="input-text"  type="text" name="price" id="price" readonly required>
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10 hidden">
          <button class="btn btn-submit">修 改</button>
        </div>
      </form>
    </div>
  </div>
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>

  /**
   * 列表联动
   * 设备类型 value 对应 设备型号 data-type
   * 设备型号 data-price 对应 设备价格
   */

   // 设备型号
   var deviceTypeId = $('[name="agentDeviceOrderBean.deviceTypeId"]');
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
    if (options.length == 0) {
    	deviceTypeId.html('<option value="">请选择</option>').SelectBox();
    } else {
    	deviceTypeId.html(options).SelectBox();
    }
    // 主动触发价格
    modifyType(deviceTypeId);
   }
   
   // 更改设备型号
   function modifyType(select) {
    var option = $(select).currentOption();
    price.val(option.attr('data-price') || 0);
   }
   $('[name="deviceInfo"]').trigger('change');

   
   $(document).ready(function() {
  	 var form = $('#form');
     // 获取表单验证对象实例 
     var formValidator = window.getValidator(form);
     formValidator.options.onCustomize = function() {
    	 sign(function() {
    		 formValidator.submit(true);
    	 });
    	 return false;
     };
     // 挂接表单结果提示钩子
     form.data('receiver-hook', function(msg, closeDialog) {
         switch(msg) {
           case 'true':
             closeDialog(true, '申请成功, 等待支付...');
             break;
           default:
             closeDialog(false, msg);
         }
     });
   });
   
   
   // 获取签名
   function sign(callback) {
     // 获取订单总金额
     var amount = orderAmount();
     if (!amount) {
    	 Messages.error('请输入金额和数量');
       return;
     }
     $('#submitTime').val(timestamp());
     $('.outOrderId').val(orderNumber());
     $('#transAmount').val(amount);
     var signForm = $('#signForm');
     utils.ajax({
    	 url: signForm.attr('action'),
    	 data : signForm.serialize(),
    	 type : 'post',
    	 dataType : 'json',
    	 success: function(result) {
    		 if (result.status == 'FAILED') {
    			 new window.top.Alert(data.msg, '获取签名失败');
    			 return;
    		 }
         $("#sign").val(result.sign);
         signForm.attr("action", result.payUrl);
         signForm.attr("target", "_blank");
         signForm.submit();
         callback(true);
    	 },
    	 error: function() {
    		 Messages.error('网络异常 #1, 请联系管理员或稍后重试...');
    	 }
     });
     
     
   }
   // 获取订单金额
   function orderAmount() {
     var total = parseFloat($('[name="agentDeviceOrderBean.quantity"]').val() || 0);
     var amount = parseFloat(price.val() || 0);
     if (isNaN(total) || isNaN(amount)) {
    	 Messages.error('单价或者数量不正确!');
       throw new Error("单价或者数量不为数值..");
       return false;
     }
     
     return total * amount;
   }
   
   // 获取订单号
   function orderNumber() {
     var chars = [ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
           'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
           'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' ];
     var val = "";
     for ( var i = 0; i < 16; i++) {
       var v = Math.random() * 10;
       var g = Math.floor(v);
       val += chars[g];
     }
     return val;
   }
   
   // 获取时间戳
   function timestamp() {
     var day = new Date();
     var Year = 0;
     var Month = 0;
     var Day = 0;
     var CurrentDate = "";
     Year = day.getFullYear();
     Month = day.getMonth() + 1;
     Day = day.getDate();
     h = day.getHours();
     m = day.getMinutes();
     s = day.getSeconds();
     CurrentDate += Year;
     if (Month >= 10) {
       CurrentDate += Month;
     } else {
       CurrentDate += "0" + Month;
     }
     if (Day >= 10) {
       CurrentDate += Day;
     } else {
       CurrentDate += "0" + Day;
     }
     if (h >= 10) {
       CurrentDate += h;
     } else {
       CurrentDate += "0" + h;
     }
     if (m >= 10) {
       CurrentDate += m;
     } else {
       CurrentDate += "0" + m;
     }
     if (s >= 10) {
       CurrentDate += s;
     } else {
       CurrentDate += "0" + s;
     }
     return CurrentDate;
   }

  </script>
</body>
</html>
