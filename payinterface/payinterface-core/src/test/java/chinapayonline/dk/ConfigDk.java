package chinapayonline.dk;

import java.io.InputStream;
import java.util.Properties;

/**
 * sdk配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月13日
 * @version V1.0.0
 */
public class ConfigDk {

	private static ConfigDk instance = null;
	
	private Properties properties = null;
	
	private ConfigDk() {
		init();
	}
	
	public static ConfigDk getInstance() {
		if (instance == null) {
			instance = new ConfigDk();
		}
		return instance;
	}
	
	/**
	 * 初始化配置文件
	 */
	public void init(){
		
		try{
			InputStream is = ConfigDk.class.getResourceAsStream("cp_config.properties");
			properties = new Properties();
			properties.load(is);
			
		}catch (Exception e){
			throw new RuntimeException("Failed to get properties!");
		}
	}
	
	/**
	 * 根据key值取得对应的value值
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		return properties.getProperty(key);
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}
}
