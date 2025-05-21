package com.ilpalazzo.service.impl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.ilpalazzo.errors.LoginInfoNotFoundException;
import com.ilpalazzo.model.entity.LoginInfo;
import com.ilpalazzo.repository.LoginInfoRepository;

class LoginInfoServiceImplTest {

    @Mock
    private LoginInfoRepository loginInfoRepository;

    @InjectMocks
    private LoginInfoServiceImpl loginInfoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createLoginInfo_shouldReturnSavedLoginInfo() {
        LoginInfo loginInfo = new LoginInfo("63651687-80d9-4a8a-b3e6-e3c7924dac32", "pendir", "grenade55", "WAITER");
        when(loginInfoRepository.save(loginInfo)).thenReturn(loginInfo);

        LoginInfo result = loginInfoService.createLoginInfo(loginInfo);

        assertNotNull(result);
        assertEquals("63651687-80d9-4a8a-b3e6-e3c7924dac32", result.getUserId());
        verify(loginInfoRepository, times(1)).save(loginInfo);
    }

    @Test
    void getAllLoginInfos_shouldReturnAllLoginInfos() {
        List<LoginInfo> list = List.of(
                new LoginInfo("63651687-80d9-4a8a-b3e6-e3c7924dac32", "pendir", "pass1", "WAITER"),
                new LoginInfo("e7757052-bdfa-4946-9210-f56beb0510f4", "zbebz", "coffeeaddict", "MANAGER")
        );
        when(loginInfoRepository.findAll()).thenReturn(list);

        List<LoginInfo> result = loginInfoService.getAllLoginInfos();

        assertEquals(2, result.size());
        verify(loginInfoRepository).findAll();
    }

    @Test
    void getLoginInfoById_shouldReturnLoginInfo() {
        LoginInfo loginInfo = new LoginInfo("63651687-80d9-4a8a-b3e6-e3c7924dac32", "pendir", "grenade55", "WAITER");
        when(loginInfoRepository.findById("63651687-80d9-4a8a-b3e6-e3c7924dac32")).thenReturn(Optional.of(loginInfo));

        LoginInfo result = loginInfoService.getLoginInfoById("63651687-80d9-4a8a-b3e6-e3c7924dac32");

        assertNotNull(result);
        assertEquals("pendir", result.getUsername());
    }

    @Test
    void getLoginInfoById_whenNotFound_shouldThrowException() {
        when(loginInfoRepository.findById("63651687-80d9-4a8a-b3e6-e3c7924dac32")).thenReturn(Optional.empty());

        assertThrows(LoginInfoNotFoundException.class, () -> loginInfoService.getLoginInfoById("63651687-80d9-4a8a-b3e6-e3c7924dac32"));
    }

    @Test
    void getLoginInfoByUsernameAndPassword_shouldReturnLoginInfo() {
        LoginInfo loginInfo = new LoginInfo("63651687-80d9-4a8a-b3e6-e3c7924dac32", "pendir", "grenade55", "WAITER");
        when(loginInfoRepository.findByUsernameAndPassword("pendir", "grenade55")).thenReturn(loginInfo);

        LoginInfo result = loginInfoService.getLoginInfoByUsernameandPassw("pendir", "grenade55");

        assertEquals("63651687-80d9-4a8a-b3e6-e3c7924dac32", result.getUserId());
    }

    @Test
    void deleteLoginInfo_shouldCallDeleteById() {
        when(loginInfoRepository.existsById("63651687-80d9-4a8a-b3e6-e3c7924dac32")).thenReturn(true);

        loginInfoService.deleteLoginInfo("63651687-80d9-4a8a-b3e6-e3c7924dac32");

        verify(loginInfoRepository).deleteById("63651687-80d9-4a8a-b3e6-e3c7924dac32");
    }

    @Test
    void deleteLoginInfo_whenNotFound_shouldThrowException() {
        when(loginInfoRepository.existsById("63651687-80d9-4a8a-b3e6-e3c7924dac32")).thenReturn(false);

        assertThrows(LoginInfoNotFoundException.class, () -> loginInfoService.deleteLoginInfo("63651687-80d9-4a8a-b3e6-e3c7924dac32"));
    }

    @Test
        void updateLoginInfo_shouldUpdateAndReturnUpdatedInfo() {
            LoginInfo existing = new LoginInfo("63651687-80d9-4a8a-b3e6-e3c7924dac32", "pendir", "grenade55", "WAITER");
            LoginInfo update = new LoginInfo("63651687-80d9-4a8a-b3e6-e3c7924dac32", "tuntun", "cspro77", "MANAGER");
            when(loginInfoRepository.findById("63651687-80d9-4a8a-b3e6-e3c7924dac32")).thenReturn(Optional.of(existing));
            when(loginInfoRepository.save(any(LoginInfo.class))).thenReturn(update);

            LoginInfo result = loginInfoService.updateLoginInfo("63651687-80d9-4a8a-b3e6-e3c7924dac32", update);

            assertEquals("tuntun", result.getUsername());
            assertEquals("MANAGER", result.getRole());
        }
}