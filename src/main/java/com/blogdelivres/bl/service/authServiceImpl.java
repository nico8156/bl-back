package com.blogdelivres.bl.service;

import com.blogdelivres.bl.dto.*;
import com.blogdelivres.bl.entity.Library;
import com.blogdelivres.bl.entity.UserBis;
import com.blogdelivres.bl.enums.UserRole;
import com.blogdelivres.bl.repository.LibraryRepository;
import com.blogdelivres.bl.repository.UserRepository;
import com.blogdelivres.bl.service.JWT.JwtUserService;
import com.blogdelivres.bl.utils.JWTUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class authServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final LibraryRepository libraryRepository;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private JwtUserService jwtUserService;

    @Override
    public AuthenticationResponse createUser(RegisterRequest registerRequest){


        UserBis userBis = new UserBis();
        userBis.setUsername(registerRequest.getUsername());
        userBis.setEmail(registerRequest.getEmail());
        userBis.setPassword(new BCryptPasswordEncoder().encode(registerRequest.getPassword()));
        userBis.setUserRole(UserRole.USER);
        UserBis createdUser = userRepository.save(userBis);

        Library firstLibrary = new Library();
        firstLibrary.setUser(userBis);
        firstLibrary.setLibraryName("My First Library");
        libraryRepository.save(firstLibrary);

        final UserDetails userDetails = jwtUserService.userDetailsService().loadUserByUsername(createdUser.getEmail());
        UserBis optionalUser = userRepository.findFirstByEmail(userDetails.getUsername()).orElse(null);
        final String jwt = jwtUtil.generateToken(userDetails);
        if(optionalUser != null){
            return logToResponse(optionalUser, jwt);
        } else {
            return null;
        }
    }

    @Override
    public boolean isUserInDB(String email){
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @Override
    public boolean isUsernameInDB(String username){return userRepository.findByUsername(username).isPresent();}

    @Override
    public AuthenticationResponse updateUser(UpdateRequest updateRequest, String token){

        String username = jwtUtil.getUsernameFromToken(token.substring(7));
        UserBis user = userRepository.findFirstByEmail(username).orElse(null);

        if (user != null) {
            user.setUsername(updateRequest.getUsername());
            user.setPhoto(updateRequest.getPhoto());
            userRepository.save(user);
            return  dataToResponse(user, token);
        }
        return null;
    }

    @Override
    public AuthenticationResponse getUserFromToken(String token){
        String username = jwtUtil.getUsernameFromToken(token.substring(7));
        UserBis user = userRepository.findFirstByEmail(username).orElse(null);
        if (user != null) {
            return  dataToResponse(user, token);
        } else {
            return null;
        }

    }
    @Override
    public AuthenticationResponse logUser(AuthenticationRequest authenticationRequest){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect Username or Password");
        }
        final UserDetails userDetails = jwtUserService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        UserBis optionalUser = userRepository.findFirstByEmail(userDetails.getUsername()).orElse(null);
        final String jwt = jwtUtil.generateToken(userDetails);
        if(optionalUser != null){
            return logToResponse(optionalUser, jwt);
        } else {
            return null;
        }
    }
    private AuthenticationResponse dataToResponse(UserBis user, String token){
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .jwt(token.substring(7))
                .userRole(user.getUserRole())
                .userId(user.getId())
                .email(user.getEmail())
                .username(user.getRealUsername())
                .photo(user.getPhoto())
                .build();
        return authenticationResponse;
    }
    private AuthenticationResponse logToResponse(UserBis user, String token){
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .jwt(token)
                .userRole(user.getUserRole())
                .userId(user.getId())
                .email(user.getEmail())
                .username(user.getRealUsername())
                .photo(user.getPhoto())
                .build();
        return authenticationResponse;
    }


}
