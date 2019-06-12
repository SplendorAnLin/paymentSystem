<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>新增配置</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 664px; padding:10px;">
  
  <form class="validator notification" action="savePayinterfaceAction.action" method="post" prompt="DropdownMessage" data-success="操作成功" data-fail="操作失败" style="padding: 0.4em;">

    <!-- 配置信息 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">配置信息</span>
      </div>
      <div class="content fix">
        <div class="col fl w-p-50">
          <div class="fix">
            <div class="item">
              <div class="input-area">
                <span class="label w-90">名称:</span>
                <div class="input-wrap">
                  <input class="input-text" type="text" name="noticeConfigBean.name" required notChinese>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">类型:</span>
                <div class="input-wrap">
                  <input class="input-text" type="text" name="noticeConfigBean.type" required notChinese>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">服务商编号:</span>
                <div class="input-wrap">
                  <input class="input-text" type="text" name="noticeConfigBean.ownerId" required>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">oem标志:</span>
                <div class="input-wrap">
                  <dict:select name="noticeConfigBean.oem" styleClass="input-select" dictTypeId="FEE_TYPE"></dict:select>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">状态:</span>
                <div class="input-wrap">
                  <input class="input-text" type="text" name="noticeConfigBean.stauts" required amount>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 交易配置 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">交易配置</span>
      </div>
      <div class="content">
        <div class="textarea-box">
          <textarea name="configs" wrap="hard" cols="100" class="w-p-100" style="height: 1.8rem;"></textarea>
        </div>
      </div>
    </div>


    <div class="text-center mt-10 hidden">
      <button class="btn btn-submit">新 增</button>
    </div>

  </form>

  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
