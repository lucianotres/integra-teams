package br.dev.ltres.poc.integrateams.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class CreateEventRequest {

    private String subject;
    private String body;
    private String userId; // opcional, se não vier, usamos default
    private OffsetDateTime start;
    private OffsetDateTime end;
    private Boolean teamsMeeting; // se true, queremos link de Teams
}