package com.ilpalazzo.mapper;

import com.ilpalazzo.model.dto.LoginInfoRequestDto;
import com.ilpalazzo.model.dto.LoginInfoResponseDto;
import com.ilpalazzo.model.entity.LoginInfo;

public class LoginInfoMapper {

    public static LoginInfo toEntity(LoginInfoRequestDto dto) {
        if (dto == null) {
            return null;
        }
        LoginInfo entity = new LoginInfo();
        entity.setUserId(dto.getUserId());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
        return entity;
    }

    public static LoginInfoResponseDto toResponse(LoginInfo entity) {
        if (entity == null) {
            return null;
        }
        LoginInfoResponseDto dto = new LoginInfoResponseDto();
        dto.setUserId(entity.getUserId());
        dto.setUsername(entity.getUsername());
        dto.setRole(entity.getRole());
        return dto;
    }
}
