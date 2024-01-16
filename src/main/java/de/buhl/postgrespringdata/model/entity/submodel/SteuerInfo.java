package de.buhl.postgrespringdata.model.entity.submodel;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.OneToMany;

import java.util.List;

@Embeddable
public record SteuerInfo(

        String steuerId,
        long jahresGehalt,

      @ElementCollection
        List<TaxDeductables> deductablesList,

        @ElementCollection
        List<TaxFreeIncome> taxFreeIncomeList
) {
}
