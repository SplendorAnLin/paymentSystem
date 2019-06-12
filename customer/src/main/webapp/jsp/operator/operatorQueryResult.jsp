<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
 <%@ include file="../include/header.jsp"%>
  <title>用户查询-结果</title>
</head>
<body class="pb-2">

  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  
    
  <s:if test="#attr['operator'].list.size()>0">
	 <% int count = 1; %>
   <vlh:root value="operator" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>登录名</th>
                <th>用户名</th>
                <th>操作员类型</th>
                <th>状态</th>
                <th>创建时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
                <vlh:row bean="operator">
                  <s:set name="entity" value="#attr['operator']" />
                  <vlh:column attributes="width='10%'">${entity.username}</vlh:column>
                  <vlh:column property="realname" attributes="width='10%'" />
                  <vlh:column property="role_name" attributes="width='10%'"/>
                  <vlh:column attributes="width='8%'"><dict:write dictId="${entity.status }" dictTypeId="ACCOUNT_STATUS_COLOR"></dict:write></vlh:column>
                  <vlh:column attributes="width='10%'"><s:date name="#entity.create_time" format="yyyy-MM-dd" /></vlh:column>
                  <vlh:column attributes="width='10%'">
                    <button data-myiframe='{
                      "target": "${pageContext.request.contextPath}/operatorDetail.action?operator.username=${entity.username}",
                      "btnType": 2,
                      "btns": [
                        {"lable": "取消"},
                        {"lable": "修改", "trigger": ".btn-submit"}
                      ]
                    }' class="btn-small">修改信息</button>
                  </vlh:column>
                </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
      <%@ include file="../include/vlhPage.jsp"%>
	 </vlh:root>
	</s:if>
	<s:if test="#attr['operator'].list.size()==0">
		无符合条件的查询结果
	</s:if>


 <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>