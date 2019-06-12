<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>POS分配信息-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  
  <s:if test="#attr['posAssignOuter'].list.size()>0">
    <vlh:root value="posAssignOuter" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th><input type="checkbox" class="checkbox"></th>
                <th>POS终端号</th>
                <th>品牌</th>
                <th>POS机型号</th>
                <th>状态</th>
                <th>批次号</th>
                <th>操作员</th>
                <th>POS机具序列号</th>
                <th>创建时间</th>
                <th>POS类型</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="posAssignOuter">
                <s:set name="posAssignOuter" value="#attr['posAssignOuter']" />
                <vlh:column attributes="width='45px'" >
                <input type="checkbox" class="checkbox" name="select-posOrder" value="${posAssignOuter.pos_cati }" >
                </vlh:column>
                <vlh:column property="pos_cati" />
                <vlh:column><dict:write dictId="${posAssignOuter.pos_brand }" dictTypeId="POS_BRAND"></dict:write></vlh:column>
                <vlh:column property="type" />
                <vlh:column><dict:write dictId="${posAssignOuter.status }" dictTypeId="STATUS"></dict:write></vlh:column>
                <vlh:column property="batch_no" />
                <vlh:column property="operator" />
                <vlh:column property="pos_sn" />
                <vlh:column property="create_time" format="yyyy-MM-dd HH:mm:ss" />
                <vlh:column><dict:write dictId="${posAssignOuter.pos_type }" dictTypeId="POS_TYPE"></dict:write></vlh:column>
              </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
      <%@ include file="../include/vlhPage.jsp"%>
      <div class="text-center mt-10">
        <a class="btn" onclick="posBatch.delivery(this)" href="javascript:void(0);">批量出库</a>
        <a class="btn"onclick="posBatch.bind(this)" href="javascript:void(0);">批量绑定</a>
      </div>
    </vlh:root>
  </s:if>
  <s:if test="#attr['posAssignOuter'].list.size() == 0">
    <p class="pd-10">无符合条件的查询结果</p>
  </s:if>
  
  
  <!-- POS批量出库对话框-->
  <div id="pos-batch-Delivery" style="width: 378px; padding: 0.2em;display:none;">
    <div class="text-left" style="padding: 2em 1.2em;">
      <form class="validator stop-formeNnter" prompt="DropdownMessage">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">处理数量:</span>
              <div class="input-wrap">
                <span class="text-secondary"><span class="mr-5 count">1</span>个</span>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">所属代理商:</span>
              <div class="input-wrap">
                <input type="text" name="agentNo" class="input-text agentNo" required checkPosAgentNo trigger='{"checkPosAgentNo": 2}' />
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">代理商全称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text agentName" id="agentName" disabled />
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
  
  <!-- POS批量绑定对话框-->
  <div id="pos-batch-Bind" style="width: 378px; padding: 0.2em;display:none;">
    <div class="text-left" style="padding: 2em 1.2em;">
      <form class="validator stop-formeNnter" prompt="DropdownMessage">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">处理数量:</span>
              <div class="input-wrap">
                <span class="text-secondary"><span class="mr-5 count">1</span>个</span>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">所属商户:</span>
              <div class="input-wrap">
                <input type="text" name="customerNo" class="input-text customerNo" required checkPosCustomerNo trigger='{"checkPosCustomerNo": 2}' />
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">商户全称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text customerName" id="customerName" disabled />
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
