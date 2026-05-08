package se.iths.axel.usersapi.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import se.iths.axel.usersapi.dto.AppUserRequestDTO;
import se.iths.axel.usersapi.dto.AppUserResponseDTO;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AppUserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Get all users should return status OK")
    void getAllUsersReturnOk() throws Exception {
        mockMvc.perform(get("/appusers"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get user by ID should return status OK")
    void getUserByIdReturnsOk() throws Exception {
        AppUserRequestDTO dto = new AppUserRequestDTO("axel", "password");

        mockMvc.perform(post("/appusers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/appusers/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Create user should return status CREATED")
    void createUserReturnsCreated() throws Exception {
        AppUserRequestDTO dto = new AppUserRequestDTO("peter", "pass");

        mockMvc.perform(post("/appusers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Update user should return status OK")
    void updateUserReturnsOk() throws Exception {
        AppUserRequestDTO dto = new AppUserRequestDTO("johan", "pass");

        MvcResult createResult = mockMvc.perform(post("/appusers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn();

        AppUserResponseDTO createdUser = objectMapper.readValue(
                createResult.getResponse().getContentAsString(),
                AppUserResponseDTO.class
        );

        AppUserRequestDTO updateDTO = new AppUserRequestDTO("bertil", "lösenord");


        mockMvc.perform(put("/appusers/{id}", createdUser.id())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete user should return status NO_CONTENT")
    void deleteUserReturnsNoContent() throws Exception {
        AppUserRequestDTO appUserRequestDTO = new AppUserRequestDTO("linus", "pass");

        MvcResult createResult = mockMvc.perform(post("/appusers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(appUserRequestDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        AppUserResponseDTO createdUser = objectMapper.readValue(createResult.getResponse().getContentAsString(),
                AppUserResponseDTO.class);

        mockMvc.perform(delete("/appusers/{id}", createdUser.id()))
                .andExpect(status().isNoContent());
    }
}
