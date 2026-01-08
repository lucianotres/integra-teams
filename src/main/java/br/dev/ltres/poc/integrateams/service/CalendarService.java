package br.dev.ltres.poc.integrateams.service;

import br.dev.ltres.poc.integrateams.dto.CreateEventRequest;
import com.microsoft.graph.models.*;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CalendarService {

    private final GraphServiceClient graphClient;
    private final String defaultUserId;

    public CalendarService(GraphServiceClient graphClient,
            @Value("${microsoft.graph.default-user-id}") String defaultUserId) {
        this.graphClient = graphClient;
        this.defaultUserId = defaultUserId;
    }

    public Event createEvent(CreateEventRequest request) {
        String userId = (request.getUserId() != null && !request.getUserId().isBlank())
                ? request.getUserId()
                : defaultUserId;

        Event event = new Event();
        event.setSubject(request.getSubject());

        ItemBody body = new ItemBody();
        body.setContentType(BodyType.Text);
        body.setContent(request.getBody());
        event.setBody(body);

        DateTimeTimeZone start = new DateTimeTimeZone();
        start.setDateTime(request.getStart().toLocalDateTime().toString());
        start.setTimeZone("America/Sao_Paulo");
        event.setStart(start);

        DateTimeTimeZone end = new DateTimeTimeZone();
        end.setDateTime(request.getEnd().toLocalDateTime().toString());
        end.setTimeZone("America/Sao_Paulo");
        event.setEnd(end);

        // Teams meeting
        if (Boolean.TRUE.equals(request.getTeamsMeeting())) {
            event.setIsOnlineMeeting(true);
            event.setOnlineMeetingProvider(OnlineMeetingProviderType.TeamsForBusiness);
        }

        return graphClient
                .users().byUserId(userId)
                .events()
                .post(event);
    }

    public EventCollectionResponse listEvents(String userId) {
        String effectiveUserId = (userId != null && !userId.isBlank())
                ? userId
                : defaultUserId;

        return graphClient
                .users().byUserId(effectiveUserId)
                .calendar()
                .events()
                .get();
    }
}