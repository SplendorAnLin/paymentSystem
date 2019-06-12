package com.yl.chat.web.controller;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.lefu.commons.utils.Page;
import com.yl.boss.api.interfaces.ProtocolInterface;


@Controller
public class ChelpController {
 
	 @Resource
	 ProtocolInterface  protocolInterface;

	 @RequestMapping("wechatindex")
	 public String wechatindex(HttpServletRequest request, HttpServletResponse response) {
		 return "center/appHelpIndex";
	 }
		
	 /**
	  * 帮助中心列表
	  * @param sort
	  * @return
	  */
	 @RequestMapping("chatlist")
   	 public String wechatlist(HttpServletRequest request, HttpServletResponse response, String sort){
	 	Map<String, Object> param = new HashMap<>();
		param.put("status", "TRUE");
		param.put("sort", "WECHAT_HELP");
		Page page = protocolInterface.getProtolList(param);
		request.setAttribute("page", page);
		return "center/appHelpList";
	}
	 
	/** 
	 * 帮助中心内容
	 */
	@RequestMapping("chathelp")
	public String  wechathelp(HttpServletRequest request, HttpServletResponse response, String sort, int id){
	  	Map<String, Object> wechatlist = protocolInterface.selectWechathelp("WECHAT_HELP", id, "TRUE");
		String title = wechatlist.get("title").toString();
		String content = wechatlist.get("content").toString().replaceAll("\n", "");
		request.setAttribute("title",title);
		request.setAttribute("content",content);
	   	return "center/AppHelp";//消息内容
	}
}