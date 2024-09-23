//not used
package com.example.mvc.jwt;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.mvc.customUserDetail.CustomDetail;
import com.example.mvc.home.homeController;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class jwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private final AuthenticationManager authenticationManager;
	private final jwtProvider jwtProvider;
    //login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException{

		String name = null;
		String password = null;
		try {
			name = req.getParameter("name");
			password = req.getParameter("password");
		} catch(Exception e) {
			e.printStackTrace();
		}
		// 유저네임패스워드 토큰 생성
        UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(name, password);
		
		// authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
		// loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
		// UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
		// UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
		// Authentication 객체를 만들어서 필터체인으로 리턴해준다.
		
		// Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
		// Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
		// 결론은 인증 프로바이더에게 알려줄 필요가 없음.
		Authentication authentication = authenticationManager.authenticate(authToken);

		return authentication;
    }
	
// JWT Token 생성해서 response에 cookie로 담아주기
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		String role = ((CustomDetail)authResult.getPrincipal()).getRole();
		String jwtToken = jwtProvider.createToken(authResult.getName(), role);
		Cookie cookie = new Cookie("jwt",jwtToken);
		cookie.setPath("/");
	    cookie.setHttpOnly(true);
        cookie.setMaxAge(60*60*24*7);
		cookie.setSecure(true);
		cookie.setAttribute("SameSite", "None");
		res.addCookie(cookie);
		chain.doFilter(req, res);
	}
}
