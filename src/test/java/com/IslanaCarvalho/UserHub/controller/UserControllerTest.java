package com.IslanaCarvalho.UserHub.controller;

import com.IslanaCarvalho.UserHub.business.UserService;
import com.IslanaCarvalho.UserHub.infrastructure.dto.request.CreateUserDTO;
import com.IslanaCarvalho.UserHub.infrastructure.dto.request.UpdateUserDTO;
import com.IslanaCarvalho.UserHub.infrastructure.dto.response.UserResponseDTO;
import com.IslanaCarvalho.UserHub.infrastructure.exception.EmailAlreadyRegisteredException;
import com.IslanaCarvalho.UserHub.infrastructure.exception.UserExceptionHandler;
import com.IslanaCarvalho.UserHub.infrastructure.exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(UserExceptionHandler.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void createUser_returnsUserResponse() throws Exception {
        CreateUserDTO request = CreateUserDTO.builder()
                .email("ana@example.com")
                .password("Secret123")
                .name("Ana")
                .build();

        UserResponseDTO response = UserResponseDTO.builder()
                .id(1L)
                .email("ana@example.com")
                .name("Ana")
                .build();

        when(userService.saveUser(ArgumentMatchers.any(CreateUserDTO.class)))
                .thenReturn(response);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("ana@example.com"))
                .andExpect(jsonPath("$.name").value("Ana"));
    }

    @Test
    void createUser_emailAlreadyRegistered_returnsConflict() throws Exception {
        CreateUserDTO request = CreateUserDTO.builder()
                .email("ana@example.com")
                .password("Secret123")
                .name("Ana")
                .build();

        when(userService.saveUser(ArgumentMatchers.any(CreateUserDTO.class)))
                .thenThrow(new EmailAlreadyRegisteredException("Email already registered"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void createUser_invalidEmail_returnsBadRequest() throws Exception {
        CreateUserDTO request = CreateUserDTO.builder()
                .email("invalid-email")
                .password("Secret123")
                .name("Ana")
                .build();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_missingName_returnsBadRequest() throws Exception {
        CreateUserDTO request = CreateUserDTO.builder()
                .email("ana@example.com")
                .password("Secret123")
                .name("")
                .build();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void searchUserByEmail_returnsUserResponse() throws Exception {
        UserResponseDTO response = UserResponseDTO.builder()
                .id(2L)
                .email("bia@example.com")
                .name("Bia")
                .build();

        when(userService.searchUserByEmail("bia@example.com"))
                .thenReturn(response);

        mockMvc.perform(get("/users/email/{email}", "bia@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.email").value("bia@example.com"))
                .andExpect(jsonPath("$.name").value("Bia"));
    }

    @Test
    void searchUserByEmail_notFound_returnsNotFound() throws Exception {
        when(userService.searchUserByEmail("missing@example.com"))
                .thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(get("/users/email/{email}", "missing@example.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    void home_returnsOkMessage() throws Exception {
        mockMvc.perform(get("/users/"))
                .andExpect(status().isOk())
                .andExpect(content().string("API UserHub funcionando 🚀"));
    }

    @Test
    void updateUserById_returnsUserResponse() throws Exception {
        UpdateUserDTO request = UpdateUserDTO.builder()
                .email("carlos@example.com")
                .password("NewSecret123")
                .name("Carlos")
                .build();

        UserResponseDTO response = UserResponseDTO.builder()
                .id(3L)
                .email("carlos@example.com")
                .name("Carlos")
                .build();

        when(userService.updateUserById(ArgumentMatchers.eq(3L), ArgumentMatchers.any(UpdateUserDTO.class)))
                .thenReturn(response);

        mockMvc.perform(put("/users/{id}", 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.email").value("carlos@example.com"))
                .andExpect(jsonPath("$.name").value("Carlos"));
    }

    @Test
    void updateUserById_notFound_returnsNotFound() throws Exception {
        UpdateUserDTO request = UpdateUserDTO.builder()
                .name("Carlos")
                .build();

        when(userService.updateUserById(ArgumentMatchers.eq(99L), ArgumentMatchers.any(UpdateUserDTO.class)))
                .thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(put("/users/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_invalidEmail_returnsBadRequest() throws Exception {
        UpdateUserDTO request = UpdateUserDTO.builder()
                .email("invalid-email")
                .name("Carlos")
                .build();

        mockMvc.perform(put("/users/{id}", 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteUserByEmail_returnsNoContent() throws Exception {
        doNothing().when(userService).deleteUserByEmail("duda@example.com");

        mockMvc.perform(delete("/users/email/{email}", "duda@example.com"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUserByEmail_notFound_returnsNotFound() throws Exception {
        org.mockito.Mockito.doThrow(new UserNotFoundException("User not found"))
                .when(userService).deleteUserByEmail("missing@example.com");

        mockMvc.perform(delete("/users/email/{email}", "missing@example.com"))
                .andExpect(status().isNotFound());
    }
}
