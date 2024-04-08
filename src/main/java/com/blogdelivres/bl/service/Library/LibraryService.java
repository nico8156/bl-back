package com.blogdelivres.bl.service.Library;

import com.blogdelivres.bl.dto.LibraryDTO;
import com.blogdelivres.bl.dto.LibraryToSaveRequest;
import com.blogdelivres.bl.entity.Library;
import java.util.List;
import java.util.Optional;

public interface LibraryService {

    List<LibraryDTO> getAllLibrariesForUser (String token);

    LibraryDTO createLibrary(LibraryToSaveRequest libraryToSaveRequest, String token);

    Optional<Library> getLibraryForID(Long libraryId);

    LibraryDTO updateLibrary(LibraryToSaveRequest libraryToSaveRequest, Long id);

    LibraryDTO deleteLibrary(Long id);

}
