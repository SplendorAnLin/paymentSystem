<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html>
<head>
	<script type="text/javascript" src="${applicationScope.staticFilesHost}${pageContext.request.contextPath}/js/interfaceProvider/interfaceProvider.js"></script>
	<script type="text/javascript">
		$(function(){
			initPage('${page.currentPage}','${page.totalResult}','${page.totalPage}','${page.entityOrField}',parent.document.getElementById("form"));
		});
	</script>
</head>
<body class="w-p-100 min-width">
<div class="ctx-iframe">
<div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
</div>
	<div class="table-warp pd-r-10">
    <div class="table-scroll">
      <table class="table-two-color">
       <thead>
          <tr>
          	<th>级别编号</th>
            <th>级别名称</th>
            <th>级别描述</th>
            <th>创建时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
            <tr>
              <td>JPDL</td>
              <td>金牌会员</td>
              <td>测试</td>
              <td>2016-11-23 09:29:39</td>
              <td>
                <button data-url="toLevelEdit.action" data-title="修改级别" data-rename="修改" class="pop-modal btn-loading btn btn-size-small">修改</button>
              </td>
            </tr>
          </tbody>
      </table>
     </div>
  </div>

  <div class="jump-rarp mtb-20 text-center">
    <div class="jump-bar">
      <span class="record mr-20">共<span class="count">356</span>条记录</span>
      <span class="btn-1"><i class="fa-chevron-left fa"></i></span>
      <span class="jump-number mlr-5">
        <a class="current" href="javascript:void(0);">1</a>
        <a href="javascript:void(0);">2</a>
        <a href="javascript:void(0);">3</a>
        <a href="javascript:void(0);">4</a>
        <a href="javascript:void(0);">5</a>
        <a class="ellips" href="javascript:void(0);"><i class="fa-ellipsis-h fa"></i></a>
        <a href="javascript:void(0);">35</a>
      </span>
      <span class="btn-1"><i class="fa-chevron-right fa"></i></span>

      <span class="ml-20">
        <span class="plr-5">转至</span>
        <span class="input-wrap">
          <input class="input-text" type="text" placeholder="1" value="1" max="999">
          <span class="jump-btn btn-1"><i class="fa-share fa"></i></span>
        </span>
        <span class="plr-5">页</span>
      </span>
    </div>
  </div>

</div>
<%@ include file="../include/bodyLink.jsp"%>
</body>
</html>