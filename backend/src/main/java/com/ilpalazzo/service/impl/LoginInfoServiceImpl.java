package com.ilpalazzo.service.impl;

import com.ilpalazzo.errors.LoginInfoNotFoundException;
import com.ilpalazzo.model.entity.LoginInfo;
import com.ilpalazzo.repository.LoginInfoRepository;
import com.ilpalazzo.service.LoginInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginInfoServiceImpl implements LoginInfoService {

    private static final Logger log = LoggerFactory.getLogger(LoginInfoServiceImpl.class);

    @Autowired
    private LoginInfoRepository loginInfoRepository;

    @Override
    public LoginInfo createLoginInfo(LoginInfo loginInfo) {
        return loginInfoRepository.save(loginInfo);
    }

    @Override
    public List<LoginInfo> getAllLoginInfos() {
        return loginInfoRepository.findAll();
    }

    @Override
    public LoginInfo getLoginInfoById(String userId) {
        return loginInfoRepository.findById(userId)
                .orElseThrow(() -> new LoginInfoNotFoundException(userId));
    }
    
    @Override
    public LoginInfo getLoginInfoByUsernameandPassw(String username, String password) {
        return loginInfoRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public void deleteLoginInfo(String userId) {
        if (!loginInfoRepository.existsById(userId)) {
            throw new LoginInfoNotFoundException(userId);
        }
        loginInfoRepository.deleteById(userId);
    }

    @Override
    public LoginInfo updateLoginInfo(String userId, LoginInfo updatedLoginInfo) {
        LoginInfo existing = loginInfoRepository.findById(userId)
                .orElseThrow(() -> new LoginInfoNotFoundException(userId));

        existing.setUsername(updatedLoginInfo.getUsername());
        existing.setPassword(updatedLoginInfo.getPassword());
        existing.setRole(updatedLoginInfo.getRole());

        LoginInfo saved = loginInfoRepository.save(existing);
        log.info("Updated LoginInfo with userId {}", userId);
        return saved;
    }
}
