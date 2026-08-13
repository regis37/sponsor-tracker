package de.thnuernberg.eit.regis.sponsor_tracker.controller;

import de.thnuernberg.eit.regis.sponsor_tracker.model.Event;
import de.thnuernberg.eit.regis.sponsor_tracker.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping
    public List<Event> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getOne(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Event> create(@RequestBody Event event) {
        Event created = service.create(event);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> update(@PathVariable Long id, @RequestBody Event event) {
        return service.update(id, event)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
