<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>微信菜单</title>
  <%@ include file="../include/header.jsp"%>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile/wxMenu.css">
</head>
<body>

  <div class="pd-10">
    <div class="wx-menu-manage">

      <!-- 左侧预览界面 -->
      <div class="preview-area">
        <div class="virtual-phone">
          <div class="phone-hd">聚合支付</div>
          <div class="phone_bd">
            <ul class="menu-list fix" id="menu-box">


              <li class="menu sub_selected" menu-type="dir" style="width: 50%;">
                <a href="javascript:void(0);">
                  <i class="fa"></i>
                  <span>歌曲</span>
                </a>
                <div class="sub_menus">
                  <ul class="sub_menus_list">
                    <li class="submenu selected">
                      <a href="javascript:void(0);">
                        <span class="inner"><span>我要升级</span></span>
                      </a>
                    </li>
                    <li class="submenu sub—placeholder">
                      <a href="javascript:void(0);">
                        <span class="inner"><i class="fa fa-plus"></i></span>
                      </a>
                    </li>
                  </ul>
                  <i class="arrow arrow_out"></i>
                  <i class="arrow arrow_in"></i>
                </div>
              </li>


              <li class="menu" menu-type="placeholder" style="width: 50%;">
                <a href="javascript:void(0);">
                  <i class="fa fa-plus"></i>
                  <span></span>
                </a>
              </li>


            </ul>
          </div>
        </div>
      </div>


      <!-- 右侧编辑界面 -->
      <div class="editor-area" id="editor-area">
        <div class="editor-inbox">
          <div class="editor-inner">
            <div class="editor-hd fix">
              <h4 class="title">编辑菜单</h4>
              <a id="btn-delMenu" href="javascript:void(0);">删除菜单</a>
            </div>
            <div class="editor-bd">

              <!--验证提示-->
              <div class="frm-error" style="display: none;">
                <p class="fai-reasonl">菜单名称长度必须在1-4个汉字</p>
                <span class="close">
                  <i class="fa fa-close"></i>
                </span>
              </div>


              <!-- NAME -->
              <div class="frm-group">
                <label class="frm-label"><strong class="title">菜单名称:</strong></label>
                <div class="frm-controls">
                  <div class="frm-input-box counter_in">
                    <input type="text" class="frm-input" id="menu-name">
                  </div>
                  <p class="frm-tips">必填且字数不超过4个汉字或8个字母</p>
                </div>
              </div>

              <!-- TYPE-->
              <div class="frm-group">
                <label class="frm-label"><strong class="title">菜单类型:</strong></label>
                <div class="frm-controls frm-select">
                  <div class="frm-input-box ">
                    <select class="frm-select" id="menu-type">
                      <option value="">目录</option>
                      <option value="click">click</option>
                      <option value="view">view</option>
                    </select>
                  </div>
                  <p class="frm-tips">暂只支持 click, view 类型</p>
                </div>
              </div>


              <!-- URL -->
              <div class="frm-group">
                <label class="frm-label"><strong class="title">菜单链接:</strong></label>
                <div class="frm-controls">
                  <div class="frm-input-box counter_in">
                    <input type="text" class="frm-input" id="menu-url">
                  </div>
                  <p class="frm-tips">必填且字数不超过128个字母</p>
                </div>
              </div>

              <!-- KEY -->
              <div class="frm-group">
                <label class="frm-label"><strong class="title">Key:</strong></label>
                <div class="frm-controls">
                  <div class="frm-input-box counter_in">
                    <input type="text" class="frm-input" id="menu-key">
                  </div>
                  <p class="frm-tips">必填且字数不超过128个字节</p>
                </div>
              </div>

              <!-- 顺序 -->
              <div class="frm-group">
                <label class="frm-label"><strong class="title">菜单顺序:</strong></label>
                <div class="frm-controls frm-select">
                  <div class="frm-input-box">
                    <select class="frm-select" id="menu-index"  ></select>
                  </div>
                </div>
              </div>

            </div>
            <div class="editor-ft">
              <button id="btn-modify" class="btn mr-10">修改</button>
              <button id="btn-save" class="btn">发布</button>
            </div>
          </div>
        </div>
        <div class="disabled-bg active"></div>
      </div>

    </div>
  </div>
  
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script src="${pageContext.request.contextPath}/js/profile/WxMenu.js"></script>
  <script>

    wxMenu.init($('#menu-box'), $('#editor-area'));
    wxMenu.bindSave(function(data) {
      $.ajax({
        url : "wxMenuSave.action",
        type : "post",
        data : {"data":JSON.stringify(data)},
        success : function(msg) {
          if (msg === true || msg === 'TRUE') {
            window.Messages.success('发布成功!');
          } else {
            window.Messages.error('发布失败!');
          }
          window.location.reload();
        },
        error : function(){
          console.log('wxMenuSave.action ajax 错误');
        }
      });
    });
    
    $.ajax({
      url : "wxMenu.action",
      type : "post",
      dataType: 'json',
      success : function(data) {
        console.log(data);
        var menuData = JSON.parse(data);
        wxMenu.data(menuData);
      },
      error : function(){
        console.log('wxMenu.action ajax 错误');
      }
    });
  
  </script>
</body>
</html>
