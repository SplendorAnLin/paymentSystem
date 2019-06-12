<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>字典类型管理-结果</title>
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
              <th>字典类型编码</th>
              <th>字典类型名称</th>
              <td>备注信息</td>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${page.object}" var="dt">
              <tr>
                <td>${fn:split(dt.code,".")[1]}</td>
                <td>${dt.name }</td>
                <td>${dt.remark }</td>
                <td>
                <button data-myiframe='{
                    "target": "${pageContext.request.contextPath}/dictionaryQuery.action?dictionaryTypeRanged.code=${fn:split(dt.code,".")[1]}",
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "添加", "trigger": ".btn-add"}
                    ]
                  }' class="btn-small">字典管理</button>
                  <button data-myiframe='{
                    "target": "${pageContext.request.contextPath}/queryDictionaryTypeByCode.action?dictionaryTypeRanged.code=${fn:split(dt.code,".")[1]}",
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "修改", "trigger": ".btn-submit"}
                    ]
                  }' class="btn-small">修改</button>
                  <button onclick="delFunction('${dt.code}')" class="btn-small">删除</button>
                  <%-- <button data-dialog='${pageContext.request.contextPath}/dictionaryQuery.action?dictionaryTypeRanged.code=${fn:split(dt.code,".")[1]}' class="btn-small">字典管理</button> --%>
                  
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
    function delFunction(code) {
      AjaxConfirm('是否删除该字典类型?', '请确认', {
        url: '${pageContext.request.contextPath}/dictionaryTypeDel.action?dictionaryTypeRanged.code=' + code,
        type: 'post',
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
