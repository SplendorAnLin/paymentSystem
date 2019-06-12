<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
 <%@ include file="../include/header.jsp"%>
  <title>白名单</title>
</head>
<body>

  <div class="query-box">
    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="nameListQuery.action" method="post" target="query-result">
        <input name="ownerId" class="hidden" value="${sessionScope.auth.customerno}">
        <div class="adaptive fix">
           <div class="item">
            <div class="input-area">
              <span class="label w-90">姓名:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="userName">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">证件号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="licenseNumber">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">帐号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="account">
              </div>
            </div>
          </div>
           <div class="item">
            <div class="input-area">
              <span class="label w-90">手机号码:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="phoneNumber">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">证件类型:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="typeOfCertificate" dictTypeId="TYPE_OF_CERTIFICATE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">卡类型:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select" nullOption="true" name="cardType" dictTypeId="WHITE_CARD_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">创建时间:</span>
              <div class="input-wrap">
                <input type="text" name="createTimeStart" class="input-text double-input date-start">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="createTimeEnd" class="input-text double-input date-end">
              </div>
            </div>
          </div>
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

  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>