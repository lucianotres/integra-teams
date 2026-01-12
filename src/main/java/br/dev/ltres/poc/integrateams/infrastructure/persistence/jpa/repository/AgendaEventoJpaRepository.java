package br.dev.ltres.poc.integrateams.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.dev.ltres.poc.integrateams.infrastructure.persistence.jpa.entity.AgendaEventoEntity;

public interface AgendaEventoJpaRepository extends JpaRepository<AgendaEventoEntity, Long> {

    List<AgendaEventoEntity> findByAtivoTrue();

    Optional<AgendaEventoEntity> findByAtivoTrueAndId(Long id);

    Optional<List<AgendaEventoEntity>> findByAtivoTrueAndInicioGreaterThanEqualAndFimLessThanEqual(LocalDateTime inicio,
            LocalDateTime fim);

    Optional<List<AgendaEventoEntity>> findByAtivoTrueAndCategoriasCategoriaIgnoreCase(String categoria);

    @Query("""
                select a
                from AgendaEventoEntity a
                left join a.msGraph m
                where
                    (a.ativo = true and m is null)
                    or (m is not null and m.changeKey is null)
            """)
    Optional<List<AgendaEventoEntity>> findPendentesEnvio();

    @Modifying
    @Query("update AgendaEventoEntity c set c.ativo = false where c.id = :id and c.ativo = true")
    int desativar(@Param("id") Long id);

    @Modifying
    @Query("update AgendaEventoMSGraphEntity c set c.changeKey = null where c.id = :id")
    int limparChangeKey(@Param("id") Long id);
}
