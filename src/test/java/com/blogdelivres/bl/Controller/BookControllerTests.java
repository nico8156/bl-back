package com.blogdelivres.bl.Controller;

import com.blogdelivres.bl.controller.BookController;
import com.blogdelivres.bl.dto.BookDTO;
import com.blogdelivres.bl.dto.BookToSaveRequest;
import com.blogdelivres.bl.service.Book.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private BookDTO bookDTO;
    private BookToSaveRequest book;
    private List<BookDTO> bookDTOList;

    @MockBean
    private BookService bookService;

    @BeforeEach
    public void init() {

        bookDTO = BookDTO.builder().googleId("AAAAAAA").title("test").authors("quelqu'un").publisher("d'autre").description("ceci est un test").pageCount(10).imageLinks("ceci est un lien").build();
        book = BookToSaveRequest.builder().googleId("AAAAAAA").title("test").authors("quelqu'un").publisher("d'autre").description("ceci est un test").pageCount(10).imageLinks("ceci est un lien").build();
    }

    @Test
    public void BookController_CreateBook_ReturnCreated() throws Exception {
        given(bookService.createBookInDb(ArgumentMatchers.any(BookToSaveRequest.class),ArgumentMatchers.any(),ArgumentMatchers.any())).willReturn(bookDTO);
        String token = "votre_token_d'autorisation";
        ResultActions response = mockMvc.perform(post("/book/library/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(book)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.googleId", CoreMatchers.is(bookDTO.getGoogleId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(bookDTO.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authors", CoreMatchers.is(bookDTO.getAuthors())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publisher", CoreMatchers.is(bookDTO.getPublisher())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(bookDTO.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.imageLinks", CoreMatchers.is(bookDTO.getImageLinks())));
    }
    @Test
    public void BookController_BookFromLibrary_ReturnListOfBookDto() throws Exception {
        Long libraryId = 1L;
        when(bookService.getBooksFromLibraryId(libraryId)).thenReturn(Arrays.asList(bookDTO));

        ResultActions response = mockMvc.perform(get("/book/library/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(bookDTO).size())));

    }
    @Test
    public void BookController_GetBookFromId_ReturnBookDTO() throws Exception {
        String googleId = "AAAAAAA";
        when(bookService.getBookFromId(googleId)).thenReturn(bookDTO);

        ResultActions response = mockMvc.perform(get("/book/AAAAAAA")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.googleId", CoreMatchers.is(bookDTO.getGoogleId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(bookDTO.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authors", CoreMatchers.is(bookDTO.getAuthors())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publisher", CoreMatchers.is(bookDTO.getPublisher())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(bookDTO.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.imageLinks", CoreMatchers.is(bookDTO.getImageLinks())));
    }

}
