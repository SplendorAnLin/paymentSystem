<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>接口对账单配置</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">

    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="reconBillConfigQuery.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">接口名称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="interface_name">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">接口编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="interface_code">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">对账单路径:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="recon_bill_url">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">配置状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" name="status" styleClass="input-select" dictTypeId="ACCOUNT_STATUS"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">创建日期:</span>
              <div class="input-wrap">
                <input type="text" name="create_time_start" class="input-text default-time double-input date-start ignoreEmpy" date-fmt="yyyy-MM-dd">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="create_time_end" class="input-text default-time-end double-input date-end ignoreEmpy" date-fmt="yyyy-MM-dd">
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <a data-myiframe='{
                "target": "${pageContext.request.contextPath}/toReconBillConfigAdd.action",
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
