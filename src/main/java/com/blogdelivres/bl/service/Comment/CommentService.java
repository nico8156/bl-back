package com.blogdelivres.bl.service.Comment;

import com.blogdelivres.bl.dto.CommentDTO;
import com.blogdelivres.bl.dto.CommentToSaveRequest;
import java.util.List;

public interface CommentService {
    CommentDTO createCommentInDb(CommentToSaveRequest commentToSaveRequest, String googleId, String token);
    List<CommentDTO> getComments(String googleId);
}

