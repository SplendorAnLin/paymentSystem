<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>角色信息</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="min-width: 1000px; padding: 0.2em;">
  
  
  <div class="role-page">
    <form>
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
                  <input type="text" class="input-text" name="role.name" value="${role.name }" disabled>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">备注:</span>
                <div class="input-wrap">
                  <textarea name="role.remark" cols="50" rows="8" disabled >${role.remark }</textarea>
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
            
            
            <c:forEach items="${roleMenu }" var="menu">
              <c:if test="${menu.level==1 }">
              <div class="row">
                <!-- 一级 -->
                <div class="primary fix">
                  <!-- 一级复选框 -->
                  <div class="check-list">
                    <input type="checkbox" class="checkbox" value="${menu.id }" disabled checked>
                    <label>${menu.name }</label>
                  </div>
                  <!-- 一级右侧 -->
                  <div class="sub">
                    <c:forEach items="${roleMenu }" var="childMenu" varStatus="status">
                      <c:if test="${childMenu.parentId==menu.id }">
                        <!-- 二级 -->
                        <div class="secondary fix">
                          <!-- 二级复选框行 -->
                          <div class="secondary-row">
      
                            <!-- 二级复选框列表 -->
                            <div class="check-list">
                              <input type="checkbox" class="checkbox"  value="${childMenu.id }"  disabled checked>
                              <label>${childMenu.name }</label>
                            </div>
      
                            <!-- 三级 -->
                            <div class="sub fix">
                              <c:forEach items="${roleFunction }" var="function" varStatus="funI">
                                <c:if test="${function.menuId==childMenu.id}">
                                  <span class="featur">
                                    <input type="checkbox" class="checkbox" value="${function.id }" disabled checked>
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
    </form>
  </div>
  
  
  
  <%@ include file="../include/bodyLink.jsp"%>

</body>
</html>
