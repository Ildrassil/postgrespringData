package de.buhl.postgrespringdata.model.dto;

import de.buhl.postgrespringdata.model.entity.submodel.SteuerInfo;
import de.buhl.postgrespringdata.model.entity.submodel.UserInfo;

public record UserResponse(
        String userName,
        UserInfo userInfo,
        SteuerInfo steuerInfo
) {
}
