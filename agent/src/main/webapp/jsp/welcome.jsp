<%@ page language="java" pageEncoding="UTF-8"%>
<html lang="zh-cmn">
<head>
  <%@ include file="include/header.jsp"%>
  <title>首页</title>
  <style>
    .welcome .decs .amount-count .depict {
      width: 70px;
    }
    .plane {
          position: relative;
    }
.animated {
    -webkit-animation-duration: 1.5s;
    animation-duration: 1.5s;
    -webkit-animation-fill-mode: both;
    animation-fill-mode: both;
    animation-iteration-count: 5;
}

@keyframes tada {
    0% {
        -webkit-transform: scale3d(1, 1, 1);
        transform: scale3d(1, 1, 1)
    }
    10%, 20% {
        -webkit-transform: scale3d(.9, .9, .9) rotate3d(0, 0, 1, -3deg);
        transform: scale3d(.9, .9, .9) rotate3d(0, 0, 1, -3deg)
    }
    30%, 50%, 70%, 90% {
        -webkit-transform: scale3d(1.1, 1.1, 1.1) rotate3d(0, 0, 1, 3deg);
        transform: scale3d(1.1, 1.1, 1.1) rotate3d(0, 0, 1, 3deg)
    }
    40%, 60%, 80% {
        -webkit-transform: scale3d(1.1, 1.1, 1.1) rotate3d(0, 0, 1, -3deg);
        transform: scale3d(1.1, 1.1, 1.1) rotate3d(0, 0, 1, -3deg)
    }
    100% {
        -webkit-transform: scale3d(1, 1, 1);
        transform: scale3d(1, 1, 1)
    }
}

.tada {
    -webkit-animation-duration: 1.5s;
    animation-duration: 1.5s;
    -webkit-animation-fill-mode: both;
    animation-fill-mode: both;
    animation-iteration-count: 3;
    -webkit-animation-name: tada;
    animation-name: tada;
    -webkit-animation-name: tada;
    animation-name: tada;
}







    .share-tips {
      position: absolute;
      right: 0;
      top: 0;
      width: 50px;
      height: 50px;
      background: url(/agent/images/shareQRcode.png) no-repeat center;
      background-size: contain;
      cursor: pointer;
    }
    .share-tips:hover {
	    -webkit-animation-duration: 1.5s;
	    animation-duration: 1.5s;
	    -webkit-animation-fill-mode: both;
	    animation-fill-mode: both;
	    animation-iteration-count: infinite;
	    -webkit-animation-name: tada;
	    animation-name: tada;
    }
  </style>
</head>
<body>

  <div class="welcome pb-10 fix">
    
    <!-- 第一列 -->
    <div class="col-1 w-p-70 fl">
      <!-- 第一行 -->
      <div class="h-315">
        <!-- 个人信息 -->
        <div class="information h-p-100 w-p-65 fl">
          <div class="area-block mr-10">
            <div class="title">
              <a class="primary" href="javascript:void(0);">个人信息</a>
            </div>
            <div class="plane">
              <%--<div class="share-tips" title="分享好友，开启致富之路" data-dialog="${pageContext.request.contextPath}/share.action">--%>
              <%--</div>--%>
              <div class="decs">
                <span class="avatar mr-20">
                  <div class="avatar-img"></div>
                </span>
                <div class="detailed">
                  <p class="company-name">${sessionScope.auth.realname}<span class="tag ml-10 bg-yellow"><c:forEach items="${sessionScope.auth.roles}" var="role">${role.name }</c:forEach></span></p>
                  <p>账户编号: <span id="userNo">获取中...</span></p>
                  <p>上次登录: <span class="color-green" id="ipAddress">获取中...</span></p>
                  <p>状态: <span class="color-green" id="status">获取中...</span></p>
                  <p class="authenticate">
                    <span class="ico"><i class="fa fa-address-card"></i></span>
                    <span class="line"></span>
                    <span class="ico" style="font-size: 2.3em;"><i class="fa fa-mobile"></i></span>
                    <%--<span class="line"></span>
                     <img data-dialog="${pageContext.request.contextPath}/share.action" id="showQrCode" style="height:18px;cursor: pointer;vertical-align: middle;" src="${pageContext.request.contextPath}/images/qr_code.png" alt=""> --%>
                  </p>
                  <div class="amount-count">
                    <p><span class="depict">可用余额:</span><span class="amount" id="amount">获取中...</span> 元 <a href="javascript:void(0);" data-myiframe='{
                        "target": "balanceInfo.action",
                        "btns": [
                          {"lable": "取消"},
                          {"lable": "提现", "trigger": ".btn-submit"}
                        ]
                      }' class="btn ml-20">提现</a> </p>
                    <p><span class="depict">账户余额:</span><span class="amount" id="balance">获取中...</span> 元</p>
                    <p><span class="depict">在途余额:</span><span class="amount" id="transitBalance">获取中...</span> 元</p>
                    <p><span class="depict">冻结余额:</span><span class="amount" id="freezeBalance">获取中...</span> 元</p>
                    <p><span class="depict">开户时间:</span><span class="amount color-green" id="openDate">获取中...</span></p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- 今日数据 -->
        <div class="today-data h-p-100 w-p-35 fl">
          <div class="area-block mr-10">
            <div class="title">
              <a class="primary" href="javascript:void(0);">今日数据</a>
            </div>
            <div class="plane">
              <div class="statistics">
                <ul>
                  <li>
                    <span class="ico bg-green"></span>
                    <span class="content">
                      <p class="depict">今日收款笔数</p>
                      <p class="count"><span id="receipt_count">0</span>笔</p>
                    </span>
                  </li>
                  <li>
                    <span class="ico bg-orange"></span>
                    <span class="content">
                      <p class="depict">今日收款金额</p>
                      <p class="count"><span id="receipt_amount">0</span>元</p>
                    </span>
                  </li>
                  <li>
                    <span class="ico bg-blue"></span>
                    <span class="content">
                      <p class="depict">今日结算笔数</p>
                      <p class="count"><span id="sett_count">0</span>笔</p>
                    </span>
                  </li>
                  <li>
                    <span class="ico bg-red"></span>
                    <span class="content">
                      <p class="depict">今日结算金额</p>
                      <p class="count"><span id="sett_amount">0</span>元</p>
                    </span>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- 第二行 -->
      <div class="h-320 mt-10 mr-10">
        <!-- 收支明细 -->
        <div class="h-p-100 w-p-100 fl" id="pay-and-bay-chart">
          <div class="area-block">
            <div class="title">
              <a class="primary" href="javascript:void(0);">收支明细</a>
              <span class="fr">
                <a href="javascript:void(0);" data-filter="WEEK" class="filter-item active">周</a>
                <span class="line"></span>
                <a href="javascript:void(0);" data-filter="MONTH" class="filter-item">月</a>
                <span class="line"></span>
                <a href="javascript:void(0);" data-filter="YEAR" class="filter-item">年</a>
              </span>
            </div>
            <div class="plane">
              <div class="pay-and-bay-chart chart chart chart-draw" id="pay-and-bay-chart-container"></div>
            </div>
          </div>
        </div>
      </div>
      <!-- 第三行 -->
      <div class="h-320 mt-10 mr-10">
        <!-- 经营分析 -->
        <div class="h-p-100 w-p-100 fl" id="analysis-chart">
          <div class="area-block">
            <div class="title">
              <a class="primary" href="javascript:void(0);">经营分析</a>
              <span class="fr">
                <a href="javascript:void(0);" data-filter="DAY" class="filter-item active">日</a>
                <span class="line"></span>
                <a href="javascript:void(0);" data-filter="WEEK" class="filter-item">周</a>
                <span class="line"></span>
                <a href="javascript:void(0);" data-filter="MONTH" class="filter-item">月</a>
                <span class="line"></span>
                <a href="javascript:void(0);" data-filter="ALL" class="filter-item">所有</a>
                <span class="line"></span>
                <a href="javascript:void(0);" class="filter-item date-ico">
                  <i class="fa fa-calendar"></i>
                  <span class="filter-date hide ib"></span>
                </a>
              </span>
            </div>
            <div class="plane">
              <div class="analysis-chart chart chart chart-draw" id="analysis-chart-container"></div>
            </div>
          </div>
        </div>
      </div>

    </div>

    <!-- 第二列 -->
    <div class="col-2 w-p-30 fl">
      <!-- 第一行 -->
      <div class="h-315">
        <!-- 公告列表 -->
        <div class="bulletin h-p-100 w-p-100 fl">
          <div class="area-block">
            <div class="title">
              <a class="primary" href="javascript:void(0);">公告</a>
            </div>
            <div class="plane">
              <iframe src="bulletinList.action" frameborder="0"></iframe>
            </div>
          </div>
        </div>
      </div>
      <!-- 第二行 -->
      <div class="h-320 mt-10">
        <!-- 订单转换率 -->
        <div class="h-p-100 w-p-100 fl" id="conver-chart">
          <div class="area-block">
            <div class="title">
              <a class="primary" href="javascript:void(0);">订单转换率</a>
              <span class="fr">
                <a href="javascript:void(0);" data-filter="DAY" class="filter-item active">日</a>
                <span class="line"></span>
                <a href="javascript:void(0);" data-filter="WEEK" class="filter-item">周</a>
                <span class="line"></span>
                <a href="javascript:void(0);" data-filter="MONTH" class="filter-item">月</a>
                <span class="line"></span>
                <a href="javascript:void(0);" data-filter="ALL" class="filter-item">所有</a>
                <span class="line"></span>
                <a href="javascript:void(0);" class="filter-item date-ico">
                  <i class="fa fa-calendar"></i>
                  <span class="filter-date hide ib"></span>
                </a>
              </span>
            </div>
            <div class="plane">
              <div class="conver-chart chart chart chart-draw" id="conver-chart-container"></div>
            </div>
          </div>
        </div>
      </div>
      <!-- 第三行 -->
      <div class="h-320 mt-10">
        <!-- 分析率 -->
        <div class="h-p-100 w-p-100 fl" id="analysis-conver-chart">
          <div class="area-block">
            <div class="title">
              <a class="primary" href="javascript:void(0);">分析率</a>
              <span class="fr">
                <a href="javascript:void(0);" data-filter="DAY" class="filter-item active">日</a>
                <span class="line"></span>
                <a href="javascript:void(0);" data-filter="WEEK" class="filter-item">周</a>
                <span class="line"></span>
                <a href="javascript:void(0);" data-filter="MONTH" class="filter-item">月</a>
                <span class="line"></span>
                <a href="javascript:void(0);" data-filter="ALL" class="filter-item">所有</a>
                <span class="line"></span>
                <a href="javascript:void(0);" class="filter-item date-ico">
                  <i class="fa fa-calendar"></i>
                  <span class="filter-date hide ib"></span>
                </a>
              </span>
            </div>
            <div class="plane" style="position: relative;">
              <span class="toggle-box fix">
                <span class="toggle-span amount">金额</span>
                <span class="toggle-span count">笔数</span>
              </span>
              <div class="analysis-conver-chart chart chart chart-draw" id="analysis-conver-chart-container"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/plugin/highcharts/highcharts.js"></script>
  <script src="${pageContext.request.contextPath}/js/plugin/highcharts/no-data-to-display.js"></script>
  <%@ include file="include/bodyLink.jsp"%>
  <script src="${pageContext.request.contextPath}/js/profile/chart.js"></script>
  <script>
    $(window).load(function() {
      $('.share-tips').addClass('tada');
      //获取上次登录信息
      Api.agent.lastloginInfo(function(loginInfo) {
        var ipAddress = $('#ipAddress');
       if (!loginInfo) {
         ipAddress.text("未知")
         return;
       }
       ipAddress.text(loginInfo.loginIp || '未知')
      });
      
      //获取账户信息
      Api.agent.accountInfo(function(accInfo) {
        var userNo = $('#userNo');
        var amount = $('#amount');
        var balance = $('#balance');
        var transitBalance = $('#transitBalance');
        var freezeBalance = $('#freezeBalance');
        var openDate = $('#openDate');
        var status = $('#status');
        
        if (!accInfo) {
        	 userNo.text('获取信息异常')
           amount.text('获取信息异常');
        	 balance.text('获取信息异常')
           transitBalance.text('获取信息异常');
        	 freezeBalance.text('获取信息异常');
        	 openDate.text('获取信息异常');
        	 status.text('获取信息异常');
           return;
        }
        
        var statusLabel = '';
        switch(accInfo.status) {
        	case 'NORMAL':
        		statusLabel = '正常';
        		break;
          case 'END_IN':
            statusLabel = '止收';
            break;
          case 'EN_OUT':
            statusLabel = '止支';
            break;
          case 'FREEZE':
            statusLabel = '冻结';
            break;
          default:
            statusLabel = '未知';
            break;
        }
        status.text(statusLabel);
        userNo.text(accInfo.userNo);
        amount.text(utils.toFixed(accInfo.amount, 2));
        balance.text(utils.toFixed(accInfo.balance, 2));
        transitBalance.text(utils.toFixed(accInfo.transitBalance, 2));
        freezeBalance.text(utils.toFixed(accInfo.freezeBalance, 2));
        var date = new Date(accInfo.openDate);
        openDate.text(date.toLocaleString());
      });
      
       //获取今日数据
       Api.agent.dayData(function(info) {
         var receipt_count = $('#receipt_count');
         var receipt_amount = $('#receipt_amount');
         var sett_count = $('#sett_count');
         var sett_amount = $('#sett_amount');
        if (!info) {
          receipt_count.text('0')
          receipt_amount.text('0');
          sett_count.text('0')
          sett_amount.text('0');
          return;
        }
        receipt_count.text(info.resMap.counts)
        receipt_amount.text(info.resMap.sum_amount);
        sett_count.text(info.dfMap.counts)
        sett_amount.text(info.dfMap.sum_amount);
       });
    });
    
    
    
  </script>
</body>
</html>