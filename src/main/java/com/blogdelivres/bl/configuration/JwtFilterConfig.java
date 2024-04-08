package com.blogdelivres.bl.configuration;

import com.blogdelivres.bl.service.JWT.JwtUserService;
import com.blogdelivres.bl.service.JWT.JwtUserServiceImpl;
import com.blogdelivres.bl.utils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

public class JwtFilterConfig extends OncePerRequestFilter {

    private HandlerExceptionResolver handlerExceptionResolver;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private JwtUserServiceImpl jwtUserService;

    @Autowired
    public JwtFilterConfig(HandlerExceptionResolver handlerExceptionResolver){
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        try{
            if(StringUtils.isEmpty(authorizationHeader) || !StringUtils.startsWith(authorizationHeader, "Bearer ")){
                filterChain.doFilter(request, response);
                return;
            }
            jwt = authorizationHeader.substring(7);
            userEmail = jwtUtil.getUsernameFromToken(jwt);
            if(StringUtils.isNoneEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = jwtUserService.userDetailsService().loadUserByUsername(userEmail);
                if(jwtUtil.isTokenValid(jwt, userDetails)){
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(authToken);
                    SecurityContextHolder.setContext(context);
                }
            }
            filterChain.doFilter(request, response);

        } catch (Exception ex){
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }


    }
}
