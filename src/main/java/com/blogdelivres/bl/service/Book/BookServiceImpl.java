package com.blogdelivres.bl.service.Book;

import com.blogdelivres.bl.dto.BookDTO;
import com.blogdelivres.bl.dto.BookToSaveRequest;
import com.blogdelivres.bl.entity.Book;
import com.blogdelivres.bl.entity.Library;
import com.blogdelivres.bl.entity.LibraryBook;
import com.blogdelivres.bl.entity.UserBis;
import com.blogdelivres.bl.repository.BookRepository;
import com.blogdelivres.bl.repository.LibraryBookRepository;
import com.blogdelivres.bl.repository.LibraryRepository;
import com.blogdelivres.bl.repository.UserRepository;
import com.blogdelivres.bl.service.Library.LibraryService;
import com.blogdelivres.bl.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@Slf4j
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final LibraryBookRepository libraryBookRepository;
    private final LibraryService libraryService;
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;
    private final LibraryRepository libraryRepository;

    @Autowired
    public BookServiceImpl (BookRepository bookRepository,JWTUtil jwtUtil, LibraryRepository libraryRepository, LibraryService libraryService, UserRepository userRepository, LibraryBookRepository libraryBookRepository){
        this.jwtUtil= jwtUtil;
        this.libraryBookRepository= libraryBookRepository;
        this.libraryService = libraryService;
        this.userRepository = userRepository;
        this.libraryRepository = libraryRepository;
        this.bookRepository= bookRepository;
    }

    @Override
    public BookDTO createBookInDb(BookToSaveRequest bookToSaveRequest, Long libraryId, String token){

        String username = jwtUtil.getUsernameFromToken(token.substring(7));
        UserBis user = userRepository.findFirstByEmail(username).orElse(null);

        Library library  = libraryService.getLibraryForID(libraryId).orElse(null);

        Book isBookInDb = bookRepository.getBookByGoogleId(bookToSaveRequest.getGoogleId()).orElse(null);

        if(isBookInDb != null){

            log.info("book already in DB !!!");

            LibraryBook libraryBook = LibraryBook.builder()
                    .book(isBookInDb)
                    .library(library)
                    .build();

            libraryBookRepository.save(libraryBook);

            return entityToDto(isBookInDb);
        }

        Book bookSaved = bookRepository.save(requestToEntity(bookToSaveRequest));

        LibraryBook libraryBook = LibraryBook.builder()
                .book(bookSaved)
                .library(library)
                .build();

        libraryBookRepository.save(libraryBook);

        return entityToDto(bookSaved);
    }
    @Override
    public List<BookDTO> getBooksFromLibraryId(Long libraryId){

        Library library = libraryRepository.findByLibraryId(libraryId).orElse(null);
        List<LibraryBook> libraryBookList = libraryBookRepository.findBylibrary(library);
        return  libraryBookList.stream().map(item -> entityToDto(item.getBook())
        ).toList();

    }
    @Override
    public BookDTO getBookFromId(String googleId) {
        Book book = bookRepository.getBookByGoogleId(googleId).orElse(null);
        assert book != null;
        return entityToDto(book);
    }

    @Override
    public BookDTO deleteBookFromLib(String googleId, Long libraryId){
        log.info(googleId, libraryId);

        Book bookToDelete = bookRepository.getBookByGoogleId(googleId).orElse(null);
        assert bookToDelete != null;

        Library library = libraryRepository.findByLibraryId(libraryId).orElse(null);
        assert library != null;

        LibraryBook libraryBookToDelete = libraryBookRepository.findByLibraryAndBook(library, bookToDelete);

        libraryBookRepository.delete(libraryBookToDelete);

        return entityToDto(bookToDelete);
    }

    private BookDTO entityToDto (Book book){

        BookDTO bookDTO;

        bookDTO = BookDTO.builder()
                .title(book.getTitle())
                .description(book.getDescription())
                .authors(book.getAuthors())
                .googleId(book.getGoogleId())
                .publisher(book.getPublisher())
                .pageCount(book.getPageCount())
                .imageLinks(book.getImageLinks())
                .build();

        return bookDTO;
    }
    private Book requestToEntity (BookToSaveRequest bookToSaveRequest){
        Book book = Book.builder()
                .title(bookToSaveRequest.getTitle())
                .description(bookToSaveRequest.getDescription())
                .authors(bookToSaveRequest.getAuthors())
                .googleId(bookToSaveRequest.getGoogleId())
                .publisher(bookToSaveRequest.getPublisher())
                .pageCount(bookToSaveRequest.getPageCount())
                .imageLinks(bookToSaveRequest.getImageLinks())
                .build();

        return book;
    }
}
