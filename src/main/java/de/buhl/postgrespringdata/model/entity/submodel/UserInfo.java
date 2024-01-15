package de.buhl.postgrespringdata.model.entity.submodel;

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
