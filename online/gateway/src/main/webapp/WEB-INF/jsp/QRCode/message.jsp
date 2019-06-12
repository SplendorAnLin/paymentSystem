<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="renderer" content="webkit|ie-comp|ie-stand">
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <title>温馨提示</title>
<link rel="stylesheet" href="${applicationScope.staticFilesHost}/gateway/css/QRCode/register.css">
<link rel="stylesheet" href="${applicationScope.staticFilesHost}/gateway/css/QRCode/Modal.css">
<link rel="stylesheet" href="${applicationScope.staticFilesHost}/gateway/css/QRCode/font-awesome.css">
<script src="${applicationScope.staticFilesHost}/gateway/js/QRCode/flexible_wap.js"></script>
<style>
    .message-wrap {
      text-align: center;
      display: flex;
      align-items: center;
      flex-wrap: wrap;
      flex-direction: column;
      align-content: space-around;
      font-size: 1.5em;
      height: 100%;
    }
    .ico {
      font-size: 7em;
      display: flex;
      align-items: center;
    }
    .row_one {
      flex: 1;
      align-self: center;
    }

    [data-status="check-circle"] {
      color: #1fa67a;
    }
    [data-status="exclamation-circle"] {
      color: #FF9900;
    }
    [data-status="times-circle"] {
      color: red;
    }
    [data-status="clock-o"] {
      color: #1fa67a;
    }
    
    .app_links {
		margin-top: 48px;
	    color: #03A9F4;
	    font-size: 16px;
    }
    .app_links a {
    	color: #03A9F4;
    }
    .app_android {

    }
    .app_apple {

    }
    .line {
    	margin: 0 10px;
    }
    
</style>
</head>
<body class="bc-lightGray">
  <div class="mobileFrame">
    <div class="message-wrap" data-status="${status }">
      <div class="ico row_one">
      	<img src="${applicationScope.staticFilesHost}/gateway/image/message/${status }.png" alt="" />
        <!-- <i class="fa"></i> -->
      </div>
      <div class="text row_one">
        <p>${message }</p>
        <p class="app_links">
        	<%--<a href="http://pay.feiyijj.com/app/download/" class="app_android">--%>
       			<%--<span>立刻体验APP</span>--%>
       		<%--</a>--%>
       		<!-- <a href="http://pay.feiyijj.com/appDown/" class="app_android">
       			<i class="fa fa-android"></i>
       			<span>安卓 APP</span>
       		</a>
       		<span class="line"> | </span>
       		<a href="http://pay.feiyijj.com/appDown/" class="app_apple">
       			<i class="fa fa-apple"></i>
       			<span>IOS APP</span>
       		</a> -->
       	</p>
      </div>
    </div>
  </div>
	<script src="${applicationScope.staticFilesHost}/gateway/js/QRCode/jquery-1.8.3.js"></script>
	<script src="${applicationScope.staticFilesHost}/gateway/js/QRCode/utils.js"></script>
  <script>
  $('.ico .fa').addClass('fa-' + $('.message-wrap').attr('data-status'));
  </script>
</body>
</html>