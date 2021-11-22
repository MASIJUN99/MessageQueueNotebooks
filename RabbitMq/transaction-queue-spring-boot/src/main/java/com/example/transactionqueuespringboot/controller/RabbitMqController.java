package com.example.transactionqueuespringboot.controller;

import com.example.transactionqueuespringboot.service.RabbitMqService;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMqController {

  @Autowired
  RabbitTemplate rabbitTemplate;
  @Autowired
  RabbitMqService rabbitMqService;

  @RequestMapping("/test")
  public String test() {
    rabbitMqService.test();
    return "ok";
  }

}
