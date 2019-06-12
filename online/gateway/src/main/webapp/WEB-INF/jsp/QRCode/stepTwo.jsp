<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.*" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>扫码入网</title>
<link rel="stylesheet" href="${applicationScope.staticFilesHost}/gateway/css/QRCode/register.css">
<link rel="stylesheet" href="${applicationScope.staticFilesHost}/gateway/css/QRCode/Modal.css">
<link rel="stylesheet" href="${applicationScope.staticFilesHost}/gateway/css/QRCode/font-awesome.css">
<script src="${applicationScope.staticFilesHost}/gateway/js/QRCode/flexible_wap.js"></script>
<style>
.input-row .in.last {
    border-bottom: none;
}
.maskTop {
	display: flex;
	position: absolute;
	left: 0;
	top: 0;
	width: 0;
	height: 0;
	background-color: rgba(0, 0, 0, 0.7);
    text-align: center;
    align-items: center;
    transition: opacity 0.3s;
    opacity: 0;
}
.maskTop.active {
	width: 100%;
	height: 100%;
	opacity: 1;
	z-index: 5;
}
.maskTop .i {
	align-self: center;
}
.load-block {
    width: 50px;
    height: 50px;
    font-size: 1.5em;
    flex: 1;
}
</style>
</head>
<body class="bc-lightGray">
	<div class="mobileFrame">
		<div class="maskTop">
			<div class="load-block c-red">
				<i class="fa fa-spinner fa-spin fa-3x fa-fw margin-bottom"></i>
			</div>
		</div>
		<div class="nav-warp">
			<ul class="row">
				<li class="col-4"><a href="javascript:void(0);">登陆信息</a></li>
				<li class="col-4 active"><a href="javascript:void(0);">基本信息</a></li>
				<li class="col-4"><a href="javascript:void(0);">结算信息</a></li>
			</ul>
		</div>
		<form class="validator" action="${applicationScope.staticFilesHost}/gateway/two" method="POST">
		<input type="hidden" name="id" value="${id }">
			<div class="input-row">
				<div class="in row">
					<label class="col-3">商户名称:</label> <input class="col-9" type="text" name="fullName"
						maxlength="11" required checkFullName>
				</div>
			</div>
			<div class="input-row">
				<div class="in row">
					<label class="col-3">所在省:</label> <select name="province"
						class="arraw-select col-9 prov" id="province" required>
					</select>
				</div>
			</div>
			<div class="input-row">
				<div class="in row">
					<label class="col-3">所在市:</label> <select name="city"
						class="arraw-select col-9 city" id="city" required >
					</select>
				</div>
			</div>
			<div class="input-row">
				<div class="in row">
					<label class="col-3">经营地址:</label> <input class="col-9" type="text" name="addr"
						required>
				</div>
			</div>
			<div class="input-row">
				<div class="in row">
					<label class="col-3">法人姓名:</label> <input class="col-9" type="text" name="linkName"
						required>
				</div>
			</div>
			<div class="input-row">
				<div class="in row last">
					<label class="col-3">身份证号:</label> <input maxlength="18" class="col-9" type="text" name="idCard"
						required idCard>
				</div>
			</div>
			<div class="btnWrap">
				<button class="btn-full">下一步</button>
			</div>
			<%--<div class="btnWrap">--%>
				<%--<span class="contact">客服电话: <span>400-860-7199</span></span>--%>
			<%--</div>--%>
		</form>
	</div>
	<script src="${applicationScope.staticFilesHost}/gateway/js/QRCode/jquery-1.8.3.js"></script>
	<script src="${applicationScope.staticFilesHost}/gateway/js/QRCode/utils.js"></script>
	<script src="${applicationScope.staticFilesHost}/gateway/js/QRCode/verification.js"></script>
	<script src="${applicationScope.staticFilesHost}/gateway/js/QRCode/Modal.js"></script>
	<script src="${applicationScope.staticFilesHost}/gateway/js/QRCode/register.js"></script>
	<script src="${applicationScope.staticFilesHost}/gateway/js/QRCode/citySelect.js"></script>
	<script>
	var api = {
		      /**
		       * @description 获取省份数组 - 商户入网/代理商入网
		       * @param fn {function} 回调函数
		       * @returns 返回 省份数组, 否则null
		       */
		      queryProvList: function (fn) {
		        $.ajax({
		          url: '${applicationScope.staticFilesHost}/gateway/cityInformation?code=PROVINCE_TYPE',
		          type: 'post',
		          success: function (provList) {
		        	  fn(provList);
		            
		          },
		          error: function () {
		            fn(null);
		          }
		        });
		      },
		      /**
		       * 数组遍历
		       */
		      each: function(array, callback) {
		        if (!array || array.length == 0)
		          return;
		        for (var i = 0; i < array.length; ++i) {
		          callback(array[i], i);
		        }
		      },
		      /**
		       * @description 获取城市数组 - 商户入网/代理商入网
		       * @param provCode {string} 省份编码
		       * @param fn {function} 回调函数
		       * @returns 返回 城市数组, 否则null
		       */
		      queryCityList: function (provCode, fn) {
		        if (!provCode || provCode == '') {
		          fn(null);
		        }
		        $.ajax({
		          url: '${applicationScope.staticFilesHost}/gateway/cityInformation?code=' + provCode,
		          type: 'post',
		          success: function (cityList) {
		        	  fn(cityList);
		            
		          },
		          error: function () {
		            fn(null);
		          }
		        });
		      },
	};
	
	
	
  	var validator = $('.validator').data('Validator');
  	validator.options.submitHandler = function(from, status) {
  		if (status === true) {
  			$('.maskTop').addClass('active');
  		}
  	};
    $.addMethod('checkFullName', function(value, element, param, fn) {
		$.ajax({
			url : '${applicationScope.staticFilesHost}/gateway/checkFullName?fullName=' + value,
			type : 'post',
			success : function(msg) {
				fn(msg == "TRUE");
			},
			error : function() {
				fn(false);
			}
		});
   	}, '商户名称重复!', '等待中...');
    
    
    // 初始化城市下拉列表
    $('form').dictCitySelect({
      prov: null,
      city: null
    });
	</script>
</body>
</html>