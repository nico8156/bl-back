package com.blogdelivres.bl.Controller;


import com.blogdelivres.bl.controller.LibraryController;
import com.blogdelivres.bl.dto.LibraryDTO;
import com.blogdelivres.bl.dto.LibraryToSaveRequest;
import com.blogdelivres.bl.repository.UserRepository;
import com.blogdelivres.bl.service.Library.LibraryService;
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

@WebMvcTest(controllers = LibraryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class LibraryControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LibraryService libraryService;

    @MockBean
    private UserRepository userRepository;

    private LibraryDTO libraryDTO;
    private LibraryToSaveRequest libraryToSaveRequest;

    @BeforeEach
    public void init() {
        libraryDTO = LibraryDTO.builder()
                .libraryName("test")
                .build();

        libraryToSaveRequest = LibraryToSaveRequest.builder()
                .name("test")
                .build();
    }

    @Test
    public void LibraryController_CreateLibrary_ReturnCreated() throws Exception {
        given(libraryService.createLibrary(ArgumentMatchers.any(LibraryToSaveRequest.class),ArgumentMatchers.any(String.class))).willReturn(libraryDTO);
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwOTMxMjcyNCwiZXhwIjoxNzA5MzE0NTI0fQ.GE-qvuAUKzA4qE25qUC6KPg7CkvQhVo4E0ABZ_Ip-Zg";
        ResultActions response = mockMvc.perform(post("/library/save")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(libraryToSaveRequest)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.libraryName", CoreMatchers.is(libraryDTO.getLibraryName())));

    }
    @Test
    public void LibraryController_GetLibraries_ReturnListOfLibraryDTO() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwOTMxMjcyNCwiZXhwIjoxNzA5MzE0NTI0fQ.GE-qvuAUKzA4qE25qUC6KPg7CkvQhVo4E0ABZ_Ip-Zg";
        when(libraryService.getAllLibrariesForUser(ArgumentMatchers.any(String.class))).thenReturn(Arrays.asList(libraryDTO));

        ResultActions response = mockMvc.perform(get("/library")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(libraryDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(libraryDTO).size())));

    }

}
