<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>Mcc管理-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
    <div class="title-h1 fix">
      <span class="primary fl">查询结果</span>
    </div>
    
    <s:if test="#attr['mccInfo'].list.size()>0">
      <vlh:root value="mccInfo" url="?" includeParameters="*" configName="defaultlook">
        <div class="table-warp">
          <div class="table-sroll">
            <table class="data-table shadow--2dp  w-p-100 tow-color nextline">
              <thead>
                <tr>
                  <th>MCC</th>
                  <th>类别</th>
                  <th>标准费率</th>
                  <th>简称</th>
                  <th>描述</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                <vlh:row bean="mccInfo">
                  <s:set name="mccInfo" value="#attr['mccInfo']" />
                  <vlh:column property="mcc" />
                  <vlh:column attributes="width='80px'"><dict:write dictId="${mccInfo.category }" dictTypeId="CATEGORY"></dict:write></vlh:column>
                  <vlh:column property="rate" />
                  <vlh:column property="name" />
                  <vlh:column property="description" />
                  <vlh:column attributes="width='10%'">
                    <button data-myiframe='{
                      "target": "${pageContext.request.contextPath}/mccById.action?id=${mccInfo.id}",
                      "btnType": 2,
                      "btns": [
                        {"lable": "取消"},
                        {"lable": "修改", "trigger": ".btn-submit"}
                      ]
                    }' class="btn-small">修改</button>
                  </vlh:column>
                </vlh:row>
              </tbody>
            </table>
          </div>
        </div>
        <%@ include file="../include/vlhPage.jsp"%>
      </vlh:root>
    </s:if>
    <s:if test="#attr['mccInfo'].list.size()==0">
      <p class="pd-10">无符合条件的查询结果</p>
    </s:if>
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
