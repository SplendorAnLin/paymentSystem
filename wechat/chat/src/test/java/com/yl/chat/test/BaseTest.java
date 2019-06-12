package com.yl.chat.test;



import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.lefu.commons.utils.Page;
import com.yl.boss.api.interfaces.ProtocolInterface;
import com.yl.chat.context.SpringRootConfig;
import com.yl.chat.service.MessageService;
import com.yl.customer.api.interfaces.CustOperInterface;
import com.yl.dpay.hessian.service.DpayFacade;
import com.yl.online.trade.hessian.OnlineTradeQueryHessianService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringRootConfig.class })
@ActiveProfiles("development")
@TransactionConfiguration(defaultRollback = false)
public class BaseTest {
	
	@Resource
	MessageService messageService;
	@Resource
    OnlineTradeQueryHessianService onlineTradeQueryHessianService;
	@Resource
	private CustOperInterface custOperInterface;
	@Resource
	DpayFacade dpayFacade;
   @Resource
   ProtocolInterface  protocolInterface;

//交易    
/*	 @Test
    public void trantest(){
  		    int pageSize=10;
			int currentPage=1;	
			Map<String, Object> params = new HashMap<>();
			params.put("ownerId", "C100000");
			params.put("pageSize", pageSize);
			params.put("currentPage",(currentPage-1)*pageSize);
			params.put("startCompleteDate", "2016-01-05 ");
			params.put("endCompleteDate", "2017-06-30 ");
  	    List<Map<String, Object>>  tradeOrders=dpayFacade.selectRequest(params);
    }
	*/
	//详情
	/* @Test
    public void trantest(){
		 Map<String, Object> params = new HashMap<>();
		 params.put("Payer", "C100000");
		 params.put("code", "TO-20170301-101615821901");
		List<Map<String, Object>> maps=onlineTradeQueryHessianService.selectTradeOrderDetailed(params);
    }*/
	//结算条数
/*	
	@Test
	public void paytest(){
		 Map<String, Object> params = new HashMap<>();
		 params.put("Payer", "C100000");
		Map<String,Object> totalResultMap =onlineTradeQueryHessianService.selectOrderCount(params);//总条数
		int totalResultMap =256;
		if(totalResultMap > 0){
			Page page = new Page();
			//设置总条数
			page.setTotalResult(totalResultMap);
			
			//设置当前页
			
			page.setCurrentPage(Integer.parseInt("1"));
			
			
			params.put("startCompleteDate", "2016-01-05 ");
			params.put("endCompleteDate", "2017-06-30 ");
			
		List<Map<String, Object>>	payorderlist=onlineTradeQueryHessianService.findTradeOrder(page,params);	
			
		}	 
	
		 
	}
	*/
	
	
/*	@Test
  public void paytets(){
		Map<String, Object> params = new HashMap<>();
		params.put("ownerId", "C100000");
	    Map<String, Object>	payorderlist=dpayFacade.selectRequestsum(params);
	    int totalResultMap =2938;
	    Page page=new Page();
	  //设置总条数
		page.setTotalResult(totalResultMap);
		
		//设置当前页
		
		page.setCurrentPage(Integer.parseInt("1"));
		Map<String, Object> map=new HashMap<>();
		map.put("page",page );
		map.put("ownerId", "C100000");
		List list=dpayFacade.findrequest(map);
		System.out.println(JsonUtils.toJsonString(list));
		
	}	*/
	
	@SuppressWarnings("unused")
	@Test
	 public void  helptest(){
		Map<String, Object> prolist=protocolInterface.wapProtocol("WECHAT_REGISTER", "PACT");
	/*	Map<String, Object> params = new HashMap<>();
		params.put("status", "OPEN");
		Page page = protocolInterface.getProtolList(params);
		Map<String, Object> m = new HashMap<>();
			m.put("ProtolAll", page);*/
		
	}
	
	
	/*public static Map<String, Object> transBeanMap(Object obj) throws Exception {  
        if(obj == null){  
            return null;  
        }          
        Map<String, Object> map = new HashMap<String, Object>();  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
                if (!key.equals("class")) {  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
                    map.put(key, value);  
                }  
            }  
        return map;  
    }  
*/
	
	
	
	
/*	
	@Test
	public void testConfig() {
		System.out.println(ChatUtil.getSysAccessToken());
		
	}*/
//	String oper="oJgDzstvLoBGeMJFWH8Z6O8UTXjM";
//	@Test
//	public void pushMsg(){
//		PushMessage message=new PushMessage();
//		SimpleDateFormat time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		Map<String,String> info=new HashMap<>();
//		info.put("first", "您好，系统发生异常信息：");
//		info.put("time", time.format(new Date()));
//		info.put("performance", "间歇型抽风");
//		info.put("remark", "所属系统：BOSS");
//		for (Object openId : ChatUtil.getGroupUserByName("技术组")) {
//			message.Fundreminder(openId.toString(), Constant.templeEX,"http://m.bank-pay.com", info);
//		}
//	}
//	
//	public void aaa() {
//		
//	}
	
/*	@Test
	public void getMenu(){
		Map<String,String> info=new HashMap<>();
		info.put("first", "您于有一笔现金帐户充值到账：");
		info.put("date", "2013/10/29 15:24");
		info.put("adCharge", "￥100.00");
		info.put("cashBalance","￥200.00");
		info.put("remark", "点击立即查阅您的帐户财务记录");
		messageService.send("oJgDzstvLoBGeMJFWH8Z6O8UTXjM", Constant.templeFunds, "http://m.bank-pay.com", info,"2017/06/27");
		//HttpUtils.sendJsonReq("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx41a15733bd0f9f08&redirect_uri=http%3A%2F%2Fyl.zk1006.cn%2Fcallback&response_type=code&scope=snsapi_base&state=123#wechat_redirect");
		
	}*/
/*	@Test
	public void test(){
		Map<String, Object> params=new HashMap<String, Object>();
	 params.put("ownerId", "C100000");
	 params.put("requestNo", "DF-170227-100839000102");
	 dpayFacade.selectRequestDetailed(params);
	}*/
	
	
	
}
