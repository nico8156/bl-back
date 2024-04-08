package com.blogdelivres.bl.Repository;

import com.blogdelivres.bl.entity.Library;
import com.blogdelivres.bl.entity.LibraryBook;
import com.blogdelivres.bl.repository.LibraryBookRepository;
import com.blogdelivres.bl.service.LibraryBook.LibraryBookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LibraryBookRepositoryTest {
    @Mock
    private LibraryBookRepository libraryBookRepository;

    @InjectMocks
    private LibraryBookServiceImpl service; // Inject the service where you'll use the repository

    @Test
    public void testFindByLibrary_Success() {
        // Mock data
        Library library = new Library(); // Create a library object
        List<LibraryBook> expectedLibraryBooks = new ArrayList<>(); // Create a list of expected library books

        // Mock behavior of the libraryBookRepository
        when(libraryBookRepository.findBylibrary(library)).thenReturn(expectedLibraryBooks);

        // Invoke the method from your service that uses the repository
        List<LibraryBook> result = service.getBooksFromLibrary(library);

        // Verify the result
        assertEquals(expectedLibraryBooks, result); // Ensure that the returned list is the same as the expected list
    }
}
