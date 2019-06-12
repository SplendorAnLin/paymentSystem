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
    <link rel="stylesheet" href="${applicationScope.staticFilesHost}/gateway/css/new_file.css?author=zhupan&date=2017110717"/>
    <script src="${applicationScope.staticFilesHost}/gateway/js/response.js"></script>
    <title>订单支付</title>
   
</head>
<div class="contant">
    <form action="${applicationScope.staticFilesHost}/gateway/quick/sale" method="post" id="form" class="form">
   <%--      <header>
            订单支付
            <div>
                <img src="${applicationScope.staticFilesHost}/gateway/image/ico_logo.png"/>
            </div>
        </header> --%>

        <section >
            <div class="sec loginContent" >

                <div class="section user_form">
                    <div class="item">
                        <span class="co_left" >商户名称：</span>
                        <span class="co_right right_margin " style="color: #999;"><input  class="add_flo_right" disabled="disabled" readonly="readonly" type="text"value=" ${result.params.customerName}"/></span>
                    </div>
                </div>

                <div class="section user_form">
                    <div class="item">
                        <span class="co_left">结算卡号：</span>
                        <span class="co_right  right_margin " style="color: #e74c3c;"> <input class="add_flo_right" id="PayCard" disabled="disabled" readonly="readonly" type="text" value='${result.params.settleCardNo}'/> </span>
                        <div class="co_ico" id="toggle_ico"><img
                                src="${applicationScope.staticFilesHost}/gateway/image/ico_arrow_up.png"/></div>
                    </div>
                </div>

                <div class="toggle_mation" id="toggle_mation">
                    <div class="section user_form">
                        <div class="item">
                            <span class="co_left ">持卡人姓名：</span>
                            <span class="co_right right_margin "><input class="add_flo_right" id="username" disabled="disabled" readonly="readonly" type="text" value="${result.params.payerName}"/> </span>
                        </div>
                    </div>
                    <div class="section user_form">
                        <div class="item">
                            <span class="co_left ">身份证号：</span>
                            <span class="co_right right_margin"><input class="add_flo_right" disabled="disabled" readonly="readonly" type="text" id="Idcard"
                                                          value='${result.params.certNo}'/> </span>
                            <div class="co_ico"></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="sec null">
                <div class="section_item">

                    <div class="section user_form">
                        <div class="item item_border">
                            <span class="co_left">订单编号：</span>
                            <span class="co_right" style="float: right;">${result.params.orderCode}</span>

                        </div>
                    </div>
                    <div class="section user_form">
                        <div class="item item_border">
                            <span class="co_left">支付金额：</span>
                            <span class="co_right" style="float: right;"><i>${result.params.amount}</i>元</span>

                        </div>
                    </div>

                    <div class="section user_form">
                        <div class="item item_border">
                            <span class="co_left">支付卡号：</span>
                            <span class="co_right" style="float: right;"><input  disabled="disabled" readonly="readonly" class="input_right" type="text" id="paycards"
                                                          value=" ${result.params.payCardNo}"/></span>

                        </div>
                    </div>

                    <div class="section user_form">
                        <div class="item item_border">
                            <span class="co_left">手机号码：</span>
                            <span class="co_right" style="float: right;"><input disabled="disabled" readonly="readonly" class="input_right" type="text" id="phone" value="${result.params.phone}"/></span>
                        </div>
                    </div>
                    <div class="section user_form" id="NO_YLZF_CODE">
                        <div class="item item_border">
                            <span class="co_left">&nbsp;&nbsp;&nbsp;验证码：</span>
                          <input style="margin-left: 0.2rem;"  class="input" name="smsCode"  oninput="if(value.length>6)value=value.slice(0,6)"  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" pattern="[0-9]"   type="number" style="font-size: 0.26rem;" id="yanzheng" width="2rem" placeholder="请输入验证码"/>
                            <input type="hidden" name="token" value="${result.params.token}"/>
                            <input type="hidden" name="interfaceRequestID" value="${result.params.interfaceRequestID}"/>
                            <input type="hidden" name="interfaceCode" value="${result.params.interfaceCode}"/>
                            <input type="hidden" name="amount" value="${result.params.amount}"/>
                            <input type="hidden" name="phone" value="${result.params.phone}"/>
                            <input type="hidden" name="certNo" value="${result.params.certNo}"/>
                            <input type="hidden" name="payCardNo" value="${result.params.payCardNo}"/>
                            <input type="hidden" name="payerName" value="${result.params.payerName}"/>
                            <input type="hidden" name="settleCardNo" value="${result.params.settleCardNo}"/>
                            <input type="hidden" id="key" name="key" value="${result.params.key}"/>
                            <input type="hidden" id="customerNo" name="customerNo" value="${result.params.customerNo}"/>
                            <input type="hidden" id="outOrderId" name="outOrderId" value="${result.params.outOrderId}"/>
                            <input type="hidden" name="smsCodeType" value="${result.params.smsCodeType }" id="smsCodeType"/>
                            
                            <div class="co_ico">
                                <a class="btn yanzheng">发送验证码</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <footer>
            <button type="submit" class="btn btn_ok" style="margin-top: 0.5rem">确认付款</button>
        </footer>
    </form>
</div>
<!--加载中-->
<div class="fullpage" ><img src="${applicationScope.staticFilesHost}/gateway/image/loading.gif"/></div>
<script type="text/javascript" src="${applicationScope.staticFilesHost}/gateway/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${applicationScope.staticFilesHost}/gateway/js/validate.js"></script>
<script type="text/javascript" src="${applicationScope.staticFilesHost}/gateway/js/commons.js"></script>
<script type="text/javascript">
    $(function () {
       
        //获取页面高度

        var clientHeight =$('html').height();
        var clientWidth = $('html').width();
        $(".fullpage").css("height",clientHeight);
        $(".fullpage").css("width",clientWidth);
       
        var current = 0;
        $("#toggle_ico img").click(function () {
            current = (current + 180) % 360;
            $(this).css('transform', 'rotate(' + current + 'deg)')
            $("#toggle_mation").toggle();
        })

      

        //密文显示，隐藏中间数****
        var check = {
            //手机号隐藏
            iphoneCheck: function (dom) {
                if (!dom) {
                    return false;
                }
                var myphone = dom.val().substr(3, 4);
                var lphone = dom.val().replace(myphone, "****");
                dom.val(lphone);
            },
            //身份证号||银行卡号隐藏
            idCheck: function (dom) {
                if (!dom) {
                    return false;
                }
                var start = dom.val().substr(0, 4);
                var end = dom.val().substr(dom.val().length-4, dom.val().length);
                dom.val(start + "****" + end);
            },
            //姓名隐藏
            nameCheck: function (dom) {
                if (!dom) {
                    return false;
                }
                var myName = dom.val().substr(1, dom.length+1);
                var names = dom.val().replace(myName, "**");
                dom.val(names);
            }

        }

        check.idCheck($("#Idcard"));
        check.iphoneCheck($("#phone"));
        check.nameCheck($("#username"));
        check.idCheck($("#PayCard"));
        check.idCheck($("#paycards"));

    })
    $(function () {
        /**
         * S zhupan类库
         * $submit 提交按钮
         * $form form表单
         * alertDom
         */

        var S = zhupan,
            SC = zhupanCommon,
            yanzheng = $("#yanzheng"),
            $submit = $(":submit"),
            $form = $('form'),
            $loadingModalParent = $('.loginContent'),
            action = $form.attr('action');

        /*
         * 订单失败样式
         */
        function css_success() {
            $('.null,footer,.co_ico img').css('display', 'none');
            $(".user_form,.sec").css('background-color', 'transparent');
            $(".co_right").css('float', 'right');
            $(".add_flo_right").css("text-align","right");
            $(".loginContent").before("<div class='boll'><span class='span_ico '><img src=''/></span>  <a class='fo_mation'></a><div class='error_block'> <a class='error_nummber'>错误编码：<span></span></a><a>错误信息：<span class='error_state'></span></a> </div> </div>");
            $(".boll").after("<div class='hist'><p><a class='back' style='color:white;text-decoration: none;'  href='http://www.bank-pay.com/app/closewebview'  >返回</a></p></div>");
            $("#toggle_mation").css("display", 'block');
        }
      //判斷是否需要短信驗證
		if($("#smsCodeType").val() == "NOT_SMS_CODE"){
			$("#NO_YLZF_CODE").css("display","none");
		}
        valiFunc = function () {
            var verificationClass = S.verification();
            var validator = new verificationClass;
            if($("#smsCodeType").val() != "NOT_SMS_CODE"){
            validator.add(yanzheng, [{
                strategy: 'isNonEmpty',
                errorMsg: '验证码不能为空'
            }, {
                strategy: 'isNumber',
                errorMsg: '验证码输入格式不正确'
            }]);
            }
            return validator.start();
        },
            removeDom = function (elem) {
                SC.removeDom($(elem));
            },
            createErrorTip = function (filed, errorMsg) {
                filed.focus();
                var createErrorTip = S.getSingle(SC.createErrorTip);
                var layer = createErrorTip(filed, errorMsg);
                layer.fadeIn("fast").delay(2000).fadeOut("fast", function () {
                    this.remove();
                });
            },
            createBeforeErrorTip = function (target, cssClass, msg) {
                var createTip = S.getSingle(SC.createBeforeTip);
                var layer = createTip(target, cssClass, msg);
            };

        SC.iHoverFun(yanzheng, 'iHover');

        //调用表单提交方法
        S.formSubmit(valiFunc, {
            form: $form,
            action: action,
            dataType: 'json',
            createErrorTip: createErrorTip,
            beforeSendFunc: function () {
                $(".fullpage").fadeIn(100);
                removeDom('.error');
                $submit.prop("disabled", true);
            },
            successCallback: function (data) {
                switch (data.responseCode) {
                    case '00': //请求成功
                        css_success();
                        $('.fo_mation').text('恭喜您！交易成功');
                        $('.boll img').attr('src', '${applicationScope.staticFilesHost}/gateway/image/success_pay.png');
                        $('.error_block').css('display', 'none');
                        $(".fullpage").fadeOut('fast');
                        removeDom('.loading');
                        $submit.prop("disabled", false);
                        break;
                    case '01': //请求成功
                        var queryOrder =  function () {
                            $.post("${applicationScope.staticFilesHost}/gateway/queryWalletOrder.htm","partnerCode="+$("#customerNo").val()+"&requestCode="+$("#outOrderId").val(),function(result){
                                if (result != null && result != '') {
                                    result = eval('(' + result + ')');
                                    if (result.code == '00') {
                                        css_success();
                                        $('.fo_mation').text('恭喜您！交易成功');
                                        $('.boll img').attr('src', '${applicationScope.staticFilesHost}/gateway/image/success_pay.png');
                                        $('.error_block').css('display', 'none');
                                        $(".fullpage").fadeOut('fast');
                                        removeDom('.loading');
                                        $submit.prop("disabled", false);
                                    } else {
                                        css_success();
                                        $('.error_nummber').addClass('state');
                                        $('.fo_mation').text('交易失败');
                                        $('.boll img').attr('src', '${applicationScope.staticFilesHost}/gateway/image/fail_pay.png');
                                        $('.error_nummber span').text(result.code);
                                        $('.error_state').text(result.message);
                                        $('.back').text('重新支付');
                                        $(".fullpage").fadeOut('fast');
                                        removeDom('.loading');
                                        $submit.prop("disabled", false);
                                    }
                                } else {
                                    setTimeout(queryOrder, 1000);
                                }
                            });
                        };
                        queryOrder();
                        break;
                    case '77':
                    	var param =  '${result.params.orderCode}' + "&bankCardNo=" + $("input[name=payCardNo]").val() + "&interfaceCode=" + 
                    	$("input[name=interfaceCode]").val() + "&smsCodeType=" + data.smsCodeType;
                    	window.location.href = '${applicationScope.staticFilesHost}/gateway/quick/openCard?orderCode=' + param;
                    	break;
                    default:
                        css_success();
                        $('.error_nummber').addClass('state');
                        $('.fo_mation').text('交易失败');
                        $('.boll img').attr('src', '${applicationScope.staticFilesHost}/gateway/image/fail_pay.png');
                        $('.error_nummber span').text(data.responseCode);
                        $('.error_state').text(data.responseMsg);
                        $('.back').text('重新支付');
                        $(".fullpage").fadeOut('fast');
                        removeDom('.loading');
                        $submit.prop("disabled", false);
                        break;
                }
            },
            errorFunc: function () {
                $(".fullpage").fadeOut('fast');
                alert('Request error!!');
            },
            completeFunc: function () {
                return false;
            }
        });
    }(jQuery));
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

	if(!browser.versions.mobile) {
		//否则就是PC浏览器打开
		$("body").delegate(".back", "click", function() {
			window.history.go(-1);
		})
	}
	$(function() {
	    var curTime = 0,
		timer = null,
		btn = $('.yanzheng');
		if(timer === null) {
			curTime = 120;
			timer = setInterval(handleTimer, 1000);
		}
		
		function handleTimer() {
			if(curTime == 0) {
				clearInterval(timer);
				timer = null;
				$(".yanzheng").css("background", "#ccc");
				btn.text('发送成功');
			} else {
				$(".yanzheng").css("background", "#ccc");
				btn.text(curTime + 's后发送');
				curTime--;
			}
		}
	});

</script>
</html>