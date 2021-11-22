package com.example.simplerabbitmqspringboot.service.impl;

import com.example.simplerabbitmqspringboot.service.RabbitTemplateService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitTemplateServiceImpl implements RabbitTemplateService {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Override
  public void sendMsg(String exchangeName, String routingKey, String msg) {
    rabbitTemplate.convertAndSend(exchangeName, routingKey, msg);
  }

  /**
   * 发送对象, 这个时候查看里面的内容, 会发现是JDK进行编码的, 就很乱, 所以我们需要JSON化
   * <p>查看源码, 原来RabbitTemplate有个自己的属性是messageConverter,
   * 对应有个构造器, 我们只需要注入这个类型的Bean即可</p>
   */
  @Override
  public void sendMsg(String exchangeName, String routingKey, Object obj) {
    rabbitTemplate.convertAndSend(exchangeName, routingKey, obj);
  }
}
