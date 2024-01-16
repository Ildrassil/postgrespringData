package de.buhl.postgrespringdata.model.entity.submodel;


import jakarta.persistence.Embedded;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;

import java.util.List;

public record SteuerInfo(

        String steuerId,
        long jahresGehalt,
        @ManyToMany(fetch = FetchType.LAZY)
        @JoinColumn(name = "tax_deductables_id")
        List<TaxDeductables> deductablesList,
        @ManyToMany(fetch = FetchType.LAZY)
        @JoinColumn(name = "tax_free_income_id")
        List<TaxFreeIncome> taxFreeIncomeList
) {
}
