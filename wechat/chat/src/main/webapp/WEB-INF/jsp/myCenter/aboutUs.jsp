<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
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
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/css/common.css?author=zhupan&v=171201"/>
    <!--通用样式-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/css/index.css?author=zhupan&v=171201"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/assest/css/myCenter/aboutUs.css?author=zhupan&v=171201""/>
    <!--自适应函数-->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/public/response.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/public/jquery-1.8.3.min.js"></script>
    <title>关于我们</title>

</head>
<body>
<!--标题start-->
<header>
    <div class="goback" style="z-index: 3;">
        <a href="toWelcome?name=0" id="back"><img src="${pageContext.request.contextPath}/image/public/web_ico/back.png"/></a>
        关于我们
    </div>
    <div class="headers">
    <h1>渤海银行，线上渤海</h1>
    <p class="about_news" style="padding-bottom: 15px;">
        <%--来源：<span>渤海银行</span>--%>
        <span class="FTS_opt"></span>
    </p>
    <%--<p class="about_news" style="padding-bottom: 15px;"><span>2017-08-29 10:00</span></p>--%>
    </div>
</header>

<!--标题end-->
<!--正文start-->
<section>
 <p>渤海银行是1996年至今国务院批准新设立的唯一一家全国性股份制商业银行，是第一家在发起设立阶段就引进境外战略投资者的中资商业银行，是第一家总部设在天津的全国性股份制商业银行。</p>
 <p>渤海银行由天津泰达投资控股有限公司、渣打银行（香港）有限公司、中国远洋运输(集团)总公司、国家开发投资公司、宝钢集团有限公司、天津信托有限责任公司和天津商汇投资(控股)有限公司等7家股东发起设立。2005年12月30日成立，2006年2月正式对外营业。</p>
 <p> 渤海银行在发展规划中确定了成为“最佳体验现代财资管家” 的长远发展愿景，明确了以客户为中心，通过特色化、综合化、数字化、国际化四大抓手，建立人才、科技、财务、风险和机制五大保障，持续推动转型的战略定位，树立了“客户为先、开放创新、协作有为、人本关爱”的企业核心价值观。自成立以来，在制度、管理、商业模式和科技平台创新上进行了不懈地探索，实现了资本、风险和效益的协同发展，保持了包括规模、利润等成长性指标和风险控制指标的同业领先水平。</p>
 <p>2016年，渤海银行资产总额达到8562.4亿元，较年初增长12%;实现营业收入218.7亿元，同比增长18.3%；实现净利润64.8亿元，同比增长17%（未经审计）。渤海银行已在全国设立了22家一级分行、23家二级分行、119家支行、87家社区小微支行，并在香港设立了代表处，下辖分支机构网点总数达到252家，网点布局覆盖了环渤海、长三角、珠三角及中西部地区的重点城市。</p>
 <p> 2016年，在英国《银行家》杂志公布的“全球银行1000强”排名中，渤海银行综合排名逐年大幅提升，从2009年的603位，提升至202位；亚洲银行综合竞争力排名第50位。在《金融时报》主办的“中国金融机构金牌榜·金龙奖”评选中，荣获 “年度十佳互联网金融创新机构”奖，以及在《中国经营报》、《21世纪经济报道》、《每日经济新闻》等组织的一系列评选活动中，先后获得“卓越竞争力个人贷款业务银行”、“卓越金融市场业务银行”、“卓越资金存托管银行”等多项殊荣。</p>
</section>
<!--正文end-->
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript">
    $(function() {
        $(".FTS_opt").click(function() {
            if(!$(this).hasClass("hasclass")) {
                $(this).css("background-image", "url(${pageContext.request.contextPath}/image/public/mycenter/icon_A-.png)");
                $("section").css("font-size", "18px");
                $(this).addClass("hasclass");
            } else {
                $(this).css("background-image", "url(${pageContext.request.contextPath}/image/public/mycenter/icon_A.png)");
                $("section").css("font-size", "16px");
                $(this).removeClass("hasclass");
            }
        })


    })
</script>

</body>
</html>