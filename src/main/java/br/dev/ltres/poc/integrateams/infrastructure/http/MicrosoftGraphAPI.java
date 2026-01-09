package br.dev.ltres.poc.integrateams.infrastructure.http;

import org.springframework.stereotype.Component;

import br.dev.ltres.poc.integrateams.application.dto.MSGraphEvent;
import br.dev.ltres.poc.integrateams.application.gateway.MicrosoftGraphGateway;

@Component
public class MicrosoftGraphAPI implements MicrosoftGraphGateway {

    @Override
    public MSGraphEvent registraNovoEvento(MSGraphEvent evento) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registraNovoEvento'");
    }

    @Override
    public MSGraphEvent atualizaEvento(MSGraphEvent evento) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizaEvento'");
    }

}
