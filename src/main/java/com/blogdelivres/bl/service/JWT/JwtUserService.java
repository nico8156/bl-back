package com.blogdelivres.bl.service.JWT;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface JwtUserService {
    UserDetailsService userDetailsService();
}
