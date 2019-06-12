<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html>
<head>
<title>系统公告</title>
</head>
<body>
  <div class="bulletinList">
    <div class="list">
      <ul>
        <c:forEach items="${bulletinBeanList }" var="bulletin">
          <li>
            <span class="circle"></span>
            <a data-myiframe='{
                      "target": "bulletinDetail.action?bulletin.id=${bulletin.id}",
                      "autoTitle": false,
                      "isModal": false,
                      "fixed": true,
                      "btns": [
                        {"lable": "确定"}
                      ]
                    }' href="javascript:void(0);">
              <span class="text">${bulletin.title}</span>
              <span class="plus">[<fmt:formatDate value="${bulletin.effTime }" pattern="yyyy-MM-dd" />]</span>
            </a>
          </li>
        </c:forEach>
      </ul>
    </div>
    <div style="margin-top: 20px;" class="jump-rarp mt-10 text-center" id="bulletinList" total="${page.totalPage}" current="${page.currentPage }"></div>
  </div>
	<%@ include file="../include/bodyLink.jsp"%>
  <script>
    // 公告列表特例分页
    var bulletinList = $('#bulletinList');
    var totalPage = parseInt(bulletinList.attr('total').replace(/,/g, ''));
    var current = parseInt(bulletinList.attr('current').replace(/,/g, ''));
    bulletinList.page({
      // 总条数
      total: 0,
      // 总页数
      totalPage: totalPage,
      // 当前页数
      current: current,
      // 是否渲染总条数
      isTotal: false,
      // 是否渲染跳转编辑框
      isJumpInput: false,
      // 是否渲染首页尾页
      isFAL: true,
      // 跳转钩子
      jumpHook: function(to, from) {
        location.href = 'bulletinShow.action?currentPage=' + to;
      }
    });
  </script>
</body>
</html>
