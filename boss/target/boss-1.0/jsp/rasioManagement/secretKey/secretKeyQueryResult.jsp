<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>密钥管理-结果</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <s:if test="#attr['secretKeyInfo'].list.size()>0">
    <vlh:root value="secretKeyInfo" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>keyName</th>
                <th>keyVal</th>
                <th>checkValue</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="secretKeyInfo">
                <s:set name="secretKeyInfo" value="#attr['secretKeyInfo']" />
                <vlh:column property="key_name" />
                <vlh:column property="key_val" />
                <vlh:column property="check_value" />
                <vlh:column attributes="width='10%'">
                  <button data-myiframe='{
                    "target": "${pageContext.request.contextPath}/secretKeyById.action?id=${secretKeyInfo.id}",
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
      <%@ include file="../../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:if test="#attr['secretKeyInfo'].list.size()==0">
    <p class="pd-10">无符合条件的查询结果</p>
  </s:if>
  
  
  
  <%@ include file="../../include/bodyLink.jsp"%>
</body>
</html>
