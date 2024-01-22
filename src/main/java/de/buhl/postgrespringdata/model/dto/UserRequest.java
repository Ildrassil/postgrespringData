package de.buhl.postgrespringdata.model.dto;

import de.buhl.postgrespringdata.model.entity.submodel.TaxInfo;
import de.buhl.postgrespringdata.model.entity.submodel.UserInfo;

public record UserRequest(

        String userName,
        String password,
        UserInfo userInfo,
        TaxInfo taxInfo
) {
}
