<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>POS信息-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  
  <s:if test="#attr['posInfo'].list.size()>0">
    <vlh:root value="posInfo" url="?" includeParameters="*" configName="defaultlook">
    
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>POS终端号</th>
                <th>所属服务商</th>
                <th>所属商户</th>
                <th>品牌</th>
                <th>POS机型号</th>
                <th>所属网点</th>
                <th>状态</th>
                <th>批次号</th>
                <th>操作员</th>
                <th>POS机具序列号</th>
                <th>创建时间</th>
                <th>POS类型</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="posInfo">
                <s:set name="posInfo" value="#attr['posInfo']" />
                <vlh:column property="pos_cati" />
                <vlh:column property="agent_no" />
                <vlh:column property="customer_no" />
                <vlh:column><dict:write dictId="${posInfo.pos_brand }" dictTypeId="POS_BRAND"></dict:write></vlh:column>
                <vlh:column property="type" />
                <vlh:column property="shop_no" />
                <vlh:column><dict:write dictId="${posInfo.status }" dictTypeId="STATUS"></dict:write></vlh:column>
                <vlh:column property="batch_no" />
                <vlh:column property="operator" />
                <vlh:column property="pos_sn" />
                <vlh:column property="create_time" format="yyyy-MM-dd HH:mm:ss" />
                <vlh:column><dict:write dictId="${posInfo.pos_type }" dictTypeId="POS_TYPE"></dict:write></vlh:column>
              </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
    
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:if test="#attr['posInfo'].list.size() == 0">
    <p class="pd-10">无符合条件的查询结果</p>
  </s:if>
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
