package com.yl.trans.front.socket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/system.properties")
public class SocketServerConfig {

	@Value("${trans.front.socket.port}")
	private int port ;

	@Bean
	public SocketThread initSocket(){
		SocketThread ist = new SocketThread(port);
		ist.start();
		return ist;
	}
}
