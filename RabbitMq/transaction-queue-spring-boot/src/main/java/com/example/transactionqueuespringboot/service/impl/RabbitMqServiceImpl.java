package com.example.transactionqueuespringboot.service.impl;

import com.example.transactionqueuespringboot.service.RabbitMqService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RabbitMqServiceImpl implements RabbitMqService {

  @Autowired
  RabbitTemplate rabbitTemplate;

  /**
   * 事务队列，必须整体成功，跟业务共存
   */
  @Override
  @Transactional(rollbackFor = Exception.class, transactionManager = "rabbitTransactionManager")
  public void test() {
    rabbitTemplate.convertAndSend("testExchange", "test.queue", 111);
    rabbitTemplate.convertAndSend("testExchange", "test.queue", 222);
    rabbitTemplate.convertAndSend("testExchange", "test.queue", 333);
    rabbitTemplate.convertAndSend("testExchange", "test.queue", 444);
    // int res = 1/0;
  }
}
