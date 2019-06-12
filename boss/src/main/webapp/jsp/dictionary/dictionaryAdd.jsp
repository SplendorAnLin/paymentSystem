<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>添加字典</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 380px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 2.5em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="${pageContext.request.contextPath}/dictionarySaveOrUpdate.action?dictionaryType=addDictionary" method="post" prompt="DropdownMessage"  data-success="新增成功" data-fail="新增失败">
        <input type="text" class="input-text hidden" name="dictionaryTypeRanged.code" value="${param.dictionaryType }" > 
        <div class="fix">
<!--           <div class="item">
            <div class="input-area">
              <span class="label w-90">字典类型编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="dictionaryTypeRanged.code" placeholder="只能输入英文/数字/下划线" required charcheck notchinese >
              </div>
            </div>
          </div> -->
          <div class="item">
            <div class="input-area">
              <span class="label w-90">字典KEY:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="dictionaryRanged.key" placeholder="只能输入英文/数字/下划线" required charcheck notchinese>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">字典VALUE:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="dictionaryRanged.value" required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">字典状态:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select" name="dictionaryRanged.status" dictTypeId="ACCOUNT_STATUS"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">字典顺序:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="dictionaryRanged.order" required digits>
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
    
    var form = $('form');
    // 挂接表单结果提示钩子
    form.data('receiver-hook', function(status, closeDialog, iframe) {
    	window.lastWin.reloadPage();
    	closeDialog(status);
    });

    // 检测字段类型编号是否存在
/*     Compared.add('checkdiccode', function(val, params, ele, ansyFn) {
      Api.boss.checkDictionaryCode(val, function(result) {
      	ansyFn(Compared.toStatus(result === 'true', '字典编号不存在!'));
      });
    }); */

  </script>
</body>
</html>
