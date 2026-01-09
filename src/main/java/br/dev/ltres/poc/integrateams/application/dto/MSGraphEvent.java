package br.dev.ltres.poc.integrateams.application.dto;

import java.time.LocalDateTime;

public record MSGraphEvent(
        String id,
        String subject,
        String body,
        Boolean bodyHtml,
        LocalDateTime start,
        LocalDateTime end,
        Boolean isOnlineMeeting,
        Boolean isReminderOn,
        String changeKey,
        LocalDateTime createdDateTime,
        LocalDateTime lastModifiedDateTime) {
}
