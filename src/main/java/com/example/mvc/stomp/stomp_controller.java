package com.example.mvc.stomp;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class stomp_controller {


  @GetMapping("/socket/test1")
  public String test() {
    return "chatting/index";
  }
  
  @GetMapping("/socket/test2")
  public String test2() {
    return "chatting/test";
  }

  

  @SendTo("/sub/test1")
  @MessageMapping("/test1")
  public String test_socket(chat msg) throws Exception {
    System.out.println(msg.getData());
    return msg.getData() + " from server";
  }


  @SendTo("/sub/greeting")
  @MessageMapping("/testMsg")
  public chat greeting(chat msg) throws Exception {
    Thread.sleep(1000); // simulated delay
    System.out.println(msg.getData());
    return new chat("Hello", msg.getData());
  }
}