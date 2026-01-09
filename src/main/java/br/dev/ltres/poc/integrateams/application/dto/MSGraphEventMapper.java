package br.dev.ltres.poc.integrateams.application.dto;

import br.dev.ltres.poc.integrateams.domain.model.AgendaEvento;

public class MSGraphEventMapper {
    public static MSGraphEvent fromDomain(AgendaEvento evento) {
        var msGraph = evento.getRegistroGraph();

        return new MSGraphEvent(msGraph == null ? null : msGraph.id(),
                evento.getTitulo(),
                evento.getDescricao(),
                false,
                evento.getInicio(),
                evento.getFim(),
                false,
                false,
                msGraph == null ? null : msGraph.changeKey(),
                msGraph == null ? null : msGraph.createdDateTime(),
                msGraph == null ? null : msGraph.lastModifiedDateTime());
    }
}
