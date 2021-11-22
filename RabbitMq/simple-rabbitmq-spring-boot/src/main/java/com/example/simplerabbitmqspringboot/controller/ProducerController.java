package com.example.simplerabbitmqspringboot.controller;

import com.example.simplerabbitmqspringboot.model.po.UserEntity;
import com.example.simplerabbitmqspringboot.service.RabbitTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

  @Autowired
  RabbitTemplateService rabbitTemplateService;

  @PostMapping("/test")
  public String testPublish(@RequestBody UserEntity userEntity) {
    rabbitTemplateService.sendMsg("dead-letter-exchange", "dead.letter.in", userEntity.toString());
    return "ok";
  }


}
