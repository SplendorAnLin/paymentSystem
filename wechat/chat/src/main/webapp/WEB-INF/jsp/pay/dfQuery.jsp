<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="zh-cmn">

	<head>
		<meta charset="utf-8" />
		<meta name="renderer" content="webkit">
		<!--不要缓存-->
		<META HTTP-EQUIV="pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
		<META HTTP-EQUIV="expires" CONTENT="0">
		<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="black" name="apple-mobile-web-app-status-bar-style">
		<meta content="telephone=no" name="format-detection">
		<meta name="format-detection" content="telephone=no">
		<!-- home screen app 全屏 -->
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta http-equiv="x-rim-auto-match" content="none">
		<link rel="stylesheet" href="css/DateSelector/DateSelector.css">
		<!--全局样式-->
		<link rel="stylesheet" href="css/common.css">
		<!--下拉加载css-->
		<!--下拉加载-->
		<link rel="stylesheet" href="js/dist/debug/minirefresh.css" />

		<!--时间插件样式-->
		<link rel="stylesheet" href="css/searchQuery.css">
		<script type="text/javascript" src="js/zepto.min.js"></script>
		<!--时间插件js-->
		<script type="text/javascript" src="js/DateSelector/DateSelector.js"></script>
		<!--页面自适应函数-->
		<script type="text/javascript" src="js/response.js"></script>
		<!--下拉加载js-->
		<script type="text/javascript" src="js/dist/debug/minirefresh.js"></script>
		<script type="text/javascript">
			var contextPath = "${pageContext.request.contextPath }";
		</script>
		<title>代付订单</title>
		<style type="text/css">
		.minirefresh-theme-default .minirefresh-downwrap .downwrap-tips, .minirefresh-theme-default .minirefresh-upwrap .upwrap-tips{
			text-align: center;
			display: block;
		}
		.minirefresh-theme-default .minirefresh-upwrap{
			width: 100%;
			background: white;
		}
	</style>

	</head>

	<body oncontextmenu="return false" onselectstart="return false" >
		<div class="contant" >
			<!--头部时间选择+两个小标签start-->
			<header>
				<div class="header quary">

					<div class="div_item" id="div_item">
						<div class="item_data" id="item_data">
							<input type="text" readonly /> <i class="icon_data"><img
								src="image/riqi.png" /></i> <input type="text" class="input_click" id="date-selector-input" readonly name="startCompleteDate" on-refresh="doRefresh()" />
							<!--<div class="dtBox"></div>-->
						</div>
						<span class="to" >-</span>
						<div class="item_data">
							<input type="text" readonly /> <i class="icon_data"><img
								src="image/riqi.png" /></i> <input type="text" class="input_click" id="date-selector-inputs" readonly readonly name="endCompleteDate" on-refresh="doRefresh()" />
						</div>
					</div>
					<p class="box_sea">
						<a id="three_day" >三天内</a>
						<a id="seven_day" style="margin-right: 0.2rem;">七天内</a>
					</p>

				</div>

			</header>
			<!--头部时间选择+两个小标签end-->
			<!--展示表格内容start-->
			<section >
					<div id="minirefresh" class="minirefresh-wrap" style="top:1.8rem">
						<div class="minirefresh-scroll">
							<ul class="list" id="listdata">
								
							</ul>
						</div>
					</div>
			</section>
			<!--展示表格内容end-->
			<footer></footer>
		</div>
		<div id="targetContainers"></div>
		<div id="targetContainer"></div>
		<!--------------------获取当前时间start------------------- -->
		<script type="text/javascript">
			$(function() {
				window.onload = function() {
					var date = new Date();
					var strDate = date.getDate();
					var three_day = date.getDate() - 2;
					var seven_day = date.getDate() - 6;
					function getNowFormatDate(strDate) {
						var seperator1 = "-";
						var seperator2 = ":";
						var month = date.getMonth() + 1;
						if(month >= 1 && month <= 9) {
							month = "0" + month;
						}
						if(strDate > 0 && strDate <= 9) {
							strDate = "0" + strDate;
						}
						if(strDate <= 0) {
							strDate = getDays() + strDate;
							month -= 1;
						}
						var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
						return currentdate;
					}
					function getDays() {
						//构造当前日期对象
						var date = new Date();
						//获取年份
						var year = date.getFullYear();
						//获取当前月份
						var mouth = date.getMonth();
						//定义当月的天数；
						var days;
						//当月份为二月时，根据闰年还是非闰年判断天数
						if(mouth == 2) {
							days = year % 4 == 0 ? 29 : 28;

						} else if(mouth == 1 || mouth == 3 || mouth == 5 || mouth == 7 || mouth == 8 || mouth == 10 || mouth == 12) {
							//月份为：1,3,5,7,8,10,12 时，为大月.则天数为31；
							days = 31;
						} else {
							//其他月份，天数为：30.
							days = 30;
						}
						return days;
					}

					$("#date-selector-inputs").val(getNowFormatDate(strDate));
					$("#date-selector-input").val(getNowFormatDate(strDate));
					$("#three_day").click(function() {
						$("#date-selector-input").val(getNowFormatDate(three_day));
					})
					$("#seven_day").click(function() {
						$("#date-selector-input").val(getNowFormatDate(seven_day));
					})

				}
			});
		</script>
		<!--------------------获取当前时间end------------------ -->
		<!--下拉加载start-->
		<script type="text/javascript">
			var page = 1;
			var page_total =1;
			var miniRefresh = new MiniRefresh({
				container: '#minirefresh',
				down: {
					callback: function() {
						page=1;
						// 下拉事件
						setTimeout(function() {
							// 每次下拉刷新后，上拉的状态会被自动重置
							$.ajax({
								type: "post",
								url: "dfQueryRequest",
								data: {
									'totalPage': page_total,
									'currentPage': page,
									'startCompleteDate': $("#date-selector-input").val(),
									'endCompleteDate': $("#date-selector-inputs").val()
								},
								success: function(data) {
                                    data= eval('('+data+')');
						           	page_total = data.page.totalPage;
									$("#listdata").html("");
									for(var i = 0; i < data.result.length; i++) {
										var dates = new Date(data.result[i].CREATE_DATE.time);
										var year = dates.getFullYear();
										var month = dates.getMonth() + 1;
										var da = dates.getDate();
										var hour = dates.getHours();
										var minute = dates.getMinutes();
										var second = dates.getSeconds();
						               	data.result[i].CREATE_DATE.time = year + "-" + month + "-" + da + "   " + hour + ":" + minute + ":" + second;

                                        var texts='';
                                        var clazz = "";
                                        if(data.result[i].AUDIT_STATUS=="WAIT"){
                                            texts="待审核";
                                            clazz= "#FF0000";

										} else if(data.result[i].AUDIT_STATUS=="REFUSE"){
                                            texts="审核拒绝";
                                            clazz= "#FF0000";
                                        } else if(data.result[i].AUDIT_STATUS=='REMIT_REFUSE'){
                                            texts="付款拒绝";
                                            clazz= "#FF0000";
                                        }else if((data.result[i].AUDIT_STATUS=='PASS' || data.result[i].AUDIT_STATUS=='REMIT_WAIT') && (data.result[i].STATUS=='WAIT' || data.result[i].STATUS=='HANDLEDING')){
                                            texts="处理中";
                                            clazz= "#FF9800";
                                        }else if(data.result[i].STATUS=='SUCCESS'){
                                            texts="成功";
                                            clazz= "blue";
                                        }else if(data.result[i].AUDIT_STATUS=='PASS' && data.result[i].STATUS=='FAILED'){
                                            texts="付款失败";
                                            clazz= "#FF0000";
                                        }
                                        data.result[i].AMOUNT=parseFloat(data.result[i].AMOUNT);
										var money=data.result[i].AMOUNT.toFixed(2);
										var str =data.result[i].ACCOUNT_NO;
										var reg = /^(\d{4})\d+(\d{4})$/;
										str = str.replace(reg, "$1****$2");
						               	var vation =  '<a target="_top" href="dfQueryDetail?requestNo='+data.result[i].FLOW_NO+'">'+
                                            '<li><div class="img_paymethod"><img src="image/logo.png" /> </div>' +
											'<div class="pay_data">' +
											'<p class="font_paymethod">' + str + '</p>' +
                                            '<p class="pay_time">' + data.result[i].CREATE_DATE.time + '</p>' +
											'</div>' +
                                            '<span class="pay_states"  style="color:'+ clazz+'">' + texts + '</span>' +
											'<span class="pay_money">' + money + '</span>' +
											'</li></a>';
										$("#listdata").append(vation);
									}
									page++;
                                  miniRefresh.endDownLoading(page> page_total ? true : false);
                                  miniRefresh.endUpLoading(page> page_total ? true : false);
								}
							});
						}, 1000);


					},

				},
				up: {
					callback: function() {
						// 上拉事件
						setTimeout(function() {
							$.ajax({
								type: "POST",
								url: "dfQueryRequest",
								data: {
									'totalPage': page_total,
									'currentPage': page,
									'startCompleteDate': $("#date-selector-input").val(),
									'endCompleteDate': $("#date-selector-inputs").val()
								},
								success: function(data) {

                                    data= eval('('+data+')');
                                    console.log(data);
						           	page_total = data.page.totalPage;
									if(page_total>=page){
										for(var i = 0; i < data.result.length; i++) {
											var dates = new Date(data.result[i].CREATE_DATE.time);
											var year = dates.getFullYear();
											var month = dates.getMonth() + 1;
											var da = dates.getDate();
											var hour = dates.getHours();
											var minute = dates.getMinutes();
											var second = dates.getSeconds();
											data.result[i].CREATE_DATE.time = year + "-" + month + "-" + da + "   " + hour + ":" + minute + ":" + second;

                                            var texts='';
                                            var clazz = "";
                                            if(data.result[i].AUDIT_STATUS=="WAIT"){
                                                texts="待审核";
                                                clazz= "#FF0000";

                                            } else if(data.result[i].AUDIT_STATUS=="REFUSE"){
                                                texts="审核拒绝";
                                                clazz= "#FF0000";
                                            } else if(data.result[i].AUDIT_STATUS=='REMIT_REFUSE'){
                                                texts="付款拒绝";
                                                clazz= "#FF0000";
                                            }else if((data.result[i].AUDIT_STATUS=='PASS' || data.result[i].AUDIT_STATUS=='REMIT_WAIT') && (data.result[i].STATUS=='WAIT' || data.result[i].STATUS=='HANDLEDING')){
                                                texts="处理中";
                                                clazz= "#FF9800";
                                            }else if(data.result[i].STATUS=='SUCCESS'){
                                                texts="成功";
                                                clazz= "blue";
                                            }else if(data.result[i].AUDIT_STATUS=='PASS' && data.result[i].STATUS=='FAILED'){
                                                texts="付款失败";
                                                clazz= "#FF0000";
                                            }
                                            data.result[i].AMOUNT=parseFloat(data.result[i].AMOUNT);
    										var money=data.result[i].AMOUNT.toFixed(2);
    										
    										var str =data.result[i].ACCOUNT_NO;
											var reg = /^(\d{4})\d+(\d{4})$/;
											str = str.replace(reg, "$1****$2");

                                            var	vation = '<a target="_top" href="dfQueryDetail?requestNo='+data.result[i].FLOW_NO+'">'+
                                                '<li><div class="img_paymethod"><img src="image/logo.png" /> </div>' +
												'<div class="pay_data">' +
                                                '<p class="font_paymethod">' + str + '</p>' +
                                                '<p class="pay_time">' + data.result[i].CREATE_DATE.time + '</p>' +
                                                '</div>' +
                                               '<span class="pay_states" style="color:'+ clazz+'">' + texts + '</span>' +
                                                '<span class="pay_money">' + money + '</span>' +
												'</li></a>';
											$("#listdata").append(vation);
										}
                                  }
                                    page++;
                                  miniRefresh.endUpLoading(page> page_total ? true : false);

								}
							});
						}, 1000);
					}
				}

			});
			/***下拉加载start***/
			$("#three_day,#seven_day").click(function() {
					$("#listdata").html("");

			    miniRefresh.triggerDownLoading();
			 })
          /***时间框start**/
			new DateSelector({
					input: 'date-selector-input', //点击触发插件的input框的id 
					container: 'targetContainers', //插件插入的容器id
					type: 0, //切换按钮
					param: [1, 1, 1, 0, 0], //设置['year','month','day','hour','minute'],1为需要，0为不需要,需要连续的1
					beginTime: [1944, 13, 10], //如空数组默认设置成1970年1月1日0时0分开始，如需要设置开始时间点，数组的值对应param参数的对应值。
					endTime: [2060, 12, 12], //如空数组默认设置成次年12月31日23时59分结束，如需要设置结束时间点，数组的值对应param参数的对应值。
					recentTime: [], //如不需要设置当前时间，被为空数组，如需要设置的开始的时间点，数组的值对应param参数的对应值。
					success: function(arr) {
							$("#date-selector-input").val(arr[0] + "-" + arr[1] + "-" + arr[2]);
							$("#listdata").html("");
			            miniRefresh.triggerDownLoading();
						} //回调
				});
				new DateSelector({
					input: 'date-selector-inputs',
					container: 'targetContainer',
					type: 0,
					param: [1, 1, 1, 0, 0],
					beginTime: [1944, 13, 20],
					endTime: [2060, 10, 21],
					recentTime: [],
					success: function(arr) {
						$("#date-selector-inputs").val(arr[0] + "-" + arr[1] + "-" + arr[2]);
						$("#listdata").html("");
			       miniRefresh.triggerDownLoading();
					}
				});
	           /***时间框end***/
			

			</script>
		<!------------------------------------下拉加载end------------------------------------->

	</body>

</html>