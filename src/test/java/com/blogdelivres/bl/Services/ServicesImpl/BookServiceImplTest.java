package com.blogdelivres.bl.Services.ServicesImpl;

import com.blogdelivres.bl.Services.TestData;
import com.blogdelivres.bl.dto.BookDTO;
import com.blogdelivres.bl.dto.BookToSaveRequest;
import com.blogdelivres.bl.entity.Book;
import com.blogdelivres.bl.entity.Library;
import com.blogdelivres.bl.entity.LibraryBook;
import com.blogdelivres.bl.entity.UserBis;
import com.blogdelivres.bl.repository.BookRepository;
import com.blogdelivres.bl.repository.LibraryBookRepository;
import com.blogdelivres.bl.repository.LibraryRepository;
import com.blogdelivres.bl.repository.UserRepository;
import com.blogdelivres.bl.service.Book.BookServiceImpl;
import com.blogdelivres.bl.service.Library.LibraryServiceImpl;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private LibraryBookRepository libraryBookRepository;
    @Mock
    private UserRepository userRepository;
    @Mock LibraryRepository libraryRepository;

    @Mock
    private JWTUtil jwtUtil;
    @Mock
    private LibraryServiceImpl libraryServiceImpl;


    @Test
    public void testThatBookIsCreated() {
        final Book book = TestData.testBook();
        final BookToSaveRequest bookToSaveRequest = TestData.bookToSaveRequest();
        final String token = "votre_token_d'autorisation";
        final BookDTO bookDTO = TestData.bookDTO();

        String username = "test@example.com";

        UserBis user = new UserBis();
        user.setEmail(username);

        Long libraryId = 1L;
        Library library = new Library();

        when(jwtUtil.getUsernameFromToken(token.substring(7))).thenReturn(username);
        when(userRepository.findFirstByEmail(username)).thenReturn(Optional.of(user));
        when(libraryServiceImpl.getLibraryForID(libraryId)).thenReturn(Optional.of(library));
        when(bookRepository.save(eq(book))).thenReturn(book);
        when(libraryBookRepository.save(any(LibraryBook.class))).thenReturn(new LibraryBook());

        final BookDTO result = bookServiceImpl.createBookInDb(bookToSaveRequest, libraryId, token);
        assertNotNull(result);
        assertEquals(bookDTO, result);
    }
    @Test
    public void  getBookFromId(){
        String googleId = "un id google existant";
        Book mockBook = new Book();
        mockBook.setGoogleId(googleId);

        when(bookRepository.getBookByGoogleId(googleId)).thenReturn(Optional.of(mockBook));

        BookDTO result = bookServiceImpl.getBookFromId(googleId);

        assertNotNull(result);
        assertEquals(googleId, result.getGoogleId());
    }

    @Test
    public void getBooksFromLibraryId(){
        Long libraryId = 1L;
        Library mockLibrary = new Library();
        mockLibrary.setLibraryId(libraryId);

        List<LibraryBook> mockLibraryBooks = new ArrayList<>();

        when(libraryRepository.findByLibraryId(libraryId)).thenReturn(Optional.of(mockLibrary));
        when(libraryBookRepository.findBylibrary(mockLibrary)).thenReturn(mockLibraryBooks);

        List<BookDTO> result = bookServiceImpl.getBooksFromLibraryId(libraryId);

        assertNotNull(result);
    }


}
