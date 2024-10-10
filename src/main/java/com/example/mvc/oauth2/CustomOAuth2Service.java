package com.example.mvc.oauth2;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.mvc.customUserDetail.CustomDetail;
import com.example.mvc.user.user;
import com.example.mvc.user.user_repository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{
    private final user_repository repo;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest : " + userRequest);
        // System.out.println("access token : " + userRequest.getAccessToken());
        log.info("client Registration : " + userRequest.getClientRegistration());
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        log.info("user : " + delegate.loadUser(userRequest));
        oauth2Info info = new googleInfo(delegate.loadUser(userRequest).getAttributes());

        Optional<user> userOp = repo.findOneByProviderIdAndProvider(info.getProviderId(), info.getProvider());
        if(info.getProvider().equals("google")) {
            log.info("google login");
        }
        user user;
        if(!userOp.isPresent()) {
            user = new user();
            user.setProvider(info.getProvider());
            user.setProviderId(info.getProviderId());
            user.setName(info.getName());
            user.setEmail(info.getEmail());
            repo.save(user);
        }
        else {
            user = userOp.get();
        }
        return new CustomDetail(user);
    }
}