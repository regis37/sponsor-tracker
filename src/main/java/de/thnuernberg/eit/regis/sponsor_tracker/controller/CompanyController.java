package de.thnuernberg.eit.regis.sponsor_tracker.controller;

import de.thnuernberg.eit.regis.sponsor_tracker.dto.CompanyResponse;
import de.thnuernberg.eit.regis.sponsor_tracker.model.Company;
import de.thnuernberg.eit.regis.sponsor_tracker.model.Interaction;
import de.thnuernberg.eit.regis.sponsor_tracker.service.CompanyService;
import de.thnuernberg.eit.regis.sponsor_tracker.service.InteractionService;
import de.thnuernberg.eit.regis.sponsor_tracker.dto.InteractionResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService service;
    private final InteractionService interactionService;

    public CompanyController(CompanyService service, InteractionService interactionService) {
        this.service = service;
        this.interactionService = interactionService;
    }

    @GetMapping
    public List<CompanyResponse> list() {
        return service.findAll().stream()
                .map(CompanyResponse::new)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getOne(@PathVariable Long id) {
        return service.findById(id)
                .map(CompanyResponse::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<InteractionResponse>> history(@PathVariable Long id) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        List<InteractionResponse> history = interactionService.findByCompany(id).stream()
                .map(InteractionResponse::new)
                .toList();
        return ResponseEntity.ok(history);
    }

    @GetMapping("/to-follow-up")
    public List<InteractionResponse> toFollowUp() {
        return interactionService.findToFollowUp().stream()
                .map(InteractionResponse::new)
                .toList();
    }

    @PostMapping
    public ResponseEntity<CompanyResponse> create(
            @Valid @RequestBody Company company,
            @RequestHeader(value = "X-Created-By", defaultValue = "anonymous") String createdBy) {
        Company created = service.create(company, createdBy);
        return ResponseEntity.status(201).body(new CompanyResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> update(@PathVariable Long id, @Valid @RequestBody Company company) {
        return service.update(id, company)
                .map(CompanyResponse::new)
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