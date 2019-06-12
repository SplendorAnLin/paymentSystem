<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>设备采购审核-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">

  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <s:if test="#attr['agentDeviceOrderInfo'].list.size()>0">
    <vlh:root value="agentDeviceOrderInfo" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>订单流水号</th>
                <th>服务商编号</th>
                <th>服务商级别</th>
                <th>服务商简称</th>
                <th>设备类型</th>
                <th>采购渠道</th>
                <th>数量</th>
                <th>单价</th>
                <th>总价</th>
                <th>订单状态</th>
                <th>采购状态</th>
                <th>采购人</th>
                <th>创建时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="info">
                <s:set name="entity" value="#attr['info']" />
                <vlh:column attributes="width='45px'" >${entity.purchase_serial_number }</vlh:column>
                <vlh:column property="owner_id" />
                <vlh:column property="level" />
                <vlh:column property="agent_name" />
                <vlh:column property="device_name" attributes="width='80px'" />
                <vlh:column property="purchase_channel" attributes="width='220px'">${entity.purchase_channel}<dict:write dictId="${entity.purchase_channel }" dictTypeId="PURCHASE_CHANNEL"></dict:write></vlh:column>
                <vlh:column property="quantity" attributes="width='180px'" />
                <vlh:column property="unit_price" attributes="width='80px'" />
                <vlh:column property="total" attributes="width='180px' class='accoutNoStar'" />
                <vlh:column property="flow_status" attributes="width='80px'"><dict:write dictId="${entity.flow_status }" dictTypeId="DEVICE_ORDER_STATUS_COLOR"></dict:write></vlh:column>
                <vlh:column property="purchase_status" attributes="width='40px'"><dict:write dictId="${entity.purchase_status }" dictTypeId="AGENT_QRSTATUS_COLOR"></dict:write></vlh:column>
                <vlh:column property="user" />
                <vlh:column property="create_time" attributes="width='180px'" format="yyyy-MM-dd HH:mm:ss" />
                <vlh:column>
                  <s:if test="${entity.flow_status=='ORDER_WAIT' }">
                    <a class="btn-small" onclick="allocation('${entity.purchase_serial_number }', this);" href="javascript:void(0);">分配设备</a>
                    <a class="btn-small" onclick="ship('${entity.purchase_serial_number }', 2, this);" href="javascript:void(0);">拒绝</a>
                  </s:if>
                  <s:elseif test="${entity.flow_status=='ALLOT' && entity.purchase_status=='FALSE'}">
                    <a class="btn-small" href="deviceExportByPsn.action?purchaseSerialNumber=${entity.purchase_serial_number }">导出</a>
                    <a class="btn-small" onclick="ship('${entity.purchase_serial_number }', 1, this);" href="javascript:void(0);">发货</a>
                  </s:elseif>
                  <s:else>
                    <c:if test="${entity.purchase_status=='TRUE' }">
                      <a class="btn-small" href="deviceExportByPsn.action?purchaseSerialNumber=${entity.purchase_serial_number }">导出</a>
                    </c:if>
                    <a class="btn-small disabled" href="javascript:void(0);">处理完成</a>
                  </s:else>
                </vlh:column>
              </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:else>
    <p class="pd-10">无符合条件的查询结果</p>
  </s:else>

  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    // 分配
    function allocation(psNumber, btn) {
      $(btn).loading();
      utils.ajax({
        url: 'allotDevice.action?psNo=' + psNumber,
        type: 'post',
        success: function(status) {
          $(btn).ending();
          switch(status) {
            case 'true':
              Messages.success('分配成功');
              break;
            case 'false':
              Messages.error('分配失败');
              break;
            default:
              Messages.error(status);
              break;
          }
          window.RefresQueryResult();
        },
        error: function() {
          Messages.error('分配失败, 请稍后重试! #1');
          $(btn).ending();
        }
      });
    }

    // 发货/拒绝
    function ship(psNumber, type, btn) {
      $(btn).loading();
      var url = type === 1 ? 'remitDevice' : 'denyAllot';
      var prefix = type === 1 ? '发货' : '拒绝';
      utils.ajax({
        url: url + '.action?psNo=' + psNumber,
        type: 'post',
        success: function() {
          $(btn).ending();
          Messages.success(prefix + '成功!');
          window.RefresQueryResult();
        },
        error: function() {
          Messages.error(prefix + '失败, 请稍后重试! #1');
          $(btn).ending();
        }
      });
    }

  </script>
</body>
</html>
