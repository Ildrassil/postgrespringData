package de.buhl.postgrespringdata.model.entity.submodel;

import jakarta.persistence.Embeddable;

@Embeddable
public record TaxFreeIncome(

        String taxFreeIncomeId,

        String name,

        long amount
) {
}
