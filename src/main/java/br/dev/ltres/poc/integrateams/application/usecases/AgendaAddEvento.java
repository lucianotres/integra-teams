package br.dev.ltres.poc.integrateams.application.usecases;

import br.dev.ltres.poc.integrateams.application.dto.AgendaEventoDTO;
import br.dev.ltres.poc.integrateams.application.dto.AgendaEventoDTOMapper;
import br.dev.ltres.poc.integrateams.domain.model.AgendaEvento;
import br.dev.ltres.poc.integrateams.domain.repository.AgendaEventoRepository;

public class AgendaAddEvento {
    private final AgendaEventoRepository repository;

    public AgendaAddEvento(AgendaEventoRepository repository) {
        this.repository = repository;
    }

    public AgendaEventoDTO executa(AgendaEventoDTO dto) {
        AgendaEvento evento = AgendaEventoDTOMapper.toDomain(dto);
        return AgendaEventoDTOMapper.fromDomain(repository.salvar(evento));
    }

}
