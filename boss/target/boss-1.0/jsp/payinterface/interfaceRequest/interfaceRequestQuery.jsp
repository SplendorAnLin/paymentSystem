<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>接口请求</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">
    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="findAllInterfaceRequestAction.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">商户编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="requestBean.ownerID">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">业务请求号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="requestBean.bussinessOrderID">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">接口请求号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="requestBean.interfaceRequestID">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">通道订单号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="requestBean.interfaceOrderID">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">支付接口:</span>
              <div class="input-wrap">
                <select class="input-select" name="requestBean.interfaceInfoCode" id="interfaceInfo">
                  <option value="">全部</option>
                </select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">接口请求状态:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select" nullOption="true" name="requestBean.status" dictTypeId="INTERFACE_REQUEST_STATUS"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">接口类型:</span>
              <div class="input-wrap">
                <dict:select name="requestBean.remark" styleClass="input-select" nullOption="true" dictTypeId="BF_INTERFACE_TYPE_NAME"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">订单金额:</span>
              <div class="input-wrap">
                <input type="text" name="requestBean.amountStart" class="input-text double-input">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="requestBean.amountEnd" class="input-text double-input">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">创建时间:</span>
              <div class="input-wrap">
                <input type="text" name="requestBean.createTimeStart" class="input-text double-input default-time date-start ignoreEmpy">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="requestBean.createTimeEnd" class="input-text double-input default-time-end date-end ignoreEmpy">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">完成时间:</span>
              <div class="input-wrap">
                <input type="text" name="requestBean.completeTimeStart" class="input-text double-input default-time date-start ignoreEmpy">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="requestBean.completeTimeEnd" class="input-text double-input default-time-end date-end ignoreEmpy">
              </div>
            </div>
          </div>
          
          <!-- 待修复 #2  说明:暂时注释当前查询条件，待修复状态
          <div class="item">
            <div class="input-area">
              <span class="label w-90">接口提供方:</span>
              <div class="input-wrap">
                <select class="input-select" name="requestBean.interfaceProviderCode">
                  <option value="">全部</option>
                </select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">业务类型:</span>
              <div class="input-wrap">
                <select class="input-select" name="requestBean.payInterfaceRequestType">
                  <option value="">全部</option>
                </select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">卡类型:</span>
              <div class="input-wrap">
                <select class="input-select" name="requestBean.cardType">
                  <option value="">全部</option>
                </select>
              </div>
            </div>
          </div>
         -->
          
          
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <a class="btn" id="show_viewCount" data-action="interfaceFeeSumAction.action" href="javascript:void(0);">查看合计</a>
          <input type="reset" class="btn clear-form" value="重 置">
        </div>
      </form>
    </div>


    <!--查询结果框架-->
    <div class="query-result mt-20">
      <iframe name="query-result" frameborder="0"></iframe>
    </div>

  </div>

  
  <!-- 查看合计弹出框 -->
  <div class="viewCount fix" style="min-width: 250px;">
    <div class="col fl">
      <div class="row fix">
        <p class="label">总金额:</p>
        <p class="value"><span class="sum-amount"></span> 元</p>
      </div>
      <div class="row fix">
        <p class="label">总笔数:</p>
        <p class="value"><span class="sum-count"></span> 笔</p>
      </div>
      <div class="row fix">
        <p class="label">总交易成本:</p>
        <p class="value"><span class="sum-cost"></span> 元</p>
      </div>
    </div>
  </div>
  
  
  <%@ include file="../../include/bodyLink.jsp"%>
  <script>
    var interfaceInfo = $('#interfaceInfo');
    // 填充接口提供方信息下拉列表
    Api.boss.findAllInterface(function(interfaceInfoBeanList) {
      // 获取SelectBox组件实例
      var selectBox = window.getSelectBox(interfaceInfo);
      selectBox.options = window.optionArray(interfaceInfoBeanList, 'name', 'code', true);
    });
    
    // 查看合计
    $('#show_viewCount').data('sumFn', function(data, viewCount) {
    	var result = JSON.parse(data);
    	$('.sum-amount', viewCount).text(utils.toFixed(result.am) || 0);
    	$('.sum-count', viewCount).text(utils.toFixed(result.al, 0) || 0);
    	$('.sum-cost', viewCount).text(utils.toFixed(result.rf) || 0);
    });
  </script>
</body>
</html>
