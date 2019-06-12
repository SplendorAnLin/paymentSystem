<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>角色管理-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>

  <s:if test="#attr['roleInfo'].list.size()>0">
  
    <vlh:root value="roleInfo" url="?" includeParameters="*" configName="defaultlook">
  
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th width='30%'>角色名称</th>
                <th width='15%'>状态</th>
                <th width='30%'>备注</th>
                <th width='20%'>操作</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="roleInfo">
                <s:set name="roleInfo" value="#attr['roleInfo']" />
                <vlh:column property="name" />
                <vlh:column property="status">
                  <s:if test="#roleInfo.status == 'TRUE'">
                  <span style="color: blue;">开启</span></s:if>
                  <s:else><span style="color: red">关闭</span></s:else>
                </vlh:column>
                <vlh:column property="remark" />
                <vlh:column>
                  <button data-dialog="${pageContext.request.contextPath}/roleQueryById.action?role.id=${roleInfo.id}" class="btn-small mr-5">详细</button>
                  <button data-myiframe='{
                    "target": "toUpdateRole.action?role.id=${roleInfo.id}",
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "修改", "trigger": ".btn-submit"}
                    ]
                  }' class="btn-small mr-5">修改</button>
                  <a href="roleUpdateStatus.action?role.id=${roleInfo.id}" class="btn-small">
                    <s:if test="${roleInfo.status eq 'TRUE' }">禁用</s:if>
                    <s:else>启用</s:else>
                  </a>
                </vlh:column>
              </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
    
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
    
  </s:if>
  <s:if test="#attr['roleInfo'].list.size()==0">
    <div style="padding-left: 30px; width: 200px;">
      <div class="erropop-auto">没有找到符合条件的角色．</div>
    </div>
  </s:if>

  <%@ include file="../include/bodyLink.jsp"%>
  <script>
/*     function changeRoleStatus(status, id) {
    	var prefix = status === 'disabled' ? '禁用' : '启用'
      AjaxConfirm('确认' + prefix + '此角色吗?', '请确认', {
        url: '${pageContext.request.contextPath}/roleUpdateStatus.action?role.id=' + id,
        type: 'get',
        success: function(msg) {
           if (msg === 'TRUE') {
            Messages.success(prefix + '成功!');
          } else {
            Messages.error( prefix + '失败!');
          }
        },
        error: function() {
          Messages.error('网络异常, ' + prefix + '角色失败, 请稍后重试!');
        }
      }, true);
    } */
  </script>
</body>
</html>
