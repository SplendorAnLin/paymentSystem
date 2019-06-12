<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

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
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/weui/dist/weui.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/weui/dist/example/example.css?author=zhupan&v=171201" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/weui/dist/style/weui.min.css" />
	  <script type="text/javascript" src="${pageContext.request.contextPath}/js/public/jquery-1.8.3.min.js"></script>
		<title></title>
		<style type="text/css">
			.weui-tabbar__item.weui-bar__item_on .weui-tabbar__icon,
			.weui-tabbar__item.weui-bar__item_on .weui-tabbar__icon>i,
			.weui-tabbar__item.weui-bar__item_on .weui-tabbar__label {
				color: #1B82D2;
			}
		</style>
	</head>

	<body>
		<div class="page tabbar js_show">
			<div class="page__bd" style="height: 100%;">
				<div class="weui-tab">
					<div class="weui-tab__panel">
						<iframe name="myIframe" id="myIframe" frameborder="0" width="100%" height="100%">	</iframe>
					</div>
					<div class="weui-tabbar">
						<a href="toHome" target="myIframe" class="weui-tabbar__item weui-bar__item_on" data-id="0">
							<img src="${pageContext.request.contextPath}/image/public/icon/collect.png" alt="" class="weui-tabbar__icon">
							<p class="weui-tabbar__label">首页</p>
						</a>
						<a href="toWeeklySales" target="myIframe" class="weui-tabbar__item" data-id="1">
							<span style="display: inline-block;position: relative;">
                            <img src="${pageContext.request.contextPath}/image/public/icon/statistics_gray.png" alt="" class="weui-tabbar__icon">
                          	  </span>
							<p class="weui-tabbar__label">统计</p>
						</a>
						<a href="javascript:;" class="weui-tabbar__item " data-id="2">
							<span style="display: inline-block;position: relative;">
                        <img src="${pageContext.request.contextPath}/image/public/icon/message_gray.png" alt="" class="weui-tabbar__icon">
                        <span class="weui-badge" style="position: absolute;top: -2px;right: -13px;">8</span>
							</span>
							<p class="weui-tabbar__label">营销分润</p>
						</a>
						<a href="javascript:;" class="weui-tabbar__item " data-id="2">
							<span style="display: inline-block;position: relative;">
                        <img src="${pageContext.request.contextPath}/image/public/icon/message_gray.png" alt="" class="weui-tabbar__icon">
                        <span class="weui-badge" style="position: absolute;top: -2px;right: -13px;">8</span>
							</span>
							<p class="weui-tabbar__label">消息</p>
						</a>

						<a href="toMyCenter" class="weui-tabbar__item" target="myIframe" data-id="3">
							<img src="${pageContext.request.contextPath}/image/public/icon/person_gray.png" alt="" class="weui-tabbar__icon">
							<p class="weui-tabbar__label">我</p>
						</a>
					</div>
				</div>
			</div>
		</div>

		<script type="text/javascript">
			$(function() {
				var imglistok = new Array();
				imglistok[0] = "${pageContext.request.contextPath}/image/public/icon/collect.png";
				imglistok[1] = "${pageContext.request.contextPath}/image/public/icon/statistics.png";
				imglistok[2] = "${pageContext.request.contextPath}/image/public/icon/message.png";
				imglistok[3] = "${pageContext.request.contextPath}/image/public/icon/person.png";
				var imglistno = new Array();
				imglistno[0] = "${pageContext.request.contextPath}/image/public/icon/collect_gray.png";
				imglistno[1] = "${pageContext.request.contextPath}/image/public/icon/statistics_gray.png";
				imglistno[2] = "${pageContext.request.contextPath}/image/public/icon/message_gray.png";
				imglistno[3] = "${pageContext.request.contextPath}/image/public/icon/person_gray.png";
				$('.weui-tabbar__item').on('click', function() {
					$(this).addClass('weui-bar__item_on').siblings('.weui-bar__item_on').removeClass('weui-bar__item_on');
					for(var i = 0; i <= $(".weui-tabbar__item").length; i++) {
						$(".weui-tabbar__item").eq(i).find("img").attr("src", imglistno[i]);
					}
					var j = $(this).attr("data-id");
					$(this).find("img").attr("src", imglistok[j]);
				})



              //  取url中的参数
                function getUrlParam(name) {
                    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                    var r = window.location.search.substr(1).match(reg);
                    if (r != null)  {
                        return unescape(r[2]); return null;
                    }
                }
              if(getUrlParam("name")=="0")
			  {
                  $(".weui-tabbar__item").eq(3).find("img").attr("src", imglistok[0]);
                  $(".weui-tabbar__item").eq(3).css("color", "#1B82D2");
                  $(".weui-tabbar__item").eq(0).removeClass('weui-bar__item_on');
                  $(".weui-tabbar__item").eq(0).find("img").attr("src", imglistno[0]);
                  $("#myIframe").attr("src", "toMyCenter");
                  return false;
			  }
			  else {
                  $("#myIframe").attr("src", "toHome");
              }

			});
		</script>
	</body>

</html>