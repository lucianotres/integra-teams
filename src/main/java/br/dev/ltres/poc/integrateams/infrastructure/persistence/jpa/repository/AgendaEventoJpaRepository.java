package br.dev.ltres.poc.integrateams.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.ltres.poc.integrateams.infrastructure.persistence.jpa.entity.AgendaEventoEntity;

public interface AgendaEventoJpaRepository extends JpaRepository<AgendaEventoEntity, Long> {

    Optional<List<AgendaEventoEntity>> findByInicioGreaterThanEqualAndFimLessThanEqual(LocalDateTime inicio,
            LocalDateTime fim);

    Optional<List<AgendaEventoEntity>> findByCategoriasCategoriaIgnoreCase(String categoria);

    Optional<List<AgendaEventoEntity>> findByMsGraphIsNullOrMsGraphChangeKeyIsNull();

}
