package com.orderprocessing.activemqconfig;

import jakarta.jms.Queue;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActiveMQConfig {
    
    @Bean
    public Queue orderqueue() {  
        return new ActiveMQQueue("order.queue");  
    }
    
}
