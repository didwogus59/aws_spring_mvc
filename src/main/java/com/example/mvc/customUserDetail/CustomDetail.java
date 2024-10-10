package com.example.mvc.customUserDetail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.mvc.user.user;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomDetail implements UserDetails, OAuth2User {
// OAuth2User
    private user user = null;
    //private static final long serialVersionUID = 1L;
	private Map<String, Object> attributes;

    public CustomDetail(user user) {
        this.user = user;
    }

    public CustomDetail(user user, Map<String, Object> attribute) {
        this.user = user;
		this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    // 권한 관련 작업을 하기 위한 role return
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
    });
    return collect;
}
    public boolean isUser() {
        if(user == null)
            return false;
        return true;
    }
    // get Password 메서드
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // get Username 메서드 (생성한 User은 loginId 사용)
    @Override
    public String getUsername() {
        return user.getName();
    }

    public String getRole() {
        return user.getRole();
    }
    // 계정이 만료 되었는지 (true: 만료X)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겼는지 (true: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되었는지 (true: 만료X)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화(사용가능)인지 (true: 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "";
    }

    // 리소스 서버로 부터 받는 회원정보
	// @Override
	// public Map<String, Object> getAttributes() {
	// 	return attributes;
	// }

	// // User의 PrimaryKey
	// @Override
	// public String getName() {
	// 	return user.getId()+"";
	// }
}
