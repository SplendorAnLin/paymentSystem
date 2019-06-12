package com.yl.client.front.context;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

//import com.lefu.dsmclient.mongodb.LefuMongoClient;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

/**
 * Mongodb数据源配置
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月24日
 * @version V1.0.0
 */
@Configuration
@PropertySource("classpath:/mongodb.properties")
public class MongoDBConfig extends AbstractMongoConfiguration {
	
	private static String MAPPING_BASE_PACKAGE = "";
	@Value("${mongodb.addresses}")
	private String addresses;
	@Value("${mongodb.databaseName}")
	private String databaseName;
	@Value("${mongodb.username}")
	private String username;
	@Value("${mongodb.password}")
	private String password;

	@Override
	public Mongo mongo() throws Exception {
		List<ServerAddress> seeds = new ArrayList<>();
		for (String address : addresses.split(",")) {
			String[] addressInfo = address.split(":");
			seeds.add(new ServerAddress(addressInfo[0], Integer.parseInt(addressInfo[1])));
		}
		return new MongoClient(seeds);
	}

	@Override
	protected String getDatabaseName() {
		return databaseName;
	}

	@Override
	protected UserCredentials getUserCredentials() {
		return new UserCredentials(username, password);
	}

	@Override
	protected String getMappingBasePackage() {
		return MAPPING_BASE_PACKAGE;
	}

}
