<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="MobileOptimized" content="240">
<meta name="viewport"
	content="width=device-width; initial-scale=1.0; minimum-scale=1.0; maximum-scale=1.0">
<link href="${pageContext.request.contextPath}/jsp/moble/css/common.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jsp/moble/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jsp/moble/js/common.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jsp/moble/js/spin.min.js"></script>
<style type="text/css">
body {
	word-break: break-all;
}
/*图片尺寸修改后 必须改此尺寸  多平台时需要适配*/
.ylcontainer img {
	width: 300px;
	height: 298px;
	margin: 0px auto;
	border: #f6f6f6 solid 1px;
}

.ylcontainer {
	width: 302px;
	height: 305px;
	margin: 0px auto;
	text-align: center;
}

.pic-num2 {
	margin: 0px auto;
	text-align: right;
}

.pic-num2 li {
	float: left;
	width: 60px;
	height: 5px;
	text-align: right;
	background-color: #fae0af;
}

.pic-num2 li.on {
	background-color: #f9a400;
}

.t_table {
	height: 305px;
}
p{text-indent:2em;}
p.more{
text-indent:0px;
}
</style>
</head>

<body>

	<link href="${pageContext.request.contextPath}/jsp/moble/css/css.css" rel="stylesheet" type="text/css">
	<div class="ylhead"><center><div style="margin-top:7px;"><font size="4" color="white">${convenientServiceInfo.title}</font></div></center></div>
	<div class="clear" style="height: 10px;"></div>
	<div class="index-ads w">
		<div class="ylcontainer" id="idContainer2"
			ontouchmove="touchMove(event);" ontouchend="touchEnd(event);"
			style="text-align: center; position: relative; overflow: hidden;">
			<div style="position: relative; height: 300px;">
				<table id="idSlider2" border="0" cellpadding="0" cellspacing="0"
					style="height: 300px; position: absolute; left: -302px;">
					<tbody>
						<tr>
							<td><a> <img src="" alt="" border="0">
							</a></td>
							<td><a> <img src="${convenientServiceInfo.img}" border="0">
							</a></td>
							<td><a> <img src="" alt="" border="0">
							</a></td>
						</tr>
					</tbody>
				</table>
			</div>
			<ul class="pic-num2" id="idNum" style="width: 300px;">
			</ul>
		</div>
	</div>
	<div class="clear" style="height: 10px;"></div>
	<div>
		&nbsp;&nbsp;详细信息：
		<div style="margin-left:10px;"><p>${convenientServiceInfo.content}</p></div>
	</div>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ylul300">
		<tbody>
			<tr>
				<td width="40%">&nbsp;&nbsp;商户名称：</td>
				<td width="60%">${convenientServiceInfo.customerName}</td>
			</tr>
			<tr>
				<td>&nbsp;&nbsp;联系人：</td>
				<td>${convenientServiceInfo.linkMan}</td>
			</tr>
			<tr>
				<td>&nbsp;&nbsp;联系电话：</td>
				<td><font color="#af251b">${convenientServiceInfo.phone}</font></td>
			</tr>
			<tr>
				<td width="50">&nbsp;&nbsp;地址：</td>
				<td>${convenientServiceInfo.addr}</td>
			</tr>
		</tbody>
	</table>

	<input type="hidden" value="5" id="activity">
	<!-- <script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript">
	var picCount = $("#activity").val();
	$(".pic-num2").css("width",(picCount*60)+"px");
	var forEach = function(array, callback){
		for (var i = 0, len = array.length; i < len; i++) { callback.call(this, array[i], i); }
	}
	var st = createPicMove("idContainer2", "idSlider2", picCount);	//图片数量更改后需更改此数值
	var nums = [];
	//插入数字
	for(var i = 0, n = st._count - 1; i <= n;i++){
		//(nums[i] = $("idNum").appendChild(document.createElement("li"))).innerHTML = ++i;
		nums[i] = document.getElementById("idNum").appendChild(document.createElement("li"));
	}
	//设置按钮样式
	st.onStart = function(){
		forEach(nums, function(o, i){ o.className = st.Index == i ? "on" : ""; })
	}

    var touchX = [];
	var touchY = [];
	function touchMove(event) {
		var touches = event.touches;
		var fristTouch = touches[0];
		var lastX = $(window).scrollLeft();
		var lastY = $(window).scrollTop();
		touchX.push(fristTouch.pageX);
		touchY.push(fristTouch.pageY);
		if (touchY.length > 1) {
			event.preventDefault();
			var dis = touchY[touchY.length-2]-touchY[touchY.length-1];
			if(Math.abs(dis)>50){
				window.scrollTo(lastX,window.lastY+dis);
			}
		}
	}
	//触屏  离开屏幕事件
	function touchEnd(event) {
		var touches = event.touches;
		var fristTouch = touches[0];

		if (touchX != undefined && touchX.length > 1) {
			var startX = parseInt(touchX.shift(),10);
			var endX = parseInt(touchX.pop(),10);

			var disValue = Math.abs(startX - endX);

            if (touchY.length > 1) {
                var lastX = $(window).scrollLeft();
                event.preventDefault();
                var dis = touchY[touchY.length - 2] - touchY[touchY.length - 1];
                if (Math.abs(dis) > 50 && Math.abs(dis) > disValue) {
                    window.scrollTo(lastX, window.lastY + dis);
                } else {
                    if (disValue > 50) {
                        event.preventDefault();
                        bindEvent(startX,endX);
                    }
                }
            }else{
               if (disValue > 50) {
                event.preventDefault();
                bindEvent(startX,endX);
            }
            }

			touchX = [];
			touchY = [];
		}
	}

    /**
     *  绑定触屏触发事件
     * @param start
     * @param end
     */
    function bindEvent(start,end){
         if (start >= end) {
                   st.Next();
                } else {
                    st.Previous();
                }
    }
	st.Run();
</script> -->
</body>
</html>