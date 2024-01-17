package de.buhl.postgrespringdata.model.entity.submodel;

import jakarta.persistence.Embeddable;

@Embeddable
public record TaxFreeIncome(
        String taxId,
        String name,
        long amount
) {
}
