package com.blogdelivres.bl.controller;
import com.blogdelivres.bl.dto.*;
import com.blogdelivres.bl.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "auth")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class UserController {

    private final AuthService authService;


    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        log.info("dans inscription");
        if(authService.isUserInDB(registerRequest.getEmail())){
            return new ResponseEntity<>("User already exists with this email", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto createdUserDto = authService.createUser(registerRequest);
        if (createdUserDto == null) {
            return new ResponseEntity<>("User not created, try again later", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws BadCredentialsException, DisabledException, UsernameNotFoundException {
        return ResponseEntity.ok(authService.logUser(authenticationRequest));
    }

    @PostMapping("check/username")
    public ResponseEntity<?> checkUsername(@RequestBody String username){
         Boolean response = authService.isUsernameInDB(username);
         return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("user")
    public ResponseEntity<AuthenticationResponse> getUserFromToken(@RequestHeader("Authorization") String token){
        log.info(token);
        return ResponseEntity.ok(authService.getUserFromToken(token));
    }
    @PatchMapping("update")
    public ResponseEntity<AuthenticationResponse> updateUser(@RequestBody UpdateRequest updateRequest, @RequestHeader("Authorization") String token){
        AuthenticationResponse userToSend = authService.updateUser(updateRequest, token);
        return ResponseEntity.ok(userToSend);
    }
}