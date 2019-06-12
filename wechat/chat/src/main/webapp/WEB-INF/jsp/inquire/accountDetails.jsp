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
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/weui/dist/style/weui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/weui/dist/style/weui.css"/>
		<!--css初始化-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/css/common.css?author=zhupan&v=171201" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/assest/css/jsmx/header.css?author=zhupan&v=171201" />
		<!--页面样式-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/assest/css/jsmx/jsmx.css?author=zhupan&v=171201" />
		<!--页面自适应js-->
	  <script type="text/javascript" src="${pageContext.request.contextPath}/js/public/response.js?author=zhupan&v=171201"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/jquery-1.8.3.min.js"></script>
		<!--时间插件-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/DateSelector/DateSelector.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/DateSelector/DateSelector.js"></script>
		<!--下拉加载-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/public/dist/debug/minirefresh.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/dist/debug/minirefresh.js?author=zhupan&v=171204"></script>

		<title>账户明细</title>
		<style type="text/css">
			.minirefresh-wrap {
				top: 2.3rem;
			}
			.minirefresh-scroll {
				background: #FFFFFF;
			}
			#date-selector-input {
				background: transparent;
				position: absolute;
				width: 0.6rem;
				height: 0.6rem;
				border: none;
				outline: none;
				color: transparent;
			}
		</style>
	</head>

	<body>
		<header>
			<div class="header">
				<div class="back">
					<a onclick="history.go(-1)"><img src="${pageContext.request.contextPath}/image/public/web_ico/back.png" /></a>
					<h3>我的账户</h3>
					<div class="riqi">
						<img class="icon_riqi" src="${pageContext.request.contextPath}/image/public/icon/riqi.fw.png" />
						<input type="text" id="date-selector-input" value="" readonly="readonly" unselectable="on" onfocus="this.blur()" />
					</div>
				</div>

				<div class="all_money"><span id="my_count">0.00</span>元</div>
				<div class="text-center">可用：<span id="keyong">0.00</span></div>
			</div>
		</header>
		<section>
			<div id="minirefresh1" class="minirefresh-wrap">
				<div class="minirefresh-scroll">
					<div id="listdata">

					</div>
				</div>
			</div>
		</section>
		<form action="weeklySales">
			<input name="ownerId" type="hidden" id="ownerId" value="${ownerId }" />
			<input name="customerNo" type="hidden" id="customerNo" value="${customerNo }" />
			<input name="userName" type="hidden" id="userName" value="${userName }" />
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
		
		<div id="targetContainer"></div>
		<div id="targetContainers"></div>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/assest/minifreshzp.js?author=zhupan&v=171204"></script>
		<script type="text/javascript">
			$(document).ready(function() {
                function getNowFormatDate() {
                    var date = new Date();
                    var seperator1 = "-";
                    var month = date.getMonth() + 1;
                    var strDate = date.getDate();
                    if (month >= 1 && month <= 9) {
                        month = "0" + month;
                    }
                    if (strDate >= 0 && strDate <= 9) {
                        strDate = "0" + strDate;
                    }
                    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
                    return currentdate;
                }
                $("#date-selector-input").val(getNowFormatDate());
                quarynowdata();
			    function quarynowdata(){
                    $.ajax({
                        type:"post",
                        url:"findBalance",
                        data:{
                            "customerNo":$("#ownerId").val(),
                            "userName":$("#userName").val()
                        },
                        success:function(data){
                            if(data.responseCode=="000000"){
                                $("#my_count").html(returnFloat(data.responseData.balance));
                                $("#keyong").html(returnFloat(data.responseData.availableSettleAmount));
                            }else{
                                console.log("异常")
                            }
                        }
                    })
				}

				var freshs;
                freshs=fresh.initMiniRefreshs(1, "findAccountChange",$("#date-selector-input").val(), function(index, data, i) {
                    var plas;
                    var color;
                    if(data.responseData.resmap[i].FUND_SYMBOL=="SUBTRACT"){
                        plas="-";
                        color="red";
                    } else{
                        plas="+";
                        color="green"}
					var d=new Date(data.responseData.resmap[i].CREATE_TIME); 
					$("#listdata").append(add_list(plas,formatDate(d),returnFloat(data.responseData.resmap[i].TRANS_AMT),color ));
				});

				new DateSelector({
					input: 'date-selector-input',
					container: 'targetContainer',
					type: 0,
					param: [1, 1, 1, 0, 0],
					beginTime: [1944, 1, 1],
					endTime: [2060, 12, 12],
					recentTime: [],
					success: function(arr) {
							$("#date-selector-input").val(arr[0] + "-" + arr[1] + "-" + arr[2]);
							$("#listdata").html("");
							freshs.changeData(1,$("#date-selector-input").val());
                            quarynowdata();
						}
				});

				function add_list(symbols, time, money,color) {
					var vation=$("<div>");
					var list = $("<div>").addClass("list");
					var divData = $("<p>").addClass("left fon_30").append(time);
					var pTime = $("<p>").addClass("right fon_gray").css("color",color);
					var pFont = $("<span>").addClass("left_20").append(money);
					var symbols = $("<span>").append(symbols).addClass("lepo");
					pTime.append(symbols).append(pFont);
                    list.append(divData).append(pTime);
					vation.append(list);
					return vation;
				}
				function formatDate(now) { 
				     var year=now.getFullYear(); 
				     var month=now.getMonth()+1;
				     if(month<10){
				    	 month="0"+month;
                     }
				     var date=now.getDate();
                      if(date<10){
                    	  date="0"+date;
                      }
				     var hour=now.getHours(); 
				     if(hour<10){
				    	 hour="0"+hour;
                     }
				     var minute=now.getMinutes(); 
				     if(minute<10){
				    	 minute="0"+minute;
                     }
				     var second=now.getSeconds(); 
				     if(second<10){
				    	 second="0"+second;
                     }
				     return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second; 
				} 
			});
		</script>
	</body>
</html>