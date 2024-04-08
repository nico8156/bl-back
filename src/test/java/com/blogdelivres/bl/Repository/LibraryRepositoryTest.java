package com.blogdelivres.bl.Repository;

import com.blogdelivres.bl.dto.LibraryDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LibraryRepositoryTest {
    @Mock
    private LibraryRepository libraryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LibraryServiceImpl service; // Inject the service where you'll use the repository

    @Mock
    private JWTUtil jwtUtil;

    @Test
    public void testFindAllByUserId_Success() {
        // Mock data
        Long userId = 1L; // Example user ID
        String token = "valid_token";
        String username = "test@example.com";
        UserBis user = new UserBis();
        user.setId(userId);
        List<Library> expectedLibraries = new ArrayList<>(); // Create a list of expected libraries

        // Mock behavior of the libraryRepository
        when(libraryRepository.findAllByUserId(userId)).thenReturn(expectedLibraries);
        when(jwtUtil.getUsernameFromToken(token.substring(7))).thenReturn(username);
        when(userRepository.findFirstByEmail(username)).thenReturn(Optional.of(user));
        // Invoke the method from your service that uses the repository
        List<LibraryDTO> result = service.getAllLibrariesForUser(token);

        // Verify the result
        assertEquals(expectedLibraries, result); // Ensure that the returned list is the same as the expected list
    }
}
