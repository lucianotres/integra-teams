package br.dev.ltres.poc.integrateams.application.usecases;

import br.dev.ltres.poc.integrateams.application.dto.MSGraphEventMapper;
import br.dev.ltres.poc.integrateams.application.gateway.MicrosoftGraphGateway;
import br.dev.ltres.poc.integrateams.domain.model.AgendaEvento;
import br.dev.ltres.poc.integrateams.domain.model.RegistroEventoMSGraph;
import br.dev.ltres.poc.integrateams.domain.repository.AgendaEventoRepository;
import jakarta.transaction.Transactional;

public class AgendaEventoParaMSGraph {
    private final AgendaEventoRepository repository;
    private final MicrosoftGraphGateway gateway;

    public AgendaEventoParaMSGraph(AgendaEventoRepository repository, MicrosoftGraphGateway gateway) {
        this.repository = repository;
        this.gateway = gateway;
    }

    @Transactional
    public int executa() {
        var aEnviar = repository.buscaEventosAEnviar();

        if (aEnviar == null || aEnviar.isEmpty())
            return 0;

        int total = 0;
        for (var evento : aEnviar) {
            if (evento.isAtivo())
                registraViaGateway(evento, evento.getRegistroGraph() == null);
            else
                removeViaGateway(evento);

            total++;
        }
        return total;
    }

    private void registraViaGateway(AgendaEvento evento, boolean novo) {
        var dtoEvento = MSGraphEventMapper.fromDomain(evento);

        var dtoRegistrado = novo
                ? gateway.registraNovoEvento(dtoEvento)
                : gateway.atualizaEvento(dtoEvento);

        if (dtoRegistrado == null || dtoRegistrado.id() == null || dtoRegistrado.changeKey() == null)
            throw new IllegalStateException("Retorno inválido do gateway Microsoft Graph");

        evento.setRegistroGraph(new RegistroEventoMSGraph(dtoRegistrado.id(), dtoRegistrado.changeKey(),
                dtoRegistrado.createdDateTime(), dtoRegistrado.lastModifiedDateTime()));

        repository.salvar(evento, true);
    }

    private void removeViaGateway(AgendaEvento evento) {
        if (evento.getRegistroGraph() == null || evento.getRegistroGraph().id() == null)
            throw new IllegalStateException("Solicitando remoção de evento sem Id do MS Graph");

        if (gateway.removeEvento(evento.getRegistroGraph().id()))
            repository.salvaRemocaoEventoMsGraph(evento.getId());
        else
            throw new IllegalStateException(); // TODO: tratar erro quando a API não conseguir remover o evento
    }
}
