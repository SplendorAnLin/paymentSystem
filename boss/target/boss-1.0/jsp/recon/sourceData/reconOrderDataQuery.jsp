<%@ page language="java" pageEncoding="UTF-8" %>
<html>
<head>
  <title>对账数据查询</title>
  <%@ include file="../../include/header.jsp" %>
</head>
<body>

<div class="query-box">
  <!--查询表单-->
  <div class="query-form mt-10">
    <form id="reconOrderDataQueryForm" action="${pageContext.request.contextPath }/reconOrderDataQuery.action"
          method="post"
          target="query-result">
      <div class="adaptive fix">
        <div class="item">
          <div class="input-area">
            <span class="label w-90">对账文件类型:</span>
            <div class="input-wrap">
              <dict:select nullOption="false" id="filetype" styleClass="input-select"
                           onchange="onchangeReconFileType(this)"
                           name="reconOrderDataQueryBean.reconFileType" value="${reconOrderDataQueryBean.reconFileType}"
                           dictTypeId="RECON_FILE_TYPE"></dict:select>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-90">对账日期:</span>
            <div class="input-wrap">
              <input type="text" name="reconOrderDataQueryBean.reconDate"
                     class="input-text double-input date-start ignoreEmpy"
                     date-fmt="yyyy-MM-dd">
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-90">创建日期:</span>
            <div class="input-wrap">
              <input type="text" name="reconOrderDataQueryBean.createStartTime"
                     class="input-text double-input date-start default-time ignoreEmpy">
            </div>
            <span class="cut"> - </span>
            <div class="input-wrap">
              <input type="text" name="reconOrderDataQueryBean.createEndTime"
                     class="input-text double-input date-end default-time-end ignoreEmpy">
            </div>
          </div>
        </div>


        <%--PAYINTERFACE start--%>
        <div class="item" data-type="PAYINTERFACE">
          <div class="input-area">
            <span class="label w-100">接口订单号：</span>
            <div class="input-wrap">
              <input type="text" class="input-text"
                     name="reconOrderDataQueryBean.payinterfaceOrder.interfaceOrderCode"/>
            </div>
          </div>
        </div>
        <div class="item" data-type="PAYINTERFACE">
          <div class="input-area">
            <span class="label w-100">通道订单号：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconOrderDataQueryBean.payinterfaceOrder.bankChannelCode"/>
            </div>
          </div>
        </div>
        <%--PAYINTERFACE end--%>

        <%--ACCOUNT satrt--%>
        <div class="item" data-type="ACCOUNT">
          <div class="input-area">
            <span class="label w-100">账户号：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconOrderDataQueryBean.accountOrder.accountNo"/>
            </div>
          </div>
        </div>
        <div class="item" data-type="ACCOUNT">
          <div class="input-area">
            <span class="label w-100">交易订单号：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconOrderDataQueryBean.accountOrder.tradeOrderCode"/>
            </div>
          </div>
        </div>
        <%--ACCOUNT end--%>


        <%--BANK start--%>
        <div class="item" data-type="BANK">
          <div class="input-area">
            <span class="label w-100">接口编号：</span>
            <div class="input-wrap">
              <input type="text" class="input-text"
                     name="reconOrderDataQueryBean.baseBankChannelOrder.interfaceInfoCode"/>
            </div>
          </div>
        </div>
        <div class="item" data-type="BANK">
          <div class="input-area">
            <span class="label w-100">通道订单号：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconOrderDataQueryBean.baseBankChannelOrder.bankOrderCode"/>
            </div>
          </div>
        </div>
        <div class="item" data-type="BANK">
          <div class="input-area">
            <span class="label w-100">接口订单号：</span>
            <div class="input-wrap">
              <input type="text" class="input-text"
                     name="reconOrderDataQueryBean.baseBankChannelOrder.interfaceOrderCode"/>
            </div>
          </div>
        </div>
        <%--BANK end--%>

        <%--ONLINE start--%>
        <div class="item" data-type="ONLINE">
          <div class="input-area">
            <span class="label w-100">交易订单号：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconOrderDataQueryBean.tradeOrder.tradeOrderCode"/>
            </div>
          </div>
        </div>
        <%--ONLINE end--%>

        <%--DPAY start--%>
        <div class="item" data-type="DPAY">
          <div class="input-area">
            <span class="label w-100">交易订单号：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconOrderDataQueryBean.remitOrder.tradeOrderCode"/>
            </div>
          </div>
        </div>
        <%--DPAY end--%>

        <%--REAL_AUTH start--%>
        <div class="item" data-type="REAL_AUTH">
          <div class="input-area">
            <span class="label w-100">交易订单号：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconOrderDataQueryBean.realAuthOrder.tradeOrderCode"/>
            </div>
          </div>
        </div>
        <%--REAL_AUTH end--%>
      </div>
      <div class="text-center mt-10">
        <button class="btn query-btn">查 询</button>
        <input type="reset" class="btn clear-form" value="重 置">
      </div>
    </form>
  </div>


  <!--查询结果框架-->
  <div class="query-result mt-20">
    <iframe name="query-result" frameborder="0"></iframe>
  </div>
</div>

<%@ include file="../../include/bodyLink.jsp" %>

<script>
    function onchangeReconFileType(self) {
        $('[data-type]').hide();
        $('[data-type="' + self.value + '"]').show();
    }

    $('#filetype').trigger('change');
</script>
</body>
</html>
