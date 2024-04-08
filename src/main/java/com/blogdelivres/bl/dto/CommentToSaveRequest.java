package com.blogdelivres.bl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentToSaveRequest {
    private  String title;
    private  String content;
    private  Long parentId;
}
