<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>代付申请</title>
  <%@ include file="../include/header.jsp"%>
  <style>
  .w-p-40 {
    width: 40%;
  }
  .w-p-60 {
    width: 60%;
  }
  .tips-list {
    line-height: 30px;
    margin-left: 36px;
  }
  </style>
</head>
<body class="pd-10">
<input type="hidden" id="msg" value="${errorMsg }" />

  <!-- 单笔代付 -->
  <div class="row">
    <div class="title-h1 fix tabSwitch2">
      <span class="primary fl">单笔代付</span>
    </div>
    <div class="content fix" style="width: 1000px;padding: 10px 20px;">
      <input type="hidden" id="balance" value="${balance }" /> 
      <form class="validator fix" action="dfSingleApply.action" id="singleForm" method="post"  prompt="DropdownMessage">
        <!-- <input type="hidden" name="dfSingle.bankName" id="openBank" />  -->
        <input type="hidden" name="dfSingle.bankCode" id="bankCode" /> 
        <input type="hidden" name="dfSingle.cardType" id="cardType" /> 
        <input type="hidden" name="payee.sabkCode" id="sabkCode" />
        <input type="hidden" name="dfSingle.cerType" value="ID" />
        <input type="hidden" name="dfSingle.requestType" value="PAGE" /> 
        <div class="col fl w-p-40">
          <div class="fix">
            <div class="item">
              <div class="input-area">
                <span class="label w-90">收款账号:</span>
                <div class="input-wrap">
                  <input type="text" name="dfSingle.accountNo" id="accountNo" class="input-text" required>
                </div>
              </div>
            </div>
          <div class="item">
              <div class="input-area">
                  <span class="label w-90">身份证号:</span>
                  <div class="input-wrap">
                      <input type="text" name="dfSingle.cerNo" id="cerNo" class="input-text" required>
                  </div>
              </div>
          </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">打款原因:</span>
                <div class="input-wrap">
                  <input type="text" name="dfSingle.description"class="input-text">
                </div>
              </div>
            </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">收款账户类型:</span>
              <div class="input-wrap">
                <dict:select name="dfSingle.accountType" styleClass="input-select" id="accType" dictTypeId="PAYEE_TYPE"></dict:select>
              </div>
            </div>
          </div>
          </div>
        </div>
        <div class="col fl w-p-60">
          <div class="fix">
            <div class="item">
              <div class="input-area">
                <span class="label w-90">金额:</span>
                <div class="input-wrap">
                  <input type="text" name="dfSingle.amount" class="input-text" required amount data-ignorezero="true" maxIgnoreEmpy="#balance" message='{"maxIgnoreEmpy": "代付金额不能超过当前可用余额"}'>
                  <span class="ml-10">可用余额:<c:if test="${balance >=0}"><span class="text-secondary" id="dfbalance" > ${balance } </span></c:if></span>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">收款人姓名:</span>
                <div class="input-wrap">
                  <input type="text" name="dfSingle.accountName" id="cardName" class="input-text" required>
                  <span class="ib ml-10">
                    <span class="ib">是否保存收款人信息:</span>
                    <div class="switch-wrap ib">
                      <input type="radio" name="dfSingle.saveInfo" id="u-on" value="true" checked>
                      <lable for="u-on">是</lable>
                      <input type="radio" name="dfSingle.saveInfo" id="u-off" value="false">
                      <lable for="u-off">否</lable>
                    </div>
                  </span>
                </div>
              </div>
            </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">开户行信息:</span>
              <div class="input-wrap mr-10">
                <input type="text" class="input-text" style="width: 280px;" name="dfSingle.bankName" id="openBankName" placeholder="请输入地区、分行支行名称等关键词进行匹配" required>
              </div>
              <input class="checkbox" type="checkbox" id="sabkBankFlag" value="0">
              <label>总行</label>
            </div>
          </div>
          </div>
        </div>
        <div class="text-right mt-10" style="width: 776px;">
          <button class="btn btn-submit">提交</button>
        </div>
      </form>
    </div>
  </div>

  <!-- 批量代付 -->
  <div class="row">
    <div class="title-h1 fix tabSwitch2">
      <span class="primary fl">批量代付</span>
    </div>
    <div class="content">
      <form action="dfPayeeUpload.action" id="uploadForm" enctype="multipart/form-data" target="payeeInfoList" method="post" >
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">上传文件:</span>
              <div class="input-wrap">
                <div class="file-wrap">
                  <input type="text" class="file-text w-200" placeholder="上传您的批量表格文件" readonly>
                  <a class="btn" href="javascript:void(0);">浏览</a>
                  <input type="file" name="excel" required accept="application/vnd.ms-excel,application/x-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" prompt="message">
                  <span><span class="color-red">提示:</span>上传信息大于200条请分批上传</span>
                </div>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">下载模板:</span>
              <div class="input-wrap">
                <a href="${pageContext.request.contextPath}/jsp/template/template.xlsx" target="_blank" class="btn" >下载</a>
              </div>
            </div>
          </div>
        </div>
       </form>
      <!-- 上传文件接收器 -->
      <iframe class="hidden" id="payeeInfoList" name="payeeInfoList"></iframe>
    </div>
  </div>
  
  
  <!-- 交易提示 -->
  <div class="row">
    <div class="title-h1 fix tabSwitch2">
      <span class="primary fl">交易提示</span>
    </div>
    <div class="content">
      <ul class="tips-list">
        <%--<li>1、代付业务出款时间：早上5:00-晚上22:00。</li>--%>
        <p>1、在开户行输入框中，请输入地区、分行支行名称等关键词进行匹配。</p>
        <p>2、如果未找到开户行，请勾选总行，在开户行输入框中输入银行名称等关键词 ，选中匹配中的银行。</p>
      </ul>
    </div>
  </div>
  
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    var singleForm = $('#singleForm');
    $(document).ready(function() {

      // 获取表单验证对象实例 
      var singValidator = window.getValidator(singleForm);
      singValidator.options.onCustomize = function() {
        // 代付金额
        var amount = $('[name="dfSingle.amount"]', singleForm).val()
    	  Api.customer.dfSingleApply(singleForm.serialize(), function(msg) {
  	      var result = msg;
  	      var message = '';
  	      if (result.msg == '成功') {
  	        message = '单笔代付成功! 共 ' + amount + ' 元.';
  	      } else {
  	        message = '单笔代付失败!';
  	      }
  	      new window.top.Alert(message, '单笔代付');
  	      window.location.reload();
    	  });
      	$(document.body).showLoadImage(null, true);
      	return false;
      };
      // 初始化卡识别验证
      var autoComplete = general.SettleCardValidator(singleForm, singValidator);
    });
    
    // 初始化批量代付
    dfPayeeUpload.init($('#uploadForm'), $('#payeeInfoList'), '${balance }');
  </script>
</body>
</html>
