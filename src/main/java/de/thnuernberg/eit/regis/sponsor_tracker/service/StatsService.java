package de.thnuernberg.eit.regis.sponsor_tracker.service;

import de.thnuernberg.eit.regis.sponsor_tracker.dto.StatsSummary;
import de.thnuernberg.eit.regis.sponsor_tracker.model.Interaction;
import de.thnuernberg.eit.regis.sponsor_tracker.model.Outcome;
import de.thnuernberg.eit.regis.sponsor_tracker.model.Sponsorship;
import de.thnuernberg.eit.regis.sponsor_tracker.model.SponsorshipStatus;
import de.thnuernberg.eit.regis.sponsor_tracker.repository.CompanyRepository;
import de.thnuernberg.eit.regis.sponsor_tracker.repository.EventRepository;
import de.thnuernberg.eit.regis.sponsor_tracker.repository.InteractionRepository;
import de.thnuernberg.eit.regis.sponsor_tracker.repository.SponsorshipRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatsService {

    private final CompanyRepository companyRepository;
    private final EventRepository eventRepository;
    private final InteractionRepository interactionRepository;
    private final SponsorshipRepository sponsorshipRepository;

    public StatsService(CompanyRepository companyRepository,
                        EventRepository eventRepository,
                        InteractionRepository interactionRepository,
                        SponsorshipRepository sponsorshipRepository) {
        this.companyRepository = companyRepository;
        this.eventRepository = eventRepository;
        this.interactionRepository = interactionRepository;
        this.sponsorshipRepository = sponsorshipRepository;
    }

    public StatsSummary compute() {
        long totalCompanies = companyRepository.count();
        long totalEvents = eventRepository.count();
        long totalInteractions = interactionRepository.count();
        long totalSponsorships = sponsorshipRepository.count();

        // Répartition des interactions par outcome (EMAIL -> 3, INTERESTED -> 1, ...)
        Map<String, Long> byOutcome = interactionRepository.findAll().stream()
            .filter(i -> i.getOutcome() != null)
            .collect(Collectors.groupingBy(
                i -> i.getOutcome().name(),
                Collectors.counting()));

        // Montant total réellement sécurisé (SIGNED + RECEIVED uniquement)
        double totalSecured = sponsorshipRepository.findAll().stream()
            .filter(s -> s.getStatus() == SponsorshipStatus.SIGNED
                      || s.getStatus() == SponsorshipStatus.RECEIVED)
            .mapToDouble(Sponsorship::getAmount)
            .sum();

        // Nombre d'interactions à relancer aujourd'hui
        long toFollowUp = interactionRepository
            .findByNextActionDateLessThanEqualOrderByNextActionDateAsc(LocalDate.now())
            .size();

        return new StatsSummary(totalCompanies, totalEvents, totalInteractions,
                                totalSponsorships, byOutcome, totalSecured, toFollowUp);
    }
}