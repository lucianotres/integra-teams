package br.dev.ltres.poc.integrateams.application.usecases;

import br.dev.ltres.poc.integrateams.domain.repository.AgendaEventoRepository;
import jakarta.transaction.Transactional;

public class AgendaExcluiEvento {
    private final AgendaEventoRepository repository;

    public AgendaExcluiEvento(AgendaEventoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public boolean executa(Long id) {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("Id do evento não informado ou inválido.");

        return repository.excluir(id);
    }
}
