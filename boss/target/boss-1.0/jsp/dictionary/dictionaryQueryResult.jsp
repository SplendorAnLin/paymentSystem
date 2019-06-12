<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>字典【${dictionaryTypeRanged.name}】管理</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 1000px; padding: 10px;">

  <div class="title-h1 fix">
    <span class="primary fl">字典列表</span>
  </div>
  <c:if test="${dictionaryRangedList != null }">
    <div class="table-warp">
      <div class="table-sroll">
        <table class="data-table shadow--2dp  w-p-100 tow-color">
          <thead>
            <tr>
              <th>字典key</th>
              <th>字典value</th>
              <th>状态</th>
              <td>字典类型编号</td>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${dictionaryRangedList }" var="dr" varStatus="status">
              <tr>
                <td>${fn:split(dr.key,".")[1]}</td>
                <td>${dr.value }</td>
                <td><c:choose><c:when test="${dr.status != null }"><dict:write dictId="${dr.status }" dictTypeId="STATUS_COLOR"></dict:write></c:when></c:choose></td>
                <td>${dr.order }</td>
                <td>
                  <button onclick="dictionaryEdit('${dictionaryTypeRanged.code }', '${dr.key }','updateDictionary')" class="btn-small">修改</button>
                  <button onclick="delDictionary('${dictionaryTypeRanged.code }', '${dr.key }', 'delDictionary')"class="btn-small">删除</button>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    <%-- <div class="jump-rarp mt-20 text-center" id="PageWrap" total_count="${page.totalResult}" total="${page.totalPage}"  current="${page.currentPage}"></div> --%>
  </c:if>
  <c:if test="${dictionaryRangedList == null }">
    <p class="pd-10">查询结果不存在！</p>
  </c:if>

  <div class="text-center mt-10 hidden">
       <button onclick="dictionaryAdd('${fn:split(dictionaryTypeRanged.code,".")[1]}')" class="btn-add">添加</button>
  </div>
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    function delDictionary(code, key, type) {
      AjaxConfirm('是否删除该字典?', '请确认', {
        url: '${pageContext.request.contextPath}/dictionaryRangedByKeyCrud.action?dictionaryTypeRanged.code=' + code + '&dictionaryRanged.key=' + key + '&dictionaryType=' + type,
        type: 'post',
        success: function(msg) {
          Messages.success('删除成功!');
          window.reloadPage();
        },
        error: function() {
          Messages.error('网络异常, 删除失败, 请稍后重试!');
        }
      }, true);
    }
    
    window.reloadPage = function() {
    	window.location.href = '${pageContext.request.contextPath}/dictionaryQuery.action?dictionaryTypeRanged.code=${fn:split(dictionaryTypeRanged.code,".")[1]}';
    };
    
    
    function dictionaryAdd(dictionaryType) {
  	 var dialog = new window.top.MyIframe({
  		target: "${pageContext.request.contextPath}/jsp/dictionary/dictionaryAdd.jsp?dictionaryType=" + dictionaryType,
      btns: [
               {lable: "取消"},
               {lable: "新增", "trigger": ".btn-submit"}
            ],
      onOpen: function(self, content) {
    	  self.iframe[0].contentWindow.lastWin = window;
      }
  	 });
    }
    
    
    function dictionaryEdit(code, key,type) {
        var dialog = new window.top.MyIframe({
         target: "${pageContext.request.contextPath}/dictionaryRangedByKeyCrud.action?dictionaryTypeRanged.code=" + code + "&dictionaryRanged.key=" + key + "&dictionaryType=queryDictionary",
         btns: [
                  {lable: "取消"},
                  {lable: "修改", "trigger": ".btn-submit"}
               ],
         onOpen: function(self, content) {
        	 self.iframe[0].contentWindow.lastWin = window;
         }
        });
       }


    
  </script>
</body>
</html>
