<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>登陆历史</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 1000px; padding: 10px;">
  
  <s:if test="#attr['loginLogQuery'].list.size()>0">
    <vlh:root value="loginLogQuery" url="loginLogQuery.action?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>登陆名</th>
                <th>登陆IP</th>
                <th>登陆时间</th>
                <th>退出时间</th>
                <th>IP所在地</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="loginLogQuery">
                <s:set name="entity" value="#attr['loginLogQuery']" />
                <vlh:column property="username" attributes="width='10%'" />
                <vlh:column attributes="width='10%'">${entity.ip}</vlh:column>
                <vlh:column attributes="width='10%'">
                  <s:date name="#entity.login_time" format="yyyy-MM-dd HH:mm:ss" />
                </vlh:column>
                <vlh:column attributes="width='10%'">
                  <s:date name="#entity.login_out_time" format="yyyy-MM-dd" />
                </vlh:column>
                <vlh:column property="remarks" attributes="width='10%'" />
              </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:if test="#attr['loginLogQuery'].list.size()==0">
    无符合条件的查询结果
  </s:if>

  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
