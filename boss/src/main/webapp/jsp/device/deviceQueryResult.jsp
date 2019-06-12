<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>设备管理-结果</title> 
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
 
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <s:if test="#attr['deviceInfo'].list.size()>0">
    <vlh:root value="deviceInfo" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>设备编号</th>
                <th>设备类型</th>
                <th>设备价格</th>
                <th>采购流水号</th>
                <th>所属服务商</th>
                <th>所属商户</th>
                <th>所属批次</th>
                <th>状态</th>
                <th>出库时间</th>
                <th>激活时间</th>
                <th>更新时间</th>
                <th>创建时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="info">
                <s:set name="entity" value="#attr['info']" />
                <vlh:column property="customer_pay_no" attributes="width='7%'"/>
                <vlh:column property="device_name" attributes="width='10%'"/>
                <vlh:column property="unit_price" attributes="width='10%'"/>
                <vlh:column property="purchase_serial_number" attributes="width='10%'"/>
                <vlh:column property="agent_no" attributes="width='10%'"/>
                <vlh:column property="customer_no" attributes="width='10%'"/>
                <vlh:column property="batch_number" attributes="width='10%'"/>
                <vlh:column property="status" attributes="width='10%'" ><dict:write dictId="${entity.status }" dictTypeId="PURCHASING_STATUS_COLOR"></dict:write></vlh:column>
                <vlh:column property="out_warehouse_time" attributes="width='15%'"><s:date name="#entity.out_warehouse_time" format="yyyy-MM-dd HH:mm:ss" /></vlh:column>
                <vlh:column property="activate_time" attributes="width='15%'"><s:date name="#entity.activate_time" format="yyyy-MM-dd HH:mm:ss" /></vlh:column>
                <vlh:column property="update_time" attributes="width='15%'"><s:date name="#entity.update_time" format="yyyy-MM-dd HH:mm:ss" /></vlh:column>
                <vlh:column property="create_time" attributes="width='15%'"><s:date name="#entity.create_time" format="yyyy-MM-dd HH:mm:ss" /></vlh:column>
                <vlh:column>
                  <a class="btn-small" onclick="showQrCode('${entity.customer_pay_no}', this);" href="javascript:void(0);">查看二维码</a>
                </vlh:column>
              </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:if test="#attr['deviceInfo'].list.size()==0">
    <p class="pd-10">没有查找到符合条件的结果</p>
  </s:if>
  
  
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
      Api.boss.findImgForPayNo(payNumber, function(chunkBaee64, orderNumber) {
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
