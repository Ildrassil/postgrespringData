package de.buhl.postgrespringdata.model.entity.submodel;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;

import java.util.List;

@Embeddable
public record SteuerInfo(

        String steuerId,
        long jahresGehalt,

        @ElementCollection(fetch = FetchType.EAGER)

        List<TaxDeductables> deductablesList,

        @ElementCollection(fetch = FetchType.EAGER)
        List<TaxFreeIncome> taxFreeIncomeList
) {
}
