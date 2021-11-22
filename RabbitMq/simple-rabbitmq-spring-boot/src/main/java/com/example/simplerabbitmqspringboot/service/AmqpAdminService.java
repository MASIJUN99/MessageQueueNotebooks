package com.example.simplerabbitmqspringboot.service;

public interface AmqpAdminService {

  void createExchange(String name);

  void createQueue(String name);

  void binding(String exchangeName, String queueName, String routingKey);

}
