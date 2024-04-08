package com.blogdelivres.bl.Repository;

import com.blogdelivres.bl.dto.BookDTO;
import com.blogdelivres.bl.entity.Book;
import com.blogdelivres.bl.repository.BookRepository;
import com.blogdelivres.bl.service.Book.BookServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookRepositoryTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl service; // Inject the service where you'll use the repository

    @Test
    public void testGetBookByGoogleId_BookExists() {
        // Mock data
        String googleId = "your_google_id";
        Book book = new Book();
        book.setGoogleId("your_google_id");// Create a book object with the provided Google ID

        // Mock behavior of the bookRepository
        when(bookRepository.getBookByGoogleId(googleId)).thenReturn(Optional.of(book));

        // Invoke the method from your service that uses the repository
        BookDTO result = service.getBookFromId(googleId);

        // Verify the result
        assertNotNull(result);
        assertEquals(book.getGoogleId(), result.getGoogleId()); // Ensure that the returned book is the same as the mocked book
    }

}
