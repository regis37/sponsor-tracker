package de.thnuernberg.eit.regis.sponsor_tracker.service;

import de.thnuernberg.eit.regis.sponsor_tracker.model.Sponsorship;
import de.thnuernberg.eit.regis.sponsor_tracker.model.SponsorshipStatus;
import de.thnuernberg.eit.regis.sponsor_tracker.model.Company;
import de.thnuernberg.eit.regis.sponsor_tracker.model.Event;
import de.thnuernberg.eit.regis.sponsor_tracker.model.Interaction;
import de.thnuernberg.eit.regis.sponsor_tracker.repository.SponsorshipRepository;
import de.thnuernberg.eit.regis.sponsor_tracker.repository.CompanyRepository;
import de.thnuernberg.eit.regis.sponsor_tracker.repository.EventRepository;
import de.thnuernberg.eit.regis.sponsor_tracker.repository.InteractionRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SponsorshipService {

    private final SponsorshipRepository repository;
    private final CompanyRepository companyRepository;
    private final EventRepository eventRepository;

    public SponsorshipService(SponsorshipRepository repository,
            CompanyRepository companyRepository,
            EventRepository eventRepository) {
        this.repository = repository;
        this.companyRepository = companyRepository;
        this.eventRepository = eventRepository;
    }

    public List<Sponsorship> findAll() {
        return repository.findAll();
    }

    public List<Sponsorship> findByEvent(Long eventId) {
        return repository.findByEventId(eventId);
    }

    public double securedAmountForEvent(Long eventId) {
        return repository.findByEventId(eventId).stream()
                .filter(s -> s.getStatus() == SponsorshipStatus.SIGNED
                        || s.getStatus() == SponsorshipStatus.RECEIVED)
                .mapToDouble(Sponsorship::getAmount)
                .sum();
    }

    public Optional<Sponsorship> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Sponsorship> create(Sponsorship sponsorship, Long companyId, Long eventId, String createdBy) {
        return companyRepository.findById(companyId).flatMap(company -> eventRepository.findById(eventId).map(event -> {
            sponsorship.setCreatedBy(createdBy);
            sponsorship.setCreatedAt(LocalDate.now());
            sponsorship.setCompany(company);
            sponsorship.setEvent(event);
            return repository.save(sponsorship);
        }));
    }

    public Optional<Sponsorship> update(Long id, Sponsorship updated) {
        return repository.findById(id).map(existing -> {
            existing.setAmount(updated.getAmount());
            existing.setContributionType(updated.getContributionType());
            existing.setStatus(updated.getStatus());
            existing.setSignedDate(updated.getSignedDate());
            existing.setNotes(updated.getNotes());
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
