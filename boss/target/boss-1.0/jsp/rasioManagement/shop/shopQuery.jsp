<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>网点管理</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">
    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="${pageContext.request.contextPath}/shopQuery.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">网点编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="shop_no">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">网点名称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="shop_name">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">小票打印名称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="print_name">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90" style="width: 110px;">拨号POS绑定号码:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="bind_phone_no">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">所属商户编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="customer_no">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="status" dictTypeId="STATUS"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">创建时间:</span>
              <div class="input-wrap">
                <input type="text" name="create_time_start" class="input-text default-date double-input date-start ignoreEmpy" date-fmt="yyyy-MM-dd">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="create_time_end" class="input-text default-date-end double-input date-end ignoreEmpy" date-fmt="yyyy-MM-dd">
              </div>
            </div>
          </div>
          
          
          
          
          
          
          
          
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <a data-myiframe='{
                "target": "${pageContext.request.contextPath}/jsp/rasioManagement/shop/shopAdd.jsp",
                "btnType": 2,
                "btns": [
                  {"lable": "取消"},
                  {"lable": "新增", "trigger": ".btn-submit"}
                ]
              }' class="btn" href="javascript:void(0);">新增网点</a>
          <input type="reset" class="btn clear-form" value="重 置">
        </div>
      </form>
    </div>


    <!--查询结果框架-->
    <div class="query-result mt-20">
      <iframe name="query-result" frameborder="0"></iframe>
    </div>

  </div>
  
  <%@ include file="../../include/bodyLink.jsp"%>
</body>
</html>
