<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>编辑oem版</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 480px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 3em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator ajaxFormNotification" action="updateOemAndroid.action" enctype="multipart/form-data" method="post" prompt="DropdownMessage"  data-success="操作成功" data-fail="操作失败">
        <c:if test="${appVersion.id!=null}"><input type="hidden" name="appVersion.id" value="${appVersion.id }"></c:if>
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">服务商编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="appVersion.agentNo" value="${appVersion.agentNo }" required <c:if test="${appVersion.id!=null}">readonly</c:if> >
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">app简称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="appVersion.shortApp" value="${appVersion.shortApp }" required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">是否强制更新:</span>
              <div class="input-wrap">
                <select class="input-select" name="appVersion.enforce" required data-value="${appVersion.enforce}">
                  <option value="TRUE">开启</option>
                  <option value="FALSE">关闭</option>
                </select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">状态:</span>
              <div class="input-wrap">
                <select class="input-select" name="appVersion.status" required data-value="${appVersion.status}">
                  <option value="TRUE">开启</option>
                  <option value="FALSE">关闭</option>
                </select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">apk安装包:</span>
              <div class="input-wrap">
                <div class="file-wrap">
                  <input type="text" class="file-text w-250" name="fileName" placeholder="上传你.apk格式的文件" readonly>
                  <a class="btn-file btn-medium btn-primary input-btn-right" href="javascript:void(0);">
                    <i class="fa fa-folder-open "></i>
                    <span>浏览</span>
                  </a>
                  <input type="file" accept=".apk" name="file" type="file" prompt="message" required  prompt-ele="#error-msg">
                  <span class="vd-error" id="error-msg" style="display: none;position: relative;left: 30px;"></span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10 hidden">
          <button class="btn btn-submit">新 增</button>
        </div>
      </form>
    </div>
  </div>
  
  <%@ include file="../include/bodyLink.jsp"%>
<!--   <script>
  // 检测角色名称是否重复
  var shortApp = $('[name="appVersion.shortApp"]');
  Compared.add('checkAgentNo', function(val, params, ele, ansyFn) {
    Api.boss.findAgentShortName(val, function(shortName) {
    	if (shortName) {
    		shortApp.val(shortName);
    	} else {
    		shortApp.val('');
    	}
    	ansyFn(Compared.toStatus(shortName != null, '服务商编号不存在'));
    });
  });
  
  
  
  
  
  
  
  
  
  
  
    // 操作结果判断
    $('form').data('receiver-hook', function(status, closeDialog, iframe) {
      var msg = utils.iframeFind(iframe, '#msg').val();
      closeDialog(msg == true || msg == 'true');
    });
  </script> -->
</body>
</html>
