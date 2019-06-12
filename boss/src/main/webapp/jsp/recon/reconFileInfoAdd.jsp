<%@ page language="java" pageEncoding="UTF-8" %>
<html>
<head>
  <title>新增银行通道对账文件</title>
  <%@ include file="../include/header.jsp" %>
</head>
<body style="width: 450px; padding:0.2em;">

<div class="query-box" style="padding: 0.8em 0.1em 0 4.5em;">
  <!--查询表单-->
  <div class="query-form">
    <form class="validator notification" action="reconFileInfoAdd.action" method="post" prompt="DropdownMessage"
          data-success="新增成功" data-fail="新增失败">
      <div class="fix">
        <div class="item">
          <div class="input-area">
            <span class="label w-100">接口编号：</span>
            <div class="input-wrap">
              <select onchange="getBankName(this)" class="input-select"
                      name="reconFileInfo.reconFileInfoExtend.interfaceInfoCode" required
                      checkInterfaceCode prompt="NextLineMessage">
                <option value="">请选择</option>
                <c:forEach items="${ myInterfaceInfoBeans}" var="info">
                  <option date-name="${info.name }" value="${info.code }">${info.code }</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">接口名称：</span>
            <div class="input-wrap">
              <input id="bankName" type="text" class="input-text" readonly
                     name="reconFileInfo.reconFileInfoExtend.bankName" required>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">是否开启：</span>
            <div class="input-wrap">
              <div class="input-wrap">
                <select class="input-select" name="reconFileInfo.valid" disabled required>
                  <option value="0">关闭</option>
                  <option value="1" selected>开启</option>
                </select>
              </div>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">文件名：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconFileInfo.fileName" required>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">表头行：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconFileInfo.reconFileInfoExtend.header" required>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">合计行：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconFileInfo.reconFileInfoExtend.footer">
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">分隔符：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconFileInfo.reconFileInfoExtend.delimiter" maxlength="1"
                     required>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">通道订单号列名：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconFileInfo.reconFileInfoExtend.bankOrderCode" required>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">接口订单号列名：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconFileInfo.reconFileInfoExtend.interfaceOrderCode"
                     required>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">订单金额列名：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconFileInfo.reconFileInfoExtend.amount" required>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">手续费列名：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconFileInfo.reconFileInfoExtend.fee" required>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">交易时间列名：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconFileInfo.reconFileInfoExtend.transTime" required>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">备注：</span>
            <div class="input-wrap">
              <textarea name="reconFileInfo.remark" cols="22" rows="4"></textarea>
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

<%@ include file="../include/bodyLink.jsp" %>
<script>
    // 检测接口编号是否重复
    var interfaceInfoCode = '${reconFileInfo.reconFileInfoExtend.interfaceInfoCode }';
     Compared.add('checkInterfaceCode', function (val, params, ele, ansyFn) {
        if (val === interfaceCode && interfaceCode !== '')
            return Compared.toStatus(true);
        Api.boss.checkInterfaceCode(val, function (status) {
            ansyFn(Compared.toStatus(status == 'TRUE', '此接口编号已存在!'));
        });
    });

    //选种后赋值名称
    function getBankName(self) {
        $('#bankName').val($('option:selected', self).attr('date-name'));
    }
</script>
</body>
</html>
