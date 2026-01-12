package br.dev.ltres.poc.integrateams.interfaces.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.dev.ltres.poc.integrateams.application.dto.AgendaEventoDTO;
import br.dev.ltres.poc.integrateams.application.usecases.AgendaAddEvento;
import br.dev.ltres.poc.integrateams.application.usecases.AgendaAtualizaEvento;
import br.dev.ltres.poc.integrateams.application.usecases.AgendaExcluiEvento;
import br.dev.ltres.poc.integrateams.application.usecases.AgendaListaEventos;
import br.dev.ltres.poc.integrateams.interfaces.rest.request.AgendaEventoAddDTO;
import br.dev.ltres.poc.integrateams.interfaces.rest.request.AgendaEventoAddDTOMapper;
import br.dev.ltres.poc.integrateams.interfaces.rest.request.AgendaEventoUpdDTO;
import br.dev.ltres.poc.integrateams.interfaces.rest.request.AgendaEventoUpdDTOMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/agenda/evento")
public class AgendaEventoController {
    private final AgendaListaEventos agendaListaEventos;
    private final AgendaAddEvento agendaAddEvento;
    private final AgendaAtualizaEvento agendaAtualizaEvento;
    private final AgendaExcluiEvento agendaExcluiEvento;

    public AgendaEventoController(AgendaListaEventos agendaListaEventos, AgendaAddEvento agendaAddEvento,
            AgendaAtualizaEvento agendaAtualizaEvento, AgendaExcluiEvento agendaExcluiEvento) {
        this.agendaListaEventos = agendaListaEventos;
        this.agendaAddEvento = agendaAddEvento;
        this.agendaAtualizaEvento = agendaAtualizaEvento;
        this.agendaExcluiEvento = agendaExcluiEvento;
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

    @PutMapping
    @Operation(summary = "Atualiza Evento", description = "Atualiza um evento existente na agenda.")
    public ResponseEntity<AgendaEventoDTO> atualizaEvento(@RequestBody @Valid AgendaEventoUpdDTO evento) {
        var atualizar = AgendaEventoUpdDTOMapper.toApp(evento);
        var atualizado = agendaAtualizaEvento.executa(atualizar);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui Evento", description = "Exclui um evento da agenda.")
    public ResponseEntity<Void> excluiEvento(@PathVariable Long id) {
        if (agendaExcluiEvento.executa(id))
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.notFound().build();
    }
}
