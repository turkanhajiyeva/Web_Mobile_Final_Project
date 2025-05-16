package com.ilpalazzo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ilpalazzo.model.entity.LoginInfo;

public interface LoginInfoRepository extends JpaRepository<LoginInfo, String> {
    Optional<LoginInfo> findByUsername(String username);
    LoginInfo findByUsernameAndPassword(String username, String password);
}
