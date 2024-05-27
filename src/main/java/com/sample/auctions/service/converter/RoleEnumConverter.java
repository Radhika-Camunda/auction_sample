package com.sample.auctions.service.converter;

import com.sample.auctions.model.user.RoleEnum;

import jakarta.persistence.AttributeConverter;

public class RoleEnumConverter implements AttributeConverter<RoleEnum, String> {


    @Override
    public String convertToDatabaseColumn(RoleEnum roleEnum) {

        if (roleEnum == null) {
            return null;
        }

        String roleEnumString = null;

        switch (roleEnum) {
            case ROLE_ADMIN -> roleEnumString = "SELLER";
            case ROLE_USER -> roleEnumString = "BUYER";
        }
        return roleEnumString;

    }

    @Override
    public RoleEnum convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }

        RoleEnum roleEnum = null;

        switch (s) {
            case "SELLER" -> roleEnum = RoleEnum.ROLE_ADMIN;
            case "BUYER" -> roleEnum = RoleEnum.ROLE_USER;
        }
        return roleEnum;
    }
}
