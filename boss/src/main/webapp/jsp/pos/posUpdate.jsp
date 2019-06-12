<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>POS修改</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 2em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="${pageContext.request.contextPath}/posUpdate.action" method="post" prompt="DropdownMessage"  data-success="修改成功" data-fail="修改失败">
        <input type="hidden" name="pos.id" value="${pos.id }" />
        <div class="fix">
          <c:if test="${pos.customerNo != null }">
            <div class="item">
              <div class="input-area">
                <span class="label w-120">所属商户:</span>
                <div class="input-wrap">
                  <input type="text" class="input-text" value="${pos.customerNo }" readonly >
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-120">所属网点:</span>
                <div class="input-wrap">
                  <input type="text" class="input-text" value="${pos.shopNo }" readonly="readonly" >
                </div>
              </div>
            </div>
          </c:if>
          <div class="item">
            <div class="input-area">
              <span class="label w-120">POS终端号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" value="${pos.posCati }" readonly="readonly" >
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-120">品牌:</span>
              <div class="input-wrap">
                <dict:select dictTypeId="POS_BRAND" value="${pos.posBrand }" name="pos.posBrand" styleClass="input-select"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-120">POS机型号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="pos.type" value="${pos.type }" required >
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-120">批次号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" value="${pos.batchNo }" readonly="readonly" required >
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-120">POS机具序列号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" value="${pos.posSn }" readonly="readonly" required minlength='8'>
                <i id="tips" style="cursor: pointer;" class="color-orange fa fa-exclamation-circle"></i>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-120">当前软件版本号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="pos.softVersion" value="${pos.softVersion }" required >
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-120">当前参数版本号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="pos.paramVersion" value="${pos.paramVersion }" required >
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-120">POS类型:</span>
              <div class="input-wrap">
                <dict:select dictTypeId="POS_TYPE" value="${pos.posType }" name="pos.posType" styleClass="input-select"></dict:select>
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10 hidden">
          <button class="btn btn-submit">修改</button>
        </div>
      </form>
    </div>
  </div>
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
