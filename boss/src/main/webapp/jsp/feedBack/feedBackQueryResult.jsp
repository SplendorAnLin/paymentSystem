<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>意见反馈-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <s:if test="#attr['userFeedBackQuery'].list.size()>0">
    <vlh:root value="userFeedBackQuery" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color nextline">
            <thead>
              <tr>
                <th>序号</th>
                <th>商户编号</th>
                <th>手机号</th>
                <th>意见内容</th>
                <th>服务商编号</th>
                <th>OEM</th>
                <th>反馈日期</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="userFeedBackQuery">
                <s:set name="userFeedBack" value="#attr['userFeedBackQuery']" />
                <vlh:column property="id" />
                <vlh:column property="customer_no" />
                <vlh:column property="phone_no" />
                <vlh:column property="content" >
                  ${fn:substring(userFeedBack.content,0,15 )}...
                  <pre class="hidden content">${userFeedBack.content }</pre>
                </vlh:column>
                <vlh:column property="agent_no"/>
                <vlh:column property="oem"/>
                <vlh:column property="create_time" format="yyyy-MM-dd HH:mm:ss" />
                <vlh:column>
                  <button onclick="showDetail(this);" class="btn-small">详细意见</button>
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
  <script>
   function showDetail(btn) {
    var description = $(btn).closest('tr').find('.content').html();
    var content = $('<pre style="max-width: 800px;"></pre>').html(description).css('line-height', '1.5');
    content.Alert('详细意见');
   }
  </script>
</body>
</html>
