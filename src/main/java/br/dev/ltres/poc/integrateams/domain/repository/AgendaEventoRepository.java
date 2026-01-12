package br.dev.ltres.poc.integrateams.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import br.dev.ltres.poc.integrateams.domain.model.AgendaEvento;

public interface AgendaEventoRepository {

    AgendaEvento salvar(AgendaEvento evento, boolean alteraIncluiMsGraph);

    Optional<AgendaEvento> buscaEvento(Long id);

    List<AgendaEvento> buscarEventosPeriodo(LocalDateTime inicio, LocalDateTime fim);

    List<AgendaEvento> buscarEventosPorCategoria(String categoria);

    List<AgendaEvento> buscarEventosTodos();

    boolean excluir(Long id);

    List<AgendaEvento> buscaEventosAEnviar();
}
