var queryForms;
function initPage(currentPage,totalResult,totalPage,entityOrField,queryForm){

	var pageDiv = document.getElementById("page");
	if(pageDiv == undefined){
		return;
	}
	queryForms = queryForm;
	var showTag = 10;	// 分页标签显示数量
	var startTag =  1 ; // 分页标签开始
    var pageStr = "";

    if (totalResult > 0 ){
        pageStr += " <span>\n";
        if (currentPage == 1 ){
            pageStr += " <span class='spanPageStyle'>首页</span>\n";
            pageStr += " <span class='spanPageStyle'>上页</span>\n";
        }else {
            pageStr += " <a class='aPageStyle' href=\"#@\" onclick=\"nextPage(1,"+entityOrField+")\"><span class='spanPageStyle'>首页</span></a>\n";
            pageStr += " <a class='aPageStyle' href=\"#@\" onclick=\"nextPage(" +(currentPage- 1)+","+entityOrField + ")\"><span class='spanPageStyle'>上页</span></a>\n";
        }
        if (currentPage>(showTag/2)){
            startTag = currentPage-5 ;
        }
        var  endTag = Number(currentPage)+Number(showTag/2)- 1 ;
        for ( var  i=startTag; i<=totalPage && i<=endTag; i++){
            if (currentPage==i)
                pageStr += "<span class='spanPageNumSelectedStyle'>" +i+ "</span>\n";
            else
                pageStr += " <a class='aPageStyle' href=\"#@\" onclick=\"nextPage(" +i+ ","+entityOrField+")\"><span class='spanPageNumStyle'>" +i+ "</span></a>\n";
        }
        if (currentPage==totalPage){
            pageStr += " <span class='spanPageStyle'>下页</span>\n";
            pageStr += " <span class='spanPageStyle'>尾页</span>\n";
        }else {
            pageStr += " <a class='aPageStyle' href=\"#@\" onclick=\"nextPage(" +(parseInt(currentPage) + parseInt(1)) +","+entityOrField+ ")\"><span class='spanPageStyle'>下页</span></a>\n";
            pageStr += " <a class='aPageStyle' href=\"#@\" onclick=\"nextPage(" +totalPage+","+entityOrField+ ")\"><span class='spanPageStyle'>尾页</span></a>\n";
        }
        pageStr += " <span class='spanPageStyle'>第" +currentPage+ "页</span>\n";
        pageStr += " <span class='spanPageStyle'>共" +totalPage+ "页</span>\n";
        pageStr += " <span class='spanPageStyle'>共" +totalResult+ "条</span>\n";
        pageStr += " <span class='spanPageStyle'><input onkeypress='onkey(event,"+entityOrField+","+totalPage+")' type='text' style='width:30px;' id='pageNum'/>" +
        		"<a class='aPageStyle' href=\"#@\" onclick=\"getPageNum("+entityOrField+","+totalPage+")\">" +
        				"&nbsp;\n<span class='aPageStyle'>跳</span></a></span>\n";
        pageStr += "</span>\n";
    }
    pageDiv.innerHTML = "";
	pageDiv.innerHTML = pageStr;
}

function nextPage(page,entityOrField){
     if(queryForms){
    	 	 var queryFormAction = queryForms.action;
             var url = queryForms.action;
             if(url.indexOf('?')>-1){url += "&" +(entityOrField? "currentPage" : "currentPage" )+ "=";}
             else{url += "?" +(entityOrField? "currentPage" : "currentPage" )+ "=";}
             queryForms.action = url+page;
             queryForms.submit();
             queryForms.action = queryFormAction;
     }else{
         var url = document.location.origin+document.location.pathname+document.location.search;
         if(url.indexOf('?')>-1){
             if(url.indexOf('currentPage')>-1){
                 var reg = /currentPage=\d*/g;
                 url = url.replace(reg,'currentPage=');
             }else{
                 url += "&" +(entityOrField? "currentPage" : "currentPage" )+ "=";
             }
         }else{url += "?" +(entityOrField? "currentPage" : "currentPage" )+ "=";}
         document.location = url + page;
     }
}

function getPageNum(entityOrField,totalPage){
	var r = /^[0-9]*[1-9][0-9]*$/;
	var pageNum = $('#pageNum').val();
	if(!r.test(pageNum) || Number(pageNum) > Number(totalPage)){
		alert("页码输入错误！");
		return;
	}
	nextPage(pageNum,entityOrField);
}

function onkey(event,entityOrField,totalPage){
    if(event.keyCode=='13'){
		getPageNum(entityOrField,totalPage);
	}
}