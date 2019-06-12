package com.yl.boss.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import com.yl.boss.Constant;
import com.yl.boss.entity.Authorization;

/**
 * Session监听器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
public class AuthorityListener implements HttpSessionAttributeListener {

	Map<String, HttpSession> map = new HashMap<String, HttpSession>();
  
    public void attributeAdded(HttpSessionBindingEvent event) {
    	
       String attributeName = event.getName();       
       
       if(attributeName.equals(Constant.SESSION_AUTH)){    	   
    	   
    	   Authorization auth = (Authorization)event.getValue();    	   
    	   String userName = auth.getUsername();    	   
    	   
    	   HttpSession beforeSession = map.get(userName);
    	   
           if(beforeSession!=null){        	   
        	   Authorization sameAuth = (Authorization) beforeSession.getAttribute(Constant.SESSION_AUTH);
        	   sameAuth.setRepeat(true);
        	   beforeSession.setAttribute(Constant.SESSION_AUTH, sameAuth);            
           }
           map.put(auth.getUsername(), event.getSession());   // map中始终维护着同一用户名的一个session信息，后登录的会覆盖之前
           													  // 之前的session未被销毁，但被打上"重复登录"标记      
       }
    }

	public void attributeRemoved(HttpSessionBindingEvent event) {
			
		String attributeName = event.getName(); 				
		
		if(attributeName.equals(Constant.SESSION_AUTH)){ 	
						
			Authorization auth = (Authorization)event.getValue(); 
			String userName = auth.getUsername(); 
			
			HttpSession beforeSession = map.get(userName); 
			
			if(beforeSession!=null){			
				String sessionId = beforeSession.getId();			
				if(sessionId.equals(auth.getSessionId())){
					map.remove(auth.getUsername());
				}			
			}
		}		
	}

	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
	}

}
