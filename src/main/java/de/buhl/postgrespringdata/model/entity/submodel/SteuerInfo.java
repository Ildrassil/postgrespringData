package de.buhl.postgrespringdata.model.entity.submodel;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Embeddable
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SteuerInfo{

        private String steuerId;

        private long jahresGehalt;

        @ElementCollection(fetch = FetchType.EAGER)
        private List<TaxDeductables> deductablesList;

        @ElementCollection(fetch = FetchType.EAGER)
        private List<TaxFreeIncome> taxFreeIncomeList;

}
