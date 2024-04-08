package com.blogdelivres.bl.dto;
import com.blogdelivres.bl.enums.UserRole;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private String username;
    private UserRole userRole;
    private String photo;
}
