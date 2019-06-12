package chinapayonline.df;

import java.io.InputStream;
import java.util.Properties;

/**
 * 代付配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月13日
 * @version V1.0.0
 */
public class ConfigDf {

	private static ConfigDf instance = null;
	
	private Properties properties = null;
	
	private ConfigDf() {
		init();
	}
	
	public static ConfigDf getInstance() {
		if (instance == null) {
			instance = new ConfigDf();
		}
		return instance;
	}
	
	/**
	 * 初始化配置文件
	 */
	public void init(){
		
		try{
			InputStream is = ConfigDf.class.getResourceAsStream("cp_config.properties");
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
