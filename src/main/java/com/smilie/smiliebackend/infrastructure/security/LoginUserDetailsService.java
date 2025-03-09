package com.smilie.smiliebackend.infrastructure.security;

import com.smilie.smiliebackend.loginandregister.LoginAndRegisterFacade;
import com.smilie.smiliebackend.loginandregister.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

@AllArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {

    private final LoginAndRegisterFacade loginAndRegisterFacade;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final UserResponseDto byUsername = loginAndRegisterFacade.findByUsername(username);
        return getUser(byUsername);
    }

    private org.springframework.security.core.userdetails.User getUser(UserResponseDto dto) {
        return new org.springframework.security.core.userdetails.User(
                dto.login(),
                dto.password(),
                Collections.emptyList());
    }
}
