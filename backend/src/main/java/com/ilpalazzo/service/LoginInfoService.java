package com.ilpalazzo.service;

import java.util.List;

import com.ilpalazzo.model.entity.LoginInfo;

public interface LoginInfoService {
    LoginInfo createLoginInfo(LoginInfo loginInfo);
    List<LoginInfo> getAllLoginInfos();
    LoginInfo getLoginInfoById(String userId);
    void deleteLoginInfo(String userId);
    LoginInfo updateLoginInfo(String userId, LoginInfo updatedLoginInfo);
    LoginInfo getLoginInfoByUsernameandPassw(String username, String password);
}
