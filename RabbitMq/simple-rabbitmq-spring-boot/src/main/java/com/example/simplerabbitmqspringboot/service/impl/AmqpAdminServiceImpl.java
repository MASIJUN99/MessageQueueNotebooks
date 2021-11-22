package com.example.simplerabbitmqspringboot.service.impl;

import com.example.simplerabbitmqspringboot.service.AmqpAdminService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmqpAdminServiceImpl implements AmqpAdminService {

  @Autowired
  private AmqpAdmin amqpAdmin;

  /**
   * 声明交换机, 注意Exchange别导错包
   * <p>Exchange是个接口, 具体下面有很多实现类, 主要使用Direct, Fanout, Topic</p>
   * <p>全参构造的Exchange比较复杂, 可以看下传入什么参数</p>
   */
  @Override
  public void createExchange(String name) {
    boolean durable = true;
    boolean autoDelete = false;
    Map<String, Object> arguments = new HashMap<>();
    Exchange exchange = new DirectExchange(name, durable, autoDelete, arguments);
    amqpAdmin.declareExchange(exchange);

  }

  /**
   * 声明队列
   * <p>一样的道理, 就不赘述了</p>
   */
  @Override
  public void createQueue(String name) {
    boolean durable = true;
    boolean exclusive = false;
    boolean autoDelete = false;
    Map<String, Object> argument = new HashMap<>();
    Queue queue = new Queue(name, durable, exclusive, autoDelete, argument);
    amqpAdmin.declareQueue(queue);
  }

  @Override
  public void binding(String exchangeName, String queueName, String routingKey) {
    Binding binding = new Binding(
        queueName,
        DestinationType.QUEUE,
        exchangeName,
        routingKey,
        null
    );
    amqpAdmin.declareBinding(binding);
  }
}
