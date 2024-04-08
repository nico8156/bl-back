package com.blogdelivres.bl.Services.ServicesImpl;

import com.blogdelivres.bl.entity.Library;
import com.blogdelivres.bl.entity.LibraryBook;
import com.blogdelivres.bl.repository.LibraryBookRepository;
import com.blogdelivres.bl.service.LibraryBook.LibraryBookService;
import com.blogdelivres.bl.service.LibraryBook.LibraryBookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LibraryBookServiceImplTest {
    @InjectMocks
    private LibraryBookServiceImpl libraryBookService;

    @Mock
    private LibraryBookRepository libraryBookRepository;

    @Test
    public void testGetBooksFromLibrary_Success() {

        Library library = new Library();
        List<LibraryBook> expectedBooks = new ArrayList<>();

        when(libraryBookRepository.findBylibrary(library)).thenReturn(expectedBooks);

        List<LibraryBook> result = libraryBookService.getBooksFromLibrary(library);

        assertNotNull(result);
        assertSame(expectedBooks, result);
    }
}
