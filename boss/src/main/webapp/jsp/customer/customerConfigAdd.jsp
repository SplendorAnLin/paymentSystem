<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>新增商户交易配置信息</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 4em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="customeConfigAdd.action" method="post" id="form" prompt="DropdownMessage"  data-success="新增成功" data-fail="新增失败">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">商户编号:</span>
              <div class="input-wrap">
                    <input type="text" class="input-text" name="customerNo"  required checkCustomerNo trigger='{"checkCustomerNo": 2 }'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">商户全称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="fullName" readonly required placeholder="请填写商户编号">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">产品类型:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select" name="productType" id="productType" dictTypeId="PRODUCT_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">交易开始时间:</span>
              <div class="input-wrap">
                <input type="text" class="input-text date-start" date-fmt="H:mm" name="startTime" required placeholder="00:00 ~ 23:59" date-parent="form">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">交易结束时间:</span>
              <div class="input-wrap">
                <input type="text" class="input-text date-end" date-fmt="H:mm" name="endTime" required placeholder="00:00 ~ 23:59" date-parent="form">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">单笔最低金额:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="minAmount" required amount maxIgnoreEmpy="[name='maxAmount']" message='{"maxIgnoreEmpy": "超过单笔最高金额"}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">单笔最高金额:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="maxAmount" required amount minIgnoreEmpy="[name='minAmount']" message='{"minIgnoreEmpy": "低于单笔最低金额"}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">日交易上限:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="dayMax" required amount minIgnoreEmpy="[name='maxAmount']" message='{"minIgnoreEmpy": "低于单笔最高金额"}' >
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">操作原因:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="reason">
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

    // 商户全称
    var fullName = $('[name="fullName"]');
    // 检测操作员手机号是否重复
    Compared.add('checkCustomerNo', function(val, params, ele, ansyFn) {
      Api.boss.findCustomerFullName(val, function(customerFullName) {
        // 填充商户全称
        if (customerFullName != 'FALSE') {
          fullName.val(customerFullName);
        } else {
          fullName.val('');
        }
        ansyFn(Compared.toStatus(customerFullName != 'FALSE', '此商户不存在!'));
      });
    });
    
    $(document).ready(function() {
      // 获取验证实例
      var formValidator = window.getValidator($('form'));
      // 挂接验证钩子
      formValidator.options.onCustomize = function() {
        // 当前对话框
        var dialog = window.queryDialog($('form'));
        Api.boss.checkCustProduct($('[name="customerNo"]').val(), $('#productType').val(), function(msg) {
          switch (msg) {
            case 'false':
              formValidator.submit(true);
              break;
            default:
              Messages.warn('该商户已存在此产品，不能重复添加!');
              dialog.loading(false);
              break;
          }
        });
        return false;
      };
   });

    
    
  </script>
</body>
</html>
