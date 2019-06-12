<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>修改代付路由</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 1053px;padding:0.2em;">
  <input type="hidden" id="msg" value="${success}">
  <form class="validator notification" style="padding: 1em;" action="${pageContext.request.contextPath}/dfRouteUpdate.action" method="post" id="form" prompt="DropdownMessage"  data-success="修改成功" data-fail="修改失败">
    <div class="query-box" style="padding: 0.1em;">

      <!-- 基本信息 -->
      <div class="row">
        <div class="title-h1 fix tabSwitch2">
          <span class="primary fl">基本信息</span>
        </div>
        <div class="content">
          <div class="adaptive fix">
            <div class="item">
              <div class="input-area">
                <span class="label w-90">路由编码:</span>
                <div class="input-wrap">
                  <input type="text" class="input-text" name="routeConfigBean.code" value="${routeConfigBean.code }"  readonly>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">代付路由名称:</span>
                <div class="input-wrap">
                  <input type="text" class="input-text" name="routeConfigBean.name" value="${routeConfigBean.name }"  disabled>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">权重:</span>
                <div class="input-wrap">
                  <input type="text" class="input-text" name="routeConfigBean.routeInfo.priority" value="${routeConfigBean.routeInfo.priority }" disabled  >
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">代付接口编号:</span>
                <div class="input-wrap">
                  <select class="input-select" name="routeConfigBean.routeInfo.remitCode" required data-defaultValue="${routeConfigBean.routeInfo.remitCode }">
                  </select>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">状态:</span>
                <div class="input-wrap">
                  <dict:select value="${routeConfigBean.status }" styleClass="input-select" name="routeConfigBean.status" dictTypeId="STATUS"></dict:select>
                </div>
              </div>
            </div>
          </div>
          <div class="text-center mt-10 hidden">
            <button class="btn btn-submit">新 增</button>
          </div>
        </div>
      </div>

      <!-- 支持类型 -->
      <div class="row">
        <div class="title-h1 fix">
          <span class="primary fl">支持类型</span>
        </div>
        <div class="content subTitle">

          <!-- 支持类型 -->
          <div class="checkbox-group">
            <div class="title-h2 fix">
              <span class="primary fl">支持银行类型</span>
            </div>
            <div class="checkbox-list fix">
              <c:forEach items="${supportPayList }" var="map">
                <c:if test="${map.key == 'bankCodeList' }">
                  <c:forEach items="${map.value }" var="m">
                    <div class="checkbox-wrap">
                      <input type="checkbox" name="routeConfigBean.routeInfo.bankCodes" value="${fn:split(m.key,".")[1] }" class="checkbox" 
                          <c:forEach items="${routeConfigBean.routeInfo.bankCodes }" var="bankCodes">
                            <c:if test="${bankCodes eq (fn:split(m.key,'.')[1]) }">checked</c:if>
                          </c:forEach>
                       >
                      <label>${m.value}</label>
                    </div>
                  </c:forEach>
                </c:if>
              </c:forEach>  
            </div>
          </div>

          <!-- 支持账户类型 -->
          <div class="checkbox-group">
            <div class="title-h2 fix">
              <span class="primary fl">支持账户类型</span>
            </div>
            <div class="checkbox-list fix">
              <c:forEach items="${supportPayList }" var="map">
                <c:if test="${map.key == 'accountTypeList' }">
                  <c:forEach items="${map.value }" var="m">
                    <div class="checkbox-wrap">
                      <input type="checkbox" class="checkbox" name="routeConfigBean.routeInfo.accountTypes" value="${fn:split(m.key,".")[1] }" 
                        <c:forEach items="${routeConfigBean.routeInfo.accountTypes }" var="accountTypes">
                          <c:if test="${accountTypes eq (fn:split(m.key,'.')[1]) }">checked</c:if>
                        </c:forEach>
                       >
                      <label>${m.value}</label>
                    </div>
                  </c:forEach>
                </c:if>
              </c:forEach>
            </div>
          </div>

          <!-- 支持卡类型 -->
          <div class="checkbox-group">
            <div class="title-h2 fix">
              <span class="primary fl">支持卡类型</span>
            </div>
            <div class="checkbox-list fix">
             <c:forEach items="${supportPayList }" var="map">
                <c:if test="${map.key == 'cardTypeList' }">
                  <c:forEach items="${map.value }" var="m">
                    <div class="checkbox-wrap">
                      <input type="checkbox" class="checkbox" name="routeConfigBean.routeInfo.cardTypes" value="${fn:split(m.key,".")[1] }"
                        <c:forEach items="${routeConfigBean.routeInfo.cardTypes }" var="cardTypes">
                          <c:if test="${cardTypes eq (fn:split(m.key,'.')[1]) }">checked</c:if>
                        </c:forEach>
                       >
                      <label>${m.value}</label>
                    </div>
                  </c:forEach>
                </c:if>
              </c:forEach>
            </div>
          </div>


          <!-- 支持认证类型 -->
          <div class="checkbox-group">
            <div class="title-h2 fix">
              <span class="primary fl">支持认证类型</span>
            </div>
            <div class="checkbox-list fix">
              <c:forEach items="${supportPayList }" var="map">
                <c:if test="${map.key == 'cerTypeList' }">
                  <c:forEach items="${map.value }" var="m">
                    <div class="checkbox-wrap">
                      <input type="checkbox" class="checkbox" name="routeConfigBean.routeInfo.cerTypes" value="${fn:split(m.key,".")[1] }" 
                        <c:forEach items="${routeConfigBean.routeInfo.cerTypes }" var="cerTypes">
                          <c:if test="${cerTypes eq (fn:split(m.key,'.')[1]) }">checked</c:if>
                        </c:forEach>
                      >
                      <label>${m.value}</label>
                    </div>
                  </c:forEach>
                </c:if>
              </c:forEach>
            </div>
          </div>



        </div>
      </div>

    </div>
  </form>
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    // 操作结果判断
    $('form').data('receiver-hook', function(status, closeDialog, iframe) {
      var msg = utils.iframeFind(iframe, '#msg').val();
      closeDialog(msg == true || msg == 'true');
    });
    // 查询代收接口信息, 并填入下拉列表
    general.infaceInfoSelect('REMIT', $('[name="routeConfigBean.routeInfo.remitCode"]'));
  </script>
</body>
</html>
