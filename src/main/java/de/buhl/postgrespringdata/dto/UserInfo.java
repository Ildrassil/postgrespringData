package de.buhl.postgrespringdata.dto;

public record UserInfo(
        String firstName,
        String lastName,
        String street,
        String city,
        String country,
        String companyName
) {
}
