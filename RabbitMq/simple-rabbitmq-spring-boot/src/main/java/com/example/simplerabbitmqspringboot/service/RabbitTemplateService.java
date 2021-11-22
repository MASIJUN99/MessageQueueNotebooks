package com.example.simplerabbitmqspringboot.service;

public interface RabbitTemplateService {

  void sendMsg(String exchangeName, String routingKey, String msg);

  void sendMsg(String exchangeName, String routingKey, Object obj);

}
