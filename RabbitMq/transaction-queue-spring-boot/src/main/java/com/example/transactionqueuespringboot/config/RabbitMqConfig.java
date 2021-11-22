package com.example.transactionqueuespringboot.config;

import javax.annotation.PostConstruct;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Bean("rabbitTransactionManager")
  public RabbitTransactionManager rabbitTransactionManager(
      CachingConnectionFactory connectionFactory) {
    return new RabbitTransactionManager(connectionFactory);
  }

  @PostConstruct
  private void init() {
    //启用事务模式,不能开确认回调
    //rabbitTemplate.setConfirmCallback(this);
    rabbitTemplate.setChannelTransacted(true);
    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
  }

}
