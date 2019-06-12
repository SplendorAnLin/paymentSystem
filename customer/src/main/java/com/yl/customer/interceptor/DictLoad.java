package com.yl.customer.interceptor;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.customer.Constant;
import com.yl.mail.MailType;
import com.yl.mail.MailUtils;

import net.sf.json.JSONObject;

public class DictLoad implements ServletContextListener {
	private static final Logger logger = LoggerFactory.getLogger(DictLoad.class);
	
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(
					new InputStreamReader(DictLoad.class.getClassLoader().getResourceAsStream("system.properties")));
		} catch (IOException e) {
			logger.error("DictLoad load Properties error:", e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		long startTime = System.currentTimeMillis();
		try {
			Set<byte[]> info = (Set<byte[]>)CacheUtils.execute(1, new CacheUtils.CacheCallback(){
					  public Set<byte[]> doCallback(CacheUtils.Redis redis) {
						  return redis.keys(StringUtils.serialize("Dictionary.DICTIONARY_TYPE.*", CacheUtils.DEFAULT_CHARSET));
					  }});
			if (info.size()>1){
				Constant.DICTS=new HashMap<>();
                ThreadPoolExecutor pool = new ThreadPoolExecutor(20, 50, 100L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(500));
				for (byte[] keyByte : info) {
					pool.submit(new WorkThread(keyByte));
				}
				logger.info("字典加载中...");
				pool.shutdown();
				while (!pool.isTerminated()){
					TimeUnit.MILLISECONDS.sleep(100);
				}
			}
			logger.info("字典初始化成功,用时:" + (System.currentTimeMillis() - startTime)+ "ms");
		} catch (Exception e) {
			logger.error("字典初始化异常:{}",e);
			try {
				MailUtils.send(MailType.EXCEPTION, "【商户系统】字典加载异常",e.getMessage(), prop.getProperty("mail.from"));
			} catch (MessagingException | IOException e1) {
				logger.error("邮箱发送异常:{}",e);
			}
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
		//
	}

}
class WorkThread implements Runnable{
	private byte[] keyByte;
	public WorkThread(byte[] keyByte){
		this.keyByte = keyByte;
	}

	@Override
	public void run() {
		Constant.DICTS.put(StringUtils.deserialize(keyByte, CacheUtils.DEFAULT_CHARSET).replace("Dictionary.DICTIONARY_TYPE.",""),
				CacheUtils.get(1, StringUtils.deserialize(keyByte, CacheUtils.DEFAULT_CHARSET), JSONObject.class, true));
	}
	
}
