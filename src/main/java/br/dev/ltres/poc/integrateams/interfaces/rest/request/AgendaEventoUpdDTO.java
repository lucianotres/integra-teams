package br.dev.ltres.poc.integrateams.interfaces.rest.request;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AgendaEventoUpdDTO(
        @NotNull(message = "ID é obrigatório") Long id,
        @NotBlank(message = "Título é obrigatório") String titulo,
        String descricao,
        List<String> categorias,
        @NotNull(message = "Data de início é obrigatória") LocalDateTime inicio,
        @NotNull(message = "Data de fim é obrigatória") LocalDateTime fim) {
}
