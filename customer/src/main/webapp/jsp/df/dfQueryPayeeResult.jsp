<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>收款人管理-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <s:if test="#attr['dpayQueryRecipientResult'].list.size()>0">
    <vlh:root value="dpayQueryRecipientResult" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th><input type="checkbox" class="checkbox"></th>
                <th>姓名</th>
                <th>账户</th>
                <th>开户行</th>
                <th>账户类型</th>
                <th>创建时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="dpayQueryRecipientResult">
                <s:set name="dpayQueryRecipientResult" value="#attr['dpayQueryRecipientResult']" />
                <vlh:column><input type="checkbox" class="checkbox" value="${dpayQueryRecipientResult.id }" /></vlh:column>
                <vlh:column property="account_name" />
                <vlh:column property="account_no" />
                <vlh:column property="open_bank_name" />
                <vlh:column><dict:write dictId="${dpayQueryRecipientResult.account_type }" dictTypeId="ACCOUNT_TYPE"></dict:write></vlh:column>
                <vlh:column><s:date name="#dpayQueryRecipientResult.create_date" format="yyyy-MM-dd" /></vlh:column>
                <vlh:column>
                  <button onclick="deleteByPayee('<s:property value="#attr['dpayQueryRecipientResult'].id" />')" class="btn-small">删除</button>
                  <button data-myiframe='{
                    "target": "toPayeeEdit.action?id=<s:property value="#attr['dpayQueryRecipientResult'].id"/>",
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "修改", "trigger": ".btn-submit"}
                    ]
                  }' class="btn-small">编辑</button>
                  <input class="pay_bank_code" type="hidden" value="${dpayQueryRecipientResult.bank_code }" />
                  <input class="pay_sabk_code" type="hidden" value="${dpayQueryRecipientResult.sabk_code }" />
                  <input class="pay_sabk_name" type="hidden" value="${dpayQueryRecipientResult.sabk_name }" />
                  <input class="pay_cnaps_code" type="hidden" value="${dpayQueryRecipientResult.cnaps_code }" />
                  <input class="cerNo" type="hidden" value="${dpayQueryRecipientResult.id_card_no }" />
                </vlh:column>
              </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
      <%@ include file="../include/vlhPage.jsp"%>
      <div class="text-center mt-10">
        <a class="btn" onclick="dfPayee.batchDf(this)" href="javascript:void(0);">批量发起</a>
        <a class="btn"onclick="dfPayee.batchDelPayee(this)" href="javascript:void(0);">批量删除</a>
      </div>
    </vlh:root>
  </s:if>
  <s:else>
    <p class="pd-10">无符合条件的查询结果</p>
  </s:else>



  <!-- 批量代付对话框-->
  <div id="payee-batch-dialog" style="width: 770px; padding: 10px;display:none;">
    <div class="text-left" style="padding: 1px;">
      <p class="text-center mb-10 ">
        共发起: <span class="total text-secondary">0</span> 笔,
        总手续费: <span class="total-fee text-secondary">0</span> 元
      </p>
      <form class="validator stop-formeNnter" prompt="DropdownMessage" style="overflow: hidden;">
        <table class="data-table w-p-100 ">
          <tbody>
            <tr>
              <td><span class="accountName">李书记</span></td>
              <td><span class="accountNo">6215583202002031321</span></td>
              <td><input type="number" class="input-text amount" required amount placeholder="代付金额" name="dfSingle.amount" /></td>
              <td><input type="text" class="input-text description" value="代付出款" required name="dfSingle.description" /></td>
              <td>
                <!-- 状态图标 -->
                <span style="color: rgb(132, 182, 232);"><i class="fa fa-spinner icon-rotate"></i></span>
                <!-- 收款账号 -->
                <input type="hidden" name="dfSingle.accountNo">
                <!-- 收款人姓名 -->
                <input type="hidden" name="dfSingle.accountName">
                <!-- 银行名称-->
                <input type="hidden" name="dfSingle.bankName">
                <!-- 银行编号 -->
                <input type="hidden" name="dfSingle.bankCode">
                <!-- 对公对私 -->
                <input type="hidden" name="dfSingle.accountType">
                <!-- code -->
                <input type="hidden" name="payee.sabkCode">
                <input type="hidden" name="dfSingle.cerType" value="ID">
                <input type="hidden" name="dfSingle.requestType" value="PAGE">
                <input type="hidden" name="dfSingle.cardType" value="DEBIT">
                <input type="hidden" name="dfSingle.cerNo">
              </td>
            </tr>
          </tbody>
        </table>
      </form>
    </div>
  </div>



  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    // 初始化批量代付
    dfPayee.init();
    
    // 删除收款人
    function deleteByPayee(id) {
      AjaxConfirm('是否删除该收款人?', '请确认', {
        url: 'deleteByPayeeId.action',
        type: 'post',
        data : {
          "id" : id
        },
        success: function(msg) {
        	new window.top.Alert(msg == 'true' ? '删除收款人成功!' : '删除收款人失败!');
        },
        error: function() {
        	new window.top.Alert('网络异常, 删除收款人失败, 请稍后重试!');
        }
      }, true);
    }
  </script>
</body>
</html>
