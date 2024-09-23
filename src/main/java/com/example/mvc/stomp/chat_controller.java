package com.example.mvc.stomp;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class chat_controller {

  @GetMapping("/chatting")
  public String test2() {
    return "chatting/chatting";
  }

  @MessageMapping("/new_msg")
  @SendTo("/sub/chatting")
  public chat new_msg(chat msg) throws Exception {
    System.out.println(msg.getData());
    msg.setData(": " + msg.getData());
    return msg;
  }

  @MessageMapping("/new_user")
  @SendTo("/sub/chatting")
  public chat new_user(chat msg) throws Exception {
    System.out.println(msg.getName());
    return new chat(msg.getName(), " is come!");
  }
}