<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>APP版本管理-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <s:if test="#attr['clientVersionQuery'].list.size()>0">
    <vlh:root value="clientVersionQuery" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>服务商编号</th>
                <th>app简称</th>
                <th>app系统</th>
                <th>版本号</th>
                <th>地址</th>
                <th>审核状态</th>
                <th>备注</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="clientVersionQuery">
                <s:set name="clientVersion" value="#attr['clientVersionQuery']" />
                <vlh:column property="agent_no" />
                <vlh:column property="short_app" />
                <vlh:column property="type" />
                <vlh:column property="version" />
                <vlh:column property="url" />
                <vlh:column>
                	<dict:write dictId="${clientVersion.app_audit_status }" dictTypeId="APP_AUDIT_STATUS" />
                </vlh:column>
                <vlh:column property="remark" />
                <vlh:column>
                  <c:if test="${clientVersion.type eq 'ios' }">
                    <button data-myiframe='{
                      "target": "toIosOemUpdate.action?id=${clientVersion.id}",
                      "btns": [
                        {"lable": "取消"},
                        {"lable": "更新", "trigger": ".btn-submit"}
                      ]
                    }' class="btn-small">更新版本</button>
                  </c:if>
                  <c:if test="${clientVersion.type eq 'iosStore' }">
                    <button data-myiframe='{
                      "target": "toIosStoreOemUpdate.action?id=${clientVersion.id}",
                      "btns": [
                        {"lable": "取消"},
                        {"lable": "更新", "trigger": ".btn-submit"}
                      ]
                    }' class="btn-small">更新版本</button>
                  </c:if>
                  <c:if test="${clientVersion.type eq 'android' }">
                    <button data-myiframe='{
                      "target": "toAndroidOemUpdate.action?id=${clientVersion.id}",
                      "btns": [
                        {"lable": "取消"},
                        {"lable": "更新", "trigger": ".btn-submit"}
                      ]
                    }' class="btn-small">更新版本</button>
                  </c:if>
                </vlh:column>
              </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:if test="#attr['clientVersionQuery'].list.size()==0">
    <p class="pd-10">无符合条件的查询结果</p>
  </s:if>
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
