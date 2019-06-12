function getFormJson(form) {
		var o = {};
		var a = $(form).serializeArray();
		$.each(a, function () {
		if (o[this.name] !== undefined) {
		if (!o[this.name].push) {
		o[this.name] = [o[this.name]];
		}
		o[this.name].push(this.value || '');
		} else {
		o[this.name] = this.value || '';
		}
		});
		return o;
	}
function CurentTime(){ 
        var now = new Date();
        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日
       
        var hh = now.getHours();            //时
        var mm = now.getMinutes();          //分
        var ss = now.getSeconds();     //获取当前秒数(0-59)
        var clock = year+"";
       
        if(month < 10)
            clock += "0";
       
        clock += month;
       
        if(day < 10)
            clock += "0";
           
        clock += day;
       
        if(hh < 10)
            clock += "0";
           
        clock += hh;
        if (mm < 10) clock += "0"; 
        clock += mm; 
         if (ss < 10) clock += "0"; 
        clock += ss; 
        return(clock); 
    } 	