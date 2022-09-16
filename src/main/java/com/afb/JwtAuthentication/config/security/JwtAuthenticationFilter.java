package com.afb.JwtAuthentication.config.security;

import com.afb.JwtAuthentication.domain.model.User;
import com.afb.JwtAuthentication.domain.repository.UserRepository;
import com.afb.JwtAuthentication.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private JwtUtils jwtUtils;

    private UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtils jwtUtils, UserRepository userRepository) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recoveryToken(request);
        validateAndAuthenticateClient(token);
        filterChain.doFilter(request,response);
    }

    private void validateAndAuthenticateClient(String token) {
        Optional<Jws<Claims>> optClaim = jwtUtils.getTokenInfo(token);

        optClaim.ifPresent(claimsJws -> {
            long userId = Long.parseLong(claimsJws.getBody().getSubject());

            User user = userRepository.findById(userId).get();

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        });
    }

    private String recoveryToken(HttpServletRequest request) {
        String tokenHeader = request.getHeader("Authorization");
        String startToken = "Bearer ";
        if(tokenHeader == null || tokenHeader.isEmpty() || !tokenHeader.startsWith(startToken)){
            return null;
        }
        return tokenHeader.substring(startToken.length(), tokenHeader.length());
    }
}
