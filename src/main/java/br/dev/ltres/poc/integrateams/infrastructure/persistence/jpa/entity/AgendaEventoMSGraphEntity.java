package br.dev.ltres.poc.integrateams.infrastructure.persistence.jpa.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "agenda_evento_ms_graph")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendaEventoMSGraphEntity {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "id")
    private AgendaEventoEntity evento;

    @Column(name = "ms_graph_id", nullable = false, unique = true)
    private String msGraphId;

    @Column(name = "change_key")
    private String changeKey;

    @Column(name = "created_date_time")
    private LocalDateTime createdDateTime;

    @Column(name = "last_modified_date_time")
    private LocalDateTime lastModifiedDateTime;
}
