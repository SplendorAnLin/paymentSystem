<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>运营功能管理-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <s:if test="#attr['function'].list.size()>0">
    <vlh:root value="function" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>功能名称</th>
                <th>ACTION</th>
                <td>关联菜单</td>
                <th>状态</th>
                <th>是否验证</th>
                <th>描述</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="function">
                <s:set name="function" value="#attr['function']" />
                <vlh:column property="name" />
                <vlh:column property="action" />
                <vlh:column property="menu_name" />
                <vlh:column><dict:write dictId="${function.status }" dictTypeId="ACCOUNT_STATUS_COLOR"></dict:write></vlh:column>
                <vlh:column><dict:write dictId="${function.is_check }" dictTypeId="YNY_COLOR"></dict:write></vlh:column>
                <vlh:column property="remark" />
                <vlh:column>
                  <button data-myiframe='{
                    "target": "tofunctionModify.action?id=${function.id }",
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "修改", "trigger": ".btn-submit"}
                    ]
                  }' class="btn-small">修改</button>      
                  <button onclick="delFunction('${function.id }')" class="btn-small">删除</button>
                </vlh:column>
              </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:if test="#attr['function'].list.size()==0">
    <p class="pd-10">无符合条件的查询结果</p>
  </s:if>
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    function delFunction(id) {
      AjaxConfirm('是否删除该功能?', '请确认', {
        url: 'deleteFunction.action',
        type: 'post',
        data : {
          "Id" : id
        },
        success: function(msg) {
          Messages.success('删除成功!');
        },
        error: function() {
          Messages.error('网络异常, 删除失败, 请稍后重试!');
        }
      }, true);
    }
  </script>
</body>
</html>
