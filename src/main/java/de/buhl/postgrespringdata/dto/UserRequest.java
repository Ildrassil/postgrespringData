package de.buhl.postgrespringdata.dto;

public record UserRequest(

        String userName,
        String password,
        UserInfo userInfo,
        SteuerInfo steuerInfo
) {
}
