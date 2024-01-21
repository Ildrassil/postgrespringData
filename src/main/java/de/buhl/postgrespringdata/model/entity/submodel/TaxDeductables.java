package de.buhl.postgrespringdata.model.entity.submodel;


import jakarta.persistence.Embeddable;

@Embeddable
public record TaxDeductables(

        String deductableId,

        String name,

        long deductableAmount
) {
}
