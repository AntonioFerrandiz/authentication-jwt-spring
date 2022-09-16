package com.afb.JwtAuthentication.controller;

import com.afb.JwtAuthentication.config.security.JwtUtils;
import com.afb.JwtAuthentication.domain.dto.Token.GetTokenResource;
import com.afb.JwtAuthentication.domain.model.Login;
import com.afb.JwtAuthentication.domain.model.User;
import com.afb.JwtAuthentication.service.UserDetailsServiceImpl;
import com.afb.JwtAuthentication.shared.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<?> auth(@RequestBody Login login) throws Exception{
        try{
            authenticate(login.getUsername(), login.getPassword());
        } catch (Exception e){
            throw new ResourceNotFoundException();
        }
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(login.getUsername());
        String token = this.jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new GetTokenResource(token));

    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        } catch (DisabledException exception){
            throw new Exception("User disabled " + exception.getMessage());
        } catch (BadCredentialsException e){
            throw new ResourceNotFoundException();
        }
    }
    @GetMapping("/obtainActual")
    public User obtain(Principal principal){
        return (User) this.userDetailsService.loadUserByUsername(principal.getName());
    }
}
