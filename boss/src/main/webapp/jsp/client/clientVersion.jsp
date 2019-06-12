<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>APP主版本管理</title>
  <%@ include file="../include/header.jsp"%>
  <style>
  .margin {
    margin-left: 40px;
    margin-top: 20px;
  }
  </style>
</head>
<body style="min-width: 430px;">

  <div class="row">
    <div class="title-h1 fix">
      <span class="primary fl">安卓版本发布</span>
    </div>
    <div class="content">
      <form class="notification validator margin" id="androidForm" action="updateAndroid.action" method="post" enctype="multipart/form-data" data-success="安卓版本更新成功" data-fail="安卓版本更新失败" >
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">当前版本号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" value="${all.android.version }" disabled>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">当前识别号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" value="${all.android.versionCode }" disabled>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">app简称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="info.shortApp" value="ylzf" readonly>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">apk安装包:</span>
              <div class="input-wrap">
                <div class="file-wrap">
                  <input type="text" class="file-text" name="fileName" placeholder="上传你.apk格式的文件" readonly>
                  <a class="btn" href="javascript:void(0);">浏览</a>
                  <input type="file" accept=".apk" name="file" type="file" required>
                </div>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">是否强制更新:</span>
              <div class="input-wrap">
                <select class="input-select" name="info.enforce" required>
                  <option value="TRUE">开启</option>
                  <option value="FALSE">关闭</option>
                </select>
              </div>
            </div>
          </div>
        </div>
        <div class="text-right" style="width: 280px;">
          <button class="btn">发布新版</button>
        </div>
      </form>
    </div>
  </div>

  <div class="row mt-20">
    <div class="title-h1 fix">
      <span class="primary fl">IOS版本发布</span>
    </div>
    <div class="content">
      <form class="notification validator margin" id="isoForm" action="updateIos.action" method="post" data-success="IOS版本更新成功" data-fail="IOS版本更新失败">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">当前版本号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" value="${all.ios.version}" disabled>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">更新地址:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="info.url" required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">是否强制更新:</span>
              <div class="input-wrap">
                <select class="input-select" name="info.enforce" required>
                  <option value="TRUE">开启</option>
                  <option value="FALSE">关闭</option>
                </select>
              </div>
            </div>
          </div>
        </div>
        <div class="text-right" style="width: 280px;">
          <button class="btn">发布新版</button>
        </div>
      </form>
    </div>
  </div>
  
  
  <div class="row mt-20">
    <div class="title-h1 fix">
      <span class="primary fl">IOS_Store版本发布</span>
    </div>
    <div class="content">
      <form class="notification validator margin" id="isoForm" action="upInsStoreIos.action" method="post" data-success="IOS_Store版本更新成功" data-fail="IOS_Store版本更新失败">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">当前版本号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" value="${all.ios.version}" disabled>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">更新地址:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="info.url" required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">是否强制更新:</span>
              <div class="input-wrap">
                <select class="input-select" name="info.enforce" required>
                  <option value="TRUE">开启</option>
                  <option value="FALSE">关闭</option>
                </select>
              </div>
            </div>
          </div>
        </div>
        <div class="text-right" style="width: 280px;">
          <button class="btn">发布新版</button>
        </div>
      </form>
    </div>
  </div>
  
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    // 操作结果判断
/*     $('#androidForm').data('receiver-hook', function(status, closeDialog, iframe) {
      var msg = utils.iframeFind(iframe, '#msg').val();
      closeDialog(msg == true || msg == 'true');
    });
    // 操作结果判断
    $('#isoForm').data('receiver-hook', function(status, closeDialog, iframe) {
      var msg = utils.iframeFind(iframe, '#msg').val();
      closeDialog(msg == true || msg == 'true');
    }); */
  </script>
</body>
</html>
