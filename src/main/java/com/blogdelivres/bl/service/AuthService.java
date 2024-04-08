package com.blogdelivres.bl.service;
import com.blogdelivres.bl.dto.*;

public interface AuthService {

    UserDto createUser(RegisterRequest registerRequest );

    boolean isUserInDB(String email);

    boolean isUsernameInDB(String username);

    AuthenticationResponse updateUser(UpdateRequest updateRequest, String token);
    AuthenticationResponse getUserFromToken(String token);
    AuthenticationResponse logUser(AuthenticationRequest authenticationRequest);
}
