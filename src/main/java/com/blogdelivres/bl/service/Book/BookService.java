package com.blogdelivres.bl.service.Book;

import com.blogdelivres.bl.dto.BookDTO;
import com.blogdelivres.bl.dto.BookToSaveRequest;
import com.blogdelivres.bl.entity.Book;
import com.blogdelivres.bl.entity.Library;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookDTO getBookFromId(String googleId);

    List<BookDTO> getBooksFromLibraryId(Long LibrarId);

    BookDTO createBookInDb(BookToSaveRequest bookToSaveRequest, Long libraryId, String token);

    BookDTO deleteBookFromLib(String googleId, Long libraryId);
}
