<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>代收路由-结果<</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  
  <s:if test="#attr['routeConfig'].list.size()>0">
    <vlh:root value="routeConfig" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>路由编码</th>
                <th>路由名称</th>
                <th>路由状态</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="defConfig">
                <s:set name="entity" value="#attr['defConfig']" />
                <vlh:column property="code" attributes="width='10%'" />
                <vlh:column property="name" attributes="width='12%'"/>
                <vlh:column property="status" attributes="width='8%'"><dict:write dictId="${entity.status }" dictTypeId="STATUS_COLOR"></dict:write></vlh:column>
                <vlh:column attributes="width='10%'">
                  <button data-myiframe='{
                    "target": "${pageContext.request.contextPath}/dfRouteQueryByCode.action?routeConfigBean.code=${entity.code }&operate=update",
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "修改", "trigger": ".btn-submit"}
                    ]
                  }' class="btn-small">修改</button>
                  <button data-dialog="${pageContext.request.contextPath}/dfRouteQueryByCode.action?routeConfigBean.code=${entity.code }&operate=detail" class="btn-small">详情</button>
                </vlh:column>
              </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
    
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:if test="#attr['routeConfig'].list.size()==0">
    <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;没有查找到信息
  </s:if>
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
