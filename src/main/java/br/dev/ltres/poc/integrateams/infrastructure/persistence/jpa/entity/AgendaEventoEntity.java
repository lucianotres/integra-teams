package br.dev.ltres.poc.integrateams.infrastructure.persistence.jpa.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "agenda_evento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendaEventoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 4000)
    private String descricao;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AgendaEventoCategoriaEntity> categorias = new ArrayList<>();

    private LocalDateTime inicio;

    private LocalDateTime fim;

    @OneToOne(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private AgendaEventoMSGraphEntity msGraph;

}
