package com.ilpalazzo.controller;

import com.ilpalazzo.model.entity.LoginInfo;
import com.ilpalazzo.service.LoginInfoService;
import com.ilpalazzo.repository.LoginInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logininfo")
public class LoginInfoController {

    @Autowired
    private LoginInfoService loginInfoService;

    @Autowired
    private LoginInfoRepository loginInfoRepository;

    @PostMapping
    public ResponseEntity<LoginInfo> createLoginInfo(@RequestBody LoginInfo loginInfo) {
        LoginInfo created = loginInfoService.createLoginInfo(loginInfo);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<LoginInfo>> getAllLoginInfos() {
        List<LoginInfo> list = loginInfoService.getAllLoginInfos();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<LoginInfo> getLoginInfoById(@PathVariable String userId) {
        LoginInfo loginInfo = loginInfoService.getLoginInfoById(userId);
        return ResponseEntity.ok(loginInfo);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<LoginInfo> authenticate(@RequestBody LoginInfo loginRequest) {
        LoginInfo loginInfo = loginInfoService.getLoginInfoByUsernameandPassw(
            loginRequest.getUsername(), loginRequest.getPassword());

        if (loginInfo != null) {
            return ResponseEntity.ok(loginInfo);
        } else {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteLoginInfo(@PathVariable String userId) {
        loginInfoService.deleteLoginInfo(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<LoginInfo> updateLoginInfo(
            @PathVariable String userId,
            @RequestBody LoginInfo updatedLoginInfo) {

        LoginInfo updated = loginInfoService.updateLoginInfo(userId, updatedLoginInfo);
        return ResponseEntity.ok(updated);
    }
}
