package br.dev.ltres.poc.integrateams.interfaces.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.dev.ltres.poc.integrateams.application.dto.AgendaEventoDTO;
import br.dev.ltres.poc.integrateams.application.usecases.AgendaAddEvento;
import br.dev.ltres.poc.integrateams.application.usecases.AgendaListaEventos;
import br.dev.ltres.poc.integrateams.interfaces.rest.request.AgendaEventoAddDTO;
import br.dev.ltres.poc.integrateams.interfaces.rest.request.AgendaEventoAddDTOMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/agenda/evento")
public class AgendaEventoController {
    private final AgendaListaEventos agendaListaEventos;
    private final AgendaAddEvento agendaAddEvento;

    public AgendaEventoController(AgendaListaEventos agendaListaEventos, AgendaAddEvento agendaAddEvento) {
        this.agendaListaEventos = agendaListaEventos;
        this.agendaAddEvento = agendaAddEvento;
    }

    @GetMapping
    @Operation(summary = "Lista Eventos", description = "Lista todos os eventos cadastrados para a agenda.")
    public ResponseEntity<List<AgendaEventoDTO>> listaEventos() {
        return ResponseEntity.ok(agendaListaEventos.executaTodos());
    }

    @PostMapping
    @Operation(summary = "Adiciona Evento", description = "Adiciona um novo evento na agenda.")
    public ResponseEntity<AgendaEventoDTO> adicionaEvento(@RequestBody @Valid AgendaEventoAddDTO evento,
            UriComponentsBuilder uriBuilder) {
        var adicionar = AgendaEventoAddDTOMapper.toApp(evento);
        var novo = agendaAddEvento.executa(adicionar);
        var uri = uriBuilder.path("/api/agenda/evento/{id}").buildAndExpand(novo.id()).toUri();
        return ResponseEntity.created(uri).body(novo);
    }
}
