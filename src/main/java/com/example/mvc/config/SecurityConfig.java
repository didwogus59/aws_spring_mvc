package com.example.mvc.config;

import com.example.mvc.oauth2.CustomOAuth2Service;
import com.example.mvc.oauth2.OAuth2AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

import com.example.mvc.jwt.jwtAuthenticationFilter;
import com.example.mvc.jwt.jwtAuthorizationFilter;
import com.example.mvc.jwt.jwtProvider;
// import com.example.demo.oauth.CustomOAuth2Service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(
    prePostEnabled = true, 
    securedEnabled = true, 
    jsr250Enabled = true
    )
public class SecurityConfig {

     @Autowired
     private CustomOAuth2Service customoAuth2Service;
    
    @Autowired
    private final jwtProvider tokenProvider;

    @Autowired
    OAuth2AuthenticationSuccessHandler handler;
    @Qualifier("corsConfig")
    @Autowired
    CorsConfigurationSource corsConfig;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

        http.cors(c -> c
            .configurationSource(corsConfig)
            );

        http.httpBasic((basic) -> basic.disable());
        
        
        http.addFilter(jwtAuthor(authenticationManager));
        http.addFilterBefore(jwtAuth(authenticationManager), UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement((session) ->
            session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.csrf((csrf) -> csrf
            .ignoringRequestMatchers(new AntPathRequestMatcher("/form/**"))
            // .ignoringRequestMatchers(new AntPathRequestMatcher("/**"))
            .ignoringRequestMatchers(new AntPathRequestMatcher("/webclient"))
            .ignoringRequestMatchers(new AntPathRequestMatcher("/postgre/**")));


        http.headers((headers) -> headers
            .addHeaderWriter(new XFrameOptionsHeaderWriter(
                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)));

        http.oauth2Login((oauth) -> oauth
                .loginPage("/")
                .successHandler(handler)
                .userInfoEndpoint((userInfoEndpoint) -> userInfoEndpoint
                        .userService(customoAuth2Service)));

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    jwtAuthenticationFilter jwtAuth(AuthenticationManager authenticationManager) {
        jwtAuthenticationFilter filter = new jwtAuthenticationFilter(authenticationManager, tokenProvider);
        filter.setFilterProcessesUrl("/user/login/jwt/auth");
        return filter;
    }

    jwtAuthorizationFilter jwtAuthor(AuthenticationManager authenticationManager) {
        return new jwtAuthorizationFilter(authenticationManager, tokenProvider);
    }
}