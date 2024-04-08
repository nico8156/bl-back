package com.blogdelivres.bl.dto;

import com.blogdelivres.bl.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
    private String jwt;
    private UserRole userRole;
    private long userId;
    private String email;
    private String username;
    private String photo;
}
