<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>商户功能管理-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <c:if test="${page.object != null && page.object.size() > 0 }">
    <div class="table-warp">
      <div class="table-sroll">
        <table class="data-table shadow--2dp  w-p-100 tow-color">
          <thead>
            <tr>
              <th>功能名称</th>
              <th>ACTION</th>
              <td>关联菜单</td>
              <th>状态</th>
              <th>是否验证</th>
              <th>描述</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${page.object}" var="stu">
              <tr>
                <td>${stu.name}</td>
                <td>${stu.action}</td>
                <td>${stu.menu_name}</td>
                <td><dict:write dictId="${stu.status }" dictTypeId="ACCOUNT_STATUS_COLOR"></dict:write></td>
                <td><dict:write dictId="${stu.is_check }" dictTypeId="YNY_COLOR"></dict:write></td>
                <td>${stu.remark}</td>
                <td>
                  <button data-myiframe='{
                    "target": "tocustFunctionModify.action?id=${stu.id }",
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "修改", "trigger": ".btn-submit"}
                    ]
                  }' class="btn-small">修改</button>      
                  <button onclick="delFunction('${stu.id }')" class="btn-small">删除</button>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    <div class="jump-rarp mt-20 text-center" id="PageWrap" total_count="${page.totalResult}" total="${page.totalPage}"  current="${page.currentPage}"></div>
  </c:if>
  <c:if test="${page.object eq null or page.object.size() == 0 }">
    <p class="pd-10">无符合条件的查询结果</p>
  </c:if>
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    function delFunction(id) {
      AjaxConfirm('是否删除该功能?', '请确认', {
        url: 'custDeleteFunction.action',
        type: 'post',
        data : {
          "Id" : id
        },
        success: function(msg) {
          Messages.success('删除成功!');
        },
        error: function() {
          Messages.error('网络异常, 删除失败, 请稍后重试!');
        }
      }, true);
    }
  </script>
</body>
</html>
