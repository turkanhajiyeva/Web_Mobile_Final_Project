package com.ilpalazzo.controller;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilpalazzo.model.entity.LoginInfo;
import com.ilpalazzo.repository.LoginInfoRepository;

@SpringBootTest
@AutoConfigureMockMvc
class LoginInfoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoginInfoRepository loginInfoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanUp() {
        loginInfoRepository.deleteAll();
    }

    @Test
    void createLoginInfo_shouldReturnCreatedLoginInfo() throws Exception {
        LoginInfo loginInfo = new LoginInfo(
            UUID.randomUUID().toString(), "testuser", "pass123", "WAITER"
        );

        mockMvc.perform(post("/api/logininfo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginInfo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.role").value("WAITER"));
    }

    @Test
    void getAllLoginInfos_shouldReturnList() throws Exception {
        LoginInfo l1 = new LoginInfo(UUID.randomUUID().toString(), "u1", "p1", "MANAGER");
        LoginInfo l2 = new LoginInfo(UUID.randomUUID().toString(), "u2", "p2", "WAITER");
        loginInfoRepository.save(l1);
        loginInfoRepository.save(l2);

        mockMvc.perform(get("/api/logininfo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getLoginInfoById_shouldReturnLoginInfo() throws Exception {
        LoginInfo loginInfo = new LoginInfo("123", "user", "pass", "WAITER");
        loginInfoRepository.save(loginInfo);

        mockMvc.perform(get("/api/logininfo/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user"));
    }

    @Test
    void authenticate_shouldReturnLoginInfoIfValid() throws Exception {
        LoginInfo loginInfo = new LoginInfo("999", "authuser", "authpass", "ADMIN");
        loginInfoRepository.save(loginInfo);

        LoginInfo request = new LoginInfo();
        request.setUsername("authuser");
        request.setPassword("authpass");

        mockMvc.perform(post("/api/logininfo/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    void deleteLoginInfo_shouldDelete() throws Exception {
        LoginInfo loginInfo = new LoginInfo("toDelete", "deluser", "delpass", "WAITER");
        loginInfoRepository.save(loginInfo);

        mockMvc.perform(delete("/api/logininfo/toDelete"))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateLoginInfo_shouldUpdate() throws Exception {
        LoginInfo existing = new LoginInfo("toUpdate", "olduser", "oldpass", "WAITER");
        loginInfoRepository.save(existing);

        LoginInfo updated = new LoginInfo("toUpdate", "newuser", "newpass", "MANAGER");

        mockMvc.perform(put("/api/logininfo/toUpdate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("newuser"))
                .andExpect(jsonPath("$.role").value("MANAGER"));
    }
}
