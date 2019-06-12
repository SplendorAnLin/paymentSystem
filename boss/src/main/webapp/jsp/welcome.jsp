<%@ page language="java" pageEncoding="UTF-8"%>
<html lang="zh-cmn">
<head>
<%@ include file="include/header.jsp"%>
<title>首页</title>
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
              <div class="decs">
                <span class="avatar mr-20">
                  <div class="avatar-img"></div>
                </span>
                <div class="detailed">
                  <p class="company-name">${sessionScope.auth.realname}<span class="tag ml-10 bg-yellow"><c:forEach items="${sessionScope.auth.roles}" var="role">${role.name }</c:forEach></span></p>
                  <p>账户号: <span>${sessionScope.auth.username}</span></p>
                  <p>上次登录: <span class="color-green" id="ipAddress">获取中...</span></p>
                  <p class="authenticate">
                    <span class="ico"><i class="fa fa-address-card"></i></span>
                    <span class="line"></span>
                    <span class="ico" style="font-size: 2.3em;"><i class="fa fa-mobile"></i></span>
                  </p>

                  <div class="amount-count">
                    <p><span class="depict">总可用余额:</span><span class="amount" id="sumBalance">获取中...</span></p>
                    <p><span class="depict">总冻结余额:</span><span class="amount" id="freeze">获取中...</span></p>
                    <p><span class="depict">总在途余额:</span><span class="amount" id="transit">获取中...</span></p>
                    <p><span class="depict">商户可用余额:</span><span class="amount" id="customerBalance">获取中...</span></p>
                    <p><span class="depict">代理可用余额:</span><span class="amount" id="agentBalance">获取中...</span></p>
                    <p><span class="depict">上次登陆时间:</span><span class="amount color-green" id="loginTime">获取中...</span></p>
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
      //获取上次登录信息
      Api.boss.lastloginInfo(function(loginInfo) {
      	var ipAddress = $('#ipAddress');
      	var loginTime = $('#loginTime');
    	 if (!loginInfo) {
         ipAddress.text("未知")
         loginTime.text("未知");
    		 return;
    	 }
       ipAddress.text(loginInfo.loginIp || '未知')
       loginTime.text(loginInfo.loginTime.replace('T',' '));
      });
      
      //获取账户余额信息
      Api.boss.accBalance(function(balance) {
        var freeze = $('#freeze');
        var transits = $('#transit');
        var sumBalances = $('#sumBalance');
        var agentBalance = $('#agentBalance');
        var customerBalance = $('#customerBalance');
        
        if (!balance) {
           freeze.text('获取信息异常')
           transits.text('获取信息异常');
           sumBalances.text('获取信息异常')
           agentBalance.text('获取信息异常');
           customerBalance.text('获取信息异常');
           return;
        }

         var agent = balance.AGENT;
         var customer = balance.CUSTOMER;
         var sumBalance = (Number(customer.accsum - (customer.freeze + customer.transit)) + (agent.accsum - (agent.freeze + agent.transit))).toFixed(2);
         var transit = Number((agent.transit + customer.transit)).toFixed(2);
         
         freeze.text(Number(agent.freeze + customer.freeze).toFixed(2) + ' 元');
         transits.text(Number(transit).toFixed(2) + ' 元');
         sumBalances.text(Number(sumBalance).toFixed(2) + ' 元');
         agentBalance.text(Number(agent.accsum - (agent.freeze + agent.transit)).toFixed(2) + ' 元');
         customerBalance.text(Number(customer.accsum - (customer.freeze + customer.transit)).toFixed(2) + ' 元');
      });
      
       //获取进入数据
       Api.boss.dayData(function(info) {
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