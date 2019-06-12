<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>收款人管理</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>

  <div class="query-box">

    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="dfQueryPayee.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">姓名:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="account_name">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账户:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="account_no">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">开户行:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="open_bank_name">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账户类型:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="account_type" dictTypeId="ACCOUNT_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">创建时间:</span>
              <div class="input-wrap">
                <input type="text" name="create_time_start" class="input-text double-input date-start">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="create_time_end" class="input-text double-input date-end">
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <a data-myiframe='{
                "target": "toPayeeAdd.action",
                "btns": [
                  {"lable": "取消"},
                  {"lable": "新增", "trigger": ".btn-submit"}
                ]
              }' class="btn" href="javascript:void(0);">新增</a>
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
