package br.dev.ltres.poc.integrateams.infrastructure.persistence.jpa;

import java.time.LocalDateTime;
import java.util.List;
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
    public AgendaEvento salvar(AgendaEvento evento, boolean alteraIncluiMsGraph) {
        AgendaEventoEntity entity;

        if (evento.getId() == null) {
            entity = AgendaEventoMapper.toEntity(evento);
        } else {
            entity = jpaRepository.findByAtivoTrueAndId(evento.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));

            AgendaEventoMapper.updateEntity(evento, entity, alteraIncluiMsGraph);

            // alterações no evento da agenda, limpa changeKey para refletir no MS Graph
            if (!alteraIncluiMsGraph && entity.getMsGraph() != null)
                entity.getMsGraph().setChangeKey(null);
        }

        var savedEntity = jpaRepository.save(entity);
        return AgendaEventoMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<AgendaEvento> buscaEvento(Long id) {
        return jpaRepository.findByAtivoTrueAndId(id)
                .map(AgendaEventoMapper::toDomain);
    }

    private List<AgendaEvento> resultadoFindParaDominio(Optional<List<AgendaEventoEntity>> entities) {
        return entities
                .orElse(List.of())
                .stream()
                .map(AgendaEventoMapper::toDomain)
                .toList();
    }

    @Override
    public List<AgendaEvento> buscarEventosPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return resultadoFindParaDominio(
                jpaRepository.findByAtivoTrueAndInicioGreaterThanEqualAndFimLessThanEqual(inicio, fim));
    }

    @Override
    public List<AgendaEvento> buscarEventosPorCategoria(String categoria) {
        return resultadoFindParaDominio(jpaRepository.findByAtivoTrueAndCategoriasCategoriaIgnoreCase(categoria));
    }

    @Override
    public List<AgendaEvento> buscarEventosTodos() {
        return jpaRepository.findByAtivoTrue()
                .stream()
                .map(AgendaEventoMapper::toDomain)
                .toList();
    }

    @Override
    public boolean excluir(Long id) {
        jpaRepository.limparChangeKey(id);
        return (jpaRepository.desativar(id) > 0);
    }

    @Override
    public List<AgendaEvento> buscaEventosAEnviar() {
        var entities = jpaRepository.findPendentesEnvio();
        return entities
                .orElse(List.of())
                .stream()
                .map(AgendaEventoMapper::toDomain)
                .toList();
    }

    @Override
    public boolean salvaRemocaoEventoMsGraph(Long id) {
        var entity = jpaRepository.findById(id);
        if (entity.isEmpty())
            return false;

        var msGraph = entity.get().getMsGraph();
        if (msGraph == null) {
            return false;
        }

        msGraph.setChangeKey("removed");

        jpaRepository.save(entity.get());
        return true;
    }

}
