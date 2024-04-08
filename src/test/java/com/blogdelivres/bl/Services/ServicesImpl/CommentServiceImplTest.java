package com.blogdelivres.bl.Services.ServicesImpl;

import com.blogdelivres.bl.Services.TestData;
import com.blogdelivres.bl.dto.CommentDTO;
import com.blogdelivres.bl.dto.CommentToSaveRequest;
import com.blogdelivres.bl.entity.Book;
import com.blogdelivres.bl.entity.Comment;
import com.blogdelivres.bl.entity.UserBis;
import com.blogdelivres.bl.repository.BookRepository;
import com.blogdelivres.bl.repository.CommentRepository;
import com.blogdelivres.bl.repository.UserRepository;
import com.blogdelivres.bl.service.Comment.CommentServiceImpl;
import com.blogdelivres.bl.utils.JWTUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private JWTUtil jwtUtil;

    @Test
    public void testThatCommentIsCreated(){

    }
    @Test
    public void testThatGetCommentsForBookId(){
        String googleId = "5v4lDwAAQBAJ";
        Book mockBook = new Book();
        mockBook.setGoogleId(googleId);
        List<Comment> comments = new ArrayList<>();

        when(bookRepository.getBookByGoogleId(googleId)).thenReturn(Optional.of(mockBook));
        when(commentRepository.findAllByBook(mockBook)).thenReturn(comments);

        List<CommentDTO> result = commentService.getComments(googleId);

        assertNotNull(result);
    }
}









