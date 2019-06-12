<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" class="w-p-100">
<head>
<meta charset="UTF-8">
<title>设备管理-查询</title>
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
            <th>序号</th>
            <th>手机号</th>
            <th>设备类型</th>
            <th>设备号</th>
            <th>终端号</th>
            <th>设备状态</th>
            <th>激活日期</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
            <tr>
              <td>1</td>
              <td>15527558460</td>
              <td>大POS机</td>
              <td>75077256</td>
              <td>80805682</td>
              <td style="color:#009688">正常</td>
              <td>2016-11-23 09:29:39</td>
              <td>
                <button data-url="editDevice.action" data-title="编辑设备" data-rename="修改" class="pop-modal btn-loading btn btn-size-small">编辑</button>
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