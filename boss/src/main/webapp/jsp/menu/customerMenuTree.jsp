<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>商户菜单管理</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="min-width: 1150px;">
  
  <!-- 菜单管理 -->
  <div class="fix pd-10">

    <!-- 表单 -->
    <form id="tree-form" action="toCustomerMenuEdit.action" method="post" target="menuDetail">
      <input type="hidden" id="menuId" name="menuId" value="" />
    </form>


    <!-- 左侧菜单预览 -->
    <div class="fl mr-10" style="width: 450px;">
      <div class="title-h1 fix">
        <span class="primary fl">菜单预览</span>
        <span class="fr">
          <a class="menu-openAll" href="javascript:void(0);">展开所有</a>
          <span> / </span>
          <a class="menu-closeAll" href="javascript:void(0);">收起所有</a>
        </span>
      </div>
      <div class="content">

        <div class="menu-tree">
          <ul class="tree-ul" id="tree-ul">
          </ul>
        </div>



      </div>
    </div>

    <!-- 右侧管理表单 -->
    <div style="overflow: hidden;">
      <div class="title-h1 fix">
        <span class="primary fl">菜单详细信息</span>
      </div>
      <div class="content" style="height: 500px;">
        <iframe class="w-p-100 h-p-100" name="menuDetail" frameborder="0"></iframe>
      </div>
    </div>
  </div>

  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    // 初始化目录树管理器
    menuTree.init();
    menuTree.onClick = function(li, data) {
      // console.log('选中', li, '附加数据:', data);
      $('#menuId').val(data.id);
      $('#tree-form').submit();
    };
  </script>
  
  <!-- 初始化菜单项 -->
  <c:forEach items="${menuList}" var="m">
    <c:if test="${m.level==1 }">
      <script>
        menuTree.add('${m.name}', '${m.id}');
      </script>
    </c:if>
    <c:if test="${m.level!=1 }">
      <script>
        menuTree.add('${m.name}', '${m.id}', '${m.parentId}');
      </script>
    </c:if>
    
  </c:forEach>
</body>
</html>
