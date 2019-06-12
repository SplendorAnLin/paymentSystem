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
		<!--css初始化样式-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/css/common.css?author=zhupan&v=171201" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/css/index.css?author=zhupan&v=171201" />
		 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/weui/dist/style/weui.css"/>
		<!--页面样式-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/assest/css/home/card_public.css?author=zhhupan&v=1201" />
			<!--页面自适应js-->
	   <script type="text/javascript" src="${pageContext.request.contextPath}/js/public/response.js?author=zhupan&v=171201"></script>
	   <script type="text/javascript" src="${pageContext.request.contextPath}/js/public/jquery-1.8.3.min.js"></script>
		<title>银行卡管理</title>
	</head>
	<body style="background: white;">
			<header>
			<div class="goback">
			<a href="toWelcome?name=0"><img src="${pageContext.request.contextPath}/image/public/web_ico/back.png"/></a>
			结算卡管理
			</div>
		</header>
		<section>
			<div class="hea">

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
    <div id="loadingToast" style="display:none">
        <div class="weui-mask_transparent" style="background: rgba(0,0,0,0.2);"></div>
        <div class="weui-toast">
            <i class="weui-loading weui-icon_toast"></i>
            <p class="weui-toast__content">加载中</p>
        </div>
    </div>
    <!--操作中提示end-->
			<script type="text/javascript">

		$(function() {
          /***银行卡添加节点操作start**/
			function add_card(bankNames, cardType,cardAlias, bankNOs,bankIco,bgcolor,cardAttr) {
				var card_bg = $("<div>").addClass("card_bg");
				card_bg.css("background",bgcolor);
				var card_line_f = $("<div>").addClass("height_100 top_10 po_re");
				var div_left = $("<div>").addClass("po_ab left").append($("<div>").addClass("bg_white w-80 radius_50 height_80").append($("<img>").attr("src","${pageContext.request.contextPath}/"+bankIco).addClass("w-80")));
				var div_center = $("<div>").addClass("po_ab center");
				var bankName = $("<p>").addClass("fon_30 wi47").append(bankNames);
				var bankmeth=$("<span>").append(cardType);
				var bankali=$("<span>").append(cardAlias);
				var bankmethod = $("<p>").addClass("fon_18").append(bankmeth).append("").append(bankali);
				div_center.append(bankName).append(bankmethod);
				var div_right = $("<p>").addClass("po_ab right").append("(已绑定)");
				card_line_f.append(div_left).append(div_center).append(div_right);
				var card_line_s = $("<div>").addClass("height_100 dis_box po_re");
				var bankNO = $("<p>").addClass("fon_28 line_height_100 po_ab center").append(bankNOs);
				<%--var deleteimg = $("<p>").addClass("po_ab right delete_card").append($("<img>").attr("src", "${pageContext.request.contextPath}/image/public/mycenter/Trash.png").addClass("w-80 po_ab right")).after($("<p>").addClass("hidden").append(cardAttr));--%>
				card_line_s.append(bankNO);
				card_bg.append(card_line_f).append(card_line_s);
				return card_bg;
			}
			 /***银行卡添加节点操作方法end**/

			$.ajax({
				type: "post",
				url: "queryCustomerInfo",
				data: {
					"customerNo":$("#customerNo").val(),
					"userName":$("#userName").val()
				},
                async: true,
				 beforeSend:function(){$("#loadingToast").fadeIn("fast")},
				success: function(data) {
					if(data.responseCode == "000000") {
                            var bankIco,bgcolor,bankCode;
                            var bankName=data.responseData.custSettle.openBankName;
                            var cardAlias=data.responseData.custSettle.cardAlias;
                            var accountNo= data.responseData.custSettle.accountNo;
                            var cardAttr= data.responseData.custSettle.cardAttr;
                            bankCode=data.responseData.custSettle.bankCode;
                                $.ajax({
									type:"get",
									data:"",
                                    async: false,
                                    url:"${pageContext.request.contextPath}/js/assest/bankList.json",
								    success:function(result) {
                                        var resultbank;
                                    for(var j=0;j<result.bankList.length;j++){
                                       resultbank=result.bankList[j].bankCode;
                                        if(bankCode == resultbank ){
                                            bankIco=result.bankList[j].bankIco;
                                            bgcolor=result.bankList[j].bgColor;
                                            return false;
										}
										else if (bankName.indexOf("信用")>-1){
                                            bankIco="image/public/bank_list/RCC.png";
                                            bgcolor="#006138";
                                            return false;
										}
                                    }
                                    if(resultbank != bankCode && (bankName.indexOf("信用")) <= -1){
                                        bankIco="image/public/bank_list/else.png";
										bgcolor="#D4237A";
                                        return false;
									}

                                    }
								})

                            var cards = add_card(bankName, "","",accountNo ,bankIco,bgcolor,cardAttr);
                            $(".hea").append(cards);


					}
				},
				  complete:function(){$("#loadingToast").fadeOut("fast")}
			});
			$("body").delegate(".delete_card","click",function(){
			    var ref=$(this);
                var cardAttr=$(this).next().text();
				var deleteCardNum= $(this).prev().text();
				$.ajax({
					type:"post",
					url:"unlockTansCard",
					data:{
                      "accountNo":deleteCardNum,
					  "customerNo":$("#customerNo").val(),
						"cardAttr":cardAttr,
						"userName":$("#userName").val()
					},
					success:function(data){
						if(data.responseCode=="000000"){
                            if(data.responseData.resultCode=="REP00000"){
                                ref.parent().parent().remove();
                            }
						}else{
                            $("#responseMsg").html(data.responseMsg)
                            $(".weui_dialog_alert").fadeIn("fast").delay(2000).fadeOut("fast");
						}


					}
				})
			})


		})
	</script>
	</body>
</html>
