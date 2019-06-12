<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>新增接口对账单配置</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 664.2px; padding:0.2em;">
  
  <form class="validator notification" action="createInterfaceReconBillConfigAction.action" method="post" prompt="DropdownMessage"  data-success="新增成功" data-fail="新增失败" style="padding: 2em 0.4em 0.4em;">

    <!-- 基本信息 -->
    <div class="row">
      <div class="content">

        <div class="productType fix">
          <div class="col fl w-p-50">
            <div class="fix">
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">接口名称:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="interfaceReconBillConfig.interfaceName" required checkInterfaceName trigger='{"checkInterfaceName": 2}'>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">接口编号:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" id="interfaceCode" name="interfaceReconBillConfig.interfaceCode" placeholder="请填写接口名称" readonly required>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">账单前缀:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="interfaceReconBillConfig.filePrefix" required rangelength="[2, 20]">
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">对账单路径:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="interfaceReconBillConfig.reconBillUrl" required rangelength="[2, 30]">
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col fl w-p-50">
            <div class="fix">
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">配置状态:</span>
                  <div class="input-wrap">
                    <dict:select styleClass="input-select" name="interfaceReconBillConfig.status" dictTypeId="ACCOUNT_STATUS"></dict:select>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">生成账单时间:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text date-hms" date-fmt="H:mm" name="interfaceReconBillConfig.generateTime">
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>


    <div class="text-center mt-10 hidden">
      <button class="btn btn-submit">新 增</button>
    </div>

  </form>
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    // 检测根据接口名称获取接口编号
    Compared.add('checkInterfaceName', function(value, params, element, callback) {
      Api.boss.checkInterfaceName(value, function(interfaceCode) {
        $('#interfaceCode').val(interfaceCode !== null ? interfaceCode : '');
        callback(Compared.toStatus(interfaceCode !== null, '该接口名称不存在!'));
      });
    });
  </script>
</body>
</html>
