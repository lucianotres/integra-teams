package br.dev.ltres.poc.integrateams.application.usecases;

import br.dev.ltres.poc.integrateams.application.dto.AgendaEventoDTO;
import br.dev.ltres.poc.integrateams.application.dto.AgendaEventoDTOMapper;
import br.dev.ltres.poc.integrateams.domain.model.AgendaEvento;
import br.dev.ltres.poc.integrateams.domain.repository.AgendaEventoRepository;

public class AgendaAtualizaEvento {
    private final AgendaEventoRepository repository;

    public AgendaAtualizaEvento(AgendaEventoRepository repository) {
        this.repository = repository;
    }

    public AgendaEventoDTO executa(AgendaEventoDTO dto) {
        if (dto == null || dto.id() == null)
            throw new IllegalArgumentException("Evento não informado ou inválido.");

        AgendaEvento evento = AgendaEventoDTOMapper.toDomain(dto);
        return AgendaEventoDTOMapper.fromDomain(repository.salvar(evento, false));
    }
}
