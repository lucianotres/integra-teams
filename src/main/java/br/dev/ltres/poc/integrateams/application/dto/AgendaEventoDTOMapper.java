package br.dev.ltres.poc.integrateams.application.dto;

import br.dev.ltres.poc.integrateams.domain.model.AgendaEvento;
import br.dev.ltres.poc.integrateams.domain.model.RegistroEventoMSGraph;

public class AgendaEventoDTOMapper {
    public static AgendaEventoDTO fromDomain(AgendaEvento evento) {
        if (evento == null) {
            return null;
        }

        var msGraph = evento.getRegistroGraph();

        return new AgendaEventoDTO(
                evento.getId(),
                evento.getTitulo(),
                evento.getDescricao(),
                evento.getCategorias(),
                evento.getInicio(),
                evento.getFim(),
                msGraph != null ? msGraph.id() : null,
                msGraph != null ? msGraph.changeKey() : null,
                msGraph != null ? msGraph.createdDateTime() : null,
                msGraph != null ? msGraph.lastModifiedDateTime() : null);
    }

    public static AgendaEvento toDomain(AgendaEventoDTO dto) {
        var evento = new AgendaEvento(null);
        evento.setTitulo(dto.titulo());
        evento.setDescricao(dto.descricao());
        evento.setInicioFim(dto.inicio(), dto.fim());
        dto.categorias().forEach(c -> evento.addCategoria(c));

        var msGraph = dto.msGraphId();
        if (msGraph != null) {
            evento.setRegistroGraph(new RegistroEventoMSGraph(msGraph, dto.changeKey(), dto.createdDateTime(),
                    dto.lastModifiedDateTime()));
        }

        return evento;
    }
}
