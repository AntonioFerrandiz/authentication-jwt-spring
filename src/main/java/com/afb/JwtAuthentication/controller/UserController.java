package com.afb.JwtAuthentication.controller;

import com.afb.JwtAuthentication.domain.dto.User.CreateUserResource;
import com.afb.JwtAuthentication.domain.dto.User.GetUserResource;
import com.afb.JwtAuthentication.domain.model.User;
import com.afb.JwtAuthentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<CreateUserResource> registerUser(@Valid @RequestBody CreateUserResource userResource, UriComponentsBuilder uriComponentsBuilder) throws Exception {
        User user = userService.save(userResource);
        URI uri = uriComponentsBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new CreateUserResource(user));
    }

    @GetMapping
    public ResponseEntity<List<GetUserResource>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResource> getUser(@PathVariable Long id) throws Exception {
        GetUserResource user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }
}
