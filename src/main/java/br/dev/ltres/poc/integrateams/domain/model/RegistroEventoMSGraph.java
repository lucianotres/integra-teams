package br.dev.ltres.poc.integrateams.domain.model;

import java.time.LocalDateTime;

public record RegistroEventoMSGraph(
        String id,
        String changeKey,
        LocalDateTime createdDateTime,
        LocalDateTime lastModifiedDateTime) {
}
