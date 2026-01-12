package br.dev.ltres.poc.integrateams.infrastructure.http;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.DateTimeTimeZone;
import com.microsoft.graph.models.Event;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.OnlineMeetingProviderType;
import com.microsoft.graph.serviceclient.GraphServiceClient;

import br.dev.ltres.poc.integrateams.application.dto.MSGraphEvent;
import br.dev.ltres.poc.integrateams.application.gateway.MicrosoftGraphGateway;

@Component
public class MicrosoftGraphAPI implements MicrosoftGraphGateway {

    private final GraphServiceClient client;
    private final String defaultUserId;

    public MicrosoftGraphAPI(GraphServiceClient client,
            @Value("${microsoft.graph.default-user-id}") String defaultUserId) {
        this.client = client;
        this.defaultUserId = defaultUserId;
    }

    private DateTimeTimeZone convertFromLocalDateTime(LocalDateTime localDateTime) {
        var dt = new DateTimeTimeZone();
        dt.setDateTime(localDateTime.toString());
        dt.setTimeZone("America/Sao_Paulo");
        return dt;
    }

    private MSGraphEvent convertToMSGraphEvent(Event event) {
        return new MSGraphEvent(event.getId(),
                event.getSubject(),
                event.getBody().getContent(),
                Boolean.valueOf(event.getBody().getContentType() == BodyType.Html),
                LocalDateTime.parse(event.getStart().getDateTime()),
                LocalDateTime.parse(event.getEnd().getDateTime()),
                event.getIsOnlineMeeting(),
                event.getIsReminderOn(),
                event.getChangeKey(),
                event.getCreatedDateTime().toLocalDateTime(),
                event.getLastModifiedDateTime().toLocalDateTime(),
                event.getCategories());
    }

    private Event convertFromMSGraphEvent(MSGraphEvent evento) {
        var event = new Event();
        event.setSubject(evento.subject());
        event.setStart(convertFromLocalDateTime(evento.start()));
        event.setEnd(convertFromLocalDateTime(evento.end()));

        var body = new ItemBody();
        body.setContentType(Boolean.TRUE.equals(evento.bodyHtml()) ? BodyType.Html : BodyType.Text);
        body.setContent(evento.body());
        event.setBody(body);

        if (Boolean.TRUE.equals(evento.isOnlineMeeting())) {
            event.setIsOnlineMeeting(true);
            event.setOnlineMeetingProvider(OnlineMeetingProviderType.TeamsForBusiness);
        }

        if (Boolean.TRUE.equals(evento.isReminderOn())) {
            event.setIsReminderOn(true);
            event.setReminderMinutesBeforeStart(15);
        }

        if (evento.categories() != null)
            event.setCategories(evento.categories());

        return event;
    }

    @Override
    public MSGraphEvent registraNovoEvento(MSGraphEvent evento) {
        var event = convertFromMSGraphEvent(evento);

        var returnedEvent = client.users().byUserId(defaultUserId).events().post(event);

        return convertToMSGraphEvent(returnedEvent);
    }

    @Override
    public MSGraphEvent atualizaEvento(MSGraphEvent evento) {
        var event = convertFromMSGraphEvent(evento);

        var returnedEvent = client.users().byUserId(defaultUserId).events().byEventId(evento.id()).patch(event);

        return convertToMSGraphEvent(returnedEvent);
    }

    @Override
    public boolean removeEvento(String id) {
        try {
            client.users().byUserId(defaultUserId).events().byEventId(id).delete();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}
