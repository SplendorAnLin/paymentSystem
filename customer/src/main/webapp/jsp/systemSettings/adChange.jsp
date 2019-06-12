<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<!DOCTYPE html>
<html lang="en" class="over-x-h">
<head>
  <meta charset="UTF-8">
  <title>编辑广告</title>
</head>
<body>

  <div class="in-pop" style="width: 370px; padding: 10px;">
    <form>
      <div class="block">
        <span class="lable w-90">广告图片:</span>
        <div class="input-file ib">
          <div class="b-input w-120">
            <div class="input-wrap w-p-100">
              <input type="text" class="input-text file-text rest-text" >
            </div>
          </div>
          <a class="btn file-btn" href="javascript:void(0);">浏览</a>
          <input class="file-input hidden" type="file">
        </div>
      </div>
        <div class="block">
          <span class="lable w-90">广告链接:</span>
          <div class="b-input w-200">
            <div class="input-wrap w-p-100">
              <input type="text" class="input-text rest-text" >
            </div>
          </div>
        </div>
    </form>
  </div>




<%@ include file="../include/bodyLink.jsp"%>
<script>


</script>
</body>
</html>