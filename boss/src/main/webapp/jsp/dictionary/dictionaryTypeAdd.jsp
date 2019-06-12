<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>添加字典类型</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 430px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 0;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="${pageContext.request.contextPath}/dictionaryTypeSaveOrUpdate.action" method="post" prompt="DropdownMessage"  data-success="新增成功" data-fail="新增失败">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">字典类型编码:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="dictionaryTypeRanged.code" placeholder="只能输入英文/数字/下划线" required charcheck notchinese checkdiccode trigger='{"checkdiccode": 2}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">字典类型名称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="dictionaryTypeRanged.name">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90" style="vertical-align: top;">备注信息:</span>
              <div class="input-wrap">
                <textarea class="failRemark" style="width: 285px;height: 90px;" name="dictionaryTypeRanged.remark" required></textarea>
              </div>
            </div>
          </div>

        </div>
        <div class="text-center mt-10 hidden">
          <button class="btn btn-submit">新 增</button>
        </div>
      </form>
    </div>
  </div>

  <%@ include file="../include/bodyLink.jsp"%>
  <script>

    // 字符验证, 只能为英文数字下划线
    Compared.add('charcheck', function(val, params, ele, ansyFn) {
      for (var i = 0; i < val.length; ++i) {
        var char = val[i];
        if ( /^\d+$/.test(char) || /[a-z]/.test(char.toLowerCase()) || /_/.test(char) ) {
          continue;
        } else {
          return Compared.toStatus(false, '只能为英文数字下划线');
        }
      }
      return Compared.toStatus(true);
    });

    // 检测字段类型编号是否存在
    Compared.add('checkdiccode', function(val, params, ele, ansyFn) {
      Api.boss.checkDictionaryCode(val, function(result) {
      	ansyFn(Compared.toStatus(result === 'false', '字典编号已存在!'));
      });
    });

  </script>
</body>
</html>
