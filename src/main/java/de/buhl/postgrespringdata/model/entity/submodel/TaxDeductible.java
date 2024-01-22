package de.buhl.postgrespringdata.model.entity.submodel;


import jakarta.persistence.Embeddable;

@Embeddable
public record TaxDeductible(

        String deductibleId,

        String name,

        long deductibleAmount
) {
}
