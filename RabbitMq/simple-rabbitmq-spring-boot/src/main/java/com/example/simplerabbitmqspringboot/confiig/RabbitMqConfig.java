package com.example.simplerabbitmqspringboot.confiig;

import com.example.simplerabbitmqspringboot.model.po.UserEntity;
import java.util.HashMap;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 这里演示一个延时队列，不会覆盖已有的属性！如果错误必须重新创建！
 */
@Configuration
public class RabbitMqConfig {

  /**
   * 队列监听器，一般是有个单独包，这里为了省事就这样弄了
   * @param userEntity 接受的内容，自动进行反序列化
   */
  @RabbitListener(queues = "dead.letter.queue")
  public void deadListener(UserEntity userEntity) {
    System.out.println("收到" + userEntity);
  }

  /**
   * 配置序列化方式，使用Jackson的json序列化
   */
  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  /**
   * 延迟队列
   */
  @Bean
  public Queue delayQueue() {
    String queueName = "delay.queue";
    boolean durable = true;
    boolean exclusive = false;
    boolean autoDelete = false;
    HashMap<String, Object> arguments = new HashMap<>(){{
      put("x-dead-letter-exchange", "dead-letter-exchange");  // 交换机
      put("x-dead-letter-routing-key", "dead.letter.out");  // 死信路由键
      put("x-message-ttl", 60000);
    }};
    return new Queue(queueName, durable, exclusive, autoDelete, arguments);
  }

  /**
   * 死信队列，监听这个队列可以收到过期消息
   */
  @Bean
  public Queue deadLetterQueue() {
    String queueName = "dead.letter.queue";
    boolean durable = true;
    boolean exclusive = false;
    boolean autoDelete = false;
    HashMap<String, Object> arguments = null;
    return new Queue(queueName, durable, exclusive, autoDelete, arguments);
  }

  /**
   * 延迟队列死信队列交换机
   */
  @Bean
  public Exchange deadLetterExchange() {
    String queueName = "dead-letter-exchange";
    boolean durable = true;
    boolean autoDelete = false;
    HashMap<String, Object> arguments = null;
    return new TopicExchange(queueName, durable, autoDelete, arguments);
  }

  /**
   * 延迟队列与交换机绑定关系
   */
  @Bean
  public Binding deadLetterInBinding() {
    String destination = "delay.queue";
    String exchange = "dead-letter-exchange";
    String routingKey = "dead.letter.in";
    return new Binding(destination, DestinationType.QUEUE, exchange, routingKey, null);
  }

  /**
   * 交换机放入死信队列的绑定
   */
  @Bean
  public Binding deadLetterOutBinding() {
    String destination = "dead.letter.queue";
    String exchange = "dead-letter-exchange";
    String routingKey = "dead.letter.out";
    return new Binding(destination, DestinationType.QUEUE, exchange, routingKey, null);
  }

}
