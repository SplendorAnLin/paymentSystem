<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setAttribute("contextPath", request.getContextPath());
%>
<%@ include file="include/commons-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>订单支付 - 聚合支付</title>
  <link rel="stylesheet" href="css/content.css">
  <link rel="stylesheet" href="css/font-awesome.min.css">
</head>
<body>

<div class="header">
  <div class="logo-box fl">
    <img src="imgs/logo-head.png" alt="">
  </div>
  <div class="account fr">
    <ul class="links fl">
      <li><a href="javascript:void(0);">聚合运营管理系统</a></li>
      <li><a href="javascript:void(0);">API文档</a></li>
      <li><a href="javascript:void(0);">帮助</a></li>
    </ul>
    <div class="user-plan fl">
      <span class="title">HI , 聚合支付有限公司<span class="arrow-2"></span></span>
    </div>
  </div>
</div>


<div class="b-out mtb-40 w-950">
  <div class="b-in">
    <div class="uc-process">
      <span class="unit b-babyBlue">确认订单</span>
      <span class="arrow-complete" style="background: #9cddf5;">
        <span class="next"></span>
        <span class="prev"></span>
      </span>
      <span class="unit b-babyBlue">支付</span>
      <span class="arrow-complete">
        <span class="next"></span>
        <span class="prev"></span>
      </span>
      <span class="unit b-coralBlue">支付成功</span>
    </div>

    <div class="order-box">
      <div class="order-info">
        <div class="order-title fix">
          <strong class="fl f-18">订单信息</strong>
          <span class="total fr">
            <span class="cny">${amount}</span>
          </span>
        </div>
        <div class="order-table-box mt-20" style="font-size: 14px;">
          <div class="order-table-scroll">
            <table class="uc-table w-p-100 f-12">
              <tbody>
                <tr>
                  <td class="c_black">商户名称</td>
                  <td>${customerName}</td>
                </tr>
                  <td class="c_black">订单号码</td>
                  <td>${outOrderId}</td>
                </tr>
                  <td class="c_black">订单金额</td>
                  <td class="money">${amount}</span></span>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

      </div>


      <div class="code" style="text-align: center;padding-bottom: 15px;padding-top: 15px;">
        <img src="pay_complete.png" alt="" style="width: 20%;">
      </div>



    </div>


  </div>
</div>





<script src="vendor.js"></script>
<script src="plugins/WdatePicker.js"></script>
<script src="UI.js"></script>
<script>
  if (window.tabSwitch) {
    tabSwitch($('#payway-tab'), 'active');
  }
</script>
</body>
</html>