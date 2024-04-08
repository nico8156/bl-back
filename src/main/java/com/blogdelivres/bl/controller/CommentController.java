package com.blogdelivres.bl.controller;
import com.blogdelivres.bl.dto.CommentToSaveRequest;
import com.blogdelivres.bl.service.Comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "comment")
@CrossOrigin("http://localhost:4200")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping(path = "book/{googleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createComment(
            @RequestBody CommentToSaveRequest commentToSaveRequest,
            @PathVariable ("googleId") String googleId,
            @RequestHeader("Authorization") String token
    ){
        return new ResponseEntity<>(commentService.createCommentInDb(commentToSaveRequest, googleId, token), HttpStatus.CREATED);
    }
    @GetMapping(path = "book/{googleId}")
    public ResponseEntity<?> getComments(@PathVariable ("googleId") String googleId){
        return new ResponseEntity<>(commentService.getComments(googleId), HttpStatus.OK);
    }
}
