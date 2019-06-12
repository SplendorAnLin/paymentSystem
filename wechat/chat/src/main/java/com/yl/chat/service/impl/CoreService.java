package com.yl.chat.service.impl;

import com.yl.chat.message.resp.TextMessage;
import com.yl.chat.service.UserService;
import com.yl.chat.utils.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 核心服务类
 *
 * @author
 * @date 2017年06月23日
 */
public class CoreService {

    private static Logger log = LoggerFactory.getLogger(CoreService.class);

    private static UserService userService;

    /**
     * 处理微信发来的请求
     *
     * @param request
     * @return
     */
    public static String processRequest(HttpServletRequest request, HttpServletResponse response) {
        String respMessage = null;
        try {
            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                // 接收用户发送的文本消息内容
                String content = requestMap.get("Content");
                respMessage = "您好！如果您在使用中遇到任何问题请于我们联系";
                //事件推送
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                String eventType = requestMap.get("Event");
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respMessage = "聚合支付，随心所付！让聚合支付成为您身边真正的支付专家！\n欢迎访问<a href=\"https://www.bank-pay.com\">公司主页</a>";
                }
                // 取消订阅  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    //  取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息  
                }
                // 自定义菜单点击事件  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // 事件KEY值，与创建自定义菜单时指定的KEY值对应  
                    String eventKey = requestMap.get("EventKey");
                    if (eventKey.equals("21")) {
                        respMessage = "微信交易订单点击！";
                    } else if (eventKey.equals("22")) {
                        respMessage = "微信结算订单点击！";
                    }
                    respMessage = respMessage + fromUserName;
                }
            } else {//默认消息
                respMessage = defaultMSG();
            }
            textMessage.setContent(respMessage);
            respMessage = MessageUtil.textMessageToXml(textMessage);
        } catch (Exception e) {
            log.info("微信信息回复出现异常:{}", e);
        }
        return respMessage;
    }


    /**
     * 默认发送此文本
     *
     * @return
     */
    public static String defaultMSG() {
        StringBuffer contentMsg = new StringBuffer();
        contentMsg.append("欢迎访问<a href=\"https://www.bank-pay.com\">公司主页</a>").append("\n");
        contentMsg.append("我们专注于为商户构建安全、快捷、独立的支付系统，期待与您合作！").append("\n");
        contentMsg.append("如果您在使用中遇到任何问题请于我们联系").append("\n");
//        contentMsg.append("电话：400-860-7199");
        return contentMsg.toString();
    }

    /**
     * emoji表情转换(hex -> utf-16)
     *
     * @param hexEmoji
     * @return
     */
    public static String emoji(int hexEmoji) {
        return String.valueOf(Character.toChars(hexEmoji));
    }

    // 创建图文消息
//	NewsMessage newsMessage = new NewsMessage();
//	newsMessage.setToUserName(fromUserName);
//	newsMessage.setFromUserName(toUserName);
//	newsMessage.setCreateTime(new Date().getTime());
//	newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
//	newsMessage.setFuncFlag(0);

//	List<Article> articleList = new ArrayList<Article>();
    // 单图文消息
//	if ("1".equals(content)) {
//		Article article = new Article();
//		article.setTitle("微信公众帐号开发教程Java版");
//		article.setDescription("方便PICC信息技术人员以及公司其他用户交流运维经验、提供运维技术支持、提高运维服务相应速度和服务质量。");
//		article.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
//		article.setUrl("http://blog.csdn.net/lyq8479");
//		articleList.add(article);
//		// 设置图文消息个数
//		newsMessage.setArticleCount(articleList.size());
//		// 设置图文消息包含的图文集合
//		newsMessage.setArticles(articleList);
//		// 将图文消息对象转换成xml字符串
//		respMessage = MessageUtil.newsMessageToXml(newsMessage);
//	}
//	// 单图文消息---不含图片
//	else if ("2".equals(content)) {
//		Article article = new Article();
//		article.setTitle("微信公众帐号开发教程Java版");
//		// 图文消息中可以使用QQ表情、符号表情
//		article.setDescription("柳峰，80后，" + emoji(0x1F6B9)
//				+ "，微信公众帐号开发经验4个月。为帮助初学者入门，特推出此系列连载教程，也希望借此机会认识更多同行！\n\n目前已推出教程共12篇，包括接口配置、消息封装、框架搭建、QQ表情发送、符号表情发送等。\n\n后期还计划推出一些实用功能的开发讲解，例如：天气预报、周边搜索、聊天功能等。");
//		// 将图片置为空
//		article.setPicUrl("");
//		article.setUrl("http://blog.csdn.net/lyq8479");
//		articleList.add(article);
//		newsMessage.setArticleCount(articleList.size());
//		newsMessage.setArticles(articleList);
//		respMessage = MessageUtil.newsMessageToXml(newsMessage);
//	}
//	// 多图文消息
//	else if ("3".equals(content)) {
//		Article article1 = new Article();
//		article1.setTitle("微信公众帐号开发教程\n引言");
//		article1.setDescription("");
//		article1.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
//		article1.setUrl("http://blog.csdn.net/lyq8479/article/details/8937622");
//
//		Article article2 = new Article();
//		article2.setTitle("第2篇\n微信公众帐号的类型");
//		article2.setDescription("");
//		article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
//		article2.setUrl("http://blog.csdn.net/lyq8479/article/details/8941577");
//
//		Article article3 = new Article();
//		article3.setTitle("第3篇\n开发模式启用及接口配置");
//		article3.setDescription("");
//		article3.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
//		article3.setUrl("http://blog.csdn.net/lyq8479/article/details/8944988");
//
//		articleList.add(article1);
//		articleList.add(article2);
//		articleList.add(article3);
//		newsMessage.setArticleCount(articleList.size());
//		newsMessage.setArticles(articleList);
//		respMessage = MessageUtil.newsMessageToXml(newsMessage);
//	}
//	// 多图文消息---首条消息不含图片
//	else if ("4".equals(content)) {
//		Article article1 = new Article();
//		article1.setTitle("微信公众帐号开发教程Java版");
//		article1.setDescription("");
//		// 将图片置为空
//		article1.setPicUrl("");
//		article1.setUrl("http://blog.csdn.net/lyq8479");
//
//		Article article2 = new Article();
//		article2.setTitle("第4篇\n消息及消息处理工具的封装");
//		article2.setDescription("");
//		article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
//		article2.setUrl("http://blog.csdn.net/lyq8479/article/details/8949088");
//
//		Article article3 = new Article();
//		article3.setTitle("第5篇\n各种消息的接收与响应");
//		article3.setDescription("");
//		article3.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
//		article3.setUrl("http://blog.csdn.net/lyq8479/article/details/8952173");
//
//		Article article4 = new Article();
//		article4.setTitle("第6篇\n文本消息的内容长度限制揭秘");
//		article4.setDescription("");
//		article4.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
//		article4.setUrl("http://blog.csdn.net/lyq8479/article/details/8967824");
//
//		articleList.add(article1);
//		articleList.add(article2);
//		articleList.add(article3);
//		articleList.add(article4);
//		newsMessage.setArticleCount(articleList.size());
//		newsMessage.setArticles(articleList);
//		respMessage = MessageUtil.newsMessageToXml(newsMessage);
//	}
//	// 多图文消息---最后一条消息不含图片
//	else if ("5".equals(content)) {
//		Article article1 = new Article();
//		article1.setTitle("第7篇\n文本消息中换行符的使用");
//		article1.setDescription("");
//		article1.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
//		article1.setUrl("http://blog.csdn.net/lyq8479/article/details/9141467");
//
//		Article article2 = new Article();
//		article2.setTitle("第8篇\n文本消息中使用网页超链接");
//		article2.setDescription("");
//		article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
//		article2.setUrl("http://blog.csdn.net/lyq8479/article/details/9157455");
//
//		Article article3 = new Article();
//		article3.setTitle("如果觉得文章对你有所帮助，请通过博客留言或关注微信公众帐号xiaoqrobot来支持柳峰！");
//		article3.setDescription("");
//		// 将图片置为空
//		article3.setPicUrl("");
//		article3.setUrl("http://blog.csdn.net/lyq8479");
//
//		articleList.add(article1);
//		articleList.add(article2);
//		articleList.add(article3);
//		newsMessage.setArticleCount(articleList.size());
//		newsMessage.setArticles(articleList);
//		respMessage = MessageUtil.newsMessageToXml(newsMessage);
//	}
}