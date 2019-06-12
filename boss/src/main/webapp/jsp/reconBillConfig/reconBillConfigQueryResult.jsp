<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>接口对账单配置-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <s:if test="#attr['reconBillConfigInfo'].list.size()>0">
    <vlh:root value="reconBillConfigInfo" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>接口名称</th>
                <th>接口编号</th>
                <th>对账单路径</th>
                <th>账单文件前缀</th>
                <th>生成账单时间</th>
                <th>创建时间</th>
                <th>状态</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="reconBillConfigInfo">
                <s:set name="entity" value="#attr['reconBillConfigInfo']" />
                <vlh:column property="interface_name" attributes="width='20%'" />
                <vlh:column property="interface_code" attributes="width='20%'" />
                <vlh:column property="recon_bill_url" attributes="width='15%'" />
                <vlh:column property="file_prefix" attributes="width='15%'" />
                <vlh:column property="generate_time" attributes="width='10%'" />
                <vlh:column property="create_time" attributes="width='15%'"><s:date name="#entity.create_time" format="yyyy-MM-dd HH:mm:ss" /></vlh:column>
                <vlh:column property="status" attributes="width='15%'"><dict:write dictId="${entity.status }" dictTypeId="ACCOUNT_STATUS_COLOR"></dict:write></vlh:column>
                <vlh:column attributes="width='8%'">
                  <button data-myiframe='{
                    "target": "${pageContext.request.contextPath}/reconBillConfigById.action?id=${entity.id}",
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
  <s:if test="#attr['reconBillConfigInfo'].list.size()==0">
    <p class="pd-10">暂无接口对账单配置信息</p>
  </s:if>
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
