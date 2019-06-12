<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>新增文档</title>
  <%@ include file="../include/header.jsp"%>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile/wechat.css">
</head>
<body style="width: 1400px;  padding: 10px;">
  
  <form class="validator ajaxFormNotification" action="protocolManagementAdd.action" method="post" prompt="DropdownMessage"  data-success="新增成功" data-fail="新增失败">

    <!-- 基本信息 -->
    <div class="row">
      <div class="title-h1 fix tabSwitch2">
        <span class="primary fl">基本信息</span>
      </div>
      <div class="content">
        <div class="fix">
          <div class="col fl w-p-50">
            <div class="fix">
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">标题:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text" name="protocolManagement.title" required>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">状态:</span>
                  <div class="input-wrap">
                    <dict:select styleClass="input-select" name="protocolManagement.status" dictTypeId="ALL_STATUS"></dict:select>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col fl w-p-50">
            <div class="fix">
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">文档类型:</span>
                  <div class="input-wrap">
                    <dict:select styleClass="input-select" name="protocolManagement.type" onchange="changeDocType(this.value);" dictTypeId="PROTOCOL_MANAGEMENT"></dict:select>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-100">所属分类:</span>
                  <div class="input-wrap">
                    <!--隐藏下拉列表-->
                    <dict:select diy=" dataType=\"PACT\"" style="display:none;" dictTypeId="PROTOCOL_MANAGEMENT_PACT"></dict:select>
                    <dict:select diy=" dataType=\"HELP\""  style="display:none;" dictTypeId="PROTOCOL_MANAGEMENT_HELP"></dict:select>
                    <!--实际下拉列表-->
                    <select class="input-select" name="protocolManagement.sort" id="target" required>
                    </select>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 公告内容 -->
  <div class="hidden" style="padding: 0 10px;">
    <textarea class="w-p-100" name="protocolManagement.content" id="myEditor"  style="min-height: 400px;"></textarea>
  </div>
    
    <div class="text-center mt-10 hidden">
      <button class="btn btn-submit">新 增</button>
    </div>

  </form>
  
  <div class="row">
    <div class="title-h1 fix tabSwitch2">
      <span class="primary fl">文档内容</span>
    </div>
    <dic class="content">
    
      <div class="edit">
        <div class="edit-header fix">
          <ul class="color-list fl" style="margin-top: 5px;">
            <li class="bgcrev trans"></li>
            <li class="trans coloritem" style="background: #c53f46" color="#c53f46"></li>
            <li class="trans coloritem" style="background: #ffcb15" color="#ffcb15"></li>
            <li class="trans coloritem" style="background: #ffa921" color="#ffa921"></li>
            <li class="trans coloritem" style="background: #fde800" color="#fde800"></li>
            <li class="trans coloritem" style="background: #87c943" color="#87c943"></li>
            <li class="trans coloritem" style="background: #129527" color="#129527"></li>
            <li class="trans coloritem" style="background: #00589c" color="#00589c"></li>
            <li class="trans coloritem" style="background: #009e96" color="#009e96"></li>
            <li class="trans coloritem" style="background: #aa89bd" color="#aa89bd"></li>
            <li class="more">
              <div id="colorPicker">
                <strong>更多</strong>
              </div>
            </li>
          </ul>
    
          <div class="fr">
            <a href="javascript:void(0);" class="btn-white previous">预览</a>
          </div>
    
    
        </div>
    
        <div class="edit-body">
          <div class="material-library h-700 ib fix">
            <div class="fl">
              <ul class="itemTab-list">
                <li data-type="title" class="curt"><a href="javascript:">标题</a></li>
                <li data-type="content"><a href="javascript:">内容</a></li>
                <li data-type="gala"><a href="javascript:">节日</a></li>
                <li data-type="image"><a href="javascript:">图片</a></li>
                <li data-type="puzzle"><a href="javascript:">拼图</a></li>
                <li data-type="follow"><a href="javascript:">关注/原文</a></li>
                <li data-type="dividing-line"><a href="javascript:">分割线</a></li>
                <li data-type="spread"><a href="javascript:">互推</a></li>
                <li data-type="chother"><a href="javascript:">其他</a></li>
              </ul>
            </div>
            <div class="right-side h-700">
              <div class="content2 curt" data-type="title">
                <div id="title_001" class="item">
                  <section style="margin:1em auto;padding: 0.5em 0;white-space: normal;border: none;border-style: none;text-align: center;width: 100%;"><span style="width: 100%; border-bottom: 1px solid rgb(40, 255, 107); border-top-color: rgb(40, 255, 107); border-right-color: rgb(40, 255, 107); border-left-color: rgb(40, 255, 107); display: inline-block;"
                      class="bc"><section class="bc" style="min-height: 32px; color: rgb(0, 0, 0); display: inline-block; border-bottom: 5px solid rgb(40, 255, 107); border-top-color: rgb(40, 255, 107); border-right-color: rgb(40, 255, 107); border-left-color: rgb(40, 255, 107); padding: 5px 10px 0px; margin-bottom: -3px; font-size: 20px;"><p style="margin-bottom: 5px;">大标题</p></section></span></section>
                </div>
                <div id="title_003" class="item">
                  <section style="border:0;text-align: left; margin: 0.8em 0 0.5em 0;border-style: none;">
                    <section style="font-size: 1.4em; font-family: inherit; font-style: normal;font-weight: inherit; text-align: inherit; text-decoration: inherit;color: rgb(102, 102, 102); background-color: transparent;">
                    <span style="font-size: 3em; line-height: 1em; font-weight: bolder; vertical-align: middle; color: rgb(40, 255, 107);"
                        class="fc">“</span><span style="line-height: 1.2; vertical-align: middle; font-size: 1.4em;font-family: inherit; font-style: normal; font-weight: inherit;text-align: inherit; text-decoration: inherit; color: rgb(42, 52, 58);">标题</span>
                      <span
                        class="fc" style="font-size: 3em; line-height: 1em; font-weight: bolder; vertical-align: middle; color: rgb(40, 255, 107);">”</span>
                    </section>
                  </section>
                </div>
                <div id="title_006" class="item" style="display: block;">
                  <section style="font-family:微软雅黑,Microsoft YaHei; white-space: normal; max-width: 100%; box-sizing: border-box; color: rgb(255, 57, 31); line-height: 25.6px; word-wrap: break-word !important; background-color: rgb(255, 255, 255);">
                    <section style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;">
                      <section style="margin-top: 10px; margin-bottom: 10px; max-width: 100%; box-sizing: border-box; text-align: center; word-wrap: break-word !important;">
                        <section class="bc" style="max-width: 100%; box-sizing: border-box; border: 3px dotted rgb(40, 255, 107); border-radius: 100%; display: inline-block; word-wrap: break-word !important;">
                          <section style="margin-right: auto; margin-left: auto; max-width: 100%; box-sizing: border-box; width: 1.6em; height: 1.6em; line-height: 1.6em; border-radius: 100%; font-size: 21px; word-wrap: break-word !important;">
                            <section class="fc" style="max-width: 100%; box-sizing: border-box; word-wrap: break-word !important; color: rgb(40, 255, 107);">1</section>
                          </section>
                        </section>
                      </section>
                    </section>
                  </section>
                </div>
                <div id="title_007" class="item" style="display: block;">
                  <section style="font-size:14px;font-family: 微软雅黑,Microsoft YaHei;margin: 5px auto;white-space: normal;">
                    <section style="position:static;box-sizing: border-box;">
                      <section style="margin: 0px auto; text-align: left; font-size: 16px; position: static;">
                        <section style="display: inline-block;">
                          <section style="margin-right: auto; margin-bottom: 4px; margin-left: auto; text-align: right; line-height: 0;"><span style="width: 0.3em; height: 0.3em; margin-right: 2px; display: inline-block; vertical-align: bottom; background-color: rgb(40, 255, 107);"
                              class="bgc"></span><span style="width: 0.6em; height: 0.6em; margin-left: 2px; display: inline-block; vertical-align: bottom; background-color: rgb(40, 255, 107);"
                              class="bgc"></span></section>
                          <section style="text-align: left; margin: auto; line-height: 0;"><span style="min-height: 1.2em; margin-right: 2px; line-height: 1.2em; display: inline-block; vertical-align: top; color: rgb(255, 255, 255); text-align: center; background-color: rgb(40, 255, 107); padding: 5px 10px;"
                              class="bgc"><section><span style="text-align: center;">小标题</span></section>
                          </span><span style="width: 0.6em; height: 0.6em; margin-left: 2px; display: inline-block; vertical-align: top; background-color: rgb(40, 255, 107);"
                            class="bgc"></span></section>
                      </section>
                    </section>
                  </section>
                  </section>
                </div>
                <div id="title_008" class="item" style="display: block;">
                  <section style="font-size:14px;font-family:Microsoft YaHei;margin: 5px auto;white-space: normal;">
                    <section style="margin:0 auto;width:68%">
                      <section style="margin:0;clear:both;box-sizing:border-box;padding:0;color:inherit">
                        <section style="color: inherit; float: right; width: 11px; background-color: rgb(40, 255, 107); height: 11px !important;"
                          class="bgc"></section>
                        <section style="color: inherit; float: left; width: 11px; background-color: rgb(40, 255, 107); height: 11px !important;"
                          class="bgc"></section>
                      </section>
                      <section style="margin: 0px; padding: 10px 0px 0px; color: inherit;">
                        <section style="color: rgb(192, 0, 0); float: right; width: 12px; margin-right: -1px; background-color: rgb(40, 255, 107); height: 11px !important;"
                          class="bgc"></section>
                        <section style="color: rgb(192, 0, 0); float: left; width: 12px; margin-left: -1px; background-color: rgb(40, 255, 107); height: 11px !important;"
                          class="bgc"></section>
                      </section>
                      <section style="margin: 0px; padding: 11px 0px 0px; color: inherit;">
                        <section style="color: inherit; float: right; width: 12px; margin-right: -1px; margin-top: -1px; background-color: rgb(40, 255, 107); height: 11px !important;"
                          class="bgc"></section>
                        <section style="color: inherit; float: left; width: 12px; margin-left: -1px; margin-top: -1px; background-color: rgb(40, 255, 107); height: 11px !important;"
                          class="bgc"></section>
                      </section>
                    </section>
                    <section style="margin-top:-1.2em;text-align:center;padding-left:20px;padding-right:20px;font-size:16px;font-weight:bold">这里输入标题</section>
                  </section>
                </div>
                <div id="title_009" class="item" style="display: block;">
                  <section style="border: 0px none; box-sizing: border-box; ">
                    <p style="color: rgb(62, 62, 62); line-height: 1.6; font-size: 11px; text-align: center; white-space: normal;"><span style="color: #FCCB87;"><strong>◆</strong></span><strong><span style="color: #E81E9B;"> </span><span style="color: #DBE6A2;">◆</span><span style="color: #1E9BE8;"> </span><span style="color: #88BFB1;">◆</span></strong></p>
                  </section>
                </div>
                <div id="title_010" class="item" style="display: block;">
                  <section style="font-size:14px;font-family:Microsoft YaHei;margin: 5px auto;white-space: normal;">
                    <section style="border:0 none;padding:0;box-sizing:border-box;margin:10px 0;font-size:15px;font-family:微软雅黑">
                      <section style="margin-top: 0px; margin-bottom: 0px; padding: 10px; max-width: 100%; line-height: 2em; word-wrap: break-word; word-break: normal; text-align: center; background-color: rgb(40, 255, 107); box-sizing: border-box;"
                        class="bgc"><span style="color:#fff">请输入标题</span></section>
                      <p style="margin-top:0;white-space:normal"><img src="http://tool.chinaz.com/template/default/temp/title/img/000.png" style="height:auto!important;width:100%"></p>
                      <section
                        style="width:0;height:0;clear:both;box-sizing:border-box;padding:0;margin:0"></section>
                  </section>
                  </section>
                </div>
                <div id="title_011" class="item" style="display: block;">
                  <section style="border: 0px; box-sizing: border-box; width: 100%; clear: both; margin-top: 0.8em; margin-bottom: 0.5em;">
                    <section style="margin-right: auto; margin-left: auto;"><img src="http://tool.chinaz.com/template/default/temp/title/img/001.gif" style="width: 10em; height: 10em; border-top-left-radius: 50%; border-top-right-radius: 50%; border-bottom-right-radius: 50%; border-bottom-left-radius: 50%; "></section>
                    <section
                      style="width: 0px; height: 0px; clear: both;"></section>
                  </section>
                  <p style="text-align: center;"> <span style="font-family:微软雅黑, Microsoft YaHei"><span style="font-size: 18px;">好好学习，天天向上。</span></span>
                  </p>
                </div>
                <div id="title_012" class="item" style="display: block;">
                  <section style="box-sizing: border-box; border: 0px none; ">
                    <section style=" margin: 0.5em;box-sizing: border-box;">
                      <section style=" display: inline-block; margin-left: -0.2em; box-sizing: border-box;-webkit-transform: skew(-45deg); ">
                        <section style="border-right: 2px solid rgb(40, 255, 107); border-bottom: 1px solid rgb(40, 255, 107); padding: 5px 15px 5px 5px; box-sizing: border-box; border-top-color: rgb(40, 255, 107); border-left-color: rgb(40, 255, 107);"
                          class="bc">
                          <section style=" text-align: right; box-sizing: border-box;-webkit-transform: skew(45deg);">
                            <p style="color: rgb(40, 255, 107); font-size: 20px; white-space: normal;" class="fc"><span style="font-size: 18px;">F</span></p>
                          </section>
                        </section>
                      </section>
                      <section style=" display: inline-block;-webkit-transform: skew(-45deg); box-sizing: border-box; ">
                        <section style="border-left: 2px solid rgb(40, 255, 107); border-top: 1px solid rgb(40, 255, 107); padding: 5px 5px 5px 15px; box-sizing: border-box; border-right-color: rgb(40, 255, 107); border-bottom-color: rgb(40, 255, 107);"
                          class="bc">
                          <section style="-webkit-transform: skew(45deg); box-sizing: border-box;">
                            <p style="color: rgb(40, 255, 107); font-size: 20px; white-space: normal;" class="fc"><span style="text-align: right; font-size: 18px;">标题标题</span></p>
                          </section>
                        </section>
                      </section>
                    </section>
                    <section style="width: 0px; height: 0px; clear: both; box-sizing: border-box;"></section>
                  </section>
                </div>
                <div id="title_013" class="item" style="display: block;">
                  <section style="box-sizing: border-box; border: 0px none; ">
                    <section style="margin-top: 0.8em; margin-bottom: 0.5em; line-height: 32px; font-weight: bold; box-sizing: border-box;">
                      <section style="display: inline-block; float: left; min-width: 32px; height: 32px; vertical-align: top; text-align: center; box-sizing: border-box;">
                        <section style="display: table; width: 100%; color: inherit; border-color: rgb(72, 192, 163); box-sizing: border-box;">
                          <section style="display: table-cell; vertical-align: middle; color: rgb(255, 255, 255); border-color: rgb(72, 192, 163); box-sizing: border-box;"><span style="color:#C6C6C7; font-size:50px">2</span></section>
                          <section style="width: 18px; height: 70px;margin-left: -6px; margin-top:5px;border-left:1px solid rgb(198,198,199);background-color: rgb(254,254,254); box-sizing: border-box;transform: rotate(35deg);-webkit-transform: rotate(35deg);-moz-transform: rotate(35deg);-ms-transform: rotate(35deg);-o-transform: rotate(35deg);"></section>
                        </section>
                      </section>
                      <section style="margin-left: 40px; padding-top: 18px; font-size: 30px; color: rgb(115, 115, 115); border-color: rgb(72, 192, 163); box-sizing: border-box;">&nbsp;&nbsp;<span style="font-size: 20px; font-family: 微软雅黑,Microsoft YaHei;">标题一</span></section>
                    </section>
                    <section style="width: 0px; height: 0px; clear: both; box-sizing: border-box;"></section>
                  </section>
                </div>
                <div id="title_014" class="item" style="display: block;">
                  <section style="box-sizing: border-box; border: 0px none;">
                    <section style="width: 100%; box-sizing: border-box;">
                      <section style="width: 25%; float: left; box-sizing: border-box;">
                        <section style="opacity: 0.8; margin-top: 5px; border-bottom-width: 10px; border-bottom-style: solid; border-bottom-color: rgb(89, 195, 249); border-top-color: rgb(89, 195, 249); box-sizing: border-box; color: inherit; float: left; border-left-width: 6px !important; border-left-style: solid !important; border-left-color: transparent !important; border-right-width: 6px !important; border-right-style: solid !important; border-right-color: transparent !important;"></section>
                        <section style="opacity: 0.4; border-right-width: 10px; border-left-width: 0px; border-right-style: solid; border-right-color: rgb(89, 195, 249); border-left-color: rgb(89, 195, 249); display: inline-block; float: left; color: inherit; margin-top: 5px; margin-left: 10px; margin-right: 5px; border-bottom-width: 6px !important; border-top-width: 6px !important; border-top-style: solid !important; border-bottom-style: solid !important; border-top-color: transparent !important; border-bottom-color: transparent !important;transform: rotate(10deg);-webkit-transform: rotate(10deg);-moz-transform: rotate(10deg);-ms-transform: rotate(10deg);-o-transform: rotate(10deg);"></section>
                        <section style="border-left-width: 20px; border-right-width: 0px; border-left-style: solid; border-left-color: rgb(89, 195, 249); border-right-color: rgb(89, 195, 249); display: inline-block; float: left; color: inherit; border-bottom-width: 10px !important; border-top-width: 15px !important; border-top-style: solid !important; border-bottom-style: solid !important; border-top-color: transparent !important; border-bottom-color: transparent !important;transform: rotate(10deg);-webkit-transform: rotate(10deg);-moz-transform: rotate(10deg);-ms-transform: rotate(10deg);-o-transform: rotate(10deg);"></section>
                      </section>
                      <section style="width: 50%; text-align: center; float: left; padding-right: 5px; padding-left: 5px; box-sizing: border-box;">
                        <section style="display: inline-block; box-sizing: border-box;"><span style="font-size:18px;font-family: 微软雅黑,Microsoft YaHei;"><strong>这里输入标题</strong></span></section>
                      </section>
                      <section style="float: right; width: 25%; text-align: right; box-sizing: border-box;">
                        <section style="display: inline-block; box-sizing: border-box;">
                          <section style="opacity: 0.8; margin-top: 5px; border-bottom-width: 10px; border-bottom-style: solid; border-bottom-color: rgb(89, 195, 249); border-top-color: rgb(89, 195, 249); box-sizing: border-box; color: inherit; float: left; border-left-width: 6px !important; border-left-style: solid !important; border-left-color: transparent !important; border-right-width: 6px !important; border-right-style: solid !important; border-right-color: transparent !important;"></section>
                          <section style="opacity: 0.4; border-right-width: 10px; border-left-width: 0px; border-right-style: solid; border-right-color: rgb(89, 195, 249); border-left-color: rgb(89, 195, 249); display: inline-block; float: left; color: inherit; margin-top: 5px; margin-left: 10px; margin-right: 5px; border-bottom-width: 6px !important; border-top-width: 6px !important; border-top-style: solid !important; border-bottom-style: solid !important; border-top-color: transparent !important; border-bottom-color: transparent !important;transform: rotate(10deg);-webkit-transform: rotate(10deg);-moz-transform: rotate(10deg);-ms-transform: rotate(10deg);-o-transform: rotate(10deg);"></section>
                          <section style="border-left-width: 20px; border-right-width: 0px; border-left-style: solid; border-left-color: rgb(89, 195, 249); border-right-color: rgb(89, 195, 249); display: inline-block; float: left; color: inherit; border-bottom-width: 10px !important; border-top-width: 15px !important; border-top-style: solid !important; border-bottom-style: solid !important; border-top-color: transparent !important; border-bottom-color: transparent !important;transform: rotate(10deg);-webkit-transform: rotate(10deg);-moz-transform: rotate(10deg);-ms-transform: rotate(10deg);-o-transform: rotate(10deg);"></section>
                        </section>
                      </section>
                    </section>
                    <section style="width: 0px; height: 0px; clear: both; box-sizing: border-box;"></section>
                  </section>
                </div>
                <div id="title_015" class="item" style="display: block;">
                  <section style="border: 0px none; box-sizing: border-box; ">
                    <section style="margin-right: auto; margin-left: auto; width: 68%; box-sizing: border-box;">
                      <section style="clear: both; color: inherit; box-sizing: border-box;">
                        <section style="color: inherit; float: right; width: 11px; box-sizing: border-box; height: 11px !important; background-color: rgb(89, 195, 249);"></section>
                        <section style="color: inherit; float: left; width: 11px; box-sizing: border-box; height: 11px !important; background-color: rgb(89, 195, 249);"></section>
                      </section>
                      <section style="padding-top: 10px; color: inherit; border-color: rgb(89, 195, 249); box-sizing: border-box;">
                        <section style="color: rgb(0, 106, 160); float: right; width: 12px; margin-right: -1px; box-sizing: border-box; height: 11px !important; background-color: rgb(163, 222, 252);"></section>
                        <section style="color: rgb(0, 106, 160); float: left; width: 12px; margin-left: -1px; box-sizing: border-box; height: 11px !important; background-color: rgb(163, 222, 252);"></section>
                      </section>
                      <section style="padding-top: 11px; color: inherit; border-color: rgb(89, 195, 249); box-sizing: border-box;">
                        <section style="color: inherit; float: right; width: 12px; margin-right: -1px; margin-top: -1px; box-sizing: border-box; height: 11px !important; background-color: rgb(89, 195, 249);"></section>
                        <section style="color: inherit; float: left; width: 12px; margin-left: -1px; margin-top: -1px; box-sizing: border-box; height: 11px !important; background-color: rgb(89, 195, 249);"></section>
                      </section>
                    </section>
                    <section style="margin-top: -1em; text-align: center; padding-left: 20px; padding-right: 20px; box-sizing: border-box;"><strong>100% JUNG</strong></section>
                    <section style="width: 0px; height: 0px; clear: both; box-sizing: border-box;"></section>
                  </section>
                </div>
    
    
    
    
    
    
    
              </div>
    
              <div class="content2" data-type="content">
                <div id="content_001" class="item" style="display: block;">
                  <section style="font-family:微软雅黑,Microsoft YaHei; white-space: normal; margin: 0.8em 1em; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); line-height: 25.6px; border: 0px; word-wrap: break-word !important;">
                    <section class="bc" style="padding: 10px; max-width: 100%; box-sizing: border-box; word-wrap: break-word; border: 1px solid rgb(0, 187, 236); line-height: 1.4; font-family: inherit; text-decoration: inherit; color: rgb(51, 51, 51);">
                      <p style="max-width: 100%; min-height: 1em; white-space: pre-wrap; box-sizing: border-box !important; word-wrap: break-word !important;"><span style="line-height: 19.6px; font-family: 微软雅黑,Microsoft YaHei;">你是否在处理很多事情的时候赢了道理，输了感情？</span></p>
                    </section>
                  </section>
                </div>
                <div id="content_002" class="item" style="display: block;">
                  <section>
                    <blockquote class="bgc bc" style="margin: 0px; padding: 5px 15px; border-radius: 5px 5px 0px 0px; border: 0px currentColor; text-align: left; color: rgb(255, 255, 255); line-height: 25px; font-weight: bold; max-width: 100%; background-color: rgb(255, 142, 0);">这输入标题</blockquote>
                    <blockquote class="bc" style="margin: 0px; padding: 10px 15px 20px; border-radius: 0px 0px 5px 5px; border: 1px solid rgb(255, 142, 0); line-height: 25px; max-width: 100%;">
                      <p style="text-indent: 2em;">人生最可悲的并非失去四肢，而是没有生存希望及目标！人们经常埋怨什么也做不来，但如果我们只记挂着想拥有或欠缺的东西，而不去珍惜所拥有的，那根本改变不了问题！真正改变命运的，并不是我们的机遇，而是我们的态度。</p>
                      <p style="text-align: right;">——尼克·胡哲</p>
                    </blockquote>
                  </section>
                </div>
                <div id="content_003" class="item" style="display: block;">
                  <section style="position:static;box-sizing: border-box;">
                    <section style="border: 1px solid #e2e2e2; box-shadow: #e2e2e2 0em 1em 0.1em -0.6em;line-height: 1.6em;">
                      <section style="padding: 1em; color:rgb(255, 255, 255);text-align: center; font-size: 1.4em; font-weight: bold; line-height: 1.4em; box-shadow: 0em 0.2em 0.2em rgb(221, 221, 221); background-color: rgb(58, 188, 255);"
                        class="bgc"><span style="font-size:1.4em; font-style:normal;font-family: 微软雅黑,Microsoft YaHei;">文艺展示活动</span></section>
                      <section style="margin-top: 1.5em; text-align: left;font-family: 微软雅黑,Microsoft YaHei;"><img src="http://tool.chinaz.com/template/default/temp/content/img/000.png" style="margin-left:1em; vertical-align:top; width:30px">
                        <section style="display: inline-block; width: 75%; padding: 0.2em; margin-left: 0.5em; font-size: 1em; font-style: normal; color: inherit;">活动时间：2016年01月01日</section>
                      </section>
                      <section style="margin-top: 1em; text-align: left;font-family: 微软雅黑,Microsoft YaHei;"><img src="http://tool.chinaz.com/template/default/temp/content/img/001.png" style="margin-left:1em; vertical-align:top; width:30px">
                        <section style="display: inline-block; width: 75%; padding: 0.2em; margin-left: 0.5em; font-size: 1em; color: inherit;">活动地点：市歌剧院</section>
                      </section>
                      <section style="display: inline-block;background-color: rgb(58, 188, 255); height: 2em; max-width: 100%;margin-top: 1.5em; line-height: 1.4em; box-sizing: border-box;"
                        class="bgc">
                        <section style="height: 2em; max-width: 100%; padding: 0.5em 1em;color: rgb(255, 255, 255); white-space: nowrap; text-overflow: ellipsis; font-size: 1em;font-family: 微软雅黑,Microsoft YaHei;">活动要求</section>
                      </section>
                      <p style="padding: 1em; line-height: 1.4em; font-size: 1em; color: inherit; text-indent: 2em;font-family: 微软雅黑,Microsoft YaHei;">为全面贯彻落实党的十八大、十八届三中全会精神和习近平书记在文艺工作座谈会上的讲话精神，大力践行党的群众路线教育实践活动，丰富群众精神文化生活，促进民间文艺团队的发展和提高，推进群众文化艺术大发展大繁荣。开展“十佳民间文艺团队”文艺展示。</p>
                    </section>
                    <section style="display: block; width: 0; height: 0; clear: both;"></section>
                  </section>
                </div>
                <div id="content_004" class="item" style="display: block;">
                  <section style="font-family:微软雅黑,Microsoft YaHei; margin: 5px auto; white-space: normal;">
                    <section style="border: 0px none; border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; padding: 0px; box-sizing: border-box; margin: 0px; font-family: 微软雅黑,Microsoft YaHei;">
                      <section style="margin: 0.5em 0px 1em; padding: 0px; box-sizing: border-box; min-width: 0px; word-wrap: break-word !important;">
                        <section style="font-size: 14px; color: inherit; text-align: right; box-sizing: border-box; padding: 0px; margin: 0px;">
                          <section style="margin-right:12px;padding:0;box-sizing:border-box;display:inline-block;vertical-align:top;height:5em;width:5em;border:5px solid #f5fefe;font-family:inherit;font-weight:inherit;text-decoration:inherit;font-size:1.6em;color:inherit;border-top-left-radius:4px;border-top-right-radius:4px;border-bottom-right-radius:4px;border-bottom-left-radius:4px;box-shadow:#c6c6c6 0 2px 5px;word-wrap:break-word!important">
                          <img src="http://tool.chinaz.com/template/default/temp/content/img/g001.png" style="box-sizing:border-box;color:inherit;display:inline-block"
                              width="100%" height="100%"> </section>
                        </section>
                        <section style="margin: -6.5em 0px 0px; padding: 10px 50% 10px 15px; border-radius: 4px; font-weight: inherit; text-decoration: inherit; box-sizing: border-box; overflow: hidden; color: rgb(255, 255, 255); background-color: rgb(89, 195, 249);"
                          class="bgc">
                          <p style="font-size: 14px;white-space: normal; line-height: 2em;"> <span style="font-size: 20px;">宠物猫咪</span> </p>
                          <p style="font-size: 14px;white-space: normal; line-height: 2em;">
                          猫在高兴时，尾巴会竖起来，或者发出呼噜呼噜的声音。猫生气的时候，会使劲地摇尾巴。 </p>
                        </section>
                      </section>
                    </section>
                  </section>
                </div>
                <div id="content_005" class="item" style="display: block;">
                  <section style="position:static;box-sizing: border-box;">
                    <section style="font-size: 14px; line-height: 22.39px;margin: 10px 0px; padding:15px 20px 15px 45px; outline: 0px; border: 0px currentcolor; color: rgb(62, 62, 62); vertical-align: baseline; background-image:url(http://tool.chinaz.com/template/default/temp/content/img/006.jpg); background-color: rgb(241, 241, 241); background-position: 1% 5px; background-repeat: no-repeat no-repeat;">
                      <p>当那些睡在绒毛上面的人所做的梦，并不比睡在土地上的人的梦更美好的时候，我怎能对生命的公平失掉信心呢？《沙与沫》——纪伯伦</p>
                    </section>
                    <section style="display: block; width: 0; height: 0; clear: both;"></section>
                  </section>
                </div>
                <div id="content_006" class="item" style="display: block;">
                  <section style="font-size:14px;font-family:微软雅黑,Microsoft YaHei;margin: 5px auto;white-space: normal;">
                    <section style="border:0 none;padding:0;box-sizing:border-box;margin:0;font-size:16px;font-family:微软雅黑,Microsoft YaHei">
                      <section style="border:0;box-sizing:border-box;width:100%;clear:both;padding:0;margin:0">
                        <section style="box-sizing:border-box;float:left;padding:0 .1em 0 0;color:inherit;margin:0">
                          <section style="width:5em;height:5em;text-align:center;padding:12px 5px;color:#fff;border-color:#00bbec;background-color:#00bbec;box-sizing:border-box;margin:0"
                            class="bc bgc">
                            <p style="color:inherit;white-space:normal">2015年</p>
                            <p style="color:inherit;white-space:normal"><span style="color:inherit;line-height:1.6em">7月10日</span></p>
                          </section>
                        </section>
                        <section style="display:inline-block;width:65%;box-sizing:border-box;float:left;padding:0 .5em;color:inherit;margin:0">
                          <section style="border-bottom-width:2px;border-bottom-style:solid;border-bottom-color:#00bbec;padding:5px;margin:5px 0 0;color:inherit;box-sizing:border-box"
                            class="bc">
                            <p style="margin-right:.5px;color:#00bbec;border-color:#00bbec;white-space:normal" class="fc bc"><span style="border-color:#00bbec;color:#000;font-family:微软雅黑,Microsoft YaHei;font-size:18px" class="bc">主标题</span></p>
                          </section>
                          <p style="padding:5px;color:#00bbec;line-height:1.5em;border-color:#00bbec;white-space:normal" class="bc"><span style="color:#000;font-family:微软雅黑,Microsoft YaHei;font-size:15px">副标题</span></p>
                        </section>
                      </section>
                      <section style="width:0;height:0;clear:both;box-sizing:border-box;padding:0;margin:0"></section>
                    </section>
                  </section>
                </div>
                <div id="content_007" class="item" style="display: block;">
                  <section>
                    <blockquote style="margin: 0px; padding: 15px; border-radius: 5px; border: 1px solid rgb(0, 187, 236);" class="bc">
                      <section style="border: 0px none; padding: 0px; box-sizing: border-box; margin: 0px; font-family:微软雅黑,Microsoft YaHei;">
                        <section style="width: 5em; height: 5em; border-top-left-radius: 50%; border-top-right-radius: 50%; border-bottom-right-radius: 50%; border-bottom-left-radius: 50%; float: left; padding: 12px 5px; margin-right: 10px; border-color: rgb(0, 187, 236); color: rgb(255, 255, 255); background-color: rgb(0, 187, 236); box-sizing: border-box;"
                          class="bc bgc">
                          <p style="line-height: normal; text-align: center; border-color: rgb(89, 195, 249); color: inherit; white-space: normal; margin-bottom: 5px; margin-top: 5px;">
                          <span style="border-color: rgb(89, 195, 249); color: inherit;">沙与沫</span> </p>
                          <p style="line-height: normal; text-align: center; border-color: rgb(89, 195, 249); color: inherit; white-space: normal; margin-bottom: 5px; margin-top: 5px;">
                          <span style="border-color:rgb(89, 195, 249); color:inherit; font-size:14px">纪伯伦</span> </p>
                        </section>
                        <section style="box-sizing: border-box; padding: 0px; margin: 0px;">
                          <p style="clear: none; color: inherit; line-height: 1.75em; white-space: normal;"> <span style="color:rgb(127, 127, 127)"><span style="color: rgb(127, 127, 127); font-family:微软雅黑,Microsoft YaHei; line-height: 24.5px;">如果别人嘲笑你，你可以怜悯他；但是如果你嘲笑他，你决不可自恕。 如果别人伤害你，你可以忘掉它；但是如果你伤害了他，你须永远记住。 实际上别人就是最敏感的你，附托在另一个躯壳上。 他们认为我疯了，因为我不肯拿我的光阴去换金钱； 我认为他们是疯了，因为他们以为我的光阴是可以估价的。</span></span>
                          </p>
                        </section>
                      </section>
                    </blockquote>
                  </section>
                </div>
                <div id="content_008" class="item" style="display: block;">
                  <section style="font-size:14px;font-family:微软雅黑,Microsoft YaHei;margin: 5px auto;white-space: normal;">
                    <section style="box-sizing:border-box;padding:0;margin:1em auto;border:none;width:319px;text-align:center">
                      <section style="box-sizing:border-box;padding:20px 20px 0;margin:0;font-size:16px;color:#fff;border:4px solid #fbc16c;background-color:#254932;min-height:18px;width:270px;position:relative;line-height:1.4">
                        <section style="box-sizing:border-box;padding:0;margin:0;line-height:1.6;font-size:17px">
                          <p style="box-sizing: border-box; padding: 0px; margin-top: 0px; margin-bottom: 10px; text-align: left;"> 信仰是心中的绿洲，思想的骆驼队是永远走不到的。</p>
                          <p style="box-sizing: border-box; padding: 0px; margin-top: 0px; margin-bottom: 10px; text-align: right;">
                          —— 纪伯伦 </p>
                        </section><span style="box-sizing:border-box;padding:0;margin:15px 0 0 54.19px;border:none;background-color:#fff;width:20px;height:4px;display:block"></span>
                        <span
                          style="box-sizing:border-box;padding:0;margin:0 0 0 54.19px;border:none;background-color:#84ccc9;width:20px;height:4px;display:block"></span>
                      </section>
                    </section>
                  </section>
                </div>
                <div id="content_009" class="item" style="display: block;">
                  <section>
                    <blockquote style="margin: 0px; padding: 15px; border-radius: 5px; border: 1px solid rgb(0, 187, 236);" class="bc">
                      <p>人的面孔常常反映他的内心世界，以为思想没有色彩，那是错误的。</p>
                    </blockquote>
                  </section>
                </div>
                <div id="content_010" class="item" style="display: block;">
                  <section style=" border: 1px solid rgb(0, 187, 236); padding: 10px;" class="bc">
                    <section style="font-family: 微软雅黑,Microsoft YaHei; margin: 5px auto; white-space: normal;">
                      <section style="border: 0px none; border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; padding: 0px; box-sizing: border-box; margin: 0px; font-family: 微软雅黑,Microsoft YaHei;">
                        <section style="border: 0px; border-image-source: initial; border-image-slice: initial; border-image-width: initial; border-image-outset: initial; border-image-repeat: initial; box-sizing: border-box; width: 100%; clear: both; padding: 0px; margin: 0px;">
                          <section style="border-bottom-width: 2px; border-bottom-style: solid; border-bottom-color: rgb(0, 187, 236); padding: 5px; margin: 5px 0px 0px; box-sizing: border-box;"
                            class="bc">
                            <p style="margin-right: 0.5px; white-space: normal;"><span style="font-size: 18px;">文艺展示活动</span></p>
                          </section>
                          <p style="padding: 5px; line-height: 1.5em; border-color: rgb(0, 187, 236); white-space: normal;"><span style="line-height: 22.3999996185303px; text-indent: 32px;">为全面贯彻落实党的十八大、十八届三中全会精神和习近平书记在文艺工作座谈会上的讲话精神，大力践行党的群众路线教育实践活动，丰富群众精神文化生活，促进民间文艺团队的发展和提高，推进群众文化艺术大发展大繁荣。开展“十佳民间文艺团队”文艺展示。</span></p>
                        </section>
                        <section style="font-size: 16px; width: 0px; height: 0px; clear: both; box-sizing: border-box; padding: 0px; margin: 0px;"></section>
                      </section>
                    </section>
                  </section>
                </div>
                <div id="content_011" class="item" style="display: block;">
                  <section style="border: medium none; margin-top: 0.5em; margin-bottom: 0.5em;">
                    <section style="border: 1px solid #F96E57; padding: 10px 15px; font-size: 1em; font-family: inherit; text-align: center; text-decoration: inherit;"
                      class="bc">
                      <section style="width: 100%; border-bottom-width: 1px; border-bottom-style: solid; border-color: #F96E57; padding-bottom: 10px; font-size: 1.2em; font-family: inherit; text-decoration: inherit;"
                        class="bc">
                        <section style="text-align: center;"> <span style="font-family: 微软雅黑,Microsoft YaHei;">《沙与沫》 —— 纪伯伦</span> </section>
                      </section>
                      <section style="margin-top: -3px; width: 100%;">
                        <section class="bgc" style="width: 6px; height: 6px; float: left; margin-left: -3px; border-top-left-radius: 100%; border-top-right-radius: 100%; border-bottom-right-radius: 100%; border-bottom-left-radius: 100%; background-color: #F96E57; color: rgb(255, 255, 238);"></section>
                      </section>
                      <section style="font-size:16px;font-family:微软雅黑,Microsoft YaHei;margin: 5px auto;white-space: normal;color: rgb(17, 17, 17); padding-top:10px;">
                        <p>我们中间</p>
                        <p>有些人像墨水</p>
                        <p>有些人像纸张</p>
                        <p>若不是因为有些人是黑的话</p>
                        <p>有些人就成了哑吧</p>
                        <p>若不是因为有些人是白的话</p>
                        <p>有些人就成了瞎子</p>
                      </section>
                    </section>
                  </section>
                </div>
                <div id="content_012" class="item" style="display: block;">
                  <section style="font-size:14px;font-family:微软雅黑,Microsoft YaHei;margin: 5px auto;white-space: normal;">
                    <section style="margin:0 5px;padding:0;font-family:微软雅黑,Microsoft YaHei;">
                      <section style="margin-bottom:-48px;padding:0;text-align:right">
                        <section style="margin-right:10px;display:inline-block;padding:0">
                          <section class="bbc" style="margin: 0px; padding: 0px; border-bottom-width: 12px; border-bottom-style: solid; border-bottom-color: rgb(0, 187, 236); border-left-width: 12px; border-left-style: solid; border-left-color: transparent; border-right-width: 0px; border-right-style: solid; border-right-color: transparent; height: 0px; width: 0px; float: left;"></section>
                          <section style="margin:0;padding:0;background-color:#fff;float:left">
                            <section style="margin:0;padding:0;opacity:.85">
                              <section style="margin: 0px; padding: 0px 5px; width: 50px; height: 50px; display: table-cell; vertical-align: middle; color: rgb(255, 255, 255); text-align: center; background-color: rgb(0, 187, 236);"
                                class="bgc">
                                <section style="margin:0;padding:0;font-size:16px;color:inherit"><strong><span style="color:inherit">01</span></strong></section>
                                <section style="margin:0;padding:0;font-size:12px;color:inherit"><span style="color:inherit">排行</span></section>
                              </section>
                              <section class="btc" style="margin: 0px; padding: 0px; border-top-width: 10px; border-top-style: solid; border-top-color: rgb(0, 187, 236); border-left-width: 26px; border-left-style: solid; border-left-color: transparent; border-right-width: 34px; border-right-style: solid; border-right-color: transparent; height: 0px; width: 0px;"></section>
                            </section>
                          </section>
                          <section class="bbc" style="margin: 0px; padding: 0px; border-bottom-width: 12px; border-bottom-style: solid; border-bottom-color: rgb(0, 187, 236); border-left-width: 0px; border-left-style: solid; border-left-color: transparent; border-right-width: 12px; border-right-style: solid; border-right-color: transparent; height: 0px; width: 0px; float: left;"></section>
                        </section>
                      </section>
                      <section style="margin:0;padding:50px 10px 10px;box-shadow:0 0 6px rgba(0,0,0,.5)">
                        <section style="margin: 0px; padding: 0px; color: rgb(0, 187, 236); text-align: left;" class="fc">
                          <section style="margin:0;padding:0 10px;font-size:16px;color:inherit;word-wrap:break-word"><strong>请输入标题</strong></section>
                        </section>
                        <section class="bc" style="margin:20px 0 0;padding:20px 10px;border-top:1px dashed #999;color:#3c3c3c;line-height:30px;text-indent:2em;text-align:justify;word-wrap:break-word"><span style="color:#3c3c3c;font-size:14px;">在这里输入你的内容，注意不要用退格键把所有文字删除，请保留一个或者用鼠标选取后直接输入，防止格式错乱。</span></section>
                      </section>
                    </section>
                  </section>
                </div>
                <div id="content_013" class="item" style="display: block;">
                  <section style="clear: both; width: 100%; margin-right: auto; margin-left: auto; overflow: hidden;">
                    <section style="margin-top:40px; margin-bottom: 0em;">
                      <section style="border: 2px solid rgb(95, 170, 255); width: auto; margin-right: auto; margin-left: auto; display: inline-block; text-align: center;"
                        class="bc">
                        <section style="width: 50px;height: 50px;margin-left: -19px;margin-top: 20px; margin-top: -40px;margin-left: auto;margin-right: auto;">
                        <img src="http://tool.chinaz.com/template/default/temp/content/img/003.png" style="width: 100%; background-color: rgb(95, 170, 255);"
                            class="bgc"></section>
                        <section style="text-align: left; padding: 20px 15px 10px 15px; margin-top:0px; border: 1px; line-height:1.75em">
                          <p><span style=" color:inherit">在此处添加你的内容。小技巧：删除内容时请保留一个文字，不然容易造成格式错乱，或者全选文字后直接输入内容。 复制时候请用右边的复制按钮，不然复制到公众号也会乱。</span></p>
                        </section>
                      </section>
                    </section>
                  </section>
                </div>
                <div id="content_014" class="item" style="display: block;">
                  <section style="padding: 5px 0px 0px 25px; margin-left:2.5em; border-left-width:1px; border-left-style:solid; border-left-color: #59c3f9;"
                    class="bc">
                    <section style="margin-top: 10px; margin-bottom: 10px; position: static; text-align: center;margin-left:-4em;">
                      <section style="width:64px;text-align:center;">
                        <section style="display: inline-block; padding: 0.3em 2px; font-size: 14px; width: 64px; border-top-right-radius: 4px; border-top-left-radius: 4px; color: #59c3f9; background-color: #59c3f9;"
                          class="bgc"><span style="color: rgb(255, 255, 255); text-align: center; font-family: 微软雅黑,Microsoft YaHei;">一月</span></section>
                      </section>
                      <section style="width: 64px; text-align: center;">
                        <section style="width: 62px; display: inline-block; border: 1px solid #59c3f9; padding: 0.1em 2px; border-bottom-right-radius: 4px; border-bottom-left-radius: 4px; background-color: rgb(254, 254, 254);"
                          class="bc">
                          <p><span style="font-family: 微软雅黑,Microsoft YaHei;"><strong><span style="font-family: 微软雅黑,Microsoft YaHei; color: #59c3f9; font-size: 18px;" class="fc">12</span></strong>
                            </span>
                          </p>
                        </section>
                      </section>
                    </section>
                    <section style="margin-top: -66px;padding-bottom:20px; color: inherit;margin-left:20px;">
                      <p><span style="font-size: 14px; font-family: 微软雅黑,Microsoft YaHei;">成长不是学会表达，而是学会咽下，当你终于可以克制自己的时候，才能驾驭梦想，让人生从此与众不同。</span></p>
                    </section>
                  </section>
                </div>
                <div id="content_015" class="item" style="display: block;">
                  <section style="font-size:14px;font-family:微软雅黑,Microsoft YaHei;margin: 5px auto;white-space: normal;">
                    <section style="margin:1em 0 .5em">
                      <section class="bc" style="border: 1px solid #3abcff; padding: 10px; margin-left: 1.3em; font-size: 1em; text-align: center;">
                        <section style="border-radius: 50%; width: 2em; height: 2em; line-height: 2em; padding: 5px; display: inline-block; float: left; vertical-align: top; margin: -1.3em 0px 0px -1.3em; font-size: 16px; color: rgb(255, 255, 255); background-color: #3abcff;"
                          class="bgc">
                          <section style="color:inherit">名言</section>
                        </section>
                        <section style="padding:8px 15px;text-align:left;text-indent:2em">
                          <p style="color:inherit;line-height:30px"><span style="font-size:14px">我说的话有一半是没有意义的；我把它说出来，为的是也许会让你听到其他的一半。幽默感就是分寸感。</span></p>
                        </section>
                      </section>
                    </section>
                  </section>
                </div>
                <div id="content_016" class="item" style="display: block;">
                  <section style=" box-sizing: border-box; border: 0px none; ">
                    <section style="text-align: center; box-sizing: border-box;">
                      <section style="display: inline-block; margin-top: 5px; margin-bottom: 3em; box-sizing: border-box;">
                        <section style="font-size: 14px; border: none; margin-top: 3.5em; margin-bottom: 0.5em; box-sizing: border-box;">
                          <section style="box-sizing: border-box;">
                            <section style="width: 16.5em; height: 16.5em; margin-top: 0em; box-sizing: border-box; transform: rotate(-45deg); -webkit-transform: rotate(-45deg); background-color: rgba(221, 240, 237, 0.498039);">
                              <section style="width: 90%; height: 20em; margin-left: -1.5em; padding-top: 0.5em; padding-right: 1em; font-size: 1em; font-family: inherit; font-weight: inherit; text-align: right; text-decoration: inherit; color: rgb(255, 255, 255); box-sizing: border-box;transform: rotate(-10deg);-webkit-transform: rotate(-10deg);-moz-transform: rotate(-10deg);-ms-transform: rotate(-10deg);-o-transform: rotate(-10deg);"><br></section>
                            </section>
                          </section>
                        </section>
                        <section style="font-size: 14px; border: none; margin-top: 0em; margin-bottom: 0.5em; box-sizing: border-box;">
                          <section style="box-sizing: border-box;">
                            <section style="width: 16.5em; height: 16.5em; margin-top: -16.5em; box-sizing: border-box; transform: rotate(-35deg); -webkit-transform: rotate(-35deg); background-color: rgba(221, 240, 237, 0.498039);">
                              <section style="width: 80%; height: 20em;padding-top: 2em; padding-bottom: 2em; padding-left: 1em;font-family: inherit; font-weight: inherit;text-decoration: inherit; box-sizing: border-box;transform: rotate(35deg);-webkit-transform: rotate(35deg);-moz-transform: rotate(35deg);-ms-transform: rotate(35deg);-o-transform: rotate(35deg);">
                                <p><span style="font-family: 微软雅黑, 'Microsoft YaHei';">我们中间</span></p>
                                <p><span style="font-family: 微软雅黑, 'Microsoft YaHei';">有些人像墨水</span></p>
                                <p><span style="font-family: 微软雅黑, 'Microsoft YaHei';">有些人像纸张</span></p>
                                <p><span style="font-family: 微软雅黑, 'Microsoft YaHei';">若不是因为有些人是黑的话</span></p>
                                <p><span style="font-family: 微软雅黑, 'Microsoft YaHei';">有些人就成了哑吧</span></p>
                                <p><span style="font-family: 微软雅黑, 'Microsoft YaHei';">若不是因为有些人是白的话</span></p>
                                <p><span style="font-family: 微软雅黑, 'Microsoft YaHei';">有些人就成了瞎子</span></p>
                              </section>
                            </section>
                          </section>
                        </section>
                      </section>
                    </section>
                    <section style="width: 0px; height: 0px; clear: both; box-sizing: border-box;"></section>
                  </section>
                </div>
    
              </div>
    
    
              <div class="content2" data-type="gala">
                <div id="jeDate_001" class="item" style="display: block;">
                  <section>
                    <section style="display: block;background-image:url(http://tool.chinaz.com/template/default/temp/jeDate/img/000.png);background-size:contain;background-position:center;background-repeat:repeat-x;margin:10px 0 15px 0;height: 60px;">
                      <p style="display:none;">聚合支付微信编辑器</p>
                    </section>
                  </section>
                </div>
                <div id="jeDate_002" class="item" style="display: block;">
                  <section>
                    <section style="display: block;background-image:url(http://tool.chinaz.com/template/default/temp/jeDate/img/001.png);background-size:contain;background-position:center;background-repeat:repeat-x;margin:10px 0 15px 0;height: 20px;">
                      <p style="display:none;">聚合支付微信编辑器</p>
                    </section>
                  </section>
                </div>
                <div id="jeDate_003" class="item" style="display: block;">
                  <section>
                    <p style="text-align: center;"><img src="http://tool.chinaz.com/template/default/temp/jeDate/img/002.png" style="max-width: 100%;"></p>
                  </section>
                </div>
                <div id="jeDate_004" class="item" style="display: block;">
                  <section>
                    <p style="text-align: center;"><img src="http://tool.chinaz.com/template/default/temp/jeDate/img/003.png" style="max-width: 100%;"></p>
                  </section>
                </div>
                <div id="jeDate_005" class="item" style="display: block;">
                  <section>
                    <section style="display: block;background-image:url(http://tool.chinaz.com/template/default/temp/jeDate/img/004.png);background-size:contain;background-position:center;background-repeat:repeat-x;margin: 10px 0;height: 20px;">
                      <p style="display:none;">聚合支付微信编辑器</p>
                    </section>
                  </section>
                </div>
                <div id="jeDate_006" class="item" style="display: block;">
                  <section>
                    <section style="display: block;background-image:url(http://tool.chinaz.com/template/default/temp/jeDate/img/005.png);background-size:contain;background-position:center;background-repeat:repeat-x;margin:10px 0 15px 0;height: 20px;">
                      <p style="display:none;">聚合支付微信编辑器</p>
                    </section>
                  </section>
                </div>
                <div id="jeDate_007" class="item" style="display: block;">
                  <section>
                    <section style="display: block;background-image:url(http://tool.chinaz.com/template/default/temp/jeDate/img/011.png);background-size:contain;background-position:center;background-repeat:no-repeat;margin:10px 0 15px 0;height: 60px;">
                      <p style="display:none;">
                        聚合支付微信编辑器
                      </p>
                    </section>
                  </section>
                </div>
                <div id="jeDate_008" class="item" style="display: block;">
                  <section style="clear: both; width: 100%; margin-right: auto; margin-left: auto; overflow: hidden;">
                    <section style="margin-top:40px; margin-bottom: 0em;">
                      <section style="border: 2px solid #dc457d; width: auto; margin-right: auto; margin-left: auto; display: inline-block; text-align: center;"
                        class="bc">
                        <section style="width: 50px;height: 50px;margin-left: -19px;margin-top: 20px; margin-top: -40px;margin-left: auto;margin-right: auto;">
                          <img src="http://tool.chinaz.com/template/default/temp/jeDate/img/010.png" style="width: 100%; background-color: #dc457d;"
                            class="bgc">
                        </section>
                        <section style="text-align: left; padding: 20px 15px 10px 15px; margin-top:0px; border: 1px; line-height:1.75em">
                          <p>
                            <span style=" color:inherit">在此处添加你的内容。小技巧：删除内容时请保留一个文字，不然容易造成格式错乱，或者全选文字后直接输入内容。 复制时候请用右边的复制按钮，不然复制到公众号也会乱。</span>
                          </p>
                        </section>
                      </section>
                    </section>
                  </section>
                </div>
                <div id="jeDate_009" class="item" style="display: block;">
                  <section style="overflow:hidden;">
                    <span style="display:inline-block; width:30px; height:45px;float:left;"><img src="http://tool.chinaz.com/template/default/temp/jeDate/img/013.png" style="max-width:100%"></span>
                    <span style=" display:inline-block; float:left; min-width:40px; padding-top:20px; padding-left:10px; padding-right:10px;font-family:Microsoft YaHei;">3.8魅力女人节</span>
                    <span style="clear:both;"></span>
                  </section>
    
                </div>
                <div id="jeDate_010" class="item" style="display: block;">
                  <section style="text-align:center;">
                    <img style="display:inline-block;" src="http://tool.chinaz.com/template/default/temp/jeDate/img/014.png">
                  </section>
    
                </div>
                <div id="jeDate_011" class="item" style="display: block;">
                  <section style=" border:2px solid #5bad6b;" class="bc">
                    <section style="text-align:center; min-height:200px; background:url(http://tool.chinaz.com/template/default/temp/jeDate/img/016.png) right bottom no-repeat;background-size:40%; padding-bottom:30px;">
                      <p><span class="bc fc" style=" border-bottom:2px solid #5bad6b; padding:5px; font-family:Microsoft YaHei; font-size:16px; display:inline-block; color:#00731a;">3.12植树节</span></p>
                      <p
                        class="fc" style="color:#00731a; line-height:24px; padding:10px; text-align:left; text-indent:24px; font-family:Microsoft YaHei;">植树节是一些国家以法律规定宣传保护树木，并动员群众参加以植树造林为活动内容的节日。按时间长短可分为植树日，植树周或植树月，总称国际植树节。通过这种活动，激发人们爱林，造林的热情。</p>
                    </section>
                  </section>
                </div>
                <div id="jeDate_012" class="item" style="display: block;">
                  <section style="text-align:center; border:1px solid #00731a; overflow:hidden;" class="bc">
                    <p style="padding:10px; max-height:100px;"><img src="http://tool.chinaz.com/template/default/temp/jeDate/img/017.png" style="box-shadow: rgba(0, 0, 0, 0.329412) 0px 0px 6px 0px; border: 10px solid rgb(255, 255, 255);"></p>
                    <p
                      style="color:#00731a; line-height:24px; padding:20px;text-align:left; text-indent:24px; font-family:Microsoft YaHei;">植树节是一些国家以法律规定宣传保护树木，并动员群众参加以植树造林为活动内容的节日</p>
                  </section>
                </div>
                <div id="jeDate_013" class="item" style="display: block;">
                  <section style="text-align:center; border:1px solid #00731a; overflow:hidden; position:relative;" class="bc">
                    <p style=" background:url(http://tool.chinaz.com/template/default/temp/jeDate/img/018.png) no-repeat; margin:20px 10px 10px 20px; background-size:100%; width:133px; height:75px;-transform: rotate(-15deg);-ms-transform:rotate(-15deg);-moz-transform:rotate(-15deg);-webkit-transform:rotate(-15deg);-o-transform:rotate(-15deg)"></p>
                    <p style="font-size:45px; position:absolute;font-weight: bold; right:25px; top:25px; font-family:Microsoft YaHei; color:#379134;">3.12</p>
                    <p style="color:#00731a; line-height:24px; padding:0px 20px 20px 20px;text-align:left; text-indent:24px; font-family:Microsoft YaHei;">植树节是一些国家以法律规定宣传保护树木，并动员群众参加以植树造林为活动内容的节日。按时间长短可分为植树日，植树周或植树月，总称国际植树节。</p>
                  </section>
                </div>
                <div id="jeDate_014" class="item" style="display: block;">
                  <section style="text-align:center;"><img style="display:inline-block;" src="http://tool.chinaz.com/template/default/temp/jeDate/img/021.png"></section>
                </div>
                <div id="jeDate_015" class="item" style="display: block;">
                  <section style="text-align:center;"><img style="display:inline-block; max-width:100%;" src="http://tool.chinaz.com/template/default/temp/jeDate/img/022.png"></section>
                </div>
                <div id="jeDate_016" class="item" style="display: block;">
                  <section style="text-align:center;"><img style="display:inline-block; max-width:100%;" src="http://tool.chinaz.com/template/default/temp/jeDate/img/023.png"></section>
                </div>
                <div id="jeDate_017" class="item" style="display: block;">
                  <section style="text-align:center;"><img style="display:inline-block; max-width:100%;" src="http://tool.chinaz.com/template/default/temp/jeDate/img/024.png"></section>
                </div>
                <div id="jeDate_018" class="item" style="display: block;">
                  <section style="text-align:center;"><img style="display:inline-block; max-width:100%;" src="http://tool.chinaz.com/template/default/temp/jeDate/img/025.png"></section>
                </div>
                <div id="jeDate_019" class="item" style="display: block;">
                  <section>
                    <blockquote class="bgc bc" style="font-family: 微软雅黑,Microsoft YaHei;margin: 0px; padding: 5px 15px; border-radius: 5px 5px 0px 0px; border: 0px rgb(255, 24, 140); text-align: left; color: rgb(255, 255, 255); line-height: 25px; font-weight: bold; max-width: 100%; background-color: rgb(255, 24, 140);"><span style="font-family: 微软雅黑, 'Microsoft YaHei'; line-height: 25px; text-indent: 32px;">母亲节（Mother's Day）快乐！</span></blockquote>
                    <blockquote
                      class="bc" style="font-family: 微软雅黑,Microsoft YaHei;margin: 0px; padding: 10px 15px 20px; border-radius: 0px 0px 5px 5px; border: 1px solid rgb(255, 24, 140); line-height: 25px; max-width: 100%;">
                      <p style="text-indent: 2em;">母亲节（Mother's Day），是一个感谢母亲的节日。母亲们在这一天通常会收到礼物，康乃馨被视为献给母亲的花，而中国的母亲花是萱草花，又叫忘忧草</p>
                      <section style="text-align:center;"><img style="display:inline-block;" src="http://tool.chinaz.com/template/default/temp/jeDate/img/026.png"></section>
                      <p
                        style="text-align: right;"><br></p>
                        </blockquote>
                  </section>
                </div>
                <div id="jeDate_020" class="item" style="display: block;">
                  <section style="text-align:center; float:left; width:20%"><img style="display:inline-block; max-width:100%;" src="http://tool.chinaz.com/template/default/temp/jeDate/img/027.png"></section>
                  <p></p>
                  <section style="float:left; width:70%; font-family:微软雅黑,Microsoft YaHei; white-space: normal; margin: 0.8em 1em; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); line-height: 25.6px; border: 0px; word-wrap: break-word !important;">
                    <section class="bc" style="padding: 10px; max-width: 100%; box-sizing: border-box; word-wrap: break-word; border: 1px solid #ff188c; line-height: 1.4; font-family: inherit; text-decoration: inherit; color: rgb(51, 51, 51);">
                      <p style="max-width: 100%; min-height: 1em; white-space: pre-wrap; box-sizing: border-box !important; word-wrap: break-word !important;"><span style="line-height: 19.6px; font-family: 微软雅黑,Microsoft YaHei;">母亲节（Mother's Day），是一个感谢母亲的节日。母亲们在这一天通常会收到礼物，康乃馨被视为献给母亲的花，而中国的母亲花是萱草花，又叫忘忧草</span></p>
                    </section>
                  </section>
                  <p></p>
                  <p style="clear: both;"><br></p>
                </div>
                <div id="jeDate_021" class="item" style="display: block;">
                  <section style="text-align:center;"><img style="display:inline-block; max-width:100%;" src="http://tool.chinaz.com/template/default/temp/jeDate/img/030.png"></section>
                  <section
                    style="font-family:微软雅黑,Microsoft YaHei; white-space: normal; margin: 0.8em 1em; max-width: 100%; box-sizing: border-box; color: rgb(62, 62, 62); line-height: 25.6px; border: 0px; word-wrap: break-word !important;">
                    <section class="bc" style="padding: 10px; max-width: 100%; box-sizing: border-box; word-wrap: break-word; border: 1px solid #9f223d; line-height: 1.4; font-family: inherit; text-decoration: inherit; color: rgb(51, 51, 51);">
                      <p style="max-width: 100%; min-height: 1em; white-space: pre-wrap; box-sizing: border-box !important; word-wrap: break-word !important;"><span style="line-height: 19.6px; font-family: 微软雅黑,Microsoft YaHei;"></span></p>
                      <p style="white-space: normal; text-align: center;"><span style="color: rgb(159, 34, 61); font-family: 微软雅黑, Microsoft YaHei; font-size: 24px;">I Love Mom</span></p>
                      <p
                        style="white-space: normal;"><span style="color: rgb(159, 34, 61); font-family: 微软雅黑, Microsoft YaHei; line-height: 19.6000003814697px; white-space: pre-wrap;">母亲节（Mothers Day），是一个感谢母亲的节日。母亲们在这一天通常会收到礼物，康乃馨被视为献给母亲的花，而中国的母亲花是萱草花，又叫忘忧草</span></p>
                        <p
                          style="max-width: 100%; min-height: 1em; white-space: pre-wrap; box-sizing: border-box !important; word-wrap: break-word !important;"><span style="line-height: 19.6px; font-family: 微软雅黑,Microsoft YaHei;"></span><br></p>
                    </section>
                    </section>
                </div>
                <div id="jeDate_022" class="item" style="display: block;">
                  <section style="text-align:center;"><img style="display:inline-block; max-width:100%;" src="http://tool.chinaz.com/template/default/temp/jeDate/img/031.png"></section>
                </div>
              </div>
    
    
              <div class="content2" data-type="image">
                <div id="tuImg_001" class="item" style="display: block;">
                  <section>
                    <section style="margin: 1em; border: 0px rgb(117, 117, 118); color: rgb(62, 62, 62); font-size: 16px; text-align: center; ">
                      <section style="display: inline-block; padding: 1px; border: 1px solid rgb(117, 117, 118); color: rgb(0, 0, 0); font-size: 14px; border-top-left-radius: 100%; border-top-right-radius: 100%; border-bottom-right-radius: 100%; border-bottom-left-radius: 100%; height: 200px; width: 200px; ">
                        <section style="padding: 1px; border: 1px solid rgb(117, 117, 118); border-top-left-radius: 100%; border-top-right-radius: 100%; border-bottom-right-radius: 100%; border-bottom-left-radius: 100%; height: 196px; width: 196px; color: inherit; ">
                          <section style="padding: 2px; border: 1px solid rgb(117, 117, 118); border-top-left-radius: 100%; border-top-right-radius: 100%; border-bottom-right-radius: 100%; border-bottom-left-radius: 100%; height: 192px; width: 192px; color: inherit; "><img src="http://tool.chinaz.com/template/default/temp/content/img/g002.png" style="border-radius: 50%; color: inherit; width: 192px; padding: 0px; height: auto !important;"
                              width="192" height="192" border="0" vspace="0" title="" alt=""></section>
                        </section>
                      </section>
                    </section>
                  </section>
                </div>
                <div id="tuImg_002" class="item" style="display: block;">
                  <section style="border: none; margin-top: 40px; box-sizing: border-box; padding: 0px; margin-bottom: 280px;">
                    <section style="margin-right: 130px;">
                      <p style="text-align: right;font-family: 微软雅黑, Microsoft YaHei;"><span style="color:rgb(127, 127, 127); font-size:14px">用一句话描述图片</span></p>
                    </section>
                    <section style=" text-align: right; margin-right: 0px;">
                      <section style="display: inline-block; color: inherit; border-color: rgb(158, 207, 219); width:100%;height: 300px !important;"><img src="http://tool.chinaz.com/template/default/temp/content/img/g001.png" style="border-color: rgb(237, 241, 242); color: inherit; height: 100% ! important; width: 100%; display: inline;"></section>
                    </section>
                    <section style="margin: -70px 0px 2px 4px; color: rgb(94, 123, 130); width: 120px; height: 90px;"><img src="http://tool.chinaz.com/template/default/temp/content/img/g003.png" style="border: 6px solid rgb(254, 254, 254); color: inherit; height: 100% ! important; width: 100%; display: inline;"></section>
                    <section
                      style="text-align: right;margin-top: -370px;margin-left: -20px;">
                      <section style="display:inline-block;color: rgb(94, 123, 130); width: 120px; height: 90px;"><img src="http://tool.chinaz.com/template/default/temp/content/img/g004.png" style="border: 6px solid rgb(254, 254, 254); color: inherit; height: 100% ! important; width: 100%; display: inline;"></section>
                  </section>
                  </section>
                </div>
                <div id="tuImg_003" class="item" style="display: block;">
                  <section style="border: 0px; margin: 0px -1px; box-sizing: border-box; width: 100%; padding: 0px;">
                    <section style="width: 100%; box-sizing: border-box; text-align: center; display: inline-block; color: inherit; padding: 0px; margin: 0px;">
                      <section style="width: 33%; float: left; margin-top: 20px; padding-right: 10px; color: inherit; box-sizing: border-box;">
                        <section style="border: 5px solid rgb(245, 245, 244); box-sizing: border-box; width: 90px; clear: both; margin: 0px auto; text-align: center; height: 90px; border-radius: 50%; color: inherit; padding: 0px;">
                        <img src="http://tool.chinaz.com/template/default/temp/content/img/g002.png" style="border-radius: 50%; box-sizing: border-box; color: inherit; display: inline-block; height: auto ! important; width: 100%;">          </section>
                      </section>
                      <section style="width: 33%; float: left; padding-left: 5px; color: inherit; box-sizing: border-box; margin: 0px;">
                        <section style="border: 5px solid rgb(245, 245, 244); box-sizing: border-box; width: 90px; clear: both; margin: 0px auto; text-align: center; height: 90px; border-radius: 50%; color: inherit; padding: 0px;">
                        <img src="http://tool.chinaz.com/template/default/temp/content/img/g004.png" style="border-radius: 50%; box-sizing: border-box; color: inherit; display: inline-block; height: auto ! important; width: 100%;">          </section>
                      </section>
                      <section style="width: 33%; float: left; margin-top: 20px; padding-left: 10px; color: inherit; box-sizing: border-box;">
                        <section style="border: 5px solid rgb(245, 245, 244); box-sizing: border-box; width: 90px; clear: both; margin: 0px auto; text-align: center; height: 90px; border-radius: 50%; color: inherit; padding: 0px;">
                        <img src="http://tool.chinaz.com/template/default/temp/content/img/g005.png" style="border-radius: 50%; box-sizing: border-box; color: inherit; display: inline-block; height: auto ! important; width: 100%;"></section>
                      </section>
                    </section>
                    <section style="margin-top: -25px; width: 100%; box-sizing: border-box; text-align: center; display: inline-block; color: inherit; padding: 0px;">
                      <section style="width: 50%; float: left; text-align: right; color: inherit; box-sizing: border-box; padding: 0px; margin: 0px;">
                        <section style="border: 5px solid rgb(245, 245, 244); box-sizing: border-box; width: 90px; clear: both; margin: 0px 6px 0px 0px; height: 90px; border-radius: 50%; display: inline-block; color: inherit; padding: 0px;">
                        <img src="http://tool.chinaz.com/template/default/temp/content/img/g007.png" style="border-radius: 50%; box-sizing: border-box; color: inherit; display: inline-block; height: auto ! important; width: 100%;">          </section>
                      </section>
                      <section style="width: 50%; float: left; color: inherit; box-sizing: border-box; padding: 0px; margin: 0px;">
                        <section style="border: 5px solid rgb(245, 245, 244); box-sizing: border-box; width: 90px; clear: both; margin: 0px 0px 0px 5px; height: 90px; border-radius: 50%; color: inherit; padding: 0px;">
                        <img src="http://tool.chinaz.com/template/default/temp/content/img/g006.png" style="border-radius: 50%; box-sizing: border-box; color: inherit; display: inline-block; height: auto ! important; width: 100%;">          </section>
                      </section>
                    </section>
                  </section>
                </div>
                <div id="tuImg_004" class="item" style="display: block;">
                  <section style="text-align: center; box-sizing: border-box; padding: 0px; margin: 0px;">
                    <section style="margin: 5px 5px 10px; display: inline-block; box-sizing: border-box; padding: 0px;">
                      <section style="margin-bottom: -30px; box-sizing: border-box; padding: 0px;">
                        <section style="border-top-left-radius: 50%; border-top-right-radius: 50%; border-bottom-right-radius: 50%; border-bottom-left-radius: 50%; display: inline-block; margin: 0px; max-width: 100%; width: 260px; height: 260px; border-color: #3abcff; box-sizing: border-box; padding: 0px; background-color: #3abcff; word-wrap: break-word !important;">
                          <section style="margin: 20px;width: 220px;height: 220px !important;border-radius:50%;"><img src="http://tool.chinaz.com/template/default/temp/content/img/g006.png" style="border-radius: 50%; width: 100%; display: inline;"></section>
                        </section>
                        <section style="margin: -30px auto 20px; width: 0px; border-top-width: 60px; border-top-style: solid; border-top-color: #3abcff; font-family: inherit; border-bottom-color: #3abcff; color: inherit; box-sizing: border-box; padding: 0px; border-left-width: 80px !important; border-left-style: solid !important; border-left-color: transparent !important; border-right-width: 80px !important; border-right-style: solid !important; border-right-color: transparent !important;"></section>
                      </section>
                    </section>
                  </section>
                </div>
                <div id="tuImg_005" class="item" style="display: block;">
                  <section style="text-align:center;">
                    <section style="display: inline-block;margin-top: 35px;margin-bottom: 180px;">
                      <section style=" box-sizing: border-box; width: 240px; clear: both; height: 240px; border-radius: 50%; color: inherit;display:inline-block;"><img src="http://tool.chinaz.com/template/default/temp/content/img/g004.png" style="border-radius:50%; box-sizing:border-box; color:inherit; display:inline-block; height:auto !important; width:100%"></section>
                      <section
                        style="border: 0px; box-sizing: border-box; clear: both; overflow: hidden; margin-top: -270px;margin-left: 185px;">
                        <section style="border: 5px solid rgb(254,254,254); box-sizing: border-box; width: 100px; clear: both; height: 100px; border-radius: 50%; color: inherit;display:inline-block;"><img src="http://tool.chinaz.com/template/default/temp/content/img/g003.png" style="border-radius:50%; box-sizing:border-box; color:inherit; display:inline-block; height:auto !important; width:100%"></section>
                    </section>
                  </section>
                  </section>
                </div>
                <div id="tuImg_006" class="item" style="display: block;">
                  <section style="box-shadow: rgba(0, 0, 0, 0.298039) 0px 1px 3px; background-color: rgb(255, 255, 255);">
                    <img src="http://tool.chinaz.com/template/default/temp/content/img/g006.png" style="width: 100%; height: 100%; display: inline;">
                    <section data-type="main">
                      <p style="line-height: 1.35em; margin-top: 10px; overflow: hidden; padding: 0px 16px; word-wrap: break-word;">
                        <span style="line-height: 1.35em; font-family: 微软雅黑, Microsoft YaHei">关注站长之家，小站将为您提供最新的站长资讯，创业信息，业界动态及行业趋势。</span>
                      </p>
                    </section>
                    <section style="background: none repeat scroll 0 0 #fafafa;border-top: 1px solid #f2f2f2;color: #999;padding: 16px; 15px;">
                      <img src="http://tool.chinaz.com/template/default/temp/content/img/g007.png" style="float: left; height: 34px; margin-right: 10px; width: 34px; display: block;">
                      <section data-style="clear: none;line-height:17px;padding:0 0;font-size:12px;">
                        <p style="clear: none;font-size:12px;line-height:17px;padding:0 0;margin:0 0;">
                          <span style="font-family: 微软雅黑, Microsoft YaHei; font-size: 14px;"><strong>站长之家</strong></span>
                        </p>
                        <p style="clear: none;font-size:12px;line-height:17px;padding:0 0;margin:0 0;">
                          <span style="font-family: 微软雅黑, Microsoft YaHei">微信号：wwwchinaz</span>
                        </p>
                      </section>
                    </section>
                  </section>
                </div>
                <div id="tuImg_007" class="item" style="display: block;">
                  <section style="border: 0px; box-sizing: border-box; width: 100%; margin: 0.8em 0px 0px; padding: 0px 0.5em 0.5em 0px;"><img style="box-sizing: border-box; width: 100%; border: 0.3em solid white; -webkit-box-shadow: rgb(102, 102, 102) 0.2em 0.2em 0.5em; box-shadow: rgb(102, 102, 102) 0.2em 0.2em 0.5em; height: auto !important; float: none;"
                      src="http://tool.chinaz.com/template/default/temp/tuImg/img/g001.png">
                    <section style="width: 0px; height: 0px; clear: both;"></section>
                  </section>
                </div>
                <div id="tuImg_008" class="item" style="display: block;">
                  <section style="margin: 3px; box-sizing: border-box; padding: 0px;">
                    <p style="text-align: center; box-sizing: border-box; "><img src="http://tool.chinaz.com/template/default/temp/content/img/g001.png" style="box-sizing: border-box; margin: 0px; padding: 0px; width: 100%; display: inline;">      </p>
                    <section style="padding: 2px 0px; box-sizing: border-box; margin: 0px;">
                      <section style="float: left; margin-right: 20px; margin-left: 5px; box-sizing: border-box; padding: 0px;"> <span style="box-sizing:border-box; color:rgb(192, 0, 0); font-size:30px; margin:0px; padding:0px;font-family: 微软雅黑, Microsoft YaHei;"> <em style="box-sizing: border-box; padding: 0px; margin: 0px;">1</em></span>        <span style="box-sizing:border-box; font-size:14px; margin:0px; padding:0px;font-family: 微软雅黑, Microsoft YaHei;"> <em style="box-sizing: border-box; padding: 0px; margin: 0px;color:rgb(153,153,153)">/11</em></span></section>
                      <section
                        style="padding: 5px 0px; box-sizing: border-box; margin-top: 5px;">
                        <p style="clear: none; font-size: 12px; line-height: 17px; box-sizing: border-box; padding: 0px; margin: 0px;"> <span style="box-sizing:border-box; color:rgb(165, 165, 165); margin:0px; padding:0px;font-family: 微软雅黑, Microsoft YaHei;">人生就像一场旅行，不必在乎目的地，在乎的，是沿途的风景，以及看风景的心情</span></p>
                    </section>
                  </section>
                  </section>
                </div>
                <div id="tuImg_009" class="item" style="display: block;">
                  <fieldset style="border: none; margin: 0.8em 0px 0.3em; box-sizing: border-box; padding: 0px;">
                    <section style="line-height: 0; box-sizing: border-box;"><img style="display: inline-block; width: 100%; border: 0px; box-sizing: border-box; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/content/img/g001.png"></section>
                    <section
                      style="width: 30%; display: inline-block; float: left; text-align: right; margin: 15px 0px 0px; padding: 0px; box-sizing: border-box;">
                      <section style="float: right; text-align: center; margin-top: -15px; box-sizing: border-box;">
                        <section style="width: 1px; height: 1.2em; margin-left: 50%; box-sizing: border-box; background-color: rgb(102, 102, 102);"></section>
                        <section style="width: 2em; height: 2em; border: 1px solid rgb(102, 102, 102); border-top-left-radius: 50%; border-top-right-radius: 50%; border-bottom-right-radius: 50%; border-bottom-left-radius: 50%; line-height: 2em; font-size: 1em; font-family: inherit; font-weight: inherit; text-decoration: inherit; color: rgb(39, 44, 51); box-sizing: border-box;">
                          <section style="box-sizing: border-box;font-family: 微软雅黑, Microsoft YaHei;"> 小 </section>
                        </section>
                        <section style="width: 2em; height: 2em; border: 1px solid rgb(102, 102, 102); margin-top: 2px; border-top-left-radius: 50%; border-top-right-radius: 50%; border-bottom-right-radius: 50%; border-bottom-left-radius: 50%; line-height: 2em; font-size: 1em; font-family: inherit; font-weight: inherit; text-decoration: inherit; color: rgb(39, 44, 51); box-sizing: border-box;">
                          <section style="box-sizing: border-box;font-family: 微软雅黑, Microsoft YaHei;"> 憩 </section>
                        </section>
                      </section>
                      </section>
                      <section style="width: 65%; float: left; margin-top: 20px; line-height: 1.5em; margin-left: 5%; padding: 0px; font-size: 1em; font-family: inherit; font-weight: inherit; text-decoration: inherit; color: rgb(39, 44, 51); box-sizing: border-box;">
                        <section style="box-sizing: border-box;font-family: 微软雅黑, Microsoft YaHei;">
                          <section style="box-sizing: border-box;"><span style="font-size: 175%; box-sizing: border-box;font-family: 微软雅黑, Microsoft YaHei;">标题</span></section>
                          <section
                            style="box-sizing: border-box; padding-top:5px;"> 注意查看预，览控制字数 </section>
                      </section>
                      </section>
                  </fieldset>
                </div>
                <div id="tuImg_010" class="item" style="display: block;">
                  <fieldset style="border: 0px; box-sizing: border-box; width: 100%; margin: 0.8em 0px 0.5em; overflow: hidden; padding: 0px;"><img style="box-sizing: border-box; width: 100%; float: left; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/content/img/g001.png">
                    <section
                      style="display: inline-block; font-size: 2em; z-index: 100; padding: 0.1em 0.5em; margin: -1.5em 0px 0px; line-height: 1.2; box-sizing: border-box; float: right; text-align: right; font-family: inherit; font-weight: inherit; text-decoration: inherit; color: rgb(255, 255, 255); border-color: rgb(249, 110, 87); background-color: rgba(200, 14, 71, 0.470588);">
                      <section style="box-sizing: border-box;"> 右标题 </section>
                      <section style="box-sizing: border-box; font-size: 0.7em; font-family: inherit; font-weight: inherit; text-decoration: inherit;">
                      背景色可调透明度 </section>
                      </section>
                  </fieldset>
                </div>
                <div id="tuImg_011" class="item" style="display: block;">
                  <fieldset style="border: 0px; box-sizing: border-box; width: 100%; margin: 0.8em 0px 0.5em; overflow: hidden; padding: 0px;"><img style="box-sizing: border-box; width: 100%; float: left; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/content/img/g001.png">
                    <section
                      style="display: inline-block; font-size: 2em; z-index: 100; padding: 0.1em 0.5em; margin: -1.5em 0px 0px; line-height: 1.2; box-sizing: border-box; float: left; text-align: left; font-family: inherit; font-weight: inherit; text-decoration: inherit; color: rgb(255, 255, 255); border-color: rgb(249, 110, 87); background-color: rgba(200, 14, 71, 0.470588);">
                      <section style="box-sizing: border-box;"> 左标题 </section>
                      <section style="box-sizing: border-box; font-size: 0.7em; font-family: inherit; font-weight: inherit; text-decoration: inherit;">
                      背景色可调透明度 </section>
                      </section>
                  </fieldset>
                </div>
              </div>
    
    
              <div class="content2" data-type="puzzle">
                <div id="pinTu_001" class="item" style="display: block;">
                  <section>
                    <fieldset style="border: 0; box-sizing: border-box; width: 100%; clear: both; margin: 0.8em 0 0.5em 0;overflow: hidden">
                      <section style="box-sizing: border-box; width: 65%; float: right; padding: 0 0 0 0.1em"><img src="http://tool.chinaz.com/template/default/temp/pinTu/img/g000.png" style="box-sizing: border-box; width: 100%; height: auto !important; text-align: start">        </section>
                      <section style="display: inline-block; width: 35%; box-sizing: border-box; float: right; padding: 0 0.1em 0 0; text-align: right">
                        <section style="box-sizing: border-box; margin-right: 4px; padding: 4px 6px; color: rgb(52, 54, 60); border-bottom: 1px solid black; font-size: 1.2em; font-family: inherit; font-style: normal; font-weight: inherit; text-align: inherit; text-decoration: inherit; background-color: transparent; border-color: black;">
                          <section><span style="font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; color: rgb(39, 0, 180);" class="fc">图片在右</span></section>
                        </section>
                        <section style="box-sizing: border-box; margin-right: 0.3em; padding: 3px 5px; color: rgb(120, 124, 129); font-size: 0.9em; font-family: inherit; font-style: normal; font-weight: inherit; text-align: inherit; text-decoration: inherit;">
                          <section><span style="font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; color: rgb(39, 0, 180);" class="fc">描述在左边</span></section>
                        </section>
                      </section>
                    </fieldset>
                  </section>
                </div>
                <div id="pinTu_002" class="item" style="display: block;">
                  <section>
                    <fieldset style="border: 0; box-sizing: border-box; width: 100%; clear: both;margin: 0.8em 0 0.5em 0;overflow: hidden">
                      <section style="box-sizing: border-box; width: 65%; float: left; padding: 0 0.1em 0 0"> <img src="http://tool.chinaz.com/template/default/temp/pinTu/img/g001.png" style="box-sizing: border-box; width: 100%; height: auto !important; text-align: start">        </section>
                      <section style="display: inline-block; width: 35%; box-sizing: border-box; float: left; padding: 0 0 0 0.1em; text-align: left">
                        <section style="box-sizing: border-box; margin-right: 4px; padding: 4px 6px; color: rgb(52, 54, 60); font-size: 1.2em; border-bottom: 1px solid black; font-family: inherit; font-style: normal; font-weight: inherit; text-align: inherit; text-decoration: inherit; background-color: transparent; border-color: black;">
                          <section><span style="font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; color: rgb(39, 0, 180);" class="fc">图片在左</span></section>
                        </section>
                        <section style="box-sizing: border-box; margin-right: 0.3em; padding: 0.3em 0.5em; color: rgb(120, 124, 129); font-size: 0.9em; font-family: inherit; font-style: normal; font-weight: inherit; text-align: inherit; text-decoration: inherit;">
                          <section><span style="font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; color: rgb(39, 0, 180);" class="fc">描述在右边</span></section>
                        </section>
                      </section>
                    </fieldset>
                  </section>
                </div>
                <div id="pinTu_003" class="item" style="display: block;">
                  <div>
                    <section>
                      <fieldset style="border:0; box-sizing: border-box; width: 100%; margin: 0; padding: 0; overflow: hidden"><img src="http://tool.chinaz.com/template/default/temp/pinTu/img/g001.png" style="float: left;box-sizing: border-box;width: 100%;height: auto !important;text-align: start"></fieldset>
                    </section>
                  </div>
                </div>
                <div id="pinTu_004" class="item" style="display: block;">
                  <div>
                    <fieldset style="border:none; margin: 0.5em 0; text-align: center; padding: 2em">
                      <section style="width: 12em; height: 12em; margin: 2em auto; border: 1px solid rgb(73, 37, 110);">
                        <section class="bc" style="width: 10em; height: 10em; border-radius: 1px; margin: 1em auto; overflow: hidden; transform: rotate(45deg); border-color: rgb(39, 0, 180);">
                          <section style="-transform: rotate(-45deg);-ms-transform:rotate(-45deg);-moz-transform:rotate(-45deg);-webkit-transform:rotate(-45deg);-o-transform:rotate(-45deg)">
                            <section style="width: 14em; height: 14em; margin: -2em; max-width: 150% ! important;"><img src="http://tool.chinaz.com/template/default/temp/pinTu/img/g001.png"></section>
                          </section>
                        </section>
                      </section>
                    </fieldset>
                  </div>
                </div>
                <div id="pinTu_005" class="item" style="display: block;">
                  <section style="width: 80%; margin:20px auto 0px 15px; border: 8px solid white; box-sizing: border-box; display: inline-block; vertical-align: top">
                    <img src="http://tool.chinaz.com/template/default/temp/pinTu/img/g002.png" style="width: 100%;border: 8px solid white;margin: 0 0.5em -1em 0;box-shadow: 2px 2px 5px #666; -transform: rotateZ(-10deg);-ms-transform:rotateZ(-10deg); -moz-transform:rotateZ(-10deg);-webkit-transform:rotateZ(-10deg);-o-transform:rotateZ(-10deg)">
                    <img src="http://tool.chinaz.com/template/default/temp/pinTu/img/g001.png" style="width: 100%;border: 8px solid white;margin: 0 0.5em 0 0.8em;box-shadow: 2px 2px 5px #666">
                  </section>
                </div>
                <div id="pinTu_006" class="item" style="display: block;">
                  <div>
                    <fieldset style="border:none; margin: 0.5em 0; text-align: center; padding-top: 0.5em">
                      <section style="width: 6em; height: 6em;border-radius: 1px; margin:1em auto;overflow: hidden; background-color: transparent;-transform: rotate(45deg);-ms-transform:rotate(45deg);-moz-transform:rotate(45deg);-webkit-transform:rotate(45deg);-o-transform:rotate(45deg)">
                        <section style="width: 9.5em; height: 9.5em; margin: -2em;"><img src="http://tool.chinaz.com/template/default/temp/content/img/g002.png" style="max-width: 150% ! important; transform: rotate(-45deg); width: 9.5em; height: 9.5em; "></section>
                      </section>
                      <section style="width:100%; margin: -3.5em auto 0.5em">
                        <section style="width: 6em; height: 6em;border-radius: 1px;margin: 1em 3.3em 0 0; display: inline-block; vertical-align: top; overflow: hidden; background-color: transparent;-transform: rotate(45deg);-ms-transform:rotate(45deg);-moz-transform:rotate(45deg);-webkit-transform:rotate(45deg);-o-transform:rotate(45deg)">
                          <section style="width: 9.5em; height: 9.5em; margin: -2em;"><img src="http://tool.chinaz.com/template/default/temp/content/img/g007.png" style="width: 9.5em; height: 9.5em; max-width: 150% ! important; transform: rotate(-45deg);"></section>
                        </section>
                        <section style="width: 6em; height: 6em;border-radius: 1px; display: inline-block; vertical-align: top; margin:1em auto;overflow: hidden; background-color: transparent;-transform: rotate(45deg);-ms-transform:rotate(45deg);-moz-transform:rotate(45deg);-webkit-transform:rotate(45deg);-o-transform:rotate(45deg)">
                          <section style="width: 9.5em; height: 9.5em; margin: -2em; "><img src="http://tool.chinaz.com/template/default/temp/content/img/g006.png" style="width: 9.5em; height: 9.5em; max-width: 150% ! important; transform: rotate(-45deg);"></section>
                        </section>
                      </section>
                    </fieldset>
                  </div>
                </div>
                <div id="pinTu_007" class="item" style="display: block;">
                  <div>
                    <fieldset style="border: none; margin: 0.5em 0">
                      <section style="overflow: hidden">
                        <section style="height: 20em;"><img src="http://tool.chinaz.com/template/default/temp/pinTu/img/g001.png" style="height: 20em; max-width:100%"></section>
                        <section
                          style="width: 12em; height: 20em; margin: -21em 0px 0px -3em; background-color: rgba(74, 113, 165, .7); padding: 1em 0px 0px 3em; transform: skew(-15deg);">
                          <section style="width: 100%; height: 20em; margin-left: 1.2em; transform: skew(15deg); font-size: 1em; font-family: inherit; font-style: normal; font-weight: inherit; text-align: left; text-decoration: inherit; color: rgb(255, 255, 255);">
                            <section style="padding-top:1em;">如果你不出去走走，你就会以为这就是世界。<br><br>——《天堂电影院》</section>
                          </section>
                      </section>
                      </section>
                    </fieldset>
                  </div>
                </div>
                <div id="pinTu_008" class="item" style="display: block;">
                  <div>
                    <fieldset style="overflow: hidden; border: none; margin: 0.5em 0">
                      <section style="width: 10em; height: 2em; line-height: 2em; margin: 1em 0px -4em -3em; transform: rotate(-45deg); font-size: 1.5em; font-family: inherit; font-style: normal; font-weight: inherit; text-align: center; text-decoration: inherit; color: rgb(255, 255, 255); background-color: rgb(39, 0, 180); border-color: white; opacity: 0.701961;"
                        class="bgc">
                        <section>单车特写</section>
                      </section><img src="http://tool.chinaz.com/template/default/temp/pinTu/img/g001.png" style="box-sizing: border-box;max-width: 100%;height: auto !important;text-align: start"></fieldset>
                  </div>
                </div>
                <div id="pinTu_009" class="item" style="display: block;">
                  <div>
                    <fieldset style="border: none; margin: 0.5em 0; padding: 0 0.5em; text-align: center"><img src="http://tool.chinaz.com/template/default/temp/pinTu/img/g001.png" style="box-sizing: border-box; width: 60%; box-shadow: 3px 3px 3px rgba(0, 0, 0, 0.29); height: auto !important; margin: 0;text-align: start"></fieldset>
                  </div>
                </div>
                <div id="pinTu_010" class="item" style="display: block;">
                  <div>
                    <fieldset style="border:0;box-sizing: border-box; width: 100%;margin: 0.8em 0 0.5em 0;text-align: center"><img src="http://tool.chinaz.com/template/default/temp/pinTu/img/g004.png" style="box-sizing: border-box; width: 100%; height: auto !important; text-align: start">
                      <section
                        style="width: 7em; height: 7em; display: inline-block; vertical-align: middle; border: 2px solid rgb(208, 122, 15); border-radius: 100%; margin: -3.5em auto 0px;">
                        <section style="box-sizing: border-box; width: 100%; height: 100%; border-radius: 100%;"><img src="http://tool.chinaz.com/template/default/temp/spread/img/chinaz.jpg" style="box-sizing: border-box; width: 100%; text-align: start; height: auto !important; border-radius: 50%;"></section>
                        </section>
                        <section style="display: inline-block; vertical-align: top; margin-top: -2.1em; line-height: 2em; max-width: 60%; padding-left: 5px; font-size: inherit; font-family: inherit; font-style: normal; font-weight: inherit; text-align: left; text-decoration: inherit; color: rgb(249, 110, 87);">
                          <section style=" font-size: 1em; color: #ffffff;"><span style="font-family: 微软雅黑,Microsoft YaHei;">站长之家</span></section>
                          <section style=" font-size: 1em; color: #000000;"><span style="font-family: 微软雅黑,Microsoft YaHei;">资讯 | 创业 | 动态 | 趋势</span></section>
                        </section>
                    </fieldset>
                  </div>
                </div>
                <div id="pinTu_011" class="item" style="display: block;">
                  <div>
                    <fieldset style="border: 0">
                      <section style="display: inline-block; width:20%;vertical-align: top;margin: 0.72em 0.6em 0 0 0"><img src="http://tool.chinaz.com/template/default/temp/pinTu/img/g000.png" style="width:2.4em; height: 2.4em; margin-top: 0.75em;"></section>
                      <section
                        style="display: inline-block; vertical-align: top; width: 75%">
                        <section class="bc bgc" style="display: inline-block; float: left; width: 1em; height: 1em; margin: 1.5em 0px -2em -0.5em; border: 1px solid rgb(39, 0, 180); border-radius: 100%; background-color: rgb(39, 0, 180);">
                          <section style="width: 100%; height: 100%; border-radius: 100%; background-color: rgb(249, 110, 87); border-color: rgb(39, 0, 180);"
                            class="bc"></section>
                        </section>
                        <section style="border-left: 1px solid rgb(249, 110, 87); border-color: rgb(249, 110, 87);"></section>
                        <section style="border-left: 1px solid rgb(249, 110, 87); border-color: rgb(249, 110, 87);">
                          <section style="display: inline-block;padding:0 0 5px 20px">
                            <section style="margin: 1em auto 0.5em; line-height: 1.5em; font-size: 1em; font-family: inherit; font-style: normal; font-weight: inherit; text-align: left; text-decoration: inherit; color: rgb(0, 0, 0);"><span style="font-family: 微软雅黑,Microsoft YaHei;">世界那么大<br>我想去看看美景</span></section><img src="http://tool.chinaz.com/template/default/temp/pinTu/img/g000.png"
                              style="box-sizing: border-box;width: 100%;height: auto !important;text-align: start"></section>
                        </section>
                        </section>
                    </fieldset>
                  </div>
                </div>
              </div>
    
    
              <div class="content2" data-type="follow">
                <div id="Follow_001" class="item" style="display: block;">
                  <section>
                    <p style="text-align: center;"><img style="width:100%;" src="http://tool.chinaz.com/template/default/temp/Follow/img/000.gif"></p>
                  </section>
                </div>
                <div id="Follow_002" class="item" style="display: block;">
                  <section>
                    <p><img style="width:100%;" src="http://tool.chinaz.com/template/default/temp/Follow/img/001.gif"></p>
                  </section>
                </div>
                <div id="Follow_003" class="item" style="display: block;">
                  <section>
                    <p><img style="width:100%;" src="http://tool.chinaz.com/template/default/temp/Follow/img/002.gif"></p>
                  </section>
                </div>
                <div id="Follow_004" class="item" style="display: block;">
                  <section style="font-family:微软雅黑,Microsoft YaHei;">
                    <section style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; word-wrap: break-word !important; display: inline-block; font-size: 12px;">
                      <section style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;"> <span>点击下方</span> </section>
                    </section>
                    <section style="margin: 0px 10px; padding: 0.3em 0.5em; max-width: 100%; box-sizing: border-box; word-wrap: break-word !important; display: inline-block; border-top-left-radius: 0.5em; border-top-right-radius: 0.5em; border-bottom-right-radius: 0.5em; border-bottom-left-radius: 0.5em; color: rgb(255, 255, 255); font-size: 14px; background-color: rgb(30, 155, 232)">
                      <section style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;"> <span>阅读原文</span> </section>
                    </section>
                    <section style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; word-wrap: break-word !important; display: inline-block; font-size: 14px;">
                      <section style="margin: 0px; padding: 0px; max-width: 100%; box-sizing: border-box; word-wrap: break-word !important;"> <span>了解更多。</span> </section>
                    </section>
                  </section>
                </div>
                <div id="Follow_005" class="item" style="display: block;">
                  <section>
                    <p><img style="max-width:100%;" src="http://tool.chinaz.com/template/default/temp/Follow/img/004.gif"></p>
                  </section>
                </div>
                <div id="Follow_006" class="item" style="display: block;">
                  <section>
                    <p><img style="max-width:100%;" src="http://tool.chinaz.com/template/default/temp/Follow/img/005.gif"></p>
                  </section>
                </div>
                <div id="Follow_007" class="item" style="display: block;">
                  <section>
                    <p><img style="width: 100%;" src="http://tool.chinaz.com/template/default/temp/Follow/img/006.gif"></p>
                  </section>
                </div>
                <div id="Follow_008" class="item" style="display: block;">
                  <section>
                    <p><img style="width: 100%;" src="http://tool.chinaz.com/template/default/temp/Follow/img/007.gif"></p>
                  </section>
                </div>
              </div>
    
    
              <div class="content2" data-type="dividing-line">
                <div id="fenLine_001" class="item" style="display: block;">
                  <section>
                    <section style="background-color: none;border:none;border-style: none;margin: 5px 0 0;padding: 10px;background: none;">
                    <img src="http://tool.chinaz.com/template/default/temp/fenLine/img/008.gif" style="width: 22px; margin: 0 auto; display: block;">      </section>
                  </section>
                </div>
                <div id="fenLine_002" class="item" style="display: block;">
                  <section style="border:none;border-style:none;margin: 5px auto;text-align: center;">
                    <p><img src="http://tool.chinaz.com/template/default/temp/fenLine/img/001.gif" style="margin: 0px; padding: 0px; max-width: 100%;"></p>
                  </section>
                </div>
                <div id="fenLine_003" class="item" style="display: block;">
                  <section style="border:none;border-style:none;margin: 5px auto;text-align: center;">
                    <p><img src="http://tool.chinaz.com/template/default/temp/fenLine/img/002.gif" style="margin: 0px; padding: 0px; max-width: 100%;"></p>
                  </section>
                </div>
                <div id="fenLine_004" class="item" style="display: block;">
                  <p style="text-align: center;"><span style="font-family: 微软雅黑,Microsoft YaHei;"><span style="text-align: center; color: rgb(238, 119, 0); box-sizing: border-box;">●</span>
                    <span
                      style="color: rgb(114, 113, 114); text-align: center;">&nbsp;&nbsp;</span><span style="text-align: center; color: rgb(250, 191, 0); box-sizing: border-box;">●<span style="text-align: center; color: rgb(250, 191, 0); box-sizing: border-box;">&nbsp;</span>
                      <span
                        style="color: rgb(160, 216, 239); box-sizing: border-box;">●</span>
                        </span><span style="color: rgb(114, 113, 114); text-align: center;">&nbsp;&nbsp;<span style="color: rgb(114, 113, 114); text-align: center;">END</span>        &nbsp;</span><span style="text-align: center; color: rgb(238, 119, 0); box-sizing: border-box;">&nbsp;●</span>
                        <span
                          style="color: rgb(114, 113, 114); text-align: center;">&nbsp;&nbsp;</span><span style="text-align: center; color: rgb(250, 191, 0); box-sizing: border-box;">●</span>
                          <span
                            style="color: rgb(114, 113, 114); text-align: center;">&nbsp;&nbsp;</span><span style="text-align: center;color: rgb(160, 216, 239); box-sizing: border-box;">●</span></span>
                  </p>
                </div>
                <div id="fenLine_005" class="item" style="display: block;">
                  <p><img style="width: 100%; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/028.jpg"></p>
                </div>
                <div id="fenLine_006" class="item" style="display: block;">
                  <section style="border:none;border-style:none;margin: 5px auto;text-align: center;">
                    <p><img src="http://tool.chinaz.com/template/default/temp/fenLine/img/004.gif" style="margin: 0px; padding: 0px; max-width: 100%;"></p>
                  </section>
                </div>
                <div id="fenLine_007" class="item" style="display: block;">
                  <section style="border:none;border-style:none;margin: 5px auto;text-align: center;">
                    <p><img src="http://tool.chinaz.com/template/default/temp/fenLine/img/005.gif" style="margin: 0px; padding: 0px; max-width: 100%;"></p>
                  </section>
                </div>
                <div id="fenLine_008" class="item" style="display: block;">
                  <section style="border:none;border-style:none;margin: 5px auto;text-align: center;">
                    <p><img src="http://tool.chinaz.com/template/default/temp/fenLine/img/006.gif" style="margin: 0px; padding: 0px; max-width: 100%;"></p>
                  </section>
                </div>
                <div id="fenLine_009" class="item" style="display: block;">
                  <section style="border:none;border-style:none;margin: 5px auto;text-align: center;">
                    <p><img src="http://tool.chinaz.com/template/default/temp/fenLine/img/003.gif" style="margin: 0px; padding: 0px; max-width: 100%;"></p>
                  </section>
                </div>
                <div id="fenLine_010" class="item" style="display: block;">
                  <section style="background-color: none;border:none;border-style: none;margin: 5px 0 0;padding: 10px;background: none;"><img src="http://tool.chinaz.com/template/default/temp/fenLine/img/009.png" style="width: 100%;  margin: 0 auto; display: block;"></section>
                </div>
                <div id="fenLine_011" class="item" style="display: block;">
                  <p><img src="http://tool.chinaz.com/template/default/temp/fenLine/img/029.gif"></p>
                </div>
                <div id="fenLine_012" class="item" style="display: block;">
                  <section>
                    <p><img style="width: 100%; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/009.gif"></p>
                  </section>
                </div>
                <div id="fenLine_013" class="item" style="display: block;">
                  <section>
                    <p><img style="width: 100%; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/010.gif"></p>
                  </section>
                </div>
                <div id="fenLine_014" class="item" style="display: block;">
                  <section>
                    <p><img style="width: 100%; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/011.gif"></p>
                  </section>
                </div>
                <div id="fenLine_015" class="item" style="display: block;">
                  <section>
                    <p><img style="width: 100%;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/012.gif"></p>
                  </section>
                </div>
                <div id="fenLine_016" class="item" style="display: block;">
                  <section>
                    <p><img style="width: 100%; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/013.jpg"></p>
                  </section>
                </div>
                <div id="fenLine_017" class="item" style="display: block;">
                  <section>
                    <p><img style="width: 100%; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/014.jpg"></p>
                  </section>
                </div>
                <div id="fenLine_018" class="item" style="display: block;">
                  <section>
                    <p><img style="width: 100%; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/015.png"></p>
                  </section>
                </div>
                <div id="fenLine_019" class="item" style="display: block;">
                  <section style="border:none;border-style:none;margin: 5px auto;text-align: center;">
                    <p><img src="http://tool.chinaz.com/template/default/temp/fenLine/img/016.gif" style="margin: 0px; padding: 0px; max-width: 100%;"></p>
                  </section>
                </div>
                <div id="fenLine_020" class="item" style="display: block;">
                  <p><img style="width: 100%; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/017.gif"></p>
                </div>
                <div id="fenLine_021" class="item" style="display: block;">
                  <section>
                    <p><img style="width: 100%; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/000.jpg"></p>
                  </section>
                </div>
                <div id="fenLine_022" class="item" style="display: block;">
                  <section>
                    <p><img style="width: 100%; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/007.gif"></p>
                  </section>
                </div>
                <div id="fenLine_023" class="item" style="display: block;">
                  <p><img style="width: 100%; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/018.png"></p>
                </div>
                <div id="fenLine_024" class="item" style="display: block;">
                  <p><img style="width: 100%; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/019.png"></p>
                </div>
                <div id="fenLine_025" class="item" style="display: block;">
                  <p><img style="width: 100%; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/020.png"></p>
                </div>
                <div id="fenLine_026" class="item" style="display: block;">
                  <p><img style="width: 100%; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/021.png"></p>
                </div>
                <div id="fenLine_027" class="item" style="display: block;">
                  <p><img style="width: 100%; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/022.png"></p>
                </div>
                <div id="fenLine_028" class="item" style="display: block;">
                  <p><img style="width: 100%; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/023.png"></p>
                </div>
                <div id="fenLine_029" class="item" style="display: block;">
                  <p><img style="width: 100%; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/024.png"></p>
                </div>
                <div id="fenLine_030" class="item" style="display: block;">
                  <p><img style="width: 100%; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/025.png"></p>
                </div>
                <div id="fenLine_031" class="item" style="display: block;"><p><img style="width: 100%; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/026.png"></p>
                </div>
                <div id="fenLine_032" class="item" style="display: block;">
                  <p><img style="max-width:100%;height: auto !important;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/030.gif"></p>
                </div>
                <div id="fenLine_033" class="item" style="display: block;">
                  <p style="text-align: center;"><img style="max-width:100%;height: auto !important;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/031.gif"></p>
                </div>
                <div id="fenLine_034" class="item" style="display: block;">
                  <p style="text-align: center;"><img style="max-width:100%;height: auto !important;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/032.gif"></p>
                </div>
                <div id="fenLine_035" class="item" style="display: block;">
                  <p style="text-align: center;"><img style="max-width:100%;height: auto !important;" src="http://tool.chinaz.com/template/default/temp/fenLine/img/033.gif"></p>
                </div>
              </div>
    
    
              <div class="content2" data-type="spread">
    
                <div id="spread_001" class="item" style="display: block;">
                  <section>
                    <section style="border-style: none; width: 320px; clear: both; overflow: hidden;margin: 0 auto;">
                      <section style="width: 100%; float: left; padding: 0 0.1em 0 0;"><img style="width: 295px; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/spread/img/001.jpg"
                          width="295" height="184">
                        <section style="padding:0.4em 1em;float: left;font-size: 15px; margin-top: -11em; margin-left: 0em;text-align: center; color: #fff; opacity: 0.85; background-color: abg(255,255,255);"><img src="http://tool.chinaz.com/template/default/temp/spread/img/qcoder.png" width="140" height="140" style="width: 140px; height: 140px;"
                            border="0" vspace="0" title="" alt=""></section>
                      </section>
                    </section>
                  </section>
                </div>
                <div id="spread_002" class="item" style="display: block;">
                  <section>
                    <section style="border-style: none; width: 320px; clear: both; overflow: hidden;margin: 0 auto;">
                      <section style="width: 100%; float: left; padding: 0 0.1em 0 0;"><img style="width: 295px; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/spread/img/002.gif"
                          width="295" height="150">
                        <section style="padding:0.2em 1em;float: left;font-size: 14px; margin-top: -10.5em;margin-left:0.2em;text-align: center; color: #fff; opacity: 0.95; background-color: abg(255,255,255);"><img src="http://tool.chinaz.com/template/default/temp/spread/img/qcoder.png" width="130" height="130" style="width: 130px; height: 130px;"></section>
                      </section>
                    </section>
                  </section>
                </div>
                <div id="spread_003" class="item" style="display: block;">
                  <section>
                    <section style="border-style: none; width: 320px; clear: both; overflow: hidden;margin: 0 auto;">
                      <section style="width: 100%; float: left; padding: 0 0.1em 0 0;"><img style="width: 295px; height: auto !important;" src="http://tool.chinaz.com/template/default/temp/spread/img/003.gif"
                          width="295" height="190">
                        <section style="padding:0.2em 1em;float: left;font-size: 18px; margin-top: -10.5em;margin-left:0.2em;text-align: center; color: #fff; opacity: 0.95; background-color: abg(255,255,255);"><img src="http://tool.chinaz.com/template/default/temp/spread/img/qcoder.png" width="130" height="130" style="width: 130px; height: 130px;"></section>
                      </section>
                    </section>
                  </section>
                </div>
                <div id="spread_004" class="item" style="display: block;">
                  <section>
                    <blockquote style="margin:0 auto;width:360px;max-width:100%;border:none #ff8124;box-sizing:border-box;background-image:url(http://tool.chinaz.com/template/default/temp/spread/img/004.gif);background-position:right 0;background-size:contain;background-repeat:no-repeat">
                      <section style="padding:10px 3px"><img style="max-width:50% !important" src="http://tool.chinaz.com/template/default/temp/spread/img/qcoder.png" width="150"
                          border="0" vspace="0"></section>
                    </blockquote>
                  </section>
                </div>
              </div>
    
    
              <div class="content2" data-type="chother">
                <div id="Chother_001" class="item" style="display: block;">
                  <section>
                    <section style="background-color:#fbfbfb;padding:10px;">
                      <p style="font-family:微软雅黑,Microsoft Yahei;color:#333;margin:0;padding:10px;"> 联系我们 </p>
                      <section style="overflow:hidden;border-bottom:1px solid #ddd;font-size:14px;color:#545454;background-color:#fbfbfb;line-height:38px;font-family:微软雅黑,Microsoft Yahei;">
                      <span style="background-image:url(http://tool.chinaz.com/template/default/temp/Chother/img/003.png);background-position:0 -135px ; background-repeat:no-repeat;display:block;width:24px;height:24px;float:left;margin-top:6px;margin-left:10px;"></span>
                        <span
                          style="display:block;float:left;margin-left:10px;">手机：</span><span style="display:block;float:left;margin-left:10px;"><a href="javascript:void(0);">139xxxxxxxx</a></span>
                          <span
                            style="display:block;float:right;margin-right:10px;font-weight:bold;line-height:38px;width:20px;height:36px;background-image:url(http://tool.chinaz.com/template/default/temp/Chother/img/003.png);background-repeat:no-repeat;background-position:0 -197px;"></span>
                      </section>
                      <section style="overflow:hidden;border-bottom:1px solid #ddd;font-size:14px;color:#545454;background-color:#fbfbfb;line-height:38px;font-family:微软雅黑,Microsoft Yahei;">
                      <span style="background-image:url(http://tool.chinaz.com/template/default/temp/Chother/img/003.png);background-position:0 -472px ; background-repeat:no-repeat;display:block;width:24px;height:24px;float:left;margin-top:6px;margin-left:10px;"></span>
                        <span
                          style="display:block;float:left;margin-left:11px;">QQ：</span><span style="display:block;float:left;margin-left:10px;">888888888</span> </section>
                      <section style="overflow:hidden;border-bottom:1px solid #ddd;font-size:14px;color:#545454;background-color:#fbfbfb;line-height:38px;font-family:微软雅黑,Microsoft Yahei;">
                      <span style="background-image:url(http://tool.chinaz.com/template/default/temp/Chother/img/003.png);background-position:0 0px ; background-repeat:no-repeat;display:block;width:24px;height:24px;float:left;margin-top:8px;margin-left:10px;"></span>
                        <span
                          style="display:block;float:left;margin-left:10px;">微信：</span><span style="display:block;float:left;margin-left:10px;">xxxx</span> </section>
                      <section style="overflow:hidden;border-bottom:1px solid #ddd;font-size:14px;color:#545454;background-color:#fbfbfb;line-height:38px;font-family:微软雅黑,Microsoft Yahei;">
                      <span style="background-image:url(http://tool.chinaz.com/template/default/temp/Chother/img/003.png);background-position:0 -270px ; background-repeat:no-repeat;display:block;width:24px;height:24px;float:left;margin-top:6px;margin-left:10px;"></span>
                        <span
                          style="display:block;float:left;margin-left:10px;">地址：</span><span style="display:block;float:left;margin-left:10px;">xx省xx市xx区xx</span><span style="display:block;float:right;margin-right:10px;font-weight:bold;"></span>          </section>
                      <section style="overflow:hidden;border-bottom:1px solid #ddd;font-size:14px;color:#545454;background-color:#fbfbfb;line-height:38px;font-family:微软雅黑,Microsoft Yahei;">
                      <span style="background-image:url(http://tool.chinaz.com/template/default/temp/Chother/img/003.png);background-position:0 -31px ; background-repeat:no-repeat;display:block;width:24px;height:24px;float:left;margin-top:6px;margin-left:10px;"></span>
                        <span
                          style="display:block;float:left;margin-left:10px;">官网：</span><span style="display:block;float:left;margin-left:10px;"><a href="javascript:void(0);">tool.chinaz.com</a></span>
                          <span
                            style="display:block;float:right;margin-right:10px;">&gt;</span>
                      </section>
                      <section style="overflow:hidden;font-size:14px;border-bottom:1px solid #ddd;color:#545454;background-color:#fbfbfb;font-family:微软雅黑,Microsoft Yahei;">
                      <span style="background-image:url(http://tool.chinaz.com/template/default/temp/Chother/img/003.png);background-position:0 -99px ; background-repeat:no-repeat;display:block;width:24px;height:24px;float:left;margin-top:6px;margin-left:10px;"></span>
                        <span
                          style="display:block;float:left;margin-left:10px;line-height:38px;">邮箱：</span><span style="display:block;float:left;margin-left:10px;line-height:38px;">xxxxx@xxxx.xx</span> </section>
                    </section>
                  </section>
                </div>
                <div id="Chother_002" class="item" style="display: block;">
                  <section>
                    <section style="margin: 5px auto;white-space: normal;">
                      <section style="margin-top:5px;margin-bottom:5px;border-top-width:1px;border-top-style:solid;border-top-color:#6b4d40;border-bottom-width:1px;border-bottom-style:solid;border-bottom-color:#6b4d40;color:#68423f;padding:0 0;width:100%;display:inline-block;font-family: 微软雅黑,Microsoft YaHei;">
                        <section style="width:27%;margin:15px 0;padding:4px 8px;float:left;border-right-width:1px;border-right-style:dashed;border-right-color:#6b4d40;">
                          <p style="line-height:30px;text-align:center"><span><strong style="color:inherit;font-size:16px">贴心备至</strong></span></p>
                          <p style="color:#6b4d40;border-color:#6b4d40;text-align:center;line-height:1.75em"><span style="font-size: 12px;">我们贴心备至</span></p>
                        </section>
                        <section style="width:27%;margin:15px 0;padding:4px 8px;float:left;border-right-width:1px;border-right-style:dashed;border-right-color:#6b4d40">
                          <p style="line-height:30px;text-align:center"><span><strong style="color:inherit;font-size:16px">有问必答</strong></span></p>
                          <p style="color:#6b4d40;border-color:#6b4d40;text-align:center;line-height:1.75em"><span style="font-size: 12px;">我们有问必答</span></p>
                        </section>
                        <section style="width:27%;margin:15px 0;padding:4px 8px;float:left;">
                          <p style="line-height:30px;text-align:center"><span><strong style="color:inherit;font-size:16px">暖心售后</strong></span></p>
                          <p style="color:#6b4d40;border-color:#6b4d40;text-align:center;line-height:1.75em"><span style="font-size: 12px;">我们暖心售后</span></p>
                        </section>
                      </section>
                    </section>
                  </section>
                </div>
    
    
                <div id="Chother_003" class="item" style="display: block;">
                  <section>
                    <section style="text-align:center;padding:10px;font-family:微软雅黑;">
                      星际恋歌
                    </section>
                    <section style="display:table;width:100%;">
                      <section style="display:table-row;">
                        <section style="display:table-cell;padding-bottom:10px;width:60px;">
                          <section style="width:60px;height:60px;border-radius:50%;overflow:hidden;">
                            <img style="width:60px;height:60px;border-radius:50%;" src="http://tool.chinaz.com/template/default/temp/Chother/img/g001.png">
                          </section>
                        </section>
                        <section style="display:table-cell;padding-bottom:10px;width:10px;vertical-align:top;">
                          <section class="bc" style="width: 0px; height: 0px; border-top: 10px solid rgb(39, 0, 180); border-right: 15px solid rgb(39, 0, 180); border-bottom: 10px solid rgb(39, 0, 180); margin-top: 19px; border-left-color: rgb(39, 0, 180);"></section>
                        </section>
                        <section style="display:table-cell;padding-bottom:10px;width:10000px;vertical-align:top;">
                          <section class="bgc" style="padding: 10px; margin-top: 10px; background-color: rgb(39, 0, 180); color: rgb(255, 255, 255);">
                            <p style="padding:0;margin:0;">
                              冥王星：等待了亿万年，终于有人来看我了
                            </p>
                          </section>
                        </section>
                        <section style="display:table-cell;padding-bottom:10px;width:60px;">
                          <section style="width:75px;"></section>
                        </section>
                      </section>
                    </section>
                    <section style="display:table;width:100%;">
                      <section style="display:table-row;">
                        <section style="display:table-cell;padding-bottom:10px;width:60px;">
                          <section style="width:75px;"></section>
                        </section>
                        <section style="display:table-cell;padding-bottom:10px;width:10000px;vertical-align:top;">
                          <section class="bgc" style="padding: 10px; margin-top: 10px; background-color: rgb(39, 0, 180); color: rgb(255, 255, 255); text-align: right;">
                            <p style="padding:0;margin:0;">
                              让你失望了 ，我只是路人甲
                            </p>
                          </section>
                        </section>
                        <section style="display:table-cell;padding-bottom:10px;width:10px;vertical-align:top;">
                          <section class="bc" style="width: 0px; height: 0px; border-top: 10px solid rgb(39, 0, 180); border-left: 15px solid rgb(39, 0, 180); border-bottom: 10px solid rgb(39, 0, 180); margin-top: 19px; border-right-color: rgb(39, 0, 180);"></section>
                        </section>
                        <section style="display:table-cell;padding-bottom:10px;width:60px;">
                          <section style="width:60px;height:60px;border-radius:50%;overflow:hidden;">
                            <img style="width:60px;height:60px;border-radius:50%;" src="http://tool.chinaz.com/template/default/temp/Chother/img/g002.png">
                          </section>
                        </section>
                      </section>
                    </section>
                  </section>
                </div>
                <div id="Chother_004" class="item" style="display: block;">
                  <section>
                    <section style="width:100%;">
                      <section style="display: -webkit-box;width: 100%;-webkit-box-align: stretch;margin-bottom:10px;">
                        <section style="width: 55px;word-break:break-all;word-wrap:break-word;padding-right: 15px;">
                          <section style="width:40px;height:40px;border-radius: 50%;overflow: hidden;"><img src="http://tool.chinaz.com/template/default/temp/content/img/g009.png" style="width: 100%; height: 100%; object-fit: cover; display: block; border-radius: 50%; margin: 0px; padding: 0px; max-width: 100%;"
                              alt="聚合支付微信编辑器"></section>
                        </section>
                        <section style="-webkit-box-flex:1;word-break:break-all;word-wrap:break-word;">
                          <section style="padding: 10px;background-color: #ECFBFF;border: 2px solid #D7F1FF;border-radius: 6px;">
                            <section style="margin-left: -18px;width: 12px;height: 12px;background-color: #ECFBFF;border: 2px solid #D7F1FF;border-top: 0 none;border-right: 0 none;-webkit-transform: rotate(45deg);"></section>
                            <section style="margin-top:-16px;">
                              <p style="margin:0;">喂！来玩雪吧？</p>
                            </section>
                          </section>
                        </section>
                        <section style="width: 65px;"></section>
                      </section>
                      <section style="display: -webkit-box;width: 100%;-webkit-box-align: stretch;margin-bottom:10px;">
                        <section style="width: 65px;"></section>
                        <section style="-webkit-box-flex:1;word-break:break-all;word-wrap:break-word;">
                          <section style="padding: 10px;background-color: #ECFBFF;border: 2px solid #D7F1FF;border-radius: 6px;">
                            <section style="float:right;margin-right: -18px;width: 12px;height: 12px;background-color: #ECFBFF;border: 2px solid #D7F1FF;border-bottom: 0 none;border-left: 0 none;-webkit-transform: rotate(45deg);"></section>
                            <section style="margin-top:-16px;padding-top:16px;">
                              <p style="margin:0;"> 好呀！你看我美么。</p>
                            </section>
                          </section>
                        </section>
                        <section style="width: 55px;word-break:break-all;word-wrap:break-word;padding-left: 15px;">
                          <section style="width:40px;height:40px;border-radius: 50%;overflow: hidden;"><img src="http://tool.chinaz.com/template/default/temp/content/img/g010.png" style="height: 100%; object-fit: cover; display: block; border-radius: 50%; padding: 0px; margin: 0px; width: 100%;"
                              alt="聚合支付微信编辑器"></section>
                        </section>
                      </section>
                    </section>
                  </section>
                </div>
    
    
    
    
    
    
              </div>
    
    
            </div>
    
    
    
    
          </div><div class="edit-main ib h-700">
            <div class="h-700" style="padding: 10px;padding-bottom: 0;overflow: hidden;">
              <div style="height: 630px;" id="editor"></div>
            </div>
          </div>
        </div>
    
    
      </div>
    

    </dic>
  </div>

  
  <div class="ChatPhonewrp">
    <div class="ChatPhone-wrp">
      <div class="Cprev-closed Cprevjs"></div>
      <div class="Chatprev">
        <div class="Chatprev-hd">预览效果</div>
        <div class="Chatprev-bd">
          <div class="phoneContainer Chatcon">
            <div class="right_content">
              <div class="right_part" id="mobileview" style="padding: 0 5px;">
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="ChatPhonebg"></div>
  </div>
  
  
  <%@ include file="../include/bodyLink.jsp"%>
  <!-- 加载百度编辑器 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/js/edit/themes/default/dialogbase.css">
  <script src="${pageContext.request.contextPath}/js/edit/ueditor.config.js"></script>
  <script src="${pageContext.request.contextPath}/js/edit/ueditor.all.js"></script>
  <script src="${pageContext.request.contextPath}/js/plugin/colorPicker/colorPicker.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/js/plugin/colorPicker/css/colorpicker.css" type="text/css" />
  <script>
  
    // 下拉列表联动
    var target = $('#target');
    function changeDocType(type) {
      if (type == '') {
        target.html('<option value="">全部</option>').renderSelectBox();
        return;
      }
      // 寻找对应类型隐藏select
      var dataSelect = $('[dataType="' + type + '"]');
      target.html(dataSelect.html()).renderSelectBox();
    }
    $('[name="protocolManagement.type"]').trigger('change');

    
    
    var articleTextArea = $('#myEditor');
    var editor = null;
    window.DialogLoad = function() {
      // 实例化 UEditor 编辑器
      editor = UE.getEditor("editor", {
        autoHeightEnabled: false,
        allowDivTransToP: false,
        toolbars: [[
              'source', '|',
              'undo', 'redo', '|',
              'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', '|',
              'fontfamily', 'fontsize', 'forecolor', 'backcolor', '|',
              'insertorderedlist', 'insertunorderedlist', 'selectall', '|',
              'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
              'link', 'unlink', 'anchor', '|',
              'insertimage', 'emotion', 'scrawl', 'attachment', 'map', 'template', 'background'
            ]]
       });
      // 编辑器ready事件
      editor.ready(function () {
        // 编辑页面, 初始化值到编辑器内
        // editor.setContent('value');

        // 编辑器内容改变事件
        editor.addListener('contentChange', function () {
        	articleTextArea.val(editor.getContent());
        });
        // 原件点击,加入到编辑器
        $(".item", listbox).click(function (event) {
          var html = $(this).html();
          //var item = $(this).parents('.item').length === 0 ? $(this) : $(this).parents('.item');
          editor.execCommand("insertHtml", html);
        })
      });
    };

    
    var listbox = $('.right-side');
    var article = $('#article_content');
    // 更改颜色
    function setTempColor(color) {
      listbox.find(".bgc").css("backgroundColor", color);
      listbox.find(".fc").css("color", color);
      listbox.find(".bc").css("borderColor", color);
      listbox.find(".btc").css("borderTopColor", color);
      listbox.find(".brc").css("borderRightColor", color);
      listbox.find(".bbc").css("borderBottomColor", color);
      listbox.find(".blc").css("borderLeftColor", color);
    } 
    // 初始化颜色选择器
    $('#colorPicker').ColorPicker({
      color: '#29dba9',
      onChange: function(hsb, hex, rgb) {
        setTempColor('#' + hex);
      }
    });
    
    // 预设颜色
    $('.coloritem').click(function() {
    	setTempColor($(this).attr('color'));
    });
    
    // 上一个激活
    var prevType = 'title';
    // 切换tab
    $('.itemTab-list li').click(function() {
      var type = $(this).attr('data-type');
      if (type === prevType) {
        return;
      }
      // 取消上一个
      $('.itemTab-list li[data-type="' + prevType + '"]').removeClass('curt');
      $('.content2[data-type="' + prevType + '"]').removeClass('curt');
      // 选中当前
      $(this).addClass('curt');
      $('.content2[data-type="' + type + '"]').addClass('curt');
      prevType = type;
    });


    // 预览按钮
    $('.previous').click(function() {
      var html = editor.getContent();
     if(html){
      html = '<div class="fotwrap"><span class="pr10">2016-01-01</span><a href="javascript:" class="bz">聚合支付</a></div>'+html+'<div class="fotwrap"><span class="pr5">阅读</span><span>999</span><span class="flow pl10"><em></em><span class="num pl5">58</span></span><a class="ts fr">投诉</a></div>'
     }
      jQuery("#mobileview").html(html);
      $(".ChatPhonewrp").show();
    });
    // 预览关闭
    $('.Cprev-closed').click(function() {
      $(".ChatPhonewrp").hide();
    });
    
    
    
    
    
    

  </script>
</body>
</html>
