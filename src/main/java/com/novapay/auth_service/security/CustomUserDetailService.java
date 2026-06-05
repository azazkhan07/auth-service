package com.novapay.auth_service.security;


import com.novapay.auth_service.client.UserClient;
import com.novapay.auth_service.dto.response.UserAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthResponse user = userClient.getUserByEmail(username);
        return new CustomUserDetail(user);
    }
}
