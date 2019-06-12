<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>创建角色</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="min-width: 1000px; padding: 0.2em;">
  
  
  <div class="role-page">
    <form class="notification validator" 
      action="roleAdd.action" 
      method="post" 
      prompt="DropdownMessage"  
      data-success="添加成功" data-fail="添加失败" >

      <input class="hidden" type="text" id="success" value="<%=request.getParameter("success") %>" />
      <div class="row">
        <div class="title-h1 fix tabSwitch2">
          <span class="primary fl">角色基本信息</span>
        </div>
        <div class="content">
          <div class="fix">
            <div class="item">
              <div class="input-area">
                <span class="label w-90">角色名称:</span>
                <div class="input-wrap">
                  <input type="text" class="input-text" name="role.name" required checkRoleName trigger='{"checkRoleName": 2}'>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">备注:</span>
                <div class="input-wrap">
                  <textarea name="role.remark" cols="50" rows="8" required ></textarea>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>


      <div class="row">
        <div class="title-h1 fix tabSwitch2">
          <span class="primary fl">分配角色权限</span>
        </div>
        <div class="content">
          <div class="role-group">
            
            
            <c:forEach items="${allMenu }" var="menu" varStatus="rootStatus">
              <c:if test="${menu.level==1 }">
              <div class="row">
                <!-- 一级 -->
                <div class="primary fix">
                  <!-- 一级复选框 -->
                  <div class="check-list">
                    <input type="checkbox" class="checkbox" name="menuList[${rootStatus.index }]" value="${menu.id }">
                    <label>${menu.name }</label>
                  </div>
                  <!-- 一级右侧 -->
                  <div class="sub">
                    <c:forEach items="${allMenu }" var="childMenu" varStatus="childStatus">
                      <c:if test="${childMenu.parentId==menu.id }">
                        <!-- 二级 -->
                        <div class="secondary fix">
                          <!-- 二级复选框行 -->
                          <div class="secondary-row">
      
                            <!-- 二级复选框列表 -->
                            <div class="check-list">
                              <input type="checkbox" class="checkbox"  value="${childMenu.id }" name="menuList[${childStatus.index }]">
                              <label>${childMenu.name }</label>
                            </div>
      
                            <!-- 三级 -->
                            <div class="sub fix">
                              <c:forEach items="${allFunction }" var="function" varStatus="funI">
                                <c:if test="${function.menuId==childMenu.id}">
                                  <span class="featur">
                                    <input type="checkbox" class="checkbox" name="functionList[${funI.index }]" value="${function.id }">
                                    <label>${function.name }</label>
                                  </span>
                                 </c:if>
                              </c:forEach>
                            </div>
                          </div>
                        </div>
                      </c:if>
                    </c:forEach>
                  </div>
                </div>
              </div>
              </c:if>
            </c:forEach>
          </div>
        </div>
      </div>

      <button class="btn hidden btn-submit">新 增</button>
    </form>
  </div>
  
  
  
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>

    // 检测角色名称是否重复
    var roleName = $('[name="role.name"]').val();
    Compared.add('checkRoleName', function(val, params, ele, ansyFn) {
      if (val === roleName && val !== '')
        ansyFn(Compared.toStatus(true));
      Api.customer.checkRoleName(val, function(status) {
        ansyFn(Compared.toStatus(status, '角色名称重复'));
      });
    });

    // 操作结果判断
    $('form').data('receiver-hook', function(status, closeDialog, iframe) {
      var msg = utils.iframeFind(iframe, '#success').val();
      closeDialog(msg == true || msg == 'true');
    });

    // 一级复选框
    $('.primary > .check-list > input[type="checkbox"]').change(function(event, ignore) {
      if (ignore)
        return;
      var sub = $(this).closest('.row').find('.primary > .sub');
      
      // 切换所有子集复选框
      if (this.checked) {
        $('input[type="checkbox"]', sub).check();
      } else {
        $('input[type="checkbox"]', sub).uncheck();
      }
    });

    // 二级复选框
    $('.secondary-row > .check-list > input[type="checkbox"]').change(function() {
      // 一级复选框
      var row = $(this).closest('.row');
      var primary = row.find('.primary > .check-list > input[type="checkbox"]');

      // 二级复选框
      var secondary = row.find('.primary > .sub > .secondary > .secondary-row > .check-list > input[type="checkbox"]');

      // 三级复选框
      var sub = $(this).closest('.check-list').next();
      var third = $('input[type="checkbox"]', sub);

      if (this.checked) {
        primary.check(true);
        third.enabled();
      } else {
        if (secondary.notCheckAll())
          primary.uncheck(true);
        third.disable();
      }
    }).trigger('change');

  </script>
</body>
</html>
