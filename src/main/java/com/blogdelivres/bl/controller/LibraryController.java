package com.blogdelivres.bl.controller;
import com.blogdelivres.bl.dto.LibraryDTO;
import com.blogdelivres.bl.dto.LibraryToSaveRequest;
import com.blogdelivres.bl.service.Library.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "library")
@CrossOrigin("http://localhost:4200")
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService){
        this.libraryService = libraryService;
    }

    @GetMapping()
    public ResponseEntity<?> getLibraries (@RequestHeader("Authorization") String token ){
        return ResponseEntity.ok(libraryService.getAllLibrariesForUser(token));
    }
    @PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createLibrary(
            @RequestBody LibraryToSaveRequest libraryToSaveRequest,
            @RequestHeader("Authorization") String token
    ){
        return new ResponseEntity<>(libraryService.createLibrary(libraryToSaveRequest, token), HttpStatus.CREATED);
    }
    @PutMapping(path = "update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LibraryDTO> updateLibrary(
            @RequestBody LibraryToSaveRequest libraryToSaveRequest,
            @PathVariable ("id") Long id
    ){
        return ResponseEntity.ok(libraryService.updateLibrary(libraryToSaveRequest, id));
    }

    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity<LibraryDTO> deleteLibrary(
            @PathVariable ("id") Long id
    ){
        return ResponseEntity.ok(libraryService.deleteLibrary(id));
    }
}
