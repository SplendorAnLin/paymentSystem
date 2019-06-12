<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="renderer" content="webkit|ie-comp|ie-stand">
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <title>温馨提示</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile/register.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/plugin/font-awesome.css">
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
  </style>
</head>
<body class="bc-lightGray">
  <div class="mobileFrame">
    <div class="message-wrap" data-status="${custNo }">
      <div class="ico row_one">
        <i class="fa"></i>
      </div>
      <div class="text row_one">
        <p>${msg }</p>
      </div>
    </div>
  </div>
	<script src="${pageContext.request.contextPath}/js/plugin/jquery-1.8.3.js"></script>
  <script>
  $('.ico .fa').addClass('fa-' + $('.message-wrap').attr('data-status'));
  </script>
</body>
</html>