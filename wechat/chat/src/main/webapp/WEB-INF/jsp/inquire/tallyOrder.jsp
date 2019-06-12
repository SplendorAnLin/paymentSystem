<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<meta name="renderer" content="webkit">
		<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="black" name="apple-mobile-web-app-status-bar-style">
		<meta content="telephone=no" name="format-detection">
		<meta name="apple-mobile-web-app-status-bar-style" content="black" />
		<!--微信自带样式-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/weui/dist/weui.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/weui/dist/style/weui.css"/>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/assest/css/jsmx/card_public.css?author=zhupan&v=171201" />
		<!--页面自适应js-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/response.js?author=zhupan&v=171201"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/jquery-1.8.3.min.js"></script>
		<!--时间插件-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/DateSelector/DateSelector.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/DateSelector/DateSelector.js"></script>
		<!--下拉加载-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/css/index.css?author=zhupan&v=171201" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/public/dist/debug/minirefresh.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/dist/debug/minirefresh.js?author=zhupan&v=171204"></script>

		<title>结算明细</title>
		<style type="text/css">
			.heades {
				background: rgba(240, 240, 240, 1);
				position: fixed;
				width: 100%;
				z-index: 100;
				height: 1.3rem;
			}
			.f-gray{
				color: #888888 !important;
			}
			.minirefresh-wrap {
				top: 1.9rem;
				background: #FFFFFF;
			}
			.minirefresh-scroll {
				background: #FFFFFF;
			}
			#date-selector-input{
				background: transparent;
				    position: absolute;
				    width: 0.6rem;
				    height: 0.6rem;
				  border: none;
				  outline: none;
				  color:transparent ;
			}
			.weui_cell_primary {
				width: 88%;
			}
		.weui_cell_primary p{
				max-width: 90%;
				overflow: hidden;
				white-space: nowrap;
				text-overflow: ellipsis;
			}

		</style>
	</head>

	<body>
		<header>
				<div class="goback">
					<a onClick="window.history.go(-1);"><img src="${pageContext.request.contextPath}/image/public/web_ico/back.png"/></a>
					结算订单
				</div>
			<div class="heades">
				<div class="weui_cell no_access">
					<div class="weui_cell_bd weui_cell_primary ">
						<p id="today"></p>
						<p>余额：<span id="amountnum">0.00</span>元</p>
					</div>

					<div class="weui_cell_ft">
						<p><input type="text" id="date-selector-input"  readonly="readonly" unselectable="on" onfocus="this.blur()"/> <img style="width: 0.6rem;" src="${pageContext.request.contextPath}/image/public/web_ico/data.png" /></p>
					</div>
				</div>
			</div>

		</header>
		<div id="minirefresh1" class="minirefresh-wrap">
			<div class="minirefresh-scroll">
				<div id="listdata" style="overflow-x:hidden ;">

				</div>
			</div>
		</div>
			<form action="weeklySales">
				<input name="lastday" type="hidden" id="lastday" />
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
		
		<div id="targetContainer"></div>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/assest/minifreshtally.js?author=zhupan&v=171204"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				function getNowFormatDate() {
				    var date = new Date();
				    var seperator1 = "-";
				    var seperator2 = ":";
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
				var freshs;
				freshs=fresh.initMiniRefreshs(1, "selectRequest",$("#date-selector-input").val(), $("#date-selector-input").val(), function(index, data, i) {
					var d=new Date(data.responseData.request[i].CREATE_DATE);
					$("#today").html(formatDate(d));
					$("#listdata").append(add_list(data.responseData.request[i].BANK_NAME,formatTime(d),returnFloat(data.responseData.request[i].AMOUNT)));
				});
					new DateSelector({
					input: 'date-selector-input',
					container: 'targetContainer',
					type: 0,
					param: [1, 1, 1, 0, 0],
					beginTime: [1944, 1,1],
					endTime: [2060, 12,12],
					recentTime: [],
					success: function(arr) {
							if(arr[1]<10){
                                arr[1]="0"+arr[1];
							}
							if(arr[2]<10){
                                arr[2]="0"+arr[2]
							}
                        $("#date-selector-input").val(arr[0] + "-" + arr[1] + "-" + arr[2] );
                        $("#today").html(arr[0] + "年" + arr[1] + "月" + arr[2]+'日');
							$("#listdata").html("");
							freshs.changeData(1,$("#date-selector-input").val(), $("#date-selector-input").val());
							freshs.miniRefreshArr[1].triggerDownLoading();
						} //回调
				});
				 function add_list(actions,time,money){
				 	var vation = $("<div>").addClass("weui_cell no_access");
				//	var divImg = $("<div>").addClass("weui_cell_hd").append($("<img>").attr("src", "static/assest/image/icon/icon1/bank_list/其他银行.png").addClass("hd_img"));
					var divData = $("<div>").addClass("weui_cell_bd weui_cell_primary ").append($("<p>").append(actions));
					var pTime = $("<p>").addClass("fon_18 f-gray").append(time);
					var pFont = $("<div>").addClass("weui_cell_ft").append($("<p>").append(money));
					divData.append(pTime);
					vation.append(divData).append(pFont);;
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
					    
					     return year+"年"+month+"月"+date+"日"; 
					} 
				function formatTime(now){
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
				     return hour+":"+minute+":"+second;
				}

            });
		</script>

	</body>

</html>