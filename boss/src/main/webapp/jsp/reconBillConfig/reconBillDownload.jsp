<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>对账单下载</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>
  
  <form class="validator" action="reconBillDown.action" method="post" prompt="message"  style="padding: 2em 0.4em 0.4em;">

    <!--对账单路径-->
    <select class="hidden" name="reconBillUrl" id="reconBillUrl">
      <c:forEach items="${reconBillList}" var="reconBill">
        <option value="${reconBill.id }">${reconBill.reconBillUrl }</option>
      </c:forEach>
    </select>
    <!--对账单时间-->
    <select class="hidden"  name="reconBillTime" id="reconBillTime">
      <c:forEach items="${reconBillList}" var="reconBill">
        <option value="${reconBill.id }">${reconBill.generateTime }</option>
      </c:forEach>
    </select>
    <!--文件前缀-->
    <select class="hidden"  name="filePrefixSelect" id="filePrefixSelect">
      <c:forEach items="${reconBillList}" var="reconBill">
        <option value="${reconBill.id }">${reconBill.filePrefix }</option>
      </c:forEach>
    </select>

    <!-- 文件路径 -->
    <input type="hidden" id="fileUrl" name="fileUrl" value="${reconBillList[0].reconBillUrl}" /> 
    <!-- 文件前缀 -->
    <input type="hidden" id="filePrefix" name="filePrefix" value="${reconBillList[0].filePrefix }" /> 


    <div class="fix">
      <div class="item">
        <div class="input-area">
          <span class="label w-90">支付接口:</span>
          <div class="input-wrap">
            <select class="input-select" name="interfaceName" onchange="changeItnerFace(this.val);" required>
              <c:forEach items="${reconBillList}" var="reconBill">
                <option value="${reconBill.id }">${reconBill.interfaceName }</option>
              </c:forEach>
            </select>
          </div>
        </div>
      </div>
      <div class="item">
        <div class="input-area">
          <span class="label w-90">对账单时间:</span>
          <div class="input-wrap">
            <input type="text" class="input-text input-date default-date" name="generateTime" required checkGenerateTime date>
          </div>
        </div>
      </div>
    </div>


    <div class="text-right" style="width: 278px;">
      <button class="btn btn-submit">下载接口对账单</button>
    </div>

  </form>
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>


  // 检测对账单时间
  Compared.add('checkGenerateTime', function(val, params, ele, ansyFn) {
    // 当前日期
    var now = new Date();
    // 当前 年-月-日
    var nowDate = utils.formatTime(now, 'yyyy-MM-dd');
    // 从隐藏下拉列表获取账单生成时间 时:分
    var billTime = reconBillTime.currentOption().text();
    // 获取对账单时间 年-月-日 时:分:秒
    var downTime = nowDate + ' ' + billTime + ':00';
    // 当前选择日期
    var selectDate = val + ' 23:59:59';

    // 是否等于今天
    if (val === nowDate) {
      // 当前时间小于账单生成时间
      if (now < new Date(downTime)) {
        return Compared.toStatus(false, '还未到账单生成时间!');
      }
    } else {
      // 当前时间小于选择日期
      if (now < new Date(selectDate)) {
        return Compared.toStatus(false, '选择日期不能超过当前时间!');
      }
    }

    return Compared.toStatus(true);
  });

    var reconBillUrl = $('#reconBillUrl');
    var reconBillTime = $('#reconBillTime');
    var filePrefixSelect = $('#filePrefixSelect');
    var fileUrl = $('#fileUrl');
    var filePrefix = $('#filePrefix');


    // 改变接口类型
    function changeItnerFace(interfaceType) {
      // 隐藏下拉列表选中
      reconBillUrl.selectForValue(interfaceType);
      reconBillTime.selectForValue(interfaceType);
      filePrefixSelect.selectForValue(interfaceType);
      // 隐藏输入框赋值
      fileUrl.val(reconBillUrl.currentOption().text());
      filePrefix.val(filePrefixSelect.currentOption().text());
    } 
  </script>
</body>
</html>
