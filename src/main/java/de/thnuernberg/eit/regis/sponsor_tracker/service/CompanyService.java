package de.thnuernberg.eit.regis.sponsor_tracker.service;

import de.thnuernberg.eit.regis.sponsor_tracker.model.Company;
import de.thnuernberg.eit.regis.sponsor_tracker.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository repository;

    public CompanyService(CompanyRepository repository) {
        this.repository = repository;
    }

    public List<Company> findAll() {
        return repository.findAll();
    }

    public Optional<Company> findById(Long id) {
        return repository.findById(id);
    }

    public Company create(Company company, String createdBy) {
        company.setCreatedBy(createdBy);
        company.setCreatedAt(LocalDate.now());
        return repository.save(company);
    }

    public Optional<Company> update(Long id, Company updated) {
        return repository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setSector(updated.getSector());
            existing.setWebsite(updated.getWebsite());
            existing.setCity(updated.getCity());
            existing.setContactName(updated.getContactName());
            existing.setContactEmail(updated.getContactEmail());
            existing.setContactPhone(updated.getContactPhone());
            existing.setNotes(updated.getNotes());
            // createdBy stays unchanged for tracability, neither createdAt
            return repository.save(existing);
        });
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}