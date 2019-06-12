<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>群发消息</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 664px; padding:10px;">
  
  <form class="validator notification" id="form" action="sendAllNotice.action" method="post" prompt="DropdownMessage" data-success="操作成功" data-fail="操作失败" style="padding: 0.4em;">

	<!-- 配置信息 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">配置信息</span>
      </div>
      <div class="content fix">
        <div class="col fl w-p-50">
          <div class="fix">
            <div class="item">
              <div class="input-area">
                <span class="label w-90">商户号:</span>
                <div class="input-wrap">
                  <input class="input-text" type="text" name="customerNo" />
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">登陆号:</span>
                <div class="input-wrap">
                  <input class="input-text" type="text" name="phone" />
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">消息图标:</span>
                <div class="input-wrap">
                  <input class="input-text" type="text" name="icon" value="http://pay.feiyijj.com/files/icon/notice.png" />
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">消息标题:</span>
                <div class="input-wrap">
                  <input class="input-text" type="text" name="title" required />
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">消息类型:</span>
                <div class="input-wrap">
                  <select name="type" class="input-select">
                  	<option value="CHANGES_FUNDS">资金变动</option>
                  	<option value="PAY_ORDER">交易订单</option>
                  	<option value="MESSAGE_ADVER">通知消息</option>
                  </select>
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">失效时间:</span>
                <div class="input-wrap">
                  <input type="text" name="extTime" class="date-time">
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">内容</span>
      </div>
      <div class="content fix">
      	<input type="hidden" name="content" id="content" />
        <div class="col fl w-p-50">
          <div class="fix">
            <div class="item">
              <div class="input-area">
                <span class="label w-90">地址:</span>
                <div class="input-wrap">
                  <input class="input-text" type="text" id="c_url" required />
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">标题:</span>
                <div class="input-wrap">
                  <input class="input-text" type="text" id="c_title" required />
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">内容:</span>
                <div class="input-wrap">
                  <input class="input-text" type="text" id="c_content" required />
                </div>
              </div>
            </div>
            <div class="item">
              <div class="input-area">
                <span class="label w-90">类型:</span>
                <div class="input-wrap">
                  <input class="input-text" type="text" id="c_type" required />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">备注</span>
      </div>
      <div class="content">
        <div class="textarea-box">
          <textarea name="msgTxt" wrap="hard" cols="100" class="w-p-100" style="height: 1.8rem;"></textarea>
        </div>
      </div>
    </div>

    <div class="text-center mt-10 hidden">
      <button class="btn btn-submit">发 送</button>
    </div>

  </form>

  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
  	$("#form").submit(function(e){
  		var c_url = $("#c_url").val();
  	  	var c_title = $("#c_title").val();
  	  	var c_content = $("#c_content").val();
  	  	var c_type = $("#c_type").val();
  	  	var content = '{"url":"' + c_url + '","title":"' + c_title + '","content":"' + c_content + '","type":"' + c_type + '"}';
  	  	
  	  	$("#content").val(content);
  	  	console.log("content:",content);
	});
  </script>
</body>
</html>
