<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>修改代付配置</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 664.2px; padding:0.2em;">
  
  <form class="validator notification" action="serviceConfigUpdate.action" method="post" prompt="DropdownMessage"  data-success="修改成功" data-fail="修改失败" style="padding: 2em 0.4em 0.4em;">

    <!-- 基本信息 -->
    <div class="row">
      <div class="content">
        <div class="productType fix">
          <div class="col fl w-p-50">
            <div class="fix">
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">用户编号:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="serviceConfigBean.ownerId" value="${serviceConfigBean.ownerId }" readonly>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">用户角色:</span>
                  <div class="input-wrap">
                    <select class="input-select" name="serviceConfigBean.ownerRole" data-value="${serviceConfigBean.ownerRole }" disabled>
                      <option value="CUSTOMER">商户</option>
                      <option value="AGENT">服务商</option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">域名:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="serviceConfigBean.domain" value="${serviceConfigBean.domain }">
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">IP:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="serviceConfigBean.custIp" value="${serviceConfigBean.custIp }">
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">手机号:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="serviceConfigBean.phone" value="${serviceConfigBean.phone }" required  phone checkPhone trigger='{"checkPhone": 2}' >
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">付款起止时间:</span>
                  <div class="input-wrap">
                    <input type="text" name="serviceConfigBean.startTime" value="${serviceConfigBean.startTime}" class="date-start w-78" date-fmt="H:mm" required>
                  </div>
                  <span class="cut"> - </span>
                  <div class="input-wrap">
                    <input type="text" name="serviceConfigBean.endTime" value="${serviceConfigBean.endTime}" class="date-end w-78" date-fmt="H:mm" required>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">单日最大限额:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="serviceConfigBean.dayMaxAmount" value="<fmt:formatNumber value="${serviceConfigBean.dayMaxAmount }" pattern="#0.00#"/>" required amount  minIgnoreEmpy="[name='serviceConfigBean.maxAmount']"  message='{"minIgnoreEmpy": "低于单笔最大金额"}'>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col fl w-p-50">
            <div class="fix">
              <div class="item">
                <div class="input-area">
                  <span class="label w-120">是否需要商户审核:</span>
                  <div class="input-wrap">
                    <select class="input-select manualAudit" name="serviceConfigBean.manualAudit" data-value="${serviceConfigBean.manualAudit}" required>
                      <option value="TRUE">自动</option>
                      <option value="FALSE">人工</option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-120">是否需要运营审核:</span>
                  <div class="input-wrap">
                    <select class="input-select bossAudit" name="serviceConfigBean.bossAudit" data-value="${serviceConfigBean.bossAudit}" required>
                      <option value="TRUE">自动</option>
                      <option value="FALSE">人工</option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-120">手机验证复核:</span>
                  <div class="input-wrap">
                    <select class="input-select usePhoneCheck" name="serviceConfigBean.usePhoneCheck" data-value="${serviceConfigBean.usePhoneCheck}" required>
                      <option value="TRUE">启用</option>
                      <option value="FALSE">关闭</option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-120">假日付:</span>
                  <div class="input-wrap">
                    <select class="input-select" name="serviceConfigBean.holidayStatus" data-value="${serviceConfigBean.holidayStatus}" required>
                      <option value="TRUE">启用</option>
                      <option value="FALSE">关闭</option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-120">代付状态:</span>
                  <div class="input-wrap">
                    <select class="input-select" name="serviceConfigBean.valid" data-value="${serviceConfigBean.valid}" required>
                      <option value="TRUE">启用</option>
                      <option value="FALSE">关闭</option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-120">自动结算:</span>
                  <div class="input-wrap">
                    <select class="input-select fireType" name="serviceConfigBean.fireType" data-value="${serviceConfigBean.fireType}" required>
                      <option value="AUTO">自动</option>
                      <option value="MAN">手动</option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-120">单笔最小金额:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="serviceConfigBean.minAmount" value="<fmt:formatNumber value="${serviceConfigBean.minAmount }" pattern="#0.00#"/>" required amount maxIgnoreEmpy="[name='serviceConfigBean.maxAmount']" message='{"maxIgnoreEmpy": "高于单笔最大金额"}'>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-120">单笔最大金额:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="serviceConfigBean.maxAmount" value="<fmt:formatNumber value="${serviceConfigBean.maxAmount }" pattern="#0.00#"/>" required amount minIgnoreEmpy="[name='serviceConfigBean.minAmount']" message='{"minIgnoreEmpy": "低于单笔最小金额"}'>
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
/*   	$(function(){
  		$("#minAmount").val(parseInt($("#minAmount").val(), 16));
  		
  		$("#maxAmount").val(parseInt($("#maxAmount").val(), 16));
  		
  		$("#dayMaxAmount").val(parseInt($("#dayMaxAmount").val(), 16));
  	});
   */
    // 检测手机号是否重复
    // 记住初始手机
    var phone = $('[name="serviceConfigBean.phone"]').val();
    Compared.add('checkPhone', function(value, params, element, callback) {
      if (value == phone)
        return Compared.toStatus(true);
      Api.boss.checkDfPhone(value, function(exist) {
        callback(Compared.toStatus(exist, '手机号已存在'));
      });
    });

    Api.boss.accInfo('${serviceConfigBean.ownerId}', function(info) {

        if (info == false || info == 'false') {
            return;
        }

        if (info.customerType == 'OEM') {
            $('.manualAudit').find('option[value="FALSE"]').attr('disabled', 'disabled');
            $('.fireType').find('option[value="AUTO"]').attr('disabled', 'disabled');
            $('.manualAudit').selectForValue("TRUE");
            $('.fireType').selectForValue("MAN");
            $('.bossAudit').find('option[value="TRUE"]').attr('disabled', 'disabled');
            $('.bossAudit').selectForValue("FALSE");
            $('.usePhoneCheck').find('option[value="TRUE"]').attr('disabled', 'disabled');
            $('.usePhoneCheck').selectForValue("FALSE");
        } else {
            $('.manualAudit').find('option[value="FALSE"]').removeAttr('disabled');
            $('.fireType').find('option[value="AUTO"]').removeAttr('disabled');
            $('.bossAudit').find('option[value="TRUE"]').removeAttr('disabled');
            $('.usePhoneCheck').find('option[value="TRUE"]').removeAttr('disabled');
        }

    });


  </script>
</body>
</html>
