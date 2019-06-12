<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>用户代收配置-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <s:if test="#attr['receiveConfigQuery'].list.size()>0">
    <vlh:root value="receiveConfigQuery" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>用户编号</th>
                <th>状态</th>
                <th>日限额</th>
                <th>单批次最大笔数</th>
                <th>是否校验授权</th>
                <th>IP</th>
                <th>域名</th>
                <th>创建时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="defConfig">
                <s:set name="entity" value="#attr['defConfig']" />
                <vlh:column property="owner_id" attributes="width='10%'" />
                <vlh:column property="status" attributes="width='8%'"><dict:write dictId="${entity.status }" dictTypeId="HOLIDAY_STATUS_COLOR"></dict:write></vlh:column>
                <vlh:column property="daily_max_amount" attributes="width='13%'" ><fmt:formatNumber value="${entity.daily_max_amount }" pattern="#0.00#" /></vlh:column>
                <vlh:column property="single_batch_max_num" attributes="width='10%'"/>
                <vlh:column property="is_check_contract" attributes="width='15%'" ><dict:write dictId="${entity.is_check_contract }" dictTypeId="YN_COLOR"></dict:write></vlh:column>
                <vlh:column property="cust_ip" attributes="width='15%'" />
                <vlh:column property="domain" attributes="width='15%'" />
                <vlh:column property="create_time" attributes="width='15%'"><s:date name="#entity.create_time" format="yyyy-MM-dd HH:mm:ss" /></vlh:column>
                <vlh:column attributes="width='8%'">
                  <button data-myiframe='{
                    "target": "${pageContext.request.contextPath}/toReceiveConfigUp.action?ownerId=${entity.owner_id}",
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "修改", "trigger": ".btn-submit"}
                    ]
                  }' class="btn-small">修改</button>
                  <button data-dialog="${pageContext.request.contextPath}/receiveConfigHistoryQuery.action?owner_id=${entity.owner_id}" class="btn-small">历史查询</button>
                </vlh:column>
              </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:if test="#attr['receiveConfigQuery'].list.size()==0">
    <p class="pd-10">没有查找到用户信息</p>
  </s:if>
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
