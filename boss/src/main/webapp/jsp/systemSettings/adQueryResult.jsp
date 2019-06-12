<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>广告管理-结果</title>
  <%@ include file="../include/header.jsp"%>
  <style>
  .a-img {
    max-width: 250px;
    max-height: 48px;
    margin: 5px 0;
    cursor: pointer;
    box-shadow: 1px 1px 5px rgba(0,0,0,0.3);
  }
  .a-img:hover {
    box-shadow: 1px 1px 10px rgba(0,0,0,0.7);
  }
  </style>
</head>
<body class="pb-2">

  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <s:if test="#attr['adQuery'].list.size()>0">
    <vlh:root value="adQuery" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>广告编码</th>
                <th>所属代理商</th>
                <th>OEM</th>
                <th>广告类型</th>
                <th>广告名称</th>
                <th>广告图片</th>
                <th>广告链接</th>
                <th>广告状态</th>
                <th>创建时间</th>
                <th>修改时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="adQuery">
              <s:set name="adQuery" value="#attr['adQuery']" />
              <vlh:column property="id" />
              <vlh:column property="agent_no">
              	<c:if test="${adQuery.agent_no ==null or adQuery.agent_no=='' }">运营</c:if>
              </vlh:column>
              <vlh:column property="oem">
              	<c:if test="${adQuery.oem ==null or adQuery.oem=='' }">ylzf</c:if>
              </vlh:column>
              <vlh:column property="ad_type">
                <dict:write dictId="${adQuery.ad_type }" dictTypeId="AD_TYPE"></dict:write>
              </vlh:column>
              <vlh:column property="name" />
              <vlh:column>
                <div class="td-img">
                  <img img onclick="lookImg('findAdDocumentImg.action?ad.id=${adQuery.id}&ad.imageUrl=${adQuery.image_url}', '广告图片预览', this)" class="a-img" src="findAdDocumentImg.action?ad.id=${adQuery.id}&ad.imageUrl=${adQuery.image_url}">
                </div>
              </vlh:column>
              <vlh:column property="image_click_url" />
              <vlh:column property="status">
                <dict:write dictId="${adQuery.status }" dictTypeId="ACCOUNT_STATUS_COLOR"></dict:write>
              </vlh:column>
              <vlh:column property="create_time" format="yyyy-MM-dd HH:mm:ss" />
              <vlh:column property="update_time" format="yyyy-MM-dd HH:mm:ss" />
              <vlh:column>
                <button data-myiframe='{
                  "target": "adQueryById.action?ad.id=${adQuery.id}",
                  "btns": [
                    {"lable": "取消"},
                    {"lable": "修改", "trigger": ".btn-submit"}
                  ]
                }' class="btn-small">编辑</button>
              </vlh:column>
            </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:else>
    <p class="pd-10">无符合条件的查询结果</p>
  </s:else>
  
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
