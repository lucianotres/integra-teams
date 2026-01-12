package br.dev.ltres.poc.integrateams.infrastructure.persistence.jpa.mapper;

import br.dev.ltres.poc.integrateams.domain.model.AgendaEvento;
import br.dev.ltres.poc.integrateams.domain.model.RegistroEventoMSGraph;
import br.dev.ltres.poc.integrateams.infrastructure.persistence.jpa.entity.AgendaEventoEntity;
import br.dev.ltres.poc.integrateams.infrastructure.persistence.jpa.entity.AgendaEventoMSGraphEntity;
import br.dev.ltres.poc.integrateams.infrastructure.persistence.jpa.entity.AgendaEventoCategoriaEntity;

public class AgendaEventoMapper {

    public static AgendaEventoEntity toEntity(AgendaEvento evento) {
        AgendaEventoEntity entity = new AgendaEventoEntity();
        entity.setId(evento.getId());
        entity.setTitulo(evento.getTitulo());
        entity.setDescricao(evento.getDescricao());
        entity.setInicio(evento.getInicio());
        entity.setFim(evento.getFim());

        for (String categoria : evento.getCategorias()) {
            AgendaEventoCategoriaEntity categoriaEntity = new AgendaEventoCategoriaEntity();
            categoriaEntity.setEvento(entity);
            categoriaEntity.setCategoria(categoria);
            entity.getCategorias().add(categoriaEntity);
        }
        return entity;
    }

    public static AgendaEvento toDomain(AgendaEventoEntity entity) {
        AgendaEvento domain = new AgendaEvento(entity.getId());
        domain.setTitulo(entity.getTitulo());
        domain.setDescricao(entity.getDescricao());
        domain.setInicioFim(entity.getInicio(), entity.getFim());
        entity.getCategorias().forEach(categoriaEntity -> domain.addCategoria(categoriaEntity.getCategoria()));

        var msGraph = entity.getMsGraph();
        if (msGraph != null) {
            domain.setRegistroGraph(new RegistroEventoMSGraph(msGraph.getMsGraphId(), msGraph.getChangeKey(),
                    msGraph.getCreatedDateTime(), msGraph.getLastModifiedDateTime()));
        }

        return domain;
    }

    public static void updateEntity(AgendaEvento evento, AgendaEventoEntity entity, boolean alteraIncluiMsGraph) {
        entity.setTitulo(evento.getTitulo());
        entity.setDescricao(evento.getDescricao());
        entity.setInicio(evento.getInicio());
        entity.setFim(evento.getFim());

        evento.getCategorias()
                .stream()
                .filter(f -> !entity.getCategorias().stream().anyMatch(c -> c.getCategoria().equalsIgnoreCase(f)))
                .toList()
                .forEach(c -> {
                    var novaCategoria = new AgendaEventoCategoriaEntity();
                    novaCategoria.setEvento(entity);
                    novaCategoria.setCategoria(c);
                    entity.getCategorias().add(novaCategoria);
                });

        entity.getCategorias()
                .removeIf(c -> !evento.getCategorias().stream().anyMatch(f -> f.equalsIgnoreCase(c.getCategoria())));

        if (alteraIncluiMsGraph) {
            var msGraph = evento.getRegistroGraph();
            if (msGraph == null) {
                entity.setMsGraph(null);
            } else {
                var msGraphEntity = entity.getMsGraph();
                if (msGraphEntity == null) {
                    msGraphEntity = new AgendaEventoMSGraphEntity();
                    msGraphEntity.setEvento(entity);
                    entity.setMsGraph(msGraphEntity);
                }

                msGraphEntity.setMsGraphId(msGraph.id());
                msGraphEntity.setChangeKey(msGraph.changeKey());
                msGraphEntity.setCreatedDateTime(msGraph.createdDateTime());
                msGraphEntity.setLastModifiedDateTime(msGraph.lastModifiedDateTime());
            }
        }
    }
}
