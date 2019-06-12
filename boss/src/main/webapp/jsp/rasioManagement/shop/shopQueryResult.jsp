<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>网点管理-结果</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <s:if test="#attr['shopInfo'].list.size()>0">
    <vlh:root value="shopInfo" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>网点编号</th>
                <th>网点名称</th>
                <th>小票打印名称</th>
                <th>拨号POS绑定号码</th>
                <th>创建时间</th>
                <th>所属商户编号</th>
                <th>状态</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
                <vlh:row bean="shopInfo">
                  <s:set name="shopInfo" value="#attr['shopInfo']" />
                  <vlh:column property="shop_no" />
                  <vlh:column property="shop_name" />
                  <vlh:column property="print_name" />
                  <vlh:column property="bind_phone_no" />
                  <vlh:column property="create_time" attributes="width='180px'"
                    format="yyyy-MM-dd HH:mm:ss" />
                  <vlh:column property="customer_no" />
                  <vlh:column>
                    <dict:write dictId="${shopInfo.status }" dictTypeId="STATUS"></dict:write>
                  </vlh:column>
                  <vlh:column attributes="width='10%'">
                    <button data-myiframe='{
                      "target": "${pageContext.request.contextPath}/shopById.action?id=${shopInfo.id}",
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
  <s:if test="#attr['shopInfo'].list.size()==0">
    <p class="pd-10">无符合条件的查询结果</p>
  </s:if>
  
  
  <%@ include file="../../include/bodyLink.jsp"%>
</body>
</html>
