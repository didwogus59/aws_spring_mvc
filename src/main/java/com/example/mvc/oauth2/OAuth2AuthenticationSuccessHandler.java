package com.example.mvc.oauth2;

import com.example.mvc.customUserDetail.CustomDetail;
import com.example.mvc.jwt.jwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private jwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String role = ((CustomDetail)authentication.getPrincipal()).getRole();
        String jwtToken = jwtProvider.createToken(authentication.getName(), role);
        log.info("name : " + authentication.getName());
        Cookie cookie = new Cookie("jwt",jwtToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*60*24*7);
        cookie.setSecure(true);
        cookie.setAttribute("SameSite", "None");
        log.info("jwt: " + jwtToken);
        response.addCookie(cookie);

//        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
//        String email = oAuth2User.getAttribute("email");
//
//        String token = tokenProvider.createToken(email);
//
//        response.addHeader("Authorization", "Bearer " + token);
        response.sendRedirect("/");
    }
}