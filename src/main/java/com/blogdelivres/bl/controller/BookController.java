package com.blogdelivres.bl.controller;

import com.blogdelivres.bl.dto.BookDTO;
import com.blogdelivres.bl.dto.BookToSaveRequest;
import com.blogdelivres.bl.service.Book.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "book")
@CrossOrigin("http://localhost:4200")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping(path = "library/{libraryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> saveBook(
            @RequestHeader("Authorization") String token,
            @RequestBody BookToSaveRequest book,
            @PathVariable ("libraryId") Long libraryId){

            return new ResponseEntity<>(bookService.createBookInDb(book,libraryId,token), HttpStatus.CREATED);
    }

    @GetMapping(path = "library/{libraryId}")
    public ResponseEntity<List<BookDTO>> getBooksFromLibrary (@PathVariable ("libraryId") Long libraryId){

        return ResponseEntity.ok(bookService.getBooksFromLibraryId(libraryId));
    }

    @GetMapping(path = "{bookId}")
    public ResponseEntity<BookDTO> getBookFromId(@PathVariable ("bookId") String googleId){
        return ResponseEntity.ok(bookService.getBookFromId(googleId));
    }

    @DeleteMapping(path = "{bookId}/library/{libraryId}")
    public ResponseEntity<BookDTO> deleteBookFromLib(@PathVariable ("bookId") String googleId,
                                                     @PathVariable ("libraryId") Long libraryId) {
        return ResponseEntity.ok(bookService.deleteBookFromLib(googleId, libraryId));

    }
}
