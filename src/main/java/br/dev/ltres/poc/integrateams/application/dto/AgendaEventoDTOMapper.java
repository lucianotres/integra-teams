package br.dev.ltres.poc.integrateams.application.dto;

import br.dev.ltres.poc.integrateams.domain.model.AgendaEvento;

public class AgendaEventoDTOMapper {
    public static AgendaEventoDTO fromDomain(AgendaEvento evento) {
        return new AgendaEventoDTO(
                evento.getId(),
                evento.getTitulo(),
                evento.getDescricao(),
                evento.getCategorias(),
                evento.getInicio(),
                evento.getFim());
    }
}
