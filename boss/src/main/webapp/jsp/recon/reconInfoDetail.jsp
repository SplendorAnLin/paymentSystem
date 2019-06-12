<%@ page language="java" pageEncoding="UTF-8" %>
<html>
<head>
  <title>差错明细</title>
  <%@ include file="../include/header.jsp" %>
</head>
<body style="width: 1200px;padding: 10px;">

<div class="title-h1 fix">
  <span class="primary fl">差错明细</span>
</div>
<c:if test="${page.object != null && page.object.size() > 0 }">
  <div class="table-warp">
    <div class="table-sroll">
      <table class="data-table shadow--2dp  w-p-100 tow-color">
        <thead>
        <tr>
          <th><input type="checkbox" class="checkbox"></th>
          <th>对账单编号</th>
          <th>对账类型</th>
          <th>交易订单/账户订单</th>
          <th>接口订单/银行通道订单</th>
          <th>对账异常类型</th>
          <th>金额</th>
          <th>处理状态</th>
          <th>处理备注</th>
          <th>对账日期</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.object }" var="rec">
          <tr>
            <td>
              <c:if test="${rec.handleStatus =='HANDLING'}">
                <input type="checkbox" class="checkbox" value="${rec.reconOrderId }"/>
              </c:if>
            </td>
            <td class="reconNo">${rec.reconOrderId }</td>
            <td><dict:write dictId="${rec.reconType.name() }" dictTypeId="RECON_TYPE"></dict:write></td>
            <td class="orderCode">${rec.tradeOrderCode }</td>
            <td class="orderCode">${rec.interfaceOrderCode }</td>
            <td>${rec.reconExceptionType.remark }</td>
            <td><fmt:formatNumber value="${rec.amount}" pattern="#.##"/></td>
            <td><dict:write dictId="${rec.handleStatus }" dictTypeId="HANDLE_STATUS"></dict:write></td>
            <td>${rec.handleRemark }</td>
            <td><fmt:formatDate value="${rec.reconDate }" pattern="yyyy-MM-dd"/></td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
  <div class="jump-rarp mt-20 text-center" id="PageWrapCustomize" total_count="${page.totalResult}"
       total="${page.totalPage}" current="${page.currentPage}"></div>
  <div class="text-center text-center">
    <a class="btn" id="passBatchButton" onclick="passBatch(this);">批量处理</a>
    <a class="btn" id="exceptionExport" onclick="infoExport();">导出</a>
    <form class="hidden" action="exceptionUpload.action" id="uploadForm" enctype="multipart/form-data" method="post"
          target="table-result2">
      <input type="hidden" id="fileName" name="msg">
      <input type="file" name="file" id="fileInput"
             accept="application/vnd.ms-excel,application/x-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>
    </form>
    <a class="btn" onclick="infoUpload();">上传</a>
    <iframe class="hidden" id="uploadBack" name="table-result2"></iframe>
  </div>
</c:if>
<c:if test="${page.object eq null or page.object.size() == 0 }">
  无符合条件的记录
</c:if>


<!-- 批量对账处理对话框 -->
<div id="recon-complete" style="width: 600px; padding: 10px;display:none;">
  <form method="post">
    <div class="detail" style="padding: 10px 0px;">
    </div>
    <div class="title-h1">
      <span class="primary">备注</span>
    </div>
    <div class="block">
      <textarea class="remark" style="border: 1px solid #d3d3d3;width: 100%;height:100px;" rows="2"></textarea>
      <span class="error-msg" style="color: red; display: none;"></span>
    </div>
  </form>
</div>

<%@ include file="../include/bodyLink.jsp" %>
<script>
    // 分页
    $('#PageWrapCustomize').pageCustom('findReconInfo.action', 'startTime=<%=request.getParameter("startTime")%>&reconException.reconType=<%=request.getParameter("reconException.reconType")%>');

    // 获取选择订单信息
    function orderInfo() {
        // 订单复选框
        var orders = $('tbody td input[type="checkbox"]');
        // 未选中任何订单则提示
        if (orders.notCheckAll()) {
            Messages.warn('请至少选择一条对账订单!');
            return;
        }
        // 订单信息
        var infos = [];
        var reconNos = '';
        orders.each(function (i) {
            // 忽略未选中订单
            if (!this.checked)
                return;
            var tr = $(this).closest('tr');
            // 订单信息
            var info = {
                // 对账单编号
                reconNo: $.trim($('.reconNo', tr).text()),
                // 订单编号
                orderCode: $.trim($('.orderCode', tr).text())
            };
            reconNos = reconNos + info.reconNo + ',';
            infos.push(info);
        });
        reconNos = reconNos.slice(0, reconNos.length - 1);
        return {
            reconNos: reconNos,
            infos: infos
        };
    }

    // 根据订单生成对话框DOM
    function batchInsetDom(orderInfo) {
        var reconWrap = $('#recon-complete');

        var detail = $('.detail', reconWrap).empty();
        for (var i = 0; i < orderInfo.length; ++i) {
            var info = orderInfo[i];
            var tmp = '<div><div class="col w-p-50"><div class="row"><span class="label">对账单编号:</span><span class="value">{{reconNo}}</span></div></div><div class="col w-p-50">'
                + '<div class="row"><span class="label">订单编号:</span><span class="value">{{orderCode}}</span></div></div></div>';
            detail.append($(utils.tpl(tmp, {
                reconNo: info.reconNo,
                orderCode: info.orderCode
            })));
        }
    }

    // 开始批量处理
    function passBatch() {
        var orderInfos = orderInfo();
        if (!orderInfos)
            return;
        // 创建订单信息dom
        batchInsetDom(orderInfos.infos);
        // 显示批量 处理对话框
        var dialog = new window.top.MyDialog({
            title: '批量对账处理',
            target: $('#recon-complete').clone().show(),
            btns: [
                {
                    lable: '取消'
                },
                {
                    lable: '确定',
                    click: function (data, content) {
                        var remark = $('.remark', content).val();
                        if (!remark || $.trim(remark) == '') {
                            Messages.error('请填写备注信息!');
                            return;
                        }
                        batchSubmit(orderInfos.reconNos, $.trim(remark), dialog);
                    },
                },
            ]
        });
    }

    // 批量处理请求
    function batchSubmit(reconNos, remark, dialog) {
        dialog.loading(true);
        utils.ajax({
            url: 'handerReconException.action',
            type: 'post',
            data: {
                "orders": reconNos,
                "reconException.handleRemark": remark
            },
            success: function () {
                Messages.success('批量对账处理成功!');
                overClose(dialog);
            },
            error: function () {
                Messages.error('网络异常, 处理失败, 请稍后再试...');
                overClose(dialog);
            }
        });
    }

    // 关闭批量处理对话框
    function overClose(dialog, btn) {
        dialog.loading(false);
        dialog.close(null, true);
        window.location.reload();
    }


    // 导出
    function infoExport() {
         window.open('exceptionExport.action?reconException.reconOrderId=${reconException.reconOrderId}&reconException.reconExceptionType=${reconException.reconExceptionType}');
     }

    var uploadForm = $('#uploadForm');
    var fileInput = $('#fileInput');
    fileInput.change(function () {
        if (this.value && this.value != '') {
            $('#fileName').val(this.value.replace(/c:\\fakepath\\/i, ''));
            $('.load-img').show();
            window.dialogIframe.loading(true);
            uploadForm.submit();
        }
    });
    $('#uploadBack').load(function () {
        fileInput.value = '';
        $('#fileName').val('');
        $('.load-img').hide();
        window.dialogIframe.loading(false);
        window.top.Alert('上传成功', '请确认');
        window.location.reload();
        // Messages.success('上传成功!');
    });

    function infoUpload(btn) {
        fileInput.trigger('click');
    }

</script>
</body>
</html>
