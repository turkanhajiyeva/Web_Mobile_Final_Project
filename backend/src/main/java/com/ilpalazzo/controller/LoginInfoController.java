package com.ilpalazzo.controller;

import com.ilpalazzo.model.entity.LoginInfo;
import com.ilpalazzo.service.LoginInfoService;
import com.ilpalazzo.security.JwtTokenService;
import com.ilpalazzo.security.JwtUtil;
import java.util.Map;

import com.ilpalazzo.repository.LoginInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/logininfo")
public class LoginInfoController {

    @Autowired
    private LoginInfoService loginInfoService;

    @Autowired
    private LoginInfoRepository loginInfoRepository;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private JwtUtil jwtUtil;

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
    public ResponseEntity<?> authenticate(@RequestBody LoginInfo loginRequest) {
        LoginInfo loginInfo = loginInfoService.getLoginInfoByUsernameandPassw(
            loginRequest.getUsername(), loginRequest.getPassword());

        if (loginInfo != null) {
            String token = jwtUtil.generateToken(loginInfo.getUsername());
            return ResponseEntity.ok().body(Map.of("token", token, "user", loginInfo));
        } else {
            return ResponseEntity.status(401).build();
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

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String token = extractToken(request);
        if (token != null && jwtUtil.validateToken(token)) {
            long expiry = jwtUtil.getExpirationDuration(token);
            jwtTokenService.blacklistToken(token, expiry);
            return ResponseEntity.ok("Logged out successfully");
        }
        return ResponseEntity.badRequest().body("Invalid token");
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
