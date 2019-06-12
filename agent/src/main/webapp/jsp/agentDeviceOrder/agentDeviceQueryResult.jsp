<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>设备管理 - 结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <c:if test="${deviceInfo!=null &&deviceInfo.size()>0}">
    <div class="table-warp">
      <div class="table-sroll">
        <table class="data-table shadow--2dp  w-p-100 tow-color">
          <thead>
            <tr>
              <th>设备编号</th>
              <th>采购流水号</th>
              <th>收款编号</th>
              <th>所属商户</th>
              <th>状态</th>
              <th>出库时间</th>
              <th>激活时间</th>
              <th>更新时间</th>
              <th>创建时间</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${deviceInfo }" var="info">
              <tr>
                <td>${info.id }</td>
                <td>${info.purchase_serial_number }</td>
                <td>${info.customer_pay_no }</td>
                <td>${info.customer_no }</td>
                <td><dict:write dictId="${info.status }" dictTypeId="DEVICE_STATUS_COLOR"></dict:write></td>
                <td><fmt:formatDate value="${info.out_warehouse_time }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                <td><fmt:formatDate value="${info.activate_time }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                <td><fmt:formatDate value="${info.update_time }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                <td><fmt:formatDate value="${info.create_time }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                <td>
                  <a href="javascript:void(0);" onclick="showQrCode('${info.customer_pay_no}', this);" class="btn-small">查看设备</a>
                </td> 
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </c:if>
  <c:if test="${deviceInfo eq null or deviceInfo.size() == 0}">
    <p class="pd-10">无符合条件的记录</p>
  </c:if>
  
  <!-- 二维码显示对话框 -->
  <div id="code-wrap" style="width: 500px;height:708px;display:none;overflow-y: hidden;">
    <canvas width="1000" height="1414.6272" style="width: 500px;height:708px;"></canvas>
  </div>
  
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
  // 拼接二维码
  (function() {
    // 二维码显示容器
    var codeWrap = $('#code-wrap');
    // 画布
    var canvas = $('canvas', codeWrap).get(0);
    var ctx = canvas.getContext('2d');
    // 背景图
    var bg = null;
    // 加载二维码背景
    utils.loadImage('${pageContext.request.contextPath}/images/shopQrCode.png', function(img, status) {
      if (!status) {
        console.warn('获取二维码背景图片失败...');
        return;
      }
      bg = img;
      // 将背景画入画布
      ctx.fillStyle = '#FFF';
      ctx.drawImage(bg, 0, 0, canvas.width, canvas.height);
    });

    // 显示二维码对话框
    function showQrCode(payNumber, btn) {
      $(btn).loading();
      Api.agent.findImgForPayNo(payNumber, function(chunkBaee64, orderNumber) {
        if (!chunkBaee64) {
          $(btn).ending();
          Messages.error('获取二维码失败, 请稍后重试! #1');
          return;
        }
     // 将背景画入画布
        ctx.fillStyle = '#FFF';
        ctx.drawImage(bg, 0, 0, canvas.width, canvas.height);
        // 将base64赋予img标签
        var codeImg = new Image();
        codeImg.src = 'data:image/jpeg;base64,' + chunkBaee64;
        codeImg.onload = function() {
          // 画笔与原始背景比例
          var ratio = canvas.width / bg.naturalWidth;
          ctx.drawImage(codeImg, 810 * ratio, 1180 * ratio, ratio * 855, ratio * 855);
          ctx.fillStyle = '#000';
          ctx.font = '30px Microsoft yahei';
          ctx.fillText('NO:' + orderNumber, 860 * ratio, 2150 * ratio);
          // 显示对话框
          $(btn).ending();
          var dialog = new window.top.MyDialog({
            target: codeWrap,
            title: '查看二维码',
            isModal: false
          });

        };
      });
    }
    window.showQrCode = showQrCode;
  })();


  </script>
</body>
</html>
