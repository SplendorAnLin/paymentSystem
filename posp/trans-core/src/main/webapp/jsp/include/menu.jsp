<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html>
<head>        
	<link rel="stylesheet" type="text/css" href="../../js/dtree/dtree.css">
	<script type='text/javascript' src='../../js/dtree/dtree.js'></script>
	
</head>

<body style="background-color:#eee">

	<div class="dtree">
	
		<p><a href="javascript: d.openAll();">open all</a> | <a href="javascript: d.closeAll();">close all</a></p>
	
		<script type="text/javascript">
			d = new dTree('d');
			d.add(0,-1,'POSP平台');
				d.add(1,0,'交易相关');					
					//d.add(10,1,'手工入账','../9002003/signinBatch.jsp','','main');
				d.add(2,0,'接口相关');
					d.add(23,2,'P310000');
						d.add(203,23,'银行终端签到','../P310000/signinBatch.jsp','','main');
						d.add(204,23,'银行联通测试','../P310000/echoTest.jsp','','main');
						d.add(205,23,'银行初始化密钥','../P310000/initSecretKey.jsp','','main');
					d.add(24,2,'P440303');
					d.add(241,24,'银行终端签到','../P440303/signinBatch.jsp','','main');
				d.add(9,0,'短信网关测试','../smsSender.jsp','','main');
				d.add(10,0,'缓存维护','../cache/ehcache.jsp','','main');
			document.write(d);
	
		</script>

	</div> 
    
  </body>
</html>
