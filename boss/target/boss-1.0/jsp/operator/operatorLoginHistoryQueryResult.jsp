<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>用户登陆日志-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">

  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>

  <s:if test="#attr['loginLogQuery'].list.size()>0">
    <vlh:root value="loginLogQuery" url="operatorLoginHistoryQuery.action?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>所属系统</th>
                <th>所属商户</th>
                <th>商户名称</th>
                <th>登录名</th>
                <th>登陆IP</th>
                <th>IP所在地</th>
                <th>登陆时间</th>
                <th>退出时间</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="loginLogQuery">
                <s:set name="entity" value="#attr['loginLogQuery']" />
                <vlh:column attributes="width='10%'">${customer_type}</vlh:column>
                <vlh:column attributes="width='10%'">${entity.customer_no }</vlh:column>
                <vlh:column attributes="width='10%'">${entity.realname }</vlh:column>
                <vlh:column attributes="width='10%'">${entity.username }</vlh:column>
                <vlh:column attributes="width='10%'">${entity.ip}</vlh:column>
                <vlh:column attributes="width='10%'">${entity.remarks }</vlh:column>
                <vlh:column attributes="width='10%'">
                  <s:date name="#entity.login_time" format="yyyy-MM-dd HH:mm:ss" />
                </vlh:column>
                <vlh:column attributes="width='10%'">
                  <s:date name="#entity.login_out_time" format="yyyy-MM-dd" />
                </vlh:column>
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
