package com.blogdelivres.bl.Repository;

import com.blogdelivres.bl.dto.CommentDTO;
import com.blogdelivres.bl.entity.Book;
import com.blogdelivres.bl.entity.Comment;
import com.blogdelivres.bl.repository.BookRepository;
import com.blogdelivres.bl.repository.CommentRepository;
import com.blogdelivres.bl.service.Comment.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentRepositoryTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private CommentServiceImpl service; // Inject the service where you'll use the repository

    @Test
    public void testThatFindAllByBook_Success() {

        String googleId = "your_google_id";
        Book book = new Book();
        book.setGoogleId(googleId);
        List<Comment> expectedComments = new ArrayList<>();

        when(bookRepository.getBookByGoogleId(googleId)).thenReturn(Optional.of(book));
        when(commentRepository.findAllByBook(book)).thenReturn(expectedComments);

        List<CommentDTO> result = service.getComments(googleId);

        assertEquals(expectedComments, result); // Ensure that the returned list is the same as the expected list
    }
}
