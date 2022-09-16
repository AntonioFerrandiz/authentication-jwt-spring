package com.afb.JwtAuthentication.service;

import com.afb.JwtAuthentication.domain.dto.User.CreateUserResource;
import com.afb.JwtAuthentication.domain.dto.User.GetUserResource;
import com.afb.JwtAuthentication.domain.model.User;
import com.afb.JwtAuthentication.domain.repository.UserRepository;
import com.afb.JwtAuthentication.shared.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final String ENTITY = "USER";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public User save(CreateUserResource resource) throws Exception {
        String username = resource.getUsername();
        List<User> verify = userRepository.findByUsername(username);
        if(!verify.isEmpty()){
            throw new Exception(new ResourceNotFoundException(username));
        }

        User user = resource.convert();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<GetUserResource> getAllUsers(){
        List<User> users;
        users = userRepository.findAll();
        return GetUserResource.convert(users);
    }

    public GetUserResource getUser(Long id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            throw new ResourceNotFoundException(id);
        }
        return new GetUserResource(user.get());
    }
}
