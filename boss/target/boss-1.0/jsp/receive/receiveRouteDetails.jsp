<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>代收路由详情</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 1053px;padding:0.2em;">
  
  <form style="padding: 1em;">
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
                  <input type="text" class="input-text" name="routeConfigBean.code" value="${routeConfigBean.code }"  disabled required>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">代收路由名称:</span>
                <div class="input-wrap">
                  <input type="text" class="input-text" name="routeConfigBean.name" value="${routeConfigBean.name }"  disabled required>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">权重:</span>
                <div class="input-wrap">
                  <input type="text" class="input-text" name="routeConfigBean.routeInfo.priority" value="${routeConfigBean.routeInfo.priority }" disabled required digits>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">代收接口编号:</span>
                <div class="input-wrap">
                  <select class="input-select" name="routeConfigBean.routeInfo.remitCode" data-defaultValue="${routeConfigBean.routeInfo.remitCode }" required disabled>
                  </select>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">状态:</span>
                <div class="input-wrap">
                  <dict:select value="${routeConfigBean.status }" styleClass="input-select" disabled="true" dictTypeId="ACCOUNT_STATUS"></dict:select>
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
                    <c:forEach items="${routeConfigBean.routeInfo.bankCodes }" var="bankCodes">
                      <c:if test="${bankCodes eq (fn:split(m.key,'.')[1]) }">
                        <div class="checkbox-wrap">
                          <input type="checkbox" class="checkbox" value="${fn:split(m.key,".")[1] }" checked disabled>
                          <label>${m.value}</label>
                        </div>
                      </c:if>
                    </c:forEach>
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
                    <c:forEach items="${routeConfigBean.routeInfo.accountTypes }" var="accountTypes">
                      <c:if test="${accountTypes eq (fn:split(m.key,'.')[1]) }">
                        <div class="checkbox-wrap">
                          <input type="checkbox" class="checkbox" value="${fn:split(m.key,".")[1] }" name="routeConfigBean.routeInfo.accountTypes" value="INDIVIDUAL" checked disabled>
                          <label>${m.value}</label>
                        </div>
                      </c:if>
                    </c:forEach>
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
                    <c:forEach items="${routeConfigBean.routeInfo.cardTypes }" var="cardTypes">
                      <c:if test="${cardTypes eq (fn:split(m.key,'.')[1]) }">
                        <div class="checkbox-wrap">
                          <input type="checkbox" class="checkbox" value="${fn:split(m.key,".")[1] }" name="routeConfigBean.routeInfo.cardTypes" value="DEBIT_CARD" checked disabled>
                          <label>${m.value}</label>
                        </div>
                      </c:if>
                    </c:forEach>
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
                    <c:forEach items="${routeConfigBean.routeInfo.cerTypes }" var="cerTypes">
                      <c:if test="${cerTypes eq (fn:split(m.key,'.')[1]) }">
                        <div class="checkbox-wrap">
                          <input type="checkbox" class="checkbox" value="${fn:split(m.key,".")[1] }" name="routeConfigBean.routeInfo.cerTypes" value="ID" checked disabled>
                          <label>${m.value}</label>
                        </div>
                      </c:if>
                    </c:forEach>
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
    // 查询代收接口信息, 并填入下拉列表
    general.infaceInfoSelect('RECEIVE', $('[name="routeConfigBean.routeInfo.remitCode"]'));
  </script>
</body>
</html>
