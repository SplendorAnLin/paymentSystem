<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>POS新增</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 2em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator ajaxFormNotification" id="form" action="${pageContext.request.contextPath}/posAdd.action" method="post" prompt="DropdownMessage"  data-success="新增成功" data-fail="新增失败">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-120">品牌:</span>
              <div class="input-wrap">
                <dict:select dictTypeId="POS_BRAND" name="pos.posBrand" styleClass="input-select"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-120">POS机型号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="pos.type" required >
              </div>
            </div>
          </div>
          <div class="item hidden">
            <div class="input-area">
              <span class="label w-120">批次号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="pos.batchNo" value="000001"  required rangelength='[1, 6]'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-120">POS机具序列号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="pos.posSn" required digits minlength='8'>
                <i id="tips" style="cursor: pointer;" class="color-orange fa fa-exclamation-circle"></i>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-120">POS机具采购数量:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="posSnNumber" required digits notZero value="1">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-120">当前软件版本号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="pos.softVersion" value="1.0.0" required >
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-120">当前参数版本号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="pos.paramVersion" value="1.0.1" required >
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-120">POS类型:</span>
              <div class="input-wrap">
                <dict:select dictTypeId="POS_TYPE" name="pos.posType" styleClass="input-select"></dict:select>
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
  
  
    <!-- 序列号提示 -->
    <div id="tip-content" style="width: 300px;display:none;" class="pd-10 tips">
      <h1>起始序列编号!</h1>
      <p class="text-secondary">例如: 序列号=1000, 采购数量=5</p>
      <p class="text-secondary">则序列号范围=1000~1005</p>
    </div>
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
  // 手机号下拉提示
  $('#tips').Dropdown({
    target: '#tip-content',
    direction: 'down',
    trigger: 'mouseenter',
    autoClose: true,
    style: ['Dropdown-on', 'Dropdown-off']
  });
  var form = $('form');
  // 挂接表单结果提示钩子
  form.data('receiver-hook', function(msg, closeDialog) {
      switch(msg) {
        case 'true':
          closeDialog(true);
          break;
        case 'false':
          closeDialog(false);
          break;
        default:
          closeDialog(false, '', true);
          new window.top.Alert('POS新增成功, 序列号: ' + msg + ' 重复!');
          //closeDialog(false, 'POS新增成功, 序列号: ' + msg + ' 重复!');
      }
  });
  </script>
</body>
</html>
