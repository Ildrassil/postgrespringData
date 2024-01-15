package de.buhl.postgrespringdata.model.entity.submodel;

import java.util.List;

public record SteuerInfo(

        String steuerId,
        long jahresGehalt,
        List<TaxDeductables> deductablesList,
        List<TaxFreeIncome> taxFreeIncomeList
) {
}
