<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="../include/commons-include.jsp" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="renderer" content="webkit">
    <!--不要缓存-->
    <META HTTP-EQUIV="pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <META HTTP-EQUIV="expires" CONTENT="0">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="x-rim-auto-match" content="none">
    <link rel="stylesheet" href="${applicationScope.staticFilesHost}/gateway/css/new_file.css"/>
    <script src="${applicationScope.staticFilesHost}/gateway/js/response.js"></script>
    <script type="text/javascript" src="${applicationScope.staticFilesHost}/gateway/js/jquery-1.8.3.js"></script>
    <title>订单失败</title>
</head>
<div class="contant">
    <header>
        订单失败
        <div>
            <img src="${applicationScope.staticFilesHost}/gateway/image/ico_logo.png"/>
        </div>
    </header>
    <section>
        <div class='boll'><span class='span_ico '><img src='${applicationScope.staticFilesHost}/gateway/image/fail_pay.png'/></span>
            <a class='fo_mation'></a>
            <div class='error_block'>
                <a class='error_nummber'>错误编码：${responseCode}<span></span></a>
                <a>错误信息：<span class='error_state'>${responseMsg}</span></a>
            </div>
        </div>
    </section>

    <footer>
        <div class='hist'>
            <p>
                <a class='back' style="position: relative; top: 3rem;color:white;text-decoration: none;"  href='http://www.bank-pay.com/app/closewebview'>返回</a>
            </p>
        </div>
    </footer>
    <script>
    /**判断用户当前位置start**/
	var browser = {
		versions: function() {
			var u = navigator.userAgent,
				app = navigator.appVersion;
			return { //移动终端浏览器版本信息
				trident: u.indexOf('Trident') > -1, //IE内核
				presto: u.indexOf('Presto') > -1, //opera内核
				webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
				gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
				mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
				ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
				android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或uc浏览器
				iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
				iPad: u.indexOf('iPad') > -1, //是否iPad
				webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
			};
		}(),
		language: (navigator.browserLanguage || navigator.language).toLowerCase()
	}

	if(browser.versions.mobile) { //判断是否是移动设备打开。browser代码在下面
		var ua = navigator.userAgent.toLowerCase(); //获取判断用的对象
		if(ua.match(/MicroMessenger/i) == "micromessenger") {
			//在微信中打开
		
		}
		if(ua.match(/WeiBo/i) == "weibo") {
			//在新浪微博客户端打开
		
		}
		if(ua.match(/QQ/i) == "qq") {
			//在QQ空间打开
			document.getElementById("addImg").style.display="block";
		}
	
			if(browser.versions.ios) {
			//是否在IOS浏览器打开
			
			
		}
		if(browser.versions.android) {
			//是否在安卓浏览器打开
			
		
		}
		
	} else {
		//否则就是PC浏览器打开
		$("body").delegate(".back", "click", function() {
			window.history.go(-1);
		})
	}
	/**判断用户当前位置end**/</script>
</div>
</html>