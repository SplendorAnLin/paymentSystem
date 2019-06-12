<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>字典管理</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">

    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="${pageContext.request.contextPath}/dictionaryQuery.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">字典类型编码:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="dictionaryTypeRanged.code">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">字典KEY:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="dictionaryRanged.key">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">字典VALUE:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="dictionaryRanged.value">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">字典序列:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="order">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">字典状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" name="dictionaryRanged.status" id="accountType" styleClass="input-select" dictTypeId="ALL_STATUS"></dict:select>
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <a data-myiframe='{
                "target": "${pageContext.request.contextPath}/jsp/dictionary/dictionaryAdd.jsp",
                "btns": [
                  {"lable": "取消"},
                  {"lable": "新增", "trigger": ".btn-submit"}
                ]
              }' class="btn" href="javascript:void(0);">添加</a>
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
