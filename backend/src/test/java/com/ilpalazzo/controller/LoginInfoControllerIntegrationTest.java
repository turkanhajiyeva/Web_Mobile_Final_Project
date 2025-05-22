package com.ilpalazzo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilpalazzo.model.entity.LoginInfo;
import com.ilpalazzo.repository.LoginInfoRepository;
import com.ilpalazzo.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import com.ilpalazzo.ilpalazzoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ilpalazzoApplication.class)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class LoginInfoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LoginInfoRepository loginInfoRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private String token;

    @BeforeEach
    void setup() {
        loginInfoRepository.deleteAll();

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserId("test-user-id");
        loginInfo.setUsername("testuser");
        loginInfo.setPassword("password");
        loginInfo.setRole("ROLE_USER");
        loginInfoRepository.save(loginInfo);

        token = jwtUtil.generateToken("testuser");
    }

    @Test
    void getLoginInfoById_shouldReturnLoginInfo() throws Exception {
        LoginInfo saved = loginInfoRepository.findAll().get(0);
        mockMvc.perform(get("/api/logininfo/" + saved.getUserId())
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void deleteLoginInfo_shouldDelete() throws Exception {
        LoginInfo saved = loginInfoRepository.findAll().get(0);
        mockMvc.perform(delete("/api/logininfo/" + saved.getUserId())
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateLoginInfo_shouldUpdate() throws Exception {
        LoginInfo saved = loginInfoRepository.findAll().get(0);
        saved.setPassword("newpassword");

        mockMvc.perform(put("/api/logininfo/" + saved.getUserId())
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(saved)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").value("newpassword"));
    }

    @Test
    void authenticate_shouldReturnLoginInfoIfValid() throws Exception {
        LoginInfo loginRequest = new LoginInfo();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password");

        mockMvc.perform(post("/api/logininfo/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.username").value("testuser"))  // Note: "user" is nested in response
                .andExpect(jsonPath("$.token").exists());
    }
}
