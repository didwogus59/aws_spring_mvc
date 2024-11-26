package com.example.mvc.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/webflux")
public class socket_controller {
    @GetMapping("/websocket")
    public String socket() {
        return "webflux/chatting";
    }

    @GetMapping("/webrtc")
    public String webRTC() {
        return "webrtc/client";
    }

}
