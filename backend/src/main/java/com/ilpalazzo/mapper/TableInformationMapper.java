package com.ilpalazzo.mapper;

import com.ilpalazzo.model.dto.TableInformationRequestDto;
import com.ilpalazzo.model.dto.TableInformationResponseDto;
import com.ilpalazzo.model.entity.TableInformation;

public class TableInformationMapper {

    public static TableInformation toEntity(TableInformationRequestDto dto) {
        TableInformation entity = new TableInformation();
        entity.setTableName(dto.getTableName());
        entity.setQrCodeUrl(dto.getQrCodeUrl());
        return entity;
    }

    public static TableInformationResponseDto toResponse(TableInformation entity) {
        TableInformationResponseDto dto = new TableInformationResponseDto();
        dto.setTableId(entity.getTableId());
        dto.setTableName(entity.getTableName());
        dto.setQrCodeUrl(entity.getQrCodeUrl());
        return dto;
    }
}
