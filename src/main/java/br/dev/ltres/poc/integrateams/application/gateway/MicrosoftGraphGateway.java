package br.dev.ltres.poc.integrateams.application.gateway;

import br.dev.ltres.poc.integrateams.application.dto.MSGraphEvent;

public interface MicrosoftGraphGateway {
    MSGraphEvent registraNovoEvento(MSGraphEvent evento);

    MSGraphEvent atualizaEvento(MSGraphEvent evento);

    boolean removeEvento(String id);
}
