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
<link rel="stylesheet" href="http://yl.bank-pay.com/gateway/css/QRCode/register.css">
<link rel="stylesheet" href="http://yl.bank-pay.com/gateway/css/QRCode/Modal.css">
<link rel="stylesheet" href="http://yl.bank-pay.com/gateway/css/QRCode/font-awesome.css">
<script src="http://yl.bank-pay.com/gateway/js/QRCode/flexible_wap.js"></script>
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
    <div class="message-wrap" data-status="${message  }">
      <div class="ico row_one">
        <i class="fa"></i>
      </div>
       <div>${message}</div>
      <<%-- div class="text row_one">
       <img class="details-bg" src="${pageContext.request.contextPath}/image/icon_result_ok.png">
      </div> --%>
    </div>
  </div>
	<script src="http://yl.bank-pay.com/gateway/js/QRCode/jquery-1.8.3.js"></script>
	<script src="http://yl.bank-pay.com/gateway/js/QRCode/utils.js"></script>
  <script>
  $('.ico .fa').addClass('fa-' + $('.message-wrap').attr('data-status'));
  </script>
</body>
</html>