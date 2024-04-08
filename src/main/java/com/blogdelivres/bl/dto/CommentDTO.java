package com.blogdelivres.bl.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDTO {
    private Long CommentId;
    private UserDto userDto;
    private String GoogleId;
    private Long parentId;
    private String title;
    private String Content;
}
