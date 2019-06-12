<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>设备生产订单-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <s:if test="#attr['deviceOrderInfo'].list.size()>0">
    <vlh:root value="deviceOrderInfo" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>批次号</th>
                <th>设备类型</th>
                <th>数量</th>
                <th>单价</th>
                <th>总价</th>
                <th>采购状态</th>
                <th>采购人</th>
                <th>创建时间</th>
                <th>最后更新时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="info">
                <s:set name="entity" value="#attr['info']" />
                <vlh:column property="batch_number" attributes="width='10%'" />
                <vlh:column property="device_name" attributes="width='7%'"/>
                <vlh:column property="quantity" attributes="width='10%'"/>
                <vlh:column property="unit_price" attributes="width='10%'"><fmt:formatNumber value="${entity.unit_price }" pattern="#0.00#" /></vlh:column>
                <vlh:column property="total" attributes="width='10%'"><fmt:formatNumber value="${entity.total }" pattern="#0.00#" /></vlh:column>
                <vlh:column property="purchase_status" attributes="width='7%'"><dict:write dictId="${entity.purchase_status }" dictTypeId="PURCHASING_STATUS_COLOR"></dict:write></vlh:column>
                <vlh:column property="user" attributes="width='10%'"/>
                <vlh:column property="create_time" attributes="width='15%'"><s:date name="#entity.create_time" format="yyyy-MM-dd HH:mm:ss" /></vlh:column>
                <vlh:column property="update_time" attributes="width='15%'"><s:date name="#entity.update_time" format="yyyy-MM-dd HH:mm:ss" /></vlh:column>
                <vlh:column>
                  <a class="btn-small" <c:if test="${entity.purchase_status eq 'ORDER_WAIT'}">onclick="exprot(this, '${entity.batch_number }');"</c:if> href="deviceExport.action?batchNumber=${entity.batch_number }">导出</a>  
                  <s:if test="${entity.purchase_status eq 'MAKING'}">
                    <a class="btn-small" onclick="deviceSave('${entity.batch_number }', this)" href="javascript:void(0);">入库</a>
                  </s:if>
                  <s:if test="${entity.purchase_status eq 'OK' }">
                    <a class="btn-small disabled" href="javascript:void(0);">已完成</a>
                  </s:if>
                </vlh:column>
              </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:if test="#attr['deviceOrderInfo'].list.size()==0">
    <p class="pd-10">没有查找到符合条件的结果</p>
  </s:if>
  
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
  // 1. 采购状态:初始化  >> 点击导出
  // 2. 采购状态改为:制作中 >> 出现入库按钮
  // 3. 采购状态改为: 已入库 >> 显示已完成禁用样式按钮



  // 轮循检测设备状态, 是否导出表格
  function exprot(btn, batchNumber) {
    var url = 'checkOrderMaking.action?batchNumber=' + batchNumber;
    $(btn).loading();
    // 循环检测次数
    var count = 0;

    // 完毕回调
    var overFn = function() {
      clearInterval(timeId);
      $(btn).ending();
      // 刷新当前查询表格
      window.RefresQueryResult();
    };

    var timeId = setInterval(function() {
      if (count >= 30) {
        clearInterval(timeId);
        $(btn).ending();
        return;
      }
      utils.ajax({
        url: url,
        type: 'get',
        success: function(msg) {
          if (msg === 'true') {
            Messages.success('导出成功!');
            overFn();
          }
        },
        error: function() {
          Messages.error('检测设备之作状态失败， 请稍后重试...');
          overFn();
        }
      });
    }, 1000);


  }

  // 设备入库
  function deviceSave(batchNumber, btn) {
    $(btn).loading();
    utils.ajax({
      url: 'warehousing.action?batchNumber=' + batchNumber,
      type: 'post',
      success: function(status) {
        $(btn).ending();
        if (status === 'true') {
          Messages.success('入库成功!');
        } else {
          Messages.error('入库失败!');
        }
        window.RefresQueryResult();
      },
      error: function() {
        Messages.error('网络异常, 设备入库失败! #1');
        $(btn).ending();
      }
    });
  }


  </script>
</body>
</html>
