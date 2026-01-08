package br.dev.ltres.poc.integrateams.application.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AgendaEventoDTO(Long id,
        String titulo,
        String descricao,
        List<String> categorias,
        LocalDateTime inicio,
        LocalDateTime fim) {
}
