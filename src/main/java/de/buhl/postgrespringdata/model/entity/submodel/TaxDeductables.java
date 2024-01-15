package de.buhl.postgrespringdata.model.entity.submodel;

public record TaxDeductables(
        String id,
        String name,
        long deductable
) {
}
