package br.dev.ltres.poc.integrateams.domain.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import br.dev.ltres.poc.integrateams.domain.model.AgendaEvento;

public interface AgendaEventoRepository {

    AgendaEvento salvar(AgendaEvento evento);

    Optional<AgendaEvento> buscaEvento(Long id);

    AgendaEvento[] buscarEventosPeriodo(LocalDateTime inicio, LocalDateTime fim);

    AgendaEvento[] buscarEventosPorCategoria(String categoria);

    AgendaEvento[] buscarEventosTodos();

    void excluir(Long id);
}
