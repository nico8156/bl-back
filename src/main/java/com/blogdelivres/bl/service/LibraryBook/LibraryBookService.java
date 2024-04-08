package com.blogdelivres.bl.service.LibraryBook;

import com.blogdelivres.bl.entity.Library;
import com.blogdelivres.bl.entity.LibraryBook;

import java.util.List;

public interface LibraryBookService {
    List<LibraryBook>  getBooksFromLibrary(Library library);
}
