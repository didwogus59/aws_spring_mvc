package com.example.mvc.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/webflux/websocket")
public class socket_controller {
    @GetMapping("")
    public String socket() {
        return "webflux/chatting";
    }
}
