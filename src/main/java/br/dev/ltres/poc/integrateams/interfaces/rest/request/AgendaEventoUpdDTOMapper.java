package br.dev.ltres.poc.integrateams.interfaces.rest.request;

import br.dev.ltres.poc.integrateams.application.dto.AgendaEventoDTO;

public class AgendaEventoUpdDTOMapper {
    public static AgendaEventoDTO toApp(AgendaEventoUpdDTO dto) {
        return new AgendaEventoDTO(
                dto.id(),
                dto.titulo(),
                dto.descricao(),
                dto.categorias(),
                dto.inicio(),
                dto.fim(),
                null,
                null,
                null,
                null);
    }

}
