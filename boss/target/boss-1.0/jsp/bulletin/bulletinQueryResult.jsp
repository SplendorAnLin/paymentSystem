<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>系统公告-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <s:if test="#attr['bulletin'].list.size()>0">
    <vlh:root value="bulletin" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>标题</th>
                <th>创建时间</th>
                <th>系统</th>
                <th>状态</th>
                <th>操作人</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
                <vlh:row bean="bulletin">
                  <s:set name="entity" value="#attr['bulletin']" />
                  <vlh:column property="title" attributes="width='30%'" />
                  <vlh:column property="create_time" attributes="width='15%'"><s:date name="#entity.create_time" format="yyyy-MM-dd HH:mm:ss" /></vlh:column>
                  <vlh:column property="sys_code" attributes="width='15%'"><dict:write dictId="${entity.sys_code }" dictTypeId="SYSTEM_NAME"></dict:write></vlh:column>
                  <vlh:column property="status" attributes="width='15%'"><dict:write dictId="${entity.status  }" dictTypeId="ACCOUNT_STATUS_COLOR"></dict:write></vlh:column>
                  <vlh:column property="operator" attributes="width='15%'" />
                  <vlh:column attributes="width='8%'">
                    <button data-myiframe='{
                      "target": "bulletinDetail.action?bulletin.id=${entity.id}",
                      "autoTitle": false,
                      "isModal": false,
                      "fixed": true,
                      "btns": [
                        {"lable": "确定"}
                      ]
                    }' class="btn-small">公告预览</button>
                  <button data-myiframe='{
                    "target": "bulletinFindById.action?bulletin.id=${entity.id}",
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
    <s:if test="#attr['bulletin'].list.size()==0">
      <p class="pd-10">无符合条件的查询结果</p>
    </s:if>
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
