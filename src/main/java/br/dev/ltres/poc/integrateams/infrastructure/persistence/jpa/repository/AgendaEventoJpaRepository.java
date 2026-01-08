package br.dev.ltres.poc.integrateams.infrastructure.persistence.jpa.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.ltres.poc.integrateams.infrastructure.persistence.jpa.entity.AgendaEventoEntity;

public interface AgendaEventoJpaRepository extends JpaRepository<AgendaEventoEntity, Long> {

    Optional<AgendaEventoEntity[]> findByInicioGreaterThanEqualAndFimLessThanEqual(LocalDateTime inicio,
            LocalDateTime fim);

    Optional<AgendaEventoEntity[]> findByCategoriasCategoriaIgnoreCase(String categoria);

}
