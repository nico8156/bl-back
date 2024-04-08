package com.blogdelivres.bl.Services.ServicesImpl;

import com.blogdelivres.bl.dto.LibraryDTO;
import com.blogdelivres.bl.dto.LibraryToSaveRequest;
import com.blogdelivres.bl.entity.Library;
import com.blogdelivres.bl.entity.UserBis;
import com.blogdelivres.bl.repository.LibraryRepository;
import com.blogdelivres.bl.repository.UserRepository;
import com.blogdelivres.bl.service.Library.LibraryServiceImpl;
import com.blogdelivres.bl.utils.JWTUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceImplTest {
    @InjectMocks
    private LibraryServiceImpl libraryService;

    @Mock
    LibraryRepository libraryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JWTUtil jwtUtil;

    @Test
    public void testThatGetAllLibrariesForUser(){
        String token = "valid_token";
        String username = "test@example.com";
        Long userId = 123L;

        UserBis user = new UserBis();
        user.setId(userId);


        List<Library> libraries = new ArrayList<>();


        when(jwtUtil.getUsernameFromToken(token.substring(7))).thenReturn(username);
        when(userRepository.findFirstByEmail(username)).thenReturn(Optional.of(user));
        when(libraryRepository.findAllByUserId(userId)).thenReturn(libraries);


        List<LibraryDTO> result = libraryService.getAllLibrariesForUser(token);


        assertNotNull(result);

    }
    @Test
    public void testThatLibraryIsCreated() {

        String token = "valid_token";
        String username = "test@example.com";
        String libraryName = "Sample Library";

        UserBis user = new UserBis();

        LibraryToSaveRequest libraryToSaveRequest = new LibraryToSaveRequest();
        libraryToSaveRequest.setName(libraryName);

        when(jwtUtil.getUsernameFromToken(token.substring(7))).thenReturn(username);
        when(userRepository.findFirstByEmail(username)).thenReturn(Optional.of(user));

        LibraryDTO result = libraryService.createLibrary(libraryToSaveRequest, token);

        assertNotNull(result);
        assertEquals(libraryName, result.getLibraryName());

    }

    @Test
    public void testThatLibraryIsFoundForId() {

        Long libraryId = 123L;
        Library library = new Library();

        when(libraryRepository.findByLibraryId(libraryId)).thenReturn(Optional.of(library));

        Optional<Library> result = libraryService.getLibraryForID(libraryId);

        assertTrue(result.isPresent());
        assertSame(library, result.get());
    }
    @Test
    public void testThatLibraryIsNotFoundForId() {

        Long libraryId = 456L;

        when(libraryRepository.findByLibraryId(libraryId)).thenReturn(Optional.empty());

        Optional<Library> result = libraryService.getLibraryForID(libraryId);

        assertFalse(result.isPresent());
    }

}
