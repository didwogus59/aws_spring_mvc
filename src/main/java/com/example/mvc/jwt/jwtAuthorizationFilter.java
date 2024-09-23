package com.example.mvc.jwt;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class jwtAuthorizationFilter extends BasicAuthenticationFilter{

    private jwtProvider jwtProvider;
    public jwtAuthorizationFilter(AuthenticationManager authenticationManager, jwtProvider jwtProvider) {
        super(authenticationManager);
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Cookie[] cookies = req.getCookies();
        String token = null;
        if(cookies != null) {
            for(Cookie cookie: cookies) {
                if(cookie.getName().equals("jwt"))
                    token = cookie.getValue();
            }
        }
        log.info("token : {}",token);
        if(token == null || jwtProvider.validateToken(token)) {
            chain.doFilter(req, res);
            return;
        }
        Authentication auth = jwtProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
//        //SecurityContextHolder.clearContext();
//        req.setAttribute("name", jwtProvider.getAccount(token));
        chain.doFilter(req, res);
    }
}
