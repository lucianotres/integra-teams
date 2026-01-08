package br.dev.ltres.poc.integrateams.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.dev.ltres.poc.integrateams.application.usecases.AgendaListaEventos;
import br.dev.ltres.poc.integrateams.domain.repository.AgendaEventoRepository;

@Configuration
public class UseCaseConfig {

    @Bean
    public AgendaListaEventos agendaListaEventos(AgendaEventoRepository repository) {
        return new AgendaListaEventos(repository);
    }
}
