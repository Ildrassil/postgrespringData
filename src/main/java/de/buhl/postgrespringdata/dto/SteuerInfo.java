package de.buhl.postgrespringdata.dto;

import java.util.List;

public record SteuerInfo(

        String steuerId,
        double jahresGehalt,
        List<TaxDeductables> deductablesList
) {
}
