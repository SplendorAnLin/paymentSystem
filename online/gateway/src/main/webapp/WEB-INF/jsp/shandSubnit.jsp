<%@page import ="java.util.Enumeration,java.util.Map"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Iterator"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>loading...</title>
<script type="text/javascript" src="js/paymentjs.js"></script>
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
</head>
<body>
<script>
    function wap_pay() {
        var responseText = $("#credential").text();
        console.log(responseText);
        paymentjs.createPayment(responseText, function(result, err) {
                        console.log(result);
                        console.log(err.msg);
                        console.log(err.extra);
                        
        });
//        setTimeout(closeWin, 1500);
   }
   function closeWin(){
	   this.window.opener = null;  
       window.close();
   }
   
</script>
<div style="display: none" >
        <p id="credential">${params.JWP_ATTR}</p>
</div>
</body>

<script>
window.onload=function(){
        wap_pay();
};
</script>
</html>