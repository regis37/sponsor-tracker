package de.thnuernberg.eit.regis.sponsor_tracker.controller;

import de.thnuernberg.eit.regis.sponsor_tracker.model.Interaction;
import de.thnuernberg.eit.regis.sponsor_tracker.dto.SponsorshipResponse;
import de.thnuernberg.eit.regis.sponsor_tracker.model.Sponsorship;
import de.thnuernberg.eit.regis.sponsor_tracker.service.SponsorshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/sponsorships")
public class SponsorshipController {

    private final SponsorshipService service;

    public SponsorshipController(SponsorshipService service) {
        this.service = service;
    }

    @GetMapping
    public List<SponsorshipResponse> list() {
        return service.findAll().stream()
                .map(SponsorshipResponse::new)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SponsorshipResponse> getOne(@PathVariable Long id) {
        return service.findById(id)
                .map(SponsorshipResponse::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SponsorshipResponse> create(
            @Valid @RequestBody Sponsorship sponsorship,
            @RequestParam Long companyId,
            @RequestParam Long eventId,
            @RequestHeader(value = "X-Created-By", defaultValue = "anonymous") String createdBy) {
        return service.create(sponsorship, companyId, eventId, createdBy)
                .map(created -> ResponseEntity.status(201).body(new SponsorshipResponse(created)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SponsorshipResponse> update(@PathVariable Long id,
            @Valid @RequestBody Sponsorship sponsorship) {
        return service.update(id, sponsorship)
                .map(SponsorshipResponse::new)
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
