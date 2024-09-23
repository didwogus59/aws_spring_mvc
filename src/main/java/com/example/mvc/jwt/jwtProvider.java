package com.example.mvc.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.mvc.customUserDetail.CustomDetailsService;

import java.security.Key;
import java.util.Date;

import java.util.Map;
import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@Component
@PropertySource("classpath:jwt.properties")
public class jwtProvider {

    @Value("${secret}")
    private String key;
    
    private static final String AUTHORITIES_KEY = "auth";

    @Value("${seconds}")
    private String seconds;

    private Key secretKey;

    // 만료시간 : 1Hour
    private long exp;

    @Autowired
    private CustomDetailsService principalDetailsService;


    @PostConstruct
    protected void init() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.exp = Long.parseLong(seconds);
    }

    // 토큰 생성
    public String createToken(String name, String role) {
        Map<String, Object> payloads = new HashMap<String, Object>();
        Map<String, Object> headers = new HashMap<>();

        headers.put("alg", "HS256");
        headers.put("typ", "JWT");

        payloads.put("name", name);
        payloads.put("role", role);
        // 토큰의 expire 시간을 설정
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.exp);

        return Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .signWith(secretKey, SignatureAlgorithm.HS256) // 사용할 암호화 알고리즘과 , signature 에 들어갈 secret값 세팅
                .setExpiration(validity) // set Expire Time 해당 옵션 안넣으면 expire안함
                .compact();
    }

    // 권한정보 획득
    // Spring Security 인증과정에서 권한확인을 위한 기능
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = principalDetailsService.loadUserByUsername(this.getAccount(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에 담겨있는 유저 account 획득
    public String getAccount(String token) {
        // 만료된 토큰에 대해 parseClaimsJws를 수행하면 io.jsonwebtoken.ExpiredJwtException이 발생한다.
        try {
            log.info((String) Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("name"));
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return e.getClaims().getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (String)Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("name");
    }
    public String getRole(String token) {
        try {
            log.info("jwt provider get role : " + Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("role"));
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return e.getClaims().getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (String)Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("role");
    }
    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            // Bearer 검증
            if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
                return false;
            } else {
                token = token.split(" ")[1].trim();
            }
            log.info("splited token : " + token);
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            // 만료되었을 시 false
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}