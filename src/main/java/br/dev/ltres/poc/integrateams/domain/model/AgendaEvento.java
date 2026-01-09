package br.dev.ltres.poc.integrateams.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AgendaEvento {
    private Long id;
    private String titulo;
    private String descricao;
    private List<String> categorias;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private RegistroEventoMSGraph registroGraph;

    public AgendaEvento(Long id) {
        this.id = id;
        this.categorias = new ArrayList<String>();
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank())
            throw new IllegalArgumentException("Título é obrigatório!");

        this.titulo = titulo;
    }

    public void setDescricao(String description) {
        this.descricao = description;
    }

    public void addCategoria(String categoria) {
        boolean exists = categorias.stream()
                .anyMatch(c -> c.equalsIgnoreCase(categoria));

        if (exists) {
            throw new IllegalArgumentException("Categoria já existe para este evento");
        }

        categorias.add(categoria);
    }

    public void removeCategoria(String categoria) {
        categorias.stream()
                .filter(c -> c.equalsIgnoreCase(categoria))
                .findFirst()
                .ifPresent(categorias::remove);
    }

    public void limparCategorias() {
        categorias.clear();
    }

    public void setInicioFim(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio == null || fim == null)
            throw new IllegalArgumentException("Data de início e fim são obrigatórios!");

        if (inicio.isAfter(fim))
            throw new IllegalArgumentException("Data de início deve ser anterior à data de fim!");

        this.inicio = inicio;
        this.fim = fim;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<String> getCategorias() {
        return categorias.stream().toList();
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public void setRegistroGraph(RegistroEventoMSGraph registroGraph) {
        this.registroGraph = registroGraph;
    }

    public RegistroEventoMSGraph getRegistroGraph() {
        return registroGraph;
    }
}
