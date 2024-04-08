package com.blogdelivres.bl.service.LibraryBook;
import com.blogdelivres.bl.entity.Library;
import com.blogdelivres.bl.entity.LibraryBook;
import com.blogdelivres.bl.repository.LibraryBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class LibraryBookServiceImpl implements LibraryBookService{

    private final LibraryBookRepository libraryBookRepository;
    @Autowired
    public LibraryBookServiceImpl(LibraryBookRepository libraryBookRepository){
        this.libraryBookRepository = libraryBookRepository;
    }
    @Override
    public List<LibraryBook> getBooksFromLibrary(Library library) {
        return libraryBookRepository.findBylibrary(library);
    }
}
