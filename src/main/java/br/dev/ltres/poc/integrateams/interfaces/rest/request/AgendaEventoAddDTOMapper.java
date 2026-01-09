package br.dev.ltres.poc.integrateams.interfaces.rest.request;

import br.dev.ltres.poc.integrateams.application.dto.AgendaEventoDTO;

public class AgendaEventoAddDTOMapper {
    public static AgendaEventoDTO toApp(AgendaEventoAddDTO dto) {
        return new AgendaEventoDTO(
                null,
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
