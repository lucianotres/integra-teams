package br.dev.ltres.poc.integrateams.infrastructure.persistence.jpa.mapper;

import br.dev.ltres.poc.integrateams.domain.model.AgendaEvento;
import br.dev.ltres.poc.integrateams.infrastructure.persistence.jpa.entity.AgendaEventoEntity;
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
        return domain;
    }
}
