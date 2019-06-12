<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>编辑菜单</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>
  
  <form id="modifyForm" class="validator notification text-center" action="customerModifyMenu.action" method="post" prompt="message"  data-success="修改成功" data-fail="修改失败" style="padding: 2em 0.4em 0.4em;">
    <input type="hidden" name="menu.id" value="${menu.id}">
    <div class="fix">
      <div class="col fl w-p-50">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-100">菜单名称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="menu.name" required rangelength="[1, 50]" value="${menu.name}">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-100">显示顺序:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="menu.displayOrder" required digits min="1" value="${menu.displayOrder}">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-100">菜单链接:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="menu.url" <s:if test="${menu.level == '1'}">readonly</s:if> required value="${menu.url}" placeholder="主菜单无需填写" >
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col fl w-p-50">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-100">菜单级别:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="menu.level" required range="[1, 2]" value="${menu.level}" <s:if test="${menu.level == '1'}">readonly</s:if>>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-100">菜单状态:</span>
              <div class="input-wrap">
                <dict:select value="${menu.status }" styleClass="input-select" name="menu.status" dictTypeId="ALL_STATUS"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-100">选择上级菜单:</span>
              <div class="input-wrap">
                <select class="input-select" name="menu.parentId">
                  <c:forEach items="${pMenus}" var="pm">
                      <c:if test="${pm.id == menu.parentId}">
                          <option value="${pm.id}" selected="selected">${pm.name}</option>
                      </c:if>
                      <c:if test="${pm.id != menu.parentId}">
                          <option value="${pm.id}">${pm.name}</option>
                      </c:if>
                  </c:forEach>
                </select>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div>
      <button class="btn btn-submit">修改菜单</button>
      <c:if test="${menu.level != '2'}">
        <a class="btn" onclick="addSubMenu();" href="javascript:void(0);">添加子菜单</a>
      </c:if>
    </div>

  </form>


  <!-- 新增子菜单对话框 -->
  <div class="not-ready-render" id="add-subMenu-wrap" style="width: 360px; padding: 10px;display: none;">
    <form class="validator notification" action="customerAddChildMenu.action" method="post" prompt="DropdownMessage"  data-success="新增成功" data-fail="新增失败">
      <input type="hidden" name="menu.parentId" value="${menu.id}"/>
      <div class="fix">
        <div class="item">
          <div class="input-area">
            <span class="label w-90">菜单名称:</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="menu.name" required rangelength="[1, 50]">
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-90">显示顺序:</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="menu.displayOrder" min="1" required digits>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-90">菜单链接:</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="menu.url" required>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-90">菜单状态:</span>
            <div class="input-wrap">
              <dict:select styleClass="input-select" name="menu.status" dictTypeId="ALL_STATUS"></dict:select>
            </div>
          </div>
        </div>
      </div>
    </form>
  </div>
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>

  var dialogWrap = $('#add-subMenu-wrap');
  // 添加子菜单
  function addSubMenu() {
    var dialog = new window.top.MyDialog({
      target: dialogWrap.clone(),
      btns: [
        {
          lable: '取消'
        },
        {
          lable: '添加',
          // 按钮单击事件
          click: function(data, content) {
            $('form', content).submit();
          }
        }
      ],
      btnType: 2,
      title: '添加子菜单',
      isModal: true
    });
  }

  // 验证钩子
  var modifyForm = $('#modifyForm');
  $('[name="menu.url"]', modifyForm).data('valid-hook', function(ele) {
    // 二级菜单才执行验证
    return $('[name="menu.level"]', modifyForm).val() != 1;
  });
  
  // 表单结果钩子
  modifyForm.data('receiver-hook', function(status, closeDialog, iframe) {
    // 刷新页面
    window.top.windowManage.current().flush();
    closeDialog(status)
  });


  </script>
</body>
</html>
