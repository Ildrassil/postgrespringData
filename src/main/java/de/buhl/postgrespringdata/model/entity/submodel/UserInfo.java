package de.buhl.postgrespringdata.model.entity.submodel;


import jakarta.persistence.Embeddable;


@Embeddable

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
