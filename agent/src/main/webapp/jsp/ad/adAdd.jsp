<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>添加广告</title>
  <%@ include file="../include/header.jsp"%>
  <style>
  #error-message-file {
    position: absolute;
    right: -27px;
    top: 6px;
  }
  </style>
</head>
<body style="width: 470px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 3em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="adAdd.action" enctype="multipart/form-data" method="post" prompt="DropdownMessage"  data-success="添加成功" data-fail="添加失败">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">广告名称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="ad.name" required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">广告链接:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="ad.imageClickUrl" required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">广告类型:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select" dictTypeId="AD_TYPE" name="ad.adType"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">状态:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select" dictTypeId="ALL_STATUS" name="ad.status"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">广告图片:</span>
              <div class="input-wrap">
                <div class="file-wrap">
                  <input type="text" class="file-text w-250" placeholder="上传广告图片" readonly>
                  <a class="btn-file btn-medium btn-primary input-btn-right" href="javascript:void(0);">
                    <i class="fa fa-folder-open "></i>
                    <span>浏览</span>
                  </a>
                  <input type="file" accept="image/jpeg,image/jpg,image/png" name="imageContent" checkImageSize type="file" prompt="NextLineMessage" prompt-ele="#error-message-file" required acceptiamge>
                  <span id="error-message-file" class="vd-error"></span>
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
</body>
</html>
