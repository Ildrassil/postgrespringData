package de.buhl.postgrespringdata.model.dto;

import java.time.Instant;

public record ErrorMessage(
        String errorMessage,
        Instant errorTime
) {
    public ErrorMessage(String errorMessage){
        this(errorMessage, Instant.now());
    }

}
