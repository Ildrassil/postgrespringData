package de.buhl.postgrespringdata.dto;

public record UserResponse(
        String userName,
        UserInfo userInfo,
        SteuerInfo steuerInfo
) {
}
