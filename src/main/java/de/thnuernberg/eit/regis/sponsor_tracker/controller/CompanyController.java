package de.thnuernberg.eit.regis.sponsor_tracker.controller;

import de.thnuernberg.eit.regis.sponsor_tracker.model.Company;
import de.thnuernberg.eit.regis.sponsor_tracker.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @GetMapping
    public List<Company> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getOne(@PathVariable Long id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Company> create(
            @RequestBody Company company,
            @RequestHeader(value = "X-Created-By", defaultValue = "anonymous") String createdBy) {
        Company created = service.create(company, createdBy);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> update(@PathVariable Long id, @RequestBody Company company) {
        return service.update(id, company)
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