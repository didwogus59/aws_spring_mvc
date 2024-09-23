package com.example.mvc.home;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@Controller
@RequestMapping("/")
public class homeController {
    
    @GetMapping("")
	public String home(Model model, HttpServletRequest req, Authentication auth) {
        log.trace("im trace");
        log.debug("im debug");
        log.info("im info");
        log.warn("im warn");
        //log.error("im error");
        String name = (String)req.getAttribute("name");
        if(auth != null) {
            model.addAttribute("type", "jwt");
            model.addAttribute("name", auth.getName());
        }

		return "home";
	}
}