<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>新增接口</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body style="width: 664px; padding:10px;">
  
  <form class="validator notification" action="savePayinterfaceAction.action" method="post"  prompt="DropdownMessage"  data-success="操作成功" data-fail="操作失败" style="padding: 0.4em;">
    <input name="infoBean.reverseSwitch" type="hidden" value="OPEN" />
    <input name="infoBean.handlerType" type="hidden" value="AUTO" />
    <input name="infoBean.cardType" type="hidden" value="" />


    <!-- 接口信息 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">接口信息</span>
      </div>
      <div class="content fix">
        <div class="col fl w-p-50">
          <div class="fix">
            <div class="item">
              <div class="input-area">
                <span class="label w-90">接口编号:</span>
                <div class="input-wrap">
                  <input class="input-text" type="text" name="infoBean.code" required notChinese>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">接口名称:</span>
                <div class="input-wrap">
                  <input class="input-text" type="text" name="infoBean.name" required>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">费率类型:</span>
                <div class="input-wrap">
                  <dict:select name="infoBean.feeType" styleClass="input-select" dictTypeId="FEE_TYPE"></dict:select>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">费率:</span>
                <div class="input-wrap">
                  <input class="input-text" type="text" name="infoBean.fee" required amount>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">接口时间段:</span>
                <div class="input-wrap">
                  <input type="text" name="infoBean.startTime" class="date-start w-78" date-fmt="H:mm" required>
                </div>
                <span class="cut"> - </span>
                <div class="input-wrap">
                  <input type="text" name="infoBean.endTime" class="date-end w-78" date-fmt="H:mm" required>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">接口状态:</span>
                <div class="input-wrap switch-wrap">
                  <dict:radio name="infoBean.status" dictTypeId="ALL_STATUS"></dict:radio>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col fl w-p-50">
          <div class="fix">
            <div class="item">
              <div class="input-area">
                <span class="label w-90">单笔最大交易额:</span>
                <div class="input-wrap">
                  <input type="text" class="input-text" name="infoBean.singleAmountLimit" required amount minIgnoreEmpy="[name='infoBean.singleAmountLimitSmall']" message='{"minIgnoreEmpy": "低于单笔最小交易额"}'>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">单笔最小交易额:</span>
                <div class="input-wrap">
                  <input type="text" class="input-text" name="infoBean.singleAmountLimitSmall" required amount maxIgnoreEmpy="[name='infoBean.singleAmountLimit']" message='{"maxIgnoreEmpy": "超过单笔最大交易额"}'>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">接口类型:</span>
                <div class="input-wrap">
                  <dict:select name="infoBean.type" styleClass="input-select" dictTypeId="BF_INTERFACE_TYPE_NAME"></dict:select>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">接口提供方信息:</span>
                <div class="input-wrap">
                  <select class="input-select" name="infoBean.provider" id="provider" required>
                    <option value="">暂无</option>
                  </select>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">是否实时:</span>
                <div class="input-wrap">
                  <dict:select styleClass="input-select" disabled="disabled" name="infoBean.isReal" dictTypeId="INTERFACE_IS_REAL"></dict:select>
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


    <!-- 描述 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">描述</span>
      </div>
      <div class="content">
        <div class="textarea-box">
          <textarea name="infoBean.description" class="w-p-100" style="height: 1.8rem;"></textarea>
        </div>
      </div>
    </div>



    <div class="text-center mt-10 hidden">
      <button class="btn btn-submit">新 增</button>
    </div>

  </form>

  
  <%@ include file="../../include/bodyLink.jsp"%>
  <script>
    var provider = $('#provider');
    // 填充接口提供方信息下拉列表
    Api.boss.findAllProvider(function(providerList) {
      // 获取SelectBox组件实例
      var selectBox = window.getSelectBox(provider);
      selectBox.options = window.optionArray(providerList, 'shortName', 'code');
    });
    // 绑定接口类型更改
    interfaceInfo.bindInfoType();
  </script>
</body>
</html>
