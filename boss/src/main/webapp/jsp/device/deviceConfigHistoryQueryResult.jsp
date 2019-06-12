<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>设备配置-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <s:if test="#attr['deviceConfigHistory'].list.size()>0">
    <vlh:root value="deviceConfigHistory" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>设备配置名称</th>
                <th>操作时间</th>
                <th>操作人</th>
              </tr>
            </thead>
            <tbody>
                <vlh:row bean="deviceConfigHistory">
                  <s:set name="dpayConfig" value="#attr['deviceConfigHistory']" />
                  <vlh:column property="update_key"></vlh:column>
                  <vlh:column property="update_time"  format="yyyy-MM-dd HH:ss:mm"></vlh:column>
                  <vlh:column property="operator"></vlh:column>
                </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:if test="#attr['deviceConfigHistory'].list.size()==0">
    <p class="pd-10">无符合条件的查询结果</p>
  </s:if>
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
