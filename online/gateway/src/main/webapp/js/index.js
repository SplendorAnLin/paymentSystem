// JavaScript Document
//获取class	
function getByClass(oParent, sClass)
{
	var aEle=oParent.getElementsByTagName('*');
	var aResult=[];
	var re=new RegExp('\\b'+sClass+'\\b', 'i');
	var i=0;
	
	for(i=0;i<aEle.length;i++)
	{
		//if(aEle[i].className==sClass)
		//if(aEle[i].className.search(sClass)!=-1)
		if(re.test(aEle[i].className))
		{
			aResult.push(aEle[i]);
		}
	}
	
	return aResult;
}
//获取样式
function getStyle(obj,attr){
if(obj.currentStyle){
	return obj.currentStyle[attr];
	}
else{
	return getComputedStyle(obj,false)[attr];
	}	
}
//运动		
function startMove(obj, json, fn)
{
	clearInterval(obj.timer);
	obj.timer=setInterval(function (){
		var bStop=true;		//这一次运动就结束了——所有的值都到达了
		for(var attr in json)
		{
			//1.取当前的值
			var iCur=0;
			
			if(attr=='opacity')
			{
				iCur=parseInt(parseFloat(getStyle(obj, attr))*100);
			}
			else
			{
				iCur=parseInt(getStyle(obj, attr));
			}
			
			//2.算速度
			var iSpeed=(json[attr]-iCur)/8;
			iSpeed=iSpeed>0?Math.ceil(iSpeed):Math.floor(iSpeed);
			
			//3.检测停止
			if(iCur!=json[attr])
			{
				bStop=false;
			}
			
			if(attr=='opacity')
			{
				obj.style.filter='alpha(opacity:'+(iCur+iSpeed)+')';
				obj.style.opacity=(iCur+iSpeed)/100;
			}
			else
			{
				obj.style[attr]=iCur+iSpeed+'px';
			}
		}
		
		if(bStop)
		{
			clearInterval(obj.timer);
			
			if(fn)
			{
				fn();
			}
		}
	}, 30)
}	


//选项卡
function lfpaytab()
{
	var oLFPayId=document.getElementById('LFPayId');
	var aLFPayTab=getByClass(oLFPayId,'LFPayTab')[0];
	var aLFPayTabA=aLFPayTab.getElementsByTagName('a');
	var aLFPayCC=getByClass(oLFPayId,'LFPayCC');
	var i=0;
    var navNIndex=0;
	var reIndex=null;
	var oLFPayId=document.getElementById('LFPayId');
	var oForms=document.getElementById('LFFormBk');
	var aLFPayCC=getByClass(oLFPayId,'LFPayCC');
	var aLFPayCCUl0=aLFPayCC[navNIndex].getElementsByTagName('ul')[0];
	var oLFPayCCElse0=aLFPayCC[navNIndex].getElementsByTagName('p')[0];
	var oLFPayCCElseA0=oLFPayCCElse0.getElementsByTagName('a')[0]
	var s=0;
	var aLFPayCCLi0=aLFPayCC[navNIndex].getElementsByTagName('label');
	var aLFPayCCLiAll=oLFPayId.getElementsByTagName('label');	
	var aLFPayCCLI0=aLFPayCC[navNIndex].getElementsByTagName('li');
	var aLFPayCCLIH0=parseInt((aLFPayCCLI0[0].offsetHeight+10)*Math.ceil(aLFPayCCLI0.length/4));

	for(i=0;i<aLFPayTabA.length;i++)
	{
		aLFPayTabA[i].index=i;
		aLFPayTabA[i].onmouseover=function()
		{
			for(i=0;i<aLFPayTabA.length;i++)
			{
				aLFPayTabA[i].className='';
				aLFPayCC[i].style.display='none';
			}
			this.className='LFPayTabActive';
			aLFPayCC[this.index].style.display='block';
			var  navNIndex=this.index;
			
			function getUnfoldThree()
			{
				
				var aLFPayCCUl0=aLFPayCC[navNIndex].getElementsByTagName('ul')[0];
				var oLFPayCCElse0=aLFPayCC[navNIndex].getElementsByTagName('p')[0];
				var oLFPayCCElseA0=oLFPayCCElse0.getElementsByTagName('a')[0];
				var aLFPayCCLi0=aLFPayCC[navNIndex].getElementsByTagName('label');
				var aLFPayCCLiAll=oLFPayId.getElementsByTagName('label');
				var aLFPayCCLI0=aLFPayCC[navNIndex].getElementsByTagName('li');
				var aLFPayCCLIH0=parseInt((aLFPayCCLI0[0].offsetHeight+10)*Math.ceil(aLFPayCCLI0.length/4));
				for(var i=0;i<aLFPayCCLi0.length;i++)
				{
					aLFPayCCLi0[i].index=i;
					aLFPayCCLi0[i].onclick=function()
					{
						for(var i=0;i<aLFPayCCLiAll.length;i++)
						{
							aLFPayCCLiAll[i].style.borderColor='#ddd';
						}
						this.style.borderColor='#d80119';
						reIndex=this.index;
						
					}
				}
				//alert(aLFPayCCLI0.length)
				if(aLFPayCCLI0.length>16)
				{
					aLFPayCCUl0.style.height=196+"px";
					oLFPayCCElse0.style.display='block';
					oLFPayCCElse0.onclick=function()
					{
						
						
						
						if(s==0)
						{
							startMove(aLFPayCCUl0,{height:aLFPayCCLIH0});
							s=1;
							oLFPayCCElseA0.innerHTML='收起'
						}
						else
						{
							startMove(aLFPayCCUl0,{height:196});
							s=0;
							oLFPayCCElseA0.innerHTML='更多银行';
							if(reIndex){
							if(reIndex>15)
							{
								oForms.reset();
								aLFPayCCLi0[reIndex].style.borderColor='#ddd';
							}
							}
						}
					}
				}
				else
				{
					
					aLFPayCCUl0.style.height='auto'
					oLFPayCCElse0.style.display='none';
				}	
				
			}
							
			getUnfoldThree()
        }
			
	}

function getUnfoldThree()
{
	
	for(var i=0;i<aLFPayCCLi0.length;i++)
	{
		aLFPayCCLi0[i].index=i;
		aLFPayCCLi0[i].onclick=function()
		{
			for(var i=0;i<aLFPayCCLiAll.length;i++)
			{
				aLFPayCCLiAll[i].style.borderColor='#ddd';
			}
			this.style.borderColor='#d80119';
			reIndex=this.index;
		}
	}
	
	if(aLFPayCCLI0.length>16)
	{
		aLFPayCCUl0.style.height=196+"px";
	    oLFPayCCElse0.style.display='block';
		oLFPayCCElse0.onclick=function()
		{
			
			
			
			if(s==0)
			{
				startMove(aLFPayCCUl0,{height:aLFPayCCLIH0});
				s=1;
				oLFPayCCElseA0.innerHTML='收起'
			}
			else
			{
				startMove(aLFPayCCUl0,{height:196});
				s=0;
				oLFPayCCElseA0.innerHTML='更多银行';
				
				if(reIndex){
				if(reIndex>15)
				{
					oForms.reset();
					aLFPayCCLi0[reIndex].style.borderColor='#ddd';
				}
				}
			}
		}
	}
	else
	{
		aLFPayCCUl0.style.height='auto'
		oLFPayCCElse0.style.display='none';
	}	
	
}
				
return getUnfoldThree()
}
lfpaytab();

var flag = 0;
function disDoubleClick(){
	if(flag == 0){
		flag = 1;
		document.getElementById("LFFormBk").submit();
		setTimeout('gatewaySubmit()',8000);
	}
}

function gatewaySubmit(){
	flag = 0;
}