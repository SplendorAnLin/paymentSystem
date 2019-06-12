<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<!DOCTYPE html>
<html lang="en" class="over-x-h">
<head>
  <meta charset="UTF-8">
  <title></title>
</head>
<body>

  <div class="in-pop" style="width: 400px; padding: 10px;">
    <form>
      <div class="block">
        <span class="lable w-75">会员手机号:</span>
         <div class="b-input w-170">
              <div class="input-wrap w-p-100">
                <input type="text" class="input-text rest-text" value="15527558460">
              </div>
          </div>
      </div>
      <div class="block">
						<span class="lable w-75">设备状态:</span>
						<div class="b-input w-170">
							<div class="input-wrap w-p-100">
								<select class="input-select" name="sysType" id="sysType" onchange="selectSys(this.value);">
									<option value="AGENT">正常</option>
									<option value="CUST">冻结</option>
									<option value="CUST">未使用</option>
								</select>
							</div>
						</div>
					</div>
      <div class="block">
        <span class="lable w-75">终端号:</span>
         <div class="b-input w-170">
              <div class="input-wrap w-p-100">
                <input disabled="disabled" type="text" class="input-text rest-text" value="80805682">
              </div>
          </div>
      </div>
      <div class="block">
        <span class="lable w-75">设备号:</span>
         <div class="b-input w-170">
              <div class="input-wrap w-p-100">
                <input disabled="disabled" type="text" class="input-text rest-text" value="75077256">
              </div>
          </div>
      </div>
      
    </form>
  </div>





<script src="${pageContext.request.contextPath}/plugins/nicEdit.js"></script>
<%@ include file="../include/bodyLink.jsp"%>
<script>


</script>
</body>
</html>