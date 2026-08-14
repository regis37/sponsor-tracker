package de.thnuernberg.eit.regis.sponsor_tracker.service;

import de.thnuernberg.eit.regis.sponsor_tracker.model.Company;
import de.thnuernberg.eit.regis.sponsor_tracker.model.Interaction;
import de.thnuernberg.eit.regis.sponsor_tracker.repository.CompanyRepository;
import de.thnuernberg.eit.regis.sponsor_tracker.repository.InteractionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InteractionService {

    private final InteractionRepository repository;
    private final CompanyRepository companyRepository;

    public InteractionService(InteractionRepository repository,
            CompanyRepository companyRepository) {
        this.repository = repository;
        this.companyRepository = companyRepository;
    }

    public List<Interaction> findAll() {
        return repository.findAll();
    }

    public Optional<Interaction> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Interaction> create(Interaction interaction, Long companyId, String createdBy) {
        return companyRepository.findById(companyId).map(company -> {
            interaction.setCompany(company);
            interaction.setCreatedBy(createdBy);
            return repository.save(interaction);
        });
    }

    public Optional<Interaction> update(Long id, Interaction updated) {
        return repository.findById(id).map(existing -> {
            existing.setDate(updated.getDate());
            existing.setType(updated.getType());
            existing.setSummary(updated.getSummary());
            existing.setOutcome(updated.getOutcome());
            existing.setNextActionDate(updated.getNextActionDate());
            existing.setNextActionNote(updated.getNextActionNote());
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

    public List<Interaction> findByCompany(Long companyId) {
        return repository.findByCompanyIdOrderByDateDesc(companyId);
    }

}