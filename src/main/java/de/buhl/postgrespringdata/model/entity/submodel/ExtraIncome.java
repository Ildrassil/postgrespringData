package de.buhl.postgrespringdata.model.entity.submodel;

import jakarta.persistence.Embeddable;

@Embeddable
public record ExtraIncome(

        String extraIncomeId,

        String name,

        long amount
) {
}
