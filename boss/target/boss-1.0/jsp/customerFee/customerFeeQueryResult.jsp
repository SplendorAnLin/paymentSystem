<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>商户费率-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">

  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>

  <s:if test="#attr['customerFeeQuery'].list.size()>0">
    <vlh:root value="customerFeeQuery" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>商户编号</th>
                <th>商户名称</th>
                <th>状态</th>
                <th>产品类型</th>
                <th>费率类型</th>
                <th>商户费率</th>
                <th>最低费率</th>
                <th>最高费率</th>
                <th>创建时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>

              <vlh:row bean="customerFeeQuery">
                <s:set name="customerFee" value="#attr['customerFeeQuery']" />
                <vlh:column property="customer_no" />
                <vlh:column>${customerFee.short_name}</vlh:column>
                <vlh:column>
                  <dict:write dictId="${customerFee.status }" dictTypeId="SERVICE_PROVIDER_STATUS_COLOR"></dict:write>
                </vlh:column>
                <vlh:column>
                  <dict:write dictId="${customerFee.product_type }" dictTypeId="BF_SHARE_PAYTYPE"></dict:write>
                </vlh:column>
                <vlh:column>
                  <dict:write dictId="${customerFee.fee_type }" dictTypeId="FEE_TYPE"></dict:write>
                </vlh:column>
                <vlh:column>${customerFee.fee} </vlh:column>
                <vlh:column>${customerFee.min_fee}</vlh:column>
                <vlh:column>${customerFee.max_fee}</vlh:column>
                <vlh:column><s:date name="#customerFee.create_time" format="yyyy-MM-dd" /></vlh:column>
                <vlh:column attributes="width='10%'">
                  <button data-myiframe='{
                    "target": "toUpdateCustomerFeeAction.action?customerFee.id=${customerFee.id }",
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "修改", "trigger": ".btn-submit"}
                    ]
                  }' class="btn-small">修改</button>
                </vlh:column>
              </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
    
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:if test="#attr['customerFeeQuery'].list.size()==0">
    无符合条件的查询结果
  </s:if>


  
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
