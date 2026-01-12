package br.dev.ltres.poc.integrateams.application.dto;

import br.dev.ltres.poc.integrateams.domain.model.AgendaEvento;

public class MSGraphEventMapper {
    private static String htmlBodyPadrao(String original) {
        return "<html><body>" + original
                + "<br/><br/><i>Gerado automaticamente pelo sistema, alterações realizadas aqui não terão efeito no sistema.</i></body></html>";
    }

    public static MSGraphEvent fromDomain(AgendaEvento evento) {
        var msGraph = evento.getRegistroGraph();

        return new MSGraphEvent(msGraph == null ? null : msGraph.id(),
                evento.getTitulo(),
                htmlBodyPadrao(evento.getDescricao()),
                true,
                evento.getInicio(),
                evento.getFim(),
                false,
                false,
                msGraph == null ? null : msGraph.changeKey(),
                msGraph == null ? null : msGraph.createdDateTime(),
                msGraph == null ? null : msGraph.lastModifiedDateTime(),
                evento.getCategorias() == null ? null : evento.getCategorias());
    }
}
