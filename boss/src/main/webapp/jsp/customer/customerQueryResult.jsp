<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>商户信息-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
    
  <s:if test="#attr['customerInfo'].list.size()>0">
    <vlh:root value="customerInfo" url="?" includeParameters="*" configName="defaultlook">
    
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>商户编号</th>
                <th>商户名称</th>
                <th>商户简称</th>
                <th>服务商</th>
                <th>商户地址</th>
                <th>状态</th>
                <th>商户类型</th>
                <th>开通时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="customerInfo"><s:set name="customer" value="#attr['customerInfo']" /><vlh:column property="customer_no" />
                <vlh:column>${customer.full_name}</vlh:column>
                <vlh:column>${customer.short_name}</vlh:column>
                <vlh:column property="agent_no" />
                <vlh:column>${customer.province }${customer.city }${customer.addr}</vlh:column>
                <vlh:column><dict:write dictId="${customer.status }" dictTypeId="CUSTOMER_STATUS_COLOR"></dict:write></vlh:column>
                <vlh:column><dict:write dictId="${customer.customer_type }" dictTypeId="AGENT_TYPE"></dict:write></vlh:column>
                <vlh:column><s:date name="#customer.create_time" format="yyyy-MM-dd" /></vlh:column>
                <vlh:column attributes="width='10%'">
                  <s:if test="${fn:length(customer.pay_no)>0}">
                    <button onclick="showQrCode('${customer.pay_no}', this)" class="btn-small">查看设备</button>
                  </s:if>
                  <s:else>
                    <button data-myiframe='{
                      "target": "${pageContext.request.contextPath}/toAllotCustDevice.action?custNo=${customer.customer_no}",
                      "btns": [
                        {"lable": "取消"},
                        {"lable": "分配", "trigger": ".btn-submit"}
                      ]
                    }' class="btn-small">分配设备</button>
                  </s:else>
                  
                  <button data-dialog="${pageContext.request.contextPath}/customerDetail.action?customer.customerNo=${customer.customer_no}" class="btn-small">详细</button>
                  <button data-myiframe='{
                    "target": "${pageContext.request.contextPath}/toUpdateCustomerAction.action?customer.customerNo=${customer.customer_no}",
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "修改", "trigger": ".btn-submit"}
                    ]
                  }' class="btn-small">修改</button>
                  <button onclick="restPassWord('${customer.customer_no}')" class="btn-small <c:if test="${customer.status  != 'TRUE' }">disabled</c:if>" >重置密码</button>
                </vlh:column>
              </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:if test="#attr['customerInfo'].list.size()==0">
    无符合条件的查询结果
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
  
    // 重置商户密码
    function restPassWord(customerNo) {
      AjaxConfirm('确认重置此商户密码?', '请确认', {
        url: '${pageContext.request.contextPath}/toresetPassWordAction.action?customer.customerNo=' + customerNo,
        dataType: 'json',
        type: 'post',
        success: function(msg) {
          Messages.success('重置商户成功!');
        },
        error: function() {
          Messages.error('网络异常, 重置商户密码失败, 请稍后重试!');
        }
      }, true);
    }
    
    
    
  </script>
</body>
</html>
