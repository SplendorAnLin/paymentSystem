  
(function($){
	jQuery.fn.extend({
		Tab:function(options){
			var settings={
				event:"auto",
				pannel:"auto",
				direction:"auto",
				autoPaly:false,
				container:"",
				autoTime:2000
			};
			if(options){
				$.extend(settings,options);
		    };
		    var $this=this;
		    if("auto"==settings.event){
		    	this.bind("click",function(){
		    		    var elems=$(this).parent().children();
		    			var $index=elems.index($(this));
		    			var pannels=settings.pannel;
		    			doSelect($index,elems,pannels);
		    		});
		    	}
		    	else{
		    		this.bind(settings.event,function(){
		    			var elems=$(this).parent().children();
		    			var $index=elems.index($(this));
		    			var pannels=settings.pannel;
		    			doSelect($index,elems,pannels);
		    		});
		    };
			if(settings.autoPaly){
		    	if(settings.container==""){
		    		alert("请指定自动切换的容器");
		    	}else{
		    		$(settings.container).hover(function(){
			    		clearInterval(autoTimer);
			    	},function(){
			    		var $index=0;
			    		var elems=$(this).parent().children();
			    		autoTimer=setInterval(function(){
			    			elems.each(function(i){
			    				if($(this).hasClass("select")){
			    					$index=i;
			    					return false;
			    				}
			    			});
			    			$index++;
			    			if($index>$this.length-1){
			    				$index=0;
			    			}
			    			doSelect($index,elems,settings.pannel);
			    		},settings.autoTime);
			    	}).trigger("mouseleave");
		    	}
		    };
		    doSelect=function(n,elems,tag){
		    	elems.removeClass("select").eq(n).addClass("select");
		    	$(tag).css("display","none").eq(n).css("display","block");
		    };
		},
		showSelect:function(options){
			var settings={
				event:"hover",
				effect:"auto",
				hideDom:".hideDom"
			};
			if(options){
				$.extend(settings,options);
		    };
		    $this=this;
		    if(settings.event=="hover"){
		    	$this.hover(function(){
		    		$this.addClass("select");
		    		$this.find(settings.hideDom).stop(true,true).slideDown("slow");
		    	},function(){
		    		$this.removeClass("select");
		    		$this.find(settings.hideDom).hide();
		    	});
		    }
		}
	});
})(jQuery);
$(function(){
	$(".Tab1 li").Tab({pannel:".listo",event:"mouseover",autoPaly:true,container:".foucusli .select",autoTime:5000});

});

