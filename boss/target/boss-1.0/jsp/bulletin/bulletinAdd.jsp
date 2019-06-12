<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>标题</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 1000px;padding: 10px;">
  
  <form class="validator notification" action="bulletinAdd.action" method="post" prompt="DropdownMessage"  data-success="新增成功" data-fail="新增失败">

    <!-- 基本信息 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">基本信息</span>
      </div>
      <div class="content">
        <div class="fix">
          <div class="col fl w-p-50">
            <div class="fix">
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">公告标题:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="bulletin.title" required>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">生效时间:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text date-start default-time" name="bulletin.effTime" required>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">失效时间:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text date-start default-time" name="bulletin.extTime" required>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col fl w-p-50">
            <div class="fix">
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">类型:</span>
                  <div class="input-wrap">
                    <dict:select styleClass="input-select" name="bulletin.sysCode" dictTypeId="TYPE"></dict:select>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">状态:</span>
                  <div class="input-wrap">
                    <dict:select styleClass="input-select" name="bulletin.status" dictTypeId="ALL_STATUS"></dict:select>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>


    <!-- 公告内容 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">公告内容</span>
      </div>
      <dic class="content">
        <div class="editor-box" style="padding: 0 10px;">
          <textarea class="w-p-100" name="bulletin.content" id="myEditor" required style="min-height: 400px;"></textarea>
        </div>
      </dic>
    </div>


    <div class="text-center mt-10 hidden">
      <button class="btn btn-submit">新 增</button>
    </div>

  </form>
  
  <%@ include file="../include/bodyLink.jsp"%>
  <!-- 加载百度编辑器 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/js/edit/themes/default/dialogbase.css">
  <script src="${pageContext.request.contextPath}/js/edit/ueditor.config.js"></script>
  <script src="${pageContext.request.contextPath}/js/edit/ueditor.all.js"></script>
  <script>
    var editor = new UE.ui.Editor();
    editor.render("myEditor");
    UE.getEditor('myEditor');
  </script>
</body>
</html>
