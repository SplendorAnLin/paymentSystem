<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>设备类型管理-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">

  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <s:if test="#attr['deviceType'].list.size()>0">
    <vlh:root value="deviceType" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>类型编号</th>
                <th>父类型名称</th>
                <th>类型名称</th>
                <th>单价</th>
                <th>状态</th>
                <th>备注</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="info">
                <s:set name="entity" value="#attr['info']" />
                <vlh:column property="id" attributes="width='7%'"/>
                <vlh:column property="parent_name" attributes="width='10%'">
                  <c:if test="${entity.parent_name == null}">
                    <span>顶级</span>
                  </c:if>
                  <c:if test="${entity.parent_name != null}">
                  </c:if>
                </vlh:column>
                <vlh:column property="device_name" attributes="width='10%'"/>
                <vlh:column property="unit_price" attributes="width='10%'"/>
                <vlh:column property="status" attributes="width='10%'">
                  <dict:write dictId="${entity.status }" dictTypeId="ACCOUNT_STATUS_COLOR"></dict:write>
                </vlh:column>
                <vlh:column property="remark" attributes="width='10%'"/>
                <vlh:column property="parent_name" attributes="width='10%'">
                <c:if test="${entity.parent_name == null}">
                  <!-- 修改设备分类类型 -->
                  <button data-myiframe='{
                    "target": "toUpDeviceParentType.action?deviceTypeId=${entity.id }",
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "修改", "trigger": ".btn-submit"}
                    ]
                  }' class="btn-small">修改</button>
                </c:if>
                <c:if test="${entity.parent_name != null}">
                  <!-- 修改设备类型 -->
                  <button data-myiframe='{
                    "target": "toUpDeviceType.action?deviceTypeId=${entity.id }",
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "修改", "trigger": ".btn-submit"}
                    ]
                  }' class="btn-small">修改</button>
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
  <s:if test="#attr['deviceType'].list.size()==0">
    <p class="pd-10">没有查找到符合条件的结果</p>
  </s:if>
  
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
