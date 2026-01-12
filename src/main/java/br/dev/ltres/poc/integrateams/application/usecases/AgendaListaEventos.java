package br.dev.ltres.poc.integrateams.application.usecases;

import java.time.LocalDateTime;
import java.util.List;

import br.dev.ltres.poc.integrateams.application.dto.AgendaEventoDTO;
import br.dev.ltres.poc.integrateams.application.dto.AgendaEventoDTOMapper;
import br.dev.ltres.poc.integrateams.domain.model.AgendaEvento;
import br.dev.ltres.poc.integrateams.domain.repository.AgendaEventoRepository;

public class AgendaListaEventos {
    private final AgendaEventoRepository repository;

    public AgendaListaEventos(AgendaEventoRepository repository) {
        this.repository = repository;
    }

    private List<AgendaEventoDTO> fromDomainList(List<AgendaEvento> eventos) {
        return eventos.stream()
                .map(AgendaEventoDTOMapper::fromDomain)
                .toList();
    }

    public List<AgendaEventoDTO> executaTodos() {
        return fromDomainList(repository.buscarEventosTodos());
    }

    public List<AgendaEventoDTO> executaPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio == null || fim == null || inicio.isAfter(fim))
            throw new IllegalArgumentException("Período invalido ou não informado.");

        return fromDomainList(repository.buscarEventosPeriodo(inicio, fim));
    }

    public List<AgendaEventoDTO> executaPorCategoria(String categoria) {
        if (categoria == null || categoria.isBlank())
            throw new IllegalArgumentException("Categoria não informada.");

        return fromDomainList(repository.buscarEventosPorCategoria(categoria));
    }
}
