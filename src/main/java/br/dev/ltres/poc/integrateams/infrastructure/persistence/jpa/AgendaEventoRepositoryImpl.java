package br.dev.ltres.poc.integrateams.infrastructure.persistence.jpa;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.dev.ltres.poc.integrateams.domain.model.AgendaEvento;
import br.dev.ltres.poc.integrateams.domain.repository.AgendaEventoRepository;
import br.dev.ltres.poc.integrateams.infrastructure.persistence.jpa.entity.AgendaEventoEntity;
import br.dev.ltres.poc.integrateams.infrastructure.persistence.jpa.mapper.AgendaEventoMapper;
import br.dev.ltres.poc.integrateams.infrastructure.persistence.jpa.repository.AgendaEventoJpaRepository;

@Repository
public class AgendaEventoRepositoryImpl implements AgendaEventoRepository {

    private final AgendaEventoJpaRepository jpaRepository;

    public AgendaEventoRepositoryImpl(AgendaEventoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public AgendaEvento salvar(AgendaEvento evento) {
        var entity = AgendaEventoMapper.toEntity(evento);
        var savedEntity = jpaRepository.save(entity);
        return AgendaEventoMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<AgendaEvento> buscaEvento(Long id) {
        return jpaRepository.findById(id)
                .map(AgendaEventoMapper::toDomain);
    }

    private AgendaEvento[] resultadoFindParaDominio(Optional<AgendaEventoEntity[]> entities) {
        return Arrays.stream(entities.orElse(new AgendaEventoEntity[0]))
                .map(AgendaEventoMapper::toDomain)
                .toArray(AgendaEvento[]::new);
    }

    @Override
    public AgendaEvento[] buscarEventosPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return resultadoFindParaDominio(jpaRepository.findByInicioGreaterThanEqualAndFimLessThanEqual(inicio, fim));
    }

    @Override
    public AgendaEvento[] buscarEventosPorCategoria(String categoria) {
        return resultadoFindParaDominio(jpaRepository.findByCategoriasCategoriaIgnoreCase(categoria));
    }

    @Override
    public AgendaEvento[] buscarEventosTodos() {
        return jpaRepository.findAll()
                .stream()
                .map(AgendaEventoMapper::toDomain)
                .toArray(AgendaEvento[]::new);
    }

    @Override
    public void excluir(Long id) {
        jpaRepository.deleteById(id);
    }

}
