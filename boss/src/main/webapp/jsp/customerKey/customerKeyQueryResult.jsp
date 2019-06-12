<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>商户密匙-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  
  <s:if test="#attr['customerKeyQuery'].list.size()>0">
    <vlh:root value="customerKeyQuery" url="?" includeParameters="*" configName="defaultlook">
    
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>商户编号</th>
                <th>商户名称</th>
                <th>类型</th>
                <th>创建时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>

              <vlh:row bean="customerKeyQuery">
                <s:set name="customerKey" value="#attr['customerKeyQuery']" />
                <vlh:column property="customer_no" />
                <vlh:column>${customerKey.short_name}</vlh:column>
                <vlh:column property="key_type" />
                <vlh:column><s:date name="#customerKey.create_time" format="yyyy-MM-dd" /></vlh:column>
                <vlh:column>
                  <button onclick="showKey(this);"  data-key="${customerKey.public_key}" class="btn-small">查看</button>
                </vlh:column>
              </vlh:row>

            </tbody>
          </table>
        </div>
      </div>
      
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:if test="#attr['customerKeyQuery'].list.size()==0">
    无符合条件的查询结果
  </s:if>
  
  
  
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
