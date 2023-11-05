package com.myblog8.controller;


import com.myblog8.entity.Role;
import com.myblog8.entity.User;
import com.myblog8.payloadDTO.JWTAuthResponse;
import com.myblog8.payloadDTO.LoginDto;
import com.myblog8.repository.RoleRepository;
import com.myblog8.payloadDTO.SignUpDto;
import com.myblog8.repository.UserRepository;
import com.myblog8.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")  // registration
    public ResponseEntity<?> createUser(@RequestBody SignUpDto signUpDto){

        if(userRepo.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }

        if(userRepo.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

           // Role roles = roleRepo.findByName("ROLE_NAME").get();
            Role roles = roleRepo.findByName("ROLE_USER").get();
            Set<Role> role = new HashSet<>();
            role.add(roles);
            user.setRoles(role);

        User savedUser = userRepo.save(user);

        SignUpDto dto = new SignUpDto();
        dto.setName(savedUser.getName());
        dto.setUsername(savedUser.getUsername());
        dto.setEmail(savedUser.getEmail());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }

    @PostMapping("/signin")  // login
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto
                                                                    loginDto){
        Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));
    }
}
