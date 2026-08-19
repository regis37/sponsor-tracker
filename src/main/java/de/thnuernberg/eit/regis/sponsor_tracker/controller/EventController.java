package de.thnuernberg.eit.regis.sponsor_tracker.controller;

import de.thnuernberg.eit.regis.sponsor_tracker.dto.BudgetSummary;
import de.thnuernberg.eit.regis.sponsor_tracker.model.Event;
import de.thnuernberg.eit.regis.sponsor_tracker.model.Interaction;
import de.thnuernberg.eit.regis.sponsor_tracker.model.Sponsorship;
import de.thnuernberg.eit.regis.sponsor_tracker.service.EventService;
import de.thnuernberg.eit.regis.sponsor_tracker.service.SponsorshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService service;
    private final SponsorshipService sponsorshipService;

    public EventController(EventService service, SponsorshipService sponsorshipService) {
        this.service = service;
        this.sponsorshipService = sponsorshipService;
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

    @GetMapping("/{id}/sponsorships")
    public ResponseEntity<List<Sponsorship>> history(@PathVariable Long id) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sponsorshipService.findAll());
    }

    @GetMapping("/{id}/budget")
    public ResponseEntity<BudgetSummary> budget(@PathVariable Long id) {
        return service.findById(id)
                .map(event -> {
                    double target = event.getTargetBudget();
                    double secured = sponsorshipService.securedAmountForEvent(id);
                    double remaining = target - secured;
                    return ResponseEntity.ok(new BudgetSummary(target, secured, remaining));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Event> create(@Valid @RequestBody Event event) {
        Event created = service.create(event);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> update(@PathVariable Long id, @Valid @RequestBody Event event) {
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
