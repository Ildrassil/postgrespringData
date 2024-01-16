package de.buhl.postgrespringdata.model.entity.submodel;

import org.hibernate.annotations.Subselect;


public record UserInfo(
        String email,
        String firstName,
        String lastName,
        String street,
        String city,
        String country,
        String companyName
) {
}
