package com.yl.chat.context;

import com.yl.mq.consumer.client.ConsumerClient;
import com.yl.mq.consumer.client.config.ConsumerClientConfig;
import com.yl.mq.consumer.client.listener.ConsumerMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.Properties;

/**
 * 消费者配置
 *
 * @author AnLin
 * @since 2017年4月21日
 */
@Configuration
public class ConsumerConfig extends ConsumerClientConfig {

    @Resource
    private ConsumerMessageListener changesFundsListener;

    @Resource
    private ConsumerClient consumerClient;

    private static Properties prop = new Properties();

    static {
        try {
            prop.load(ConsumerConfig.class.getClassLoader().getResourceAsStream("consumer-client.proptites"));
        } catch (IOException e) {

        }
    }

    @Bean
    @Override
    public boolean listener() {
        String topic = prop.getProperty("topic");
        consumerClient.subscribe(topic, changesFundsListener);
        return true;
    }
}