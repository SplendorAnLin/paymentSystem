<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<!DOCTYPE html>
<html lang="en" class="over-x-h">
<head>
  <meta charset="UTF-8">
  <title></title>
</head>
<body>

  <div class="in-pop" style="width: 400px; padding: 10px;">
    <form>
      <div class="block">
        <span class="lable w-75">会员编号:</span>
         <div class="b-input w-170">
              <div class="input-wrap w-p-100">
                <input type="text" class="input-text rest-text" value="">
              </div>
          </div>
      </div>
      <div class="block">
        <span class="lable w-75">会员名称:</span>
         <div class="b-input w-170">
              <div class="input-wrap w-p-100">
                <input type="text" class="input-text rest-text" value="" readonly="readonly">
              </div>
          </div>
      </div>
      <div class="block">
        <span class="lable w-75">添加数量:</span>
         <div class="b-input w-170">
              <div class="input-wrap w-p-100">
                <input type="text" class="input-text rest-text" value="">
              </div>
          </div>
      </div>
    </form>
  </div>





<script src="${pageContext.request.contextPath}/plugins/nicEdit.js"></script>
<%@ include file="../include/bodyLink.jsp"%>
</body>
</html>