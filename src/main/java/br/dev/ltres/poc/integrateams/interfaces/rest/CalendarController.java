package br.dev.ltres.poc.integrateams.interfaces.rest;

import br.dev.ltres.poc.integrateams.interfaces.rest.request.CreateEventRequest;

import com.microsoft.graph.models.Event;
import com.microsoft.graph.models.EventCollectionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @PostMapping("/events")
    public ResponseEntity<Event> createEvent(@RequestBody CreateEventRequest request) {
        Event event = calendarService.createEvent(request);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/events")
    public ResponseEntity<EventCollectionResponse> listEvents(
            @RequestParam(required = false) String userId) {
        EventCollectionResponse events = calendarService.listEvents(userId);
        return ResponseEntity.ok(events);
    }
}