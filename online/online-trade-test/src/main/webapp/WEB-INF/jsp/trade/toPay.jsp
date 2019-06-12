<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../include/commons-include.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="utf-8" />
    <title></title>
    <link href="${pageContext.request.contextPath}/css/result.css" rel="stylesheet" type="text/css" />
</head>
<body>
<form class="payform" action="toPayFast.htm" method="post">
    <div class="x-bank w600">
        <p>请选择银行：</p>
        <ul>
            <li><label class="bank-icon ABC" data="农业银行" code="ABC-NET-B2C"></label></li>
            <li><label class="bank-icon ICBC" data="工商银行" code="ICBC-NET-B2C"></label></li>
            <li><label class="bank-icon CCB" data="建设银行" code="CCB-NET-B2C"></label></li>
            <li><label class="bank-icon BCOM" data="交通银行" code="BOCO-NET-B2C"></label></li>
            <li><label class="bank-icon BOC" data="中国银行" code="BOC-NET-B2C"></label></li>
            <li><label class="bank-icon CMB" data="招商银行" code="CMBCHINA-NET-B2C"></label></li>
            <li><label class="bank-icon CMBC" data="民生银行" code="CMBC-NET-B2C"></label></li>
            <li><label class="bank-icon CEBB" data="光大银行" code="CEB-NET-B2C"></label></li>
            <li><label class="bank-icon BOB" data="北京银行" code="BCCB-NET-B2C"></label></li>
            <li><label class="bank-icon HXB" data="华夏银行" code="HXB-NET-B2C"></label></li>
            <li><label class="bank-icon CIB" data="兴业银行" code="CIB-NET-B2C"></label></li>
            <li><label class="bank-icon PSBC" data="中国邮政银行" code="POST-NET-B2C"></label></li>
            <li><label class="bank-icon SPDB" data="浦发银行" code="SPDB-NET-B2C"></label></li>
            <li><label class="bank-icon GDB" data="广发银行" code="GDB-NET-B2C"></label></li>
            <li><label class="bank-icon CITIC" data="中信银行" code="ECITIC-NET-B2C"></label></li>
        </ul>
    </div>
    <input name="bankco" type="hidden" />
    <p class="prompt w600">您已选择<span class="red paybank">---</span>,入金<span class='red paymoney'>---</span>元</p>
    <div class="money w600"><span>金额：</span><input type="text" name="amount" onblur="getMoney(this)" /></div>
    <div class="btn disable"><input disabled type="submit" value="提交" /></div>
</form>
<script type="text/javascript">
    document.querySelectorAll(".x-bank ul li").forEach(function (e) {
        e.addEventListener("click", getBank);
    });
    function checkValue() {
        var bankco = document.querySelector("input[name=bankco]").value;
        var amount = document.querySelector("input[name=amount]").value;
        if (bankco != "" && amount != "") {
            console.log(bankco);
            document.querySelector("input[type=submit]").removeAttribute("disabled");
            document.querySelector(".btn").className="btn";
        }
    }
    function getMoney() {
        var amount = document.querySelector("input[name=amount]").value;
        document.querySelector(".paymoney").innerHTML = amount;
        checkValue();
    };
    function getBank() {
        var arr=document.querySelectorAll(".x-bank ul li");
        for (var ele of arr) {
            ele.removeAttribute("class");
        }
        this.className = "active";
        var obj = document.querySelector("input[name=bankco]"),
            obj2=document.querySelector(".active label"),
            d = obj2.getAttribute("data"),
            v = obj2.getAttribute("code");
        obj.setAttribute("data", d);
        obj.setAttribute("value", v);
        var bank = obj.getAttribute("data");
        document.querySelector(".paybank").innerHTML = bank;
        checkValue();
    }
</script>
</body>
</html>