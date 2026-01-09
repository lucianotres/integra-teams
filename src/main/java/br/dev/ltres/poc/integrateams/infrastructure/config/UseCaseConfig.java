package br.dev.ltres.poc.integrateams.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.dev.ltres.poc.integrateams.application.gateway.MicrosoftGraphGateway;
import br.dev.ltres.poc.integrateams.application.usecases.AgendaAddEvento;
import br.dev.ltres.poc.integrateams.application.usecases.AgendaEventoParaMSGraph;
import br.dev.ltres.poc.integrateams.application.usecases.AgendaListaEventos;
import br.dev.ltres.poc.integrateams.domain.repository.AgendaEventoRepository;

@Configuration
public class UseCaseConfig {

    @Bean
    public AgendaListaEventos agendaListaEventos(AgendaEventoRepository repository) {
        return new AgendaListaEventos(repository);
    }

    @Bean
    public AgendaAddEvento agendaAddEvento(AgendaEventoRepository repository) {
        return new AgendaAddEvento(repository);
    }

    @Bean
    public AgendaEventoParaMSGraph agendaEventoParaMSGraph(AgendaEventoRepository repository,
            MicrosoftGraphGateway gateway) {
        return new AgendaEventoParaMSGraph(repository, gateway);
    }
}
