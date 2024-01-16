package de.buhl.postgrespringdata.model.entity.submodel;


import jakarta.persistence.Embedded;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;

import java.util.List;


public record SteuerInfo(

        String steuerId,
        long jahresGehalt,

        @JoinColumn(name = "tax_deductables_id")
        @Embedded
        List<TaxDeductables> deductablesList,

        @JoinColumn(name = "tax_free_income_id")
        @Embedded
        List<TaxFreeIncome> taxFreeIncomeList
) {
}
