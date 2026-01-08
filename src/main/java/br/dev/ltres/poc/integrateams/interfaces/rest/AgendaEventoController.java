package br.dev.ltres.poc.integrateams.interfaces.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.ltres.poc.integrateams.application.dto.AgendaEventoDTO;
import br.dev.ltres.poc.integrateams.application.usecases.AgendaListaEventos;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/agenda/evento")
public class AgendaEventoController {
    private final AgendaListaEventos agendaListaEventos;

    public AgendaEventoController(AgendaListaEventos agendaListaEventos) {
        this.agendaListaEventos = agendaListaEventos;
    }

    @GetMapping
    @Operation(summary = "Lista Eventos", description = "Lista todos os eventos cadastrados para a agenda.")
    public ResponseEntity<List<AgendaEventoDTO>> listaEventos() {
        return ResponseEntity.ok(agendaListaEventos.executaTodos());
    }
}
