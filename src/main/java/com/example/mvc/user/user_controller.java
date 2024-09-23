package com.example.mvc.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;

import com.example.mvc.jwt.jwtProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class user_controller {

    @Autowired
    private user_service service;

    @Autowired
    jwtProvider jwtProvider;
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public @ResponseBody String check_login() {
        return "you are user";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(path = "/admin", method = RequestMethod.GET)
    public @ResponseBody String check_admin() {
        return "you are admin";
    }


    @RequestMapping(path = "/sign", method = RequestMethod.GET)
    public String sign_get() {
        return "user/sign";
    }

    @RequestMapping(path = "/sign", method = RequestMethod.POST)
    public String sign_post(@ModelAttribute user user, Model model) {
        if(service.create_user(user))
            return "home";
        return "user/sign";
    }

    @RequestMapping(path = "/login/jwt", method = RequestMethod.GET)
    public String login_page_jwt() {
        return "user/login";
    }

    
    @RequestMapping(path = "/login/jwt/auth", method = RequestMethod.POST)
    public String login_jwt_complete() {
        return "redirect:/";
    }

    @RequestMapping(path = "/logout/jwt", method = RequestMethod.GET)
    public String logout_jwt(HttpServletResponse res,HttpServletRequest req) throws ServletException {
        Cookie jwt = new Cookie("jwt", null);
        jwt.setMaxAge(0);
        jwt.setPath("/");
        res.addCookie(jwt);
        req.logout();
        return "redirect:/";
    }
}
