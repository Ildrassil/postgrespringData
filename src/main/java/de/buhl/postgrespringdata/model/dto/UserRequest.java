package de.buhl.postgrespringdata.model.dto;

import de.buhl.postgrespringdata.model.entity.submodel.SteuerInfo;
import de.buhl.postgrespringdata.model.entity.submodel.UserInfo;

public record UserRequest(

        String userName,
        String password,
        UserInfo userInfo,
        SteuerInfo steuerInfo
) {
}
