<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="utf-8" />
		<meta name="renderer" content="webkit">
		<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="black" name="apple-mobile-web-app-status-bar-style">
		<meta content="telephone=no" name="format-detection">
		<meta name="apple-mobile-web-app-status-bar-style" content="black" />
		<!--微信自带样式-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/weui/dist/weui.css/">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/weui/dist/style/weui.css"/>
		<!--css初始化-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/css/common.css?author=zhupan&v=171201" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/css/index.css?author=zhupan&v=171201" />
		<!--页面自适应js-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/response.js?author=zhupan&v=171201"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/jquery-1.8.3.min.js"></script>
		<title>二维码收款</title>
		<style type="text/css">
			section{
				padding: 0.15rem;
				font-size: 0.2rem;
			}
			.ipt{
				border: none;
				outline: none;
				font-size: 0.5rem;
				margin-left: 0.2rem;
				width: 60%;
				font-weight: 700;
			}
			.setmoney{
				position: relative;
				font-size: 0.5rem;
				font-weight: 800;
				border-bottom: 1px solid rgba(240,240,240,0.8);
				height: 1.5rem;
				line-height: 1.5rem;
				margin: 0.2rem 0;
			}
			.container{
				padding: 0.2rem;
			}
		     .f-gray{
		     	color: gray;
		     }
			.error {
				height: 40px;
				position: absolute;
				right: 0;
				 bottom: -0.2rem;
				font-weight: 400;
				background: white;
				border: 1px solid rgba(220, 220, 220, 1);
				padding: 0 5px;
				border-radius: 5px;
				font-size: 14px;
				/* z-index: 100000000; */
				line-height: 40px;
			}
			.weui_icon_warn:before {
				border: 1px solid #f43530;
				font-size: 24px;
				color: #f43530 !important;
				background: white !important;
				border-radius: 50%;
			}
		</style>
	</head>
	<body>
	<!--	<div class="back"><a>返回</a> </div>-->
		<header>
		<div class="goback">
			<a onClick="window.history.go(-1);"><img src="${pageContext.request.contextPath}/image/public/web_ico/back.png"/></a>
			二维码收款
			</div>
		</header>
			<section>
		<div class="container bg_white" >
			<p>收款金额</p>
			<p class="setmoney">￥
				<input class="ipt" type="tel"  autofocus="autofocus"   onKeyPress="if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46 || /\.\d\d$/.test(value))event.returnValue=false;" />
			</p>
				<p class="f-gray">点击按钮生成二维码让对方扫一扫收钱</p>
			<div class="page-bd-15" style="margin-top: 0.3rem;">
			<a id="gather" class="weui_btn bg_bohai" id="bankcard_add" style="color: white;">收款</a>
		</div>
		
		</div>
			</section>
		<form action="weeklySales">
			<input name="ownerId" type="hidden" id="ownerId" value="${ownerId }"/>
			<input name="customerNo" type="hidden" id="customerNo" value="${customerNo }"/>
			<input name="userName" type="hidden" id="userName" value="${userName }"/>
		</form>
		<!--操作提示start-->
		<div class="weui_dialog_alert" style="display: none;">
			<div class="weui-mask_transparent"></div>
			<div class="weui-toast" >
				<i class="weui_icon_warn weui-icon_toast"></i>
				<p style="margin-top: 10px" class="weui-toast__content" id="responseMsg">已完成</p>
			</div>
		</div>
		<!--操作提示end-->
		<!--操作中提示start-->
		<div id="loadingToast"  style="display:none">
			<div class="weui-mask_transparent" style="background: rgba(0,0,0,0.2);"></div>
			<div class="weui-toast">
				<i class="weui-loading weui-icon_toast"></i>
				<p class="weui-toast__content">加载中</p>
			</div>
		</div>
		<!--操作中提示end-->



		<script type="text/javascript">
			$(function(){
                var m =$(".ipt");
                m.keyup(function () {
                    checkBlus();
                })
                function checkBlus(){
                    var rechargeMoney = $(".ipt").val();
                    var checkedValue = checkNum(rechargeMoney);
                    console.log(checkedValue)
                    $(".ipt").val(checkedValue);
                }
                //验证输入金额start
                function checkNum(value){
                    var str = value;
                    var len1 = str.substr(0,1);
                    var len2 = str.substr(1,1);
                    //如果第一位是0，第二位不是点，就用数字把点替换掉
                    if(str.length > 1 && len1==0 && len2 != '.'){
                        str = str.substr(1,1);
                    }
                    //第一位不能是.
                    if(len1=='.'){
                        str = '';
                    }
                    //限制只能输入一个小数点
                    if(str.indexOf(".")!=-1){
                        var str_=str.substr(str.indexOf(".")+1);
                        //限制只能输入一个小数点
                        if(str_.indexOf(".")!=-1){
                            str=str.substr(0,str.indexOf(".")+str_.indexOf(".")+1);
                        }
                    }
                    return str;
                }
                //验证输入金额end
                $(".ipt").trigger("click").focus();
				$("#gather").click(function(){
                if($.trim(m.val().length) <= 0 || m.val() == "0" || m.val() == "0.00"){
                    m.after($("<p>").addClass("error").append("所输入金额必须大于0").fadeIn("fast").delay(2000).fadeOut("fast"));
                    return false;
				}
				else {
                    $.ajax({
						type:"post",
						url:"recharge",
						data:{
                            "customerNo":$("#customerNo").val(),
                            "userName":$("#userName").val(),
							"amount":$(".ipt").val(),
							"payType":"WXNATIVE"
						},
                        beforeSend:function(){$("#loadingToast").fadeIn("fast")},
						success:function (data) {
						    if(data.responseCode=="FALSE"){
						        $("#responseMsg").html(data.responseMsg);
						     $(".weui_dialog_alert").fadeIn("fast").delay(2000).fadeOut("fast");
							}
							else {
						        window.location.href="toGathringResulet";
							}
                        },
                        complete:function(){$("#loadingToast").fadeOut("fast")}
					})
				}



				})

            })
		</script>
	</body>
</html>
