package com.blogdelivres.bl.service.Library;
import com.blogdelivres.bl.dto.LibraryDTO;
import com.blogdelivres.bl.dto.LibraryToSaveRequest;
import com.blogdelivres.bl.entity.Library;
import com.blogdelivres.bl.entity.LibraryBook;
import com.blogdelivres.bl.entity.UserBis;
import com.blogdelivres.bl.repository.LibraryBookRepository;
import com.blogdelivres.bl.repository.LibraryRepository;
import com.blogdelivres.bl.repository.UserRepository;
import com.blogdelivres.bl.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class LibraryServiceImpl implements LibraryService{

    private final LibraryRepository libraryRepository;
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    private final LibraryBookRepository libraryBookRepository;

    @Autowired
    public LibraryServiceImpl(LibraryRepository libraryRepository, JWTUtil jwtUtil, UserRepository userRepository, LibraryBookRepository libraryBookRepository){
        this.libraryRepository = libraryRepository;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.libraryBookRepository = libraryBookRepository;
    }

    @Override
    public List<LibraryDTO> getAllLibrariesForUser(String token) {

        String username = jwtUtil.getUsernameFromToken(token.substring(7));

        UserBis user = userRepository.findFirstByEmail(username).orElse(null);
        if(user != null){
            return libraryRepository.findAllByUserId(user.getId()).stream().map(this::mapToDto).toList();
        }
        return null;
    }

    @Override
    public LibraryDTO createLibrary(LibraryToSaveRequest libraryToSaveRequest, String token) {
        String username = jwtUtil.getUsernameFromToken(token.substring(7));

        UserBis user = userRepository.findFirstByEmail(username).orElse(null);

        Library library = new Library();

        library.setLibraryName(libraryToSaveRequest.getName());
        library.setUser(user);

        libraryRepository.save(library);

        return mapToDto(library);
    }

    @Override
    public Optional<Library> getLibraryForID(Long libraryId) {

        return libraryRepository.findByLibraryId(libraryId);

    }

    @Override
    public LibraryDTO updateLibrary(LibraryToSaveRequest libraryToSaveRequest, Long id){
        Library libraryToUpdate = libraryRepository.findByLibraryId(id).orElse(null);
        if(libraryToUpdate != null){
            libraryToUpdate.setLibraryName(libraryToSaveRequest.getName());
            Library newLibrary = libraryRepository.save(libraryToUpdate);
            return mapToDto(newLibrary);
        }
        return null;
    }

    @Override
    public LibraryDTO deleteLibrary(Long id){
        Library libraryToDelete = libraryRepository.findByLibraryId(id).orElse(null);
        if(libraryToDelete != null) {
            List<LibraryBook> libraryBookToDelete = libraryBookRepository.findBylibrary(libraryToDelete);
            libraryBookToDelete.forEach(libraryBookRepository::delete); // supprime chaque record libraryBook sans supprimer le book, celui ci peut etre reutilisé par un autre utilisateur.
            libraryRepository.delete(libraryToDelete);
            return mapToDto(libraryToDelete);
        }
        return null;
    }

    private LibraryDTO mapToDto(Library library){
        LibraryDTO libraryDTO;
        libraryDTO = LibraryDTO.builder().libraryName(library.getLibraryName())
                .libraryId(library.getLibraryId())
                .build();
        return libraryDTO;
    }

}
