package de.thnuernberg.eit.regis.sponsor_tracker.controller;

import de.thnuernberg.eit.regis.sponsor_tracker.model.Interaction;
import de.thnuernberg.eit.regis.sponsor_tracker.dto.InteractionResponse;
import de.thnuernberg.eit.regis.sponsor_tracker.service.InteractionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/interactions")
public class InteractionController {

    private final InteractionService service;

    public InteractionController(InteractionService service) {
        this.service = service;
    }

    @GetMapping
    public List<InteractionResponse> list() {
        return service.findAll().stream()
                .map(InteractionResponse::new)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InteractionResponse> getOne(@PathVariable Long id) {
        return service.findById(id)
                .map(InteractionResponse::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InteractionResponse> create(
            @Valid @RequestBody Interaction interaction,
            @RequestParam Long companyId,
            @RequestHeader(value = "X-Created-By", defaultValue = "anonymous") String createdBy) {
        return service.create(interaction, companyId, createdBy)
                .map(created -> ResponseEntity.status(201).body(new InteractionResponse(created)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<InteractionResponse> update(@PathVariable Long id,
            @Valid @RequestBody Interaction interaction) {
        return service.update(id, interaction)
                .map(InteractionResponse::new)
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
