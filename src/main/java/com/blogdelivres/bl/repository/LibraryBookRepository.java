package com.blogdelivres.bl.repository;

import com.blogdelivres.bl.entity.Book;
import com.blogdelivres.bl.entity.Library;
import com.blogdelivres.bl.entity.LibraryBook;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface LibraryBookRepository extends CrudRepository<LibraryBook, Long> {
    List<LibraryBook> findBylibrary(Library library);
    LibraryBook findByLibraryAndBook(Library library, Book book);
}
