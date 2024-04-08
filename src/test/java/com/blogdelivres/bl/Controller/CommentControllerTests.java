package com.blogdelivres.bl.Controller;

import com.blogdelivres.bl.controller.BookController;
import com.blogdelivres.bl.controller.CommentController;
import com.blogdelivres.bl.dto.BookToSaveRequest;
import com.blogdelivres.bl.dto.CommentDTO;
import com.blogdelivres.bl.dto.CommentToSaveRequest;
import com.blogdelivres.bl.dto.UserDto;
import com.blogdelivres.bl.entity.Book;
import com.blogdelivres.bl.entity.Comment;
import com.blogdelivres.bl.entity.UserBis;
import com.blogdelivres.bl.enums.UserRole;
import com.blogdelivres.bl.service.Book.BookService;
import com.blogdelivres.bl.service.Comment.CommentService;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CommentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    @MockBean
    private BookService bookService;

    private CommentToSaveRequest comment;
    private CommentDTO commentDTO;
    private UserDto userDto;

    @BeforeEach
    public void init() {
        commentDTO = CommentDTO.builder()
                .userDto(userDto)
                .GoogleId("AAAAAA")
                .parentId(null)
                .title("Ceci est un test")
                .Content("Merci d'avoir testé ce commentaire")
                .build();

        comment = CommentToSaveRequest.builder()
                .title("Ceci est un test")
                .content("Merci d'avoir testé ce commentaire")
                .parentId(null)
                .build();

    }

    @Test
    public void CommentController_CreateComment_ReturnCreated() throws Exception {

        given(commentService.createCommentInDb(ArgumentMatchers.any(CommentToSaveRequest.class),ArgumentMatchers.any(),ArgumentMatchers.any())).willReturn(commentDTO);
        String token = "votre_token_d'autorisation";
        ResultActions response = mockMvc.perform(post("/comment/book/AAAAAA")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(comment)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(commentDTO.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(commentDTO.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.googleId", CoreMatchers.is(commentDTO.getGoogleId())));

    }
    @Test
    public void CommentController_GetComments_ReturnListOfCommentDTO() throws Exception {
        String googleId = "AAAAAA";
        when(commentService.getComments(googleId)).thenReturn(Arrays.asList(commentDTO));

        ResultActions response = mockMvc.perform(get("/comment/book/AAAAAA")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(commentDTO).size())));

    }


}
