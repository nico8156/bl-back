package com.blogdelivres.bl.service.Comment;

import com.blogdelivres.bl.dto.*;
import com.blogdelivres.bl.entity.Book;
import com.blogdelivres.bl.entity.Comment;
import com.blogdelivres.bl.entity.UserBis;
import com.blogdelivres.bl.repository.BookRepository;
import com.blogdelivres.bl.repository.CommentRepository;
import com.blogdelivres.bl.repository.UserRepository;
import com.blogdelivres.bl.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final JWTUtil jwtUtil;

    @Autowired
    public CommentServiceImpl(

            JWTUtil jwtUtil,
            BookRepository bookRepository,
            CommentRepository commentRepository,
            UserRepository userRepository){

        this.jwtUtil = jwtUtil;
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
        this.userRepository =userRepository;
    }

    @Override
    public CommentDTO createCommentInDb(CommentToSaveRequest commentToSaveRequest,String googleId,String token){

        String username = jwtUtil.getUsernameFromToken(token.substring(7));

        UserBis user = userRepository.findFirstByEmail(username).orElse(null);

        Book book = bookRepository.getBookByGoogleId(googleId).orElse(null);

        assert user != null;
        Comment savedComment = requestToEntity(commentToSaveRequest, book, user);
        Comment finalsavedComment = commentRepository.save(savedComment);
        return entityToDto(finalsavedComment);

    }
    @Override
    public List<CommentDTO> getComments(String googleId){
        Book book = bookRepository.getBookByGoogleId(googleId).orElse(null);
        if (book != null){
            return commentRepository.findAllByBook(book).stream().map(this::entityToDto).toList();
        }
        return null;
    }

    private CommentDTO entityToDto (Comment comment){

        UserDto userDto = UserDto.builder()
                .username(comment.getUser().getRealUsername())
                .userRole(comment.getUser().getUserRole())
                .photo(comment.getUser().getPhoto())
                .build();

        CommentDTO commentDTO = CommentDTO.builder()
                .CommentId(comment.getCommentId())
                .userDto(userDto)
                .GoogleId(comment.getBook().getGoogleId())
                .parentId(comment.getParentId())
                .title(comment.getTitle())
                .Content(comment.getContent())
                .build();

        return commentDTO;
    }
    private Comment requestToEntity (CommentToSaveRequest comment, Book book, UserBis user){

        Comment commentEntity = Comment.builder()
                .user(user)
                .book(book)
                .content(comment.getContent())
                .title(comment.getTitle())
                .parentId(comment.getParentId())
                .build();

        return commentEntity;
    }
}
