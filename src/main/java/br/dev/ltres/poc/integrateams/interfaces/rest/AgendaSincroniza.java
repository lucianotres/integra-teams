package br.dev.ltres.poc.integrateams.interfaces.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.ltres.poc.integrateams.application.usecases.AgendaEventoParaMSGraph;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/agenda/sincroniza")
public class AgendaSincroniza {
    private final AgendaEventoParaMSGraph agendaEventoParaMSGraph;

    public AgendaSincroniza(AgendaEventoParaMSGraph agendaEventoParaMSGraph) {
        this.agendaEventoParaMSGraph = agendaEventoParaMSGraph;
    }

    @PatchMapping
    @Operation(summary = "Sincroniza Eventos", description = "Atualiza os eventos de forma forçada no Teams")
    public ResponseEntity<Void> forcarSincronizacao() {
        agendaEventoParaMSGraph.executa();
        return ResponseEntity.ok().build();
    }
}
